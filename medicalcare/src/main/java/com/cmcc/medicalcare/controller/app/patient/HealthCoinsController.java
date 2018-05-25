/**   
* @Title: DoctorsReferralController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午11:55:17 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.HealthCoins;
import com.cmcc.medicalcare.model.HealthCoinsLog;
import com.cmcc.medicalcare.model.HealthPrizesLog;
import com.cmcc.medicalcare.model.HealthTrafficCount;
import com.cmcc.medicalcare.service.IHealthCoinsLogService;
import com.cmcc.medicalcare.service.IHealthCoinsService;
import com.cmcc.medicalcare.service.IHealthPrizesLogService;
import com.cmcc.medicalcare.service.IHealthStepsService;
import com.cmcc.medicalcare.service.IHealthTrafficCountService;
import com.cmcc.medicalcare.service.IPatientUserService;

/**
 * @ClassName: HealthCoinsController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:55:17
 * 
 */
@Controller
@RequestMapping("/app/healthCoins")
public class HealthCoinsController {

	@Resource
	private IHealthStepsService healthStepsService;

	@Resource
	private IHealthCoinsService healthCoinsService;

	@Resource
	private IHealthCoinsLogService healthCoinsLogService;

	@Resource
	private IHealthPrizesLogService healthPrizesLogService;

	@Resource
	private IHealthTrafficCountService healthTrafficCountService;

	@Resource
	private IPatientUserService patientUserService;

	/**
	 * parameter={"costCoins":"","patientPhone":"","patientName":"","
	 * trafficCount":"","prizeName":""}
	 * 
	 * @Title: exchangeTraffic @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "兑换流量")
	@RequestMapping({ "exchangeTraffic" })
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> exchangeTraffic(@RequestParam String parameter, HttpServletRequest request) {
		// 更新isget=1 health_coins_log health_ prizes_log
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String patientName = dataJsonObject.getString("patientName");
		Integer costCoins = dataJsonObject.getInteger("costCoins");
		String trafficCount = dataJsonObject.getString("trafficCount");
		String prizeName = dataJsonObject.getString("prizeName");

		if (StringUtils.isNotBlank(patientPhone)) {
			HealthCoins healthCoins = healthCoinsService.findByParam("selectByPatientPhone", patientPhone);
			HealthTrafficCount healthTrafficCount = healthTrafficCountService.findByParam("selectByPatientPhone",
					patientPhone);
			if (healthCoins.getCoins() < costCoins) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("流量币不足");
				return results;
			}

			int coins = healthCoins.getCoins() - costCoins;
			healthCoins.setCoins(coins);
			healthCoins.setUpdatetime(new Date());
			healthCoinsService.update("updateByPrimaryKey", healthCoins);

			if (healthTrafficCount == null) {
				healthTrafficCount = new HealthTrafficCount();
				healthTrafficCount.setCreatetime(new Date());
				healthTrafficCount.setTrafficCount(trafficCount.trim());
				healthTrafficCount.setUserLoginid(Constant.patient + patientPhone);
				healthTrafficCount.setUserName(patientName);
				healthTrafficCount.setUserPhone(patientPhone);
				healthTrafficCountService.insert(healthTrafficCount);
			} else {
				String trafficCount2 = healthTrafficCount.getTrafficCount();
				Integer total = Integer.parseInt(trafficCount) + Integer.parseInt(trafficCount2);
				healthTrafficCount.setTrafficCount(String.valueOf(total));
				healthTrafficCount.setUpdatetime(new Date());
				healthTrafficCountService.update("updateByPrimaryKey", healthTrafficCount);
			}

			HealthCoinsLog healthCoinsLog = new HealthCoinsLog();
			healthCoinsLog.setCreatetime(new Date());
			healthCoinsLog.setDetail("兑换流量"+trafficCount+"M");
			healthCoinsLog.setGcoins(costCoins);
			healthCoinsLog.setLcoins(coins + costCoins);
			healthCoinsLog.setMcoins(coins);
			healthCoinsLog.setType(2);
			healthCoinsLog.setUserLoginid(Constant.patient + patientPhone);
			healthCoinsLog.setUserName(patientName);
			healthCoinsLog.setUserPhone(patientPhone);
			healthCoinsLogService.insert(healthCoinsLog);

			HealthPrizesLog healthPrizesLog = new HealthPrizesLog();
			healthPrizesLog.setCreatetime(new Date());
			healthPrizesLog.setGcoin(costCoins);
			healthPrizesLog.setPrice(costCoins);
			healthPrizesLog.setPrizesName(prizeName);
			healthPrizesLog.setReason(null);
			healthPrizesLog.setType(1);
			healthPrizesLog.setUserLoginid(Constant.patient + patientPhone);
			healthPrizesLog.setUserName(patientName);
			healthPrizesLog.setUserPhone(patientPhone);
			healthPrizesLogService.insert(healthPrizesLog);

			map.put("healthCoins", healthCoins);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("兑换成功");
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"pageIndex":"","pageSize":"","patientPhone":""} @Title:
	 * getExpenseDetail @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "收支明细")
	@RequestMapping({ "getExpenseDetail" })
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> getExpenseDetail(@RequestParam String parameter, HttpServletRequest request) {
		// 更新isget=1 health_coins_log health_ prizes_log
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String pageIndex = dataJsonObject.getString("pageIndex");
		String pageSize = dataJsonObject.getString("pageSize");

		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(pageSize) && StringUtils.isNotBlank(pageIndex)) {
			HealthCoins healthCoins = healthCoinsService.findByParam("selectByPatientPhone", patientPhone);
			map.put("healthCoins", healthCoins);
			HealthTrafficCount healthTrafficCount = healthTrafficCountService.findByParam("selectByPatientPhone",
					patientPhone);
			map.put("healthTrafficCount", healthTrafficCount);

			param.put("pageIndex",
					Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize));
			param.put("pageSize", Integer.parseInt(pageSize));
			param.put("patientPhone", patientPhone);
			
			
			List<HealthCoinsLog> list = healthCoinsLogService.getList("selectALLByPage", param);
			map.put("healthCoinsLogList", list);
			
			Map<String, Object> param1 = new HashMap<String, Object>();
			param1.put("patientPhone", patientPhone);
			param1.put("type", 1);//收入
			List<HealthCoinsLog> list1 = healthCoinsLogService.getList("selectByPhoneAndType", param1);
			int getCoinsTotal = 0;
			if (list1!=null&&list1.size()>0) {
				for(int i=0;i<list1.size();i++)
					getCoinsTotal = list1.get(i).getGcoins()+getCoinsTotal;
			}
			map.put("getCoinsTotal", getCoinsTotal);
			
			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("patientPhone", patientPhone);
			param2.put("type", 2);//支出
			List<HealthCoinsLog> list2 = healthCoinsLogService.getList("selectByPhoneAndType", param2);
			int costCoinsTotal=0;
			if (list2!=null&&list2.size()>0) {
				for(int k=0;k<list2.size();k++)
					costCoinsTotal = list2.get(k).getGcoins()+costCoinsTotal;
			}
			map.put("costCoinsTotal", costCoinsTotal);
			
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
}
