package com.forms.prms.web.budget.budgetResolve.resolve.domain;

public class BudgetResolveBean {
	
	private String tmpltId;		//预算模板ID
	private String montCode;	//监控指标编号
	private String dutyCode;	//责任中心编号
	private String dutyName;	//责任中心名称
	private String matrCode;	//物料编号
	private String matrName;	//物料名称
	private String sumAmt;		//总分配金额
	private String usedAmt;		//占用金额
	private String freezeAmt;	//冻结金额
	private String surplusAmt;	//剩余金额
	private String splitedAmt;	//已分解金额
	
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
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getMatrName() {
		return matrName;
	}
	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}
	public String getSplitedAmt() {
		return splitedAmt;
	}
	public void setSplitedAmt(String splitedAmt) {
		this.splitedAmt = splitedAmt;
	}
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getSumAmt() {
		return sumAmt;
	}
	public void setSumAmt(String sumAmt) {
		this.sumAmt = sumAmt;
	}
	public String getUsedAmt() {
		return usedAmt;
	}
	public void setUsedAmt(String usedAmt) {
		this.usedAmt = usedAmt;
	}
	public String getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public String getSurplusAmt() {
		return surplusAmt;
	}
	public void setSurplusAmt(String surplusAmt) {
		this.surplusAmt = surplusAmt;
	}
	
}
