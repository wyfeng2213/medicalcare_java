package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SecretaryLoginInfo;
import com.cmcc.medicalcare.service.ISecretaryLoginInfoService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="secretaryLoginInfoService")
@Transactional
public class SecretaryLoginInfoServiceImpl extends GenericServiceImpl<SecretaryLoginInfo, Integer> implements ISecretaryLoginInfoService{

}
