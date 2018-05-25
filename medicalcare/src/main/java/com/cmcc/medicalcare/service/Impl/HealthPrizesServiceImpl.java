package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthPrizes;
import com.cmcc.medicalcare.service.IHealthPrizesService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="healthPrizesService")
@Transactional
public class HealthPrizesServiceImpl extends GenericServiceImpl<HealthPrizes, Integer>
		implements IHealthPrizesService {
	/**
	 * 
	 */
}
