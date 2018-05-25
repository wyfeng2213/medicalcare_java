package com.cmcc.medicalcare.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class PatientBloodSugarAuto implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.device_name
     *
     * @mbggenerated
     */
    private String deviceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.device_id
     *
     * @mbggenerated
     */
    private String deviceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.device_from
     *
     * @mbggenerated
     */
    private String deviceFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.uploadtime
     *
     * @mbggenerated
     */
    private String uploadtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.bloodglucose
     *
     * @mbggenerated
     */
    private String bloodglucose;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_blood_sugar_auto.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;
    
	private Integer dataFromType;

    public Integer getDataFromType() {
		return dataFromType;
	}

	public void setDataFromType(Integer dataFromType) {
		this.dataFromType = dataFromType;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.id
     *
     * @return the value of patient_blood_sugar_auto.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.id
     *
     * @param id the value for patient_blood_sugar_auto.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.device_name
     *
     * @return the value of patient_blood_sugar_auto.device_name
     *
     * @mbggenerated
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.device_name
     *
     * @param deviceName the value for patient_blood_sugar_auto.device_name
     *
     * @mbggenerated
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.device_id
     *
     * @return the value of patient_blood_sugar_auto.device_id
     *
     * @mbggenerated
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.device_id
     *
     * @param deviceId the value for patient_blood_sugar_auto.device_id
     *
     * @mbggenerated
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.device_from
     *
     * @return the value of patient_blood_sugar_auto.device_from
     *
     * @mbggenerated
     */
    public String getDeviceFrom() {
        return deviceFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.device_from
     *
     * @param deviceFrom the value for patient_blood_sugar_auto.device_from
     *
     * @mbggenerated
     */
    public void setDeviceFrom(String deviceFrom) {
        this.deviceFrom = deviceFrom == null ? null : deviceFrom.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.uploadtime
     *
     * @return the value of patient_blood_sugar_auto.uploadtime
     *
     * @mbggenerated
     */
    public String getUploadtime() {
        return uploadtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.uploadtime
     *
     * @param uploadtime the value for patient_blood_sugar_auto.uploadtime
     *
     * @mbggenerated
     */
    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime == null ? null : uploadtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.bloodglucose
     *
     * @return the value of patient_blood_sugar_auto.bloodglucose
     *
     * @mbggenerated
     */
    public String getBloodglucose() {
        return bloodglucose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.bloodglucose
     *
     * @param bloodglucose the value for patient_blood_sugar_auto.bloodglucose
     *
     * @mbggenerated
     */
    public void setBloodglucose(String bloodglucose) {
        this.bloodglucose = bloodglucose == null ? null : bloodglucose.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.status
     *
     * @return the value of patient_blood_sugar_auto.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.status
     *
     * @param status the value for patient_blood_sugar_auto.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.createtime
     *
     * @return the value of patient_blood_sugar_auto.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.createtime
     *
     * @param createtime the value for patient_blood_sugar_auto.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_blood_sugar_auto.updatetime
     *
     * @return the value of patient_blood_sugar_auto.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_blood_sugar_auto.updatetime
     *
     * @param updatetime the value for patient_blood_sugar_auto.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}