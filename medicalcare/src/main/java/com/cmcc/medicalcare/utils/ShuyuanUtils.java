/**   
* @Title: ShuyuanUtils.java 
* @Package com.cmcc.medicalcare.utils 
* @Description: TODO
* @author adminstrator   
* @date 2017年7月31日 下午4:42:27 
* @version V1.0   
*/
package com.cmcc.medicalcare.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.config.Config;

/**
 * @ClassName: ShuyuanUtils
 * @Description: TODO
 * @author adminstrator
 * @date 2017年7月31日 下午4:42:27
 * 
 */
public class ShuyuanUtils {

	private static Log log = LogFactory.getLog(EcopUtils.class);
	private static String URL = Config.getProperty("shuyuanUrl", "");
	private static String ACCOUNT = "cmkjnj@cmkjnj";
	private static String PASSWORD = "VP4pItU1";

	private static RedisUtil redisUtil = (RedisUtil) SpringConfigTool.getBean("redisUtil");
	/**
	 * 发送验证码
	 * 
	 * @param telphone
	 * @return
	 */
	public static boolean sendValcode(String telphone) {
		String randomCode = RandomUtils.generateRandom(6, 4);
		String contentStr = "您本次验证码是" + randomCode + "，将于15分钟失效，请尽快使用，验证码是您登录的凭证，请勿泄露给他人。【粤健康】";
		boolean flag = false;

		Map<String, String> params = new HashMap<String, String>();
		params.put("account", ACCOUNT);
		params.put("password", PASSWORD);
		params.put("content", contentStr);
		params.put("mobiles", telphone);
		String ss = HTTPUtils.httpDoPost(URL, params);

		System.out.println(ss);
		if (ss != null && StringUtils.isNotBlank(ss)) {
			if (ss.startsWith("{")) {
				JSONObject json = JSONObject.parseObject(ss);
				String plaJson = json.getString("code");
				if (plaJson.equals("401")) {
					log.info("AjaxGetRetCode : " + telphone + " 密码验证失败");
					flag = false;
				} else if (plaJson.equals("200")) {// 成功
					log.info("AjaxGetRetCode : " + telphone + " 接口成功接收");
					flag = true;
//					if (GlobalMapUtils.containsKey(telphone)) {
//						GlobalMapUtils.remove(telphone);
//					}
					if (redisUtil.exists(telphone)) {
						redisUtil.remove(telphone);
					}
					Map<Object, Object> map = new HashMap<Object, Object>();
					map.put("code", randomCode);
					map.put("time", System.currentTimeMillis());
					//GlobalMapUtils.put(telphone, map);
					redisUtil.set(telphone, map);
				} else {
					log.info("AjaxGetRetCode : " + telphone + " 密码验证失败");
					flag = false;
				}
			} else {
				log.info("AjaxGetRetCode : " + telphone + " 失败");
				flag = false;
			}
		} else {
			flag = false;
			log.info("AjaxGetRetCode : " + telphone + " shuyuan error");
		}
		return flag;
	}

