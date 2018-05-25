/**   
* @Title: DoctorsTeamUserLinkController.java 
* @Package com.cmcc.medicalcare.controller.app.doctor 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月14日 下午4:45:10 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsConsultation;
import com.cmcc.medicalcare.model.DoctorsConsultationInvite;
import com.cmcc.medicalcare.model.DoctorsConsultationUserLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.service.IDoctorsConsultationInviteService;
import com.cmcc.medicalcare.service.IDoctorsConsultationService;
import com.cmcc.medicalcare.service.IDoctorsConsultationUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;
import io.swagger.client.model.UserNames;

/**
 * @ClassName: 医生会诊团队与医生关联
 * @Description: TODO
 * @author siming.wu
 * @date 2017年4月14日 下午4:45:10
 * 
 */
@Controller
@RequestMapping("/app/doctorsConsultationUserLink")
public class DoctorsConsultationUserLinkController {

	@Resource
	private IDoctorsConsultationUserLinkService doctorsConsultationUserLinkService;
	
	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;
	
	@Resource
	private IDoctorsTeamService doctorsTeamService;
	
	@Resource
	private IDoctorsConsultationService doctorsConsultationService;
	
	@Resource
	private IDoctorsConsultationInviteService doctorsConsultationInviteService;
	

	/**
	 * parameter={"keyword":"","teamId":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "检索会诊医生")
	@RequestMapping({ "searchDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> searchDoctor(@RequestParam String parameter, HttpServletRequest request) {
		// 根据医生的名字或者电话精确查询医生，返回一个list,已经在这个团队的就不能再出现在list里面
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> listDoctorsUser = new ArrayList<DoctorsUser>();
		List<DoctorsConsultationUserLink> listDoctorsConsultationUserLink = null;

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String teamId = dataJsonObject.getString("teamId");
		
		if (StringUtils.isNotBlank(teamId)) { //团队id不为空，默认只查非团队成员
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("team_id", teamId);
			listDoctorsConsultationUserLink = doctorsConsultationUserLinkService.getList("selectByTeamId", paraMap);
			if (listDoctorsConsultationUserLink != null && listDoctorsConsultationUserLink.size() > 0) {// 有成员
				for (int i = 0; i < listDoctorsConsultationUserLink.size(); i++) {
					String phone = listDoctorsConsultationUserLink.get(i).getDoctorsPhone();
					String doctorName = listDoctorsConsultationUserLink.get(i).getDoctorsName();
					if (phone.equals(keyword) || doctorName.equals(keyword)) {//如果团队已经存在该成员，检索结果为空
						results.setCode(MessageCode.CODE_405);
						results.setMessage(MessageCode.MESSAGE_405);
						return results;
					}
				}
			}
		}
		
		if (!StringUtils.isNotBlank(keyword)) { //关键字为空，返回全部医生数据
			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject_ = JSONObject.parseObject(equipmentData);
			String phone = dataJsonObject_.getString("phone");
			listDoctorsUser = doctorsUserService.getList("selectOthers", phone);
			map.put("listDoctorsUser", listDoctorsUser);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功");
			results.setData(map);
			return results;
		} else if (Toolkit.isMobileNO(keyword)) {//如果关键字为电话号码
			String phone = keyword;
			// 根据电话去查询doctors_user
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);
			if (doctorsUser!=null) {
				listDoctorsUser.add(doctorsUser);
				map.put("listDoctorsUser", listDoctorsUser);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				results.setData(map);
				return results;
			} else {//未注册
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该用户未注册");
				results.setData(map);
				return results;
			}
			
		} else {//如果关键字是医生名字
			String name = keyword;
			// 根据名字去查询doctors_user，
			listDoctorsUser = doctorsUserService.getList("selectByName", name);
			if (listDoctorsUser!=null&&listDoctorsUser.size()>0) {
				map.put("listDoctorsUser", listDoctorsUser);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				results.setData(map);
				return results;
			} else {//未注册
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该用户未注册");
				results.setData(map);
				return results;
			}
		}

	}

	/**
	 * parameter={"doctorsPhoneSet":""} @Title:
	 * teammateInfo @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "查询医生详细信息")
	@RequestMapping(value = "doctorInfo")
	@ResponseBody
	public Results<Map<String, Object>> doctorInfo(@RequestParam String parameter, HttpServletRequest request) {
		// 对应doctors_team_user_link表
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter); 
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //医生电话
		
		if (StringUtils.isNotBlank(doctorsPhone)) {
			//根据医生电话查询医生信息
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			if (doctorsUser != null) {
				map.put("doctorsUser", doctorsUser);
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该医生尚未注册");
				return results;
			}
			
			//根据医生电话查询该医生的领队团队信息
			List<DoctorsTeamUserLink> leaderTeamList = doctorsTeamUserLinkService.getList("getLeaderList", doctorsPhone);
			if (leaderTeamList.size() > 0) {
				//填充医生团队关系中的团队实体
				DoctorsTeam doctorsTeam = null;
				for(int i=0;i<leaderTeamList.size();i++){
					doctorsTeam = doctorsTeamService.findByParam("selectByTeamId", leaderTeamList.get(i).getTeamId());
					if (doctorsTeam != null) leaderTeamList.get(i).setDoctorsTeam(doctorsTeam);;		
				}
				map.put("teamLink", leaderTeamList);
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(map);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
	/**
	 * parameter={"teamId":"","doctorsPhoneSet":"1,2,3,4,5..."} @Title:
	 * addNewMemberForTeam @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results
	 * <String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "邀请医生加入会诊")
	@RequestMapping(value = "inviteForConsultation")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> inviteForConsultation(@RequestParam String parameter, HttpServletRequest request) {
		// 对应doctors_consultation_invite表，更新或添加一条status为1，state为0，isagree为2的记录，并且发透传通知对方
		// 判断是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter); 
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String doctorsPhoneSet = dataJsonObject.getString("doctorsPhoneSet"); //被邀请医生电话集合
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(doctorsPhoneSet)) {
			DoctorsConsultationUserLink doctorsConsultationUserLink = null;
			
			String[] phonesArray = doctorsPhoneSet.split(",");
			for(int i = 0; i < phonesArray.length; i++){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("doctors_phone", phonesArray[i]);
				paramMap.put("team_id", teamId);
				doctorsConsultationUserLink = doctorsConsultationUserLinkService.findByParam("selectByTeamidAndPhone", paramMap);
				if (null == doctorsConsultationUserLink) {//新成员
					//根据团队id查询团队信息
					DoctorsConsultation doctorsConsultation = doctorsConsultationService.findById("selectByTeamId", Integer.valueOf(teamId));
					//根据被邀请人电话查询被邀请人信息
					DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", phonesArray[i]);
					if (doctorsConsultation != null && doctorsUser != null) {
						// 更新或添加doctors_consultation_invite表一条status为1，state为0，isagree为2的记录
						Map<String, Object> paramMap2 = new HashMap<String,Object>();
						paramMap2.put("invited_phone", phonesArray[i]);
						paramMap2.put("team_id", teamId);
						DoctorsConsultationInvite doctorsConsultationInvite_ = doctorsConsultationInviteService.findByParam("selectByTeamidAndPhone", paramMap2); //查询团队提醒
						if (doctorsConsultationInvite_ != null) {//已存在，则更新
							doctorsConsultationInvite_.setStatus(1);
							doctorsConsultationInvite_.setState(0);
							doctorsConsultationInvite_.setIsagree(2);
							doctorsConsultationInvite_.setUpdatetime(new Date());
							doctorsConsultationInviteService.update("updateByPrimaryKeySelective", doctorsConsultationInvite_);
						} else {//未存在，则插入
							DoctorsConsultationInvite doctorsConsultationInvite = new DoctorsConsultationInvite();
							doctorsConsultationInvite.setTeamId(doctorsConsultation.getId());
							doctorsConsultationInvite.setTeamName(doctorsConsultation.getSubject());
							doctorsConsultationInvite.setInviteUseid(doctorsConsultation.getDoctorsId());
							doctorsConsultationInvite.setInviteName(doctorsConsultation.getDoctorsName());
							doctorsConsultationInvite.setInvitePhone(doctorsConsultation.getDoctorsPhone());
							doctorsConsultationInvite.setInviteLoginId(doctorsConsultation.getDoctorsLoginId());
							doctorsConsultationInvite.setInvitedId(doctorsUser.getId());
							doctorsConsultationInvite.setInvitedName(doctorsUser.getName());
							doctorsConsultationInvite.setInvitedPhone(doctorsUser.getPhone());
							doctorsConsultationInvite.setInvitedLoginId(doctorsUser.getLoginId());
							doctorsConsultationInvite.setStatus(1);
							doctorsConsultationInvite.setState(0);
							doctorsConsultationInvite.setIsagree(2);
							doctorsConsultationInvite.setCreatetime(new Date());
							doctorsConsultationInviteService.insert(doctorsConsultationInvite);
						}
						
						//给被邀请人发送透传消息
						Object result_ = null;
						try {
							EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
							Msg msg = new Msg();
							MsgContent msgContent = new MsgContent();
							msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的会诊消息");
							UserName userName = new UserName();
							userName.add(Constant.doctor + phonesArray[i]);
							msg.from(doctorsConsultation.getDoctorsLoginId()).target(userName).targetType("users").msg(msgContent);
							result_ = easemobSendMessage.sendMessage(msg);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							System.out.println("result=========================" + result_);
						}
						
					}
				}
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage("会诊邀请发送成功");
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
	
	/**
	 * parameter={"teamId":"","doctorsPhone":""} @Title:
	 * addNewMemberForTeam @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results
	 * <String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生接受会诊邀请")
	@RequestMapping(value = "agreeForConsultation")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> agreeForConsultation(@RequestParam String parameter, HttpServletRequest request) {
		// 先调用环信接口添加群员,对应doctors_consultation_user_link表，添加status为1的记录；同时对应doctors_consultation_invite表，更新一条status为1，state为1和isagree为1的记录，并给会诊群组透传加入的消息
		// 判断是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter); 
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //被邀请医生电话
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(doctorsPhone)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("team_id", teamId);
			DoctorsConsultationUserLink doctorsConsultationUserLink = doctorsConsultationUserLinkService.findByParam("selectByTeamidAndPhone", paramMap);
			if (null == doctorsConsultationUserLink) {//新成员
				//根据团队id查询团队信息
				DoctorsConsultation doctorsConsultation = doctorsConsultationService.findById("selectByTeamId", Integer.valueOf(teamId));
				//根据被邀请人电话查询被邀请人信息
				DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
				if (doctorsConsultation != null && doctorsUser != null) {
					//先调用环信接口添加群员
					EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
			        UserNames userNames = new UserNames();
			        UserName userList = new UserName();
			        userList.add(doctorsUser.getLoginId());
			        userNames.usernames(userList);
			        Object result = easemobChatGroup.addBatchUsersToChatGroup(doctorsConsultation.getChatGroupId(), userNames);
			        if (result != null) {
			        	//插入doctors_consultation_user_link表,添加status为1的记录
						DoctorsConsultationUserLink doctorsConsultationUserLink_ = new DoctorsConsultationUserLink();
						doctorsConsultationUserLink_.setTeamId(doctorsConsultation.getId());
						doctorsConsultationUserLink_.setTeamName(doctorsConsultation.getSubject());
						doctorsConsultationUserLink_.setChatGroupId(doctorsConsultation.getChatGroupId());
						doctorsConsultationUserLink_.setChatGroupName(doctorsConsultation.getChatGroupName());
						doctorsConsultationUserLink_.setDoctorsId(doctorsUser.getId());
						doctorsConsultationUserLink_.setDoctorsName(doctorsUser.getName());
						doctorsConsultationUserLink_.setDoctorsPhone(doctorsUser.getPhone());
						doctorsConsultationUserLink_.setDoctorsLoginId(doctorsUser.getLoginId());
						doctorsConsultationUserLink_.setDoctorsHeadUrl(doctorsUser.getHeadUrl());
						doctorsConsultationUserLink_.setStatus(1);
						doctorsConsultationUserLink_.setIsleader(0);
						doctorsConsultationUserLink_.setCreatetime(new Date());
						doctorsConsultationUserLinkService.insert(doctorsConsultationUserLink_);
						
						// 更新doctors_consultation_invite表一条status为1，state为1和isagree为1的记录
						Map<String, Object> paramMap2 = new HashMap<String,Object>();
						paramMap2.put("invited_phone", doctorsPhone);
						paramMap2.put("team_id", teamId);
						DoctorsConsultationInvite doctorsConsultationInvite_ = doctorsConsultationInviteService.findByParam("selectByTeamidAndPhone", paramMap2); //查询团队提醒
						if (doctorsConsultationInvite_ != null) {//更新
							doctorsConsultationInvite_.setStatus(1);
							doctorsConsultationInvite_.setState(1);
							doctorsConsultationInvite_.setIsagree(1);
							doctorsConsultationInvite_.setUpdatetime(new Date());
							doctorsConsultationInviteService.update("updateByPrimaryKeySelective", doctorsConsultationInvite_);
						} else {//插入
							DoctorsConsultationInvite doctorsConsultationInvite = new DoctorsConsultationInvite();
							doctorsConsultationInvite.setTeamId(doctorsConsultation.getId());
							doctorsConsultationInvite.setTeamName(doctorsConsultation.getSubject());
							doctorsConsultationInvite.setInviteUseid(doctorsConsultation.getDoctorsId());
							doctorsConsultationInvite.setInviteName(doctorsConsultation.getDoctorsName());
							doctorsConsultationInvite.setInvitePhone(doctorsConsultation.getDoctorsPhone());
							doctorsConsultationInvite.setInviteLoginId(doctorsConsultation.getDoctorsLoginId());
							doctorsConsultationInvite.setInvitedId(doctorsUser.getId());
							doctorsConsultationInvite.setInvitedName(doctorsUser.getName());
							doctorsConsultationInvite.setInvitedPhone(doctorsUser.getPhone());
							doctorsConsultationInvite.setInvitedLoginId(doctorsUser.getLoginId());
							doctorsConsultationInvite.setStatus(1);
							doctorsConsultationInvite.setState(1);
							doctorsConsultationInvite.setIsagree(1);
							doctorsConsultationInvite.setCreatetime(new Date());
							doctorsConsultationInviteService.insert(doctorsConsultationInvite);
						}
						
						//给群组发送加入会诊的消息
						Object result_ = null;
						try {
							EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
							Msg msg = new Msg();
							MsgContent msgContent = new MsgContent();
							msgContent.type(MsgContent.TypeEnum.TXT).msg(doctorsUser.getName() + "医生加入会诊");
							UserName userName = new UserName();
							userName.add(doctorsConsultation.getChatGroupId());
							msg.from(Constant.doctor + doctorsPhone).target(userName).targetType("chatgroups").msg(msgContent);
							result_ = easemobSendMessage.sendMessage(msg);
						} catch (Exception e) {
							System.out.println("result================================" + result_);
							// TODO: handle exception
							e.printStackTrace();
						}
						
						results.setCode(MessageCode.CODE_200);
						results.setMessage("成功加入会诊");
						return results;
			        } else {
			        	results.setCode(MessageCode.CODE_501);
						results.setMessage("环信添加群员失败");
						return results;
			        }
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("尚未注册或者该会诊团队不存在");
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_405);
				results.setMessage("您已经接受过了该会诊");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
	
	/**
	 * parameter={"teamId":"","doctorsPhone":"","rejectReason":""} @Title:
	 * addNewMemberForTeam @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results
	 * <String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生拒绝会诊邀请")
	@RequestMapping(value = "rejectForConsultation")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> rejectForConsultation(@RequestParam String parameter, HttpServletRequest request) {
		// 对应doctors_consultation_invite表，更新一条state为1和isagree为0的记录，并且发透传通知邀请发起者
		// 判断是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter); 
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //被邀请医生电话
		String rejectReason = dataJsonObject.getString("rejectReason"); //拒绝理由
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(doctorsPhone)) {
			//根据团队id查询团队信息
			DoctorsConsultation doctorsConsultation = doctorsConsultationService.findById("selectByTeamId", Integer.valueOf(teamId));
			if (doctorsConsultation != null) {
				// 更新doctors_consultation_invite表一条state为1和isagree为0的记录
				Map<String, Object> paramMap = new HashMap<String,Object>();
				paramMap.put("invited_phone", doctorsPhone);
				paramMap.put("team_id", teamId);
				DoctorsConsultationInvite doctorsConsultationInvite_ = doctorsConsultationInviteService.findByParam("selectByTeamidAndPhone", paramMap); //查询团队提醒
				if (doctorsConsultationInvite_ != null) {//更新
					doctorsConsultationInvite_.setStatus(1);
					doctorsConsultationInvite_.setState(1);
					doctorsConsultationInvite_.setIsagree(0);
					doctorsConsultationInvite_.setRejectReason(rejectReason);
					doctorsConsultationInvite_.setUpdatetime(new Date());
					doctorsConsultationInviteService.update("updateByPrimaryKeySelective", doctorsConsultationInvite_);
				}
				
				//给会诊发起人透传拒绝的消息
				Object result_ = null;
				try {
					EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
					Msg msg = new Msg();
					MsgContent msgContent = new MsgContent();
					msgContent.type(MsgContent.TypeEnum.CMD).msg(rejectReason);
					UserName userName = new UserName();
					userName.add(doctorsConsultation.getDoctorsLoginId());
					msg.from(Constant.doctor + doctorsPhone).target(userName).targetType("users").msg(msgContent);
					result_ = easemobSendMessage.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("result=========================" + result_);
				}
				
				results.setCode(MessageCode.CODE_200);
				results.setMessage("已拒绝该会诊请求");
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("该会诊团队不存在");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"teamId":"","doctorsPhoneSet":"1,2,3,4,5..."} @Title:
	 * addNewMemberForTeam @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results
	 * <String> 返回类型 @throws
	 */
/*	@SystemControllerLog(description = "为会诊添加成员")
	@RequestMapping(value = "addNewMemberForConsultation")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> addNewMemberForConsultation(@RequestParam String parameter, HttpServletRequest request) {
		// 先调用环信接口添加群员,对应doctors_consultation_user_link表，添加status为1的记录；同时对应doctors_consultation_invite表，更新或添加一条status为1，state为0的记录，并且发透传通知对方
		// 判断是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter); 
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String doctorsPhoneSet = dataJsonObject.getString("doctorsPhoneSet"); //被邀请医生电话集合
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(doctorsPhoneSet)) {
			DoctorsConsultationUserLink doctorsConsultationUserLink = null;
			
			String[] phonesArray = doctorsPhoneSet.split(",");
			for(int i = 0; i < phonesArray.length; i++){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("doctors_phone", phonesArray[i]);
				paramMap.put("team_id", teamId);
				doctorsConsultationUserLink = doctorsConsultationUserLinkService.findByParam("selectByTeamidAndPhone", paramMap);
				if (null == doctorsConsultationUserLink) {//新成员
					//根据团队id查询团队信息
					DoctorsConsultation doctorsConsultation = doctorsConsultationService.findById("selectByTeamId", Integer.valueOf(teamId));
					
					//根据被邀请人电话查询被邀请人信息
					DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", phonesArray[i]);
					
					if (doctorsConsultation != null && doctorsUser != null) {
						//先调用环信接口添加群员
						EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
				        UserNames userNames = new UserNames();
				        UserName userList = new UserName();
				        userList.add(doctorsUser.getLoginId());
				        userNames.usernames(userList);
				        Object result = easemobChatGroup.addBatchUsersToChatGroup(doctorsConsultation.getChatGroupId(), userNames);
				        if (result != null) {
				        	System.out.println(result.toString());
				        	//插入doctors_consultation_user_link表,添加status为1的记录
							DoctorsConsultationUserLink doctorsConsultationUserLink_ = new DoctorsConsultationUserLink();
							doctorsConsultationUserLink_.setTeamId(doctorsConsultation.getId());
							doctorsConsultationUserLink_.setTeamName(doctorsConsultation.getSubject());
							doctorsConsultationUserLink_.setChatGroupId(doctorsConsultation.getChatGroupId());
							doctorsConsultationUserLink_.setChatGroupName(doctorsConsultation.getChatGroupName());
							doctorsConsultationUserLink_.setDoctorsId(doctorsUser.getId());
							doctorsConsultationUserLink_.setDoctorsName(doctorsUser.getName());
							doctorsConsultationUserLink_.setDoctorsPhone(doctorsUser.getPhone());
							doctorsConsultationUserLink_.setDoctorsLoginId(doctorsUser.getLoginId());
							doctorsConsultationUserLink_.setDoctorsHeadUrl(doctorsUser.getHeadUrl());
							doctorsConsultationUserLink_.setStatus(1);
							doctorsConsultationUserLink_.setIsleader(0);
							doctorsConsultationUserLink_.setCreatetime(new Date());
							doctorsConsultationUserLinkService.insert(doctorsConsultationUserLink_);
							
							// 更新或添加doctors_consultation_invite表一条status为1，state为0的记录
							Map<String, Object> paramMap2 = new HashMap<String,Object>();
							paramMap2.put("invited_phone", phonesArray[i]);
							paramMap2.put("team_id", teamId);
							DoctorsConsultationInvite doctorsConsultationInvite_ = doctorsConsultationInviteService.findByParam("selectByTeamidAndPhone", paramMap2); //查询团队提醒
							if (doctorsConsultationInvite_ != null) {//更新
								doctorsConsultationInvite_.setStatus(1);
								doctorsConsultationInvite_.setState(0);
								doctorsConsultationInvite_.setUpdatetime(new Date());
								doctorsConsultationInviteService.update("updateByPrimaryKeySelective", doctorsConsultationInvite_);
							} else {//插入
								DoctorsConsultationInvite doctorsConsultationInvite = new DoctorsConsultationInvite();
								doctorsConsultationInvite.setTeamId(doctorsConsultation.getId());
								doctorsConsultationInvite.setTeamName(doctorsConsultation.getSubject());
								doctorsConsultationInvite.setInviteUseid(doctorsConsultation.getDoctorsId());
								doctorsConsultationInvite.setInviteName(doctorsConsultation.getDoctorsName());
								doctorsConsultationInvite.setInvitePhone(doctorsConsultation.getDoctorsPhone());
								doctorsConsultationInvite.setInviteLoginId(doctorsConsultation.getDoctorsLoginId());
								doctorsConsultationInvite.setInvitedId(doctorsUser.getId());
								doctorsConsultationInvite.setInvitedName(doctorsUser.getName());
								doctorsConsultationInvite.setInvitedPhone(doctorsUser.getPhone());
								doctorsConsultationInvite.setInvitedLoginId(doctorsUser.getLoginId());
								doctorsConsultationInvite.setStatus(1);
								doctorsConsultationInvite.setState(0);
								doctorsConsultationInvite.setCreatetime(new Date());
								doctorsConsultationInviteService.insert(doctorsConsultationInvite);
							}
							
							//给被邀请人发送透传消息
							Object result_ = null;
							try {
								EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
								Msg msg = new Msg();
								MsgContent msgContent = new MsgContent();
								msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的会诊消息");
								UserName userName = new UserName();
								userName.add(Constant.doctor + phonesArray[i]);
								msg.from(doctorsConsultation.getDoctorsLoginId()).target(userName).targetType("users").msg(msgContent);
								result_ = easemobSendMessage.sendMessage(msg);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								System.out.println("result=========================" + result_);
							}
				        } else {
				        	results.setCode(MessageCode.CODE_501);
							results.setMessage("环信添加用户失败");
							return results;
				        }
						
					}
				}
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功添加会诊成员");
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}*/

