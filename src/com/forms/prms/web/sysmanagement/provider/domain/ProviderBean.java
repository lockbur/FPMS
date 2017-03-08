package com.forms.prms.web.sysmanagement.provider.domain;

import java.io.Serializable;
import java.util.List;

public class ProviderBean implements Serializable {
	private static final long serialVersionUID = -1215768648604303920L;

	private String providerCode;// 供应商编号

	private String providerCodePop;// 弹出框编号

	private String providerName;// 供应商名称

	private String providerType;// 供应商类型 01-EMPLOYEE 02-VENDOR 03-IAP

	private String actTypeText;

	private String providerTypePop;// 弹出框类型

	private String providerAddr;// 供应商地点

	private String payCondition;// 付款条件 ?? 值列表，待FMS提供。

	private String payMode;// 支付方式 ?? 值列表，待FMS提供。

	private String ouCode;// 所属财务中心代码

	private String ouName;// 所属财务中心名称

	private String actNo;// 银行账户编号

	private String actCurr;// 银行账户币种 RMB

	private String bankName;// 银行名称

	private String branchName;// 分行名称

	private String actName;// 银行账户名称

	private String isMasterAct;// 是否主要帐号 Y/N

	private String actType;// 账户类型 值列表

	private String bankInfo;// 开户行详细信息

	private String bankCode;// 开户行行号

	private String bankArea;// 银行地区码 值列表

	private List<ProviderActBean> providerActList;// 银行号集合

	private String providerTypeName;// 供应商类型名称

	private String compare;// 弹出层选择区分

	private String isAdmin; // 是否超级管理员 1-是 0-否

	private String org1Code;

	private String providerAddrCode;// 供应商地点编号

	public String getProviderAddrCode() {
		return providerAddrCode;
	}

	public void setProviderAddrCode(String providerAddrCode) {
		this.providerAddrCode = providerAddrCode;
	}

	public String getActTypeText() {
		return actTypeText;
	}

	public void setActTypeText(String actTypeText) {
		this.actTypeText = actTypeText;
	}

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

	public String getProviderTypeName() {
		return providerTypeName;
	}

	public void setProviderTypeName(String providerTypeName) {
		this.providerTypeName = providerTypeName;
	}

	private String taxRate;// 供应商税率

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	private String[] delIds;//

	private List<String> delIdList;

	private String actNoPop;// 弹出框银行账号

	private String actTypePop;// 弹出框账户类型

	private String bankNamePop;// 弹出框开户银行

	private String bankCodePop;// 弹出框开户行行号

	private String actCurrPop;// 弹出框币种

	private String flag;// 供应商类型判断

	private String instOper;// 最后操作者ID

	private String instDate;// 最后操作日期

	private String instTime;// 最后操作时间

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
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

	public String getProviderCodePop() {
		return providerCodePop;
	}

	public void setProviderCodePop(String providerCodePop) {
		this.providerCodePop = providerCodePop;
	}

	public String getProviderTypePop() {
		return providerTypePop;
	}

	public void setProviderTypePop(String providerTypePop) {
		this.providerTypePop = providerTypePop;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getActCurrPop() {
		return actCurrPop;
	}

	public void setActCurrPop(String actCurrPop) {
		this.actCurrPop = actCurrPop;
	}

	public String getActNoPop() {
		return actNoPop;
	}

	public void setActNoPop(String actNoPop) {
		this.actNoPop = actNoPop;
	}

	public String getActTypePop() {
		return actTypePop;
	}

	public void setActTypePop(String actTypePop) {
		this.actTypePop = actTypePop;
	}

	public String getBankNamePop() {
		return bankNamePop;
	}

	public void setBankNamePop(String bankNamePop) {
		this.bankNamePop = bankNamePop;
	}

	public String getBankCodePop() {
		return bankCodePop;
	}

	public void setBankCodePop(String bankCodePop) {
		this.bankCodePop = bankCodePop;
	}

	public String[] getDelIds() {
		return delIds;
	}

	public void setDelIds(String[] delIds) {
		this.delIds = delIds;
	}

	public List<String> getDelIdList() {
		return delIdList;
	}

	public void setDelIdList(List<String> delIdList) {
		this.delIdList = delIdList;
	}

	public List<ProviderActBean> getProviderActList() {
		return providerActList;
	}

	public void setProviderActList(List<ProviderActBean> providerActList) {
		this.providerActList = providerActList;
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getProviderAddr() {
		return providerAddr;
	}

	public void setProviderAddr(String providerAddr) {
		this.providerAddr = providerAddr;
	}

	public String getPayCondition() {
		return payCondition;
	}

	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
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

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

}
