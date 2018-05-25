package com.cmcc.medicalcare.controller.app.doctor;

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
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.DoctorSecretaryMessageLog;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IDoctorSecretaryMessageLogService;
import com.cmcc.medicalcare.service.IInquiryMessageLogService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobFile;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

@Controller
@RequestMapping({ "/app/doctorsSendMessage" })
public class DoctorsSendMessageController {
	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private IDoctorSecretaryMessageLogService doctorSecretaryMessageLogService;

	@Resource
	private IInquiryMessageLogService inquiryMessageLogService;

	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;

	@Resource
	private IMediaLogService mediaLogService;

	private EasemobFile easemobFile = new EasemobFile();

	/**
	 * parameter={"doctorsLoginId":"","fromUserName":"","toUserName":"",
	 * "patientLoginId":"","msgContentStr":"","chatroomId":"","nickName":"",
	 * "headUrl":""} @Title: sendTxtMessage @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送文本消息到聊天室")
	@RequestMapping({ "sendTxtMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToChatroom(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("doctorsLoginId");
		String fromUserName = dataJsonObject.getString("fromUserName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		String chatroomId = dataJsonObject.getString("chatroomId");
		String chatroomName = dataJsonObject.getString("chatroomName");

		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(chatroomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		// 获取头像地址和昵称
		String nickName = dataJsonObject.getString("nickName");
		String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		if (nickName != null)
			ext.put("nickName", nickName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);
		ext.put("type", "inquiry");

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
				// 保存问诊消息
				inquiryMessageLogService.saveInquiryMessageLog(chatroomId, chatroomName, fromUserLoginId, fromUserName,
						msgContentStr, 1, 1);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", msgContentStr);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * parameter={"doctorsLoginId":"","doctorsName":"","secretaryName":"",
	 * "headUrl":"", "secretaryLoginId":"","msgContentStr":""} @Title:
	 * sendTxtMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送文本消息给小秘书")
	@RequestMapping({ "sendTxtMessageToSingle" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToSingle(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryName = dataJsonObject.getString("secretaryName");
		String headUrl = dataJsonObject.getString("headUrl");
		String msgContentStr = dataJsonObject.getString("msgContentStr");

		if (StringUtils.isNotBlank(msgContentStr)) {

			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(msgContentStr);

			if (sensitiveWord != null) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());
				sensitiveRecordLog.setUserLoginid(doctorsLoginId);
				sensitiveRecordLog.setUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				sensitiveRecordLog.setType(sensitiveWord.getType());
				sensitiveRecordLog.setToUserName(secretaryName);
				sensitiveRecordLog.setToUserLoginid(secretaryLoginId);
				sensitiveRecordLog.setToUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));

				String equipmentData = request.getParameter("equipmentData");
				JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
				if (dataJsonObject2 != null) {
					String userName = dataJsonObject2.getString("userName");
					sensitiveRecordLog.setUserName(userName);
				}

				if (sensitiveWord.getType() == 1) {

					sensitiveRecordLogService.insert(sensitiveRecordLog);

					results.setCode(MessageCode.CODE_408);
					results.setMessage("您发送的内容包含敏感词，发送失败！");
					return results;

				} else if (sensitiveWord.getType() == 2) {

					sensitiveRecordLogService.insert(sensitiveRecordLog);

					EasemobSendMessage easemobSendMessage2 = new EasemobSendMessage();
					Msg msg = new Msg();
					MsgContent msgContent = new MsgContent();

					Map<String, Object> ext = new HashMap<String, Object>();
					ext.put("sensitiveWord", "对方发送的内容涉及钱财安全，请勿轻易告知银行帐号、交易密码等私人信息");
					ext.put("messageType", "sensitiveWord208");
					if (doctorsName != null)
						ext.put("nickName", doctorsName);
					if (headUrl != null)
						ext.put("headUrl", headUrl);
					// ext.put("type", "inquiry");

					msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
					UserName userName = new UserName();
					userName.add(secretaryLoginId);
					msg.from(doctorsLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
					Object result = easemobSendMessage2.sendMessage(msg);

					if (result != null) {
						System.out.println(result.toString());
						JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
						String dataStr = dataJsonObject1.getString("data");
						JSONObject dataJsonObject3 = JSONObject.parseObject(dataStr);
						String isSuccess = dataJsonObject3.getString(secretaryLoginId);
						if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
							DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
							try {
								doctorSecretaryMessageLog.setCreatetime(new Date());
								doctorSecretaryMessageLog.setFromUserId(null);
								doctorSecretaryMessageLog.setFromUserName(doctorsName);
								doctorSecretaryMessageLog
										.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
								doctorSecretaryMessageLog.setFromUserType(1);
								doctorSecretaryMessageLog.setMessagecontent(msgContentStr);
								doctorSecretaryMessageLog.setMessageType(1);
								doctorSecretaryMessageLog.setSendtime(new Date());
								doctorSecretaryMessageLog.setToUserId(null);
								doctorSecretaryMessageLog.setToUserName(secretaryName);
								doctorSecretaryMessageLog
										.setToUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
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
							map.put("serverTime", dataJsonObject1.getString("timestamp"));
							results.setData(map);
						} else {
							results.setCode(MessageCode.CODE_501);
							results.setMessage("消息发送失败");
						}
					} else {
						results.setCode(MessageCode.CODE_501);
						results.setMessage("无法发送环信消息");
					}

					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功");
					return results;
				}
			}

		}

		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);
		ext.put("type", "inquiry");

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();

			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(secretaryLoginId);
			msg.from(doctorsLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
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
			String isSuccess = dataJsonObject2.getString(secretaryLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				try {
					doctorSecretaryMessageLog.setCreatetime(new Date());
					doctorSecretaryMessageLog.setFromUserId(null);
					doctorSecretaryMessageLog.setFromUserName(doctorsName);
					doctorSecretaryMessageLog
							.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
					doctorSecretaryMessageLog.setFromUserType(1);
					doctorSecretaryMessageLog.setMessagecontent(msgContentStr);
					doctorSecretaryMessageLog.setMessageType(1);
					doctorSecretaryMessageLog.setSendtime(new Date());
					doctorSecretaryMessageLog.setToUserId(null);
					doctorSecretaryMessageLog.setToUserName(secretaryName);
					doctorSecretaryMessageLog
							.setToUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
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
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * 
	 * @Title: sendImgMessageToSingle @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         image @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送图片消息到秘书")
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
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryName = dataJsonObject.getString("secretaryName");
		String headUrl = dataJsonObject.getString("headUrl");

		String prefix = doctorsLoginId + "_";
		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		Object result = null;
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendImage(file, secretaryLoginId, doctorsLoginId,
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
			String isSuccess = dataJsonObject2.getString(secretaryLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				doctorSecretaryMessageLog.setCreatetime(new Date());
				doctorSecretaryMessageLog.setFromUserId(null);
				doctorSecretaryMessageLog.setFromUserName(doctorsName);
				doctorSecretaryMessageLog.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLog.setFromUserType(1);
				doctorSecretaryMessageLog.setMessagecontent(fileUrl);
				doctorSecretaryMessageLog.setMessageType(2);
				doctorSecretaryMessageLog.setSendtime(new Date());
				doctorSecretaryMessageLog.setToUserId(null);
				doctorSecretaryMessageLog.setToUserName(secretaryName);
				doctorSecretaryMessageLog.setToUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * 
	 * @param parameter
	 * @param request
	 * @param image
	 * @return
	 */
	@SystemControllerLog(description = "医生发送图片消息到聊天室")
	@RequestMapping({ "sendImgMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendImgMessageToChatroom(@RequestParam String parameter,
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
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("doctorsLoginId");
		String fromUserName = dataJsonObject.getString("fromUserName");
		String chatroomId = dataJsonObject.getString("chatroomId");
		String chatroomName = dataJsonObject.getString("chatroomName");

		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(chatroomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		// 获取头像地址和昵称
		String nickName = dataJsonObject.getString("nickName");
		String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		if (nickName != null)
			ext.put("nickName", nickName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		String prefix = fromUserLoginId + "_";
		String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(fromUserLoginId);
		userInfo.setUserName(fromUserName);
		userInfo.setUserPhone(fromUserLoginId.substring(fromUserLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		Object result = null;
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
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(chatroomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				// 保存问诊消息
				inquiryMessageLogService.saveInquiryMessageLog(chatroomId, chatroomName, fromUserLoginId, fromUserName,
						fileUrl, 2, 1);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * 
	 * @param parameter
	 * @param request
	 * @param audio
	 * @return
	 */
	@SystemControllerLog(description = "医生发送语音消息到聊天室")
	@RequestMapping({ "sendAudioMessageToChatroom" })
	@ResponseBody
	public Results<Map<String, Object>> sendAudioMessageToChatroom(@RequestParam String parameter,
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
		if (audio != null && !FileFilterUtils.isMediaFileType(audio.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String fromUserLoginId = dataJsonObject.getString("doctorsLoginId");
		String fromUserName = dataJsonObject.getString("fromUserName");
		String chatroomId = dataJsonObject.getString("chatroomId");
		String chatroomName = dataJsonObject.getString("chatroomName");
		Integer length = dataJsonObject.getInteger("length");
		if (!StringUtils.isNotBlank(fromUserLoginId) || !StringUtils.isNotBlank(chatroomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		// 获取头像地址和昵称
		String nickName = dataJsonObject.getString("nickName");
		String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		if (nickName != null)
			ext.put("nickName", nickName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		String prefix = fromUserLoginId + "_";
		String filetempUrl = UploadFilesUtils.cacheFile(request, audio, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(fromUserLoginId);
		userInfo.setUserName(fromUserName);
		userInfo.setUserPhone(fromUserLoginId.substring(fromUserLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		Object result = null;
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendAudio(file, length, chatroomId, fromUserLoginId,
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
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(chatroomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				// 保存问诊消息
				inquiryMessageLogService.saveInquiryMessageLog(chatroomId, chatroomName, fromUserLoginId, fromUserName,
						fileUrl, 3, 1);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * 
	 * @Title: sendAudioMessageToSingle @Description: TODO @param @param
	 *         parameter @param @param request @param @param
	 *         audio @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送语音消息到秘书")
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
		if (audio != null && !FileFilterUtils.isMediaFileType(audio.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String secretaryLoginId = dataJsonObject.getString("secretaryLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String secretaryName = dataJsonObject.getString("secretaryName");
		String headUrl = dataJsonObject.getString("headUrl");
		Integer length = dataJsonObject.getInteger("length");
		if (!StringUtils.isNotBlank(doctorsLoginId) || !StringUtils.isNotBlank(secretaryLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		String prefix = doctorsLoginId + "_";
		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		String filetempUrl = UploadFilesUtils.cacheFile(request, audio, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		Object result = null;
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendAudio(file, length, secretaryLoginId, doctorsLoginId,
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
			// System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(secretaryLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorSecretaryMessageLog doctorSecretaryMessageLog = new DoctorSecretaryMessageLog();
				doctorSecretaryMessageLog.setCreatetime(new Date());
				doctorSecretaryMessageLog.setFromUserId(null);
				doctorSecretaryMessageLog.setFromUserName(doctorsName);
				doctorSecretaryMessageLog.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLog.setFromUserType(1);
				doctorSecretaryMessageLog.setMessagecontent(fileUrl);
				doctorSecretaryMessageLog.setMessageType(3);
				doctorSecretaryMessageLog.setSendtime(new Date());
				doctorSecretaryMessageLog.setToUserId(null);
				doctorSecretaryMessageLog.setToUserName(secretaryName);
				doctorSecretaryMessageLog.setToUserPhone(secretaryLoginId.substring(secretaryLoginId.indexOf("_") + 1));
				doctorSecretaryMessageLogService.insert(doctorSecretaryMessageLog);

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * parameter={"doctorsLoginId":"","doctorsName":"","doctorsHeadUrl":"",
	 * "patientLoginId":"","patientName":"","msgContentStr":"","ordersId":
	 * "订单id"}
	 * 
	 * @Title: sendTxtMessageToSingle @Description: TODO @param @param
	 *         parameter @param @param request @param @return 设定文件 @return
	 *         Results<Map <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送文本消息给患者")
	@RequestMapping({ "doctorSendTxtMessageToPatient" })
	@ResponseBody
	public Results<Map<String, Object>> doctorSendTxtMessageToPatient(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String doctorsHeadUrl = dataJsonObject.getString("doctorsHeadUrl");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (!StringUtils.isNotBlank(patientLoginId) || !StringUtils.isNotBlank(doctorsLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (doctorsHeadUrl != null)
			ext.put("headUrl", doctorsHeadUrl);
		ext.put("type", "inquiry");

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();

			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(patientLoginId);
			msg.from(doctorsLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
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
			String isSuccess = dataJsonObject2.getString(patientLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				// 保存单聊问诊消息
				if (ordersId != null) {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId,
							patientName, 2, msgContentStr, 1, ordersId);
				} else {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId,
							patientName, 2, msgContentStr, 1, null);
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", msgContentStr);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * parameter={"doctorsLoginId":"","doctorsName":"","doctorsHeadUrl":"","
	 * patientLoginId":"","patientName":"","ordersId":"订单id"} @Title:
	 * sendImgMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @param image @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送图片消息到患者")
	@RequestMapping({ "doctorSendImgMessageToPatient" })
	@ResponseBody
	public Results<Map<String, Object>> doctorSendImgMessageToPatient(@RequestParam String parameter,
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
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String doctorsHeadUrl = dataJsonObject.getString("doctorsHeadUrl");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (!StringUtils.isNotBlank(patientLoginId) || !StringUtils.isNotBlank(doctorsLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		String prefix = doctorsLoginId + "_";
		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (doctorsHeadUrl != null)
			ext.put("headUrl", doctorsHeadUrl);

		String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		Object result = null;
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendImage(file, patientLoginId, doctorsLoginId,
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
			String isSuccess = dataJsonObject2.getString(patientLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				// 保存单聊问诊消息
				if (ordersId != null) {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId,
							patientName, 2, fileUrl, 2, ordersId);
				} else {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId,
							patientName, 2, fileUrl, 2, null);
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * parameter={"doctorsLoginId":"","doctorsName":"","doctorsHeadUrl":"","
	 * patientLoginId":"","patientName":"","ordersId":"订单id"} @Title:
	 * doctorSaveImgMessage @Description: TODO @param @param
	 * parameter @param @param request @param @param image @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生端保存图片消息（仅供ios使用）")
	@RequestMapping({ "doctorSaveImgMessage" })
	@ResponseBody
	public Results<Map<String, Object>> doctorSaveImgMessage(@RequestParam String parameter, HttpServletRequest request,
			MultipartFile image) {
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
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String doctorsHeadUrl = dataJsonObject.getString("doctorsHeadUrl");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (!StringUtils.isNotBlank(patientLoginId) || !StringUtils.isNotBlank(doctorsLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		String prefix = doctorsLoginId + "_";
		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (doctorsHeadUrl != null)
			ext.put("headUrl", doctorsHeadUrl);

		String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		if (file.exists()) {
			file.delete();
		}
		// 保存单聊问诊消息
		if (ordersId != null) {
			inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
					2, fileUrl, 2, ordersId);
		} else {
			inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
					2, fileUrl, 2, null);
		}
		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		map.put("msgContentStr", fileUrl);
		results.setData(map);
		return results;
	}

	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"","doctorsHeadUrl":"","
	 * patientLoginId":"","patientName":"","length":"","ordersId":"订单id"
	 * } @Title: sendAudioMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @param audio @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送语音消息到患者")
	@RequestMapping({ "doctorSendAudioMessageToPatient" })
	@ResponseBody
	public Results<Map<String, Object>> doctorSendAudioMessageToPatient(@RequestParam String parameter,
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
		if (audio != null && !FileFilterUtils.isMediaFileType(audio.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String doctorsHeadUrl = dataJsonObject.getString("doctorsHeadUrl");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		Integer length = dataJsonObject.getInteger("length");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (!StringUtils.isNotBlank(patientLoginId) || !StringUtils.isNotBlank(doctorsLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		String prefix = doctorsLoginId + "_";
		Map<String, Object> ext = new HashMap<String, Object>();
		if (doctorsName != null)
			ext.put("nickName", doctorsName);
		if (doctorsHeadUrl != null)
			ext.put("headUrl", doctorsHeadUrl);

		String filetempUrl = UploadFilesUtils.cacheFile(request, audio, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		Object result = null;
		try {
			if (file.exists()) {
				result = HttpToEasemobUtil.sendAudio(file, length, patientLoginId, doctorsLoginId,
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
			// System.out.println(result.toString());
			JSONObject dataJsonObject1 = JSONObject.parseObject(result.toString());
			String dataStr = dataJsonObject1.getString("data");
			JSONObject dataJsonObject2 = JSONObject.parseObject(dataStr);
			String isSuccess = dataJsonObject2.getString(patientLoginId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				// 保存单聊问诊消息
				if (ordersId != null) {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId,
							patientName, 2, fileUrl, 3, ordersId);
				} else {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId,
							patientName, 2, fileUrl, 3, null);
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				map.put("msgContentStr", fileUrl);
				map.put("serverTime", dataJsonObject1.getString("timestamp"));
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
	 * parameter={"doctorsLoginId":"","doctorsName":"","doctorsHeadUrl":"","
	 * patientLoginId":"","patientName":"","length":"","ordersId":"订单id"
	 * } @Title: sendAudioMessageToSingle @Description: TODO @param @param
	 * parameter @param @param request @param @param audio @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生端保存语音消息（仅供ios使用）")
	@RequestMapping({ "doctorSaveAudioMessage" })
	@ResponseBody
	public Results<Map<String, Object>> doctorSaveAudioMessage(@RequestParam String parameter,
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
		if (audio != null && !FileFilterUtils.isMediaFileType(audio.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String doctorsHeadUrl = dataJsonObject.getString("doctorsHeadUrl");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String patientName = dataJsonObject.getString("patientName");
		Integer length = dataJsonObject.getInteger("length");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (!StringUtils.isNotBlank(patientLoginId) || !StringUtils.isNotBlank(doctorsLoginId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
		}

		String prefix = doctorsLoginId + "_";

		String filetempUrl = UploadFilesUtils.cacheFile(request, audio, prefix); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_") + 1));
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件

		if (file.exists()) {
			file.delete();
		}

		// 保存单聊问诊消息
		if (ordersId != null) {
			inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
					2, fileUrl, 3, ordersId);
		} else {
			inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
					2, fileUrl, 3, null);
		}

		results.setCode(MessageCode.CODE_200);
		results.setMessage("成功");
		map.put("msgContentStr", fileUrl);
//		map.put("serverTime", dataJsonObject1.getString("timestamp"));
		results.setData(map);

		return results;
	}

	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"","patientName":"",
	 * "headUrl":"", "patientLoginId":"","msgContentStr":""} @Title:
	 * saveAndValidateTxTByDoctor @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生端保存消息并验证敏感词（仅供ios使用）")
	@RequestMapping({ "saveAndValidateTxTByDoctor" })
	@ResponseBody
	public Results<Map<String, Object>> saveAndValidateTxTByDoctor(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String patientLoginId = dataJsonObject.getString("patientLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String patientName = dataJsonObject.getString("patientName");
		String headUrl = dataJsonObject.getString("headUrl");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		Integer ordersId = dataJsonObject.getInteger("ordersId");

		if (StringUtils.isNotBlank(msgContentStr)) {

			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(msgContentStr);

			if (sensitiveWord != null) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());
				sensitiveRecordLog.setUserLoginid(doctorsLoginId);
				sensitiveRecordLog.setUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				sensitiveRecordLog.setType(sensitiveWord.getType());
				sensitiveRecordLog.setToUserName(patientName);
				sensitiveRecordLog.setToUserLoginid(patientLoginId);
				sensitiveRecordLog.setToUserPhone(patientLoginId.substring(patientLoginId.indexOf("_") + 1));

				String equipmentData = request.getParameter("equipmentData");
				JSONObject dataJsonObject2 = JSONObject.parseObject(equipmentData);
				if (dataJsonObject2 != null) {
					String userName = dataJsonObject2.getString("userName");
					sensitiveRecordLog.setUserName(userName);
				}

				if (sensitiveWord.getType() == 1) {

					sensitiveRecordLogService.insert(sensitiveRecordLog);

					EasemobSendMessage easemobSendMessage2 = new EasemobSendMessage();
					Msg msg = new Msg();
					MsgContent msgContent = new MsgContent();

					Map<String, Object> ext = new HashMap<String, Object>();
					ext.put("sensitiveWord", "你发送的内容包含敏感词，发送失败");
					ext.put("messageType", "sensitiveWord408");
					if (doctorsName != null)
						ext.put("nickName", doctorsName);
					if (headUrl != null)
						ext.put("headUrl", headUrl);
					// ext.put("type", "inquiry");

					msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
					UserName userName = new UserName();
					userName.add(doctorsLoginId);
					msg.from(patientLoginId).target(userName).targetType("users").msg(msgContent).ext(ext);
					Object result = easemobSendMessage2.sendMessage(msg);

					results.setCode(MessageCode.CODE_408);
					results.setMessage("您发送的内容包含敏感词，发送失败！");
					return results;

				} else if (sensitiveWord.getType() == 2) {

					sensitiveRecordLogService.insert(sensitiveRecordLog);

					if (ordersId != null) {
						inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
								2, msgContentStr, 1, ordersId);
					} else {
						inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
								2, msgContentStr, 1, null);
					}

					results.setCode(MessageCode.CODE_208);
					results.setMessage("成功");
					map.put("msgContentStr", msgContentStr);
					map.put("sensitiveWord", "对方发送的内容涉及钱财安全，请勿轻易告知银行帐号、交易密码等私人信息");
					map.put("messageType", "sensitiveWord208");
					results.setData(map);

					return results;
				}
			} else {
				// 保存单聊问诊消息
				if (ordersId != null) {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
							2, msgContentStr, 1, ordersId);
				} else {
					inquiryMessageLogService.saveInquiryMessageLog(doctorsLoginId, doctorsName, 1, patientLoginId, patientName,
							2, msgContentStr, 1, null);
				}
				results.setCode(MessageCode.CODE_200);
				results.setMessage("成功");
				return results;
			}

		}
		results.setCode(MessageCode.CODE_404);
		results.setMessage("消息内容为空");
		return results;
	}

}
