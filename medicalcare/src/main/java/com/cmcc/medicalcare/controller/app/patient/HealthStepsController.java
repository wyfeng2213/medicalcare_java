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
import com.cmcc.medicalcare.model.HealthSteps;
import com.cmcc.medicalcare.model.MessageReminding;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IHealthCoinsLogService;
import com.cmcc.medicalcare.service.IHealthCoinsService;
import com.cmcc.medicalcare.service.IHealthStepsService;
import com.cmcc.medicalcare.service.IMessageRemindingService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.StepsToCoinsUtils;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: HealthCoinsController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:55:17
 * 
 */
@Controller
@RequestMapping("/app/healthSteps")
public class HealthStepsController {

	@Resource
	private IHealthStepsService healthStepsService;

	@Resource
	private IHealthCoinsService healthCoinsService;

	@Resource
	private IHealthCoinsLogService healthCoinsLogService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IMessageRemindingService messageRemindingService;

	/**
	 * parameter={"steps":"","patientPhone":"","patientName":"","
	 * type":""} @Title: updateSteps @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "更新步数")
	@RequestMapping({ "updateSteps" })
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> updateSteps(@RequestParam String parameter, HttpServletRequest request) {
		// 每隔一段时间上传步数,拿当天总步数兑换健康币StepsToCoinsUtils.getCoinsFromSteps(),isget=0
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		Integer steps = dataJsonObject.getInteger("steps");
		String patientPhone = dataJsonObject.getString("patientPhone");
		String patientName = dataJsonObject.getString("patientName");
		Integer type = dataJsonObject.getInteger("type");// type 1 steps为增量
															// ,2 steps为总步数
		if (StringUtils.isNotBlank(patientName) && StringUtils.isNotBlank(patientPhone) && type != null) {

			// 获取该号码当天数据
			HealthSteps healthSteps = healthStepsService.findByParam("selectTodayByPatientPhone", patientPhone);

			// List<HealthSteps> list =
			// healthStepsService.getList("selectByPatientPhone", patientPhone);
			if (healthSteps == null) {
				healthSteps = new HealthSteps();
				healthSteps.setCreatetime(new Date());
				healthSteps.setIsget(0);
				healthSteps.setSteps(steps);
				healthSteps.setUserLoginid(Constant.patient + patientPhone);
				healthSteps.setUserName(patientName);
				healthSteps.setUserPhone(patientPhone);
				Integer coins=0 ;
				if (steps>0) {
					 coins = StepsToCoinsUtils.getCoinsFromSteps(steps);
					 if (coins>0) {
//						 MessageReminding messageReminding = new MessageReminding();
//						 messageReminding.setCreatetime(new Date());
//						 messageReminding.setMessageButton(Constant.messageButton);
//						 messageReminding.setMessageContent("您有"+coins+"个流量币可以领取哦！");
//						 messageReminding.setMessageName("健康走");
//						 messageReminding.setMessageStatus(0);
//						 messageReminding.setMessageType(2);
//						 messageReminding.setPatientLoginId(Constant.patient+patientPhone);
//						 messageReminding.setPatientName(patientName);
//						 messageReminding.setPatientPhone(patientPhone);
//						 messageRemindingService.insert(messageReminding);
						 
//						 EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//							Msg msg = new Msg();
//							MsgContent msgContent = new MsgContent();
//							
//							Map<String, Object> ext = new HashMap<String, Object>();
//							ext.put("messageType", "messageReminding");
//							
//							// 获取未读消息数
//							List<MessageReminding> messageRemindingList = messageRemindingService.getList("getUnreadMessage", patientPhone);
//							
//							ext.put("UnreadMessageSize", messageRemindingList.size());
//							msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
//							UserName userName = new UserName();
//							userName.add(Constant.patient+patientPhone);
//							msg.from(Constant.SYSTEM).target(userName).targetType("users").msg(msgContent).ext(ext);
//							easemobSendMessage.sendMessage(msg);
					}
				}
				healthSteps.setCoins(coins);

				healthStepsService.insert(healthSteps);
				map.put("healthSteps", healthSteps);

				results.setData(map);
				results.setMessage(MessageCode.MESSAGE_200);
				results.setCode(MessageCode.CODE_200);
				return results;
			}

			if (type == 1) {
				Integer steps2 = healthSteps.getSteps();
				steps = steps2 + steps;
				Integer coins=0 ;
				if (steps>0) {
					 coins = StepsToCoinsUtils.getCoinsFromSteps(steps);
				}
				healthSteps.setCoins(coins);
				healthSteps.setSteps(steps);
				healthSteps.setUpdatetime(new Date());
				healthStepsService.update("updateByPrimaryKey", healthSteps);
				map.put("healthSteps", healthSteps);

			} else {// 2
				Integer coins = StepsToCoinsUtils.getCoinsFromSteps(steps);
				healthSteps.setCoins(coins);
				healthSteps.setSteps(steps);
				healthSteps.setUpdatetime(new Date());
				healthStepsService.update("updateByPrimaryKey", healthSteps);
				map.put("healthSteps", healthSteps);
			}
			results.setData(map);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setCode(MessageCode.CODE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: stepsAndCoinsMian @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "运动赚流量主界面")
	@RequestMapping({ "stepsAndCoinsMian" })
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> stepsAndCoinsMian(@RequestParam String parameter, HttpServletRequest request) {
		// 更新isget=1
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");

		if (StringUtils.isNotBlank(patientPhone)) {

			HealthCoins healthCoins = healthCoinsService.findByParam("selectByPatientPhone", patientPhone);
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			// 获取该号码昨天数据
			HealthSteps healthSteps = healthStepsService.findByParam("selectYesterTodayByPhone", patientPhone);
			
			map.put("healthCoins", healthCoins);
			map.put("patientUser", patientUser);
			map.put("healthSteps", healthSteps);
			results.setData(map);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setCode(MessageCode.CODE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"patientPhone":"","patientName":""}
	 * 
	 * @Title: getCoins @Description: TODO @param @param parameter @param @param
	 *         request @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@SystemControllerLog(description = "领取流量币")
	@RequestMapping({ "recevingCoins" })
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> recevingCoins(@RequestParam String parameter, HttpServletRequest request) {
		// 更新isget=1
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String patientName = dataJsonObject.getString("patientName");

		if (StringUtils.isNotBlank(patientPhone) && StringUtils.isNotBlank(patientName)) {

			// 获取该号码昨天数据
			HealthSteps healthSteps = healthStepsService.findByParam("selectYesterTodayByPhone", patientPhone);
			HealthCoins healthCoins = healthCoinsService.findByParam("selectByPatientPhone", patientPhone);

			if (healthSteps == null) {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("没有昨天的流量币");
				return results;
			}
			
			if (healthSteps!=null&&healthSteps.getIsget()==1) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			}

			Integer coins = healthSteps.getCoins();
			healthSteps.setIsget(1);
			healthSteps.setUpdatetime(new Date());
			healthStepsService.update("updateByPrimaryKey", healthSteps);

			if (healthCoins == null) {
				healthCoins = new HealthCoins();
				healthCoins.setUserLoginid(Constant.patient + patientPhone);
				healthCoins.setUserName(patientName);
				healthCoins.setUserPhone(patientPhone);
				healthCoins.setCreatetime(new Date());
				healthCoins.setCoins(coins);
				healthCoinsService.insert(healthCoins);

				HealthCoinsLog healthCoinsLog = new HealthCoinsLog();
				healthCoinsLog.setCreatetime(new Date());
				healthCoinsLog.setDetail("行走了"+healthSteps.getSteps()+"步");
				healthCoinsLog.setGcoins(coins);
				healthCoinsLog.setLcoins(0);
				healthCoinsLog.setMcoins(coins);
				healthCoinsLog.setType(1);
				healthCoinsLog.setUserLoginid(Constant.patient + patientPhone);
				healthCoinsLog.setUserName(patientName);
				healthCoinsLog.setUserPhone(patientPhone);
				healthCoinsLogService.insert(healthCoinsLog);
			} else {
				Integer coins2 = healthCoins.getCoins();
				coins2 = coins2 + coins;
				healthCoins.setCoins(coins2);
				healthCoins.setUpdatetime(new Date());
				healthCoinsService.update("updateByPrimaryKey", healthCoins);

				HealthCoinsLog healthCoinsLog = new HealthCoinsLog();
				healthCoinsLog.setCreatetime(new Date());
				healthCoinsLog.setDetail("行走了"+healthSteps.getSteps()+"步");
				healthCoinsLog.setGcoins(coins);
				healthCoinsLog.setLcoins(coins2 - coins);
				healthCoinsLog.setMcoins(coins2);
				healthCoinsLog.setType(1);
				healthCoinsLog.setUserLoginid(Constant.patient + patientPhone);
				healthCoinsLog.setUserName(patientName);
				healthCoinsLog.setUserPhone(patientPhone);
				healthCoinsLogService.insert(healthCoinsLog);
			}

			map.put("healthCoins", healthCoins);
			results.setData(map);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setCode(MessageCode.CODE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
}
