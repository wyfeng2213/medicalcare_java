package com.cmcc.medicalcare.servlet;

import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.utils.MyDispatcherUtil;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * MyDispatcherServlet
 * @author Administrator
 *
 */
public class MyDispatcherServlet extends DispatcherServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4326366227399480131L;
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MyDispatcherServlet.class);
	/**
	 * ISDEV
	 */
	private static final Boolean ISDEV = false;//是否开发模式,false 开发，true生产，开发模式可以直接通过pc访问，生产模式则只能移动访问
	/**
	 * LOCALE_SESSION_ATTRIBUTE_NAME
	 */
	private static final String LOCALE_SESSION_ATTRIBUTE_NAME = SessionLocaleResolver.class.getName() + ".LOCALE";
	
	/**
	 * doService
	 */
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Locale targetLocal = null;
		Locale sessionLocale = (Locale) WebUtils.getSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME);
		String urlLocale = request.getParameter("locale");
		logger.debug("客户端浏览器默认Locale：" + request.getLocale());
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		if(ISDEV&&requestUrl.indexOf("notMobile.htm")==-1){
			 //获取ua，用来判断是否为移动端访问  
            String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
            if(!Toolkit.check(userAgent)){
            	String url = "/index/notMobile.htm";		
				request.getRequestDispatcher(url).forward(request, response);
				return ;
            }  
            //判断是否为移动端访问  
		}
		// 请求的URL中携带了locale参数，说明用户希望改变页面的语言显示
		if (urlLocale != null) {
			targetLocal = StringUtils.parseLocaleString(urlLocale);
			logger.info("用户希望手动改变Locale语言到 -> " + targetLocal);
			setLocale(request, response, targetLocal);
		} else {// 虽然没有在当前页面手动请求更改Locale标记，但在其他页面上仍要使用用户的语言习惯
			// Session中有Locale标记，则以Session中的Locale标记为准
			if (sessionLocale != null) {
				targetLocal = sessionLocale;
				logger.info("使用Session中的Locale语言 -> " + targetLocal);
				setLocale(request, response, targetLocal);
			}
		}
				
		//过滤
		int flag = MyDispatcherUtil.isDispatcher(request);		
		if(flag != 0){
			MyDispatcherUtil.goDispatch(request, response, flag);
			return;
		}
		super.doService(request, response);
	}
	
	/**
	 * 设置Locale，并写入到Session中
	 */
	private void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		WebUtils.setSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME, locale);
	}
	
	/**
	 * init
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String prefix = getServletContext().getRealPath("/"); // 获取项目所在绝对路径
		Config.readProperties(prefix + "WEB-INF/classes/config/config.properties");
	}
	
	/**
	 * destroy
	 */
	@Override
	public void destroy() {
		super.destroy();
	}
}
