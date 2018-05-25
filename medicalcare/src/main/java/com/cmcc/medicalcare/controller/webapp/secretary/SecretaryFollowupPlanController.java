/**   
* @Title: SecretaryFollowupPlanController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:37:11 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.ArrayList;
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
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;

/**
 * @ClassName: 随访计划
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月10日 上午11:37:11
 * 
 */
@Controller
@RequestMapping("/web/secretaryFollowupPlan")
public class SecretaryFollowupPlanController {

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	/**
	 * parameter={"patientPhone":""}
	 * 
	 * @Title: assignDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "指定医师")
	@RequestMapping({ "assignDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> assignDoctor(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("patient_phone", patientPhone);

		List<PatientDoctorsLink> patientDoctorsLink = patientDoctorsLinkService
				.getList("selectByPatientPhoneAndDoctorsPhone", paramMap);

		if (patientDoctorsLink != null && patientDoctorsLink.size() > 0) {
			for (int i = 0; i < patientDoctorsLink.size(); i++) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("doctorsName", patientDoctorsLink.get(i).getDoctorsName());
				map2.put("doctorsPhone", patientDoctorsLink.get(i).getDoctorsPhone());
				map2.put("doctorsLoginId", patientDoctorsLink.get(i).getDoctorsLoginId());
				list.add(map2);
			}
		}
		map.put("list", list);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"patientName":"","patientPhone":"","doctorsPhone":"",
	 * "doctorsName":"", "secretaryName":"","secretaryPhone":""} @Title:
	 * addFollowupPlan @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "添加随访计划")
	@RequestMapping({ "addFollowupPlan" })
	@ResponseBody
	public Results<Map<String, Object>> addFollowupPlan(@RequestParam String parameter, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientName = dataJsonObject.getString("patientName");
		String patientPhone = dataJsonObject.getString("patientPhone");

		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String doctorsName = dataJsonObject.getString("doctorsName");

		String record = dataJsonObject.getString("record");

		String secretaryName = dataJsonObject.getString("secretaryName");
		String secretaryPhone = dataJsonObject.getString("secretaryPhone");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctorsPhone", doctorsPhone);
		paramMap.put("patientPhone", patientPhone);
		paramMap.put("type", 1);

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"patientName":"","patientPhone":"","doctorsPhone":"",
	 * "doctorsName":"", "secretaryName":"","secretaryPhone":""}
	 * 
	 * @Title: selectFollowupPlanBySecretary @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         pageSize @param @param pageIndex @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书查看病人的随访计划")
	@RequestMapping({ "selectFollowupPlanBySecretary" })
	@ResponseBody
	public Results<Map<String, Object>> selectFollowupPlanBySecretary(@RequestParam String parameter,
			HttpServletRequest request, @RequestParam(required = false) Long pageSize,
			@RequestParam(required = false) Long pageIndex) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientName = dataJsonObject.getString("patientName");
		String patientPhone = dataJsonObject.getString("patientPhone");

		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String doctorsName = dataJsonObject.getString("doctorsName");

		String record = dataJsonObject.getString("record");

		String secretaryName = dataJsonObject.getString("secretaryName");
		String secretaryPhone = dataJsonObject.getString("secretaryPhone");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctorsPhone", doctorsPhone);
		paramMap.put("patientPhone", patientPhone);
		paramMap.put("type", 1);

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

}
