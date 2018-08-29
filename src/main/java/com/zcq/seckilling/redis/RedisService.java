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
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * @Description: 放置到set中
	 * @Author: zcq
	 * @Date: 2018/8/14 下午4:36
	 */
	public <T> T getSet(KeyPrefix prefix, String key, String value, Class<T> clazz) {
		Jedis jedis = null;
		try {
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			jedis = jedisPool.getResource();
			String str = jedis.getSet(realKey,value);
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
	 * @Description: 设置对象到set
	 * @Author: zcq
	 * @Date: 2018/7/1 下午4:27
	 */
	public <T> boolean setSet(KeyPrefix prefix, String key, T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			if (str == null || str.length() <= 0) {
				return false;
			}
			// 生成真正的key
			String realKey = prefix.getPrefix() + key;
			jedis.sadd(realKey, str);
			return true;
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * @param key     key
	 * @param seconds 存活时间
	 * @Description: 放置对象
	 * @Method: put
	 * @Author: zcq
	 * @Date: 2018/7/2 下午2:15
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
	 * 删除
	 */
	public boolean delete(KeyPrefix prefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			//生成真正的key
			String realKey = prefix.getPrefix() + key;
			long ret = jedis.del(realKey);
			return ret > 0;
		} finally {
			returnToPool(jedis);
		}
	}

	public boolean delete(KeyPrefix prefix) {
		if(prefix == null) {
			return false;
		}
		List<String> keys = scanKeys(prefix.getPrefix());
		if(keys==null || keys.size() <= 0) {
			return true;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

	public List<String> scanKeys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<String>();
			String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*"+key+"*");
			sp.count(100);
			do{
				ScanResult<String> ret = jedis.scan(cursor, sp);
				List<String> result = ret.getResult();
				if(result!=null && result.size() > 0){
					keys.addAll(result);
				}
				//再处理cursor
				cursor = ret.getStringCursor();
			}while(!cursor.equals("0"));
			return keys;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
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

	public static  <T> String beanToString(T value) {
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

	public static <T> T stringToBean(String str, Class<T> clazz) {
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

	/**
	 * @Description: 统计pv(page count)
	 * @Author: zcq
	 * @Date: 2018/8/14 下午3:53
	 */
	public Long getPv(String pageUrl) {
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Long pv = this.get(CountKey.getPv, "_" + pageUrl + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), Long.class);
		if (pv != null) {
			pv = this.incr(CountKey.getPv, "_" + pageUrl + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		} else {
			this.set(CountKey.getPv, "_" + pageUrl + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 0);
		}
		return pv;
	}

	/**
	 * @Description: 统计uv(user count)
	 * @Author: zcq
	 * @Date: 2018/8/14 下午3:53
	 */
	public void getUv(String user) {
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		this.set(CountKey.getUv, "_" + user + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()), user);
	}
}
