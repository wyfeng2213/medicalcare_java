package com.cmcc.medicalcare.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 切面保存请求日志..
 * @author luoyizhou
 *
 */
@Component
@Aspect
public class RequestLogAspect {

	private RequestLog requestLog;

	@Resource
	private IRequestLogService requestLogService;

	private final static Log logger = LogFactory.getLog(RequestLogAspect.class);
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
	
	@Pointcut("@annotation(com.cmcc.medicalcare.annotation.SystemControllerLog)")
	public void controllerAspect() {

	}

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		try {
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
			logger.info("request-----------------"+requestUrl+"----------------------开始");
			
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
			logger.info("---------------------------请求参数-------------------------------");
			logger.info(requestString);
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
				requestLog.setUserLoginId(dataJsonObject.getString("userRole")+"_"+dataJsonObject.getString("phone"));
			}
			String curmonth = df.format(new Date());
			String storage_table = "request_log_"+curmonth;
			requestLog.setStorage_table(storage_table);
			requestLogService.insert(requestLog);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String request = requestLog.getRequest();
				request = EmojiFilter.filterEmoji(request);
				requestLog.setRequest(request);
				requestLogService.insert(requestLog);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@AfterReturning(value = "controllerAspect()", returning = "results")
	public void afterReturning(JoinPoint joinpoint, Object results) throws Throwable {
		try {
			String resultString = new Gson().toJson(results);
			if (resultString != null && resultString.length() > 700) {
				resultString = resultString.substring(0,700);
			}
			requestLog.setState(1);
			requestLog.setResponse(resultString);
			requestLog.setUpdatetime(new Date());
			requestLogService.update("updateByPrimaryKeySelective", requestLog);
			
			logger.info("-----------------------------正常返回参数-----------------------------");
			logger.info(resultString);
			logger.info("response--------------"+requestLog.getUrl()+"----------------正常结束\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@AfterThrowing(value = "controllerAspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
		try {
			String exString = throwableToString(ex);
			String _exString = exString;
			if (exString != null && exString.length() > 700) {
				exString = exString.substring(0,700);
			}
			requestLog.setState(99);
			requestLog.setResponse(exString);
			requestLog.setUpdatetime(new Date());
			requestLogService.update("updateByPrimaryKeySelective", requestLog);
			logger.info("-------------------------------异常返回参数-----------------------------");
			logger.info(_exString);
			logger.info("response-----------------"+requestLog.getUrl()+"---------------异常结束\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
