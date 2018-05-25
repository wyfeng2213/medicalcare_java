/**   
* @Title: DoctorsUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:32:23 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.exception.CoderException;
import com.cmcc.medicalcare.model.DoctorsConsultation;
import com.cmcc.medicalcare.model.DoctorsConsultationInvite;
import com.cmcc.medicalcare.model.DoctorsConsultationUserLink;
import com.cmcc.medicalcare.model.DoctorsLoginInfo;
import com.cmcc.medicalcare.model.DoctorsPackage;
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsTeam;
import com.cmcc.medicalcare.model.DoctorsTeamInvite;
import com.cmcc.medicalcare.model.DoctorsTeamUserLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.model.SecretaryDoctorsAdvice;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IDoctorsConsultationInviteService;
import com.cmcc.medicalcare.service.IDoctorsConsultationService;
import com.cmcc.medicalcare.service.IDoctorsConsultationUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsPackageService;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsTeamInviteService;
import com.cmcc.medicalcare.service.IDoctorsTeamService;
import com.cmcc.medicalcare.service.IDoctorsTeamUserLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsAdviceLinkService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;
import com.cmcc.medicalcare.utils.ShuyuanUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobIMUsers;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import io.swagger.client.model.UserName;
import net.sf.json.JSONArray;

/**
 * @ClassName: 医生信息
 * @Description: TODO
 * @author siming.wu
 * @date 2017年4月10日 上午10:32:23
 * 
 */
@Controller
@RequestMapping("/app/doctorsUser")
public class DoctorsUserController {

	@Resource
	private IDoctorsUserService doctorsUserService;

	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;

	@Resource
	private ISecretaryDoctorsLinkService secretaryDoctorsLinkService;

	@Resource
	private IDoctorsTeamService doctorsTeamService;

	@Resource
	private IDoctorsTeamUserLinkService doctorsTeamUserLinkService;

	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;

	@Resource
	private IPatientLoginInfoService patientLoginInfoService;

	@Resource
	private IDoctorsConsultationUserLinkService doctorsConsultationUserLinkService;

	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;

	@Resource
	private IDoctorsTeamInviteService doctorsTeamInviteService;

	@Resource
	private IDoctorsPackageService doctorsPackageService;

	@Resource
	private IDoctorsConsultationService doctorsConsultationService;

	@Resource
	private IDoctorsConsultationInviteService doctorsConsultationInviteService;

	@Resource
	private ISecretaryDoctorsAdviceLinkService secretaryDoctorsAdviceService;

	@Resource
	private ISecretaryAndDoctorsAndPatientOfChatroomService secretaryAndDoctorsAndPatientOfChatroomService;

	@Resource
	private IMediaLogService mediaLogService;
	
	@Resource
	private ISensitiveRecordLogService sensitiveRecordLogService;

	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#insert(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "插入医生资料")
	@Transactional
	public Results<Map<String, Object>> insert(HttpServletRequest request, String equipmentData, String doctorsUser,
			MultipartFile headFile, MultipartFile[] certificateFiles) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		JSONObject doctorsUserObject = JSONObject.parseObject(doctorsUser);
		System.out.println("doctorsUser:" + doctorsUser);
		System.out.println("equipmentData:" + equipmentData);

		// ************************************************multipart请求特殊过滤处理******************************************
		// if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************

