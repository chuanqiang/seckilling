package com.zcq.seckilling.redis;

public class CountKey extends BasePrefix {

	private CountKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	private CountKey(String prefix) {
		super(prefix);
	}

	public static CountKey getPv = new CountKey("pv");
	public static CountKey getUv = new CountKey("uv");
}
