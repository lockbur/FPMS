package com.forms.prms.web.reportmgr.preproInfoReport.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreproInfoReportBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String org1Code;//一级分行
	private String org1Name;//一级分行名
	private String org2Code;//二级分行
	private String org2Name;//二级分行名
	private String feeDept;//费用承担部门
	private String cglCode;//核算码
	private String matrCode;//物料编码
	private String matrName;//物料名称
	private String cntNum;//合同编号
	private String cntName;//合同事项
	private BigDecimal countAllPayAmt;//累计已付金额
	private BigDecimal countAllInAmt;//累计已列账金额
	private BigDecimal countAllOutAmt;//累计尚未摊销的待摊金额
	private BigDecimal yearAllPayAmt;//当年累计已付金额
	private BigDecimal yearAllInAmt;//当年累计已列账金额
	private BigDecimal yearAllOutAmt;//当年累计尚未摊销的待摊金额
	private BigDecimal yearAllFee;//当年累计受益金额
	
	private String feeYear;
	
	private String orgFlag;
	private String payType;
	private BigDecimal notaxAllPayAmt;
	
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
	public String getFeeDept() {
		return feeDept;
	}
	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}
	public String getCglCode() {
		return cglCode;
	}
	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
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
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getCntName() {
		return cntName;
	}
	public void setCntName(String cntName) {
		this.cntName = cntName;
	}
	public BigDecimal getCountAllPayAmt() {
		return countAllPayAmt;
	}
	public void setCountAllPayAmt(BigDecimal countAllPayAmt) {
		this.countAllPayAmt = countAllPayAmt;
	}
	public BigDecimal getCountAllInAmt() {
		return countAllInAmt;
	}
	public void setCountAllInAmt(BigDecimal countAllInAmt) {
		this.countAllInAmt = countAllInAmt;
	}
	public BigDecimal getCountAllOutAmt() {
		return countAllOutAmt;
	}
	public void setCountAllOutAmt(BigDecimal countAllOutAmt) {
		this.countAllOutAmt = countAllOutAmt;
	}
	public BigDecimal getYearAllPayAmt() {
		return yearAllPayAmt;
	}
	public void setYearAllPayAmt(BigDecimal yearAllPayAmt) {
		this.yearAllPayAmt = yearAllPayAmt;
	}
	public BigDecimal getYearAllInAmt() {
		return yearAllInAmt;
	}
	public void setYearAllInAmt(BigDecimal yearAllInAmt) {
		this.yearAllInAmt = yearAllInAmt;
	}
	public BigDecimal getYearAllOutAmt() {
		return yearAllOutAmt;
	}
	public void setYearAllOutAmt(BigDecimal yearAllOutAmt) {
		this.yearAllOutAmt = yearAllOutAmt;
	}
	public BigDecimal getYearAllFee() {
		return yearAllFee;
	}
	public void setYearAllFee(BigDecimal yearAllFee) {
		this.yearAllFee = yearAllFee;
	}
	public String getOrgFlag() {
		return orgFlag;
	}
	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}
	public String getFeeYear() {
		return feeYear;
	}
	public void setFeeYear(String feeYear) {
		this.feeYear = feeYear;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public BigDecimal getNotaxAllPayAmt() {
		return notaxAllPayAmt;
	}
	public void setNotaxAllPayAmt(BigDecimal notaxAllPayAmt) {
		this.notaxAllPayAmt = notaxAllPayAmt;
	}
	

	
}
