/**   
* @Title: PatientBloodPressureController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:50:27 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientBloodPressure;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IDoctorsFollowupPlanService;
import com.cmcc.medicalcare.service.IPatientBloodPressureService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.Toolkit;

/** 
* @ClassName: 患者血压 
* @Description: TODO
* @author adminstrator
* @date 2017年4月10日 上午11:50:27 
*  
*/
@Controller
@RequestMapping("/app/patientBloodPressure")
public class PatientBloodPressureController {
	
	@Resource
	private IPatientBloodPressureService patientBloodPressureService;
	
	@Resource
	private IDoctorsFollowupPlanService doctorsFollowupPlanService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	/**
	 * 查询患者血压记录
	 * 
	 * parameter={"patientPhone":"","scope":""}
	 * 
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者血压记录")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PatientBloodPressure> patientBloodPressureList = null;
		PatientBloodPressure patientBloodPressure = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone"); //患者电话
		String scope = dataJsonObject.getString("scope"); //查询范围：当前一天date;当前一周week;所有all
		
		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(scope)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("patientPhone", patientPhone);
			if ("date".equals(scope)) {
//				paramMap.put("scope", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//				patientBloodPressureList = patientBloodPressureService.getList("getBloodPressureByScope", paramMap);
				paramMap.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//获取当前日期
				paramMap.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime() + 24 * 60 * 60 * 1000));//获取当前日期的下一天日期
				patientBloodPressureList = patientBloodPressureService.getList("getBloodPressureOfCurrentDay", paramMap);
			} else if ("week".equals(scope)) {
//				paramMap.put("scope", Toolkit.getCurrentMonday());
//				patientBloodPressureList = patientBloodPressureService.getList("getBloodPressureByScope", paramMap);
				//返回当前周，每天的平均数据
//				patientBloodPressureList = new ArrayList<PatientBloodPressure>();
//				for (int i=0; i<7; i++) {
//					paramMap.put("startTime", Toolkit.getDayOfCurrentWeek(i));//获取当前循环的日期
//					paramMap.put("endTime", Toolkit.getDayOfCurrentWeek(i+1));//获取当前循环的下一天日期
//					patientBloodPressure = patientBloodPressureService.findByParam("getAvgBloodPressureOfDay", paramMap);
//					try {
//						if (patientBloodPressure != null) patientBloodPressure.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").parse(Toolkit.getDayOfCurrentWeek(i)));
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if (patientBloodPressure != null) patientBloodPressureList.add(patientBloodPressure);
//				}
				paramMap.put("startTime", Toolkit.getTimesWeekmorning()); //获取本周一日期
				paramMap.put("endTime",Toolkit.getTimesNextWeekmorning()); //获取下周一日期
				patientBloodPressureList = patientBloodPressureService.getList("getAvgBloodPressureOfDay", paramMap);
				
			} else if ("all".equals(scope)) {
				patientBloodPressureList = patientBloodPressureService.getList("getAllBloodPressureByPhone", paramMap);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage(MessageCode.MESSAGE_501);
				return results;
			}
			if (patientBloodPressureList.size() > 0) {
				map.put("patientBloodPressureList", patientBloodPressureList);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
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
	
	/**
	 * 新增患者血压记录
	 * 
	 * parameter={"patientPhone":"","high":"","low":"","pulse":"","note":""}
	 * 
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "新增患者血压记录")
	public Results<Map<String, Object>> insert(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone"); //患者电话
		
		if (StringUtils.isNotBlank(patientPhone)) {
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			
			if (patientUser != null) {
				//构造记录实体
				String high = dataJsonObject.getString("high");
				String low = dataJsonObject.getString("low");
				PatientBloodPressure patientBloodPressure_ = new PatientBloodPressure();
				patientBloodPressure_.setHigh(high);
				patientBloodPressure_.setLow(low);
				Double high_ = Double.valueOf(high);
				Double low_ = Double.valueOf(low);
				if (high_ > 140 || low_ > 90) { //高血压
					patientBloodPressure_.setBloodPressureState(1);
				} else if (high_ < 90 || low_ < 60) { //低血压
					patientBloodPressure_.setBloodPressureState(2);
				} else { //正常血压
					patientBloodPressure_.setBloodPressureState(0);
				}
				patientBloodPressure_.setPulse(dataJsonObject.getString("pulse"));
				patientBloodPressure_.setPatientId(patientUser.getId());
				patientBloodPressure_.setPatientName(patientUser.getName());
				patientBloodPressure_.setPatientPhone(patientPhone);
				patientBloodPressure_.setNote(dataJsonObject.getString("note"));
				patientBloodPressure_.setCreatetime(new Date());
				
				//记录入库
				patientBloodPressureService.insert(patientBloodPressure_);
				Integer id = patientBloodPressure_.getId();
				if (id != null) {
					map.put("patientBloodPressure", patientBloodPressure_);
					results.setData(map);
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法新增患者血压记录");
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
	
	/**
	 * 删除患者血压记录
	 * 
	 * parameter={"bloodPressureId":""}
	 * 
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	@SystemControllerLog(description = "删除患者血压记录")
	public Results<Map<String, Object>> delete(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String bloodPressureId = dataJsonObject.getString("bloodPressureId"); //患者电话
		
		if (StringUtils.isNotBlank(bloodPressureId)) {
			PatientBloodPressure patientBloodPressure = patientBloodPressureService.findById("selectByPrimaryKey", Integer.valueOf(bloodPressureId));
			
			if (patientBloodPressure != null) {
				patientBloodPressureService.delete("deleteByPrimaryKey", patientBloodPressure);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
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
	
	/**
	 * 查询患者血压记录
	 * @param request
	 * @return
	 */
/*	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者血压记录")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String planId) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		
		Integer p_Id = Integer.parseInt(planId.trim());
		List<PatientBloodPressure> patientBloodPressureList = patientBloodPressureService.getList("selectByPlanId", p_Id);
		if (patientBloodPressureList.size() > 0) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			resultsMap.put("patientBloodPressureList", patientBloodPressureList);
			results.setData(resultsMap);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			results.setData(null);
			return results;
		}
		
	}*/

