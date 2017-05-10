package com.cmcc.medicalcare.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.config.Config;

//import redis.clients.jedis.Jedis;
/**
 * MyDispatcherUtil
 * 
 * @author Administrator
 *
 */
public class MyDispatcherUtil {

	/**
	 * 过滤器方法
	 * 
	 * @param request
	 * @return
	 */
	public static int isDispatcher(HttpServletRequest request) {
		int flag = 0;
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		// System.out.println("过滤==============================================requestUrl："+requestUrl);
		boolean isCheck = true;
		String[] notCheckURLs = Config.notCheckURL.trim().split(";");
		for (int i = 0; i < notCheckURLs.length; i++) {
			if (requestUrl.indexOf(notCheckURLs[i]) > -1) { // 配置文件中不需要检查的请求
				isCheck = false;
				break;
			}
		}
		// System.out.println("isCheck: "+isCheck);
		if (isCheck && requestUrl.startsWith("/appTest/")) {
			flag = dispatcher(request, MySessionContext.APP_CLIENT_SESSION_TYPE);
		} else if (isCheck && requestUrl.startsWith("/h5/")) {
			flag = dispatcher(request, MySessionContext.H5_CLIENT_SESSION_TYPE);
		}

		return flag;
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
//		ApplicationContext applicationContext = WebApplicationContextUtils
//				.getRequiredWebApplicationContext(request.getServletContext());
		boolean flag = false;
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		if (dataJsonObject != null) {
			String session1 = dataJsonObject.getString("session");
//			String role = dataJsonObject.getString("userRole");// "doctor"、"patient"、"secretary"三种角色
			String hPhonenumber = dataJsonObject.getString("phone");
			String session2 = MySessionContext.getInstance().getSession("medicalcare_"+hPhonenumber, distype);
			if (session1 != null && !"".equals(session1)) {
				if (session1.equals(session2)) {
					flag = true;
				}else {
					flag = false;
				}
			} else {
				flag = false;
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
		String url = "/index/400.htm";
		if (flag == 1) {
			url = "/index/400.htm";
		} else if (flag == 2) {
			url = "/index/399.htm";
		}
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
