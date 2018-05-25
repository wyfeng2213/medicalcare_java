package com.cmcc.medicalcare.inter.jiuzhoutong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName:
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
public class MemberUserUtils {

	private final static String updateMemberLoginCodeUrl = Config.jiuZhouTongTestUrl + "/out/updateMemberLoginCode?";

	private final static String updateMemberUrl = Config.jiuZhouTongTestUrl + "/out/updateMember?";

	private final static String saveMemberUrl = Config.jiuZhouTongTestUrl + "/out/saveMember?";
	
	private final static String findMemberUrl = Config.jiuZhouTongTestUrl + "/out/findMember?";
	
	/**
	 * 查询会员
	 * 
	 * @Title: findPatient @Description: TODO @param @param phone @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	public static String findMember(String phone, String memberId, String startNumber) {
		String method = "/out/findMember";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("phone", phone);
		String param = "phone=" + phone+"&memberId="+memberId+"&startNumber="+startNumber;

		String url = ThirdInterfaceUtils.getURL(findMemberUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	/**
	 * 会员唯一标识添加/修改 @Title: updateMemberLoginCode @Description:
	 * TODO @param @param phone @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String updateMemberLoginCode(String phone, String appUserId) {
		String method = "/out/updateMemberLoginCode";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		String param = "phone=" + phone + "&appUserId=" + appUserId;

		String url = ThirdInterfaceUtils.getURL(updateMemberLoginCodeUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	/**
	 * 会员基本信息更新 @Title: updateMember @Description: TODO @param @param
	 * phone @param @param name @param @param updatePhone @param @param
	 * idNumber @param @param gender @param @param age @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String updateMember(String phone, String name, String updatePhone, String idNumber, String gender,
			String age) {
		String method = "/out/updateMember";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("phone", phone);
		// param.put("name", name);
		// param.put("updatePhone", updatePhone);
		// param.put("idNumber", idNumber);
		// param.put("gender", gender);
		// param.put("age", age);
		String param = "phone=" + phone + "&name=" + name + "&updatePhone=" + updatePhone + "&idNumber=" + idNumber
				+ "&gender=" + gender + "&age=" + age;

		String url = ThirdInterfaceUtils.getURL(updateMemberUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}

	/**
	 * 会员基本信息注册 @Title: saveMember @Description: TODO @param @param
	 * phone @param @param name @param @param updatePhone @param @param
	 * idNumber @param @param gender 1:男 2:女 3:不详 9:未知 @param @param
	 * age @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String saveMember(String phone, String name, String idNumber, String gender, String age) {
		String method = "/out/saveMember";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("phone", phone);
//		param.put("name", name);
//		param.put("idNumber", idNumber);
//		param.put("gender", gender);
//		param.put("age", age);
		String param = "phone=" + phone + "&name=" + name + "&idNumber=" + idNumber
				+ "&gender=" + gender + "&age=" + age;

		String url = ThirdInterfaceUtils.getURL(saveMemberUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param, "UTF-8");
		return jsonStr;
	}
	
	/**
	 * 根据患者电话查询
	 * 
	 * @param phone
	 * @return JSONObject
	 */
	public static JSONObject findMemberPatient(String phone) {
		JSONObject memberPatient = new JSONObject();
		//查询九州通会员信息
		JSONObject memberInfo = null;
		boolean isExist_member = false;
		String jsonStr = MemberUserUtils.findMember(phone, "","");
		if (jsonStr != null) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONObject jsonObject= json.getJSONObject("data");
				JSONArray jsonArray = jsonObject.getJSONArray("members");
				if (jsonArray != null && jsonArray.size() > 0) {
					memberInfo = (JSONObject) jsonArray.get(0);
					isExist_member = true;
				}
			}
		}
		if (!isExist_member) {
			return null;
		}
		String memberId = memberInfo.getString("memberId");
		String memberName = memberInfo.getString("name");
		String idNumber = memberInfo.getString("idNumber");
		
		//查询九州通患者信息
		JSONObject patientUser = null;
		boolean isExist_patient = false;
		String jsonStr2 = PatientUserUtils.findPatient("", memberId);
		if (jsonStr2 != null) {
			JSONObject json = JSONObject.parseObject(jsonStr2);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONArray jsonArray = json.getJSONArray("data");
				if (jsonArray != null && jsonArray.size() > 0) {
					for (int i=0; i<jsonArray.size(); i++) {
						JSONObject patientUser_ = (JSONObject) jsonArray.get(i);
						if (patientUser_.getString("phone").equals(phone)) {
							patientUser = patientUser_;
							isExist_patient = true;
							break;
						}
					}
				}
			}
		}
		if (!isExist_patient) {
			return null;
		}
		String patientId = patientUser.getString("patientId");
		String patientName = patientUser.getString("name");
		String gender = patientUser.getString("gender");
		String age = patientUser.getString("age");
		String occupation = patientUser.getString("occupation");
		String height = patientUser.getString("height");
		String province = patientUser.getString("province");
		String city = patientUser.getString("city");
		String area = patientUser.getString("area");
		
		//合并member和Patient实体
		memberPatient.put("memberId", memberId);
		memberPatient.put("memberName", memberName);
		memberPatient.put("idNumber", idNumber);
		memberPatient.put("phone", phone);
		memberPatient.put("patientId", patientId);
		memberPatient.put("patientName", patientName);
		memberPatient.put("gender", gender);
		memberPatient.put("age", age);
		memberPatient.put("occupation", occupation);
		memberPatient.put("height", height);
		memberPatient.put("province", province);
		memberPatient.put("city", city);
		memberPatient.put("area", area);
		
		return memberPatient;
		
	}
	
	public static void main(String[] args) {
//		System.out.println(MemberUserUtils.findMemberPatient("13432947998"));
//		System.out.println(MemberUserUtils.findMember("13432947998", ""));
//		System.out.println(MemberUserUtils.saveMember("15876587133", "吴思铭", "440902199008150812", "1", "18"));
//		System.out.println(MemberUserUtils.updateMember("15876587133", "David Wu", "15876587133", "440902199008150812", "1", "20"));

	}

}
