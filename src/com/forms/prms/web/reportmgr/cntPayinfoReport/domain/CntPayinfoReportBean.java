package com.forms.prms.web.reportmgr.cntPayinfoReport.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CntPayinfoReportBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2024788691006796619L;
	
	private String org1Code;//一级分行
	private String org1Name;//一级分行名
	private String org2Code;//二级分行
	private String org2Name;//二级分行名
	private String feeDept;//费用承担部门
	private String cntNum;//合同编号
	private String cntNumRelated;//关联的合同编号
	private String cntName;//合同事项
	private String projName;//项目名称
	private String providerCode;//供应商编码
	private String providerName;//供应商名
	private String feeStartDate;//合同受益起始日期
	private String feeEndDate;//合同受益终止日期
	private String cglCode;//核算码
	private BigDecimal cntAllAmt;//合同总金额
	private BigDecimal countAllPayAmt;//累计已付金额
	private String countAllInAmt;//累计已列账金额
	private String countAllOutAmt;//累计尚未摊销的待摊金额
	private BigDecimal yearAllPayAmt;//当年累计已付金额
	private String yearAllInAmt;//当年累计已列账金额
	private String yearAllOutAmt;//当年累计尚未摊销的待摊金额
	private BigDecimal yearAllFee;//当年累计受益金额
	
	
	private String orgFlag;
	private String cntType;
	private String feeType;
	private String matrType;
	private String createDept;
	private String payDutyCode;
	
	private String payType;
	private String notaxAllPayAmt;
	
	
	
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
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getCntNumRelated() {
		return cntNumRelated;
	}
	public void setCntNumRelated(String cntNumRelated) {
		this.cntNumRelated = cntNumRelated;
	}
	public String getCntName() {
		return cntName;
	}
	public void setCntName(String cntName) {
		this.cntName = cntName;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
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
	public String getFeeStartDate() {
		return feeStartDate;
	}
	public void setFeeStartDate(String feeStartDate) {
		this.feeStartDate = feeStartDate;
	}
	public String getFeeEndDate() {
		return feeEndDate;
	}
	public void setFeeEndDate(String feeEndDate) {
		this.feeEndDate = feeEndDate;
	}
	public String getCglCode() {
		return cglCode;
	}
	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}
	public BigDecimal getCntAllAmt() {
		return cntAllAmt;
	}
	public void setCntAllAmt(BigDecimal cntAllAmt) {
		this.cntAllAmt = cntAllAmt;
	}
	public BigDecimal getCountAllPayAmt() {
		return countAllPayAmt;
	}
	public void setCountAllPayAmt(BigDecimal countAllPayAmt) {
		this.countAllPayAmt = countAllPayAmt;
	}
	
	public BigDecimal getYearAllPayAmt() {
		return yearAllPayAmt;
	}
	public void setYearAllPayAmt(BigDecimal yearAllPayAmt) {
		this.yearAllPayAmt = yearAllPayAmt;
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
	public String getCntType() {
		return cntType;
	}
	public void setCntType(String cntType) {
		this.cntType = cntType;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getMatrType() {
		return matrType;
	}
	public void setMatrType(String matrType) {
		this.matrType = matrType;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public String getPayDutyCode() {
		return payDutyCode;
	}
	public void setPayDutyCode(String payDutyCode) {
		this.payDutyCode = payDutyCode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCountAllInAmt() {
		return countAllInAmt;
	}
	public void setCountAllInAmt(String countAllInAmt) {
		this.countAllInAmt = countAllInAmt;
	}
	public String getCountAllOutAmt() {
		return countAllOutAmt;
	}
	public void setCountAllOutAmt(String countAllOutAmt) {
		this.countAllOutAmt = countAllOutAmt;
	}
	public String getYearAllInAmt() {
		return yearAllInAmt;
	}
	public void setYearAllInAmt(String yearAllInAmt) {
		this.yearAllInAmt = yearAllInAmt;
	}
	public String getYearAllOutAmt() {
		return yearAllOutAmt;
	}
	public void setYearAllOutAmt(String yearAllOutAmt) {
		this.yearAllOutAmt = yearAllOutAmt;
	}
	public String getNotaxAllPayAmt() {
		return notaxAllPayAmt;
	}
	public void setNotaxAllPayAmt(String notaxAllPayAmt) {
		this.notaxAllPayAmt = notaxAllPayAmt;
	}
	
	
	
	
	
}
