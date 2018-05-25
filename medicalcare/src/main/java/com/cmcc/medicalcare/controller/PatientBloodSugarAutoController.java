package com.cmcc.medicalcare.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.inter.nianjia.NianjiaInterUtils;
import com.cmcc.medicalcare.model.PatientBloodPressureAuto;
import com.cmcc.medicalcare.model.PatientBloodSugarAuto;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IPatientBloodSugarAutoService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.Toolkit;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/patientBloodSugarAuto")
public class PatientBloodSugarAutoController extends BaseController{
	
	@Resource
	private IPatientBloodSugarAutoService patientBloodSugarAutoService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	
	/**
	 * parameter={"patientPhone":"","device_id":""}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "bindBloodSugarMeterDevice") 
	@ResponseBody 
	@SystemControllerLog(description = "绑定血糖仪设备")    
	public Results<String> bindBloodSugarMeterDevice(HttpServletRequest request, String parameter) {
		Results<String> results = new Results<String>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String device_id = dataJsonObject.getString("device_id");
		
		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(device_id)) {
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			if (patientUser != null) {
				patientUser.setBsDeviceid(device_id);
				patientUserService.update("updateByPrimaryKeySelective", patientUser);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_204);
				results.setMessage("用户不存在");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
	/**
	 * parameter={"device_id":"","pageIndex":"","pageSize":""}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "selectBloodSugarMeter") 
	@ResponseBody 
	@SystemControllerLog(description = "查询血糖仪数据")    
	public Results<Map<String, Object>> selectBloodSugarMeter(HttpServletRequest request, String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager<PatientBloodSugarAuto> page = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String device_id = dataJsonObject.getString("device_id");
		Long pageIndex = dataJsonObject.getLong("pageIndex");
		Long pageSize = dataJsonObject.getLong("pageSize");
		
		if (StringUtils.isNotBlank(device_id)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("device_id", device_id);
			page = patientBloodSugarAutoService.getList("selectByDeviceID", getPageIndex(pageIndex), getPageSize(pageSize), paramMap);
			
			if (page != null) {
				map.put("page", page);
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
	 * 保存血糖仪数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveBloodSugarMeter") 
	@ResponseBody 
	@SystemControllerLog(description = "保存血糖仪数据")    
	public Results<String> saveBloodSugarMeter(HttpServletRequest request) {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb=====================================");
		Results<String> results = new Results<String>();
		PatientBloodSugarAuto patientBloodSugarAuto = null;
		
		String device_name = request.getParameter("device_name");
		String device_id = request.getParameter("device_id");
		String uploadtime = request.getParameter("uploadtime");
		String bloodglucose = request.getParameter("bloodglucose");
		String status = request.getParameter("status");
		
		patientBloodSugarAuto = new PatientBloodSugarAuto();
		patientBloodSugarAuto.setDeviceName(device_name);
		patientBloodSugarAuto.setDeviceId(device_id);
		patientBloodSugarAuto.setUploadtime(uploadtime);
		patientBloodSugarAuto.setBloodglucose(bloodglucose);
		patientBloodSugarAuto.setStatus(status);
		patientBloodSugarAuto.setDataFromType(0);
		patientBloodSugarAuto.setCreatetime(new Date());
		try {
			if (StringUtils.isNotBlank(device_id)) patientBloodSugarAutoService.insert(patientBloodSugarAuto);
		} catch (Exception e) {
			results.setCode(500);
			results.setMessage("服务器异常！");
			return results;
		}
		
		results.setCode(200);
		results.setMessage("成功");
		return results;
		
	}
	
	/**
	 * 查询患者血糖监测数据
	 * 
	 * parameter={"patientPhone":"","scope":""}
	 * 
	 */
	@RequestMapping(value = "getMonitorBloodSugar")
	@ResponseBody
	@SystemControllerLog(description = "查询患者血糖监测数据")
	public Results<Map<String, Object>> getMonitorBloodSugar(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PatientBloodSugarAuto> patientBloodSugarList = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone"); //患者电话
		String scope = dataJsonObject.getString("scope"); //查询范围：当前一天date;当前一周week;所有all
		
		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(scope)) {
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			String device_id = patientUser.getBsDeviceid();
			if (StringUtils.isBlank(device_id)) {
				String httpUrl_nj = "http://app.careeach.com/app_api/v2.4.1/json_201411/getDeveice.jsp";
				String param = "action=deviceBindInfo&phone=" + patientPhone;
	    		String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, "UTF-8");
	    		if (StringUtils.isNotBlank(result_nj)) {
	    			JSONObject dataJson = JSONObject.parseObject(result_nj);
	    			String status = dataJson.getString("status");
					if ("200".equals(status)) {
						String data = dataJson.getString("data");
						if (data != null) {
							JSONArray dataJSONArray = JSONArray.fromObject(data);
							for (int i=0; i<dataJSONArray.size(); i++) {
								String entity = dataJSONArray.get(i).toString();
								JSONObject entityObject = JSONObject.parseObject(entity);
								String devType = entityObject.getString("devType");
								String devNo = (String) entityObject.getString("devNo");
								if ("2".equals(devType)) {
									device_id = devNo;
								}
							}
						}
					}
	    		}
	    		if (StringUtils.isBlank(device_id)) {
	    			results.setCode(MessageCode.CODE_204);
					results.setMessage("没有绑定血糖仪设备");
					return results;
	    		}
			}
			
			//根据设备号device_id取本地表-最大时间maxtime
			//若最大时间为空，去念加取全量数据
			//若最大时间不为空，去念加取maxtime+1秒到当前时间之间的增量数据
			PatientBloodSugarAuto PatientBloodSugarAuto_ = patientBloodSugarAutoService.findByParam("getMaxTimeByDeviceID", device_id);
			if (PatientBloodSugarAuto_ != null && StringUtils.isNotBlank(PatientBloodSugarAuto_.getUploadtime())) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date maxTime_d = null;
				try {
					maxTime_d = df.parse(PatientBloodSugarAuto_.getUploadtime());
				} catch (ParseException e) {
					e.printStackTrace();
					maxTime_d = new Date();
				}
				String beginTime = df.format(new Date(maxTime_d.getTime()+1000));
				String endTime = df.format(new Date());
				NianjiaInterUtils.insertHealthData(patientPhone,  device_id, "2", beginTime, endTime);
			} else {
				String beginTime = "";
				String endTime = "";
				NianjiaInterUtils.insertHealthData(patientPhone,  device_id, "2", beginTime, endTime);
			}
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("device_id", device_id);
			if ("date".equals(scope)) {
				paramMap.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//获取当前日期
				paramMap.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime() + 24 * 60 * 60 * 1000));//获取当前日期的下一天日期
				patientBloodSugarList = patientBloodSugarAutoService.getList("getBloodSugarOfCurrentDay", paramMap);
			} else if ("week".equals(scope)) {
				paramMap.put("startTime", Toolkit.getTimesWeekmorning()); //获取本周一日期
				paramMap.put("endTime",Toolkit.getTimesNextWeekmorning()); //获取下周一日期
				patientBloodSugarList = patientBloodSugarAutoService.getList("getAvgBloodSugarOfDay", paramMap);
			} else if ("all".equals(scope)) {
				patientBloodSugarList = patientBloodSugarAutoService.getList("getAllBloodSugarByPhone", paramMap);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage(MessageCode.MESSAGE_501);
				return results;
			}
			map.put("patientBloodSugarList", patientBloodSugarList);
			results.setData(map);
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
