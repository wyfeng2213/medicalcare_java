package com.cmcc.medicalcare.controller.app.patient.v2;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.common.Utils;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPrescriptionUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.OrderUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PatientUserUtils;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.model.PaymentPrescription;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.service.IPaymentPrescriptionService;

/**
 * 患者处方
 * @author zds
 *
 */
@Controller
@RequestMapping({ "/app/patient/prescription/v2" })
public class PatientPrescriptionV2Controller {
	
	@Resource
	private IOrdersService ordersService;
	
	@Resource
	private IPaymentPrescriptionService paymentPrescriptionService;

	/**
	 * 处方查询
	 * 
	 * @param request
	 * @param parameter={"memberId":"会员id", "patientId":"患者id", "doctorId":"医生id"
	 * , "startTime":"起始时间", "endTime":"结束时间", "prescriptionId":"处方id", "startNumber":"起始页"}
	 * @return
	 */
	@RequestMapping(value = "findPrescription")
	@ResponseBody
	@SystemControllerLog(description = "患者处方查询")
	public Results<Map<String, Object>> findPrescription(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String memberId = dataJsonObject.getString("memberId");
		String patientId = dataJsonObject.getString("patientId");
		String doctorId = dataJsonObject.getString("doctorId");
		String startTime = dataJsonObject.getString("startTime");
		String endTime = dataJsonObject.getString("endTime");
		String prescriptionId = dataJsonObject.getString("prescriptionId");
		String startNumber = dataJsonObject.getString("startNumber");
		
		if (StringUtils.isBlank(memberId)) { memberId = ""; }
		if (StringUtils.isBlank(patientId)) { patientId = ""; }
		if (StringUtils.isBlank(doctorId)) { doctorId = ""; }
		if (StringUtils.isBlank(startTime)) { startTime = ""; }
		if (StringUtils.isBlank(endTime)) { endTime = ""; }
		if (StringUtils.isBlank(prescriptionId)) { prescriptionId = ""; }
		if (StringUtils.isBlank(startNumber)) { startNumber = ""; }
		
		String prescriptionInfo = DoctorPrescriptionUtils.findPrescription(memberId, patientId, doctorId, startTime, endTime, prescriptionId, startNumber);

		if (prescriptionInfo != null) {
			JSONObject prescriptionJson = JSONObject.parseObject(prescriptionInfo);
			String status = prescriptionJson.getString("status");
			JSONObject prescriptionInfo_ = null;
			JSONObject prescriptionInfo_new = new JSONObject();
			if ("0".equals(status)) {
				prescriptionInfo_ = JSONObject.parseObject(prescriptionJson.getString("data"));
			}
			
			//患者查询处方，在处方信息里，填充医生实体
			if (prescriptionInfo_ != null) {
				JSONArray prescriptions_arr = prescriptionInfo_.getJSONArray("prescriptions");
				JSONArray prescriptions_arr_new = new JSONArray();
				if (prescriptions_arr != null && prescriptions_arr.size()>0) {
					for(int i=0;i<prescriptions_arr.size();i++){
						JSONObject prescriptions_ = (JSONObject) prescriptions_arr.get(i);
						String doctorId_ = prescriptions_.getString("orderId");
						String doctorName_ = prescriptions_.getString("orderName");
						if (StringUtils.isBlank(doctorId_)) {
							doctorId_="";
						}
						if (StringUtils.isBlank(doctorName_)) {
							doctorName_="";
						}
						JSONObject doctorEntity = DoctorUserUtils.findDoctorInfo(doctorId_, doctorName_);
						
						prescriptions_.put("doctorEntity", doctorEntity);
						prescriptions_arr_new.add(prescriptions_);
					}
				}
				
				JSONArray prescriptiondrugs_arr = prescriptionInfo_.getJSONArray("prescriptiondrugs");
				prescriptionInfo_new.put("prescriptiondrugs", prescriptiondrugs_arr);
				prescriptionInfo_new.put("prescriptions", prescriptions_arr_new);
			}
			
			map.put("prescriptionInfo", prescriptionInfo_new);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("没有数据");
			return results;
		}

	}
	
