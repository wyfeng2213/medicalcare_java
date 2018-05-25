package com.cmcc.medicalcare.model;

import java.util.Date;

public class GroupDoctorsPatient {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.subject
     *
     * @mbggenerated
     */
    private String subject;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.doctors_id
     *
     * @mbggenerated
     */
    private String doctorsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.doctors_name
     *
     * @mbggenerated
     */
    private String doctorsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.doctors_phone
     *
     * @mbggenerated
     */
    private String doctorsPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_doctors_patient.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.id
     *
     * @return the value of group_doctors_patient.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.id
     *
     * @param id the value for group_doctors_patient.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.name
     *
     * @return the value of group_doctors_patient.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.name
     *
     * @param name the value for group_doctors_patient.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.subject
     *
     * @return the value of group_doctors_patient.subject
     *
     * @mbggenerated
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.subject
     *
     * @param subject the value for group_doctors_patient.subject
     *
     * @mbggenerated
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.doctors_id
     *
     * @return the value of group_doctors_patient.doctors_id
     *
     * @mbggenerated
     */
    public String getDoctorsId() {
        return doctorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.doctors_id
     *
     * @param doctorsId the value for group_doctors_patient.doctors_id
     *
     * @mbggenerated
     */
    public void setDoctorsId(String doctorsId) {
        this.doctorsId = doctorsId == null ? null : doctorsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.doctors_name
     *
     * @return the value of group_doctors_patient.doctors_name
     *
     * @mbggenerated
     */
    public String getDoctorsName() {
        return doctorsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.doctors_name
     *
     * @param doctorsName the value for group_doctors_patient.doctors_name
     *
     * @mbggenerated
     */
    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName == null ? null : doctorsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.doctors_phone
     *
     * @return the value of group_doctors_patient.doctors_phone
     *
     * @mbggenerated
     */
    public String getDoctorsPhone() {
        return doctorsPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.doctors_phone
     *
     * @param doctorsPhone the value for group_doctors_patient.doctors_phone
     *
     * @mbggenerated
     */
    public void setDoctorsPhone(String doctorsPhone) {
        this.doctorsPhone = doctorsPhone == null ? null : doctorsPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.createtime
     *
     * @return the value of group_doctors_patient.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.createtime
     *
     * @param createtime the value for group_doctors_patient.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_doctors_patient.updatetime
     *
     * @return the value of group_doctors_patient.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_doctors_patient.updatetime
     *
     * @param updatetime the value for group_doctors_patient.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}