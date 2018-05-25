/**   
* @Title: ThirdInterfaceUtils.java 
* @Package com.cmcc.medicalcare.utils 
* @Description: TODO
* @author adminstrator   
* @date 2018年1月15日 下午7:27:29 
* @version V1.0   
*/
package com.cmcc.medicalcare.utils;

import com.cmcc.medicalcare.config.Constant;

/**
 * @ClassName: ThirdInterfaceUtils
 * @Description: TODO
 * @author adminstrator
 * @date 2018年1月15日 下午7:27:29
 * 
 */
public class ThirdInterfaceUtils {

	public static String getMD5signstr(String method, String timestamp) {
		String signstr = method + "&" + Constant.APPID + "&" + timestamp + "&" + Constant.KEY;
		System.out.println("signstr: " + signstr);
		//signstr = MD5.encode(signstr);
		signstr = MD5Utils.MD5Encode(signstr, "UTF-8");
		return signstr;
	}
	
	public static String getMD5signstr(String method, String appid, String timestamp) {
		String signstr = method + "&" + appid + "&" + timestamp + "&" + Constant.KEY;
		System.out.println("signstr1: " + signstr);
		//signstr = MD5.encode(signstr);
		signstr = MD5Utils.MD5Encode(signstr, "UTF-8");
		System.out.println("signstr2: " + signstr);
		return signstr;
	}

	public static String getURL(String url, String timestamp, String MD5Signstr) {
		String menberLoginUrl = url + "appid="+Constant.APPID + "&" + "timestamp="+timestamp + "&" + "signtype="+ Constant.SIGNTYPE + "&" + "signstr="+ MD5Signstr;
		System.out.println("url:  "+menberLoginUrl);
		return menberLoginUrl;
	}
	
	public static String getURL(String url, String appid,String timestamp, String MD5Signstr) {
		String menberLoginUrl = url + "appid="+appid + "&" + "timestamp="+timestamp + "&" + "signtype="+ Constant.SIGNTYPE + "&" + "signstr="+ MD5Signstr;
		System.out.println("url:  "+menberLoginUrl);
		return menberLoginUrl;
	}
}
