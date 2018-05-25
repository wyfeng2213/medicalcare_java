package com.cmcc.medicalcare.model;

import java.util.Date;

public class ContractedHospitalsDoctorsLink {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contracted_hospitals_doctors_link.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contracted_hospitals_doctors_link.hospital_id
     *
     * @mbggenerated
     */
    private Integer hospitalId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contracted_hospitals_doctors_link.doctor_phone
     *
     * @mbggenerated
     */
    private String doctorPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contracted_hospitals_doctors_link.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column contracted_hospitals_doctors_link.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contracted_hospitals_doctors_link.id
     *
     * @return the value of contracted_hospitals_doctors_link.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contracted_hospitals_doctors_link.id
     *
     * @param id the value for contracted_hospitals_doctors_link.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contracted_hospitals_doctors_link.hospital_id
     *
     * @return the value of contracted_hospitals_doctors_link.hospital_id
     *
     * @mbggenerated
     */
    public Integer getHospitalId() {
        return hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contracted_hospitals_doctors_link.hospital_id
     *
     * @param hospitalId the value for contracted_hospitals_doctors_link.hospital_id
     *
     * @mbggenerated
     */
    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contracted_hospitals_doctors_link.doctor_phone
     *
     * @return the value of contracted_hospitals_doctors_link.doctor_phone
     *
     * @mbggenerated
     */
    public String getDoctorPhone() {
        return doctorPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contracted_hospitals_doctors_link.doctor_phone
     *
     * @param doctorPhone the value for contracted_hospitals_doctors_link.doctor_phone
     *
     * @mbggenerated
     */
    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone == null ? null : doctorPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contracted_hospitals_doctors_link.createtime
     *
     * @return the value of contracted_hospitals_doctors_link.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contracted_hospitals_doctors_link.createtime
     *
     * @param createtime the value for contracted_hospitals_doctors_link.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column contracted_hospitals_doctors_link.updatetime
     *
     * @return the value of contracted_hospitals_doctors_link.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column contracted_hospitals_doctors_link.updatetime
     *
     * @param updatetime the value for contracted_hospitals_doctors_link.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}