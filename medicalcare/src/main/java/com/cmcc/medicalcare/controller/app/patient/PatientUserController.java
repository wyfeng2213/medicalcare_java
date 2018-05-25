/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.PatientBloodPressure;
import com.cmcc.medicalcare.model.PatientBloodSugar;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientFastingWeight;
import com.cmcc.medicalcare.model.PatientHealthRecord;
import com.cmcc.medicalcare.model.PatientLoginInfo;
import com.cmcc.medicalcare.model.PatientMedicalRecord;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.model.SecretaryAndDoctorsAndPatientOfChatroom;
import com.cmcc.medicalcare.model.SecretaryDoctorsAdvice;
import com.cmcc.medicalcare.model.SensitiveRecordLog;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IDoctorsConsultationService;
import com.cmcc.medicalcare.service.IDoctorsLoginInfoService;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IPatientBloodPressureService;
import com.cmcc.medicalcare.service.IPatientBloodSugarService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientFastingWeightService;
import com.cmcc.medicalcare.service.IPatientHealthRecordService;
import com.cmcc.medicalcare.service.IPatientLoginInfoService;
import com.cmcc.medicalcare.service.IPatientMedicalRecordService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.service.ISecretaryAndDoctorsAndPatientOfChatroomService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsAdviceLinkService;
import com.cmcc.medicalcare.service.ISensitiveRecordLogService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.SensitiveWordUtil;
import com.cmcc.medicalcare.utils.Toolkit;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.utils.ValidationAIUtil;
import com.cmcc.medicalcare.vo.UserInfo;
import com.easemob.server.api.impl.EasemobChatGroup;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import net.sf.json.JSONArray;

