package com.cmcc.test;

/**
 * Created by xieyongsheng on 18/1/15.
 */



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.cmcc.common.ObjectConvert;
import com.cmcc.common.RSA;
import com.cmcc.common.Utils;
import com.cmcc.common.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/****微信小程序支付demo
 * Created by xieyongsheng on 17/12/14.
 */
public class Wchatmpay {

    public static final String CHARSET = "UTF-8";

    public static void main(String[] args) {


    }
  public  static void testpay(){
      //String url="http://120.25.71.44:8089/pay/wechatm/charges";
      String url="http://localhost:8089/pay/wechatm/charges";

      Map<String,String> map = new HashMap<String,String>();
      map.put("order_no",Utils.getCatId());
      map.put("openid","oevH20B1f9sIL5ix6bAWstXk5N4k");
      map.put("amount","1");
      map.put("subject","测试");
      map.put("body","好好好哈");
      map.put("detail","好哈");
      map.put("attach","好哈");
      map.put("channel","weichatapp");

      String bodyjason= ObjectConvert.objectToJson(map);
      String prikey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKbATV+tmRj3UaD5/TS35j63OXX2ADqfVeQU+4o5cKdRpWR5V5NrDvG9dAIyE8ILuwnusnnO6fM9aAkZskFyBI3A++lOR3o0AluWFu3JvmsLXMKWoJau4Dz351XTWNrE3tIAa0j0nd08oEd16nsUnRErKn6r6HqNf3XrqIULp6OhAgMBAAECgYBJSyE3OtCePqbsgLUg5OwZCOQCy1wSaFKSa/6cJN81TBfMb+FkdJwe+UzyYhx5IDrYBMkB7Ua7mXQO+/Jz/a+uANzUm1L18Ok9290Cxk42NSo79nC7aTdmtLDjG/E0XxgsPQxGgEk5R5TaYT4YJnURxErJ+bLY2mATWe0V/Bg8gQJBAN3OXhzBlZYGYtMIIGmqUvfp01W5NqMWgaysjOZYsbGS+5/prSwlzay5lxrIB+DHl5PnlFpqle3KVXJdvBIPHakCQQDAdS2O6W8z5ehB/hbFQe3HeogobDhNvYoPEWiZx5UxQiybHuCnEu4fNlpGehTjacMXo3q/AiCVLVFMliFRxWE5AkBiuWHmRru/5OPDrlBO98KqGec4tpF9EZ1yL/Me68dblGJvEOFFTyY2hPyerP3krLHo4SCFBf/psS9LEjGNkDDRAkBLmDsy3UIOsomOEk3DYWgSaHC+3/MlpgNqc74QWTKizIlUzMYVGfxqSiEfeahmww4cZNw71owRzGEYogeoZM0RAkBdr/r4p2hM436N/1QY/P4+PrRmfPU8ExT+fwnD4uuRPCCQnsttRHzBjqeL95zLV/YvLtYeMg9g24KgEGGUliv6";
      try {
          Map<String,String> heads =new HashMap<String,String>();
          heads.put("authorization","key1995a7255d5c4fda8cafdcd61572c4ef");

          String timestamp = String.valueOf(System.currentTimeMillis());
          heads.put("timestamp", timestamp);
          heads.put("nonce", UUID.randomUUID().toString().replaceAll("-",""));
          //String returnstr= HttpUtils.requestPostBody(url,bodyjason,heads);


          String sign=signData(bodyjason,heads,prikey);
          System.out.println("sign= "  + sign);
          heads.put("sign",sign);

          String returnstr= HttpUtils.requestPostForm(url,map,heads);
          Map<String,Object> maprt= Utils.stringToMap(returnstr);

          System.out.println(returnstr);
      } catch (Exception e) {
          e.printStackTrace();

      }
  }
    //测试微信小额支付退款
    public static void testrefund(){
        //String url="http://120.25.71.44:8089/wechatm/refund";
        String url="http://localhost:8089/pay/wechatm/refund";

        Map<String,String> map = new HashMap<String,String>();
        map.put("order_no","CAT5a5d9a59ee8aab041c608b8b");
        map.put("amount","1");
        map.put("refund_fee","1");
        map.put("refund_desc","用户投诉退款");
        String bodyjason= ObjectConvert.objectToJson(map);
        String prikey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKbATV+tmRj3UaD5/TS35j63OXX2ADqfVeQU+4o5cKdRpWR5V5NrDvG9dAIyE8ILuwnusnnO6fM9aAkZskFyBI3A++lOR3o0AluWFu3JvmsLXMKWoJau4Dz351XTWNrE3tIAa0j0nd08oEd16nsUnRErKn6r6HqNf3XrqIULp6OhAgMBAAECgYBJSyE3OtCePqbsgLUg5OwZCOQCy1wSaFKSa/6cJN81TBfMb+FkdJwe+UzyYhx5IDrYBMkB7Ua7mXQO+/Jz/a+uANzUm1L18Ok9290Cxk42NSo79nC7aTdmtLDjG/E0XxgsPQxGgEk5R5TaYT4YJnURxErJ+bLY2mATWe0V/Bg8gQJBAN3OXhzBlZYGYtMIIGmqUvfp01W5NqMWgaysjOZYsbGS+5/prSwlzay5lxrIB+DHl5PnlFpqle3KVXJdvBIPHakCQQDAdS2O6W8z5ehB/hbFQe3HeogobDhNvYoPEWiZx5UxQiybHuCnEu4fNlpGehTjacMXo3q/AiCVLVFMliFRxWE5AkBiuWHmRru/5OPDrlBO98KqGec4tpF9EZ1yL/Me68dblGJvEOFFTyY2hPyerP3krLHo4SCFBf/psS9LEjGNkDDRAkBLmDsy3UIOsomOEk3DYWgSaHC+3/MlpgNqc74QWTKizIlUzMYVGfxqSiEfeahmww4cZNw71owRzGEYogeoZM0RAkBdr/r4p2hM436N/1QY/P4+PrRmfPU8ExT+fwnD4uuRPCCQnsttRHzBjqeL95zLV/YvLtYeMg9g24KgEGGUliv6";
        try {
            Map<String,String> heads =new HashMap<String,String>();
            heads.put("authorization","key1995a7255d5c4fda8cafdcd61572c4ef");

            String timestamp = String.valueOf(System.currentTimeMillis());
            heads.put("timestamp", timestamp);
            heads.put("nonce", UUID.randomUUID().toString().replaceAll("-",""));
            //String returnstr= HttpUtils.requestPostBody(url,bodyjason,heads);
            String sign=signData(bodyjason,heads,prikey);
            System.out.println("sign= "  + sign);
            heads.put("sign",sign);

            String returnstr= HttpUtils.requestPostForm(url,map,heads);
            Map<String,Object> maprt= Utils.stringToMap(returnstr);

            System.out.println(returnstr);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //测试微信小额支付查询
    public static void testpayqury(){
        //String url="http://120.25.71.44:8089/pay/payresult/query";
        String url="http://localhost:8089/pay/payresult/query";

        Map<String,String> map = new HashMap<String,String>();
        map.put("order_no","CAT5a5d9a59ee8aab041c608b8b");

        String bodyjason= ObjectConvert.objectToJson(map);
        String prikey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKbATV+tmRj3UaD5/TS35j63OXX2ADqfVeQU+4o5cKdRpWR5V5NrDvG9dAIyE8ILuwnusnnO6fM9aAkZskFyBI3A++lOR3o0AluWFu3JvmsLXMKWoJau4Dz351XTWNrE3tIAa0j0nd08oEd16nsUnRErKn6r6HqNf3XrqIULp6OhAgMBAAECgYBJSyE3OtCePqbsgLUg5OwZCOQCy1wSaFKSa/6cJN81TBfMb+FkdJwe+UzyYhx5IDrYBMkB7Ua7mXQO+/Jz/a+uANzUm1L18Ok9290Cxk42NSo79nC7aTdmtLDjG/E0XxgsPQxGgEk5R5TaYT4YJnURxErJ+bLY2mATWe0V/Bg8gQJBAN3OXhzBlZYGYtMIIGmqUvfp01W5NqMWgaysjOZYsbGS+5/prSwlzay5lxrIB+DHl5PnlFpqle3KVXJdvBIPHakCQQDAdS2O6W8z5ehB/hbFQe3HeogobDhNvYoPEWiZx5UxQiybHuCnEu4fNlpGehTjacMXo3q/AiCVLVFMliFRxWE5AkBiuWHmRru/5OPDrlBO98KqGec4tpF9EZ1yL/Me68dblGJvEOFFTyY2hPyerP3krLHo4SCFBf/psS9LEjGNkDDRAkBLmDsy3UIOsomOEk3DYWgSaHC+3/MlpgNqc74QWTKizIlUzMYVGfxqSiEfeahmww4cZNw71owRzGEYogeoZM0RAkBdr/r4p2hM436N/1QY/P4+PrRmfPU8ExT+fwnD4uuRPCCQnsttRHzBjqeL95zLV/YvLtYeMg9g24KgEGGUliv6";
        try {
            Map<String,String> heads =new HashMap<String,String>();
            heads.put("authorization","key1995a7255d5c4fda8cafdcd61572c4ef");

            String timestamp = String.valueOf(System.currentTimeMillis());
            heads.put("timestamp", timestamp);
            heads.put("nonce", UUID.randomUUID().toString().replaceAll("-",""));
            //String returnstr= HttpUtils.requestPostBody(url,bodyjason,heads);


            String sign=signData(bodyjason,heads,prikey);
            System.out.println("sign= "  + sign);
            heads.put("sign",sign);

            String returnstr= HttpUtils.requestPostForm(url,map,heads);
            Map<String,Object> maprt= Utils.stringToMap(returnstr);

            System.out.println(returnstr);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
//加密
    private static String signData(String bodyjason, Map<String,String> map,String prikey) throws IOException {


        System.out.println("authorization= "  + map.get("authorization"));
        System.out.println("nonce= "  + map.get("nonce"));
        System.out.println("timestamp= "  + map.get("timestamp"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(map.get("nonce").getBytes(CHARSET));
        out.write('\n');//query string
        out.write(map.get("timestamp").getBytes(CHARSET));
        out.write('\n');//header
        out.write(map.get("authorization").getBytes(CHARSET));
        out.write('\n');//header
        out.write(bodyjason.getBytes(CHARSET));
        out.write('\n');//method
        out.close();
        String toSignString = out.toString(CHARSET);
        return RSA.sign(toSignString, prikey);
    }
}

