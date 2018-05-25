package com.cmcc.medicalcare.controller.sys;

import java.util.Date;
import java.util.HashMap;
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

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.ISensitiveWordService;

/** 
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/sysSensitiveWord")
public class SystemSensitiveWordController extends BaseController {
	
	@Resource
	private ISensitiveWordService sensitiveWordService;
	
	/**
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "跳转敏感词管理页面")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
    	 return "/sys/sensitiveWord/sensitiveWord";
    }

	/**
	 * 获取sensitiveWordList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取敏感词list")
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
			Pager<SensitiveWord> pagers = sensitiveWordService.getList("findSensitiveWordPage", getPageIndex(page), getPageSize(rows), map);
			map.put("total", pagers.getTotalCount());
			map.put("rows", pagers.getList());
		}catch(Exception e){
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}
		
		return map;
	}
	
	/**
     * 跳转新增页
     *
     * @return
     */
	@SystemControllerLog(description = "跳转敏感词新增页面")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "/sys/sensitiveWord/sensitiveWordAdd";
    }
	
	
	/**
	 * 新增
	 * @param sensitiveWord
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "新增敏感词")
	public Results<String> add(HttpServletRequest request,SensitiveWord sensitiveWord){
		Results<String> results = new Results<String>();
		try {
			sensitiveWord.setCreatetime(new Date());
			sensitiveWord.setUpdatetime(new Date());;
			sensitiveWordService.insert(sensitiveWord);
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
	 * 跳转编辑页面
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/editPage")
	@SystemControllerLog(description = "跳转修改敏感词页面")
	public String editPage(int id, Model model) {
		try {
			SensitiveWord sensitiveWord = sensitiveWordService.findById("selectByPrimaryKey", id);
			model.addAttribute("sensitiveWord", sensitiveWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/sensitiveWord/sensitiveWordEdit";
	}
	
	/**
	 * 修改
	 * @param sensitiveWord
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "修改敏感词")
	public Results<String> edit(HttpServletRequest request,SensitiveWord sensitiveWord) {
		Results<String> results = new Results<String>();
		
		try {
			sensitiveWord.setUpdatetime(new Date());;
			sensitiveWordService.update("updateByPrimaryKeySelective", sensitiveWord);
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
	 * @param sensitiveWord
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除敏感词")
	public Results<String> delete(Integer id){
		Results<String> results = new Results<String>();
		try {
			SensitiveWord sensitiveWord = new SensitiveWord();
			sensitiveWord.setId(id);
			sensitiveWordService.delete("deleteByPrimaryKey", sensitiveWord);
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
	
	
}