	/**
	 * 订单查询
	 * @param request
	 * @param parameter={"orderinfoId":"订单id", "memberId":"会员id", "prescriptionId":"处方id", "startNumber":"起始页"}
	 * @return
	 */
	@RequestMapping(value = "findOrderinfo")
	@ResponseBody
	@SystemControllerLog(description = "处方订单查询")
	public Results<Map<String, Object>> findOrderinfo(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String orderinfoId = dataJsonObject.getString("orderinfoId");
		String memberId = dataJsonObject.getString("memberId");
		String prescriptionId = dataJsonObject.getString("prescriptionId");
		String startNumber = dataJsonObject.getString("startNumber");
		
		if (StringUtils.isBlank(memberId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		String orderInfo = OrderUtils.findOrderinfo(orderinfoId, memberId, prescriptionId, startNumber);
		
		if (orderInfo != null) {
			JSONObject orderInfoJson = JSONObject.parseObject(orderInfo);
			String status = orderInfoJson.getString("status");
			JSONObject orderInfo_ = null;
			if ("0".equals(status)) {
				orderInfo_ = JSONObject.parseObject(orderInfoJson.getString("data"));
			}
			map.put("orderInfo", orderInfo_);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("没有数据");
			return results;
		}

	}
	
	/**
	 * 患者处方支付
	 * @param request
	 * @param parameter={"memberId":"会员id", "prescriptionId":"处方id", "addressId":"收货地址id"}
	 * @return
	 */
	@RequestMapping(value = "paymentPrescription")
	@ResponseBody
	@SystemControllerLog(description = "患者处方支付")
	public Results<Map<String, Object>> paymentPrescription(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String memberId = dataJsonObject.getString("memberId");
		String prescriptionId = dataJsonObject.getString("prescriptionId");
		String addressId = dataJsonObject.getString("addressId");
		
		if (StringUtils.isBlank(memberId) || StringUtils.isBlank(prescriptionId) || StringUtils.isBlank(addressId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		//查询orders表信息
		Orders orders = ordersService.findByParam("selectByPrescriptionId", prescriptionId);
		if (null == orders) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("不存在相应的订单");
			return results;
		}
		String doctorPhone = orders.getDoctorPhone(); 
		String doctorName = orders.getDoctorName();
		String patientPhone = orders.getPatientPhone();
		String patientName = orders.getPatientName();
		
		//查询处方订单信息
		JSONObject prescriptionOrder = paymentPrescriptionService.findPrescriptionAndOrder(memberId, doctorPhone, patientPhone, prescriptionId);
		if (null == prescriptionOrder) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("创建充值订单失败");
			return results;
		}
		
		//计算处方订单总金额
		Double amount_d = 0.0;
		JSONObject prescriptionInfo = prescriptionOrder.getJSONObject("prescriptionInfo");
		JSONObject orderInfo = prescriptionOrder.getJSONObject("orderInfo");
		if (null == prescriptionInfo || null == orderInfo) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("创建充值订单失败");
			return results;
		}
		JSONArray prescriptions = prescriptionInfo.getJSONArray("prescriptions");
		if (prescriptions!=null && prescriptions.size()>0) {
			for(int i=0;i<prescriptions.size();i++){
				JSONObject pre = (JSONObject) prescriptions.get(i);
				Double serviceMoney = pre.getDouble("serviceMoney");
				Double fee = pre.getDouble("fee");
				amount_d += serviceMoney + fee;
			}
		}
		JSONArray orderinfos = orderInfo.getJSONArray("orderinfos");
		if (orderinfos!=null && orderinfos.size()>0) {
			for(int i=0;i<orderinfos.size();i++){
				JSONObject order = (JSONObject) orderinfos.get(i);
				Double orderMoney = order.getDouble("orderMoney");
				amount_d += orderMoney;
			}
		}
		
		//创建充值订单
		String orderNo = Utils.getCatId();
		String amount = "0.01";System.out.println("amount===================================" + amount_d);
//		String amount = String.valueOf(amount_d);
		String subject = orders.getOrdersTitle();
		String body = patientName + " 购买  " + doctorName + orders.getOrdersTitle();
		String detail = patientName + " 购买  " + doctorName + orders.getOrdersTitle();
		String attach = "";
		JSONObject charge = ordersService.createPaymentOrders(orderNo, amount, subject, body, detail, attach);
		if (null == charge) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("创建充值订单失败");
			return results;
		}
		
		//回填orderNo
		orders.setOrderNo(orderNo);
		ordersService.updateOrders(orders);
		
		//入库患者处方支付表
		PaymentPrescription paymentPrescription = new PaymentPrescription();
		paymentPrescription.setMemberId(memberId);
		paymentPrescription.setPrescriptionId(prescriptionId);
		paymentPrescription.setAddressId(addressId);
		paymentPrescription.setPayState(0);
		paymentPrescription.setOrderinfoJson(orderInfo.toString());
		orders.setCreatetime(new Date());
		paymentPrescriptionService.insert(paymentPrescription);
		
		map.put("charge", charge);
		map.put("orders", orders);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}
	
}
