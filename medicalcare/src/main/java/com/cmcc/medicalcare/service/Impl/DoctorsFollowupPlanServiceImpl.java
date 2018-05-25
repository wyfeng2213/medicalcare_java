package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.DoctorsFollowupPlan;
import com.cmcc.medicalcare.service.IDoctorsFollowupPlanService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="doctorsFollowupPlanService")
@Transactional
public class DoctorsFollowupPlanServiceImpl extends GenericServiceImpl<DoctorsFollowupPlan, Integer> implements IDoctorsFollowupPlanService{

}
