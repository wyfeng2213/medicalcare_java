package com.cmcc.medicalcare.controller.model;

/**
 * 所有ajax和mobile移动设备访问获取数据的时候的返回数据，作用主要是code，通过code判断是不是有数据，或者返回
 * 一些错误信息的标识 ，有利于消息的统一管理
  * @ClassName Results
  * @Description TODO
  * @author wanggang wanggangworkemail@gmail.com
  * @date 2015年6月28日
  * @Commen 功能说明
  * @ModifyByAuthor 修改人
  * @ModifyCommand 修改说明
  * @ModifyTime 2015年6月28日
  * @param <T>
 */
public class Results<T> {
	/**
	 * 这里规定，如果是出现错误，code的值为500
	 * 200 获取成功
	 * 404 没有数据
	 * 400 没有登录
	 * 201 缺失参数
	 * 501 操作失败
	 * 402 参数错误
	 * 405 已经存在
	 * 202 数据错误
	 * 205 超时
	 */
	private Integer code;
	/**
	 * message
	 */
	private String message;
	/**
	 * data
	 */
	private T data;//接口参数信息及返回信息
	/**
	 * equipmentData
	 */
	private T equipmentData;//客户端设备信息
	/**
	 * getCode
	 * @return
	 */
	public Integer getCode() {
		return code;
	}
	
	/**
	 * 这里规定，如果是出现错误，code的值为500
	 * 200 获取成功
	 * 404 没有数据
	 * 400 没有登录
	 * 201 缺失参数
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * getData
	 * @return
	 */
	public T getData() {
		return data;
	}
	/**
	 * setData
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	
	}

	/**
	 * getMessage
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * setMessage
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	

	/**
	 * getEquipmentData
	 * @return
	 */
	public T getEquipmentData() {
		return equipmentData;
	}

	/**
	 * setEquipmentData
	 * @param equipmentData
	 */
	public void setEquipmentData(T equipmentData) {
		this.equipmentData = equipmentData;
	}

	/**
	 * Results
	 */
	public Results(){
		
	}
	/**
	 * Results
	 * @param code
	 * @param data
	 */
	public Results(Integer code, T data) {
		super();
		this.code = code;
		this.data = data;
	}

	/**
	 * Results
	 * @param code
	 * @param message
	 * @param data
	 * @param equipmentData
	 */
	public Results(Integer code, String message, T data,T equipmentData) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.equipmentData = equipmentData;
	}
	

	/**
	 * 
	 * @param code 返回码
	 * @param message 返回描述信息
	 * @param data 返回数据
	 */
	public Results(Integer code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
}	