/**   
* @Title: PatientBloodPressureController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:50:27 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.util.Date;
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
import com.cmcc.medicalcare.model.DoctorsFollowupPlan;
import com.cmcc.medicalcare.model.PatientFastingWeight;
import com.cmcc.medicalcare.service.IDoctorsFollowupPlanService;
import com.cmcc.medicalcare.service.IPatientFastingWeightService;

/** 
* @ClassName: 患者空腹体重 
* @Description: TODO
* @author adminstrator
* @date 2017年4月10日 上午11:50:27 
*  
*/
@Controller
@RequestMapping("/app/patientFastingWeight")
public class PatientFastingWeightController {
	
	@Resource
	private IPatientFastingWeightService patientFastingWeightService;
	
	@Resource
	private IDoctorsFollowupPlanService doctorsFollowupPlanService;
	
	/**
	 * 查询患者空腹体重记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者空腹体重记录")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String planId) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		
		Integer p_Id = Integer.parseInt(planId.trim());
		List<PatientFastingWeight> patientFastingWeightList = patientFastingWeightService.getList("selectByPlanId", p_Id);
		if (patientFastingWeightList.size() > 0) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			resultsMap.put("patientBloodPressureList", patientFastingWeightList);
			results.setData(resultsMap);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			results.setData(null);
			return results;
		}
		
	}

	/**
	 * 新增患者空腹体重记录
	 * @param request
	 * @param patientBloodPressure
	 * @return
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "新增患者空腹体重记录")
	public Results<String> insert(HttpServletRequest request, @RequestParam String patientFastingWeight) {
		Results<String> results = new Results<String>();
		JSONObject patientFastingWeightObject = JSONObject.parseObject(patientFastingWeight);
		if (patientFastingWeightObject != null) {
			String planId = patientFastingWeightObject.getString("planId");//获取计划id
			
			DoctorsFollowupPlan doctorsFollowupPlan = doctorsFollowupPlanService.findById(Integer.valueOf(planId));
			if (doctorsFollowupPlan != null) {
				//构造记录实体
				PatientFastingWeight patientFastingWeight_ = new PatientFastingWeight();
				patientFastingWeight_.setWeight(patientFastingWeightObject.getString("weight"));
				patientFastingWeight_.setPlanId(Integer.valueOf(planId));
				patientFastingWeight_.setDoctorsId(doctorsFollowupPlan.getDoctorsId());
				patientFastingWeight_.setDoctorsName(doctorsFollowupPlan.getDoctorsName());
				patientFastingWeight_.setDoctorsPhone(doctorsFollowupPlan.getDoctorsPhone());
				patientFastingWeight_.setPatientId(doctorsFollowupPlan.getPatientId());
				patientFastingWeight_.setPatientName(doctorsFollowupPlan.getPatientName());
				patientFastingWeight_.setPatientPhone(doctorsFollowupPlan.getPatientPhone());
				patientFastingWeight_.setNote(patientFastingWeightObject.getString("note"));
				patientFastingWeight_.setCreatetime(new Date());
				
				//记录入库
				patientFastingWeightService.insert(patientFastingWeight_);
				int id = patientFastingWeight_.getId();
				if (id > 0) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法新增患者空腹体重记录");
					return results;
				}
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

	/* (非 Javadoc) 
	* <p>Title: delete</p> 
	* <p>Description: </p> 
	* @param request
	* @return 
	* @see com.cmcc.medicalcare.controller.AppBaseController#delete(javax.servlet.http.HttpServletRequest) 
	*/
	public Results<String> delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (非 Javadoc) 
	* <p>Title: update</p> 
	* <p>Description: </p> 
	* @param request
	* @return 
	* @see com.cmcc.medicalcare.controller.AppBaseController#update(javax.servlet.http.HttpServletRequest) 
	*/
	public Results<String> update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
