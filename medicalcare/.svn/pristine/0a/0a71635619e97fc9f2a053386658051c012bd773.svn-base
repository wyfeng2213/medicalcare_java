package com.cmcc.medicalcare.inter.nianjia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.model.HealthDeviceInfo;
import com.cmcc.medicalcare.model.PatientBloodPressureAuto;
import com.cmcc.medicalcare.model.PatientBloodSugarAuto;
import com.cmcc.medicalcare.service.IHealthDeviceInfoService;
import com.cmcc.medicalcare.service.IPatientBloodPressureAutoService;
import com.cmcc.medicalcare.service.IPatientBloodSugarAutoService;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.SpringConfigTool;

public class NianjiaInterUtils {

	public static String httpEncoding = "UTF-8";
	private static String httpUrl_nj = "http://app.careeach.com/app_api/v2.4.1/json_201411/getDeveice.jsp";
	
	
	/**
	 * 粤健康，查询念加健康设备数据
	 * @param phone
	 * @param action
	 * @param devNo
	 * @param devType
	 * @param beginTime
	 * @param endTime
	 */
	public static List<Object> getHealthData(String phone, String action, String devNo, String devType,
			String beginTime, String endTime) {
		List<Object> patientBloodPressureList = null;
		
		if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(devNo) && StringUtils.isNotBlank(devType)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String param = "action=deviceData&phone=" + phone.trim() + "&devNo=" + devNo.trim();
			if ("1".equals(devType)) {
    			param += "&devType=1";
    		} else if ("2".equals(devType)) {
    			param += "&devType=2";
    		} else {
				return patientBloodPressureList;
    		}
			
			if (StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)) {
    			try {
    				String beginTime_fm = df.format(df.parse(beginTime));
    				String endTime_fm = df.format(df.parse(endTime));
    				param += "&beginTime=" + beginTime_fm.trim() + "&endTime=" + endTime_fm.trim();
				} catch (ParseException e) {
					e.printStackTrace();
					return patientBloodPressureList;
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
					JSONArray data = dataJsonObject.getJSONArray("data");
					if(data!=null&&data.size()>0){
						patientBloodPressureList = new ArrayList<Object>();
						for(int i=0;i<data.size();i++){
							JSONObject jsonObject= data.getJSONObject(i);
							if ("1".equals(devType)) {
								PatientBloodPressureAuto patientBloodPressureAuto = new PatientBloodPressureAuto();
								patientBloodPressureAuto.setBpDbp(jsonObject.getString("bp_dbp"));
								patientBloodPressureAuto.setBpSbp(jsonObject.getString("bp_sbp"));
								patientBloodPressureAuto.setBpPulse(jsonObject.getString("bp_pulse"));
								patientBloodPressureAuto.setBpMp(jsonObject.getString("bp_mp"));
								patientBloodPressureAuto.setBpSbp(jsonObject.getString("bp_sbp"));
								patientBloodPressureAuto.setBpMeasuretime(jsonObject.getString("bp_measuretime").replace(".0",""));
								patientBloodPressureList.add(patientBloodPressureAuto);
								
				    		} else if ("2".equals(devType)) {
				    			PatientBloodSugarAuto patientBloodSugarAuto = new PatientBloodSugarAuto();
				    			
				    			patientBloodSugarAuto.setBloodglucose(jsonObject.getString("bloodglucose"));
				    			patientBloodSugarAuto.setStatus(jsonObject.getString("status"));
				    			patientBloodSugarAuto.setUploadtime(jsonObject.getString("bg_measure_time").replace(".0",""));
								patientBloodPressureList.add(patientBloodSugarAuto);
				    		}
							
						}
						
					}
					
				}
			}
			
		}
		return patientBloodPressureList;
	}
	
	/**
	 * 往表里面插入数据
	 * @param deviceId
	 * @param phone
	 * @param devNo
	 * @param devType
	 * @param beginTime
	 * @param endTime
	 */
	public static void insertHealthData(String phone,String devNo, String devType,String beginTime, String endTime) {
		
		if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(devNo) && StringUtils.isNotBlank(devType)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String param = "action=deviceData&phone=" + phone.trim() + "&devNo=" + devNo.trim();
			if ("1".equals(devType)) {
    			param += "&devType=1";
    		} else if ("2".equals(devType)) {
    			param += "&devType=2";
    		} else {
    			return ;
    		}
			
			if (StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)) {
    			try {
    				String beginTime_fm = df.format(df.parse(beginTime));
    				String endTime_fm = df.format(df.parse(endTime));
    				param += "&beginTime=" + beginTime_fm.trim() + "&endTime=" + endTime_fm.trim();
				} catch (ParseException e) {
					e.printStackTrace();
					return ;
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
					JSONArray data = dataJsonObject.getJSONArray("data");
					if(data!=null&&data.size()>0){
						for(int i=0;i<data.size();i++){
							JSONObject jsonObject= data.getJSONObject(i);
							if ("1".equals(devType)) {
								Map<String, Object> paramMap1 = new HashMap<String, Object>();
								paramMap1.put("deviceId", devNo);
								paramMap1.put("deviceType", 0); //血压
								paramMap1.put("deviceFrom", 1); //念加
								IHealthDeviceInfoService healthDeviceInfoService= (IHealthDeviceInfoService)SpringConfigTool.getBean("healthDeviceInfoService");
								List<HealthDeviceInfo> healthDeviceInfoList = healthDeviceInfoService.getList("getDeviceInfo", paramMap1);
								String patientPhone = "";
								if (healthDeviceInfoList.size() > 0) {
									patientPhone = healthDeviceInfoList.get(0).getPatientPhone();
								}
								
								PatientBloodPressureAuto patientBloodPressureAuto = new PatientBloodPressureAuto();
								patientBloodPressureAuto.setBpName(jsonObject.getString("des"));
								patientBloodPressureAuto.setBpDeviceid(devNo);
								patientBloodPressureAuto.setDeviceFrom("念加本身数据");
								patientBloodPressureAuto.setBpMeasuretime(jsonObject.getString("bp_measuretime").replace(".0",""));
								patientBloodPressureAuto.setBpSbp(jsonObject.getString("bp_sbp"));
								patientBloodPressureAuto.setBpDbp(jsonObject.getString("bp_dbp"));
								patientBloodPressureAuto.setBpPulse(jsonObject.getString("bp_pulse"));
								patientBloodPressureAuto.setBpMp(jsonObject.getString("bp_mp"));
								patientBloodPressureAuto.setBpStatus(jsonObject.getString("status"));
								patientBloodPressureAuto.setDataFromType(1);
								patientBloodPressureAuto.setPatientPhone(phone);
								patientBloodPressureAuto.setPatientPhone(patientPhone);
								patientBloodPressureAuto.setDeviceFromType(1);
								patientBloodPressureAuto.setCreatetime(new Date());
								IPatientBloodPressureAutoService patientBloodPressureAutoService= (IPatientBloodPressureAutoService)SpringConfigTool.getBean("patientBloodPressureAutoService");
								patientBloodPressureAutoService.insert(patientBloodPressureAuto);
								
				    		} else if ("2".equals(devType)) {
				    			PatientBloodSugarAuto patientBloodSugarAuto = new PatientBloodSugarAuto();
				    			patientBloodSugarAuto.setDeviceId(devNo);
				    			patientBloodSugarAuto.setDeviceFrom("念加本身数据");
				    			patientBloodSugarAuto.setBloodglucose(jsonObject.getString("bloodglucose"));
				    			patientBloodSugarAuto.setStatus(jsonObject.getString("status"));
				    			patientBloodSugarAuto.setUploadtime(jsonObject.getString("bg_measure_time").replace(".0",""));
				    			patientBloodSugarAuto.setDataFromType(1);
				    			patientBloodSugarAuto.setCreatetime(new Date());
								IPatientBloodSugarAutoService patientBloodSugarAutoService= (IPatientBloodSugarAutoService)SpringConfigTool.getBean("patientBloodSugarAutoService");
								patientBloodSugarAutoService.insert(patientBloodSugarAuto);
				    			
				    		}
							
						}
						
					}
					
				}
			}
			
		}
	}
	
}
