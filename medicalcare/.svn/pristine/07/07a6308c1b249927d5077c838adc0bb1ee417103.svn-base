package com.cmcc.medicalcare.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.cache.redis.Constants;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPatientsUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.OrderUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PatientUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PaymentUtils;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.model.PaymentPrescription;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.service.IPaymentPrescriptionService;
import com.cmcc.medicalcare.service.IPaymentService;
import com.easemob.server.comm.HttpToEasemobUtil;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="paymentService")
@Transactional
public class PaymentServiceImpl implements IPaymentService{
	
	@Resource
	private RedisUtil redisUtil;
	
	@Resource
	private IPaymentPrescriptionService paymentPrescriptionService;

	/* (非 Javadoc) 
	* <p>Title: paymentResult</p> 
	* <p>Description: </p> 
	* @param isFefund
	* @param orderinfoId
	* @param addressId
	* @param payDetails
	* @return 
	* @see com.cmcc.medicalcare.service.IPaymentService#paymentResult(java.lang.String, java.lang.String, java.lang.String, com.alibaba.fastjson.JSONArray) 
	*/
	@Override
	public String paymentResult(String patientPhone, String wetchatOrderNo, String resultCode, String msg) {
		JSONObject memberPatient = MemberUserUtils.findMemberPatient(patientPhone);
		String memberId = memberPatient.getString("memberId");

		//查询九州通订单信息
		String jsonStr1 = OrderUtils.findOrderinfo("", memberId, "", "");
		JSONObject json2 = JSONObject.parseObject(jsonStr1);
		String status = json2.getString("status");
		if (!"0".equals(status)) {
			return null;
		}
		
		JSONObject jsonObject = json2.getJSONObject("data");
		JSONArray orderinfosArray = jsonObject.getJSONArray("orderinfos");
		if (orderinfosArray.size() < 1) {
			return null;
		}
		for (int i = 0; i < orderinfosArray.size(); i++) {
			String orderinfo = orderinfosArray.get(i).toString();
			JSONObject json3 = JSONObject.parseObject(orderinfo);
			String presscriptionId = (String) json3.get("presscriptionId");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("presscriptionId", presscriptionId);
			params.put("memberId", memberId);
			List<PaymentPrescription> paymentPrescriptionList = paymentPrescriptionService
					.getList("getPrescription", params);
			PaymentPrescription paymentPrescription = paymentPrescriptionList.get(0);

			String addressId = paymentPrescription.getAddressId();
			String orderinfoJson = paymentPrescription.getOrderinfoJson();
			JSONArray orderinfoArray = JSONArray.parseArray(orderinfoJson);
			for (int k = 0; orderinfoArray.size() > k; k++) {
				JSONObject orderinfoJsonObject = (JSONObject) orderinfoArray.get(k);
				String orderMoney = (String) orderinfoJsonObject.get("orderMoney");
				String patientPayType = (String) orderinfoJsonObject.get("patientPayType");
				String orderinfoId = (String) orderinfoJsonObject.get("orderinfoId");

				JSONObject payDetailsJSONObject = new JSONObject();
				payDetailsJSONObject.put("feeType", "");
				payDetailsJSONObject.put("amount", orderMoney);
				payDetailsJSONObject.put("payType", patientPayType);
				payDetailsJSONObject.put("paymentOrderNumber", "");
				payDetailsJSONObject.put("wetchatOrderNo", wetchatOrderNo);
				payDetailsJSONObject.put("resultCode", resultCode);
				payDetailsJSONObject.put("msg", msg);

				JSONArray payDetails = new JSONArray();
				payDetails.add(payDetailsJSONObject);
				String jsonStr = PaymentUtils.paymentResult("2", orderinfoId, addressId, payDetails);
			}

		}
		
		return "成功";
		
	}

	

}
