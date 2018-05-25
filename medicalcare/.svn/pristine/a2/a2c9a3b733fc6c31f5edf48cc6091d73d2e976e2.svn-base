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
import com.cmcc.medicalcare.model.ScienceArticle;
import com.cmcc.medicalcare.model.ScienceColumn;
import com.cmcc.medicalcare.service.IScienceArticleService;
import com.cmcc.medicalcare.service.IScienceColumnService;

/**
 * 
 * @author wsm
 *
 */
@Controller
@RequestMapping("/app/science")
public class ScienceController {

	@Resource
	private IScienceColumnService scienceColumnService;

	@Resource
	private IScienceArticleService scienceArticleService;

	@RequestMapping(value = "selectScinece")
	@ResponseBody
	@SystemControllerLog(description = "获取健康科普信息")
	public Results<Map<String, Object>> selectScinece(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<ScienceColumn> list = scienceColumnService.getList("getOn", null);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				List<ScienceArticle> list2 = scienceArticleService.getList("getByColumnId", list.get(i).getId());
				list.get(i).setScienceArticleList(list2);
			}
			map.put("list", list);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(map);

		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage(MessageCode.MESSAGE_204);
		}

		return results;

	}
}
