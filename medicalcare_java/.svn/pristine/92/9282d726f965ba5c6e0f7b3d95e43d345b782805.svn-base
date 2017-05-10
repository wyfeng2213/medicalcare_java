package com.cmcc.medicalcare.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * @author Jack Chan
 *控制交互的平台的请求数量
 */
public class ReadCountUtil {
	/**
	 * ecopCount
	 */
	public static int ecopCount;//控制对Ecop的的请求控制
	/**
	 * aaaCount
	 */
	public static int aaaCount;//控制对AAA平台的请求控制
	/**
	 * hbawsCount
	 */
	public static int hbawsCount;//控制对感知平台的请求控制
	/**
	 * ipSwitch
	 */
	public static int ipSwitch; //ip过滤开关 0表示关1表示开
	
	
	
	static{
		Properties props = new Properties();  
        try {  
            props=PropertiesLoaderUtils.loadAllProperties("count.properties");
            Object ecopObject = props.get("EcopCount");
            Object aaaObject  = props.get("AAACount");
            Object hbawsObject = props.get("HbawsCount");
            Object ipObject = props.get("ipSwitch");
            if(null!=props && null!=ecopObject && null!=aaaObject &&null!=hbawsObject && null!=ipObject) {
//            	 System.out.println(ecopObject.toString());
                 ecopCount = Integer.parseInt(ecopObject.toString());
                 aaaCount = Integer.parseInt(aaaObject.toString());
                 hbawsCount = Integer.parseInt(hbawsObject.toString());
                 ipSwitch = Integer.parseInt(ipObject.toString());
            }
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
          
	
	}

}
