package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.service.IDoctorsTeamService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="doctorsTeamService")
@Transactional
public class DoctorsTeamServiceImpl extends GenericServiceImpl<DoctorsTeam, Integer> implements IDoctorsTeamService{

}
