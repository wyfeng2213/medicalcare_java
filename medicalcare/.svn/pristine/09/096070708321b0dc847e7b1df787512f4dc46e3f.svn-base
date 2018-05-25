package com.cmcc.medicalcare.filter;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

import com.cmcc.medicalcare.utils.DES;

public class DesHttpRequestWapper extends HttpServletRequestWrapper {
	
	public DesHttpRequestWapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		if (parameters.containsKey(name)) {
			String[] values = parameters.get(name);
			if (values != null && values.length > 0) {
				return values[0];
			}
		}
		String value = super.getParameter(name);
		String retValue = filter(this, name, value);
		parameters.put(retValue, new String[] { retValue });
		return retValue;
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
			values[i] = filter(this, name, values[i]);
		}
		parameters.put(name, values);
		return values;
	}

	/**
	 * 过滤方法
	 * 
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
			ret = DES.decryptBeas64DES(input);
		} catch (Exception e) {
//			logger.error(request.getRequestURI() + " error decode " + name + "=" + input, e);
		}
		return injectSQLFilter(name, ret);
	}

	/**
	 * 防止sql、html注入
	 * 
	 * @param name
	 * @param text
	 * @return
	 */
	public String injectSQLFilter(String name, String text) {
		if (text == null) {
			return null;
		}
		text = StringEscapeUtils.escapeSql(text);
		return text;
	}

	protected Map<String, String[]> parameters = new HashMap<String, String[]>();
}
