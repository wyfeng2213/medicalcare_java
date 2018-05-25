package com.cmcc.medicalcare.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
import com.cmcc.medicalcare.model.HealthDeviceInfo;
import com.cmcc.medicalcare.model.PatientBloodPressure;
import com.cmcc.medicalcare.model.PatientBloodPressureAuto;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IHealthDeviceInfoService;
import com.cmcc.medicalcare.service.IPatientBloodPressureAutoService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.Toolkit;
import com.cmcc.medicalcare.utils.ValidationAIUtil;
import com.yjktws.BindDev;
import com.yjktws.QueryAbpm;
import com.yjktws.YjktService;
import com.yjktws.YjktService_Service;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/patientBloodPressureAuto")
public class PatientBloodPressureAutoController extends BaseController{
	
	@Resource
	private IPatientBloodPressureAutoService patientBloodPressureAutoService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IHealthDeviceInfoService healthDeviceInfoService;
	
	public static String httpEncoding = "UTF-8";
	
	/**
	 * parameter={"patientPhone":"","bp_deviceID":""}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
//	@RequestMapping(value = "bindDevice") 
//	@ResponseBody 
//	@SystemControllerLog(description = "绑定设备")    
//	public Results<String> bindDevice(HttpServletRequest request, String parameter) {
//		Results<String> results = new Results<String>();
//		
//		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
//		String patientPhone = dataJsonObject.getString("patientPhone");
//		String bp_deviceID = dataJsonObject.getString("bp_deviceID");
//		
//		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(bp_deviceID)) {
//			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
//			if (patientUser != null) {
//				patientUser.setBpDeviceid(bp_deviceID);
//				patientUserService.update("updateByPrimaryKeySelective", patientUser);
//				results.setCode(MessageCode.CODE_200);
//				results.setMessage(MessageCode.MESSAGE_200);
//				return results;
//			} else {
//				results.setCode(MessageCode.CODE_404);
//				results.setMessage("用户不存在");
//				return results;
//			}
//		} else {
//			results.setCode(MessageCode.CODE_201);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//		
//	}
	
	/**
	 * 绑定血压设备
	 * 
	 * parameter={"patientPhone":"患者电话","bp_deviceID":"设备编号","idCard":"身份证号(壹家用户参数)","device_role":"设备角色(壹家用户参数)：1表示A、2表示B","device_from":"设备来源类型：1念加、2壹家康泰"}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "bindDevice") 
	@ResponseBody 
	@SystemControllerLog(description = "绑定血压设备")    
	public Results<String> bindDevice(HttpServletRequest request, String parameter) {
		Results<String> results = new Results<String>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String bp_deviceID = dataJsonObject.getString("bp_deviceID");
		String idCard = dataJsonObject.getString("idCard");
		Integer device_role = dataJsonObject.getInteger("device_role");if (device_role == null) device_role = 1;
		String device_from = dataJsonObject.getString("device_from");
		
		if (StringUtils.isBlank(patientPhone) || StringUtils.isBlank(bp_deviceID) || StringUtils.isBlank(device_from)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
		if (patientUser == null) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("用户不存在");
			return results;
		}
		
		if ("1".equals(device_from)) { //念加
			//绑定用户
			patientUser.setBpDeviceid(bp_deviceID);
			patientUserService.update("updateByPrimaryKeySelective", patientUser);
			
			//保存设备信息
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("deviceId", bp_deviceID);
			paramMap.put("deviceType", 0);
			paramMap.put("deviceFrom", 1);
			paramMap.put("patientPhone", patientPhone);
			List<HealthDeviceInfo> healthDeviceInfo_ = healthDeviceInfoService.getList("getDeviceInfo", paramMap);
			if (healthDeviceInfo_.size() < 1) {
				HealthDeviceInfo healthDeviceInfo = new HealthDeviceInfo();
				healthDeviceInfo.setDeviceId(bp_deviceID);
				healthDeviceInfo.setDeviceType(0); //血压设备
				healthDeviceInfo.setDeviceFrom(1); //来源念加
				healthDeviceInfo.setPatientPhone(patientPhone);
				healthDeviceInfo.setCreatetime(new Date());
				healthDeviceInfo.setUpdatetime(new Date());
				healthDeviceInfoService.insert(healthDeviceInfo);
			}
		} else if ("2".equals(device_from)) { //壹家康泰
			if (false == ValidationAIUtil.isIDCard(idCard)) {
				results.setCode(MessageCode.CODE_201);
				results.setMessage("请输入正确的身份证号码");
				return results;
			}
			
			//1、在壹家平台绑定
			YjktService service = new YjktService_Service().getYjktServiceImplPort();
			BindDev bindDev = new BindDev();
			bindDev.setName(patientUser.getName());
			bindDev.setPhone(patientPhone);
			bindDev.setIdCard(idCard);
			bindDev.setDevCode(bp_deviceID);
			if (device_role == 1) {
				bindDev.setDevRole("1");
			} else {
				bindDev.setDevRole("2");
			}
			int code = service.bindUserDev(bindDev); //在壹家平台绑定
			if (code == 1) { //壹家平台绑定成功
				//2、绑定本地用户
				patientUser.setBpDeviceid(bp_deviceID);
				patientUserService.update("updateByPrimaryKeySelective", patientUser);
				
				//3、保存设备信息
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("deviceId", bp_deviceID);
				paramMap.put("deviceRole", device_role); //设备角色
				paramMap.put("deviceType", 0); //血压设备
				paramMap.put("deviceFrom", 2); //来源壹家
				List<HealthDeviceInfo> healthDeviceInfo_ = healthDeviceInfoService.getList("getDeviceInfo", paramMap);
				if (healthDeviceInfo_.size() > 0) {
					healthDeviceInfo_.get(0).setPatientPhone(patientPhone);
					healthDeviceInfo_.get(0).setUpdatetime(new Date());
					healthDeviceInfoService.update("updateByPrimaryKeySelective", healthDeviceInfo_.get(0));
				} else {
					HealthDeviceInfo healthDeviceInfo = new HealthDeviceInfo();
					healthDeviceInfo.setDeviceId(bp_deviceID);
					healthDeviceInfo.setDeviceRole(device_role);
					healthDeviceInfo.setDeviceType(0); //血压设备
					healthDeviceInfo.setDeviceFrom(2); //来源壹家
					healthDeviceInfo.setPatientPhone(patientPhone);
					healthDeviceInfo.setCreatetime(new Date());
					healthDeviceInfo.setUpdatetime(new Date());
					healthDeviceInfoService.insert(healthDeviceInfo);
				}
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("壹家绑定设备失败");
				return results;
			}
			
		}
		
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
		
	}
	
	/**
	 * 查询患者血压全部数据
	 * 
	 * parameter={"patientPhone":"患者电话","bp_deviceID":"设备编号","pageIndex":"","pageSize":""}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "selectBloodPressureNeedle") 
	@ResponseBody 
	@SystemControllerLog(description = "查询患者血压全部数据")    
	public Results<Map<String, Object>> selectBloodPressureNeedle(HttpServletRequest request, String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Pager<PatientBloodPressureAuto> page = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String bp_deviceID = dataJsonObject.getString("bp_deviceID");
		Long pageIndex = dataJsonObject.getLong("pageIndex");
		Long pageSize = dataJsonObject.getLong("pageSize");
		
		if (StringUtils.isBlank(patientPhone)) {
			String equipmentData = request.getParameter("equipmentData");
			JSONObject equipmentDataJsonObj = JSONObject.parseObject(equipmentData);
			patientPhone = equipmentDataJsonObj.getString("phone");
		}
		
		if (StringUtils.isNotBlank(bp_deviceID)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("bp_deviceID", bp_deviceID);
			paramMap.put("patientPhone", patientPhone);
			page = patientBloodPressureAutoService.getList("selectByDeviceID", getPageIndex(pageIndex), getPageSize(pageSize), paramMap);
			
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
	 * 保存血压针数据 (念加调用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveBloodPressureNeedle") 
	@ResponseBody 
	@SystemControllerLog(description = "保存血压针数据")    
	public Results<String> saveBloodPressureNeedle(HttpServletRequest request) {
		Results<String> results = new Results<String>();
		PatientBloodPressureAuto patientBloodPressureAuto = null;
		
		String bp_name = request.getParameter("bp_name");
		String bp_deviceID = request.getParameter("deviceid");
		String bp_measuretime = request.getParameter("bp_measuretime");
		String bp_sbp = request.getParameter("bp_sbp");
		String bp_dbp = request.getParameter("bp_dbp");
		String bp_pulse = request.getParameter("bp_pulse");
		String bp_mp = request.getParameter("bp_mp");
		String bp_status = request.getParameter("bp_status");
		
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("deviceId", bp_deviceID);
		paramMap1.put("deviceType", 0); //血压
		paramMap1.put("deviceFrom", 1); //念加
		List<HealthDeviceInfo> healthDeviceInfoList = healthDeviceInfoService.getList("getDeviceInfo", paramMap1);
		String patientPhone = "";
		if (healthDeviceInfoList.size() > 0) {
			patientPhone = healthDeviceInfoList.get(0).getPatientPhone();
		}
		
		patientBloodPressureAuto = new PatientBloodPressureAuto();
		patientBloodPressureAuto.setBpName(bp_name);
		patientBloodPressureAuto.setBpDeviceid(bp_deviceID);
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
			results.setCode(500);
			results.setMessage("服务器异常！");
			return results;
		}
		
		results.setCode(200);
		results.setMessage("成功");
		return results;
		
	}
	
	/**
	 * 统计患者血压监测数据
	 * 
	 * parameter={"patientPhone":"","scope":""}
	 * 
	 */
	@RequestMapping(value = "getMonitorBloodPressure")
	@ResponseBody
	@SystemControllerLog(description = "统计患者血压监测数据")
	public Results<Map<String, Object>> getMonitorBloodPressure(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<PatientBloodPressureAuto> patientBloodPressureList = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone"); //患者电话
		String scope = dataJsonObject.getString("scope"); //查询范围：当前一天date;当前一周week;所有all
		
		if (StringUtils.isBlank(patientPhone) || StringUtils.isBlank(scope)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		/**
		 * 查询该患者所绑定的设备
		 */
//		Map<String, Object> paramMap0 = new HashMap<String, Object>();
//		paramMap0.put("patientPhone", patientPhone);
//		List<HealthDeviceInfo> healthDeviceInfoList0 = healthDeviceInfoService.getList("getDeviceInfo", paramMap0);
//		if (healthDeviceInfoList0.size() < 1) {
//			String bp_deviceID = "";
//			String httpUrl_nj = "http://app.careeach.com/app_api/v2.4.1/json_201411/getDeveice.jsp";
//			String param = "action=deviceBindInfo&phone=" + patientPhone;
//    		String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, "UTF-8");
//    		if (StringUtils.isNotBlank(result_nj)) {
//    			JSONObject dataJson = JSONObject.parseObject(result_nj);
//    			String status = dataJson.getString("status");
//				if ("200".equals(status)) {
//					String data = dataJson.getString("data");
//					if (data != null) {
//						JSONArray dataJSONArray = JSONArray.fromObject(data);
//						for (int i=0; i<dataJSONArray.size(); i++) {
//							String entity = dataJSONArray.get(i).toString();
//							JSONObject entityObject = JSONObject.parseObject(entity);
//							String devType = entityObject.getString("devType");
//							String devNo = (String) entityObject.getString("devNo");
//							if ("1".equals(devType)) {
//								bp_deviceID = devNo;
//							}
//						}
//					}
//				}
//    		}
//    		if (StringUtils.isBlank(bp_deviceID)) {
//    			results.setCode(MessageCode.CODE_204);
//				results.setMessage("没有绑定血压设备");
//				return results;
//    		}
//		}
		
		/**
		 * 加载念加数据
		 */
		//根据设备号bp_deviceID取本地表-最大时间maxtime
		//若最大时间为空，去念加取全量数据
		//若最大时间不为空，去念加取maxtime+1秒到当前时间之间的增量数据
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("patientPhone", patientPhone);
		paramMap1.put("deviceType", 0); //血压
		paramMap1.put("deviceFrom", 1); //念加
		List<HealthDeviceInfo> healthDeviceInfoList1 = healthDeviceInfoService.getList("getDeviceInfo", paramMap1);
		if (healthDeviceInfoList1.size() > 0) {
			String bp_deviceID_ = healthDeviceInfoList1.get(0).getDeviceId();
			PatientBloodPressureAuto patientBloodPressureAuto_ = patientBloodPressureAutoService.findByParam("getMaxTimeByDeviceID", bp_deviceID_);
			if (patientBloodPressureAuto_ != null &&StringUtils.isNotBlank(patientBloodPressureAuto_.getBpMeasuretime())) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date maxTime_d = null;
				try {
					maxTime_d = df.parse(patientBloodPressureAuto_.getBpMeasuretime());
				} catch (ParseException e) {
					e.printStackTrace();
					maxTime_d = new Date();
				}
				String beginTime = df.format(new Date(maxTime_d.getTime()+1000));
				String endTime = df.format(new Date());
				NianjiaInterUtils.insertHealthData(patientPhone,  bp_deviceID_, "1", beginTime, endTime);
			} else {
				String beginTime = "";
				String endTime = "";
				NianjiaInterUtils.insertHealthData(patientPhone,  bp_deviceID_, "1", beginTime, endTime);
			}
		}
		
		/**
		 * 加载壹家康泰数据
		 */
		Map<String, Object> paramMap2 = new HashMap<String, Object>();
		paramMap2.put("patientPhone", patientPhone);
		paramMap2.put("deviceType", 0); //血压
		paramMap2.put("deviceFrom", 2); //壹家康泰
		List<HealthDeviceInfo> healthDeviceInfoList2 = healthDeviceInfoService.getList("getDeviceInfo", paramMap2);
		if (healthDeviceInfoList2.size() > 0) {
			for (int i=0; i<healthDeviceInfoList2.size(); i++) {
				String devCode = healthDeviceInfoList2.get(i).getDeviceId();
				if (StringUtils.isNotBlank(devCode)) {
					YjktService service = new YjktService_Service().getYjktServiceImplPort();
					QueryAbpm queryAbpm = service.findAbpm(patientPhone, devCode);
					if (queryAbpm != null) {
						Date measuretime = queryAbpm.getRecordDate().toGregorianCalendar().getTime();
						String measuretime_str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(measuretime);
						//查询记录是否存在
						Map<String, Object> paramMap3 = new HashMap<String, Object>();
						paramMap3.put("bp_deviceID", devCode);
						paramMap3.put("patientPhone", patientPhone);
						paramMap3.put("deviceFromType", 2);
						paramMap3.put("bp_measuretime", measuretime_str);
						List<PatientBloodPressureAuto> patientBloodPressureList_ = patientBloodPressureAutoService.getList("getBySelective", paramMap3);
						if (patientBloodPressureList_.size() < 1) {
							//壹家数据本地入库
							Integer high_ = queryAbpm.getSbp(); //收缩压(高压)
							Integer low_ = queryAbpm.getDbp(); //舒张压(低压)
							PatientBloodPressureAuto patientBloodPressureAuto = new PatientBloodPressureAuto();
							patientBloodPressureAuto.setBpDeviceid(devCode);
							patientBloodPressureAuto.setBpMeasuretime(measuretime_str);
							patientBloodPressureAuto.setBpSbp(Integer.toString(high_));
							patientBloodPressureAuto.setBpDbp(Integer.toString(low_));
							patientBloodPressureAuto.setBpPulse(Integer.toString(queryAbpm.getHr()));
							if (high_ > 140 || low_ > 90) { //高血压
								patientBloodPressureAuto.setBpStatus("血压偏高");
							} else if (high_ < 90 || low_ < 60) { //低血压
								patientBloodPressureAuto.setBpStatus("血压偏低");
							} else { //正常血压
								patientBloodPressureAuto.setBpStatus("正常血压");
							}
							patientBloodPressureAuto.setDeviceFromType(2);
							patientBloodPressureAuto.setPatientPhone(patientPhone);
							patientBloodPressureAuto.setCreatetime(new Date());
							patientBloodPressureAuto.setUpdatetime(new Date());
							patientBloodPressureAutoService.insert(patientBloodPressureAuto);
						}
					}
				}
			}
		}
		
		/**
		 * 获取返回统计数据
		 */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("patientPhone", patientPhone);
		if ("date".equals(scope)) {
			paramMap.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//获取当前日期
			paramMap.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime() + 24 * 60 * 60 * 1000));//获取当前日期的下一天日期
			patientBloodPressureList = patientBloodPressureAutoService.getList("getBloodPressureOfCurrentDay", paramMap);
		} else if ("week".equals(scope)) {
			paramMap.put("startTime", Toolkit.getTimesWeekmorning()); //获取本周一日期
			paramMap.put("endTime",Toolkit.getTimesNextWeekmorning()); //获取下周一日期
			patientBloodPressureList = patientBloodPressureAutoService.getList("getAvgBloodPressureOfDay", paramMap);
		} else if ("all".equals(scope)) {
			patientBloodPressureList = patientBloodPressureAutoService.getList("getAllBloodPressureByPhone", paramMap);
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
			return results;
		}
		map.put("patientBloodPressureList", patientBloodPressureList);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
		
	}
	
