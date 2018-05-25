/**   
 * @Title: DoctorsPatientLinkController.java 
 * @Package com.cmcc.medicalcare.controller.app 
 * @Description: TODO
 * @author adminstrator   
 * @date 2017年4月10日 上午10:35:30 
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.app.patient.component.HealthRecordDESUtil;
import com.cmcc.medicalcare.controller.app.patient.component.MedicalRecordDESUtil;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientBloodPressure;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientHealthRecord;
import com.cmcc.medicalcare.model.PatientMedicalRecord;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.model.SecretaryPatientLink;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientBloodPressureService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientHealthRecordService;
import com.cmcc.medicalcare.service.IPatientMedicalRecordService;
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
import net.sf.json.JSONArray;

/**
 * @ClassName: 医生的患者
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:35:30
 * 
 */
@Controller
@RequestMapping("/app/doctorsPatientLink")
public class DoctorsPatientLinkController {

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private IPatientUserService patientUserService;

	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private ISecretaryPatientLinkService secretaryPatientLinkService;

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private IPatientMedicalRecordService patientMedicalRecordService;

	@Resource
	private IPatientHealthRecordService patientHealthRecordService;

	@Resource
	private IPatientBloodPressureService patientBloodPressureService;

	@Resource
	private IDoctorsUserService doctorsUserService;

