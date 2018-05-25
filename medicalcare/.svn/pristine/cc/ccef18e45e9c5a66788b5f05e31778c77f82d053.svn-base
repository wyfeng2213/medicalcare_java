package com.cmcc.medicalcare.controller.outer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 提醒患者接口
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/outer/remindingPatient")
@Controller
public class RemindingPatientController {

	/**
	 * 
	* @Title: orderReminding 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "患者的订单提醒")
	@RequestMapping(value = "orderReminding")
	@ResponseBody
	public Results<Map<String, Object>> orderReminding(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		String method = "/outer/remindingPatient/orderReminding";
		String appid = request.getParameter("appid");
		String timestamp = request.getParameter("timestamp");
		String signtype = request.getParameter("signtype");
		String signstr = request.getParameter("signstr");

		String signstr_ = ThirdInterfaceUtils.getMD5signstr(method, appid, timestamp);

		if (!signstr_.equals(signstr)) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("参数错误");
			return results;
		}

		InputStream is = null;
		String contentStr = "";

		try {
			is = request.getInputStream();
			contentStr = IOUtils.toString(is, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_404);
			results.setMessage("无法获取json字符串");
			return results;
		}
		if (StringUtils.isNotBlank(contentStr)) {
			System.out.println("contentStr:  "+contentStr);
			JSONObject json = JSONObject.parseObject(contentStr);
			Integer presId = json.getInteger("presId");//处方id
			String patPhone = json.getString("patPhone");//会员手机号
			String DocPhone = json.getString("DocPhone");//医生手机号
			String orderFee = json.getString("orderFee");//订单费用
			System.out.println("成功json字符串: "+json.toJSONString());
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("presId", presId);
			ext.put("orderFee", orderFee);
			ext.put("messageType", "orderReminding");
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
			UserName userName = new UserName();
			userName.add(Constant.patient+patPhone);
			msg.from(Constant.doctor+DocPhone).target(userName).targetType("users").msg(msgContent)
					.ext(ext);
			Object result = easemobSendMessage.sendMessage(msg);
			if (result==null) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("订单提醒失败");
				return results;
			}
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("json字符串为空");
		}
		return results;

	}
	
	
	/**
	 * 
	* @Title: orderRemindingAfterTransmit 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "转方完成之后推送")
	@RequestMapping(value = "orderRemindingAfterTransmit")
	@ResponseBody
	public Results<Map<String, Object>> orderRemindingAfterTransmit(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		String method = "/outer/remindingPatient/orderRemindingAfterTransmit";
		String appid = request.getParameter("appid");
		String timestamp = request.getParameter("timestamp");
		String signtype = request.getParameter("signtype");
		String signstr = request.getParameter("signstr");

		String signstr_ = ThirdInterfaceUtils.getMD5signstr(method, appid, timestamp);

		if (!signstr_.equals(signstr)) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("参数错误");
			return results;
		}

		InputStream is = null;
		String contentStr = "";

		try {
			is = request.getInputStream();
			contentStr = IOUtils.toString(is, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_404);
			results.setMessage("无法获取json字符串");
			return results;
		}
		if (StringUtils.isNotBlank(contentStr)) {
			System.out.println("contentStr:  "+contentStr);
			JSONObject json = JSONObject.parseObject(contentStr);
			Integer presId = json.getInteger("presId");//处方id
			String orderFee = json.getString("orderFee");//处方总费用
			String orderidlist = json.getString("orderidlist");//订单明细
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("json字符串为空");
		}
		return results;

	}
	
	
	@RequestMapping(value = "test")
	@ResponseBody
	public String test(HttpServletRequest request) throws IOException {
		String method = "/outer/remindingPatient/orderReminding";
		String appid = "7180f71e17db46618a999b4d445f2ee0";
		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signtype = "MD5";

		String signstr = ThirdInterfaceUtils.getMD5signstr(method, appid, timestamp);
		String url_ = "http://localhost:8080/medicalcare/outer/remindingPatient/orderReminding.action?";
		url_ = ThirdInterfaceUtils.getURL(url_,appid, timestamp, signstr);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("presId", 123456789);
		param.put("patPhone", "15876587133");
		param.put("DocPhone", "15876587133");
		param.put("orderFee", "100.9");
		String jsonStr = HTTPUtils.sendJsonHttpPost(url_, param, "UTF-8");
		return jsonStr;
	}

}
