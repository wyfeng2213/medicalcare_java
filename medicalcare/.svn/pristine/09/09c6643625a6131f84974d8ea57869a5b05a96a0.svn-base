package com.cmcc.medicalcare.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.jiuzhoutong.DoctorPrescriptionUtils;
import com.cmcc.medicalcare.model.SensitiveWord;
import com.cmcc.medicalcare.service.IMediaLogService;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

	@Resource
	private IMediaLogService mediaLogService;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "test1")
	@ResponseBody
	@SystemControllerLog(description = "测试")
	public Results<Object> test1(HttpServletRequest request,MultipartFile imgFile) {
		Results<Object> results = new Results<Object>();
		
		//mediaLogService.saveMediaLog("15876587133", "wsm", "doctor_15876587133", request, null);
//		String jsonStr = DoctorPrescriptionUtils.uploadPrescription("3", "3", "4", imgFile);
//		results.setData(jsonStr);
		results.setCode(200);
		results.setMessage("ok");
		return results;
	}

	public static void main(String[] args) {
		File file = new File("/Users/zhouzhou/Projects/cmcc/mobilehealthcare/apk/doctor-cmcc-release-1.0.2.apk");
		ApkInfo apkInfo = null;
		try {
			apkInfo = GetApkInfo.getApkInfoByFilePath(file.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(apkInfo);
	}
}
