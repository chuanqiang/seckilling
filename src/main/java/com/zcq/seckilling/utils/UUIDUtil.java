package com.zcq.seckilling.utils;

import java.util.UUID;

/**
* @Description: 生成uuid
* @Author: zcq
* @Date: 2018/7/3 下午10:42
*/
public class UUIDUtil {
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
