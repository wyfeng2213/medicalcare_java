/**   
* @Title: PatientDoctorsLinkController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:36:59 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.easemob.server.api.impl.EasemobSendMessage;

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
	
//	private EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
	
	/**
	 * 查询患者的所有医生
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description = "查询患者的所有医生")
	@RequestMapping(value = "selectOwnDoctors")
	@ResponseBody
	Results<Map<String, Object>> selectOwnDoctors(HttpServletRequest request) {
		// patient_doctors_link,查询条件为患者电话
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PatientDoctorsLink> listPatientDoctorsLink = null;

		String patientPhone = request.getParameter("patientPhone");
		if (StringUtils.isNotBlank(patientPhone)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("patient_phone", patientPhone);
			paramMap.put("activeType", new Integer(1)); //活跃类型：包括已同意和未处理两种状态下的好友
			listPatientDoctorsLink = patientDoctorsLinkService.getList("selectByPatientPhoneAndDoctorsPhone", paramMap);
			if (listPatientDoctorsLink != null && listPatientDoctorsLink.size() > 0) {
				for (int i = 0; i < listPatientDoctorsLink.size(); i++) {
					DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone",
							listPatientDoctorsLink.get(i).getDoctorsPhone());// 查询医生资料
					listPatientDoctorsLink.get(i).setDoctorsUser(doctorsUser);
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
	 * 患者请求加医生为好友
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description = "患者请求加医生为好友")
	@RequestMapping(value = "requestJoining")
	@ResponseBody
	Results<String> requestJoining(HttpServletRequest request) {
		Results<String> results = new Results<String>();
	
		String doctorsLoginId = request.getParameter("doctorsLoginId");
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String myPhone = dataJsonObject.getString("phone");
				
		if (!StringUtils.isNotBlank(equipmentData) && !StringUtils.isNotBlank(myPhone) && !StringUtils.isNotBlank(doctorsLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		String doctorsPhone = null;
		try {
			doctorsPhone = doctorsLoginId.trim().split("_")[1];
		} catch (Exception e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_202);
			results.setMessage(MessageCode.MESSAGE_202);
			return results;
		}
		
		PatientUser patientUser = patientUserService.findByParam("selectByPhone", myPhone);
		DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
		if (patientUser == null || doctorsUser == null) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("尚未注册");
			return results;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctors_phone", doctorsPhone);
		paramMap.put("patient_phone", myPhone);
		
		PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);
		DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
		if (null == patientDoctorsLink_ && null == doctorsPatientLink_) {//没有记录，则插入
			//添加记录到patient_doctors_link表
			PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
			patientDoctorsLink.setCreatetime(new Date());
			patientDoctorsLink.setDoctorsId(doctorsUser.getId());
			patientDoctorsLink.setDoctorsLoginId(doctorsUser.getLoginId());
			patientDoctorsLink.setDoctorsName(doctorsUser.getName());
			patientDoctorsLink.setDoctorsPhone(doctorsPhone);
			
			patientDoctorsLink.setPatientId(patientUser.getId());
			patientDoctorsLink.setPatientLoginId(patientUser.getLoginId());
			patientDoctorsLink.setPatientName(patientUser.getName());
			patientDoctorsLink.setPatientPhone(myPhone);
			patientDoctorsLink.setType(3);
			patientDoctorsLinkService.insert(patientDoctorsLink);
			
			//添加记录到doctors_patient_link表
			DoctorsPatientLink doctorsPatientLink = new DoctorsPatientLink();
			doctorsPatientLink.setCreatetime(new Date());
			doctorsPatientLink.setDoctorsId(doctorsUser.getId());
			doctorsPatientLink.setDoctorsLoginId(doctorsUser.getLoginId());
			doctorsPatientLink.setDoctorsName(doctorsUser.getName());
			doctorsPatientLink.setDoctorsPhone(doctorsPhone);
			
			doctorsPatientLink.setPatientId(patientUser.getId());
			doctorsPatientLink.setPatientLoginId(patientUser.getLoginId());
			doctorsPatientLink.setPatientName(patientUser.getName());
			doctorsPatientLink.setPatientPhone(myPhone);
			doctorsPatientLink.setType(3);
			doctorsPatientLink.setGroupName("未分组患者");
			doctorsPatientLinkService.insert(doctorsPatientLink);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else if(patientDoctorsLink_!=null && 2==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 2==doctorsPatientLink_.getType()) {//有记录，为拒绝状态，则修改为未处理状态
			//修改记录到patient_doctors_link表
			patientDoctorsLink_.setType(3);
			patientDoctorsLink_.setUpdatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			patientDoctorsLinkService.update("updateByPrimaryKeySelective", patientDoctorsLink_);
			
			//修改记录到doctors_patient_link表
			doctorsPatientLink_.setType(3);
			doctorsPatientLink_.setGroupName("未分组患者");
			doctorsPatientLink_.setUpdatetime(new Date());
			doctorsPatientLinkService.update("updateByPrimaryKeySelective", doctorsPatientLink_);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else if (patientDoctorsLink_!=null && 3==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 3==doctorsPatientLink_.getType()) {//有记录，为未处理状态，则回复重复操作
			results.setCode(MessageCode.CODE_300);
			results.setMessage(MessageCode.MESSAGE_300);
			return results;
		} else if (patientDoctorsLink_!=null && 1==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 1==doctorsPatientLink_.getType()) {//有记录，为接受状态，则回复已是好友关系
			results.setCode(MessageCode.CODE_405);
			results.setMessage("已是好友关系");
			return results;
		} else if (patientDoctorsLink_!=null && 4==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 4==doctorsPatientLink_.getType()) {//有记录，为不再提示状态，则回复重复操作
			results.setCode(MessageCode.CODE_502);
			results.setMessage(MessageCode.MESSAGE_300);
			return results;
		} else {//其他情况，视为操作失败
			results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
			return results;
		}

	}

	/**
	 * 
	 * @Title: requestJoining @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
/*	@SystemControllerLog(description = "患者请求加医生为好友")
	@RequestMapping(value = "requestJoining")
	@ResponseBody
	Results<String> requestJoining(HttpServletRequest request) {
		Results<String> results = new Results<String>();
		
		String doctorsPhone = request.getParameter("doctorsPhone");
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String myPhone = dataJsonObject.getString("phone");
		String content = "{'msgType':'patientInfo','msgContent':'hello','chatRoomId':'','phone':'','doctorInfo':'','patientInfo':'patientInfo_entity'}";
		String sendContent = null;
				
		if (!StringUtils.isNotBlank(equipmentData) && !StringUtils.isNotBlank(myPhone) && !StringUtils.isNotBlank(doctorsPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		PatientUser patientUser = patientUserService.findByParam("selectByPhone", myPhone);
		if (patientUser != null) {
			patientUser.setPassword(null);
			patientUser.setLoginId(null);
			String strPatientUser = JSONObject.toJSONString(patientUser);
			sendContent = new String(content);
			sendContent = sendContent.replace("patientInfo_entity", strPatientUser);
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
			return results;
		}
		
		// 调用环信接口，發送添加請求給醫生
		Msg msg = new Msg();
        MsgContent msgContent = new MsgContent();
        msgContent.type(MsgContent.TypeEnum.TXT).msg(sendContent);
        UserName targetUser = new UserName();
        String targetName = "doctor_" + doctorsPhone;
        targetUser.add(targetName);
        String myName = "patient_" + myPhone;
        msg.from(myName).target(targetUser).targetType("users").msg(msgContent);
        Object result = easemobSendMessage.sendMessage(msg);

		if (result != null) {
			// 解释result
			JSONObject resultObject = JSONObject.parseObject(result.toString());
			JSONObject dataObject = resultObject.getJSONObject("data");
			String requestResult = dataObject.getString(targetName);
			if ("success".equals(requestResult)) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("环信发送消息失败");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("环信发送消息失败");
			return results;
		}
		
	}*/
	
	/**
	 * 患者成功添加医生为好友
	 * @param request
	 * @return
	 */
	/*@SystemControllerLog(description = "患者成功添加医生为好友")
	@RequestMapping(value = "addDoctorAsFriend")
	@ResponseBody
	Results<String> addDoctorAsFriend(HttpServletRequest request) {
		// 添加记录patient_doctors_link
		Results<String> results = new Results<String>();
		String doctorsId = request.getParameter("doctorsId");
		String doctorsLoginId = request.getParameter("doctorsLoginId");
		String doctorsName = request.getParameter("doctorsName");
		String doctorsPhone = request.getParameter("doctorsPhone");
		String patientId = request.getParameter("patientId");
		String patientLoginId = request.getParameter("patientLoginId");
		String patientName = request.getParameter("patientName");
		String patientPhone = request.getParameter("patientPhone");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doctorsPhone", doctorsPhone);
		map.put("patientPhone", patientPhone);
		PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService
				.findByParam("selectByPatientPhoneAndDoctorsPhone", map);
		if (patientDoctorsLink_ == null) {
			PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
			patientDoctorsLink.setCreatetime(new Date());
			if (StringUtils.isNotBlank(doctorsId)) {
				patientDoctorsLink.setDoctorsId(Integer.parseInt(doctorsId));
			}
			patientDoctorsLink.setDoctorsLoginId(doctorsLoginId);
			patientDoctorsLink.setDoctorsName(doctorsName);
			patientDoctorsLink.setDoctorsPhone(doctorsPhone);
			if (StringUtils.isNotBlank(patientId)) {
				patientDoctorsLink.setPatientId(Integer.parseInt(patientId));
			}
			patientDoctorsLink.setPatientLoginId(patientLoginId);
			patientDoctorsLink.setPatientName(patientName);
			patientDoctorsLink.setPatientPhone(patientPhone);
			patientDoctorsLinkService.insert(patientDoctorsLink);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
		} else {
			results.setCode(MessageCode.CODE_405);
			results.setMessage(MessageCode.MESSAGE_405);
		}

		return results;
	}*/
	
}
