/**   
* @Title: PatientDoctorsLinkController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:36:59 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.model.SecretaryPatientLink;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IContractedHospitalsDoctorsLinkService;
import com.cmcc.medicalcare.service.IContractedHospitalsService;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.cmcc.medicalcare.service.ISecretaryPatientLinkService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 患者的医生
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:36:59
 * 
 */
@Controller
@RequestMapping("/app/patientDoctorsLink")
public class PatientDoctorsLinkController {

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private IPatientUserService patientUserService;

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private ISecretaryPatientLinkService secretaryPatientLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;
	// private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();

	@Resource
	private IContractedHospitalsService contractedHospitalsService;

	@Resource
	private IContractedHospitalsDoctorsLinkService contractedHospitalsDoctorsLinkService;

	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;

	/**
	 * 查询患者的所有医生
	 * 
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description = "查询患者的所有医生")
	@RequestMapping(value = "selectOwnDoctors")
	@ResponseBody
	public Results<Map<String, Object>> selectOwnDoctors(HttpServletRequest request) {
		// patient_doctors_link,查询条件为患者电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PatientDoctorsLink> listPatientDoctorsLink = null;
		SecretaryDoctorsLink secretaryDoctorsLink = null;
		List<PatientDoctorsLink> patientDoctorsLinkList = null;

		String patientPhone = request.getParameter("patientPhone");
		if (StringUtils.isNotBlank(patientPhone)) {
			// 查询该患者是否有过签约医生申请记录
			// Map<String, Object> paramMap2 = new HashMap<String, Object>();
			// paramMap2.put("patient_phone", patientPhone);
			// paramMap2.put("doctorType", 2);
			// PatientDoctorsLink patientDoctorsLink_2 =
			// patientDoctorsLinkService.findByParam("selectByPatientPhoneAndDoctorType",
			// paramMap2);
			// DoctorsPatientLink doctorsPatientLink_2 =
			// doctorsPatientLinkService.findByParam("selectByPatientPhoneAndDoctorType",
			// paramMap2);
			// if (patientDoctorsLink_2!=null &&
			// 1==patientDoctorsLink_2.getType() && doctorsPatientLink_2!=null
			// && 1==doctorsPatientLink_2.getType()) {
			// String doctorsPhone = patientDoctorsLink_2.getDoctorsPhone();
			// DoctorsTeam doctorsTeam =
			// doctorsTeamService.findByParam("selectByPhone", doctorsPhone);
			// map.put("ContractedDoctorsTeam", doctorsTeam); //签约医生的团队信息
			// } else if (patientDoctorsLink_2!=null &&
			// 3==patientDoctorsLink_2.getType() && doctorsPatientLink_2!=null
			// && 3==doctorsPatientLink_2.getType()) {
			// Date recentDate =
			// doctorsPatientLink_2.getUpdatetime()==null?doctorsPatientLink_2.getCreatetime():doctorsPatientLink_2.getUpdatetime();
			// if (Toolkit.daysBetween(recentDate, new Date())>3) {//超过三天，当过期处理
			// //删除过期记录
			// patientDoctorsLinkService.delete("deleteByPrimaryKey",
			// patientDoctorsLink_2);
			// doctorsPatientLinkService.delete("deleteByPrimaryKey",
			// doctorsPatientLink_2);
			// }
			//
			// map.put("ContractedDoctorsTeam", null);
			// } else {
			// map.put("ContractedDoctorsTeam", null);
			// }

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("patient_phone", patientPhone);
			paramMap.put("activeType", new Integer(1)); // 活跃类型：包括已同意和未处理两种状态下的好友
			listPatientDoctorsLink = patientDoctorsLinkService.getList("selectByPatientPhoneAndDoctorsPhone", paramMap);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("patient_phone", patientPhone);
			param.put("doctorType", 2);
			param.put("type", 1);
			patientDoctorsLinkList = patientDoctorsLinkService.getList("selectContractedDoctor", param);// 查找是否有签约医生
			if (patientDoctorsLinkList == null || patientDoctorsLinkList.size() < 1) {
				map.put("ContractedDoctorsTeam", null);
			} else {
				String doctorsPhone = patientDoctorsLinkList.get(0).getDoctorsPhone();
				DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPhone", doctorsPhone);
				map.put("ContractedDoctorsTeam", doctorsTeam); // 签约医生的团队信息
			}

			if (listPatientDoctorsLink != null && listPatientDoctorsLink.size() > 0) {
				for (int i = 0; i < listPatientDoctorsLink.size(); i++) {
					DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone",
							listPatientDoctorsLink.get(i).getDoctorsPhone());// 查询医生资料
					listPatientDoctorsLink.get(i).setDoctorsUser(doctorsUser);
					secretaryDoctorsLink = secretaryDoctorsLinkService.findByParam("selectByDoctorsPhone",
							listPatientDoctorsLink.get(i).getDoctorsPhone());// 查询护士资料
					if (secretaryDoctorsLink != null)
						listPatientDoctorsLink.get(i)
								.setSecretaryName("医生助理-" + secretaryDoctorsLink.getSecretaryName());
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("listPatientDoctorsLink", listPatientDoctorsLink);
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

	}

	/**
	 * 患者请求加医生为好友（注：该接口被/app/patientUser/submitRequest接口代替）
	 * 
	 * @param request
	 * @return
	 */
	// @Deprecated
	// @SystemControllerLog(description = "患者请求加医生为好友")
	// @RequestMapping(value = "requestJoining")
	// @ResponseBody
	// @Transactional
	// public Results<String> requestJoining(HttpServletRequest request) {
	// Results<String> results = new Results<String>();
	//
	// String doctorsLoginId = request.getParameter("doctorsLoginId");
	// String equipmentData = request.getParameter("equipmentData");
	// JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
	// String myPhone = dataJsonObject.getString("phone");
	//
	// if (!StringUtils.isNotBlank(equipmentData) &&
	// !StringUtils.isNotBlank(myPhone)
	// && !StringUtils.isNotBlank(doctorsLoginId)) {
	// results.setCode(MessageCode.CODE_201);
	// results.setMessage(MessageCode.MESSAGE_201);
	// return results;
	// }
	// String doctorsPhone = null;
	// try {
	// doctorsPhone = doctorsLoginId.trim().split("_")[1];
	// } catch (Exception e) {
	// e.printStackTrace();
	// results.setCode(MessageCode.CODE_202);
	// results.setMessage(MessageCode.MESSAGE_202);
	// return results;
	// }
	//
	// PatientUser patientUser = patientUserService.findByParam("selectByPhone",
	// myPhone);
	// DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone",
	// doctorsPhone);
	// if (patientUser == null || doctorsUser == null) {
	// results.setCode(MessageCode.CODE_501);
	// results.setMessage("尚未注册");
	// return results;
	// }
	//
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	// paramMap.put("doctors_phone", doctorsPhone);
	// paramMap.put("patient_phone", myPhone);
	//
	// PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService
	// .findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);
	// DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService
	// .findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
	// if (null == patientDoctorsLink_ && null == doctorsPatientLink_) {//
	// 没有记录，则插入
	// // 添加记录到patient_doctors_link表
	// PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
	// patientDoctorsLink.setCreatetime(new Date());
	// patientDoctorsLink.setDoctorsId(doctorsUser.getId());
	// patientDoctorsLink.setDoctorsLoginId(doctorsUser.getLoginId());
	// patientDoctorsLink.setDoctorsName(doctorsUser.getName());
	// patientDoctorsLink.setDoctorsPhone(doctorsPhone);
	//
	// patientDoctorsLink.setPatientId(patientUser.getId());
	// patientDoctorsLink.setPatientLoginId(patientUser.getLoginId());
	// patientDoctorsLink.setPatientName(patientUser.getName());
	// patientDoctorsLink.setPatientPhone(myPhone);
	// patientDoctorsLink.setType(3);
	// patientDoctorsLinkService.insert(patientDoctorsLink);
	//
	// // 添加记录到doctors_patient_link表
	// DoctorsPatientLink doctorsPatientLink = new DoctorsPatientLink();
	// doctorsPatientLink.setCreatetime(new Date());
	// doctorsPatientLink.setDoctorsId(doctorsUser.getId());
	// doctorsPatientLink.setDoctorsLoginId(doctorsUser.getLoginId());
	// doctorsPatientLink.setDoctorsName(doctorsUser.getName());
	// doctorsPatientLink.setDoctorsPhone(doctorsPhone);
	//
	// doctorsPatientLink.setPatientId(patientUser.getId());
	// doctorsPatientLink.setPatientLoginId(patientUser.getLoginId());
	// doctorsPatientLink.setPatientName(patientUser.getName());
	// doctorsPatientLink.setPatientPhone(myPhone);
	// doctorsPatientLink.setType(3);
	// doctorsPatientLink.setGroupName("未分组患者");
	// doctorsPatientLinkService.insert(doctorsPatientLink);
	//
	// results.setCode(MessageCode.CODE_200);
	// results.setMessage(MessageCode.MESSAGE_200);
	// return results;
	// } else if (patientDoctorsLink_ != null && 2 ==
	// patientDoctorsLink_.getType() && doctorsPatientLink_ != null
	// && 2 == doctorsPatientLink_.getType()) {// 有记录，为拒绝状态，则修改为未处理状态
	// // 修改记录到patient_doctors_link表
	// patientDoctorsLink_.setType(3);
	// patientDoctorsLink_.setUpdatetime(new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss").format(new Date()));
	// patientDoctorsLinkService.update("updateByPrimaryKeySelective",
	// patientDoctorsLink_);
	//
	// // 修改记录到doctors_patient_link表
	// doctorsPatientLink_.setType(3);
	// doctorsPatientLink_.setGroupName("未分组患者");
	// doctorsPatientLink_.setUpdatetime(new Date());
	// doctorsPatientLinkService.update("updateByPrimaryKeySelective",
	// doctorsPatientLink_);
	//
	// results.setCode(MessageCode.CODE_200);
	// results.setMessage(MessageCode.MESSAGE_200);
	// return results;
	// } else if (patientDoctorsLink_ != null && 3 ==
	// patientDoctorsLink_.getType() && doctorsPatientLink_ != null
	// && 3 == doctorsPatientLink_.getType()) {// 有记录，为未处理状态，则回复重复操作
	// results.setCode(MessageCode.CODE_300);
	// results.setMessage(MessageCode.MESSAGE_300);
	// return results;
	// } else if (patientDoctorsLink_ != null && 1 ==
	// patientDoctorsLink_.getType() && doctorsPatientLink_ != null
	// && 1 == doctorsPatientLink_.getType()) {// 有记录，为接受状态，则回复已是好友关系
	// results.setCode(MessageCode.CODE_405);
	// results.setMessage("已是医患关系");
	// return results;
	// } else if (patientDoctorsLink_ != null && 4 ==
	// patientDoctorsLink_.getType() && doctorsPatientLink_ != null
	// && 4 == doctorsPatientLink_.getType()) {// 有记录，为不再提示状态，则回复重复操作
	// results.setCode(MessageCode.CODE_502);
	// results.setMessage(MessageCode.MESSAGE_300);
	// return results;
	// } else {// 其他情况，视为操作失败
	// results.setCode(MessageCode.CODE_501);
	// results.setMessage(MessageCode.MESSAGE_501);
	// return results;
	// }
	//
	// }

