package com.cmcc.medicalcare.utils;

import java.util.Date;

import com.cmcc.medicalcare.controller.model.AppClientSession;
import com.google.gson.Gson;

/**
 * MySessionContext
 * 
 * @author Administrator
 *
 */
public class MySessionContext {
	/**
	 * mymap
	 */
	// private static HashMap<String, String> mymap = new HashMap<String,
	// String>();
	/**
	 * app session
	 */
	public final static int APP_CLIENT_SESSION_TYPE = 1;
	/**
	 * H5 session
	 */
	public final static int H5_CLIENT_SESSION_TYPE = 2;
	
	public final static long OUT_TIME = 30 * 24 * 3600 * 1000L;
	private final static String APP_CLIENT_SESSION = "APP_CLIENT_SESSION_";
	private final static String H5_CLIENT_SESSION = "H5_CLIENT_SESSION_";

	private volatile static MySessionContext mySessionContext;
	
	private RedisUtil redisUtil;
	
	/**
	 * 构造函数
	 */
	public MySessionContext(){
		redisUtil = (RedisUtil) SpringConfigTool.getBean("redisUtil");
	} 
	 
	/**
	 * 单例方法
	 * @return
	 */
	public static MySessionContext getInstance() {
		if (mySessionContext == null) {
			synchronized (MySessionContext.class) {
				if (mySessionContext == null) {
					mySessionContext = new MySessionContext();
				}
			}
		}
		return mySessionContext;
	}
	
	
	/**
	 * addSession
	 * 
	 * @param num
	 * @param sessionId
	 */
	public void addSession(String num, String sessionId) {
		if (!num.contains(APP_CLIENT_SESSION) && !num.contains(H5_CLIENT_SESSION)) {
			num = H5_CLIENT_SESSION+num;
		}
		redisUtil.set(num, sessionId, OUT_TIME);
	}

	/**
	 * 设置过期时间
	 * @param num
	 * @param accountBindExpireTime
	 * @param type
	 */
	public void setAccountBindExpireTime(String num,Date accountBindExpireTime,int type) {
		if(type == APP_CLIENT_SESSION_TYPE){
			setAppAccountBindExpireTime(num, accountBindExpireTime);
		}else if(type == H5_CLIENT_SESSION_TYPE){
			setH5AccountBindExpireTime(num, accountBindExpireTime);
		}
	}
	
	/**
	 * 获取过期时间
	 * @param num
	 * @param type
	 */
	public Date getAccountBindExpireTime(String num,int type) {
		Date date = null;
		if(type == APP_CLIENT_SESSION_TYPE){
			date = getAppAccountBindExpireTime(num);
		}else if(type == H5_CLIENT_SESSION_TYPE){
			date = getH5AccountBindExpireTime(num);
		}
		return date;
	}
	
	/**
	 * 设置H5绑定宽带过期
	 * setAccountBindExpireTime
	 * @param num
	 * @param accountBindExpireTime
	 */
	public void setH5AccountBindExpireTime(String num,Date accountBindExpireTime) {
		String key = H5_CLIENT_SESSION + num;
		String session = getSession(key);
		AppClientSession appClientSession = null;
		Gson gson = new Gson();
		if(session!=null && !"".equals(session)){
			try {
				appClientSession = gson.fromJson(session, AppClientSession.class);
			} catch (Exception e) {
				appClientSession = new AppClientSession();
			}
			appClientSession.setAccountBindExpireTime(accountBindExpireTime);
			appClientSession.setPhone(num);
		}else{
			appClientSession = new AppClientSession();
			appClientSession.setPhone(num);
			appClientSession.setAccountBindExpireTime(accountBindExpireTime);
		}
		addSession(key, gson.toJson(appClientSession));
	}
	
	
	/**
	 * 设置app宽带绑定过期时间
	 * @param num
	 * @param accountBindExpireTime
	 */
	public void setAppAccountBindExpireTime(String num,Date accountBindExpireTime) {
		String key = APP_CLIENT_SESSION + num;
		String session = getSession(key);
		AppClientSession appClientSession = null;
		Gson gson = new Gson();
		if(session!=null && !"".equals(session)){
			try {
				appClientSession = gson.fromJson(session, AppClientSession.class);
			} catch (Exception e) {
				appClientSession = new AppClientSession();
			}
			appClientSession.setAccountBindExpireTime(accountBindExpireTime);
			appClientSession.setPhone(num);
		}else{
			appClientSession = new AppClientSession();
			appClientSession.setPhone(num);
			appClientSession.setAccountBindExpireTime(accountBindExpireTime);
		}
		addSession(key, gson.toJson(appClientSession));
	}
	
	/**
	 * 获取绑定过期时间
	 * @param num
	 * @return
	 */
	public Date getH5AccountBindExpireTime(String num) {
		String key =  H5_CLIENT_SESSION + num;
		String session = getSession(key);
		Date date = null;
		if(session!=null && !"".equals(session)){
			Gson gson = new Gson();
			try {
				AppClientSession appClientSession = gson.fromJson(session, AppClientSession.class);
				date = appClientSession.getAccountBindExpireTime();
			} catch (Exception e) {
			}
		}
		return date;
	}
	
	/**
	 * 获取app绑定过期时间
	 * @param num
	 * @return
	 */
	public Date getAppAccountBindExpireTime(String num) {
		String key =  APP_CLIENT_SESSION + num;
		String session = getSession(key);
		Date date = null;
		if(session!=null && !"".equals(session)){
			Gson gson = new Gson();
			try {
				AppClientSession appClientSession = gson.fromJson(session, AppClientSession.class);
				date = appClientSession.getAccountBindExpireTime();
			} catch (Exception e) {
			}
		}
		return date;
	}
	
