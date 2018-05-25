package com.cmcc.medicalcare.controller.sys.packageVersion;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.annotation.SystemControllerLog;
import com.cmcc.medicalcare.controller.model.MessageCode;
import com.cmcc.medicalcare.controller.model.Results;
import com.cmcc.medicalcare.inter.Pager;
import com.cmcc.medicalcare.model.PackageVersionDoctor;
import com.cmcc.medicalcare.model.SystemUser;
import com.cmcc.medicalcare.service.IPackageVersionDoctorService;
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
@RequestMapping("/sys/pav/doctorApk")
public class DoctorApkController {
	
	private static final String PACKAGE_NAME = "com.cmcc.doctor";
	
	@Resource
	private IPackageVersionDoctorService packageVersionDoctorService;
	
	/**
	 * 添加单个apk
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "上传医生端apk")
	public Results<PackageVersionDoctor> addPackage(HttpServletRequest request,String parame,MultipartFile file){
		Results<PackageVersionDoctor> results = new Results<PackageVersionDoctor>();
		String path =  request.getSession().getServletContext().getRealPath("/");
		String fileName = file.getOriginalFilename();
		boolean isApk = FileFilterUtils.getFileTypeForMimeType(fileName, "APK");
		if(!isApk){
			results.setCode(MessageCode.CODE_501);
			results.setMessage("请上传APK文件");
			return results;
		}
        File targetFile = new File(path + "/" + "upload", fileName);
        //保存  
        try { 
        	if(!targetFile.exists()){  
                targetFile.mkdirs();
                file.transferTo(targetFile);
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
            
            PackageVersionDoctor packageVersionDoctor_ = packageVersionDoctorService.findByParam("selectByStatus_1", null);
            if (packageVersionDoctor_ != null) {
            	if(Integer.valueOf(apkInfo.getVersionCode())<=packageVersionDoctor_.gethPackagecode()){
            		results.setCode(MessageCode.CODE_501);
        			results.setMessage("请上传高版本APK");
        			if (targetFile!=null && targetFile.exists()) {
        				targetFile.delete();
        			}
        			return results;
                }
            	packageVersionDoctorService.update("updateStatusTo0",null);
            }
            
            String serverUrl = UploadFilesUtils.formUploadFileToServer(null,null,null,targetFile);
            PackageVersionDoctor packageVersionDoctor = new PackageVersionDoctor();
            packageVersionDoctor.sethPackagecode(Integer.valueOf(apkInfo.getVersionCode()));
            packageVersionDoctor.sethPackageversion(apkInfo.getVersionName());
            packageVersionDoctor.sethSize(Integer.valueOf(""+targetFile.length()));
            packageVersionDoctor.sethPackageurl(targetFile.getPath());
            packageVersionDoctor.sethStatus(1);
            packageVersionDoctor.sethCreatetime(new Date());
            
            packageVersionDoctor.sethServerurl(serverUrl);
            packageVersionDoctorService.insert(packageVersionDoctor);
            
            results.setCode(MessageCode.CODE_200);
			results.setMessage(MessageCode.MESSAGE_200);
			results.setData(packageVersionDoctor);
        } catch (Exception e) {  
            e.printStackTrace(); 
            results.setCode(MessageCode.CODE_404);//没有任何数据
			results.setMessage(MessageCode.MESSAGE_404);
        } finally {
			if (targetFile!=null && targetFile.exists()) {
				targetFile.delete();
			}
		}
        
		return results;
	}
	
	/**
     * APK管理页
     *
     * @return
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "/packageVersion/doctorApk";
    }
	
    /**
     * 用户管理页
     *
     * @return
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
    	
        return "/packageVersion/doctorApkAdd";
    }
	
	/**
	 * 获取apklist
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findPage", method = RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "获取医生端apklist")
	public Map<String, Object> findPage(HttpServletRequest request, PackageVersionDoctor packageVersionDoctor,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Pager<PackageVersionDoctor> pagers = packageVersionDoctorService.getList("getList", page, rows, packageVersionDoctor);
			map.put("total", pagers.getTotalCount());
			map.put("rows", pagers.getList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	
	
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

	
}