	/**
	 * parameter={"doctorsLoginId":"","patientLoginId":""} @Title:
	 * deleteDoctor @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者删除医生")
	@RequestMapping(value = "deleteDoctor")
	@ResponseBody
	public Results<Map<String, Object>> deleteDoctor(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		System.out.println("doctorsLoginId--" + doctorsLoginId);
		System.out.println("patientLoginId--" + patientLoginId);

		if (StringUtils.isNotBlank(patientLoginId) && StringUtils.isNotBlank(doctorsLoginId)) {
			System.out.println("doctorsLoginId::::" + doctorsLoginId);
			System.out.println("patientLoginId::::" + patientLoginId);
			String doctorsPhone = null;
			String patientPhone = null;
			try {
				doctorsPhone = doctorsLoginId.trim().split("_")[1];
				patientPhone = patientLoginId.trim().split("_")[1];
			} catch (Exception e) {
				e.printStackTrace();
				results.setCode(MessageCode.CODE_202);
				results.setMessage(MessageCode.MESSAGE_202);
				return results;
			}
			System.out.println("doctorsPhone::::" + doctorsPhone);
			System.out.println("patientPhone::::" + patientPhone);

			// 判断是否为好友关系
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("patient_phone", patientPhone);
			paramMap.put("type", 1);
			PatientDoctorsLink patientDoctorsLink = patientDoctorsLinkService
					.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);
			DoctorsPatientLink doctorsPatientLink = doctorsPatientLinkService
					.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
			if (patientDoctorsLink == null || doctorsPatientLink == null) {
				results.setCode(MessageCode.CODE_204);
				results.setMessage("还不是好友关系，无法删除");
				return results;
			}

			String chatRommId = patientDoctorsLink.getChatroomId();
			System.out.println("chatRommId:" + chatRommId);
			EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
			Object result = easemobChatGroup.deleteChatGroup(chatRommId);
			// if (result == null) {
			// results.setCode(MessageCode.CODE_501);
			// results.setMessage("删除环信群组失败");
			// return results;
			// }

			// 删除环信好友关系
			String ret = HttpToEasemobUtil.deleteFriendSingle(patientDoctorsLink.getDoctorsLoginId(),
					patientDoctorsLink.getPatientLoginId());
			// System.out.println(ret);
			// JSONObject jsonObject = JSONObject.parseObject(ret);
			// JSONArray jsonArray = jsonObject.getJSONArray("entities");
			// JSONObject entity = jsonArray.getJSONObject(0);
			// Boolean activated = entity.getBoolean("activated");
			// System.out.println("activated: " + activated);
			// if (activated != true) {
			// results.setCode(MessageCode.CODE_501);
			// results.setMessage("删除环信好友关系失败");
			// return results;
			// }
			patientDoctorsLinkService.delete("deleteByDoctorPhoneAndPatientPhone", patientDoctorsLink);

			doctorsPatientLinkService.delete("deleteByDoctorPhoneAndPatientPhone", doctorsPatientLink);

			// 如果该医生有小秘书，则找出医生的小秘书，删除小秘书患者表
			SecretaryDoctorsLink secretaryDoctorsLink = secretaryDoctorsLinkService.findByParam("selectByDoctorsPhone",
					doctorsPhone);
			if (secretaryDoctorsLink != null) {
				String secretaryPhone = secretaryDoctorsLink.getSecretaryPhone();
				Map<String, Object> paramMap2 = new HashMap<String, Object>();
				paramMap2.put("patientPhone", patientPhone);
				paramMap2.put("secretaryPhone", secretaryPhone);
				List<SecretaryAndDoctorsAndPatientOfChatroom> secretaryAndDoctorsAndPatientOfChatroomList_ = secretaryAndDoctorsAndPatientOfChatroomService
						.getList("selectBySecretaryPhoneAndPatientPhone", paramMap2);
				if (secretaryAndDoctorsAndPatientOfChatroomList_.size() == 1) {// 患者如果只有一个好友，就可以删除secretaryPatientLink记录
					SecretaryPatientLink secretaryPatientLink = new SecretaryPatientLink();
					secretaryPatientLink.setPatientPhone(patientPhone);
					secretaryPatientLink.setSecretaryPhone(secretaryPhone);
					secretaryPatientLinkService.delete("deleteBySecretaryPhoneAndPatientPhone", secretaryPatientLink);
				}

				Map<String, Object> paramMap3 = new HashMap<String, Object>();
				paramMap3.put("doctorsPhone", doctorsPhone);
				paramMap3.put("patientPhone", patientPhone);
				paramMap3.put("secretaryPhone", secretaryPhone);
				List<SecretaryAndDoctorsAndPatientOfChatroom> secretaryAndDoctorsAndPatientOfChatroomList = secretaryAndDoctorsAndPatientOfChatroomService
						.getList("selectBySecretaryPhoneAndDoctorsPhoneAndPatientPhone", paramMap3);
				if (secretaryAndDoctorsAndPatientOfChatroomList.size() > 0) {
					for (int i = 0; i < secretaryAndDoctorsAndPatientOfChatroomList.size(); i++) {
						secretaryAndDoctorsAndPatientOfChatroomService.delete("deleteByDoctorPhoneAndPatientPhone",
								secretaryAndDoctorsAndPatientOfChatroomList.get(i));
					}
				}
			}

			System.out.println("发透传通知医生被删除--------------------------------");
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("messageType", "deleteDoctor");
			ext.put("patientPhone", patientDoctorsLink.getPatientPhone());
			ext.put("patientName", patientDoctorsLink.getPatientName());

			msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
			UserName userName = new UserName();
			userName.add(Constant.doctor + patientDoctorsLink.getDoctorsPhone());
			msg.from(Constant.patient + patientDoctorsLink.getPatientPhone()).target(userName).targetType("users")
					.msg(msgContent).ext(ext);
			Object result_ = easemobSendMessage.sendMessage(msg);

			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}

	/**
	 * parameter={"keyword":""," patientPhone":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者查找医生")
	@RequestMapping({ "searchDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> searchDoctor(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String patientPhone = dataJsonObject.getString("patientPhone");

		if (StringUtils.isNotBlank(keyword)) {
			if (Toolkit.isMobileNO(keyword)) {// 如果关键字为电话号码
				String phone = keyword;
				// 根据电话去查询doctors_user
				DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);
				if (doctorsUser != null) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("patient_phone", patientPhone);
					param.put("doctors_phone", phone);
					param.put("type", 1);
					List<PatientDoctorsLink> patientDoctorsLinkList = patientDoctorsLinkService
							.getList("selectByPatientPhoneAndDoctorsPhone", param);// 查找是否为好友
					System.out.println("size:" + patientDoctorsLinkList.size());
					if (patientDoctorsLinkList != null && patientDoctorsLinkList.size() > 0) {// 不能返回好友
						results.setCode(MessageCode.CODE_405);
						results.setMessage("已经是好友");
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
					results.setMessage("该号码未注册");
					results.setData(map);
					return results;
				}
			} else {// 如果关键字是医生名字
				String name = keyword;
				// 根据名字去查询doctors_user，
				doctorsUsersList = doctorsUserService.getList("selectReferralDoctorByName", name);
				if (doctorsUsersList != null && doctorsUsersList.size() > 0) {

					Map<String, Object> param = new HashMap<String, Object>();
					param.put("patient_phone", patientPhone);
					param.put("doctors_name", name);
					param.put("type", 1);
					List<PatientDoctorsLink> patientDoctorsLinkList = patientDoctorsLinkService
							.getList("selectByPatientPhoneAndDoctorsName", param);// 查找是否为好友

					if (patientDoctorsLinkList != null && patientDoctorsLinkList.size() > 0) {
						for (int i = 0; i < doctorsUsersList.size(); i++) {
							if (doctorsUsersList.get(i).getPhone()
									.equals(patientDoctorsLinkList.get(0).getDoctorsPhone())) {// 不能返回好友
								doctorsUsersList.remove(i);// 去掉好友信息
								if (doctorsUsersList.size() == 0) {
									results.setCode(MessageCode.CODE_204);
									results.setMessage("已经是好友");
									return results;
								}
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
			doctorsUsersList = doctorsUserService.getList("selectALL", null);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("patient_phone", patientPhone);
			param.put("type", 1);
			List<PatientDoctorsLink> patientDoctorsLinkList = patientDoctorsLinkService
					.getList("selectByPatientPhoneAndDoctorsName", param);// 查找患者所有好友

			for (int j = 0; j < patientDoctorsLinkList.size(); j++) {
				String pphone = patientDoctorsLinkList.get(j).getDoctorsPhone();
				for (int i = 0; i < doctorsUsersList.size(); i++) {
					if (doctorsUsersList.get(i).getPhone().equals(pphone)) {// 不能返回好友
						doctorsUsersList.remove(i);// 去掉好友信息
						if (doctorsUsersList.size() == 0) {
							results.setCode(MessageCode.CODE_204);
							results.setMessage("找不到对应的医生");
							return results;
						}
					}
				}
			}

			map.put("doctorsUsersList", doctorsUsersList);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功");
			results.setData(map);
			return results;
		}

	}

	/**
	 * parameter={"keyword":""," patientPhone":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者搜索医院/医生/科室")
	@RequestMapping({ "fuzzySearch" })
	@ResponseBody
	public Results<Map<String, Object>> fuzzySearch(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsNameList = new ArrayList<DoctorsUser>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();
		List<DoctorsUser> hospitalList = new ArrayList<DoctorsUser>();
		List<DoctorsUser> departmentList = new ArrayList<DoctorsUser>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String patientPhone = dataJsonObject.getString("patientPhone");

		if (StringUtils.isNotBlank(keyword)) {

			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(keyword);

			if (sensitiveWord != null && sensitiveWord.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
				sensitiveRecordLog.setUserPhone(patientPhone);
				sensitiveRecordLog.setType(sensitiveWord.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);

				String equipmentData = request.getParameter("equipmentData");
				JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
				if (dataJsonObject2 != null) {
					String userName = dataJsonObject2.getString("userName");
					sensitiveRecordLog.setUserName(userName);
				}

				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("根据法律法规不给于搜索相应的违法信息");
				return results;
			}

			String name = keyword;
			String hospital = keyword;
			String department = keyword;
			// 根据名字去查询doctors_user，
			doctorsNameList = doctorsUserService.getList("selectByName_", name);
			System.out.println("大小：" + doctorsNameList.size());
			hospitalList = doctorsUserService.getList("selectByHospital_", hospital);
			departmentList = doctorsUserService.getList("selectByDepartment_", department);
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("输入不能为空");
			return results;
		}

		resultMap.put("hospitalList", hospitalList);
		resultMap.put("doctorsNameList", doctorsNameList);
		resultMap.put("departmentList", departmentList);
		map.put("resultList", resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"input":"","patientPhone":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者搜索医生")
	@RequestMapping({ "newSearchDoctors" })
	@ResponseBody
	public Results<Map<String, Object>> newSearchDoctors(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsNameList = new ArrayList<DoctorsUser>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();
		List<DoctorsUser> hospitalList = new ArrayList<DoctorsUser>();
		List<DoctorsUser> departmentList = new ArrayList<DoctorsUser>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String input = dataJsonObject.getString("input");
		String patientPhone = dataJsonObject.getString("patientPhone");

		if (StringUtils.isNotBlank(input)) {

			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(input);

			if (sensitiveWord != null && sensitiveWord.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
				sensitiveRecordLog.setUserPhone(patientPhone);
				sensitiveRecordLog.setType(sensitiveWord.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);

				String equipmentData = request.getParameter("equipmentData");
				JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
				if (dataJsonObject2 != null) {
					String userName = dataJsonObject2.getString("userName");
					sensitiveRecordLog.setUserName(userName);
				}

				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("根据法律法规不给于搜索相应的违法信息");
				return results;
			}

			String name = input;
			String hospital = input;
			String department = input;
			// 根据名字去查询doctors_user，
			doctorsNameList = doctorsUserService.getList("selectByName_2", name);
			System.out.println("大小：" + doctorsNameList.size());
			hospitalList = doctorsUserService.getList("selectByHospital_2", hospital);
			departmentList = doctorsUserService.getList("selectByDepartment_2", department);
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("输入不能为空");
			return results;
		}

		resultMap.put("hospitalList", hospitalList);
		resultMap.put("doctorsNameList", doctorsNameList);
		resultMap.put("departmentList", departmentList);
		map.put("resultList", resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"pageIndex":"","pageSize":"","keyword":"","department":"",
	 * "province":"广东","city":"广州","area":"天河区","hospital":""}
	 * 
	 * @Title: recommendDoctors @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者的推荐医生")
	@RequestMapping({ "recommendDoctors" })
	@ResponseBody
	public Results<Map<String, Object>> recommendDoctors(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String pageIndex = dataJsonObject.getString("pageIndex");
		String pageSize = dataJsonObject.getString("pageSize");
		String keyword = dataJsonObject.getString("keyword");
		String province = dataJsonObject.getString("province");
		String city = dataJsonObject.getString("city");
		String area = dataJsonObject.getString("area");
		String department = dataJsonObject.getString("department");
		String hospital = dataJsonObject.getString("hospital");
		Map<String, Object> param = new HashMap<String, Object>();

		if (StringUtils.isBlank(pageSize) && StringUtils.isBlank(pageIndex)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		System.out.println(
				"pageIndex:" + (Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize)));
		System.out.println("pageSize:" + pageSize);
		param.put("pageIndex", Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize));
		param.put("pageSize", Integer.parseInt(pageSize));

		param.put("province", province);
		param.put("cityName", city);
		param.put("area", area);
		param.put("name", keyword);
		param.put("department", department);
		param.put("hospital", hospital);

		doctorsUsersList = doctorsUserService.getList("selectALL_2", param);

		resultMap.put("doctorsUsersList", doctorsUsersList);

		map.put("resultList", resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"keyword":"","province":"","city":"","area":"","hospital":
	 * ""} @Title: searchFamilyDoctors @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者搜索家庭医生")
	@RequestMapping({ "searchFamilyDoctors" })
	@ResponseBody
	public Results<Map<String, Object>> searchFamilyDoctors(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String province = dataJsonObject.getString("province");
		String city = dataJsonObject.getString("city");
		String area = dataJsonObject.getString("area");
		String hospital = dataJsonObject.getString("hospital");
		String department = dataJsonObject.getString("department");

		SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(keyword);

		if (sensitiveWord != null && sensitiveWord.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
			sensitiveRecordLog.setText(sensitiveWord.getText());

			sensitiveRecordLog.setType(sensitiveWord.getType());
			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);

			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
			if (dataJsonObject2 != null) {
				String userName = dataJsonObject2.getString("userName");
				String patientPhone = dataJsonObject2.getString("phone");
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
				sensitiveRecordLog.setUserPhone(patientPhone);
			}

			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("根据法律法规不给于搜索相应的违法信息");
			return results;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("province", province);
		paramMap.put("cityName", city);
		paramMap.put("area", area);
		paramMap.put("name", keyword);
		paramMap.put("hospital", hospital);
		paramMap.put("department", department);

		doctorsUsersList = doctorsUserService.getList("selectFamilyDoctors", paramMap);

		resultMap.put("doctorsUsersList", doctorsUsersList);

		map.put("resultList", resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"keyword":"","province":"","city":"","area":"","department":""
	 * ,"hospital": ""} @Title: searchFamilyDoctors @Description:
	 * TODO @param @param parameter @param @param request @param @return
	 * 设定文件 @return Results<Map <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者搜索专科医生")
	@RequestMapping({ "searchSpecialDoctors" })
	@ResponseBody
	public Results<Map<String, Object>> searchSpecialDoctors(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String keyword = dataJsonObject.getString("keyword");
		String province = dataJsonObject.getString("province");
		String city = dataJsonObject.getString("city");
		String area = dataJsonObject.getString("area");
		String department = dataJsonObject.getString("department");
		String hospital = dataJsonObject.getString("hospital");

		SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(keyword);

		if (sensitiveWord != null && sensitiveWord.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
			sensitiveRecordLog.setText(sensitiveWord.getText());

			sensitiveRecordLog.setType(sensitiveWord.getType());

			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);

			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
			if (dataJsonObject2 != null) {
				String userName = dataJsonObject2.getString("userName");
				String patientPhone = dataJsonObject2.getString("phone");
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
				sensitiveRecordLog.setUserPhone(patientPhone);
			}

			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("根据法律法规不给于搜索相应的违法信息");
			return results;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("province", province);
		paramMap.put("cityName", city);
		paramMap.put("area", area);
		paramMap.put("name", keyword);
		paramMap.put("department", department);
		paramMap.put("hospital", hospital);

		doctorsUsersList = doctorsUserService.getList("selectSpecialDoctors", paramMap);

		resultMap.put("doctorsUsersList", doctorsUsersList);

		map.put("resultList", resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"pageIndex":"","pageSize":"","keyword":"","hospital":"","
	 * department":"","province":"","city":"","area":""} @Title:
	 * chooseDoctor @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "选择解读医生")
	@RequestMapping({ "chooseDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> chooseDoctor(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String pageIndex = dataJsonObject.getString("pageIndex");
		String pageSize = dataJsonObject.getString("pageSize");
		String keyword = dataJsonObject.getString("keyword");
		String province = dataJsonObject.getString("province");
		String city = dataJsonObject.getString("city");
		String area = dataJsonObject.getString("area");
		String department = dataJsonObject.getString("department");
		String hospital = dataJsonObject.getString("hospital");
		Map<String, Object> param = new HashMap<String, Object>();

		if (StringUtils.isBlank(pageSize) && StringUtils.isBlank(pageIndex)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(keyword);

		if (sensitiveWord != null && sensitiveWord.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
			sensitiveRecordLog.setText(sensitiveWord.getText());

			sensitiveRecordLog.setType(sensitiveWord.getType());

			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);

			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
			if (dataJsonObject2 != null) {
				String userName = dataJsonObject2.getString("userName");
				String patientPhone = dataJsonObject2.getString("phone");
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
				sensitiveRecordLog.setUserPhone(patientPhone);
			}

			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("根据法律法规不给于输入相应的违法信息");
			return results;
		}

		System.out.println(
				"pageIndex:" + (Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize)));
		System.out.println("pageSize:" + pageSize);
		param.put("pageIndex", Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize));
		param.put("pageSize", Integer.parseInt(pageSize));
		param.put("province", province);
		param.put("cityName", city);
		param.put("area", area);
		param.put("name", keyword);
		param.put("department", department);
		param.put("hospital", hospital);

		doctorsUsersList = doctorsUserService.getList("selectChooseDoctors", param);

		resultMap.put("doctorsUsersList", doctorsUsersList);

		map.put("resultList", resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * 
	 * @Title: requestJoining @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	/*
	 * @SystemControllerLog(description = "患者请求加医生为好友")
	 * 
	 * @RequestMapping(value = "requestJoining")
	 * 
	 * @ResponseBody Results<String> requestJoining(HttpServletRequest request)
	 * { Results<String> results = new Results<String>();
	 * 
	 * String doctorsPhone = request.getParameter("doctorsPhone"); String
	 * equipmentData = request.getParameter("equipmentData"); JSONObject
	 * dataJsonObject = JSONObject.parseObject(equipmentData); String myPhone =
	 * dataJsonObject.getString("phone"); String content =
	 * "{'msgType':'patientInfo','msgContent':'hello','chatRoomId':'','phone':'','doctorInfo':'','patientInfo':'patientInfo_entity'}";
	 * String sendContent = null;
	 * 
	 * if (!StringUtils.isNotBlank(equipmentData) &&
	 * !StringUtils.isNotBlank(myPhone) &&
	 * !StringUtils.isNotBlank(doctorsPhone)) {
	 * results.setCode(MessageCode.CODE_201);
	 * results.setMessage(MessageCode.MESSAGE_201); return results; }
	 * 
	 * PatientUser patientUser = patientUserService.findByParam("selectByPhone",
	 * myPhone); if (patientUser != null) { patientUser.setPassword(null);
	 * patientUser.setLoginId(null); String strPatientUser =
	 * JSONObject.toJSONString(patientUser); sendContent = new String(content);
	 * sendContent = sendContent.replace("patientInfo_entity", strPatientUser);
	 * } else { results.setCode(MessageCode.CODE_501);
	 * results.setMessage(MessageCode.MESSAGE_501); return results; }
	 * 
	 * // 调用环信接口，發送添加請求給醫生 Msg msg = new Msg(); MsgContent msgContent = new
	 * MsgContent(); msgContent.type(MsgContent.TypeEnum.TXT).msg(sendContent);
	 * UserName targetUser = new UserName(); String targetName = "doctor_" +
	 * doctorsPhone; targetUser.add(targetName); String myName = "patient_" +
	 * myPhone;
	 * msg.from(myName).target(targetUser).targetType("users").msg(msgContent);
	 * Object result = easemobSendMessage.sendMessage(msg);
	 * 
	 * if (result != null) { // 解释result JSONObject resultObject =
	 * JSONObject.parseObject(result.toString()); JSONObject dataObject =
	 * resultObject.getJSONObject("data"); String requestResult =
	 * dataObject.getString(targetName); if ("success".equals(requestResult)) {
	 * results.setCode(MessageCode.CODE_200);
	 * results.setMessage(MessageCode.MESSAGE_200); return results; } else {
	 * results.setCode(MessageCode.CODE_501); results.setMessage("环信发送消息失败");
	 * return results; } } else { results.setCode(MessageCode.CODE_501);
	 * results.setMessage("环信发送消息失败"); return results; }
	 * 
	 * }
	 */

