package com.forms.prms.web.amortization.reconciliationMgr.domain;

/**
 * PO采购订单Bean
 * Copyright: formssi
 * @author HQQ
 * @project ERP
 * @date 2015-05-09
 * @version 1.0
 */
public class PoOrderReconlieBean {
	
	private String	cntNum;				//合同编号
	private String	orderId;				//订单号
	private String	rowSeqNo;			//订单行号
	private String	sendValue;			//ERP发送值
	private String	uploadDate;			//ERP发送时间
	private String	checkValue;			//检查文件返回值
	private String	checkResult;		//检查文件返回结果
	private String	checkDownDate;		//检查文件处理时间
	private String	orderValue;			//采购订单返回值
	private String	orderResult;		//采购订单返回结果
	private String	orderDate;			//采购订单ERP处理时间
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRowSeqNo() {
		return rowSeqNo;
	}
	public void setRowSeqNo(String rowSeqNo) {
		this.rowSeqNo = rowSeqNo;
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
	public String getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(String orderValue) {
		this.orderValue = orderValue;
	}
	public String getOrderResult() {
		return orderResult;
	}
	public void setOrderResult(String orderResult) {
		this.orderResult = orderResult;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
}
