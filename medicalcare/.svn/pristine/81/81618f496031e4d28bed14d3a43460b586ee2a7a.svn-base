/**   
* @Title: SecretaryInterrogationRecordController.java 
* @Package com.cmcc.medicalcare.controller.webapp.secretary 
* @Description: TODO
* @author adminstrator   
* @date 2017年5月10日 上午11:38:06 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.webapp.secretary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;

/**
 * @ClassName: SecretaryChatroomController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月10日 上午11:38:06
 * 
 */
@Controller
@RequestMapping("/web/SecretaryChatroomController")
public class SecretaryChatroomController {

	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	/**
	 * parameter={"secretaryPhone":""}
	 * 
	 * @Title: getChatRoomListOfSecretary @Description: TODO @param @param
	 *         parameter @param @param file @param @param request @param @return
	 *         设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "获取小秘书聊天室列表")
	@RequestMapping({ "getChatRoomListOfSecretary" })
	@ResponseBody
	public Results<Map<String, Object>> getChatRoomListOfSecretary(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);

		String secretaryPhone = dataJsonObject.getString("secretaryPhone");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secretaryPhone", secretaryPhone);
		List<SecretaryAndDoctorsAndPatientOfChatroom> chatroomList = secretaryAndDoctorsAndPatientOfChatroomService
				.getList("selectBySecretaryPhone", secretaryPhone);

		if (chatroomList != null && chatroomList.size() > 0) {
			resultMap.put("chatroomList", chatroomList);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("成功");
			results.setData(resultMap);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		}

	}
}