//	/**
//	 * 统计患者血压监测数据(2017-11-17注释备份)
//	 * 
//	 * parameter={"patientPhone":"","scope":""}
//	 * 
//	 */
//	@RequestMapping(value = "getMonitorBloodPressure")
//	@ResponseBody
//	@SystemControllerLog(description = "统计患者血压监测数据")
//	public Results<Map<String, Object>> getMonitorBloodPressure(HttpServletRequest request, @RequestParam String parameter) {
//		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		List<PatientBloodPressureAuto> patientBloodPressureList = null;
//		
//		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
//		String patientPhone = dataJsonObject.getString("patientPhone"); //患者电话
//		String scope = dataJsonObject.getString("scope"); //查询范围：当前一天date;当前一周week;所有all
//		
//		//判断该患者设备绑定情况
//		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(scope)) {
//			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
//			String bp_deviceID = patientUser.getBpDeviceid();
//			if (StringUtils.isBlank(bp_deviceID)) {
//				String httpUrl_nj = "http://app.careeach.com/app_api/v2.4.1/json_201411/getDeveice.jsp";
//				String param = "action=deviceBindInfo&phone=" + patientPhone;
//	    		String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, "UTF-8");
//	    		if (StringUtils.isNotBlank(result_nj)) {
//	    			JSONObject dataJson = JSONObject.parseObject(result_nj);
//	    			String status = dataJson.getString("status");
//					if ("200".equals(status)) {
//						String data = dataJson.getString("data");
//						if (data != null) {
//							JSONArray dataJSONArray = JSONArray.fromObject(data);
//							for (int i=0; i<dataJSONArray.size(); i++) {
//								String entity = dataJSONArray.get(i).toString();
//								JSONObject entityObject = JSONObject.parseObject(entity);
//								String devType = entityObject.getString("devType");
//								String devNo = (String) entityObject.getString("devNo");
//								if ("1".equals(devType)) {
//									bp_deviceID = devNo;
//								}
//							}
//						}
//					}
//	    		}
//	    		if (StringUtils.isBlank(bp_deviceID)) {
//	    			results.setCode(MessageCode.CODE_204);
//					results.setMessage("没有绑定血压设备");
//					return results;
//	    		}
//			}
//			
//			//根据设备号bp_deviceID取本地表-最大时间maxtime
//			//若最大时间为空，去念加取全量数据
//			//若最大时间不为空，去念加取maxtime+1秒到当前时间之间的增量数据
//			PatientBloodPressureAuto patientBloodPressureAuto_ = patientBloodPressureAutoService.findByParam("getMaxTimeByDeviceID", bp_deviceID);
//			if (patientBloodPressureAuto_ != null &&StringUtils.isNotBlank(patientBloodPressureAuto_.getBpMeasuretime())) {
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date maxTime_d = null;
//				try {
//					maxTime_d = df.parse(patientBloodPressureAuto_.getBpMeasuretime());
//				} catch (ParseException e) {
//					e.printStackTrace();
//					maxTime_d = new Date();
//				}
//				String beginTime = df.format(new Date(maxTime_d.getTime()+1000));
//				String endTime = df.format(new Date());
//				NianjiaInterUtils.insertHealthData(patientPhone,  bp_deviceID, "1", beginTime, endTime);
//			} else {
//				String beginTime = "";
//				String endTime = "";
//				NianjiaInterUtils.insertHealthData(patientPhone,  bp_deviceID, "1", beginTime, endTime);
//			}
//			
//			//获取返回统计数据
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("bp_deviceID", bp_deviceID);
//			if ("date".equals(scope)) {
//				paramMap.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//获取当前日期
//				paramMap.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime() + 24 * 60 * 60 * 1000));//获取当前日期的下一天日期
//				patientBloodPressureList = patientBloodPressureAutoService.getList("getBloodPressureOfCurrentDay", paramMap);
//			} else if ("week".equals(scope)) {
//				paramMap.put("startTime", Toolkit.getTimesWeekmorning()); //获取本周一日期
//				paramMap.put("endTime",Toolkit.getTimesNextWeekmorning()); //获取下周一日期
//				patientBloodPressureList = patientBloodPressureAutoService.getList("getAvgBloodPressureOfDay", paramMap);
//			} else if ("all".equals(scope)) {
//				patientBloodPressureList = patientBloodPressureAutoService.getList("getAllBloodPressureByPhone", paramMap);
//			} else {
//				results.setCode(MessageCode.CODE_501);
//				results.setMessage(MessageCode.MESSAGE_501);
//				return results;
//			}
//			map.put("patientBloodPressureList", patientBloodPressureList);
//			results.setData(map);
//			results.setCode(MessageCode.CODE_200);
//			results.setMessage(MessageCode.MESSAGE_200);
//			return results;
//		} else {
//			results.setCode(MessageCode.CODE_201);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//		
//	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "saveBloodPressureNeedle") 
	@ResponseBody 
	@SystemControllerLog(description = "保存血压针数据")    
	public Results<String> saveBloodPressureNeedle(HttpServletRequest request) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa=====================================");
		Results<String> results = new Results<String>();
		InputStreamReader request_input = null;
		StringBuffer strbuf = new StringBuffer();
		char[] charbuff = new char[2048];
		String bloodPressure = null;
		boolean isException = false;
        int i;
		try {
			request_input = new InputStreamReader(request.getInputStream(), httpEncoding);
			while ((i = request_input.read(charbuff)) != -1) {
				strbuf.append(charbuff, 0, i);
			}
			System.out.println("strbuf============================================================" + strbuf);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			isException = true;
		} catch (IOException e) {
			e.printStackTrace();
			isException = true;
		} finally {
			if (request_input != null) {
				try {
					request_input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (isException) {
			results.setCode(500);
			results.setMessage("服务器异常！");
			return results;
		}
        bloodPressure = strbuf.toString();
		System.out.println("bloodPressure=============================================" + bloodPressure);
		JSONArray bloodPressureArray = JSONObject.parseArray(bloodPressure);
		if (bloodPressureArray != null && bloodPressureArray.size() > 0) {
			for (int j = 0; j < bloodPressureArray.size(); j++) {
				JSONObject bloodPressureObject = bloodPressureArray.getJSONObject(j);
				
				PatientBloodPressureAuto patientBloodPressureAuto = new PatientBloodPressureAuto();
				patientBloodPressureAuto.setBpName(bloodPressureObject.getString("bp_name"));
				patientBloodPressureAuto.setBpDeviceid(bloodPressureObject.getString("bp_deviceID"));
				patientBloodPressureAuto.setBpMeasuretime(bloodPressureObject.getString("bp_measuretime"));
				patientBloodPressureAuto.setBpSbp(bloodPressureObject.getString("bp_sbp"));
				patientBloodPressureAuto.setBpDbp(bloodPressureObject.getString("bp_dbp"));
				patientBloodPressureAuto.setBpPulse(bloodPressureObject.getString("bp_pulse"));
				patientBloodPressureAuto.setBpMp(bloodPressureObject.getString("bp_mp"));
				patientBloodPressureAuto.setBpStatus(bloodPressureObject.getString("bp_status"));
				patientBloodPressureAuto.setCreatetime(new Date());
		        patientBloodPressureAutoService.insert(patientBloodPressureAuto);
			}
			
			//Gson gson = new Gson();
	        //PatientBloodPressureAuto patientBloodPressureAuto = null;
	        //patientBloodPressureAuto = gson.fromJson(bloodPressureObject.toString(), PatientBloodPressureAuto.class);
			PatientBloodPressureAuto patientBloodPressureAuto = new PatientBloodPressureAuto();
			patientBloodPressureAuto.setBpName(bloodPressureObject.getString("bp_name"));
			patientBloodPressureAuto.setBpDeviceid(bloodPressureObject.getString("bp_deviceID"));
			patientBloodPressureAuto.setBpMeasuretime(bloodPressureObject.getString("bp_measuretime"));
			patientBloodPressureAuto.setBpSbp(bloodPressureObject.getString("bp_sbp"));
			patientBloodPressureAuto.setBpDbp(bloodPressureObject.getString("bp_dbp"));
			patientBloodPressureAuto.setBpPulse(bloodPressureObject.getString("bp_pulse"));
			patientBloodPressureAuto.setBpMp(bloodPressureObject.getString("bp_mp"));
			patientBloodPressureAuto.setBpStatus(bloodPressureObject.getString("bp_status"));
			patientBloodPressureAuto.setCreatetime(new Date());
	        patientBloodPressureAutoService.insert(patientBloodPressureAuto);
	        
			results.setCode(200);
			results.setMessage("成功");
			return results;
		} else {
			results.setCode(300);
			results.setMessage("设备不存在");
			return results;
		}
		
	}*/
	
	/*@RequestMapping(value = "test") 
	@ResponseBody 
	public Results<String> test(HttpServletRequest request) {
		Results<String> results = new Results<String>();
		//String URL = "http://10.1.2.166:8080/medicalcare/patientBloodPressureAuto/saveBloodPressureNeedle.action";
		String URL = "http://120.197.89.249:8080/medicalcare/patientBloodPressureAuto/saveBloodPressureNeedle.action";
		
		String content =
				"[{\"bp_name\":\"bbb\",\"bp_deviceID\":\"bbb\",\"bp_measuretime\":\"2017-05-27 10:40:00\","
				+ "\"bp_sbp\":\"bb\",\"bp_dbp\":\"aaa\",\"bp_pulse\":\"bbb\","
				+ "\"bp_mp\":\"bb\",\"bp_status\":\"bbb\""
				+ "}]";
		
		//HTTPUtils.httpDoPostStrT(URL, content, "UTF-8", "UTF-8");
		try {
			doPostWithParam2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		results.setData(content);
		return results;
	}*/
	
	/*public static void doPostWithParam2() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个post对象
		HttpPost post = new HttpPost("http://10.1.2.166:8080/medicalcare/patientBloodPressureAuto/saveBloodPressureNeedle.action");
		//HttpPost post = new HttpPost("http://120.197.89.249:8080/medicalcare/patientBloodPressureAuto/saveBloodPressureNeedle.action");
		// 创建一个Entity。模拟一个表单
		ArrayList<NameValuePair> kvList = new ArrayList<NameValuePair>();
		kvList.add(new BasicNameValuePair("bp_name", "cdfsdscc"));
		kvList.add(new BasicNameValuePair("deviceid", "aaa"));
		kvList.add(new BasicNameValuePair("bp_measuretime", "2017-05-27 10:40:00"));
		kvList.add(new BasicNameValuePair("bp_sbp", "96"));
		kvList.add(new BasicNameValuePair("bp_dbp", "65"));
		kvList.add(new BasicNameValuePair("bp_pulse", "75"));
		kvList.add(new BasicNameValuePair("bp_mp", "80"));
		kvList.add(new BasicNameValuePair("bp_status", "正常血压"));
		// 包装成一个Entity对象
		StringEntity strEntity = new UrlEncodedFormEntity(kvList, "utf-8");
		// 设置请求的内容
		post.setEntity(strEntity);

		// 执行post请求
		CloseableHttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			if (response.getStatusLine().getStatusCode() == 200) {
				String string = EntityUtils.toString(entity);
				System.out.println(string);
			} else {
				String string = EntityUtils.toString(entity);
				System.out.println(string);
			}

		}
		response.close();
		httpClient.close();
	}*/
	
}
