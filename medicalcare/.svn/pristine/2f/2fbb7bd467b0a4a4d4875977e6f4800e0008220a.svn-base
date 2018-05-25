package com.cmcc.medicalcare.controller.inter;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.HealthDeviceInfo;
import com.cmcc.medicalcare.model.PatientBloodPressureAuto;
import com.cmcc.medicalcare.model.PatientBloodSugarAuto;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IHealthDeviceInfoService;
import com.cmcc.medicalcare.service.IPatientBloodPressureAutoService;
import com.cmcc.medicalcare.service.IPatientBloodSugarAutoService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.HTTPUtils;


@Controller
@RequestMapping("/inter/deviceManage")
public class DeviceManageController extends BaseController{
	
	@Resource
	private IPatientBloodPressureAutoService patientBloodPressureAutoService;
	
	@Resource
	private IPatientBloodSugarAutoService patientBloodSugarAutoService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IHealthDeviceInfoService healthDeviceInfoService;
	
	public static String httpEncoding = "UTF-8";


	private static String httpUrl_nj = "http://app.careeach.com/app_api/v2.4.1/json_201411/getDeveice.jsp";
	
	
	/**
	 * 保存测量设备数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveDeviceData") 
	@ResponseBody 
	@SystemControllerLog(description = "保存设备数据")
	public Results<String> saveDeviceData(HttpServletRequest request) {
		Results<String> results = new Results<String>();
		
		String device_from = request.getParameter("from");
		String device_type = request.getParameter("type");
		String device_data = request.getParameter("data");
		
		if (StringUtils.isNotBlank(device_type) && StringUtils.isNotBlank(device_data)) {
			if ("1".equals(device_type)) {
				JSONObject dataJsonObject = JSONObject.parseObject(device_data);
				String bp_name = dataJsonObject.getString("bp_name");
				String bp_deviceID = dataJsonObject.getString("deviceid");
				String bp_measuretime = dataJsonObject.getString("bp_measuretime");
				String bp_sbp = dataJsonObject.getString("bp_sbp");
				String bp_dbp = dataJsonObject.getString("bp_dbp");
				String bp_pulse = dataJsonObject.getString("bp_pulse");
				String bp_mp = dataJsonObject.getString("bp_mp");
				String bp_status = dataJsonObject.getString("bp_status");
				
				Map<String, Object> paramMap1 = new HashMap<String, Object>();
				paramMap1.put("deviceId", bp_deviceID);
				paramMap1.put("deviceType", 0); //血压
				paramMap1.put("deviceFrom", 1); //念加
				List<HealthDeviceInfo> healthDeviceInfoList = healthDeviceInfoService.getList("getDeviceInfo", paramMap1);
				String patientPhone = "";
				if (healthDeviceInfoList.size() > 0) {
					patientPhone = healthDeviceInfoList.get(0).getPatientPhone();
				}
				
				PatientBloodPressureAuto patientBloodPressureAuto = new PatientBloodPressureAuto();
				patientBloodPressureAuto.setBpName(bp_name);
				patientBloodPressureAuto.setBpDeviceid(bp_deviceID);
				patientBloodPressureAuto.setDeviceFrom(device_from);
				patientBloodPressureAuto.setBpMeasuretime(bp_measuretime);
				patientBloodPressureAuto.setBpSbp(bp_sbp);
				patientBloodPressureAuto.setBpDbp(bp_dbp);
				patientBloodPressureAuto.setBpPulse(bp_pulse);
				patientBloodPressureAuto.setBpMp(bp_mp);
				patientBloodPressureAuto.setBpStatus(bp_status);
				patientBloodPressureAuto.setDataFromType(0);
				patientBloodPressureAuto.setPatientPhone(patientPhone);
				patientBloodPressureAuto.setDeviceFromType(1);
				patientBloodPressureAuto.setCreatetime(new Date());
				try {
					if (StringUtils.isNotBlank(bp_deviceID)) patientBloodPressureAutoService.insert(patientBloodPressureAuto);
				} catch (Exception e) {
					e.printStackTrace();
					results.setCode(500);
					results.setMessage("服务器异常！");
					return results;
				}
				
				results.setCode(200);
				results.setMessage("成功");
				return results;
			} else if ("2".equals(device_type)) {
				JSONObject dataJsonObject = JSONObject.parseObject(device_data);
				String device_name = dataJsonObject.getString("device_name");
				String device_id = dataJsonObject.getString("device_id");
				String uploadtime = dataJsonObject.getString("uploadtime");
				String bloodglucose = dataJsonObject.getString("bloodglucose");
				String status = dataJsonObject.getString("status");
				
				PatientBloodSugarAuto patientBloodSugarAuto = new PatientBloodSugarAuto();
				patientBloodSugarAuto.setDeviceName(device_name);
				patientBloodSugarAuto.setDeviceId(device_id);
				patientBloodSugarAuto.setDeviceFrom(device_from);
				patientBloodSugarAuto.setUploadtime(uploadtime);
				patientBloodSugarAuto.setBloodglucose(bloodglucose);
				patientBloodSugarAuto.setStatus(status);
				patientBloodSugarAuto.setDataFromType(0);
				patientBloodSugarAuto.setCreatetime(new Date());
				try {
					if (StringUtils.isNotBlank(device_id)) patientBloodSugarAutoService.insert(patientBloodSugarAuto);
				} catch (Exception e) {
					e.printStackTrace();
					results.setCode(500);
					results.setMessage("服务器异常！");
					return results;
				}
				
				results.setCode(200);
				results.setMessage("成功");
				return results;
			} else if ("3".equals(device_type)) {
				results.setCode(402);
				results.setMessage("用户未绑定设备");
				return results;
			} else {
				results.setCode(403);
				results.setMessage("设备不存在");
				return results;
			}
		} else {
			results.setCode(403);
			results.setMessage("设备不存在");
			return results;
		}
	}
	
	/**
	 * 粤健康通过手机号查询念加用户绑定的设备信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deviceBindInfo") 
	@ResponseBody 
	@SystemControllerLog(description = "设备绑定信息")
    public Results<Map<String, Object>> deviceBindInfo(HttpServletRequest request) {
    	Results<Map<String, Object>> results = new Results<Map<String,Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	String phone = request.getParameter("phone");
    	if (StringUtils.isNotBlank(phone)) {
    		String param = "action=deviceBindInfo&phone=" + phone.trim();
    		
    		String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, httpEncoding);
    		if (StringUtils.isNotBlank(result_nj)) {
    			JSONObject dataJsonObject = JSONObject.parseObject(result_nj);
    			String status = dataJsonObject.getString("status");
				String msg = dataJsonObject.getString("msg");
				if ("200".equals(status)) {
					String data = dataJsonObject.getString("data");
					map.put("deviceBindInfo", data);
		    		results.setData(map);
		    		results.setCode(MessageCode.CODE_200);
					results.setMessage(msg);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage(msg);
					return results;
				}
    		} else {
    			results.setCode(MessageCode.CODE_501);
    			results.setMessage("请求念加服务失败");
    			return results;
    		}
    	} else {
    		results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
    	}
    	
    }
	
	/**
	 * 查询念加健康设备数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deviceData") 
	@ResponseBody 
	@SystemControllerLog(description = "查询念加健康设备数据")
    public Results<Map<String, Object>> deviceData(HttpServletRequest request) {
    	Results<Map<String, Object>> results = new Results<Map<String,Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	String phone = request.getParameter("phone");
    	String devNo = request.getParameter("devNo");
    	String devType = request.getParameter("devType");
    	String beginTime = request.getParameter("beginTime");
    	String endTime = request.getParameter("endTime");
    	if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(devNo) && StringUtils.isNotBlank(devType)) {
    		String param = "action=deviceData&phone=" + phone.trim() + "&devNo=" + devNo.trim();
    		
    		if ("1".equals(devType)) {
    			param += "&devType=1";
    		} else if ("2".equals(devType)) {
    			param += "&devType=2";
    		} else {
    			results.setCode(MessageCode.CODE_501);
				results.setMessage("用户未绑定设备");
				return results;
    		}
    		
    		if (StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)) {
    			try {
    				String beginTime_fm = df.format(df.parse(beginTime));
    				String endTime_fm = df.format(df.parse(endTime));
    				param += "&beginTime=" + beginTime_fm.trim() + "&endTime=" + endTime_fm.trim();
				} catch (ParseException e) {
					e.printStackTrace();
					results.setCode(MessageCode.CODE_501);
					results.setMessage("日期格式错误");
					return results;
				}
    		} else {
    			param += "&beginTime=-1&endTime=-1";
    		}
    		
    		param = param.replace(" ", "%20");
    		
    		String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, httpEncoding);
    		if (StringUtils.isNotBlank(result_nj)) {
    			JSONObject dataJsonObject = JSONObject.parseObject(result_nj);
    			String status = dataJsonObject.getString("status");
				String msg = dataJsonObject.getString("msg");
				if ("200".equals(status)) {
					String data = dataJsonObject.getString("data");
					map.put("deviceData", data);
		    		results.setData(map);
		    		results.setCode(MessageCode.CODE_200);
					results.setMessage(msg);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage(msg);
					return results;
				}
    		} else {
    			results.setCode(MessageCode.CODE_501);
    			results.setMessage("请求念加服务失败");
    			return results;
    		}
    	} else {
    		results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
    	}
    	
    }
	
	/**
	 * 通过设备编号查询设备信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deviceInfo") 
	@ResponseBody 
	@SystemControllerLog(description = "查询设备信息")
    public Results<Map<String, Object>> deviceInfo(HttpServletRequest request) {
    	Results<Map<String, Object>> results = new Results<Map<String,Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	String devNo = request.getParameter("devNo");
    	if (StringUtils.isNotBlank(devNo)) {
    		String param = "action=deviceInfo&devNo=" + devNo.trim();
    		
    		String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, httpEncoding);
    		if (StringUtils.isNotBlank(result_nj)) {
    			JSONObject dataJsonObject = JSONObject.parseObject(result_nj);
    			String status = dataJsonObject.getString("status");
				String msg = dataJsonObject.getString("msg");
				if ("200".equals(status)) {
					String data = dataJsonObject.getString("data");
					map.put("deviceInfo", data);
		    		results.setData(map);
		    		results.setCode(MessageCode.CODE_200);
					results.setMessage(msg);
					return results;
				} else if ("300".equals(status)) {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("设备不存在");
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage(msg);
					return results;
				}
    		} else {
    			results.setCode(MessageCode.CODE_501);
    			results.setMessage("请求念加服务失败");
    			return results;
    		}
    	} else {
    		results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
    	}
    	
    }
	
	/**
	 * parameter={"patientPhone":"","deviceType":""}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "unbundledDevice") 
	@ResponseBody 
	@SystemControllerLog(description = "解绑设备")    
	public Results<String> unbundledDevice(HttpServletRequest request, String parameter) {
		Results<String> results = new Results<String>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String deviceType = dataJsonObject.getString("deviceType");
		
		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(deviceType)) {
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			if (patientUser != null) {
				if ("1".equals(deviceType)) {
					patientUser.setBpDeviceid("");
					patientUserService.update("updateByPrimaryKeySelective", patientUser);
				} else if ("2".equals(deviceType)) {
					patientUser.setBsDeviceid("");
					patientUserService.update("updateByPrimaryKeySelective", patientUser);
				}
				
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("用户不存在");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
	
}
