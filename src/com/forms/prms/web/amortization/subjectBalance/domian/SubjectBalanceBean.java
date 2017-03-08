package com.forms.prms.web.amortization.subjectBalance.domian;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubjectBalanceBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tradeDate;//交易日期
	private String befDate;//查询起始时间
	private String aftDate;//查询结束时间
	private String batchNo;//批次号
	private String seqNo;//序号
	private String org1Code;//所属一级行代码
	private String org1Name;//所属一级行名称
	private String ouName;//财务中心名称
	private String orgCode;//机构代码
	private String orgName;//机构名称
	private String dutyCode;//责任中心代码
	private String dutyName;//责任中心名称
	private String matrCode;// 物料编码
	private String montCode;//监控指标
	private String cglCode;//核算码
	private String cglName;//核算码名称
	private String referenceId;//参考段
	private String referenceName;//参考段名称
	private String specialId;//专项段
	private String specialName;//专项段名称
	private String prodId;//产品段
	private String prodName;//产品段名称
	private String compId;//公司间段
	private String compName;//公司间段名称
	private String rsv1Code;//备用段1
	private String rsv1Name;//备用段1名称
	private String rsv2Code;//备用段2
	private String rsv2Name;//备用段2名称
	private String currCode;//币种
	private BigDecimal drAmt;//借方余额
	private BigDecimal crAmt;//贷方余额
	
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getBefDate() {
		return befDate;
	}
	public void setBefDate(String befDate) {
		this.befDate = befDate;
	}
	public String getAftDate() {
		return aftDate;
	}
	public void setAftDate(String aftDate) {
		this.aftDate = aftDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
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
	public String getOuName() {
		return ouName;
	}
	public void setOuName(String ouName) {
		this.ouName = ouName;
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
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getCglCode() {
		return cglCode;
	}
	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}
	public String getCglName() {
		return cglName;
	}
	public void setCglName(String cglName) {
		this.cglName = cglName;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getReferenceName() {
		return referenceName;
	}
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getSpecialName() {
		return specialName;
	}
	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getRsv1Code() {
		return rsv1Code;
	}
	public void setRsv1Code(String rsv1Code) {
		this.rsv1Code = rsv1Code;
	}
	public String getRsv1Name() {
		return rsv1Name;
	}
	public void setRsv1Name(String rsv1Name) {
		this.rsv1Name = rsv1Name;
	}
	public String getRsv2Code() {
		return rsv2Code;
	}
	public void setRsv2Code(String rsv2Code) {
		this.rsv2Code = rsv2Code;
	}
	public String getRsv2Name() {
		return rsv2Name;
	}
	public void setRsv2Name(String rsv2Name) {
		this.rsv2Name = rsv2Name;
	}
	public String getCurrCode() {
		return currCode;
	}
	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}
	public BigDecimal getDrAmt() {
		return drAmt;
	}
	public void setDrAmt(BigDecimal drAmt) {
		this.drAmt = drAmt;
	}
	public BigDecimal getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(BigDecimal crAmt) {
		this.crAmt = crAmt;
	}	 
}
