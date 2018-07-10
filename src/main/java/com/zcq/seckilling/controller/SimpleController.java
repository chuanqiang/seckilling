package com.zcq.seckilling.controller;

import com.zcq.seckilling.domain.User;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.redis.UserKey;
import com.zcq.seckilling.result.CodeMsg;
import com.zcq.seckilling.result.Result;
import com.zcq.seckilling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试
 * @Author: zcq
 * @Date: 2018/7/1 下午5:11
 */
@RestController
@RequestMapping("/demo")
public class SimpleController {
	@Autowired
	private UserService userService;

	@Autowired
	private RedisService redisService;

	@RequestMapping("/hello")
	public Result<String> hello(){
		return Result.success("hello world");
	}

	@RequestMapping("/helloError")
	public Result<String> helloError(){
		return Result.error(CodeMsg.SERVICE_ERROR);
	}

	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "zhangan");
		return "hello";
	}

	@GetMapping("/dbGet")
	public String dbGet() {
		User user = userService.getById(1);
		return user.toString();
	}

	@GetMapping("/dbTx")
	public void dbTx() {
		userService.dbTx();
	}

	@GetMapping("/redisGet")
	public String redisGet() {
		User user = redisService.getObject(UserKey.getById+"");
		return user.toString();
	}

	@RequestMapping("/redisSet")
	public void redisSet() {
		User user = new User(2, "1234");
		redisService.put(UserKey.getById+"", user,60);
	}
}
