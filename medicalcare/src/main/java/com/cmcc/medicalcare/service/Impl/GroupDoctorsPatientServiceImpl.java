package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.GroupDoctorsPatient;
import com.cmcc.medicalcare.service.IGroupDoctorsPatientService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="groupDoctorsPatientService")
@Transactional
public class GroupDoctorsPatientServiceImpl extends GenericServiceImpl<GroupDoctorsPatient, Integer> implements IGroupDoctorsPatientService{

}
