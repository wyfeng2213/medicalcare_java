package com.cmcc.medicalcare.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
/**
 * UtilDatePropertyEditor
 * @author Administrator
 *
 */
public class UploadFilesUtils {
	
	private static final String UPLOAD_URL = "upload"; //上传路径
	private static final String SEPARATOR = ","; //分隔符
	
	/**
	 * 批量保存文件
	 * @param request
	 * @param file
	 */
	public static String saveFiles(HttpServletRequest request,MultipartFile[] files){
		String urls = "";
		
		if (files != null && files.length > 0) {
			String url = "";
            for (int i = 0; i < files.length; i++) {
                // 保存文件
            	url = saveFile(request, files[i]);
            	if (StringUtils.isNotBlank(url)) {
            		urls += SEPARATOR + url;
            	}
            }
        }
		
		if (StringUtils.isNotBlank(urls)) {
			urls = urls.trim().substring(1, urls.length());
		}
		
		return urls;
	}
	
	/**
	 * 保存单个文件
	 * @param request
	 * @param file
	 */
	public static String saveFile(HttpServletRequest request,MultipartFile file){
		OutputStream out = null;
		String url = "";
		try {
			if(file!=null){
				String path =  request.getSession().getServletContext().getRealPath("/");
				String fileName = file.getOriginalFilename();
		        File targetFile = new File(path + "/" + UPLOAD_URL, fileName);
		        if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }  
	        	file.transferTo(targetFile); 
	        	url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
	        	url = url + "/" + UPLOAD_URL + "/" + fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		}
		return url;
	}
	
	/**
	 * 批量保存文件_加前缀
	 * @param request
	 * @param file
	 */
	public static String saveFiles(HttpServletRequest request, MultipartFile[] files, String prefix){
		String urls = "";
		
		if (files != null && files.length > 0) {
			String url = "";
            for (int i = 0; i < files.length; i++) {
                // 保存文件
            	url = saveFile(request, files[i], prefix);
            	if (StringUtils.isNotBlank(url)) {
            		urls += SEPARATOR + url;
            	}
            }
        }
		
		if (StringUtils.isNotBlank(urls)) {
			urls = urls.trim().substring(1, urls.length());
		}
		
		return urls;
	}
	
	/**
	 * 保存单个文件_加前缀
	 * @param request
	 * @param file
	 */
	public static String saveFile(HttpServletRequest request, MultipartFile file, String prefix){
		OutputStream out = null;
		String url = "";
		try {
			if(file!=null){
				String path =  request.getSession().getServletContext().getRealPath("/");
				String fileName = prefix + file.getOriginalFilename();
		        File targetFile = new File(path + "/" + UPLOAD_URL, fileName);
		        if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }  
	        	file.transferTo(targetFile); 
	        	url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+request.getContextPath();
	        	url = url + "/" + UPLOAD_URL + "/" + fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		}
		return url;
	}
	
}

