package com.cmcc.medicalcare.service.Impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.model.InquiryMessageLog;
import com.cmcc.medicalcare.service.IInquiryMessageLogService;
import com.cmcc.medicalcare.utils.EmojiFilter;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="inquiryMessageLogService")
@Transactional
public class InquiryMessageLogServiceImpl extends GenericServiceImpl<InquiryMessageLog, Integer> implements IInquiryMessageLogService{

	@Override
	public void saveInquiryMessageLog(String chatroomId, String chatroomName,
			String fromUserLoginId, String fromUserName, String msgContentStr, int messageType,
			int fromUserType) {
		InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
		try {
			inquiryMessageLog.setChatroomId(chatroomId);
			inquiryMessageLog.setChatroomName(chatroomName);
			inquiryMessageLog.setFromUserLoginId(fromUserLoginId);
			inquiryMessageLog.setFromUserName(fromUserName);
			inquiryMessageLog.setFromUserPhone(fromUserLoginId.substring(fromUserLoginId.indexOf("_") + 1));
			inquiryMessageLog.setFromUserType(fromUserType);
			inquiryMessageLog.setMessagecontent(msgContentStr);
			inquiryMessageLog.setMessageType(messageType);
			inquiryMessageLog.setSendtime(new Date());
			inquiryMessageLog.setCreatetime(new Date());
			insert(inquiryMessageLog);
		} catch (Exception e) {
			try {
				String msgContentStr_ = EmojiFilter.filterEmoji(msgContentStr);
				inquiryMessageLog.setMessagecontent(msgContentStr_);
				insert(inquiryMessageLog);
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void saveInquiryMessageLog(String fromUserLoginId, String fromUserName, int fromUserType,
			String toUserLoginId, String toUserName, int toUserType, String msgContentStr, int messageType,
			Integer ordersId) {
		InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
		try {
			inquiryMessageLog.setChatroomId(Constant.single_chat_chatroom_id);
			inquiryMessageLog.setFromUserLoginId(fromUserLoginId);
			inquiryMessageLog.setFromUserName(fromUserName);
			inquiryMessageLog.setFromUserPhone(fromUserLoginId.substring(fromUserLoginId.indexOf("_") + 1));
			inquiryMessageLog.setFromUserType(fromUserType);
			inquiryMessageLog.setToUserLoginId(toUserLoginId);
			inquiryMessageLog.setToUserName(toUserName);
			inquiryMessageLog.setToUserPhone(toUserLoginId.substring(toUserLoginId.indexOf("_") + 1));
			inquiryMessageLog.setToUserType(toUserType);
			inquiryMessageLog.setMessagecontent(msgContentStr);
			inquiryMessageLog.setMessageType(messageType);
			if (ordersId != null)inquiryMessageLog.setOrdersId(ordersId);
			inquiryMessageLog.setSendtime(new Date());
			inquiryMessageLog.setCreatetime(new Date());
			insert(inquiryMessageLog);
		} catch (Exception e) {
			try {
				String msgContentStr_ = EmojiFilter.filterEmoji(msgContentStr);
				inquiryMessageLog.setMessagecontent(msgContentStr_);
				insert(inquiryMessageLog);
			} catch (Exception e2) {
				e.printStackTrace();
			}
		}
		
	}

}
