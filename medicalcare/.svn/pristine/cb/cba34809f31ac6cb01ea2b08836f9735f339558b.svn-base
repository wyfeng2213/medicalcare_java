package com.cmcc.medicalcare.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.service.IMediaLogService;
import com.cmcc.medicalcare.vo.UserInfo;

/**
 * UtilDatePropertyEditor
 * 
 * @author Administrator
 *
 */
public class UploadFilesUtils {

	private static final String UPLOAD_URL = "upload"; // 上传路径
	private static final String SEPARATOR = ","; // 分隔符

	/**
	 * 批量保存文件
	 * 
	 * @param request
	 * @param file
	 */
	public static String saveFiles(IMediaLogService mediaLogService, UserInfo userInfo, HttpServletRequest request,
			MultipartFile[] files) {
		String urls = "";

		if (files != null && files.length > 0) {
			String url = "";
			for (int i = 0; i < files.length; i++) {
				// 保存文件
				url = saveFile(mediaLogService, userInfo, request, files[i]);
				if (StringUtils.isNotBlank(url)) {
					urls += SEPARATOR + url;
				}
			}
		}

		if (StringUtils.isNotBlank(urls)) {
			urls = urls.trim();
			urls = urls.substring(1, urls.length());
		}

		return urls;
	}

	/**
	 * 保存单个文件
	 * 
	 * @param request
	 * @param file
	 */
	public static String saveFile(IMediaLogService mediaLogService, UserInfo userInfo, HttpServletRequest request,
			MultipartFile file) {
		OutputStream out = null;
		String url = "";
		File targetFile = null;
		try {
			if (file != null) {
				String path = request.getSession().getServletContext().getRealPath("/");

				String fileName = changeFileName(file, null);
				targetFile = new File(path + "/" + UPLOAD_URL, fileName);
				if (!targetFile.exists()) {
					// targetFile.mkdirs();
					targetFile.createNewFile();
				}
				file.transferTo(targetFile);
				// url = request.getScheme() + "://" + request.getServerName() +
				// ":" + request.getServerPort()+request.getContextPath();
				// url = url + "/" + UPLOAD_URL + "/" + fileName;
				url = formUploadFileToServer(mediaLogService, userInfo, request, targetFile).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (targetFile != null && targetFile.exists()) {
				targetFile.delete();
			}
		}
		return url;
	}

	/**
	 * 批量保存文件_加前缀
	 * 
	 * @param request
	 * @param file
	 */
	public static String saveFiles(IMediaLogService mediaLogService, UserInfo userInfo, HttpServletRequest request,
			MultipartFile[] files, String prefix) {
		String urls = "";

		if (files != null && files.length > 0) {
			String url = "";
			for (int i = 0; i < files.length; i++) {
				// 保存文件
				url = saveFile(mediaLogService, userInfo, request, files[i], prefix);
				if (StringUtils.isNotBlank(url)) {
					urls += SEPARATOR + url;
				}
			}
		}

		if (StringUtils.isNotBlank(urls)) {
			urls = urls.trim();
			urls = urls.substring(1, urls.length());
		}

		return urls;
	}

	/**
	 * 保存单个文件_加前缀
	 * 
	 * @param request
	 * @param file
	 */
	public static String saveFile(IMediaLogService mediaLogService, UserInfo userInfo, HttpServletRequest request,
			MultipartFile file, String prefix) {
		OutputStream out = null;
		String url = "";
		File targetFile = null;
		try {
			if (file != null) {
				String path = request.getSession().getServletContext().getRealPath("/");
				String fileName = changeFileName(file, prefix);
				targetFile = new File(path + "/" + UPLOAD_URL, fileName);
				if (!targetFile.exists()) {
					// targetFile.mkdirs();
					targetFile.createNewFile();
				}
				file.transferTo(targetFile);
				// url = request.getScheme() + "://" + request.getServerName() +
				// ":" + request.getServerPort()+request.getContextPath();
				// url = url + "/" + UPLOAD_URL + "/" + fileName;
				url = formUploadFileToServer(mediaLogService, userInfo, request, targetFile).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (targetFile != null && targetFile.exists()) {
				targetFile.delete();
			}
		}
		return url;
	}

	/**
	 * 保存单个文件_加前缀
	 * 
	 * @param request
	 * @param file
	 */
	public static String cacheFile(HttpServletRequest request, MultipartFile file, String prefix) {
		OutputStream out = null;
		String url = "";
		try {
			if (file != null) {
				String path = request.getSession().getServletContext().getRealPath("/");
				String fileName = changeFileName(file, prefix);
				;
				File targetFile = new File(path + "/" + UPLOAD_URL, fileName);
				if (!targetFile.exists()) {
					// targetFile.mkdirs();
					targetFile.createNewFile();
					file.transferTo(targetFile);
				}
				url = targetFile.getPath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
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
	 * 上传文件到服务器
	 * 
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return
	 */
	public static String formUploadFileToServer(IMediaLogService mediaLogService, UserInfo userInfo,
			HttpServletRequest request, File file) {
		String uploadUrl = Config.uploadUrl;
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		InputStream is = null;
		InputStreamReader isr = null;
		FileInputStream fileInputStream = null;
		try {
			URL url = new URL(uploadUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// file
			String filename = file.getName();
			// MagicMatch match = Magic.getMagicMatch(file, false, true);
			// String contentType = match.getMimeType();

			StringBuilder strBuf = new StringBuilder();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=file; filename=\"" + filename + "\"\r\n");
			strBuf.append("Content-Type:application/octet-stream" + "\r\n\r\n");

			out.write(strBuf.toString().getBytes());
			int length = strBuf.length();
			strBuf.delete(0, length);
			;
			fileInputStream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fileInputStream);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// 读取返回数据
			StringBuffer strBufreturn = new StringBuffer();
			is = conn.getInputStream();
			isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBufreturn.append(line).append("\n");
			}
			res = strBufreturn.toString();
			length = strBufreturn.length();
			strBufreturn.delete(0, length);
			reader.close();
			reader = null;

			try {
				if (userInfo != null) {
					MediaLogUtils.saveMediaLog(mediaLogService, userInfo, request, file);// 仅统一保存APP端的媒体到数据表
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + uploadUrl);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				is = null;
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isr = null;
			}
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileInputStream = null;
			}
		}
		return res.replace("\n", "");
	}

	public static String changeFileName(MultipartFile file, String prefix) {
		String fileName_ = file.getOriginalFilename();
		int index = fileName_.lastIndexOf(".");
		String fileName = prefix + System.currentTimeMillis() + fileName_.substring(index);
		return fileName;
	}
}
