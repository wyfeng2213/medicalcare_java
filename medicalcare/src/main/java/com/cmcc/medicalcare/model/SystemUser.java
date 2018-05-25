package com.cmcc.medicalcare.model;

import java.io.Serializable;
import java.util.Date;

public class SystemUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_password
     *
     * @mbggenerated
     */
    private String userPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_realname
     *
     * @mbggenerated
     */
    private String userRealname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_address
     *
     * @mbggenerated
     */
    private String userAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_email
     *
     * @mbggenerated
     */
    private String userEmail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_tel
     *
     * @mbggenerated
     */
    private String userTel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_role
     *
     * @mbggenerated
     */
    private Integer userRole;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.create_user
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.create_userid
     *
     * @mbggenerated
     */
    private Integer createUserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.last_editpwd_time
     *
     * @mbggenerated
     */
    private Date lastEditpwdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.is_enable
     *
     * @mbggenerated
     */
    private Integer isEnable;
    
    private Integer hospitalId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_user.user_name
     *
     * @mbggenerated
     */
    private String hospitalName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.id
     *
     * @return the value of system_user.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.id
     *
     * @param id the value for system_user.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_name
     *
     * @return the value of system_user.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_name
     *
     * @param userName the value for system_user.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_password
     *
     * @return the value of system_user.user_password
     *
     * @mbggenerated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_password
     *
     * @param userPassword the value for system_user.user_password
     *
     * @mbggenerated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_realname
     *
     * @return the value of system_user.user_realname
     *
     * @mbggenerated
     */
    public String getUserRealname() {
        return userRealname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_realname
     *
     * @param userRealname the value for system_user.user_realname
     *
     * @mbggenerated
     */
    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname == null ? null : userRealname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_address
     *
     * @return the value of system_user.user_address
     *
     * @mbggenerated
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_address
     *
     * @param userAddress the value for system_user.user_address
     *
     * @mbggenerated
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_email
     *
     * @return the value of system_user.user_email
     *
     * @mbggenerated
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_email
     *
     * @param userEmail the value for system_user.user_email
     *
     * @mbggenerated
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_tel
     *
     * @return the value of system_user.user_tel
     *
     * @mbggenerated
     */
    public String getUserTel() {
        return userTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_tel
     *
     * @param userTel the value for system_user.user_tel
     *
     * @mbggenerated
     */
    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.user_role
     *
     * @return the value of system_user.user_role
     *
     * @mbggenerated
     */
    public Integer getUserRole() {
        return userRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.user_role
     *
     * @param userRole the value for system_user.user_role
     *
     * @mbggenerated
     */
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.create_user
     *
     * @return the value of system_user.create_user
     *
     * @mbggenerated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.create_user
     *
     * @param createUser the value for system_user.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.create_userid
     *
     * @return the value of system_user.create_userid
     *
     * @mbggenerated
     */
    public Integer getCreateUserid() {
        return createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.create_userid
     *
     * @param createUserid the value for system_user.create_userid
     *
     * @mbggenerated
     */
    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.create_time
     *
     * @return the value of system_user.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.create_time
     *
     * @param createTime the value for system_user.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.last_editpwd_time
     *
     * @return the value of system_user.last_editpwd_time
     *
     * @mbggenerated
     */
    public Date getLastEditpwdTime() {
        return lastEditpwdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.last_editpwd_time
     *
     * @param lastEditpwdTime the value for system_user.last_editpwd_time
     *
     * @mbggenerated
     */
    public void setLastEditpwdTime(Date lastEditpwdTime) {
        this.lastEditpwdTime = lastEditpwdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_user.is_enable
     *
     * @return the value of system_user.is_enable
     *
     * @mbggenerated
     */
    public Integer getIsEnable() {
        return isEnable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_user.is_enable
     *
     * @param isEnable the value for system_user.is_enable
     *
     * @mbggenerated
     */
    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

	/**
	 * @return the hospitalId
	 */
	public Integer getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the hospitalName
	 */
	public String getHospitalName() {
		return hospitalName;
	}

	/**
	 * @param hospitalName the hospitalName to set
	 */
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
}