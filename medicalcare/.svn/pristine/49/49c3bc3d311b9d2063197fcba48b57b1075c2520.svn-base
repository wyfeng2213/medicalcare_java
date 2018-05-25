package com.cmcc.medicalcare.controller.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.BaseController;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.ScienceArticle;
import com.cmcc.medicalcare.model.ScienceColumn;
import com.cmcc.medicalcare.model.SystemBanner;
import com.cmcc.medicalcare.service.IScienceArticleService;
import com.cmcc.medicalcare.service.IScienceColumnService;

/**
 * 
 * @author zhouzhou
 *
 */
@Controller
@RequestMapping("/sys/scienceColumn")
public class SystemScienceColumnController extends BaseController {

	@Resource
	private IScienceColumnService scienceColumnService;

	@Resource
	private IScienceArticleService scienceArticleService;

	private int scienceColumnCountMax = 4;

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@SystemControllerLog(description = "管理页面")
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String manager() {
		return "/sys/science/scienceColumn";
	}

	/**
	 * 用户管理页
	 *
	 * @return
	 */
	@RequestMapping(value = "/findUserPage")
	@ResponseBody
	@SystemControllerLog(description = "查找健康科普标题栏信息")
	public Map<String, Object> findUserPage(HttpServletResponse response, Integer onOff, String keyword,
			@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (StringUtils.isNotBlank(keyword)) {
				// java : 字符解码
				keyword = java.net.URLDecoder.decode(keyword, "UTF-8");
				map.put("keyword", keyword);
				Pager<ScienceColumn> scienceColumnPage = scienceColumnService.getList("selectKeyWordBySys",
						getPageIndex(page), getPageSize(rows), map);
				map.put("total", scienceColumnPage.getTotalCount());
				map.put("rows", scienceColumnPage.getList());
			} else {
				map.put("onOff", onOff);
				Pager<ScienceColumn> scienceColumnPage = scienceColumnService.getList("selectOnOffBySys",
						getPageIndex(page), getPageSize(rows), map);
				map.put("total", scienceColumnPage.getTotalCount());
				map.put("rows", scienceColumnPage.getList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("total", 0);
			map.put("rows", "");
		}

		return map;
	}

	/**
	 * 启用显示
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/show")
	@ResponseBody
	@SystemControllerLog(description = "健康科普启用显示标题")
	public Results<Map<String, Object>> show(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {

			List<ScienceColumn> list = scienceColumnService.getList("getOn", null);

			if (list != null && list.size() > 0 && (scienceColumnCountMax <= list.size())) {
				result.setCode(MessageCode.CODE_404);
				result.setMessage("启用显示标题不能超过四个!");
				return result;
			}

			ScienceColumn scienceColumn = scienceColumnService.findById("selectByPrimaryKey", id);
			scienceColumn.setOnOff(1);
			scienceColumn.setUpdatetime(new Date());
			if (list == null || list.size() == 0) {
				scienceColumn.setSequence(1);
			}
			scienceColumnService.update("updateByPrimaryKey", scienceColumn);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}

	/**
	 * 启用隐藏
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/hide")
	@ResponseBody
	@SystemControllerLog(description = "健康科普启用隐藏标题")
	public Results<Map<String, Object>> hide(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			ScienceColumn scienceColumn = scienceColumnService.findById("selectByPrimaryKey", id);
			scienceColumn.setOnOff(0);
			scienceColumn.setSequence(null);
			scienceColumn.setUpdatetime(new Date());
			scienceColumnService.update("updateByPrimaryKey", scienceColumn);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}

	@RequestMapping("/editPage")
	public String editPage(int id, Model model) {
		try {
			ScienceColumn scienceColumn = scienceColumnService.findById("selectByPrimaryKey", id);
			model.addAttribute("scienceColumn", scienceColumn);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/sys/science/scienceColumnEdit";
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Results<Map<String, Object>> edit(ScienceColumn scienceColumn_, HttpServletRequest request) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			ScienceColumn scienceColumn = new ScienceColumn();
			scienceColumn.setId(scienceColumn_.getId());
			scienceColumn.setColumnHeading(scienceColumn_.getColumnHeading());
			scienceColumn.setOnOff(scienceColumn_.getOnOff());
			scienceColumn.setSequence(scienceColumn_.getSequence());
			scienceColumn.setUpdatetime(new Date());
			scienceColumnService.update("updateByPrimaryKeySelective", scienceColumn);

			System.out.println("---------------成功------");

			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (RuntimeException e) {
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}

	@RequestMapping("/addPage")
	public String addPage() {
		return "/sys/science/scienceColumnAdd";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "新增标题栏")
	public Results<Map<String, Object>> add(HttpServletRequest request, ScienceColumn scienceColumn_) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();

		ScienceColumn scienceColumn = scienceColumnService.findByParam("selectByColumnHeading",
				scienceColumn_.getColumnHeading());

		if (scienceColumn != null) {
			results.setCode(MessageCode.CODE_405);
			results.setMessage(MessageCode.MESSAGE_405);
			return results;
		}

		try {
			scienceColumn = new ScienceColumn();
			scienceColumn.setColumnHeading(scienceColumn_.getColumnHeading());
			scienceColumn.setCreatetime(new Date());
			scienceColumn.setOnOff(0);
			scienceColumn.setSequence(scienceColumn_.getSequence());
			scienceColumnService.insert(scienceColumn);

			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);

			return results;
		} catch (Exception e) {
			// TODO: handle exception
			results.setCode(MessageCode.CODE_404);
			results.setMessage(e.getMessage());
			return results;
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "删除标题栏")
	public Results<Map<String, Object>> delete(HttpServletRequest request, int id) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();

		ScienceColumn scienceColumn = scienceColumnService.findByParam("selectByPrimaryKey", id);

		if (scienceColumn == null) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage(MessageCode.MESSAGE_404);
			return results;
		}

		try {

			List<ScienceArticle> scienceArticleList = scienceArticleService.getList("getByColumnId", id);
			if (scienceArticleList!=null&&scienceArticleList.size()>0) {
				for (ScienceArticle scienceArticle : scienceArticleList) {
					scienceArticleService.delete("deleteByPrimaryKey", scienceArticle);
				}
			}
			
			scienceColumnService.delete("deleteByPrimaryKey", scienceColumn);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);

			return results;
		} catch (Exception e) {
			// TODO: handle exception
			results.setCode(MessageCode.CODE_404);
			results.setMessage(e.getMessage());
			return results;
		}
	}
	
	
	@RequestMapping("/reject")
	@ResponseBody
	@SystemControllerLog(description = "拒绝审核科普标题栏")	
	public Results<Map<String, Object>> reject(int id) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			ScienceColumn scienceColumn = scienceColumnService.findById("selectByPrimaryKey", id);
			scienceColumn.setFlag(0);
			scienceColumn.setUpdatetime(new Date());
			scienceColumnService.update("updateByPrimaryKey", scienceColumn);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	
	@RequestMapping("/pass")
	@ResponseBody
	@SystemControllerLog(description = "通过审核科普标题栏")
	public Results<Map<String, Object>> pass(int id, Model model) {
		Results<Map<String, Object>> result = new Results<Map<String, Object>>();
		try {
			ScienceColumn scienceColumn = scienceColumnService.findById("selectByPrimaryKey", id);
			scienceColumn.setFlag(1);
			scienceColumn.setUpdatetime(new Date());
			scienceColumnService.update("updateByPrimaryKey", scienceColumn);
			result.setCode(MessageCode.CODE_200);
			result.setMessage("成功");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(MessageCode.CODE_404);
			result.setMessage(e.getMessage());
			return result;
		}
	}
}
