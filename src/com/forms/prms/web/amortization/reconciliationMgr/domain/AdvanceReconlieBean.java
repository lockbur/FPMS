package com.forms.prms.web.amortization.reconciliationMgr.domain;

/**
 * 预付款核销Bean
 * Copyright: formssi
 * @author HQQ
 * @project ERP
 * @date 2015-05-09
 * @version 1.0
 */
public class AdvanceReconlieBean {

	private String	cntNum;				//合同编号
	private String	payId;				//订单号
	private String	invoiceRowNo;		//发票行序号
	private String	sendValue;			//ERP发送值
	private String	sendTime;			//ERP发送时间
	private String	checkValue;			//检查文件返回值
	private String	checkResult;		//检查文件返回结果
	private String	checkTime;			//检查文件处理时间
	private String	invoiceValue;		//AP发票返回值
	private String	invoiceResult;		//AP发票返回结果
	private String	invoiceTime;		//AP发票ERP处理时间
	private String	invoiceState;		//发票创建结果
	private String	org1Code;			//一级行行号
	
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
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
	public String getInvoiceRowNo() {
		return invoiceRowNo;
	}
	public void setInvoiceRowNo(String invoiceRowNo) {
		this.invoiceRowNo = invoiceRowNo;
	}
	public String getSendValue() {
		return sendValue;
	}
	public void setSendValue(String sendValue) {
		this.sendValue = sendValue;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
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
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
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
	public String getInvoiceTime() {
		return invoiceTime;
	}
	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	public String getInvoiceState() {
		return invoiceState;
	}
	public void setInvoiceState(String invoiceState) {
		this.invoiceState = invoiceState;
	}
	
}
