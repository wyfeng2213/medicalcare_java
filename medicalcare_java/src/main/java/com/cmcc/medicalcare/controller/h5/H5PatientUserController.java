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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PatientUser;
import com.cmcc.medicalcare.service.IPatientUserService;
import com.easemob.server.api.impl.EasemobIMUsers;

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
@RequestMapping("/h5/patientUser")
public class H5PatientUserController {
	
	@Resource
	private IPatientUserService patientUserService;

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
	public Results<Map<String, Object>> insert(HttpServletRequest request,@RequestParam String equipmentData,@RequestParam String patientUser) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		JSONObject patientUserObject = JSONObject.parseObject(patientUser);
		
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
				// System.out.println(":::::" + activated);
				// System.out.println(":::::" + uuid);
				if (activated != null && "true".equals(activated)) {
					PatientUser patientUser1 = new PatientUser();
					patientUser1.setEasemobUUid(uuid);// 环信返回来的uuid
					patientUser1.setPassword("mingyizaixian123456789");
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
					patientUser1.setHeadUrl(patientUserObject.getString("headUrl"));
					patientUser1.setVisitState(patientUserObject.getString("visitState"));
					patientUser1.setHospital(patientUserObject.getString("hospital"));
					patientUser1.setRemarks(patientUserObject.getString("remarks"));
					patientUser1.setCreatetime(new Date());
					patientUserService.insert(patientUser1);
					int id = patientUser1.getId();
					if (id > 0) {
						results.setCode(200);
						map.put("patientUser", patientUser1);
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

}
