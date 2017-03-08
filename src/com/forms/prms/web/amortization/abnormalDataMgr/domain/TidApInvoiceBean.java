package com.forms.prms.web.amortization.abnormalDataMgr.domain;

/**
 * Title:		TidApInvoiceBean
 * Description:	JavaBean,对应数据库中表：TID_AP_INVOICE
 * Copyright: 	formssi
 * @author： 	HQQ
 * @project： 	ERP
 * @date： 		2015-04-08
 * @version： 	1.0
 */
public class TidApInvoiceBean {
	
	private String batchNo;					//批次号
	private String seqNo;					//序号
	private String cntNum;					//合同编号
	private String payId;					//付款单号
	private String invoiceAmt;				//发票金额
	private String invoiceType;				//发票类型
	private String invoiceCancelState;		//发票取消状态
	private String invoiceCancelDate;		//发票取消日期
	private String invoiceRowType;			//发票行类型
	private String cancelInvoiceId;			//预付款发票编号
	private String poLineno;				//PO行号
	private String poNumber;				//PO单号
	private String invoiceNo;				//发票编号
	private String ivrowSeqno;				//发票行序号
	private String ivrowAmt;				//发票行金额
	private String dataFlag;				//数据状态标识
	private String useFlag;					//数据异常标识
	
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
	public String getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceCancelState() {
		return invoiceCancelState;
	}
	public void setInvoiceCancelState(String invoiceCancelState) {
		this.invoiceCancelState = invoiceCancelState;
	}
	public String getInvoiceCancelDate() {
		return invoiceCancelDate;
	}
	public void setInvoiceCancelDate(String invoiceCancelDate) {
		this.invoiceCancelDate = invoiceCancelDate;
	}
	public String getInvoiceRowType() {
		return invoiceRowType;
	}
	public void setInvoiceRowType(String invoiceRowType) {
		this.invoiceRowType = invoiceRowType;
	}
	public String getCancelInvoiceId() {
		return cancelInvoiceId;
	}
	public void setCancelInvoiceId(String cancelInvoiceId) {
		this.cancelInvoiceId = cancelInvoiceId;
	}
	public String getPoLineno() {
		return poLineno;
	}
	public void setPoLineno(String poLineno) {
		this.poLineno = poLineno;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getIvrowSeqno() {
		return ivrowSeqno;
	}
	public void setIvrowSeqno(String ivrowSeqno) {
		this.ivrowSeqno = ivrowSeqno;
	}
	public String getIvrowAmt() {
		return ivrowAmt;
	}
	public void setIvrowAmt(String ivrowAmt) {
		this.ivrowAmt = ivrowAmt;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

}
