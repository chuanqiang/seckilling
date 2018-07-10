package com.zcq.seckilling.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Description: 参数工具, 用于处理参数
 * @Author: zcq
 * @Date: 2018/7/2 上午10:28
 */
public class ValidatorUtil {
	private static final Pattern mobile_pattern = compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");

	public static boolean isMobile(String str) {
		Pattern pattern = compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static Boolean isEmail(String str) {
		Pattern pattern = compile("^([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+.[a-zA-Z]{2,3}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static Boolean isQQ(String str) {
		Pattern pattern = compile("^\\d{5,11}$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static boolean isUserName(String str) {
		Pattern pattern = compile("^[a-zA-Z]+[a-zA-Z0-9_]+");
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	public static boolean isChinese(String str) {
		Pattern pattern = compile("^[\u4e00-\u9fa5]+$");
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	public static boolean isChinaeseAndWord(String str) {
		Pattern pattern = compile("^[\u4e00-\u9fa5\\w]+$");
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	public static boolean isNum(String str) {
		Pattern pattern = compile("[0-9X|x]*");
		return pattern.matcher(str).matches();
	}


	public static void main(String[] args) {
	}
}
