package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthReferee;
import com.cmcc.medicalcare.service.IHealthRefereeService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="healthRefereeService")
@Transactional
public class HealthRefereeServiceImpl extends GenericServiceImpl<HealthReferee, Integer>
		implements IHealthRefereeService {
	/**
	 * 
	 */
}
