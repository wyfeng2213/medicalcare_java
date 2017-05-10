/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;

/**
 * @ClassName: 秘书的医生
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/web/secretaryDoctorsLink")
public class SecretaryDoctorsLinkController {

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	/**
	 * 
	 * @Title: addDoctor @Description: TODO @param @param request @param @return
	 *         设定文件 @return Results<String> 返回类型 @throws
	 */
	@SystemControllerLog(description = "秘书添加医生")
	@RequestMapping(value = "addDoctor")
	@ResponseBody
	Results<String> addDoctor(HttpServletRequest request) {
		// 添加記錄secretary_doctors_link
		Results<String> results = new Results<String>();
		String secretaryLoginId = request.getParameter("secretaryLoginId");
		String secretaryName = request.getParameter("secretaryName");
		String secretaryPhone = request.getParameter("secretaryPhone");
		String doctorsId_ = request.getParameter("doctorsId");

		String doctorsName = request.getParameter("doctorsName");
		String doctorsLoginId = request.getParameter("doctorsLoginId");
		String doctorsPhone = request.getParameter("doctorsPhone");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secretaryPhone", secretaryPhone);
		map.put("doctorsPhone", doctorsPhone);
		SecretaryDoctorsLink secretaryDoctorsLink_ = secretaryDoctorsLinkService
				.findByParam("selectBySecretaryPhoneAndDoctorsPhone", map);
		if (secretaryDoctorsLink_ == null) {
			SecretaryDoctorsLink secretaryDoctorsLink = new SecretaryDoctorsLink();
			secretaryDoctorsLink.setSecretaryLoginId(secretaryLoginId);
			secretaryDoctorsLink.setSecretaryName(secretaryName);
			secretaryDoctorsLink.setSecretaryPhone(secretaryPhone);
			if (StringUtils.isNotBlank(doctorsId_)) {
				secretaryDoctorsLink.setDoctorsId(Integer.parseInt(doctorsId_));
			}
			secretaryDoctorsLink.setDoctorsName(doctorsName);
			secretaryDoctorsLink.setDoctorsLoginId(doctorsLoginId);
			secretaryDoctorsLink.setDoctorsPhone(doctorsPhone);
			secretaryDoctorsLink.setCreatetime(new Date());
			secretaryDoctorsLinkService.insert(secretaryDoctorsLink);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(null);
		} else {
			results.setCode(MessageCode.CODE_405);
			results.setMessage(MessageCode.MESSAGE_405);
			results.setData(null);
		}

		return results;
	}

}
