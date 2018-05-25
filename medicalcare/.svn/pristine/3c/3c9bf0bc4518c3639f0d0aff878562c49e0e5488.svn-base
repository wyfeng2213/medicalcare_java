package com.cmcc.medicalcare.controller.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.RequestLog;
import com.cmcc.medicalcare.service.IRequestLogService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/** 
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/systemRequestLog")
public class SystemRequestLogController {
	
	@Resource
	private IRequestLogService requestLogService;
	
	/**
	 * 跳转管理页面
	 * @return
	 */
	@SystemControllerLog(description = "跳转管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
    	 return "/sys/admin/syslog";
    }

	/**
	 * 获取请求日志list
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findSysLogPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取请求日志list")
	public Map<String, Object> findSysLogPage(HttpServletRequest request, RequestLog requestLog,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String s_table = requestLog.getStorage_table();
		if (s_table.contains("-")) {
			String curmonth = s_table.replace("-", "");
			String storage_table = "request_log_"+curmonth;
			requestLog.setStorage_table(storage_table);
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			String curmonth = df.format(new Date());
			String storage_table = "request_log_"+curmonth;
			requestLog.setStorage_table(storage_table);
		}
		
		try{
			Pager<RequestLog> pagers = requestLogService.getList("findSysLogPage", page, rows, requestLog);
			map.put("total", pagers.getTotalCount());
			map.put("rows", pagers.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		} 
		
		return map;
	}
	
	
}
