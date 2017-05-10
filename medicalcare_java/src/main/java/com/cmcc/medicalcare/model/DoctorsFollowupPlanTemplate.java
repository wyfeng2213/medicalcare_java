package com.cmcc.medicalcare.model;

import java.util.Date;

public class DoctorsFollowupPlanTemplate {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.doctors_id
     *
     * @mbggenerated
     */
    private Integer doctorsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.doctors_name
     *
     * @mbggenerated
     */
    private String doctorsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.operation_time
     *
     * @mbggenerated
     */
    private Date operationTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.is_remind_doctors
     *
     * @mbggenerated
     */
    private Integer isRemindDoctors;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.is_remind_patient
     *
     * @mbggenerated
     */
    private Integer isRemindPatient;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.remind_time
     *
     * @mbggenerated
     */
    private Integer remindTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.is_patient_visible
     *
     * @mbggenerated
     */
    private Integer isPatientVisible;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan_template.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.id
     *
     * @return the value of doctors_followup_plan_template.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.id
     *
     * @param id the value for doctors_followup_plan_template.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.title
     *
     * @return the value of doctors_followup_plan_template.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.title
     *
     * @param title the value for doctors_followup_plan_template.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.content
     *
     * @return the value of doctors_followup_plan_template.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.content
     *
     * @param content the value for doctors_followup_plan_template.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.type
     *
     * @return the value of doctors_followup_plan_template.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.type
     *
     * @param type the value for doctors_followup_plan_template.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.doctors_id
     *
     * @return the value of doctors_followup_plan_template.doctors_id
     *
     * @mbggenerated
     */
    public Integer getDoctorsId() {
        return doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.doctors_id
     *
     * @param doctorsId the value for doctors_followup_plan_template.doctors_id
     *
     * @mbggenerated
     */
    public void setDoctorsId(Integer doctorsId) {
        this.doctorsId = doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.doctors_name
     *
     * @return the value of doctors_followup_plan_template.doctors_name
     *
     * @mbggenerated
     */
    public String getDoctorsName() {
        return doctorsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.doctors_name
     *
     * @param doctorsName the value for doctors_followup_plan_template.doctors_name
     *
     * @mbggenerated
     */
    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName == null ? null : doctorsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.operation_time
     *
     * @return the value of doctors_followup_plan_template.operation_time
     *
     * @mbggenerated
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.operation_time
     *
     * @param operationTime the value for doctors_followup_plan_template.operation_time
     *
     * @mbggenerated
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.is_remind_doctors
     *
     * @return the value of doctors_followup_plan_template.is_remind_doctors
     *
     * @mbggenerated
     */
    public Integer getIsRemindDoctors() {
        return isRemindDoctors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.is_remind_doctors
     *
     * @param isRemindDoctors the value for doctors_followup_plan_template.is_remind_doctors
     *
     * @mbggenerated
     */
    public void setIsRemindDoctors(Integer isRemindDoctors) {
        this.isRemindDoctors = isRemindDoctors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.is_remind_patient
     *
     * @return the value of doctors_followup_plan_template.is_remind_patient
     *
     * @mbggenerated
     */
    public Integer getIsRemindPatient() {
        return isRemindPatient;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.is_remind_patient
     *
     * @param isRemindPatient the value for doctors_followup_plan_template.is_remind_patient
     *
     * @mbggenerated
     */
    public void setIsRemindPatient(Integer isRemindPatient) {
        this.isRemindPatient = isRemindPatient;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.remind_time
     *
     * @return the value of doctors_followup_plan_template.remind_time
     *
     * @mbggenerated
     */
    public Integer getRemindTime() {
        return remindTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.remind_time
     *
     * @param remindTime the value for doctors_followup_plan_template.remind_time
     *
     * @mbggenerated
     */
    public void setRemindTime(Integer remindTime) {
        this.remindTime = remindTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.is_patient_visible
     *
     * @return the value of doctors_followup_plan_template.is_patient_visible
     *
     * @mbggenerated
     */
    public Integer getIsPatientVisible() {
        return isPatientVisible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.is_patient_visible
     *
     * @param isPatientVisible the value for doctors_followup_plan_template.is_patient_visible
     *
     * @mbggenerated
     */
    public void setIsPatientVisible(Integer isPatientVisible) {
        this.isPatientVisible = isPatientVisible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.createtime
     *
     * @return the value of doctors_followup_plan_template.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.createtime
     *
     * @param createtime the value for doctors_followup_plan_template.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan_template.updatetime
     *
     * @return the value of doctors_followup_plan_template.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan_template.updatetime
     *
     * @param updatetime the value for doctors_followup_plan_template.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}