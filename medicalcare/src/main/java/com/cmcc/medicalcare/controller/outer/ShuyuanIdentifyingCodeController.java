package com.cmcc.medicalcare.controller.outer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.service.ISendcodeLogService;
import com.cmcc.medicalcare.utils.SendCodeUtil;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName: 第三方登录类
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/outer/shuyuan")
@Controller
public class ShuyuanIdentifyingCodeController {

	@Resource
	private ISendcodeLogService sendCodeLogService;

	/**
	 * 下发验证码 @Title: sendCode @Description: TODO @param @param
	 * request @param @param hPhonenumber @param @param
	 * equipmentData @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "第三方下发数远验证码")
	@RequestMapping(value = "sendCode")
	@ResponseBody
	public Results<String> sendCode(HttpServletRequest request, @RequestParam String hPhonenumber,
			String equipmentData) {
		Results<String> results = new Results<String>();
		try {
			hPhonenumber = DESOuter.decryptBeas64DES(hPhonenumber);
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null == hPhonenumber || "".equals(hPhonenumber)) { // 如果没有传入指定的参数则返回null，不做任何处理
			return new Results<String>(MessageCode.CODE_201, "手机号码不能为空！", null);
		}

		// 验证手机号码是否合法
		if (Toolkit.isMobileNO(hPhonenumber)) {

			if (SendCodeUtil.validateSendCodeTimes(hPhonenumber, sendCodeLogService)) {
				// 发送下发验证码请求

				boolean flag = ShuyuanUtils.sendValcode(hPhonenumber);

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
	@SystemControllerLog(description = "第三方数远验证码登录")
	@RequestMapping(value = "codeLogin")
	@ResponseBody
	public Results<Map<String, Object>> codeLogin(HttpServletRequest request, @RequestParam String hPhonenumber,
			@RequestParam String code) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			hPhonenumber = DESOuter.decryptBeas64DES(hPhonenumber);
			code = DESOuter.decryptBeas64DES(code);
			System.out.println(code);
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 验证验证码与手机号码的正确性
		if (Toolkit.isMobileNO(hPhonenumber)) {

			// 向服务器发送验证码验证请求
			Map<String, Object> flagMap = ShuyuanUtils.checkValcode(hPhonenumber, code);

			if (flagMap == null || flagMap.isEmpty()) {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码有误或已过有效期！");
				return results;
			}

			Object flagObject = flagMap.get("flag");
			boolean flag = false;
			if (null != flagObject) { // 如果flagObject是null的话，就不转换
				flag = (Boolean) flagObject;
			}

			if (true == flag) {
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
			results.setCode(MessageCode.CODE_404);
			results.setMessage("请填写正确的手机号码！");
		}

		return results;
	}

	@SystemControllerLog(description = "第三方发送数远短信内容")
	@RequestMapping(value = "sendSMS")
	@ResponseBody
	public Results<String> sendSMS(HttpServletRequest request, @RequestParam String hPhonenumber, String messageStr) {
		Results<String> results = new Results<String>();
		try {
			hPhonenumber = DESOuter.decryptBeas64DES(hPhonenumber);
			messageStr = DESOuter.decryptBeas64DES(messageStr);
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null == hPhonenumber || "".equals(hPhonenumber)) { // 如果没有传入指定的参数则返回null，不做任何处理
			return new Results<String>(MessageCode.CODE_201, "手机号码不能为空！", null);
		}

		// 验证手机号码是否合法
		if (Toolkit.isMobileNO(hPhonenumber)) {

			boolean flag = ShuyuanUtils.sendSMS(messageStr, hPhonenumber);

			if (true == flag) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage("短信下发成功");
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("短信下发失败！请重试");
			}

		} else {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("请填写正确的手机号码！");
		}

		return results;

	}

}
