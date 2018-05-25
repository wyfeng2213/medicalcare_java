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
import com.cmcc.medicalcare.model.DoctorsConsultationMessageLog;
import com.cmcc.medicalcare.service.IDoctorsConsultationMessageLogService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobSendMessage;
import com.easemob.server.comm.HttpToEasemobUtil;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;

/**
 * 
 * @ClassName: DoctorsConsultationSendMessageController
 * @Description: TODO
 * @author adminstrator
 * @date 2017年5月19日 上午10:01:03
 *
 */
@Controller
@RequestMapping({ "/app/doctorsConsultationSendMessage" })
public class DoctorsConsultationSendMessageController {

	@Resource
	private IDoctorsConsultationMessageLogService doctorsConsultationMessageLogService;

	@Resource
	private IMediaLogService  mediaLogService;
	
	/**
	 * parameter={"doctorsLoginId":"","doctorsName":"","msgContentStr":"",
	 * "grouproomId":"","grouproomName":"","nickName":"", "headUrl":""} @Title:
	 * sendTxtMessageToGrouproom @Description: TODO @param @param
	 * parameter @param @param request @param @return 设定文件 @return Results<Map
	 * <String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送文本消息到会诊团队")
	@RequestMapping({ "sendTxtMessageToGrouproom" })
	@ResponseBody
	public Results<Map<String, Object>> sendTxtMessageToGrouproom(@RequestParam String parameter,
			HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("parameter:----" + parameter);
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String msgContentStr = dataJsonObject.getString("msgContentStr");
		String grouproomId = dataJsonObject.getString("grouproomId");
		String grouproomName = dataJsonObject.getString("grouproomName");

		if (!StringUtils.isNotBlank(doctorsLoginId) || !StringUtils.isNotBlank(grouproomId)) {
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

		Object result = null;
		try {
			EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
			Msg msg = new Msg();
			MsgContent msgContent = new MsgContent();
			msgContent.type(MsgContent.TypeEnum.TXT).msg(msgContentStr);
			UserName userName = new UserName();
			userName.add(grouproomId);
			msg.from(doctorsLoginId).target(userName).targetType("chatgroups").msg(msgContent).ext(ext);
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
			String isSuccess = dataJsonObject2.getString(grouproomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorsConsultationMessageLog doctorsConsultationMessageLog = new DoctorsConsultationMessageLog();
				try {
					doctorsConsultationMessageLog.setChatGroupId(grouproomId);
					doctorsConsultationMessageLog.setChatGroupName(grouproomName);
					doctorsConsultationMessageLog.setCreatetime(new Date());
					doctorsConsultationMessageLog.setFromUserLoginId(doctorsLoginId);
					doctorsConsultationMessageLog.setFromUserName(doctorsName);
					doctorsConsultationMessageLog
							.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
					doctorsConsultationMessageLog.setFromUserType(1);
					doctorsConsultationMessageLog.setMessagecontent(msgContentStr);
					doctorsConsultationMessageLog.setMessageType(1);
					doctorsConsultationMessageLog.setSendtime(new Date());

					doctorsConsultationMessageLogService.insert(doctorsConsultationMessageLog);
				} catch (Exception e) {
					// TODO: handle exception
					try {
						String msgContentStr_ = EmojiFilter.filterEmoji(msgContentStr);
						doctorsConsultationMessageLog.setMessagecontent(msgContentStr_);
						doctorsConsultationMessageLogService.insert(doctorsConsultationMessageLog);
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
	 * parameter={"doctorsLoginId":"","doctorsName":"","msgContentStr":"",
	 * "grouproomId":"","grouproomName":"","nickName":"", "headUrl":""} @Title:
	 * sendImgMessageToChatroom @Description: TODO @param @param
	 * parameter @param @param request @param @param image @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生发送图片消息到会诊团队")
	@RequestMapping({ "sendImgMessageToGrouproom" })
	@ResponseBody
	public Results<Map<String, Object>> sendImgMessageToGrouproom(@RequestParam String parameter,
			HttpServletRequest request, MultipartFile image) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String grouproomId = dataJsonObject.getString("grouproomId");
		String grouproomName = dataJsonObject.getString("grouproomName");

		if (!StringUtils.isNotBlank(doctorsLoginId) || !StringUtils.isNotBlank(grouproomId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		if(!image.isEmpty()&&!FileFilterUtils.isMediaFileType(image.getOriginalFilename())){
			results.setCode(MessageCode.CODE_202);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		// 获取头像地址和昵称
		String nickName = dataJsonObject.getString("nickName");
		String headUrl = dataJsonObject.getString("headUrl");
		Map<String, Object> ext = new HashMap<String, Object>();
		if (nickName != null)
			ext.put("nickName", nickName);
		if (headUrl != null)
			ext.put("headUrl", headUrl);

		String prefix = doctorsLoginId + "_";
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(doctorsLoginId);
		userInfo.setUserName(doctorsName);
		userInfo.setUserPhone(doctorsLoginId.substring(doctorsLoginId.lastIndexOf("_")+1));
		String fileUrl = UploadFilesUtils.saveFile(mediaLogService,userInfo,request, image, prefix); // 保存远程文件

		Object result = null;
		try {
			String filetempUrl = UploadFilesUtils.cacheFile(request, image, prefix); // 保存临时文件
			File file = new File(filetempUrl);
			if (file.exists()) {
				result = HttpToEasemobUtil.sendImage(file, grouproomId, doctorsLoginId,
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
			String isSuccess = dataJsonObject2.getString(grouproomId);
			if ((StringUtils.isNotBlank(isSuccess)) && ("success".equals(isSuccess))) {
				DoctorsConsultationMessageLog doctorsConsultationMessageLog = new DoctorsConsultationMessageLog();
				
				doctorsConsultationMessageLog.setChatGroupId(grouproomId);
				doctorsConsultationMessageLog.setChatGroupName(grouproomName);
				doctorsConsultationMessageLog.setCreatetime(new Date());
				doctorsConsultationMessageLog.setFromUserLoginId(doctorsLoginId);
				doctorsConsultationMessageLog.setFromUserName(doctorsName);
				doctorsConsultationMessageLog
						.setFromUserPhone(doctorsLoginId.substring(doctorsLoginId.indexOf("_") + 1));
				doctorsConsultationMessageLog.setFromUserType(1);
				doctorsConsultationMessageLog.setMessagecontent(fileUrl);
				doctorsConsultationMessageLog.setMessageType(2);
				doctorsConsultationMessageLog.setSendtime(new Date());
				doctorsConsultationMessageLog.setCreatetime(new Date());
				doctorsConsultationMessageLogService.insert(doctorsConsultationMessageLog);

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
