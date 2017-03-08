package com.forms.prms.web.budget.budgetdeclare.domain;

import java.io.Serializable;

public class StockBudgetBean implements Serializable {

	private static final long serialVersionUID = -4393073275694052920L;
	
	private String projId;
	private String projName;
	private String budgetAmt;
	private String cntNum;
	private String cntAmt;
	private String dutyName;
	private String matrName;
	private String execAmt;
	private String amt;
	private String dutyCode;
	private String org1Code;
	private String matrCode;
	private String sumConfirmedAmt;
	private String confirmState;
	private String needConfirmAmt;
	private String unionPrimaryKey;
	private String confirmOper;
	private String hasTemplate;
	public String getConfirmOper() {
		return confirmOper;
	}
	public void setConfirmOper(String confirmOper) {
		this.confirmOper = confirmOper;
	}
	public String getHasTemplate() {
		return hasTemplate;
	}
	public void setHasTemplate(String hasTemplate) {
		this.hasTemplate = hasTemplate;
	}
	public String getUnionPrimaryKey() {
		return unionPrimaryKey;
	}
	public void setUnionPrimaryKey(String unionPrimaryKey) {
		this.unionPrimaryKey = unionPrimaryKey;
	}
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getSumConfirmedAmt() {
		return sumConfirmedAmt;
	}
	public void setSumConfirmedAmt(String sumConfirmedAmt) {
		this.sumConfirmedAmt = sumConfirmedAmt;
	}
	public String getConfirmState() {
		return confirmState;
	}
	public void setConfirmState(String confirmState) {
		this.confirmState = confirmState;
	}
	public String getNeedConfirmAmt() {
		return needConfirmAmt;
	}
	public void setNeedConfirmAmt(String needConfirmAmt) {
		this.needConfirmAmt = needConfirmAmt;
	}
	public String getProjId() {
		return projId;
	}
	public String getExecAmt() {
		return execAmt;
	}
	public void setExecAmt(String execAmt) {
		this.execAmt = execAmt;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getBudgetAmt() {
		return budgetAmt;
	}
	public void setBudgetAmt(String budgetAmt) {
		this.budgetAmt = budgetAmt;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getCntAmt() {
		return cntAmt;
	}
	public void setCntAmt(String cntAmt) {
		this.cntAmt = cntAmt;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getMatrName() {
		return matrName;
	}
	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
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

}
