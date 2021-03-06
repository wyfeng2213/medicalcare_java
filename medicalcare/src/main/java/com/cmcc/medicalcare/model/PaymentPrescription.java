package com.cmcc.medicalcare.model;

import java.util.Date;

public class PaymentPrescription {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.prescription_id
     *
     * @mbggenerated
     */
    private String prescriptionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.address_id
     *
     * @mbggenerated
     */
    private String addressId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.orderinfo_json
     *
     * @mbggenerated
     */
    private String orderinfoJson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.pay_state
     *
     * @mbggenerated
     */
    private Integer payState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column payment_prescription.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.id
     *
     * @return the value of payment_prescription.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.id
     *
     * @param id the value for payment_prescription.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.member_id
     *
     * @return the value of payment_prescription.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.member_id
     *
     * @param memberId the value for payment_prescription.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.prescription_id
     *
     * @return the value of payment_prescription.prescription_id
     *
     * @mbggenerated
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.prescription_id
     *
     * @param prescriptionId the value for payment_prescription.prescription_id
     *
     * @mbggenerated
     */
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId == null ? null : prescriptionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.address_id
     *
     * @return the value of payment_prescription.address_id
     *
     * @mbggenerated
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.address_id
     *
     * @param addressId the value for payment_prescription.address_id
     *
     * @mbggenerated
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.orderinfo_json
     *
     * @return the value of payment_prescription.orderinfo_json
     *
     * @mbggenerated
     */
    public String getOrderinfoJson() {
        return orderinfoJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.orderinfo_json
     *
     * @param orderinfoJson the value for payment_prescription.orderinfo_json
     *
     * @mbggenerated
     */
    public void setOrderinfoJson(String orderinfoJson) {
        this.orderinfoJson = orderinfoJson == null ? null : orderinfoJson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.pay_state
     *
     * @return the value of payment_prescription.pay_state
     *
     * @mbggenerated
     */
    public Integer getPayState() {
        return payState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.pay_state
     *
     * @param payState the value for payment_prescription.pay_state
     *
     * @mbggenerated
     */
    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.createtime
     *
     * @return the value of payment_prescription.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.createtime
     *
     * @param createtime the value for payment_prescription.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column payment_prescription.updatetime
     *
     * @return the value of payment_prescription.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column payment_prescription.updatetime
     *
     * @param updatetime the value for payment_prescription.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}