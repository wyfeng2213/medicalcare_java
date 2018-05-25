package com.cmcc.medicalcare.controller.app.patient.v2;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.geronimo.mail.util.UUDecoderStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.cache.redis.Constants;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberLoginUtils;
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
@RequestMapping("/app/patientLoginInfo/v2")
@Controller
public class PatientLoginInfoV2Controller {

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
	
	@Resource
	private RedisUtil redisUtil;

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
				boolean flag = false;
				String jsonStr = MemberLoginUtils.memberLogin(hPhonenumber);
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
	@SystemControllerLog(description = "患者端验证码登录")
	@RequestMapping(value = "codeLogin")
	@ResponseBody
	public Results<Map<String, Object>> codeLogin(HttpServletRequest request, String parame,
			@RequestParam String hPhonenumber, @RequestParam String code, String equipmentData) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		PatientLoginInfo patientLoginInfo_ = patientLoginInfoService.findByParam("selectByPhone", hPhonenumber);
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String model = dataJsonObject.getString("model");
		String systemtype = dataJsonObject.getString("systemtype");
		String deviceToken = dataJsonObject.getString("deviceToken");
		String packageVesion = dataJsonObject.getString("packageVesion");

		// 验证验证码与手机号码的正确性
		if (Toolkit.isMobileNO(hPhonenumber)) {
			// 向服务器发送验证码验证请求
			boolean flag = false;
			String jsonStr = MemberLoginUtils.isMemberCode(hPhonenumber, code);
			JSONObject json = null;
			if (StringUtils.isNotBlank(jsonStr)) {
				json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					flag = true;
				}
			}
			
			String token = RandomUtils.generateUUID();
			
			if (true == flag) {
				if (null == patientLoginInfo_) {
					PatientLoginInfo patientLoginInfo = new PatientLoginInfo();
					patientLoginInfo.setPatientPhone(hPhonenumber);
					patientLoginInfo.setPatientLoginId(Constant.patient + hPhonenumber);
					patientLoginInfo.setPatientId(null);
					patientLoginInfo.setCreatetime(new Date());
					patientLoginInfo.setLoginTime(new Date());
					patientLoginInfo.setModel(model);
					patientLoginInfo.setSystem(systemtype);
					patientLoginInfo.setToken(token);
					patientLoginInfo.setType(null);
					patientLoginInfo.setVersion(packageVesion);
					patientLoginInfo.setIp(HTTPUtils.getIpAddrByRequest(request));
					patientLoginInfo.setIsEnable(1);
					patientLoginInfoService.insert(patientLoginInfo);
					map.put("patientLoginInfo", patientLoginInfo);
				} else {
					patientLoginInfo_.setLoginTime(new Date());
					patientLoginInfo_.setVersion(null);
					patientLoginInfo_.setToken(token);
					patientLoginInfo_.setIp(HTTPUtils.getIpAddrByRequest(request));
					patientLoginInfoService.update("updateByPrimaryKeySelective", patientLoginInfo_);
					map.put("patientLoginInfo", patientLoginInfo_);
				}
				
				//检查九州通是否有注册用户
				JSONObject data = json.getJSONObject("data");
				if (null == data) {
					map.put("session", token);
					results.setData(map);
					results.setCode(MessageCode.CODE_204);
					results.setMessage("尚未注册");
					return results;
				} else {
					data.put("loginId", Constant.patient + hPhonenumber);
				}
				
				//缓存九州通会员信息
				redisUtil.set(Constants.KEY_USER_MEMBER + hPhonenumber, data);
				
				map.put("memberInfo", data);
				map.put("session", token);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("验证码登录成功！");
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码有误或已过有效期！");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_400);
			results.setMessage("请填写正确的手机号码！");
			return results;
		}

	}
	
}
