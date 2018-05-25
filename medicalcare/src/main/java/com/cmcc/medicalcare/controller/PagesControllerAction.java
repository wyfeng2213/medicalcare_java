package com.cmcc.medicalcare.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;

/**
 * 进入时要使用的action
 * 
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/index")
public class PagesControllerAction extends BaseController {

	/**
	 * 没有找到页面时显示的错误信息
	 * <p>
	 * CreateTime: Jul 1, 2015 8:29:54 AM
	 * </p>
	 * <p>
	 * 描述: #添加文字描述#
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public @RequestMapping(value = "404") @ResponseBody Results<String> show404() {
		return new Results<String>(MessageCode.CODE_404, MessageCode.MESSAGE_404, null);
	}

	/**
	 * 
	 * <p>
	 * CreateTime: Jul 1, 2015 8:30:27 AM
	 * </p>
	 * <p>
	 * 描述: 系统出现错误的描述信息
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public @RequestMapping(value = "500") @ResponseBody Results<String> show500() {
		return new Results<String>(MessageCode.CODE_500, MessageCode.MESSAGE_500, null);
	}

	/**
	 * 
	 * <p>
	 * CreateTime: Jul 1, 2015 8:30:47 AM
	 * </p>
	 * <p>
	 * 描述: 没有登陆的情况下的返回提示信息
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public @RequestMapping(value = "400") @ResponseBody Results<String> show400() {
		return new Results<String>(MessageCode.CODE_400, MessageCode.MESSAGE_400, null);
	}

	/**
	 * 
	 * <p>
	 * CreateTime: Jul 1, 2015 8:30:47 AM
	 * </p>
	 * <p>
	 * 描述: 缺失参数
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public @RequestMapping(value = "201") @ResponseBody Results<String> show201() {
		return new Results<String>(MessageCode.CODE_201, MessageCode.MESSAGE_201, null);
	}

	/**
	 * 
	 * <p>
	 * CreateTime: Jul 1, 2015 8:30:47 AM
	 * </p>
	 * <p>
	 * 描述: 不是移动设备发送的求情直接驳回的提示信息
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public @RequestMapping(value = "notMobile") @ResponseBody Results<String> showMobile() {
		return new Results<String>(MessageCode.CODE_999, MessageCode.MESSAGE_MOBILE, null);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "399")
	@ResponseBody
	public Results<String> accountBindExpired() {
		return new Results<String>(MessageCode.CODE_399, MessageCode.MESSAGE_399, null);
	}

	public @RequestMapping(value = "244_APP") @ResponseBody Results<String> show244_APP(HttpServletRequest request
			) {
		String sensitiveWord = (String) request.getAttribute("sensitiveWord");
		return new Results<String>(MessageCode.CODE_244, "根据法律法规不给于搜索'"+sensitiveWord+"'相应的违法信息", null);
	}

	public @RequestMapping(value = "244_SYSTEM") void show244_SYSTEM(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sensitiveWord = (String) request.getAttribute("sensitiveWord");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">");
		builder.append("alert(\"根据法律法规不给于搜索\'"+sensitiveWord+"\'相应的违法信息！\");");
		builder.append("</script>");
		out.print(builder.toString());
		out.close();
	}
}
