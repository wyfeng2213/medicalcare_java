package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="patientDoctorsLinkService")
@Transactional
public class PatientDoctorsLinkServiceImpl extends GenericServiceImpl<PatientDoctorsLink, Integer> implements IPatientDoctorsLinkService{

}
