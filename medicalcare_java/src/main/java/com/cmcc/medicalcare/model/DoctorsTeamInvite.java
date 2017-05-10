package com.cmcc.medicalcare.model;

import java.util.Date;

public class DoctorsTeamInvite {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.team_id
     *
     * @mbggenerated
     */
    private Integer teamId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.team_name
     *
     * @mbggenerated
     */
    private String teamName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invite_useid
     *
     * @mbggenerated
     */
    private Integer inviteUseid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invite_name
     *
     * @mbggenerated
     */
    private String inviteName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invite_phone
     *
     * @mbggenerated
     */
    private String invitePhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invite_login_id
     *
     * @mbggenerated
     */
    private String inviteLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invited_id
     *
     * @mbggenerated
     */
    private Integer invitedId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invited_name
     *
     * @mbggenerated
     */
    private String invitedName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invited_phone
     *
     * @mbggenerated
     */
    private String invitedPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.invited_login_id
     *
     * @mbggenerated
     */
    private String invitedLoginId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.createtime
     *
     * @mbggenerated
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column doctors_team_invite.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.id
     *
     * @return the value of doctors_team_invite.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.id
     *
     * @param id the value for doctors_team_invite.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.team_id
     *
     * @return the value of doctors_team_invite.team_id
     *
     * @mbggenerated
     */
    public Integer getTeamId() {
        return teamId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.team_id
     *
     * @param teamId the value for doctors_team_invite.team_id
     *
     * @mbggenerated
     */
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.team_name
     *
     * @return the value of doctors_team_invite.team_name
     *
     * @mbggenerated
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.team_name
     *
     * @param teamName the value for doctors_team_invite.team_name
     *
     * @mbggenerated
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invite_useid
     *
     * @return the value of doctors_team_invite.invite_useid
     *
     * @mbggenerated
     */
    public Integer getInviteUseid() {
        return inviteUseid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invite_useid
     *
     * @param inviteUseid the value for doctors_team_invite.invite_useid
     *
     * @mbggenerated
     */
    public void setInviteUseid(Integer inviteUseid) {
        this.inviteUseid = inviteUseid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invite_name
     *
     * @return the value of doctors_team_invite.invite_name
     *
     * @mbggenerated
     */
    public String getInviteName() {
        return inviteName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invite_name
     *
     * @param inviteName the value for doctors_team_invite.invite_name
     *
     * @mbggenerated
     */
    public void setInviteName(String inviteName) {
        this.inviteName = inviteName == null ? null : inviteName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invite_phone
     *
     * @return the value of doctors_team_invite.invite_phone
     *
     * @mbggenerated
     */
    public String getInvitePhone() {
        return invitePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invite_phone
     *
     * @param invitePhone the value for doctors_team_invite.invite_phone
     *
     * @mbggenerated
     */
    public void setInvitePhone(String invitePhone) {
        this.invitePhone = invitePhone == null ? null : invitePhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invite_login_id
     *
     * @return the value of doctors_team_invite.invite_login_id
     *
     * @mbggenerated
     */
    public String getInviteLoginId() {
        return inviteLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invite_login_id
     *
     * @param inviteLoginId the value for doctors_team_invite.invite_login_id
     *
     * @mbggenerated
     */
    public void setInviteLoginId(String inviteLoginId) {
        this.inviteLoginId = inviteLoginId == null ? null : inviteLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invited_id
     *
     * @return the value of doctors_team_invite.invited_id
     *
     * @mbggenerated
     */
    public Integer getInvitedId() {
        return invitedId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invited_id
     *
     * @param invitedId the value for doctors_team_invite.invited_id
     *
     * @mbggenerated
     */
    public void setInvitedId(Integer invitedId) {
        this.invitedId = invitedId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invited_name
     *
     * @return the value of doctors_team_invite.invited_name
     *
     * @mbggenerated
     */
    public String getInvitedName() {
        return invitedName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invited_name
     *
     * @param invitedName the value for doctors_team_invite.invited_name
     *
     * @mbggenerated
     */
    public void setInvitedName(String invitedName) {
        this.invitedName = invitedName == null ? null : invitedName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invited_phone
     *
     * @return the value of doctors_team_invite.invited_phone
     *
     * @mbggenerated
     */
    public String getInvitedPhone() {
        return invitedPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invited_phone
     *
     * @param invitedPhone the value for doctors_team_invite.invited_phone
     *
     * @mbggenerated
     */
    public void setInvitedPhone(String invitedPhone) {
        this.invitedPhone = invitedPhone == null ? null : invitedPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.invited_login_id
     *
     * @return the value of doctors_team_invite.invited_login_id
     *
     * @mbggenerated
     */
    public String getInvitedLoginId() {
        return invitedLoginId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.invited_login_id
     *
     * @param invitedLoginId the value for doctors_team_invite.invited_login_id
     *
     * @mbggenerated
     */
    public void setInvitedLoginId(String invitedLoginId) {
        this.invitedLoginId = invitedLoginId == null ? null : invitedLoginId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.state
     *
     * @return the value of doctors_team_invite.state
     *
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.state
     *
     * @param state the value for doctors_team_invite.state
     *
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.createtime
     *
     * @return the value of doctors_team_invite.createtime
     *
     * @mbggenerated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.createtime
     *
     * @param createtime the value for doctors_team_invite.createtime
     *
     * @mbggenerated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column doctors_team_invite.updatetime
     *
     * @return the value of doctors_team_invite.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column doctors_team_invite.updatetime
     *
     * @param updatetime the value for doctors_team_invite.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}