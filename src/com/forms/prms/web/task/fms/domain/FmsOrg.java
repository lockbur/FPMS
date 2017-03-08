package com.forms.prms.web.task.fms.domain;

import java.io.Serializable;

public class FmsOrg implements Serializable {

	/**
	 * 从FMS到入的机构及机构层级表
	 */
	private static final long serialVersionUID = 152025932452888025L;
	private String org1Code;
	private String org1Name;
	private String org2Code;
	private String org2Name;
	private String orgCode;
	private String orgName;
	private String dutyCode;
	private String dutyName;
	private String ouCode;
	private String ouName;
	private String defOrgDuty;
	private String defDelivery;
	private String stockOrgName;
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
	public String getStockOrgName() {
		return stockOrgName;
	}
	public void setStockOrgName(String stockOrgName) {
		this.stockOrgName = stockOrgName;
	}
	
	
	

}
