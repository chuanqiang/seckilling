package com.zcq.seckilling.controller;

import com.zcq.seckilling.access.AccessLimit;
import com.zcq.seckilling.domain.MiaoshaOrder;
import com.zcq.seckilling.domain.MiaoshaUser;
import com.zcq.seckilling.rabbitmq.MQSender;
import com.zcq.seckilling.rabbitmq.MiaoshaMessage;
import com.zcq.seckilling.redis.GoodsKey;
import com.zcq.seckilling.redis.MiaoshaKey;
import com.zcq.seckilling.redis.OrderKey;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.result.CodeMsg;
import com.zcq.seckilling.result.Result;
import com.zcq.seckilling.service.GoodsService;
import com.zcq.seckilling.service.MiaoshaService;
import com.zcq.seckilling.service.MiaoshaUserService;
import com.zcq.seckilling.service.OrderService;
import com.zcq.seckilling.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

	@Autowired
	MiaoshaUserService userService;

	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	MiaoshaService miaoshaService;

	@Autowired
	MQSender sender;

	private Map<Long, Boolean> localOverMap = new HashMap();

	/**
	 * @Description: 容器启动的之后回回调此方法（系统初始化）
	 * @Author: zcq
	 * @Date: 2018/8/18 下午10:44
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> goodsVos = goodsService.listGoodsVo();
		if (goodsVos == null) {
			return;
		}
		for (GoodsVo goodsVo : goodsVos) {
			redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
			localOverMap.put(goodsVo.getId(), false);
		}
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	@ResponseBody
	public Result<Boolean> reset(Model model) {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		for (GoodsVo goods : goodsList) {
			goods.setStockCount(10);
			redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), 10);
			localOverMap.put(goods.getId(), false);
		}
		redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
		redisService.delete(MiaoshaKey.isGoodsOver);
		miaoshaService.reset(goodsList);
		return Result.success(true);
	}

	/**
	 * QPS:1306
	 * 5000 * 10
	 */
	@RequestMapping(value = "/{path}/do_miaosha", method = RequestMethod.POST)
	@ResponseBody
	public Result<Integer> list(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId, @PathVariable("path") String path) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}

		// 验证path
		boolean check = miaoshaService.checkPath(user, goodsId, path);
		if(!check){
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		}

		// 库存标记，减少redis访问
		boolean isOver = localOverMap.get(goodsId);
		if (isOver) {
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
		if (stock < 0) {
			localOverMap.put(goodsId, true);
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}

		// 判断是否被秒杀到了
		MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if (miaoshaOrder != null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}

		// 入队
		MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
		miaoshaMessage.setGoodsId(goodsId);
		miaoshaMessage.setUser(user);
		sender.sendMiaoshaMessage(miaoshaMessage);
		// 0:排队中
		return Result.success(0);

		//判断库存
		/*GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if(stock <= 0) {
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		//判断是否已经秒杀到了
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if(order != null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		//减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
		return Result.success(orderInfo);*/
	}

	/**
	 * orderId：成功
	 * -1：秒杀失败
	 * 0： 排队中
	 */
	@RequestMapping(value = "/miaoshaResult", method = RequestMethod.GET)
	@ResponseBody
	public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
		return Result.success(result);
	}

	/**
	 * @Description: 获取秒杀地址
	 * @Author: zcq
	 * @Date: 2018/8/19 下午5:28
	 */
	@AccessLimit(seconds=5, maxCount=5, needLogin=true)
	@RequestMapping(value = "/getMiaoshaPath", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> getMiaoshaPath(MiaoshaUser user, @RequestParam("goodsId") long goodsId, Model model, @RequestParam(value="verifyCode", defaultValue="0")int verifyCode) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
		boolean check = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
		if(!check) {
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		}
		String path  =miaoshaService.createMiaoshaPath(user, goodsId);
		return Result.success(path);
	}

	@RequestMapping(value="/verifyCode", method=RequestMethod.GET)
	@ResponseBody
	public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
		if(user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		try {
			BufferedImage image  = miaoshaService.createVerifyCode(user, goodsId);
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
			out.flush();
			out.close();
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.MIAOSHA_FAIL);
		}
	}
}
