/**   
* @Title: PatientHealthRecordsController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:52:49 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.app.patient.component.HealthRecordDESUtil;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientHealthRecord;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IPatientHealthRecordService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;


/**
 * @ClassName: 病人健康档案
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:52:49
 * 
 */
@Controller
@RequestMapping("/app/patientHealthRecords")
public class PatientHealthRecordController {
	
	@Resource
	private IPatientHealthRecordService patientHealthRecordService;
	
	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;
	
	/*
	 * (非 Javadoc) <p>Title: select</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#select(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者健康档案")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String patientId) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		
		PatientHealthRecord patientHealthRecord = patientHealthRecordService.findByParam("selectByPatientLoginId", patientId);
		patientHealthRecord = HealthRecordDESUtil.decryptPatientHealthRecord(patientHealthRecord); //出库解密
		if (patientHealthRecord != null) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			resultsMap.put("patientHealthRecords", patientHealthRecord);
			results.setData(resultsMap);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			results.setData(null);
			return results;
		}
		
	}
	
	/**
	 * 保存患者健康档案
	 * @param request
	 * @param patientHealthRecords={"patientName":"患者姓名","patientPhone":"患者电话"
	 * ,"patientLoginId":"患者登录id","height":"身高","weight":"体重","sex":"性别","birthday":"出生日期"
	 * ,"identification":"身份证号","workplace":"工作单位","contactPerson":"联系人姓名","contactPersonPhone":"联系人电话"
	 * ,"censustype":"户籍类型","ethnic":"民族","bloodtype":"血型","culturalLevel":"文化程度","job":"职业"
	 * ,"maritalStatus":"婚姻状态","paymentMethod":"医疗费用支付方式","radiationHistory":"暴露史","pastHistoryIllness":"既往史_疾病"
	 * ,"pastHistorySurgery":"既往史_手术","pastHistoryTrauma":"既往史_外伤","pastHistoryTransfusionBlood":"既往史_输血"
	 * ,"familyHistory":"家族史","geneticDisease":"遗传病史","deformityStatus":"残疾情况","smokingHistory":"抽烟史"
	 * ,"drinkingHistory":"饮酒习惯","sleepState":"睡眠状况","stoolurineIsNormal":"大小便是否正常","drugHistory":"药物历史"
	 * ,"allergicHistory":"过敏史","dietHistory":"饮食历史","livingEnvironment":"生活环境"}
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	@SystemControllerLog(description = "保存患者健康档案")
	public Results<String> insertOrUpdate(HttpServletRequest request, @RequestParam String patientHealthRecords) {
		Results<String> results = new Results<String>();
		
		JSONObject patientHealthRecordsObject = JSONObject.parseObject(patientHealthRecords);
		if (patientHealthRecordsObject != null) {
			String patientLoginId = patientHealthRecordsObject.getString("patientLoginId");
			
			//************************************************敏感词过滤_开始******************************************
			SensitiveWord sensitiveWord1 = SensitiveWordUtil.getInstance().isSensitiveWord(patientHealthRecordsObject.getString("workplace"));
			if (sensitiveWord1 != null && sensitiveWord1.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord1.getLevel());
				sensitiveRecordLog.setText(sensitiveWord1.getText());
				sensitiveRecordLog.setUserLoginid(patientLoginId);
				sensitiveRecordLog.setUserPhone(patientLoginId.substring(patientLoginId.indexOf("_")+1));
				sensitiveRecordLog.setType(sensitiveWord1.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(patientHealthRecordsObject.getString("patientName"));
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的工作单位包含敏感词，保存失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord2 = SensitiveWordUtil.getInstance().isSensitiveWord(patientHealthRecordsObject.getString("contactPerson"));
			if (sensitiveWord2 != null && sensitiveWord2.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord2.getLevel());
				sensitiveRecordLog.setText(sensitiveWord2.getText());
				sensitiveRecordLog.setUserLoginid(patientLoginId);
				sensitiveRecordLog.setUserPhone(patientLoginId.substring(patientLoginId.indexOf("_")+1));
				sensitiveRecordLog.setType(sensitiveWord2.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(patientHealthRecordsObject.getString("patientName"));
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的联系人姓名包含敏感词，保存失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord3 = SensitiveWordUtil.getInstance().isSensitiveWord(patientHealthRecordsObject.getString("contactPersonPhone"));
			if (sensitiveWord3 != null && sensitiveWord3.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord3.getLevel());
				sensitiveRecordLog.setText(sensitiveWord3.getText());
				sensitiveRecordLog.setUserLoginid(patientLoginId);
				sensitiveRecordLog.setUserPhone(patientLoginId.substring(patientLoginId.indexOf("_")+1));
				sensitiveRecordLog.setType(sensitiveWord3.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(patientHealthRecordsObject.getString("patientName"));
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的联系人电话包含敏感词，保存失败！");
				return results;
			}
			//************************************************敏感词过滤_结束******************************************
			
			PatientHealthRecord patientHealthRecords_ = patientHealthRecordService.findByParam("selectByPatientLoginId", patientLoginId);
			if (null == patientHealthRecords_) {
				PatientHealthRecord patientHealthRecords1 = new PatientHealthRecord();
				patientHealthRecords1.setPatientName(patientHealthRecordsObject.getString("patientName"));
				patientHealthRecords1.setPatientPhone(patientHealthRecordsObject.getString("patientPhone"));
				patientHealthRecords1.setPatientLoginId(patientLoginId);
				patientHealthRecords1.setHeight(patientHealthRecordsObject.getString("height"));
				patientHealthRecords1.setWeight(patientHealthRecordsObject.getString("weight"));
				patientHealthRecords1.setSex(patientHealthRecordsObject.getString("sex"));
				patientHealthRecords1.setBirthday(patientHealthRecordsObject.getString("birthday"));
				patientHealthRecords1.setIdentification(patientHealthRecordsObject.getString("identification"));
				patientHealthRecords1.setWorkplace(patientHealthRecordsObject.getString("workplace"));
				patientHealthRecords1.setContactPerson(patientHealthRecordsObject.getString("contactPerson"));
				patientHealthRecords1.setContactPersonPhone(patientHealthRecordsObject.getString("contactPersonPhone"));
				patientHealthRecords1.setCensustype(patientHealthRecordsObject.getString("censustype"));
				patientHealthRecords1.setEthnic(patientHealthRecordsObject.getString("ethnic"));
				patientHealthRecords1.setBloodtype(patientHealthRecordsObject.getString("bloodtype"));
				patientHealthRecords1.setCulturalLevel(patientHealthRecordsObject.getString("culturalLevel"));
				patientHealthRecords1.setJob(patientHealthRecordsObject.getString("job"));
				patientHealthRecords1.setMaritalStatus(patientHealthRecordsObject.getString("maritalStatus"));
				patientHealthRecords1.setPaymentMethod(patientHealthRecordsObject.getString("paymentMethod"));
				patientHealthRecords1.setRadiationHistory(patientHealthRecordsObject.getString("radiationHistory"));
				patientHealthRecords1.setPastHistoryIllness(patientHealthRecordsObject.getString("pastHistoryIllness"));
				patientHealthRecords1.setPastHistorySurgery(patientHealthRecordsObject.getString("pastHistorySurgery"));
				patientHealthRecords1.setPastHistoryTrauma(patientHealthRecordsObject.getString("pastHistoryTrauma"));
				patientHealthRecords1.setPastHistoryTransfusionBlood(patientHealthRecordsObject.getString("pastHistoryTransfusionBlood"));
				patientHealthRecords1.setFamilyHistory(patientHealthRecordsObject.getString("familyHistory"));
				patientHealthRecords1.setGeneticDisease(patientHealthRecordsObject.getString("geneticDisease"));
				patientHealthRecords1.setDeformityStatus(patientHealthRecordsObject.getString("deformityStatus"));
				patientHealthRecords1.setSmokingHistory(patientHealthRecordsObject.getString("smokingHistory"));
				patientHealthRecords1.setDrinkingHistory(patientHealthRecordsObject.getString("drinkingHistory"));
				patientHealthRecords1.setSleepState(patientHealthRecordsObject.getString("sleepState"));
				patientHealthRecords1.setStoolurineIsNormal(patientHealthRecordsObject.getString("stoolurineIsNormal"));
				patientHealthRecords1.setDrugHistory(patientHealthRecordsObject.getString("drugHistory"));
				patientHealthRecords1.setAllergicHistory(patientHealthRecordsObject.getString("allergicHistory"));
				patientHealthRecords1.setDietHistory(patientHealthRecordsObject.getString("dietHistory"));
				patientHealthRecords1.setLivingEnvironment(patientHealthRecordsObject.getString("livingEnvironment"));
				patientHealthRecords1.setCreatetime(new Date());
				
				patientHealthRecords1 = HealthRecordDESUtil.encryptPatientHealthRecord(patientHealthRecords1);//加密入库
				if (patientHealthRecords1 != null) {
					patientHealthRecordService.insert(patientHealthRecords1);
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage(MessageCode.MESSAGE_501);
					return results;
				}
			} else {
				/////////////////////////////////////////////////
				patientHealthRecords_.setHeight(patientHealthRecordsObject.getString("height"));
				patientHealthRecords_.setWeight(patientHealthRecordsObject.getString("weight"));
				patientHealthRecords_.setSex(patientHealthRecordsObject.getString("sex"));
				patientHealthRecords_.setBirthday(patientHealthRecordsObject.getString("birthday"));
				patientHealthRecords_.setIdentification(patientHealthRecordsObject.getString("identification"));
				patientHealthRecords_.setWorkplace(patientHealthRecordsObject.getString("workplace"));
				patientHealthRecords_.setContactPerson(patientHealthRecordsObject.getString("contactPerson"));
				patientHealthRecords_.setContactPersonPhone(patientHealthRecordsObject.getString("contactPersonPhone"));
				patientHealthRecords_.setCensustype(patientHealthRecordsObject.getString("censustype"));
				patientHealthRecords_.setEthnic(patientHealthRecordsObject.getString("ethnic"));
				patientHealthRecords_.setBloodtype(patientHealthRecordsObject.getString("bloodtype"));
				patientHealthRecords_.setCulturalLevel(patientHealthRecordsObject.getString("culturalLevel"));
				patientHealthRecords_.setJob(patientHealthRecordsObject.getString("job"));
				patientHealthRecords_.setMaritalStatus(patientHealthRecordsObject.getString("maritalStatus"));
				patientHealthRecords_.setPaymentMethod(patientHealthRecordsObject.getString("paymentMethod"));
				patientHealthRecords_.setRadiationHistory(patientHealthRecordsObject.getString("radiationHistory"));
				patientHealthRecords_.setPastHistoryIllness(patientHealthRecordsObject.getString("pastHistoryIllness"));
				patientHealthRecords_.setPastHistorySurgery(patientHealthRecordsObject.getString("pastHistorySurgery"));
				patientHealthRecords_.setPastHistoryTrauma(patientHealthRecordsObject.getString("pastHistoryTrauma"));
				patientHealthRecords_.setPastHistoryTransfusionBlood(patientHealthRecordsObject.getString("pastHistoryTransfusionBlood"));
				patientHealthRecords_.setFamilyHistory(patientHealthRecordsObject.getString("familyHistory"));
				patientHealthRecords_.setGeneticDisease(patientHealthRecordsObject.getString("geneticDisease"));
				patientHealthRecords_.setDeformityStatus(patientHealthRecordsObject.getString("deformityStatus"));
				patientHealthRecords_.setSmokingHistory(patientHealthRecordsObject.getString("smokingHistory"));
				patientHealthRecords_.setDrinkingHistory(patientHealthRecordsObject.getString("drinkingHistory"));
				patientHealthRecords_.setSleepState(patientHealthRecordsObject.getString("sleepState"));
				patientHealthRecords_.setStoolurineIsNormal(patientHealthRecordsObject.getString("stoolurineIsNormal"));
				patientHealthRecords_.setDrugHistory(patientHealthRecordsObject.getString("drugHistory"));
				patientHealthRecords_.setAllergicHistory(patientHealthRecordsObject.getString("allergicHistory"));
				patientHealthRecords_.setDietHistory(patientHealthRecordsObject.getString("dietHistory"));
				patientHealthRecords_.setLivingEnvironment(patientHealthRecordsObject.getString("livingEnvironment"));
				patientHealthRecords_.setUpdatetime(new Date());
				
				patientHealthRecords_ = HealthRecordDESUtil.encryptPatientHealthRecord(patientHealthRecords_);//加密入库
				if (patientHealthRecords_ != null) {
					patientHealthRecordService.update("updateByPrimaryKeySelective", patientHealthRecords_);
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage(MessageCode.MESSAGE_501);
					return results;
				}
			}
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}


	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#insert(javax.servlet.
	 * http.HttpServletRequest)
	 */
	public Results<String> insert(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (非 Javadoc) <p>Title: delete</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#delete(javax.servlet.
	 * http.HttpServletRequest)
	 */
	public Results<String> delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (非 Javadoc) <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#update(javax.servlet.
	 * http.HttpServletRequest)
	 */
	public Results<String> update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
