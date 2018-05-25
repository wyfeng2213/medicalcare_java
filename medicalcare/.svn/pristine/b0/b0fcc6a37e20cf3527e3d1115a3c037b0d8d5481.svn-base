package com.yjktws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class test {

	public static void main(String[] args) {
		YjktService service = new YjktService_Service().getYjktServiceImplPort();
//		BindDev bindDev = new BindDev();
//		bindDev.setDevCode("06B31704010367");
//		bindDev.setDevRole("A");
//		bindDev.setIdCard("441283198602012019");
//		bindDev.setName("罗亦洲");
//		bindDev.setPhone("13928980514");
//		int code = service.bindUserDev(bindDev);
//		System.out.println("code==============="+code);
		
		QueryAbpm queryAbpm = service.findAbpm("13312340987","06B31704010367");
		System.out.println("queryAbpm==============="+queryAbpm);
		System.out.println("queryAbpm.getDbp()==============="+queryAbpm.getDevCode());
		System.out.println("queryAbpm.getDbp()==============="+queryAbpm.getSbp());
		System.out.println("queryAbpm.getDbp()==============="+queryAbpm.getDbp());
		System.out.println("queryAbpm.getDbp()==============="+queryAbpm.getHr());
		System.out.println("queryAbpm.getDbp()==============="+queryAbpm.getRecordDate());
		
		
		
		DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("dsdsdsdsdsds========"+ dateFormat.format(queryAbpm.getRecordDate().toGregorianCalendar().getTime()));
		
	}
	
}
