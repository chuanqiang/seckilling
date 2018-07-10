package com.zcq.seckilling.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zcq.seckilling.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

	private Logger log = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	private JedisPool jedisPool;

	/**
	* @Description: 获取单个对象
	* @Method: get
	* @Author: zcq
	* @Date: 2018/7/1 下午4:27
	*/
	public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			jedis = jedisPool.getResource();
			String str = jedis.get(realKey);
			T t = stringToBean(str, clazz);
			return t;
		} finally {
			returnToPool(jedis);
		}
	}
	
	/** 
	* @Description: 设置对象 
	* @Method: set 
	* @Author: zcq
	* @Date: 2018/7/1 下午4:27 
	*/
	public <T> boolean set(KeyPrefix prefix, String key, T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			if (str == null || str.length() <= 0) {
				return false;
			}
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			int seconds = prefix.expireSeconds();
			if (seconds <= 0) {
				jedis.set(realKey, str);
			} else {
				jedis.setex(realKey, seconds, str);
			}
			return true;
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	* @Description: 放置对象
	* @Method: put
	* @Author: zcq
	* @Date: 2018/7/2 下午2:15
	 * @param key key
	 * @param seconds 存活时间
	*/
	public <T> void put(String key, T value, Integer seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			JObjectTranscoder<T> coder = new JObjectTranscoder<T>();
			String result = jedis.setex(key.getBytes(), seconds, coder.serialize(value));
			log.info("存-返回值:" + result);

		} catch (Exception e) {
			log.info("redis异常" + e.getMessage());
		} finally {
			if (jedis != null) {
				returnToPool(jedis);
			}
		}
	}

	public <T> T getObject(String key) {
		T t = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			JObjectTranscoder<T> coder = new JObjectTranscoder<T>();
			t = coder.deserialize(jedis.get(key.getBytes()));
		} catch (Exception e) {
			log.info("redis异常" + e.getMessage());
		} finally {
			if (jedis != null) {
				returnToPool(jedis);
			}
		}
		return t;
	}

	/**
	 * @Description: 判断一个key是否存在
	 * @Method: exists
	 * @Author: zcq
	 * @Date: 2018/7/1 下午4:18
	 */
	public boolean exists(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.exists(realKey);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * @Description: 增加值
	 * @Method: incr
	 * @Author: zcq
	 * @Date: 2018/7/1 下午4:18
	 */
	public Long incr(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.incr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * @Description: 减少值
	 * @Method: decr
	 * @Author: zcq
	 * @Date: 2018/7/1 下午4:18
	 */
	public Long decr(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			return jedis.decr(realKey);
		} finally {
			returnToPool(jedis);
		}
	}

	private <T> String beanToString(T value) {
		if (value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		} else if (clazz == String.class) {
			return (String) value;
		} else if (clazz == long.class || clazz == Long.class) {
			return "" + value;
		} else {
			return JSONObject.toJSONString(value);
		}
	}

	private <T> T stringToBean(String str, Class<T> clazz) {
		if (str == null || str.length() <= 0) {
			return null;
		}
		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		} else if (clazz == String.class) {
			return (T) str;
		} else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		} else {
			return JSONObject.toJavaObject(JSON.parseObject(str), clazz);
		}
	}

	private void returnToPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
}