	/**
	 * 患者成功添加医生为好友
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @SystemControllerLog(description = "患者成功添加医生为好友")
	 * 
	 * @RequestMapping(value = "addDoctorAsFriend")
	 * 
	 * @ResponseBody Results<String> addDoctorAsFriend(HttpServletRequest
	 * request) { // 添加记录patient_doctors_link Results<String> results = new
	 * Results<String>(); String doctorsId = request.getParameter("doctorsId");
	 * String doctorsLoginId = request.getParameter("doctorsLoginId"); String
	 * doctorsName = request.getParameter("doctorsName"); String doctorsPhone =
	 * request.getParameter("doctorsPhone"); String patientId =
	 * request.getParameter("patientId"); String patientLoginId =
	 * request.getParameter("patientLoginId"); String patientName =
	 * request.getParameter("patientName"); String patientPhone =
	 * request.getParameter("patientPhone");
	 * 
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * map.put("doctorsPhone", doctorsPhone); map.put("patientPhone",
	 * patientPhone); PatientDoctorsLink patientDoctorsLink_ =
	 * patientDoctorsLinkService
	 * .findByParam("selectByPatientPhoneAndDoctorsPhone", map); if
	 * (patientDoctorsLink_ == null) { PatientDoctorsLink patientDoctorsLink =
	 * new PatientDoctorsLink(); patientDoctorsLink.setCreatetime(new Date());
	 * if (StringUtils.isNotBlank(doctorsId)) {
	 * patientDoctorsLink.setDoctorsId(Integer.parseInt(doctorsId)); }
	 * patientDoctorsLink.setDoctorsLoginId(doctorsLoginId);
	 * patientDoctorsLink.setDoctorsName(doctorsName);
	 * patientDoctorsLink.setDoctorsPhone(doctorsPhone); if
	 * (StringUtils.isNotBlank(patientId)) {
	 * patientDoctorsLink.setPatientId(Integer.parseInt(patientId)); }
	 * patientDoctorsLink.setPatientLoginId(patientLoginId);
	 * patientDoctorsLink.setPatientName(patientName);
	 * patientDoctorsLink.setPatientPhone(patientPhone);
	 * patientDoctorsLinkService.insert(patientDoctorsLink);
	 * results.setCode(MessageCode.CODE_200);
	 * results.setMessage(MessageCode.MESSAGE_200); } else {
	 * results.setCode(MessageCode.CODE_405);
	 * results.setMessage(MessageCode.MESSAGE_405); }
	 * 
	 * return results; }
	 * 
	 */
	/**
	 * 
	* @Title: selectAllDepartments 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "查找所有科室")
	@RequestMapping({ "selectAllDepartments " })
	@ResponseBody
	public Results<Map<String, Object>> selectAllDepartments(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);

		// 调用接口返回json
		String jsonStr = "";
		
		if (StringUtils.isNotBlank(jsonStr)) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = json.getJSONArray("data");
				map.put("deparmentList", jsonArray);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_204);
				results.setMessage(json.getString("msg"));
			}
		}else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("接口调用失败");
		}

		return results;
	}

	/**
	 * 
	* @Title: lookupDoctors 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "模糊查询医生")
	@RequestMapping({ "lookupDoctors" })
	@ResponseBody
	public Results<Map<String, Object>> lookupDoctors(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String departmentId = dataJsonObject.getString("departmentId");
		if (StringUtils.isBlank(departmentId)) {
			departmentId="";
		}
		String doctorName = dataJsonObject.getString("doctorName");
		if (StringUtils.isBlank(doctorName)) {
			doctorName="";
		}
		
		// 调用接口返回json
		String jsonStr = DoctorUserUtils.findDoctor("", "", departmentId, doctorName, "");
		
		if (StringUtils.isNotBlank(jsonStr)) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = json.getJSONArray("data");
				map.put("deparmentList", jsonArray);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_204);
				results.setMessage(json.getString("msg"));
			}
		}else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("接口调用失败");
		}

		return results;
	}
	
	/**
	 * 
	* @Title: selectDoctorsByDepartmentId 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "通过科室ID查询医生")
	@RequestMapping({ "selectDoctorsByDepartmentId" })
	@ResponseBody
	public Results<Map<String, Object>> selectDoctorsByDepartmentId(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String departmentId = dataJsonObject.getString("departmentId");
		
		// 调用接口返回json
		String jsonStr = DoctorUserUtils.findDoctor("", "", departmentId, "", "");
		
		if (StringUtils.isNotBlank(jsonStr)) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = json.getJSONArray("data");
				map.put("deparmentList", jsonArray);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_204);
				results.setMessage(json.getString("msg"));
			}
		}else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("接口调用失败");
		}

		return results;
	}
}
