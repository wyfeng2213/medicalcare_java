package com.easemob.server.comm;

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
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easemob.server.api.impl.EasemobSendMessage;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

public class HttpToEasemobUtil {

	public final static String TARGETTYPE_USERS = "users";
	public final static String TARGETTYPE_CHATGROUPS = "chatgroups";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filepath = "/Users/zhouzhou/Desktop/git_big_jb51.jpg";
		// String urlStr =
		// "http://127.0.0.1:8080/medicalmedia/media/save.action";
		File file = new File(filepath);
		// String ret = formUpload(urlStr, file);
		// System.out.println(ret);
		String receiver = "15821499858945";
		String sender = "scretary_12345678912";
		String targetType = "chatgroups";
		Map<String, Object> ext = new HashMap<String, Object>();
		sendImage(file, receiver, sender, targetType, ext);

	}

	/**
	 * 上传图片
	 * 
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return
	 */
	public static String formUpload(File file) {

		String orgName = OrgInfo.ORG_NAME;
		String appName = OrgInfo.APP_NAME;
		String authorization = TokenUtil.getAccessToken();
		String urlStr = "https://a1.easemob.com/{org_name}/{app_name}/chatfiles";
		urlStr = urlStr.replaceAll("\\{org_name\\}", orgName).replaceAll("\\{app_name\\}", appName);

		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		InputStream is = null;
		InputStreamReader isr = null;
		FileInputStream fileInputStream = null;
		try {
			URL url = new URL(urlStr);
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
			conn.setRequestProperty("Authorization", "Bearer " + authorization);
			conn.setRequestProperty("restrict-access", "true");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// file
			String filename = file.getName();
//			MagicMatch match = Magic.getMagicMatch(file, false, true);
//			String contentType = match.getMimeType();

			StringBuffer strBuf = new StringBuffer();
			strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			strBuf.append("Content-Disposition: form-data; name=file; filename=\"" + filename + "\"\r\n");
			strBuf.append("Content-Type:application/octet-stream"+"\r\n\r\n");

			out.write(strBuf.toString().getBytes());
			fileInputStream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fileInputStream);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			int length = strBuf.length();
			strBuf.delete(0, length);
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
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
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				is = null;
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

	/**
	 * 
	 * @param uri
	 * @param uuid
	 * @param secret
	 * @param filename
	 * @param receiver
	 * @param sender
	 * @param targetType
	 */
	public static Object sendImage(File file, String receiver, String sender, String targetType,
			Map<String, Object> ext) {
		String ret = formUpload(file);
		// System.out.println(ret);
		JSONObject jsonObject = JSONObject.parseObject(ret);
		JSONArray jsonArray = jsonObject.getJSONArray("entities");
		JSONObject entity = jsonArray.getJSONObject(0);
		String uri = jsonObject.getString("uri");
		String uuid = entity.getString("uuid");
		String secret = entity.getString("share-secret");
		Msg msg = new Msg();
		ImageMsgContent msgContent = new ImageMsgContent();
		String url = uri + "/" + uuid;
		msgContent.url(url).secret(secret).filename(file.getName()).size(new ImageMsgContent.Size(480, 720))
				.type(MsgContent.TypeEnum.IMG).msg("this is an image message");
		UserName userName = new UserName();
		userName.add(receiver);
		msg.from(sender).target(userName).targetType(targetType).msg(msgContent).ext(ext);
//		 System.out.println("msg:"+msg);
		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
		Object result = easemobSendMessage.sendMessage(msg);
//		 System.out.println("result:"+result);
		return result;
	}

	
	
	/**
	 * 
	 * @param file
	 * @param receiver
	 * @param sender
	 * @param targetType
	 * @param ext
	 * @return
	 */
	public static Object sendAudio(File file,int length, String receiver, String sender, String targetType,Map<String, Object> ext) {
		String ret = formUpload(file);
		// System.out.println(ret);
		JSONObject jsonObject = JSONObject.parseObject(ret);
		JSONArray jsonArray = jsonObject.getJSONArray("entities");
		JSONObject entity = jsonArray.getJSONObject(0);
		String uri = jsonObject.getString("uri");
		String uuid = entity.getString("uuid");
		String secret = entity.getString("share-secret");
		Msg msg = new Msg();
		AudioMsgContent msgContent = new AudioMsgContent();
		String url = uri + "/" + uuid;
		msgContent.url(url).secret(secret).filename(file.getName()).length(length)
				.type(MsgContent.TypeEnum.AUDIO).msg("this is an audio message");
		UserName userName = new UserName();
		userName.add(receiver);
		msg.from(sender).target(userName).targetType(targetType).msg(msgContent).ext(ext);
//		 System.out.println("msg:"+msg);
		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
		Object result = easemobSendMessage.sendMessage(msg);
//		 System.out.println("result:"+result);
		return result;
	}
	
	public static Object sendTxt(String sender, String receiver, String targetType, String msgTxt, Map<String, Object> ext) {
		EasemobSendMessage easemobSendMessage = new EasemobSendMessage();
		Msg msg = new Msg();
		MsgContent msgContent = new MsgContent();
		msgContent.type(MsgContent.TypeEnum.TXT).msg(msgTxt);
		UserName userName = new UserName();
		userName.add(receiver);
		msg.from(sender).target(userName).targetType(targetType).msg(msgContent).ext(ext);
		Object result = easemobSendMessage.sendMessage(msg);
		return result;
	}
	
	static class ImageMsgContent extends MsgContent {
		private String url;
		private String filename;
		private String secret;
		private Size size;

		ImageMsgContent url(String url) {
			this.url = url;
			return this;
		}

		ImageMsgContent filename(String filename) {
			this.filename = filename;
			return this;
		}

		ImageMsgContent secret(String secret) {
			this.secret = secret;
			return this;
		}

		ImageMsgContent size(Size size) {
			this.size = size;
			return this;
		}

		static class Size {
			private long width;
			private long height;

			Size(long width, long height) {
				this.width = width;
				this.height = height;
			}
		}
	}

	static class AudioMsgContent extends MsgContent {
		private String url;
		private String filename;
		private String secret;
		private int length;

		AudioMsgContent url(String url) {
			this.url = url;
			return this;
		}

		AudioMsgContent filename(String filename) {
			this.filename = filename;
			return this;
		}

		AudioMsgContent secret(String secret) {
			this.secret = secret;
			return this;
		}

		AudioMsgContent length(int length) {
			this.length = length;
			return this;
		}
	}
	
	
	public static String deleteFriendSingle(String ownerUsername, String friendUsername) {

		String orgName = OrgInfo.ORG_NAME;
		String appName = OrgInfo.APP_NAME;
		String authorization = TokenUtil.getAccessToken();
		System.out.println(authorization);
		String urlStr = "https://a1.easemob.com/{org_name}/{app_name}/users/{owner_username}/contacts/users/{friend_username}";
		urlStr = urlStr.replaceAll("\\{org_name\\}", orgName).replaceAll("\\{app_name\\}", appName)
				.replaceAll("\\{owner_username\\}", ownerUsername).replaceAll("\\{friend_username\\}", friendUsername);

		String res = "";
		HttpURLConnection conn = null;
		int length ;
		InputStream is = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", authorization);
			conn.connect();
			
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
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
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				is = null;
			}
		}
		return res;
	}
}