/**   
* @Title: DoctorsReferralController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:55:17 
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
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientReferral;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.model.SecretaryPatientLink;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientReferralService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.cmcc.medicalcare.service.ISecretaryPatientLinkService;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.easemob.server.api.impl.EasemobIMUsers;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;

import io.swagger.client.model.Group;
import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: DoctorsReferralController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:55:17
 * 
 */
@Controller
@RequestMapping("/app/doctorsReferral")
public class DoctorsReferralController {

	@Resource
	private IPatientReferralService patientReferralService;

	@Resource
	private IPatientUserService patientUserService;

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private ISecretaryPatientLinkService secretaryPatientLinkService;

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	/**
	 * parameter={"doctorsPhone":""} @Title: tipsOfDoctorsReferral @Description:
	 * TODO @param @param parameter @param @param request @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	// @SystemControllerLog(description = "获取转诊邀请提醒信息")
	// @RequestMapping(value = "tipsOfDoctorsReferral")
	// @ResponseBody
	// @Transactional
	// public Results<Map<String, Object>> tipsOfDoctorsReferral(@RequestParam
	// String parameter,
	// HttpServletRequest request) {
	// //
	// Results<Map<String, Object>> results = new Results<Map<String,
	// Object>>();
	// Map<String, Object> resultsMap = new HashMap<String, Object>();
	//
	// JSONObject dataJsonObject = JSONObject.parseObject(parameter);
	// String phone = dataJsonObject.getString("doctorsPhone");
	//
	// List<PatientReferral> patientReferralList =
	// patientReferralService.getList("selectByPrimaryKey", 1);
	//
	// PatientReferral patientReferral = patientReferralList.get(0);
	// patientReferral.setRejectReason("123");
	// patientReferralService.update("updateByPrimaryKey", patientReferral);
	//
	// String id = "1";
	// if (id.equals("1")) {
	// // throw new NumberFormatException();
	// }
	//
	// patientReferral.setRejectReason("456");
	// patientReferralService.update("updateByPrimaryKey", patientReferral);
	//
	// resultsMap.put("patientReferral", patientReferralList.get(0));
	// results.setData(resultsMap);
	// results.setCode(MessageCode.CODE_200);
	// results.setMessage(MessageCode.MESSAGE_200);
	// return results;
	// }

	/**
	 * parameter={"doctorsPhone":""} @Title:
	 * getListOfDoctorsReferral @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "获取转诊邀请列表")
	@RequestMapping(value = "getListOfDoctorsReferral")
	@ResponseBody
	public Results<Map<String, Object>> getListOfDoctorsReferral(@RequestParam String parameter,
			HttpServletRequest request) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String phone = dataJsonObject.getString("doctorsPhone");

		// 获取转诊邀请列表
		List<PatientReferral> patientReferralList = patientReferralService.getList("selectByPhone2", phone);// to_doctors_phone
																											// OR
																											// from_doctors_phone
		if (patientReferralList != null && patientReferralList.size() > 0) {
			for (int i = 0; i < patientReferralList.size(); i++) {
				PatientUser patientUser = patientUserService.findByParam("selectByPhone",
						patientReferralList.get(i).getPatientPhone());// 查询患者资料
				patientReferralList.get(i).setPatientUser(patientUser);
			}
		}

		resultsMap.put("patientReferralList", patientReferralList);

		results.setData(resultsMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
	}

	/**
	 * parameter={"id":""}
	 * 
	 * @Title: getInfoOfDoctorsReferral @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "获取转诊邀请详细信息")
	@RequestMapping(value = "getInfoOfDoctorsReferral")
	@ResponseBody
	public Results<Map<String, Object>> getInfoOfDoctorsReferral(@RequestParam String parameter,
			HttpServletRequest request) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String id = dataJsonObject.getString("id");

		// 获取转诊邀请详细信息
		List<PatientReferral> patientReferralList = patientReferralService.getList("selectByPrimaryKey",
				Integer.parseInt(id));
		if (patientReferralList != null && patientReferralList.size() > 0) {
			PatientUser patientUser = patientUserService.findByParam("selectByPhone",
					patientReferralList.get(0).getPatientPhone());// 查询患者资料
			patientReferralList.get(0).setPatientUser(patientUser);

			resultsMap.put("patientReferral", patientReferralList.get(0));
		}

		results.setData(resultsMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
	}

	/**
	 * parameter={"id":"","rejectReason":""} @Title:
	 * rejectOthersReferral @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "拒绝转入邀请")
	@RequestMapping(value = "rejectOthersReferral")
	@ResponseBody
	public Results<Map<String, Object>> rejectOthersReferral(@RequestParam String parameter,
			HttpServletRequest request) {
		// type为1，status为2
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String id = dataJsonObject.getString("id");
		String rejectReason = dataJsonObject.getString("rejectReason");

		if (StringUtils.isNotBlank(id)) {
			List<PatientReferral> patientReferralList = patientReferralService.getList("selectByPrimaryKey",
					Integer.parseInt(id));
			if (patientReferralList != null && patientReferralList.size() > 0) {
				PatientReferral patientReferral = patientReferralList.get(0);
				patientReferral.setRejectReason(rejectReason);
				patientReferral.setUpdatetime(new Date());
				patientReferral.setState(2);
				patientReferralService.update("updateByPrimaryKey", patientReferral);

				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("messageType", "doctorReferral");

				EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();

				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName = new UserName();
				userName.add(patientReferral.getFromDoctorsLoginId());
				msg.from(patientReferral.getToDoctorsLoginId()).target(userName).targetType("users").msg(msgContent)
						.ext(ext);
				Object result = easemobSendMessage.sendMessage(msg);// 发透传通知已拒绝

//				System.out.println(result.toString());

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

	/**
	 * parameter={"patientName":"","patientPhone":"","fromDoctorsName":"","
	 * fromDoctorsPhone":"","toDoctorsName":"","toDoctorsPhone":"","message":""
	 * } @Title: requestReferral @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "转诊申请")
	@RequestMapping(value = "requestReferral")
	@ResponseBody
	public Results<Map<String, Object>> requestReferral(@RequestParam String parameter, HttpServletRequest request) {
		// type为1，status为1
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientName = dataJsonObject.getString("patientName");
		String patientPhone = dataJsonObject.getString("patientPhone");
		String fromDoctorsName = dataJsonObject.getString("fromDoctorsName");
		String fromDoctorsPhone = dataJsonObject.getString("fromDoctorsPhone");
		String toDoctorsName = dataJsonObject.getString("toDoctorsName");
		String toDoctorsPhone = dataJsonObject.getString("toDoctorsPhone");
		String message = dataJsonObject.getString("message");

		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(fromDoctorsPhone)
				&& StringUtils.isNotBlank(toDoctorsPhone)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", toDoctorsPhone);
			paramMap.put("patient_phone", patientPhone);
			paramMap.put("type", 1);
			DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService.findByParam(
					"selectByDoctorsPhoneAndPatientPhone", paramMap);
			if (doctorsPatientLink_!=null) {
				results.setCode(MessageCode.CODE_405);
				results.setMessage("已经存在好友关系");
				return results;
			}
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("patientPhone", patientPhone);
			paraMap.put("fromDoctorsPhone", fromDoctorsPhone);
			paraMap.put("toDoctorsPhone", toDoctorsPhone);
			List<PatientReferral> patientReferralList = patientReferralService
					.getList("selectByFromPhoneAndToPhoneAndPatientPhone", paraMap);
			if (patientReferralList != null && patientReferralList.size() > 0) {
				PatientReferral patientReferral = patientReferralList.get(0);
				patientReferral.setUpdatetime(new Date());
				patientReferral.setMessage(message);
				patientReferral.setState(3);
				patientReferral.setReferraltime(new Date());
				patientReferralService.update("updateByPrimaryKey", patientReferral);

				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("messageType", "doctorReferral");
				ext.put("patientReferralId", patientReferral.getId());

				EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();

				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName = new UserName();
				userName.add(Constant.doctor + toDoctorsPhone);
				msg.from(Constant.doctor + fromDoctorsPhone).target(userName).targetType("users").msg(msgContent)
						.ext(ext);
				Object result = easemobSendMessage.sendMessage(msg);

//				System.out.println(result.toString());

				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				PatientReferral patientReferral = new PatientReferral();
				patientReferral.setCreatetime(new Date());
				patientReferral.setFromDoctorsLoginId(Constant.doctor + fromDoctorsPhone);
				patientReferral.setFromDoctorsName(fromDoctorsName);
				patientReferral.setFromDoctorsPhone(fromDoctorsPhone);
				patientReferral.setToDoctorsLoginId(Constant.doctor + toDoctorsPhone);
				patientReferral.setToDoctorsName(toDoctorsName);
				patientReferral.setToDoctorsPhone(toDoctorsPhone);
				patientReferral.setMessage(message);
				patientReferral.setPatientLoginId(Constant.patient + patientPhone);
				patientReferral.setPatientName(patientName);
				patientReferral.setPatientPhone(patientPhone);
				patientReferral.setReferraltime(new Date());
				patientReferral.setState(3);
				// patientReferral.setType(1);

				patientReferralService.insert(patientReferral);

				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("messageType", "doctorReferral");
				ext.put("patientReferralId", patientReferral.getId());

				EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();

				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName = new UserName();
				userName.add(Constant.doctor + toDoctorsPhone);
				msg.from(Constant.doctor + fromDoctorsPhone).target(userName).targetType("users").msg(msgContent)
						.ext(ext);
				Object result = easemobSendMessage.sendMessage(msg);

//				System.out.println(result.toString());

				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			}

		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		}

	}

	/**
	 * parameter={"keyword":""," fromDoctorsPhone":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "检索转诊医生")
	@RequestMapping({ "searchDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> searchDoctor(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String fromDoctorsPhone = dataJsonObject.getString("fromDoctorsPhone");

		if (StringUtils.isNotBlank(keyword)) {
			if (Toolkit.isMobileNO(keyword)) {// 如果关键字为电话号码
				String phone = keyword;
				// 根据电话去查询doctors_user
				DoctorsUser doctorsUser = doctorsUserService.findByParam("selectReferralDoctorByPhone", phone);
				if (doctorsUser != null) {
					if (doctorsUser.getPhone().equals(fromDoctorsPhone)) {// 不能返回自己
						results.setCode(MessageCode.CODE_405);
						results.setMessage(MessageCode.MESSAGE_405);
						return results;
					}
					
					if (2 == doctorsUser.getDoctortype()) {
						results.setCode(MessageCode.CODE_204);
						results.setMessage("不能转诊给家庭医生");
						return results;
					}
					
					doctorsUsersList.add(doctorsUser);
					map.put("doctorsUsersList", doctorsUsersList);
					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功");
					results.setData(map);
					return results;
				} else {// 未注册
					results.setCode(MessageCode.CODE_204);
					results.setMessage("找不到对应的医生");
					results.setData(map);
					return results;
				}
			} else {// 如果关键字是医生名字
				String name = keyword;
				// 根据名字去查询doctors_user，
				doctorsUsersList = doctorsUserService.getList("selectReferralDoctorByName", name);
				if (doctorsUsersList != null && doctorsUsersList.size() > 0) {
					for (int i = 0; i < doctorsUsersList.size(); i++) {
						if (doctorsUsersList.get(i).getPhone().equals(fromDoctorsPhone) || 2 == doctorsUsersList.get(i).getDoctortype()) {// 不能返回自己和家庭医生
							doctorsUsersList.remove(i);// 去掉自己信息
							if (doctorsUsersList.size() == 0) {
								results.setCode(MessageCode.CODE_204);
								results.setMessage("找不到对应的医生");
								return results;
							}
						}
					}
					map.put("doctorsUsersList", doctorsUsersList);
					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功");
					results.setData(map);
					return results;
				} else {// 未注册
					results.setCode(MessageCode.CODE_204);
					results.setMessage("找不到对应的医生");
					results.setData(map);
					return results;
				}
			}
		} else {
			String equipmentData = request.getParameter("equipmentData");
			JSONObject equipmentDataJson = JSONObject.parseObject(equipmentData);
			String phone = equipmentDataJson.getString("phone");
			doctorsUsersList = doctorsUserService.getList("selectAllReferralDoctorButMyself", phone);
			map.put("doctorsUsersList", doctorsUsersList);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功");
			results.setData(map);
			return results;
		}

	}

	/**
	 * parameter={"fromDoctorsPhone":"","toDoctorsPhone":"","patientPhone":
	 * ""} @Title: agreeOthersReferral @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "同意转入邀请")
	@RequestMapping(value = "agreeOthersReferral")
	@ResponseBody
	@Transactional(value = "transactionManager")
	public Results<Map<String, Object>> agreeOthersReferral(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromDoctorsPhone = dataJsonObject.getString("fromDoctorsPhone");
		String toDoctorsPhone = dataJsonObject.getString("toDoctorsPhone");
		String patientPhone = dataJsonObject.getString("patientPhone");

		if (StringUtils.isNotBlank(fromDoctorsPhone) && StringUtils.isNotBlank(toDoctorsPhone)
				&& StringUtils.isNotBlank(patientPhone)) {

			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("patientPhone", patientPhone);
			paraMap.put("fromDoctorsPhone", fromDoctorsPhone);
			paraMap.put("toDoctorsPhone", toDoctorsPhone);
			List<PatientReferral> patientReferralList = patientReferralService
					.getList("selectByFromPhoneAndToPhoneAndPatientPhone", paraMap);
			PatientReferral patientReferral = patientReferralList.get(0);

			if (patientReferral.getState() == 3) {// 判断是否重复操作
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("doctors_phone", toDoctorsPhone);
				paramMap.put("patient_phone", patientPhone);
				paramMap.put("doctorType", 1);
				DoctorsPatientLink doctorsPatientLink = doctorsPatientLinkService.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
				PatientDoctorsLink patientDoctorsLink = patientDoctorsLinkService.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);
				
				//判断是否已存在好友关系
				if (doctorsPatientLink != null && patientDoctorsLink != null 
						&& doctorsPatientLink.getType() == 1 && patientDoctorsLink.getType() == 1) {
					patientReferral.setState(1);
					patientReferral.setUpdatetime(new Date());
					patientReferralService.update("updateByPrimaryKeySelective", patientReferral);// 更新patient_referral表，为同意状态
					
					results.setCode(MessageCode.CODE_405);
					results.setMessage("重复操作");
					return results;
				}
				
				//获取相关用户信息
				PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
				DoctorsUser fromDoctorsUser = doctorsUserService.findByParam("selectByPhone", fromDoctorsPhone);
				DoctorsUser toDoctorsUser = doctorsUserService.findByParam("selectByPhone", toDoctorsPhone);
				String patientName = patientUser.getName();
				String patientLoginId = patientUser.getLoginId();
				String toDoctorsUserLoginId = toDoctorsUser.getLoginId();
				String toDoctorsName = toDoctorsUser.getName();
				
				// 如果转入的医生有小秘书，则找出转入的医生的小秘书
				SecretaryDoctorsLink secretaryDoctorsLink = secretaryDoctorsLinkService.findByParam("selectByDoctorsPhone", toDoctorsPhone);
				
				/**
				 * 先在环信建立新的群组
				 */
				EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
				UserName userName = new UserName();
				userName.add(Constant.doctor + toDoctorsPhone);
				userName.add(Constant.patient + patientPhone);
				if (secretaryDoctorsLink != null) userName.add(secretaryDoctorsLink.getSecretaryLoginId());
				String description = "问诊" + patientPhone;
				String chatroomName = patientName;
				Group group = new Group();
				group.groupname(chatroomName).desc(description)._public(true).maxusers(3).approval(false)
						.owner(toDoctorsUserLoginId).members(userName);
				Object result = easemobChatGroup.createChatGroup(group);// 在环信建立新的群组

