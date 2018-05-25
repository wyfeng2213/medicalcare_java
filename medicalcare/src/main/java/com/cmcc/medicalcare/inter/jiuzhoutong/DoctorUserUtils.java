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
public class DoctorUserUtils {

	private final static String updateDoctorLoginCodeUrl = Config.jiuZhouTongTestUrl+"/out/updateDoctorLoginCode?";
	
	private final static String findDoctorUrl = Config.jiuZhouTongTestUrl+"/out/findDoctor?";

//	private final static String findDoctorUrl = Constant.URL+"/out/findDoctor?";
	
	/**
	 * 医生唯一标识添加/修改
	* @Title: updateMemberLoginCode 
	* @Description: TODO 
	* @param @param phone
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String updateDoctorLoginCode(String phone,String appUserId) {
		String method = "/out/updateDoctorLoginCode";
		
		String timestamp = Toolkit.getyyyyMMddHHmmss();		
		String signstr= ThirdInterfaceUtils.getMD5signstr(method, timestamp);
		
		String param = "phone="+phone+"&appUserId="+appUserId;
		
		String url = ThirdInterfaceUtils.getURL(updateDoctorLoginCodeUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param,"UTF-8");
		return jsonStr;
	}
	
	
	/**
	 * 3.16	医生信息查询接口
	* @Title: findDoctor 
	* @Description: TODO 
	* @param @param phone 医生手机号
	* @param @param deptCode 医生科室代码
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String findDoctor(String doctorId,String phone,String deptCode,String doctorName,String startNumber) {
		String method = "/out/findDoctor";
		
		String timestamp = Toolkit.getyyyyMMddHHmmss();		
		String signstr= ThirdInterfaceUtils.getMD5signstr(method, timestamp);
		
		String param = "doctorId="+doctorId+"&phone="+phone+"&deptCode="+deptCode+"&doctorName="+doctorName+"&startNumber="+startNumber;
		
		String url = ThirdInterfaceUtils.getURL(findDoctorUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendPost(url, param,"UTF-8");
		return jsonStr;
	}
	
	/**
	 * 根据电话查询返回医生JSONObject实体
	 * @param phone
	 * @param deptCode
	 * @param doctorName
	 * @return
	 */
	public static JSONObject findDoctorInfo(String phone) {
		JSONObject doctorEntity = new JSONObject();
		//医生信息查询
		JSONObject doctorInfo = null;
		boolean isExist_doctor = false;
		String jsonStr = DoctorUserUtils.findDoctor("",phone, "", "","");
		if (jsonStr != null) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONObject jsonObject= json.getJSONObject("data");
				JSONArray jsonArray = jsonObject.getJSONArray("doctors");
				if (jsonArray != null && jsonArray.size() > 0) {
					doctorInfo = (JSONObject) jsonArray.get(0);
					isExist_doctor = true;
				}
			}
		}
		if (!isExist_doctor) {
			return null;
		}
		String doctorId = doctorInfo.getString("doctorId");//医生平台ID
		String name = doctorInfo.getString("name");//医生姓名
		String gender = doctorInfo.getString("gender");//性别
		String age = doctorInfo.getString("age");//年龄
		String hospitalName = doctorInfo.getString("hospitalName");//医院
		String hospitalDepartment = doctorInfo.getString("hospitalDepartment");//科室
		String doctorPhone = doctorInfo.getString("phone");//手机号
		String mail = doctorInfo.getString("mail");//邮箱
		String doctorBrief = doctorInfo.getString("doctorBrief");//简介
		String doctorGood = doctorInfo.getString("doctorGood");//擅长
		String qrcode = doctorInfo.getString("qrcode");//二维码
		String departmentCode = doctorInfo.getString("departmentCode");//科室代码
		String hospitaCode = doctorInfo.getString("hospitaCode");//医院代码
		String picture = doctorInfo.getString("picture");//医生头像
		
		doctorEntity.put("doctorId", doctorId);
		doctorEntity.put("name", name);
		doctorEntity.put("phone", doctorPhone);
		doctorEntity.put("gender", gender);
		doctorEntity.put("age", age);
		doctorEntity.put("hospitalName", hospitalName);
		doctorEntity.put("hospitalDepartment", hospitalDepartment);
		doctorEntity.put("mail", mail);
		doctorEntity.put("doctorBrief", doctorBrief);
		doctorEntity.put("doctorGood", doctorGood);
		doctorEntity.put("qrcode", qrcode);
		doctorEntity.put("departmentCode", departmentCode);
		doctorEntity.put("hospitaCode", hospitaCode);
		doctorEntity.put("picture", picture);
		
		return doctorEntity;
	}
	
	/**
	 * 根据doctorId和doctorName查询返回医生JSONObject实体
	 * @param doctorId
	 * @param doctorName
	 * @return JSONObject
	 */
	public static JSONObject findDoctorInfo(String doctorId, String doctorName) {
		JSONObject doctorEntity = new JSONObject();
		//医生信息查询
		JSONObject doctorInfo = null;
		boolean isExist_doctor = false;
		String jsonStr = DoctorUserUtils.findDoctor("","", "", doctorName,"");
		if (jsonStr != null) {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String status = json.getString("status");
			if ("0".equals(status)) {
				JSONObject jsonObject= json.getJSONObject("data");
				JSONArray jsonArray = jsonObject.getJSONArray("doctors");
				if (jsonArray != null && jsonArray.size() > 0) {
					for (int i=0; i<jsonArray.size(); i++) {
						JSONObject doctorInfo_ = (JSONObject) jsonArray.get(0);
						if (doctorInfo_.getString("doctorId").equals(doctorId)) {
							doctorInfo = doctorInfo_;
							isExist_doctor = true;
							break;
						}
					}
				}
			}
		}
		if (!isExist_doctor) {
			return null;
		}
		String gender = doctorInfo.getString("gender");//性别
		String age = doctorInfo.getString("age");//年龄
		String hospitalName = doctorInfo.getString("hospitalName");//医院
		String hospitalDepartment = doctorInfo.getString("hospitalDepartment");//科室
		String doctorPhone = doctorInfo.getString("phone");//手机号
		String mail = doctorInfo.getString("mail");//邮箱
		String doctorBrief = doctorInfo.getString("doctorBrief");//简介
		String doctorGood = doctorInfo.getString("doctorGood");//擅长
		String qrcode = doctorInfo.getString("qrcode");//二维码
		String departmentCode = doctorInfo.getString("departmentCode");//科室代码
		String hospitaCode = doctorInfo.getString("hospitaCode");//医院代码
		String picture = doctorInfo.getString("picture");//医生头像
		
		doctorEntity.put("doctorId", doctorId);
		doctorEntity.put("name", doctorName);
		doctorEntity.put("phone", doctorPhone);
		doctorEntity.put("gender", gender);
		doctorEntity.put("age", age);
		doctorEntity.put("hospitalName", hospitalName);
		doctorEntity.put("hospitalDepartment", hospitalDepartment);
		doctorEntity.put("mail", mail);
		doctorEntity.put("doctorBrief", doctorBrief);
		doctorEntity.put("doctorGood", doctorGood);
		doctorEntity.put("qrcode", qrcode);
		doctorEntity.put("departmentCode", departmentCode);
		doctorEntity.put("hospitaCode", hospitaCode);
		doctorEntity.put("picture", picture);
		
		return doctorEntity;
	}

	public static void main(String[] args) {
		System.out.println(DoctorUserUtils.findDoctorInfo("", ""));
//		System.out.println(DoctorUserUtils.findDoctorInfo("15876587133"));
//		System.out.println(DoctorUserUtils.findDoctor("15876587133", "",""));
	}
}
