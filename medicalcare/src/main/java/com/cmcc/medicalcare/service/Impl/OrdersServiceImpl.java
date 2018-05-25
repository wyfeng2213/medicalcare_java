package com.cmcc.medicalcare.service.Impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.cache.redis.Constants;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPatientsUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PatientUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PaymentUtils;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.service.IOrdersService;
import com.easemob.server.comm.HttpToEasemobUtil;

/**
 * 
 * @author Administrator
 *
 */
@Service(value="ordersService")
@Transactional
public class OrdersServiceImpl extends GenericServiceImpl<Orders, Integer> implements IOrdersService{
	
	@Resource
	private RedisUtil redisUtil;

	@Override
	public void updateOrders(Orders orders) {
		orders.setOrdersType(6);//医生开处方
		orders.setPayType(2);
		orders.setOrdersPayState(1);
		this.update("updateByPrimaryKeySelective", orders);
		
		//给患者发通知消息
		String doctorLoginId = orders.getDoctorLoginId();
		String patientLoginId = orders.getPatientLoginId();
		String targetType = "users";
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("type", "payTip");
		String msgTxt = "订单支付成功，请赶紧咨询";
		HttpToEasemobUtil.sendTxt(doctorLoginId, patientLoginId, targetType, msgTxt, ext);
		
		//给医生发通知消息
		String msgTxt2 = "有患者订单支付成功";
		HttpToEasemobUtil.sendTxt(patientLoginId, doctorLoginId, targetType, msgTxt2, ext);
	}

	@Override
	public String saveDoctorPatient(String doctorPhone, String patientPhone) {
		String doctorId = ""; 
		String memberId = "";
		String memberPhone = "";
		String patientId = "";
		String doctorName = "";
		String patientName = "";
		
		//查询九州通医生信息
		JSONObject doctorInfo = null;
		Object doctorInfo_o = redisUtil.get(Constants.KEY_USER_DOCTOR + doctorPhone);
		if (null == doctorInfo_o) {
			boolean isExist_doctor = false;
			String jsonStr = DoctorUserUtils.findDoctor("", doctorPhone, "", "", "");
			if (jsonStr != null && !"".equals(jsonStr)) {
				JSONObject json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					JSONObject jsonObject= json.getJSONObject("data");
					JSONArray jsonArray = jsonObject.getJSONArray("doctors");
					if (jsonArray != null && jsonArray.size() > 0) {
						doctorInfo = (JSONObject) jsonArray.get(0);
						isExist_doctor = true;
					}
				}
			}
			if (!isExist_doctor) {
				return "不存在医生";
			}
		} else {
			doctorInfo = (JSONObject) doctorInfo_o;
		}
		doctorId = doctorInfo.getString("doctorId");
		doctorName = doctorInfo.getString("name");
		
		//查询九州通会员信息
		JSONObject memberInfo = null;
		Object memberInfo_o = redisUtil.get(Constants.KEY_USER_MEMBER + patientPhone);
		if (null == memberInfo_o) {
			boolean isExist_member = false;
			String jsonStr = MemberUserUtils.findMember(patientPhone, "", "");
			if (jsonStr != null) {
				JSONObject json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					JSONObject jsonObject= json.getJSONObject("data");
					JSONArray jsonArray = jsonObject.getJSONArray("members");
					if (jsonArray != null && jsonArray.size() > 0) {
						memberInfo = (JSONObject) jsonArray.get(0);
						isExist_member = true;
					}
				}
			}
			if (!isExist_member) {
				return "不存在会员";
			}
		} else {
			memberInfo = (JSONObject) memberInfo_o;
		}
		memberId = memberInfo.getString("memberId");
		memberPhone = memberInfo.getString("phone");
		
		//查询九州通患者信息
		JSONObject patientUser = null;
		Object patientUser_o = redisUtil.get(Constants.KEY_USER_PATIENT + patientPhone);
		if (null == patientUser_o) {
			boolean isExist_patient = false;
			String jsonStr = PatientUserUtils.findPatient("", memberId);
			if (jsonStr != null) {
				JSONObject json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					JSONArray jsonArray = json.getJSONArray("data");
					if (jsonArray != null && jsonArray.size() > 0) {
						patientUser = (JSONObject) jsonArray.get(0);
						isExist_patient = true;
					}
				}
			}
			if (!isExist_patient) {
				return "不存在患者";
			}
		} else {
			patientUser = (JSONObject) patientUser_o;
		}
		patientId = patientUser.getString("patientId");
		patientName = patientUser.getString("name");
		
		//查询医患关系
		boolean isExist_doctorPatients = false;
		String jsonStr = DoctorPatientsUtils.findDoctorPatient(doctorId, memberId, patientId, "", "");
		if (jsonStr != null) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONObject jsonObject= json.getJSONObject("data");
				JSONArray jsonArray = jsonObject.getJSONArray("doctorPatients");
				if (jsonArray != null && jsonArray.size() > 0) {
					memberInfo = (JSONObject) jsonArray.get(0);
					isExist_doctorPatients = true;
				}
			}
		}
		if (!isExist_doctorPatients) {
			//创建医患关系
			String jsonStr2 = DoctorPatientsUtils.saveDoctorPatient(doctorId, doctorName, doctorPhone, memberId,memberPhone,patientId, patientName, patientPhone, "");
		}
		
		return "创建医患关系成功";
	}

	//创建充值订单
	@Override
	public JSONObject createPaymentOrders(String orderNo, String amount, String subject, String body, String detail, String attach) {
		JSONObject charge = new JSONObject();
		JSONObject credential = new JSONObject();
		JSONObject wechat_app = new JSONObject();
		String jsonStr = PaymentUtils.wxPayment(orderNo, amount, subject, body, detail, attach);
		JSONObject json = JSONObject.parseObject(jsonStr);
		String status = json.getString("status");
		if ("200".equals(status)) {
			JSONObject wechat_json = json.getJSONObject("data");
			if (null == wechat_json) {
				return null;
			}
			
			wechat_app.put("sign", wechat_json.getString("sign"));
			wechat_app.put("timestamp", wechat_json.getString("timestamp"));
			wechat_app.put("noncestr", wechat_json.getString("noncestr"));
			wechat_app.put("partnerid", wechat_json.getString("partnerid"));
			wechat_app.put("prepayid", wechat_json.getString("prepayid"));
			wechat_app.put("package", wechat_json.getString("package"));
			wechat_app.put("appid", wechat_json.getString("appid"));
			
			credential.put("wechat_app", wechat_app);
			
			charge.put("credential", credential);
			charge.put("app", "");//
			charge.put("body", body);
			charge.put("status", "PROCESSING");//
			charge.put("subject", subject);
			charge.put("time_created", "");//
			charge.put("client_ip", "127.0.0.1");//
			charge.put("order_no", orderNo);
			charge.put("currency", "CNY");//
			charge.put("time_expire", "");//
			charge.put("amount", amount);
			charge.put("id", "");//
			charge.put("refunded", false);//
			charge.put("description", "description");//
			charge.put("refunds", "");//
			charge.put("livemode", false);//
			charge.put("channel", "wechat_app");
			charge.put("amount_settle", 0);
			
			return charge;
		} else {
			return null;
		}
	}

}
