package com.cmcc.medicalcare.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.cache.MySessionContext;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.model.DoctorsLoginInfo;
import com.cmcc.medicalcare.model.PatientLoginInfo;
import com.cmcc.medicalcare.model.SecretaryLoginInfo;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.model.SystemUser;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.service.ISecretaryLoginInfoService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;

//import redis.clients.jedis.Jedis;
/**
 * MyDispatcherUtil
 * 
 * @author Administrator
 *
 */
public class MyDispatcherUtil {

	private static ISensitiveRecordLogService sensitiveRecordLogService = (ISensitiveRecordLogService) SpringConfigTool
			.getBean("sensitiveRecordLogService");
	
	/**
	 * 过滤器方法
	 * 
	 * @param request
	 * @return
	 */
	public static int isDispatcher(HttpServletRequest request) {
		int flag = 0;
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
//		System.out.println("过滤==============================================requestUrl："+requestUrl);
		boolean isCheck = true;
		String[] notCheckURLs = Config.notCheckURL.trim().split(";");
		for (int i = 0; i < notCheckURLs.length; i++) {
			if (requestUrl.indexOf(notCheckURLs[i]) > -1) { // 配置文件中不需要检查的请求
				isCheck = false;
				break;
			}
		}
		
		//检查敏感词
//		if (requestUrl.startsWith("/app/")||requestUrl.startsWith("/sys/")) {
//			flag = checkSensitiveWords(request);
//			return flag;
//		}
		
		if (isCheck && requestUrl.startsWith("/app/")) {
			flag = dispatcher(request, MySessionContext.APP_CLIENT_SESSION_TYPE);
		} else if (isCheck && requestUrl.startsWith("/h5/")) {
			flag = dispatcher(request, MySessionContext.H5_CLIENT_SESSION_TYPE);
		}else if (isCheck && requestUrl.startsWith("/web/")) {
			flag = dispatcher(request, MySessionContext.WEB_SECRETARY_SESSION_TYPE);
		}

		return flag;
	}
	
