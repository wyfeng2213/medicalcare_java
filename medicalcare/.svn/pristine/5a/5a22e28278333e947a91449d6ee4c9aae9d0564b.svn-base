/**   
* @Title: DoctorsUserV2Controller.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:32:23 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.patient.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.cache.redis.Constants;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPatientsUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.easemob.server.api.impl.EasemobIMUsers;

import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

/**
 * @ClassName: 医生信息
 * @Description: TODO
 * @author siming.wu
 * @date 2017年4月10日 上午10:32:23
 * 
 */
@Controller
@RequestMapping("/app/patientDoctors/v2")
public class PatientDoctorsV2Controller {
	
	@Resource
	private RedisUtil redisUtil;

	/**
	 * parameter={"doctorPhone":"","deptCode":"","doctorName": "" } @Title:
	 * findDoctor @Description: TODO @param @param request @param @return
	 * 设定文件 @return Results<Map<String,Object>> 返回类型 @throws
	 */
	@RequestMapping(value = "findDoctor")
	@ResponseBody
	@SystemControllerLog(description = "患者查找医生V2")
	public Results<Map<String, Object>> findDoctor(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);

		if (dataJsonObject != null) {
			String doctorPhone = dataJsonObject.getString("doctorPhone");
			String deptCode = dataJsonObject.getString("deptCode");
			String doctorName = dataJsonObject.getString("doctorName");
			if (StringUtils.isBlank(doctorPhone)) {
				doctorPhone = "";
			}
			if (StringUtils.isBlank(deptCode)) {
				deptCode = "";
			}
			if (StringUtils.isBlank(doctorName)) {
				doctorName = "";
			}
			// 调用接口返回json
			String jsonStr = DoctorUserUtils.findDoctor("", doctorPhone, deptCode, doctorName, "");

			if (StringUtils.isNotBlank(jsonStr)) {
				System.out.println("jsonStr:  " + jsonStr);
				JSONObject json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					JSONObject jsonObject = json.getJSONObject("data");
					JSONArray jsonArray = jsonObject.getJSONArray("doctors");
					JSONArray feesArray = jsonObject.getJSONArray("fees");
					if (jsonArray.size() < 1) {
						results.setCode(MessageCode.CODE_204);
						results.setMessage("不存在该医生");
						return results;
					}
					map.put("doctorUserList_v2", jsonArray);
					map.put("fees", feesArray);
					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功");
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_204);
					results.setMessage(json.getString("msg"));
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("接口调用失败");
				return results;
			}
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}
	
	
	/**
	 * parameter={"keyWord":""}
	* @Title: fuzzySearch 
	* @Description: TODO 
	* @param @param request
	* @param @param parameter
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "fuzzySearch")
	@ResponseBody
	@SystemControllerLog(description = "患者模糊查找医生V2")
	public Results<Map<String, Object>> fuzzySearch(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);

		if (dataJsonObject != null) {
			String keyWord = dataJsonObject.getString("keyWord");
			if (StringUtils.isBlank(keyWord)) {
				results.setCode(MessageCode.CODE_201);
				results.setMessage("参数不能为空");
				return results;
			}
			// 调用接口返回json
			String jsonStr = DoctorUserUtils.findDoctor("", "", "", keyWord, "");

			if (StringUtils.isNotBlank(jsonStr)) {
				System.out.println("jsonStr:  " + jsonStr);
				JSONObject json = JSONObject.parseObject(jsonStr);
				String status = json.getString("status");
				if ("0".equals(status)) {
					JSONObject jsonObject = json.getJSONObject("data");
					JSONArray feesArray = jsonObject.getJSONArray("fees");
					JSONArray jsonArray = jsonObject.getJSONArray("doctors");
					if (jsonArray.size() < 1) {
						results.setCode(MessageCode.CODE_204);
						results.setMessage("不存在该医生");
						return results;
					}
					for(int i=0;i<jsonArray.size();i++){
						list.add(((JSONObject)jsonArray.get(i)).getString("name"));
					}
					map.put("doctorUserNameList_v2", list);
					map.put("fees", feesArray);
					results.setCode(MessageCode.CODE_200);
					results.setMessage("成功");
					results.setData(map);
					return results;
				} else {
					results.setCode(MessageCode.CODE_204);
					results.setMessage(json.getString("msg"));
					return results;
				}
			} else {
				results.setCode(MessageCode.CODE_404);
				results.setMessage("接口调用失败");
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
	* @Title: findPatientDoctor 
	* @Description: TODO 
	* @param @param request
	* @param @param parameter
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "findPatientDoctor")
	@ResponseBody
	@SystemControllerLog(description = "患者查询医患关系V2")
	public Results<Map<String, Object>> findPatientDoctor(HttpServletRequest request, String parameter) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorPatientId = dataJsonObject.getString("doctorPatientId");
		String memberId = dataJsonObject.getString("memberId");
		String patientId = dataJsonObject.getString("patientId");
		String doctorId = dataJsonObject.getString("doctorId");
		String startNumber = dataJsonObject.getString("startNumber");

		if (StringUtils.isBlank(doctorPatientId)) {
			doctorPatientId = "";
		}
		if (StringUtils.isBlank(patientId)) {
			patientId = "";
		}
		if (StringUtils.isBlank(doctorId)) {
			doctorId = "";
		}
		if (StringUtils.isBlank(memberId)) {
			memberId = "";
		}
		if (StringUtils.isBlank(startNumber)) {
			startNumber = "";
		}

		String jsonStr = DoctorPatientsUtils.findDoctorPatient(doctorId, memberId, patientId, doctorPatientId, startNumber);
		if (StringUtils.isBlank(jsonStr)) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("第三方接口调用失败");
			return results;
		}
		JSONObject json = JSONObject.parseObject(jsonStr);
		String status = json.getString("status");
		if ("1".equals(status)) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage(json.getString("msg"));
			return results;
		}
		JSONObject json2 = json.getJSONObject("data");
		String count = json2.getString("count");
		JSONArray doctorPatients = json2.getJSONArray("doctorPatients");
		JSONArray doctorPatients2 = new JSONArray();
		if (doctorPatients!=null&&doctorPatients.size()>0) {
			for(int i=0;i<doctorPatients.size();i++){
				JSONObject doctorPatient = (JSONObject) doctorPatients.get(i);
				String doctorPhone = doctorPatient.getString("doctorPhone");
				if (StringUtils.isBlank(doctorPhone)) {
					doctorPhone="";
				}
				JSONObject doctorEntity = DoctorUserUtils.findDoctorInfo(doctorPhone);
				
				doctorPatient.put("doctorEntity", doctorEntity);
				doctorPatients2.add(doctorPatient);
			}
		}
		
		map.put("count", count);
		map.put("doctorPatients", doctorPatients2);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}
	
}
