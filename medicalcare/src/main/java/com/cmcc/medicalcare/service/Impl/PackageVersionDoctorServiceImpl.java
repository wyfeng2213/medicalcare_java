package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.PackageVersionDoctor;
import com.cmcc.medicalcare.service.IPackageVersionDoctorService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="packageVersionDoctorService")
@Transactional
public class PackageVersionDoctorServiceImpl extends GenericServiceImpl<PackageVersionDoctor, Integer> implements IPackageVersionDoctorService{

}
