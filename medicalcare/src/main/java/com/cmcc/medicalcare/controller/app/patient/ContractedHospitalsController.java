/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

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
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.ContractedHospitalsDoctorsLink;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.service.IContractedHospitalsDoctorsLinkService;
import com.cmcc.medicalcare.service.IContractedHospitalsService;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;

/**
 * @ClassName: 签约医院
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/app/contractedHospitals")
public class ContractedHospitalsController {

	@Resource
	private IContractedHospitalsService contractedHospitalsService;

	@Resource
	private IContractedHospitalsDoctorsLinkService contractedHospitalsDoctorsLinkService;

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	/**
	 * parameter={"cityName":"佛山","area":"南海"} @Title:
	 * chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "chooseContractedHospitals")
	@ResponseBody
	@SystemControllerLog(description = "患者选择签约医院")
	public Results<Map<String, Object>> chooseContractedHospitals(HttpServletRequest request,
			@RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String cityName = dataJsonObject.getString("cityName");
		String area = dataJsonObject.getString("area");

		if (StringUtils.isNotBlank(cityName) && StringUtils.isNotBlank(area)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cityName", cityName);
			paramMap.put("area", area);
			List<ContractedHospitals> list = contractedHospitalsService.getList("selectByCityNameAndArea", paramMap);

			if (list == null || list.size() < 1) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				return results;
			}

			resultsMap.put("ContractedHospitalsList", list);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(resultsMap);
			return results;

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"hospitalId":""} @Title: chooseContractedDoctor @Description:
	 * TODO @param @param request @param @param parameter @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "chooseContractedDoctor")
	@ResponseBody
	@SystemControllerLog(description = "患者选择签约医生")
	public Results<Map<String, Object>> chooseContractedDoctor(HttpServletRequest request,
			@RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String hospitalId = dataJsonObject.getString("hospitalId");
		List<DoctorsUser> doctorsUserList = new ArrayList<DoctorsUser>();
		DoctorsTeam doctorsTeam = null;
		List<DoctorsPatientLink> doctorsPatientLinkList = new ArrayList<DoctorsPatientLink>();
		List<DoctorsTeamUserLink> teammateList = new ArrayList<DoctorsTeamUserLink>();

		if (StringUtils.isNotBlank(hospitalId)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hospitalId", Integer.parseInt(hospitalId));
			List<ContractedHospitalsDoctorsLink> list = contractedHospitalsDoctorsLinkService.getList("selectByHospitalId", paramMap);

			if (list == null || list.size() < 1) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				return results;
			}

			for (int i = 0; i < list.size(); i++) {
				String phone = list.get(i).getDoctorPhone();
				System.out.println("phone: "+phone);
				DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);

				Map<String, Object> paramMap_ = new HashMap<String, Object>();
				paramMap_.put("doctors_phone", phone);
//				paramMap_.put("doctorType", 2);
				paramMap_.put("type", 1);
				doctorsPatientLinkList = doctorsPatientLinkService.getList("selectContracted", paramMap_);
				int doctorsPatientLinkCount = doctorsPatientLinkList.size();// 获取已签约人数
				doctorsUser.setDoctorsPatientLinkCount(doctorsPatientLinkCount);

				doctorsTeam = doctorsTeamService.findByParam("getDoctorsTeamInfo", phone);// 获取医生自己团队信息
				doctorsUser.setDoctorsTeam(doctorsTeam);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("team_id", doctorsTeam.getId());
				teammateList = doctorsTeamUserLinkService.getList("getTeammateByTeamId", params);
				int doctorsTeamUserLinkCount = teammateList.size();// 获取团队成员人数
				doctorsUser.setDoctorsTeamUserLinkCount(doctorsTeamUserLinkCount);

				doctorsUserList.add(doctorsUser);
			}

			resultsMap.put("doctorsUserList", doctorsUserList);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(resultsMap);
			return results;

		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

}
