package com.cmcc.common;

/**
 * Created by chijr on 16/11/5.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {


    private static SerializeConfig mapping = new SerializeConfig();
    private static String dateFormat;
    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }


    public Utils() {


    }

    public static String formatDateTime(Date dt) {

        String tmstr = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", dt);
        return tmstr;


    }


    public static String getHashMD5(String source){

        return Utils.getHash(source,"MD5");
    }

    public static String getHashSHA(String source){

        return Utils.getHash(source,"SHA");
    }


    /**
     * 由于MD5 与SHA-1均是从MD4 发展而来，它们的结构和强度等特性有很多相似之处
     * SHA-1与MD5 的最大区别在于其摘要比MD5 摘要长 32 比特（1byte=8bit，相当于长4byte，转换16进制后比MD5多8个字符）。
     * 对于强行攻击，：MD5 是2128 数量级的操作，SHA-1 是2160数量级的操作。
     * 对于相同摘要的两个报文的难度：MD5是 264 是数量级的操作，SHA-1 是280 数量级的操作。
     * 因而，SHA-1 对强行攻击的强度更大。 但由于SHA-1 的循环步骤比MD5 多（80:64）且要处理的缓存大（160 比特:128 比特），SHA-1 的运行速度比MD5 慢。
     *
     * @param source 需要加密的字符串
     * @param hashType 加密类型 （MD5 和 SHA）
     * @return
     */

    public static String getHash(String source, String hashType) {
        // 用来将字节转换成 16 进制表示的字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            md.update(source.getBytes()); // 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
            byte[] encryptStr = md.digest(); // 获得密文完成哈希计算,产生128 位的长整数
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
                byte byte0 = encryptStr[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(str); // 换后的结果转换为字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String getTransId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "T" + id;

    }

    public static String getPreTransId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "P" + id;

    }


    public static String getToken() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "TK" + id;

    }

    public static String getFileName() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "file" + id;

    }


    public static String getId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ID" + id;

    }


    public static String getComId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "COM" + id;

    }


    public static String getItemId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "IT" + id;

    }


    public static String getKeyId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ATTR" + id;

    }

    public static String getValueId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "VAL" + id;

    }


    public static String getSKuId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SKU" + id;

    }


    public static String getAccountId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "A" + id;

    }


    public static String getAdminUserId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ADMIN" + id;

    }

    public static String getUserId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "U" + id;

    }


    public static String getMerId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Mer" + id;

    }

    public static String getSysTypeId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ST" + id;

    }

    public static String getSysSonTypeId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SST" + id;

    }


    public static String getShopId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SP" + id;

    }

    public static String getMessageId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Msg" + id;

    }


    public static String getSubAccountId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "SUB" + id;

    }

    public static String getAddressId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "ADD" + id;

    }

    public static String getCatId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "CAT" + id;

    }
    public static String getDcyId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "DCY" + id;

    }
    public static String getProductId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "PD" + id;

    }

    public static String getPayId() {
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "P" + id;

    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static synchronized String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    public static synchronized String getUserID(){
        String begin="doc";
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return begin+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    public static synchronized String getUserPatID(){
        String begin="pat";
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return begin+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    public static synchronized String getPatID(){
        String begin="son";
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return begin+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    public static synchronized String getUserLogID(){
        String begin="log";
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return begin+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    /**
     * 获得一个userID
     * @return String userID
     */
    public static synchronized String getGlobalID(){
        String begin="gid";
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return begin+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    public static String getOrderId() {
        //String begin="wzfor";
        String begin="yjkor";
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyMMddHHmmss");
        String s = outFormat.format(now);
        return begin+s+id.substring(9);

    }
    public static String getInterId() {
        //String begin="wzfin";
        String begin="yjkin";
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyMMddHHmmss");
        String s = outFormat.format(now);
        return begin+s+id.substring(9);

    }
    public static String getInterdetailId() {
        String begin="detin";
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyMMddHHmmss");
        String s = outFormat.format(now);
        return begin+s+id.substring(9);

    }
    public static String getTranId() {
        String begin="jyi";
        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return begin+s+id.substring(9);

    }
    public static String getdate() {

        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = outFormat.format(now);
        return s;
    }
    public static String gettime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mms:s");
        String s = outFormat.format(now);
        return s;

    }
    public static int getinterviewid() {
        int id=(int)((Math.random()*9+1)*100);
        return id+10000000;
    }
    public static int getmoney() {
        int id=(int)((Math.random()*9+1)*100);
        return id;
    }
    public static String getDateTime() {

        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String s = outFormat.format(now);
        return s;
    }
    public  static String getCartId(){

        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Cart" + id;


    }

    public  static String getPrepareId(){

        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Pre" + id;

    }

    public  static String getPubId(){

        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Pub" + id;

    }

    public  static String getPriceId(){

        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Prc" + id;

    }

    public  static String getPassword(){

        ObjectId objId = ObjectId.get();
        String id = objId.toString();
        return "Pw" + id;

    }


    public static int getInt(Object o) {


        if (o == null) {
            return 0;
        }

        if (o instanceof Integer) {
            Integer i = (Integer) o;
            return i.intValue();
        }

        if (o instanceof BigDecimal) {
            BigDecimal b = (BigDecimal) o;
            return b.intValue();
        }

        if (o instanceof Long) {
            Long b = (Long) o;
            return b.intValue();
        }

        if (o instanceof Float) {
            Float b = (Float) o;
            return b.intValue();
        }

        if (o instanceof Double) {
            Double b = (Double) o;
            return b.intValue();
        }

        return 0;


    }

    public static String md5(String str) {
        String newstr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {

        }
        return newstr;
    }

    public static String getString(Map m, String key) {

        Object o = m.get(key);

        if (o == null) {
            return null;
        }

        return String.valueOf(o);

    }


    public static String objectToString(Object objectInstance){


        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        String str = JSON.toJSONString(objectInstance, SerializerFeature.WriteDateUseDateFormat);


        return str;



    }


    public static HashMap<String,Object> objectToMap(Object objectInstance){


        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        String str = JSON.toJSONString(objectInstance, SerializerFeature.WriteDateUseDateFormat);


        HashMap<String, Object> map = JSON.parseObject(str, new TypeReference<HashMap<String, Object>>(){});

        return map;



    }

    public static ObjectMapper getObjectMap(){

        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(format);
        return mapper;

    }


    public static String objectToJsonString(Object o){

        try {
            String outJson = Utils.getObjectMap().writeValueAsString(o);
            return outJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }



    }

    public static  HashMap<String, Object> stringToMap(String  content){

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        HashMap<String, Object> map = JSON.parseObject(content, new TypeReference<HashMap<String, Object>>(){});

        return map;





    }


    public static  Object stringToObject(String  content){

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        Object obj = JSON.parse(content);

        return obj;



    }




    public static int getInt(Map m, String key) {

        Object o = m.get(key);

        if (o == null) {
            return 0;
        }

        if (o instanceof Integer) {
            Integer i = (Integer) o;
            return i.intValue();
        }

        if (o instanceof BigDecimal) {
            BigDecimal b = (BigDecimal) o;
            return b.intValue();
        }

        if (o instanceof Long) {
            Long b = (Long) o;
            return b.intValue();
        }

        if (o instanceof Float) {
            Float b = (Float) o;
            return b.intValue();
        }

        if (o instanceof Double) {
            Double b = (Double) o;
            return b.intValue();
        }

        return 0;


    }


    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file), encoding);
            } else {
                reader = new InputStreamReader(new FileInputStream(file));
            }
            //将输入流写入输出流
            char[] buffer = new char[10*1024];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        //返回转换结果
        if (writer != null)
            return writer.toString();
        else return null;
    }



    public static String convertStreamToString(InputStream is) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


    /**
     * 使用key值从json中取出值
     * @param json
     * @param key
     * @return
     */
    public static String getValueFromJson(String json, String key){
        JSONObject jsonMap;
        try {
            jsonMap = JSONObject.fromObject(json);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        if(jsonMap == null){
            return null;
        }

        return jsonMap.getString(key);
    }

    public static String checkBengin(String s){
        //测试
//        if (s.startsWith("wzfin")){
//            return  "问诊";
//        }else if(s.startsWith("wzfor")){
//            return  "处方";
//        }else{
//            return  "其他";
//        }
        //正式
        if (s.startsWith("yjkin")){
            return  "问诊";
        }else if(s.startsWith("yjkor")){
            return  "处方";
        }else{
            return  "其他";
        }
    }

    //byte 与 int 的相互转换
    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }
    public static String md5openid(String openid){
        String str=openid+"checkyuejiankang";
        return  PayMD5.sign(str,"&key=e1cf0ddcf6b47b59c351565d8ad717af", "utf-8");
    }
    public static String judgmentDate(String date1, String date2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime();
        if(cha<0){
            return date2;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result<=24){
            return date1;
        }else{
            return date2;
        }
    }
}
