package com.cmcc.medicalcare.controller.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.HealthyProducts;
import com.cmcc.medicalcare.model.SystemBanner;
import com.cmcc.medicalcare.service.IHealthyProductsService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;

/** 
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/systemHealthyProducts")
public class SystemHealthyProductsController {
	
	@Resource
	private IHealthyProductsService healthyProductsService;
	
	@Resource
	private IMediaLogService mediaLogService;
	
	/**
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "跳转管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
    	 return "/sys/healthyProducts/systemHealthyProducts";
    }

	
	
	/**
     * 用户管理页
     *
     * @return
     */
	@SystemControllerLog(description = "跳转新增页面")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "/sys/healthyProducts/systemHealthyProductsAdd";
    }
	
	
	/**
	 * 新增
	 * @param healthyProducts
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "新增健康产品")
	public Results<String> add(HttpServletRequest request,HealthyProducts healthyProducts,MultipartFile file){
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
				healthyProducts.setImgSrc(fileUrl);
			}
			healthyProducts.setCreatetime(new Date());
			healthyProducts.setUpdatetime(new Date());;
			healthyProductsService.insert(healthyProducts);
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
	 * 获取healthyProductslist
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取健康产品list")
	public Map<String, Object> findPage(HttpServletRequest request, HealthyProducts healthyProducts,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pager<HealthyProducts> pagers = healthyProductsService.getList("findPage", page, rows, healthyProducts);
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
	@SystemControllerLog(description = "修改健康产品页面")
	public String editPage(int id, Model model) {
		try {
			HealthyProducts healthyProducts = healthyProductsService.findById("selectByPrimaryKey", id);
			model.addAttribute("healthyProducts", healthyProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/healthyProducts/systemHealthyProductsEdit";
	}
	
	/**
	 * 修改
	 * @param healthyProducts
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "修改健康产品")
	public Results<String> edit(HttpServletRequest request,HealthyProducts healthyProducts,MultipartFile file){
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
				healthyProducts.setImgSrc(fileUrl);
			}
			healthyProducts.setUpdatetime(new Date());;
			healthyProductsService.update("updateByPrimaryKeySelective", healthyProducts);
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
	 * @param healthyProducts
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除健康产品")
	public Results<String> delete(Integer id){
		Results<String> results = new Results<String>();
		try {
			HealthyProducts healthyProducts = new HealthyProducts();
			healthyProducts.setId(id);
			healthyProductsService.delete("deleteByPrimaryKey", healthyProducts);
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
	@SystemControllerLog(description = "拒绝审核健康优品")	
	public Results<Map<String, Object>> reject(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			HealthyProducts healthyProducts = healthyProductsService.findById("selectByPrimaryKey", id);
			healthyProducts.setFlag(0);
			healthyProducts.setUpdatetime(new Date());
			healthyProductsService.update("updateByPrimaryKey", healthyProducts);
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
	@SystemControllerLog(description = "通过审核健康优品")
	public Results<Map<String, Object>> pass(int id, Model model) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			HealthyProducts healthyProducts = healthyProductsService.findById("selectByPrimaryKey", id);
			healthyProducts.setFlag(1);
			healthyProducts.setUpdatetime(new Date());
			healthyProductsService.update("updateByPrimaryKey", healthyProducts);
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
