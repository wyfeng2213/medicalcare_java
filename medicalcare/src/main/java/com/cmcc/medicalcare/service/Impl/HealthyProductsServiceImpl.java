package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthyProducts;
import com.cmcc.medicalcare.service.IHealthyProductsService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="healthyProductsService")
@Transactional
public class HealthyProductsServiceImpl extends GenericServiceImpl<HealthyProducts, Integer>
		implements IHealthyProductsService {
	/**
	 * 
	 */
}
