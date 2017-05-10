package com.cmcc.medicalcare.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.model.RequestLog;
import com.cmcc.medicalcare.service.IRequestLogService;

/**
 * 切面请求日志
 * @author zhouzhou
 *
 */
@Component
@Aspect
public class RequestLogAspect {

	private RequestLog requestLog;

	@Resource
	private IRequestLogService requestLogService;

	@Pointcut("@annotation(com.cmcc.medicalcare.annotation.SystemControllerLog)")
	public void controllerAspect() {

	}

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String serverIp = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort();
		String clientIp = getIpAddrByRequest(request);
		requestLog = new RequestLog();
		requestLog.setState(0);
		requestLog.setCreatetime(new Date());
		requestLog.setClientIp(clientIp);
		requestLog.setServerIp(serverIp);
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		requestLog.setUrl(requestUrl);
		Enumeration<String> pNames = request.getParameterNames();
		String requestString = "";
		while (pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			requestString = requestString + name + "=" + value + "&";
		}
		if (requestString.length() > 1000) {
			requestString = requestString.substring(0, 900);
		}
		requestLog.setRequest(requestString);

		String acctionName;
		try {
			acctionName = getControllerMethodDescription(joinPoint);
			requestLog.setAcctionName(acctionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String methodName = joinPoint.getSignature().getName();
		requestLog.setMethodName(methodName);

		String className = joinPoint.getTarget().getClass().getSimpleName();
		requestLog.setClassName(className);

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		if (dataJsonObject != null) {
			requestLog.setUserName(dataJsonObject.getString("userName"));
			requestLog.setUserPhone(dataJsonObject.getString("phone"));
			requestLog.setUserType(dataJsonObject.getString("userRole"));
			requestLog.setVersion(dataJsonObject.getString("packageVesion"));
			requestLog.setSystem(dataJsonObject.getString("systemtype"));
			requestLog.setModel(dataJsonObject.getString("model"));
		}
		requestLogService.insert(requestLog);
	}

	@AfterReturning(value = "controllerAspect()", returning = "results")
	public void afterReturning(JoinPoint joinpoint, Object results) throws Throwable {
		String resultString = JSONObject.toJSONString(results);
		if (resultString != null && resultString.length() > 700) {
			resultString = resultString.substring(0,700);
		}
		requestLog.setState(1);
		requestLog.setResponse(resultString);
		requestLog.setUpdatetime(new Date());
		requestLogService.update("updateByPrimaryKeySelective", requestLog);
	}

	@AfterThrowing(value = "controllerAspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		String exString = throwableToString(ex);
		if (exString != null && exString.length() > 700) {
			exString = exString.substring(0,700);
		}
		requestLog.setState(99);
		requestLog.setResponse(exString);
		requestLog.setUpdatetime(new Date());
		requestLogService.update("updateByPrimaryKeySelective", requestLog);
	}

	/**
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * @param request
	 *            IP
	 * @return IP Address
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	private String throwableToString(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

}
