package com.cmcc.medicalcare.controller.sys;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;

/** 
 * 
 * @author wsm
 *
 */
@Controller
@RequestMapping("/sys/sysSensitiveRecordLog")
public class SystemSensitiveRecordLogController extends BaseController {
	
	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;
	
	/**
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "跳转敏感词管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
    	 return "/sys/sensitiveWord/sensitiveRecordLog";
    }

	/**
	 * 获取sensitiveWordList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取敏感词记录日志list")
	public Map<String, Object> findPage(HttpServletRequest request, String keyword,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			if (StringUtils.isNotBlank(keyword)) {
				// java : 字符解码
				keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
			}
			map.put("keyword", keyword);
			Pager<SensitiveRecordLog> pagers = sensitiveRecordLogService.getList("selectByPhone", getPageIndex(page), getPageSize(rows), map);
			map.put("total", pagers.getTotalCount());
			map.put("rows", pagers.getList());
		}catch(Exception e){
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}
		
		return map;
	}

}
