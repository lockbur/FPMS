package com.forms.prms.web.sysmanagement.orgremanagement.orgrmquery.domain;

import java.io.Serializable;

public class OrgReQueryBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8527944292808080182L;
	private String batchNo;// ++批次号
	private String dutyCode;// ++责任中心
	private String dutyName;//
	private String changeType;// 01-新增责任中心 02-撤并责任中心 03-归属机构变化
	private String changeTypeName;// 01-新增责任中心 02-撤并责任中心 03-归属机构变化
	private String orgCodeBefore;// 所属机构 - 变化前（对已01、02变化前后填为一致）
	private String orgNameBefore;//
	private String orgCodeAfter;// 所属机构 - 变化后
	private String orgNameAfter;//
	private String instDate;//
	private String instDateS;//
	private String instDateE;//
	private String instTime;//
	
	private String orgCode;
	private String orgName;
	private String status;
	private String dutyCodeAfter;
	private String dutyNameAfter;
	private String org1Code;//查询条件一级行代码
	
	private String org1CheckCode;//登陆人所在一级行
	
	private String org2CheckCode;//登陆人所在二级行
	private String org1Name;
	private String org2Code;
	
	private String  org2Name;
	
	private  String ouCode;
	
	private String ouName;
	
	private String isSuperAdmin;
	
	private String flag;
	
	private String isLocked;
	
	
	
	
	
	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrg2CheckCode() {
		return org2CheckCode;
	}

	public void setOrg2CheckCode(String org2CheckCode) {
		this.org2CheckCode = org2CheckCode;
	}

	public String getOrg1CheckCode() {
		return org1CheckCode;
	}

	public void setOrg1CheckCode(String org1CheckCode) {
		this.org1CheckCode = org1CheckCode;
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

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getDutyCodeAfter() {
		return dutyCodeAfter;
	}

	public void setDutyCodeAfter(String dutyCodeAfter) {
		this.dutyCodeAfter = dutyCodeAfter;
	}

	public String getDutyNameAfter() {
		return dutyNameAfter;
	}

	public void setDutyNameAfter(String dutyNameAfter) {
		this.dutyNameAfter = dutyNameAfter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getChangeTypeName() {
		return changeTypeName;
	}

	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public String getInstDateS() {
		return instDateS;
	}

	public void setInstDateS(String instDateS) {
		this.instDateS = instDateS;
	}

	public String getInstDateE() {
		return instDateE;
	}

	public void setInstDateE(String instDateE) {
		this.instDateE = instDateE;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getOrgCodeBefore() {
		return orgCodeBefore;
	}

	public void setOrgCodeBefore(String orgCodeBefore) {
		this.orgCodeBefore = orgCodeBefore;
	}

	public String getOrgNameBefore() {
		return orgNameBefore;
	}

	public void setOrgNameBefore(String orgNameBefore) {
		this.orgNameBefore = orgNameBefore;
	}

	public String getOrgCodeAfter() {
		return orgCodeAfter;
	}

	public void setOrgCodeAfter(String orgCodeAfter) {
		this.orgCodeAfter = orgCodeAfter;
	}

	public String getOrgNameAfter() {
		return orgNameAfter;
	}

	public void setOrgNameAfter(String orgNameAfter) {
		this.orgNameAfter = orgNameAfter;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getInstTime() {
		return instTime;
	}

	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}

}
