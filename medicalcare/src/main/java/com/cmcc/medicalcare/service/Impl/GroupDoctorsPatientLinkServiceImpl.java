package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.GroupDoctorsPatientLink;
import com.cmcc.medicalcare.service.IGroupDoctorsPatientLinkService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="groupDoctorsPatientLinkService")
@Transactional
public class GroupDoctorsPatientLinkServiceImpl extends GenericServiceImpl<GroupDoctorsPatientLink, Integer> implements IGroupDoctorsPatientLinkService{

}