/**
 * @ClassName: 患者信息
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/app/patientUser")
public class PatientUserController {
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;
	
	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;
	
	@Resource
	private IDoctorsLoginInfoService doctorsLoginInfoService;
	
	@Resource
	private IPatientLoginInfoService patientLoginInfoService;
	
	@Resource
	private IPatientBloodPressureService patientBloodPressureService;
	
	@Resource
	private IPatientHealthRecordService patientHealthRecordService;
	
	@Resource
	private IPatientMedicalRecordService patientMedicalRecordService;
	
	@Resource
	private IPatientBloodSugarService patientBloodSugarService;
	
	@Resource
	private IPatientFastingWeightService patientFastingWeightService;
	
	@Resource
	private IDoctorsConsultationService doctorsConsultationService;
	
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
	@SystemControllerLog(description = "插入患者资料")
	public Results<Map<String, Object>> insert(HttpServletRequest request,@RequestParam String equipmentData,@RequestParam String patientUser, MultipartFile headFile) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		JSONObject patientUserObject = JSONObject.parseObject(patientUser);
		//************************************************multipart请求特殊过滤处理******************************************
//		if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		//************************************************multipart请求特殊过滤处理******************************************
		if(headFile!=null&&!FileFilterUtils.isMediaFileType(headFile.getOriginalFilename())){
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		String userName;
		String phone;
		if (dataJsonObject != null && patientUserObject != null) {
			phone = dataJsonObject.getString("phone");
			userName = dataJsonObject.getString("userName");

			PatientUser patientUser_ = patientUserService.findByParam("selectByPhone", phone);
			if (patientUser_ != null) {
				results.setCode(MessageCode.CODE_405);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_405);
				return results;
			}
			
			//************************************************敏感词过滤_开始******************************************
			SensitiveWord sensitiveWord1 = SensitiveWordUtil.getInstance().isSensitiveWord(userName);
			if (sensitiveWord1 != null && sensitiveWord1.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord1.getLevel());
				sensitiveRecordLog.setText(sensitiveWord1.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + phone);
				sensitiveRecordLog.setUserPhone(phone);
				sensitiveRecordLog.setType(sensitiveWord1.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的姓名包含敏感词，注册失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord2 = SensitiveWordUtil.getInstance().isSensitiveWord(patientUserObject.getString("visitState"));
			if (sensitiveWord2 != null && sensitiveWord2.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord2.getLevel());
				sensitiveRecordLog.setText(sensitiveWord2.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + phone);
				sensitiveRecordLog.setUserPhone(phone);
				sensitiveRecordLog.setType(sensitiveWord2.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的内容包含敏感词，注册失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord3 = SensitiveWordUtil.getInstance().isSensitiveWord(patientUserObject.getString("hospital"));
			if (sensitiveWord3 != null && sensitiveWord3.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord3.getLevel());
				sensitiveRecordLog.setText(sensitiveWord3.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + phone);
				sensitiveRecordLog.setUserPhone(phone);
				sensitiveRecordLog.setType(sensitiveWord3.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的内容包含敏感词，注册失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord4 = SensitiveWordUtil.getInstance().isSensitiveWord(patientUserObject.getString("medicalCard"));
			if (sensitiveWord4 != null && sensitiveWord4.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord4.getLevel());
				sensitiveRecordLog.setText(sensitiveWord4.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + phone);
				sensitiveRecordLog.setUserPhone(phone);
				sensitiveRecordLog.setType(sensitiveWord4.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的内容包含敏感词，注册失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord5 = SensitiveWordUtil.getInstance().isSensitiveWord(patientUserObject.getString("remarks"));
			if (sensitiveWord5 != null && sensitiveWord5.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord5.getLevel());
				sensitiveRecordLog.setText(sensitiveWord5.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + phone);
				sensitiveRecordLog.setUserPhone(phone);
				sensitiveRecordLog.setType(sensitiveWord5.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的内容包含敏感词，注册失败！");
				return results;
			}
			
			SensitiveWord sensitiveWord6 = SensitiveWordUtil.getInstance().isSensitiveWord(patientUserObject.getString("identification"));
			if (sensitiveWord6 != null && sensitiveWord6.getType() == 1) {
				SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
				sensitiveRecordLog.setCreatetime(new Date());
				sensitiveRecordLog.setLevel(sensitiveWord6.getLevel());
				sensitiveRecordLog.setText(sensitiveWord6.getText());
				sensitiveRecordLog.setUserLoginid(Constant.patient + phone);
				sensitiveRecordLog.setUserPhone(phone);
				sensitiveRecordLog.setType(sensitiveWord6.getType());
				sensitiveRecordLog.setToUserName(null);
				sensitiveRecordLog.setToUserLoginid(null);
				sensitiveRecordLog.setToUserPhone(null);
				sensitiveRecordLog.setUserName(userName);
				sensitiveRecordLogService.insert(sensitiveRecordLog);

				results.setCode(MessageCode.CODE_404);
				results.setMessage("您输入的内容包含敏感词，注册失败！");
				return results;
			}
			//************************************************敏感词过滤_结束******************************************
			
			EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
			RegisterUsers users = new RegisterUsers();
			User user;
			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(phone)) {
				user = new User().username(Constant.patient + phone).password("mingyizaixian123456789");// 环信测试帐号
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
				 System.out.println(resultObject.get("entities"));
				JSONArray entities = JSONArray.fromObject(resultObject.get("entities"));
				String entity = entities.get(0).toString();
				JSONObject entityObject = JSONObject.parseObject(entity);
				String activated = entityObject.getString("activated");
				String uuid = (String) entityObject.getString("uuid");
				// System.out.println(":::::" + activated);
				// System.out.println(":::::" + uuid);
//				if (activated != null && "true".equals(activated)) {
				Nickname nickName = new Nickname();
				nickName.setNickname(userName);
				easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.patient + phone, nickName);//修改昵称
				
				String prefix = "patient_" + phone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLoginId(Constant.patient+phone);
				userInfo.setUserName(userName);
				userInfo.setUserPhone(phone);
				String headUrl = UploadFilesUtils.saveFile(mediaLogService,  userInfo,request, headFile, prefix); //保存头像文件
				
				PatientUser patientUser1 = new PatientUser();
				patientUser1.setEasemobUuid(uuid);// 环信返回来的uuid
				try {
					patientUser1.setPassword(DES.encryptDESBeas64("mingyizaixian123456789"));
				} catch (CoderException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				patientUser1.setLoginId(Constant.patient + phone);
				patientUser1.setPhone(phone);
				patientUser1.setName(userName);
				patientUser1.setSex(patientUserObject.getString("sex"));
				try {
					patientUser1.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patientUserObject.getString("birthday")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					patientUser1.setBirthday(null);
				}
				patientUser1.setHeadUrl(headUrl);
				patientUser1.setVisitState(patientUserObject.getString("visitState"));
				patientUser1.setHospital(patientUserObject.getString("hospital"));
				patientUser1.setMedicalCard(patientUserObject.getString("medicalCard"));
				patientUser1.setRemarks(EmojiFilter.filterEmoji(patientUserObject.getString("remarks")));
				patientUser1.setCreatetime(new Date());
				String identification = patientUserObject.getString("identification");
				if (StringUtils.isNotBlank(identification)) {
					if (false == ValidationAIUtil.isIDCard(identification)) {
						results.setCode(MessageCode.CODE_201);
						results.setMessage("请输入正确的身份证号码");
						return results;
					}
					
					try {
						patientUser1.setIdentification(DES.encryptDESBeas64(identification)); //身份证加密保存
					} catch (CoderException e) {
						e.printStackTrace();
					}
				}
				
				patientUserService.insert(patientUser1);
				Integer id = patientUser1.getId();
				if (id != null) {
					results.setCode(200);
					map.put("patientUser", patientUser1);
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
	 * (非 Javadoc) <p>Title: delete</p> <p>Description: </p>
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @see
	 * com.cmcc.medicalcare.controller.AppBaseController#delete(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@RequestMapping(value = "delete") 
	@ResponseBody 
	public Results<String> delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
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
	@SystemControllerLog(description = "更新患者资料")
	public Results<String> update(PatientUser patientUser, HttpServletRequest request) {
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
				PatientUser patientUser1 = patientUser;
				patientUser1.setPhone(phone);
				patientUser1.setName(userName);
				patientUser1.setUpdatetime(new Date());
				patientUserService.update("updateByPhone", patientUser1);
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
	@SystemControllerLog(description = "查询患者资料")
	public Results<Map<String, Object>> select(HttpServletRequest request) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		PatientUser patientUser;
		String patientPhone = request.getParameter("patientPhone");
		if (StringUtils.isNotBlank(patientPhone)) {
			patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			if (patientUser != null) {
				if (StringUtils.isNotBlank(patientUser.getIdentification())) { //解密身份证
					try {
						patientUser.setIdentification(DES.decryptBeas64DES(patientUser.getIdentification()));
					} catch (CoderException e) {
						e.printStackTrace();
					}
				}
				
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				
				if (StringUtils.isBlank(patientUser.getBpDeviceid()) 
						|| StringUtils.isBlank(patientUser.getBpDeviceid())) {
					String httpUrl_nj = "http://app.careeach.com/app_api/v2.4.1/json_201411/getDeveice.jsp";
					String param = "action=deviceBindInfo&phone=" + patientPhone;
					String result_nj = HTTPUtils.sendGet(httpUrl_nj, param, "UTF-8");
					if (StringUtils.isNotBlank(result_nj)) {
		    			JSONObject dataJsonObject = JSONObject.parseObject(result_nj);
		    			String status = dataJsonObject.getString("status");
						if ("200".equals(status)) {
							String data = dataJsonObject.getString("data");
							if (data != null) {
								JSONArray dataJSONArray = JSONArray.fromObject(data);
								for (int i=0; i<dataJSONArray.size(); i++) {
									String entity = dataJSONArray.get(i).toString();
									JSONObject entityObject = JSONObject.parseObject(entity);
									String devType = entityObject.getString("devType");
									String devNo = (String) entityObject.getString("devNo");
									if ("1".equals(devType)) {
										patientUser.setBpDeviceid(devNo);
									} else if ("2".equals(devType)) {
										patientUser.setBsDeviceid(devNo);
									}
								}
							}
						}
		    		}
				}
				
				map.put("patientUser", patientUser);
				results.setData(map);
				return results;
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage(MessageCode.MESSAGE_404);
				results.setData(null);
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
	 * 更换患者头像
	 * @param request
	 * @param patientPhone
	 * @param headFile
	 * @return
	 */
	@SystemControllerLog(description = "更换患者头像")
	@RequestMapping(value = "replaceHeadPicture")
	@ResponseBody
	public Results<Map<String, Object>> replaceHeadPicture(HttpServletRequest request, @RequestParam String patientPhone, MultipartFile headFile) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		//************************************************multipart请求特殊过滤处理******************************************
