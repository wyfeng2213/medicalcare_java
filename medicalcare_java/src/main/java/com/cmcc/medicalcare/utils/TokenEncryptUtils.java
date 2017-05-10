package com.cmcc.medicalcare.utils;

import java.util.Random;

/**
 * <p>Title: iSoftStone</p>
 *
 * <p>Description: </p>
 * DES加密TOKEN
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: iSoftStone</p>
 *
 * @author lm
 * @version 1.0
 */
public class TokenEncryptUtils {

	public static final String numberChar = "0123456789";
	public static final String STATIC_DATA = "RINGTONE_SETTING";
	
	/**
	 * MD5加密手机号码, 加密3-5次
	 * 
	 * @param data 需要加密的字符串
	 * @return
	 * @throws Exception
	 */
	public static String encryptData2MD5(String data) throws Exception {
		String encrypt_result = null;
		try {
			char[] pchars = data.toCharArray();
			int phonel = pchars.length;
			int phonesum = 0;
			for (int i = 0; i < phonel; i++) {
				phonesum += Character.getNumericValue(pchars[i]);
			}
			int n = (phonesum % 3) + 3;
			encrypt_result = data;
			for (int i = 0; i < n; i++) {
				encrypt_result = SecurityUtils.encryptMD5(encrypt_result);
			}
		} catch (Exception e) {
			encrypt_result = null;
			e.printStackTrace();
		}
		return encrypt_result;
	}

	public static String sxyEncryMD5(String data) {
		int times = data.length() % 3 + 3;
		String res_data = SecurityUtils.encryptMD5(data, times);
		return res_data.toUpperCase();
	}
	/**
	 * Token加密
	 * 
	 * @param phone_data 手机号码 + 4位随机数，获得pipelinekey加密次数。
	 * @param pipelinekey 由3部分组成：公钥字串6位 手机号码后5位 4位0-9的随机数，加密后为32位字符串，即公钥。
	 * @return
	 * @throws Exception
	 */
	public static String encryptToken(String phone_data, String pipelinekey) throws Exception {
		char[] pchars = phone_data.toCharArray();
		int phonel = pchars.length;
		int phonesum = 0;
		for (int i = 0; i < phonel; i++) {
			phonesum += (int) Character.getNumericValue(pchars[i]);
		}
		int n = (phonesum % 5) + 1;
		String enpipelinekey = pipelinekey;
		for (int i = 0; i < n; i++) {
			enpipelinekey = SecurityUtils.encryptMD5(enpipelinekey);
		}
		return SecurityUtils.encryptDES(phone_data, enpipelinekey);
	}
	
	/**
	 * 将随机生成的4位数插入到MD5加密后的32位串的4个不同位置
	 * 
	 * @param encrypt_result MD5加密后的32位串
	 * @param random_number 随机生成的4位数
	 * @param params 4个不同位置
	 * @return 返回36位字符串
	 * @throws Exception
	 */
	public static String insertRandomNumber(String encrypt_result, String random_number, int[] params) throws Exception {
		// long start_time = System.currentTimeMillis();
		String result = null;
		try {
			char[] char_encrypt_result = encrypt_result.toCharArray();
			char[] char_random_number = random_number.toCharArray();
			char[] pchars = new char[char_encrypt_result.length + char_random_number.length];
			// System.out.println(params.length);
			for (int i = 0; i < pchars.length; i++) {
				
				for (int j = 0; j < params.length; j++) {
					if (j == 0) {
						if (i < params[0]) {
							pchars[i] = char_encrypt_result[i];
							//String res = String.valueOf(pchars);
							//System.out.println(res.length() + " : " + res);
						}
						if (i == params[0]) {
							pchars[i] = char_random_number[0];
							//String res = String.valueOf(pchars);
							//System.out.println(res.length() + " " + res);
							continue;
						}
					}
					
					if (j > 0 && j < params.length) {
						if (i > params[j - 1] && i < params[j]) {
							pchars[i] = char_encrypt_result[i - j];
							//String res = String.valueOf(pchars);
							//System.out.println(res.length() + " " + res);
						}
						if (i == params[j]) {
							pchars[i] = char_random_number[j];
							//String res = String.valueOf(pchars);
							//System.out.println(res.length() + " : " + res);
						}
					}
					
					if (j == params.length - 1) {
						if (i > params[j]) {
							pchars[i] = char_encrypt_result[i - params.length];
						}
					}
				}
			}
			result = String.valueOf(pchars);
			// System.out.println(result.length() + " : " + result);
		} catch (Exception e) {
			throw e;
		}
		// long end_time = System.currentTimeMillis();
		// System.out.println("run_time : " + (end_time - start_time) + " ms.");
		return result;
	}
	
