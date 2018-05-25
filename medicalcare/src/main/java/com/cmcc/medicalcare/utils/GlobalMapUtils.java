/**   
* @Title: GlobalUtils.java 
* @Package com.cmcc.medicalcare.utils 
* @Description: TODO
* @author adminstrator   
* @date 2017年7月31日 下午7:04:37 
* @version V1.0   
*/
package com.cmcc.medicalcare.utils;

import java.util.HashMap;
import java.util.Map;

/** 
* @ClassName: GlobalUtils 
* @Description: TODO
* @author adminstrator
* @date 2017年7月31日 下午7:04:37 
*  
*/
public class GlobalMapUtils {

	private static Map<Object, Map<Object, Object>> globalMap = new HashMap<Object, Map<Object,Object>>();

	public static void put(Object key,Map<Object, Object> value) {
		globalMap.put(key, value);
	}
	
	public static Map<Object, Object> get(Object key) {
		Map<Object, Object> map = globalMap.get(key);
		return map;
	}
	
	public static void remove(Object key) {
		globalMap.remove(key);
	}
	
	public static boolean containsKey(Object key) {
		boolean flag= globalMap.containsKey(key);
		return flag;
	}
}
