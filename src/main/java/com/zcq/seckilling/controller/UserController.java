package com.zcq.seckilling.controller;

import com.zcq.seckilling.domain.MiaoshaUser;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.result.Result;
import com.zcq.seckilling.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	MiaoshaUserService userService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/info")
	@ResponseBody
	public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
		return Result.success(user);
	}

}
