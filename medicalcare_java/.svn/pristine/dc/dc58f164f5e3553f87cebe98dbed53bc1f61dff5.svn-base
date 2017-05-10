package com.cmcc.medicalcare.controller.app.patient;

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
import com.cmcc.medicalcare.model.MessageLog;
import com.cmcc.medicalcare.service.IMessageLogService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

@Controller
@RequestMapping({ "/app/patientSendMessage" })
public class PatientSendMessageController {
	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private IMessageLogService messageLogService;

	/**
	 * parameter={"doctorsLoginId":"","fromUserName":"","toUserName":"",
	 * "patientLoginId":"","msgContentStr":"","chatroomId":""} @Title:
	 * sendTxtMessage @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者发送文本消息到聊天室")
	@RequestMapping({ "sendTxtMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String fromUserName = dataJsonObject.getString("fromUserName");
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String toPatientName = dataJsonObject.getString("toUserName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		String chatroomId = dataJsonObject.getString("chatroomId");
		
		//获取头像地址和昵称
		String nickName = dataJsonObject.getString("nickName");
		String headUrl = dataJsonObject.getString("headUrl");
		Map<String,Object> ext = new HashMap<String,Object>();
        if (nickName != null) ext.put("nickName",nickName);
        if (headUrl != null) ext.put("headUrl",headUrl);
        
		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(chatroomId);
			msg.from(patientLoginId).target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
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
				messageLog.setFromUserPhone(patientLoginId.substring(patientLoginId.indexOf("_") + 1));
				messageLog.setFromUserType(2);
				messageLog.setMessagecontent(msgContentStr);
				messageLog.setMessageType(1);
				messageLog.setSendtime(new Date());
				messageLog.setToUserName1(toPatientName);
				messageLog.setToUserName2(null);
				messageLog.setToUserPhone1(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
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


}
