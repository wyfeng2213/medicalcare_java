package com.cmcc.medicalcare.controller.app.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorSecretaryMessageLog;
import com.cmcc.medicalcare.model.MessageLog;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IMessageLogService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

@Controller
@RequestMapping({ "/app/doctorsSendMessage" })
public class DoctorsSendMessageController {
	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private IMessageLogService messageLogService;

	@Resource
	private IDoctorSecretaryMessageLogService doctorSecretaryMessageLogService;

	/**
	 * parameter={"doctorsLoginId":"","fromUserName":"","toUserName":"",
	 * "patientLoginId":"","msgContentStr":"","chatroomId":"","nickName":"",
	 * "headUrl":""} @Title: sendTxtMessage @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送文本消息到聊天室")
	@RequestMapping({ "sendTxtMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String fromUserName = dataJsonObject.getString("fromUserName");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String toPatientName = dataJsonObject.getString("toUserName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		String chatroomId = dataJsonObject.getString("chatroomId");

		// 获取头像地址和昵称
		String nickName = dataJsonObject.getString("nickName");
		String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		if (nickName != null)
			ext.put("nickName", nickName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(chatroomId);
			msg.from(doctorsLoginId).target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
			result = easemobSendMessage.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(chatroomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				MessageLog messageLog = new MessageLog();
				messageLog.setCreatetime(new Date());
				messageLog.setFromUserId(null);
				messageLog.setFromUserName(fromUserName);
				messageLog.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				messageLog.setFromUserType(1);
				messageLog.setMessagecontent(msgContentStr);
				messageLog.setMessageType(1);
				messageLog.setSendtime(new Date());
				messageLog.setToUserName1(toPatientName);
				messageLog.setToUserName2(null);
				messageLog.setToUserPhone1(patientLoginId.substring(patientLoginId.indexOf("_") + 1));
				messageLog.setToUserPhone2(null);
				messageLogService.insert(messageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", msgContentStr);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"","secretaryName":"",
	 * "headUrl":"", "secretaryLoginId":"","msgContentStr":""} @Title:
	 * sendTxtMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送文本消息到医生")
	@RequestMapping({ "sendTxtMessageToSingle" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToSingle(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryName = dataJsonObject.getString("secretaryName");
		String headUrl = dataJsonObject.getString("headUrl");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		
		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();

			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(secretaryLoginId);
			msg.from(doctorsLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
			result = easemobSendMessage.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(secretaryLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				doctorSecretaryMessageLog.setCreatetime(new Date());
				doctorSecretaryMessageLog.setFromUserId(null);
				doctorSecretaryMessageLog.setFromUserName(doctorsName);
				doctorSecretaryMessageLog.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLog.setFromUserType(1);
				doctorSecretaryMessageLog.setMessagecontent(msgContentStr);
				doctorSecretaryMessageLog.setMessageType(1);
				doctorSecretaryMessageLog.setSendtime(new Date());
				doctorSecretaryMessageLog.setToUserId(null);
				doctorSecretaryMessageLog.setToUserName(secretaryName);
				doctorSecretaryMessageLog.setToUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", msgContentStr);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	public static void main(String[] args) {
		String msgContentStr = "hello world";
		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
		UserName userName = new UserName();
		userName.add("15116163678210");
		msg.from(Constant.scretary + Constant.scretaryPhone).target(userName).targetType("chatrooms").msg(msgContent);
		Object result = easemobSendMessage.sendMessage(msg);
		System.out.println(result.toString());
	}
}
