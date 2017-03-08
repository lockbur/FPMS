package com.forms.prms.web.projmanagement.projectMgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String projId;// 项目编号 PRO+一级分行机构号+yyyymmd+四位序号
	private String projType;// 项目类型
	private String projTypeName;// 项目类型
	private String projName;// 项目名称
	private String projDesc;// 项目描述
	private String scope;// 安全性 0全局 1责任中心
	private BigDecimal budgetAmt;// 预算金额
	private BigDecimal cntTotalAmt;// 合同已执行金额 合同确认时增加，合同变更时根据合同是否变化进行修改
	private BigDecimal payTotalAmt;// 已付款金额 ????
	private BigDecimal freezeTotalAmt;// 冻结金额
	private String startDate;// 起始日期
	private String endDate = "9999-12-31";// 结束日期
	private String currency = "CNY";// 币别 默认为CNY
	private String instDutyCode;// 创建责任中心
	private String org1Code;// 所属一级行机构号 或者二级行

	private String startYear;// 开始年份

	private String userId;// 登陆用户ID
	private String uDutyCode;// 登陆用户所属责任中心编码
	private String uOrg1Code;// 登陆用户所属一级分行编码
	private String uOrg2Code; //登陆用户所属二级分行编码
	
	private String scopeName;
	// project_log
	private BigDecimal versionNo;// 版本号
	private String operType;// 操作类型 新增 删除 修改
	private String operMemo;// 操作说明 操作类型为“变更”时填写具体变更内容。
	private String operUser;// 操作人员
	private String operDutyCode;// 操作责任中心
	private String operDate;// 操作日期 yyyy-mm-dd
	private String operTime;// 操作时间 hh24:mi:ss

	// project_dept
	private String dutyCode;// 责任中心编号
	private String[] dutyCodeList;// 责任中心编号列表

	private String[] projIdList;

	private String endMemo;// 终止意见

	private String signDate;// 签订日期
	
	private String orgFlag;
	
	private  String isContract;
	
	

	
	
	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public String getIsContract() {
		return isContract;
	}

	public void setIsContract(String isContract) {
		this.isContract = isContract;
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getuOrg2Code() {
		return uOrg2Code;
	}

	public void setuOrg2Code(String uOrg2Code) {
		this.uOrg2Code = uOrg2Code;
	}

	public String getProjTypeName() {
		return projTypeName;
	}

	public void setProjTypeName(String projTypeName) {
		this.projTypeName = projTypeName;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	// 是否超级管理员
	private String isSuper;

	public String[] getProjIdList() {
		return projIdList;
	}

	public void setProjIdList(String[] projIdList) {
		this.projIdList = projIdList;
	}

	public String getEndMemo() {
		return endMemo;
	}

	public void setEndMemo(String endMemo) {
		this.endMemo = endMemo;
	}

	public String getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(String isSuper) {
		this.isSuper = isSuper;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getProjType() {
		return projType;
	}

	public void setProjType(String projType) {
		this.projType = projType;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjDesc() {
		return projDesc;
	}

	public void setProjDesc(String projDesc) {
		this.projDesc = projDesc;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public BigDecimal getBudgetAmt() {
		return budgetAmt;
	}

	public void setBudgetAmt(BigDecimal budgetAmt) {
		this.budgetAmt = budgetAmt;
	}

	public BigDecimal getCntTotalAmt() {
		return cntTotalAmt;
	}

	public void setCntTotalAmt(BigDecimal cntTotalAmt) {
		this.cntTotalAmt = cntTotalAmt;
	}

	public BigDecimal getPayTotalAmt() {
		return payTotalAmt;
	}

	public void setPayTotalAmt(BigDecimal payTotalAmt) {
		this.payTotalAmt = payTotalAmt;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getInstDutyCode() {
		return instDutyCode;
	}

	public void setInstDutyCode(String instDutyCode) {
		this.instDutyCode = instDutyCode;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getuDutyCode() {
		return uDutyCode;
	}

	public void setuDutyCode(String uDutyCode) {
		this.uDutyCode = uDutyCode;
	}

	public String getuOrg1Code() {
		return uOrg1Code;
	}

	public void setuOrg1Code(String uOrg1Code) {
		this.uOrg1Code = uOrg1Code;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getOperDutyCode() {
		return operDutyCode;
	}

	public void setOperDutyCode(String operDutyCode) {
		this.operDutyCode = operDutyCode;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String[] getDutyCodeList() {
		return dutyCodeList;
	}

	public void setDutyCodeList(String[] dutyCodeList) {
		this.dutyCodeList = dutyCodeList;
	}

	public BigDecimal getFreezeTotalAmt() {
		return freezeTotalAmt;
	}

	public void setFreezeTotalAmt(BigDecimal freezeTotalAmt) {
		this.freezeTotalAmt = freezeTotalAmt;
	}

}
