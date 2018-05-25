package com.cmcc.medicalcare.controller.manage.patient;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IPatientUserService;

/**
 * 管理平台患者信息
 * 
 */
@Controller
@RequestMapping("/manage/adminPatientUser")
public class AdminPatientUserController extends BaseController {

	@Resource
	private IPatientUserService patientUserService;

	/**
	 * 获取患者信息
	 * @param request
	 * @param model
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@SystemControllerLog(description = "获取患者信息")
	@RequestMapping(value = "getPatientInfo")
	public String getPatientInfo(HttpServletRequest request, Model model, Long pageSize, Long pageIndex) {
		String patientName = request.getParameter("patientName");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", patientName);
		
		Pager<PatientUser> page = patientUserService.getList("selectALLByAdmin", getPageIndex(pageIndex), getPageSize(pageSize), map);
		model.addAttribute("page", page);
		model.addAttribute("patientName_res", patientName);

		return setTilesView("manage/patient/patientUser", LAYOUT_STANDARD);
	}
	
	/**
	 * 查询患者详细资料
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description = "查询患者详细资料")
	@RequestMapping(value = "getPatientInfoDetail")
	public String getPatientInfoDetail(HttpServletRequest request, Model model) {
		String patientPhone = request.getParameter("patientPhone");
		
		PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
		model.addAttribute("patientInfoDetail", patientUser);
		
		return setTilesView("manage/patient/patientUserDetail", LAYOUT_STANDARD);
	}

}
