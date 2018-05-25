package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PatientBloodPressureAuto;
import com.cmcc.medicalcare.service.IPatientBloodPressureAutoService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="patientBloodPressureAutoService")
@Transactional
public class PatientBloodPressureAutoServiceImpl extends GenericServiceImpl<PatientBloodPressureAuto, Integer> implements IPatientBloodPressureAutoService{

}
