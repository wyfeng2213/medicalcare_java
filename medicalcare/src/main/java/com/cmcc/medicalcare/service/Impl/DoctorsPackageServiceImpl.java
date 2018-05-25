package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.DoctorsPackage;
import com.cmcc.medicalcare.service.IDoctorsPackageService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="doctorsPackageService")
@Transactional
public class DoctorsPackageServiceImpl extends GenericServiceImpl<DoctorsPackage, Integer> implements IDoctorsPackageService{

}
