package com.cmcc.medicalcare.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * UtilDatePropertyEditor
 * @author Administrator
 *
 */
public class UtilDatePropertyEditor extends PropertyEditorSupport {
	private String format  = "yyyy-MM-dd HH:mm:ss";
	/**
	 * setAsText
	 */
	public void setAsText(String text) throws IllegalArgumentException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(text);
			this.setValue(date);
		} catch (ParseException e) {
			System.out.println(e.getMessage() + "日期的格式不对");
		}
	}
	/**
	 * setFormat
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
}
