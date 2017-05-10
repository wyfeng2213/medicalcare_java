package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SecretaryUser;
import com.cmcc.medicalcare.service.ISecretaryUserService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="secretaryUserService")
@Transactional
public class SecretaryUserServiceImpl extends GenericServiceImpl<SecretaryUser, Integer> implements ISecretaryUserService{

}
