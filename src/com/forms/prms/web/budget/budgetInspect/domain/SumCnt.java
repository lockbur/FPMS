package com.forms.prms.web.budget.budgetInspect.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class SumCnt implements Serializable{

	private static final long serialVersionUID = 4613973821257005485L;
	
	private String cntNum;//合同号
	private String dutyName;//费用承担部门
	private String montName;//监控指标
	private String matrName;//物料编码
	private String referenceName;//参考
	private String specialName;//专项
	private String bgtYear;//预算年份  
	private String scId;//
	private BigDecimal bgtFrozen;//冻结预算
	private BigDecimal bgtOverdraw;//透支预算
	private String bgtUsedSum;//总占用预算
	private String bgtUsedThisyear;//
	private String bgtFree;//
	private String bgtWaitFree;//
	private String frozenbgtFree;//
	private String frozenbgtWaitFree;//
	private String bgtId;//
	private String bgtIdNew;//
	private String bgtTemp;//
	private String orgFlag;//机构标识
	private String bgtMatrcode;
	private String orgType;//1 -省行  2-分行 
	
	
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getBgtMatrcode() {
		return bgtMatrcode;
	}
	public void setBgtMatrcode(String bgtMatrcode) {
		this.bgtMatrcode = bgtMatrcode;
	}
	public String getOrgFlag() {
		return orgFlag;
	}
	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getMontName(){
		return this.montName;
	}
	public void setMontName(String montName){
		this.montName = montName;
	}
	public String getMatrName() {
		return matrName;
	}
	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}
	public String getReferenceName() {
		return referenceName;
	}
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	public String getSpecialName() {
		return specialName;
	}
	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}
	public String getBgtYear() {
		return bgtYear;
	}
	public void setBgtYear(String bgtYear) {
		this.bgtYear = bgtYear;
	}
	public String getScId() {
		return scId;
	}
	public void setScId(String scId) {
		this.scId = scId;
	}
	public BigDecimal getBgtFrozen() {
		return bgtFrozen;
	}
	public void setBgtFrozen(BigDecimal bgtFrozen) {
		this.bgtFrozen = bgtFrozen;
	}
	public BigDecimal getBgtOverdraw() {
		return bgtOverdraw;
	}
	public void setBgtOverdraw(BigDecimal bgtOverdraw) {
		this.bgtOverdraw = bgtOverdraw;
	}
	public String getBgtUsedSum() {
		return bgtUsedSum;
	}
	public void setBgtUsedSum(String bgtUsedSum) {
		this.bgtUsedSum = bgtUsedSum;
	}
	public String getBgtUsedThisyear() {
		return bgtUsedThisyear;
	}
	public void setBgtUsedThisyear(String bgtUsedThisyear) {
		this.bgtUsedThisyear = bgtUsedThisyear;
	}
	public String getBgtFree() {
		return bgtFree;
	}
	public void setBgtFree(String bgtFree) {
		this.bgtFree = bgtFree;
	}
	public String getBgtWaitFree() {
		return bgtWaitFree;
	}
	public void setBgtWaitFree(String bgtWaitFree) {
		this.bgtWaitFree = bgtWaitFree;
	}
	public String getFrozenbgtFree() {
		return frozenbgtFree;
	}
	public void setFrozenbgtFree(String frozenbgtFree) {
		this.frozenbgtFree = frozenbgtFree;
	}
	public String getFrozenbgtWaitFree() {
		return frozenbgtWaitFree;
	}
	public void setFrozenbgtWaitFree(String frozenbgtWaitFree) {
		this.frozenbgtWaitFree = frozenbgtWaitFree;
	}
	public String getBgtId() {
		return bgtId;
	}
	public void setBgtId(String bgtId) {
		this.bgtId = bgtId;
	}
	public String getBgtIdNew() {
		return bgtIdNew;
	}
	public void setBgtIdNew(String bgtIdNew) {
		this.bgtIdNew = bgtIdNew;
	}
	public String getBgtTemp() {
		return bgtTemp;
	}
	public void setBgtTemp(String bgtTemp) {
		this.bgtTemp = bgtTemp;
	}
	
}
