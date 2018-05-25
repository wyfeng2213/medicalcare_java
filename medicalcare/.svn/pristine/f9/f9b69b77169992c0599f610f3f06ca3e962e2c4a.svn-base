package com.cmcc.medicalcare.controller.sys.doctor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;

/**
 * 医生档案管理
 * 
 * @author wsm
 *
 */
@Controller
@RequestMapping("/sys/doctorServiceSetting")
public class SystemServiceSettingController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemServiceSettingController.class);

	/**
	 * doctorsUserService
	 */
	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	@SystemControllerLog(description = "跳转到医生档案管理页面")
	public String manager() {
		return "/doctor/doctorServiceSetting";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/findUserPage")
	@ResponseBody
	@SystemControllerLog(description = "查找医生服务设置")
	public Map<String, Object> findUserPage(HttpServletResponse response, DoctorsUser doctorsUser, String hospitalId2,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("phone", doctorsUser.getPhone());
		map.put("name", doctorsUser.getName());
		Pager<DoctorsUser> systemUserPage = new Pager<DoctorsUser>();
		try {

			if (StringUtils.isNotBlank(hospitalId2) && !"null".equalsIgnoreCase(hospitalId2)) {
				map.put("hospitalId", hospitalId2);
				systemUserPage = doctorsUserService.getList("selectAllBySys2", getPageIndex(page), getPageSize(rows),
						map);
			} else {
				systemUserPage = doctorsUserService.getList("selectAllBySys", getPageIndex(page), getPageSize(rows),
						map);
			}

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
	 * 编辑医生服务设置页
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(int id, Model model) {
		try {
			DoctorsUser doctorUser = doctorsUserService.findById("selectByPrimaryKey", id);
			model.addAttribute("doctorRecord", doctorUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/doctor/doctorServiceSettingEdit";
	}

	// /**
	// * 编辑医生档案
	// *
	// * @param mediaCategory
	// * @return
	// */
	@RequestMapping("/edit")
	@ResponseBody
	public Results<Map<String, Object>> edit(DoctorsUser doctorsUser_, HttpServletRequest request) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			DoctorsUser doctorsUser = doctorsUserService.findById("selectByPrimaryKey", doctorsUser_.getId());
			doctorsUser.setId(doctorsUser_.getId());
			doctorsUser.setName(doctorsUser_.getName());
			doctorsUser.setPhone(doctorsUser_.getPhone());

			
			doctorsUser.setIsVideoConsultation(doctorsUser_.getIsVideoConsultation());
			doctorsUser.setGraphicConsulting(doctorsUser_.getGraphicConsulting());
			doctorsUser.setIsQuickConsulting(doctorsUser_.getIsQuickConsulting());
			
			doctorsUserService.update("updateByPrimaryKeySelective", doctorsUser);
			System.out.println("---------------成功------");

			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (RuntimeException e) {
			log.error("修改医生服务设置失败：{}", e);
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}

	@RequestMapping(value = "/downLoadFile")
	@ResponseBody
	public ResponseEntity<byte[]> download(HttpServletRequest request, Model model) throws Exception {
		String filename = "粤健康-医生表更新（模板）-20171129.xlsx";
		// 下载文件路径
		String path = request.getServletContext().getRealPath("/upload/");
		File file = new File(path + File.separator + filename);
		HttpHeaders headers = new HttpHeaders();
		// 下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
		// 通知浏览器以attachment（下载方式）打开图片
		headers.setContentDispositionFormData("attachment", downloadFielName);
		// application/octet-stream ： 二进制流数据（最常见的文件下载）。
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
}
