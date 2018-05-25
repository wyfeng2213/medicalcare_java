package com.cmcc.medicalcare.service;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.model.PaymentPrescription;

/**
 * 
 * @author Administrator
 *
 */
public interface IPaymentPrescriptionService extends GenericService<PaymentPrescription, Integer> {

	public JSONObject findPrescriptionAndOrder(String memberId, String doctorPhone, String patientPhone,
			String prescriptionId);
}
