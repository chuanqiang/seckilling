package com.zcq.seckilling.controller;

import com.zcq.seckilling.domain.MiaoshaUser;
import com.zcq.seckilling.redis.CountKey;
import com.zcq.seckilling.redis.GoodsKey;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.result.Result;
import com.zcq.seckilling.service.GoodsService;
import com.zcq.seckilling.service.MiaoshaUserService;
import com.zcq.seckilling.vo.GoodsDetailVo;
import com.zcq.seckilling.vo.GoodsVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;

	@Autowired
	RedisService redisService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private ThymeleafViewResolver thymeleafViewResolver;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * @Description: 到商品列表页(增加页面缓存)
	 * @Author: zcq
	 * @Date: 2018/7/17 下午7:53
	 */
	@RequestMapping(value = "/to_list", produces = "text/html")
	@ResponseBody
	public String list(Model model, MiaoshaUser user, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("user", user);

		// 增加redis计数器
		Long pv = redisService.getPv(request.getRequestURI());
		if (pv != null) {
			// 如果做了缓存,就没法统计展现量
			System.out.println("=======> pv=" + pv);
		}
		if (user != null) {
			redisService.getUv(user.getId().toString());
		}

		// 取缓存
		String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
		if (!StringUtils.isEmpty(html)) {
			return html;
		}
		//查询商品列表
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		// return "goods_list";
		// 手动渲染
		SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext);
		html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
		if (!StringUtils.isEmpty(html)) {
			redisService.set(GoodsKey.getGoodsList, "", html);
		}
		return html;
	}

	/**
	 * @Description: 到商品列表页（增加页面缓存）
	 * @Author: zcq
	 * @Date: 2018/7/17 下午8:02
	 */
	@RequestMapping(value = "/to_detail2/{goodsId}",produces = "text/html")
	@ResponseBody
	public String detail2(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId,HttpServletRequest request,HttpServletResponse response) {
		model.addAttribute("user", user);

		//取缓存
		String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
		if(!org.apache.commons.lang3.StringUtils.isEmpty(html)) {
			return html;
		}

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus;
		int remainSeconds;
		//秒杀还没开始，倒计时
		if (now < startAt) {
			miaoshaStatus = 0;
			remainSeconds = (int) ((startAt - now) / 1000);
		} else if (now > endAt) {
			//秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		} else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		// return "goods_detail";
		SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
		html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
		if(!org.apache.commons.lang3.StringUtils.isEmpty(html)) {
			redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
		}
		return html;
	}

	@RequestMapping(value="/detail/{goodsId}")
	@ResponseBody
	public Result<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user, @PathVariable("goodsId")long goodsId) {
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();
		int miaoshaStatus = 0;
		int remainSeconds = 0;
		if(now < startAt ) {//秒杀还没开始，倒计时
			miaoshaStatus = 0;
			remainSeconds = (int)((startAt - now )/1000);
		}else  if(now > endAt){//秒杀已经结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		}else {//秒杀进行中
			miaoshaStatus = 1;
			remainSeconds = 0;
		}
		GoodsDetailVo vo = new GoodsDetailVo();
		vo.setGoods(goods);
		vo.setUser(user);
		vo.setRemainSeconds(remainSeconds);
		vo.setMiaoshaStatus(miaoshaStatus);
		return Result.success(vo);
	}
}
