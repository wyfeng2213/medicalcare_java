package com.cmcc.medicalcare.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 登录过滤
 * 
 */
public class SessionFilter extends OncePerRequestFilter {

	private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		 System.out.println("=====================SessionFilter");
		// 不过滤的uri
		// String[] notFilterUrls = new String[] { "login.jsp",
		// "login2.htm","index.jsp", "download.jsp", "api" };
		String[] filterUrls = new String[] {
				"/medicalcare/manage/login.jsp",
				"/medicalcare/manage/login.html",
				"/medicalcare/manage/systemUser/login.action",
				"/medicalcare/manage/systemUser/loginout.action",
				"/medicalcare/sys/systemUser/login.action",
				"/medicalcare/sys/login.action",
				"/medicalcare/sys/login.jsp",
				"/medicalcare/sys/login",
				"/medicalcare/sys/systemUser/editPwdPage.action",
				"/medicalcare/sys/systemUser/editUserPwd.action"};

		// 请求的uri
		String uri = request.getRequestURI();
//		System.out.println("=====================SessionFilter:"+uri);
		// 是否过滤
		boolean doFilter = false;

		if (uri.startsWith("/medicalcare/sys/")) {
			doFilter = true;
			for (String s : filterUrls) {
				if (uri.equals(s)) {
					doFilter = false;
					break;
				}
			}
		}
//		System.out.println("=====================doFilter :"+doFilter);
		if (doFilter) {
			// 执行过滤
			// 从session中获取登录者实体
			Object obj = request.getSession().getAttribute("systemUser");
//			 System.out.println("=====================obj:"+obj);
			if (null == obj) {
				// 如果session中不存在登录者实体，则弹出框提示重新登录
				// 设置request和response的字符集，防止乱码
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8;");
				PrintWriter out = response.getWriter();
				String loginPage = request.getContextPath() + "/sys/login.action";
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\">");
				builder.append("alert('网页过期或没有登录，请重新登录！');");
				builder.append("window.top.location.href='");
				builder.append(loginPage);
				builder.append("';");
				builder.append("</script>");
				out.print(builder.toString());
				out.close();
			} else {
				// 如果session中存在登录者实体，则继续
//				filterChain.doFilter(new MyRequestWrappe(request), response);
//				String contentType = request.getContentType();// 获取请求的content-type
//				if (contentType != null && contentType.contains("multipart/form-data")) {
//					MultipartHttpServletRequest multiReq = multipartResolver.resolveMultipart(request);
//					filterChain.doFilter(new MyRequestWrappe(multiReq), response);
//				} else {
//					filterChain.doFilter(new MyRequestWrappe(request), response);
//				}
				filterChain.doFilter(new MyRequestWrappe(request), response);
			}
		} else {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}

	}
	
	private class MyRequestWrappe extends HttpServletRequestWrapper {

		public MyRequestWrappe(HttpServletRequest request) {
			super(request);
		}

		protected Map<String, String[]> parameters = new HashMap<String, String[]>();

		@Override
		public String getParameter(String name) {
			if (parameters.containsKey(name)) {
				String[] values = parameters.get(name);
				if (values != null && values.length > 0) {
					return values[0];
				}
			}
			String value = super.getParameter(name);
			if (value != null && !value.trim().equals("(null)")) {
				value = htmlEncode(value);
			}
			parameters.put(value, new String[] { value });
			return value;
		}

		@Override
		public String[] getParameterValues(String name) {
			if (parameters.containsKey(name)) {
				String[] values = parameters.get(name);
				return values;
			}
			String[] values = super.getParameterValues(name);
			if (values == null) {
				return null;
			}
			for (int i = 0; i < values.length; i++) {
				values[i] = htmlEncode(values[i]);
			}
			parameters.put(name, values);
			return values;
		}
	}

	public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
            case '<':
                buffer.append("&lt;");
                break;
            case '>':
                buffer.append("&gt;");
                break;
            case '&':
                buffer.append("&amp;");
                break;
            case '"':
                buffer.append("&quot;");
                break;
            case 10:
            case 13:
                break;
            default:
                buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }
	
	
}
