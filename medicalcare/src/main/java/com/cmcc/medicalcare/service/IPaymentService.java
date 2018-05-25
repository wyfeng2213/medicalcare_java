package com.cmcc.medicalcare.service;

/**
 * 
 * @author Administrator
 *
 */
public interface IPaymentService{

	public String paymentResult(String patientPhone, String wetchatOrderNo, String resultCode, String msg);
}
