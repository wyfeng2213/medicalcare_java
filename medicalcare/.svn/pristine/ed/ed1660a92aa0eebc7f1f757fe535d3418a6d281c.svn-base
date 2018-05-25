package com.cmcc.medicalcare.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.config.Config;

/**
 * 工具类
 * @author 
 *
 */
public class EcopUtils {
	private static Log log = LogFactory.getLog(EcopUtils.class);
	private static String URL = Config.getProperty("ecopUrl", "");
	private static String CHANNELID = "CMGDHBHelper";

	/**
	 * 发送验证码
	 * @param telphone
	 * @return
	 */
	public static boolean sendValcode(String telphone){
		
		boolean flag = false;
		String content =
				"{\"process_code\":\"smsauthsend\",\"menuid\":\"0\",\"operatorid\":\"0\",\"channelid\":\""
						+ CHANNELID
						+ "\",\"unitid\":\"0\",\"servnumber\":"
						+ telphone + "}";
		
		String ss = HTTPUtils.httpDoPostStrT(URL, content, "UTF-8", "UTF-8");
	    
		System.out.println(ss);
		if (ss!=null&&StringUtils.isNotBlank(ss)) {
			if (ss.startsWith("{")) {
				JSONObject json = JSONObject.parseObject(ss);
				String plaJson = json.getString("recode");
				if (!plaJson.equals("0")) {
					log.info("AjaxGetRetCode : " + telphone
							+ " connect is fail");
					flag = false;
				} else {
					String rettype =
							json.getJSONObject("msg")
									.getJSONObject("msgheader")
									.getJSONObject("retinfo")
									.getString("rettype").trim();
					String retcode =
							json.getJSONObject("msg")
									.getJSONObject("msgheader")
									.getJSONObject("retinfo")
									.getString("retcode").trim();
					//							String  retmsg =json.getJSONObject("msg").getJSONObject("msgheader").getJSONObject("retinfo").getString("retmsg");
					if (retcode.equals("0") && rettype.equals("0")) {//成功
						log.info("AjaxGetRetCode : " + telphone
								+ " request is success");
						flag = true;
						
					} else {
						log.info("AjaxGetRetCode :" + telphone
								+ " request is fail");
						flag = false;
					}
				}
			}
		} else {
			log.info("AjaxGetRetCode : " + telphone + " ecop error");
		}
		return flag ;
	}
	/**
	 * 校验验证码
	 * @param telphone
	 * @param identifyCode
	 * @return
	 */
	public static Map<String,Object> checkValcode(String telphone ,String identifyCode){
		Map<String,Object> flagMap = new HashMap<String, Object>();
		boolean flag = false;
		if(StringUtils.isBlank(telphone) || StringUtils.isBlank(identifyCode)){
			log.info("AjaxCheckCode : param is null");
		}else{
			 String content="{\"process_code\":\"smsauthcheck\",\"menuid\":\"0\",\"operatorid\":\"0\",\"channelid\":\""+CHANNELID+"\",\"unitid\":\"0\",\"servnumber\":"+telphone+",\"smsno\":\""+identifyCode+"\"}";
				
			 String ss = HTTPUtils.httpDoPostStrT(URL,content , "UTF-8","UTF-8");
			 
			 if (ss != null) {
				 if(StringUtils.isNotBlank(ss)){
						if(ss.startsWith("{")){
							JSONObject json = JSONObject.parseObject(ss);
							String plaJson = json.getString("recode");
							if(!plaJson.equals("0")){
								flag=false;
								log.info("AjaxCheckCode : "+telphone+" connect is fail");
							 }else {
								String  rettype =json.getJSONObject("msg").getJSONObject("msgheader").getJSONObject("retinfo").getString("rettype").trim();
								String  retcode =json.getJSONObject("msg").getJSONObject("msgheader").getJSONObject("retinfo").getString("retcode").trim();
//								String  retmsg =json.getJSONObject("msg").getJSONObject("msgheader").getJSONObject("retinfo").getString("retmsg");
							    if(retcode.equals("0")&&rettype.equals("0")){//成功
							    	flag = true;
							    	String region = json.getJSONObject("msg").getJSONObject("msgbody").getJSONObject("userinfo").getString("region").trim();
							    	flagMap.put("region", region);
							    	log.info("AjaxCheckCode : "+telphone+" request is success");
							    }else{
							    	flag = false;
							    	log.info("AjaxCheckCode :"+telphone+" request is fail");
							    }
							}
						}
					}else{
						flag = false;
						log.info("AjaxCheckCode : "+telphone+" ecop error");
					}
			 }
		}
		flagMap.put("flag", flag);
		return flagMap ;
				
	}

}
