/**   
* @Title: DoctorsTeamUserLinkController.java 
* @Package com.cmcc.medicalcare.controller.app.doctor 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月14日 下午4:45:10 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.Date;
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
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 医生团队与医生关联
 * @Description: TODO
 * @author siming.wu
 * @date 2017年4月14日 下午4:45:10
 * 
 */
@Controller
@RequestMapping("/app/doctorsTeamUserLink")
public class DoctorsTeamUserLinkController {

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	/**
	 * 
	 * @Title: selectAllMembersFromTeam @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<List
	 *         <DoctorsTeamUserLink>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "查询团队所属医生成员")
	@RequestMapping(value = "selectAllMembersFromTeam")
	@ResponseBody
	public Results<Map<String, List<DoctorsTeamUserLink>>> selectAllMembersFromTeam(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 对应doctors_team_user_link表,根据团队id查找所有医生成员
		Results<Map<String, List<DoctorsTeamUserLink>>> results = new Results<Map<String, List<DoctorsTeamUserLink>>>();
		List<DoctorsTeamUserLink> listDoctorsTeamUser = null;
		Map<String, List<DoctorsTeamUserLink>> map = new HashMap<String, List<DoctorsTeamUserLink>>();

		String team_id = request.getParameter("team_id");
		if (StringUtils.isNotBlank(team_id)) {
			listDoctorsTeamUser = doctorsTeamUserLinkService.getList("selectByTeamid", team_id);
			if (listDoctorsTeamUser != null) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("listDoctorsTeamUser", listDoctorsTeamUser);
				results.setData(map);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				results.setData(null);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: addNewMemberForTeam @Description: TODO @param @param
	 *         doctorsTeamUserLink @param @param request @param @return
	 *         设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "为团队添加成员")
	@RequestMapping(value = "addNewMemberForTeam")
	@ResponseBody
	public Results<String> addNewMemberForTeam(@RequestParam String doctorsTeamUserLink, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 对应doctors_team_user_link表,添加记录 ，判断是否存在的条件为团队id和医生电话,并调用环信接口通知医生
		Results<String> results = new Results<String>();

		if (doctorsTeamUserLink != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			JSONObject doctorsTeamUserLinkObject = JSONObject.parseObject(doctorsTeamUserLink);
			paramMap.put("team_id", doctorsTeamUserLinkObject.getString("teamId"));
			paramMap.put("doctors_phone", doctorsTeamUserLinkObject.getString("doctorsPhone"));
			DoctorsTeamUserLink doctorsTeamUserLink_ = doctorsTeamUserLinkService.findByParam("selectByTeamidAndPhone",
					paramMap);
			if (doctorsTeamUserLink_ != null) {
				results.setCode(MessageCode.CODE_405);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_405);
				return results;
			}
			/**
			 * 调用环信接口通知医生
			 */
			String teamName = doctorsTeamUserLink_.getTeamName();
			String tipsJson = "{\"type\":\"tips\",\"msgContent\":\"您已加入" + teamName
					+ "\",\"chatRoomId\":\"\",\"phone\":\"\"}";
			String doctorPhone = doctorsTeamUserLink_.getDoctorsPhone();
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();

