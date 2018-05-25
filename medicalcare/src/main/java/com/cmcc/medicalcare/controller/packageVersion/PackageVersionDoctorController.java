package com.cmcc.medicalcare.controller.packageVersion;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PackageVersionDoctor;
import com.cmcc.medicalcare.service.IPackageVersionDoctorService;

/** 
* @ClassName: 包版本Controller
* @Description: TODO
* @author adminstrator
* @date 2017年4月10日 上午11:50:27 
*  
*/
@Controller
@RequestMapping("/pav/packageVersionDoctor")
public class PackageVersionDoctorController {
	
	@Resource
	private IPackageVersionDoctorService packageVersionDoctorService;
	
	
	/**
	 * 获取新版本的apk
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getLP")
	@ResponseBody
	@SystemControllerLog(description = "获取新版本的apk")
	public Results<PackageVersionDoctor> getLatestPackage(HttpServletRequest request){
		Results<PackageVersionDoctor> results = new Results<PackageVersionDoctor>();
		try{
			PackageVersionDoctor packageVersionDoctor = packageVersionDoctorService.findByParam("selectByStatus_1", null);
			packageVersionDoctor.sethPackageurl("");
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(packageVersionDoctor);
		}catch(Exception e){
			e.printStackTrace();
			results.setCode(MessageCode.CODE_404);//没有任何数据
			results.setMessage(MessageCode.MESSAGE_404);
		}
		return results;
	}

	/**
	 * 获取新版本的apk
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "downloadApk", method = RequestMethod.GET)
	@SystemControllerLog(description = "获取新版本的apk")
	public void downloadApk(HttpServletResponse resp) {
		try {
			PackageVersionDoctor packageVersionDoctor = packageVersionDoctorService.findByParam("selectByStatus_1", null);
			resp.sendRedirect(packageVersionDoctor.gethServerurl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
