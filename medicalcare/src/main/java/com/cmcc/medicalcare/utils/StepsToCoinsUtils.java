/**   
* @Title: StepsToCoinsUtils.java 
* @Package com.cmcc.medicalcare.utils 
* @Description: TODO
* @author adminstrator   
* @date 2017年9月1日 下午3:23:01 
* @version V1.0   
*/
package com.cmcc.medicalcare.utils;

/**
 * @ClassName: StepsToCoinsUtils
 * @Description: TODO
 * @author wsm
 * @date 2017年9月1日 下午3:23:01
 * 
 */
public class StepsToCoinsUtils {

	public static Integer getCoinsFromSteps(Integer steps) {
		Integer coins = 0;
		if (steps < 3000) {
			coins = 0;
		} else if ((3000 <= steps) && (steps < 5000)) {
			coins = 20;
		} else if ((5000 <= steps) && (steps < 10000)) {
			coins = 50;
		} else if(10000<=steps){
			coins = 100;
		}
		return coins;
	}
}
