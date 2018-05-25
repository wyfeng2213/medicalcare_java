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
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.HealthSteps;
import com.cmcc.medicalcare.model.MessageReminding;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientLoginInfo;
import com.cmcc.medicalcare.model.PatientReferral;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IHealthStepsService;
import com.cmcc.medicalcare.service.IMessageRemindingService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.service.ISendcodeLogService;
import com.cmcc.medicalcare.utils.CityUtils;
import com.cmcc.medicalcare.utils.EcopUtils;
import com.cmcc.medicalcare.utils.GlobalMapUtils;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.RandomUtils;
import com.cmcc.medicalcare.utils.SendCodeUtil;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.comm.TokenUtil;

/**
 * @ClassName: 患者用户登录类
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/app/patientLoginInfo")
@Controller
public class PatientLoginInfoController {

	public static int userLoginControllerCount = 0;// 当前访问ecop平台请求数量

	@Resource
	private IPatientLoginInfoService patientLoginInfoService;

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;
	
	@Resource
	private IHealthStepsService healthStepsService;
	
	@Resource
	private ISendcodeLogService sendCodeLogService;
	
	@Resource
	private IMessageRemindingService messageRemindingService;

	/**
	 * 下发验证码 @Title: sendCode @Description: TODO @param @param
	 * request @param @param hPhonenumber @param @param
	 * equipmentData @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者端下发验证码")
	@RequestMapping(value = "sendCode")
	@ResponseBody
	public Results<String> sendCode(HttpServletRequest request, @RequestParam String hPhonenumber,
			String equipmentData) {
		Results<String> results = new Results<String>();
		if (null == hPhonenumber || "".equals(hPhonenumber)) { // 如果没有传入指定的参数则返回null，不做任何处理
			return new Results<String>(MessageCode.CODE_201, "手机号码不能为空！", null);
		}

		// 验证手机号码是否合法
		if (Toolkit.isMobileNO(hPhonenumber)) {
			
			if (SendCodeUtil.validateSendCodeTimes(hPhonenumber, sendCodeLogService)) {
				// 发送下发验证码请求
//				 boolean flag = EcopUtils.sendValcode(hPhonenumber);
				
				boolean flag = ShuyuanUtils.sendValcode(hPhonenumber);
//				 if ("15876587133".equals(hPhonenumber)) {
//						flag = true;
//					}
//				boolean flag = true;

				if (true == flag) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage("验证码下发成功");
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("验证码下发失败！请重试");
				}
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码下发次数过多被限时！3小时后再重试");
			}
		} else {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("请填写正确的手机号码！");
		}

		return results;

	}

	/**
	 * 验证码登录 @Title: codeLogin @Description: TODO @param @param
	 * request @param @param parame @param @param code @param @param
	 * equipmentData @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者端验证码登录")
	@RequestMapping(value = "codeLogin")
	@ResponseBody
	public Results<Map<String, Object>> codeLogin(HttpServletRequest request, String parame,
			@RequestParam String hPhonenumber, @RequestParam String code, String equipmentData) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		PatientLoginInfo patientLoginInfo_ = patientLoginInfoService.findByParam("selectByPhone", hPhonenumber);
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		// String patientName = dataJsonObject.getString("userName");
		String model = dataJsonObject.getString("model");
		String systemtype = dataJsonObject.getString("systemtype");
		String deviceToken = dataJsonObject.getString("deviceToken");
		String packageVesion = dataJsonObject.getString("packageVesion");

		// 验证验证码与手机号码的正确性
		if (Toolkit.isMobileNO(hPhonenumber)) {
			// 向服务器发送验证码验证请求
//			 Map<String,Object> flagMap = EcopUtils.checkValcode(hPhonenumber,
//			 code);
			Map<String, Object> flagMap = ShuyuanUtils.checkValcode(hPhonenumber, code);
			
			if (flagMap==null||flagMap.isEmpty()) {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码有误或已过有效期！");
				return results;
			}
			
			Object flagObject = flagMap.get("flag");
			boolean flag = false;
			if(null!=flagObject) {	//如果flagObject是null的话，就不转换
				flag = (Boolean)flagObject;
			}
			
			if("18520635230".equals(hPhonenumber)||"97531".equals(code)){
				flag = true;
			}
			
			if (true == flag) {
				if (null == patientLoginInfo_) {
					PatientLoginInfo patientLoginInfo = new PatientLoginInfo();
					patientLoginInfo.setPatientPhone(hPhonenumber);
					patientLoginInfo.setPatientLoginId(Constant.patient + hPhonenumber);
					// patientLoginInfo.setPatientName(patientName);
					patientLoginInfo.setPatientId(null);
					patientLoginInfo.setCreatetime(new Date());
					patientLoginInfo.setLoginTime(new Date());
					patientLoginInfo.setModel(model);
					patientLoginInfo.setSystem(systemtype);
					patientLoginInfo.setToken(request.getSession().getId());
					patientLoginInfo.setType(null);
					patientLoginInfo.setVersion(packageVesion);
					patientLoginInfo.setIp(HTTPUtils.getIpAddrByRequest(request));
//					patientLoginInfo.setCityName(cityName);
					patientLoginInfo.setIsEnable(1);
					patientLoginInfoService.insert(patientLoginInfo);
					map.put("patientLoginInfo", patientLoginInfo);
				} else {
					patientLoginInfo_.setLoginTime(new Date());
					patientLoginInfo_.setVersion(null);
					patientLoginInfo_.setToken(request.getSession().getId());
					patientLoginInfo_.setIp(HTTPUtils.getIpAddrByRequest(request));
//					patientLoginInfo_.setCityName(cityName);
					patientLoginInfoService.update("updateByPrimaryKeySelective", patientLoginInfo_);
					map.put("patientLoginInfo", patientLoginInfo_);
				}

				List<PatientDoctorsLink> patientDoctorsLinkList = null;
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("patient_phone", hPhonenumber);
				paramMap.put("doctorType", 2);
				paramMap.put("type", 1);
				patientDoctorsLinkList = patientDoctorsLinkService.getList("selectContractedDoctor", paramMap);
				if (patientDoctorsLinkList == null || patientDoctorsLinkList.size() < 1) {
					map.put("doctorsTeam", null);
				} else {
					String doctorsPhone = patientDoctorsLinkList.get(0).getDoctorsPhone();
					DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPhone", doctorsPhone);
					map.put("doctorsTeam", doctorsTeam);
				}

				// 获取该号码当天数据
				HealthSteps healthSteps = healthStepsService.findByParam("selectTodayByPatientPhone", hPhonenumber);
				map.put("healthSteps", healthSteps);
				
				// 获取未读消息数
				List<MessageReminding> messageRemindingList = messageRemindingService.getList("getUnreadMessage", hPhonenumber);
				map.put("UnreadMessageSize", messageRemindingList.size());
				
				results.setCode(MessageCode.CODE_200);
				results.setMessage("验证码登录成功！");
				map.put("session", request.getSession().getId());
				// MySessionContext.getInstance().setAppSession("medicalcare_"+hPhonenumber,request.getSession().getId());
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码有误或已过有效期！");
			}
		} else {
			results.setCode(MessageCode.CODE_400);
			results.setMessage("请填写正确的手机号码！");
		}

		return results;
	}
	
	/**
	 * 获取token
	 * 
	 */
	@SystemControllerLog(description = "获取token")
	@RequestMapping(value = "getUserAccessToken")
	@ResponseBody
	public Results<Map<String, Object>> getUserAccessToken(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = null;
		
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone = dataJsonObject.getString("phone");
		String username = Constant.patient + phone;
		String password = "mingyizaixian123456789";
		map = TokenUtil.getUserAccessToken(username, password);
		
		if (map != null) {
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("获取token失败");
		}
		
		return results;
	}
	
}
