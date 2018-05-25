package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.DoctorsConsultationMessageLog;
import com.cmcc.medicalcare.service.IDoctorsConsultationMessageLogService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="doctorsConsultationMessageLogService")
@Transactional
public class DoctorsConsultationMessageLogServiceImpl extends GenericServiceImpl<DoctorsConsultationMessageLog, Integer> implements IDoctorsConsultationMessageLogService{

}
