package com.cmcc.medicalcare.controller.app.patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.HealthyProducts;
import com.cmcc.medicalcare.service.IHealthyProductsService;

@Controller
@RequestMapping("/app/healthyProducts")
public class HealthyProductsController {
	
	@Resource
	private IHealthyProductsService healthyProductsService;
	
	/**
	 * 查询健康优品记录
	 * 
	 * parameter={"type":"类型:0健康自测与体验套餐，1健康自测，2体验套餐"}
	 * 
	 */
	@RequestMapping(value = "selectHealthyProducts")
	@ResponseBody
	@SystemControllerLog(description = "查询健康优品记录")
	public Results<Map<String, Object>> selectHealthyProducts(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		Integer type = dataJsonObject.getInteger("type"); 
		if (type != null) {
			if (1 == type) {
				List<HealthyProducts> healthyProductsList_jkzc = healthyProductsService.getList("selectByType", 1);
				map.put("healthyProductsList_jkzc", healthyProductsList_jkzc);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else if (2 == type) {
				List<HealthyProducts> healthyProductsList_tytc = healthyProductsService.getList("selectByType", 2);
				map.put("healthyProductsList_tytc", healthyProductsList_tytc);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				List<HealthyProducts> healthyProductsList_jkzc = healthyProductsService.getList("selectByType", 1);
				map.put("healthyProductsList_jkzc", healthyProductsList_jkzc);
				List<HealthyProducts> healthyProductsList_tytc = healthyProductsService.getList("selectByType", 2);
				map.put("healthyProductsList_tytc", healthyProductsList_tytc);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
	}
	
}
