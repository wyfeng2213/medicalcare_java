package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthTrafficCount;
import com.cmcc.medicalcare.service.IHealthTrafficCountService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="healthTrafficCountService")
@Transactional
public class HealthTrafficCountServiceImpl extends GenericServiceImpl<HealthTrafficCount, Integer>
		implements IHealthTrafficCountService {
	/**
	 * 
	 */
}
