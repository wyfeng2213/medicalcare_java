package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPrescriptionUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.OrderUtils;
import com.cmcc.medicalcare.model.PaymentPrescription;
import com.cmcc.medicalcare.service.IPaymentPrescriptionService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="paymentPrescriptionService")
@Transactional
public class PaymentPrescriptionServiceImpl extends GenericServiceImpl<PaymentPrescription, Integer> implements IPaymentPrescriptionService {

	@Override
	public JSONObject findPrescriptionAndOrder(String memberId, String doctorPhone, String patientPhone,
			String prescriptionId) {
		JSONObject prescriptionOrder = new JSONObject();
		
		//查询九州通医生用户
		JSONObject doctorEntity = DoctorUserUtils.findDoctorInfo(doctorPhone);
		if (null == doctorEntity) {
			return null;
		}
		String doctorId = doctorEntity.getString("doctorId");
		
		//查询九州通患者用户
		JSONObject memberPatient = MemberUserUtils.findMemberPatient(patientPhone);
		if (null == memberPatient) {
			return null;
		}
		String patientId = memberPatient.getString("patientId");
		
		//查询九州通处方信息
		String prescriptionInfo = DoctorPrescriptionUtils.findPrescription(memberId, patientId, doctorId, "", "", prescriptionId, "");
		if (null == prescriptionInfo) {
			return null;
		}
		JSONObject prescriptionJson = JSONObject.parseObject(prescriptionInfo);
		String status = prescriptionJson.getString("status");
		JSONObject prescriptionInfo_ = null;
		if ("0".equals(status)) {
			prescriptionInfo_ = JSONObject.parseObject(prescriptionJson.getString("data"));
		}
		
		//查询九州通订单信息
		String orderInfo = OrderUtils.findOrderinfo("", memberId, prescriptionId, "");
		if (null == orderInfo) {
			return null;
		}
		JSONObject orderInfoJson = JSONObject.parseObject(orderInfo);
		String status2 = orderInfoJson.getString("status");
		JSONObject orderInfo_ = null;
		if ("0".equals(status2)) {
			orderInfo_ = JSONObject.parseObject(orderInfoJson.getString("data"));
		}
		
		prescriptionOrder.put("prescriptionInfo", prescriptionInfo_);
		prescriptionOrder.put("orderInfo", orderInfo_);

		return prescriptionOrder;
	}

}
