package com.cmcc.medicalcare.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.StaticApplicationContext;

/**
 * 
 * @author zhouzhou
 *
 */
public class Config {

	private static final Log log = LogFactory.getLog(Config.class);// log

	private static Properties properties = new Properties();// properties

	public static String httpRequestEncoding = "UTF-8"; // HTTP请求编码方式
	public static String httpResponseEncoding = "GBK"; // HTTP响应编码方式
	public static int httpConnectTimeout = 60; // 连接超时(秒)
	public static int httpReadTimeout = 60; // 获取数据超时(秒)
	public static int httpRetryCount = 1; // 错误时重试次数
	public static int THREADPOOLNUM = 100;// THREADPOOLNUM
	public static String ecopUrl = "";
	public static String uploadUrl = "";
	public static String notCheckURL = "";
	public static String shuyuanUrl = "";
	//小秘书头像
	public static  final String secretaryIMG = "http://120.197.89.249:9001/medicalmedia/download/secretaryIMG.jpg";
	
	/**
	 * 验证码限制
	 */
	public static String sendCodelimitTimes = "";// 限制一天发送验证码的次数
	public static String sendCodeProhibitedHours = "";// 禁用验证码的时间,单位为hours
	public static String sendCodeWhiteList = "";// 不受发送验证码限制的白名单
	
	public static String jiuZhouTongTestUrl = "";//九州通平台测试地址
	public static String appPaymentUrl = "";
	
	/**
	 * 读配置文件
	 * 
	 * @param filepath
	 * @return
	 */
	public static boolean readProperties(String filepath) {
		InputStreamReader input = null;
		try {
			log.info("------------ Read Properties ------------");
			File file = new File(filepath);
			if (!file.exists()) {
				log.info("Properties Service File Not Exist.");
			}
			input = new InputStreamReader(new FileInputStream(file), "UTF-8");
			properties.load(input);
			ecopUrl = getProperty("ecopUrl", "");
			uploadUrl =  getProperty("uploadUrl", "");
			notCheckURL = getProperty("notCheckURL", "");
			shuyuanUrl = getProperty("ecopUrl", "");
			sendCodelimitTimes = getProperty("sendCodelimitTimes", ""); // 读取配置文件信息
			sendCodeProhibitedHours = getProperty("sendCodeProhibitedHours", ""); // 读取配置文件信息
			sendCodeWhiteList = getProperty("sendCodeWhiteList", ""); // 读取配置文件信息
			jiuZhouTongTestUrl = getProperty("jiuZhouTongTestUrl", ""); // 读取配置文件信息
			appPaymentUrl = getProperty("appPaymentUrl", ""); 
			return true;
		} catch (Exception e) {
			log.error("Properties not loading. " + e.getMessage());
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					log.error("Closed InputStreamReader Fail. " + e.getMessage());
				}
			}
		}
	}

	/**
	 * 获取配置信息
	 * 
	 * @param key
	 * @param default_value
	 * @return
	 */
	public static String getProperty(String key, String default_value) {
		String properties_value = null;
		try {
			if (properties.containsKey(key)) {
				properties_value = (String) properties.get(key);
				log.info(key + " : " + properties_value);
			} else {
				properties_value = default_value;
				log.warn("key (" + key + ") not found. DEFAULT (" + default_value + ")");
			}
		} catch (Exception e) {
			properties_value = default_value;
			log.error("read key (" + key + ") error. DEFAULT (" + default_value + ") ; Error : " + e.getMessage());
		}
		return properties_value;
	}

	/**
	 * 
	 * @param key
	 * @param default_value
	 * @return
	 */
	public static int getIntProperty(String key, int default_value) {
		int properties_value = 0;
		try {
			if (properties.containsKey(key)) {
				properties_value = Integer.valueOf((String) properties.get(key));
				log.info(key + " : " + properties_value);
			} else {
				properties_value = default_value;
				log.warn("key (" + key + ") not found. DEFAULT (" + default_value + ")");
			}
		} catch (Exception e) {
			properties_value = default_value;
			log.error("read key (" + key + ") error. DEFAULT (" + default_value + ") ; Error : " + e.getMessage());
		}
		return properties_value;
	}
}
