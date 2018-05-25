package com.cmcc.medicalcare.controller.outer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.OrderUtils;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.PaymentPrescription;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.IPaymentPrescriptionService;
import com.cmcc.medicalcare.service.IPaymentService;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;
import com.easemob.server.comm.HttpToEasemobUtil;

/**
 * @ClassName: 结果推送接口
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/outer/pushingResult")
@Controller
public class PushingResultController {

	@Resource
	private IOrdersService ordersService;

	@Resource
	private IPaymentService paymentService;

	@Resource
	private IPaymentPrescriptionService paymentPrescriptionService;
	
	@Resource
	private IPatientUserService patientUserService;

	/**
	 * 
	 * @Title: paymentResult @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "支付结果推送")
	@RequestMapping(value = "paymentResult")
	@ResponseBody
	public Results<Map<String, Object>> paymentResult(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		String method = "/outer/pushingResult/paymentResult";
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
		if (StringUtils.isBlank(contentStr)) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("json字符串为空");
		}
		
		System.out.println("contentStr:  " + contentStr);
		JSONObject json = JSONObject.parseObject(contentStr);
		String orderno = json.getString("orderno");
		Double amount = json.getDouble("amount");
		String wetchatOrderNo = json.getString("wetchatOrderNo");
		String resultCode = json.getString("resultCode");
		String msg = json.getString("msg");
		System.out.println("成功json字符串: " + json.toJSONString());
		System.out.println("amount: " + amount);
		
		// 回填订单信息
		Orders orders = ordersService.findByParam("selectByOrderNo", orderno);
		if (null != orders) {
			if (6 == orders.getOrdersType()) { //医生开处方类型订单
				String patientPhone = orders.getPatientPhone();
				String paymentResult = paymentService.paymentResult(patientPhone, wetchatOrderNo, resultCode, msg);//支付结果回调九州通接口
			} else { //图文视频等类型订单
				//查询患者信息
				PatientUser patientUser = patientUserService.findByParam("selectByPhone", orders.getPatientPhone());
				
				String doctorLoginId = orders.getDoctorLoginId();
				String doctorName = orders.getDoctorName();
				String patientLoginId = orders.getPatientLoginId();
				String patientName = orders.getPatientName();
				String targetType = "users";
				/**
		         * 给患者发提示
		         */
		        String msgTxt = "";
		        if (5 == orders.getOrdersType()) {
		        	msgTxt = "您已成功购买视频问诊服务，接诊医生" + doctorName + "。";
				} else if (3 == orders.getOrdersType()) {
					msgTxt = "您已成功购买图文问诊服务，接诊医生" + doctorName + "。";
				}
	        	Map<String, Object> ext = new HashMap<String, Object>();
	    		ext.put("type", "videoTip");
	    		ext.put("orders", orders);
				HttpToEasemobUtil.sendTxt(doctorLoginId, patientLoginId, targetType, msgTxt, ext);
				
				/**
		         * 给医生发提示
		         */
		        String msgTxt2 = "";
		        if (5 == orders.getOrdersType()) {
		        	msgTxt2 = "问诊请求：对方购买了一次视频问诊服务，请及时处理";
				} else if (3 == orders.getOrdersType()) {
					msgTxt2 = "问诊请求：对方购买了一次图文问诊服务，请及时处理";
				}
	        	Map<String, Object> ext2 = new HashMap<String, Object>();
	    		ext2.put("type", "videoTip");
	    		ext2.put("orders", orders);
	    		ext2.put("nickName", patientName);
	    		if (patientUser != null) ext2.put("headUrl", patientUser.getHeadUrl());
				HttpToEasemobUtil.sendTxt(patientLoginId, doctorLoginId, targetType, msgTxt2, ext2);
			}
			
			orders.setOrdersPrice(String.valueOf(amount));
			orders.setPayOrderNo(wetchatOrderNo);// 支付订单号
			orders.setOrdersPayState(1);
			ordersService.updateOrders(orders);
		}
		
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}

	/**
	 * 
	 * @throws IOException
	 * @Title: refundResult @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@SystemControllerLog(description = "退款结果推送")
	@RequestMapping(value = "refundResult")
	@ResponseBody
	public Results<Map<String, Object>> refundResult(HttpServletRequest request) throws IOException {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		String method = "/outer/pushingResult/refundResult";
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
			System.out.println("contentStr:  " + contentStr);
			JSONObject json = JSONObject.parseObject(contentStr);
			String orderno = json.getString("orderno");
			Double refund = json.getDouble("refund");
			String wetchatOrderNo = json.getString("wetchatOrderNo");
			String resultCode = json.getString("resultCode");
			String msg = json.getString("msg");
			System.out.println("成功json字符串: " + json.toJSONString());
			System.out.println(refund);
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
		String method = "/outer/pushingResult/paymentResult";
		String appid = "7180f71e17db46618a999b4d445f2ee0";
		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signtype = "MD5";

		String signstr = ThirdInterfaceUtils.getMD5signstr(method, appid, timestamp);
		String url_ = "http://localhost:8080/medicalcare/outer/pushingResult/paymentResult.action?";
		url_ = ThirdInterfaceUtils.getURL(url_, appid, timestamp, signstr);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderno", 123456789);
		param.put("amount", 10.23);
		param.put("msg", "SUCCESS");
		String jsonStr = HTTPUtils.sendJsonHttpPost(url_, param, "UTF-8");
		return jsonStr;
	}

	public static void main(String[] args) {
		String method = "/outer/pushingResult/paymentResult";
		String appid = "7180f71e17db46618a999b4d445f2ee0";
		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signtype = "MD5";

		String signstr = ThirdInterfaceUtils.getMD5signstr(method, appid, timestamp);
		String url_ = "https://health.hivedata.com.cn:9011/medicalcare/outer/pushingResult/paymentResult.action?";
		url_ = ThirdInterfaceUtils.getURL(url_, appid, timestamp, signstr);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderno", 123456789);
		param.put("amount", 10.23);
		param.put("msg", "SUCCESS");
		String jsonStr = HTTPUtils.sendJsonHttpsPost(url_, param, "UTF-8");
		System.out.println(jsonStr);
	}
}
