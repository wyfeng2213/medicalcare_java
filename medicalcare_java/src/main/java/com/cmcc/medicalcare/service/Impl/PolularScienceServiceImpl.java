package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PolularScience;
import com.cmcc.medicalcare.service.IPolularScienceService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="polularScienceService")
@Transactional
public class PolularScienceServiceImpl extends GenericServiceImpl<PolularScience, Integer> implements IPolularScienceService{

}