//		if(!SecurityUtils.multipartCheck(request)){ //multipart请求特殊过滤处理
//			results.setCode(MessageCode.CODE_400);
//			results.setMessage("尚未登录，请先登录");
//			return results;
//		}
		//************************************************multipart请求特殊过滤处理******************************************
		if(!headFile.isEmpty()&&!FileFilterUtils.isMediaFileType(headFile.getOriginalFilename())){
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		if (headFile != null) {
			PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			if (patientUser != null) {
				String prefix = "patient_" + patientPhone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
				UserInfo userInfo = new UserInfo();
				userInfo.setUserLoginId(patientUser.getLoginId());
				userInfo.setUserName(patientUser.getName());
				userInfo.setUserPhone(patientUser.getPhone());
				String headUrl = UploadFilesUtils.saveFile(mediaLogService,userInfo,request, headFile, prefix); //保存头像文件
				patientUser.setHeadUrl(headUrl);
				patientUserService.update("updateByPrimaryKeySelective", patientUser);
				
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
				map.put("patientUser", patientUser);
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
	 * parameter={"patientPhone":"", "patientName":"", "sex":"", "birthday":"", "identification":""}
	* @Title: checkTeamInvite 
	* @Description: TODO 
	* @param @param parameter
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@SystemControllerLog(description = "修改患者资料")
	@RequestMapping(value = "modifyPatientUserInfo")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> modifyPatientUserInfo(HttpServletRequest request, @RequestParam String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String patientName = dataJsonObject.getString("patientName");
		String sex = dataJsonObject.getString("sex");
		String birthday = dataJsonObject.getString("birthday");
		String identification = dataJsonObject.getString("identification");
		
		if (StringUtils.isBlank(patientPhone) || StringUtils.isBlank(patientName)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		//************************************************敏感词过滤_开始******************************************
		SensitiveWord sensitiveWord1 = SensitiveWordUtil.getInstance().isSensitiveWord(patientName);
		if (sensitiveWord1 != null && sensitiveWord1.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord1.getLevel());
			sensitiveRecordLog.setText(sensitiveWord1.getText());
			sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
			sensitiveRecordLog.setUserPhone(patientPhone);
			sensitiveRecordLog.setType(sensitiveWord1.getType());
			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);
			sensitiveRecordLog.setUserName(patientName);
			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("您输入的姓名包含敏感词，修改失败！");
			return results;
		}
		
		SensitiveWord sensitiveWord2 = SensitiveWordUtil.getInstance().isSensitiveWord(identification);
		if (sensitiveWord2 != null && sensitiveWord2.getType() == 1) {
			SensitiveRecordLog sensitiveRecordLog = new SensitiveRecordLog();
			sensitiveRecordLog.setCreatetime(new Date());
			sensitiveRecordLog.setLevel(sensitiveWord2.getLevel());
			sensitiveRecordLog.setText(sensitiveWord2.getText());
			sensitiveRecordLog.setUserLoginid(Constant.patient + patientPhone);
			sensitiveRecordLog.setUserPhone(patientPhone);
			sensitiveRecordLog.setType(sensitiveWord2.getType());
			sensitiveRecordLog.setToUserName(null);
			sensitiveRecordLog.setToUserLoginid(null);
			sensitiveRecordLog.setToUserPhone(null);
			sensitiveRecordLog.setUserName(patientName);
			sensitiveRecordLogService.insert(sensitiveRecordLog);

			results.setCode(MessageCode.CODE_404);
			results.setMessage("您输入的身份证包含敏感词，修改失败！");
			return results;
		}
		//************************************************敏感词过滤_结束******************************************
		
		PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
		if (patientUser != null) {
			//更新患者名字及相关表
			if (!patientName.equals(patientUser.getName())) { //名字有改动
				EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
				Nickname nickName = new Nickname();
				nickName.setNickname(patientName);
				easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.patient + patientPhone, nickName);//修改昵称
				
				/*patient_user
				patient_login_info
				patient_blood_pressure
				patient_health_record
				patient_medical_record
				patient_blood_sugar
				patient_fasting_weight
				doctors_patient_link
				patient_doctors_link
				doctors_consultation
				secretary_doctors_advice
				secretary_doctors_patient_chatroom*/
				//更新patient_user表
				patientUser.setName(patientName); 
				
				//更新patient_login_info表
				List<PatientLoginInfo> listPatientLoginInfo = patientLoginInfoService.getList("selectByPatientPhone", patientPhone);
				if (listPatientLoginInfo.size() > 0) {
					for (int i=0; i<listPatientLoginInfo.size(); i++) {
						listPatientLoginInfo.get(i).setPatientName(patientName);
						patientLoginInfoService.update("updateByPrimaryKeySelective", listPatientLoginInfo.get(i));
					}
				}
				
				//更新patient_blood_pressure表
				List<PatientBloodPressure> listPatientBloodPressure = patientBloodPressureService.getList("selectByPatientPhone", patientPhone);
				if (listPatientBloodPressure.size() > 0) {
					for (int i=0; i<listPatientBloodPressure.size(); i++) {
						listPatientBloodPressure.get(i).setPatientName(patientName);
						patientBloodPressureService.update("updateByPrimaryKeySelective", listPatientBloodPressure.get(i));
					}
				}
				
				//更新patient_health_record表
				List<PatientHealthRecord> listPatientHealthRecord = patientHealthRecordService.getList("selectByPatientPhone", patientPhone);
				if (listPatientHealthRecord.size() > 0) {
					for (int i=0; i<listPatientHealthRecord.size(); i++) {
						listPatientHealthRecord.get(i).setPatientName(patientName);
						patientHealthRecordService.update("updateByPrimaryKeySelective", listPatientHealthRecord.get(i));
					}
				}
				
				//更新patient_medical_record表
				List<PatientMedicalRecord> listPatientMedicalRecord = patientMedicalRecordService.getList("selectByPatientPhone", patientPhone);
				if (listPatientMedicalRecord.size() > 0) {
					for (int i=0; i<listPatientMedicalRecord.size(); i++) {
						listPatientMedicalRecord.get(i).setPatinetName(patientName);
						patientMedicalRecordService.update("updateByPrimaryKeySelective", listPatientMedicalRecord.get(i));
					}
				}
				
				//更新patient_blood_sugar表
				List<PatientBloodSugar> listPatientBloodSugar = patientBloodSugarService.getList("selectByPatientPhone", patientPhone);
				if (listPatientBloodSugar.size() > 0) {
					for (int i=0; i<listPatientBloodSugar.size(); i++) {
						listPatientBloodSugar.get(i).setPatientName(patientName);
						patientBloodSugarService.update("updateByPrimaryKeySelective", listPatientBloodSugar.get(i));
					}
				}
				
				//更新patient_fasting_weight表
				List<PatientFastingWeight> listPatientFastingWeight = patientFastingWeightService.getList("selectByPatientPhone", patientPhone);
				if (listPatientFastingWeight.size() > 0) {
					for (int i=0; i<listPatientFastingWeight.size(); i++) {
						listPatientFastingWeight.get(i).setPatientName(patientName);
						patientFastingWeightService.update("updateByPrimaryKeySelective", listPatientFastingWeight.get(i));
					}
				}
				
				//更新doctors_patient_link表
				List<DoctorsPatientLink> listDoctorsPatientLink = doctorsPatientLinkService.getList("selectByPatientPhone", patientPhone);
				if (listDoctorsPatientLink.size() > 0) {
					for (int i=0; i<listDoctorsPatientLink.size(); i++) {
						listDoctorsPatientLink.get(i).setPatientName(patientName);
						doctorsPatientLinkService.update("updateByPrimaryKeySelective", listDoctorsPatientLink.get(i));
						
						EasemobChatGroup easemobChatGroup = new EasemobChatGroup();
						ModifyGroup group = new ModifyGroup();
						String groupId = listDoctorsPatientLink.get(i).getChatroomId();
						group.groupname(patientName);
						easemobChatGroup.modifyChatGroup(groupId, group);
					}
				}
				
				//更新patient_doctors_link表
				List<PatientDoctorsLink> listPatientDoctorsLink = patientDoctorsLinkService.getList("selectByPatientPhone", patientPhone);
				if (listPatientDoctorsLink.size() > 0) {
					for (int i=0; i<listPatientDoctorsLink.size(); i++) {
						listPatientDoctorsLink.get(i).setPatientName(patientName);
						patientDoctorsLinkService.update("updateByPrimaryKeySelective", listPatientDoctorsLink.get(i));
					}
				}
				
				//更新doctors_consultation表
				List<DoctorsConsultation> listDoctorsConsultation = doctorsConsultationService.getList("selectByPatientPhone", patientPhone);
				if (listDoctorsConsultation.size() > 0) {
					for (int i=0; i<listDoctorsConsultation.size(); i++) {
						listDoctorsConsultation.get(i).setPatientName(patientName);
						doctorsConsultationService.update("updateByPrimaryKeySelective", listDoctorsConsultation.get(i));
					}
				}
				
				//更新secretary_doctors_advice表
				List<SecretaryDoctorsAdvice> listSecretaryDoctorsAdvice = secretaryDoctorsAdviceService.getList("selectByPatientPhone", patientPhone);
				if (listSecretaryDoctorsAdvice.size() > 0) {
					for (int i=0; i<listSecretaryDoctorsAdvice.size(); i++) {
						listSecretaryDoctorsAdvice.get(i).setPatientName(patientName);
						secretaryDoctorsAdviceService.update("updateByPrimaryKeySelective", listSecretaryDoctorsAdvice.get(i));
					}
				}
				
				//更新secretary_doctors_patient_chatroom表
				List<SecretaryAndDoctorsAndPatientOfChatroom> listSecretaryAndDoctorsAndPatientOfChatroom = secretaryAndDoctorsAndPatientOfChatroomService.getList("selectByPatientPhone", patientPhone);
				if (listSecretaryAndDoctorsAndPatientOfChatroom.size() > 0) {
					for (int i=0; i<listSecretaryAndDoctorsAndPatientOfChatroom.size(); i++) {
						listSecretaryAndDoctorsAndPatientOfChatroom.get(i).setPatientName(patientName);
						secretaryAndDoctorsAndPatientOfChatroomService.update("updateByPrimaryKeySelective", listSecretaryAndDoctorsAndPatientOfChatroom.get(i));
					}
				}
				
			}
			
			//更新患者其他资料
			if (sex != null) patientUser.setSex(sex);
			if (birthday != null) {
				try {
					patientUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					patientUser.setBirthday(null);
				}
			}
			if (StringUtils.isNotBlank(identification)) {
				if (false == ValidationAIUtil.isIDCard(identification)) {
					results.setCode(MessageCode.CODE_201);
					results.setMessage("请输入正确的身份证号码");
					return results;
				}
				try {
					patientUser.setIdentification(DES.encryptDESBeas64(identification) ); //身份证加密保存
				} catch (CoderException e) {
					e.printStackTrace();
				}
			}
			
			patientUserService.update("updateByPrimaryKeySelective", patientUser);
			
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			map.put("patientUser", patientUser);
			results.setData(map);
			return results;
		} else {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("尚未注册");
			return results;
		}

	}
	
	/**
	 * 患者申请加医生：分普通医生及家庭签约医生
	 * parameter={"patientPhone":"","doctorsLoginId":"","doctorType":"","IDOrRemark":"","location":""}
	 * @Title: updateTeamName @Description: TODO @param @param
	 *         request @param @param parameter @param @return 设定文件 @return
	 *         Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "提交申请")
	@RequestMapping(value = "submitRequest")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> submitRequest(HttpServletRequest request, String parameter) {
		// 根据doctorType类型，填写身份证还是备注
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String patientPhone = dataJsonObject.getString("patientPhone");
		String doctorsLoginId = dataJsonObject.getString("doctorsLoginId");
		String doctorsType = dataJsonObject.getString("doctorType");
		String IDOrRemark = dataJsonObject.getString("IDOrRemark");
		
		if (!StringUtils.isNotBlank(patientPhone) && !StringUtils.isNotBlank(doctorsLoginId) && !StringUtils.isNotBlank(doctorsType)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		String doctorsPhone = null;
		try {
			doctorsPhone = doctorsLoginId.trim().split("_")[1];
		} catch (Exception e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_202);
			results.setMessage(MessageCode.MESSAGE_202);
			return results;
		}
		
		DoctorsUser doctorsUser = doctorsUserService.findByParam("selectByPhone", doctorsPhone); //获取医生注册用户信息
		PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone); //获取患者注册用户信息
		if (patientUser == null || doctorsUser == null) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("尚未注册");
			return results;
		}
		
		if (1 == doctorsUser.getDoctortype() && "1".equals(doctorsType)) { //添加普通医生申请记录
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("doctors_phone", doctorsPhone);
			paramMap.put("patient_phone", patientPhone);
			paramMap.put("doctorType", 1);
			PatientDoctorsLink patientDoctorsLink_ = patientDoctorsLinkService.findByParam("selectByPatientPhoneAndDoctorsPhone", paramMap);
			DoctorsPatientLink doctorsPatientLink_ = doctorsPatientLinkService.findByParam("selectByDoctorsPhoneAndPatientPhone", paramMap);
			if (null == patientDoctorsLink_ && null == doctorsPatientLink_) {//没有记录，则插入
				//添加记录到patient_doctors_link表
				PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
				patientDoctorsLink.setCreatetime(new Date());
				patientDoctorsLink.setDoctorsId(doctorsUser.getId());
				patientDoctorsLink.setDoctorsLoginId(doctorsUser.getLoginId());
				patientDoctorsLink.setDoctorsName(doctorsUser.getName());
				patientDoctorsLink.setDoctorsPhone(doctorsPhone);
				
				patientDoctorsLink.setPatientId(patientUser.getId());
				patientDoctorsLink.setPatientLoginId(patientUser.getLoginId());
				patientDoctorsLink.setPatientName(patientUser.getName());
				patientDoctorsLink.setPatientPhone(patientPhone);
				patientDoctorsLink.setType(3);
				patientDoctorsLink.setDoctortype(doctorsUser.getDoctortype());
				patientDoctorsLinkService.insert(patientDoctorsLink);
				
				//添加记录到doctors_patient_link表
				DoctorsPatientLink doctorsPatientLink = new DoctorsPatientLink();
				doctorsPatientLink.setCreatetime(new Date());
				doctorsPatientLink.setDoctorsId(doctorsUser.getId());
				doctorsPatientLink.setDoctorsLoginId(doctorsUser.getLoginId());
				doctorsPatientLink.setDoctorsName(doctorsUser.getName());
				doctorsPatientLink.setDoctorsPhone(doctorsPhone);
				
				doctorsPatientLink.setPatientId(patientUser.getId());
				doctorsPatientLink.setPatientLoginId(patientUser.getLoginId());
				doctorsPatientLink.setPatientName(patientUser.getName());
				doctorsPatientLink.setPatientPhone(patientPhone);
				doctorsPatientLink.setType(3);
				doctorsPatientLink.setDoctortype(doctorsUser.getDoctortype());
				doctorsPatientLink.setGroupName("未分组患者");
				doctorsPatientLinkService.insert(doctorsPatientLink);
			} else if(patientDoctorsLink_!=null && 2==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 2==doctorsPatientLink_.getType()) {//有记录，为拒绝状态，则修改为未处理状态
				//修改记录到patient_doctors_link表
				patientDoctorsLink_.setType(3);
				patientDoctorsLink_.setDoctortype(doctorsUser.getDoctortype());
				patientDoctorsLink_.setUpdatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				patientDoctorsLinkService.update("updateByPrimaryKeySelective", patientDoctorsLink_);
				
				//修改记录到doctors_patient_link表
				doctorsPatientLink_.setType(3);
				doctorsPatientLink_.setDoctortype(doctorsUser.getDoctortype());
				doctorsPatientLink_.setGroupName("未分组患者");
				doctorsPatientLink_.setUpdatetime(new Date());
				doctorsPatientLinkService.update("updateByPrimaryKeySelective", doctorsPatientLink_);
			} else if (patientDoctorsLink_!=null && 3==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 3==doctorsPatientLink_.getType()) {//有记录，为未处理状态，则回复重复操作
				results.setCode(MessageCode.CODE_300);
				results.setMessage(MessageCode.MESSAGE_300);
				return results;
			} else if (patientDoctorsLink_!=null && 1==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 1==doctorsPatientLink_.getType()) {//有记录，为接受状态，则回复已是好友关系
				results.setCode(MessageCode.CODE_405);
				results.setMessage("已是医患关系");
				return results;
			} else if (patientDoctorsLink_!=null && 4==patientDoctorsLink_.getType() && doctorsPatientLink_!=null && 4==doctorsPatientLink_.getType()) {//有记录，为不再提示状态，则回复重复操作
				results.setCode(MessageCode.CODE_502);
				results.setMessage(MessageCode.MESSAGE_300);
				return results;
			} else {//其他情况，视为操作失败
				results.setCode(MessageCode.CODE_501);
				results.setMessage(MessageCode.MESSAGE_501);
				return results;
			}
			
			//更新申请备注
			patientUser.setRemarks(EmojiFilter.filterEmoji(IDOrRemark));
			patientUserService.update("updateByPrimaryKeySelective", patientUser);
		} else if (2 == doctorsUser.getDoctortype() && "2".equals(doctorsType)) { //添加家庭签约医生申请记录
			//查询该患者是否有过签约医生申请记录
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap2.put("patient_phone", patientPhone);
			paramMap2.put("doctorType", 2);
			PatientDoctorsLink patientDoctorsLink_2 = patientDoctorsLinkService.findByParam("selectByPatientPhoneAndDoctorType", paramMap2);
			DoctorsPatientLink doctorsPatientLink_2 = doctorsPatientLinkService.findByParam("selectByPatientPhoneAndDoctorType", paramMap2);
			if (null == patientDoctorsLink_2 && null == doctorsPatientLink_2) {//没有记录，则插入
				//添加记录到patient_doctors_link表
				PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
				patientDoctorsLink.setCreatetime(new Date());
				patientDoctorsLink.setDoctorsId(doctorsUser.getId());
				patientDoctorsLink.setDoctorsLoginId(doctorsUser.getLoginId());
				patientDoctorsLink.setDoctorsName(doctorsUser.getName());
				patientDoctorsLink.setDoctorsPhone(doctorsPhone);
				
				patientDoctorsLink.setPatientId(patientUser.getId());
				patientDoctorsLink.setPatientLoginId(patientUser.getLoginId());
				patientDoctorsLink.setPatientName(patientUser.getName());
				patientDoctorsLink.setPatientPhone(patientPhone);
				patientDoctorsLink.setType(3);
				patientDoctorsLink.setDoctortype(doctorsUser.getDoctortype());
				patientDoctorsLinkService.insert(patientDoctorsLink);
				
				//添加记录到doctors_patient_link表
				DoctorsPatientLink doctorsPatientLink = new DoctorsPatientLink();
				doctorsPatientLink.setCreatetime(new Date());
				doctorsPatientLink.setDoctorsId(doctorsUser.getId());
				doctorsPatientLink.setDoctorsLoginId(doctorsUser.getLoginId());
				doctorsPatientLink.setDoctorsName(doctorsUser.getName());
				doctorsPatientLink.setDoctorsPhone(doctorsPhone);
				
				doctorsPatientLink.setPatientId(patientUser.getId());
				doctorsPatientLink.setPatientLoginId(patientUser.getLoginId());
				doctorsPatientLink.setPatientName(patientUser.getName());
				doctorsPatientLink.setPatientPhone(patientPhone);
				doctorsPatientLink.setType(3);
				doctorsPatientLink.setDoctortype(doctorsUser.getDoctortype());
				doctorsPatientLink.setGroupName("未分组患者");
				doctorsPatientLinkService.insert(doctorsPatientLink);
			} else if(patientDoctorsLink_2!=null && 2==patientDoctorsLink_2.getType() && doctorsPatientLink_2!=null && 2==doctorsPatientLink_2.getType()) {//有记录，为拒绝状态，则修改为未处理状态
				//修改记录到patient_doctors_link表
				patientDoctorsLink_2.setType(3);
				patientDoctorsLink_2.setDoctortype(doctorsUser.getDoctortype());
				patientDoctorsLink_2.setUpdatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				patientDoctorsLinkService.update("updateByPrimaryKeySelective", patientDoctorsLink_2);
				
				//修改记录到doctors_patient_link表
				doctorsPatientLink_2.setType(3);
				doctorsPatientLink_2.setDoctortype(doctorsUser.getDoctortype());
				doctorsPatientLink_2.setGroupName("未分组患者");
				doctorsPatientLink_2.setUpdatetime(new Date());
				doctorsPatientLinkService.update("updateByPrimaryKeySelective", doctorsPatientLink_2);
			} else if (patientDoctorsLink_2!=null && 3==patientDoctorsLink_2.getType() && doctorsPatientLink_2!=null && 3==doctorsPatientLink_2.getType()) {//有记录，为未处理状态，则判断该申请记录是否过期
				//判断最近处理时间是否超过三天，是则当过期处理，重新添加申请记录
				Date recentDate = doctorsPatientLink_2.getUpdatetime()==null?doctorsPatientLink_2.getCreatetime():doctorsPatientLink_2.getUpdatetime();
				if (Toolkit.daysBetween(recentDate, new Date())>3) {//超过三天，当过期处理
					//删除过期记录
					patientDoctorsLinkService.delete("deleteByPrimaryKey", patientDoctorsLink_2);
					doctorsPatientLinkService.delete("deleteByPrimaryKey", doctorsPatientLink_2);
					
					//添加记录到patient_doctors_link表
					PatientDoctorsLink patientDoctorsLink = new PatientDoctorsLink();
					patientDoctorsLink.setCreatetime(new Date());
					patientDoctorsLink.setDoctorsId(doctorsUser.getId());
					patientDoctorsLink.setDoctorsLoginId(doctorsUser.getLoginId());
					patientDoctorsLink.setDoctorsName(doctorsUser.getName());
					patientDoctorsLink.setDoctorsPhone(doctorsPhone);
					
					patientDoctorsLink.setPatientId(patientUser.getId());
					patientDoctorsLink.setPatientLoginId(patientUser.getLoginId());
					patientDoctorsLink.setPatientName(patientUser.getName());
					patientDoctorsLink.setPatientPhone(patientPhone);
					patientDoctorsLink.setType(3);
					patientDoctorsLink.setDoctortype(doctorsUser.getDoctortype());
					patientDoctorsLinkService.insert(patientDoctorsLink);
					
					//添加记录到doctors_patient_link表
					DoctorsPatientLink doctorsPatientLink = new DoctorsPatientLink();
					doctorsPatientLink.setCreatetime(new Date());
					doctorsPatientLink.setDoctorsId(doctorsUser.getId());
					doctorsPatientLink.setDoctorsLoginId(doctorsUser.getLoginId());
					doctorsPatientLink.setDoctorsName(doctorsUser.getName());
					doctorsPatientLink.setDoctorsPhone(doctorsPhone);
					
					doctorsPatientLink.setPatientId(patientUser.getId());
					doctorsPatientLink.setPatientLoginId(patientUser.getLoginId());
					doctorsPatientLink.setPatientName(patientUser.getName());
					doctorsPatientLink.setPatientPhone(patientPhone);
					doctorsPatientLink.setType(3);
					doctorsPatientLink.setDoctortype(doctorsUser.getDoctortype());
					doctorsPatientLink.setGroupName("未分组患者");
					doctorsPatientLinkService.insert(doctorsPatientLink);
				} else {
					results.setCode(MessageCode.CODE_300);
					results.setMessage("您在3天之内已经申请过签约医生，请耐心等待回复");
					return results;
				}
			} else if (patientDoctorsLink_2!=null && 1==patientDoctorsLink_2.getType() && doctorsPatientLink_2!=null && 1==doctorsPatientLink_2.getType()) {//有记录，为接受状态，则回复已是好友关系
				results.setCode(MessageCode.CODE_405);
				results.setMessage("您已经签约了医生");
				return results;
			} else if (patientDoctorsLink_2!=null && 4==patientDoctorsLink_2.getType() && doctorsPatientLink_2!=null && 4==doctorsPatientLink_2.getType()) {//有记录，为不再提示状态，则回复重复操作
				results.setCode(MessageCode.CODE_502);
				results.setMessage(MessageCode.MESSAGE_300);
				return results;
			} else {//其他情况，视为操作失败
				results.setCode(MessageCode.CODE_501);
				results.setMessage(MessageCode.MESSAGE_501);
				return results;
			}
			
			//更新患者身份证信息
			patientUser.setIdentification(EmojiFilter.filterEmoji(IDOrRemark));
			patientUser.setLocation(EmojiFilter.filterEmoji(dataJsonObject.getString("location")));
			patientUserService.update("updateByPrimaryKeySelective", patientUser);
			
		} else {
			results.setCode(MessageCode.CODE_501);
			results.setMessage(MessageCode.MESSAGE_501);
			return results;
		}
		
		map.put("patientUser", patientUser);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}

}
