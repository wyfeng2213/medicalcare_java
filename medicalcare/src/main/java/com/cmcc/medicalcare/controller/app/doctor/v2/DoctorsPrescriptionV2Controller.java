package com.cmcc.medicalcare.controller.app.doctor.v2;

import java.io.File;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.cache.redis.Constants;
import com.cmcc.medicalcare.cache.redis.RedisUtil;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPrescriptionUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.MemberUserUtils;
import com.cmcc.medicalcare.inter.jiuzhoutong.PatientUserUtils;
import com.cmcc.medicalcare.model.DoctorsUser;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;

/**
 * 医生处方
 * 
 * @author zhouzhou
 * @version 2018年1月22日 下午3:31:21 TODO
 */
@Controller
@RequestMapping({ "/app/doctors/prescription/v2" })
public class DoctorsPrescriptionV2Controller {

	@Resource
	private IMediaLogService mediaLogService;

	@Resource
	private IOrdersService ordersService;
	
	@Resource
	private RedisUtil redisUtil;

	/**
	 * 医生的处方上传
	 * 
	 * @param request
	 * @param parameter={"doctorId":"医生id", "doctorName":"", "doctorPhone":"", "patientPhone":"", "serviceMoney":"服务费", "fee":"咨询费"}
	 * @param image
	 * @return
	 */
	@SystemControllerLog(description = "医生的处方上传")
	@RequestMapping(value = "upload")
	@ResponseBody
	public Results<Map<String, Object>> upload(HttpServletRequest request, @RequestParam String parameter, MultipartFile image) {
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String doctorId = dataJsonObject.getString("doctorId");
		String doctorName = dataJsonObject.getString("doctorName");
		String doctorPhone = dataJsonObject.getString("doctorPhone");
		String patientPhone = dataJsonObject.getString("patientPhone");
		String serviceMoney = dataJsonObject.getString("serviceMoney");
		String fee = dataJsonObject.getString("fee");
		
		if (image != null && !FileFilterUtils.isMediaFileType(image.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		if (StringUtils.isBlank(doctorId) || StringUtils.isBlank(patientPhone)) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage("医生ID、患者电话不能为空");
			return results;
		}
		
		String memberId = "";
		String patientId = "";
		String patientName = "";
		//获取缓存九州通会员信息
		Object memberInfo_o = redisUtil.get(Constants.KEY_USER_MEMBER + patientPhone);
		//获取缓存九州通患者信息
		Object patientUser_o = redisUtil.get(Constants.KEY_USER_PATIENT + patientPhone);
		if (null == memberInfo_o || null == patientUser_o) {
			//查询九州通用户信息
			JSONObject memberPatient = MemberUserUtils.findMemberPatient(patientPhone);
			if (null == memberPatient) {
				results.setCode(MessageCode.CODE_204);
				results.setMessage("该患者尚未注册");
				return results;
			} else {
				memberId = memberPatient.getString("memberId");
				patientId = memberPatient.getString("patientId");
				patientName = memberPatient.getString("memberName");
			}
		} else {
			JSONObject memberInfo = (JSONObject) memberInfo_o;
			JSONObject patientUser = (JSONObject) patientUser_o;
			memberId = memberInfo.getString("memberId");
			patientId = patientUser.getString("patientId");
			patientName = patientUser.getString("name");
		}
		
		// 缓存图片到服务
		String filetempUrl = UploadFilesUtils.cacheFile(request, image, ""); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId(Constant.doctor + doctorPhone);
		userInfo.setUserName(doctorName);
		userInfo.setUserPhone(doctorPhone);
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		String jsonstr = DoctorPrescriptionUtils.uploadPrescription(memberId, patientId, doctorId, fileUrl, serviceMoney, fee);
		if (StringUtils.isBlank(jsonstr)) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("第三方接口调用失败");
			return results;
		}
		JSONObject json = JSONObject.parseObject(jsonstr);
		String status = json.getString("status");
		if ("1".equals(status)) {
			results.setCode(MessageCode.CODE_204);
			results.setMessage(json.getString("msg"));
			return results;
		}
		JSONObject jsonObject = json.getJSONObject("data");
		// 上传处方到九州通
		Integer prescriptionId = jsonObject.getInteger("prescriptionId");
		map.put("prescriptionId", prescriptionId);
		// 生成订单
		Orders orders = new Orders();
		orders.setDoctorLoginId(Constant.doctor + doctorPhone);
		orders.setDoctorName(doctorName);
		orders.setDoctorPhone(doctorPhone);
		orders.setPatientLoginId(Constant.patient + patientPhone);
		orders.setPatientName(patientName);
		orders.setPatientPhone(patientPhone);
		orders.setOrdersTitle("医生开处方");
		orders.setOrdersType(6);
		orders.setPrescriptionId(prescriptionId);
		orders.setPayType(2);
		orders.setOrdersPayState(0);
		orders.setOrdersState(0);
		ordersService.insert(orders);
		
		//在九州通添加医患关系
		ordersService.saveDoctorPatient(doctorPhone, patientPhone);

		results.setCode(MessageCode.CODE_200);
		results.setData(map);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;
	}

