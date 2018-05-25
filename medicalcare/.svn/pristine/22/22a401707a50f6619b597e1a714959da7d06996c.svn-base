package com.cmcc.medicalcare.controller.app.patient.v2;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.common.Utils;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPatientsUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PaymentUtils;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IInquiryMessageLogService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;
import com.paymax.utils.ChargeUtils;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: 订单
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/app/orders/v2")
public class OrdersV2Controller {

	@Resource
	private IOrdersService ordersService;
	
	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IInquiryMessageLogService inquiryMessageLogService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IMediaLogService mediaLogService;
	
	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;

	/**
	 * parameter={"ordersId":"订单id"} @Title:
	 * chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "selectOrdersInfo")
	@ResponseBody
	@SystemControllerLog(description = "查询订单信息")
	public Results<Map<String, Object>> selectOrdersInfo(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (ordersId != null) {
			Orders orders = ordersService.findByParam("selectByPrimaryKey", ordersId);
			if (orders != null) {
				map.put("orders", orders);
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_204);
				results.setMessage("没有数据");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}
	
	/**
	 * parameter={"patientLoginId":"患者loginId","patientName":"患者姓名","symptom":"症状","ordersPrice":"订单金额","payType":"支付方式：1支付宝支付，2微信支付，3银行卡支付，4其他方式支付"} @Title:
	 * chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "提交快速问诊订单")
	public Results<Map<String, Object>> insert(HttpServletRequest request, @RequestParam String parameter, MultipartFile[] files) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		//************************************************multipart请求特殊过滤处理******************************************
//		if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		//************************************************multipart请求特殊过滤处理******************************************
		if (files!=null&&files.length>0) {
			for (MultipartFile f : files) {
				if(!FileFilterUtils.isMediaFileType(f.getOriginalFilename())){
					results.setCode(MessageCode.CODE_404);
					results.setMessage("不支持该文件格式上传");
					return results;
				}
			}
		}
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		String symptom = dataJsonObject.getString("symptom");
		SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(symptom);
		if (sensitiveWord != null && sensitiveWord.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
			sensitiveRecordLog.setText(sensitiveWord.getText());
			sensitiveRecordLog.setUserLoginid(patientLoginId);
			sensitiveRecordLog.setUserPhone(patientLoginId.substring(patientLoginId.indexOf("_") + 1));
			sensitiveRecordLog.setType(sensitiveWord.getType());
			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);

			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
			if (dataJsonObject2 != null) {
				String userName = dataJsonObject2.getString("userName");
				sensitiveRecordLog.setUserName(userName);
			}

			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("您输入的症状包含敏感词，提交失败！");
			return results;
		}
		
		String ordersPrice = dataJsonObject.getString("ordersPrice");
		Integer payType = dataJsonObject.getInteger("payType");
		if (StringUtils.isNotBlank(patientLoginId)) {
//			String filetempUrls = "";
//			String urls = "";
//			for (int i=0; i<files.length; i++) {
//				String filetempUrl = UploadFilesUtils.cacheFile(request, files[i], patientLoginId+"_"); // 保存临时文件
//    			File file = new File(filetempUrl);
//    			String fileUrl = UploadFilesUtils.formUploadFileToServer(file); // 保存远程文件
//    			filetempUrls += "," + filetempUrl;
//    			urls += "," + fileUrl;
//			}
//			if (StringUtils.isNotBlank(filetempUrls)) {
//				filetempUrls = filetempUrls.trim();
//				filetempUrls = filetempUrls.substring(1, filetempUrls.length());
//			}
//			if (StringUtils.isNotBlank(urls)) {
//				urls = urls.trim();
//				urls = urls.substring(1, urls.length());
//			}
			
			UserInfo userInfo = new UserInfo();
			userInfo.setUserLoginId(patientLoginId);
			userInfo.setUserName(patientName);
			userInfo.setUserPhone(patientLoginId.substring(patientLoginId.lastIndexOf("_")+1));
			String urls = UploadFilesUtils.saveFiles(mediaLogService, userInfo,request, files, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_"); //保存文件
			
//			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientLoginId.split("_")[1]);
			
			//保存订单
			Orders orders = new Orders();
			orders.setOrdersTitle("快速问诊服务");
			orders.setPatientLoginId(patientLoginId);
			orders.setPatientName(patientName);
			orders.setSymptom(symptom);
			if (StringUtils.isNotBlank(urls))orders.setImageUrl(urls);
			orders.setPayType(payType);
			orders.setOrdersPrice(ordersPrice);
			orders.setOrdersType(2);
			orders.setCreatetime(new Date());
			ordersService.insert(orders);
			
			//随机指定医生
			DoctorsUser doctorsUser_ = doctorsUserService.findByParam("getMaxDoctorsUserId", "");
	        int random_id = new Random().nextInt(doctorsUser_.getId());
	        DoctorsUser doctorsUser = doctorsUserService.findByParam("getDoctorUserByRandomId", random_id);
	        
	        //更新订单信息
	        orders.setDoctorLoginId(doctorsUser.getLoginId());
			orders.setDoctorName(doctorsUser.getName());
			orders.setDoctorPhone(doctorsUser.getPhone());
			ordersService.update("updateByPrimaryKeySelective", orders);
	        
	        /**
	         * 给该医生发送症状信息,并保持问诊记录
	         */
	        if (StringUtils.isNotBlank(symptom)) {
//	        	Map<String, Object> ext = new HashMap<String, Object>();
//	    		if (patientName != null) ext.put("nickName", patientName);
//	    		if (patientUser.getHeadUrl() != null) ext.put("headUrl", patientUser.getHeadUrl());
//	    		ext.put("type", "inquiry");
//	    		
//	    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//    			Msg msg = new Msg();
//    			MsgContent msgContent = new MsgContent();
//
//    			msgContent.type(MsgContent.TypeEnum.TXT).msg(symptom);
//    			UserName userName = new UserName();
//    			userName.add(doctorsUser.getLoginId());
//    			msg.from(patientLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
//    			easemobSendMessage.sendMessage(msg);
    			
    			//保存问诊记录
    			inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorsUser.getLoginId(),doctorsUser.getName(),1,symptom,1, orders.getId());
	        	
	        }
	        /**
	         * 给该医生发送图片信息,并保持问诊记录
	         */
	        if (StringUtils.isNotBlank(urls)) {
//				Map<String, Object> ext = new HashMap<String, Object>();
//				if (patientName != null) ext.put("nickName", patientName);
//	    		if (patientUser.getHeadUrl() != null) ext.put("headUrl", patientUser.getHeadUrl());
//	    		
//	    		String[] strs_arr = filetempUrls.split(",");
//	    		for (int i=0; i<strs_arr.length; i++) {
//	    			File file = new File(strs_arr[i]);
//	    			if(file.exists()){
//						HttpToEasemobUtil.sendImage(file, doctorsUser.getLoginId(), patientLoginId, HttpToEasemobUtil.TARGETTYPE_USERS, ext);
//					}
//				}
	    		
	    		//保存问诊记录
	    		inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorsUser.getLoginId(),doctorsUser.getName(),1,urls,2, orders.getId());

	        }
    		
	        /**
	         * 给患者发提示
	         */
	        String tip_mes = "您的提问已提交，系统正在指派医生解答";
	        Map<String, Object> ext = new HashMap<String, Object>();
    		ext.put("type", "tip");
    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes);
			UserName userName = new UserName();
			userName.add(patientLoginId);
			msg.from(doctorsUser.getLoginId()).target(userName).targetType("users").msg(msgContent).ext(ext);
			easemobSendMessage.sendMessage(msg);
			map.put("orders", orders);
			map.put("doctorsUser", doctorsUser);
			
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
	
	/**
	 * parameter={"doctorLoginId":"医生loginId","doctorName":"医生姓名","patientLoginId":"患者loginId","patientName":"患者姓名","requirement":"解读需求","diagnoseTime":"检查时间","ordersPrice":"订单金额","payType":"支付方式：1支付宝支付，2微信支付，3银行卡支付，4其他方式支付"} 
	 * @Title:chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "submitReport")
	@ResponseBody
	@SystemControllerLog(description = "上传解读报告")
	public Results<Map<String, Object>> submitReport(HttpServletRequest request, @RequestParam String parameter, MultipartFile[] files) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		//************************************************multipart请求特殊过滤处理******************************************
//		if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		//************************************************multipart请求特殊过滤处理******************************************
		if (files!=null&&files.length>0) {
			for (MultipartFile f : files) {
				if(!FileFilterUtils.isMediaFileType(f.getOriginalFilename())){
					results.setCode(MessageCode.CODE_404);
					results.setMessage("不支持该文件格式上传");
					return results;
				}
			}
		}
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorLoginId = dataJsonObject.getString("doctorLoginId");
		String doctorName = dataJsonObject.getString("doctorName");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		String requirement = dataJsonObject.getString("requirement");
		SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(requirement);
		if (sensitiveWord != null && sensitiveWord.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
			sensitiveRecordLog.setText(sensitiveWord.getText());
			sensitiveRecordLog.setUserLoginid(patientLoginId);
			sensitiveRecordLog.setUserPhone(patientLoginId.substring(patientLoginId.indexOf("_") + 1));
			sensitiveRecordLog.setType(sensitiveWord.getType());
			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);

			String equipmentData = request.getParameter("equipmentData");
			JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
			if (dataJsonObject2 != null) {
				String userName = dataJsonObject2.getString("userName");
				sensitiveRecordLog.setUserName(userName);
			}

			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("您输入的报告解读需求包含敏感词，上传失败！");
			return results;
		}
		
		String diagnoseTime = dataJsonObject.getString("diagnoseTime");
		String ordersPrice = dataJsonObject.getString("ordersPrice");
		Integer payType = dataJsonObject.getInteger("payType");
		if (StringUtils.isNotBlank(doctorLoginId) && StringUtils.isNotBlank(patientLoginId)) {
//			String filetempUrls = "";
//			String urls = "";
//			for (int i=0; i<files.length; i++) {
//				String filetempUrl = UploadFilesUtils.cacheFile(request, files[i], patientLoginId+"_"); // 保存临时文件
//    			File file = new File(filetempUrl);
//    			String fileUrl = UploadFilesUtils.formUploadFileToServer(file); // 保存远程文件
//    			filetempUrls += "," + filetempUrl;
//    			urls += "," + fileUrl;
//			}
//			if (StringUtils.isNotBlank(filetempUrls)) {
//				filetempUrls = filetempUrls.trim();
//				filetempUrls = filetempUrls.substring(1, filetempUrls.length());
//			}
//			if (StringUtils.isNotBlank(urls)) {
//				urls = urls.trim();
//				urls = urls.substring(1, urls.length());
//			}
			
			UserInfo userInfo  = new UserInfo();
			userInfo.setUserLoginId(patientLoginId);
			userInfo.setUserName(patientName);
			userInfo.setUserPhone(patientLoginId.substring(patientLoginId.lastIndexOf("_")+1));
			String urls = UploadFilesUtils.saveFiles(mediaLogService, userInfo,request, files, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_"); //保存文件
			
//			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientLoginId.split("_")[1]);

			//保存订单
			Orders orders = new Orders();
			orders.setOrdersTitle("报告解读服务");
			orders.setDoctorLoginId(doctorLoginId);
			orders.setDoctorName(doctorName);
			orders.setDoctorPhone(doctorLoginId.split("_")[1]);
			orders.setPatientLoginId(patientLoginId);
			orders.setPatientName(patientName);
			orders.setPatientPhone(patientLoginId.split("_")[1]);
			orders.setRequirement(requirement);
			orders.setDiagnoseTime(diagnoseTime);
			if (StringUtils.isNotBlank(urls))orders.setImageUrl(urls);
			orders.setPayType(payType);
			orders.setOrdersPrice(ordersPrice);
			orders.setOrdersType(1);
			orders.setCreatetime(new Date());
			ordersService.insert(orders);
			
			/**
	         * 给该医生发送解读需求信息,并保持问诊记录
	         */
	        if (StringUtils.isNotBlank(requirement)) {
//	        	Map<String, Object> ext = new HashMap<String, Object>();
//	    		if (patientName != null) ext.put("nickName", patientName);
//	    		if (patientUser.getHeadUrl() != null) ext.put("headUrl", patientUser.getHeadUrl());
//	    		ext.put("type", "inquiry");
//	    		
//	    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//    			Msg msg = new Msg();
//    			MsgContent msgContent = new MsgContent();
//
//    			msgContent.type(MsgContent.TypeEnum.TXT).msg(requirement);
//    			UserName userName = new UserName();
//    			userName.add(doctorLoginId);
//    			msg.from(patientLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
//    			easemobSendMessage.sendMessage(msg);
    			
    			//保存问诊记录
    			inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorLoginId,doctorName,1,requirement,1, orders.getId());
	        	
	        }
	        /**
	         * 给该医生发送图片信息,并保持问诊记录
	         */
	        if (StringUtils.isNotBlank(urls)) {
//				Map<String, Object> ext = new HashMap<String, Object>();
//				if (patientName != null) ext.put("nickName", patientName);
//	    		if (patientUser.getHeadUrl() != null) ext.put("headUrl", patientUser.getHeadUrl());
//	    		
//	    		String[] strs_arr = filetempUrls.split(",");
//	    		for (int i=0; i<strs_arr.length; i++) {
//	    			File file = new File(strs_arr[i]);
//	    			if(file.exists()){
//						HttpToEasemobUtil.sendImage(file, doctorLoginId, patientLoginId, HttpToEasemobUtil.TARGETTYPE_USERS, ext);
//					}
//				}
	    		
	    		//保存问诊记录
	    		inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorLoginId,doctorName,1,urls,2, orders.getId());

	        }
	        
	        /**
	         * 给患者发提示
	         */
	        String tip_mes = "您的报告解读申请已提交发送给医生，请耐心\n等候医生的回复。";
        	Map<String, Object> ext = new HashMap<String, Object>();
    		ext.put("type", "tip");
    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent(); 
			msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes);
			UserName userName = new UserName();
			userName.add(patientLoginId);
			msg.from(doctorLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
			easemobSendMessage.sendMessage(msg);
			
			map.put("orders", orders);
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
	
	/**
	 * parameter={"doctorPhone":"医生电话","doctorName":"医生姓名","patientPhone":"患者电话","patientName":"患者姓名","ordersType":"订单类型：1报告解读服务，2快速问诊服务，3图文咨询服务，4语音咨询服务，5视频问诊服务","ordersPrice":"订单金额","payType":"支付方式：1支付宝支付，2微信支付，3银行卡支付，4其他方式支付"} 
	 * @Title:chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "videoInterrogation")
	@ResponseBody
	@SystemControllerLog(description = "提交图文/视频问诊服务订单")
	public Results<Map<String, Object>> videoInterrogation(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorPhone = dataJsonObject.getString("doctorPhone");
		String doctorName = dataJsonObject.getString("doctorName");
		String patientPhone = dataJsonObject.getString("patientPhone");
		String patientName = dataJsonObject.getString("patientName");
		Integer ordersType = dataJsonObject.getInteger("ordersType");
		String ordersPrice = dataJsonObject.getString("ordersPrice");
		Integer payType = dataJsonObject.getInteger("payType");
		String doctorLoginId = "";
		String patientLoginId = "";
		if (StringUtils.isNotBlank(doctorPhone) && StringUtils.isNotBlank(patientPhone) && ordersType != null) {
			doctorLoginId = Constant.doctor + doctorPhone;
			patientLoginId = Constant.patient + patientPhone;
			
			//查询患者信息
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);

			//保存订单
//			String orderNo = UUID.randomUUID().toString();
			String orderNo = Utils.getCatId();
			Orders orders = new Orders();
			orders.setOrderNo(orderNo);
			orders.setDoctorLoginId(doctorLoginId);
			orders.setDoctorName(doctorName);
			orders.setDoctorPhone(doctorPhone);
			orders.setPatientLoginId(patientLoginId);
			orders.setPatientName(patientName);
			orders.setPatientPhone(patientPhone);
			orders.setPayType(payType);
			orders.setOrdersPrice(ordersPrice);
			orders.setOrdersType(ordersType);
			orders.setOrdersPayState(0);
			orders.setOrdersState(0);
			if (5 == ordersType) {
				orders.setOrdersTitle("视频问诊服务");
			} else if (3 == ordersType) {
				orders.setOrdersTitle("图文问诊服务");
			}
			orders.setCreatetime(new Date());
			ordersService.insert(orders);
			
			//在九州通添加医患关系
			ordersService.saveDoctorPatient(doctorPhone, patientPhone);
			
			//保存问诊记录
			if (5 == ordersType) {
				inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorLoginId,doctorName,1,null,4, orders.getId());
			} else if (3 == ordersType) {
				inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorLoginId,doctorName,1,null,2, orders.getId());
			}
			
			//创建充值订单
//			String charge=ChargeUtils.chargePreOrders(orderNo, 0.01f, "测试产品", "测试产品", "wechat_app", "127.0.0.1", "测试产品");
//			JSONObject json = JSONObject.parseObject(contentStr);
//			String orderno = json.getString("orderno");
//			Long amount = json.getLong("amount");
//			String msg = json.getString("msg");
			
			//创建充值订单
			String amount = "0.01";
			String subject = orders.getOrdersTitle();
			String body = patientName + " 购买  " + doctorName + orders.getOrdersTitle();
			String detail = patientName + " 购买  " + doctorName + orders.getOrdersTitle();
			String attach = "";
			JSONObject charge = ordersService.createPaymentOrders(orderNo, amount, subject, body, detail, attach);
			if (null == charge) {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("创建充值订单失败");
				return results;
			}
			
			map.put("charge", charge);
			map.put("orders", orders);
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
	
	/**
	 * parameter={"doctorsLoginid":"医生loginid","patientLoginid":"患者loginid"} 
	 * @Title:
	 * chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "getUnfinishedOrdersInfo")
	@ResponseBody
	@SystemControllerLog(description = "查询患者与医生未完成视频订单")
	public Results<Map<String, Object>> getUnfinishedOrdersInfo(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorLoginId = dataJsonObject.getString("doctorsLoginid");
		String patientLoginId = dataJsonObject.getString("patientLoginid");
		
		if (StringUtils.isBlank(doctorLoginId) || StringUtils.isBlank(patientLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("doctorLoginId", doctorLoginId);
		paramMap.put("patientLoginId", patientLoginId);
		List<Orders> ordersList = ordersService.getList("getUnfinishedOrders2", paramMap);
		
		map.put("ordersList", ordersList);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
		
	}
	
	/**
	 * parameter={"patientPhone":"患者手机号"} @Title:
	 * chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "selectOrdersList")
	@ResponseBody
	@SystemControllerLog(description = "查询患者订单列表")
	public Results<Map<String, Object>> selectOrdersList(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		if (StringUtils.isBlank(patientPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("patientPhone", patientPhone);
		List<Orders> ordersList = ordersService.getList("selectByPatientPhone", paramMap);
		if (ordersList != null) {
			map.put("ordersList", ordersList);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("没有数据");
			return results;
		}

	}
	
	/**
	 * 支付完成回调
	 * 
	 * @param request
	 * @param parameter={"orderno":"订单号", "amount":"订单金额"}
	 * @return
	 */
	@SystemControllerLog(description = "支付完成回调")
	@RequestMapping(value = "paymentFulfill")
	@ResponseBody
	public Results<Map<String, Object>> paymentFulfill(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String orderno = dataJsonObject.getString("orderno");
		String amount = dataJsonObject.getString("amount");
//		String wetchatOrderNo = dataJsonObject.getString("wetchatOrderNo");
		if (StringUtils.isBlank(orderno) || StringUtils.isBlank(amount)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		// 回填订单信息
		Orders orders = ordersService.findByParam("selectByOrderNo", orderno);
		if (null != orders) {
			if (6 == orders.getOrdersType()) { //医生开处方类型订单
//				String patientPhone = orders.getPatientPhone();
//				String paymentResult = paymentService.paymentResult(patientPhone, wetchatOrderNo, resultCode, msg);//支付结果回调九州通接口
			} else { //图文视频等类型订单
				//查询患者信息
				PatientUser patientUser = patientUserService.findByParam("selectByPhone", orders.getPatientPhone());
				
				String doctorLoginId = orders.getDoctorLoginId();
				String doctorName = orders.getDoctorName();
				String patientLoginId = orders.getPatientLoginId();
				String patientName = orders.getPatientName();
				String targetType = "users";
				/**
		         * 给患者发提示
		         */
		        String msgTxt = "";
		        if (5 == orders.getOrdersType()) {
		        	msgTxt = "您已成功购买视频问诊服务，接诊医生" + doctorName + "。";
				} else if (3 == orders.getOrdersType()) {
					msgTxt = "您已成功购买图文问诊服务，接诊医生" + doctorName + "。";
				}
	        	Map<String, Object> ext = new HashMap<String, Object>();
	    		ext.put("type", "videoTip");
	    		ext.put("orders", orders);
				HttpToEasemobUtil.sendTxt(doctorLoginId, patientLoginId, targetType, msgTxt, ext);
				
				/**
		         * 给医生发提示
		         */
		        String msgTxt2 = "";
		        if (5 == orders.getOrdersType()) {
		        	msgTxt2 = "问诊请求：对方购买了一次视频问诊服务，请及时处理";
				} else if (3 == orders.getOrdersType()) {
					msgTxt2 = "问诊请求：对方购买了一次图文问诊服务，请及时处理";
				}
	        	Map<String, Object> ext2 = new HashMap<String, Object>();
	    		ext2.put("type", "videoTip");
	    		ext2.put("orders", orders);
	    		ext2.put("nickName", patientName);
	    		if (patientUser != null) ext2.put("headUrl", patientUser.getHeadUrl());
				HttpToEasemobUtil.sendTxt(patientLoginId, doctorLoginId, targetType, msgTxt2, ext2);
			}
			
			orders.setOrdersPrice(String.valueOf(amount));
//			orders.setPayOrderNo(wetchatOrderNo);// 支付订单号
			orders.setOrdersPayState(1);
			orders.setPaytime(new Date());
			orders.setPayType(2);
			ordersService.update("updateByPrimaryKeySelective", orders);
		}
		
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}
	
	/**
	 * 查询最新图文视频订单
	 * 
	 * @param request
	 * @param parameter={"patientPhone":"患者电话", "doctorPhone":"医生电话"}
	 * @return
	 */
	@SystemControllerLog(description = "查询最新图文视频订单")
	@RequestMapping(value = "selectNewestOrders")
	@ResponseBody
	public Results<Map<String, Object>> selectNewestOrders(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String doctorPhone = dataJsonObject.getString("doctorPhone");
		if (StringUtils.isBlank(patientPhone) || StringUtils.isBlank(doctorPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("patientPhone", patientPhone);
		paramMap.put("doctorPhone", doctorPhone);
		
		//最新图文订单
		Orders graphicOrders = ordersService.findByParam("selectGraphicOrders", paramMap);
		if (graphicOrders != null) {
			Long defTime = new Date().getTime() - graphicOrders.getPaytime().getTime();
			if (defTime >= 24*60*60*1000) {
				map.put("graphicOrders", null);
			} else {
				graphicOrders.setRemindTime(defTime);
				map.put("graphicOrders", graphicOrders);
			}
		} else {
			map.put("graphicOrders", null);
		}
		
		//最新视频订单
		Orders videoOrders = ordersService.findByParam("selectVideoOrders", paramMap);
		if (videoOrders != null) {
			Long defTime2 = new Date().getTime() - graphicOrders.getPaytime().getTime();
			if (defTime2 >= 24*60*60*1000) {
				map.put("videoOrders", null);
			} else {
				graphicOrders.setRemindTime(defTime2);
				map.put("videoOrders", videoOrders);
			}
		} else {
			map.put("videoOrders", null);
		}
		
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}

}
