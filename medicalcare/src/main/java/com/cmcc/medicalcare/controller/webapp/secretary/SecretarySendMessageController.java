package com.cmcc.medicalcare.controller.webapp.secretary;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
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
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorSecretaryMessageLog;
import com.cmcc.medicalcare.model.InquiryMessageLog;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IInquiryMessageLogService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

@Controller
@RequestMapping({ "/web/secretarySendMessage" })
public class SecretarySendMessageController {
	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private IInquiryMessageLogService inquiryMessageLogService;

	@Resource
	private IDoctorSecretaryMessageLogService doctorSecretaryMessageLogService;

	@Resource
	private IMediaLogService mediaLogService;

	/**
	 * parameter={"secretaryName":"","secretaryLoginId":"","doctorsLoginId":"",
	 * "patientName":"","doctorName":"",
	 * "patientLoginId":"","msgContentStr":"","chatroomId":""} @Title:
	 * sendTxtMessage @Description: TODO @param @param parameter @param @param
	 * request @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送文本消息到聊天室")
	@RequestMapping({ "sendTxtMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendtMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("secretaryLoginId");
		String fromUserName = dataJsonObject.getString("secretaryName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		String chatroomId = dataJsonObject.getString("chatroomId");
		String chatroomName = dataJsonObject.getString("chatroomName");

		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(chatroomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		// // 获取头像地址和昵称
		// String nickName = dataJsonObject.getString("nickName");
		// String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		// if (nickName != null)
		ext.put("nickName", "医生助理-" + fromUserName);
		// if (headUrl != null)
		ext.put("headUrl", Config.secretaryIMG);

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(chatroomId);
			msg.from(fromUserLoginId).target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
			result = easemobSendMessage.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(chatroomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
				try {
					inquiryMessageLog.setChatroomId(chatroomId);
					inquiryMessageLog.setChatroomName(chatroomName);
					inquiryMessageLog.setFromUserLoginId(fromUserLoginId);
					inquiryMessageLog.setFromUserName(fromUserName);
					inquiryMessageLog.setFromUserPhone(fromUserLoginId.substring(fromUserLoginId.indexOf("_") + 1));
					inquiryMessageLog.setFromUserType(3);
					inquiryMessageLog.setMessagecontent(msgContentStr);
					inquiryMessageLog.setMessageType(1);
					inquiryMessageLog.setSendtime(new Date());
					inquiryMessageLog.setCreatetime(new Date());
					inquiryMessageLogService.insert(inquiryMessageLog);
				} catch (Exception e) {
					// TODO: handle exception
					try {
						String msgContentStr_ = EmojiFilter.filterEmoji(msgContentStr);
						inquiryMessageLog.setMessagecontent(msgContentStr_);
						inquiryMessageLogService.insert(inquiryMessageLog);
					} catch (Exception e2) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", msgContentStr);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"",
	 * "secretaryLoginId":"","secretaryName":"","msgContentStr":""} @Title:
	 * sendTxtMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送文本消息到医生")
	@RequestMapping({ "sendTxtMessageToSingle" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToSingle(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String secretaryName = dataJsonObject.getString("secretaryName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");

		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("nickName", "医生助理-" + secretaryName);
		ext.put("headUrl", Config.secretaryIMG);

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();

			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(doctorsLoginId);
			msg.from(secretaryLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
			result = easemobSendMessage.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(doctorsLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {

				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();

				try {
					doctorSecretaryMessageLog.setCreatetime(new Date());
					doctorSecretaryMessageLog.setFromUserId(null);
					doctorSecretaryMessageLog.setFromUserName(secretaryName);
					doctorSecretaryMessageLog
							.setFromUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
					doctorSecretaryMessageLog.setFromUserType(2);
					doctorSecretaryMessageLog.setMessagecontent(msgContentStr);
					doctorSecretaryMessageLog.setMessageType(1);
					doctorSecretaryMessageLog.setSendtime(new Date());
					doctorSecretaryMessageLog.setToUserId(null);
					doctorSecretaryMessageLog.setToUserName(doctorsName);
					doctorSecretaryMessageLog.setToUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
					doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);

				} catch (Exception e) {
					// TODO: handle exception
					try {
						String msgContentStr_ = EmojiFilter.filterEmoji(msgContentStr);
						doctorSecretaryMessageLog.setMessagecontent(msgContentStr_);
						doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);
					} catch (Exception e2) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", msgContentStr);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"","secretaryLoginId":"",
	 * "secretaryName":""} @Title: sendImgMessageToSingle @Description:
	 * TODO @param @param parameter @param @param request @param @param
	 * image @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送图片消息到医生")
	@RequestMapping({ "sendImgMessageToSingle" })
	@ResponseBody
	public Results<Map<String, Object>> sendImgMessageToSingle(@RequestParam String parameter,
			HttpServletRequest request, MultipartFile image) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// ************************************************multipart请求特殊过滤处理******************************************
		// if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************
		if (image != null && !FileFilterUtils.isMediaFileType(image.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryName = dataJsonObject.getString("secretaryName");

		String prefix = secretaryLoginId + "_";
		// String fileUrl = UploadFilesUtils.saveFile(request, image, prefix);
		// // 保存远程文件
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("nickName", "医生助理-" + secretaryName);
		ext.put("headUrl", Config.secretaryIMG);

		Object result = null;
		String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(secretaryLoginId);
		userInfo.setUserName(secretaryName);
		userInfo.setUserLoginId(secretaryLoginId.substring(secretaryLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		System.out.println("fileUrl::" + fileUrl);
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendImage(file, doctorsLoginId, secretaryLoginId,
						HttpToEasemobUtil.TARGETTYPE_USERS, ext);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(doctorsLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				doctorSecretaryMessageLog.setCreatetime(new Date());
				doctorSecretaryMessageLog.setFromUserId(null);
				doctorSecretaryMessageLog.setFromUserName(secretaryName);
				doctorSecretaryMessageLog
						.setFromUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLog.setFromUserType(2);
				doctorSecretaryMessageLog.setMessagecontent(fileUrl);
				doctorSecretaryMessageLog.setMessageType(2);
				doctorSecretaryMessageLog.setSendtime(new Date());
				doctorSecretaryMessageLog.setToUserId(null);
				doctorSecretaryMessageLog.setToUserName(doctorsName);
				doctorSecretaryMessageLog.setToUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	/**
	 * parameter={"secretaryLoginId":"","secretaryName":"","":"chatroomId":"","
	 * chatroomName":""} @Title: sendImgMessageToChatroom @Description:
	 * TODO @param @param parameter @param @param request @param @param
	 * image @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送图片消息到聊天室")
	@RequestMapping({ "sendImgMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendImgMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request, MultipartFile image) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// ************************************************multipart请求特殊过滤处理******************************************
		// if (!SecurityUtils.multipartCheck(request)) { // multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************
		if (image != null && !FileFilterUtils.isMediaFileType(image.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("secretaryLoginId");
		String fromUserName = dataJsonObject.getString("secretaryName");
		String chatroomId = dataJsonObject.getString("chatroomId");
		String chatroomName = dataJsonObject.getString("chatroomName");

		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(chatroomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		// 获取头像地址和昵称
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("nickName", "医生助理-" + fromUserName);
		ext.put("headUrl", Config.secretaryIMG);

		String prefix = fromUserLoginId + "_";

		Object result = null;
		System.out.println("OriginalFilename::::" + image.getOriginalFilename());
		String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
		System.out.println("11:    " + filetempUrl);
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(fromUserLoginId);
		userInfo.setUserName(fromUserName);
		userInfo.setUserPhone(fromUserLoginId.substring(fromUserLoginId.lastIndexOf("_")+1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		System.out.println("fileUrl::---" + fileUrl);
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendImage(file, chatroomId, fromUserLoginId,
						HttpToEasemobUtil.TARGETTYPE_CHATGROUPS, ext);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(chatroomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
				inquiryMessageLog.setChatroomId(chatroomId);
				inquiryMessageLog.setChatroomName(chatroomName);
				inquiryMessageLog.setFromUserLoginId(fromUserLoginId);
				inquiryMessageLog.setFromUserName(fromUserName);
				inquiryMessageLog.setFromUserPhone(fromUserLoginId.substring(fromUserLoginId.indexOf("_") + 1));
				inquiryMessageLog.setFromUserType(3);
				inquiryMessageLog.setMessagecontent(fileUrl);
				inquiryMessageLog.setMessageType(2);
				inquiryMessageLog.setSendtime(new Date());
				inquiryMessageLog.setCreatetime(new Date());
				inquiryMessageLogService.insert(inquiryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"","secretaryLoginId":"",
	 * "secretaryName":"","length":""} @Title:
	 * sendImgMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @param image @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送语音消息到医生")
	@RequestMapping({ "sendAudioMessageToSingle" })
	@ResponseBody
	public Results<Map<String, Object>> sendAudioMessageToSingle(@RequestParam String parameter,
			HttpServletRequest request, MultipartFile audio) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// ************************************************multipart请求特殊过滤处理******************************************
		// if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************
		if (!audio.isEmpty() && !FileFilterUtils.isMediaFileType(audio.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryName = dataJsonObject.getString("secretaryName");
		Integer length = dataJsonObject.getInteger("length");

		String prefix = secretaryLoginId + "_";
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(secretaryLoginId);
		userInfo.setUserName(secretaryName);
		userInfo.setUserPhone(secretaryLoginId.substring(secretaryLoginId.lastIndexOf("_")+1));
		String fileUrl = UploadFilesUtils.saveFile(mediaLogService,userInfo,request, audio, prefix); // 保存远程文件
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("nickName", "医生助理-" + secretaryName);
		ext.put("headUrl", Config.secretaryIMG);

		Object result = null;
		try {
			String filetempUrl = UploadFilesUtils.cacheFile(request, audio, prefix); // 保存临时文件
			File file = new File(filetempUrl);
			if (file.exists()) {
				result = HttpToEasemobUtil.sendAudio(file, length, doctorsLoginId, secretaryLoginId,
						HttpToEasemobUtil.TARGETTYPE_USERS, ext);
				file.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(secretaryLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				doctorSecretaryMessageLog.setCreatetime(new Date());
				doctorSecretaryMessageLog.setFromUserId(null);
				doctorSecretaryMessageLog.setFromUserName(secretaryName);
				doctorSecretaryMessageLog
						.setFromUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLog.setFromUserType(2);
				doctorSecretaryMessageLog.setMessagecontent(fileUrl);
				doctorSecretaryMessageLog.setMessageType(3);
				doctorSecretaryMessageLog.setSendtime(new Date());
				doctorSecretaryMessageLog.setToUserId(null);
				doctorSecretaryMessageLog.setToUserName(doctorsName);
				doctorSecretaryMessageLog.setToUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

	/**
	 * parameter={"secretaryLoginId":"","secretaryName":"","":"chatroomId":"","
	 * chatroomName":"","length":""} @Title:
	 * sendImgMessageToChatroom @Description: TODO @param @param
	 * parameter @param @param request @param @param image @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "小秘书发送语音消息到聊天室")
	@RequestMapping({ "sendAudioMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendAudioMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request, MultipartFile audio) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// ************************************************multipart请求特殊过滤处理******************************************
		// if (!SecurityUtils.multipartCheck(request)) { // multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************
		if (!audio.isEmpty() && !FileFilterUtils.isMediaFileType(audio.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_202);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("secretaryLoginId");
		String fromUserName = dataJsonObject.getString("secretaryName");
		String chatroomId = dataJsonObject.getString("chatroomId");
		String chatroomName = dataJsonObject.getString("chatroomName");
		Integer length = dataJsonObject.getInteger("length");
		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(chatroomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		// 获取头像地址和昵称
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("nickName", "医生助理-" + fromUserName);
		ext.put("headUrl", Config.secretaryIMG);

		String prefix = fromUserLoginId + "_";
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(fromUserLoginId);
		userInfo.setUserName(fromUserName);
		userInfo.setUserPhone(fromUserLoginId.substring(fromUserLoginId.lastIndexOf("_")+1));
		String fileUrl = UploadFilesUtils.saveFile( mediaLogService,userInfo,request, audio, prefix); // 保存远程文件

		Object result = null;
		try {
			String filetempUrl = UploadFilesUtils.cacheFile(request, audio, prefix); // 保存临时文件
			File file = new File(filetempUrl);
			if (file.exists()) {
				result = HttpToEasemobUtil.sendAudio(file, length, chatroomId, fromUserLoginId,
						HttpToEasemobUtil.TARGETTYPE_CHATGROUPS, ext);
				file.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(chatroomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				InquiryMessageLog inquiryMessageLog = new InquiryMessageLog();
				inquiryMessageLog.setChatroomId(chatroomId);
				inquiryMessageLog.setChatroomName(chatroomName);
				inquiryMessageLog.setFromUserLoginId(fromUserLoginId);
				inquiryMessageLog.setFromUserName(fromUserName);
				inquiryMessageLog.setFromUserPhone(fromUserLoginId.substring(fromUserLoginId.indexOf("_") + 1));
				inquiryMessageLog.setFromUserType(3);
				inquiryMessageLog.setMessagecontent(fileUrl);
				inquiryMessageLog.setMessageType(3);
				inquiryMessageLog.setSendtime(new Date());
				inquiryMessageLog.setCreatetime(new Date());
				inquiryMessageLogService.insert(inquiryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				results.setData(map);
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("消息发送失败");
			}
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("无法发送环信消息");
		}

		return results;
	}

}
