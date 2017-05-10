package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="secretaryDoctorsLinkService")
@Transactional
public class SecretaryDoctorsLinkServiceImpl extends GenericServiceImpl<SecretaryDoctorsLink, Integer> implements ISecretaryDoctorsLinkService{

}
