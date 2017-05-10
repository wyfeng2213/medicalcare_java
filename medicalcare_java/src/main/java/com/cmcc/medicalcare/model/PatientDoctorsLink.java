package com.cmcc.medicalcare.model;

import java.util.Date;

public class PatientDoctorsLink {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.patient_id
     *
     * @mbggenerated
     */
    private Integer patientId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.patient_name
     *
     * @mbggenerated
     */
    private String patientName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.patient_phone
     *
     * @mbggenerated
     */
    private String patientPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.patient_login_id
     *
     * @mbggenerated
     */
    private String patientLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.doctors_id
     *
     * @mbggenerated
     */
    private Integer doctorsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.doctors_name
     *
     * @mbggenerated
     */
    private String doctorsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.doctors_phone
     *
     * @mbggenerated
     */
    private String doctorsPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.chatroom_id
     *
     * @mbggenerated
     */
    private String chatroomId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.doctors_login_id
     *
     * @mbggenerated
     */
    private String doctorsLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.updatetime
     *
     * @mbggenerated
     */
    private String updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column patient_doctors_link.type
     *
     * @mbggenerated
     */
    private Integer type;
    
    private DoctorsUser doctorsUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.id
     *
     * @return the value of patient_doctors_link.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.id
     *
     * @param id the value for patient_doctors_link.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.patient_id
     *
     * @return the value of patient_doctors_link.patient_id
     *
     * @mbggenerated
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.patient_id
     *
     * @param patientId the value for patient_doctors_link.patient_id
     *
     * @mbggenerated
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.patient_name
     *
     * @return the value of patient_doctors_link.patient_name
     *
     * @mbggenerated
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.patient_name
     *
     * @param patientName the value for patient_doctors_link.patient_name
     *
     * @mbggenerated
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.patient_phone
     *
     * @return the value of patient_doctors_link.patient_phone
     *
     * @mbggenerated
     */
    public String getPatientPhone() {
        return patientPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.patient_phone
     *
     * @param patientPhone the value for patient_doctors_link.patient_phone
     *
     * @mbggenerated
     */
    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone == null ? null : patientPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.patient_login_id
     *
     * @return the value of patient_doctors_link.patient_login_id
     *
     * @mbggenerated
     */
    public String getPatientLoginId() {
        return patientLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.patient_login_id
     *
     * @param patientLoginId the value for patient_doctors_link.patient_login_id
     *
     * @mbggenerated
     */
    public void setPatientLoginId(String patientLoginId) {
        this.patientLoginId = patientLoginId == null ? null : patientLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.doctors_id
     *
     * @return the value of patient_doctors_link.doctors_id
     *
     * @mbggenerated
     */
    public Integer getDoctorsId() {
        return doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.doctors_id
     *
     * @param doctorsId the value for patient_doctors_link.doctors_id
     *
     * @mbggenerated
     */
    public void setDoctorsId(Integer doctorsId) {
        this.doctorsId = doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.doctors_name
     *
     * @return the value of patient_doctors_link.doctors_name
     *
     * @mbggenerated
     */
    public String getDoctorsName() {
        return doctorsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.doctors_name
     *
     * @param doctorsName the value for patient_doctors_link.doctors_name
     *
     * @mbggenerated
     */
    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName == null ? null : doctorsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.doctors_phone
     *
     * @return the value of patient_doctors_link.doctors_phone
     *
     * @mbggenerated
     */
    public String getDoctorsPhone() {
        return doctorsPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.doctors_phone
     *
     * @param doctorsPhone the value for patient_doctors_link.doctors_phone
     *
     * @mbggenerated
     */
    public void setDoctorsPhone(String doctorsPhone) {
        this.doctorsPhone = doctorsPhone == null ? null : doctorsPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.chatroom_id
     *
     * @return the value of patient_doctors_link.chatroom_id
     *
     * @mbggenerated
     */
    public String getChatroomId() {
        return chatroomId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.chatroom_id
     *
     * @param chatroomId the value for patient_doctors_link.chatroom_id
     *
     * @mbggenerated
     */
    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId == null ? null : chatroomId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.doctors_login_id
     *
     * @return the value of patient_doctors_link.doctors_login_id
     *
     * @mbggenerated
     */
    public String getDoctorsLoginId() {
        return doctorsLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.doctors_login_id
     *
     * @param doctorsLoginId the value for patient_doctors_link.doctors_login_id
     *
     * @mbggenerated
     */
    public void setDoctorsLoginId(String doctorsLoginId) {
        this.doctorsLoginId = doctorsLoginId == null ? null : doctorsLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.createtime
     *
     * @return the value of patient_doctors_link.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.createtime
     *
     * @param createtime the value for patient_doctors_link.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.updatetime
     *
     * @return the value of patient_doctors_link.updatetime
     *
     * @mbggenerated
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.updatetime
     *
     * @param updatetime the value for patient_doctors_link.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column patient_doctors_link.type
     *
     * @return the value of patient_doctors_link.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column patient_doctors_link.type
     *
     * @param type the value for patient_doctors_link.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

	public DoctorsUser getDoctorsUser() {
		return doctorsUser;
	}

	public void setDoctorsUser(DoctorsUser doctorsUser) {
		this.doctorsUser = doctorsUser;
	}
}