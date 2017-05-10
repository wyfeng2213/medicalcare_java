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

import com.cmcc.medicalcare.utils.DES;

/**
 * 加密解密过滤器
 * @author zhouzhou
 *
 */
public class DecryptFilter  extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(DecryptFilter.class);
	private DES des = new DES();
	
	
	/**
	 * 过滤方法
	 * @param request
	 * @param name
	 * @param input
	 * @return
	 */
	public String filter(HttpServletRequest request, String name, String input) {
        String ret = input;
        if (input == null || input.trim().equals("(null)")) {
            return null;
        }
        try {
            ret = des.decryptBeas64DES(input);
        } catch (Exception e) {
//        	logger.error(request.getRequestURI() + " error decode " + name + "=" + input, e);
        }
        return injectSQLFilter(name, ret);
    }
	
	/**
	 * 防止sql、html注入
	 * @param name
	 * @param text
	 * @return
	 */
	public String injectSQLFilter(String name, String text){
        if(text==null){
            return null;
        }
//        if(name.matches("password|passwd|loginPassword|dealPassword")){
//            return text;
//        }
        //TODO 这里处理SQL注入，直接处理掉注入数据,或者下层拦截器处理
//        text = StringEscapeUtils.escapeHtml(text);
        text = StringEscapeUtils.escapeSql(text);
        return text;
    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		System.out.println("=====================DecryptFilter");
		filterChain.doFilter(new HttpServletRequestWrapper(request) {

            protected Map<String, String[]> parameters = new HashMap<String, String[]>();
            
            @Override
            public String getParameter(String name) {
                if (parameters.containsKey(name)){
                    String[] values = parameters.get(name);
                    if (values!=null && values.length>0){
                        return values[0];
                    }
                }
                String value = super.getParameter(name);
                String retValue = filter(this, name, value);
                parameters.put(retValue, new String[]{retValue});
                return retValue;
            }

            @Override
            public String[] getParameterValues(String name) {
                if (parameters.containsKey(name)){
                    String[] values = parameters.get(name);
                    return values;
                }
                String[] values = super.getParameterValues(name);
                if (values == null) {
                    return null;
                }
                for (int i = 0; i < values.length; i++) {
                    values[i] = filter(this, name, values[i]);
                }
                parameters.put(name, values);
                return values;
            }

        }, response);

    }
}
