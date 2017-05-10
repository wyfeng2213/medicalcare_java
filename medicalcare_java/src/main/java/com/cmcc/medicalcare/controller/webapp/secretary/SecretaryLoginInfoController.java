package com.cmcc.medicalcare.controller.webapp.secretary;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SecretaryLoginInfo;
import com.cmcc.medicalcare.model.SecretaryUser;
import com.cmcc.medicalcare.service.ISecretaryLoginInfoService;
import com.cmcc.medicalcare.service.ISecretaryUserService;
import com.cmcc.medicalcare.utils.EcopUtils;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.Toolkit;
import com.cmcc.medicalcare.utils.SecurityUtils;

/**
 * @ClassName: 秘书用户登录类
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
@RequestMapping("/web/secretaryLoginInfo")
@Controller
public class SecretaryLoginInfoController {

	@Resource
	private ISecretaryLoginInfoService secretaryLoginInfoService;

	@Resource
	private ISecretaryUserService secretaryUserService;

	private SecretaryUser secretaryUser;

	/**
	 * 
	 * @Title: 秘书端密码登录 @Description: TODO @param @param locale @param @param
	 *         model @param @param request @param @param response @param @param
	 *         hUserName @param @param hUserPassword @param @param
	 *         verifyCode @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST) 
	@ResponseBody
	@SystemControllerLog(description = "秘书端密码登录")
	public String login(HttpServletRequest request, String hUserName, String hUserPassword, String verifyCode) {
		Integer count = (Integer) request.getSession().getAttribute(hUserName);
		if (count == null) {
			count = 0;
		}
		if (count >= 5) {
			return "frozen";
		}
		
		String imgVcode = (String) request.getSession().getAttribute("imgVcode");
		secretaryUser = secretaryUserService.findByParam("selectByUserName", hUserName.trim());
		try {
			if (secretaryUser != null && secretaryUser.getPassword().equals(SecurityUtils.encryptMD5(hUserPassword))
					&& imgVcode.equalsIgnoreCase(verifyCode)) {
				request.getSession().setAttribute("secretaryUser", secretaryUser);
				
				//登录成功后,记录登录信息
				SecretaryLoginInfo secretaryLoginInfo_ = secretaryLoginInfoService.findByParam("selectBySecretaryId", secretaryUser.getId());
				if (null == secretaryLoginInfo_) {
					SecretaryLoginInfo secretaryLoginInfo = new SecretaryLoginInfo();
					secretaryLoginInfo.setSecretaryId(secretaryUser.getId());
					secretaryLoginInfo.setSecretaryName(secretaryUser.getName());
					secretaryLoginInfo.setSecretaryPhone(secretaryUser.getPhone());
					secretaryLoginInfo.setSecretaryLoginId(secretaryUser.getLoginId());
					secretaryLoginInfo.setToken(request.getSession().getId());
					secretaryLoginInfo.setLoginTime(new Date());
					secretaryLoginInfo.setIp(HTTPUtils.getIpAddrByRequest(request));
					secretaryLoginInfo.setCreatetime(new Date());
					
					secretaryLoginInfoService.insert(secretaryLoginInfo);
				} else {
					secretaryLoginInfo_.setLastLoginTime(secretaryLoginInfo_.getLoginTime());
					secretaryLoginInfo_.setLoginTime(new Date());
					secretaryLoginInfo_.setToken(request.getSession().getId());
					secretaryLoginInfo_.setUpdatetime(new Date());
					
					secretaryLoginInfoService.update("updateByPrimaryKeySelective", secretaryLoginInfo_);
				}
				
				request.getSession().setAttribute("secretaryUser", secretaryUser);
				
				return "success";
			} else {
				request.getSession().setAttribute(hUserName, count + 1);
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}
	
	/**
	 * 下发验证码 @Title: sendCode @Description: TODO @param @param
	 * request @param @param hPhonenumber @param @param
	 * equipmentData @param @return 设定文件 @return Results<String> 返回类型 @throws
	 */
	@RequestMapping(value = "sendCode") 
	@ResponseBody 
	public Results<String> sendCode(HttpServletRequest request, @RequestParam String phone) {
		Results<String> results = new Results<String>();
		if (null == phone || "".equals(phone)) { // 如果没有传入指定的参数则返回null，不做任何处理
			return new Results<String>(MessageCode.CODE_201, "手机号码不能为空！", null);
		}
		
		//验证手机号码是否合法
		if(Toolkit.isMobileNO(phone)) {
			//发送下发验证码请求
			boolean flag = EcopUtils.sendValcode(phone);
			
			if(true == flag) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage("验证码下发成功");
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("验证码下发失败！请重试");
			}
		} else {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("手机号码填写错误，请填写正确的手机号码！");
		}
		
		return results;
		
	}

	/**
	 * 验证码登录 @Title: codeLogin @Description: TODO @param @param
	 * request @param @param parame @param @param code @param @param
	 * equipmentData @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "codeLogin") 
	@ResponseBody
	@SystemControllerLog(description = "秘书端验证码登录")
	public String codeLogin(HttpServletRequest request, @RequestParam String phone, @RequestParam String code, String verifyCode) {
		Integer count = (Integer) request.getSession().getAttribute(phone);
		if (count == null) {
			count = 0;
		}
		if (count >= 5) {
			return "frozen";
		}
		String imgVcode = (String) request.getSession().getAttribute("imgVcode");
		secretaryUser = secretaryUserService.findByParam("selectByPhone", phone);

		//验证验证码与手机号码的正确性
		if(Toolkit.isMobileNO(phone)){
			//向服务器发送验证码验证请求
			Map<String,Object> flagMap = EcopUtils.checkValcode(phone, code);
			
			Object flagObject = flagMap.get("flag");
			boolean flag = false;
			if(null!=flagObject) {	//如果flagObject是null的话，就不转换
				flag = (Boolean)flagObject;
			}
			if(secretaryUser != null && true == flag && imgVcode.equalsIgnoreCase(verifyCode)) { //校验成功
				//登录成功后,记录登录信息
				SecretaryLoginInfo secretaryLoginInfo_ = secretaryLoginInfoService.findByParam("selectByPhone", phone);
				if (null == secretaryLoginInfo_) {
					SecretaryLoginInfo secretaryLoginInfo = new SecretaryLoginInfo();
					secretaryLoginInfo.setSecretaryId(secretaryUser.getId());
					secretaryLoginInfo.setSecretaryName(secretaryUser.getName());
					secretaryLoginInfo.setSecretaryPhone(secretaryUser.getPhone());
					secretaryLoginInfo.setSecretaryLoginId(secretaryUser.getLoginId());
					secretaryLoginInfo.setToken(request.getSession().getId());
					secretaryLoginInfo.setLoginTime(new Date());
					secretaryLoginInfo.setIp(HTTPUtils.getIpAddrByRequest(request));
					secretaryLoginInfo.setCreatetime(new Date());
					
					secretaryLoginInfoService.insert(secretaryLoginInfo);
				} else {
					secretaryLoginInfo_.setLastLoginTime(secretaryLoginInfo_.getLoginTime());
					secretaryLoginInfo_.setLoginTime(new Date());
					secretaryLoginInfo_.setToken(request.getSession().getId());
					secretaryLoginInfo_.setUpdatetime(new Date());
					
					secretaryLoginInfoService.update("updateByPrimaryKeySelective", secretaryLoginInfo_);
				}
				
				request.getSession().setAttribute("secretaryUser", secretaryUser);
				
				return "success";
			} else {
				request.getSession().setAttribute(phone, count + 1);
				return "error";
			}
		} else {
			request.getSession().setAttribute(phone, count + 1);
			return "error";
		}
		
	}
	

	/**
	 * 
	 * @Title: logout @Description: TODO @param @param request @param @param
	 *         response @param @return @param @throws
	 *         ServletException @param @throws IOException 设定文件 @return String
	 *         返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("secretaryUser");
		response.sendRedirect(request.getContextPath() + "/secretary/login.jsp");
		return null;
	}

	/**
	 * 
	 * @Title: userGoto @Description: TODO @param @param request @param @param
	 *         response @param @return @param @throws
	 *         ServletException @param @throws IOException 设定文件 @return String
	 *         返回类型 @throws
	 */
	@RequestMapping(value = "userGoto")
	public String userGoto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.sendRedirect(request.getContextPath()+"/msg/list.htm");
		// return setTilesView("pages/systemPush/list",LAYOUT_STANDARD);
		return "success";
	}
	
	/**
	 * @return the secretaryUser
	 */
	public SecretaryUser getSecretaryUser() {
		return secretaryUser;
	}

	/**
	 * @param secretaryUser
	 *            the secretaryUser to set
	 */
	public void setSecretaryUser(SecretaryUser secretaryUser) {
		this.secretaryUser = secretaryUser;
	}
}
