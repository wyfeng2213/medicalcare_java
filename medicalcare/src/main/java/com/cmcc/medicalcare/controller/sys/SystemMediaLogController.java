package com.cmcc.medicalcare.controller.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.MediaLog;
import com.cmcc.medicalcare.model.ScienceArticle;
import com.cmcc.medicalcare.model.ScienceColumn;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IScienceArticleService;
import com.cmcc.medicalcare.service.IScienceColumnService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;

/**
 * 
 * 
 *
 */
@Controller
@RequestMapping("/sys/mediaLog")
public class SystemMediaLogController extends BaseController {

	@Resource
	private IScienceColumnService scienceColumnService;

	@Resource
	private IScienceArticleService scienceArticleService;
	
	@Resource
	private IMediaLogService mediaLogService;

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@SystemControllerLog(description = "管理页面")
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager(Model model) {
		return "/sys/log/mediaLog";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/findUserPage")
	@ResponseBody
	@SystemControllerLog(description = "查找媒体信息")
	public Map<String, Object> findUserPage(HttpServletResponse response, String phone,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();

		try {
			if(StringUtils.isNotBlank(phone)){
				phone = java.net.URLDecoder.decode(phone, "UTF-8");// java : 字符解码
			}
				map2.put("phone", phone);
				Pager<MediaLog> mediaLogPage = mediaLogService.getList("getAll",
						getPageIndex(page), getPageSize(rows), map2);
				map.put("total", mediaLogPage.getTotalCount());
				map.put("rows", mediaLogPage.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}

		return map;
	}

	/**
	 * 
	 * @Title: queryPage @Description: TODO @param @param model @param @param
	 *         id @param @return 设定文件 @return String 返回类型 @throws
	 */
	@SystemControllerLog(description = "详情页面")
	@RequestMapping(value = "/queryPage", method = RequestMethod.GET)
	public String queryPage(Model model, int id) {
		MediaLog mediaLog = mediaLogService.findById("selectByPrimaryKey", id);
		model.addAttribute("mediaLog", mediaLog);
		return "/sys/log/mediaLogQuery";
	}

	
}
