package com.cmcc.medicalcare.controller.webapp.secretary;

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
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorSecretaryMessageLog;
import com.cmcc.medicalcare.model.MessageLog;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IMessageLogService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

@Controller
@RequestMapping({ "/web/secretarySendMessage" })
public class SecretarySendMessageController {
	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private IMessageLogService messageLogService;

	@Resource
	private IDoctorSecretaryMessageLogService doctorSecretaryMessageLogService;

	/**
	 * parameter={"secretaryName":"","secretaryLoginId":"","doctorsLoginId":"",
	 * "patientName":"","doctorName":"",
	 * "patientLoginId":"","msgContentStr":"","chatroomId":""} @Title:
	 * sendTxtMessage @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送文本消息到聊天室")
	@RequestMapping({ "sendTxtMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendtMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String secretaryName = dataJsonObject.getString("secretaryName");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		String doctorName = dataJsonObject.getString("doctorName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		String chatroomId = dataJsonObject.getString("chatroomId");

		// // 获取头像地址和昵称
		// String nickName = dataJsonObject.getString("nickName");
		// String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		// if (nickName != null)
		ext.put("nickName", secretaryName);
		// if (headUrl != null)
		ext.put("headUrl", null);

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(chatroomId);
			msg.from(secretaryLoginId).target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
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
				messageLog.setFromUserName(secretaryName);
				messageLog.setFromUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				messageLog.setFromUserType(1);
				messageLog.setMessagecontent(msgContentStr);
				messageLog.setMessageType(1);
				messageLog.setSendtime(new Date());
				messageLog.setToUserName1(patientName);
				messageLog.setToUserName2(doctorName);
				messageLog.setToUserPhone1(patientLoginId.substring(patientLoginId.indexOf("_") + 1));
				messageLog.setToUserPhone2(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
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
	 * parameter={"doctorsLoginId":"","doctorsName":"",
	 * "secretaryLoginId":"","secretaryName":"","msgContentStr":""} @Title:
	 * sendTxtMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送文本消息到医生")
	@RequestMapping({ "sendTxtMessageToSingle" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToSingle(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String secretaryName = dataJsonObject.getString("secretaryName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");

		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("nickName", secretaryName);
		ext.put("headUrl", null);

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();

			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(doctorsLoginId);
			msg.from(secretaryLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
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
			String isSuccess = dataJsonObject2.getString(doctorsLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {

				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				doctorSecretaryMessageLog.setCreatetime(new Date());
				doctorSecretaryMessageLog.setFromUserId(null);
				doctorSecretaryMessageLog.setFromUserName(secretaryName);
				doctorSecretaryMessageLog
						.setFromUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLog.setFromUserType(2);
				doctorSecretaryMessageLog.setMessagecontent(msgContentStr);
				doctorSecretaryMessageLog.setMessageType(1);
				doctorSecretaryMessageLog.setSendtime(new Date());
				doctorSecretaryMessageLog.setToUserId(null);
				doctorSecretaryMessageLog.setToUserName(doctorsName);
				doctorSecretaryMessageLog.setToUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
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
		String ss = "patient_15876587133";
		System.out.println(ss.substring(ss.indexOf("_") + 1));
	}
}
