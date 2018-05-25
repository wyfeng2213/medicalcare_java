package com.cmcc.common;

/**
 * Created by xieyongsheng on 17/11/30.
 */
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by oracle on 2017/3/1.
 */

@JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
public class ObjectConvert {
    public static JSONObject msgtoJSONObject(String msg,String code){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        jsonObject.put("status", code);
        return jsonObject;
    }
    public static JSONObject oderidtoJSONObject(String msg,String code,String orderid){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", msg);
        jsonObject.put("status", code);
        jsonObject.put("orderid", orderid);
        return jsonObject;
    }
    public  static String objectToJson(Object object){
        String jsonString = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
        return  jsonString;
    }
}