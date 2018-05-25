/**   
* @Title: DoctorsReferralController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:55:17 
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsTeamInvite;
import com.cmcc.medicalcare.model.SecretaryDoctorsAdvice;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsAdviceLinkService;

/**
 * @ClassName: DoctorsAdviceController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:55:17
 * 
 */
@Controller
@RequestMapping("/app/doctorsAdvice")
public class DoctorsAdviceController {

	@Resource
	private ISecretaryDoctorsAdviceLinkService secretaryDoctorsAdviceLinkService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	/**
	 * parameter={"adviceId":""} @Title: selectDoctorAdvice @Description:
	 * TODO @param @param parameter @param @param file @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "查看转医生处理意见单")
	@RequestMapping({ "selectDoctorAdvice" })
	@ResponseBody
	public Results<Map<String, Object>> selectDoctorAdvice(@RequestParam String parameter, MultipartFile file,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String id = dataJsonObject.getString("adviceId");
		SecretaryDoctorsAdvice secretaryDoctorsAdvice = null;
		if (StringUtils.isNotBlank(id)) {
			secretaryDoctorsAdvice = secretaryDoctorsAdviceLinkService.findById("selectByPrimaryKey",
					Integer.parseInt(id));
//			secretaryDoctorsAdvice.setType(1);
//			secretaryDoctorsAdviceLinkService.update("updateByPrimaryKey", secretaryDoctorsAdvice);
			results.setCode(MessageCode.CODE_200);
			map.put("secretaryDoctorsAdvice", secretaryDoctorsAdvice);
			results.setData(map);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"adviceId":"2","illness":""} @Title:
	 * updateDoctorAdvice @Description: TODO @param @param
	 * parameter @param @param file @param @param request @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "填写问诊记录")
	@RequestMapping({ "updateDoctorAdvice" })
	@ResponseBody
	public Results<Map<String, Object>> updateDoctorAdvice(@RequestParam String parameter, MultipartFile file,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String id = dataJsonObject.getString("adviceId");
		String illness = dataJsonObject.getString("illness");

		SecretaryDoctorsAdvice secretaryDoctorsAdvice = null;
		if (StringUtils.isNotBlank(id)) {
			secretaryDoctorsAdvice = secretaryDoctorsAdviceLinkService.findById("selectByPrimaryKey",
					Integer.parseInt(id));
			secretaryDoctorsAdvice.setType(1);
			secretaryDoctorsAdvice.setIllness(illness);
			secretaryDoctorsAdvice.setUpdatetime(new Date());
			secretaryDoctorsAdviceLinkService.update("updateByPrimaryKey", secretaryDoctorsAdvice);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

}
