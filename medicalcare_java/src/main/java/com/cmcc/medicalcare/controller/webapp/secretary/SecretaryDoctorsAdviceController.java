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
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.MessageLog;
import com.cmcc.medicalcare.model.SecretaryDoctorsAdvice;
import com.cmcc.medicalcare.service.ISecretaryDoctorsAdviceLinkService;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
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

		String prefix = "doctors_" + doctorsPhone + "_attachment_";

		SecretaryDoctorsAdvice secretaryDoctorsAdvice = new SecretaryDoctorsAdvice();
		secretaryDoctorsAdvice.setAdvice(advice);
		secretaryDoctorsAdvice.setCreatetime(new Date());
		secretaryDoctorsAdvice.setDoctorsLoginId(Constant.scretary + secretaryPhone);
		secretaryDoctorsAdvice.setDoctorsName(doctorsName);
		secretaryDoctorsAdvice.setDoctorsPhone(doctorsPhone);
		secretaryDoctorsAdvice.setPatientLoginId(Constant.patient + patientPhone);
		secretaryDoctorsAdvice.setPatientName(patientName);
		secretaryDoctorsAdvice.setPatientPhone(patientPhone);
		secretaryDoctorsAdvice.setProblem(problem);
		secretaryDoctorsAdvice.setSecretaryLoginId(Constant.scretary + secretaryPhone);
		secretaryDoctorsAdvice.setSecretaryName(secretaryName);
		secretaryDoctorsAdvice.setSecretaryPhone(secretaryPhone);
		secretaryDoctorsAdvice.setDoctorsLoginId(Constant.doctor + doctorsPhone);
		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.CMD).msg("您有新的意见单");
			UserName userName = new UserName();
			userName.add(Constant.doctor + doctorsPhone);
			msg.from(Constant.scretary + secretaryPhone).target(userName).targetType("users").msg(msgContent);
			result = easemobSendMessage.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(Constant.doctor + doctorsPhone);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {

				String fileUrl = UploadFilesUtils.saveFile(request, file, prefix); // 保存附件
				secretaryDoctorsAdvice.setFileUrl(fileUrl);

				secretaryDoctorsAdviceLinkService.insert(secretaryDoctorsAdvice);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
//				map.put("msgContentStr", msgContentStr);
				results.setData(map);
				return results;

			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("医生无法接收消息");
				return results;

			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
			return results;

		}

	}
}