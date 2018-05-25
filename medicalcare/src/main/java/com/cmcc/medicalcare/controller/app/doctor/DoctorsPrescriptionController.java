package com.cmcc.medicalcare.controller.app.doctor;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.Orders;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.service.IOrdersService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;
import com.cmcc.medicalcare.vo.UserInfo;

/**
* 医生处方
* @author zhouzhou
* @version 2018年1月22日 下午3:31:21
* TODO
*/
@Controller
@RequestMapping({ "/app/doctors/prescription" })
public class DoctorsPrescriptionController {

	@Resource
	private IMediaLogService mediaLogService;
	
	@Resource
	private IOrdersService ordersService;
	
	@SystemControllerLog(description = "医生的处方上传")
	@RequestMapping(value = "upload")
	@ResponseBody
	public Results<String> upload(String doctorName,String doctorPhone,String patientName,String patientPhone,MultipartFile image,HttpServletRequest request) {
		Results<String> results = new Results<String>();
		
		if (image != null && !FileFilterUtils.isMediaFileType(image.getOriginalFilename())) {
			results.setCode(MessageCode.CODE_404);
			results.setMessage("不支持该文件格式上传");
			return results;
		}
		
		//缓存图片到服务
		String filetempUrl = UploadFilesUtils.cacheFile(request, image,""); // 保存临时文件
		File file = new File(filetempUrl);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserLoginId("doctors_"+doctorPhone);
		userInfo.setUserName(doctorName);
		userInfo.setUserPhone(doctorPhone);
		String fileUrl = UploadFilesUtils.formUploadFileToServer(mediaLogService, userInfo, request, file); // 保存远程文件
		
		//上传处方到九州通
		int prescriptionId = 0;
		
		//生成订单
		Orders orders = new Orders();
		orders.setDoctorLoginId("doctors_"+doctorPhone);
		orders.setDoctorName(doctorName);
		orders.setDoctorPhone(doctorPhone);
		orders.setPatientLoginId("patient_"+patientPhone);
		orders.setPatientName(patientName);
		orders.setPatientPhone(patientPhone);
		orders.setOrdersTitle("医生开处方");
		orders.setOrdersType(6);
		orders.setPrescriptionId(prescriptionId);
		orders.setPayType(2);
		orders.setOrdersPayState(0);
		orders.setOrdersState(0);
		ordersService.insert(orders);
		
		return results;
	}
	
}
