package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

//Cnt合同关联的Pay付款对象(包含：正常付款+预付款)
public class CntRelPayInfo {
	
	private String payId;			//付款单ID
	private String payType;			//付款类型(01:正常付款、02:预付款)
	private String providerName;	//供应商名称
	private String invoiceCode;		//发票编号
	private String invoiceAmt;		//发票金额
	private BigDecimal payAmt;		//付款金额
	private String status;			//付款单状态
	private String batchNo;			//当次导入批次号
	
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
}
