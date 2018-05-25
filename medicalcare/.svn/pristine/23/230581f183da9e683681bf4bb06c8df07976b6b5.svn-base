package com.cmcc.medicalcare.controller.sys.patient;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.HealthCoinsLog;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IHealthCoinsLogService;
import com.cmcc.medicalcare.service.IPatientHealthRecordService;
import com.cmcc.medicalcare.service.IPatientMedicalRecordService;
import com.cmcc.medicalcare.service.IPatientUserService;

/**
 * 健康走管理
 * 
 */
@Controller
@RequestMapping("/sys/healthStep")
public class SystemHealthStepController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemHealthStepController.class);

	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IPatientHealthRecordService patientHealthRecordService;
	
	@Resource
	private IPatientMedicalRecordService patientMedicalRecordService;
	
	@Resource
	private IHealthCoinsLogService healthCoinsLogService;


	/**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/patient/healthStep";
    }
	
    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/findUserPage")
    @ResponseBody
    public Map<String, Object> findUserPage(HttpServletResponse response, HealthCoinsLog healthCoinsLog,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("phone", healthCoinsLog.getUserPhone());
		map.put("name", healthCoinsLog.getUserName());
		try {
			Pager<HealthCoinsLog> systemUserPage = healthCoinsLogService.getList("selectAllBySys", getPageIndex(page),
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
    
    
    @RequestMapping("/editPage")
	public String editPage(int id, Model model) {
		try {
			HealthCoinsLog healthCoinsLog = healthCoinsLogService.findById("selectByPrimaryKey", id);
			model.addAttribute("healthCoinsLog", healthCoinsLog);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/patient/healthStepEdit";
	}
    
    
    @RequestMapping("/edit")
	@ResponseBody
	public Results<Map<String, Object>> edit(HealthCoinsLog healthCoinsLog_, HttpServletRequest request) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			HealthCoinsLog healthCoinsLog = healthCoinsLogService.findById("selectByPrimaryKey", healthCoinsLog_.getId());
			healthCoinsLog.setId(healthCoinsLog_.getId());
			healthCoinsLog.setUserName(healthCoinsLog_.getUserName());
			healthCoinsLog.setUserPhone(healthCoinsLog_.getUserPhone());
			healthCoinsLog.setLcoins(healthCoinsLog_.getLcoins());
			healthCoinsLog.setMcoins(healthCoinsLog_.getMcoins());
			healthCoinsLog.setGcoins(healthCoinsLog_.getGcoins());
			healthCoinsLog.setDetail(healthCoinsLog_.getDetail());
			healthCoinsLog.setType(healthCoinsLog_.getType());
			healthCoinsLog.setUpdatetime(new Date());

		
			healthCoinsLogService.update("updateByPrimaryKeySelective", healthCoinsLog);
			System.out.println("---------------成功------");

			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (RuntimeException e) {
			log.error("修改健康走失败：{}", e);
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}
    
    
    @RequestMapping("/delete")
	@ResponseBody
	@SystemControllerLog(description = "删除机构")
	public Results<Map<String, Object>> delete(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			HealthCoinsLog healthCoinsLog = healthCoinsLogService.findById("selectByPrimaryKey", id);
			healthCoinsLogService.delete("deleteByPrimaryKey", healthCoinsLog);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}
    
}
