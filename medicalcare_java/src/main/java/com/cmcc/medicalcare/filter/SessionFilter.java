package com.cmcc.medicalcare.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 登录过滤
 * 
 */
public class SessionFilter extends OncePerRequestFilter {

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
		String[] filterUrls = new String[] { "toPush.htm", "toUpload.htm",
				"list2.htm", "toList.htm", "toAdd.htm", "uploadPackage.jsp",
				"/pav/add.htm", "/userGoto.htm", "/msg/add2.htm", "/msg/toPush.htm", 
				"/msg/list2.htm","/exception/toList.htm","/systemUser2/logout.htm",
				"/exception/toUpdate.htm","/msg/sendMsg.htm","/pav/add.htm",
				"/patchVersion/add.htm","/Pc/add.htm","/msg/update2.htm",
				"/msg/list2.htm"};

		// 请求的uri
		String uri = request.getRequestURI();
//		 System.out.println("=====================SessionFilter:"+uri);
		// 是否过滤
		boolean doFilter = false;

		for (String s : filterUrls) {
			if (uri.indexOf(s) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = true;
				break;
			}
		}
		//
		// for (String s : notFilterUrls) {
		// if (uri.indexOf(s) != -1 || "//".equals(uri) || "\\".equals(uri)||
		// "/".equals(uri)) {
		// // 如果uri中包含不过滤的uri，则不进行过滤
		// doFilter = false;
		// break;
		// }
		// }

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
				PrintWriter out = response.getWriter();
				String loginPage = request.getContextPath() + "/login.jsp";
				StringBuilder builder = new StringBuilder();
				builder.append("<script type=\"text/javascript\">");
				builder.append("alert('网页过期，请重新登录！');");
				builder.append("window.top.location.href='");
				builder.append(loginPage);
				builder.append("';");
				builder.append("</script>");
				out.print(builder.toString());
			} else {
				// 如果session中存在登录者实体，则继续
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
				value = StringEscapeUtils.escapeHtml(value);
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
				values[i] = StringEscapeUtils.escapeHtml(values[i]);
			}
			parameters.put(name, values);
			return values;
		}
	}

}
