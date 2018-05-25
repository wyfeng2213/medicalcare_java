package com.cmcc.medicalcare.controller.app.patient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
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
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobSendMessage;
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
@RequestMapping("/app/orders")
public class OrdersController {

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
	 * parameter={"doctorLoginId":"医生loginId","doctorName":"医生姓名","patientLoginId":"患者loginId","patientName":"患者姓名","ordersType":"订单类型：1报告解读服务，2快速问诊服务，3图文咨询服务，4语音咨询服务，5视频问诊服务","ordersPrice":"订单金额","payType":"支付方式：1支付宝支付，2微信支付，3银行卡支付，4其他方式支付"} 
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
		String doctorLoginId = dataJsonObject.getString("doctorLoginId");
		String doctorName = dataJsonObject.getString("doctorName");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		Integer ordersType = dataJsonObject.getInteger("ordersType");
		String ordersPrice = dataJsonObject.getString("ordersPrice");
		Integer payType = dataJsonObject.getInteger("payType");
		if (StringUtils.isNotBlank(doctorLoginId) && StringUtils.isNotBlank(patientLoginId) && ordersType != null) {
			
			//查询患者信息
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientLoginId.split("_")[1]);

			//保存订单
			String orderNo = UUID.randomUUID().toString();
			Orders orders = new Orders();
			orders.setOrderNo(orderNo);
			orders.setDoctorLoginId(doctorLoginId);
			orders.setDoctorName(doctorName);
			orders.setDoctorPhone(doctorLoginId.split("_")[1]);
			orders.setPatientLoginId(patientLoginId);
			orders.setPatientName(patientName);
			orders.setPatientPhone(patientLoginId.split("_")[1]);
			orders.setPayType(payType);
			orders.setOrdersPrice(ordersPrice);
			orders.setOrdersType(ordersType);
			orders.setOrdersState(0);
			if (5 == ordersType) {
				orders.setOrdersTitle("视频问诊服务");
			} else if (3 == ordersType) {
				orders.setOrdersTitle("图文问诊服务");
			}
			orders.setCreatetime(new Date());
			ordersService.insert(orders);
			
			//保存问诊记录
			if (5 == ordersType) {
				inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorLoginId,doctorName,1,null,4, orders.getId());
			} else if (3 == ordersType) {
				inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientName,2,doctorLoginId,doctorName,1,null,2, orders.getId());
			}
			
	        /**
	         * 给患者发提示
	         */
//	        String tip_mes = "";
//	        if (5 == ordersType) {
//	        	tip_mes = "您已成功购买视频问诊服务，接诊医生" + doctorName + "。";
//			} else if (3 == ordersType) {
//				tip_mes = "您已成功购买图文问诊服务，接诊医生" + doctorName + "。";
//			}
//        	Map<String, Object> ext = new HashMap<String, Object>();
//    		ext.put("type", "videoTip");
//    		ext.put("orders", orders);
//    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
//			Msg msg = new Msg();
//			MsgContent msgContent = new MsgContent();
//			msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes);
//			UserName userName = new UserName();
//			userName.add(patientLoginId);
//			msg.from(doctorLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
//			easemobSendMessage.sendMessage(msg);
			
			/**
	         * 给医生发提示
	         */
//	        String tip_mes2 = "";
//	        if (5 == ordersType) {
//	        	tip_mes2 = "问诊请求：对方购买了一次视频问诊服务，请及时处理";
//			} else if (3 == ordersType) {
//				tip_mes2 = "问诊请求：对方购买了一次图文问诊服务，请及时处理";
//			}
//        	Map<String, Object> ext2 = new HashMap<String, Object>();
//    		ext2.put("type", "videoTip");
//    		ext2.put("orders", orders);
//    		ext2.put("nickName", patientName);
//    		if (patientUser != null) ext2.put("headUrl", patientUser.getHeadUrl());
//    		EasemobSendMessage easemobSendMessage2 = new EasemobSendMessage();
//			Msg msg2 = new Msg();
//			MsgContent msgContent2 = new MsgContent();
//			msgContent2.type(MsgContent.TypeEnum.TXT).msg(tip_mes2);
//			UserName userName2 = new UserName();
//			userName2.add(doctorLoginId);
//			msg2.from(patientLoginId).target(userName2).targetType("users").msg(msgContent2).ext(ext2);
//			easemobSendMessage2.sendMessage(msg2);
			
			//创建充值订单
//			String charge=ChargeUtils.chargePreOrders(orderNo, 0.01f, "测试产品", "测试产品", "wechat_app", "127.0.0.1", "测试产品");
//			JSONObject json = JSONObject.parseObject(contentStr);
//			String orderno = json.getString("orderno");
//			Long amount = json.getLong("amount");
//			String msg = json.getString("msg");
			
//			map.put("charge", charge);
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
	 * parameter={"doctorLoginId":"医生loginId","patientLoginId":"患者loginId","payType":"支付方式：1支付宝支付，2微信支付，3银行卡支付，4其他方式支付"} 
	 * @Title:chooseContractedHospitals @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
//	@RequestMapping(value = "graphicInterrogation")
//	@ResponseBody
//	@SystemControllerLog(description = "提交图文问诊服务订单")
//	public Results<Map<String, Object>> graphicInterrogation(HttpServletRequest request, @RequestParam String parameter) {
//		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
//		String doctorLoginId = dataJsonObject.getString("doctorLoginId");
//		String patientLoginId = dataJsonObject.getString("patientLoginId");
//		Integer payType = dataJsonObject.getInteger("payType");
//		if (StringUtils.isNotBlank(doctorLoginId) && StringUtils.isNotBlank(patientLoginId)) {
//			
//			//查询患者信息
//			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientLoginId.split("_")[1]);
//			
//			//查询医生信息
//			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorLoginId.split("_")[1]);
//
//			//保存订单
//			Orders orders = new Orders();
//			orders.setOrdersTitle("图文问诊服务");
//			orders.setDoctorLoginId(doctorLoginId);
//			orders.setDoctorName(doctorsUser.getName());
//			orders.setDoctorPhone(doctorLoginId.split("_")[1]);
//			orders.setPatientLoginId(patientLoginId);
//			orders.setPatientName(patientUser.getName());
//			orders.setPatientPhone(patientLoginId.split("_")[1]);
//			orders.setPayType(payType);
//			orders.setOrdersPrice(doctorsUser.getGraphicConsultingPrice());
//			orders.setOrdersType(3);
//			orders.setOrdersState(0);
//			orders.setCreatetime(new Date());
//			ordersService.insert(orders);
//			
//			//保存问诊记录
//			inquiryMessageLogService.saveInquiryMessageLog(patientLoginId,patientUser.getName(),2,doctorLoginId,doctorsUser.getName(),1,null,2, orders.getId());
//			
//			//创建充值订单
//			String charge=ChargeUtils.chargePreOrders(0.01f, "测试产品", "测试产品", "wechat_app", "127.0.0.1", "测试产品");
//			
//			map.put("charge", charge);
//			map.put("orders", orders);
//			results.setData(map);
//			//
//			results.setCode(MessageCode.CODE_200);
//			results.setMessage(MessageCode.MESSAGE_200);
//			return results;
//		} else {
//			results.setCode(MessageCode.CODE_201);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//		
//	}

}
