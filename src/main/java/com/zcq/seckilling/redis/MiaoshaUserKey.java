package com.zcq.seckilling.redis;

public class MiaoshaUserKey extends BasePrefix{
	private static final int TOKEN_EXPIRE = 3600*24*2;
	public MiaoshaUserKey(int expireSecond,String prefix) {
		super(expireSecond,prefix);
	}

	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
	public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
}
