package com.cmcc.medicalcare.model;

import java.util.Date;

public class SecretaryAndDoctorsAndPatientOfChatroom {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.patient_id
     *
     * @mbggenerated
     */
    private Integer patientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.patient_name
     *
     * @mbggenerated
     */
    private String patientName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.patient_phone
     *
     * @mbggenerated
     */
    private String patientPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.patient_login_id
     *
     * @mbggenerated
     */
    private String patientLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.secretary_id
     *
     * @mbggenerated
     */
    private Integer secretaryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.secretary_name
     *
     * @mbggenerated
     */
    private String secretaryName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.secretary_phone
     *
     * @mbggenerated
     */
    private String secretaryPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.secretary_login_id
     *
     * @mbggenerated
     */
    private String secretaryLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.doctors_id
     *
     * @mbggenerated
     */
    private Integer doctorsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.doctors_name
     *
     * @mbggenerated
     */
    private String doctorsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.doctors_phone
     *
     * @mbggenerated
     */
    private String doctorsPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.doctors_login_id
     *
     * @mbggenerated
     */
    private String doctorsLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.chatroom_id
     *
     * @mbggenerated
     */
    private String chatroomId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.chatroom_name
     *
     * @mbggenerated
     */
    private String chatroomName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.chatroom_description
     *
     * @mbggenerated
     */
    private String chatroomDescription;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.chatroom_owner
     *
     * @mbggenerated
     */
    private String chatroomOwner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.chatroom_maxusers
     *
     * @mbggenerated
     */
    private Integer chatroomMaxusers;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column secretary_doctors_patient_chatroom.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.id
     *
     * @return the value of secretary_doctors_patient_chatroom.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.id
     *
     * @param id the value for secretary_doctors_patient_chatroom.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.patient_id
     *
     * @return the value of secretary_doctors_patient_chatroom.patient_id
     *
     * @mbggenerated
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.patient_id
     *
     * @param patientId the value for secretary_doctors_patient_chatroom.patient_id
     *
     * @mbggenerated
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.patient_name
     *
     * @return the value of secretary_doctors_patient_chatroom.patient_name
     *
     * @mbggenerated
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.patient_name
     *
     * @param patientName the value for secretary_doctors_patient_chatroom.patient_name
     *
     * @mbggenerated
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.patient_phone
     *
     * @return the value of secretary_doctors_patient_chatroom.patient_phone
     *
     * @mbggenerated
     */
    public String getPatientPhone() {
        return patientPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.patient_phone
     *
     * @param patientPhone the value for secretary_doctors_patient_chatroom.patient_phone
     *
     * @mbggenerated
     */
    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone == null ? null : patientPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.patient_login_id
     *
     * @return the value of secretary_doctors_patient_chatroom.patient_login_id
     *
     * @mbggenerated
     */
    public String getPatientLoginId() {
        return patientLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.patient_login_id
     *
     * @param patientLoginId the value for secretary_doctors_patient_chatroom.patient_login_id
     *
     * @mbggenerated
     */
    public void setPatientLoginId(String patientLoginId) {
        this.patientLoginId = patientLoginId == null ? null : patientLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.secretary_id
     *
     * @return the value of secretary_doctors_patient_chatroom.secretary_id
     *
     * @mbggenerated
     */
    public Integer getSecretaryId() {
        return secretaryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.secretary_id
     *
     * @param secretaryId the value for secretary_doctors_patient_chatroom.secretary_id
     *
     * @mbggenerated
     */
    public void setSecretaryId(Integer secretaryId) {
        this.secretaryId = secretaryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.secretary_name
     *
     * @return the value of secretary_doctors_patient_chatroom.secretary_name
     *
     * @mbggenerated
     */
    public String getSecretaryName() {
        return secretaryName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.secretary_name
     *
     * @param secretaryName the value for secretary_doctors_patient_chatroom.secretary_name
     *
     * @mbggenerated
     */
    public void setSecretaryName(String secretaryName) {
        this.secretaryName = secretaryName == null ? null : secretaryName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.secretary_phone
     *
     * @return the value of secretary_doctors_patient_chatroom.secretary_phone
     *
     * @mbggenerated
     */
    public String getSecretaryPhone() {
        return secretaryPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.secretary_phone
     *
     * @param secretaryPhone the value for secretary_doctors_patient_chatroom.secretary_phone
     *
     * @mbggenerated
     */
    public void setSecretaryPhone(String secretaryPhone) {
        this.secretaryPhone = secretaryPhone == null ? null : secretaryPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.secretary_login_id
     *
     * @return the value of secretary_doctors_patient_chatroom.secretary_login_id
     *
     * @mbggenerated
     */
    public String getSecretaryLoginId() {
        return secretaryLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.secretary_login_id
     *
     * @param secretaryLoginId the value for secretary_doctors_patient_chatroom.secretary_login_id
     *
     * @mbggenerated
     */
    public void setSecretaryLoginId(String secretaryLoginId) {
        this.secretaryLoginId = secretaryLoginId == null ? null : secretaryLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.doctors_id
     *
     * @return the value of secretary_doctors_patient_chatroom.doctors_id
     *
     * @mbggenerated
     */
    public Integer getDoctorsId() {
        return doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.doctors_id
     *
     * @param doctorsId the value for secretary_doctors_patient_chatroom.doctors_id
     *
     * @mbggenerated
     */
    public void setDoctorsId(Integer doctorsId) {
        this.doctorsId = doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.doctors_name
     *
     * @return the value of secretary_doctors_patient_chatroom.doctors_name
     *
     * @mbggenerated
     */
    public String getDoctorsName() {
        return doctorsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.doctors_name
     *
     * @param doctorsName the value for secretary_doctors_patient_chatroom.doctors_name
     *
     * @mbggenerated
     */
    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName == null ? null : doctorsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.doctors_phone
     *
     * @return the value of secretary_doctors_patient_chatroom.doctors_phone
     *
     * @mbggenerated
     */
    public String getDoctorsPhone() {
        return doctorsPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.doctors_phone
     *
     * @param doctorsPhone the value for secretary_doctors_patient_chatroom.doctors_phone
     *
     * @mbggenerated
     */
    public void setDoctorsPhone(String doctorsPhone) {
        this.doctorsPhone = doctorsPhone == null ? null : doctorsPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.doctors_login_id
     *
     * @return the value of secretary_doctors_patient_chatroom.doctors_login_id
     *
     * @mbggenerated
     */
    public String getDoctorsLoginId() {
        return doctorsLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.doctors_login_id
     *
     * @param doctorsLoginId the value for secretary_doctors_patient_chatroom.doctors_login_id
     *
     * @mbggenerated
     */
    public void setDoctorsLoginId(String doctorsLoginId) {
        this.doctorsLoginId = doctorsLoginId == null ? null : doctorsLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.remark
     *
     * @return the value of secretary_doctors_patient_chatroom.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.remark
     *
     * @param remark the value for secretary_doctors_patient_chatroom.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.chatroom_id
     *
     * @return the value of secretary_doctors_patient_chatroom.chatroom_id
     *
     * @mbggenerated
     */
    public String getChatroomId() {
        return chatroomId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.chatroom_id
     *
     * @param chatroomId the value for secretary_doctors_patient_chatroom.chatroom_id
     *
     * @mbggenerated
     */
    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.chatroom_name
     *
     * @return the value of secretary_doctors_patient_chatroom.chatroom_name
     *
     * @mbggenerated
     */
    public String getChatroomName() {
        return chatroomName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.chatroom_name
     *
     * @param chatroomName the value for secretary_doctors_patient_chatroom.chatroom_name
     *
     * @mbggenerated
     */
    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName == null ? null : chatroomName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.chatroom_description
     *
     * @return the value of secretary_doctors_patient_chatroom.chatroom_description
     *
     * @mbggenerated
     */
    public String getChatroomDescription() {
        return chatroomDescription;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.chatroom_description
     *
     * @param chatroomDescription the value for secretary_doctors_patient_chatroom.chatroom_description
     *
     * @mbggenerated
     */
    public void setChatroomDescription(String chatroomDescription) {
        this.chatroomDescription = chatroomDescription == null ? null : chatroomDescription.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.chatroom_owner
     *
     * @return the value of secretary_doctors_patient_chatroom.chatroom_owner
     *
     * @mbggenerated
     */
    public String getChatroomOwner() {
        return chatroomOwner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.chatroom_owner
     *
     * @param chatroomOwner the value for secretary_doctors_patient_chatroom.chatroom_owner
     *
     * @mbggenerated
     */
    public void setChatroomOwner(String chatroomOwner) {
        this.chatroomOwner = chatroomOwner == null ? null : chatroomOwner.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.chatroom_maxusers
     *
     * @return the value of secretary_doctors_patient_chatroom.chatroom_maxusers
     *
     * @mbggenerated
     */
    public Integer getChatroomMaxusers() {
        return chatroomMaxusers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.chatroom_maxusers
     *
     * @param chatroomMaxusers the value for secretary_doctors_patient_chatroom.chatroom_maxusers
     *
     * @mbggenerated
     */
    public void setChatroomMaxusers(Integer chatroomMaxusers) {
        this.chatroomMaxusers = chatroomMaxusers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.createtime
     *
     * @return the value of secretary_doctors_patient_chatroom.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.createtime
     *
     * @param createtime the value for secretary_doctors_patient_chatroom.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column secretary_doctors_patient_chatroom.updatetime
     *
     * @return the value of secretary_doctors_patient_chatroom.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column secretary_doctors_patient_chatroom.updatetime
     *
     * @param updatetime the value for secretary_doctors_patient_chatroom.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}