	/**
	 * 
	 * @param num
	 * @param type
	 * @return
	 */
	public String getSession(String num,int type){
		String session = null;
		if(type == APP_CLIENT_SESSION_TYPE){
			session = getAppSession(num);
		}else if(type == H5_CLIENT_SESSION_TYPE){
			session = getH5Session(num);
		}
		return session;
	}
	
	
	/**
	 * 
	 * @param num
	 * @param sessionId
	 * @param type
	 */
	public void setSession(String num, String sessionId,int type){
		if(type == APP_CLIENT_SESSION_TYPE){
			setAppSession(num, sessionId);
		}else if(type == H5_CLIENT_SESSION_TYPE){
			setH5Session(num, sessionId);
		}
	}
	
	/**
	 * setAppSession
	 * 
	 * @param num
	 * @param sessionId
	 */
	public void setH5Session(String num, String sessionId) {
		String key = H5_CLIENT_SESSION + num;
		String session = getSession(key);
		AppClientSession appClientSession = null;
//		System.out.println("session:"+session);
		Gson gson = new Gson();
		if(session!=null && !"".equals(session)){
			try {
				appClientSession = gson.fromJson(session, AppClientSession.class);
//				System.out.println("appClientSession.getSessionId():"+appClientSession.getSessionId());
			} catch (Exception e) {
				appClientSession = new AppClientSession();
			}
			appClientSession.setSessionId(sessionId);
			appClientSession.setPhone(num);
		}else{
			appClientSession = new AppClientSession();
			appClientSession.setPhone(num);
			appClientSession.setSessionId(sessionId);
		}
//		System.out.println("gson.toJson(appClientSession):"+gson.toJson(appClientSession));
		addSession(key, gson.toJson(appClientSession));
	}
	
	
	/**
	 * setAppSession
	 * 
	 * @param num
	 * @param sessionId
	 */
	public void setAppSession(String num, String sessionId) {
		String key = APP_CLIENT_SESSION + num;
		String session = getSession(key);
		AppClientSession appClientSession = null;
		Gson gson = new Gson();
		if(session!=null && !"".equals(session)){
			try {
				appClientSession = gson.fromJson(session, AppClientSession.class);
			} catch (Exception e) {
				appClientSession = new AppClientSession();
			}
			appClientSession.setSessionId(sessionId);
			appClientSession.setPhone(num);
		}else{
			appClientSession = new AppClientSession();
			appClientSession.setPhone(num);
			appClientSession.setSessionId(sessionId);
		}
		addSession(key, gson.toJson(appClientSession));
	}

	/**
	 * getAppSession
	 * @param num
	 * @return
	 */
	public String getAppSession(String num) {
		String key =  APP_CLIENT_SESSION + num;
		String session = getSession(key);
		if(session!=null && !"".equals(session)){
			Gson gson = new Gson();
			try {
				AppClientSession appClientSession = gson.fromJson(session, AppClientSession.class);
				session = appClientSession.getSessionId();
			} catch (Exception e) {
				AppClientSession appClientSession = new AppClientSession();
				appClientSession.setPhone(num);
				appClientSession.setSessionId(session);
				addSession(key, gson.toJson(appClientSession));
			}
		}
		return session;
	}
	
	/**
	 * getAppSession
	 * @param num
	 * @return
	 */
	public String getH5Session(String num) {
		String key =  H5_CLIENT_SESSION + num;
		String session = getSession(key);
		if(session!=null && !"".equals(session)){
			Gson gson = new Gson();
			try {
				AppClientSession appClientSession = gson.fromJson(session, AppClientSession.class);
				session = appClientSession.getSessionId();
			} catch (Exception e) {
				AppClientSession appClientSession = new AppClientSession();
				appClientSession.setPhone(num);
				appClientSession.setSessionId(session);
				addSession(key, gson.toJson(appClientSession));
			}
		}
		return session;
	}
	
	
	/**
	 * delAppSession
	 * 
	 * @param num
	 */
	public void delAppSession(String num) {
		delSession(APP_CLIENT_SESSION + num);
	}
	
	/**
	 * delSession
	 * 
	 * @param num
	 */
	public void delH5Session(String num) {
		redisUtil.remove(H5_CLIENT_SESSION + num);
	}
	
	/**
	 * delSession
	 * 
	 * @param num
	 */
	public void delSession(String num) {
		if (!num.contains(APP_CLIENT_SESSION) && !num.contains(H5_CLIENT_SESSION)) {
			num = H5_CLIENT_SESSION+num;
		}
		redisUtil.remove(num);
	}

	/**
	 * getSession
	 * 
	 * @param num
	 * @return
	 */
	public String getSession(String key) {
		if (key == null)
			return null;
		if (!key.contains(APP_CLIENT_SESSION) && !key.contains(H5_CLIENT_SESSION)) {
			key = H5_CLIENT_SESSION+key;
		}
		String session = redisUtil.getString(key);
		return session;
	}
	/**
	 * 
	 * @param key
	 * @param class1
	 */
	public <T> void setSession(String key,Class<T> class1){
		RedisUtil redisUtil = (RedisUtil) SpringConfigTool.getBean("redisUtil");
		Gson gson = new Gson();
		redisUtil.set(key, gson.toJson(class1));
	}
	
	/**
	 * 
	 * @param key
	 * @param class1
	 */
	public <T> T getSession(String key,Class<T> class1){
		T t = null;
		RedisUtil redisUtil = (RedisUtil) SpringConfigTool.getBean("redisUtil");
		Gson gson = new Gson();
		String session = redisUtil.getString(key);
		try {
			t = gson.fromJson(session, class1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
