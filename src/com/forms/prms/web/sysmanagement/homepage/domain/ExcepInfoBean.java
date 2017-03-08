package com.forms.prms.web.sysmanagement.homepage.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ExcepInfoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String org1Code;
	
	private String excepId;
	
	private String excepTitle;
	
	private String excepInfo;
	
	private String excepOrgId;
	
	private String addUid;
	
	private String addTime;
	
	private String excepType;
	
	private String[] excepInfoChecked;
	
	List<Map<String, String>> excepInfoList;
	
	
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String[] getExcepInfoChecked() {
		return excepInfoChecked;
	}
	public void setExcepInfoChecked(String[] excepInfoChecked) {
		this.excepInfoChecked = excepInfoChecked;
	}
	public List<Map<String, String>> getExcepInfoList() {
		return excepInfoList;
	}
	public void setExcepInfoList(List<Map<String, String>> excepInfoList) {
		this.excepInfoList = excepInfoList;
	}
	public String getExcepType() {
		return excepType;
	}
	public void setExcepType(String excepType) {
		this.excepType = excepType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getExcepId() {
		return excepId;
	}
	public void setExcepId(String excepId) {
		this.excepId = excepId;
	}
	public String getExcepTitle() {
		return excepTitle;
	}
	public void setExcepTitle(String excepTitle) {
		this.excepTitle = excepTitle;
	}
	public String getExcepInfo() {
		return excepInfo;
	}
	public void setExcepInfo(String excepInfo) {
		this.excepInfo = excepInfo;
	}
	public String getExcepOrgId() {
		return excepOrgId;
	}
	public void setExcepOrgId(String excepOrgId) {
		this.excepOrgId = excepOrgId;
	}
	public String getAddUid() {
		return addUid;
	}
	public void setAddUid(String addUid) {
		this.addUid = addUid;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}