package com.cmcc.medicalcare.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Toolkit
 * 
 * @author Administrator
 *
 */
public class Toolkit {
	/**
	 * count
	 */
	private static int count = 1;
	public final static String PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,3,6,7,8]))|(18[0-3,5-9]))\\d{8}$";

	/**
	 * 验证是不是移动电话号码
	 * 
	 * @param mobiles
	 * @return
	 */
	// public static boolean isMobileNO(String mobiles){
	// if(mobiles == null)
	// return false;
	// //Pattern p =
	// Pattern.compile("^0{0,1}(13[0-9]?|15[0-3]?|15[5-9]?|145|147|178|18[0-9])[0-9]{8}$");
	// Pattern p =
	// Pattern.compile("^1(3[4-9]|47|5[012789]|78|8[23478])\\d{8}$");
	//
	// Matcher m = p.matcher(mobiles);
	// return m.matches();
	// }

	/**
	 * 验证是不是手机号码（移动、电信、联通）
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null)
			return false;
		// Pattern p =
		// Pattern.compile("^0{0,1}(13[0-9]?|15[0-3]?|15[5-9]?|145|147|178|18[0-9])[0-9]{8}$");
		Pattern p = Pattern.compile(PHONE_PATTERN);

		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	
	/**
	 * 验证是不是手机号码（移动）
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isChinaMobileNO(String mobiles) {
		if (mobiles == null)
			return false;
		 Pattern p =Pattern.compile("(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)");
		//Pattern p = Pattern.compile(PHONE_PATTERN);

		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	

	/**
	 * 空字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String nullToString(Object object) {
		if (object == null || object.toString().trim().equals("null"))
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
	 * <p>
	 * CreateTime: Jul 1, 2015 8:58:32 AM
	 * </p>
	 * <p>
	 * 描述: 生成四位编号
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @return
	 */
	public static String getCountSeq() {
		String seq = "000";
		if (count > 9999)
			count = 1;
		if (count >= 10 && count < 100)
			seq = "00";
		if (count >= 100 && count < 1000)
			seq = "0";
		if (count >= 1000)
			seq = "";
		count++;
		return seq + count;
	}

	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
	// 字符串在编译时会被转码一次,所以是 "\\b"
	// \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
	static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
			+ "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
			+ "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
	static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	// 移动设备正则匹配：手机端、平板
	static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

	/**
	 * 检测是否是移动设备访问
	 * <p>
	 * CreateTime: Jul 1, 2015 9:09:03 AM
	 * </p>
	 * <p>
	 * 描述: 检测是否是移动设备访问
	 * </p>
	 * 
	 * @author wanggang 1085772106@qq.com
	 * @param userAgent
	 *            浏览器标识
	 * @return 移动设备返回true 否则返回false
	 */
	public static boolean check(String userAgent) {
		if (null == userAgent) {
			userAgent = "";
		}
		// 匹配
		Matcher matcherPhone = phonePat.matcher(userAgent);
		Matcher matcherTable = tablePat.matcher(userAgent);
		if (matcherPhone.find() || matcherTable.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得本周星期一的日期
	 * 
	 * @return
	 */
	public static String getCurrentMonday() { // 将星期一视为每周的第一天，周日为每周的最后一天
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得本周某一天的日期
	 * 
	 * @return
	 */
	public static String getDayOfCurrentWeek(int i) { // 将星期一视为每周的第一天，周日为每周的最后一天
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + i);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得当前周的周日的日期
	 * 
	 * @return
	 */
	public static String getSunday() { // 将星期一视为每周的第一天，周日为每周的最后一天
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preSunday = df.format(monday);
		return preSunday;
	}

	/**
	 * 获得当前日期与本周一相差的天数
	 * 
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 检查是否是数字字符串 @Title: isNumeric @Description: TODO @param @param
	 * str @param @return 设定文件 @return boolean 返回类型 @throws
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 检查字符串是否包含数字
	 * @param str
	 * @return
	 */
	public static boolean isContainNumeric(String str) {
		Pattern p = Pattern.compile("[0-9]");
	    Matcher m = p.matcher(str);
	    if (m.find()) {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 检查字符串是否包含字母
	 * @param str
	 * @return
	 */
	public static boolean isContainLetter(String str) {
		Pattern p = Pattern.compile("[a-zA-Z]");
	    Matcher m = p.matcher(str);
	    if (m.find()) {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 检查字符串是否包含符号
	 * @param str
	 * @return
	 */
	public static boolean isContainSymbol(String str) {
		Pattern p = Pattern.compile("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	    Matcher m = p.matcher(str);
	    if (m.find()) {
	        return true;
	    }
	    return false;
	}

	public static void main(String args[]) {
		System.out.println("" + isMobileNO("18215003985"));
	}

	/**
	 * 本周一时间
	 * 
	 * @return
	 */
	public static String getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		int week_index = cal2.get(Calendar.DAY_OF_WEEK);
		if (week_index == 1) {
			long nextTime = cal.getTime().getTime() - 7 * 24 * 3600 * 1000;
			return new SimpleDateFormat("yyyy-MM-dd").format(new Date(nextTime));
		} else {
			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		}
	}

	/**
	 * 下周一时间
	 * 
	 * @return
	 */
	public static String getTimesNextWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		int week_index = cal2.get(Calendar.DAY_OF_WEEK);
		if (week_index == 1) {
			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} else {
			long nextTime = cal.getTime().getTime() + 7 * 24 * 3600 * 1000;
			return new SimpleDateFormat("yyyy-MM-dd").format(new Date(nextTime));
		}

	}

	/**
	 * 本周一时间
	 * 
	 * @return
	 */
	public static String getTimesWeekSunday() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * 下周一时间
	 * 
	 * @return
	 */
	public static String getTimesNextWeekSunday() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		long nextTime = cal.getTime().getTime() + 7 * 24 * 3600 * 1000;
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(nextTime));
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 相差天数
	 */
	public static int daysBetween(Date beginDate, Date endDate) {
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
		long betweenDays = (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5);
		return Integer.parseInt(String.valueOf(betweenDays));
	}
	
	/**
	 * 获取某一天的下周一日期
	 * @param time
	 * @return
	 */
	public static String getOnedayNextMonday(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String onedayNextMonday = sdf.format(new Date(cal.getTime().getTime() + 7 * 24 * 60 * 60 * 1000)); // 下周一时间
		
		return onedayNextMonday;

	}
	
	/**
     * 获取某一天的下个月第一天日期
     * @param time
     * @return
     */
    public static String getOnedayNextMonthFirstDay(Date date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
        // 设置为这个月的第 1 天
    	cal.set(Calendar.DATE, 1); //设置为这个月的第 1 天
    	cal.add(Calendar.MONTH, 1); //置为加上 1 个月
//    	cal.add(Calendar.DATE, -1); //设置为减去 1 天
    	String onedayNextMonthFirstDay = sdf.format(cal.getTime());
		return onedayNextMonthFirstDay;
    }

    
    /**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);

        //对比的时间
        String day = sf.format(date);
         
        return day.equals(nowDay);
         
    }
    
    
    public static String getyyyyMMddHHmmss() {
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		String timestamp = df.format(calendar.getTime());
		return timestamp;
	}
    
    public static int getAge(Date birthDay) throws Exception {  
        Calendar cal = Calendar.getInstance();  
  
        if (cal.before(birthDay)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(birthDay);  
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }  
        return age;  
    }
    
}
