package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PatientHealthRecords;
import com.cmcc.medicalcare.service.IPatientHealthRecordsService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="patientHealthRecordsService")
@Transactional
public class PatientHealthRecordsServiceImpl extends GenericServiceImpl<PatientHealthRecords, Integer> implements IPatientHealthRecordsService{

}