			MsgContent msgContent = new MsgContent();
			String msgContentJson = tipsJson; // type:tips/chatRoom/single
			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentJson);
			UserName userName = new UserName();
			userName.add(Constant.doctor + doctorPhone);// 秘书端loginId
			msg.from("stringa").target(userName).targetType("users").msg(msgContent);

			Object result = easemobSendMessage.sendMessage(msg);

			// 判断医生端是否发送成功
			JSONObject dataJsonObject = JSONObject.parseObject(result.toString());
			String str = dataJsonObject.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(str);
			String isSuccess = dataJsonObject2.getString(Constant.doctor + doctorPhone);

			if (true) {
				doctorsTeamUserLink_ = (DoctorsTeamUserLink) JSONObject.toJavaObject(doctorsTeamUserLinkObject,
						DoctorsTeamUserLink.class);
				doctorsTeamUserLink_.setCreatetime(new Date());
				doctorsTeamUserLinkService.insert(doctorsTeamUserLink_);

				results.setCode(200);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("环信发送信息失败");
				return results;
			}

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: deleteMemberFromTeam @Description: TODO @param @param
	 *         doctorsTeamUserLink @param @param request @param @return
	 *         设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "从团队中删除成员")
	@RequestMapping(value = "deleteMemberFromTeam")
	@ResponseBody
	public Results<String> deleteMemberFromTeam(@RequestParam String doctorsTeamUserLink, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 对应doctors_team_user_link表,根据团队id和医生电话号码删除记录，并且通过环信接口发信息通知被踢出者和小秘书
		Results<String> results = new Results<String>();

		if (doctorsTeamUserLink != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			JSONObject doctorsTeamUserLinkObject = JSONObject.parseObject(doctorsTeamUserLink);
			paramMap.put("team_id", doctorsTeamUserLinkObject.getString("teamId"));
			paramMap.put("doctors_phone", doctorsTeamUserLinkObject.getString("doctorsPhone"));
			DoctorsTeamUserLink doctorsTeamUserLink_ = doctorsTeamUserLinkService.findByParam("selectByTeamidAndPhone",
					paramMap);
			if (doctorsTeamUserLink_ == null) {
				results.setCode(MessageCode.CODE_501);
				results.setData("没有该成员记录");
				results.setMessage(MessageCode.MESSAGE_501);
				return results;
			}
			/**
			 * 调用环信接口发信息通知被踢出者和小秘书
			 */
			String teamName = doctorsTeamUserLink_.getTeamName();
			String doctorPhone = doctorsTeamUserLink_.getDoctorsPhone();
			String doctorName = doctorsTeamUserLink_.getDoctorsName();
			String tipsJson1 = "{\"type\":\"tips\",\"msgContent\":\"您已被移出" + teamName
					+ "\",\"chatRoomId\":\"\",\"phone\":\"\"}";// 通知被踢出者
			String tipsJson2 = "{\"type\":\"tips\",\"msgContent\":\"" + doctorName + "已被踢出" + teamName
					+ "\",\"chatRoomId\":\"\",\"phone\":\"\"}";// 通知秘书端

			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg1 = new Msg();
			Msg msg2 = new Msg();
			MsgContent msgContent1 = new MsgContent();
			MsgContent msgContent2 = new MsgContent();

			String msgContentJson1 = tipsJson1; // type:tips/chatRoom/single
			String msgContentJson2 = tipsJson2; // type:tips/chatRoom/single
			msgContent1.type(MsgContent.TypeEnum.TXT).msg(msgContentJson1);
			msgContent2.type(MsgContent.TypeEnum.TXT).msg(msgContentJson2);
			UserName userName1 = new UserName();
			UserName userName2 = new UserName();
			userName1.add(Constant.doctor + doctorPhone);
			userName2.add("");// 秘书端loginId
			msg1.from("stringa").target(userName1).targetType("users").msg(msgContent1);
			msg2.from("stringa").target(userName2).targetType("users").msg(msgContent2);
			Object result1 = easemobSendMessage.sendMessage(msg1);
			Object result2 = easemobSendMessage.sendMessage(msg2);

			// 判断医生端是否发送成功
			JSONObject dataJsonObject = JSONObject.parseObject(result1.toString());
			String str = dataJsonObject.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(str);
			String isSuccess1 = dataJsonObject2.getString(Constant.doctor + doctorPhone);

			// 判断秘书端是否发送成功
			// JSONObject dataJsonObject3 =
			// JSONObject.parseObject(result2.toString());
			// String str2 = dataJsonObject3.getString("data");
			// JSONObject dataJsonObject4 = JSONObject.parseObject(str2);
			// String isSuccess2 =
			// dataJsonObject4.getString("scretary_"+doctorPhone);

			if (true) {
				doctorsTeamUserLinkService.delete("deleteByPrimaryKey", doctorsTeamUserLink_);
				results.setCode(200);
				results.setData("删除团队成员成功");
				results.setMessage(MessageCode.MESSAGE_200);
				return results;

			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("环信发送信息失败");
				return results;
			}

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	public static void main(String[] args) {
		String ss = " {\"action\" : \"post\"," + "\"application\" : \"33313130-263a-11e7-b145-5962e0bcac56\","
				+ "\"path\" : \"messages\","
				+ "\"uri\" : \"https://a1.easemob.com/1140170421178546/mingyizaixian/messages\"," + "\"data\" : {"
				+ "\"qwqwqww\" : \"success\"" + "}," + "\"timestamp\" : 1493002377124," + "\"duration\" : 0,"
				+ "\"organization\" : \"1140170421178546\"," + "\"applicationName\" : \"mingyizaixian\"" + "}";
		System.out.println(ss);
		JSONObject dataJsonObject = JSONObject.parseObject(ss);
		String str = dataJsonObject.getString("data");
		JSONObject dataJsonObject2 = JSONObject.parseObject(str);
		System.out.println(dataJsonObject2.getString("qwqwqww"));
	}
}
