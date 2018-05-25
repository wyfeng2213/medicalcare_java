package com.cmcc.medicalcare.controller.sys;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.InquiryMessageLog;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IInquiryMessageLogService;
import com.cmcc.medicalcare.utils.WriteExcel;

/** 
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/systemUserCount")
public class SystemUserCountController {
	
	@Resource
	private IInquiryMessageLogService inquiryMessageLogService;
	
	@Resource
	private IDoctorsTeamService doctorsTeamService;
	
	/**
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "跳转管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager(Model model) {
		List<DoctorsTeam> doctorsTeamList = doctorsTeamService.getList("selectAll", null);
		model.addAttribute("doctorsTeamList", doctorsTeamList);
		return "/sys/userCount/userCount";
    }	
	
	/**
	 * 统计活跃用户数及活跃总次数
	 * 
	 * @param request
	 * @param model
	 * @param hospitalId 所属机构id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "getUserCount", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "统计活跃用户数及活跃总次数")
	public Map<String, Object> getUserCount(HttpServletRequest request, Model model, Integer hospitalId, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<InquiryMessageLog> resultList = new ArrayList<InquiryMessageLog>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(StringUtils.isBlank(startTime)) {
			map.put("rows", "");
			return map;
		}
		if(null == hospitalId ) {
			hospitalId = -1;
		}
		if(StringUtils.isBlank(endTime)){
			endTime = sdf.format(new Date());
		}
		
		try{
			if (-1 == hospitalId) {
				List<InquiryMessageLog> list = null;
				List<DoctorsTeam> doctorsTeamList = doctorsTeamService.getList("selectAll", null);
				for (int i=0; i<doctorsTeamList.size(); i++) {
					Integer teamId = doctorsTeamList.get(i).getId();
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("hospitalId", teamId);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					list = inquiryMessageLogService.getList("getUserCount", paramMap);
					if (list.size()>0) resultList.addAll(list);
				}
			} else {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("hospitalId", hospitalId);
				paramMap.put("startTime", startTime);
				paramMap.put("endTime", endTime);
				resultList = inquiryMessageLogService.getList("getUserCount", paramMap);
			}
			
			map.put("rows", resultList);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("rows", "");
		}
		
		model.addAttribute("hospitalId", hospitalId);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		
		return map;
	}
	
	/**
	 * 统计活跃用户数及活跃总次数_导出Excel信息
	 * 
	 * @param request
	 * @param response
	 * @param hospital
	 * @param startTime
	 * @param endTime
	 * @throws IOException
	 */
	@SystemControllerLog(description = "统计活跃用户数及活跃总次数_导出Excel信息")
	@RequestMapping(value="exportUserCount")
	public void exportUserCount(HttpServletRequest request, HttpServletResponse response, Integer hospitalId, String startTime, String endTime) throws IOException {
		List<InquiryMessageLog> resultList = new ArrayList<InquiryMessageLog>();
		XSSFWorkbook workbook = null;
		
		if(null != hospitalId && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = "统计活跃用户数及活跃总次数_"+simpleDateFormat.format(new Date())+".xlsx";
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1" ) );//指定下载的文件名
			response.setContentType("application/vnd.ms-excel"); 
			
			if (-1 == hospitalId) {
				List<InquiryMessageLog> list = null;
				List<DoctorsTeam> doctorsTeamList = doctorsTeamService.getList("selectAll", null);
				for (int i=0; i<doctorsTeamList.size(); i++) {
					Integer teamId = doctorsTeamList.get(i).getId();
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("hospitalId", teamId);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					list = inquiryMessageLogService.getList("getUserCount", paramMap);
					if (list.size()>0) resultList.addAll(list);
				}
			} else {
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("hospitalId", hospitalId);
				paramMap.put("startTime", startTime);
				paramMap.put("endTime", endTime);
				resultList = inquiryMessageLogService.getList("getUserCount", paramMap);
			}
			
			//导出Excel
			if (resultList != null) workbook = WriteExcel.exportUserCountToExcel(resultList); 
			if (workbook != null) {
		        workbook.write(response.getOutputStream());
			}
		}
		
	}
	
	/**
	 * 跳转统计医疗机构每个用户使用次数页面
	 * 
	 * @param request
	 * @param model
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SystemControllerLog(description = "跳转统计医疗机构每个用户使用次数页面")
	@RequestMapping(value = "gotoUserCountDetails", method = RequestMethod.GET)
	public String gotoUserCountDetails(HttpServletRequest request, Model model, Integer hospitalId, String startTime, String endTime) {
		DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPrimaryKey", hospitalId);
		
		model.addAttribute("hospitalId", hospitalId);
		model.addAttribute("hospital", doctorsTeam.getName());
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "/sys/userCount/userCountDetails";
	}
	
	/**
	 * 统计医疗机构每个用户使用次数
	 * 
	 * @param request
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "getUserCountDetails")
	@ResponseBody
	@SystemControllerLog(description = "统计医疗机构每个用户使用次数")
	public Map<String, Object> getUserCountDetails(HttpServletRequest request, Integer hospitalId, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<InquiryMessageLog> resultList = new ArrayList<InquiryMessageLog>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(null == hospitalId || StringUtils.isBlank(startTime)) {
			map.put("rows", "");
			return map;
		}
		if(StringUtils.isBlank(endTime)) {
			endTime = sdf.format(new Date());
		}
		
		try{
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("hospitalId", hospitalId);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			resultList = inquiryMessageLogService.getList("getUserCountDetails", paramMap);
			
			map.put("rows", resultList);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("rows", "");
		}
		
		return map;
	}
	
	/**
	 * 统计医疗机构每个用户使用次数_导出Excel信息
	 * 
	 * @param request
	 * @param response
	 * @param hospital
	 * @param startTime
	 * @param endTime
	 * @throws IOException
	 */
	@SystemControllerLog(description = "统计医疗机构每个用户使用次数_导出Excel信息")
	@RequestMapping(value="exportUserCountDetails")
	public void exportUserCountDetails(HttpServletRequest request, HttpServletResponse response, Integer hospitalId, String startTime, String endTime) throws IOException {
		List<InquiryMessageLog> resultList = new ArrayList<InquiryMessageLog>();
		XSSFWorkbook workbook = null;
		
		if(null != hospitalId && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = "统计医疗机构每个用户使用次数_"+simpleDateFormat.format(new Date())+".xlsx";
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1" ) );//指定下载的文件名
			response.setContentType("application/vnd.ms-excel"); 
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("hospitalId", hospitalId);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			resultList = inquiryMessageLogService.getList("getUserCountDetails", paramMap);
			
			//导出Excel
			if (resultList != null) workbook = WriteExcel.exportUserCountDetailsToExcel(resultList); 
			if (workbook != null) {
		        workbook.write(response.getOutputStream());
			}
		}
		
	}
	
	/**
	 * 跳转查询医疗机构每个用户每次使用详情页面
	 * 
	 * @param request
	 * @param model
	 * @param hospitalId
	 * @param patientPhone
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SystemControllerLog(description = "跳转查询医疗机构每个用户每次使用详情页面")
	@RequestMapping(value = "gotoUseDetails", method = RequestMethod.GET)
	public String gotoUseDetails(HttpServletRequest request, Model model, Integer hospitalId, String patientPhone, String startTime, String endTime) {
		DoctorsTeam doctorsTeam = doctorsTeamService.findByParam("selectByPrimaryKey", hospitalId);
		
		model.addAttribute("hospitalId", hospitalId);
		model.addAttribute("hospital", doctorsTeam.getName());
		model.addAttribute("patientPhone", patientPhone);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "/sys/userCount/useDetails";
	}
	
	/**
	 * 查询医疗机构每个用户每次使用详情
	 * 
	 * @param request
	 * @param hospitalId
	 * @param patientPhone
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "getUseDetails")
	@ResponseBody
	@SystemControllerLog(description = "查询医疗机构每个用户每次使用详情")
	public Map<String, Object> getUseDetails(HttpServletRequest request, Integer hospitalId, String patientPhone, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<InquiryMessageLog> resultList = new ArrayList<InquiryMessageLog>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null == hospitalId || StringUtils.isBlank(patientPhone) || StringUtils.isBlank(startTime)) {
			map.put("rows", "");
			return map;
		}
		if(StringUtils.isBlank(endTime)){
			endTime = sdf.format(new Date());
		}
		
		try{
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("hospitalId", hospitalId);
			paramMap.put("patientPhone", patientPhone);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			resultList = inquiryMessageLogService.getList("getUseDetails", paramMap);
			
			map.put("rows", resultList);
		} catch(Exception e) {
			e.printStackTrace();
			map.put("rows", "");
		}
		
		return map;
	}
	
	/**
	 * 医疗机构每个用户每次使用详情_导出Excel信息
	 * 
	 * @param request
	 * @param response
	 * @param hospital
	 * @param startTime
	 * @param endTime
	 * @throws IOException
	 */
	@SystemControllerLog(description = "医疗机构每个用户每次使用详情_导出Excel信息")
	@RequestMapping(value="exportUseDetails")
	public void exportUseDetails(HttpServletRequest request, HttpServletResponse response, Integer hospitalId, String patientPhone, String startTime, String endTime) throws IOException {
		List<InquiryMessageLog> resultList = new ArrayList<InquiryMessageLog>();
		XSSFWorkbook workbook = null;
		
		if(null != hospitalId && StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = "医疗机构每个用户每次使用详情_"+simpleDateFormat.format(new Date())+".xlsx";
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"), "ISO8859-1" ) );//指定下载的文件名
			response.setContentType("application/vnd.ms-excel"); 
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("hospitalId", hospitalId);
			paramMap.put("patientPhone", patientPhone);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			resultList = inquiryMessageLogService.getList("getUseDetails", paramMap);
			
			//导出Excel
			if (resultList != null) workbook = WriteExcel.exportUseDetailsToExcel(resultList); 
			if (workbook != null) {
		        workbook.write(response.getOutputStream());
			}
		}
		
	}
	
}