	/**
	 * parameter={"doctorsPhone":"","teamId":""} @Title:
	 * teammateInfosList @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "发起人查询会诊成员列表详细信息")
	@RequestMapping(value = "consultationTeammateList")
	@ResponseBody
	public Results<Map<String, Object>> consultationTeammateList(@RequestParam String parameter, HttpServletRequest request) {
		// 根据电话去查doctorUser表，把查出来的记录逐一添加到doctorUsersList里面(除开发起人不显示)
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUserList = new ArrayList<DoctorsUser>();;
		DoctorsUser doctorsUser = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String teamId = dataJsonObject.getString("teamId"); //会诊团队id
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //发起人号码
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(doctorsPhone)) {
			//根据会诊团队id获取该会诊团队的所有成员关系
			Map<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("team_id", teamId);
			List<DoctorsConsultationUserLink> listDoctorsConsultationUserLink = doctorsConsultationUserLinkService.getList("getTeammateByTeamId", paramMap);
			if (listDoctorsConsultationUserLink != null && listDoctorsConsultationUserLink.size() > 1) {//包含发起人，成员数应大于1
				for (int i = 0; i < listDoctorsConsultationUserLink.size(); i++) {
					if (!listDoctorsConsultationUserLink.get(i).getDoctorsPhone().equals(doctorsPhone)) {//不是发起人的成员
						doctorsUser = doctorsUserService.findByParam("selectByPhone", listDoctorsConsultationUserLink.get(i).getDoctorsPhone());
						if (doctorsUser != null) {
							doctorsUserList.add(doctorsUser);
						}
					}
				}
				map.put("doctorUsersList", doctorsUserList);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {//没有成员
				results.setCode(MessageCode.CODE_404);
				results.setMessage("您还没有添加会诊成员");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
	
	
	/**
	 * parameter={"phonesSet":"1,2,3,4,5...","teamId":""} @Title:
	 * teammateInfosList @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "移除会诊医生成员")
	@RequestMapping(value = "deleteConsultationTeammate")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> deleteConsultationTeammate(@RequestParam String parameter, HttpServletRequest request) {
		// 先调用环信接口删除群员，对应doctors_consultation_user_link表，删除对应记录；同时对应doctors_consultation_invite表，更新对应记录status为0，state为0，并且发透传通知对方
		// 判断团队关系是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String phonesSet = dataJsonObject.getString("phonesSet"); //被移除的会诊医生电话集合，以逗号隔开
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(phonesSet)) {
			DoctorsConsultationUserLink doctorsConsultationUserLink = null;
			DoctorsConsultationInvite doctorsConsultationInvite = null;
			
			String[] phonesArray = phonesSet.split(",");
			for(int i = 0; i < phonesArray.length; i++){
				//查询团队关系
				Map<String, Object> paramMap = new HashMap<String,Object>();
				paramMap.put("doctors_phone", phonesArray[i]);
				paramMap.put("team_id", teamId);
				doctorsConsultationUserLink = doctorsConsultationUserLinkService.findByParam("selectByTeamidAndPhone", paramMap);
				
				//查询团队提醒
				Map<String, Object> paramMap2 = new HashMap<String,Object>();
				paramMap2.put("invited_phone", phonesArray[i]);
				paramMap2.put("team_id", teamId);
				doctorsConsultationInvite = doctorsConsultationInviteService.findByParam("selectByTeamidAndPhone", paramMap2);
				
				if (doctorsConsultationUserLink != null && doctorsConsultationInvite != null) {
					//先调用环信接口删除群员
					EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
			        Object result = easemobChatGroup.removeSingleUserFromChatGroup(doctorsConsultationUserLink.getChatGroupId(), 
			        		doctorsConsultationUserLink.getDoctorsLoginId());
			        if (result != null) {
			        	System.out.println(result);
			        	JSONObject resultObject = JSONObject.parseObject(result.toString());
						String dataStr = resultObject.getString("data");
						JSONObject dataStrObject = JSONObject.parseObject(dataStr);
						Boolean isSuccess = dataStrObject.getBoolean("result");
						if (isSuccess) {
							//删除该会诊医生团队关系
							doctorsConsultationUserLinkService.delete("deleteByPrimaryKey", doctorsConsultationUserLink);
							
							//更新会诊团队提醒状态
							doctorsConsultationInvite.setStatus(0);
							doctorsConsultationInvite.setState(0);
							doctorsConsultationInviteService.update("updateByPrimaryKeySelective", doctorsConsultationInvite);
							
							//发透传通知对方
							try {
								EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
								Msg msg = new Msg();
								MsgContent msgContent = new MsgContent();
								msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的团队消息");
								UserName userName = new UserName();
								userName.add(doctorsConsultationInvite.getInvitedLoginId());
								msg.from(doctorsConsultationInvite.getInviteLoginId()).target(userName).targetType("users").msg(msgContent);
								easemobSendMessage.sendMessage(msg);
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else {
							results.setCode(MessageCode.CODE_501);
							results.setMessage("环信移除群组成员失败");
							return results;
						}
			        } else {
			        	results.setCode(MessageCode.CODE_501);
						results.setMessage("环信移除群组成员失败");
						return results;
			        }
			        
				}
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage("移除成功");
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
}
