package com.zcq.seckilling.validator;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {
	//分布式锁的key前缀
	String lockName() default "";

	//等待时长
	int waitTime() default 3;

	//有效期时长
	int effectiveTime() default 5;
}
