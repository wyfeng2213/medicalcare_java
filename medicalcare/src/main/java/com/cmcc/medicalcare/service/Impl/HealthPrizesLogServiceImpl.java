package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthPrizesLog;
import com.cmcc.medicalcare.service.IHealthPrizesLogService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="healthPrizesLogService")
@Transactional
public class HealthPrizesLogServiceImpl extends GenericServiceImpl<HealthPrizesLog, Integer>
		implements IHealthPrizesLogService {
	/**
	 * 
	 */
}
