/**   
* @Title: SecretaryDoctorsAdviceController.java 
* @Package com.cmcc.medicalcare.controller.webapp.secretary 
* @Description: TODO
* @author adminstrator   
* @date 2017年5月3日 上午11:38:06 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.SecretaryDoctorsAdvice;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsAdviceLinkService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.MyFileUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: SecretaryDoctorsAdviceController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月3日 上午11:38:06
 * 
 */
@Controller
@RequestMapping("/web/secretaryDoctorsAdvice")
public class SecretaryDoctorsAdviceController {

	@Resource
	private ISecretaryDoctorsAdviceLinkService secretaryDoctorsAdviceLinkService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;
	
	@Resource
	private IMediaLogService mediaLogService;

	/**
	 * parameter={"patientName":"","patientPhone":"","doctorsPhone":"",
	 * "doctorsName":"","problem":"","advice":"",
	 * "secretaryName":"","secretaryPhone":""} @Title:
	 * insertDoctorAdvice @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "插入小秘书转医生处理意见")
	@RequestMapping({ "insertDoctorAdvice" })
	@ResponseBody
	public Results<Map<String, Object>> insertDoctorAdvice(@RequestParam String parameter, MultipartFile file,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// ************************************************multipart请求特殊过滤处理******************************************
//		if (!SecurityUtils.multipartCheck(request)) { // multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		// ************************************************multipart请求特殊过滤处理******************************************
		if (!file.isEmpty() && !FileFilterUtils.isMediaFileType(file.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("不支持该文件格式上传");
			return results;
		}		
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientName = dataJsonObject.getString("patientName");
		String patientPhone = dataJsonObject.getString("patientPhone");

		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String doctorsName = dataJsonObject.getString("doctorsName");

		String problem = dataJsonObject.getString("problem");
		String advice = dataJsonObject.getString("advice");

		String secretaryName = dataJsonObject.getString("secretaryName");
		String secretaryPhone = dataJsonObject.getString("secretaryPhone");
				
		String prefix = "doctorAdvice_"+doctorsPhone+"_";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctors_phone", doctorsPhone);
		paramMap.put("patient_phone", patientPhone);
		paramMap.put("type", 1);

		List<DoctorsPatientLink> doctorsPatientLinkList = doctorsPatientLinkService
				.getList("selectByDoctorsPhoneAndPatientPhone", paramMap);

		Object result = null;
		String chatroomId = null;

		if (doctorsPatientLinkList != null && doctorsPatientLinkList.size() > 0) {

			chatroomId = doctorsPatientLinkList.get(0).getChatroomId();

			
			SecretaryDoctorsAdvice secretaryDoctorsAdvice = new SecretaryDoctorsAdvice();
			secretaryDoctorsAdvice.setAdvice(advice);
			secretaryDoctorsAdvice.setCreatetime(new Date());
			secretaryDoctorsAdvice.setDoctorsLoginId(Constant.secretary + secretaryPhone);
			secretaryDoctorsAdvice.setDoctorsName(doctorsName);
			secretaryDoctorsAdvice.setDoctorsPhone(doctorsPhone);
			secretaryDoctorsAdvice.setPatientLoginId(Constant.patient + patientPhone);
			secretaryDoctorsAdvice.setPatientName(patientName);
			secretaryDoctorsAdvice.setPatientPhone(patientPhone);
			secretaryDoctorsAdvice.setProblem(problem);
			secretaryDoctorsAdvice.setSecretaryLoginId(Constant.secretary + secretaryPhone);
			secretaryDoctorsAdvice.setSecretaryName(secretaryName);
			secretaryDoctorsAdvice.setSecretaryPhone(secretaryPhone);
			secretaryDoctorsAdvice.setDoctorsLoginId(Constant.doctor + doctorsPhone);
			secretaryDoctorsAdvice.setType(0);
			secretaryDoctorsAdvice.setChatroomId(chatroomId);

			if (file != null && file.getSize() > 0) {
				if (!MyFileUtils.isFileSuffix(file)) {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("无法上传该格式的文件");
					return results;
				}
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLoginId(Constant.secretary + secretaryPhone);
				userInfo.setUserName(secretaryName);
				userInfo.setUserPhone(secretaryPhone);
				String fileUrl = UploadFilesUtils.saveFile(mediaLogService, userInfo,request, file, prefix); // 保存附件
				System.out.println(fileUrl);
				secretaryDoctorsAdvice.setFileUrl(fileUrl);
			}
			secretaryDoctorsAdviceLinkService.insert(secretaryDoctorsAdvice);

			try {
				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("adviceId", secretaryDoctorsAdvice.getId());
				ext.put("messageType", "doctorAdvice");
				ext.put("nickName", "医生助理-"+secretaryName);
				ext.put("headUrl", Config.secretaryIMG);
				String doctorLoginId = doctorsPatientLinkList.get(0).getChatroomId();
				EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();
//				String msgStr = doctorsName + "医生，您好。经过对患者" + patientName
//						+ "病情的初步诊断，发现了一些问题需要您亲自问诊解答，详情请查看： 转移医生处理意见单    问诊结束后，点击右上角\"填写问诊记录\"，填写完成后即结束本次问诊。"
//						+ "若1天内未填写问诊记录，将自动结束本次问诊";
				msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
				UserName userName = new UserName();
				userName.add(chatroomId);
				msg.from(Constant.secretary + secretaryPhone).target(userName).targetType("chatgroups").msg(msgContent)
						.ext(ext);
				result = easemobSendMessage.sendMessage(msg);
				
//				System.out.println(result.toString());
				
				MsgContent msgContent2 = new MsgContent();
				String msgStr2 = doctorsName + "医生，您好。\n经过对患者" + patientName
						+ "病情的初步诊断，发现了一些问题需要您亲自问诊解答，详情请查看：\n\n转医生处理意见单\n\n问诊结束后，点击右上角\"填写问诊记录\"，填写完成后即结束本次问诊。"
						+ "若1天内未填写问诊记录，将自动结束本次问诊";
				msgContent2.type(MsgContent.TypeEnum.TXT).msg(msgStr2);
				UserName userName2 = new UserName();
				userName2.add(chatroomId);
				msg.from(Constant.secretary + secretaryPhone).target(userName2).targetType("chatgroups").msg(msgContent2)
				.ext(ext);
		        Object result2 = easemobSendMessage.sendMessage(msg);
				
//				System.out.println(result2.toString());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功");
			// map.put("msgContentStr", msgContentStr);
			results.setData(map);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不存在该患者好友");
			return results;
		}

	}

}
