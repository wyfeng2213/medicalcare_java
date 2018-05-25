package com.cmcc.medicalcare.controller.sys;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.SystemRole;
import com.cmcc.medicalcare.model.SystemUser;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.ISystemRoleService;
import com.cmcc.medicalcare.service.ISystemUserService;
import com.cmcc.medicalcare.utils.SecurityUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * 后台客户系统
 * 
 * @author luoyizhou
 *
 */
@Controller
@RequestMapping("/sys/systemUser")
public class SystemUserController {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemUserController.class);
	private final static String PWD = "yuejiankang123";

	/**
	 * systemUserService
	 */
	@Resource
	private ISystemUserService systemUserService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;
	
	@Resource
	private ISystemRoleService systemRoleService;
	
	/**
	 * 登陆
	 * 
	 * @param locale
	 * @param model
	 * @param request
	 * @param response
	 * @param userName
	 * @param userPassword
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@SystemControllerLog(description = "平台用户登录")
	@RequestMapping(value = "login")
	@ResponseBody
	public Results<String> login(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response,
			String userName, String userPassword, String verifyCode) {
		Results<String> results = new Results<String>();
		Integer count = (Integer) request.getSession().getAttribute(userName);
		if (count == null) {
			count = 0;
		}
		if (count >= 3) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("frozen");
			return results;
		}
		String imgVcode = (String) request.getSession().getAttribute("imgVcode");

		// System.out.println("userName=" + userName);
		// System.out.println("userPassword=" + userPassword);
		if (userName == null || userPassword == null || verifyCode == null) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage("请输入正确用户、密码、验证码");
			return results;
		}

		if (userName.length() >20) {
			results.setCode(MessageCode.CODE_206);
			results.setMessage("用户名长度不能大于20位！");
			return results;
		}
		
		SystemUser systemUser = systemUserService.findByParam("selectByUserName", userName.trim());
		if (systemUser == null) {
			request.getSession().setAttribute(userName, count + 1);
			results.setCode(MessageCode.CODE_204);
			results.setMessage("输入正确用户、密码、验证码");
			return results;
		}
		Date last_editpwd_time = systemUser.getLastEditpwdTime() == null ? systemUser.getCreateTime()
				: systemUser.getLastEditpwdTime();
		long time_dif = (new Date().getTime() - last_editpwd_time.getTime()) / 1000;
		long time_limit = 90 * 24 * 60 * 60;
		if (time_dif > time_limit) {
			results.setCode(MessageCode.CODE_400);
			results.setMessage("密码过期");
			return results;
		}
		
		try {
			if (systemUser != null && systemUser.getUserPassword().equals(userPassword)
					&& imgVcode.equalsIgnoreCase(verifyCode)) {
//				request.getSession().setAttribute("systemUser", systemUser);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("登录成功");
				//shiro认证
		        UsernamePasswordToken token = new UsernamePasswordToken(systemUser.getUserName(), userPassword);
		        token.setRememberMe(true);
		        Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		        subject.login(token);
		        subject.getSession().setAttribute("systemUser", systemUser);
			} else {
				request.getSession().setAttribute(userName, count + 1);
				results.setCode(MessageCode.CODE_204);
				results.setMessage("输入正确用户、密码、验证码");
			}
		} catch (Exception e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_204);
			results.setMessage("登录失败");
		}
		return results;

	}

	/**
	 * 注销登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@SystemControllerLog(description = "平台用户退出")
	@ResponseBody
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("systemUser");
		response.sendRedirect(request.getContextPath() + "/manage/login.jsp");
		return null;
	}

	/**
	 * 登陆后跳转到消息推送list 页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "userGoto")
	public String userGoto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/msg/list2.htm");
		// return setTilesView("pages/systemPush/list",LAYOUT_STANDARD);
		return null;
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage(Model model) {
		List<DoctorsTeam> doctorsTeamList = doctorsTeamService.getList("selectAll", null);
		model.addAttribute("doctorsTeamList", doctorsTeamList);
		
		List<SystemRole> systemRoleList = systemRoleService.getList("selectAll", null);
		model.addAttribute("systemRoleList", systemRoleList);
		
		return "/sys/admin/systemuserAdd";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager() {
		return "/sys/admin/systemuser";
	}

	/**
	 * 平台新增系统用户
	 *
	 * @return
	 */
	@SystemControllerLog(description = "平台新增系统用户")
	@RequestMapping(value = "/add")
	@ResponseBody
	public Results<String> add(HttpServletRequest request, SystemUser systemUser, String userName) {
		Results<String> results = new Results<String>();
		if (systemUser.getUserName().length() > 20 || systemUser.getUserName().length() < 4) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("用户名长度只能大于4位且小于20位！");
			return results;
		}

		SystemUser _systemUser = systemUserService.findByParam("selectByUserName", systemUser.getUserName());
		if (_systemUser != null) {
			results.setMessage("用户名已存在!");
			results.setCode(MessageCode.CODE_405);
			return results;
		}
		try {
			systemUser.setUserPassword(SecurityUtils.encryptMD5(PWD));
			SystemUser user = (SystemUser) request.getSession().getAttribute("systemUser");
			systemUser.setCreateTime(new Date());
			if (systemUser.getUserRole() != 4) {// 医疗机构组织
				systemUser.setHospitalId(null);
				systemUser.setHospitalName(null);
			}
			systemUser.setCreateUser(user.getUserRealname());
			systemUser.setCreateUserid(user.getId());
			systemUser.setIsEnable(1);
			systemUserService.insert(systemUser);
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
	 * 删除
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SystemControllerLog(description = "平台删除系统用户")
	public Results<Map<String, Object>> delete(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			SystemUser _systemUser = systemUserService.findById("selectByPrimaryKey", id);
			if (_systemUser != null) {
				systemUserService.delete("deleteByPrimaryKey", _systemUser);
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
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/assignRolePage", method = RequestMethod.GET)
	public String assignRolePage(Integer id, Model model) {
		SystemUser userVo = systemUserService.findByParam("selectByPrimaryKey", id);
		model.addAttribute("user", userVo);
		
		List<SystemRole> systemRoleList = systemRoleService.getList("selectAll", null);
		model.addAttribute("systemRoleList", systemRoleList);
		
		return "/sys/admin/systemuserAssignRole";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@SystemControllerLog(description = "平台系统用户分配角色")
	@RequestMapping(value = "/assignRole", method = RequestMethod.POST)
	@ResponseBody
	public Results<String> assignRole(SystemUser systemUser) {
		Results<String> results = new Results<String>();
		try {
			systemUserService.update("updateByPrimaryKeySelective", systemUser);
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
	 * 用户管理页
	 *
	 * @return
	 */
	@SystemControllerLog(description = "平台系统用户禁止or启用")
	@RequestMapping(value = "/forbiddenOrEnable", method = RequestMethod.POST)
	@ResponseBody
	public Results<String> forbiddenOrEnable(SystemUser systemUser) {
		Results<String> results = new Results<String>();
		try {
			systemUserService.update("updateByPrimaryKeySelective", systemUser);
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
	 * 用户管理页
	 *
	 * @return
	 */
	@SystemControllerLog(description = "平台系统用户重置密码")
	@RequestMapping(value = "/resetPwdFun", method = RequestMethod.POST)
	@ResponseBody
	public Results<String> resetPwdFun(SystemUser systemUser) {
		Results<String> results = new Results<String>();
		try {
			systemUser.setUserPassword(SecurityUtils.encryptMD5(PWD));
			systemUser.setLastEditpwdTime(new Date());
			systemUserService.update("updateByPrimaryKeySelective", systemUser);
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
	 * 用户管理页
	 *
	 * @return
	 */
	@SystemControllerLog(description = "平台系统用户查询")
	@RequestMapping(value = "/findUserPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findUserPage(HttpServletResponse response, SystemUser systemUser,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Pager<SystemUser> systemUserPage = systemUserService.getList("findUserPage", page, rows, systemUser);
			map.put("total", systemUserPage.getTotalCount());
			map.put("rows", systemUserPage.getList());
		} catch (Exception e) {
			map.put("total", 0);
			map.put("rows", "");
		}
		return map;
	}

	/**
	 * 修改密码页
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "跳转修改密码页")
	@RequestMapping(value = "/editPwdPage", method = RequestMethod.GET)
	public String editPwdPage() {
		return "/sys/admin/userEditPwd";
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	@SystemControllerLog(description = "修改用户密码")
	@RequestMapping(value = "/editUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Results<String> editUserPwd(HttpServletRequest request, String userName, String oldPwd, String pwd) {
		Results<String> results = new Results<String>();

		if (pwd.length() < 8) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("密码长度不能少于8位！");
			return results;
		}

		if (!((Toolkit.isContainNumeric(pwd) && Toolkit.isContainLetter(pwd))
				|| (Toolkit.isContainNumeric(pwd) && Toolkit.isContainSymbol(pwd))
				|| (Toolkit.isContainLetter(pwd) && Toolkit.isContainSymbol(pwd)))) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("修改失败,密码至少是字母,数字,符号中的2种组合");
			return results;
		}

		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser == null) {
			systemUser = systemUserService.findByParam("selectByUserName", userName);
			if (systemUser == null) {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("修改失败,请检查用户名是否正确");
				return results;
			}
		}

		if (!systemUser.getUserPassword().equals(DigestUtils.md5Hex(oldPwd))) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("修改失败,原密码输入有误");
			return results;
		}

		try {
			systemUser.setUserPassword(DigestUtils.md5Hex(pwd));
			systemUser.setLastEditpwdTime(new Date());
			systemUserService.update("updateByPrimaryKeySelective", systemUser);
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
	 * 查询角色
	 * 
	 * @return
	 */
	@SystemControllerLog(description = "查询角色")
	@RequestMapping(value="/queryRoleById", method={RequestMethod.POST})  
	@ResponseBody  
	public SystemRole queryRoleById(int id) throws Exception {  
		SystemRole systemRole = systemRoleService.findById("selectByPrimaryKey", id);
	    return systemRole;  
	}

}
