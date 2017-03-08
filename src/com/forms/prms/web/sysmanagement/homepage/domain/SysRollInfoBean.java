package com.forms.prms.web.sysmanagement.homepage.domain;

import java.io.Serializable;

public class SysRollInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rollTitle;

	private String rollInfo;
	
	private String addTime;
	
	private String rollId;
	
	private String dutyCode;  //责任中心
	
	private String org1Code;  //一级行
	
	private String org2Code;  //二级行
	
	
	
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	
	public String getRollId() {
		return rollId;
	}

	public void setRollId(String rollId) {
		this.rollId = rollId;
	}

	public String getRollTitle() {
		return rollTitle;
	}

	public void setRollTitle(String rollTitle) {
		this.rollTitle = rollTitle;
	}

	public String getRollInfo() {
		return rollInfo;
	}

	public void setRollInfo(String rollInfo) {
		this.rollInfo = rollInfo;
	}


}