	/**
	 * parameter={"memberId":"","patientId":"","doctorId": ""
	 * ,"startTime":"","endTime":"","prescriptionId":"","startNumber":""
	 * } @Title: findPrescription @Description: TODO @param @param
	 * request @param @param parameter @param @return 设定文件 @return
	 * Results<Map<String,Object>> 返回类型 @throws
	 */
	@SystemControllerLog(description = "医生处方查询")
	@RequestMapping(value = "findPrescription")
	@ResponseBody
	public Results<Map<String, Object>> findPrescription(HttpServletRequest request, String parameter) {
		//
		Results<Map<String, Object>> results = new Results<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject dataJsonObject = JSONObject.parseObject(parameter);
		String memberId = dataJsonObject.getString("memberId");
		String patientId = dataJsonObject.getString("patientId");
		String doctorId = dataJsonObject.getString("doctorId");
		String startTime = dataJsonObject.getString("startTime");
		String endTime = dataJsonObject.getString("endTime");
		String prescriptionId = dataJsonObject.getString("prescriptionId");
		String startNumber = dataJsonObject.getString("startNumber");
		if (StringUtils.isBlank(memberId)) {
			memberId = "";
		}
		if (StringUtils.isBlank(patientId)) {
			patientId = "";
		}
		if (StringUtils.isBlank(doctorId)) {
			doctorId = "";
		}
		if (StringUtils.isBlank(startTime)) {
			startTime = "";
		}
		if (StringUtils.isBlank(endTime)) {
			endTime = "";
		}
		if (StringUtils.isBlank(prescriptionId)) {
			prescriptionId = "";
		}
		if (StringUtils.isBlank(startNumber)) {
			startNumber = "";
		}

		String jsonStr = DoctorPrescriptionUtils.findPrescription(memberId, patientId, doctorId, startTime, endTime,
				prescriptionId, startNumber);
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
		JSONArray prescriptions = json2.getJSONArray("prescriptions");
		JSONArray prescriptions2 = new JSONArray();
		if (prescriptions!=null&&prescriptions.size()>1) {
			for(int i=0;i<prescriptions.size();i++){
				JSONObject prescription = (JSONObject) prescriptions.get(i);
				String memberId2 = prescription.getString("memberId");
				String patientId2 = prescription.getString("patientId");
				JSONObject patientInfo = PatientUserUtils.findPatientInfo(memberId2, patientId2);
				prescription.put("patientInfo", patientInfo);
				prescriptions2.add(prescription);
			}
		}
		JSONArray prescriptiondrugs = json2.getJSONArray("prescriptiondrugs");
		map.put("prescriptions", prescriptions2);
		map.put("prescriptiondrugs", prescriptiondrugs);
		results.setData(map);
		results.setCode(MessageCode.CODE_200);
		results.setMessage(MessageCode.MESSAGE_200);
		return results;

	}
}
