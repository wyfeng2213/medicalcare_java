/**   
* @Title: InquiryMessageLogController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年8月15日 下午4:57:38 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cmcc.medicalcare.model.InquiryMessageLog;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.VideoInterrogationLog;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IInquiryMessageLogService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.IVideoInterrogationLogService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobIMUsers;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * @ClassName: InquiryMessageLogController
 * @Description: TODO
 * @author wsm
 * @date 2017年8月15日 下午4:57:38
 * 
 */
@Controller
@RequestMapping({ "/app/inquiryMessageLog" })
public class InquiryMessageLogController {

	@Resource
	private IInquiryMessageLogService inquiryMessageLogService;
	
	@Resource
	private IVideoInterrogationLogService videoInterrogationLogService;
	
	@Resource
	private IOrdersService ordersService;
	
	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IMediaLogService  mediaLogService;

	/**
	 * parameter={"fromUserLoginId":"","fromUserName":"","
	 * fromUserType":"","toUserLoginId":"","toUserName":"","
	 * toUserType":"","messageContent":"","messageType":"","ordersId":"订单id" }
	 * 
	 * @Title: saveInquiryMessageLog @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         multipartFile @param @return 设定文件 @return Results<Map
	 *         <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "保存图文问诊消息（仅供IOS使用）")
	@RequestMapping({ "saveInquiryMessageLog" })
	@ResponseBody
	public Results<Map<String, Object>> saveInquiryMessageLog(@RequestParam String parameter,
			HttpServletRequest request, MultipartFile multipartFile) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// ************************************************multipart请求特殊过滤处理******************************************
//		if (!SecurityUtils.multipartCheck(request)) { // multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		// ************************************************multipart请求特殊过滤处理******************************************
		if(!multipartFile.isEmpty()&&!FileFilterUtils.isMediaFileType(multipartFile.getOriginalFilename())){
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("fromUserLoginId");
		String fromUserName = dataJsonObject.getString("fromUserName");
		Integer fromUserType = dataJsonObject.getInteger("fromUserType");
		String toUserLoginId = dataJsonObject.getString("toUserLoginId");
		String toUserName = dataJsonObject.getString("toUserName");
		Integer toUserType = dataJsonObject.getInteger("toUserType");
		String messageContent = dataJsonObject.getString("messageContent");
		Integer messageType = dataJsonObject.getInteger("messageType");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(toUserLoginId) || (messageType == null)
				|| (fromUserType == null) || (toUserType == null)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

		try {

			if (multipartFile != null && multipartFile.getSize() > 0) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLoginId(fromUserLoginId);
				userInfo.setUserName(fromUserName);
				userInfo.setUserPhone(fromUserLoginId.substring(fromUserLoginId.lastIndexOf("_")+1));
				String prefix = fromUserLoginId + "_";
				String filetempUrl = UploadFilesUtils.cacheFile(request, multipartFile, prefix); // 保存临时文件
				File file = new File(filetempUrl);
				messageContent = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
			}
			
			// 保存单聊问诊消息
			if (ordersId != null) {
				inquiryMessageLogService.saveInquiryMessageLog(fromUserLoginId, fromUserName, fromUserType,
						toUserLoginId, toUserName, toUserType, messageContent, messageType, ordersId);
			} else {
				inquiryMessageLogService.saveInquiryMessageLog(fromUserLoginId, fromUserName, fromUserType,
						toUserLoginId, toUserName, toUserType, messageContent, messageType, null);
			}

		} catch (Exception e) {
			// TODO: handle exception
			results.setCode(MessageCode.CODE_404);
			results.setMessage("失败");
			return results;
		}

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		map.put("msgContentStr", messageContent);
		map.put("serverTime", System.currentTimeMillis());
		results.setData(map);

		return results;
	}
	
	/**
	 * parameter={"ordersId":"订单id","patientLoginid":"患者loginid","doctorsLoginid":"医生loginid"}
	 * 
	 * @Title: saveInquiryMessageLog @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         multipartFile @param @return 设定文件 @return Results<Map
	 *         <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "患者发起视频问诊")
	@RequestMapping({ "launchVideoFromPatient" })
	@ResponseBody
	public Results<Map<String, Object>> launchVideoFromPatient(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		Integer ordersId = dataJsonObject.getInteger("ordersId");
		String patientLoginid = dataJsonObject.getString("patientLoginid");
		String doctorsLoginid = dataJsonObject.getString("doctorsLoginid");

		if (ordersId == null || StringUtils.isBlank(patientLoginid) || StringUtils.isBlank(doctorsLoginid)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}
		
		//判断订单是否完成
		Orders orders = ordersService.findByParam("selectByPrimaryKey", ordersId);
		if (orders != null) {
			if (1 == orders.getOrdersState()) {
				results.setCode(MessageCode.CODE_300);
				results.setMessage("该订单已完成");
				return results;
			}
		}
		
		//判断医生是否在线
		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
        Object result = easemobIMUsers.getIMUserStatus(doctorsLoginid);
        if (result == null) {
        	results.setCode(MessageCode.CODE_501);
			results.setMessage("获取环信用户状态失败");
			return results;
        }
        // 解释result
		JSONObject resultObject = JSONObject.parseObject(result.toString());
		String dataStr = resultObject.getString("data");
		JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
		String isOnline = dataJsonObject2.getString(doctorsLoginid);
//		String isOnline = "online";
		if ("online".equals(isOnline)) { //医生在线
			//判断医生是否空闲
			VideoInterrogationLog videoInterrogationLog_ = videoInterrogationLogService.findByParam("getMaxUpdateTimeByLoginid2", doctorsLoginid);
			if (videoInterrogationLog_ != null) {
				Date maxUpdateTime = videoInterrogationLog_.getUpdatetime();
				//排除当前患者
				if (maxUpdateTime != null && 
						!(videoInterrogationLog_.getFromUserLoginid().equals(patientLoginid) 
								|| videoInterrogationLog_.getToUserLoginid().equals(patientLoginid))) {
					long time_dif = new Date().getTime() - maxUpdateTime.getTime();
					if (time_dif < 60000) { //当前时间与最大更新时间小于60秒，医生忙碌
						results.setCode(MessageCode.CODE_501);
						results.setMessage("医生忙碌");
						return results;
					}
				}
			}
			//医生空闲,保存视频问诊记录
			VideoInterrogationLog videoInterrogationLog = videoInterrogationLogService.saveVideoInterrogationLog(ordersId, patientLoginid, 1, doctorsLoginid, 0);
			
			//查询患者信息
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientLoginid.split("_")[1]);
			
			//将视频问诊记录，记录到inquiry_message_log表，便于统计
			InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
			inquiryMessageLog.setChatroomId(Constant.single_chat_chatroom_id);
			inquiryMessageLog.setFromUserLoginId(patientLoginid);
			inquiryMessageLog.setFromUserPhone(patientLoginid.substring(patientLoginid.indexOf("_") + 1));
			inquiryMessageLog.setFromUserName(patientUser.getName());
			inquiryMessageLog.setFromUserType(2);
			inquiryMessageLog.setToUserLoginId(doctorsLoginid);
			inquiryMessageLog.setToUserPhone(doctorsLoginid.substring(doctorsLoginid.indexOf("_") + 1));
			inquiryMessageLog.setToUserType(1);
			inquiryMessageLog.setMessagecontent("video_interrogation_log_id_" + videoInterrogationLog.getId());
			inquiryMessageLog.setMessageType(4);
			inquiryMessageLog.setOrdersId(ordersId);
			inquiryMessageLog.setSendtime(new Date());
			inquiryMessageLog.setCreatetime(new Date());
			inquiryMessageLogService.insert(inquiryMessageLog);
			
			//给医生发送视频提醒
			String tip_mes = "有患者向您发起了视频请求";
        	Map<String, Object> ext = new HashMap<String, Object>();
    		ext.put("type", "tip");
    		if (patientUser != null) ext.put("nickName", patientUser.getName());
    		if (patientUser != null) ext.put("headUrl", patientUser.getHeadUrl());
    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes);
			UserName userName = new UserName();
			userName.add(doctorsLoginid);
			msg.from(patientLoginid).target(userName).targetType("users").msg(msgContent).ext(ext);
			easemobSendMessage.sendMessage(msg);
			
			map.put("videoInterrogationLogId", videoInterrogationLog.getId());
			map.put("ordersId", ordersId);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage("医生在线且空闲");
			return results;
		} else { //医生离线
			results.setCode(MessageCode.CODE_501);
			results.setMessage("医生离线");
			return results;
		}
		
	}
	
	/**
	 * parameter={"ordersId":"订单id","doctorsLoginid":"医生loginid","patientLoginid":"患者loginid"}
	 * 
	 * @Title: saveInquiryMessageLog @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         multipartFile @param @return 设定文件 @return Results<Map
	 *         <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发起视频问诊")
	@RequestMapping({ "launchVideoFromDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> launchVideoFromDoctor(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginid = dataJsonObject.getString("doctorsLoginid");
		String patientLoginid = dataJsonObject.getString("patientLoginid");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (ordersId == null || StringUtils.isBlank(patientLoginid) || StringUtils.isBlank(doctorsLoginid)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}
		
		//判断订单是否完成
		Orders orders = ordersService.findByParam("selectByPrimaryKey", ordersId);
		if (orders != null) {
			if (1 == orders.getOrdersState()) {
				results.setCode(MessageCode.CODE_300);
				results.setMessage("该订单已完成");
				return results;
			}
		}
		
		//保存视频问诊记录
		VideoInterrogationLog videoInterrogationLog = videoInterrogationLogService.saveVideoInterrogationLog(ordersId, doctorsLoginid, 0, patientLoginid, 0);
		
		//查询医生信息
		DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsLoginid.substring(doctorsLoginid.indexOf("_") + 1));
		
		//将视频问诊记录，记录到inquiry_message_log表，便于统计
		InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
		inquiryMessageLog.setChatroomId(Constant.single_chat_chatroom_id);
		inquiryMessageLog.setFromUserLoginId(doctorsLoginid);
		inquiryMessageLog.setFromUserPhone(doctorsLoginid.substring(doctorsLoginid.indexOf("_") + 1));
		inquiryMessageLog.setFromUserName(doctorsUser.getName());
		inquiryMessageLog.setFromUserType(1);
		inquiryMessageLog.setToUserLoginId(patientLoginid);
		inquiryMessageLog.setToUserPhone(patientLoginid.substring(patientLoginid.indexOf("_") + 1));
		inquiryMessageLog.setToUserType(2);
		inquiryMessageLog.setMessagecontent("video_interrogation_log_id_" + videoInterrogationLog.getId());
		inquiryMessageLog.setMessageType(4);
		inquiryMessageLog.setOrdersId(ordersId);
		inquiryMessageLog.setSendtime(new Date());
		inquiryMessageLog.setCreatetime(new Date());
		inquiryMessageLogService.insert(inquiryMessageLog);
		
		//给患者发送视频提醒
		String tip_mes = "医生向您发起了视频请求";
    	Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("type", "tip");
		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes);
		UserName userName = new UserName();
		userName.add(patientLoginid);
		msg.from(doctorsLoginid).target(userName).targetType("users").msg(msgContent).ext(ext);
		easemobSendMessage.sendMessage(msg);
		
		map.put("videoInterrogationLogId", videoInterrogationLog.getId());
		map.put("ordersId", ordersId);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
		
	}
	
	/**
	 * parameter={"videoInterrogationLogId":"视频问诊记录id","isOver":"是否最后一次调接口(0否，1是)","state":"通话状态：0未接听，1通话结束，2已拒绝，3已取消","callTime":"通话时长(通话结束时传该参数)"}
	 * 
	 * @Title: saveInquiryMessageLog @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         multipartFile @param @return 设定文件 @return Results<Map
	 *         <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "视频监听接口")
	@RequestMapping({ "videoMonitoring" })
	@ResponseBody
	public Results<Map<String, Object>> videoMonitoring(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		String tip_mes = ""; //通话状态提醒信息
		String tip_mes2 = ""; //通话状态提醒信息
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		Integer videoInterrogationLogId = dataJsonObject.getInteger("videoInterrogationLogId");
		String isOver = dataJsonObject.getString("isOver");
		String state = dataJsonObject.getString("state");
		Integer callTime = dataJsonObject.getInteger("callTime");

		if (videoInterrogationLogId == null || StringUtils.isBlank(isOver) || StringUtils.isBlank(state)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		//查询视频问诊记录
		VideoInterrogationLog videoInterrogationLog = videoInterrogationLogService.findByParam("selectByPrimaryKey", videoInterrogationLogId);
		if (videoInterrogationLog == null) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("不存在对应的视频问诊记录");
			return results;
		}
		
		//查询视频订单信息
		Orders orders = ordersService.findByParam("selectByPrimaryKey", videoInterrogationLog.getOrdersId());
		
		//更新通话时长,及更新监听时间
		if ("0".equals(isOver)) { //视频未结束
			Integer videoTime = videoInterrogationLog.getVideoTime();
			Integer videoTime_new = videoTime + 60;
			videoInterrogationLog.setVideoTime(videoTime_new);
			videoInterrogationLog.setUpdatetime(new Date());
			videoInterrogationLogService.update("updateByPrimaryKeySelective", videoInterrogationLog);
		} else { //视频结束
			//更新订单为完成
			if ("1".equals(state)) {//通话状态：0未接听，1通话结束，2已拒绝，3已取消
				if (orders != null) {
					if (orders.getOrdersState() != 1) {
						orders.setOrdersState(1);
						ordersService.update("updateByPrimaryKeySelective", orders);
					}
				}
				
				if (callTime != null) {
					//更新通话时长,及更新监听时间
					videoInterrogationLog.setVideoTime(callTime);
					videoInterrogationLog.setState(1);
					videoInterrogationLog.setUpdatetime(new Date());
					videoInterrogationLogService.update("updateByPrimaryKeySelective", videoInterrogationLog);
				} else {
					//更新通话时长,及更新监听时间
					Integer videoTime = videoInterrogationLog.getVideoTime();
					Date updatetime = videoInterrogationLog.getUpdatetime();
					Integer videoTime_new = videoTime + (int)((new Date().getTime() - updatetime.getTime())/1000);
					videoInterrogationLog.setVideoTime(videoTime_new);
					videoInterrogationLog.setState(1);
					videoInterrogationLog.setUpdatetime(new Date());
					videoInterrogationLogService.update("updateByPrimaryKeySelective", videoInterrogationLog);
				}
				
				int videoTime = videoInterrogationLog.getVideoTime();
				tip_mes = "此次视频通话时长为" + videoTime/60 + "分" + videoTime%60 + "秒";
				tip_mes2 = "此次视频通话时长为" + videoTime/60 + "分" + videoTime%60 + "秒";
			} else if ("2".equals(state)) {
				tip_mes = "对方已拒绝";
				tip_mes2 = "已拒绝";
			} else if ("3".equals(state)) {
				tip_mes = "已取消";
				tip_mes2 = "对方已取消";
			} else if ("0".equals(state)) {
				tip_mes = "对方未接听";
				tip_mes2 = "";
			}
			
			/**
			 * 视频结束，给通话双方透传通话状态信息
			 */
			//给视频发起方发消息
			if (StringUtils.isNotBlank(tip_mes)) {
				Map<String, Object> ext = new HashMap<String, Object>();
	    		ext.put("type", "tip");
	    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();
				msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes);
				UserName userName = new UserName();
				userName.add(videoInterrogationLog.getFromUserLoginid());
				msg.from(videoInterrogationLog.getToUserLoginid()).target(userName).targetType("users").msg(msgContent).ext(ext);
				easemobSendMessage.sendMessage(msg);
			}
			
			//给视频接收方发消息
			if (StringUtils.isNotBlank(tip_mes2)) {
				Map<String, Object> ext = new HashMap<String, Object>();
	    		ext.put("type", "tip");
	    		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
				Msg msg = new Msg();
				MsgContent msgContent = new MsgContent();
				msgContent.type(MsgContent.TypeEnum.TXT).msg(tip_mes2);
				UserName userName = new UserName();
				userName.add(videoInterrogationLog.getToUserLoginid());
				msg.from(videoInterrogationLog.getFromUserLoginid()).target(userName).targetType("users").msg(msgContent).ext(ext);
				easemobSendMessage.sendMessage(msg);
			}
		}
		
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
	}
	
	
