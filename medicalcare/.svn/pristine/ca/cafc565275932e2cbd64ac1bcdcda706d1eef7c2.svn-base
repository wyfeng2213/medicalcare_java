/**   
* @Title: H5PatientDoctorsLinkController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:36:59 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.h5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientUserService;

/**
 * @ClassName: 患者的医生
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:36:59
 * 
 */
@Controller
@RequestMapping("/h5/patientDoctorsLink")
public class H5PatientDoctorsLinkController {

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private IPatientUserService patientUserService;

	@Resource
	private IDoctorsUserService doctorsUserService;

	/**
	 * parameter={"doctorsLoginId":"","patientPhone":""} @Title:
	 * checkDoctorPatientLink @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生与患者是否存在好友关系")
	@RequestMapping(value = "checkDoctorPatientLink")
	@ResponseBody
	public Results<Map<String, Object>> checkDoctorPatientLink(@RequestParam String doctorsLoginId,
			@RequestParam String patientPhone, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();

		int index = doctorsLoginId.indexOf("_") + 1;
		String doctorsPhone = doctorsLoginId.substring(index);

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("doctors_phone", doctorsPhone);
		paramsMap.put("patient_phone", patientPhone);
		paramsMap.put("type", 1);
		List<DoctorsPatientLink> doctorsPatientLinkList = doctorsPatientLinkService
				.getList("selectByDoctorsPhoneAndPatientPhone", paramsMap);

		if (doctorsPatientLinkList != null && doctorsPatientLinkList.size() > 0) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		}
	}
}
