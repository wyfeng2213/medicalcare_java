package com.cmcc.medicalcare.controller.sys;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SystemResource;
import com.cmcc.medicalcare.service.ISystemResourceService;
import com.cmcc.medicalcare.vo.Tree;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统资源
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/systemResource")
public class SystemResourceController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemResourceController.class);

    @Autowired
    private ISystemResourceService systemResourceService;

    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/sys/admin/resource";
    }

    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/findTreeGrid", method = RequestMethod.POST)
    @ResponseBody
    public List<SystemResource> findTreeGrid() {
        List<SystemResource> treeGrid = systemResourceService.getList("selectAll", null);
        
        return treeGrid;
    }

    /**
     * 跳转新增页面
     *
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage() {
        return "/sys/admin/resourceAdd";
    }

    /**
     * 新增资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Results<Map<String, Object>> add(SystemResource resource) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		
        try {
        	resource.setCreatetime(new Date());
        	systemResourceService.insert(resource);
        	
            results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
            return results;
        } catch (RuntimeException e) {
        	results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
            return results;
        }
    }

    /**
     * 列出所有菜单树
     *
     * @return
     */
    @SystemControllerLog(description = "列出所有菜单树")
    @RequestMapping("/showAllTree")
    @ResponseBody
    public List<Tree> showAllTree() {
        return systemResourceService.findAllTree();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/showAllTrees", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> showAllTrees() {
        return systemResourceService.findAllTrees();
    }

    /**
     * 编辑资源跳转页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, int id) {
        SystemResource systemResource = systemResourceService.findById("selectByPrimaryKey", id);
		model.addAttribute("systemResource", systemResource);
        return "/sys/admin/resourceEdit";
    }

    /**
     * 编辑资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Results<Map<String, Object>> edit(SystemResource resource) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
    	
        try {
            resource.setUpdatetime(new Date());
        	systemResourceService.update("updateByPrimaryKeySelective", resource);
            
            results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
            return results;
        } catch (RuntimeException e) {
        	results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
            return results;
        }
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Results<Map<String, Object>> delete(int id) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
    	
        try {
        	SystemResource systemResource = systemResourceService.findById("selectByPrimaryKey", id);
			if (systemResource != null) {
				//先删除子菜单
//				systemResourceService.delete("deleteResourceByPid", systemResource);
				//再删除父菜单
				systemResourceService.delete("deleteByPrimaryKey", systemResource);
			}
            
            results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
            return results;
        } catch (RuntimeException e) {
        	results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
            return results;
        }
    }

}
