package com.zcq.seckilling.controller;


import com.zcq.seckilling.domain.MiaoshaUser;
import com.zcq.seckilling.domain.OrderInfo;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.result.CodeMsg;
import com.zcq.seckilling.result.Result;
import com.zcq.seckilling.service.GoodsService;
import com.zcq.seckilling.service.MiaoshaUserService;
import com.zcq.seckilling.service.OrderService;
import com.zcq.seckilling.vo.GoodsVo;
import com.zcq.seckilling.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	MiaoshaUserService userService;

	@Autowired
	RedisService redisService;

	@Autowired
	OrderService orderService;

	@Autowired
	GoodsService goodsService;

	@RequestMapping("/detail")
	@ResponseBody
	public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
	                                  @RequestParam("orderId") long orderId) {
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		OrderInfo order = orderService.getOrderById(orderId);
		if (order == null) {
			return Result.error(CodeMsg.ORDER_NOT_EXIST);
		}
		long goodsId = order.getGoodsId();
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		OrderDetailVo vo = new OrderDetailVo();
		vo.setOrder(order);
		vo.setGoods(goods);
		return Result.success(vo);
	}

}
