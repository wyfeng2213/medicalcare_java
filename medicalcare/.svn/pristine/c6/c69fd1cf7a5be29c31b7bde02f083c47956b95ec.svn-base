/**   
* @Title: SecretaryInterrogationRecordController.java 
* @Package com.cmcc.medicalcare.controller.webapp.secretary 
* @Description: TODO
* @author adminstrator   
* @date 2017年5月10日 上午11:38:06 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SecretaryInterrogationRecord;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.ISecretaryInterrogationRecordService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;

/**
 * @ClassName: SecretaryInterrogationRecordController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月10日 上午11:38:06
 * 
 */
@Controller
@RequestMapping("/web/secretaryInterrogationRecord")
public class SecretaryInterrogationRecordController {

	@Resource
	private ISecretaryInterrogationRecordService secretaryInterrogationRecordService;
	
	@Resource
	private IMediaLogService mediaLogService;

	/**
	 * parameter={"patientName":"","patientPhone":"","doctorsPhone":"",
	 * "doctorsName":"","record":"","secretaryName":"","secretaryPhone":
	 * ""} @Title: insertSecretaryInterrogationRecord @Description:
	 * TODO @param @param parameter @param @param request @param @return
	 * 设定文件 @return Results<Map <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "添加问诊记录")
	@RequestMapping({ "insertSecretaryInterrogationRecord" })
	@ResponseBody
	public Results<Map<String, Object>> insertSecretaryInterrogationRecord(@RequestParam String parameter,
			MultipartFile file, HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		if(!file.isEmpty()&&!FileFilterUtils.isMediaFileType(file.getOriginalFilename())){
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientName = dataJsonObject.getString("patientName");
		String patientPhone = dataJsonObject.getString("patientPhone");

		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String doctorsName = dataJsonObject.getString("doctorsName");

		String record = dataJsonObject.getString("record");

		String secretaryName = dataJsonObject.getString("secretaryName");
		String secretaryPhone = dataJsonObject.getString("secretaryPhone");

		String uuid = UUID.randomUUID().toString();
		String prefix = "interrogationRecord_" + uuid;

		SecretaryInterrogationRecord secretaryInterrogationRecord = new SecretaryInterrogationRecord();
		secretaryInterrogationRecord.setCreatetime(new Date());
		secretaryInterrogationRecord.setDoctorsLoginId(Constant.doctor + doctorsPhone);
		secretaryInterrogationRecord.setDoctorsName(doctorsName);
		secretaryInterrogationRecord.setDoctorsPhone(doctorsPhone);

		secretaryInterrogationRecord.setPatientLoginId(Constant.patient + patientPhone);
		secretaryInterrogationRecord.setPatientName(patientName);
		secretaryInterrogationRecord.setPatientPhone(patientPhone);
		secretaryInterrogationRecord.setRecord(record);
		secretaryInterrogationRecord.setSecretaryLoginId(Constant.secretary + Constant.secretaryPhone);
		secretaryInterrogationRecord.setSecretaryPhone(secretaryPhone);
		secretaryInterrogationRecord.setSecretaryName(secretaryName);

		if (file != null) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserLoginId(Constant.secretary+secretaryPhone);
			userInfo.setUserName(secretaryName);
			userInfo.setUserPhone(secretaryPhone);
			String fileUrl = UploadFilesUtils.saveFile(mediaLogService, userInfo,request, file, prefix); // 保存附件
			secretaryInterrogationRecord.setFileUrl(fileUrl);
		}
		secretaryInterrogationRecordService.insert(secretaryInterrogationRecord);

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		// map.put("msgContentStr", msgContentStr);
		results.setData(map);
		return results;

	}
}
