package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PackageVersionPatient;
import com.cmcc.medicalcare.service.IPackageVersionPatientService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="packageVersionPatientService")
@Transactional
public class PackageVersionPatientServiceImpl extends GenericServiceImpl<PackageVersionPatient, Integer> implements IPackageVersionPatientService{

}
