package com.forms.prms.web.amortization.abnormalDataMgr.domain;

/**
 * Title:CommonTidQueryObject
 * Description:	异常数据功能Bean,用于作公共查询条件使用
 * 				(abnormalDataQuery.jsp页面中查询区域，适应各个查询条件用)
 * Copyright: formssi
 * @author HQQ
 * @project ERP
 * @date 2015-04-30
 * @version 1.0
 */
public class CommonTidQueryBean {
	
	private String queryType;			//查询类型(即查询的表)
	private String useFlag;				//异常状态
	private String befDate;				//起始日期
	private String aftDate;				//结束日期
	
	private String voucherName;			//总账名称
	private String cntNum;				//合同编号
	private String debitAmt;			//借方金额
	private String creditAmt;			//贷方金额
	private String createDate;			//日记账创建日期
	private String payId;				//付款单号
	private String invoiceNo;			//发票编号
	private String invoiceAmt;			//发票金额
	private String payAmt;				//付款金额
	private String payCancelDate;		//发票取消日期
	private String orgName;				//机构名称
	private String cglName;				//核算码名称
	private String productName;			//产品名称
	private String poNumber;			//PO单号
	private String stockNum;			//采集编号
	private String orderId;				//订单号
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
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
	public String getVoucherName() {
		return voucherName;
	}
	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getDebitAmt() {
		return debitAmt;
	}
	public void setDebitAmt(String debitAmt) {
		this.debitAmt = debitAmt;
	}
	public String getCreditAmt() {
		return creditAmt;
	}
	public void setCreditAmt(String creditAmt) {
		this.creditAmt = creditAmt;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getPayCancelDate() {
		return payCancelDate;
	}
	public void setPayCancelDate(String payCancelDate) {
		this.payCancelDate = payCancelDate;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCglName() {
		return cglName;
	}
	public void setCglName(String cglName) {
		this.cglName = cglName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
