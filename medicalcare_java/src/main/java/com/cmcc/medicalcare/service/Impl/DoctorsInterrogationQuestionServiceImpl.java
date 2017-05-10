package com.cmcc.medicalcare.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmcc.medicalcare.model.DoctorsInterrogationQuestion;
import com.cmcc.medicalcare.service.IDoctorsInterrogationQuestionService;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="doctorsInterrogationQuestionService")
@Transactional
public class DoctorsInterrogationQuestionServiceImpl extends GenericServiceImpl<DoctorsInterrogationQuestion, Integer> implements IDoctorsInterrogationQuestionService{

}