	/**
	 * 随机生成4位数字
	 * 
	 * @param length
	 * @return
	 */
	public static String generateNumberString(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 获取header中token
	 * 
	 * @param phone
	 * @param random_number
	 * @param header_param
	 * @return
	 * @throws Exception
	 */
	public static String getHeaderToken(String phone, String random_number, int[] header_param) throws Exception {
		String result = null;
		try {
			// System.out.println("getHeaderToken() : " + phone.length() + " : phone : " +  phone);
			// System.out.println("getHeaderToken() : " + random_number.length() + " : random_number : " +  random_number);
//			result = RandomNumberOperation.insertRandomNumber(encryptData2MD5(phone), random_number, header_param);
			// System.out.println("getHeaderToken() : " + result.length() + " : result : " +  result);
		} catch (Exception e) {
			result = null;
			throw e;
		}
		return result;
	}
	
	/**
	 * DES加密token
	 * 
	 * @param phone
	 * @param random_number
	 * @param phone_param
	 * @return
	 * @throws Exception
	 */
	public static String getDESToken(String phone, String random_number, int[] phone_param) throws Exception {
		String result = null;
		try {
			// System.out.println(random_number.length() + " : random_number : " +  random_number);		
			String phone_data = insertRandomNumber(phone, random_number, phone_param);
			// System.out.println(phone_data.length() + " : phone_data : " +  phone_data);
			String pipelinekey = STATIC_DATA.substring(2, 8) + phone.substring(6, 11) + random_number;
			// System.out.println(pipelinekey.length() + " : pipelinekey : " +  pipelinekey);
			result = encryptToken(phone_data, pipelinekey);
			// System.out.println(result.length() + " : result : " +  result);
		} catch (Exception e) {
			result = null;
			throw e;
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		
		//String en_crypt_token = TokenEncryptUtils.encryptToken("184820507040273", "AAAAAA700270273");
		//System.out.println(en_crypt_token.length() + " : en_crypt_token : " + en_crypt_token);

//		String phone = "18820070027";
//		int[] header_param = { 4, 13, 22, 31 };
//		String random_number = generateNumberString(header_param.length);
//		int[] phone_param = { 2, 6, 10, 14 };
//		
//		String header_str = getHeaderToken(phone, random_number, header_param);
//		System.out.println(header_str);
//		
//		String token = getDESToken(phone, random_number, phone_param);
//		System.out.println(token);
//		String  data="{\"process_code\":\"productorder\",\"channelid\":\"sxy\",\"unitid\":\"0\",\"servernum\":\"13729888963\",\"area\":\"200\",\"brand\":\"0\",\"productid\":\"M-meeting\",\"productgroup\":\"0\",\"producttype\":\"0\",\"ordertype\":\"1\",\"productname\":\"M-meeting\"}";
//		System.out.println("----------------------------------");
//		System.out.println(SecurityUtils.encryptMD5(data, 3));
		String  content= "{\"process_code\":\"productorder\",\"menuid\":\"1\",\"operatorid\":\"0\",\"channelid\":\"LLHDPT\",\"unitid\":\"0\",\"userinfo.servernum\":\"18802090217\",\"userinfo.area\":\"200\",\"userinfo.brand\":\"0\",\"productinfo.productid\":\"50LLHB0\",\"productinfo.productgroup\":\"0\",\"productinfo.producttype\":\"0\",\"productinfo.ordertype\":\"1\",\"productinfo.productname\":\"50M流量红包\"}";
		//System.out.println(TokenEncryptUtils.sxyEncryMD5(content));
//		
	}
	
}
