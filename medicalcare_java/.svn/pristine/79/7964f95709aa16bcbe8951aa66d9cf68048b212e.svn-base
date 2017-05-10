/**   
* @Title: DoctorsTeamInviteController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:44:51 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsTeamInvite;
import com.cmcc.medicalcare.service.IDoctorsTeamInviteService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 医生团队邀请信息
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:44:51
 * 
 */
@Controller
@RequestMapping("/app/doctorsTeamInvite")
public class DoctorsTeamInviteController{
	
	@Resource
	private IDoctorsTeamInviteService doctorsTeamInviteService;

	/**
	 * 
	* @Title: joinTeamInvite 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<String>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "加入团队邀请信息")
	@RequestMapping(value = "joinTeamInvite") 
	@ResponseBody
	public Results<String> joinTeamInvite(@RequestParam String doctorsTeamInvite,HttpServletRequest request) {
		// TODO Auto-generated method stub
		//doctors_team_invite表，添加记录，判断是否存在的条件为团队id和被邀请者电话。同时调用环信接口给被邀请者发信息提醒
		Results<String> results = new Results<String>();
		
//		if (doctorsTeamInvite != null) {
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			JSONObject doctorsTeamUserLinkObject = JSONObject.parseObject(doctorsTeamInvite);
//			paramMap.put("team_id", doctorsTeamUserLinkObject.getString("teamId"));
//			paramMap.put("invited_phone",doctorsTeamUserLinkObject.getString("invitedPhone"));
//			DoctorsTeamInvite doctorsTeamInvite_ = doctorsTeamInviteService.findByParam("selectByTeamidAndPhone", paramMap);
//			if (doctorsTeamInvite_ != null) {
//				results.setCode(MessageCode.CODE_405);
//				results.setData(null);
//				results.setMessage(MessageCode.MESSAGE_405);
//				return results;
//			}
//			
//			/**
//			 * 调用环信接口给被邀请者发信息提醒
//			 */
//			String tipsJson = "{\"type\":\"tips\",\"msgContent\":\"您已被添加到" + teamName
//					+ "\",\"chatRoomId\":\"\",\"userId\":\"\"}";
//			String doctorPhone = doctorsTeamUserLink_.getDoctorsPhone();
//			DoctorsSendMessageController easemobSendMessage = new DoctorsSendMessageController();
//			Msg msg = new Msg();
//
//			MsgContent msgContent = new MsgContent();
//			String msgContentJson = tipsJson; // type:tips/chatRoom/single
//			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentJson);
//			UserName userName = new UserName();
//			userName.add("doctor_" + doctorPhone);// 秘书端loginId
//			msg.from("stringa").target(userName).targetType("users").msg(msgContent);
//
//			Object result = easemobSendMessage.sendMessage(msg);
//
//			// 判断医生端是否发送成功
//			JSONObject dataJsonObject = JSONObject.parseObject(result.toString());
//			String str = dataJsonObject.getString("data");
//			JSONObject dataJsonObject2 = JSONObject.parseObject(str);
//			String isSuccess = dataJsonObject2.getString("doctor_" + doctorPhone);
//			
//			if (true) {
//				doctorsTeamInvite.setCreatetime(new Date());
//				doctorsTeamInviteService.insert(doctorsTeamInvite);
//				int id = doctorsTeamInvite.getId();
//				if (id > 0) {
//					results.setCode(200);
//					results.setData(null);
//					results.setMessage(MessageCode.MESSAGE_200);
//					return results;
//				} else {
//					results.setCode(MessageCode.CODE_501);
//					results.setData("无法添加团队邀请信息");
//					results.setMessage(MessageCode.MESSAGE_501);
//					return results;
//				}
//			} else {
//				results.setCode(MessageCode.CODE_501);
//				results.setData(null);
//				results.setMessage("环信发送信息失败");
//				return results;
//			}
//			
//		} else {
//			results.setCode(MessageCode.CODE_201);
//			results.setData(null);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//		
		return results;
	}
}
