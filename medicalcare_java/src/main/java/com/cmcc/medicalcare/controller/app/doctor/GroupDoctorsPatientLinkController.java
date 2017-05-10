/**   
* @Title: GroupDoctorsPatientLinkController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:48:44 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.GroupDoctorsPatientLink;
import com.cmcc.medicalcare.service.IGroupDoctorsPatientLinkService;

/** 
* @ClassName: 医生病人分组成员 
* @Description: TODO
* @author adminstrator
* @date 2017年4月10日 上午10:48:44 
*  
*/
@Controller
@RequestMapping("/app/groupDoctorsPatientLink")
public class GroupDoctorsPatientLinkController {
	
	@Resource
	private IGroupDoctorsPatientLinkService groupDoctorsPatientLinkService;

	/**
	 * 
	* @Title: addMemberToGroup 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<String>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "addMemberToGroup")
	@ResponseBody
	@SystemControllerLog(description = "添加患者到医生的群组")
	public Results<String> addMemberToGroup(HttpServletRequest request) {
		//调用环信接口添加组与成员的关系，成功后添加记录group_doctors_patient_link，条件group_id和user_phone
		Results<String> results = new Results<String>();
		
		String group_id = request.getParameter("group_id");
		String user_phone = request.getParameter("user_phone");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("group_id", group_id);
		paramMap.put("user_phone", user_phone);
		GroupDoctorsPatientLink groupDoctorsPatientLink_ = groupDoctorsPatientLinkService.findByParam("selectByGroupIdAndUserPhone", paramMap);
		
		if (groupDoctorsPatientLink_ != null) {
			results.setCode(MessageCode.CODE_405);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_405);
			return results;
		} else {
			/*
			 *调用环信接口添加组与成员的关系
			 * 
			 */
			if (true) {
				GroupDoctorsPatientLink groupDoctorsPatientLink = new GroupDoctorsPatientLink();
				groupDoctorsPatientLink.setCreatetime(new Date());
				groupDoctorsPatientLink.setGroupId(Integer.valueOf(group_id));
				groupDoctorsPatientLink.setGroupName(request.getParameter("group_id"));
				groupDoctorsPatientLink.setType(Integer.valueOf(request.getParameter("type")));
				groupDoctorsPatientLink.setUserId(Integer.valueOf(request.getParameter("user_id")));
				groupDoctorsPatientLink.setUserName(request.getParameter("user_name"));
				groupDoctorsPatientLink.setUserPhone(user_phone);
				groupDoctorsPatientLinkService.insert(groupDoctorsPatientLink);
				int id = groupDoctorsPatientLink.getId();
				if (id > 0) {
					results.setCode(200);
					results.setData(null);
					results.setMessage(MessageCode.MESSAGE_200);
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setMessage("无法添加组与成员的关系");
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("环信添加组与成员的关系失败");
				return results;
			}
		}
	}
	
	/**
	 * 
	* @Title: deleteMemberFromGroup 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<String>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "deleteMemberFromGroup")
	@ResponseBody
	@SystemControllerLog(description = "从医生的群组删除患者")
	public Results<String> deleteMemberFromGroup(HttpServletRequest request) {
		//调用环信接口删除组与成员的关系，成功后删除记录group_doctors_patient_link，条件group_id和user_phone
		Results<String> results = new Results<String>();
		
		String group_id = request.getParameter("group_id");
		String user_phone = request.getParameter("user_phone");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("group_id", group_id);
		paramMap.put("user_phone", user_phone);
		GroupDoctorsPatientLink groupDoctorsPatientLink_ = groupDoctorsPatientLinkService.findByParam("selectByGroupIdAndUserPhone", paramMap);
		
		if (null == groupDoctorsPatientLink_) {
			results.setCode(MessageCode.CODE_404);
			results.setData("该组成员的关系不存在");
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		} else {
			/*
			 *调用环信接口删除组与成员的关系
			 * 
			 */
			if (true) {
				groupDoctorsPatientLinkService.delete("deleteByPrimaryKey", groupDoctorsPatientLink_);
				results.setCode(MessageCode.CODE_200);
				results.setData("删除组与成员的关系成功");
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("环信删除组与成员的关系失败");
				return results;
			}
		}
		
	}
}
