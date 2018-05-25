package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.HealthDeviceInfo;
import com.cmcc.medicalcare.service.IHealthDeviceInfoService;

/**
 * @author Administrator
 *
 */
@Service(value="healthDeviceInfoService")
@Transactional
public class HealthDeviceInfoServiceImpl extends GenericServiceImpl<HealthDeviceInfo, Integer> implements IHealthDeviceInfoService {

}
