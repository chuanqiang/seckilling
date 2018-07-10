package com.zcq.seckilling.service;

import com.zcq.seckilling.dao.MiaoshaUserDao;
import com.zcq.seckilling.domain.LoginVo;
import com.zcq.seckilling.domain.MiaoshaUser;
import com.zcq.seckilling.exception.GlobalException;
import com.zcq.seckilling.redis.MiaoshaUserKey;
import com.zcq.seckilling.redis.RedisService;
import com.zcq.seckilling.result.CodeMsg;
import com.zcq.seckilling.utils.MD5Util;
import com.zcq.seckilling.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * @author zhangchuanqiang
 */
@Service
public class MiaoshaUserService {
	public static final String COOKIE_NAME_TOKEN = "token";

	@Autowired
	private RedisService redisService;

	@Autowired
	MiaoshaUserDao miaoshaUserDao;

	/**
	 * 执行登录
	 *
	 * @param loginVo
	 * @param response
	 * @return
	 */
	public boolean login(LoginVo loginVo, HttpServletResponse response) {
		if (loginVo == null) {
			throw new GlobalException(CodeMsg.SERVICE_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		// 判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if (user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		// 验证密码
		String dbPass = user.getPassword();
		String salt = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, salt);
		if (!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		// 生成token
		String token = UUIDUtil.uuid();
		addUserCookie(response, token, user);
		return true;
	}

	private void addUserCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}

	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		if (user != null) {
			// 延长cookie 有效期
			addUserCookie(response, token, user);
		}
		return user;
	}
}
