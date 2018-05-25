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

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.app.patient.component.MedicalRecordDESUtil;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientMedicalRecord;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.service.IPatientMedicalRecordService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;
import com.cmcc.medicalcare.utils.Toolkit;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;

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
	
	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;
	
	@Resource
	private IPatientLoginInfoService patientLoginInfoService;
	
	@Resource
	private IMediaLogService mediaLogService;
	
	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;
	
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
		
		if (StringUtils.isNotBlank(patinetId)) {
			List<PatientMedicalRecord> patientMedicalRecordList = patientMedicalRecordService.getList("selectByPatinetLoginId", patinetId);
			if (patientMedicalRecordList.size() > 0) {
				//解密出库
				patientMedicalRecordList = MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecordList);
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
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
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
		//************************************************multipart请求特殊过滤处理******************************************
//		if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
//			return new Results<String>(MessageCode.CODE_400, "尚未登录，请先登录", "");
//		}
		//************************************************multipart请求特殊过滤处理******************************************
		if (files!=null&&files.length>0) {
			for (MultipartFile f : files) {
				if(!FileFilterUtils.isMediaFileType(f.getOriginalFilename())){
					return new Results<String>(MessageCode.CODE_404, "不支持该文件格式上传", "");
				}
			}
		}
		
		Results<String> results = new Results<String>();
		JSONObject patientMedicalRecordObject = JSONObject.parseObject(patientMedicalRecord);
		if (patientMedicalRecordObject != null) {
			UserInfo userInfo = new UserInfo();
			String patinetLoginId = patientMedicalRecordObject.getString("patinetLoginId");
			userInfo.setUserLoginId(patinetLoginId);
			userInfo.setUserName(patientMedicalRecordObject.getString("patinetName"));
			userInfo.setUserPhone(patinetLoginId.substring(patinetLoginId.indexOf("_")+1));
			String urls = UploadFilesUtils.saveFiles(mediaLogService,  userInfo,request, files, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_"); //保存文件
			
			//构造记录实体
			PatientMedicalRecord patientMedicalRecord_ = new PatientMedicalRecord();
			//String patinetLoginId = patientMedicalRecordObject.getString("patinetLoginId");
			patientMedicalRecord_.setPatinetLoginId(patinetLoginId);
			patientMedicalRecord_.setPatinetName(patientMedicalRecordObject.getString("patinetName"));
			if (patinetLoginId != null) patientMedicalRecord_.setPatientPhone(patinetLoginId.substring(patinetLoginId.indexOf("_") + 1));
			try {
				patientMedicalRecord_.setDiagnoseDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(patientMedicalRecordObject.getString("diagnoseDate")));
			} catch (ParseException e1) {
				try {
					patientMedicalRecord_.setDiagnoseDate(new SimpleDateFormat("yyyy-MM-dd").parse(patientMedicalRecordObject.getString("diagnoseDate")));
				} catch (ParseException e2) {
					e2.printStackTrace();
					patientMedicalRecord_.setDiagnoseDate(null);
				}
			}
			
			String diagnoseContent = patientMedicalRecordObject.getString("diagnoseContent");
			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(diagnoseContent);
			if (sensitiveWord != null && sensitiveWord.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());
				sensitiveRecordLog.setUserLoginid(patinetLoginId);
				sensitiveRecordLog.setUserPhone(patinetLoginId.substring(patinetLoginId.indexOf("_") + 1));
				sensitiveRecordLog.setType(sensitiveWord.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);

				String equipmentData = request.getParameter("equipmentData");
				JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
				if (dataJsonObject2 != null) {
					String userName = dataJsonObject2.getString("userName");
					sensitiveRecordLog.setUserName(userName);
				}

				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的检查内容包含敏感词，请修改重新输入！");
				return results;
			}
			patientMedicalRecord_.setDiagnoseContent(diagnoseContent);
			
			String diagnoseResult = patientMedicalRecordObject.getString("diagnoseResult");
			SensitiveWord sensitiveWord2 = SensitiveWordUtil.getInstance().isSensitiveWord(diagnoseResult);
			if (sensitiveWord2 != null && sensitiveWord2.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord2.getLevel());
				sensitiveRecordLog.setText(sensitiveWord2.getText());
				sensitiveRecordLog.setUserLoginid(patinetLoginId);
				sensitiveRecordLog.setUserPhone(patinetLoginId.substring(patinetLoginId.indexOf("_") + 1));
				sensitiveRecordLog.setType(sensitiveWord2.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);

				String equipmentData = request.getParameter("equipmentData");
				JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
				if (dataJsonObject2 != null) {
					String userName = dataJsonObject2.getString("userName");
					sensitiveRecordLog.setUserName(userName);
				}

				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的检查结果包含敏感词，请修改重新输入！");
				return results;
			}
			patientMedicalRecord_.setDiagnoseResult(EmojiFilter.filterEmoji(diagnoseResult));
			
			if (StringUtils.isNotBlank(urls)) patientMedicalRecord_.setInspectionReportUrl(urls.trim());
			patientMedicalRecord_.setMechanism(patientMedicalRecordObject.getString("mechanism"));
			String sourceType = patientMedicalRecordObject.getString("sourceType"); //病历来源类型，1检验检查单，2图文咨询，3电话咨询，4视频咨询，5医生面诊，6医生会诊
			if (sourceType != null && Toolkit.isNumeric(sourceType)) patientMedicalRecord_.setSourceType(Integer.valueOf(sourceType));
			patientMedicalRecord_.setDetails(patientMedicalRecordObject.getString("details"));
			String doctorsLoginId = patientMedicalRecordObject.getString("doctorsLoginId");
			patientMedicalRecord_.setDoctorsLoginId(doctorsLoginId);
			patientMedicalRecord_.setDoctorsName(patientMedicalRecordObject.getString("doctorsName"));
			if (doctorsLoginId != null) patientMedicalRecord_.setDoctorsPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
			patientMedicalRecord_.setNote(patientMedicalRecordObject.getString("note"));
			String launchLoginId = patientMedicalRecordObject.getString("launchLoginId");
			patientMedicalRecord_.setLaunchLoginId(launchLoginId);
			patientMedicalRecord_.setLaunchName(patientMedicalRecordObject.getString("launchName"));
			if (launchLoginId != null) {
				patientMedicalRecord_.setLaunchPhone(launchLoginId.substring(launchLoginId.indexOf("_") + 1));
				if (launchLoginId.indexOf(Constant.doctor) != -1) { //发起人为医生
					patientMedicalRecord_.setState(new Integer(1)); //状态：1已通过，2未通过，3待审核
					patientMedicalRecord_.setLaunchType(new Integer(1));//发起人类型，1医生，2患者，3秘书
				} else if (launchLoginId.indexOf(Constant.patient) != -1) { //发起人为患者
					patientMedicalRecord_.setState(new Integer(3));
					patientMedicalRecord_.setLaunchType(new Integer(2));
				} else if (launchLoginId.indexOf(Constant.secretary) != -1) { //发起人为小秘书
					patientMedicalRecord_.setState(new Integer(1));
					patientMedicalRecord_.setLaunchType(new Integer(3));
				}
			}
			
			patientMedicalRecord_.setCreatetime(new Date());
			
			patientMedicalRecord_ = MedicalRecordDESUtil.encryptPatientMedicalRecord(patientMedicalRecord_);//加密入库
			if (patientMedicalRecord_ != null) {
				patientMedicalRecordService.insert(patientMedicalRecord_);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage(MessageCode.MESSAGE_501);
				return results;
			}
			Integer id = patientMedicalRecord_.getId();
			if (id != null) {
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
	

	/**
	 * parameter={"medicalRecordId":"","pictureUrl":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "deleteImage")
	@ResponseBody
	@SystemControllerLog(description = "删除患者病历图片")
	public Results<Map<String, Object>> deleteImage(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		PatientMedicalRecord patientMedicalRecord = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String medicalRecordId = dataJsonObject.getString("medicalRecordId");
		String pictureUrl = dataJsonObject.getString("pictureUrl");

		if (StringUtils.isNotBlank(medicalRecordId) && StringUtils.isNotBlank(pictureUrl)) {
			Integer id = Integer.valueOf(medicalRecordId);
			patientMedicalRecord = patientMedicalRecordService.findByParam("selectByPrimaryKey", id);
			if (patientMedicalRecord != null) {
				String inspection_report_url = patientMedicalRecord.getInspectionReportUrl();
				if (inspection_report_url.indexOf("," + pictureUrl + ",")!=-1) { // 多条记录，处于中间位置
					String str = inspection_report_url.replace("," + pictureUrl, "");
					patientMedicalRecord.setInspectionReportUrl(str);
					patientMedicalRecordService.update("updateByPrimaryKeySelective", patientMedicalRecord);
				} else if (inspection_report_url.indexOf(pictureUrl + ",")!=-1 && inspection_report_url.indexOf("," + pictureUrl)==-1) { // 多条记录，处于首位置
					String str = inspection_report_url.replace(pictureUrl + ",", "");
					patientMedicalRecord.setInspectionReportUrl(str);
					patientMedicalRecordService.update("updateByPrimaryKeySelective", patientMedicalRecord);
				} else if (inspection_report_url.indexOf("," + pictureUrl)!=-1 && inspection_report_url.indexOf(pictureUrl + ",")==-1) { // 多条记录，处于尾位置
					String str = inspection_report_url.replace("," + pictureUrl, "");
					patientMedicalRecord.setInspectionReportUrl(str);
					patientMedicalRecordService.update("updateByPrimaryKeySelective", patientMedicalRecord);
				} else if (inspection_report_url.indexOf(pictureUrl)!=-1 && inspection_report_url.indexOf(",")==-1) { // 只有一条记录
					String str = inspection_report_url.replace(pictureUrl, "");
					patientMedicalRecord.setInspectionReportUrl(str);
					patientMedicalRecordService.update("updateByPrimaryKeySelective", patientMedicalRecord);
				}
				
				List<PatientMedicalRecord> patientMedicalRecordList = patientMedicalRecordService.getList("selectByPatinetLoginId", patientMedicalRecord.getPatinetLoginId());
				
				//解密出库
//				patientMedicalRecordList = MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecordList);
				if (patientMedicalRecordList.size() > 0) {
					//解密出库
					patientMedicalRecordList = MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecordList);
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("patientMedicalRecordList", patientMedicalRecordList);
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_404);
					results.setMessage(MessageCode.MESSAGE_404);
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该病例记录不存在");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
	/**
	 * parameter={"medicalRecordId":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "addImage")
	@ResponseBody
	@SystemControllerLog(description = "添加患者病历图片")
	public Results<Map<String, Object>> addImage(HttpServletRequest request, @RequestParam String parameter, MultipartFile[] files) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		//************************************************multipart请求特殊过滤处理******************************************
//		if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		//************************************************multipart请求特殊过滤处理******************************************
		if (files!=null&&files.length>0) {
			for (MultipartFile f : files) {
				if(!FileFilterUtils.isMediaFileType(f.getOriginalFilename())){
					results.setCode(MessageCode.CODE_404);
					results.setMessage("不支持该文件格式上传");
					return results;
				}
			}
		}
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String medicalRecordId = dataJsonObject.getString("medicalRecordId");

		if (StringUtils.isNotBlank(medicalRecordId)) {
			Integer id = Integer.valueOf(medicalRecordId);
			PatientMedicalRecord patientMedicalRecord = patientMedicalRecordService.findByParam("selectByPrimaryKey", id);
			if (patientMedicalRecord != null) {
				UserInfo userInfo =  new UserInfo();
				userInfo.setUserLoginId(patientMedicalRecord.getPatinetLoginId());
				userInfo.setUserName(patientMedicalRecord.getPatinetName());
				userInfo.setUserPhone(patientMedicalRecord.getPatientPhone());
				String url_new = UploadFilesUtils.saveFiles(mediaLogService, userInfo,request, files, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_"); //保存文件
				String url_old = patientMedicalRecord.getInspectionReportUrl();
				if (StringUtils.isNotBlank(url_old) && StringUtils.isNotBlank(url_new)) { //原来的非空，新的非空
					String urls = url_old + "," + url_new;
					patientMedicalRecord.setInspectionReportUrl(urls);
				} else if (!StringUtils.isNotBlank(url_old) && StringUtils.isNotBlank(url_new)) {//原来的空，新的非空
					patientMedicalRecord.setInspectionReportUrl(url_new);
				}
				patientMedicalRecordService.update("updateByPrimaryKeySelective", patientMedicalRecord);
				
				//解密出库
				patientMedicalRecord = MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecord);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("patientMedicalRecord", patientMedicalRecord);
				results.setData(map);
				return results;
				
				/*List<PatientMedicalRecord> patientMedicalRecordList = patientMedicalRecordService.getList("selectByPatinetLoginId", patientMedicalRecord.getPatinetLoginId());
				if (patientMedicalRecordList.size() > 0) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("patientMedicalRecordList", patientMedicalRecordList);
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_204);
					results.setMessage(MessageCode.MESSAGE_204);
					return results;
				}*/
			} else {
				results.setCode(MessageCode.CODE_204);
				results.setMessage("该病例记录不存在");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
	/**
	 * parameter={"medicalRecordId":""}
	 * 
	 * @Title: searchDoctor @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "deleteMedicalRecord")
	@ResponseBody
	@SystemControllerLog(description = "删除患者病历")
	public Results<Map<String, Object>> deleteMedicalRecord(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		PatientMedicalRecord patientMedicalRecord = null;
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String medicalRecordId = dataJsonObject.getString("medicalRecordId");

		if (StringUtils.isNotBlank(medicalRecordId)) {
			Integer id = Integer.valueOf(medicalRecordId);
			patientMedicalRecord = patientMedicalRecordService.findByParam("selectByPrimaryKey", id);
			if (patientMedicalRecord != null) {
				String patinetLoginId = patientMedicalRecord.getPatinetLoginId();
				patientMedicalRecordService.delete("deleteByPrimaryKey", patientMedicalRecord);
				List<PatientMedicalRecord> patientMedicalRecordList = patientMedicalRecordService.getList("selectByPatinetLoginId", patinetLoginId);
				if (patientMedicalRecordList.size() > 0) {
					//解密出库
					patientMedicalRecordList = MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecordList);
					map.put("patientMedicalRecordList", patientMedicalRecordList);
					results.setData(map);
				}
				results.setCode(MessageCode.CODE_200);
				results.setMessage("删除成功");
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该病例记录不存在");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
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
