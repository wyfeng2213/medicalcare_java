package com.cmcc.medicalcare.controller.manage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.service.IDoctorsUserService;

/**
 * 管理平台医生信息
 * 
 */
@Controller
@RequestMapping("/manage/adminDoctorUser")
public class AdminDoctorUserController extends BaseController {

	@Resource
	private IDoctorsUserService doctorsUserService;

	/**
	 * 
	 * @param request
	 * @param model
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@SystemControllerLog(description = "获取医生信息")
	@RequestMapping(value = "getDoctorInfo")
	public String getDoctorInfo(HttpServletRequest request, Model model, @RequestParam(required = false) Long pageSize,
			@RequestParam(required = false) Long pageIndex) {
		Map<String, Object> map = new HashMap<String, Object>();
		// String h_authaccount = request.getParameter("h_authaccount");
		// String h_phonenumber = request.getParameter("h_phonenumber");
		// String h_before = request.getParameter("h_before");
		// String h_after = request.getParameter("h_after");
		// String h_speed_time1 = request.getParameter("h_speed_time1");
		// String h_speed_time2 = request.getParameter("h_speed_time2");
		// String h_speed_up = request.getParameter("h_speed_up");
		// String h_speed_down = request.getParameter("h_speed_down");
		// String h_fail_reason = request.getParameter("h_fail_reason");
//		 map.put("name", "寂寞");
		// map.put("h_phonenumber", h_phonenumber);
		// map.put("h_before", h_before);
		// map.put("h_after", h_after);
		// map.put("h_speed_time1", h_speed_time1);
		// map.put("h_speed_time2", h_speed_time2);
		// map.put("h_speed_up", h_speed_up);
		// map.put("h_speed_down", h_speed_down);
		// map.put("h_fail_reason", h_fail_reason);
		System.out.println("pageIndex:"+pageIndex);
		System.out.println("pageSize:"+pageSize);
		Pager<DoctorsUser> page = doctorsUserService.getList("selectALLByAdmin", getPageIndex(pageIndex),
				getPageSize(pageSize), map);
		model.addAttribute("page", page);
		// model.addAttribute("h_authaccount", h_authaccount);
		// model.addAttribute("h_phonenumber", h_phonenumber);
		// model.addAttribute("h_before", h_before);
		// model.addAttribute("h_after", h_after);
		// model.addAttribute("h_speed_time1", h_speed_time1);
		// model.addAttribute("h_speed_time2", h_speed_time2);
		// model.addAttribute("h_speed_up", h_speed_up == null ||
		// "".equals(h_speed_up) ? "2" : h_speed_up);
		// model.addAttribute("h_speed_down", h_speed_down == null ||
		// "".equals(h_speed_down) ? "2" : h_speed_down);
		// model.addAttribute("h_fail_reason", h_fail_reason);

		return setTilesView("manage/doctorUser", LAYOUT_STANDARD);
	}

}