	/**
	 * 新增患者血压记录
	 * @param request
	 * @param patientBloodPressure
	 * @return
	 */
/*	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "新增患者血压记录")
	public Results<String> insert(HttpServletRequest request, @RequestParam String patientBloodPressure) {
		Results<String> results = new Results<String>();
		JSONObject patientBloodPressureObject = JSONObject.parseObject(patientBloodPressure);
		if (patientBloodPressureObject != null) {
			String planId = patientBloodPressureObject.getString("planId");//获取计划id
			
			DoctorsFollowupPlan doctorsFollowupPlan = doctorsFollowupPlanService.findById(Integer.valueOf(planId));
			if (doctorsFollowupPlan != null) {
				//构造记录实体
				PatientBloodPressure patientBloodPressure_ = new PatientBloodPressure();
				patientBloodPressure_.setHigh(patientBloodPressureObject.getString("high"));
				patientBloodPressure_.setLow(patientBloodPressureObject.getString("low"));
				patientBloodPressure_.setPulse(patientBloodPressureObject.getString("pulse"));
				patientBloodPressure_.setPlanId(Integer.valueOf(planId));
				patientBloodPressure_.setDoctorsId(doctorsFollowupPlan.getDoctorsId());
				patientBloodPressure_.setDoctorsName(doctorsFollowupPlan.getDoctorsName());
				patientBloodPressure_.setDoctorsPhone(doctorsFollowupPlan.getDoctorsPhone());
				patientBloodPressure_.setPatientId(doctorsFollowupPlan.getPatientId());
				patientBloodPressure_.setPatientName(doctorsFollowupPlan.getPatientName());
				patientBloodPressure_.setPatientPhone(doctorsFollowupPlan.getPatientPhone());
				patientBloodPressure_.setNote(patientBloodPressureObject.getString("note"));
				patientBloodPressure_.setCreatetime(new Date());
				
				//记录入库
				patientBloodPressureService.insert(patientBloodPressure_);
				int id = patientBloodPressure_.getId();
				if (id > 0) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法新增患者血压记录");
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
		
	}*/

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

	public static void main(String[] args) {
		System.out.println(Toolkit.getCurrentMonday());
		System.out.println(Toolkit.getSunday());
		System.out.println(Toolkit.getTimesWeekmorning());
		System.out.println(Toolkit.getTimesNextWeekmorning());
	}
}
