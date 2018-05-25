package com.cmcc.medicalcare.controller.app.patient.v2;

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
import com.cmcc.medicalcare.cache.redis.Constants;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientLoginInfo;
import com.cmcc.medicalcare.model.PatientUser;
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
import com.cmcc.medicalcare.utils.RandomUtils;
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
@RequestMapping("/app/patientUser/v2")
public class PatientUserV2Controller {
	
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
	
	@Resource
	private RedisUtil redisUtil;
	
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

		String patientPhone = request.getParameter("patientPhone");
		String patientId = request.getParameter("patientId");
		String memberId = request.getParameter("memberId");
		if (StringUtils.isBlank(patientPhone) || StringUtils.isBlank(memberId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		//查询九州通用户信息
		JSONObject patientUser = null;
		String patientUserStr = patientUserService.findPatientUser("", memberId);
//		patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
		if (patientUserStr != null) {
			JSONObject patientUserJson = JSONObject.parseObject(patientUserStr);
			String status = patientUserJson.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = JSONArray.fromObject(patientUserJson.get("data"));
				if (jsonArray.size() > 0) {
					patientUser = JSONObject.parseObject(jsonArray.get(0).toString());;
				}
			}
			
			if (patientUser != null) {
				patientUser.put("loginId", Constant.patient + patientPhone);
			}
			
			//缓存九州通患者信息
			redisUtil.set(Constants.KEY_USER_PATIENT + patientPhone, patientUser);
			
			String headUrl = "";
			headUrl = (String) redisUtil.get(Constants.KEY_HEADURL_PATIENT + patientPhone);
			map.put("headUrl", headUrl);
			map.put("patientUser", patientUser);
			results.setData(map);
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			return results;
		} else {
			results.setCode(MessageCode.CODE_204);
			results.setMessage(MessageCode.MESSAGE_204);
			return results;
		}
		
	}
	
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
		System.out.println("patientUserpatientUserpatientUser=========="+patientUser);
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
		
		/**
		 * 校验必传参数：电话，姓名，身份证
		 */
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		JSONObject patientUserObject = JSONObject.parseObject(patientUser);
		if (null == dataJsonObject || null == patientUserObject) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		String phone = dataJsonObject.getString("phone");
		String userName = dataJsonObject.getString("userName");
		String identification = patientUserObject.getString("identification");
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(userName) || StringUtils.isBlank(identification)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		//校验身份证合法性
		if (false == ValidationAIUtil.isIDCard(identification)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage("请输入正确的身份证号码");
			return results;
		}
		
		//************************************************注册环信用户信息_开始******************************************
		//先检查环信是否注册用户
		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
		Object result = easemobIMUsers.getIMUserByUserName(Constant.patient + phone);
		if (null == result) {
			RegisterUsers users = new RegisterUsers();
			User user = new User().username(Constant.patient + phone).password("mingyizaixian123456789");// 环信测试帐号
			users.add(user);
			Object result2 = easemobIMUsers.createNewIMUserSingle(users);
			
			if (null == result2) {
				results.setCode(MessageCode.CODE_501);
				results.setData(null);
				results.setMessage("注册用户失败");
				return results;
			}
			// 解释result
			JSONObject resultObject = JSONObject.parseObject(result2.toString());
			JSONArray entities = JSONArray.fromObject(resultObject.get("entities"));
			String entity = entities.get(0).toString();
			JSONObject entityObject = JSONObject.parseObject(entity);
			String activated = entityObject.getString("activated");
			String uuid = (String) entityObject.getString("uuid");
			
			//修改昵称
			Nickname nickName = new Nickname();
			nickName.setNickname(userName);
			easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.patient + phone, nickName);//修改昵称
		}
		//************************************************注册环信用户信息_结束******************************************
		
		//上传头像文件
		String prefix = "patient_" + phone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(Constant.patient+phone);
		userInfo.setUserName(userName);
		userInfo.setUserPhone(phone);
		String headUrl = UploadFilesUtils.saveFile(mediaLogService,  userInfo,request, headFile, prefix); //保存头像文件
		
		redisUtil.set(Constants.KEY_HEADURL_PATIENT + phone, headUrl);
		
		//***********************************构造患者实体_开始*****************************************************
