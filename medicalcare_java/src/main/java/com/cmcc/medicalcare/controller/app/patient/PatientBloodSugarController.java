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
import com.cmcc.medicalcare.model.PatientBloodSugar;
import com.cmcc.medicalcare.service.IDoctorsFollowupPlanService;
import com.cmcc.medicalcare.service.IPatientBloodSugarService;

/** 
* @ClassName: 患者血糖
* @Description: TODO
* @author adminstrator
* @date 2017年4月10日 上午11:50:27 
*  
*/
@Controller
@RequestMapping("/app/patientBloodSugar")
public class PatientBloodSugarController {
	
	@Resource
	private IPatientBloodSugarService patientBloodSugarService;
	
	@Resource
	private IDoctorsFollowupPlanService doctorsFollowupPlanService;
	
	/**
	 * 查询患者血糖记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者血糖记录")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String planId) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		
		Integer p_Id = Integer.parseInt(planId.trim());
		List<PatientBloodSugar> patientBloodSugarList = patientBloodSugarService.getList("selectByPlanId", p_Id);
		if (patientBloodSugarList.size() > 0) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			resultsMap.put("patientBloodSugarList", patientBloodSugarList);
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
	 * 新增患者血糖记录
	 * @param request
	 * @param patientBloodPressure
	 * @return
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "新增患者血糖记录")
	public Results<String> insert(HttpServletRequest request, @RequestParam String patientBloodSugar) {
		Results<String> results = new Results<String>();
		JSONObject patientBloodSugarObject = JSONObject.parseObject(patientBloodSugar);
		if (patientBloodSugarObject != null) {
			String planId = patientBloodSugarObject.getString("planId");//获取计划id
			
			DoctorsFollowupPlan doctorsFollowupPlan = doctorsFollowupPlanService.findById(Integer.valueOf(planId));
			if (doctorsFollowupPlan != null) {
				//构造记录实体
				PatientBloodSugar patientBloodSugar_ = new PatientBloodSugar();
				patientBloodSugar_.setHigh(patientBloodSugarObject.getString("high"));
				patientBloodSugar_.setLow(patientBloodSugarObject.getString("low"));
				patientBloodSugar_.setPlanId(Integer.valueOf(planId));
				patientBloodSugar_.setDoctorsId(doctorsFollowupPlan.getDoctorsId());
				patientBloodSugar_.setDoctorsName(doctorsFollowupPlan.getDoctorsName());
				patientBloodSugar_.setDoctorsPhone(doctorsFollowupPlan.getDoctorsPhone());
				patientBloodSugar_.setPatientId(doctorsFollowupPlan.getPatientId());
				patientBloodSugar_.setPatientName(doctorsFollowupPlan.getPatientName());
				patientBloodSugar_.setPatientPhone(doctorsFollowupPlan.getPatientPhone());
				patientBloodSugar_.setNote(patientBloodSugarObject.getString("note"));
				patientBloodSugar_.setCreatetime(new Date());
				
				//记录入库
				patientBloodSugarService.insert(patientBloodSugar_);
				int id = patientBloodSugar_.getId();
				if (id > 0) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法新增患者血糖记录");
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
