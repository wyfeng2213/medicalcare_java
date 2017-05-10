package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PatientPackage;
import com.cmcc.medicalcare.service.IPatientPackageService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="patientPackageService")
@Transactional
public class PatientPackageServiceImpl extends GenericServiceImpl<PatientPackage, Integer> implements IPatientPackageService{

}
