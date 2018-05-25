package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SecretaryPatientLink;
import com.cmcc.medicalcare.service.ISecretaryPatientLinkService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="secretaryPatientLinkService")
@Transactional
public class SecretaryPatientLinkServiceImpl extends GenericServiceImpl<SecretaryPatientLink, Integer> implements ISecretaryPatientLinkService{

}
