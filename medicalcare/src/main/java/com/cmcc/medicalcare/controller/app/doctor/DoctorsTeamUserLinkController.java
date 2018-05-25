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
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamInvite;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsTeamInviteService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.Toolkit;
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

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IDoctorsTeamInviteService doctorsTeamInviteService;
	
	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;
	
	@Resource
	private IPatientUserService patientUserService;

	/**
	 * parameter={"keyword":"","teamId":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "精确检索医生")
	@RequestMapping({ "searchDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> searchDoctor(@RequestParam String parameter, HttpServletRequest request) {
		// 根据医生的名字或者电话精确查询医生，返回一个list,已经在这个团队的就不能再出现在list里面，如果list长度为零则发短信邀请
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();
		List<DoctorsTeamUserLink> listDoctorsTeamUser_ = null;

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String teamId = dataJsonObject.getString("teamId");

		if (StringUtils.isNotBlank(teamId)) {

			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("team_id", teamId);
			listDoctorsTeamUser_ = doctorsTeamUserLinkService.getList("selectByTeamId", paraMap);

			if (listDoctorsTeamUser_ != null && listDoctorsTeamUser_.size() > 0) {// 有成员
				for (int i = 0; i < listDoctorsTeamUser_.size(); i++) {
					String phone = listDoctorsTeamUser_.get(i).getDoctorsPhone();
					String doctorName = listDoctorsTeamUser_.get(i).getDoctorsName();
					if (phone.equals(keyword) || doctorName.equals(keyword)) {//如果团队已经存在该成员，检索结果为空
						results.setCode(MessageCode.CODE_405);
						results.setMessage(MessageCode.MESSAGE_405);
						results.setData(map);
						return results;
					}
				}

				if (!StringUtils.isNotBlank(keyword)) { //关键字为空，返回全部医生数据
					String equipmentData = request.getParameter("equipmentData");
					JSONObject dataJsonObject_ = JSONObject.parseObject(equipmentData);
					String phone = dataJsonObject_.getString("phone");
					doctorsUsersList = doctorsUserService.getList("selectOthers", phone);
					map.put("doctorsUsersList", doctorsUsersList);
					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功");
					results.setData(map);
					return results;
				} else if (Toolkit.isMobileNO(keyword)) {//如果关键字为电话号码
					String phone = keyword;
					// 根据电话去查询doctors_user
					DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);
					if (doctorsUser!=null) {
						doctorsUsersList.add(doctorsUser);
						map.put("doctorsUsersList", doctorsUsersList);
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
					doctorsUsersList = doctorsUserService.getList("selectByName", name);
					if (doctorsUsersList!=null&&doctorsUsersList.size()>0) {
						map.put("doctorsUsersList", doctorsUsersList);
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
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		return results;

	}

	/**
	 * parameter={"":""} @Title: searchDoctor @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "发短信邀请医生注册并加入团队")
	@RequestMapping({ "sendMessage" })
	@ResponseBody
	public Results<Map<String, Object>> sendMessage(@RequestParam String parameter, HttpServletRequest request) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = null;
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);

		Map<String, Object> paramMap = new HashMap<String, Object>();

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"doctorsPhone":""} @Title:
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
			//根据被邀请人电话查询被邀请人信息
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			
			if (doctorsUser != null) {
				String hospitalName = doctorsUser.getHospital();
				DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByHospitalName", hospitalName);
				doctorsUser.setDoctorsTeam(doctorsTeam);
				map.put("doctorsUser", doctorsUser);
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该医生尚未注册");
				return results;
			}
			
			//根据被邀请人电话查询被邀请人的领队团队信息
			/*List<DoctorsTeamUserLink> leaderTeamList = doctorsTeamUserLinkService.getList("getLeaderList", doctorsPhone);
			if (leaderTeamList.size() > 0) {
				//填充医生团队关系中的团队实体
				DoctorsTeam doctorsTeam = null;
				for(int i=0;i<leaderTeamList.size();i++){
					doctorsTeam = doctorsTeamService.findByParam("selectByTeamId", leaderTeamList.get(i).getTeamId());
					if (doctorsTeam != null) leaderTeamList.get(i).setDoctorsTeam(doctorsTeam);;		
				}
				map.put("teamLink", leaderTeamList);
			}*/
			
			List<DoctorsTeamUserLink> leaderTeamList = doctorsTeamUserLinkService.getList("getLeaderList", doctorsPhone);
			if (leaderTeamList.size() > 0) {
				//填充医生团队关系中的团队实体
				DoctorsTeam doctorsTeam = null;
				for(int i=0;i<leaderTeamList.size();i++){
					doctorsTeam = doctorsTeamService.findByParam("selectByTeamId", leaderTeamList.get(i).getTeamId());
					if (doctorsTeam != null) map.put("doctorsTeam", doctorsTeam);		
				}
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
	 * parameter={"doctorsPhone":"","teamId":""} @Title:
	 * addNewMemberForTeam @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results
	 * <String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "为团队添加成员")
	@RequestMapping(value = "addNewMemberForTeam")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> addNewMemberForTeam(@RequestParam String parameter, HttpServletRequest request) {
		// 对应doctors_team_user_link表，添加status为1的记录；同时对应doctors_team_invite表，更新或添加一条status为1，state为0的记录，并且发透传通知对方
		// 判断是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter); 
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //被邀请人电话
		String teamId = dataJsonObject.getString("teamId"); //团队id
		
		if (StringUtils.isNotBlank(doctorsPhone) && StringUtils.isNotBlank(teamId)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("team_id", teamId);
			List<DoctorsTeamUserLink> doctorsTeamUserLinkList = doctorsTeamUserLinkService.getList("selectByTeamidAndPhone", paramMap);
			if (null == doctorsTeamUserLinkList||doctorsTeamUserLinkList.size()<1) {//新成员
				//根据团队id查询团队信息
				DoctorsTeam doctorsTeam = doctorsTeamService.findById("selectByTeamId", Integer.valueOf(teamId));
				
				//根据被邀请人电话查询被邀请人信息
				DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
				
				if (doctorsTeam != null && doctorsUser != null) {
					//插入doctors_team_user_link表
					DoctorsTeamUserLink doctorsTeamUserLink_ = new DoctorsTeamUserLink();
					doctorsTeamUserLink_.setTeamId(doctorsTeam.getId());
					doctorsTeamUserLink_.setTeamName(doctorsTeam.getName());
					doctorsTeamUserLink_.setDoctorsId(doctorsUser.getId());
					doctorsTeamUserLink_.setDoctorsName(doctorsUser.getName());
					doctorsTeamUserLink_.setDoctorsPhone(doctorsUser.getPhone());
					doctorsTeamUserLink_.setDoctorsLoginId(doctorsUser.getLoginId());
					doctorsTeamUserLink_.setDoctorsHeadUrl(doctorsUser.getHeadUrl());
					doctorsTeamUserLink_.setStatus(1);
					doctorsTeamUserLink_.setIsleader(0);
					doctorsTeamUserLink_.setCreatetime(new Date());
					doctorsTeamUserLinkService.insert(doctorsTeamUserLink_);
					
					// 更新或添加doctors_team_invite表一条status为1，state为0的记录
					Map<String, Object> paramMap2 = new HashMap<String,Object>();
					paramMap2.put("invited_phone", doctorsPhone);
					paramMap2.put("team_id", teamId);
					DoctorsTeamInvite doctorsTeamInvite_ = doctorsTeamInviteService.findByParam("selectByTeamidAndPhone", paramMap2); //查询团队提醒
					if (doctorsTeamInvite_ != null) {//更新
						doctorsTeamInvite_.setStatus(1);
						doctorsTeamInvite_.setState(0);
						doctorsTeamInvite_.setUpdatetime(new Date());
						doctorsTeamInviteService.update("updateByPrimaryKeySelective", doctorsTeamInvite_);
					} else {//插入
						DoctorsTeamInvite doctorsTeamInvite = new DoctorsTeamInvite();
						doctorsTeamInvite.setTeamId(doctorsTeam.getId());
						doctorsTeamInvite.setTeamName(doctorsTeam.getName());
						doctorsTeamInvite.setInviteUseid(doctorsTeam.getDoctorsId());
						doctorsTeamInvite.setInviteName(doctorsTeam.getDoctorsName());
						doctorsTeamInvite.setInvitePhone(doctorsTeam.getDoctorsPhone());
						doctorsTeamInvite.setInviteLoginId(doctorsTeam.getDoctorsLoginId());
						doctorsTeamInvite.setInvitedId(doctorsUser.getId());
						doctorsTeamInvite.setInvitedName(doctorsUser.getName());
						doctorsTeamInvite.setInvitedPhone(doctorsUser.getPhone());
						doctorsTeamInvite.setInvitedLoginId(doctorsUser.getLoginId());
						doctorsTeamInvite.setStatus(1);
						doctorsTeamInvite.setState(0);
						doctorsTeamInvite.setCreatetime(new Date());
						doctorsTeamInviteService.insert(doctorsTeamInvite);
					}
					
					//给被邀请人发送透传消息
					Object result = null;
					try {
						EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
						Msg msg = new Msg();
						MsgContent msgContent = new MsgContent();
						msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的团队消息");
						UserName userName = new UserName();
						userName.add(Constant.doctor + doctorsPhone);
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("messageType", "doctorsTeamInvite");
						msg.from(doctorsTeam.getDoctorsLoginId()).target(userName).targetType("users").msg(msgContent).ext(ext);
						result = easemobSendMessage.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					if (null == result) {
						map.put("data", "发送环信消息失败");
						results.setData(map);
					}
					
					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功添加成员");
					return results;
				} else {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("该团队或医生不存在");
					return results;
				}
			} else {//已是成员
				results.setCode(MessageCode.CODE_405);
				results.setMessage("您已经添加了该成员");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"doctorsPhone":"","teamId":""} @Title:
	 * teammateInfosList @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "领队查询团队成员列表详细信息")
	@RequestMapping(value = "teammateInfosList")
	@ResponseBody
	public Results<Map<String, Object>> teammateInfosList(@RequestParam String parameter, HttpServletRequest request) {
		// 根据电话去查doctorUser表，把查出来的记录逐一添加到doctorUsersList里面(除开领队不显示)
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUserList = new ArrayList<DoctorsUser>();;
		DoctorsUser doctorsUser = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //领队号码
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(doctorsPhone)) {
			//根据团队id获取该团队的所有成员关系
			Map<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("team_id", teamId);
			List<DoctorsTeamUserLink> allTteammateList = doctorsTeamUserLinkService.getList("getTeammateByTeamId", paramMap);
			if (allTteammateList != null && allTteammateList.size() > 1) {//包含领队，成员数应大于1
				for (int i = 0; i < allTteammateList.size(); i++) {
					if (!allTteammateList.get(i).getDoctorsPhone().equals(doctorsPhone)) {//不是领队的成员
						doctorsUser = doctorsUserService.findByParam("selectByPhone", allTteammateList.get(i).getDoctorsPhone());
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
				results.setMessage("您还没有添加成员");
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
	@SystemControllerLog(description = "移除成员")
	@RequestMapping(value = "deleteMembers")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> deleteMembers(@RequestParam String parameter, HttpServletRequest request) {
		// 删除成员，对应doctors_team_user_link表，删除对应记录；同时对应doctors_team_invite表，更新对应记录status为0，state为0，并且发透传通知对方
		// 判断团队关系是否存在的条件为团队id和医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String teamId = dataJsonObject.getString("teamId"); //团队id
		String phonesSet = dataJsonObject.getString("phonesSet"); //被移除的团员集合，以逗号隔开
		
		if (StringUtils.isNotBlank(teamId) && StringUtils.isNotBlank(phonesSet)) {
			DoctorsTeamUserLink doctorsTeamUserLink = null;
			DoctorsTeamInvite doctorsTeamInvite = null;
			
			String[] phonesArray = phonesSet.split(",");
			for(int i = 0; i < phonesArray.length; i++){
				//查询团队关系
				Map<String, Object> paramMap = new HashMap<String,Object>();
				paramMap.put("doctors_phone", phonesArray[i]);
				paramMap.put("team_id", teamId);
				List<DoctorsTeamUserLink> doctorsTeamUserLinkList = doctorsTeamUserLinkService.getList("selectByTeamidAndPhone", paramMap);
								
				//查询团队提醒
				Map<String, Object> paramMap2 = new HashMap<String,Object>();
				paramMap2.put("invited_phone", phonesArray[i]);
				paramMap2.put("team_id", teamId);
				doctorsTeamInvite = doctorsTeamInviteService.findByParam("selectByTeamidAndPhone", paramMap2);
				
				if (doctorsTeamUserLinkList != null&&doctorsTeamUserLinkList.size()>0 && doctorsTeamInvite != null) {
					doctorsTeamUserLink = doctorsTeamUserLinkList.get(0);
					doctorsTeamUserLinkService.delete("deleteByPrimaryKey", doctorsTeamUserLink);//删除团队关系
					
					//更新团队提醒状态
					doctorsTeamInvite.setStatus(0);
					doctorsTeamInvite.setState(0);
					doctorsTeamInviteService.update("updateByPrimaryKeySelective", doctorsTeamInvite);
					
					//发透传通知对方
					try {
						EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
						Msg msg = new Msg();
						MsgContent msgContent = new MsgContent();
						msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的团队消息");
						UserName userName = new UserName();
						userName.add(doctorsTeamInvite.getInvitedLoginId());
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("messageType", "doctorsTeamInvite");
						msg.from(doctorsTeamInvite.getInviteLoginId()).target(userName).targetType("users").msg(msgContent).ext(ext);
						easemobSendMessage.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
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
	
	

	/**
	 * parameter={"doctorsPhone":"","keyword":""} @Title:
	 * teammateInfo @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "检索成员的患者")
	@RequestMapping(value = "searchPatient")
	@ResponseBody
	public Results<Map<String, Object>> searchPatient(@RequestParam String parameter, HttpServletRequest request) {
		// 根据医生电话和关键字去模糊查doctors_patient_link表，找出所有符合条件患者，并根据患者电话去patient_user表找出详细信息，并返回listDoctorsPatientLink
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsPatientLink> listDoctorsPatientLink = null;

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //医生电话
		String keyword = dataJsonObject.getString("keyword"); //搜索关键字

		if (StringUtils.isNotBlank(doctorsPhone)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("type", 1); //只返回已是好友的患者列表
			paramMap.put("keyword", "%"+keyword+"%");
			listDoctorsPatientLink = doctorsPatientLinkService.getList("selectByDoctorsPhoneAndKeyword", paramMap);
			if (listDoctorsPatientLink.size() > 0) {
				//填充医生关系中的患者实体
				PatientUser patientUser = null;
				for(int i=0;i<listDoctorsPatientLink.size();i++){
					patientUser = patientUserService.findByParam("selectByPhone", listDoctorsPatientLink.get(i).getPatientPhone());
					if (patientUser != null) listDoctorsPatientLink.get(i).setPatientUser(patientUser);		
				}
				
				map.put("listDoctorsPatientLink", listDoctorsPatientLink);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
}
