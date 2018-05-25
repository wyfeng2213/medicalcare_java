package com.cmcc.medicalcare.inter.jiuzhoutong;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.taskdefs.Concat;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.service.ISendcodeLogService;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.MD5;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName:
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
public class MemberLoginUtils {

//	private final static String menberLoginUrl =Config.jiuZhouTongTestUrl+"/out/memberLogin?";
	private final static String menberLoginUrl =Config.jiuZhouTongTestUrl+"/out/memberLogin?";

	private final static String isMemberCode = Config.jiuZhouTongTestUrl+"/out/isMemberCode?";
	
	/**
	 * 会员短信发送接口
	* @Title: memberLogin 
	* @Description: TODO 
	* @param @param phone
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String memberLogin(String phone) {
		String method = "/out/memberLogin";
		
		String timestamp = Toolkit.getyyyyMMddHHmmss();		
		String signstr= ThirdInterfaceUtils.getMD5signstr(method, timestamp);
		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("phone", phone);
		String param = "phone="+phone;
		
		String url = ThirdInterfaceUtils.getURL(menberLoginUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param,"UTF-8");
		return jsonStr;
	}
	
	public static void main(String[] args) {
//		System.out.println(MemberLoginUtils.memberLogin("15876587133"));
		System.out.println(MemberLoginUtils.isMemberCode("15876587133","443185"));
	}


	/**
	 * 会员登陆验证接口
	* @Title: isMemberCode 
	* @Description: TODO 
	* @param @param phone
	* @param @param code
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String isMemberCode(String phone,String code) {
		String method = "/out/isMemberCode";
		
		String timestamp = Toolkit.getyyyyMMddHHmmss();		
		String signstr= ThirdInterfaceUtils.getMD5signstr(method, timestamp);
		
		String param = "phone="+phone+"&code="+code;
		
		String url = ThirdInterfaceUtils.getURL(isMemberCode, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param,"UTF-8");
		return jsonStr;
	}

}
