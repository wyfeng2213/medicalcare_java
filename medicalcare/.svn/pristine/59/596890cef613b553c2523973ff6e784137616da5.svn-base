/**   
* @Title: FileUtils.java 
* @Package com.cmcc.medicalcare.utils 
* @Description: TODO
* @author adminstrator   
* @date 2017年8月5日 下午3:25:27 
* @version V1.0   
*/
package com.cmcc.medicalcare.utils;

import org.springframework.web.multipart.MultipartFile;

/** 
* @ClassName: FileUtils 
* @Description: TODO
* @author adminstrator
* @date 2017年8月5日 下午3:25:27 
*  
*/
public class MyFileUtils {

	public static boolean isFileSuffix (MultipartFile file) {
		boolean flag = false;
		String fileName = file.getOriginalFilename();
		int start = fileName.lastIndexOf(".");
		String suffixName = fileName.substring(start+1);
		System.out.println(suffixName);
		if ("pdf".equalsIgnoreCase(suffixName)) {
			flag = true;
		} else if("doc".equalsIgnoreCase(suffixName)) {
			flag = true;
		}else if("excel".equalsIgnoreCase(suffixName)) {
			flag = true;
		}else if("jpg".equalsIgnoreCase(suffixName)) {
			flag = true;
		}else if("png".equalsIgnoreCase(suffixName)) {
			flag = true;
		}else if("txt".equalsIgnoreCase(suffixName)) {
			flag = true;
		}
		return flag;
	}
}




