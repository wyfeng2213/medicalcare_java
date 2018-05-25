package com.cmcc.medicalcare.controller.sys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.ContractedHospitalsDoctorsLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PackageVersionDoctor;
import com.cmcc.medicalcare.service.IContractedHospitalsDoctorsLinkService;
import com.cmcc.medicalcare.service.IContractedHospitalsService;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import net.sf.json.JSONArray;

/**
 * 医生档案管理
 * 
 * @author wsm
 *
 */
@Controller
@RequestMapping("/sys/hospital")
public class SystemHospitalController extends BaseController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemHospitalController.class);

	/**
	 * doctorsUserService
	 */
	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;

	@Resource
	private IContractedHospitalsService contractedHospitalsService;

	@Resource
	private IContractedHospitalsDoctorsLinkService contractedHospitalsDoctorsLinkService;

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	@SystemControllerLog(description = "跳转到机构管理页面")
	public String manager() {
		return "/sys/hospital/hospital";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/findUserPage")
	@ResponseBody
	@SystemControllerLog(description = "查找机构管理信息")
	public Map<String, Object> findUserPage(HttpServletResponse response, String keyword,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (StringUtils.isNotBlank(keyword)) {
				// java : 字符解码
				keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
			}
			// System.out.println("1111111111111:: " + keyword);
			map.put("keyword", keyword);
			Pager<DoctorsTeam> doctorsTeamPage = doctorsTeamService.getList("selectKeyWordBySys", getPageIndex(page),
					getPageSize(rows), map);
			map.put("total", doctorsTeamPage.getTotalCount());
			map.put("rows", doctorsTeamPage.getList());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}

		return map;
	}

	/**
	 * 详情医生注册审批页
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPage")
	@SystemControllerLog(description = "跳转到机构管理详情页面")
	public String queryPage(int id, Model model) {
		try {
			DoctorsTeam doctorsTeam = doctorsTeamService.findById("selectByPrimaryKey", id);
			model.addAttribute("doctorsTeam", doctorsTeam);
			System.out.println(doctorsTeam.getHospitalIMG());
			if (StringUtils.isNotBlank(doctorsTeam.getHospitalIMG())) {
				model.addAttribute("isHospitalIMG", 1);
			} else {
				model.addAttribute("isHospitalIMG", 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/hospital/hospitalQuery";
	}

	@RequestMapping("/addPage")
	@SystemControllerLog(description = "跳转到机构管理添加页面")
	public String addPage() {
		return "/sys/hospital/hospitalAdd";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "新增机构")
	public Results<Map<String, Object>> add(HttpServletRequest request, DoctorsTeam doctorsTeam_,
			MultipartFile file) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		if (!file.isEmpty() && !FileFilterUtils.isMediaFileType(file.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		String prefix = "hospitalIMG_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
		String fileUrl = UploadFilesUtils.saveFile(null,null,request, file, prefix); // 保存图片

		DoctorsTeam doctorsTeam = new DoctorsTeam();
		doctorsTeam.setName(doctorsTeam_.getName());
		doctorsTeam.setArea(doctorsTeam_.getArea());
		doctorsTeam.setCityName(doctorsTeam_.getCityName());
		doctorsTeam.setCreatetime(new Date());
		doctorsTeam.setHospitalAddr(doctorsTeam_.getHospitalAddr());
		doctorsTeam.setHospitalIMG(fileUrl);
		doctorsTeam.setHospitalPhone(doctorsTeam_.getHospitalPhone());
		doctorsTeam.setIntroduction(doctorsTeam_.getIntroduction());
		doctorsTeam.setProvince(doctorsTeam_.getProvince());
		
		doctorsTeamService.insert(doctorsTeam);
		
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		results.setData(null);

		return results;
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SystemControllerLog(description = "删除机构")
	public Results<Map<String, Object>> delete(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			DoctorsTeam doctorsTeam = doctorsTeamService.findById("selectByPrimaryKey", id);
			String hospitalName = doctorsTeam.getName();
			doctorsTeamService.delete("deleteByPrimaryKey", doctorsTeam);
			List<ContractedHospitals> contractedHospitals = contractedHospitalsService.getList("selectByHospitalName", hospitalName);
			if (contractedHospitals!=null&&contractedHospitals.size()>0) {
				contractedHospitalsService.delete("deleteByPrimaryKey", contractedHospitals.get(0));
			}
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
		} catch (Exception e) {
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

	/**
	 * 编辑页面
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPage")
	@SystemControllerLog(description = "跳转到机构编辑页面")
	public String editPage(int id, Model model) {
		try {
			DoctorsTeam doctorsTeam = doctorsTeamService.findById("selectByPrimaryKey", id);
			model.addAttribute("doctorsTeam", doctorsTeam);
			System.out.println(doctorsTeam.getHospitalIMG());
			if (StringUtils.isNotBlank(doctorsTeam.getHospitalIMG())) {
				model.addAttribute("isHospitalIMG", 1);
			} else {
				model.addAttribute("isHospitalIMG", 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/hospital/hospitalEdit";
	}

	/**
	 * 编辑医生档案
	 * 
	 * @param mediaCategory
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	@SystemControllerLog(description = "机构管理编辑页面")
	public Results<Map<String, Object>> edit(HttpServletRequest request, DoctorsTeam doctorsTeam_,
			MultipartFile file) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		if (!file.isEmpty() && !FileFilterUtils.isMediaFileType(file.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		String prefix = "hospitalIMG_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
		String fileUrl = UploadFilesUtils.saveFile(null,null,request, file, prefix); // 保存图片

		DoctorsTeam doctorsTeam = doctorsTeamService.findById("selectByPrimaryKey", doctorsTeam_.getId());
		doctorsTeam.setName(doctorsTeam_.getName());
		doctorsTeam.setArea(doctorsTeam_.getArea());
		doctorsTeam.setCityName(doctorsTeam_.getCityName());
		doctorsTeam.setCreatetime(doctorsTeam_.getCreatetime());
		doctorsTeam.setUpdatetime(new Date());
		doctorsTeam.setHospitalAddr(doctorsTeam_.getHospitalAddr());
		doctorsTeam.setHospitalIMG(fileUrl);
		doctorsTeam.setHospitalPhone(doctorsTeam_.getHospitalPhone());
		doctorsTeam.setIntroduction(doctorsTeam_.getIntroduction());
		doctorsTeam.setProvince(doctorsTeam_.getProvince());
		
		doctorsTeamService.update("updateByPrimaryKeySelective", doctorsTeam);
		
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		results.setData(null);

		return results;
	}

}
