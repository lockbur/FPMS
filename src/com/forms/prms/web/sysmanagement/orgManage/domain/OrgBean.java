package com.forms.prms.web.sysmanagement.orgManage.domain;

import java.io.Serializable;

public class OrgBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -785577104617699846L;
	private String dutyCode;// 责任中心
	private String dutyName;// 责任中心名称
	private String ouCode;// OU
	private String ouName;// OU名称
	private String orgCode;// 机构号
	private String orgName;// 机构名称
	private String org2Code;// 二级分行代码 二级行可能为空。
	private String org2Name;// 二级分行名称 二级行可能为空。
	private String org1Code;// 一级分行代码
	private String org1Name;// 一级分行名称
	private String defOrgDuty;// 默认机构 责任中心
	private String defDelivery;// 默认收单方 收货方
	private String stackOrgCode;
	private String stackOrgName;// 库存组织名称
	private String scanPosition;
	private String scanPositionName;
	private String org2CodeOri;
	private String org2NameOri;
	private String icms;

	private String org21Code;

	private String isLocked;
	private String[] dutyCodes;
	
	private String userId;
	
	
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getDutyCodes() {
		return dutyCodes;
	}

	public void setDutyCodes(String[] dutyCodes) {
		this.dutyCodes = dutyCodes;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	private int isSuper;

	public String getOrg21Code() {
		return org21Code;
	}

	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

	public int getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(int isSuper) {
		this.isSuper = isSuper;
	}

	public String getIcms() {
		return icms;
	}

	public void setIcms(String icms) {
		this.icms = icms;
	}

	public String getScanPositionName() {
		return scanPositionName;
	}

	public void setScanPositionName(String scanPositionName) {
		this.scanPositionName = scanPositionName;
	}

	public String getStackOrgCode() {
		return stackOrgCode;
	}

	public void setStackOrgCode(String stackOrgCode) {
		this.stackOrgCode = stackOrgCode;
	}

	public String getOrg2CodeOri() {
		return org2CodeOri;
	}

	public void setOrg2CodeOri(String org2CodeOri) {
		this.org2CodeOri = org2CodeOri;
	}

	public String getOrg2NameOri() {
		return org2NameOri;
	}

	public void setOrg2NameOri(String org2NameOri) {
		this.org2NameOri = org2NameOri;
	}

	public String getScanPosition() {
		return scanPosition;
	}

	public void setScanPosition(String scanPosition) {
		this.scanPosition = scanPosition;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getOuCode() {
		return ouCode;
	}

	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getOrg2Name() {
		return org2Name;
	}

	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getOrg1Name() {
		return org1Name;
	}

	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}

	public String getDefOrgDuty() {
		return defOrgDuty;
	}

	public void setDefOrgDuty(String defOrgDuty) {
		this.defOrgDuty = defOrgDuty;
	}

	public String getDefDelivery() {
		return defDelivery;
	}

	public void setDefDelivery(String defDelivery) {
		this.defDelivery = defDelivery;
	}

	public String getStackOrgName() {
		return stackOrgName;
	}

	public void setStackOrgName(String stackOrgName) {
		this.stackOrgName = stackOrgName;
	}

}
