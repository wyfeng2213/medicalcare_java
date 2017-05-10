/**   
* @Title: DoctorsUserController.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:32:23 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor;

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
import com.cmcc.medicalcare.model.DoctorsPatientLink;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.SecretaryDoctorsLink;
import com.cmcc.medicalcare.service.IDoctorsPatientLinkService;
import com.cmcc.medicalcare.service.IDoctorsUserService;
import com.cmcc.medicalcare.service.ISecretaryDoctorsLinkService;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
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
	public Results<Map<String, Object>> insert(HttpServletRequest request, @RequestParam String equipmentData,
			@RequestParam String doctorsUser, MultipartFile headFile) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		JSONObject doctorsUserObject = JSONObject.parseObject(doctorsUser);
		System.out.println("doctorsUser:" + doctorsUser);
		System.out.println("equipmentData:" + equipmentData);
		String userName;
		String phone;
		if (dataJsonObject != null && doctorsUserObject != null) {
			phone = dataJsonObject.getString("phone");
			userName = dataJsonObject.getString("userName");

			String sex = doctorsUserObject.getString("sex");
			// String headUrl = doctorsUserObject.getString("headUrl");
			String title = doctorsUserObject.getString("title");
			String hospital = doctorsUserObject.getString("hospital");
			String department = doctorsUserObject.getString("department");
			String certificateUrl = doctorsUserObject.getString("certificateUrl");
			String departmentTelephone = doctorsUserObject.getString("departmentTelephone");
			String introduction = doctorsUserObject.getString("introduction");

			DoctorsUser doctorsUser_ = doctorsUserService.findByParam("selectByPhone", phone);

			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			paramMap1.put("doctorsPhone", phone);
			paramMap1.put("secretaryPhone", Constant.scretaryPhone);
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
					results.setMessage("无法在环信创建用户");
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
				if (activated != null && "true".equals(activated)) {
					Nickname nickName = new Nickname();
					nickName.setNickname(userName);
					easemobIMUsers.modifyIMUserNickNameWithAdminToken(Constant.doctor + phone, nickName);

					String prefix = "doctors_" + phone + "_";
					String headUrl = UploadFilesUtils.saveFile(request, headFile, prefix); // 保存头像文件

					DoctorsUser doctorsUser1 = new DoctorsUser();
					doctorsUser1.setEasemobUUid(uuid);// 环信返回来的uuid
					doctorsUser1.setPhone(phone);
					doctorsUser1.setPassword("mingyizaixian123456789");
					doctorsUser1.setSex(sex);
					doctorsUser1.setTitle(title);
					doctorsUser1.setName(userName);
					doctorsUser1.setCertificateUrl(certificateUrl);
					doctorsUser1.setDepartment(department);
					doctorsUser1.setDepartmentTelephone(departmentTelephone);
					doctorsUser1.setHeadUrl(headUrl);
					doctorsUser1.setHospital(hospital);
					doctorsUser1.setIntroduction(introduction);
					doctorsUser1.setCreatetime(new Date());
					doctorsUser1.setLoginId(Constant.doctor + phone);
					doctorsUserService.insert(doctorsUser1);

					SecretaryDoctorsLink secretaryDoctorsLink = new SecretaryDoctorsLink();
					secretaryDoctorsLink.setCreatetime(new Date());
					secretaryDoctorsLink
							.setDoctorsLoginId(Constant.doctor +phone);
					secretaryDoctorsLink.setDoctorsName(null);
					secretaryDoctorsLink.setDoctorsPhone(phone);
					secretaryDoctorsLink.setSecretaryLoginId(Constant.scretary + Constant.scretaryPhone);
					secretaryDoctorsLink.setSecretaryName("小秘书");
					secretaryDoctorsLink.setSecretaryPhone(Constant.scretaryPhone);

					if (secretaryDoctorsLinkList != null&&secretaryDoctorsLinkList.size()<1) {
						secretaryDoctorsLinkService.insert(secretaryDoctorsLink);
					}
					int id2 = secretaryDoctorsLink.getId();
					int id = doctorsUser1.getId();
					if (id > 0 && id2 > 0) {
						results.setCode(200);
						map.put("doctorsUser", doctorsUser1);
						map.put("secretaryDoctorsLink", secretaryDoctorsLink);
						results.setData(map);
						results.setMessage(MessageCode.MESSAGE_200);
						return results;
					} else {
						results.setCode(MessageCode.CODE_501);
						results.setData(null);
						results.setMessage("无法在本地创建用户");
						return results;
					}

				} else {
					results.setCode(MessageCode.CODE_501);
					results.setData(null);
					results.setMessage("无法在环信创建用户");
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
	public @RequestMapping(value = "delete") @ResponseBody Results<String> delete(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// Results<String> results = new Results<String>();

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
					Map<String, Object> paramMap1 = new HashMap<String, Object>();
					paramMap1.put("doctorsPhone", phone);
					// paramMap1.put("secretaryPhone", "12345678912");
					List<SecretaryDoctorsLink> secretaryDoctorsLinkList = secretaryDoctorsLinkService
							.getList("selectBySecretaryPhoneAndDoctorsPhone", paramMap1);

					SecretaryDoctorsLink secretaryDoctorsLink = secretaryDoctorsLinkList.get(0);
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

}
