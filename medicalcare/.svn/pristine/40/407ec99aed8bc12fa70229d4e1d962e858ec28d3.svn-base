package com.paymax.utils;

import com.alibaba.fastjson.JSON;
import com.cmcc.medicalcare.aspect.RequestLogAspect;
import com.paymax.exception.AuthorizationException;
import com.paymax.exception.InvalidRequestException;
import com.paymax.exception.InvalidResponseException;
import com.paymax.model.Charge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author zhouzhou
 *
 */
public class ChargeUtils {

	private final static Log logger = LogFactory.getLog(ChargeUtils.class);

	public static void main(String[] args) {
		String charge=ChargeUtils.chargePreOrders(UUID.randomUUID().toString(), 0.01f, "测试产品", "测试产品", "wechat_app", "127.0.0.1", "测试产品");
		System.out.println("charge:"+charge);
	}

	/**
	 * 
	 * @param amount
	 * @param subject
	 * @param body
	 * @param channel
	 * @param client_ip
	 * @param description
	 * @return
	 */
	public static String chargePreOrders(String orderNo, float amount, String subject, String body, String channel,
			String client_ip, String description) {
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("amount", amount);
		chargeMap.put("subject", subject);
		chargeMap.put("body", body);
		chargeMap.put("order_no", orderNo);
		chargeMap.put("channel", channel);
		chargeMap.put("client_ip", client_ip);
		chargeMap.put("app", "app_7hqF2S6GYXET457i");
		chargeMap.put("currency", "CNY");
		chargeMap.put("description", description);
		// 请根据渠道要求确定是否需要传递extra字段
		Map<String, Object> extra = new HashMap<String, Object>();
		extra.put("user_id", "用户测试");
		chargeMap.put("extra", extra);
		Charge charge = null;
		try {
			charge = Charge.create(chargeMap);
//			System.out.println(JSON.toJSONString(charge));
			logger.info(JSON.toJSONString(charge));
		} catch (AuthorizationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (charge != null && charge.getReqSuccessFlag()) {// http请求成功
			return JSON.toJSONString(charge);
		} else {// http请求失败
			return null;
		}

	}

}
