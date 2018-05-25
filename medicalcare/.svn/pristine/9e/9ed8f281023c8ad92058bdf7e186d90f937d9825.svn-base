/**   
* @Title: MessageRemindingController.java 
* @Package com.cmcc.medicalcare.controller.app.patient 
* @Description: TODO
* @author adminstrator   
* @date 2017年11月8日 下午3:37:50 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.util.HashMap;
import java.util.List;
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
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.MessageReminding;
import com.cmcc.medicalcare.service.IMessageRemindingService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/** 
* @ClassName: MessageRemindingController 
* @Description: TODO
* @author adminstrator
* @date 2017年11月8日 下午3:37:50 
*  
*/
@Controller
@RequestMapping("/app/messageReminding")
public class MessageRemindingController {

	@Resource
	private IMessageRemindingService messageRemindingService;
	
	/**
	 * 
	* @Title: getAllMessageReminding 
	* @Description: TODO 
	* @param @param request
	* @param @param parameter
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "getAllMessageReminding")
	@ResponseBody
	@SystemControllerLog(description = "患者获取个人消息通知")
	public Results<Map<String, Object>> getAllMessageReminding(HttpServletRequest request,
			@RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String pageIndex = dataJsonObject.getString("pageIndex");
		String pageSize = dataJsonObject.getString("pageSize");
		Map<String, Object> param = new HashMap<String, Object>();

		if (StringUtils.isBlank(pageSize) && StringUtils.isBlank(pageIndex)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		if (StringUtils.isNotBlank(patientPhone)) {
		
			param.put("pageIndex", Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize));
			param.put("pageSize", Integer.parseInt(pageSize));
			param.put("patientPhone", patientPhone);
			List<MessageReminding> list = messageRemindingService.getList("selectByPhone", param);
			
			resultsMap.put("messageRemindingList", list);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(resultsMap);
			return results;

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
	
	
	@RequestMapping(value = "updateMessageReminding")
	@ResponseBody
	@SystemControllerLog(description = "更新患者个人消息通知状态")
	public Results<Map<String, Object>> updateMessageReminding(HttpServletRequest request,
			@RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String id = dataJsonObject.getString("id");
		
		if (StringUtils.isNotBlank(id)) {
		
			MessageReminding messageReminding = messageRemindingService.findById("selectByPrimaryKey", Integer.valueOf(id));
			messageReminding.setMessageStatus(1);
			messageRemindingService.update("updateByPrimaryKeySelective", messageReminding);
			
			//更新消息通知状态，透传剩余未读消息条数
			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
			String patientPhone = dataJsonObject2.getString("phone");
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("messageType", "messageReminding");
			// 获取未读消息数
			List<MessageReminding> messageRemindingList = messageRemindingService.getList("getUnreadMessage", patientPhone);
			ext.put("UnreadMessageSize", messageRemindingList.size());
			msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
			UserName userName = new UserName();
			userName.add(Constant.patient + patientPhone);
			msg.from(Constant.SYSTEM).target(userName).targetType("users").msg(msgContent).ext(ext);
			easemobSendMessage.sendMessage(msg);
			
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(resultsMap);
			return results;

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
}
