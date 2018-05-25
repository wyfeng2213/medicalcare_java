package com.cmcc.medicalcare.controller.manage;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SystemUser;
import com.cmcc.medicalcare.service.ISystemUserService;
import com.cmcc.medicalcare.utils.SecurityUtils;

/**
 * 后台客户系统
 * 
 * @author luoyizhou
 *
 */
@Controller
@RequestMapping("/manage/systemUser")
public class SystemUserAction {

	// 输出日志
	private static Logger log = Logger.getLogger(SystemUserAction.class);

	/**
	 * systemUserService
	 */
	@Resource
	private ISystemUserService systemUserService;

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
	@RequestMapping(value = "login")
	@ResponseBody
	public Results<String> login(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response, String userName, String userPassword, String verifyCode) {
		Results<String> results = new Results<String>();
		Integer count = (Integer) request.getSession().getAttribute(userName);
		if (count == null) {
			count = 0;
		}
		if (count >= 5) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("frozen");
			return results;
		}
		String imgVcode = (String) request.getSession().getAttribute("imgVcode");
		
//		System.out.println("userName=" + userName);
//		System.out.println("userPassword=" + userPassword);
		if(userName==null||userPassword==null){
			results.setCode(MessageCode.CODE_201);
			results.setMessage("请输入正确用户和密码");
			return results;
		}
		
		SystemUser systemUser = systemUserService.findByParam("selectByUserName", userName.trim());
		try {
			if (systemUser != null && systemUser.getUserPassword().equals(userPassword)) {
				request.getSession().setAttribute("systemUser", systemUser);
				results.setCode(MessageCode.CODE_200);
				results.setMessage("登录成功");
				log.info("登录成功");
			} else {
				log.info("登录失败");
				request.getSession().setAttribute(userName, count + 1);
				results.setCode(MessageCode.CODE_204);
				results.setMessage("登录失败");
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

}
