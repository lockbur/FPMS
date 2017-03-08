package com.forms.prms.web.amortization.accEntry.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CglTrade implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cglTradeNo;//交易流水号 yyyymmddhh24miss+16序号
	private String cntNum;//合同号
	private String feeYyyymm;//受益年月
	private String dutyCode;//责任中心编号
	private String feeCglCode;//费用核算码
	private String matrCode;//物料编码
	private String montCode;//监控指标
	private String reference;//参考
	private String special;//专项
	private BigDecimal tradeAmt;//交易金额
	private String debitCglCode;//借方核算码
	private String creditCglCode;//贷方核算码
	private String payId;//付款单号
	private String tradeType;//交易类型 0-预提冲销 1-预提 2-待摊 3-付款 4-退款 
	private String tradeDate;//交易日期
	private String tradeTime;//交易时间
	private String debitDataFlag;//借方状态 00-录入 01-失败 02-成功
	private String creditDataFlag;//贷方状态 00-录入 01-失败 02-成功
	
	private String errorReason;//校验错误原因
	
	
	
	
	
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
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
	public String getCglTradeNo() {
		return cglTradeNo;
	}
	public void setCglTradeNo(String cglTradeNo) {
		this.cglTradeNo = cglTradeNo;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getFeeYyyymm() {
		return feeYyyymm;
	}
	public void setFeeYyyymm(String feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getFeeCglCode() {
		return feeCglCode;
	}
	public void setFeeCglCode(String feeCglCode) {
		this.feeCglCode = feeCglCode;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public String getDebitCglCode() {
		return debitCglCode;
	}
	public void setDebitCglCode(String debitCglCode) {
		this.debitCglCode = debitCglCode;
	}
	public String getCreditCglCode() {
		return creditCglCode;
	}
	public void setCreditCglCode(String creditCglCode) {
		this.creditCglCode = creditCglCode;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getDebitDataFlag() {
		return debitDataFlag;
	}
	public void setDebitDataFlag(String debitDataFlag) {
		this.debitDataFlag = debitDataFlag;
	}
	public String getCreditDataFlag() {
		return creditDataFlag;
	}
	public void setCreditDataFlag(String creditDataFlag) {
		this.creditDataFlag = creditDataFlag;
	}
	
	
}
