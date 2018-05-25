/**   
* @Title: MediaLogUtils.java 
* @Package com.cmcc.medicalcare.utils 
* @Description: TODO
* @author adminstrator   
* @date 2017年9月26日 下午6:52:33 
* @version V1.0   
*/
package com.cmcc.medicalcare.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.model.MediaLog;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.vo.UserInfo;

/**
 * @ClassName: MediaLogUtils
 * @Description: TODO
 * @author adminstrator
 * @date 2017年9月26日 下午6:52:33
 * 
 */
public class MediaLogUtils {

	public static void saveMediaLog(IMediaLogService mediaLogService, UserInfo userInfo,
			HttpServletRequest request, File file) {

		String fileName = file.getName();
		File targetFile = new File(Constant.uploadPath, fileName);
		try {
			FileUtils.copyFile(file, targetFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MediaLog mediaLog = new MediaLog();
		mediaLog.setUserName(userInfo.getUserName());
		mediaLog.setUserPhone(userInfo.getUserPhone());
		mediaLog.setUserLoginid(userInfo.getUserLoginId());
		mediaLog.setPath(targetFile.getAbsolutePath());
		mediaLog.setSize(Integer.valueOf((int) targetFile.length()));
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		mediaLog.setSuffix(suffix);
		mediaLog.setServer(request.getServerName());
		String innerUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		innerUrl = innerUrl + "/" + "download" + "/" + fileName;
		mediaLog.setInnerUrl(innerUrl);

		String outerUrl = Constant.downloadUrl + "/" + "download" + "/" + fileName;
		mediaLog.setOuterUrl(outerUrl);

		String ipString = HTTPUtils.getIpAddrByRequest(request);
		mediaLog.setFromServer(ipString);
		mediaLog.setCreatetime(new Date());
		mediaLogService.insert(mediaLog);
	}
}
