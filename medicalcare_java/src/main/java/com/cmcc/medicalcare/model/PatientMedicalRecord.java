package com.cmcc.medicalcare.model;

import java.util.Date;

public class PatientMedicalRecord {
	private Integer id;
	private Integer patinetId;
	private String patinetName;
	private Date diagnoseDate;
	private String diagnoseContent;
	private String diagnoseResult;
	private String inspectionReportUrl;
	private String mechanism;
	private Integer state;
	private String note;
	private Integer secretaryId;
	private String secretaryName;
	private Integer launchType;
	private Date createtime;
	private Date updatetime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPatinetId() {
		return patinetId;
	}
	public void setPatinetId(Integer patinetId) {
		this.patinetId = patinetId;
	}
	public String getPatinetName() {
		return patinetName;
	}
	public void setPatinetName(String patinetName) {
		this.patinetName = patinetName;
	}
	public Date getDiagnoseDate() {
		return diagnoseDate;
	}
	public void setDiagnoseDate(Date diagnoseDate) {
		this.diagnoseDate = diagnoseDate;
	}
	public String getDiagnoseContent() {
		return diagnoseContent;
	}
	public void setDiagnoseContent(String diagnoseContent) {
		this.diagnoseContent = diagnoseContent;
	}
	public String getDiagnoseResult() {
		return diagnoseResult;
	}
	public void setDiagnoseResult(String diagnoseResult) {
		this.diagnoseResult = diagnoseResult;
	}
	public String getInspectionReportUrl() {
		return inspectionReportUrl;
	}
	public void setInspectionReportUrl(String inspectionReportUrl) {
		this.inspectionReportUrl = inspectionReportUrl;
	}
	public String getMechanism() {
		return mechanism;
	}
	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getSecretaryId() {
		return secretaryId;
	}
	public void setSecretaryId(Integer secretaryId) {
		this.secretaryId = secretaryId;
	}
	public String getSecretaryName() {
		return secretaryName;
	}
	public void setSecretaryName(String secretaryName) {
		this.secretaryName = secretaryName;
	}
	public Integer getLaunchType() {
		return launchType;
	}
	public void setLaunchType(Integer launchType) {
		this.launchType = launchType;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
}