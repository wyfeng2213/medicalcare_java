/**   
* @Title: PatientHealthRecordsController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:52:49 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.util.Date;
import java.util.HashMap;
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
import com.cmcc.medicalcare.model.PatientHealthRecords;
import com.cmcc.medicalcare.service.IPatientHealthRecordsService;


/**
 * @ClassName: 病人健康档案
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:52:49
 * 
 */
@Controller
@RequestMapping("/app/patientHealthRecords")
public class PatientHealthRecordsController {
	
	@Resource
	private IPatientHealthRecordsService patientHealthRecordsService;
	
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
	@SystemControllerLog(description = "查询患者健康档案")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String patientId) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		
		Integer p_Id = Integer.parseInt(patientId.trim());
		PatientHealthRecords patientHealthRecords = patientHealthRecordsService.findByParam("selectByPatientId", p_Id);
		if (patientHealthRecords != null) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			resultsMap.put("patientHealthRecords", patientHealthRecords);
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
	 * 保存患者健康档案
	 * @param request
	 * @param patientHealthRecords
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@SystemControllerLog(description = "保存患者健康档案")
	public Results<String> insertOrUpdate(HttpServletRequest request, @RequestParam String patientHealthRecords) {
		Results<String> results = new Results<String>();
		
		JSONObject patientHealthRecordsObject = JSONObject.parseObject(patientHealthRecords);
		if (patientHealthRecordsObject != null) {
			Integer p_Id = Integer.parseInt(patientHealthRecordsObject.getString("patientId"));
			PatientHealthRecords patientHealthRecords_ = patientHealthRecordsService.findByParam("selectByPatientId", p_Id);
			if (null == patientHealthRecords_) {
				PatientHealthRecords patientHealthRecords1 = new PatientHealthRecords();
				patientHealthRecords1.setPatientId(p_Id);
				patientHealthRecords1.setPatientName(patientHealthRecordsObject.getString("patientName"));
				patientHealthRecords1.setPatientPhone(patientHealthRecordsObject.getString("patientPhone"));
				patientHealthRecords1.setPatientLoginId(patientHealthRecordsObject.getString("patientLoginId"));
				patientHealthRecords1.setHeight(patientHealthRecordsObject.getString("height"));
				patientHealthRecords1.setWeight(patientHealthRecordsObject.getString("weight"));
				patientHealthRecords1.setSmokingHistory(patientHealthRecordsObject.getString("smokingHistory"));
				patientHealthRecords1.setDrinkingHistory(patientHealthRecordsObject.getString("drinkingHistory"));
				patientHealthRecords1.setSleepState(patientHealthRecordsObject.getString("sleepState"));;
				patientHealthRecords1.setStoolurineIsNormal(patientHealthRecordsObject.getString("stoolurineIsNormal"));
				patientHealthRecords1.setDrugHistory(patientHealthRecordsObject.getString("drugHistory"));
				patientHealthRecords1.setAllergicHistory(patientHealthRecordsObject.getString("allergicHistory"));
				patientHealthRecords1.setDietHistory(patientHealthRecordsObject.getString("dietHistory"));
				patientHealthRecords1.setCreatetime(new Date());
				patientHealthRecordsService.insert(patientHealthRecords1);
			} else {
				/////////////////////////////////////////////////
				patientHealthRecords_.setHeight(patientHealthRecordsObject.getString("height"));
				patientHealthRecords_.setWeight(patientHealthRecordsObject.getString("weight"));
				patientHealthRecords_.setSmokingHistory(patientHealthRecordsObject.getString("smokingHistory"));
				patientHealthRecords_.setDrinkingHistory(patientHealthRecordsObject.getString("drinkingHistory"));
				patientHealthRecords_.setSleepState(patientHealthRecordsObject.getString("sleepState"));;
				patientHealthRecords_.setStoolurineIsNormal(patientHealthRecordsObject.getString("stoolurineIsNormal"));
				patientHealthRecords_.setDrugHistory(patientHealthRecordsObject.getString("drugHistory"));
				patientHealthRecords_.setAllergicHistory(patientHealthRecordsObject.getString("allergicHistory"));
				patientHealthRecords_.setDietHistory(patientHealthRecordsObject.getString("dietHistory"));
				patientHealthRecords_.setUpdatetime(new Date());
				patientHealthRecordsService.update("updateByPrimaryKeySelective", patientHealthRecords_);
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}


	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#insert(javax.servlet.
	 * http.HttpServletRequest)
	 */
	public Results<String> insert(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (非 Javadoc) <p>Title: delete</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#delete(javax.servlet.
	 * http.HttpServletRequest)
	 */
	public Results<String> delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (非 Javadoc) <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#update(javax.servlet.
	 * http.HttpServletRequest)
	 */
	public Results<String> update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
