package com.cmcc.medicalcare.model;

import java.util.Date;

/**
 * 该实体医生信息字段对应将导入的excel模板中相应列
 * @author Administrator
 *
 */
public class ExcelDoctorUserEntity {

    private String phone; //电话
    private String name; //名字
    private String sex; //性别
    private String title; //职称
    private String hospital; //就职医院
    private String department; //科室
    private Date startWorkingTime; //开始工作时间
    private String departmentTelephone; //科室电话
    private String introduction; //医生简介
    private Integer doctortype; //医生类别
    private String cityName;  //医院所在城市
    private String area;  //医院所在区县
    private String hospitalPhone;  //医院固定电话
    private String hospitalAddr;  //医院地址
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Date getStartWorkingTime() {
		return startWorkingTime;
	}
	public void setStartWorkingTime(Date startWorkingTime) {
		this.startWorkingTime = startWorkingTime;
	}
	public String getDepartmentTelephone() {
		return departmentTelephone;
	}
	public void setDepartmentTelephone(String departmentTelephone) {
		this.departmentTelephone = departmentTelephone;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getDoctortype() {
		return doctortype;
	}
	public void setDoctortype(Integer doctortype) {
		this.doctortype = doctortype;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getHospitalPhone() {
		return hospitalPhone;
	}
	public void setHospitalPhone(String hospitalPhone) {
		this.hospitalPhone = hospitalPhone;
	}
	public String getHospitalAddr() {
		return hospitalAddr;
	}
	public void setHospitalAddr(String hospitalAddr) {
		this.hospitalAddr = hospitalAddr;
	}
    
    
}