package com.cmcc.medicalcare.inter.jiuzhoutong;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * 
 * @ClassName: PatientUserUtils
 * @Description: TODO
 * @author adminstrator
 * @date 2018年1月17日 上午11:13:22
 *
 */
public class PatientUserUtils {

	private final static String findPatientUrl = Config.jiuZhouTongTestUrl + "/out/findPatient?";

	private final static String savePatientUrl =Config.jiuZhouTongTestUrl + "/out/savePatient?";

	private final static String updatePatientUrl = Config.jiuZhouTongTestUrl + "/out/updatePatient?";

	/**
	 * 查询患者
	 * 
	 * @Title: findPatient @Description: TODO @param @param phone @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	public static String findPatient(String patientId,String memberId) {
		String method = "/out/findPatient";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("phone", phone);
		String param = "patientId=" + patientId+"&memberId="+memberId;

		String url = ThirdInterfaceUtils.getURL(findPatientUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	/**
	 * 2.7 患者信息添加 @Title: savePatient @Description: TODO @param @param
	 * phone @param @param name @param @param memberPhone @param @param
	 * age @param @param gender 1:男 2:女 3:不详 9:未知 @param @param
	 * occupation @param @param height @param @param idNumber @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String savePatient(String phone, String name, String memberPhone, String age, String gender,
			String occupation, String height, String idNumber) {
		String method = "/out/savePatient";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("phone", phone);
		// param.put("name", name);
		// param.put("memberPhone", memberPhone);
		// param.put("age", age);
		// param.put("gender", gender);
		// param.put("occupation", occupation);
		// param.put("height", height);
		// param.put("idNumber", idNumber);
		String param = "phone=" + phone + "&name=" + name + "&memberPhone=" + memberPhone + "&age=" + age + "&gender="
				+ gender + "&occupation=" + occupation + "&height=" + height + "&idNumber=" + idNumber;

		String url = ThirdInterfaceUtils.getURL(savePatientUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}
	
	/**
	 * 2.8 患者信息修改 @Title: updatePatient @Description: TODO @param @param
	 * phone @param @param name @param @param patientId @param @param
	 * memberPhone @param @param age @param @param gender 1:男 2:女 3:不详
	 * 9:未知 @param @param occupation @param @param height @param @param
	 * idNumber @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String updatePatient(String phone, String name, String patientId, String memberPhone, String age,
			String gender, String occupation, String height, String idNumber) {
		String method = "/out/updatePatient";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("phone", phone);
		// param.put("name", name);
		// param.put("patientId", patientId);
		// param.put("memberPhone", memberPhone);
		// param.put("age", age);
		// param.put("gender", gender);
		// param.put("occupation", occupation);
		// param.put("height", height);
		// param.put("idNumber", idNumber);
		String param = "phone=" + phone + "&name=" + name + "&patientId=" + patientId + "&memberPhone=" + memberPhone
				+ "&age=" + age + "&gender=" + gender + "&occupation=" + occupation + "&height=" + height + "&idNumber="
				+ idNumber;

		String url = ThirdInterfaceUtils.getURL(updatePatientUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}
	
	/**
	 * 根据memberId和patientId查询返回患者JSONObject实体
	 * @param memberId_
	 * @param patientId_
	 * @return
	 */
	public static JSONObject findPatientInfo(String memberId, String patientId) {
		JSONObject patientEntity = new JSONObject();
		//患者信息查询
		JSONObject patientInfo = null;
		boolean isExist_patient = false;
		String jsonStr = PatientUserUtils.findPatient(patientId, memberId);
		if (jsonStr != null) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = json.getJSONArray("data");
				if (jsonArray != null && jsonArray.size() > 0) {
					patientInfo = (JSONObject) jsonArray.get(0);
					isExist_patient = true;
				}
			}
		}
		if (!isExist_patient) {
			return null;
		}
		String name = patientInfo.getString("name");//患者姓名
		String age = patientInfo.getString("age");//年龄
		String gender = patientInfo.getString("gender");//性别
		String occupation = patientInfo.getString("occupation");//职业
		String height = patientInfo.getString("height");//身高
		String idNumber = patientInfo.getString("idNumber");//身份证号
		String phone = patientInfo.getString("phone");//患者手机号
		String province = patientInfo.getString("province");//省
		String city = patientInfo.getString("city");//市
		String area = patientInfo.getString("area");//区
		
		patientEntity.put("memberId", memberId);
		patientEntity.put("patientId", patientId);
		patientEntity.put("phone", phone);
		patientEntity.put("name", name);
		patientEntity.put("age", age);
		patientEntity.put("gender", gender);
		patientEntity.put("occupation", occupation);
		patientEntity.put("height", height);
		patientEntity.put("idNumber", idNumber);
		patientEntity.put("province", province);
		patientEntity.put("city", city);
		patientEntity.put("area", area);
		
		return patientEntity;
	}
	
	public static void main(String[] args) {
		System.out.println(PatientUserUtils.findPatientInfo("34", ""));
//		System.out.println(PatientUserUtils.findPatient("", ""));
//		System.out.println(PatientUserUtils.updatePatient("15876587133", "sssi", "3", "15876587133", "", "1", "IT", "175", "440902199008150812"));
	}
	
}
