package com.cmcc.medicalcare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.Results;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "test1") 
	@ResponseBody 
	@SystemControllerLog(description = "查询用户")    
	public Results<String> test1(HttpServletRequest request) {
		Results<String> results = new Results<String>();
		results.setCode(1);
		results.setData("aaaaaaaa");
		results.setMessage("testtest的点点滴滴多多多多多多多多");
		String aString = "b";
		Integer.valueOf(aString);
		return results;
	}
}