	/**
	 * 
	 * @Title: agreeReqeustJoinning @Description: TODO @param @param
	 *         doctorsPatientLink @param @param request @param @return
	 *         设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生同意患者添加好友请求")
	@RequestMapping(value = "agreeReqeustJoinning")
	@ResponseBody
	@Transactional
	public Results<String> agreeReqeustJoinning(@RequestParam String doctorsPatientLink, HttpServletRequest request) {
		// 添加記錄doctors_patient_link，最後調用環信接口通知患者已同意，和調用環信接口通知秘書端有新患者加入
		Results<String> results = new Results<String>();

		JSONObject doctorsUserObject = JSONObject.parseObject(doctorsPatientLink);
		String doctorsPhone = doctorsUserObject.getString("doctorsPhone");
		String patientPhone = doctorsUserObject.getString("patientPhone");
		if (StringUtils.isNotBlank(doctorsPhone) && StringUtils.isNotBlank(patientPhone)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("patient_phone", patientPhone);
			paramMap.put("type", 3);
			DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService
					.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
			PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService
					.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);

			if (doctorsPatientLink_ == null || patientDoctorsLink_ == null) {
				results.setCode(MessageCode.CODE_204);
				results.setMessage(MessageCode.MESSAGE_204);
				return results;
			}

			// 如果医生有小秘书，则找出医生的小秘书
			SecretaryDoctorsLink secretaryDoctorsLink = secretaryDoctorsLinkService.findByParam("selectByDoctorsPhone",
					doctorsPhone);

			String chatroomId = null;
			String description = "问诊" + patientPhone;
			String chatroomName = doctorsPatientLink_.getPatientName();

			/**
			 * 调用环信接口创建群组
			 */
			try {
				EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
				if (!StringUtils.isEmpty(doctorsPatientLink_.getChatroomId())) {
					easemobChatGroup.deleteChatGroup(doctorsPatientLink_.getChatroomId());
				}
				if (!StringUtils.isEmpty(patientDoctorsLink_.getChatroomId())) {
					easemobChatGroup.deleteChatGroup(patientDoctorsLink_.getChatroomId());
				}

				// EasemobChatRoom easemobChatRoom = new EasemobChatRoom();
				UserName userName = new UserName();
				userName.add(Constant.doctor + doctorsPhone);
				userName.add(Constant.patient + patientPhone);
				if (secretaryDoctorsLink != null)
					userName.add(secretaryDoctorsLink.getSecretaryLoginId());

				Group group = new Group();
				group.groupname(chatroomName).desc(description)._public(true).maxusers(3).approval(false)
						.owner(Constant.doctor + doctorsPhone).members(userName);
				Object result_ = easemobChatGroup.createChatGroup(group);
				if (result_ == null) {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("环信创建群组失败");
					return results;
				}
				JSONObject resultObject = JSONObject.parseObject(result_.toString());
				// System.out.println(resultObject.get("entities"));
				String dataStr = resultObject.getString("data");
				JSONObject dataStrObject = JSONObject.parseObject(dataStr);
				chatroomId = dataStrObject.getString("groupid");
				System.out.println("chatroomId:" + chatroomId);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (Toolkit.isNumeric(chatroomId)) {
				doctorsPatientLink_.setType(1);
				doctorsPatientLink_.setIsvip(2);// 非VIP
				doctorsPatientLink_.setChatroomId(chatroomId);
				patientDoctorsLink_.setType(1);
				patientDoctorsLink_.setChatroomId(chatroomId);
				doctorsPatientLinkService.update("updateByPrimaryKeySelective", doctorsPatientLink_);
				patientDoctorsLinkService.update("updateByPrimaryKeySelective", patientDoctorsLink_);

				// 如果医生有小秘书，则更新小秘书相关表
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
						secretaryPatientLink.setPatientName(doctorsPatientLink_.getPatientName());
						secretaryPatientLink.setPatientPhone(patientPhone);
						secretaryPatientLink.setSecretaryLoginId(secretaryDoctorsLink.getSecretaryLoginId());
						secretaryPatientLink.setSecretaryName(secretaryDoctorsLink.getSecretaryName());
						secretaryPatientLink.setSecretaryPhone(secretaryPhone);
						secretaryPatientLinkService.insert(secretaryPatientLink);
					}

					Map<String, Object> paramMap3 = new HashMap<String, Object>();
					paramMap3.put("doctorsPhone", doctorsPhone);
					paramMap3.put("patientPhone", patientPhone);
					paramMap3.put("secretaryPhone", secretaryPhone);
					List<SecretaryAndDoctorsAndPatientOfChatroom> secretaryAndDoctorsAndPatientOfChatroomList = secretaryAndDoctorsAndPatientOfChatroomService
							.getList("selectBySecretaryPhoneAndDoctorsPhoneAndPatientPhone", paramMap3);
					SecretaryAndDoctorsAndPatientOfChatroom secretaryAndDoctorsAndPatientOfChatroom;
					if (secretaryAndDoctorsAndPatientOfChatroomList == null
							|| secretaryAndDoctorsAndPatientOfChatroomList.size() < 1) {
						secretaryAndDoctorsAndPatientOfChatroom = new SecretaryAndDoctorsAndPatientOfChatroom();
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomDescription(description);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(chatroomId);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomMaxusers(3);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomName(chatroomName);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomOwner(Constant.secretary + secretaryPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setCreatetime(new Date());
						secretaryAndDoctorsAndPatientOfChatroom.setDoctorsLoginId(Constant.doctor + doctorsPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setDoctorsName(doctorsPatientLink_.getDoctorsName());
						secretaryAndDoctorsAndPatientOfChatroom.setDoctorsPhone(doctorsPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setPatientLoginId(Constant.patient + patientPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setPatientName(doctorsPatientLink_.getPatientName());
						secretaryAndDoctorsAndPatientOfChatroom.setPatientPhone(patientPhone);
						secretaryAndDoctorsAndPatientOfChatroom.setRemark(null);
						secretaryAndDoctorsAndPatientOfChatroom
								.setSecretaryLoginId(Constant.secretary + secretaryPhone);
						secretaryAndDoctorsAndPatientOfChatroom
								.setSecretaryName(secretaryDoctorsLink.getSecretaryName());
						secretaryAndDoctorsAndPatientOfChatroom.setSecretaryPhone(secretaryPhone);
						secretaryAndDoctorsAndPatientOfChatroomService.insert(secretaryAndDoctorsAndPatientOfChatroom);
					} else {
						secretaryAndDoctorsAndPatientOfChatroom = secretaryAndDoctorsAndPatientOfChatroomList.get(0);
						secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(chatroomId);
						secretaryAndDoctorsAndPatientOfChatroomService.update("updateByPrimaryKeySelective",
								secretaryAndDoctorsAndPatientOfChatroom);
					}
				}

				Map<String, Object> ext_ = new HashMap<String, Object>();
				ext_.put("doctorType", doctorsPatientLink_.getDoctortype());
				ext_.put("messageType", "agreeReqeustJoinning");
				ext_.put("nickName", doctorsPatientLink_.getDoctorsName());

				EasemobSendMessage easemobSendMessage_ = new EasemobSendMessage();
				Msg msg_ = new Msg();
				MsgContent msgContent_ = new MsgContent();
				msgContent_.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName_ = new UserName();
				userName_.add(doctorsPatientLink_.getPatientLoginId());
				msg_.from(doctorsPatientLink_.getDoctorsLoginId()).target(userName_)
						.targetType(HttpToEasemobUtil.TARGETTYPE_USERS).msg(msgContent_).ext(ext_);
				Object result_ = easemobSendMessage_.sendMessage(msg_);

				results.setCode(200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("环信创建聊天室失败");
				return results;
			}

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	// @SystemControllerLog(description = "医生同意患者添加好友请求")
	// @RequestMapping(value = "agreeReqeustJoinning")
	// @ResponseBody
	// @Transactional
	// public Results<String> agreeReqeustJoinning(@RequestParam String
	// doctorsPatientLink,
	// HttpServletRequest request) {
	// // 添加記錄doctors_patient_link，最後調用環信接口通知患者已同意，和調用環信接口通知秘書端有新患者加入
	// Results<String> results = new Results<String>();
	// JSONObject doctorsUserObject =
	// JSONObject.parseObject(doctorsPatientLink);
	// // String patientName = doctorsUserObject.getString("patientName");
	// System.out.println(doctorsPatientLink);
	// if (doctorsUserObject != null) {
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	// paramMap.put("doctors_phone",
	// doctorsUserObject.getString("doctorsPhone"));
	// paramMap.put("patient_phone",
	// doctorsUserObject.getString("patientPhone"));
	// DoctorsPatientLink doctorsPatientLink_ =
	// doctorsPatientLinkService.findByParam(
	// "selectByDoctorsPhoneAndPatientPhone", paramMap);
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("doctors_phone", doctorsUserObject.getString("doctorsPhone"));
	// map.put("patient_phone", doctorsUserObject.getString("patientPhone"));
	// PatientDoctorsLink patientDoctorsLink_ =
	// patientDoctorsLinkService.findByParam(
	// "selectByPatientPhoneAndDoctorsPhone", map);
	//
	// // 找出医生的小秘书
	// SecretaryDoctorsLink secretaryDoctorsLink =
	// secretaryDoctorsLinkService.findByParam(
	// "selectByDoctorsPhone", doctorsUserObject.getString("doctorsPhone"));
	// String secretaryPhone = secretaryDoctorsLink.getSecretaryPhone();
	//
	// Map<String, Object> paramMap2 = new HashMap<String, Object>();
	// paramMap2.put("patientPhone",
	// doctorsUserObject.getString("patientPhone"));
	// paramMap2.put("secretaryPhone", secretaryPhone);
	// List<SecretaryPatientLink> secretaryPatientLinkList =
	// secretaryPatientLinkService
	// .getList("selectBySecretaryPhoneAndPatientPhone", paramMap2);
	//
	// Map<String, Object> paramMap3 = new HashMap<String, Object>();
	// paramMap3.put("doctorsPhone",
	// doctorsUserObject.getString("doctorsPhone"));
	// paramMap3.put("patientPhone",
	// doctorsUserObject.getString("patientPhone"));
	// paramMap3.put("secretaryPhone", secretaryPhone);
	// List<SecretaryAndDoctorsAndPatientOfChatroom>
	// secretaryAndDoctorsAndPatientOfChatroomList =
	// secretaryAndDoctorsAndPatientOfChatroomService
	// .getList("selectBySecretaryPhoneAndDoctorsPhoneAndPatientPhone",
	// paramMap3);
	//
	// if (doctorsPatientLink_ == null || patientDoctorsLink_ == null) {
	// results.setCode(MessageCode.CODE_404);
	// results.setMessage(MessageCode.MESSAGE_404);
	// return results;
	// }
	//
	// /**
	// * 调用环信接口添加好友
	// */
	// String isSuccess = null;
	// try {
	// EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
	// Object result = easemobIMUsers.addFriendSingle(
	// Constant.patient + doctorsUserObject.getString("patientPhone"),
	// Constant.doctor + doctorsUserObject.getString("doctorsPhone"));
	//
	//// System.out.println("添加好友====" + result.toString());
	// if(result==null){
	// results.setCode(MessageCode.CODE_501);
	// results.setMessage("环信添加好友失败1");
	// return results;
	// }
	// JSONObject dataJsonObject = JSONObject.parseObject(result.toString());
	// JSONArray entities =
	// JSONArray.fromObject(dataJsonObject.get("entities"));
	// String entity = entities.get(0).toString();
	// JSONObject entityObject = JSONObject.parseObject(entity);
	// isSuccess = entityObject.getString("activated");
	// System.out.println("isSuccess:" + isSuccess);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// if (isSuccess != null && "true".equalsIgnoreCase(isSuccess)) {
	// String chatroomId = null;
	// String description = "问诊" + doctorsUserObject.getString("patientPhone");
	// String chatroomName = doctorsPatientLink_.getPatientName();
	// /**
	// * 调用环信接口创建群组
	// */
	// try {
	// EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
	// if(!StringUtils.isEmpty(doctorsPatientLink_.getChatroomId())){
	// easemobChatGroup.deleteChatGroup(doctorsPatientLink_.getChatroomId());
	// }
	// if(!StringUtils.isEmpty(patientDoctorsLink_.getChatroomId())){
	// easemobChatGroup.deleteChatGroup(patientDoctorsLink_.getChatroomId());
	// }
	//
	// // EasemobChatRoom easemobChatRoom = new EasemobChatRoom();
	// UserName userName = new UserName();
	// userName.add(Constant.doctor +
	// doctorsUserObject.getString("doctorsPhone"));
	// userName.add(Constant.patient +
	// doctorsUserObject.getString("patientPhone"));
	// userName.add(Constant.secretary + secretaryPhone);
	// // Chatroom chatroom = new Chatroom();
	// //
	// chatroom.description(description).maxusers(3).members(userName).name(chatroomName)
	// // .owner(Constant.scretary + scretaryPhone);
	// // Object result_ =
	// // easemobChatRoom.createChatRoom(chatroom);
	// System.out.println("chatroomName::" + chatroomName);
	// System.out.println("description::" + description);
	// System.out.println("secretaryLoginId::" + Constant.secretary +
	// secretaryPhone);
	// System.out.println("doctorLoginId::" + Constant.doctor
	// + doctorsUserObject.getString("doctorsPhone"));
	// System.out.println("patientLoginId::" + Constant.patient
	// + doctorsUserObject.getString("patientPhone"));
	//
	// Group group = new Group();
	// group.groupname(chatroomName).desc(description)._public(true).maxusers(3)
	// .approval(false).owner(Constant.secretary + secretaryPhone)
	// .members(userName);
	// Object result_ = easemobChatGroup.createChatGroup(group);
	//// System.out.println("群组===" + result_.toString());
	// if(result_==null){
	// results.setCode(MessageCode.CODE_501);
	// results.setMessage("环信创建群组失败");
	// return results;
	// }
	// JSONObject resultObject = JSONObject.parseObject(result_.toString());
	// // System.out.println(resultObject.get("entities"));
	// String dataStr = resultObject.getString("data");
	// JSONObject dataStrObject = JSONObject.parseObject(dataStr);
	// chatroomId = dataStrObject.getString("groupid");
	// System.out.println("chatroomId:" + chatroomId);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// if (Toolkit.isNumeric(chatroomId)) {
	//
	// doctorsPatientLink_.setType(1);
	// doctorsPatientLink_.setIsvip(2);// 非VIP
	// doctorsPatientLink_.setChatroomId(chatroomId);
	// patientDoctorsLink_.setType(1);
	// patientDoctorsLink_.setChatroomId(chatroomId);
	// doctorsPatientLinkService.update("updateByPrimaryKeySelective",
	// doctorsPatientLink_);
	// patientDoctorsLinkService.update("updateByPrimaryKeySelective",
	// patientDoctorsLink_);
	//
	// if (secretaryPatientLinkList == null || secretaryPatientLinkList.size() <
	// 1) {
	// SecretaryPatientLink secretaryPatientLink = new SecretaryPatientLink();
	// secretaryPatientLink.setCreatetime(new Date());
	// secretaryPatientLink.setPatientLoginId(Constant.patient
	// + doctorsUserObject.getString("patientPhone"));
	// secretaryPatientLink.setPatientName(doctorsPatientLink_.getPatientName());
	// secretaryPatientLink.setPatientPhone(doctorsUserObject
	// .getString("patientPhone"));
	// secretaryPatientLink.setSecretaryLoginId(secretaryDoctorsLink.getSecretaryLoginId());
	// secretaryPatientLink.setSecretaryName(secretaryDoctorsLink.getSecretaryName());
	// secretaryPatientLink.setSecretaryPhone(secretaryPhone);
	// secretaryPatientLinkService.insert(secretaryPatientLink);
	// }
	// SecretaryAndDoctorsAndPatientOfChatroom
	// secretaryAndDoctorsAndPatientOfChatroom ;
	// if (secretaryAndDoctorsAndPatientOfChatroomList == null ||
	// secretaryAndDoctorsAndPatientOfChatroomList.size() < 1) {
	// secretaryAndDoctorsAndPatientOfChatroom = new
	// SecretaryAndDoctorsAndPatientOfChatroom();
	// secretaryAndDoctorsAndPatientOfChatroom.setChatroomDescription(description);
	// secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(chatroomId);
	// secretaryAndDoctorsAndPatientOfChatroom.setChatroomMaxusers(3);
	// secretaryAndDoctorsAndPatientOfChatroom.setChatroomName(chatroomName);
	// secretaryAndDoctorsAndPatientOfChatroom.setChatroomOwner(Constant.secretary
	// + secretaryPhone);
	// secretaryAndDoctorsAndPatientOfChatroom.setCreatetime(new Date());
	// secretaryAndDoctorsAndPatientOfChatroom.setDoctorsLoginId(Constant.doctor
	// + doctorsUserObject.getString("doctorsPhone"));
	// secretaryAndDoctorsAndPatientOfChatroom.setDoctorsName(doctorsPatientLink_
	// .getDoctorsName());
	// secretaryAndDoctorsAndPatientOfChatroom.setDoctorsPhone(doctorsUserObject
	// .getString("doctorsPhone"));
	// secretaryAndDoctorsAndPatientOfChatroom.setPatientLoginId(Constant.patient
	// + doctorsUserObject.getString("patientPhone"));
	// secretaryAndDoctorsAndPatientOfChatroom.setPatientName(doctorsPatientLink_
	// .getPatientName());
	// secretaryAndDoctorsAndPatientOfChatroom.setPatientPhone(doctorsUserObject
	// .getString("patientPhone"));
	// secretaryAndDoctorsAndPatientOfChatroom.setRemark(null);
	// secretaryAndDoctorsAndPatientOfChatroom.setSecretaryLoginId(Constant.secretary
	// + secretaryPhone);
	// secretaryAndDoctorsAndPatientOfChatroom.setSecretaryName(secretaryDoctorsLink.getSecretaryName());
	// secretaryAndDoctorsAndPatientOfChatroom.setSecretaryPhone(secretaryPhone);
	// secretaryAndDoctorsAndPatientOfChatroomService.insert(secretaryAndDoctorsAndPatientOfChatroom);
	// } else {
	// secretaryAndDoctorsAndPatientOfChatroom =
	// secretaryAndDoctorsAndPatientOfChatroomList.get(0);
	// secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(chatroomId);
	// secretaryAndDoctorsAndPatientOfChatroomService.update("updateByPrimaryKey",
	// secretaryAndDoctorsAndPatientOfChatroom);
	// }
	//
	// Map<String, Object> ext_ = new HashMap<String, Object>();
	// ext_.put("doctorType", doctorsPatientLink_.getDoctortype());
	// ext_.put("messageType", "agreeReqeustJoinning");
	// ext_.put("nickName", doctorsPatientLink_.getDoctorsName());
	//
	// EasemobSendMessage easemobSendMessage_ = new EasemobSendMessage();
	// Msg msg_ = new Msg();
	// MsgContent msgContent_ = new MsgContent();
	////
	// msgContent_.type(MsgContent.TypeEnum.CMD).msg(null);
	// UserName userName_ = new UserName();
	// userName_.add(doctorsPatientLink_.getPatientLoginId());
	// msg_.from(doctorsPatientLink_.getDoctorsLoginId()).target(userName_).targetType(HttpToEasemobUtil.TARGETTYPE_USERS).msg(msgContent_)
	// .ext(ext_);
	// Object result_ = easemobSendMessage_.sendMessage(msg_);
	//
	// try {
	// System.out.println("发送消息--------------------------");
	// Map<String, Object> ext = new HashMap<String, Object>();
	// ext.put("nickName", "医生助理-"+secretaryDoctorsLink.getSecretaryName());
	// ext.put("headUrl", Config.secretaryIMG);
	// EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
	// Msg msg = new Msg();
	// MsgContent msgContent = new MsgContent();
	//
	// msgContent.type(MsgContent.TypeEnum.TXT).msg(
	// secretaryAndDoctorsAndPatientOfChatroom.getPatientName()
	// + "，您好。我是"
	// + secretaryAndDoctorsAndPatientOfChatroom.getDoctorsName()
	// + "医生的助理"
	// + secretaryAndDoctorsAndPatientOfChatroom
	// .getSecretaryName() + "，治疗期间有任何问题，可以随时联系我。");
	// UserName userName2 = new UserName();
	// userName2.add(chatroomId);
	// msg.from(Constant.secretary + secretaryPhone).target(userName2)
	// .targetType(HttpToEasemobUtil.TARGETTYPE_CHATGROUPS).msg(msgContent).ext(ext);
	// Object result = easemobSendMessage.sendMessage(msg);
	//// System.out.println("发送消息-------------------------："+result);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	//
	// results.setCode(200);
	// results.setMessage(MessageCode.MESSAGE_200);
	// return results;
	// } else {
	// results.setCode(MessageCode.CODE_501);
	// results.setMessage("环信创建聊天室失败");
	// return results;
	// }
	//
	// } else {
	// results.setCode(MessageCode.CODE_501);
	// results.setMessage("环信添加好友失败2");
	// return results;
	// }
	//
	// } else {
	// results.setCode(MessageCode.CODE_201);
	// results.setMessage(MessageCode.MESSAGE_201);
	// return results;
	// }
	//
	// }

	/**
	 * 
	 * @Title: rejectReustJoinning @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生拒绝患者添加好友请求")
	@RequestMapping(value = "rejectReustJoinning")
	@ResponseBody
	public Results<String> rejectReustJoinning(@RequestParam String doctorsPatientLink, HttpServletRequest request) {
		Results<String> results = new Results<String>();
		JSONObject doctorsUserObject = JSONObject.parseObject(doctorsPatientLink);

		if (doctorsUserObject != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsUserObject.getString("doctorsPhone"));
			paramMap.put("patient_phone", doctorsUserObject.getString("patientPhone"));
			DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService
					.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctors_phone", doctorsUserObject.getString("doctorsPhone"));
			map.put("patient_phone", doctorsUserObject.getString("patientPhone"));
			PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService
					.findByParam("selectByPatientPhoneAndDoctorsPhone", map);

			if (doctorsPatientLink_ == null || patientDoctorsLink_ == null) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				return results;
			}

			/**
			 * 调用环信接口拒绝好友
			 */
			// EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
			// // Object result =
			// easemobIMUsers.addFriendSingle("patient_"+doctorsUserObject.getString("patientPhone"),
			// "doctor_"+doctorsUserObject.getString("doctorsPhone"));
			// Object result =
			// easemobIMUsers.addFriendSingle(doctorsUserObject.getString("patientPhone"),doctorsUserObject.getString("doctorsPhone"));
			//
			// System.out.println(result.toString());
			//
			// JSONObject dataJsonObject =
			// JSONObject.parseObject(result.toString());
			// JSONArray entities =
			// JSONArray.fromObject(dataJsonObject.get("entities"));
			// String entity = entities.get(0).toString();
			// JSONObject entityObject = JSONObject.parseObject(entity);
			// String isSuccess = entityObject.getString("activated");
			// System.out.println("isSuccess:"+isSuccess);
			//
			// if (isSuccess!=null&&"isSuccess".equalsIgnoreCase(isSuccess)) {
			Map<String, Object> ext_ = new HashMap<String, Object>();
			ext_.put("doctorType", doctorsPatientLink_.getDoctortype());
			ext_.put("messageType", "rejectReustJoinning");
			ext_.put("nickName", doctorsPatientLink_.getDoctorsName());

			EasemobSendMessage easemobSendMessage_ = new EasemobSendMessage();
			Msg msg_ = new Msg();
			MsgContent msgContent_ = new MsgContent();
			//
			msgContent_.type(MsgContent.TypeEnum.CMD).msg(null);
			UserName userName_ = new UserName();
			userName_.add(doctorsPatientLink_.getPatientLoginId());
			msg_.from(doctorsPatientLink_.getDoctorsLoginId()).target(userName_)
					.targetType(HttpToEasemobUtil.TARGETTYPE_USERS).msg(msgContent_).ext(ext_);
			Object result_ = easemobSendMessage_.sendMessage(msg_);

			doctorsPatientLink_.setType(2);
			patientDoctorsLink_.setType(2);
			doctorsPatientLinkService.update("updateByPrimaryKey", doctorsPatientLink_);
			patientDoctorsLinkService.update("updateByPrimaryKey", patientDoctorsLink_);
			results.setCode(200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
			// }
			// else {
			// results.setCode(MessageCode.CODE_501);
			// results.setData(null);
			// results.setMessage("环信拒绝好友失败");
			// return results;
			// }

		} else

		{
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: selectOwnPatients @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results
	 *         <DoctorsPatientLink> 返回类型 @throws
	 */
	@SystemControllerLog(description = "获取医生的患者好友列表")
	@RequestMapping(value = "selectOwnPatients")
	@ResponseBody
	public Results<Map<String, Object>> selectOwnPatients(HttpServletRequest request) {
		// doctors_patient_link,查询条件为医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		List<DoctorsPatientLink> listDoctorsPatientLink = null;
		List<DoctorsPatientLink> listDoctorsPatientLink2 = null;
		// List<DoctorsPatientLink> listDoctorsPatientLink_ = null;
		Map<String, Object> map = new HashMap<String, Object>();

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String doctorsPhone;
		if (dataJsonObject != null) {
			doctorsPhone = dataJsonObject.getString("phone");
			if (StringUtils.isNotBlank(doctorsPhone)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("doctorsPhone", doctorsPhone);
				listDoctorsPatientLink = doctorsPatientLinkService.getList("selectByPhone", doctorsPhone);
				listDoctorsPatientLink2 = doctorsPatientLinkService.getList("selectTypeNumByPhone", doctorsPhone);
				if (listDoctorsPatientLink != null) {
					for (int i = 0; i < listDoctorsPatientLink.size(); i++) {
						PatientUser patientUser = patientUserService.findByParam("selectByPhone",
								listDoctorsPatientLink.get(i).getPatientPhone());// 查询患者资料
						listDoctorsPatientLink.get(i).setPatientUser(patientUser);
					}
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("doctorsPatientLink", listDoctorsPatientLink);
					int undoTypeCount = listDoctorsPatientLink2.size();
					map.put("undoTypeCount", undoTypeCount);
					results.setData(map);
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
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: selectUndoRequest @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@SystemControllerLog(description = "查询所有患者请求")
	@RequestMapping(value = "selectUndoRequest")
	@ResponseBody
	public Results<Map<String, Object>> selectUndoRequest(HttpServletRequest request) {
		// doctors_patient_link,查询条件为医生电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		List<DoctorsPatientLink> listDoctorsPatientLink = null;
		// List<DoctorsPatientLink> listDoctorsPatientLink_ = null;
		Map<String, Object> map = new HashMap<String, Object>();

		String doctorsPatientLink = request.getParameter("doctorsPatientLink");
		JSONObject dataJsonObject = JSONObject.parseObject(doctorsPatientLink);
		String doctorsPhone;
		if (dataJsonObject != null) {
			doctorsPhone = dataJsonObject.getString("doctorsPhone");
			if (StringUtils.isNotBlank(doctorsPhone)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("doctorsPhone", doctorsPhone);
				listDoctorsPatientLink = doctorsPatientLinkService.getList("selectByPhone_", doctorsPhone);

				if (listDoctorsPatientLink != null) {
					for (int i = 0; i < listDoctorsPatientLink.size(); i++) {
						PatientUser patientUser = patientUserService.findByParam("selectByPhone",
								listDoctorsPatientLink.get(i).getPatientPhone());// 查询患者资料
						listDoctorsPatientLink.get(i).setPatientUser(patientUser);
					}
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("doctorsPatientLink", listDoctorsPatientLink);

					results.setData(map);
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
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: selectPatientInfo @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	/*
	 * @RequestMapping(value = "selectPatientInfo")
	 * 
	 * @ResponseBody
	 * 
	 * @SystemControllerLog(description = "查询患者资料") public Results<Map<String,
	 * Object>> selectPatientInfo(HttpServletRequest request) { // TODO
	 * Auto-generated method stub Results<Map<String, Object>> results = new
	 * Results<Map<String, Object>>(); Map<String, Object> map = new
	 * HashMap<String, Object>();
	 * 
	 * PatientUser patientUser; String equipmentData =
	 * request.getParameter("equipmentData"); String phone =
	 * request.getParameter("patientPhone");// 患者电话 JSONObject dataJsonObject =
	 * JSONObject.parseObject(equipmentData); String doctorPhone; if
	 * (dataJsonObject != null && phone != null) { doctorPhone =
	 * dataJsonObject.getString("phone"); if (StringUtils.isNotBlank(phone) &&
	 * StringUtils.isNotBlank(doctorPhone)) { patientUser =
	 * patientUserService.findByParam("selectByPhone", phone);// 查询患者资料
	 * Map<String, Object> paramMap = new HashMap<String, Object>();
	 * paramMap.put("doctors_phone", doctorPhone); paramMap.put("patient_phone",
	 * phone); DoctorsPatientLink doctorsPatientLink_ =
	 * doctorsPatientLinkService.findByParam(
	 * "selectByDoctorsPhoneAndPatientPhone", paramMap);
	 * 
	 * if (patientUser != null && doctorsPatientLink_ != null) {
	 * results.setCode(MessageCode.CODE_200);
	 * results.setMessage(MessageCode.MESSAGE_200); int type =
	 * doctorsPatientLink_.getType(); map.put("type", type);
	 * map.put("patientUser", patientUser); map.put("doctorsPatientLink",
	 * doctorsPatientLink_); results.setData(map); return results; } else {
	 * results.setCode(MessageCode.CODE_404);
	 * results.setMessage(MessageCode.MESSAGE_404); results.setData(null);
	 * return results; } } else { results.setCode(MessageCode.CODE_201);
	 * results.setData(null); results.setMessage(MessageCode.MESSAGE_201);
	 * return results; } } else { results.setCode(MessageCode.CODE_201);
	 * results.setData(null); results.setMessage(MessageCode.MESSAGE_201);
	 * return results; } }
	 */

	/**
	 * parameter={"doctorsPhone":"医生电话","patientPhone":"患者电话"}
	 * 
	 * @Title: selectPatientInfo @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@RequestMapping(value = "selectPatientInfo")
	@ResponseBody
	@SystemControllerLog(description = "医生查询患者资料")
	public Results<Map<String, Object>> selectPatientInfo(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		PatientUser patientUser = null; // 患者资料
		PatientHealthRecord patientHealthRecord = null; // 患者健康档案
		List<PatientMedicalRecord> patientMedicalRecordList = null; // 患者病历记录
		List<PatientBloodPressure> patientBloodPressureList = null; // 患者血压记录
		DoctorsPatientLink doctorsPatientLink = null; // 患者医生关系

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String patientPhone = dataJsonObject.getString("patientPhone");
		if (StringUtils.isNotBlank(doctorsPhone) && StringUtils.isNotBlank(patientPhone)) {
			patientUser = patientUserService.findByParam("selectByPhone", patientPhone);// 查询患者资料
			patientHealthRecord = patientHealthRecordService.findByParam("selectByPatientLoginId",
					Constant.patient + patientPhone);
			patientHealthRecord = HealthRecordDESUtil.decryptPatientHealthRecord(patientHealthRecord); //出库解密
			patientMedicalRecordList = patientMedicalRecordService.getList("selectByPatinetLoginId",
					Constant.patient + patientPhone);
			patientBloodPressureList = patientBloodPressureService.getList("getAllBloodPressure", patientPhone);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("patient_phone", patientPhone);
			doctorsPatientLink = doctorsPatientLinkService.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);

			if (patientUser != null) {
				map.put("patientUser", patientUser);
				if (patientHealthRecord != null)
					map.put("patientHealthRecord", patientHealthRecord);
				if (patientMedicalRecordList.size() > 0) {
					//解密出库
					patientMedicalRecordList = MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecordList);
					map.put("patientMedicalRecordList", patientMedicalRecordList);
				}
				if (patientBloodPressureList.size() > 0)
					map.put("patientBloodPressureList", patientBloodPressureList);
				if (doctorsPatientLink != null) {
					map.put("isMyPatient", "yes"); // 申请过加我的患者
					int type = doctorsPatientLink.getType();
					map.put("type", type); // 关系类型：1同意，2拒绝，3未处理，4不再提示
					map.put("doctorsPatientLink", doctorsPatientLink);
				} else {
					map.put("isMyPatient", "no"); // 和我扯不上边的患者
				}

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

	/**
	 * parameter={"doctorsPhone":"","patientPhone":"","oldGroupName":"",
	 * "newGroupName":""} @Title: groupByDoctor @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生给患者好友分组")
	@RequestMapping({ "groupByDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> groupByDoctor(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String patientPhone = dataJsonObject.getString("patientPhone");
		String oldGroupName = dataJsonObject.getString("oldGroupName");
		String newGroupName = dataJsonObject.getString("newGroupName");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctors_phone", doctorsPhone);
		paramMap.put("patient_phone", patientPhone);
		paramMap.put("type", 1);

		List<DoctorsPatientLink> doctorsPatientLinkList = doctorsPatientLinkService
				.getList("selectByDoctorsPhoneAndPatientPhone", paramMap);
		if (doctorsPatientLinkList != null && doctorsPatientLinkList.size() > 0) {
			DoctorsPatientLink doctorsPatientLink = doctorsPatientLinkList.get(0);
			doctorsPatientLink.setGroupName(newGroupName);
			doctorsPatientLink.setType(1);
			doctorsPatientLink.setUpdatetime(new Date());
			doctorsPatientLinkService.update("updateByPrimaryKey", doctorsPatientLink);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功");
			results.setData(map);
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不存在该患者好友");
		}

		return results;
	}

	/**
	 * doctorsPatientLinkJson={"doctorsPhone":"","patientPhone":""} @Title:
	 * noMoreMention @Description: TODO @param @param
	 * doctorsPatientLink @param @param request @param @return 设定文件 @return
	 * Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "不再提示患者添加好友请求")
	@RequestMapping(value = "noMoreMention")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> noMoreMention(@RequestParam String doctorsPatientLinkJson,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject doctorsUserObject = JSONObject.parseObject(doctorsPatientLinkJson);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String doctorsPhone = doctorsUserObject.getString("doctorsPhone");
		String patientPhone = doctorsUserObject.getString("patientPhone");
		paramMap.put("doctors_phone", doctorsPhone);
		paramMap.put("patient_phone", patientPhone);
		paramMap.put("type", 3);

		DoctorsPatientLink doctorsPatientLink = doctorsPatientLinkService
				.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
		PatientDoctorsLink patientDoctorsLink = patientDoctorsLinkService
				.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);
		if (doctorsPatientLink == null || patientDoctorsLink == null) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不存在记录");
			return results;
		}

		doctorsPatientLink.setType(4);
		patientDoctorsLink.setType(4);

		doctorsPatientLinkService.update("updateByPrimaryKey", doctorsPatientLink);
		patientDoctorsLinkService.update("updateByPrimaryKey", patientDoctorsLink);

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"patientLoginId":"","doctorLoginId":""} @Title:
	 * collectDoctor @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生收藏患者")
	@RequestMapping({ "collectPatient" })
	@ResponseBody
	public Results<Map<String, Object>> collectPatient(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String doctorLoginId = dataJsonObject.getString("doctorLoginId");

		if (StringUtils.isBlank(doctorLoginId) && StringUtils.isBlank(patientLoginId) && doctorLoginId.contains("_")
				&& patientLoginId.contains("_")) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		String doctorPhone = doctorLoginId.substring(doctorLoginId.lastIndexOf("_")+1);

		String patientPhone = patientLoginId.substring(patientLoginId.lastIndexOf("_")+1);

		DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorPhone);

		PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);

		if (doctorsUser == null || patientUser == null) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不存在注册用户");
			return results;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctors_phone", doctorPhone);
		paramMap.put("patient_phone", patientPhone);
		paramMap.put("type", 1);
		DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService
				.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
		PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService
				.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);

		if (doctorsPatientLink_ != null || patientDoctorsLink_ != null) {
			results.setCode(MessageCode.CODE_405);
			results.setMessage("已收藏过");
			return results;
		}

		DoctorsPatientLink doctorsPatientLink = new DoctorsPatientLink();
		doctorsPatientLink.setDoctorsId(doctorsUser.getId());
		doctorsPatientLink.setCreatetime(new Date());
		doctorsPatientLink.setDoctorsLoginId(doctorsUser.getLoginId());
		doctorsPatientLink.setDoctorsName(doctorsUser.getName());
		doctorsPatientLink.setDoctorsPhone(doctorsUser.getPhone());
		doctorsPatientLink.setChatroomId(null);
		doctorsPatientLink.setDoctortype(doctorsUser.getDoctortype());
		doctorsPatientLink.setGroupName("未分组患者");
		doctorsPatientLink.setIsvip(null);
		doctorsPatientLink.setPatientId(patientUser.getId());
		doctorsPatientLink.setPatientLoginId(patientUser.getLoginId());
		doctorsPatientLink.setPatientName(patientUser.getName());
		doctorsPatientLink.setPatientPhone(patientUser.getPhone());
		doctorsPatientLink.setType(1);

		doctorsPatientLinkService.insert(doctorsPatientLink);

		PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
		patientDoctorsLink.setChatroomId(null);
		patientDoctorsLink.setCreatetime(new Date());
		patientDoctorsLink.setDoctorsId(doctorsUser.getId());
		patientDoctorsLink.setDoctorsLoginId(doctorsUser.getLoginId());
		patientDoctorsLink.setDoctorsName(doctorsUser.getName());
		patientDoctorsLink.setDoctorsPhone(doctorsUser.getPhone());
		patientDoctorsLink.setDoctortype(doctorsUser.getDoctortype());
		patientDoctorsLink.setPatientId(patientUser.getId());
		patientDoctorsLink.setPatientLoginId(patientUser.getLoginId());
		patientDoctorsLink.setPatientName(patientUser.getName());
		patientDoctorsLink.setPatientPhone(patientUser.getPhone());
		patientDoctorsLink.setType(1);

		patientDoctorsLinkService.insert(patientDoctorsLink);

		SecretaryDoctorsLink secretaryDoctorsLink_ = secretaryDoctorsLinkService.findByParam("selectByDoctorsPhone",
				doctorPhone);

		if (secretaryDoctorsLink_ == null) {// 如果医生没有秘书就直接结束
			results.setCode(MessageCode.CODE_200);
			results.setMessage("收藏成功");
			return results;
		}

		Map<String, Object> map_ = new HashMap<String, Object>();
		map_.put("secretaryPhone", secretaryDoctorsLink_.getSecretaryPhone());
		map_.put("patientPhone", patientPhone);
		SecretaryPatientLink secretaryPatientLink_ = secretaryPatientLinkService
				.findByParam("selectBySecretaryPhoneAndPatientPhone", map_);

		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("doctorsPhone", doctorPhone);
		paramMap3.put("patientPhone", patientPhone);
		paramMap3.put("secretaryPhone", secretaryDoctorsLink_.getSecretaryPhone());
		List<SecretaryAndDoctorsAndPatientOfChatroom> secretaryAndDoctorsAndPatientOfChatroomList_ = secretaryAndDoctorsAndPatientOfChatroomService
				.getList("selectBySecretaryPhoneAndDoctorsPhoneAndPatientPhone", paramMap3);

		if (secretaryDoctorsLink_ != null && secretaryPatientLink_ == null
				&& (secretaryAndDoctorsAndPatientOfChatroomList_ == null
						|| secretaryAndDoctorsAndPatientOfChatroomList_.size() < 1)) {
			SecretaryPatientLink secretaryPatientLink = new SecretaryPatientLink();
			secretaryPatientLink.setCreatetime(new Date());
			secretaryPatientLink.setPatientId(patientUser.getId());
			secretaryPatientLink.setPatientLoginId(patientUser.getLoginId());
			secretaryPatientLink.setPatientName(patientUser.getName());
			secretaryPatientLink.setPatientPhone(patientUser.getPhone());
			secretaryPatientLink.setSecretaryId(secretaryDoctorsLink_.getId());
			secretaryPatientLink.setSecretaryLoginId(secretaryDoctorsLink_.getSecretaryLoginId());
			secretaryPatientLink.setSecretaryName(secretaryDoctorsLink_.getSecretaryName());
			secretaryPatientLink.setSecretaryPhone(secretaryDoctorsLink_.getSecretaryPhone());

			secretaryPatientLinkService.insert(secretaryPatientLink);

			SecretaryAndDoctorsAndPatientOfChatroom secretaryAndDoctorsAndPatientOfChatroom = new SecretaryAndDoctorsAndPatientOfChatroom();
			secretaryAndDoctorsAndPatientOfChatroom.setChatroomDescription(null);
			secretaryAndDoctorsAndPatientOfChatroom.setChatroomId(null);
			secretaryAndDoctorsAndPatientOfChatroom.setChatroomMaxusers(null);
			secretaryAndDoctorsAndPatientOfChatroom.setChatroomName(null);
			secretaryAndDoctorsAndPatientOfChatroom.setChatroomOwner(null);
			secretaryAndDoctorsAndPatientOfChatroom.setCreatetime(new Date());
			secretaryAndDoctorsAndPatientOfChatroom.setDoctorsId(doctorsUser.getId());
			secretaryAndDoctorsAndPatientOfChatroom.setDoctorsLoginId(doctorsUser.getLoginId());
			secretaryAndDoctorsAndPatientOfChatroom.setDoctorsName(doctorsUser.getName());
			secretaryAndDoctorsAndPatientOfChatroom.setDoctorsPhone(doctorsUser.getPhone());
			secretaryAndDoctorsAndPatientOfChatroom.setPatientId(patientUser.getId());
			secretaryAndDoctorsAndPatientOfChatroom.setPatientLoginId(patientUser.getLoginId());
			secretaryAndDoctorsAndPatientOfChatroom.setPatientName(patientUser.getName());
			secretaryAndDoctorsAndPatientOfChatroom.setPatientPhone(patientUser.getPhone());
			secretaryAndDoctorsAndPatientOfChatroom.setRemark(null);
			secretaryAndDoctorsAndPatientOfChatroom.setSecretaryId(secretaryDoctorsLink_.getSecretaryId());
			secretaryAndDoctorsAndPatientOfChatroom.setSecretaryLoginId(secretaryDoctorsLink_.getSecretaryLoginId());
			secretaryAndDoctorsAndPatientOfChatroom.setSecretaryName(secretaryDoctorsLink_.getSecretaryName());
			secretaryAndDoctorsAndPatientOfChatroom.setSecretaryPhone(secretaryDoctorsLink_.getSecretaryPhone());

			secretaryAndDoctorsAndPatientOfChatroomService.insert(secretaryAndDoctorsAndPatientOfChatroom);
		}

		results.setCode(MessageCode.CODE_200);
		results.setMessage("收藏成功");
		results.setData(map);
		return results;
	}

}
