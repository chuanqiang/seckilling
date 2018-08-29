package com.zcq.seckilling.access;

import com.zcq.seckilling.domain.MiaoshaUser;

public class UserContext {
	private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();

	public static void setUser(MiaoshaUser user){
		userHolder.set(user);
	}
	public static MiaoshaUser getUser(){
		return userHolder.get();
	}
}