				if (null == result) {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("环信操作失败");
					return results;

				}
				String chatroomId =null;
				JSONObject resultObject = JSONObject.parseObject(result.toString());
				// System.out.println(resultObject.get("entities"));
				String dataStr = resultObject.getString("data");
				JSONObject dataStrObject = JSONObject.parseObject(dataStr);
				chatroomId = dataStrObject.getString("groupid");
				System.out.println("chatroomId:" + chatroomId);
				
				if (!Toolkit.isNumeric(chatroomId)) {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("环信创建聊天室失败");
					return results;
				}
				
				//更新patient_referral表
				patientReferral.setState(1);
				patientReferral.setUpdatetime(new Date());
				patientReferralService.update("updateByPrimaryKeySelective", patientReferral);// 更新patient_referral表，为同意状态
				
				//保存或更新doctors_patient_link表
				if (doctorsPatientLink == null) {// 不存在好友关系
					DoctorsPatientLink doctorsPatientLink_ = new DoctorsPatientLink();
					doctorsPatientLink_.setChatroomId(chatroomId);
					doctorsPatientLink_.setCreatetime(new Date());
					doctorsPatientLink_.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
					doctorsPatientLink_.setDoctorsName(toDoctorsName);
					doctorsPatientLink_.setDoctorsPhone(toDoctorsPhone);
					doctorsPatientLink_.setGroupName("未分组患者");
					doctorsPatientLink_.setPatientLoginId(patientLoginId);
					doctorsPatientLink_.setPatientName(patientName);
					doctorsPatientLink_.setPatientPhone(patientPhone);
					doctorsPatientLink_.setType(1);
					doctorsPatientLink_.setIsvip(2);// 非VIP
					doctorsPatientLink_.setDoctortype(1);

					doctorsPatientLinkService.insert(doctorsPatientLink_);
				} else {
					doctorsPatientLink.setChatroomId(chatroomId);
					doctorsPatientLink.setType(1);
					doctorsPatientLink.setUpdatetime(new Date());
					doctorsPatientLinkService.update("updateByPrimaryKeySelective", doctorsPatientLink);
				}
				
