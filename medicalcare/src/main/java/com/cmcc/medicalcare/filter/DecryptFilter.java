package com.cmcc.medicalcare.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cmcc.medicalcare.utils.DES;

/**
 * 加密解密过滤器
 * @author zhouzhou
 *
 */
public class DecryptFilter  extends OncePerRequestFilter {

	private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String contentType = request.getContentType();// 获取请求的content-type
		if (contentType != null && contentType.contains("multipart/form-data")) {
			MultipartHttpServletRequest multiReq = multipartResolver.resolveMultipart(request);
			filterChain.doFilter(new DesHttpRequestWapper(multiReq), response);
		} else {
			filterChain.doFilter(new DesHttpRequestWapper(request), response);
		}
    }
}
