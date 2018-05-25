package com.cmcc.medicalcare.controller.app.patient.component;

import java.util.List;

import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.model.PatientMedicalRecord;
import com.cmcc.medicalcare.utils.DES;

/**
 * 病历记录加密解密单元
 * @author zds
 *
 */
public class MedicalRecordDESUtil {
    
    /**
     * 加密病历记录_单个
     * @param patientMedicalRecord
     * @return
     */
    public static PatientMedicalRecord encryptPatientMedicalRecord(PatientMedicalRecord patientMedicalRecord) {
    	if (null == patientMedicalRecord) {
    		return null;
    	}
    	
		try {
			patientMedicalRecord.setDiagnoseContent(DES.encryptDESBeas64(patientMedicalRecord.getDiagnoseContent()));
			patientMedicalRecord.setDiagnoseResult(DES.encryptDESBeas64(patientMedicalRecord.getDiagnoseResult()));
			patientMedicalRecord.setMechanism(DES.encryptDESBeas64(patientMedicalRecord.getMechanism()));
			patientMedicalRecord.setDetails(DES.encryptDESBeas64(patientMedicalRecord.getDetails()));
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return patientMedicalRecord;
		
    }
    
    /**
     * 加密病历记录_批量
     * @param patientMedicalRecordList
     * @return
     */
    public static List<PatientMedicalRecord> encryptPatientMedicalRecord(List<PatientMedicalRecord> patientMedicalRecordList) {
    	if (null == patientMedicalRecordList || patientMedicalRecordList.size() < 1) {
    		return null;
    	}
    	
    	for (int i=0; i<patientMedicalRecordList.size(); i++) {
    		encryptPatientMedicalRecord(patientMedicalRecordList.get(i));
    	}
		
		return patientMedicalRecordList;
		
    }
    
    /**
     * 解密病历记录_单个
     * @param patientMedicalRecord
     * @return
     */
    public static PatientMedicalRecord decryptPatientMedicalRecord(PatientMedicalRecord patientMedicalRecord) {
    	if (null == patientMedicalRecord) {
    		return null;
    	}
    	
		try {
			patientMedicalRecord.setDiagnoseContent(DES.decryptBeas64DES(patientMedicalRecord.getDiagnoseContent()));
			patientMedicalRecord.setDiagnoseResult(DES.decryptBeas64DES(patientMedicalRecord.getDiagnoseResult()));
			patientMedicalRecord.setMechanism(DES.decryptBeas64DES(patientMedicalRecord.getMechanism()));
			patientMedicalRecord.setDetails(DES.decryptBeas64DES(patientMedicalRecord.getDetails()));
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return patientMedicalRecord;
		
    }
    
    /**
     * 解密病历记录_批量
     * @param patientMedicalRecordList
     * @return
     */
    public static List<PatientMedicalRecord> decryptPatientMedicalRecord(List<PatientMedicalRecord> patientMedicalRecordList) {
    	if (null == patientMedicalRecordList || patientMedicalRecordList.size() < 1) {
    		return null;
    	}
    	
    	for (int i=0; i<patientMedicalRecordList.size(); i++) {
    		decryptPatientMedicalRecord(patientMedicalRecordList.get(i));
    	}
		
		return patientMedicalRecordList;
		
    }
    
}
