package com.cmcc.medicalcare.controller.sys.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.app.patient.component.HealthRecordDESUtil;
import com.cmcc.medicalcare.controller.app.patient.component.MedicalRecordDESUtil;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.PatientHealthRecord;
import com.cmcc.medicalcare.model.PatientMedicalRecord;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IPatientHealthRecordService;
import com.cmcc.medicalcare.service.IPatientMedicalRecordService;
import com.cmcc.medicalcare.service.IPatientUserService;

/**
 * 患者档案管理
 * 
 */
@Controller
@RequestMapping("/sys/patientRecord")
public class SystemPatientRecordController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemPatientRecordController.class);

	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IPatientHealthRecordService patientHealthRecordService;
	
	@Resource
	private IPatientMedicalRecordService patientMedicalRecordService;


	/**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/patient/patientRecord";
    }
	
    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/findUserPage")
    @ResponseBody
    public Map<String, Object> findUserPage(HttpServletResponse response, PatientUser patientUser,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("phone", patientUser.getPhone());
		map.put("name", patientUser.getName());
		try {
			Pager<PatientUser> systemUserPage = patientUserService.getList("selectAllBySys", getPageIndex(page),
					getPageSize(rows), map);
			map.put("total", systemUserPage.getTotalCount());
			map.put("rows", systemUserPage.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}
		return map;
    }
    
    /**
     * 查看患者档案详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/userPageDetails")
    public String userPageDetails(int id, Model model) {
    	PatientUser patientUser = null;
//    	PatientHealthRecord patientHealthRecord = null;
    	
    	patientUser = patientUserService.findById("selectByPrimaryKey", id);
        model.addAttribute("patientRecord", patientUser);
    	
//        if (patientUser != null) {
//        	patientHealthRecord = patientHealthRecordService.findByParam("selectByPatientLoginId", patientUser.getLoginId());
//        }
//        model.addAttribute("patientHealthRecord", patientHealthRecord);
        
        return "/patient/patientRecordDetails";
    }
    
    /**
     * 查看患者档案详情_健康档案
     *
     * @param patientLoginId
     * @param model
     * @return
     */
    @RequestMapping("/getDetailsHealthRecord")
    public String getDetailsHealthRecord(String patientLoginId, Model model) {
    	PatientUser patientUser = null;
    	PatientHealthRecord patientHealthRecord = null;
    	
    	if (StringUtils.isNotBlank(patientLoginId)) {
    		patientUser = patientUserService.findByParam("selectByPhone", patientLoginId.split("_")[1]);
    		patientHealthRecord = patientHealthRecordService.findByParam("selectByPatientLoginId", patientLoginId);
    		patientHealthRecord = HealthRecordDESUtil.decryptPatientHealthRecord(patientHealthRecord); //出库解密
    	}
    	
        model.addAttribute("patientRecord", patientUser);
        model.addAttribute("patientHealthRecord", patientHealthRecord);
        
        return "/patient/patientRecordDetails_healthRecord";
    }
    
    /**
     * 跳转患者病历详情页面
     * @param patientLoginId
     * @param model
     * @return
     */
    @RequestMapping("/gotoMedicalRecordDetailsPage")
    public String gotoMedicalRecordDetailsPage(String patientLoginId, Model model) {
    	
        model.addAttribute("patientLoginId", patientLoginId);
        
        return "/patient/patientRecordDetails_medicalRecord";
    }
    
    /**
     * 查看患者档案详情_检查报告
     * @param response
     * @param patientLoginId
     * @param patientMedicalRecord
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getDetailsMedicalRecord")
    @ResponseBody
    public Map<String, Object> getDetailsMedicalRecord(HttpServletResponse response, 
    		PatientMedicalRecord patientMedicalRecord, String patientLoginId, 
      		@RequestParam(value = "page", defaultValue = "1") Long page,
    		@RequestParam(value = "rows", defaultValue = "10") Long rows) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	if (StringUtils.isBlank(patientLoginId)) {
    		map.put("total", 0);
			map.put("rows", "");
			return map;
    	}
    	
    	String startTime = patientMedicalRecord.getSearchStartTime();
    	String endTime = patientMedicalRecord.getSearchEndTime();
    	
    	Pager<PatientMedicalRecord> patientMedicalRecordPage = null;
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("patientLoginId", patientLoginId);
    	paramMap.put("diagnoseResult", patientMedicalRecord.getDiagnoseResult());
    	if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
    		try {
    			paramMap.put("startTime", sdf.format(sdf.parse(startTime)));
    			paramMap.put("endTime", sdf.format(sdf.parse(endTime).getTime() + 24 * 60 * 60 * 1000));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	} else if (StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)) {
    		try {
    			paramMap.put("startTime", sdf.format(sdf.parse(startTime)));
    			paramMap.put("endTime", sdf.format(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	} else if (StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime)) {
    		map.put("total", 0);
			map.put("rows", "");
			return map;
    	}
    	
    	try {
			patientMedicalRecordPage = patientMedicalRecordService.getList("getDetailsMedicalRecord", getPageIndex(page),
					getPageSize(rows), paramMap);
			if (null == patientMedicalRecordPage || patientMedicalRecordPage.getList().size() < 1) {
				map.put("total", 0);
				map.put("rows", "");
			} else {
				map.put("total", patientMedicalRecordPage.getTotalCount());
				map.put("rows", MedicalRecordDESUtil.decryptPatientMedicalRecord(patientMedicalRecordPage.getList()));//解密出库
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}
    	
        return map;
    }
    
}
