package com.zcq.seckilling.validator;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class LocalService {
	public static String LOCK_NAME = "lockName";
	public static String WAIT_TIME = "waitTime";
	public static String EFFECTIVE_TIME = "effectiveTime";

	private static Logger log = LoggerFactory.getLogger(LocalService.class);

	@Pointcut("@annotation(com.zcq.seckilling.validator.Lock)")
	public void lockPointcut() {

	}

	@Around("lockPointcut()")
	public Object arount(ProceedingJoinPoint point) throws Throwable {
		Map<String, Object> map = getLockParams(point);
		String lockName = (String) map.get(LOCK_NAME);
		int waitTime = (int) map.get(WAIT_TIME);
		int effectiveTime = (int) map.get(EFFECTIVE_TIME);
		Object[] methodParam = null;
		Object object = null;
		//获取方法参数
		methodParam = point.getArgs();
		Object lockKey = methodParam[0];
		try {
			log.info("lockName:{},waitTime:{},effectiveTime:{}", lockName, waitTime, effectiveTime);
			log.info("lockKey:{}", lockKey.toString());
			object = point.proceed(point.getArgs());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("网络错误，请重试");
		} finally {

		}
		return object;
	}

	/**
	 * @Description: 获取方法的中锁参数
	 * @Author: zcq
	 * @Date: 2018/9/4 上午10:49
	 */
	public Map<String, Object> getLockParams(ProceedingJoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();

		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		Map<String, Object> map = new HashMap<>();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				// 获取方法形参列表
				Class[] temCs = method.getParameterTypes();
				if (temCs.length == arguments.length) {
					Lock lock = method.getAnnotation(Lock.class);
					if (lock != null) {
						String lockName = lock.lockName();
						int waitTime = lock.waitTime();
						int effectiveTime = lock.effectiveTime();
						map.put(LOCK_NAME, lockName);
						map.put(WAIT_TIME, waitTime);
						map.put(EFFECTIVE_TIME, effectiveTime);
					}
					break;
				}
			}
		}
		return map;
	}
}
