package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.DoctorsFollowupPlanTemplate;
import com.cmcc.medicalcare.service.IDoctorsFollowupPlanTemplateService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="doctorsFollowupPlanTemplateService")
@Transactional
public class DoctorsFollowupPlanTemplateServiceImpl extends GenericServiceImpl<DoctorsFollowupPlanTemplate, Integer> implements IDoctorsFollowupPlanTemplateService{

}
