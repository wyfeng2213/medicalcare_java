/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientMedicalRecord;
import com.cmcc.medicalcare.service.IPatientMedicalRecordService;
import com.cmcc.medicalcare.utils.UploadFilesUtils;

/**
 * @ClassName: 患者病历
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/app/patientMedicalRecord")
public class PatientMedicalRecordController {
	
	@Resource
	private IPatientMedicalRecordService patientMedicalRecordService;
	
	/**
	 * 查询患者病历记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询患者病历记录")
	public Results<Map<String, Object>> select(HttpServletRequest request, @RequestParam String patinetId) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultsMap = new HashMap<String, Object>();
		
		Integer p_Id = Integer.parseInt(patinetId.trim());
		List<PatientMedicalRecord> patientMedicalRecordList = patientMedicalRecordService.getList("selectByPatinetId", p_Id);
		if (patientMedicalRecordList.size() > 0) {
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			resultsMap.put("patientMedicalRecordList", patientMedicalRecordList);
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
	 * 新增患者病历记录
	 * @param request
	 * @param patientHealthRecords
	 * @return
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "新增患者病历记录")
	public Results<String> insert(HttpServletRequest request, @RequestParam String patientMedicalRecord, MultipartFile[] files) {
		
		Results<String> results = new Results<String>();
		JSONObject patientMedicalRecordObject = JSONObject.parseObject(patientMedicalRecord);
		if (patientMedicalRecordObject != null) {
			String secretaryName = patientMedicalRecordObject.getString("secretaryName");//获取小秘书姓名
			String urls = UploadFilesUtils.saveFiles(request,files); //保存文件
			
			//构造记录实体
			PatientMedicalRecord patientMedicalRecord_ = new PatientMedicalRecord();
			patientMedicalRecord_.setPatinetId(patientMedicalRecordObject.getInteger("patinetId"));
			patientMedicalRecord_.setPatinetName(patientMedicalRecordObject.getString("patinetName"));
			try {
				patientMedicalRecord_.setDiagnoseDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(patientMedicalRecordObject.getString("diagnoseDate")));
			} catch (ParseException e) {
				e.printStackTrace();
				patientMedicalRecord_.setDiagnoseDate(null);
			}
			patientMedicalRecord_.setDiagnoseContent(patientMedicalRecordObject.getString("diagnoseContent"));
			patientMedicalRecord_.setDiagnoseResult(patientMedicalRecordObject.getString("diagnoseResult"));
			if (StringUtils.isNotBlank(urls)) patientMedicalRecord_.setInspectionReportUrl(urls.trim());
			patientMedicalRecord_.setMechanism(patientMedicalRecordObject.getString("mechanism"));
			patientMedicalRecord_.setNote(patientMedicalRecordObject.getString("note"));
			if (StringUtils.isNotBlank(secretaryName)) {//小秘书姓名不为空，则表示该记录为小秘书上传
				patientMedicalRecord_.setState(new Integer(1)); //患者添加的为3未审核，护士添加为1已通过
				patientMedicalRecord_.setSecretaryId(patientMedicalRecordObject.getInteger("secretaryId"));
				patientMedicalRecord_.setSecretaryName(secretaryName);
				patientMedicalRecord_.setLaunchType(new Integer(2));//发起人：1患者，2秘书
			} else {//该记录为患者上传
				patientMedicalRecord_.setState(new Integer(3)); //患者添加的为3未审核，护士添加为1已通过
				patientMedicalRecord_.setLaunchType(new Integer(1));//发起人：1患者，2秘书
			}
			patientMedicalRecord_.setCreatetime(new Date());
			
			//记录入库
			patientMedicalRecordService.insert(patientMedicalRecord_);
			int id = patientMedicalRecord_.getId();
			if (id > 0) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("无法新增患者病历记录");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
	/**
	 * 新增患者病历记录_测试
	 * @param request
	 * @param patientHealthRecords
	 * @return
	 */
	/*@RequestMapping(value = "insert2")
	@ResponseBody
	public Results<String> insert2(HttpServletRequest request, MultipartFile[] files) {
		Results<String> results = new Results<String>();
		if (files != null && files.length > 0) {
			String urls = saveFiles(request,files); //保存文件
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(urls);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}*/
	

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
	public @RequestMapping(value = "delete") @ResponseBody Results<String> delete(HttpServletRequest request) {
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
	public @RequestMapping(value = "update") @ResponseBody Results<String> update(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
