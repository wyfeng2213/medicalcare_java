package com.cmcc.medicalcare.controller.h5;

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
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientLoginInfo;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.utils.EcopUtils;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName: 患者用户登录类
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/h5/patientLoginInfo")
@Controller
public class H5PatientLoginInfoController {

	public static int userLoginControllerCount = 0;// 当前访问ecop平台请求数量
	
	@Resource
	private IPatientLoginInfoService patientLoginInfoService;

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	/**
	 * 下发验证码 @Title: sendCode @Description: TODO @param @param
	 * request @param @param hPhonenumber @param @param
	 * equipmentData @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "h5患者端下发验证码")
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
			// 发送下发验证码请求
//			 boolean flag = EcopUtils.sendValcode(hPhonenumber);
			
			boolean flag = ShuyuanUtils.sendValcode(hPhonenumber);
//			 if ("15876587133".equals(hPhonenumber)) {
//					flag = true;
//				}
//			boolean flag = true;

			if (true == flag) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage("验证码下发成功");
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码下发失败！请重试");
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
	@SystemControllerLog(description = "h5患者端验证码登录")
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
//			
//			if("15876587133".equals(hPhonenumber)){
//				flag = true;
//			}
			
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
				} else {
					patientLoginInfo_.setLoginTime(new Date());
					patientLoginInfo_.setVersion(null);
					patientLoginInfo_.setToken(request.getSession().getId());
					patientLoginInfo_.setIp(HTTPUtils.getIpAddrByRequest(request));
//					patientLoginInfo_.setCityName(cityName);
					patientLoginInfoService.update("updateByPrimaryKeySelective", patientLoginInfo_);
				}

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
}
