package com.cmcc.medicalcare.controller.app.patient;

import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.SystemBanner;
import com.cmcc.medicalcare.service.ISystemBannerService;

/**
 * 软件系统配置
 * 
 * @author songhaowen
 *
 */
@Controller
@RequestMapping("/app/systemBanner")
public class SystemBannerAction extends BaseController {
	/**
	 * systemBannerService
	 */
	@Resource
	private ISystemBannerService systemBannerService;

	/**
	 * parameter={"type":"类型:1首页banner，2健康优品banner"}
	 * @param request
	 * @param parameter
	 * @return
	 */
	public @RequestMapping(value = "/bannerInfo") @ResponseBody Results<Map<String, Object>> selectBannerInfo(
			HttpServletRequest request, @RequestParam String parameter) {
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		Integer type = dataJsonObject.getInteger("type");

		if(type==null){
			type = 1;
		}
		
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<SystemBanner> bis = systemBannerService.getList("selectBannerInfo", type);
		map.put("banner", bis);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		results.setData(map);
		return results;
	}

}
