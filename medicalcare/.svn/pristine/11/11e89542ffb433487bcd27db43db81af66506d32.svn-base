/**   
* @Title: H5DoctorsUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:32:23 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.h5;

import java.util.ArrayList;
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
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 医生信息
 * @Description: TODO
 * @author siming.wu
 * @date 2017年4月10日 上午10:32:23
 * 
 */
@Controller
@RequestMapping("/h5/doctorsUser")
public class H5DoctorsUserController {

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;

	/*
	 * (非 Javadoc) <p>Title: select</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#select(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询医生资料")
	public Results<Map<String, Object>> select(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// List<DoctorsPatientLink> listDoctorsPatientLink = null;
		DoctorsUser doctorsUser;
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone;
		if (dataJsonObject != null) {
			phone = dataJsonObject.getString("phone");
			if (StringUtils.isNotBlank(phone)) {
				doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);

				// Map<String, Object> paramMap = new HashMap<String, Object>();
				// paramMap.put("doctorsPhone", phone);
				// listDoctorsPatientLink =
				// doctorsPatientLinkService.getList("selectByPhone", phone);

				if (doctorsUser != null) {
					Map<String, Object> paramMap1 = new HashMap<String, Object>();
					paramMap1.put("doctorsPhone", phone);
					// paramMap1.put("secretaryPhone", "12345678912");
					List<SecretaryDoctorsLink> secretaryDoctorsLinkList = secretaryDoctorsLinkService
							.getList("selectBySecretaryPhoneAndDoctorsPhone", paramMap1);

					SecretaryDoctorsLink secretaryDoctorsLink = secretaryDoctorsLinkList.get(0);

//					try {
//						Map<String, Object> ext = new HashMap<String, Object>();
//						ext.put("nickName", "医生助理");
//						EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//						Msg msg = new Msg();
//						MsgContent msgContent = new MsgContent();
//
//						msgContent.type(MsgContent.TypeEnum.TXT).msg("欢迎回来");
//						UserName userName2 = new UserName();
//						userName2.add(Constant.doctor + phone);
//						msg.from(Constant.secretary + Constant.secretaryPhone).target(userName2).targetType("users")
//								.msg(msgContent).ext(ext);
//						Object result = easemobSendMessage.sendMessage(msg);
//					} catch (Exception e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}

					DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPhone", phone);
					;
					if (doctorsTeam != null)
						map.put("leaderTeamId", doctorsTeam.getId()); // 返回该医生领队的团队id
					map.put("secretaryDoctorsLink", secretaryDoctorsLink);
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("doctorsUser", doctorsUser);
					// int typeNum = listDoctorsPatientLink.size();
					// map.put("typeNum", typeNum);//未处理“患者请求添加好友信息”条数
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("需要认证资料");
					results.setData(null);
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_201);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_201);
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
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生主页")
	@RequestMapping(value = "doctorMainInfo")
	@ResponseBody
	public Results<Map<String, Object>> doctorMainInfo(HttpServletRequest request,@RequestParam String doctorsLoginId) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorsUser doctorsUser = null;
		DoctorsTeam doctorsLeaderTeam = null;
		List<DoctorsTeam> joinerTeamList = new ArrayList<DoctorsTeam>();
		

		if (StringUtils.isNotBlank(doctorsLoginId)&&doctorsLoginId.contains("_")) {
			int index = doctorsLoginId.indexOf("_")+1;
			String doctorsPhone = doctorsLoginId.substring(index);
			doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			if (doctorsUser != null) {

				doctorsLeaderTeam = doctorsTeamService.findByParam("selectByPhone", doctorsPhone);

				List<DoctorsTeamUserLink> doctorsTeamUserLinkList = doctorsTeamUserLinkService.getList("getJoinerList",
						doctorsPhone);
				if (doctorsTeamUserLinkList.size() > 0) {
					DoctorsTeam doctorsTeam = null;
					for (int i = 0; i < doctorsTeamUserLinkList.size(); i++) {
						doctorsTeam = doctorsTeamService.findByParam("selectByTeamId",
								doctorsTeamUserLinkList.get(i).getTeamId());
						joinerTeamList.add(doctorsTeam);
					}
				}
				map.put("doctorsUser", doctorsUser);
				map.put("doctortype", doctorsUser.getDoctortype());// 1为普通医生、
																	// 2为签约医生
				map.put("doctorsLeaderTeam", doctorsLeaderTeam);// 自己团队信息
				map.put("joinerTeamList", joinerTeamList);// 加入的团队信息
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该医生尚未注册");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

}