//	/**
//	 * parameter={"videoInterrogationLogId":"视频问诊记录id","isOver":"是否通话结束(0未结束，1结束)"}
//	 * 
//	 * @Title: saveInquiryMessageLog @Description: TODO @param @param
//	 *         parameter @param @param request @param @param
//	 *         multipartFile @param @return 设定文件 @return Results<Map
//	 *         <String,Object>> 返回类型 @throws
//	 */
//	@SystemControllerLog(description = "视频监听接口")
//	@RequestMapping({ "videoMonitoring" })
//	@ResponseBody
//	public Results<Map<String, Object>> videoMonitoring(HttpServletRequest request, @RequestParam String parameter) {
//		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
//		
//		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
//		Integer videoInterrogationLogId = dataJsonObject.getInteger("videoInterrogationLogId");
//		String isOver = dataJsonObject.getString("isOver");
//
//		if (videoInterrogationLogId == null || StringUtils.isBlank(isOver)) {
//			results.setCode(MessageCode.CODE_201);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//		
//		//查询视频问诊记录
//		VideoInterrogationLog videoInterrogationLog = videoInterrogationLogService.findByParam("selectByPrimaryKey", videoInterrogationLogId);
//		if (videoInterrogationLog != null) {
//			//更新视频问诊记录对应订单信息为完成
//			Orders orders = ordersService.findByParam("selectByPrimaryKey", videoInterrogationLog.getOrdersId());
//			if (orders != null) {
//				if (orders.getOrdersState() != 1) {
//					orders.setOrdersState(1);
//					ordersService.update("updateByPrimaryKeySelective", orders);
//				}
//			}
//			
//			//更新通话时长,及更新监听时间
//			if ("0".equals(isOver)) {
//				Integer videoTime = videoInterrogationLog.getVideoTime();
//				Integer videoTime_new = videoTime + 60;
//				videoInterrogationLog.setVideoTime(videoTime_new);
//				videoInterrogationLog.setUpdatetime(new Date());
//				videoInterrogationLogService.update("updateByPrimaryKeySelective", videoInterrogationLog);
//			} else {
//				Integer videoTime = videoInterrogationLog.getVideoTime();
//				Date updatetime = videoInterrogationLog.getUpdatetime();
//				Integer videoTime_new = videoTime + (int)((new Date().getTime() - updatetime.getTime())/1000);
//				videoInterrogationLog.setVideoTime(videoTime_new);
//				videoInterrogationLog.setUpdatetime(new Date());
//				videoInterrogationLogService.update("updateByPrimaryKeySelective", videoInterrogationLog);
//				
//				//给患者发医生空闲信息
//				
//			}
//			
//			results.setCode(MessageCode.CODE_200);
//			results.setMessage(MessageCode.MESSAGE_200);
//			return results;
//		} else {
//			results.setCode(MessageCode.CODE_204);
//			results.setMessage("不存在对应的视频问诊记录");
//			return results;
//		}
//	}
	
	/**
	 * parameter={"pageIndex":"当前页码","pageSize":"每页记录数","keyword":"搜索关键字","hospital":"医院","department":"科室","province":"省","city":"市","area":"地区"}
	* @Title: chooseVideoDoctor 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "选择视频问诊医生")
	@RequestMapping({ "chooseVideoDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> chooseVideoDoctor(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DoctorsUser> doctorsUsersList = new ArrayList<DoctorsUser>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String pageIndex = dataJsonObject.getString("pageIndex");
		String pageSize = dataJsonObject.getString("pageSize");
		String keyword = dataJsonObject.getString("keyword");
		String province = dataJsonObject.getString("province");
		String city = dataJsonObject.getString("city");
		String area = dataJsonObject.getString("area");
		String department = dataJsonObject.getString("department");
		String hospital = dataJsonObject.getString("hospital");

		if (StringUtils.isBlank(pageSize) && StringUtils.isBlank(pageIndex)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageIndex", Integer.parseInt(pageIndex) * Integer.parseInt(pageSize) - Integer.parseInt(pageSize));
		param.put("pageSize", Integer.parseInt(pageSize));
		param.put("province", province);
		param.put("cityName", city);
		param.put("area", area);
		param.put("name", keyword);
		param.put("department", department);
		param.put("hospital", hospital);
		doctorsUsersList = doctorsUserService.getList("getVideoDoctor", param);
		
		//返回医生在线状态
		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
		Object result = null;
		if (doctorsUsersList != null && doctorsUsersList.size() > 0) {
			for (int i=0; i<doctorsUsersList.size(); i++) {
				String doctorLoginId = doctorsUsersList.get(i).getLoginId();
				result = easemobIMUsers.getIMUserStatus(doctorLoginId);
				if (result != null) {
			        // 解释result
					JSONObject resultObject = JSONObject.parseObject(result.toString());
					String dataStr = resultObject.getString("data");
					JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
					String isOnline = dataJsonObject2.getString(doctorLoginId);
					if ("online".equals(isOnline)) { //医生在线
						VideoInterrogationLog videoInterrogationLog_ = videoInterrogationLogService.findByParam("getMaxUpdateTimeByLoginid", doctorLoginId);
						if (videoInterrogationLog_ != null) {
							Date maxUpdateTime = videoInterrogationLog_.getUpdatetime();
							if (maxUpdateTime != null) {
								long time_dif = new Date().getTime() - maxUpdateTime.getTime();
								if (time_dif < 60000) { //当前时间与最大更新时间小于60秒，医生忙碌
									doctorsUsersList.get(i).setOnlineState("问诊中");
								} else {
									doctorsUsersList.get(i).setOnlineState("在线且空闲");
								}
							} else {
								doctorsUsersList.get(i).setOnlineState("在线且空闲");
							}
						} else {
							doctorsUsersList.get(i).setOnlineState("在线且空闲");
						}
					} else {
						doctorsUsersList.get(i).setOnlineState("离线");
					}
				} else {
					doctorsUsersList.get(i).setOnlineState("离线");
				}
			}
		}

		resultMap.put("doctorsUsersList", doctorsUsersList);
		results.setData(resultMap);
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		return results;
	}
	
	/**
	 * 上传保存问诊视频文件
	 * 
	 * @param request
	 * @param parameter={"videoInterrogationLogId":"视频问诊记录id","doctorsLoginid":"医生loginid"}
	 * @param video
	 * @return
	 */
	@SystemControllerLog(description = "上传保存问诊视频文件")
	@RequestMapping({ "saveVideoFile" })
	@ResponseBody
	public Results<Map<String, Object>> saveVideoFile(HttpServletRequest request, @RequestParam String parameter, MultipartFile video) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (video != null && !FileFilterUtils.isVideoFileType(video.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginid = dataJsonObject.getString("doctorsLoginid");
		Integer videoInterrogationLogId = dataJsonObject.getInteger("videoInterrogationLogId");
		if (videoInterrogationLogId == null || StringUtils.isBlank(doctorsLoginid)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}
		
		//查询视频问诊记录
		VideoInterrogationLog videoInterrogationLog_ = videoInterrogationLogService.findByParam("selectByPrimaryKey", videoInterrogationLogId);
		if (videoInterrogationLog_ == null) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("不存在对应的视频问诊记录");
			return results;
		}
		
		//查询医生信息
		DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsLoginid.substring(doctorsLoginid.indexOf("_") + 1));
		if (doctorsUser == null) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("不存在对应的医生用户");
			return results;
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginid);
		userInfo.setUserName(doctorsUser.getName());
		userInfo.setUserPhone(doctorsLoginid.substring(doctorsLoginid.lastIndexOf("_")+1));
		String videoUrl = UploadFilesUtils.saveFile(mediaLogService, userInfo,request, video, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_"); //保存文件
		
		if (StringUtils.isNotBlank(videoUrl)) {
			videoInterrogationLog_.setVideoUrl(videoUrl);
			videoInterrogationLog_.setState(1);
			videoInterrogationLog_.setUpdatetime(new Date());
			videoInterrogationLogService.update("updateByPrimaryKeySelective", videoInterrogationLog_);
		}
		
		map.put("videoInterrogationLog", videoInterrogationLog_);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
		
	}

}
