/**   
* @Title: PatientUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:26:13 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.h5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.HealthCoins;
import com.cmcc.medicalcare.model.HealthCoinsLog;
import com.cmcc.medicalcare.model.HealthReferee;
import com.cmcc.medicalcare.model.PatientDoctorsLink;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.IHealthCoinsLogService;
import com.cmcc.medicalcare.service.IHealthCoinsService;
import com.cmcc.medicalcare.service.IHealthRefereeService;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IPatientDoctorsLinkService;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.cmcc.medicalcare.utils.DES;
import com.cmcc.medicalcare.utils.EmojiFilter;
import com.cmcc.medicalcare.utils.FileFilterUtils;
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
 * @ClassName: 患者信息
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:26:13
 * 
 */
@Controller
@RequestMapping("/h5/patientUser")
public class H5PatientUserController {
	
	@Resource
	private IPatientUserService patientUserService;
	
	@Resource
	private IDoctorsUserService doctorsUserService;
	
	@Resource
	private IPatientDoctorsLinkService patientDoctorsLinkService;
	
	@Resource
	private IDoctorsPatientLinkService doctorsPatientLinkService;
	
	@Resource
	private IHealthRefereeService healthRefereeService;
	
	@Resource
	private IHealthCoinsService healthCoinsService;

	@Resource
	private IHealthCoinsLogService healthCoinsLogService;
	
	@Resource
	private IMediaLogService mediaLogService;

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
//	@RequestMapping(value = "insert")
//	@ResponseBody
//	@SystemControllerLog(description = "插入患者资料")
//	public Results<Map<String, Object>> insert(HttpServletRequest request,@RequestParam String equipmentData,@RequestParam String patientUser) {
//		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
//		JSONObject patientUserObject = JSONObject.parseObject(patientUser);
//		
//		String userName;
//		String phone;
//		String referenceLoginId;
//		if (dataJsonObject != null && patientUserObject != null) {
//			phone = dataJsonObject.getString("phone");
//			userName = dataJsonObject.getString("userName");
//			referenceLoginId = patientUserObject.getString("referenceLoginId");
//			System.out.println("推荐人："+referenceLoginId);
//			PatientUser patientUser_ = patientUserService.findByParam("selectByPhone", phone);
//			if (patientUser_ != null) {
//				results.setCode(MessageCode.CODE_405);
//				results.setData(null);
//				results.setMessage(MessageCode.MESSAGE_405);
//				return results;
//			}
//			return results;
//			EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
//			RegisterUsers users = new RegisterUsers();
//			User user;
//			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(phone)) {
//				user = new User().username(Constant.patient + phone).password("mingyizaixian123456789");// 环信测试帐号
//				users.add(user);
//				Object result = easemobIMUsers.createNewIMUserSingle(users);
//
//				if (result == null) {
//					results.setCode(MessageCode.CODE_501);
//					results.setData(null);
//					results.setMessage("无法在环信创建用户");
//					return results;
//				}
//				// 解释result
//				JSONObject resultObject = JSONObject.parseObject(result.toString());
//				// System.out.println(resultObject.get("entities"));
//				JSONArray entities = JSONArray.fromObject(resultObject.get("entities"));
//				String entity = entities.get(0).toString();
//				JSONObject entityObject = JSONObject.parseObject(entity);
//				String activated = entityObject.getString("activated");
//				String uuid = (String) entityObject.getString("uuid");
//				// System.out.println(":::::" + activated);
//				// System.out.println(":::::" + uuid);
//				if (activated != null && "true".equals(activated)) {
//					PatientUser patientUser1 = new PatientUser();
//					patientUser1.setEasemobUuid(uuid);// 环信返回来的uuid
//					patientUser1.setPassword("mingyizaixian123456789");
//					patientUser1.setLoginId(Constant.patient + phone);
//					patientUser1.setPhone(phone);
//					patientUser1.setName(userName);
//					patientUser1.setSex(patientUserObject.getString("sex"));
//					try {
//						patientUser1.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patientUserObject.getString("birthday")));
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						patientUser1.setBirthday(null);
//					}
//					patientUser1.setHeadUrl(patientUserObject.getString("headUrl"));
//					patientUser1.setVisitState(patientUserObject.getString("visitState"));
//					patientUser1.setHospital(patientUserObject.getString("hospital"));
//					patientUser1.setRemarks(patientUserObject.getString("remarks"));
//					patientUser1.setCreatetime(new Date());
//					patientUserService.insert(patientUser1);
//					Integer id = patientUser1.getId();
//					if (id != null) {
//						results.setCode(200);
//						map.put("patientUser", patientUser1);
//						results.setData(map);
//						results.setMessage(MessageCode.MESSAGE_200);
//						return results;
//					} else {
//						results.setCode(MessageCode.CODE_501);
//						results.setData(null);
//						results.setMessage("无法在本地创建用户");
//						return results;
//					}
//				} else {
//					results.setCode(MessageCode.CODE_501);
//					results.setData(null);
//					results.setMessage("无法在环信创建用户");
//					return results;
//				}
//			} else {
//				results.setCode(MessageCode.CODE_201);
//				results.setData(null);
//				results.setMessage(MessageCode.MESSAGE_201);
//				return results;
//			}
//		} else {
//			results.setCode(MessageCode.CODE_201);
//			results.setData(null);
//			results.setMessage(MessageCode.MESSAGE_201);
//			return results;
//		}
//	}

	
	
