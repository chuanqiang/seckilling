package com.zcq.seckilling.validator;

import com.zcq.seckilling.common.StaticInfo;
import com.zcq.seckilling.utils.JournalUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 日志切面
 * @Author: zcq
 * @Date: 2018/9/4 下午1:43
 */
/*@Component
@Aspect
public class JournalAspect {
	@Autowired
	private JournalUtil journalUtil;

	private static final Logger logger = LoggerFactory.getLogger(JournalAspect.class);

	private final String POINT_CUT = "execution(* com.zcq.seckilling.service..*(..))";

	@Pointcut(POINT_CUT)
	private void journalPointcut() {

	}

	@After(value = "journalPointcut()")
	public void after(JoinPoint joinPoint) {
		//用的最多 通知的签名
		Signature signature = joinPoint.getSignature();
		//1.获取模块类型
		//AOP代理类的名字（包括包名）
		String declaringTypeName = signature.getDeclaringTypeName();
		logger.info("AOP代理类的名字:{}", declaringTypeName);
		//获取代理类的类名
		String className = "";
		if (declaringTypeName.contains(StaticInfo.AOP_SPIT_CLASSNAME1)) {
			String[] split = declaringTypeName.split(StaticInfo.AOP_SPIT_CLASSNAME1);
			className = split[1];
		} else {
			className = declaringTypeName;
		}

		//获取模块名
		String modularTypeName = "";
		if (className.contains(StaticInfo.AOP_SPIT_MODULAR_TYPE1)) {
			String[] modularTypeNames = className.split(StaticInfo.AOP_SPIT_MODULAR_TYPE1);
			modularTypeName = modularTypeNames[0];
		} else {
			modularTypeName = className;
		}

		int modulerType = -1;
		//模块类型筛选
		modulerType = this.getModularType(modularTypeName, modulerType);

		//2.获取操作类型
		//代理的是哪一个方法
		String methodName = signature.getName();
		logger.info("AOP代理方法的名字" + signature.getName());
		int opreationType = -1;
		opreationType = getOpreationType(joinPoint, signature, opreationType, methodName);

		if (modulerType == -1 && opreationType == -1) {
			if (!StringUtils.isBlank(methodName) || !StringUtils.isBlank(modularTypeName)) {
			}
		}

		//3.添加日志
		if (modulerType != -1 && opreationType != -1) {
			//TODO 3.1 从请求获取用户id
			journalUtil.addJournalInfo(modulerType, opreationType, 10086);
		}
	}

	*//**
	 * 模块类型筛选
	 *
	 * @param modularTypeName
	 * @param type
	 * @return
	 *//*
	private int getModularType(String modularTypeName, int type) {
		//模块类型筛选
		switch (modularTypeName) {
			case StaticInfo.AOP_MODULAR_TYPE_FIRST:
				type = StaticInfo.MODEULARTTYPE_FIRST;
				break;
			case StaticInfo.AOP_MODULAR_TYPE_MIAOSHAUSER:
				type = StaticInfo.MODEULARTTYPE_FIRST;
				break;
			//多模块添加
		}
		return type;
	}

	*//**
	 * 获取操作类型
	 *
	 * @param joinPoint
	 * @param signature
	 * @param opreationType
	 * @return
	 *//*
	private int getOpreationType(JoinPoint joinPoint, Signature signature, int opreationType, String methodName) {
		switch (methodName) {
			case StaticInfo.AOP_OPERATION_TYPE_ADD:
				opreationType = StaticInfo.OPERATIONTYPE_ADD;
				break;
			case StaticInfo.AOP_OPERATION_TYPE_EDIT:
				opreationType = StaticInfo.OPERATIONTYPE_UPDATE;
				break;
			case StaticInfo.AOP_OPERATION_TYPE_MOVE:
				opreationType = StaticInfo.OPERATIONTYPE_MOVER;
				break;
			case StaticInfo.AOP_OPERATION_TYPE_DELETE:
				opreationType = StaticInfo.OPERATIONTYPE_DELETE;
				break;
			case StaticInfo.AOP_OPERATION_TYPE_GETBYTOKEN:
				opreationType = StaticInfo.OPERATIONTYPE_GET;
				break;
			case StaticInfo.AOP_OPERATION_TYPE_OPENORCLOSE:
				Object[] obj = joinPoint.getArgs();
				int arg = (int) obj[1];
				if (arg == 1) {
					opreationType = StaticInfo.OPERATIONTYPE_OPEN;
				} else {
					opreationType = StaticInfo.OPERATIONTYPE_CLOSE;
				}
				break;
		}
		return opreationType;
	}
}*/
