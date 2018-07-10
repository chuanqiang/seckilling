package com.zcq.seckilling.controller;

import com.zcq.seckilling.domain.LoginVo;
import com.zcq.seckilling.domain.User;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.result.Result;
import com.zcq.seckilling.service.MiaoshaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.ws.soap.Addressing;


/**
 * @Description: 用户登录
 * @Author: zcq
 * @Date: 2018/7/1 下午5:10
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private MiaoshaUserService userService;
	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/to_login")
	public String toLogin() {
		return "login";
	}

	@RequestMapping(value = "/do_login")
	@ResponseBody
	public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
		log.info(loginVo.toString());
		userService.login(loginVo, response);
		return Result.success(true);
	}

	/*@RequestMapping(value = "tp_list")
	public String toList(@CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
	                     @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false) String paramToken,
	                     Model model, HttpServletResponse response){
		if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
			return "login";
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		User user = userService.getByToken(response,token);
		model.addAttribute("user", user);
		return "good_list";
	}*/

	@RequestMapping(value = "tp_list")
	public String toList(Model model, User user){
		if (user != null) {
			model.addAttribute("user", user);
		}else{
			return "redirect:/login/to_login";
		}
		return "good_list";
	}
}
