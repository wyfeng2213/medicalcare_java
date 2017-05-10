package com.cmcc.medicalcare.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cmcc.medicalcare.annotation.SystemControllerLog;

@Component
@Aspect
public class LogAspect {

//	@Pointcut("@annotation(com.cmcc.medicalcare.annotation.SystemControllerLog)")
//	public void controllerAspect() {
//	}
//
//	@Before("controllerAspect()")
//	public void doBefore(JoinPoint joinPoint) {
//		System.out.println("joinPointjoinPointjoinPointjoinPointjoinPointjoinPoint");
//		// // try {
//		// // System.out.println("方法描述:" +
//		// getControllerMethodDescription(joinPoint));
//		// System.out.println("方法描述:------");
//		// } catch (Exception e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//	}

	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	// @Pointcut("execution(* *..*Controller.*(..))")
	// public void checkToken() {
	// }
	//
	// @Before("checkToken()")
	// public void beforeCheckToken() {
	// System.out.println("调用方法之前。。。。");
	// }
	//
	// @AfterReturning("checkToken()")
	// public void afterCheckToken() {
	// System.out.println("调用方法结束之后。。。。");
	// }
	//
	// // 抛出异常时才调用
	// @AfterThrowing("checkToken()")
	// public void afterThrowing() {
	// System.out.println("校验token出现异常了......");
	// }

}