				//保存或更新patient_doctors_link表
				if (patientDoctorsLink == null) {// 不存在好友关系
					PatientDoctorsLink patientDoctorsLink_ = new PatientDoctorsLink();
					patientDoctorsLink_.setChatroomId(chatroomId);
					patientDoctorsLink_.setCreatetime(new Date());
					patientDoctorsLink_.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
					patientDoctorsLink_.setDoctorsName(toDoctorsName);
					patientDoctorsLink_.setDoctorsPhone(toDoctorsPhone);
					patientDoctorsLink_.setPatientLoginId(patientLoginId);
					patientDoctorsLink_.setPatientName(patientName);
					patientDoctorsLink_.setPatientPhone(patientPhone);
					patientDoctorsLink_.setType(1);
					patientDoctorsLink_.setDoctortype(1);

					patientDoctorsLinkService.insert(patientDoctorsLink_);
				} else {
					patientDoctorsLink.setChatroomId(chatroomId);
					patientDoctorsLink.setType(1);
					patientDoctorsLinkService.update("updateByPrimaryKeySelective", patientDoctorsLink);
				}
				
				//如果转入医生有小秘书，则更新小秘书相关表
				if (secretaryDoctorsLink != null) {
					String secretaryPhone = secretaryDoctorsLink.getSecretaryPhone();
					
					Map<String, Object> paramMap2 = new HashMap<String, Object>();
					paramMap2.put("patientPhone", patientPhone);
					paramMap2.put("secretaryPhone", secretaryPhone);
					List<SecretaryPatientLink> secretaryPatientLinkList = secretaryPatientLinkService
							.getList("selectBySecretaryPhoneAndPatientPhone", paramMap2);
					if (secretaryPatientLinkList == null || secretaryPatientLinkList.size() < 1) {
						SecretaryPatientLink secretaryPatientLink = new SecretaryPatientLink();
						secretaryPatientLink.setCreatetime(new Date());
						secretaryPatientLink.setPatientLoginId(Constant.patient + patientPhone);
						secretaryPatientLink.setPatientName(patientName);
						secretaryPatientLink.setPatientPhone(patientPhone);
						secretaryPatientLink.setSecretaryLoginId(secretaryDoctorsLink.getSecretaryLoginId());
						secretaryPatientLink.setSecretaryName(secretaryDoctorsLink.getSecretaryName());
						secretaryPatientLink.setSecretaryPhone(secretaryPhone);
						secretaryPatientLinkService.insert(secretaryPatientLink);
					}
					
					Map<String, Object> paramMap3 = new HashMap<String, Object>();
					paramMap3.put("doctorsPhone", toDoctorsPhone);
					paramMap3.put("patientPhone", patientPhone);
					paramMap3.put("secretaryPhone", secretaryPhone);
					List<SecretaryAndDoctorsAndPatientOfChatroom> secretaryAndDoctorsAndPatientOfChatroomList = secretaryAndDoctorsAndPatientOfChatroomService
							.getList("selectBySecretaryPhoneAndDoctorsPhoneAndPatientPhone", paramMap3);
					SecretaryAndDoctorsAndPatientOfChatroom secretaryAndDoctorsAndPatientOfChatroom ;
					if (secretaryAndDoctorsAndPatientOfChatroomList == null || secretaryAndDoctorsAndPatientOfChatroomList.size() < 1) {
						secretaryAndDoctorsAndPatientOfChatroom = new SecretaryAndDoctorsAndPatientOfChatroom();
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomDescription(description);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(chatroomId);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomMaxusers(3);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomName(chatroomName);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomOwner(Constant.secretary + secretaryPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setCreatetime(new Date());
						secretaryAndDoctorsAndPatientOfChatroom.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setDoctorsName(toDoctorsName);
						secretaryAndDoctorsAndPatientOfChatroom.setDoctorsPhone(toDoctorsPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setPatientLoginId(Constant.patient + patientPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setPatientName(patientName);
						secretaryAndDoctorsAndPatientOfChatroom.setPatientPhone(patientPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setRemark(null);
						secretaryAndDoctorsAndPatientOfChatroom.setSecretaryLoginId(Constant.secretary + secretaryPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setSecretaryName(secretaryDoctorsLink.getSecretaryName());
						secretaryAndDoctorsAndPatientOfChatroom.setSecretaryPhone(secretaryPhone);
						secretaryAndDoctorsAndPatientOfChatroomService.insert(secretaryAndDoctorsAndPatientOfChatroom);
					} else {
						secretaryAndDoctorsAndPatientOfChatroom = secretaryAndDoctorsAndPatientOfChatroomList.get(0);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(chatroomId);
						secretaryAndDoctorsAndPatientOfChatroomService.update("updateByPrimaryKeySelective", secretaryAndDoctorsAndPatientOfChatroom);
					}
				}
				
				/**
				 * 发送环信消息
				 */
				EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();
				System.out.println("发透传通知医生已同意--------------------------------");
				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("messageType", "doctorReferral");

				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName3 = new UserName();
				userName3.add(Constant.doctor + fromDoctorsPhone);
				msg.from(Constant.doctor + toDoctorsPhone).target(userName3).targetType("users").msg(msgContent).ext(ext);
				Object result6 = easemobSendMessage.sendMessage(msg);

//				System.out.println("发透传通知医生已同意-----end---------------------：" + result6.toString());

				System.out.println("发透传通知患者已同意--------------------------------");
				
				Map<String, Object> ext2 = new HashMap<String, Object>();
				ext2.put("messageType", "doctorReferral");
				ext2.put("fromDoctor_headUrl", fromDoctorsUser.getHeadUrl());
				ext2.put("fromDoctorName", fromDoctorsUser.getName());
				ext2.put("toDoctorName", toDoctorsUser.getName());
				ext2.put("toDoctor_headurl", toDoctorsUser.getHeadUrl());
				ext2.put("toDoctor_title", toDoctorsUser.getTitle());
				ext2.put("time", patientReferral.getReferraltime().getTime());

				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName4 = new UserName();
				userName4.add(Constant.patient + patientPhone);
				msg.from(Constant.doctor + toDoctorsPhone).target(userName4).targetType("users").msg(msgContent).ext(ext2);
				Object result7 = easemobSendMessage.sendMessage(msg);

//				System.out.println("发透传通知患者已同意----------end----------------：" + result6.toString());

				
				results.setData(resultsMap);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;

			} else {
				results.setCode(MessageCode.CODE_405);
				results.setMessage("重复操作");
				return results;
			}

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
	
	
//	@SystemControllerLog(description = "同意转入邀请")
//	@RequestMapping(value = "agreeOthersReferral")
//	@ResponseBody
//	@Transactional(value = "transactionManager")
//	public Results<Map<String, Object>> agreeOthersReferral(@RequestParam String parameter,
//			HttpServletRequest request) {
//		//
//		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
//		Map<String, Object> resultsMap = new HashMap<String, Object>();
//
//		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
//		String fromDoctorsPhone = dataJsonObject.getString("fromDoctorsPhone");
//		String toDoctorsPhone = dataJsonObject.getString("toDoctorsPhone");
//		String patientPhone = dataJsonObject.getString("patientPhone");
//
//		if (StringUtils.isNotBlank(fromDoctorsPhone) && StringUtils.isNotBlank(toDoctorsPhone)
//				&& StringUtils.isNotBlank(patientPhone)) {
//
//			Map<String, Object> paraMap = new HashMap<String, Object>();
//			paraMap.put("patientPhone", patientPhone);
//			paraMap.put("fromDoctorsPhone", fromDoctorsPhone);
//			paraMap.put("toDoctorsPhone", toDoctorsPhone);
//			List<PatientReferral> patientReferralList = patientReferralService
//					.getList("selectByFromPhoneAndToPhoneAndPatientPhone", paraMap);
//			PatientReferral patientReferral = patientReferralList.get(0);
//
//			if (patientReferral.getState() == 3) {// 判断是否重复操作
//
//				PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
//				String patientLoginId = patientUser.getLoginId();
//				String patientName = patientUser.getName();
//
//				Map<String, Object> paramMap = new HashMap<String, Object>();
//				paramMap.put("doctors_phone", toDoctorsPhone);
//				paramMap.put("patient_phone", patientPhone);
//				DoctorsPatientLink doctorsPatientLink = doctorsPatientLinkService
//						.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
//
//				DoctorsUser fromDoctorsUser = doctorsUserService.findByParam("selectByPhone", fromDoctorsPhone);
//				DoctorsUser toDoctorsUser = doctorsUserService.findByParam("selectByPhone", toDoctorsPhone);
//				String toDoctorsName=null;
//				String chatroomId =null;
//				
//				if (doctorsPatientLink == null) {// 不存在好友关系
//					// 找出转入的医生的小秘书
//					SecretaryDoctorsLink secretaryDoctorsLink2 = secretaryDoctorsLinkService
//							.findByParam("selectByDoctorsPhone", toDoctorsPhone);
//					String secretaryPhone2 = secretaryDoctorsLink2.getSecretaryPhone();
//					String secretaryLoginId2 = secretaryDoctorsLink2.getSecretaryLoginId();
//					String secretaryName2 = secretaryDoctorsLink2.getSecretaryName();
//					toDoctorsName = toDoctorsUser.getName();
//
//					//////////////// 环信操作
//					EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
//					// Object result =
//					// easemobChatGroup.deleteChatGroup(groupId); //
//					// 在环信删除旧的群组
//
//					EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
//					// Object result2 =
//					// easemobIMUsers.deleteFriendSingle(Constant.patient +
//					// patientPhone,
//					// Constant.doctor + fromDoctorsPhone);// 删除旧的好友关系
//					Object result3 = easemobIMUsers.addFriendSingle(Constant.patient + patientPhone,
//							Constant.doctor + toDoctorsPhone);// 增加新的好友关系
//
//					UserName userName = new UserName();
//					userName.add(Constant.doctor + toDoctorsPhone);
//					userName.add(Constant.patient + patientPhone);
//					userName.add(secretaryLoginId2);
//					String description = "问诊" + patientPhone;
//					String chatroomName = patientName;
//					Group group = new Group();
//					group.groupname(chatroomName).desc(description)._public(true).maxusers(3).approval(false)
//							.owner(secretaryLoginId2).members(userName);
//					Object result4 = easemobChatGroup.createChatGroup(group);// 在环信建立新的群组
//
//					if (result3 == null || result4 == null) {
//						results.setCode(MessageCode.CODE_501);
//						results.setMessage("环信操作失败");
//						return results;
//
//					}
//
//					JSONObject resultObject = JSONObject.parseObject(result4.toString());
//					// System.out.println(resultObject.get("entities"));
//					String dataStr = resultObject.getString("data");
//					JSONObject dataStrObject = JSONObject.parseObject(dataStr);
//					chatroomId = dataStrObject.getString("groupid");
//					System.out.println("chatroomId:" + chatroomId);
//
//					// doctorsPatientLinkService.delete("deleteByPrimaryKey",
//					// doctorsPatientLink);
//					// patientDoctorsLinkService.delete("deleteByPrimaryKey",
//					// patientDoctorsLink);
//					// secretaryAndDoctorsAndPatientOfChatroomService.delete("deleteByPrimaryKey",
//					// secretaryAndDoctorsAndPatientOfChatroom);
//					// secretaryPatientLinkService.delete("deleteByPrimaryKey",
//					// secretaryPatientLink);
//
//					DoctorsPatientLink doctorsPatientLink_ = new DoctorsPatientLink();
//					doctorsPatientLink_.setChatroomId(chatroomId);
//					doctorsPatientLink_.setCreatetime(new Date());
//					doctorsPatientLink_.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
//					doctorsPatientLink_.setDoctorsName(toDoctorsName);
//					doctorsPatientLink_.setDoctorsPhone(toDoctorsPhone);
//					doctorsPatientLink_.setGroupName("未分组患者");
//					doctorsPatientLink_.setPatientLoginId(patientLoginId);
//					doctorsPatientLink_.setPatientName(patientName);
//					doctorsPatientLink_.setPatientPhone(patientPhone);
//					doctorsPatientLink_.setType(1);
//					doctorsPatientLink_.setIsvip(2);// 非VIP
//					doctorsPatientLink_.setDoctortype(1);
//
//					doctorsPatientLinkService.insert(doctorsPatientLink_);
//
//					PatientDoctorsLink patientDoctorsLink_ = new PatientDoctorsLink();
//					patientDoctorsLink_.setChatroomId(chatroomId);
//					patientDoctorsLink_.setCreatetime(new Date());
//					patientDoctorsLink_.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
//					patientDoctorsLink_.setDoctorsName(toDoctorsName);
//					patientDoctorsLink_.setDoctorsPhone(toDoctorsPhone);
//					patientDoctorsLink_.setPatientLoginId(patientLoginId);
//					patientDoctorsLink_.setPatientName(patientName);
//					patientDoctorsLink_.setPatientPhone(patientPhone);
//					patientDoctorsLink_.setType(1);
//					patientDoctorsLink_.setDoctortype(1);
//
//					patientDoctorsLinkService.insert(patientDoctorsLink_);
//
//					SecretaryAndDoctorsAndPatientOfChatroom secretaryAndDoctorsAndPatientOfChatroom_ = new SecretaryAndDoctorsAndPatientOfChatroom();
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomDescription(description);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomId(chatroomId);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomMaxusers(3);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomName(chatroomName);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomOwner(secretaryLoginId2);
//					secretaryAndDoctorsAndPatientOfChatroom_.setCreatetime(new Date());
//					secretaryAndDoctorsAndPatientOfChatroom_.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
//					secretaryAndDoctorsAndPatientOfChatroom_.setDoctorsName(toDoctorsName);
//					secretaryAndDoctorsAndPatientOfChatroom_.setDoctorsPhone(toDoctorsPhone);
//					secretaryAndDoctorsAndPatientOfChatroom_.setPatientLoginId(patientLoginId);
//					secretaryAndDoctorsAndPatientOfChatroom_.setPatientName(patientName);
//					secretaryAndDoctorsAndPatientOfChatroom_.setPatientPhone(patientPhone);
//					secretaryAndDoctorsAndPatientOfChatroom_.setRemark(null);
//					secretaryAndDoctorsAndPatientOfChatroom_.setSecretaryLoginId(secretaryLoginId2);
//					secretaryAndDoctorsAndPatientOfChatroom_.setSecretaryName(secretaryName2);
//					secretaryAndDoctorsAndPatientOfChatroom_.setSecretaryPhone(secretaryPhone2);
//
//					secretaryAndDoctorsAndPatientOfChatroomService.insert(secretaryAndDoctorsAndPatientOfChatroom_);
//
//					
//					Map<String, Object> paramMap2 = new HashMap<String, Object>();
//					paramMap2.put("patientPhone", patientPhone);
//					paramMap2.put("secretaryPhone", secretaryPhone2);
//					List<SecretaryPatientLink> secretaryPatientLinkList = secretaryPatientLinkService
//							.getList("selectBySecretaryPhoneAndPatientPhone", paramMap2);
//					if (secretaryPatientLinkList == null || secretaryPatientLinkList.size() < 1) {
//						SecretaryPatientLink secretaryPatientLink_ = new SecretaryPatientLink();
//						secretaryPatientLink_.setCreatetime(new Date());
//						secretaryPatientLink_.setPatientLoginId(patientLoginId);
//						secretaryPatientLink_.setPatientName(patientName);
//						secretaryPatientLink_.setPatientPhone(patientPhone);
//						secretaryPatientLink_.setSecretaryLoginId(secretaryLoginId2);
//						secretaryPatientLink_.setSecretaryName(secretaryName2);
//						secretaryPatientLink_.setSecretaryPhone(secretaryPhone2);
//
//						secretaryPatientLinkService.insert(secretaryPatientLink_);
//					}
//					
//
//					patientReferral.setState(1);
//					patientReferral.setUpdatetime(new Date());
//					patientReferralService.update("updateByPrimaryKey", patientReferral);// 更新patient_referral表，为同意状态
//
//				} else if (doctorsPatientLink.getType() == 2 || doctorsPatientLink.getType() == 3) {
//					// 找出转入的医生的小秘书
//					SecretaryDoctorsLink secretaryDoctorsLink2 = secretaryDoctorsLinkService
//							.findByParam("selectByDoctorsPhone", toDoctorsPhone);
//					String secretaryPhone2 = secretaryDoctorsLink2.getSecretaryPhone();
//					String secretaryLoginId2 = secretaryDoctorsLink2.getSecretaryLoginId();
//					String secretaryName2 = secretaryDoctorsLink2.getSecretaryName();
//					toDoctorsName = toDoctorsUser.getName();
//
//					//////////////// 环信操作
//					EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
//					// Object result =
//					// easemobChatGroup.deleteChatGroup(groupId); //
//					// 在环信删除旧的群组
//
//					EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
//					// Object result2 =
//					// easemobIMUsers.deleteFriendSingle(Constant.patient +
//					// patientPhone,
//					// Constant.doctor + fromDoctorsPhone);// 删除旧的好友关系
//					Object result3 = easemobIMUsers.addFriendSingle(Constant.patient + patientPhone,
//							Constant.doctor + toDoctorsPhone);// 增加新的好友关系
//
//					UserName userName = new UserName();
//					userName.add(Constant.doctor + toDoctorsPhone);
//					userName.add(Constant.patient + patientPhone);
//					userName.add(secretaryLoginId2);
//					String description = "问诊" + patientPhone;
//					String chatroomName = patientName;
//					Group group = new Group();
//					group.groupname(chatroomName).desc(description)._public(true).maxusers(3).approval(false)
//							.owner(secretaryLoginId2).members(userName);
//					Object result4 = easemobChatGroup.createChatGroup(group);// 在环信建立新的群组
//
//					if (result3 == null || result4 == null) {
//						results.setCode(MessageCode.CODE_501);
//						results.setMessage("环信操作失败");
//						return results;
//
//					}
//
//					JSONObject resultObject = JSONObject.parseObject(result4.toString());
//					// System.out.println(resultObject.get("entities"));
//					String dataStr = resultObject.getString("data");
//					JSONObject dataStrObject = JSONObject.parseObject(dataStr);
//					chatroomId = dataStrObject.getString("groupid");
//					System.out.println("chatroomId:" + chatroomId);
//
//					// doctorsPatientLinkService.delete("deleteByPrimaryKey",
//					// doctorsPatientLink);
//					// patientDoctorsLinkService.delete("deleteByPrimaryKey",
//					// patientDoctorsLink);
//					// secretaryAndDoctorsAndPatientOfChatroomService.delete("deleteByPrimaryKey",
//					// secretaryAndDoctorsAndPatientOfChatroom);
//					// secretaryPatientLinkService.delete("deleteByPrimaryKey",
//					// secretaryPatientLink);
//
//					doctorsPatientLink.setChatroomId(chatroomId);
//					doctorsPatientLink.setType(1);
//					doctorsPatientLink.setUpdatetime(new Date());
//					doctorsPatientLinkService.update("updateByPrimaryKeySelective", doctorsPatientLink);
//
//					Map<String, Object> map_ = new HashMap<String, Object>();
//					map_.put("doctors_phone", toDoctorsPhone);
//					map_.put("patient_phone", patientPhone);
//					PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService
//							.findByParam("selectByPatientPhoneAndDoctorsPhone", map_);
//					patientDoctorsLink_.setChatroomId(chatroomId);
//					patientDoctorsLink_.setType(1);
//					patientDoctorsLinkService.update("updateByPrimaryKeySelective", patientDoctorsLink_);
//
//					SecretaryAndDoctorsAndPatientOfChatroom secretaryAndDoctorsAndPatientOfChatroom_ = new SecretaryAndDoctorsAndPatientOfChatroom();
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomDescription(description);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomId(chatroomId);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomMaxusers(3);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomName(chatroomName);
//					secretaryAndDoctorsAndPatientOfChatroom_.setChatroomOwner(secretaryLoginId2);
//					secretaryAndDoctorsAndPatientOfChatroom_.setCreatetime(new Date());
//					secretaryAndDoctorsAndPatientOfChatroom_.setDoctorsLoginId(Constant.doctor + toDoctorsPhone);
//					secretaryAndDoctorsAndPatientOfChatroom_.setDoctorsName(toDoctorsName);
//					secretaryAndDoctorsAndPatientOfChatroom_.setDoctorsPhone(toDoctorsPhone);
//					secretaryAndDoctorsAndPatientOfChatroom_.setPatientLoginId(patientLoginId);
//					secretaryAndDoctorsAndPatientOfChatroom_.setPatientName(patientName);
//					secretaryAndDoctorsAndPatientOfChatroom_.setPatientPhone(patientPhone);
//					secretaryAndDoctorsAndPatientOfChatroom_.setRemark(null);
//					secretaryAndDoctorsAndPatientOfChatroom_.setSecretaryLoginId(secretaryLoginId2);
//					secretaryAndDoctorsAndPatientOfChatroom_.setSecretaryName(secretaryName2);
//					secretaryAndDoctorsAndPatientOfChatroom_.setSecretaryPhone(secretaryPhone2);
//
//					secretaryAndDoctorsAndPatientOfChatroomService.insert(secretaryAndDoctorsAndPatientOfChatroom_);
//
//					
//					Map<String, Object> paramMap2 = new HashMap<String, Object>();
//					paramMap2.put("patientPhone", patientPhone);
//					paramMap2.put("secretaryPhone", secretaryPhone2);
//					List<SecretaryPatientLink> secretaryPatientLinkList = secretaryPatientLinkService
//							.getList("selectBySecretaryPhoneAndPatientPhone", paramMap2);
//					if (secretaryPatientLinkList == null || secretaryPatientLinkList.size() < 1) {
//						SecretaryPatientLink secretaryPatientLink_ = new SecretaryPatientLink();
//						secretaryPatientLink_.setCreatetime(new Date());
//						secretaryPatientLink_.setPatientLoginId(patientLoginId);
//						secretaryPatientLink_.setPatientName(patientName);
//						secretaryPatientLink_.setPatientPhone(patientPhone);
//						secretaryPatientLink_.setSecretaryLoginId(secretaryLoginId2);
//						secretaryPatientLink_.setSecretaryName(secretaryName2);
//						secretaryPatientLink_.setSecretaryPhone(secretaryPhone2);
//
//						secretaryPatientLinkService.insert(secretaryPatientLink_);
//					}
//					
//
//					patientReferral.setState(1);
//					patientReferral.setUpdatetime(new Date());
//					patientReferralService.update("updateByPrimaryKey", patientReferral);// 更新patient_referral表，为同意状态
//
//				} else {// 已经存在好友关系或不再提醒
//					results.setCode(MessageCode.CODE_502);
//					results.setMessage(MessageCode.MESSAGE_300);
//					return results;
//				}
//				
//				//发送环信消息
//				SecretaryDoctorsLink secretaryDoctorsLink2 = secretaryDoctorsLinkService
//						.findByParam("selectByDoctorsPhone", toDoctorsPhone);
//				String secretaryLoginId2 = secretaryDoctorsLink2.getSecretaryLoginId();
//				String secretaryName2 = secretaryDoctorsLink2.getSecretaryName();
//
//				System.out.println("发送消息--------------------------");
//				EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//				Msg msg = new Msg();
//				MsgContent msgContent = new MsgContent();
//
//				Map<String, Object> ext_ = new HashMap<String, Object>();
//				ext_.put("nickName", "医生助理-"+secretaryName2);
//				ext_.put("headUrl", Config.secretaryIMG);
//				
//				msgContent.type(MsgContent.TypeEnum.TXT).msg(patientName + ",您好。我是"
//						+ toDoctorsName + "的助理" + secretaryName2 + ",治疗期间有任何问题，可以随时联系我。");
//				UserName userName2 = new UserName();
//				userName2.add(chatroomId);
//				msg.from(secretaryLoginId2).target(userName2).targetType(HttpToEasemobUtil.TARGETTYPE_CHATGROUPS)
//						.msg(msgContent).ext(ext_);
//				Object result5 = easemobSendMessage.sendMessage(msg);
////				System.out.println("发送消息----------end---------------：" + result5.toString());
//
//				System.out.println("发透传通知医生已同意--------------------------------");
//				Map<String, Object> ext = new HashMap<String, Object>();
//				ext.put("messageType", "doctorReferral");
//
//				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
//				UserName userName3 = new UserName();
//				userName3.add(Constant.doctor + fromDoctorsPhone);
//				msg.from(Constant.doctor + toDoctorsPhone).target(userName3).targetType("users").msg(msgContent)
//						.ext(ext);
//				Object result6 = easemobSendMessage.sendMessage(msg);
//
////				System.out.println("发透传通知医生已同意-----end---------------------：" + result6.toString());
//
//				System.out.println("发透传通知患者已同意--------------------------------");
//				
//				Map<String, Object> ext2 = new HashMap<String, Object>();
//				ext2.put("messageType", "doctorReferral");
//				ext2.put("fromDoctor_headUrl", fromDoctorsUser.getHeadUrl());
//				ext2.put("fromDoctorName", fromDoctorsUser.getName());
//				ext2.put("toDoctorName", toDoctorsUser.getName());
//				ext2.put("toDoctor_headurl", toDoctorsUser.getHeadUrl());
//				ext2.put("toDoctor_title", toDoctorsUser.getTitle());
//				ext2.put("time", patientReferral.getReferraltime().getTime());
//
//				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
//				UserName userName4 = new UserName();
//				userName4.add(Constant.patient + patientPhone);
//				msg.from(Constant.doctor + toDoctorsPhone).target(userName4).targetType("users").msg(msgContent)
//						.ext(ext2);
//				Object result7 = easemobSendMessage.sendMessage(msg);
//
////				System.out.println("发透传通知患者已同意----------end----------------：" + result6.toString());
//
//				results.setData(resultsMap);
//				results.setCode(MessageCode.CODE_200);
//				results.setMessage(MessageCode.MESSAGE_200);
//				return results;
//
//			} else {
//				results.setCode(MessageCode.CODE_405);
//				results.setMessage("重复操作");
//				return results;
//			}
//
//		} else {
//			results.setCode(MessageCode.CODE_201);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//
//	}

}
