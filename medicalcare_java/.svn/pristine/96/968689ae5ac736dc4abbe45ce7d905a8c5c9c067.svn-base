/**   
* @Title: GroupDoctorsPatientController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:46:29 
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
import com.cmcc.medicalcare.model.GroupDoctorsPatient;
import com.cmcc.medicalcare.service.IGroupDoctorsPatientService;

/**
 * @ClassName: 医生患者群组(会诊)
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:46:29
 * 
 */
@Controller
@RequestMapping("/app/groupDoctorsPatient")
public class GroupDoctorsPatientController {
	
	@Resource
	private IGroupDoctorsPatientService groupDoctorsPatientService;

	/**
	 * 
	* @Title: selectAllOwnGroup 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<List<GroupDoctorsPatient>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "selectAllOwnGroup")
	@ResponseBody
	@SystemControllerLog(description = "查询医生的患者所有群组及患者列表")
	public Results<List<GroupDoctorsPatient>> selectAllOwnGroupAndMembers(HttpServletRequest request) {
		//根据医生手机号码doctors_phone查询group_doctors_patient
		Results<List<GroupDoctorsPatient>> results = new Results<List<GroupDoctorsPatient>>();
		List<GroupDoctorsPatient> listGroupDoctorsPatient = null;
		Map<String, Object>map = new HashMap<String,Object>();
		
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone;
		if (dataJsonObject != null) {
			phone = dataJsonObject.getString("phone");
			if (StringUtils.isNotBlank(phone)) {
				listGroupDoctorsPatient = groupDoctorsPatientService.getList("selectByPhone", phone);
				if (listGroupDoctorsPatient != null) {
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					results.setData(listGroupDoctorsPatient);
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
	* @Title: newGroup 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<GroupDoctorsPatientLink>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "newGroup")
	@ResponseBody
	@SystemControllerLog(description = "创建医生的患者群组")
	public Results<Map<String, Object>> newGroup(HttpServletRequest request) {
		//调用环信接口创建群组，创建成功后再添加记录group_doctors_patient，查询条件name、doctors_phone
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		String name = request.getParameter("name");
		String doctors_phone = request.getParameter("doctors_phone");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("doctors_phone", doctors_phone);
		GroupDoctorsPatient groupDoctorsPatient_ = groupDoctorsPatientService.findByParam("selectByNameAndDoctorsPhone", paramMap);
		
		if (groupDoctorsPatient_ != null) {
			results.setCode(MessageCode.CODE_405);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_405);
			return results;
		} else {
			/*
			 *调用环信接口创建群组
			 * 
			 */
			if (true) {
				GroupDoctorsPatient groupDoctorsPatient = new GroupDoctorsPatient();
				groupDoctorsPatient.setCreatetime(new Date());
				groupDoctorsPatient.setDoctorsId(request.getParameter("name"));
				groupDoctorsPatient.setDoctorsName(request.getParameter("doctors_name"));
				groupDoctorsPatient.setDoctorsPhone(doctors_phone);
				groupDoctorsPatient.setName(name);
				groupDoctorsPatient.setSubject(request.getParameter("subject"));
				groupDoctorsPatientService.insert(groupDoctorsPatient);
				int id = groupDoctorsPatient.getId();
				if (id > 0) {
					results.setCode(200);
					map.put("groupDoctorsPatient", groupDoctorsPatient);
					results.setData(map);
					results.setMessage(MessageCode.MESSAGE_200);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法创建群组");
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("环信创建群组失败");
				return results;
			}
		}
		
	}
	
	 /**
	  * 
	 * @Title: renameGroup 
	 * @Description: TODO 
	 * @param @param request
	 * @param @return    设定文件 
	 * @return Results<GroupDoctorsPatientLink>    返回类型 
	 * @throws
	  */
	@RequestMapping(value = "renameGroup")
	@ResponseBody
	@SystemControllerLog(description = "重命名医生的患者群组")
	public Results<String> renameGroup(HttpServletRequest request) {
		//调用环信接口更新群组，成功后再更新记录group_doctors_patient，查询条件name、doctors_phone
		Results<String> results = new Results<String>();
		
		String name = request.getParameter("name");
		String doctors_phone = request.getParameter("doctors_phone");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("doctors_phone", doctors_phone);
		GroupDoctorsPatient groupDoctorsPatient_ = groupDoctorsPatientService.findByParam("selectByNameAndDoctorsPhone", paramMap);
		
		if (null == groupDoctorsPatient_) {
			results.setCode(MessageCode.CODE_404);
			results.setData("群组不存在");
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		} else {
			/*
			 *调用环信接口更新群组
			 * 
			 */
			if (true) {
				GroupDoctorsPatient groupDoctorsPatient = new GroupDoctorsPatient();
				groupDoctorsPatient.setUpdatetime(new Date());
				groupDoctorsPatient.setName(name);
				groupDoctorsPatientService.update("updateByPrimaryKeySelective", groupDoctorsPatient);
				results.setCode(MessageCode.CODE_200);
				results.setData("更新成功");
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("环信更新群组失败");
				return results;
			}
		}
		
	}
}
