package com.cmcc.medicalcare.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmcc.medicalcare.config.Config;
import com.cmcc.medicalcare.model.SendcodeLog;
import com.cmcc.medicalcare.service.ISendcodeLogService;

/**
 * SendCodeLimitUtil
 * @author zds
 *
 */
public class SendCodeUtil {

	/**
	 * validateSendCodeTimes
	 * @param phonenumber
	 * @param sendCodeLogService
	 * @return
	 */
	public static boolean validateSendCodeTimes(String phonenumber,ISendcodeLogService sendCodeLogService) {
		Map<String, String> param = new HashMap<String, String>(); 
		Date date = new Date();
		String startTime = getStartTime();  //获取当天0点0分0秒时间
		String endTime = getEndTime();  //获取当天23点59分59秒的时间
		param.put("phonenumber", phonenumber);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
	
		//白名单
		if (Config.sendCodeWhiteList.contains(phonenumber)) {
			return true;
		}
		
		List<SendcodeLog> list  = sendCodeLogService.getList("selectByParam", param);

		//判断表是否存在当天的该号码记录
		if (list != null && list.size()>0) {
			SendcodeLog sendCodeLog =(SendcodeLog)list.get(0);
			int sendcodelimittimes = sendCodeLog.getSendcodelimittimes().intValue();
			if (sendcodelimittimes<Integer.parseInt(Config.sendCodelimitTimes)) {  //小于指定次数
				sendCodeLog.setSendcodelimittimes(++sendcodelimittimes);
				sendCodeLog.setUpdatetime(date);
				sendCodeLogService.update("updateByPrimaryKeySelective",sendCodeLog);
				return true;
			} else {//大于指定次数，则限时
				Date updateTime  = sendCodeLog.getUpdatetime();
				long diff = date.getTime()-updateTime.getTime();
				long hours = diff/(1000*60*60);
				long sendCodeProhibitedHours = Long.parseLong(Config.sendCodeProhibitedHours);//指定时间
				if (hours<sendCodeProhibitedHours) {//限时还没达到指定时间，继续限时
					return false;
				} else {  //达到指定限时，SendcodelimitTimes清零，重来
					sendCodeLog.setSendcodelimittimes(1);
					sendCodeLog.setUpdatetime(date);
					sendCodeLogService.update("updateByPrimaryKeySelective",sendCodeLog);
					return true;
				}
			}		
		}else{
	       //插入新记录
			SendcodeLog sendCodeLog_ = new SendcodeLog();
			sendCodeLog_.setPhonenumber(phonenumber);
			sendCodeLog_.setSendcodelimittimes(1);
			sendCodeLog_.setCreatetime(date);
			sendCodeLog_.setUpdatetime(date);
			sendCodeLogService.insert(sendCodeLog_);
			return true;
		}
	}
	
	/**
	 * getStartTime
	 * @return
	 */
	private static String getStartTime(){  
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        Long ll = todayStart.getTime().getTime();
        Date date = new Date(ll);
        return  ss.format(date);
    }  
      
	/**
	 * getEndTime
	 * @return
	 */
	private static String getEndTime(){  
    	SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        Long ll = todayEnd.getTime().getTime();
        Date date = new Date(ll);
        return  ss.format(date); 
    }
}
