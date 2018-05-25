/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.SecretaryPatientLink;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.ISecretaryPatientLinkService;

/**
 * @ClassName: 秘书的医生
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/web/secretaryPatientLink")
public class SecretaryPatientLinkController {

	@Resource
	private ISecretaryPatientLinkService secretaryPatientLinkService;
	
	@Resource
	private IPatientUserService patientUserService;

	/**
	 * 
	 * @Title: addPatient @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@RequestMapping(value = "addPatient")
	@ResponseBody
	@SystemControllerLog(description = "秘书添加患者")
	Results<String> addPatient(HttpServletRequest request) {
		// 添加記錄secretary_patient_link
		Results<String> results = new Results<String>();
		String secretaryLoginId = request.getParameter("secretaryLoginId");
		String secretaryName = request.getParameter("secretaryName");
		String secretaryPhone = request.getParameter("secretaryPhone");
		String secretaryId = request.getParameter("secretaryId");

		String patientId = request.getParameter("patientId");
		String patientName = request.getParameter("patientName");
		String patientLoginId = request.getParameter("patientLoginId");
		String patientPhone = request.getParameter("patientPhone");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secretaryPhone", secretaryPhone);
		map.put("patientPhone", patientPhone);
		SecretaryPatientLink secretaryPatientLink_ = secretaryPatientLinkService
				.findByParam("selectBySecretaryPhoneAndPatientPhone", map);

		if (secretaryPatientLink_ == null) {
			SecretaryPatientLink secretaryPatientLink = new SecretaryPatientLink();
			if (StringUtils.isNotBlank(patientId)) {
				secretaryPatientLink.setPatientId(Integer.parseInt(patientId));
			}

			secretaryPatientLink.setPatientLoginId(patientLoginId);
			secretaryPatientLink.setPatientName(patientName);
			secretaryPatientLink.setPatientPhone(patientPhone);

			if (StringUtils.isNotBlank(secretaryId)) {
				secretaryPatientLink.setSecretaryId(Integer.parseInt(secretaryId));
			}

			secretaryPatientLink.setSecretaryLoginId(secretaryLoginId);
			secretaryPatientLink.setSecretaryName(secretaryName);
			secretaryPatientLink.setSecretaryPhone(secretaryPhone);
			secretaryPatientLink.setCreatetime(new Date());
			secretaryPatientLinkService.insert(secretaryPatientLink);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(null);
		} else {
			results.setCode(MessageCode.CODE_405);
			results.setMessage(MessageCode.MESSAGE_405);
			results.setData(null);
		}

		return results;
	}
	
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者资料")
	public Results<Map<String, Object>> select(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		PatientUser patientUser;
		String patientPhone = request.getParameter("patientPhone");
		if (StringUtils.isNotBlank(patientPhone)) {
			patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			if (patientUser != null) {
				patientUser.setPassword(null);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("patientUser", patientUser);
				results.setData(map);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				results.setData(null);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}
}
