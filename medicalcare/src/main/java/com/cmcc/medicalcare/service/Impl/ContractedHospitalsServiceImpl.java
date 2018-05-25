package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.service.IContractedHospitalsService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="contractedHospitalsService")
@Transactional
public class ContractedHospitalsServiceImpl extends GenericServiceImpl<ContractedHospitals, Integer> implements IContractedHospitalsService{

}
