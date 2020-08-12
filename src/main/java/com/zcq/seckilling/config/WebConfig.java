package com.zcq.seckilling.config;

import com.zcq.seckilling.access.Accessinterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author zhangchuanqiang
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	UserArgumentResolver userArgumentResolver;

	@Autowired
	Accessinterceptor accessInterceptor;

	/**
	 * 框架会回调此方法，往controller里面的参数进行赋值
	 * @param argumentResolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessInterceptor);
	}
}
