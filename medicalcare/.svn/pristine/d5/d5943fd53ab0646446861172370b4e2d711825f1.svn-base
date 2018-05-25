package com.cmcc.medicalcare.controller.app.doctor.v2;

import java.util.Date;
import java.util.HashMap;
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
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorLoginUtils;
import com.cmcc.medicalcare.model.DoctorsLoginInfo;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.ISendcodeLogService;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.RandomUtils;
import com.cmcc.medicalcare.utils.SendCodeUtil;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName: 医生用户登录类
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/app/doctorsLoginInfo/v2")
@Controller
public class DoctorsLoginInfoV2Controller {

	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;

	@Resource
	private ISendcodeLogService sendCodeLogService;
			
	/**
	 * 下发验证码 @Title: sendCode @Description: TODO @param @param
	 * request @param @param hPhonenumber @param @param
	 * equipmentData @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生端下发验证码")
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

				boolean flag = false;
				String jsonStr = DoctorLoginUtils.doctorLogin(hPhonenumber);
				if (StringUtils.isNotBlank(jsonStr)) {
					JSONObject json = JSONObject.parseObject(jsonStr);
					String status = json.getString("status");
					if ("0".equals(status)) {
						flag = true;
					}
				}
				
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
	@SystemControllerLog(description = "医生端验证码登录")
	@RequestMapping(value = "codeLogin")
	@ResponseBody
	public Results<Map<String, Object>> codeLogin(HttpServletRequest request, String parame,
			@RequestParam String hPhonenumber, @RequestParam String code, String equipmentData) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorsLoginInfo doctorsLoginInfo_ = doctorsLoginInfoService.findByParam("selectByPhone", hPhonenumber);
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String doctorsName = dataJsonObject.getString("userName");
		String model = dataJsonObject.getString("model");
		String systemtype = dataJsonObject.getString("systemtype");
		String deviceToken = dataJsonObject.getString("deviceToken");
		String packageVesion = dataJsonObject.getString("packageVesion");

		// 验证验证码与手机号码的正确性
		if (Toolkit.isMobileNO(hPhonenumber)) {

			boolean flag = false;
			String jsonStr = DoctorLoginUtils.isDoctorCode(hPhonenumber, code);
			if (StringUtils.isNotBlank(jsonStr)) {
				JSONObject json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					flag = true;
				}
			}
			
			String token = RandomUtils.generateUUID();
			
			if (true == flag) {
				if (null == doctorsLoginInfo_) {
					DoctorsLoginInfo doctorsLoginInfo = new DoctorsLoginInfo();
					doctorsLoginInfo.setDoctorsPhone(hPhonenumber);
					doctorsLoginInfo.setDoctorsLoginId(Constant.doctor+hPhonenumber);
					doctorsLoginInfo.setDoctorsName(doctorsName);
					doctorsLoginInfo.setDoctorsId(null);
					doctorsLoginInfo.setCreatetime(new Date());
					doctorsLoginInfo.setLoginTime(new Date());
					doctorsLoginInfo.setModel(model);
					doctorsLoginInfo.setSystem(systemtype);
					doctorsLoginInfo.setToken(token);
					doctorsLoginInfo.setType(null);
					doctorsLoginInfo.setVersion(packageVesion);
					doctorsLoginInfo.setIp(HTTPUtils.getIpAddrByRequest(request));
//					doctorsLoginInfo.setCityName(cityName);
					doctorsLoginInfoService.insert(doctorsLoginInfo);
					map.put("doctorsLoginInfo", doctorsLoginInfo);
					doctorsLoginInfo_ = doctorsLoginInfo;
				} else {
					doctorsLoginInfo_.setLoginTime(new Date());
					doctorsLoginInfo_.setVersion(null);
					doctorsLoginInfo_.setToken(token);
					doctorsLoginInfo_.setIp(HTTPUtils.getIpAddrByRequest(request));
//					doctorsLoginInfo_.setCityName(cityName);
					doctorsLoginInfoService.update("updateByPrimaryKeySelective", doctorsLoginInfo_);
					map.put("doctorsLoginInfo", doctorsLoginInfo_);
				}
				
				results.setCode(MessageCode.CODE_200);
				results.setMessage("验证码登录成功！");
				map.put("session", token);
//				MySessionContext.getInstance().setAppSession("medicalcare_"+hPhonenumber,request.getSession().getId());
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码有误或已过有效期！");
			}
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("请填写正确的手机号码！");
		}

		return results;
	}
}
