package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cmcc.medicalcare.model.RequestLog;
import com.cmcc.medicalcare.service.IRequestLogService;

/**
 * 
 * @author zhouzhou
 *
 */
@Service(value="requestLogService")
@Transactional
public class RequestLogServiceImpl extends GenericServiceImpl<RequestLog, Integer> implements IRequestLogService{

}