		if (!headFile.isEmpty() && !FileFilterUtils.isMediaFileType(headFile.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		if (certificateFiles != null && certificateFiles.length > 0) {
			for (MultipartFile certificateFile : certificateFiles) {
				if (!FileFilterUtils.isMediaFileType(certificateFile.getOriginalFilename())) {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("不支持该文件格式上传");
					return results;
				}
			}
		}

		String userName;
		String phone;
		if (dataJsonObject != null && doctorsUserObject != null) {
			phone = dataJsonObject.getString("phone");
			userName = dataJsonObject.getString("userName");

			String sex = doctorsUserObject.getString("sex");
			// String headUrl = doctorsUserObject.getString("headUrl");
			String title = doctorsUserObject.getString("title");
			Integer hospitalId = doctorsUserObject.getInteger("hospitalId");
			String hospital = doctorsUserObject.getString("hospital");
			String department = doctorsUserObject.getString("department");
			// String certificateUrl =
			// doctorsUserObject.getString("certificateUrl");
			String departmentTelephone = doctorsUserObject.getString("departmentTelephone");
			String introduction = doctorsUserObject.getString("introduction");
			String recommended = doctorsUserObject.getString("recommended");

			DoctorsUser doctorsUser_ = doctorsUserService.findByParam("selectByPhone", phone);

			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("doctorsPhone", phone);
			paramMap1.put("secretaryPhone", Constant.secretaryPhone);
			List<SecretaryDoctorsLink> secretaryDoctorsLinkList = secretaryDoctorsLinkService
					.getList("selectBySecretaryPhoneAndDoctorsPhone", paramMap1);

			if (doctorsUser_ != null) {
				results.setCode(MessageCode.CODE_405);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_405);
				return results;
			}
			EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
			RegisterUsers users = new RegisterUsers();
			User user;
			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(phone)) {
				user = new User().username(Constant.doctor + phone).password("mingyizaixian123456789");// 环信测试帐号
				users.add(user);
				Object result = easemobIMUsers.createNewIMUserSingle(users);
				if (result == null) {
					results.setCode(MessageCode.CODE_501);
					results.setData(null);
					results.setMessage("注册用户失败");
					return results;
				}

				// 解释result
				JSONObject resultObject = JSONObject.parseObject(result.toString());
				// System.out.println(resultObject.get("entities"));
				JSONArray entities = JSONArray.fromObject(resultObject.get("entities"));
				String entity = entities.get(0).toString();
				JSONObject entityObject = JSONObject.parseObject(entity);
				String activated = entityObject.getString("activated");
				String uuid = (String) entityObject.getString("uuid");
				System.out.println("activated:::::" + activated);
				System.out.println("uuid:::::" + uuid);
				// if (activated != null && "true".equals(activated)) {
				if (result != null) {
					Nickname nickName = new Nickname();
					nickName.setNickname(userName);
					easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.doctor + phone, nickName);

					String prefix = "doctors_" + phone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
							+ "_";
					UserInfo userInfo = new UserInfo();
					userInfo.setUserLoginId(Constant.doctor + phone);
					userInfo.setUserName(userName);
					userInfo.setUserPhone(phone);
					String headUrl = UploadFilesUtils.saveFile(mediaLogService, userInfo, request, headFile, prefix); // 保存头像文件
					String certificateUrl = null;
					if (certificateFiles != null)
						certificateUrl = UploadFilesUtils.saveFiles(mediaLogService, userInfo, request,
								certificateFiles); // 保存执照证件

					DoctorsUser doctorsUser1 = new DoctorsUser();
					doctorsUser1.setEasemobUuid(uuid);// 环信返回来的uuid
					doctorsUser1.setPhone(phone);
					try {
						doctorsUser1.setPassword(DES.encryptDESBeas64("mingyizaixian123456789"));
					} catch (CoderException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					doctorsUser1.setSex(sex);
					doctorsUser1.setTitle(title);
					doctorsUser1.setName(userName);
					doctorsUser1.setCertificateUrl(certificateUrl);
					doctorsUser1.setDepartment(department);
					doctorsUser1.setDepartmentTelephone(departmentTelephone);
					doctorsUser1.setHeadUrl(headUrl);
					doctorsUser1.setHospital(hospital);
					doctorsUser1.setHospitalId(hospitalId);
					doctorsUser1.setIntroduction(introduction);
					doctorsUser1.setCreatetime(new Date());
					doctorsUser1.setLoginId(Constant.doctor + phone);
					doctorsUser1.setDoctortype(1);
					doctorsUser1.setRecommended(recommended);
					doctorsUserService.insert(doctorsUser1);

					String hPhonenumber = phone;
					DoctorsLoginInfo doctorsLoginInfo_ = doctorsLoginInfoService.findByParam("selectByPhone",
							hPhonenumber);
					doctorsLoginInfo_.setDoctorsName(userName);
					doctorsLoginInfo_.setUpdatetime(new Date());
					doctorsLoginInfoService.update("updateByPrimaryKeySelective", doctorsLoginInfo_);

					// 初始化团队
					DoctorsTeam doctorsTeam = new DoctorsTeam();
					doctorsTeam.setCreatetime(new Date());
					doctorsTeam.setDoctorsLoginId(Constant.doctor + phone);
					doctorsTeam.setDoctorsName(userName);
					doctorsTeam.setDoctorsPhone(phone);
					doctorsTeam.setDoctorsHeadUrl(headUrl);
					doctorsTeam.setIntroduction(null);
					doctorsTeam.setName(hospital);
					doctorsTeamService.insert(doctorsTeam);

					DoctorsTeamUserLink doctorsTeamUserLink = new DoctorsTeamUserLink();
					doctorsTeamUserLink.setCreatetime(new Date());
					doctorsTeamUserLink.setDoctorsLoginId(Constant.doctor + phone);
					doctorsTeamUserLink.setDoctorsName(userName);
					doctorsTeamUserLink.setDoctorsHeadUrl(headUrl);
					doctorsTeamUserLink.setDoctorsPhone(phone);
					doctorsTeamUserLink.setTeamName(hospital);
					doctorsTeamUserLink.setIsleader(1);
					doctorsTeamUserLink.setStatus(0);
					doctorsTeamUserLink.setTeamId(doctorsTeam.getId());
					doctorsTeamUserLinkService.insert(doctorsTeamUserLink);

					SecretaryDoctorsLink secretaryDoctorsLink = new SecretaryDoctorsLink();
					secretaryDoctorsLink.setCreatetime(new Date());
					secretaryDoctorsLink.setDoctorsLoginId(Constant.doctor + phone);
					secretaryDoctorsLink.setDoctorsName(userName);
					secretaryDoctorsLink.setDoctorsPhone(phone);
					secretaryDoctorsLink.setSecretaryLoginId(Constant.secretary + Constant.secretaryPhone);
					secretaryDoctorsLink.setSecretaryName(Constant.secretaryName);
					secretaryDoctorsLink.setSecretaryPhone(Constant.secretaryPhone);

					if (secretaryDoctorsLinkList != null && secretaryDoctorsLinkList.size() < 1) {
						try {
							secretaryDoctorsLinkService.insert(secretaryDoctorsLink);
							Map<String, Object> ext = new HashMap<String, Object>();
							ext.put("nickName", "医生助理-" + secretaryDoctorsLink.getSecretaryName());
							EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
							Msg msg = new Msg();
							MsgContent msgContent = new MsgContent();

							msgContent.type(MsgContent.TypeEnum.TXT).msg("欢迎加入");
							UserName userName2 = new UserName();
							userName2.add(Constant.doctor + phone);
							msg.from(Constant.secretary + Constant.secretaryPhone).target(userName2).targetType("users")
									.msg(msgContent).ext(ext);
							result = easemobSendMessage.sendMessage(msg);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					Integer id2 = secretaryDoctorsLink.getId();
					Integer id = doctorsUser1.getId();
					if (id != null && id2 != null) {
						results.setCode(200);
						map.put("doctorsUser", doctorsUser1);
						map.put("secretaryDoctorsLink", secretaryDoctorsLink);
						results.setData(map);
						results.setMessage(MessageCode.MESSAGE_200);
						return results;
					} else {
						results.setCode(MessageCode.CODE_501);
						results.setData(null);
						results.setMessage("注册用户失败");
						return results;
					}

				} else {
					results.setCode(MessageCode.CODE_501);
					results.setData(null);
					results.setMessage("注册用户失败");
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_201);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_201);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}

	/*
	 * (非 Javadoc) <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#update(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@SystemControllerLog(description = "更新医生资料")
	public Results<String> update(DoctorsUser doctorsUser, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Results<String> results = new Results<String>();

		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone;
		String userName;
		if (dataJsonObject != null) {
			phone = dataJsonObject.getString("phone");
			userName = dataJsonObject.getString("userName");
			if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(userName)) {
				DoctorsUser doctorsUser1 = doctorsUser;
				doctorsUser1.setPhone(phone);
				doctorsUser1.setName(userName);
				doctorsUser1.setUpdatetime(new Date());
				doctorsUserService.update("updateByPhone", doctorsUser1);
				results.setCode(MessageCode.CODE_200);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		return results;
	}

	/*
	 * (非 Javadoc) <p>Title: select</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#select(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询医生资料")
	public Results<Map<String, Object>> select(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// List<DoctorsPatientLink> listDoctorsPatientLink = null;
		DoctorsUser doctorsUser;
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone;
		if (dataJsonObject != null) {
			phone = dataJsonObject.getString("phone");
			if (StringUtils.isNotBlank(phone)) {
				doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);

				// Map<String, Object> paramMap = new HashMap<String, Object>();
				// paramMap.put("doctorsPhone", phone);
				// listDoctorsPatientLink =
				// doctorsPatientLinkService.getList("selectByPhone", phone);

				if (doctorsUser != null) {

					if (doctorsUser.getStatus() == 0) {
						results.setCode(MessageCode.CODE_204);
						results.setMessage(doctorsUser.getName() + "医生您好，您的注册申请正在审核中，审核结果将以短信通知您，感谢支持！");
						results.setData(null);
						return results;
					} else if (doctorsUser.getStatus() == 2) {
						results.setCode(MessageCode.CODE_205);
						results.setMessage(doctorsUser.getName() + "医生您好，您的注册申请不通过，请修改或补充相关资料，感谢支持！");
						results.setData(null);
						return results;
					}

					Map<String, Object> paramMap1 = new HashMap<String, Object>();
					paramMap1.put("doctorsPhone", phone);
					// paramMap1.put("secretaryPhone", "12345678912");
					List<SecretaryDoctorsLink> secretaryDoctorsLinkList = secretaryDoctorsLinkService
							.getList("selectBySecretaryPhoneAndDoctorsPhone", paramMap1);

					SecretaryDoctorsLink secretaryDoctorsLink = null;
					if (secretaryDoctorsLinkList != null && secretaryDoctorsLinkList.size() > 0) {
						secretaryDoctorsLink = secretaryDoctorsLinkList.get(0);
					}

					// try {
					// Map<String, Object> ext = new HashMap<String, Object>();
					// ext.put("nickName",
					// "医生助理-"+secretaryDoctorsLink.getSecretaryName());
					// EasemobSendMessage easemobSendMessage = new
					// EasemobSendMessage();
					// Msg msg = new Msg();
					// MsgContent msgContent = new MsgContent();
					//
					// msgContent.type(MsgContent.TypeEnum.TXT).msg("欢迎回来");
					// UserName userName2 = new UserName();
					// userName2.add(Constant.doctor + phone);
					// msg.from(Constant.secretary +
					// Constant.secretaryPhone).target(userName2).targetType("users")
					// .msg(msgContent).ext(ext);
					// Object result = easemobSendMessage.sendMessage(msg);
					// } catch (Exception e) {
					// // TODO: handle exception
					// e.printStackTrace();
					// }
					DoctorsTeam doctorsTeam = null;
					List<DoctorsTeamUserLink> leaderTeamList = doctorsTeamUserLinkService.getList("getLeaderList",
							phone);
					if (leaderTeamList != null && leaderTeamList.size() > 0) {
						DoctorsTeamUserLink doctorsTeamUserLink = leaderTeamList.get(0);
						doctorsTeam = doctorsTeamService.findById("selectByTeamId",
								Integer.valueOf(doctorsTeamUserLink.getTeamId()));
					}
					// DoctorsTeam doctorsTeam =
					// doctorsTeamService.findByParam("selectByPhone", phone);
					if (doctorsTeam != null)
						map.put("leaderTeam", doctorsTeam); // 返回该医生领队的团队
					if (secretaryDoctorsLink != null)
						map.put("secretaryDoctorsLink", secretaryDoctorsLink);
					results.setCode(MessageCode.CODE_200);
					results.setMessage(MessageCode.MESSAGE_200);
					map.put("doctorsUser", doctorsUser);
					// int typeNum = listDoctorsPatientLink.size();
					// map.put("typeNum", typeNum);//未处理“患者请求添加好友信息”条数
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("需要认证资料");
					results.setData(null);
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_201);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_201);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}

	/**
	 * parameter={"doctorsLoginId":""}
	 * 
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生主页")
	@RequestMapping(value = "doctorMainInfo")
	@ResponseBody
	public Results<Map<String, Object>> doctorMainInfo(HttpServletRequest request, String parameter) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorsUser doctorsUser = null;
		DoctorsTeam doctorsLeaderTeam = null;
		List<DoctorsTeam> joinerTeamList = new ArrayList<DoctorsTeam>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");

		if (StringUtils.isNotBlank(doctorsLoginId) && doctorsLoginId.contains("_")) {
			int beginIndex = doctorsLoginId.indexOf("_") + 1;
			String doctorsPhone = doctorsLoginId.substring(beginIndex);
			doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			if (doctorsUser != null) {

				doctorsLeaderTeam = doctorsTeamService.findByParam("selectByPhone", doctorsPhone);

				List<DoctorsTeamUserLink> doctorsTeamUserLinkList = doctorsTeamUserLinkService.getList("getJoinerList",
						doctorsPhone);
				if (doctorsTeamUserLinkList.size() > 0) {
					DoctorsTeam doctorsTeam = null;
					for (int i = 0; i < doctorsTeamUserLinkList.size(); i++) {
						doctorsTeam = doctorsTeamService.findByParam("selectByTeamId",
								doctorsTeamUserLinkList.get(i).getTeamId());
						joinerTeamList.add(doctorsTeam);
					}
				}
				map.put("doctorsUser", doctorsUser);
				map.put("doctorType", doctorsUser.getDoctortype());// 1为普通医生、
																	// 2为签约医生
				map.put("doctorsLeaderTeam", doctorsLeaderTeam);// 自己团队信息
				map.put("joinerTeamList", joinerTeamList);// 加入的团队信息
				results.setData(map);
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("该医生尚未注册");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"doctorsPhone":"","introduction":""}
	 * 
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "更新简介与擅长疾病")
	@RequestMapping(value = "updateIntroduction")
	@ResponseBody
	public Results<Map<String, Object>> updateIntroduction(HttpServletRequest request, String parameter) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		DoctorsUser doctorsUser = null;
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String introduction = dataJsonObject.getString("introduction");
		if (StringUtils.isNotBlank(doctorsPhone)) {
			
			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(introduction);

			if (sensitiveWord != null && sensitiveWord.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
				sensitiveRecordLog.setText(sensitiveWord.getText());
				sensitiveRecordLog.setUserLoginid(Constant.doctor + doctorsPhone);
				sensitiveRecordLog.setUserPhone(doctorsPhone);
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
				results.setMessage("您搜索的内容包含敏感词，搜索失败！");
				return results;
			}
			
			String phone = doctorsPhone;
			doctorsUser = doctorsUserService.findByParam("selectByPhone", phone);
			doctorsUser.setIntroduction(introduction);
			doctorsUser.setUpdatetime(new Date());
			doctorsUserService.update("updateByPhone", doctorsUser);
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
	 * 更换医生头像
	 * 
	 * @param request
	 * @param doctorsPhone
	 * @param headFile
	 * @return
	 */
	@SystemControllerLog(description = "更换医生头像")
	@RequestMapping(value = "replaceHeadPicture")
	@ResponseBody
	public Results<Map<String, Object>> replaceHeadPicture(HttpServletRequest request,
			@RequestParam String doctorsPhone, MultipartFile headFile) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		// ************************************************multipart请求特殊过滤处理******************************************
		// if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************
		if (headFile != null && !FileFilterUtils.isMediaFileType(headFile.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		if (headFile != null) {
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			if (doctorsUser != null) {
				String prefix = "doctor_" + doctorsPhone + "_"
						+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLoginId(doctorsUser.getLoginId());
				userInfo.setUserName(doctorsUser.getName());
				userInfo.setUserPhone(doctorsUser.getPhone());
				String headUrl = UploadFilesUtils.saveFile(mediaLogService, userInfo, request, headFile, prefix); // 保存头像文件
				doctorsUser.setHeadUrl(headUrl);
				doctorsUserService.update("updateByPrimaryKeySelective", doctorsUser);

				// 修改doctors_team表中医生头像
				List<DoctorsTeam> listDoctorsTeam = doctorsTeamService.getList("selectByPhone", doctorsPhone);
				if (listDoctorsTeam.size() > 0) {
					for (int i = 0; i < listDoctorsTeam.size(); i++) {
						listDoctorsTeam.get(i).setDoctorsHeadUrl(headUrl);
						doctorsTeamService.update("updateByPrimaryKeySelective", listDoctorsTeam.get(i));
					}
				}

				// 修改doctors_team_user_link表中医生头像
				List<DoctorsTeamUserLink> listDoctorsTeamUser = doctorsTeamUserLinkService.getList("selectByPhone",
						doctorsPhone);
				if (listDoctorsTeamUser.size() > 0) {
					for (int i = 0; i < listDoctorsTeamUser.size(); i++) {
						listDoctorsTeamUser.get(i).setDoctorsHeadUrl(headUrl);
						doctorsTeamUserLinkService.update("updateByPrimaryKeySelective", listDoctorsTeamUser.get(i));
					}
				}

				// 修改doctors_consultation_user_link表中医生头像
				List<DoctorsConsultationUserLink> listDoctorsConsultationUserLink = doctorsConsultationUserLinkService
						.getList("selectByDoctorsPhone", doctorsPhone);
				if (listDoctorsConsultationUserLink.size() > 0) {
					for (int i = 0; i < listDoctorsConsultationUserLink.size(); i++) {
						listDoctorsConsultationUserLink.get(i).setDoctorsHeadUrl(headUrl);
						doctorsConsultationUserLinkService.update("updateByPrimaryKeySelective",
								listDoctorsConsultationUserLink.get(i));
					}
				}

				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("doctorsUser", doctorsUser);
				results.setData(map);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("尚未注册");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * parameter={"doctorsPhone":"医生电话", "doctorsName":"医生姓名", "sex":"性别",
	 * "title":"职称", "hospital":"就职医院", "department":"科室",
	 * "introduction":"医生简介与擅长疾病"} @Title: checkTeamInvite @Description:
	 * TODO @param @param parameter @param @param request @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "修改医生资料")
	@RequestMapping(value = "modifyDoctorsUserInfo")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> modifyDoctorsUserInfo(HttpServletRequest request,
			@RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		String doctorsName = dataJsonObject.getString("doctorsName");
		String sex = dataJsonObject.getString("sex");
		String title = dataJsonObject.getString("title");
		String hospital = dataJsonObject.getString("hospital");
		String department = dataJsonObject.getString("department");
		String introduction = dataJsonObject.getString("introduction");

		if (StringUtils.isNotBlank(doctorsPhone) && StringUtils.isNotBlank(doctorsName)) {
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			if (doctorsUser != null) {
				// 更新医生名字及相关表
				
				SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(doctorsName);

				if (sensitiveWord != null && sensitiveWord.getType() == 1) {
					SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
					sensitiveRecordLog.setCreatetime(new Date());
					sensitiveRecordLog.setLevel(sensitiveWord.getLevel());
					sensitiveRecordLog.setText(sensitiveWord.getText());
					sensitiveRecordLog.setUserLoginid(Constant.doctor + doctorsPhone);
					sensitiveRecordLog.setUserPhone(doctorsPhone);
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
					results.setMessage("您输入的名字包含敏感词，更改失败！");
					return results;
				}
				
				
				SensitiveWord sensitiveWord2 = SensitiveWordUtil.getInstance().isSensitiveWord(department);

				if (sensitiveWord2 != null && sensitiveWord2.getType() == 1) {
					SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
					sensitiveRecordLog.setCreatetime(new Date());
					sensitiveRecordLog.setLevel(sensitiveWord2.getLevel());
					sensitiveRecordLog.setText(sensitiveWord2.getText());
					sensitiveRecordLog.setUserLoginid(Constant.doctor + doctorsPhone);
					sensitiveRecordLog.setUserPhone(doctorsPhone);
					sensitiveRecordLog.setType(sensitiveWord2.getType());
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
					results.setMessage("您输入的科室包含敏感词，更改失败！");
					return results;
				}
				
				if (!doctorsName.equals(doctorsUser.getName())) { // 名字有改动
					EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
					Nickname nickName = new Nickname();
					nickName.setNickname(doctorsName);
					easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.doctor + doctorsPhone, nickName);// 修改昵称

					/*
					 * doctors_user doctors_login_info doctors_patient_link
					 * patient_doctors_link doctors_team
					 * doctors_team_invite(invite_phone, invited_phone)
					 * doctors_team_user_link doctors_package
					 * doctors_consultation
					 * doctors_consultation_invite(invite_phone, invited_phone)
					 * doctors_consultation_user_link secretary_doctors_advice
					 * secretary_doctors_link secretary_doctors_patient_chatroom
					 */
					// 更新doctors_user表
					doctorsUser.setName(doctorsName);

					// 更新doctors_login_info表
					List<DoctorsLoginInfo> listDoctorsLoginInfo = doctorsLoginInfoService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsLoginInfo.size() > 0) {
						for (int i = 0; i < listDoctorsLoginInfo.size(); i++) {
							listDoctorsLoginInfo.get(i).setDoctorsName(doctorsName);
							doctorsLoginInfoService.update("updateByPrimaryKeySelective", listDoctorsLoginInfo.get(i));
						}
					}

					// 更新doctors_patient_link表
					List<DoctorsPatientLink> listDoctorsPatientLink = doctorsPatientLinkService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsPatientLink.size() > 0) {
						for (int i = 0; i < listDoctorsPatientLink.size(); i++) {
							listDoctorsPatientLink.get(i).setDoctorsName(doctorsName);
							doctorsPatientLinkService.update("updateByPrimaryKeySelective",
									listDoctorsPatientLink.get(i));
						}
					}

					// 更新patient_doctors_link表
					List<PatientDoctorsLink> listPatientDoctorsLink = patientDoctorsLinkService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listPatientDoctorsLink.size() > 0) {
						for (int i = 0; i < listPatientDoctorsLink.size(); i++) {
							listPatientDoctorsLink.get(i).setDoctorsName(doctorsName);
							patientDoctorsLinkService.update("updateByPrimaryKeySelective",
									listPatientDoctorsLink.get(i));
						}
					}

					// 更新doctors_team表
					List<DoctorsTeam> listDoctorsTeam = doctorsTeamService.getList("selectByDoctorsPhone",
							doctorsPhone);
					if (listDoctorsTeam.size() > 0) {
						for (int i = 0; i < listDoctorsTeam.size(); i++) {
							listDoctorsTeam.get(i).setDoctorsName(doctorsName);
							listDoctorsTeam.get(i).setName(doctorsName + "的团队");
							doctorsTeamService.update("updateByPrimaryKeySelective", listDoctorsTeam.get(i));
						}
					}

					// 更新doctors_team_invite表(invite_phone, invited_phone)
					List<DoctorsTeamInvite> listDoctorsTeamInvite = doctorsTeamInviteService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsTeamInvite.size() > 0) {
						for (int i = 0; i < listDoctorsTeamInvite.size(); i++) {
							if (doctorsPhone.equals(listDoctorsTeamInvite.get(i).getInvitePhone())) {
								listDoctorsTeamInvite.get(i).setInviteName(doctorsName);
								// listDoctorsTeamInvite.get(i).setTeamName(doctorsName
								// + "的团队");
								doctorsTeamInviteService.update("updateByPrimaryKeySelective",
										listDoctorsTeamInvite.get(i));
							} else if (doctorsPhone.equals(listDoctorsTeamInvite.get(i).getInvitedPhone())) {
								listDoctorsTeamInvite.get(i).setInvitedName(doctorsName);
								doctorsTeamInviteService.update("updateByPrimaryKeySelective",
										listDoctorsTeamInvite.get(i));
							}
						}
					}

					// 更新doctors_team_user_link表
					List<DoctorsTeamUserLink> listDoctorsTeamUserLink = doctorsTeamUserLinkService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsTeamUserLink.size() > 0) {
						for (int i = 0; i < listDoctorsTeamUserLink.size(); i++) {
							listDoctorsTeamUserLink.get(i).setDoctorsName(doctorsName);
							// listDoctorsTeamUserLink.get(i).setTeamName(doctorsName
							// + "的团队");
							doctorsTeamUserLinkService.update("updateByPrimaryKeySelective",
									listDoctorsTeamUserLink.get(i));
						}
					}

					// 更新doctors_package表
					List<DoctorsPackage> listDoctorsPackage = doctorsPackageService.getList("selectByDoctorsPhone",
							doctorsPhone);
					if (listDoctorsPackage.size() > 0) {
						for (int i = 0; i < listDoctorsPackage.size(); i++) {
							listDoctorsPackage.get(i).setDoctorsName(doctorsName);
							doctorsPackageService.update("updateByPrimaryKeySelective", listDoctorsPackage.get(i));
						}
					}

					// 更新doctors_consultation表
					List<DoctorsConsultation> listDoctorsConsultation = doctorsConsultationService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsConsultation.size() > 0) {
						for (int i = 0; i < listDoctorsConsultation.size(); i++) {
							listDoctorsConsultation.get(i).setDoctorsName(doctorsName);
							doctorsConsultationService.update("updateByPrimaryKeySelective",
									listDoctorsConsultation.get(i));
						}
					}

					// 更新doctors_consultation_invite表(invite_phone,
					// invited_phone)
					List<DoctorsConsultationInvite> listDoctorsConsultationInvite = doctorsConsultationInviteService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsConsultationInvite.size() > 0) {
						for (int i = 0; i < listDoctorsConsultationInvite.size(); i++) {
							if (doctorsPhone.equals(listDoctorsConsultationInvite.get(i).getInvitePhone())) {
								listDoctorsConsultationInvite.get(i).setInviteName(doctorsName);
								doctorsConsultationInviteService.update("updateByPrimaryKeySelective",
										listDoctorsConsultationInvite.get(i));
							} else if (doctorsPhone.equals(listDoctorsConsultationInvite.get(i).getInvitedPhone())) {
								listDoctorsConsultationInvite.get(i).setInvitedName(doctorsName);
								doctorsConsultationInviteService.update("updateByPrimaryKeySelective",
										listDoctorsConsultationInvite.get(i));
							}
						}
					}

					// 更新doctors_consultation_user_link表
					List<DoctorsConsultationUserLink> listDoctorsConsultationUserLink = doctorsConsultationUserLinkService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listDoctorsConsultationUserLink.size() > 0) {
						for (int i = 0; i < listDoctorsConsultationUserLink.size(); i++) {
							listDoctorsConsultationUserLink.get(i).setDoctorsName(doctorsName);
							doctorsConsultationUserLinkService.update("updateByPrimaryKeySelective",
									listDoctorsConsultationUserLink.get(i));
						}
					}

