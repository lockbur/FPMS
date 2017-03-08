package com.forms.prms.web.sysmanagement.provider.domain;

import java.io.Serializable;

public class ProviderActBean implements Serializable {
	private static final long serialVersionUID = -1215768648604303920L;

	private String providerCode;// 供应商编号

	private String actNo;// 银行账户编号

	private String actCurr;// 银行账户币种 RMB

	private String bankName;// 银行名称

	private String branchName;// 分行名称

	private String actName;// 银行账户名称

	private String isMasterAct;// 是否主要帐号 Y/N

	private String actType;// 账户类型 值列表

	private String actTypeText;// 账户类型文本值

	private String bankInfo;// 开户行详细信息

	private String bankCode;// 开户行行号

	private String bankArea;// 银行地区码

	

	public String getActTypeText() {
		return actTypeText;
	}

	public void setActTypeText(String actTypeText) {
		this.actTypeText = actTypeText;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActCurr() {
		return actCurr;
	}

	public void setActCurr(String actCurr) {
		this.actCurr = actCurr;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getIsMasterAct() {
		return isMasterAct;
	}

	public void setIsMasterAct(String isMasterAct) {
		this.isMasterAct = isMasterAct;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
