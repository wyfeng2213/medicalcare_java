package com.cmcc.medicalcare.controller.sys;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.SystemRole;
import com.cmcc.medicalcare.service.ISystemRoleResourceService;
import com.cmcc.medicalcare.service.ISystemRoleService;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色
 * 
 * @author zds
 *
 */
@Controller
@RequestMapping("/sys/systemRole")
public class SystemRoleController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemResourceController.class);

    @Autowired
    private ISystemRoleService systemRoleService;
    
    @Autowired
    private ISystemRoleResourceService systemRoleResourceService;

    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/sys/admin/role";
    }

    /**
     * 用户管理页
     *
     * @param role
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping(value = "/findRolePage", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> findUserPage(HttpServletRequest request, SystemRole systemRole,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pager<SystemRole> pagers = systemRoleService.getList("findRolePage", page, rows, systemRole);
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
     * 跳转新增页面
     *
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "/sys/admin/roleAdd";
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Results<Map<String, Object>> add(SystemRole role) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
    	
    	try {
    		systemRoleService.insert(role);
        	
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
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Results<Map<String, Object>> delete(int id) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
        try {
        	SystemRole systemRole = systemRoleService.findById("selectByPrimaryKey", id);
            if (systemRole != null) {
            	systemRoleService.delete("deleteByPrimaryKey", systemRole);
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

    /**
     * 编辑角色跳转页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/editPage", method = RequestMethod.GET)
    public String editPage(Model model, int id) {
        SystemRole systemRole = systemRoleService.findById("selectByPrimaryKey", id);
		model.addAttribute("systemRole", systemRole);
        return "/sys/admin/roleEdit";
    }

    /**
     * 编辑角色
     *
     * @param role
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Results<Map<String, Object>> edit(SystemRole role) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
    	
        try {
            systemRoleService.update("updateByPrimaryKeySelective", role);
            
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
     * 角色授权跳转页面
     *
     * @param request
     * @param id 角色id
     * @param model
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(HttpServletRequest request, int id, Model model) {
        model.addAttribute("id", id);
        return "/sys/admin/roleGrant";
    }
    
    /**
     * 角色授权
     *
     * @param id
     * @param resourceIds
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Results<Map<String, Object>> grant(int id, String resourceIds) {
    	Results<Map<String, Object>> results = new Results<Map<String, Object>>();
    	
        try {
        	systemRoleResourceService.updateRoleResource(id, resourceIds);
            
            results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
            return results;
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
            return results;
        }
    }

    /**
     * 根据角色id查询资源id集合
     * 
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public Results<Object> findResourceIdListByRoleId(HttpServletRequest request, Integer id) {
    	Results<Object> results = new Results<Object>();
    	
        try {
            List<Integer> resourceIdList = systemRoleResourceService.getRoleResourceIdListByRoleId(id);
            
            results.setData(resourceIdList);
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
