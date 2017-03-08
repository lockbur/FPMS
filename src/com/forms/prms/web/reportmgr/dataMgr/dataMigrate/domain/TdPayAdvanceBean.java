package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

/**
 * Title:			TdPayAdvanceBean
 * Description:		JavaBean类，用于保存【数据迁移】模块中
 * 								[预付款数据]Excel模板--Sheet1(预付款信息)的导入数据
 * 								对应表：UPLOAD_TD_PAY_ADVANCE
 * Copyright: 		formssi
 * @author： 		HQQ
 * @project： 		ERP
 * @date： 			2015-06-26
 * @version： 		1.0
 */
public class TdPayAdvanceBean {

	private String cntNum;				//合同号
	private String payId;				//预付款单号
	private String providerName;		//供应商名称
	private String provActNo;			//供应商账号
	private String provActCurr;			//供应商币别
	private BigDecimal invoiceAmt;		//发票金额 		发票金额=付款金额
	private String payDate;				//付款日期
	private String payMode;				//付款方式 		默认为供应商表的
	private String invoiceMemo;			//发票说明
	
	//6-30新增公共字段
	private String batchNo;				//批次号
	private String rowNo;				//Sheet中数据的行号
	private String uploadType;			//上传类型(对应Excel模板编号)
	private String dataType;			//数据类型(对应具体模板具体Sheet，如0101)
	private String orgId;				//导入操作用户的一级行机构编号
	
	private String providerCode;		//供应商编码
	
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
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getProvActNo() {
		return provActNo;
	}
	public void setProvActNo(String provActNo) {
		this.provActNo = provActNo;
	}
	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getInvoiceMemo() {
		return invoiceMemo;
	}
	public void setInvoiceMemo(String invoiceMemo) {
		this.invoiceMemo = invoiceMemo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String getProvActCurr() {
		return provActCurr;
	}
	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}
}
