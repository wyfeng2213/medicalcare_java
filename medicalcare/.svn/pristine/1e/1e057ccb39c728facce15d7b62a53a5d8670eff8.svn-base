package com.cmcc.medicalcare.controller.sys.patient;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.PatientLoginInfo;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;

/**
 * 患者档案管理
 * 
 */
@Controller
@RequestMapping("/sys/patientManage")
public class SystemPatientManageController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemPatientManageController.class);

	@Resource
	private IPatientLoginInfoService patientLoginInfoService;


	/**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/patient/patientManage";
    }
	
    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/findUserPage")
    @ResponseBody
    public Map<String, Object> findUserPage(HttpServletResponse response, PatientLoginInfo patientLoginInfo,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("phone", patientLoginInfo.getPatientPhone());
		map.put("name", patientLoginInfo.getPatientName());
		try {
			Pager<PatientLoginInfo> patientLoginInfoPage = patientLoginInfoService.getList("selectAllBySys", getPageIndex(page),
					getPageSize(rows), map);
			map.put("total", patientLoginInfoPage.getTotalCount());
			map.put("rows", patientLoginInfoPage.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}
		return map;
    }
    
    /**
     * 用户管理页
     *
     * @return
     */
    @SystemControllerLog(description = "患者用户禁止or启用")
    @RequestMapping(value = "/forbiddenOrEnable", method = RequestMethod.POST)
    @ResponseBody
    public Results<String> forbiddenOrEnable(PatientLoginInfo patientLoginInfo) {
    	Results<String> results = new Results<String>();
		try {
			patientLoginInfoService.update("updateByPrimaryKeySelective", patientLoginInfo);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("修改成功");
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			results.setMessage(e.getMessage());
			results.setCode(MessageCode.CODE_501);
			return results;
		}
    }
    
}