//		PatientUser patientUser1 = new PatientUser();
//		try {
//			patientUser1.setPassword(DES.encryptDESBeas64("mingyizaixian123456789"));
//		} catch (CoderException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		patientUser1.setLoginId(Constant.patient + phone);
//		patientUser1.setPhone(phone);
//		patientUser1.setName(userName);
//		patientUser1.setSex(patientUserObject.getString("sex"));
//		try {
//			patientUser1.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patientUserObject.getString("birthday")));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			patientUser1.setBirthday(null);
//		}
//		patientUser1.setHeadUrl(headUrl);
//		patientUser1.setVisitState(patientUserObject.getString("visitState"));
//		patientUser1.setHospital(patientUserObject.getString("hospital"));
//		patientUser1.setMedicalCard(patientUserObject.getString("medicalCard"));
//		patientUser1.setRemarks(EmojiFilter.filterEmoji(patientUserObject.getString("remarks")));
//		patientUser1.setCreatetime(new Date());
//		try {
//			patientUser1.setIdentification(DES.encryptDESBeas64(identification)); //身份证加密保存
//		} catch (CoderException e) {
//			e.printStackTrace();
//		}
		//***********************************构造患者实体_结束*****************************************************
		
		//患者用户信息_本地入库
//		patientUserService.insert(patientUser1);
		
		String sex = patientUserObject.getString("sex");
		String sex_;
		if ("男".equals(sex)) {
			sex_ = "1";
		} else if ("女".equals(sex)) {
			sex_ = "2";
		} else if ("不详".equals(sex)) {
			sex_ = "3";
		} else {
			sex_ = "9";
		}
		String birthday = patientUserObject.getString("birthday");
