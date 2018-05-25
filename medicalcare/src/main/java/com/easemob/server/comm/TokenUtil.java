package com.easemob.server.comm;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.swagger.client.ApiException;
import io.swagger.client.api.AuthenticationApi;
import io.swagger.client.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by easemob on 2017/3/14.
 */
public class TokenUtil {
    public static String GRANT_TYPE;
    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static Token BODY;
    private static AuthenticationApi API = new AuthenticationApi();
    private static String ACCESS_TOKEN;
    private static Double EXPIREDAT = -1D;
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * get token from server
     */
    static {
        InputStream inputStream = TokenUtil.class.getClassLoader().getResourceAsStream("easemob.properties");
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
			if (inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			inputStream = null;
		}
        GRANT_TYPE = prop.getProperty("GRANT_TYPE");
        CLIENT_ID = prop.getProperty("CLIENT_ID");
        CLIENT_SECRET = prop.getProperty("CLIENT_SECRET");
        BODY = new Token().clientId(CLIENT_ID).grantType(GRANT_TYPE).clientSecret(CLIENT_SECRET);
    }

    public static void initTokenByProp() {
        String resp = null;
        try {
            resp = API.orgNameAppNameTokenPost(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, BODY);
        } catch (ApiException e) {
            logger.error(e.getMessage());
        }
        Gson gson = new Gson();
        Map map = gson.fromJson(resp, Map.class);
        ACCESS_TOKEN = " Bearer " + map.get("access_token");
        EXPIREDAT = System.currentTimeMillis() + (Double) map.get("expires_in");
    }

    /**
     * get Token from memory
     *
     * @return
     */
    public static String getAccessToken() {
        if (ACCESS_TOKEN == null || isExpired()) {
            initTokenByProp();
        }
        return ACCESS_TOKEN;
    }

    private static Boolean isExpired() {
        return System.currentTimeMillis() > EXPIREDAT;
    }
    
    public static Map<String, Object> getUserAccessToken(String username,String password) {
    	if (null == username || "".equals(username) || null == password || "".equals(password)) {
    		return null;
    	}
    	
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        jsonObject.addProperty("password",password);
        jsonObject.addProperty("grant_type","password");
        String urlString = "http://182.92.0.214:80/1140170421178546/mingyizaixian/token ";
    	String reString = HTTPUtils.sendPost(urlString,jsonObject.toString(),"UTF-8");
    	
    	if (null != reString && !"".equals(reString)) {
    		JSONObject JsonObject = JSONObject.parseObject(reString);
    		String access_token = JsonObject.getString("access_token");
    		String expires_in = JsonObject.getString("expires_in");
    		resultMap.put("access_token", access_token);
    		resultMap.put("expires_in", expires_in);
    	} else {
    		resultMap.put("access_token", "");
    		resultMap.put("expires_in", 0);
    	}
    	
    	return resultMap;
    }

}

