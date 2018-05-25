package com.cmcc.medicalcare.model;

import java.util.Date;

public class HealthDeviceInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.device_ID
     *
     * @mbggenerated
     */
    private String deviceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.device_name
     *
     * @mbggenerated
     */
    private String deviceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.device_role
     *
     * @mbggenerated
     */
    private Integer deviceRole;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.device_type
     *
     * @mbggenerated
     */
    private Integer deviceType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.device_from
     *
     * @mbggenerated
     */
    private Integer deviceFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.patient_phone
     *
     * @mbggenerated
     */
    private String patientPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column health_device_info.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.id
     *
     * @return the value of health_device_info.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.id
     *
     * @param id the value for health_device_info.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.device_ID
     *
     * @return the value of health_device_info.device_ID
     *
     * @mbggenerated
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.device_ID
     *
     * @param deviceId the value for health_device_info.device_ID
     *
     * @mbggenerated
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.device_name
     *
     * @return the value of health_device_info.device_name
     *
     * @mbggenerated
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.device_name
     *
     * @param deviceName the value for health_device_info.device_name
     *
     * @mbggenerated
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.device_role
     *
     * @return the value of health_device_info.device_role
     *
     * @mbggenerated
     */
    public Integer getDeviceRole() {
        return deviceRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.device_role
     *
     * @param deviceRole the value for health_device_info.device_role
     *
     * @mbggenerated
     */
    public void setDeviceRole(Integer deviceRole) {
        this.deviceRole = deviceRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.device_type
     *
     * @return the value of health_device_info.device_type
     *
     * @mbggenerated
     */
    public Integer getDeviceType() {
        return deviceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.device_type
     *
     * @param deviceType the value for health_device_info.device_type
     *
     * @mbggenerated
     */
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.device_from
     *
     * @return the value of health_device_info.device_from
     *
     * @mbggenerated
     */
    public Integer getDeviceFrom() {
        return deviceFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.device_from
     *
     * @param deviceFrom the value for health_device_info.device_from
     *
     * @mbggenerated
     */
    public void setDeviceFrom(Integer deviceFrom) {
        this.deviceFrom = deviceFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.patient_phone
     *
     * @return the value of health_device_info.patient_phone
     *
     * @mbggenerated
     */
    public String getPatientPhone() {
        return patientPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.patient_phone
     *
     * @param patientPhone the value for health_device_info.patient_phone
     *
     * @mbggenerated
     */
    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone == null ? null : patientPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.createtime
     *
     * @return the value of health_device_info.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.createtime
     *
     * @param createtime the value for health_device_info.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column health_device_info.updatetime
     *
     * @return the value of health_device_info.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column health_device_info.updatetime
     *
     * @param updatetime the value for health_device_info.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}