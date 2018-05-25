/**   
* @Title: DoctorsUserV2Controller.java 
* @Package com.cmcc.medicalcare.controller.app 
* @Description: TODO
* @author adminstrator   
* @date 2017年4月10日 上午10:32:23 
* @version V1.0   
*/
package com.cmcc.medicalcare.controller.app.doctor.v2;

import java.util.HashMap;
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
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPatientsUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;

/**
 * @ClassName: 医生信息
 * @Description: TODO
 * @author siming.wu
 * @date 2017年4月10日 上午10:32:23
 * 
 */
@Controller
@RequestMapping("/app/doctorsPatients/v2")
public class DoctorsPatientsV2Controller {
	
	@Resource
	private RedisUtil redisUtil;

	/**
	 * 
	* @Title: findDoctorPatient 
	* @Description: TODO 
	* @param @param request
	* @param @param parameter
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "findDoctorPatient")
	@ResponseBody
	@SystemControllerLog(description = "医生查询医患关系V2")
	public Results<Map<String, Object>> findDoctorPatient(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub
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
		map.put("count", count);
		if (doctorPatients!=null&&doctorPatients.size()>0) {
			for(int i=0;i<doctorPatients.size();i++){
				JSONObject doctorPatient = (JSONObject) doctorPatients.get(i);
				String patientPhone = doctorPatient.getString("patientPhone");
				if (StringUtils.isBlank(patientPhone)) {
					patientPhone="";
				}
				JSONObject memberPatientEntity =  MemberUserUtils.findMemberPatient(patientPhone);
				
				if (memberPatientEntity != null) {
					//患者头像
					String headUrl = "";
					headUrl = (String) redisUtil.get(Constants.KEY_HEADURL_PATIENT + patientPhone);	
					memberPatientEntity.put("headUrl", headUrl);
				}
				
				doctorPatient.put("memberPatientEntity", memberPatientEntity);
				doctorPatients2.add(doctorPatient);
			}
			
		}
		map.put("doctorPatients", doctorPatients2);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}
}
