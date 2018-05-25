package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PatientBloodSugarAuto;
import com.cmcc.medicalcare.service.IPatientBloodSugarAutoService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="patientBloodSugarAutoService")
@Transactional
public class PatientBloodSugarAutoServiceImpl extends GenericServiceImpl<PatientBloodSugarAuto, Integer> implements IPatientBloodSugarAutoService{

}
