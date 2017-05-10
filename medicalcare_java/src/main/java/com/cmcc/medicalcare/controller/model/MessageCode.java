package com.cmcc.medicalcare.controller.model;

/**MessageCode
 * 消息错误码以及部分规定的消息
 * @author wanggang
 *
 */
public class MessageCode {

	/**
	 * 成功，并且有数据或者没有任何系统和逻辑的异常的返回code
	 */
	public final static int CODE_200 = 200;
	
	/**
	 * 标识没有任何数据的返回code
	 */
	public final static int CODE_404 = 404;
	
	/**
	 * 在运行的时候，出现系统异常，并且没有终止程序的时候返回的code
	 */
	public final static int CODE_500 = 500;
	
	/**
	 * 缺失参数 code
	 */
	public final static int CODE_201 = 201;
	
	/**
	 * 数据格式错误 code
	 */
	public final static int CODE_202 = 202;
	
	/**
	 *没有登陆的code
	 */
	public final static int CODE_400 = 400;
	
	/**
	 *  501 操作失败
	 */
	public final static int CODE_501 = 501;
	
	/**
	 *  502 不再提示
	 */
	public final static int CODE_502 = 502;
	
	/**
	 * 405 已经存在
	 */
	public final static int CODE_405 = 405;
	
	/**
	 * 重复操作
	 */
	public final static int CODE_300 = 300;
	
	/**
	 * 宽带账号绑定过期
	 */
	public final static int CODE_399 = 399;
	
	/**
	 * CODE_999
	 */
	public final static int CODE_999 = 999;
	
	/**
	 * 第三方平台服务器通道未打开
	 */
	public final static int CODE_1000 = 1000;
	
	/**
	 * 第三方平台服务器忙，请稍后再试
	 */
	public final static int CODE_1001 = 1001;
	
	/**
	 * 
	 */
	public final static int CODE_1002 = 1002;
	
	/**
	 * MESSAGE_MOBILE
	 */
	public final static String MESSAGE_MOBILE = "非移动设备登陆，请使用移动设备";
	
	
	/***********************/
	/**
	 * 成功，并且有数据或者没有任何系统和逻辑的异常的返回code
	 */
	public final static String MESSAGE_200 = "成功";
	
	/**
	 * 标识没有任何数据的返回code
	 */
	public final static String MESSAGE_404 = "没有任何数据";
	
	/**
	 * 在运行的时候，出现系统异常，并且没有终止程序的时候返回的code
	 */
	public final static String MESSAGE_500 = "在运行的时候，出现系统异常";
	
	/**
	 * 缺失参数 code
	 */
	public final static String MESSAGE_201 = "缺失参数";
	
	/**
	 * 数据格式错误 code
	 */
	public final static String MESSAGE_202 = "数据格式错误";
	
	/**
	 *没有登陆的code
	 */
	public final static String MESSAGE_400 = "没有登陆";
	
	/**
	 *  501 操作失败
	 */
	public final static String MESSAGE_501 = "操作失败";
	
	/**
	 *  502 不再提示
	 */
	public final static String MESSAGE_502 = "不再提示";
	
	/**
	 * 405 已经存在
	 */
	public final static String MESSAGE_405 = "已经存在";
	
	/**
	 * 重复操作
	 */
	public final static String MESSAGE_300 = "重复操作";
	
	/**
	 * MESSAGE_1000
	 */
	public final static String MESSAGE_1000 = "业务系统维护中";
	
	/**
	 * MESSAGE_1001
	 */
	public final static String MESSAGE_1001 = "业务系统外联开关切换失败";
	
	/**
	 * 宽带账号绑定过期
	 */
	public final static String MESSAGE_399 = "宽带账号绑定过期";
	
}
