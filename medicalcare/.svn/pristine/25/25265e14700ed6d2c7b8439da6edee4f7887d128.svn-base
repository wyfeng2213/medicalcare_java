/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;

/**
 * @ClassName: 秘书的医生
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/web/secretaryDoctorsLink")
public class SecretaryDoctorsLinkController {

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	/**
	 * parameter={"secretaryPhone":""}
	 * @Title: getDoctorList @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书获取医生单聊列表")
	@RequestMapping(value = "getDoctorList")
	@ResponseBody
	Results<Map<String, Object>> getDoctorList(@RequestParam String parameter, HttpServletRequest request) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String secretaryPhone = dataJsonObject.getString("secretaryPhone");
		Map<String, Object> map = new HashMap<String, Object>();

		List<SecretaryDoctorsLink> secretaryDoctorsLinkList = secretaryDoctorsLinkService
				.getList("selectBySecretaryPhone", secretaryPhone);

		if (secretaryDoctorsLinkList != null && secretaryDoctorsLinkList.size() > 0) {

			for (int i = 0; i < secretaryDoctorsLinkList.size(); i++) {
				String doctors_Phone = secretaryDoctorsLinkList.get(i).getDoctorsPhone();
				DoctorsUser doctorsUser2 = doctorsUserService.findByParam("selectByDoctorsPhone", doctors_Phone);
				doctorsUser2.setPassword(null);
				secretaryDoctorsLinkList.get(i).setDoctorsUser(doctorsUser2);
			}

			map.put("secretaryDoctorsLinkList", secretaryDoctorsLinkList);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(map);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		}

	}
	
	
	/**
	 * parameter={"doctorsLoginId":""}
	 * 
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书获取医生主页信息")
	@RequestMapping(value = "doctorMainInfo")
	@ResponseBody
	public Results<Map<String, Object>> doctorMainInfo(HttpServletRequest request, String parameter) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorsUser doctorsUser = null;
		DoctorsTeam doctorsLeaderTeam = null;
		List<DoctorsTeam> joinerTeamList = new ArrayList<DoctorsTeam>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");

		if (StringUtils.isNotBlank(doctorsLoginId)&&doctorsLoginId.contains("_")) {
			int beginIndex = doctorsLoginId.indexOf("_")+1;
			String doctorsPhone = doctorsLoginId.substring(beginIndex);
			doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			if (doctorsUser != null) {
				doctorsUser.setPassword(null);
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
				map.put("doctorType", doctorsUser.getDoctortype());// 1为普通医生、
																	// 2为签约医生
				map.put("doctorsLeaderTeam", doctorsLeaderTeam);//自己团队信息
				map.put("joinerTeamList", joinerTeamList);//加入的团队信息
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
