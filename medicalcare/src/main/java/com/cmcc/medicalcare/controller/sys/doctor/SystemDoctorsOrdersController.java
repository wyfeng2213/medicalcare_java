package com.cmcc.medicalcare.controller.sys.doctor;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.InquiryMessageLog;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;

/**
 * 医生服务记录
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/doctorsOrders")
public class SystemDoctorsOrdersController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemDoctorsOrdersController.class);

	@Resource
	private IOrdersService ordersService;

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	@SystemControllerLog(description = "跳转到医生服务记录页面")
	public String manager() {
		return "/doctor/doctorsOrders";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/findOrdersPage")
	@ResponseBody
	@SystemControllerLog(description = "查找医生服务记录")
	public Map<String, Object> findOrdersPage(HttpServletResponse response, String hospital, String doctorName,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		try {
			if(StringUtils.isNotBlank(hospital)){
				hospital = java.net.URLDecoder.decode(hospital, "UTF-8");// java : 字符解码
			}
			if(StringUtils.isNotBlank(doctorName)){
				doctorName = java.net.URLDecoder.decode(doctorName, "UTF-8");// java : 字符解码
			}
			
			paramMap.put("hospital", hospital);
			paramMap.put("doctorName", doctorName);
			Pager<Orders> ordersPage = ordersService.getList("selectDoctorsOrders", getPageIndex(page),
					getPageSize(rows), paramMap);
			map.put("total", ordersPage.getTotalCount());
			map.put("rows", ordersPage.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}

		return map;
	}
	
	/**
     * 查看详情
     *
     */
    @RequestMapping("/queryOrdersDetails")
    @SystemControllerLog(description = "跳转到医生服务记录详情页面")
    public String queryOrdersDetails(int id, Model model) {
    	InquiryMessageLog InquiryMessageLog = null;
    	
//    	InquiryMessageLog = patientUserService.findById("getOrdersDetails", id);
//        model.addAttribute("patientRecord", patientUser);
    	
    	model.addAttribute("patientLoginId", id);
        
        return "/doctor/doctorsOrdersQuery";
    }
    
//    public String queryPage(int id, Model model) {
//		try {
//			DoctorsUser doctorUser = doctorsUserService.findById("selectByPrimaryKey", id);
//			model.addAttribute("doctorRecord", doctorUser);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "/doctor/doctorRecordQuery";
//	}
	
}
