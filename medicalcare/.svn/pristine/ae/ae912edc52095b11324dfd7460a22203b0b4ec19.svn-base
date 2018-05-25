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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorUserUtils;
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
@RequestMapping("/app/doctorsUser/v2")
public class DoctorsUserV2Controller {

	/**
	 * 
	* @Title: select 
	* @Description: TODO 
	* @param @param request
	* @param @return    设定文件 
	* @return Results<Map<String,Object>>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "select")
	@ResponseBody
	@SystemControllerLog(description = "查询医生资料")
	public Results<Map<String, Object>> select(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String equipmentData = request.getParameter("equipmentData");
		JSONObject dataJsonObject = JSONObject.parseObject(equipmentData);
		String phone;
		if (dataJsonObject != null) {
			phone = dataJsonObject.getString("phone");
			if (StringUtils.isNotBlank(phone)) {
				// 调用接口返回json
				String jsonStr = DoctorUserUtils.findDoctor("", phone, "", "", "");
				
				if (StringUtils.isNotBlank(jsonStr)) {
					System.out.println("jsonStr:  "+jsonStr);
					JSONObject json = JSONObject.parseObject(jsonStr);
					String status = json.getString("status");
					if ("0".equals(status)) {
						JSONObject jsonObject= json.getJSONObject("data");
						JSONArray jsonArray = jsonObject.getJSONArray("doctors");
						JSONArray feesArray = jsonObject.getJSONArray("fees");
						if (jsonArray.size() < 1) {
							results.setCode(MessageCode.CODE_204);
							results.setMessage("不存在该医生");
							return results;
						}
						EasemobIMUsers easemobIMUsers = new EasemobIMUsers();
						Object result = easemobIMUsers.getIMUserByUserName(Constant.doctor + phone);
						if (result == null) {
							RegisterUsers users = new RegisterUsers();
							User user = new User().username(Constant.doctor + phone).password(Constant.password);// 环信测试帐号
							users.add(user);
							Object result2 = easemobIMUsers.createNewIMUserSingle(users);
							if (result2==null) {
								results.setCode(MessageCode.CODE_204);
								results.setMessage("医生注册失败");
								return results;
							}
						}
//						String entity = jsonArray.get(0).toString();
						JSONObject doctorUser_v2 = (JSONObject) jsonArray.get(0);
						doctorUser_v2.put("doctorLoginId", Constant.doctor + phone);
						map.put("doctorUser_v2", doctorUser_v2);
						map.put("fees", feesArray);
						map.put("doctorLoginId", Constant.doctor + phone);
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
		} else {
			results.setCode(MessageCode.CODE_201);
			results.setMessage(MessageCode.MESSAGE_201);
			return results;
		}
	}
}
