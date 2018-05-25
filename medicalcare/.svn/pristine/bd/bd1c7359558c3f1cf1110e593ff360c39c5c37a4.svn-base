package com.cmcc.medicalcare.service;

import com.cmcc.medicalcare.model.PatientUser;

/**
 * 
 * @author Administrator
 *
 */
public interface IPatientUserService extends GenericService<PatientUser, Integer> {
	
	public PatientUser findPatientUser(String patientPhone);
	
	public String findPatientUser(String patientId,String memberId);
	
	public String saveMemberInfo(String phone, String name, String idNumber, String gender, String age);
	
	public String savePatientInfo(String phone, String name, String memberPhone, String age, String gender,
			String occupation, String height, String idNumber);
	
	public String updatePatientInfo(String phone, String name, String memberPhone, String age,
			String gender, String occupation, String height, String idNumber, String memberId, String patientId);

	public String updateMemberInfo(String phone, String name, String updatePhone, String idNumber, String gender, String age);

}
