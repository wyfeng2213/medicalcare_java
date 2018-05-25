package com.cmcc.medicalcare.service;

import com.cmcc.medicalcare.model.InquiryMessageLog;

/**
 * 
 * @author Administrator
 *
 */
public interface IInquiryMessageLogService extends GenericService<InquiryMessageLog, Integer> {
	
	/**
	 * 
	 * @param chatroomId
	 * @param chatroomName
	 * @param fromUserLoginId
	 * @param fromUserName
	 * @param msgContentStr
	 * @param messageType
	 * @param fromUserType
	 */
	void saveInquiryMessageLog(String chatroomId, String chatroomName,
			String fromUserLoginId, String fromUserName, String msgContentStr,int messageType,int fromUserType);
	
	/**
	 * 
	 * @param fromUserLoginId
	 * @param fromUserName
	 * @param fromUserType
	 * @param toUserLoginId
	 * @param toUserName
	 * @param toUserType
	 * @param msgContentStr
	 * @param messageType
	 * @param ordersId
	 */
	void saveInquiryMessageLog(String fromUserLoginId, String fromUserName,int fromUserType,
			String toUserLoginId, String toUserName,int toUserType, String msgContentStr,int messageType, Integer ordersId);
	
}