	public static int checkSensitiveWords(HttpServletRequest request){
		int flag = 0;
		String requestParameters = getValue(request);
		try {
			requestParameters = java.net.URLDecoder.decode(requestParameters,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String uri = request.getRequestURI();
		
		if (uri.startsWith("/medicalcare/sys/sysSensitiveWord")) {//敏感词后台管理不需要检查
			return flag;
		}
		
		if (requestParameters != null) {
			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(requestParameters);

			if (sensitiveWord != null && sensitiveWord.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());

				sensitiveRecordLog.setType(sensitiveWord.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				request.setAttribute("sensitiveWord", sensitiveWord.getText());

				if (uri.startsWith("/medicalcare/app/")) {
					String equipmentData = request.getParameter("equipmentData");
					JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
					if (dataJsonObject2 != null) {
						String userName = dataJsonObject2.getString("userName");
						String phone = dataJsonObject2.getString("phone");
						sensitiveRecordLog.setUserName(userName);
						sensitiveRecordLog.setUserLoginid(dataJsonObject2.getString("userRole") + "_" + phone);
						sensitiveRecordLog.setUserPhone(phone);
						sensitiveRecordLogService.insert(sensitiveRecordLog);
						flag = 3;
						return flag;
					}
				} else if (uri.startsWith("/medicalcare/sys/")) {
					SystemUser user = (SystemUser) request.getSession().getAttribute("systemUser");
					sensitiveRecordLog.setUserName(user.getUserName());
					sensitiveRecordLog.setUserPhone(user.getUserTel());
					sensitiveRecordLogService.insert(sensitiveRecordLog);
					flag = 4;
					return flag;
				}
			}
		}
	
		return flag;
	}

	
	public static String getValue(HttpServletRequest request) {
		String value = "";
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			if (!"_dc".equals(paraName) && !"node".equals(paraName)) {// _dc的参数不要
				String[] arr = request.getParameterValues(paraName);
				if (arr != null && arr.length > 1) {
					value += paraName + "=" + convertObjectArrToStr(arr) + ";";
				} else {
					value += paraName + "=" + request.getParameter(paraName) + ";";
				}
			}
		}
		return value;
	}

	public static String convertObjectArrToStr(Object[] arr) {
		String result = "";
		if (arr != null && arr.length > 0) {
			for (int i = 0; i < arr.length; i++) {
				if (!"".equals(String.valueOf(arr[i]))) {
					result += String.valueOf(arr[i]) + ",";
				}
			}
			if (!"".equals(result)) {
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}
	
	/**
	 * 拦截app或者h5
	 * 
	 * @param request
	 * @return
	 */
	public static int dispatcher(HttpServletRequest request, int distype) {
		int flag = 0;
		boolean isCheckSession = isCheckSession(request, distype);
		if (!isCheckSession) {
			flag = 1;
		}
		return flag;
	}

	/**
	 * session过滤方法
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isCheckSession(HttpServletRequest request, int distype) {
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getServletContext());
		boolean flag = false;
		String equipmentData = request.getParameter("equipmentData");
//		System.out.println("---------------------------------------------------:"+equipmentData);
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		if (dataJsonObject != null) {
			String session1 = dataJsonObject.getString("session");
			String hPhonenumber = dataJsonObject.getString("phone");
			String role = dataJsonObject.getString("userRole");// "doctor"、"patient"、"secretary"三种角色

			if ("doctor".equals(role)) {
				IDoctorsLoginInfoService doctorsLoginInfoService = applicationContext.getBean("doctorsLoginInfoService",
						IDoctorsLoginInfoService.class);
				DoctorsLoginInfo doctorsLoginInfo = doctorsLoginInfoService.findByParam("selectByPhone", hPhonenumber);
//				String session2 = MySessionContext.getInstance().getSession("doctor_"+hPhonenumber, distype);
				
				if (StringUtils.isNotBlank(session1)) {
					if (doctorsLoginInfo!=null) {
						String session2 = doctorsLoginInfo.getToken();
						if (StringUtils.isNotBlank(session2)&&session1.equals(session2)) {
							flag = true;
						}else {
//							MySessionContext.getInstance().setSession(hPhonenumber, session2, distype);// 设置缓存
							flag = false;
						}
					} else {
						flag = false;
					}
					
				} else {
					flag = false;
				}
			} else if("patient".equals(role)) {
				IPatientLoginInfoService patientLoginInfoService = applicationContext.getBean("patientLoginInfoService",
						IPatientLoginInfoService.class);
				PatientLoginInfo patientLoginInfo = patientLoginInfoService.findByParam("selectByPhone", hPhonenumber);
//				String session2 = MySessionContext.getInstance().getSession("patient_"+hPhonenumber, distype);
				if (StringUtils.isNotBlank(session1)) {
					if (patientLoginInfo!=null) {
						String session2 = patientLoginInfo.getToken();
						if (StringUtils.isNotBlank(session2)&&session1.equals(session2) && patientLoginInfo.getIsEnable() == 1) {
							flag = true;
						}else {
//							MySessionContext.getInstance().setSession(hPhonenumber, session2, distype);// 设置缓存
							flag = false;
						}
					} else {
						flag = false;
					}
					
				} else {
					flag = false;
				}
			}else if ("secretary".equals(role)) {
				ISecretaryLoginInfoService secretaryLoginInfoService = applicationContext.getBean("secretaryLoginInfoService",
						ISecretaryLoginInfoService.class);
				SecretaryLoginInfo secretaryLoginInfo = secretaryLoginInfoService.findByParam("selectByPhone", hPhonenumber);
//				String session2 = MySessionContext.getInstance().getSession("secretary_"+hPhonenumber, distype);
				if (StringUtils.isNotBlank(session1)) {
					if (secretaryLoginInfo!=null) {
						String session2 = secretaryLoginInfo.getToken();
						if (StringUtils.isNotBlank(session2)&&session1.equals(session2)) {
							flag = true;
						}else {
//							MySessionContext.getInstance().setSession(hPhonenumber, session2, distype);// 设置缓存
							flag = false;
						}
					} else {
						flag = false;
					}
					
				} else {
					flag = false;
				}
			}
			
			
		}
		return flag;
	}

	/**
	 * 拦截器跳转页面
	 * 
	 * @param request
	 * @param flag
	 */
	public static void goDispatch(HttpServletRequest request, HttpServletResponse response, int flag) {
		String url = "/index/400.action";
		if (flag == 1) {
			url = "/index/400.action";
		} else if (flag == 2) {
			url = "/index/399.action";
		}else if (flag == 3) {
			url = "/index/244_APP.action";
		}else if (flag == 4) {
			url = "/index/244_SYSTEM.action";
		}
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
