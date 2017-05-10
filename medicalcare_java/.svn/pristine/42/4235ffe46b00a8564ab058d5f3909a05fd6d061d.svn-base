package com.cmcc.medicalcare.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Toolkit
 * @author Administrator
 *
 */
public class Toolkit {
	/**
	 * count
	 */
	private static int count = 1;
	
	/**
	 * 验证是不是移动电话号码
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){ 
		if(mobiles == null)
			return false;
	    //Pattern p = Pattern.compile("^0{0,1}(13[0-9]?|15[0-3]?|15[5-9]?|145|147|178|18[0-9])[0-9]{8}$");       
	    Pattern p = Pattern.compile("^1(3[4-9]|47|5[012789]|78|8[23478])\\d{8}$");       

	    Matcher m = p.matcher(mobiles);   
	    return m.matches();       
	}
	 
	/**
	 * 空字符串
	 * @param object
	 * @return
	 */
	public static String nullToString(Object object){
		if(object == null || object.toString().trim().equals("null"))
			return "";
		try {
			return object.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
	
	/**
	 * 
	 * <p>CreateTime: Jul 1, 2015 8:58:32 AM</p>
	 * <p>描述: 生成四位编号</p>
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public static String getCountSeq(){
		String seq = "000";
		if(count>9999)
			count = 1;
		if(count>=10&&count<100)
			seq="00";
		if(count>=100&&count<1000)
			seq="0";
		if(count>=1000)
			seq = "";
		count++;
		return seq+count;
	}
	
	
	 // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),    
    // 字符串在编译时会被转码一次,所以是 "\\b"    
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"    
            +"|windows (phone|ce)|blackberry"    
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"    
            +"|laystation portable)|nokia|fennec|htc[-_]"    
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"    
            +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
      
    //移动设备正则匹配：手机端、平板  
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);    
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);    
        
    /**
     * 检测是否是移动设备访问 
     * <p>CreateTime: Jul 1, 2015 9:09:03 AM</p>
     * <p>描述: 检测是否是移动设备访问 </p>
     * @author wanggang 1085772106@qq.com
     * @param userAgent 浏览器标识
     * @return 移动设备返回true 否则返回false
     */
    public static boolean check(String userAgent){    
        if(null == userAgent){    
            userAgent = "";    
        }    
        // 匹配    
        Matcher matcherPhone = phonePat.matcher(userAgent);    
        Matcher matcherTable = tablePat.matcher(userAgent);    
        if(matcherPhone.find() || matcherTable.find()){    
            return true;    
        } else {    
            return false;    
        }    
    }  
    
    /**
     * 检查是否是数字字符串
    * @Title: isNumeric 
    * @Description: TODO 
    * @param @param str
    * @param @return    设定文件 
    * @return boolean    返回类型 
    * @throws
     */
    public static boolean isNumeric(String str){ 
    	   Pattern pattern = Pattern.compile("[0-9]*"); 
    	   Matcher isNum = pattern.matcher(str);
    	   if( !isNum.matches() ){
    	       return false; 
    	   } 
    	   return true; 
    	}
	
    public static void main(String args[]){
    	System.out.println(""+isMobileNO("18215003985"));
    }
    
    
}
