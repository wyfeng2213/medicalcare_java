package com.cmcc.medicalcare.controller.app.patient.component;

import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.model.PatientHealthRecord;
import com.cmcc.medicalcare.utils.DES;

/**
 * 健康档案加密解密单元
 * @author zds
 *
 */
public class HealthRecordDESUtil {
    
    /**
     * 加密健康档案
     * @param patientHealthRecords
     * @return
     */
    public static PatientHealthRecord encryptPatientHealthRecord(PatientHealthRecord patientHealthRecords) {
    	if (null == patientHealthRecords) {
    		return null;
    	}
    	
		try {
			patientHealthRecords.setHeight(DES.encryptDESBeas64(patientHealthRecords.getHeight()));
			patientHealthRecords.setWeight(DES.encryptDESBeas64(patientHealthRecords.getWeight()));
			patientHealthRecords.setSex(DES.encryptDESBeas64(patientHealthRecords.getSex()));
			patientHealthRecords.setBirthday(DES.encryptDESBeas64(patientHealthRecords.getBirthday()));
			patientHealthRecords.setIdentification(DES.encryptDESBeas64(patientHealthRecords.getIdentification()));
			patientHealthRecords.setWorkplace(DES.encryptDESBeas64(patientHealthRecords.getWorkplace()));
			patientHealthRecords.setContactPerson(DES.encryptDESBeas64(patientHealthRecords.getContactPerson()));
			patientHealthRecords.setContactPersonPhone(DES.encryptDESBeas64(patientHealthRecords.getContactPersonPhone()));
			patientHealthRecords.setCensustype(DES.encryptDESBeas64(patientHealthRecords.getCensustype()));
			patientHealthRecords.setEthnic(DES.encryptDESBeas64(patientHealthRecords.getEthnic()));
			patientHealthRecords.setBloodtype(DES.encryptDESBeas64(patientHealthRecords.getBloodtype()));
			patientHealthRecords.setCulturalLevel(DES.encryptDESBeas64(patientHealthRecords.getCulturalLevel()));
			patientHealthRecords.setJob(DES.encryptDESBeas64(patientHealthRecords.getJob()));
			patientHealthRecords.setMaritalStatus(DES.encryptDESBeas64(patientHealthRecords.getMaritalStatus()));
			patientHealthRecords.setPaymentMethod(DES.encryptDESBeas64(patientHealthRecords.getPaymentMethod()));
			patientHealthRecords.setRadiationHistory(DES.encryptDESBeas64(patientHealthRecords.getRadiationHistory()));
			patientHealthRecords.setPastHistoryIllness(DES.encryptDESBeas64(patientHealthRecords.getPastHistoryIllness()));
			patientHealthRecords.setPastHistorySurgery(DES.encryptDESBeas64(patientHealthRecords.getPastHistorySurgery()));
			patientHealthRecords.setPastHistoryTrauma(DES.encryptDESBeas64(patientHealthRecords.getPastHistoryTrauma()));
			patientHealthRecords.setPastHistoryTransfusionBlood(DES.encryptDESBeas64(patientHealthRecords.getPastHistoryTransfusionBlood()));
			patientHealthRecords.setFamilyHistory(DES.encryptDESBeas64(patientHealthRecords.getFamilyHistory()));
			patientHealthRecords.setGeneticDisease(DES.encryptDESBeas64(patientHealthRecords.getGeneticDisease()));
			patientHealthRecords.setDeformityStatus(DES.encryptDESBeas64(patientHealthRecords.getDeformityStatus()));
			patientHealthRecords.setSmokingHistory(DES.encryptDESBeas64(patientHealthRecords.getSmokingHistory()));
			patientHealthRecords.setDrinkingHistory(DES.encryptDESBeas64(patientHealthRecords.getDrinkingHistory()));
			patientHealthRecords.setSleepState(DES.encryptDESBeas64(patientHealthRecords.getSleepState()));
			patientHealthRecords.setStoolurineIsNormal(DES.encryptDESBeas64(patientHealthRecords.getStoolurineIsNormal()));
			patientHealthRecords.setDrugHistory(DES.encryptDESBeas64(patientHealthRecords.getDrugHistory()));
			patientHealthRecords.setAllergicHistory(DES.encryptDESBeas64(patientHealthRecords.getAllergicHistory()));
			patientHealthRecords.setDietHistory(DES.encryptDESBeas64(patientHealthRecords.getDietHistory()));
			patientHealthRecords.setLivingEnvironment(DES.encryptDESBeas64(patientHealthRecords.getLivingEnvironment()));
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return patientHealthRecords;
		
    }
    
    /**
     * 解密健康档案
     * @param patientHealthRecords
     * @return
     */
    public static PatientHealthRecord decryptPatientHealthRecord(PatientHealthRecord patientHealthRecords) {
    	if (null == patientHealthRecords) {
    		return null;
    	}
    	
		try {
			patientHealthRecords.setHeight(DES.decryptBeas64DES(patientHealthRecords.getHeight()));
			patientHealthRecords.setWeight(DES.decryptBeas64DES(patientHealthRecords.getWeight()));
			patientHealthRecords.setSex(DES.decryptBeas64DES(patientHealthRecords.getSex()));
			patientHealthRecords.setBirthday(DES.decryptBeas64DES(patientHealthRecords.getBirthday()));
			patientHealthRecords.setIdentification(DES.decryptBeas64DES(patientHealthRecords.getIdentification()));
			patientHealthRecords.setWorkplace(DES.decryptBeas64DES(patientHealthRecords.getWorkplace()));
			patientHealthRecords.setContactPerson(DES.decryptBeas64DES(patientHealthRecords.getContactPerson()));
			patientHealthRecords.setContactPersonPhone(DES.decryptBeas64DES(patientHealthRecords.getContactPersonPhone()));
			patientHealthRecords.setCensustype(DES.decryptBeas64DES(patientHealthRecords.getCensustype()));
			patientHealthRecords.setEthnic(DES.decryptBeas64DES(patientHealthRecords.getEthnic()));
			patientHealthRecords.setBloodtype(DES.decryptBeas64DES(patientHealthRecords.getBloodtype()));
			patientHealthRecords.setCulturalLevel(DES.decryptBeas64DES(patientHealthRecords.getCulturalLevel()));
			patientHealthRecords.setJob(DES.decryptBeas64DES(patientHealthRecords.getJob()));
			patientHealthRecords.setMaritalStatus(DES.decryptBeas64DES(patientHealthRecords.getMaritalStatus()));
			patientHealthRecords.setPaymentMethod(DES.decryptBeas64DES(patientHealthRecords.getPaymentMethod()));
			patientHealthRecords.setRadiationHistory(DES.decryptBeas64DES(patientHealthRecords.getRadiationHistory()));
			patientHealthRecords.setPastHistoryIllness(DES.decryptBeas64DES(patientHealthRecords.getPastHistoryIllness()));
			patientHealthRecords.setPastHistorySurgery(DES.decryptBeas64DES(patientHealthRecords.getPastHistorySurgery()));
			patientHealthRecords.setPastHistoryTrauma(DES.decryptBeas64DES(patientHealthRecords.getPastHistoryTrauma()));
			patientHealthRecords.setPastHistoryTransfusionBlood(DES.decryptBeas64DES(patientHealthRecords.getPastHistoryTransfusionBlood()));
			patientHealthRecords.setFamilyHistory(DES.decryptBeas64DES(patientHealthRecords.getFamilyHistory()));
			patientHealthRecords.setGeneticDisease(DES.decryptBeas64DES(patientHealthRecords.getGeneticDisease()));
			patientHealthRecords.setDeformityStatus(DES.decryptBeas64DES(patientHealthRecords.getDeformityStatus()));
			patientHealthRecords.setSmokingHistory(DES.decryptBeas64DES(patientHealthRecords.getSmokingHistory()));
			patientHealthRecords.setDrinkingHistory(DES.decryptBeas64DES(patientHealthRecords.getDrinkingHistory()));
			patientHealthRecords.setSleepState(DES.decryptBeas64DES(patientHealthRecords.getSleepState()));
			patientHealthRecords.setStoolurineIsNormal(DES.decryptBeas64DES(patientHealthRecords.getStoolurineIsNormal()));
			patientHealthRecords.setDrugHistory(DES.decryptBeas64DES(patientHealthRecords.getDrugHistory()));
			patientHealthRecords.setAllergicHistory(DES.decryptBeas64DES(patientHealthRecords.getAllergicHistory()));
			patientHealthRecords.setDietHistory(DES.decryptBeas64DES(patientHealthRecords.getDietHistory()));
			patientHealthRecords.setLivingEnvironment(DES.decryptBeas64DES(patientHealthRecords.getLivingEnvironment()));
		} catch (CoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return patientHealthRecords;
		
    }
    
}