					// 更新secretary_doctors_advice表
					List<SecretaryDoctorsAdvice> listSecretaryDoctorsAdvice = secretaryDoctorsAdviceService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listSecretaryDoctorsAdvice.size() > 0) {
						for (int i = 0; i < listSecretaryDoctorsAdvice.size(); i++) {
							listSecretaryDoctorsAdvice.get(i).setDoctorsName(doctorsName);
							secretaryDoctorsAdviceService.update("updateByPrimaryKeySelective",
									listSecretaryDoctorsAdvice.get(i));
						}
					}

					// 更新secretary_doctors_link表
					List<SecretaryDoctorsLink> listSecretaryDoctorsLink = secretaryDoctorsLinkService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listSecretaryDoctorsLink.size() > 0) {
						for (int i = 0; i < listSecretaryDoctorsLink.size(); i++) {
							listSecretaryDoctorsLink.get(i).setDoctorsName(doctorsName);
							secretaryDoctorsLinkService.update("updateByPrimaryKeySelective",
									listSecretaryDoctorsLink.get(i));
						}
					}

					// 更新secretary_doctors_patient_chatroom表
					List<SecretaryAndDoctorsAndPatientOfChatroom> listSecretaryAndDoctorsAndPatientOfChatroom = secretaryAndDoctorsAndPatientOfChatroomService
							.getList("selectByDoctorsPhone", doctorsPhone);
					if (listSecretaryAndDoctorsAndPatientOfChatroom.size() > 0) {
						for (int i = 0; i < listSecretaryAndDoctorsAndPatientOfChatroom.size(); i++) {
							listSecretaryAndDoctorsAndPatientOfChatroom.get(i).setDoctorsName(doctorsName);
							secretaryAndDoctorsAndPatientOfChatroomService.update("updateByPrimaryKeySelective",
									listSecretaryAndDoctorsAndPatientOfChatroom.get(i));
						}
					}

				}

				// 更新患者其他资料
				if (sex != null)
					doctorsUser.setSex(sex);
				if (title != null)
					doctorsUser.setTitle(title);
				if (hospital != null)
					doctorsUser.setHospital(hospital);
				if (department != null)
					doctorsUser.setDepartment(department);
				if (introduction != null)
					doctorsUser.setIntroduction(introduction);

				doctorsUserService.update("updateByPrimaryKeySelective", doctorsUser);

				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("doctorsUser", doctorsUser);
				results.setData(map);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("尚未注册");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	/**
	 * 
	 * @Title: register @Description: TODO @param @param request @param @param
	 *         parameter @param @return 设定文件 @return Results<Map<String,Object>>
	 *         返回类型 @throws
	 */
	@SystemControllerLog(description = "医生注册")
	@RequestMapping(value = "register")
	@ResponseBody
	public Results<Map<String, Object>> register(HttpServletRequest request, String equipmentData, String doctorsUser,
			MultipartFile headFile, MultipartFile[] certificateFiles) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		JSONObject doctorsUserObject = JSONObject.parseObject(doctorsUser);
		System.out.println("doctorsUser:" + doctorsUser);
		System.out.println("equipmentData:" + equipmentData);

		// ************************************************multipart请求特殊过滤处理******************************************
		// if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
		// results.setCode(MessageCode.CODE_400);
		// results.setMessage("尚未登录，请先登录");
		// return results;
		// }
		// ************************************************multipart请求特殊过滤处理******************************************

		if (!headFile.isEmpty() && !FileFilterUtils.isMediaFileType(headFile.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}

		if (certificateFiles != null && certificateFiles.length > 0) {
			for (MultipartFile certificateFile : certificateFiles) {
				if (!FileFilterUtils.isMediaFileType(certificateFile.getOriginalFilename())) {
					results.setCode(MessageCode.CODE_404);
					results.setMessage("不支持该文件格式上传");
					return results;
				}
			}
		}

		String userName;
		String phone;
		if (dataJsonObject != null && doctorsUserObject != null) {
			phone = dataJsonObject.getString("phone");
			userName = dataJsonObject.getString("userName");

			SensitiveWord sensitiveWord = SensitiveWordUtil.getInstance().isSensitiveWord(userName);

			if (sensitiveWord != null && sensitiveWord.getType() == 1) {

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的名字包含敏感词，注册失败！");
				return results;
			}
			
			String sex = doctorsUserObject.getString("sex");
			// String headUrl = doctorsUserObject.getString("headUrl");
			String title = doctorsUserObject.getString("title");
			String hospital = doctorsUserObject.getString("hospital");
			Integer hospitalId = doctorsUserObject.getInteger("hospitalId");
			String department = doctorsUserObject.getString("department");
			// String certificateUrl =
			// doctorsUserObject.getString("certificateUrl");
			String departmentTelephone = doctorsUserObject.getString("departmentTelephone");
			String introduction = doctorsUserObject.getString("introduction");
			SensitiveWord sensitiveWord2 = SensitiveWordUtil.getInstance().isSensitiveWord(introduction);

			if (sensitiveWord2 != null && sensitiveWord2.getType() == 1) {

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的介绍包含敏感词，注册失败！");
				return results;
			}
			
			String recommended = doctorsUserObject.getString("recommended");

			SensitiveWord sensitiveWord3 = SensitiveWordUtil.getInstance().isSensitiveWord(recommended);

			if (sensitiveWord3 != null && sensitiveWord3.getType() == 1) {

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的备注包含敏感词，注册失败！");
				return results;
			}
			
			DoctorsUser doctorsUser_ = doctorsUserService.findByParam("selectByPhone", phone);

			if (doctorsUser_ != null&&(doctorsUser_.getStatus()==1||doctorsUser_.getStatus()==0)) {
				results.setCode(MessageCode.CODE_405);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_405);
				return results;
			}

			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(phone)) {

				String prefix = "doctors_" + phone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
						+ "_";
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLoginId(Constant.doctor + phone);
				userInfo.setUserName(userName);
				userInfo.setUserPhone(phone);
				String headUrl = UploadFilesUtils.saveFile(mediaLogService, userInfo, request, headFile, prefix); // 保存头像文件
				String certificateUrl = null;
				if (certificateFiles != null)
					certificateUrl = UploadFilesUtils.saveFiles(mediaLogService, userInfo, request, certificateFiles); // 保存执照证件

				DoctorsUser doctorsUser1 = new DoctorsUser();
				doctorsUser1.setEasemobUuid(null);// 环信返回来的uuid
				doctorsUser1.setPhone(phone);
				doctorsUser1.setPassword(null);
				doctorsUser1.setSex(sex);
				doctorsUser1.setTitle(title);
				doctorsUser1.setName(userName);
				doctorsUser1.setCertificateUrl(certificateUrl);
				doctorsUser1.setDepartment(department);
				doctorsUser1.setDepartmentTelephone(departmentTelephone);
				doctorsUser1.setHeadUrl(headUrl);
				doctorsUser1.setHospital(hospital);
				doctorsUser1.setHospitalId(hospitalId);
				doctorsUser1.setIntroduction(introduction);
				doctorsUser1.setCreatetime(new Date());
				doctorsUser1.setLoginId(Constant.doctor + phone);
				doctorsUser1.setDoctortype(1);
				doctorsUser1.setRecommended(recommended);
				doctorsUser1.setStatus(0);
				doctorsUser1.setTestAccount(1);
				if (doctorsUser_ == null){
					doctorsUserService.insert(doctorsUser1);
				}else if(doctorsUser_ != null&&doctorsUser_.getStatus()==2){
					doctorsUser1.setId(doctorsUser_.getId());
					doctorsUserService.update("updateByPrimaryKeySelective", doctorsUser1);
				}
				

				String hPhonenumber = phone;
				DoctorsLoginInfo doctorsLoginInfo_ = doctorsLoginInfoService.findByParam("selectByPhone", hPhonenumber);
				doctorsLoginInfo_.setDoctorsName(userName);
				doctorsLoginInfo_.setUpdatetime(new Date());
				doctorsLoginInfoService.update("updateByPrimaryKeySelective", doctorsLoginInfo_);

				Integer id = doctorsUser1.getId();
				if (id != null) {
					boolean flag = ShuyuanUtils.sendRigsterMsg(userName, phone, 0);// 发送注册通知短信
					results.setCode(200);
					map.put("doctorsUser", doctorsUser1);
					map.put("secretaryDoctorsLink", null);
					results.setData(map);
					results.setMessage("【粤健康】" + doctorsUser1.getName() + "医生您好，您的注册申请正在审核中，审核结果将以短信通知您，感谢支持");
					return results;
				} else {
					results.setCode(MessageCode.CODE_501);
					results.setData(null);
					results.setMessage("注册用户失败");
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_201);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_201);
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}

	/**
	 * parameter={"doctorsPhone":"","graphicConsulting":"1","isQuickConsulting":
	 * "1" ,"isAssistantApply":"1","isVideoConsultation":"1"} @Title:
	 * serviceSetting @Description: TODO @param @param request @param @param
	 * parameter @param @return 设定文件 @return Results<Map<String,Object>>
	 * 返回类型 @throws
	 */
	@SystemControllerLog(description = "服务设置")
	@RequestMapping(value = "serviceSetting")
	@ResponseBody
	public Results<Map<String, Object>> serviceSetting(HttpServletRequest request, String parameter) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorsPhone = dataJsonObject.getString("doctorsPhone");
		Integer graphicConsulting = dataJsonObject.getInteger("graphicConsulting");
		Integer isQuickConsulting = dataJsonObject.getInteger("isQuickConsulting");
		Integer isAssistantApply = dataJsonObject.getInteger("isAssistantApply");
		Integer isVideoConsultation = dataJsonObject.getInteger("isVideoConsultation");
		if (StringUtils.isNotBlank(doctorsPhone)) {
			DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone);
			doctorsUser.setGraphicConsulting(graphicConsulting);
			doctorsUser.setIsQuickConsulting(isQuickConsulting);
			doctorsUser.setIsAssistantApply(isAssistantApply);
			doctorsUser.setIsVideoConsultation(isVideoConsultation);
			doctorsUser.setUpdatetime(new Date());
			doctorsUserService.update("updateByPhone", doctorsUser);

			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}

	}

	@SystemControllerLog(description = "选择在职医院")
	@RequestMapping(value = "chooseHospital")
	@ResponseBody
	public Results<Map<String, Object>> chooseHospital(HttpServletRequest request) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<DoctorsTeam> list = doctorsTeamService.getList("selectAll", null);

		map.put("hospitals", list);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}
}
