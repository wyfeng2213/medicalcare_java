package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthCoins;
import com.cmcc.medicalcare.service.IHealthCoinsService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="healthCoinsService")
@Transactional
public class HealthCoinsServiceImpl extends GenericServiceImpl<HealthCoins, Integer>
		implements IHealthCoinsService {
	/**
	 * 
	 */
}
