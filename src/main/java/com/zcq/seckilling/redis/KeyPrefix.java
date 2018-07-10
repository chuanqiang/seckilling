package com.zcq.seckilling.redis;

public interface KeyPrefix {

	/**
	* 过期时间
	*/
	int expireSeconds();

	/**
	* key 的前缀
	*/
	String getPrefix();
}
