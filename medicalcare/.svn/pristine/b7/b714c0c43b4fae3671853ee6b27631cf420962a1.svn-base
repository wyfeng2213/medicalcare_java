package com.cmcc.medicalcare.controller.packageVersion;

import java.io.File;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.model.PackageVersionPatient;
import com.cmcc.medicalcare.service.IPackageVersionPatientService;
import com.cmcc.medicalcare.utils.FileFilterUtils;
import com.cmcc.medicalcare.utils.UploadFilesUtils;

/**
 * @ClassName: 包版本Controller
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午11:50:27
 * 
 */
@Controller
@RequestMapping("/pav/packageVersionPatient")
public class PackageVersionPatientController {

	private static final String PACKAGE_NAME = "com.cmcc.patient";
	
	@Resource
	private IPackageVersionPatientService packageVersionPatientService;

	/**
	 * 添加单个apk
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@SystemControllerLog(description = "上传apk")
	public Results<PackageVersionPatient> addPackage(HttpServletRequest request, String parame,
			MultipartFile file) {
		Results<PackageVersionPatient> results = new Results<PackageVersionPatient>();

		String path = request.getSession().getServletContext().getRealPath("/");
		String fileName = file.getOriginalFilename();
		File targetFile = new File(path + "/" + "upload", fileName);
		// 保存
		try {
			if(!targetFile.exists()){  
	             targetFile.mkdirs();
	             file.transferTo(targetFile);
	         }
			boolean isApk = FileFilterUtils.getFileTypeForMimeType(fileName, "APK");
    		if(!isApk){
    			results.setCode(MessageCode.CODE_501);
    			results.setMessage("请上传APK文件");
    			if (targetFile!=null && targetFile.exists()) {
    				targetFile.delete();
    			}
    			return results;
    		}
           
            ApkInfo apkInfo = GetApkInfo.getApkInfoByFilePath(targetFile.getPath());
            if(!PACKAGE_NAME.equals( apkInfo.getPackageName())){
            	results.setCode(MessageCode.CODE_501);
    			results.setMessage("包名不一致");
    			if (targetFile!=null && targetFile.exists()) {
    				targetFile.delete();
    			}
    			return results;
            }
			
			PackageVersionPatient packageVersionPatient_ = packageVersionPatientService.findByParam("selectByStatus_1", null);
			if (packageVersionPatient_ != null){
				if(Integer.valueOf(apkInfo.getVersionCode())<=packageVersionPatient_.gethPackagecode()){
            		results.setCode(MessageCode.CODE_501);
        			results.setMessage("请上传高版本APK");
        			if (targetFile!=null && targetFile.exists()) {
        				targetFile.delete();
        			}
        			return results;
                }
				packageVersionPatientService.update("updateStatusTo0", null);
			}
			String serverUrl = UploadFilesUtils.formUploadFileToServer(null ,null,null,targetFile);
			PackageVersionPatient packageVersionPatient = new PackageVersionPatient();
			packageVersionPatient.sethPackagecode(Integer.valueOf(apkInfo.getVersionCode()));
			packageVersionPatient.sethPackageversion(apkInfo.getVersionName());
			packageVersionPatient.sethSize(Integer.valueOf("" + targetFile.length()));
			packageVersionPatient.sethPackageurl(targetFile.getPath());
			packageVersionPatient.sethStatus(1);
			packageVersionPatient.sethCreatetime(new Date());

			packageVersionPatient.sethServerurl(serverUrl);
			packageVersionPatientService.insert(packageVersionPatient);

			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(packageVersionPatient);
		} catch (Exception e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_404);// 没有任何数据
			results.setMessage(MessageCode.MESSAGE_404);
		} finally {
			if (targetFile != null && targetFile.exists()) {
				targetFile.delete();
			}
		}

		return results;
	}

	/**
	 * 获取新版本的apk
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getLP")
	@ResponseBody
	@SystemControllerLog(description = "获取新版本的apk")
	public Results<PackageVersionPatient> getLatestPackage(HttpServletRequest request) {
		Results<PackageVersionPatient> results = new Results<PackageVersionPatient>();
		try {
			PackageVersionPatient packageVersionPatient = packageVersionPatientService.findByParam(
					"selectByStatus_1", null);
			packageVersionPatient.sethPackageurl("");
			results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(packageVersionPatient);
		} catch (Exception e) {
			e.printStackTrace();
			results.setCode(MessageCode.CODE_404);// 没有任何数据
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
			PackageVersionPatient packageVersionPatient = packageVersionPatientService.findByParam("selectByStatus_1", null);
			resp.sendRedirect(packageVersionPatient.gethServerurl());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
