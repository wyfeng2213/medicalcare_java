package com.cmcc.medicalcare.service;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.model.Orders;

/**
 * 
 * @author Administrator
 *
 */
public interface IOrdersService extends GenericService<Orders, Integer> {

	void updateOrders(Orders orders);
/**
 * 
 */

	public String saveDoctorPatient(String doctorPhone, String patientPhone);
	
	public JSONObject createPaymentOrders(String orderNo, String amount, String subject, String body, String detail, String attach);
}
