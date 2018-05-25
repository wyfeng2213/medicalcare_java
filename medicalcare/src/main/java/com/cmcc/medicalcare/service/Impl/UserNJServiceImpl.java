package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.UserNJ;
import com.cmcc.medicalcare.service.IUserNJService;

/**
 * 念加用户Service
 * @author Administrator
 *
 */
@Service(value="userNJService")
@Transactional
public class UserNJServiceImpl extends GenericServiceImpl<UserNJ, Integer>
		implements IUserNJService {

}
