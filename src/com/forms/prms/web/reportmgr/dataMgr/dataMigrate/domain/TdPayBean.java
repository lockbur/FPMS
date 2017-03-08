package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Title:			TdPayBean
 * Description:		JavaBean类，用于保存【数据迁移】模块中
 * 								[普通付款数据]Excel模板--Sheet1(付款信息)的导入数据
 * 								对应表：UPLOAD_TD_PAY
 * Copyright: 		formssi
 * @author： 		HQQ
 * @project： 		ERP
 * @date： 			2015-06-26
 * @version： 		1.0
 */
public class TdPayBean {
	private String cntNum;					//合同号
	private String payId;					//付款单号 	 	5位ou+8位日期+1位标志(0:预付款，1：正常付款)+序号
	private String isCreditNote;			//是否为贷项通知单(0:是   1:否)
	private String providerName;			//供应商
	private String provActNo;				//供应商帐号
	private String provActCurr;				//供应商账号币别
	private BigDecimal invoiceAmt;			//发票金额 		发票金额=暂收金额+付款金额+预付款核销金额
	private BigDecimal payAmt;				//付款金额
	private BigDecimal advanceCancelAmt;	//预付款核销金额
	private BigDecimal suspenseAmt;			//暂收金额
	private BigDecimal susTotalAmt;			//暂收已结清金额
	private String payDate;					//正常付款日期
	private String payMode;					//付款方式 		默认为供应商表的
	private String suspenseDate;			//暂收付款日期
	private String suspenseName;			//暂收名称
	private String suspenseReason;			//挂账原因
	private int suspensePeriod;				//预计处理时间(月份)
	private String invoiceMemo;				//发票说明
	
	//6-30新增公共字段
	private String batchNo;					//批次号
	private String dataType;				//数据类型(对应具体模板具体Sheet，如0101)
	private String uploadType;				//上传类型(对应Excel模板编号)
	private String rowNo;					//Sheet中数据的行号
	private String orgId;					//导入操作用户的一级行机构编号
	
	private String providerCode;			//供应商编号
	
	
	//正常付款下的预付款核销信息
	private List<TdPayAdvanceCancelBean> payAdCancelList;
	//正常付款下的物料信息(正常/预付款核销)
	private List<TdPayDeviceBean>	payDeviceList;
	
	
	public List<TdPayAdvanceCancelBean> getPayAdCancelList() {
		return payAdCancelList;
	}
	public void setPayAdCancelList(List<TdPayAdvanceCancelBean> payAdCancelList) {
		this.payAdCancelList = payAdCancelList;
	}
	public List<TdPayDeviceBean> getPayDeviceList() {
		return payDeviceList;
	}
	public void setPayDeviceList(List<TdPayDeviceBean> payDeviceList) {
		this.payDeviceList = payDeviceList;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
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
	
	//是否为贷项通知单
	public String getIsCreditNote() {
		if("是".equals(isCreditNote)){
			return "0";
		}else if("否".equals(isCreditNote)){
			return "1";
		}else{
			return isCreditNote;
		}
	}
	public void setIsCreditNote(String isCreditNote) {
		if("0".equals(isCreditNote)){
			this.isCreditNote = "是";
		}else if("1".equals(isCreditNote)){
			this.isCreditNote = "否";
		}else{
			this.isCreditNote = isCreditNote;
		}
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
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public BigDecimal getAdvanceCancelAmt() {
		return advanceCancelAmt;
	}
	public void setAdvanceCancelAmt(BigDecimal advanceCancelAmt) {
		this.advanceCancelAmt = advanceCancelAmt;
	}
	public BigDecimal getSuspenseAmt() {
		return suspenseAmt;
	}
	public void setSuspenseAmt(BigDecimal suspenseAmt) {
		this.suspenseAmt = suspenseAmt;
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
	public String getProvActCurr() {
		return provActCurr;
	}
	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}
	public BigDecimal getSusTotalAmt() {
		return susTotalAmt;
	}
	public void setSusTotalAmt(BigDecimal susTotalAmt) {
		this.susTotalAmt = susTotalAmt;
	}
	public String getSuspenseDate() {
		return suspenseDate;
	}
	public void setSuspenseDate(String suspenseDate) {
		this.suspenseDate = suspenseDate;
	}
	public String getSuspenseName() {
		return suspenseName;
	}
	public void setSuspenseName(String suspenseName) {
		this.suspenseName = suspenseName;
	}
	public String getSuspenseReason() {
		return suspenseReason;
	}
	public void setSuspenseReason(String suspenseReason) {
		this.suspenseReason = suspenseReason;
	}
	public int getSuspensePeriod() {
		return suspensePeriod;
	}
	public void setSuspensePeriod(int suspensePeriod) {
		this.suspensePeriod = suspensePeriod;
	}
}
