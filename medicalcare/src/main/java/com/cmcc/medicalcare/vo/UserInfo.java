/**   
* @Title: UserInfo.java 
* @Package com.cmcc.medicalcare.controller.vo 
* @Description: TODO
* @author adminstrator   
* @date 2017年9月27日 上午11:12:02 
* @version V1.0   
*/
package com.cmcc.medicalcare.vo;

/**
 * @ClassName: UserInfo
 * @Description: TODO
 * @author adminstrator
 * @date 2017年9月27日 上午11:12:02
 * 
 */
public class UserInfo {

	private String userPhone;

	private String userName;

	private String userLoginId;

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}

	/**
	 * @param userPhone
	 *            the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userLoginId
	 */
	public String getUserLoginId() {
		return userLoginId;
	}

	/**
	 * @param userLoginId
	 *            the userLoginId to set
	 */
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

}