//		String age = "";
//		if (StringUtils.isNotBlank(birthday)) {
//			try {
//				age = String.valueOf(Toolkit.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(birthday)));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		//注册九州通会员信息
		String memberIdJson = patientUserService.saveMemberInfo(phone, userName, identification, sex_, birthday);
		String memberId = null;
		if (StringUtils.isNotBlank(memberIdJson)) {
			JSONObject json = JSONObject.parseObject(memberIdJson);
			String status = json.getString("status");
			if ("0".equals(status)) {
				String dataJson = json.getString("data");
				JSONObject json2 = JSONObject.parseObject(dataJson);
				memberId = json2.getString("memberId");
			} else {
				results.setCode(MessageCode.CODE_501);
				results.setMessage("注册九州通用户失败");
				return results;
			}
		}
		
		//注册九州通患者信息
		String patientIdJson = patientUserService.savePatientInfo(phone, userName, phone, birthday, sex_, "", "", identification);
		String patientId = null;
		if (StringUtils.isNotBlank(patientIdJson)) {
			JSONObject json = JSONObject.parseObject(patientIdJson);
			String status = json.getString("status");
			if ("0".equals(status)) {
				String dataJson = json.getString("data");
				JSONObject json2 = JSONObject.parseObject(dataJson);
				patientId = json2.getString("patientId");
			}
		}
		
		//查询九州通用户信息
		JSONObject patientUser_ = null;
		String patientUserStr = patientUserService.findPatientUser(patientId, memberId);
		if (patientUserStr != null) {
			JSONObject patientUserJson = JSONObject.parseObject(patientUserStr);
			String status = patientUserJson.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = JSONArray.fromObject(patientUserJson.get("data"));
				if (jsonArray.size() > 0) {
					patientUser_ = JSONObject.parseObject(jsonArray.get(0).toString());
				}
			}
		}
		
		if (patientUser_ != null) {
			patientUser_.put("loginId", Constant.patient + phone);
		}
		
		//缓存九州通患者信息
		redisUtil.set(Constants.KEY_USER_PATIENT + phone, patientUser_);
		
		String token = RandomUtils.generateUUID();
		PatientLoginInfo patientLoginInfo_ = patientLoginInfoService.findByParam("selectByPhone", phone);
		if (null == patientLoginInfo_) {
			PatientLoginInfo patientLoginInfo = new PatientLoginInfo();
			patientLoginInfo.setPatientPhone(phone);
			patientLoginInfo.setPatientLoginId(Constant.patient + phone);
			patientLoginInfo.setPatientId(null);
			patientLoginInfo.setCreatetime(new Date());
			patientLoginInfo.setLoginTime(new Date());
			patientLoginInfo.setModel(dataJsonObject.getString("model"));
			patientLoginInfo.setSystem(dataJsonObject.getString("systemtype"));
			patientLoginInfo.setToken(token);
			patientLoginInfo.setType(null);
			patientLoginInfo.setVersion(dataJsonObject.getString("packageVesion"));
			patientLoginInfo.setIp(HTTPUtils.getIpAddrByRequest(request));
			patientLoginInfo.setIsEnable(1);
			patientLoginInfoService.insert(patientLoginInfo);
			map.put("patientLoginInfo", patientLoginInfo);
		} else {
			patientLoginInfo_.setLoginTime(new Date());
			patientLoginInfo_.setVersion(null);
			patientLoginInfo_.setToken(token);
			patientLoginInfo_.setIp(HTTPUtils.getIpAddrByRequest(request));
			patientLoginInfoService.update("updateByPrimaryKeySelective", patientLoginInfo_);
			map.put("patientLoginInfo", patientLoginInfo_);
		}
		
		map.put("patientUser", patientUser_);
		map.put("headUrl", headUrl);
		map.put("session", token);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
		
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
		
		if(!headFile.isEmpty()&&!FileFilterUtils.isMediaFileType(headFile.getOriginalFilename())){
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		if (null == dataJsonObject) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		String userName = dataJsonObject.getString("userName");
		
		//上传头像文件
		String prefix = "patient_" + patientPhone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(Constant.patient+patientPhone);
		userInfo.setUserName(userName);
		userInfo.setUserPhone(patientPhone);
		String headUrl = UploadFilesUtils.saveFile(mediaLogService,  userInfo,request, headFile, prefix); //保存头像文件
		
		redisUtil.set(Constants.KEY_HEADURL_PATIENT + patientPhone, headUrl);
		
		map.put("headUrl", headUrl);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
	}

	/**
	 * parameter={"patientPhone":"", "patientName":"", "sex":"", "birthday":"", "identification":"", "patientId":"", "memberId":""}
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
		String patientId = dataJsonObject.getString("patientId");
		String memberId = dataJsonObject.getString("memberId");
		
		if (StringUtils.isBlank(patientPhone) || StringUtils.isBlank(patientName) 
				|| StringUtils.isBlank(identification) || StringUtils.isBlank(patientId)
				|| StringUtils.isBlank(memberId)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		if (StringUtils.isBlank(sex)) {
			sex = "";
		}
		if (StringUtils.isBlank(birthday)) {
			birthday = "";
		}
		
		//校验身份证合法性
		if (false == ValidationAIUtil.isIDCard(identification)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage("请输入正确的身份证号码");
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
		//************************************************敏感词过滤_结束******************************************
		
//		PatientUser patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
//		if (null == patientUser) {
//			results.setCode(MessageCode.CODE_404);
//			results.setMessage("尚未注册");
//			return results;
//		}
//		
//		//更新患者其他资料
//		if (sex != null) patientUser.setSex(sex);
//		if (birthday != null) {
//			try {
//				patientUser.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				patientUser.setBirthday(null);
//			}
//		}
//		
//		try {
//			patientUser.setIdentification(DES.encryptDESBeas64(identification) ); //身份证加密保存
//		} catch (CoderException e) {
//			e.printStackTrace();
//		}
//		
//		patientUserService.update("updateByPrimaryKeySelective", patientUser);
		
		String sex_;
		if ("男".equals(sex)) {
			sex_ = "1";
		} else if ("女".equals(sex)) {
			sex_ = "2";
		} else if ("不详".equals(sex)) {
			sex_ = "3";
		} else {
			sex_ = "9";
		}
//		String age = "";
//		if (StringUtils.isNotBlank(birthday)) {
//			try {
//				age = String.valueOf(Toolkit.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(birthday)));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		//更新九州通会员信息
		patientUserService.updateMemberInfo(patientPhone, patientName, patientPhone, identification, sex_, birthday);
		
		//更新九州通患者信息
		patientUserService.updatePatientInfo(patientPhone, patientName, patientPhone, birthday,
				sex_, "", "", identification, memberId, patientId);
		
		//查询九州通用户信息
		JSONObject patientUser_ = null;
		String patientUserStr = patientUserService.findPatientUser(patientId, memberId);
		if (patientUserStr != null) {
			JSONObject patientUserJson = JSONObject.parseObject(patientUserStr);
			String status = patientUserJson.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = JSONArray.fromObject(patientUserJson.get("data"));
				if (jsonArray.size() > 0) {
					patientUser_ = JSONObject.parseObject(jsonArray.get(0).toString());
				}
			}
		}
		
		if (patientUser_ != null) {
			patientUser_.put("loginId", Constant.patient + patientPhone);
		}
		
		String headUrl = "";
		headUrl = (String) redisUtil.get(Constants.KEY_HEADURL_PATIENT + patientPhone);	
		map.put("patientUser", patientUser_);
		map.put("headUrl", headUrl);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}

}
