package com.cmcc.medicalcare.inter.jiuzhoutong;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.cmcc.common.HttpUtils;
import com.cmcc.common.ObjectConvert;
import com.cmcc.common.RSA;
import com.cmcc.common.Utils;
import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.config.Constant;
import com.cmcc.medicalcare.utils.HTTPUtils;
import com.cmcc.medicalcare.utils.ThirdInterfaceUtils;
import com.cmcc.medicalcare.utils.Toolkit;

/**
 * @ClassName:
 * @Description: TODO
 * @author adminstrator
 * @date 2017年4月10日 上午10:05:41
 * 
 */
public class PaymentUtils {
	
	public static final String CHARSET = "UTF-8";
	
	private final static String paymentResultUrl = Config.jiuZhouTongTestUrl + "/out/paymentResult?";


	public static String wxPayment(String orderNo, String amount, String subject, String body, String detail,
			String attach) {
        String returnstr = "";
        Map<String,String> map = new HashMap<String,String>();
        map.put("order_no",orderNo);
        map.put("amount",amount);
        map.put("subject",subject);
        map.put("body",body);
        map.put("detail",detail);
        map.put("attach",attach);
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

            returnstr= HttpUtils.requestPostForm(Config.appPaymentUrl,map,heads);
            Map<String,Object> maprt= Utils.stringToMap(returnstr);

            System.out.println(returnstr);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return returnstr;
    }

	// 加密
	private static String signData(String bodyjason, Map<String, String> map, String prikey) throws IOException {

		System.out.println("authorization= " + map.get("authorization"));
		System.out.println("nonce= " + map.get("nonce"));
		System.out.println("timestamp= " + map.get("timestamp"));
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		out.write(map.get("nonce").getBytes(CHARSET));
		out.write('\n');// query string
		out.write(map.get("timestamp").getBytes(CHARSET));
		out.write('\n');// header
		out.write(map.get("authorization").getBytes(CHARSET));
		out.write('\n');// header
		out.write(bodyjason.getBytes(CHARSET));
		out.write('\n');// method
		out.close();
		String toSignString = out.toString(CHARSET);
		return RSA.sign(toSignString, prikey);
	}

	
	public static String paymentResult(String isFefund,String orderinfoId,String addressId,JSONArray payDetails) {
		String method = "/out/paymentResult";

		String timestamp = Toolkit.getyyyyMMddHHmmss();
		String signstr = ThirdInterfaceUtils.getMD5signstr(method, timestamp);

		Map<String, Object> param =new HashMap<String, Object>();
		
		param.put("isFefund", isFefund);
		param.put("orderinfoId", orderinfoId);
		param.put("addressId", addressId);
		param.put("payDetails", payDetails);
		String url = ThirdInterfaceUtils.getURL(paymentResultUrl, timestamp, signstr);
		String jsonStr = HTTPUtils.sendJsonHttpPost(url, param, "UTF-8");
		return jsonStr;
	}
//	 public static void main(String[] args) {
//		 PaymentUtils.wxPayment(Utils.getCatId(), "1", "测试", "好好好哈", "好哈", "好哈");
//	 }

}
