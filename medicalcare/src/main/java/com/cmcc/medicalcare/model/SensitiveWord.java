package com.cmcc.medicalcare.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class SensitiveWord implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sensitive_word.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sensitive_word.text
     *
     * @mbggenerated
     */
    private String text;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sensitive_word.level
     *
     * @mbggenerated
     */
    private String level;
    
    
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sensitive_word.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sensitive_word.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sensitive_word.id
     *
     * @return the value of sensitive_word.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sensitive_word.id
     *
     * @param id the value for sensitive_word.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sensitive_word.text
     *
     * @return the value of sensitive_word.text
     *
     * @mbggenerated
     */
    public String getText() {
        return text;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sensitive_word.text
     *
     * @param text the value for sensitive_word.text
     *
     * @mbggenerated
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sensitive_word.level
     *
     * @return the value of sensitive_word.level
     *
     * @mbggenerated
     */
    public String getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sensitive_word.level
     *
     * @param level the value for sensitive_word.level
     *
     * @mbggenerated
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sensitive_word.createtime
     *
     * @return the value of sensitive_word.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sensitive_word.createtime
     *
     * @param createtime the value for sensitive_word.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sensitive_word.updatetime
     *
     * @return the value of sensitive_word.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sensitive_word.updatetime
     *
     * @param updatetime the value for sensitive_word.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
    
    
}