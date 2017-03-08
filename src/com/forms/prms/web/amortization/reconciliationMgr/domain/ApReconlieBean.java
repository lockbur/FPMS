package com.forms.prms.web.amortization.reconciliationMgr.domain;

/**
 *	AP发票付款对账Bean 
 */
public class ApReconlieBean {
	
	private String cntNum;				//合同号
	private String payId;				//付款单号
	private String sendValue;			//ERP发送数据
	private String uploadDate;			//ERP发送时间
	private String checkValue;			//校验文件返回值
	private String checkResult;			//校验文件返回结果
	private String checkDownDate;		//校验文件ERP处理时间
	private String invoiceValue;		//AP发票信息返回值
	private String invoiceResult;		//AP发票返回结果
	private String invDate;				//AP发票返回时间
	private String invoiceState;		//AP发票创建结果
	private String payDate;				//AP付款处理时间
	private String payState;			//AP付款结果
	private String org1Code;            //一级行行号
	
	private String apResult;		
	
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getApResult() {
		return apResult;
	}
	public void setApResult(String apResult) {
		this.apResult = apResult;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getSendValue() {
		return sendValue;
	}
	public void setSendValue(String sendValue) {
		this.sendValue = sendValue;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckDownDate() {
		return checkDownDate;
	}
	public void setCheckDownDate(String checkDownDate) {
		this.checkDownDate = checkDownDate;
	}
	public String getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(String invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public String getInvoiceResult() {
		return invoiceResult;
	}
	public void setInvoiceResult(String invoiceResult) {
		this.invoiceResult = invoiceResult;
	}
	public String getInvDate() {
		return invDate;
	}
	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}
	public String getInvoiceState() {
		return invoiceState;
	}
	public void setInvoiceState(String invoiceState) {
		this.invoiceState = invoiceState;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	
	
}
