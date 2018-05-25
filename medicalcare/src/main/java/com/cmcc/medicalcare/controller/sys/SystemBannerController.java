package com.cmcc.medicalcare.controller.sys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.ContractedHospitals;
import com.cmcc.medicalcare.model.ContractedHospitalsDoctorsLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.SystemBanner;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.ISystemBannerService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import net.sf.json.JSONArray;

/** 
 * 
 * @author zhouzhou
 *
 */
@Controller
@RequestMapping("/sys/systemBanner")
public class SystemBannerController {
	
	@Resource
	private ISystemBannerService systemBannerService;
	
	@Resource
	private IMediaLogService mediaLogService;
	/**
     * 用户管理页
     *
     * @return
     */
	@SystemControllerLog(description = "跳转新增页面")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "/sys/banner/systemBannerAdd";
    }
	
	
	/**
	 * 新增
	 * @param systemBanner
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "新增banner")
	public Results<String> add(HttpServletRequest request,SystemBanner systemBanner,MultipartFile file){
		Results<String> results = new Results<String>();
		try {
			if (!file.isEmpty() && !FileFilterUtils.isImageFileType(file.getOriginalFilename())) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("不支持该文件格式上传");
				return results;
			}
			String prefix =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ "_";
			if (!file.isEmpty()) {
				String fileUrl = UploadFilesUtils.saveFile(null,null,request, file, prefix); // 保存图片
				systemBanner.setImgSrc(fileUrl);
			}
			systemBanner.setCreatetime(new Date());
			systemBanner.setUpdatetime(new Date());;
			systemBannerService.insert(systemBanner);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("添加成功");
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			results.setMessage(e.getMessage());
			results.setCode(MessageCode.CODE_501);
			return results;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "跳转管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
    	 return "/sys/banner/systemBanner";
    }
    
	/**
	 * 获取apklist
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取bannerlist")
	public Map<String, Object> findPage(HttpServletRequest request,  SystemBanner systemBanner,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pager<SystemBanner> pagers = systemBannerService.getList("findPage", page, rows, systemBanner);
			map.put("total", pagers.getTotalCount());
			map.put("rows", pagers.getList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 编辑页面
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPage")
	@SystemControllerLog(description = "修改banner页面")
	public String editPage(int id, Model model) {
		try {
			SystemBanner systemBanner = systemBannerService.findById("selectByPrimaryKey", id);
			model.addAttribute("systemBanner", systemBanner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/banner/systemBannerEdit";
	}
	
	/**
	 * 修改
	 * @param systemBanner
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "修改banner")
	public Results<String> edit(HttpServletRequest request,SystemBanner systemBanner,MultipartFile file){
		Results<String> results = new Results<String>();
		try {
			if (!file.isEmpty() && !FileFilterUtils.isImageFileType(file.getOriginalFilename())) {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("不支持该文件格式上传");
				return results;
			}
			if (!file.isEmpty()) {
				String prefix =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ "_";
				String fileUrl = UploadFilesUtils.saveFile(null,null,request, file, prefix); // 保存图片
				systemBanner.setImgSrc(fileUrl);
			}
			systemBanner.setUpdatetime(new Date());;
			systemBannerService.update("updateByPrimaryKeySelective", systemBanner);
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
	
	/**
	 * 删除
	 * @param systemBanner
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除banner")
	public Results<String> delete(Integer id){
		Results<String> results = new Results<String>();
		try {
			SystemBanner systemBanner = new SystemBanner();
			systemBanner.setId(id);
			systemBannerService.delete("deleteByPrimaryKey", systemBanner);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("删除成功");
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			results.setMessage(e.getMessage());
			results.setCode(MessageCode.CODE_501);
			return results;
		}
	}
	
	
	/**
	 * 
	* @Title: reject 
	* @Description: TODO 
	* @param @param id
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping("/reject")
	@ResponseBody
	@SystemControllerLog(description = "拒绝审核banner")	
	public Results<Map<String, Object>> reject(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			SystemBanner systemBanner = systemBannerService.findById("selectByPrimaryKey", id);
			systemBanner.setFlag(0);
			systemBanner.setUpdatetime(new Date());
			systemBannerService.update("updateByPrimaryKey", systemBanner);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	
	@RequestMapping("/pass")
	@ResponseBody
	@SystemControllerLog(description = "通过审核banner")
	public Results<Map<String, Object>> pass(int id, Model model) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			SystemBanner systemBanner = systemBannerService.findById("selectByPrimaryKey", id);
			
			systemBanner.setFlag(1);
			systemBanner.setUpdatetime(new Date());
			systemBannerService.update("updateByPrimaryKey", systemBanner);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}

}
