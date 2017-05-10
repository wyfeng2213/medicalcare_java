/**   
* @Title: SecretaryDoctorsAdviceLinkServiceImpl.java 
* @Package com.cmcc.medicalcare.service.Impl 
* @Description: TODO
* @author adminstrator   
* @date 2017年5月3日 上午11:45:09 
* @version V1.0   
*/
package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.SecretaryDoctorsAdvice;
import com.cmcc.medicalcare.service.ISecretaryDoctorsAdviceLinkService;

/**
 * @ClassName: SecretaryDoctorsAdviceLinkServiceImpl
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月3日 上午11:45:09
 * 
 */
@Service(value = "secretaryDoctorsAdviceLinkService")
@Transactional
public class SecretaryDoctorsAdviceLinkServiceImpl extends GenericServiceImpl<SecretaryDoctorsAdvice, Integer>
		implements ISecretaryDoctorsAdviceLinkService {

}
