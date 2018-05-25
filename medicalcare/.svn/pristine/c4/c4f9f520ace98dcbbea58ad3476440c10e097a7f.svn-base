/**   
 * @Title: DoctorsTeamController.java 
 * @Package com.cmcc.medicalcare.controller.app 
 * @Description: TODO
 * @author adminstrator   
 * @date 2017年4月10日 上午10:43:20 
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
import com.cmcc.medicalcare.model.DoctorsConsultationUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.service.IDoctorsConsultationService;
import com.cmcc.medicalcare.service.IDoctorsConsultationUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.google.gson.Gson;

import io.swagger.client.model.Group;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 医生会诊团队资料
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:43:20
 * 
 */
@Controller
@RequestMapping("/app/doctorsConsultation")
public class DoctorsConsultationController {

	@Resource
	private IDoctorsConsultationService doctorsConsultationService;

	@Resource
	private IDoctorsConsultationUserLinkService doctorsConsultationUserLinkService;

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IPatientUserService patientUserService;

	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	/**
	 * parameter={"doctorsPhone":"","patientPhone":"","chatroomId":""}
	 * 
	 * @Title: select @Description: TODO @param @param request @param @return
	 *         设定文件 @return Results<List<DoctorsTeam>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "查询会诊团队资料和成员")
	@RequestMapping(value = "selectDoctorsConsultation")
	@ResponseBody
	public Results<Map<String, Object>> selectDoctorsConsultation(HttpServletRequest request,
			String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorsConsultation doctorsConsultation = null;
		List<DoctorsConsultationUserLink> listDoctorsConsultationUserLink = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String patientPhone = dataJsonObject.getString("patientPhone");
		String chatroomId = dataJsonObject.getString("chatroomId");
		if (!StringUtils.isNotBlank(patientPhone)) {
			SecretaryAndDoctorsAndPatientOfChatroom secretaryAndDoctorsAndPatientOfChatroom = secretaryAndDoctorsAndPatientOfChatroomService
					.findByParam("selectBySecretaryChatroomId", chatroomId);
			patientPhone = secretaryAndDoctorsAndPatientOfChatroom.getPatientPhone();
		}
		if (StringUtils.isNotBlank(doctorsPhone) && StringUtils.isNotBlank(patientPhone)) {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("doctorsPhone", doctorsPhone);
			paraMap.put("patientPhone", patientPhone);

			doctorsConsultation = doctorsConsultationService.findByParam(
					"selectDoctorsConsultationByDoctorsPhoneAndPatientPhone", paraMap);
			if (doctorsConsultation != null) {
				Map<String, Object> paraMap2 = new HashMap<String, Object>();
				paraMap2.put("team_id", doctorsConsultation.getId());
				listDoctorsConsultationUserLink = doctorsConsultationUserLinkService.getList(
						"selectByTeamId", paraMap2);
				/*for (int i = 0; i < listDoctorsConsultationUserLink.size(); i++) {
					String doctors_Phone = listDoctorsConsultationUserLink.get(i).getDoctorsPhone();
					DoctorsUser doctorsUser = doctorsUserService.findByParam(
							"selectByDoctorsPhone", doctors_Phone);
					listDoctorsConsultationUserLink.get(i).setDoctorsUser(doctorsUser);
				}*/
				if (doctorsPhone.equals(doctorsConsultation.getDoctorsPhone())) {
					map.put("isleader", "yes");// 是会诊发起人显示"+""-"号
				} else {
					map.put("isleader", "no");// 不显示"+""-"号
				}
				map.put("doctorsConsultation", doctorsConsultation);
				map.put("listDoctorsConsultationUserLink", listDoctorsConsultationUserLink);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				PatientUser patientUser = patientUserService.findByParam("selectByPhone",
						patientPhone);
				if (patientUser != null) {
					map.put("patientUser", patientUser);
					map.put("isForNew", "yes");
					results.setData(map);
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
				} else {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("会诊团队信息为空，请先添加会诊医生成员");
				}
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}
	
	/**
	 * parameter={"doctorsPhone":"","patientPhone":"","subject":"","target":""} @Title:
	 * teammateInfosList @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "发起会诊")
	@RequestMapping(value = "launch")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> launch(@RequestParam String parameter, HttpServletRequest request) {
//		Gson gson = new Gson();
//		DoctorsConsultation doctorsConsultation = gson.fromJson(parameter, DoctorsConsultation.class);
		// 先在环信建好群组，然后本地入库，发透传通知成员
		// 返回listDoctorsConsultationUserLink
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsConsultationUserLink> listDoctorsConsultationUserLink = new ArrayList<DoctorsConsultationUserLink>();
		DoctorsConsultationUserLink doctorsConsultationUserLink = null;
		DoctorsConsultation doctorsConsultation = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone"); //发起医生电话
		String patientPhone = dataJsonObject.getString("patientPhone"); //患者电话
		String subject = dataJsonObject.getString("subject"); //会诊标题
		String target = dataJsonObject.getString("target"); //会诊目标
		
		//判断参数是否为空
		if (!StringUtils.isNotBlank(doctorsPhone) || !StringUtils.isNotBlank(patientPhone) 
				|| !StringUtils.isNotBlank(subject) || !StringUtils.isNotBlank(target)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		//获取会诊参与医生数组
//		String[] phonesArray = partnerPhoneSet.split(",");
		
		//查询是否存在该医生和患者的会诊
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("doctorsPhone", doctorsPhone);
		paraMap.put("patientPhone", patientPhone);
		DoctorsConsultation doctorsConsultation_ = doctorsConsultationService.findByParam("selectDoctorsConsultationByDoctorsPhoneAndPatientPhone", paraMap);
		if (null == doctorsConsultation_) {//还没建立会诊群组
			String groupId = null; //环信群组id
			//先在环信建好群组
			try {
				EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
				Group group = new Group();
				UserName userName = new UserName();
				userName.add(Constant.doctor + doctorsPhone);
				group.groupname(subject).desc(target)._public(true).maxusers(50).approval(false).owner(Constant.doctor + doctorsPhone);
				Object result_ = easemobChatGroup.createChatGroup(group);
				if (result_ != null) {
					System.out.println("群组===" + result_.toString());
					JSONObject resultObject = JSONObject.parseObject(result_.toString());
					String dataStr = resultObject.getString("data");
					JSONObject dataStrObject = JSONObject.parseObject(dataStr);
					groupId = dataStrObject.getString("groupid");
					System.out.println("groupId:" + groupId);
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法在环信创建会诊群组");
					return results;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//根据医生电话查询医生信息
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			
			//根据患者电话查询患者信息
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			
			//会诊群组信息入库
			if (groupId != null && doctorsUser != null && patientUser != null) {
				//插入doctors_consultation表
				doctorsConsultation = new DoctorsConsultation();
				doctorsConsultation.setSubject(subject);
				doctorsConsultation.setTarget(target);
				doctorsConsultation.setPatientId(patientUser.getId());
				doctorsConsultation.setPatientName(patientUser.getName());
				doctorsConsultation.setPatientPhone(patientPhone);
				doctorsConsultation.setPatientLoginId(patientUser.getLoginId());
				doctorsConsultation.setPatientHeadUrl(patientUser.getHeadUrl());
				doctorsConsultation.setDoctorsId(doctorsUser.getId());
				doctorsConsultation.setDoctorsName(doctorsUser.getName());
				doctorsConsultation.setDoctorsPhone(doctorsPhone);
				doctorsConsultation.setDoctorsLoginId(doctorsUser.getLoginId());
				doctorsConsultation.setDoctorsHeadUrl(doctorsUser.getHeadUrl());
				doctorsConsultation.setChatGroupId(groupId);
				doctorsConsultation.setChatGroupName(subject);
				doctorsConsultation.setCreatetime(new Date());
				doctorsConsultationService.insert(doctorsConsultation);
				
				//插入doctors_consultation_user_link表,添加isleader为1的记录
				doctorsConsultationUserLink = new DoctorsConsultationUserLink();
				doctorsConsultationUserLink.setTeamId(doctorsConsultation.getId());
				doctorsConsultationUserLink.setTeamName(doctorsConsultation.getSubject());
				doctorsConsultationUserLink.setChatGroupId(doctorsConsultation.getChatGroupId());
				doctorsConsultationUserLink.setChatGroupName(doctorsConsultation.getChatGroupName());
				doctorsConsultationUserLink.setDoctorsId(doctorsUser.getId());
				doctorsConsultationUserLink.setDoctorsName(doctorsUser.getName());
				doctorsConsultationUserLink.setDoctorsPhone(doctorsUser.getPhone());
				doctorsConsultationUserLink.setDoctorsLoginId(doctorsUser.getLoginId());
				doctorsConsultationUserLink.setDoctorsHeadUrl(doctorsUser.getHeadUrl());
				doctorsConsultationUserLink.setStatus(0);
				doctorsConsultationUserLink.setIsleader(1);
				doctorsConsultationUserLink.setCreatetime(new Date());
				doctorsConsultationUserLinkService.insert(doctorsConsultationUserLink);
				
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("创建会诊群组失败");
				return results;
			}
			
			//返回领队信息
			if (doctorsConsultationUserLink != null && doctorsConsultation != null) {
//				doctorsConsultationUserLink_.setDoctorsConsultation(doctorsConsultation);
//				doctorsConsultationUserLink_.setDoctorsUser(doctorsUser);
				listDoctorsConsultationUserLink.add(doctorsConsultationUserLink);
				map.put("listDoctorsConsultationUserLink", listDoctorsConsultationUserLink);
				map.put("doctorsConsultation", doctorsConsultation);
				results.setData(map);
			}
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
			
		} else {
			map.put("doctorsConsultation", doctorsConsultation_);
			results.setData(map);
			results.setCode(MessageCode.CODE_405);
			results.setMessage(MessageCode.MESSAGE_405);
			return results;
		}
		
	}
	
}
