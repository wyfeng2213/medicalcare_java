package com.cmcc.medicalcare.model;

import java.util.Date;

public class DoctorsFollowupPlan {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.doctors_id
     *
     * @mbggenerated
     */
    private Integer doctorsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.doctors_name
     *
     * @mbggenerated
     */
    private String doctorsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.doctors_phone
     *
     * @mbggenerated
     */
    private String doctorsPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.doctors_login_id
     *
     * @mbggenerated
     */
    private String doctorsLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.operation_time
     *
     * @mbggenerated
     */
    private Date operationTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.is_remind_doctors
     *
     * @mbggenerated
     */
    private Integer isRemindDoctors;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.is_remind_patient
     *
     * @mbggenerated
     */
    private Integer isRemindPatient;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.remind_time
     *
     * @mbggenerated
     */
    private Integer remindTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.is_patient_visible
     *
     * @mbggenerated
     */
    private Integer isPatientVisible;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.patient_id
     *
     * @mbggenerated
     */
    private Integer patientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.patient_name
     *
     * @mbggenerated
     */
    private String patientName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.patient_phone
     *
     * @mbggenerated
     */
    private String patientPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.patient_login_id
     *
     * @mbggenerated
     */
    private String patientLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_followup_plan.doctors_followup_plan_template_id
     *
     * @mbggenerated
     */
    private Integer doctorsFollowupPlanTemplateId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.id
     *
     * @return the value of doctors_followup_plan.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.id
     *
     * @param id the value for doctors_followup_plan.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.title
     *
     * @return the value of doctors_followup_plan.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.title
     *
     * @param title the value for doctors_followup_plan.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.content
     *
     * @return the value of doctors_followup_plan.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.content
     *
     * @param content the value for doctors_followup_plan.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.doctors_id
     *
     * @return the value of doctors_followup_plan.doctors_id
     *
     * @mbggenerated
     */
    public Integer getDoctorsId() {
        return doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.doctors_id
     *
     * @param doctorsId the value for doctors_followup_plan.doctors_id
     *
     * @mbggenerated
     */
    public void setDoctorsId(Integer doctorsId) {
        this.doctorsId = doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.doctors_name
     *
     * @return the value of doctors_followup_plan.doctors_name
     *
     * @mbggenerated
     */
    public String getDoctorsName() {
        return doctorsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.doctors_name
     *
     * @param doctorsName the value for doctors_followup_plan.doctors_name
     *
     * @mbggenerated
     */
    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName == null ? null : doctorsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.doctors_phone
     *
     * @return the value of doctors_followup_plan.doctors_phone
     *
     * @mbggenerated
     */
    public String getDoctorsPhone() {
        return doctorsPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.doctors_phone
     *
     * @param doctorsPhone the value for doctors_followup_plan.doctors_phone
     *
     * @mbggenerated
     */
    public void setDoctorsPhone(String doctorsPhone) {
        this.doctorsPhone = doctorsPhone == null ? null : doctorsPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.doctors_login_id
     *
     * @return the value of doctors_followup_plan.doctors_login_id
     *
     * @mbggenerated
     */
    public String getDoctorsLoginId() {
        return doctorsLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.doctors_login_id
     *
     * @param doctorsLoginId the value for doctors_followup_plan.doctors_login_id
     *
     * @mbggenerated
     */
    public void setDoctorsLoginId(String doctorsLoginId) {
        this.doctorsLoginId = doctorsLoginId == null ? null : doctorsLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.operation_time
     *
     * @return the value of doctors_followup_plan.operation_time
     *
     * @mbggenerated
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.operation_time
     *
     * @param operationTime the value for doctors_followup_plan.operation_time
     *
     * @mbggenerated
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.is_remind_doctors
     *
     * @return the value of doctors_followup_plan.is_remind_doctors
     *
     * @mbggenerated
     */
    public Integer getIsRemindDoctors() {
        return isRemindDoctors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.is_remind_doctors
     *
     * @param isRemindDoctors the value for doctors_followup_plan.is_remind_doctors
     *
     * @mbggenerated
     */
    public void setIsRemindDoctors(Integer isRemindDoctors) {
        this.isRemindDoctors = isRemindDoctors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.is_remind_patient
     *
     * @return the value of doctors_followup_plan.is_remind_patient
     *
     * @mbggenerated
     */
    public Integer getIsRemindPatient() {
        return isRemindPatient;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.is_remind_patient
     *
     * @param isRemindPatient the value for doctors_followup_plan.is_remind_patient
     *
     * @mbggenerated
     */
    public void setIsRemindPatient(Integer isRemindPatient) {
        this.isRemindPatient = isRemindPatient;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.remind_time
     *
     * @return the value of doctors_followup_plan.remind_time
     *
     * @mbggenerated
     */
    public Integer getRemindTime() {
        return remindTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.remind_time
     *
     * @param remindTime the value for doctors_followup_plan.remind_time
     *
     * @mbggenerated
     */
    public void setRemindTime(Integer remindTime) {
        this.remindTime = remindTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.is_patient_visible
     *
     * @return the value of doctors_followup_plan.is_patient_visible
     *
     * @mbggenerated
     */
    public Integer getIsPatientVisible() {
        return isPatientVisible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.is_patient_visible
     *
     * @param isPatientVisible the value for doctors_followup_plan.is_patient_visible
     *
     * @mbggenerated
     */
    public void setIsPatientVisible(Integer isPatientVisible) {
        this.isPatientVisible = isPatientVisible;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.createtime
     *
     * @return the value of doctors_followup_plan.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.createtime
     *
     * @param createtime the value for doctors_followup_plan.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.updatetime
     *
     * @return the value of doctors_followup_plan.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.updatetime
     *
     * @param updatetime the value for doctors_followup_plan.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.patient_id
     *
     * @return the value of doctors_followup_plan.patient_id
     *
     * @mbggenerated
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.patient_id
     *
     * @param patientId the value for doctors_followup_plan.patient_id
     *
     * @mbggenerated
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.patient_name
     *
     * @return the value of doctors_followup_plan.patient_name
     *
     * @mbggenerated
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.patient_name
     *
     * @param patientName the value for doctors_followup_plan.patient_name
     *
     * @mbggenerated
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.patient_phone
     *
     * @return the value of doctors_followup_plan.patient_phone
     *
     * @mbggenerated
     */
    public String getPatientPhone() {
        return patientPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.patient_phone
     *
     * @param patientPhone the value for doctors_followup_plan.patient_phone
     *
     * @mbggenerated
     */
    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone == null ? null : patientPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.patient_login_id
     *
     * @return the value of doctors_followup_plan.patient_login_id
     *
     * @mbggenerated
     */
    public String getPatientLoginId() {
        return patientLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.patient_login_id
     *
     * @param patientLoginId the value for doctors_followup_plan.patient_login_id
     *
     * @mbggenerated
     */
    public void setPatientLoginId(String patientLoginId) {
        this.patientLoginId = patientLoginId == null ? null : patientLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_followup_plan.doctors_followup_plan_template_id
     *
     * @return the value of doctors_followup_plan.doctors_followup_plan_template_id
     *
     * @mbggenerated
     */
    public Integer getDoctorsFollowupPlanTemplateId() {
        return doctorsFollowupPlanTemplateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_followup_plan.doctors_followup_plan_template_id
     *
     * @param doctorsFollowupPlanTemplateId the value for doctors_followup_plan.doctors_followup_plan_template_id
     *
     * @mbggenerated
     */
    public void setDoctorsFollowupPlanTemplateId(Integer doctorsFollowupPlanTemplateId) {
        this.doctorsFollowupPlanTemplateId = doctorsFollowupPlanTemplateId;
    }
}