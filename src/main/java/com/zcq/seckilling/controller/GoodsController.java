package com.zcq.seckilling.controller;

import com.zcq.seckilling.domain.MiaoshaUser;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
	    if (user != null) {
		    model.addAttribute("user", user);
	    }else{
		    return "redirect:/login/to_login";
	    }
        return "goods_list";
    }
}
