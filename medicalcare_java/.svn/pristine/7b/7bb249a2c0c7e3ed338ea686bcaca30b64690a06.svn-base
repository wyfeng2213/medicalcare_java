/**   
* @Title: DoctorsTeamController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:43:20 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.service.IDoctorsTeamService;


/**
 * @ClassName: 医生团队
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:43:20
 * 
 */
@Controller
@RequestMapping("/app/doctorsTeam")
public class DoctorsTeamController {
	
	@Resource
	private IDoctorsTeamService doctorsTeamService;

	/**
	 * 
	 * @Title: select @Description: TODO @param @param request @param @return
	 *         设定文件 @return Results<List<DoctorsTeam>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "查询拥有的团队")
	@RequestMapping(value = "selectAllOwnerTeams") 
	@ResponseBody 
	public Results<Map<String,List<DoctorsTeam>>> selectAllOwnerTeams(
			HttpServletRequest request) {
		// 对应doctors_team表,根据医生电话号码查找所有团队
		Results<Map<String,List<DoctorsTeam>>> results = new Results<Map<String,List<DoctorsTeam>>>();
		List<DoctorsTeam> listDoctorsTeam = null;
		Map<String,List<DoctorsTeam>> map = new HashMap<String,List<DoctorsTeam>>();
		
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone;
		if (dataJsonObject != null) {
			phone = dataJsonObject.getString("phone");
			if (StringUtils.isNotBlank(phone)) {
				listDoctorsTeam = doctorsTeamService.getList("selectByPhone", phone);
				if (listDoctorsTeam != null) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("DoctorsTeams", listDoctorsTeam);
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_404);
					results.setMessage(MessageCode.MESSAGE_404);
					results.setData(null);
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_201);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_201);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}

	/**
	 * 
	 * @Title: createTeam @Description: TODO @param @param
	 *         request @param @return 设定文件 @return Results
	 *         <DoctorsTeam> 返回类型 @throws
	 */
	@SystemControllerLog(description = "创建医生团队")
	@RequestMapping(value = "createTeam") 
	@ResponseBody 
	public Results<Map<String, Object>> createTeam(DoctorsTeam doctorsTeam,
			HttpServletRequest request) {
		// 对应doctors_team表,添加团队记录
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		
		if (doctorsTeam != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("phone", doctorsTeam.getDoctorsPhone());
			paramMap.put("name", doctorsTeam.getName());
			DoctorsTeam doctorsUser_ = doctorsTeamService.findByParam("selectByPhoneAndName", paramMap);
			if (doctorsUser_ != null) {
				results.setCode(MessageCode.CODE_405);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_405);
				return results;
			}
			
			doctorsTeam.setCreatetime(new Date());
			doctorsTeamService.insert(doctorsTeam);
			int id = doctorsTeam.getId();
			if (id > 0) {
				results.setCode(200);
				map.put("doctorsTeam", doctorsTeam);
				results.setData(map);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("无法创建医生团队");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}

}