	/**
	 * 校验验证码
	 * 
	 * @param telphone
	 * @param identifyCode
	 * @return
	 */
	public static Map<String, Object> checkValcode(String telphone, String identifyCode) {
		Map<String, Object> flagMap = new HashMap<String, Object>();
		boolean flag = false;
		if (StringUtils.isBlank(telphone) || StringUtils.isBlank(identifyCode)) {
			flag = false;
			log.info("AjaxCheckCode : param is null");
		} else {
			//Map<Object, Object> map = GlobalMapUtils.get(telphone);
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>) redisUtil.get(telphone);
			
			if (map != null && !map.isEmpty()) {

				Object codeObject = map.get("code");
				Object timeObject = map.get("time");

				if (null != codeObject && timeObject != null) { // 如果flagObject是null的话，就不转换
					String codeStr = (String) codeObject;
					long millis = System.currentTimeMillis() - (Long) timeObject;
					double min = millis / (1000 * 60);
					System.out.println("min: " + min);
					if (codeStr.equals(identifyCode) && min <= 15) {
						flag = true;
						//GlobalMapUtils.remove(telphone);
						redisUtil.remove(telphone);
					}else {
						flag = false;
						log.info("验证码有误或已过有效期！");
					}
				} else {
					flag = false;
					log.info("验证码有误或已过有效期！");
				}
			}else {
				flag = false;
				log.info("验证码有误或已过有效期！");
			}

		}
		flagMap.put("flag", flag);
		return flagMap;
	}
	
	
	/**
	 * 发送注册通知短信
	* @Title: sendRigsterMsg 
	* @Description: TODO 
	* @param @param telphone
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean sendRigsterMsg(String name,String telphone,int i) {
		String contentStr ="";
		if (i==0) {
			contentStr = name+"医生您好，您的注册申请正在审核中，审核结果将以短信通知您，感谢支持！请勿泄露给他人。【粤健康】";
		}else if(i==1){
			contentStr = name+"医生您好，您的注册申请已审核通过，欢迎使用粤健康！请勿泄露给他人。【粤健康】";
		}else if(i==2){
			contentStr = name+"医生您好，您的注册申请不通过，请修改或补充相关资料，感谢支持！请勿泄露给他人。【粤健康】";
		}
		
		boolean flag = false;

		Map<String, String> params = new HashMap<String, String>();
		params.put("account", ACCOUNT);
		params.put("password", PASSWORD);
		params.put("content", contentStr);
		params.put("mobiles", telphone);
		String ss = HTTPUtils.httpDoPost(URL, params);
		System.out.println("电话： "+telphone);
		System.out.println(contentStr);
		System.out.println(ss);
		if (ss != null && StringUtils.isNotBlank(ss)) {
			if (ss.startsWith("{")) {
				JSONObject json = JSONObject.parseObject(ss);
				String plaJson = json.getString("code");
				if (plaJson.equals("401")) {
					log.info("AjaxGetRetCode : " + telphone + " 密码验证失败");
					flag = false;
				} else if (plaJson.equals("200")) {// 成功
					log.info("AjaxGetRetCode : " + telphone + " 接口成功接收");
					flag = true;
				} else {
					log.info("AjaxGetRetCode : " + telphone + " 密码验证失败");
					flag = false;
				}
			} else {
				log.info("AjaxGetRetCode : " + telphone + " 失败");
				flag = false;
			}
		} else {
			flag = false;
			log.info("AjaxGetRetCode : " + telphone + " shuyuan error");
		}
		return flag;
	}
	
	
	public static boolean sendSMS(String messageStr,String telphone) {
		String contentStr ="";
		contentStr = messageStr;

		boolean flag = false;

		Map<String, String> params = new HashMap<String, String>();
		params.put("account", ACCOUNT);
		params.put("password", PASSWORD);
		params.put("content", contentStr);
		params.put("mobiles", telphone);
		String ss = HTTPUtils.httpDoPost(URL, params);
		System.out.println("电话： "+telphone);
		System.out.println(contentStr);
		System.out.println(ss);
		if (ss != null && StringUtils.isNotBlank(ss)) {
			if (ss.startsWith("{")) {
				JSONObject json = JSONObject.parseObject(ss);
				String plaJson = json.getString("code");
				if (plaJson.equals("401")) {
					log.info("AjaxGetRetCode : " + telphone + " 密码验证失败");
					flag = false;
				} else if (plaJson.equals("200")) {// 成功
					log.info("AjaxGetRetCode : " + telphone + " 接口成功接收");
					flag = true;
				} else {
					log.info("AjaxGetRetCode : " + telphone + " 密码验证失败");
					flag = false;
				}
			} else {
				log.info("AjaxGetRetCode : " + telphone + " 失败");
				flag = false;
			}
		} else {
			flag = false;
			log.info("AjaxGetRetCode : " + telphone + " shuyuan error");
		}
		return flag;
	}
	
}