	@RequestMapping(value = "insert")
	@ResponseBody
	@SystemControllerLog(description = "插入患者资料")
	public Results<Map<String, Object>> insert(HttpServletRequest request,@RequestParam String equipmentData,@RequestParam String patientUser, MultipartFile headFile){
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
		String referenceLoginId;
		if (dataJsonObject != null && patientUserObject != null) {
			phone = dataJsonObject.getString("phone");
			userName = dataJsonObject.getString("userName");
			referenceLoginId = patientUserObject.getString("referenceLoginId");
			System.out.println("推荐人："+referenceLoginId);
			PatientUser patientUser_referee = patientUserService.findByParam("selectByLoginID", referenceLoginId);
			PatientUser patientUser_ = patientUserService.findByParam("selectByPhone", phone);
			if (patientUser_ != null) {
				results.setCode(MessageCode.CODE_405);
				results.setData(null);
				results.setMessage(MessageCode.MESSAGE_405);
				return results;
			}
			
			if (patientUser_referee == null) {
				results.setCode(MessageCode.CODE_201);
				results.setData(null);
				results.setMessage("缺失推荐人");
				return results;
			}
			
			EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
			RegisterUsers users = new RegisterUsers();
			User user;
			if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(phone)) {
				user = new User().username(Constant.patient + phone).password(Constant.password);// 环信测试帐号
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
				if (result!=null) {
					Nickname nickName = new Nickname();
					nickName.setNickname(userName);
					easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.patient + phone, nickName);//修改昵称
					
					String prefix = "patient_" + phone + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_";
					UserInfo  userInfo = new UserInfo();
					userInfo.setUserLoginId(Constant.patient+phone);
					userInfo.setUserName(userName);
					userInfo.setUserPhone(phone);
					String headUrl = UploadFilesUtils.saveFile(mediaLogService,userInfo,request, headFile, prefix); //保存头像文件
					
					PatientUser patientUser1 = new PatientUser();
					patientUser1.setEasemobUuid(uuid);// 环信返回来的uuid
					try {
						patientUser1.setPassword(DES.encryptDESBeas64(Constant.password));
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
					patientUserService.insert(patientUser1);
					Integer id = patientUser1.getId();
					if (id != null) {
						
						HealthReferee healthReferee = new HealthReferee();
						healthReferee.setCreatetime(new Date());
						healthReferee.setRefereeLoginid(patientUser_referee.getLoginId());
						healthReferee.setRefereeName(patientUser_referee.getName());
						healthReferee.setRefereePhone(patientUser_referee.getPhone());
						healthReferee.setUserPhone(phone);
						healthReferee.setUserName(userName);
						healthReferee.setUserLoginid(Constant.patient + phone);
						
						healthRefereeService.insert(healthReferee);
						
						HealthCoins healthCoins = healthCoinsService.findByParam("selectByLoginId", referenceLoginId);
						
						if (healthCoins==null) {
							
							healthCoins = new HealthCoins();
							healthCoins.setCoins(1000);
							healthCoins.setCreatetime(new Date());
							healthCoins.setUserLoginid(referenceLoginId);//patientUser_referee
							healthCoins.setUserName(patientUser_referee.getName());
							healthCoins.setUserPhone(patientUser_referee.getPhone());
							
							healthCoinsService.insert(healthCoins);
							
							HealthCoinsLog healthCoinsLog = new HealthCoinsLog();
							healthCoinsLog.setCreatetime(new Date());
							healthCoinsLog.setDetail("邀请好友"+phone);
							healthCoinsLog.setGcoins(1000);
							healthCoinsLog.setLcoins(0);
							healthCoinsLog.setMcoins(0+1000);
							healthCoinsLog.setType(1);
							healthCoinsLog.setUserLoginid(referenceLoginId);
							healthCoinsLog.setUserName(patientUser_referee.getName());
							healthCoinsLog.setUserPhone(patientUser_referee.getPhone());
							
							healthCoinsLogService.insert(healthCoinsLog);
							
						} else {
							int total = healthCoins.getCoins();
							healthCoins.setCoins(total+1000);
							healthCoins.setUpdatetime(new Date());
							
							healthCoinsService.update("updateByPrimaryKeySelective", healthCoins);
							HealthCoinsLog healthCoinsLog = new HealthCoinsLog();
							healthCoinsLog.setCreatetime(new Date());
							healthCoinsLog.setDetail("邀请好友"+phone);
							healthCoinsLog.setGcoins(1000);
							healthCoinsLog.setLcoins(total);
							healthCoinsLog.setMcoins(total+1000);
							healthCoinsLog.setType(1);
							healthCoinsLog.setUserLoginid(referenceLoginId);
							healthCoinsLog.setUserName(patientUser_referee.getName());
							healthCoinsLog.setUserPhone(patientUser_referee.getPhone());
							
							healthCoinsLogService.insert(healthCoinsLog);
						}
	
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("messageType", "referee");
						ext.put("coins", 1000);
						ext.put("phone", phone);
						EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
						Msg msg = new Msg();
						MsgContent msgContent = new MsgContent();
						msgContent.type(MsgContent.TypeEnum.CMD).msg(null);
						UserName userName_ = new UserName();
						userName_.add(referenceLoginId);
						msg.from(Constant.patient + phone).target(userName_).targetType("users").msg(msgContent)
								.ext(ext);
						result = easemobSendMessage.sendMessage(msg);
						
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
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		PatientUser patientUser;
		String patientPhone = request.getParameter("patientPhone");
		if (StringUtils.isNotBlank(patientPhone)) {
			patientUser = patientUserService.findByParam("selectByPhone", patientPhone);
			if (patientUser != null) {
				results.setCode(MessageCode.CODE_200);
				results.setMessage(MessageCode.MESSAGE_200);
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
	 * 提交患者注册资料
	 * @param request
	 * @param equipmentData
	 * @param patientUser={"patientPhone":"患者电话","patientName":"患者姓名","sex":"性别","birthday":"生日","location":"所在地","occupation":"职业","visitState":"就诊地址","hospital":"就诊医院","remarks":"备注","source":"1为二维码扫描  2为好友推荐","identification":"身份证号"}
	 * @param doctorsLoginId
	 * @param headFile
	 * @return
	 */
	@SystemControllerLog(description = "提交患者注册资料")
	@RequestMapping(value = "submitRegisterInfo")
	@ResponseBody
	@Transactional
	public Results<Map<String, Object>> submitRegisterInfo(HttpServletRequest request,
			@RequestParam String equipmentData, @RequestParam String patientUser, 
			@RequestParam String doctorsLoginId, MultipartFile headFile) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject equipmentDataObject = JSONObject.parseObject(equipmentData);
		JSONObject patientUserObject = JSONObject.parseObject(patientUser);
		
		String referenceLoginId = patientUserObject.getString("referenceLoginId");//推荐人登录ID
		
		if (null == equipmentDataObject || null == patientUserObject) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		
		/**
		 * 检验医生信息
		 */
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
		if (null == doctorsUser) {
			results.setCode(MessageCode.CODE_501);
			results.setMessage("该医生尚未注册");
			return results;
		}
		
		/**
		 * 检验患者信息
		 */
		String patientPhone = equipmentDataObject.getString("patientPhone");
		String patientName = equipmentDataObject.getString("patientName");
		if (!StringUtils.isNotBlank(patientName) || !StringUtils.isNotBlank(patientPhone)) {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
		PatientUser patientUser_ = patientUserService.findByParam("selectByPhone", patientPhone); //获取患者注册用户信息
		if (patientUser_ != null) {
			results.setCode(MessageCode.CODE_405);
			results.setData(null);
			results.setMessage(MessageCode.MESSAGE_405);
			return results;
		}
		
		/**
		 * 在环信及本地注册患者信息
		 */
		EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
		RegisterUsers users = new RegisterUsers();
		User user;
		user = new User().username(Constant.patient + patientPhone).password(Constant.password);// 环信测试帐号
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
		JSONArray entities = JSONArray.fromObject(resultObject.get("entities"));
		String entity = entities.get(0).toString();
		JSONObject entityObject = JSONObject.parseObject(entity);
		String activated = entityObject.getString("activated");
		String uuid = (String) entityObject.getString("uuid");
//		if (activated != null && "true".equals(activated)) {
		if (result!=null) {
			PatientUser patientUser1 = new PatientUser();
			patientUser1.setEasemobUuid(uuid);// 环信返回来的uuid
			try {
				patientUser1.setPassword(DES.encryptDESBeas64(Constant.password));
			} catch (CoderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			patientUser1.setLoginId(Constant.patient + patientPhone);
			patientUser1.setPhone(patientPhone);
			patientUser1.setName(patientName);
			patientUser1.setSex(patientUserObject.getString("sex"));
			try {
				patientUser1.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(patientUserObject.getString("birthday")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				patientUser1.setBirthday(null);
			}
			patientUser1.setHeadUrl(patientUserObject.getString("headUrl"));
			patientUser1.setLocation(patientUserObject.getString("location"));
			patientUser1.setOccupation(patientUserObject.getString("occupation"));
			patientUser1.setVisitState(patientUserObject.getString("visitState"));
			patientUser1.setHospital(patientUserObject.getString("hospital"));
			patientUser1.setRemarks(patientUserObject.getString("remarks"));
			patientUser1.setSource(patientUserObject.getInteger("source"));
			patientUser1.setIdentification(patientUserObject.getString("identification"));
			patientUser1.setCreatetime(new Date());
			patientUserService.insert(patientUser1);
			Integer id = patientUser1.getId();
			if (id != null) {
				//添加申请添加或签约医生记录
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("doctors_phone", doctorsPhone);
				paramMap.put("patient_phone", patientPhone);
				
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
					
					patientDoctorsLink.setPatientId(patientUser1.getId());
					patientDoctorsLink.setPatientLoginId(patientUser1.getLoginId());
					patientDoctorsLink.setPatientName(patientUser1.getName());
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
					
					doctorsPatientLink.setPatientId(patientUser1.getId());
					doctorsPatientLink.setPatientLoginId(patientUser1.getLoginId());
					doctorsPatientLink.setPatientName(patientUser1.getName());
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
			results.setCode(MessageCode.CODE_501);
			results.setData(null);
			results.setMessage("注册用户失败");
			return results;
		}
		
	}

}
