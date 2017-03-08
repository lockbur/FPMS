package com.forms.prms.web.amortization.abnormalDataMgr.domain;

/**
 * Title:		TidAccountBean
 * Description:	JavaBean,对应数据库中表：TID_ACCOUNT
 * Copyright: 	formssi
 * @author： 	HQQ
 * @project： 	ERP
 * @date： 		2015-04-08
 * @version： 	1.0
 */
public class TidAccountBean {
	
	private String batchNo;					//批次号
	private String seqNo;					//序号
	private String voucherBatchName;		//凭证批名称
	private String voucherName;				//凭证名称
	private String createDate;				//日记账创建日期
	private String glDate;					//GL日期
	private String accountDate;				//过账日期
	private String voucherNo;				//凭证编号
	private String voucherMemo;				//凭证说明
	private String currency;				//币别(默认CNY)
	private String exchangeRate;			//汇率
	private String cntNum;					//合同编号
	private String ouCode;					//所属财务中心
	private String org1Code;				//一级行机构号
	private String voucherSeqNo;			//凭证行号
	private String orgCode;					//机构号
	private String dutyCode;				//责任中心
	private String cglCode;					//核算码
	private String reference;				//参考
	private String special;					//专项
	private String product;					//产品
	private String company;					//公司
	private String reserve1;				//备用段1
	private String reserve2;				//备用段2
	private String debitAmt;				//借方金额
	private String creditAmt;				//贷方金额
	private String rowMemo;					//行说明
	private String cglTradeNo;				//交易流水号
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
	public String getVoucherBatchName() {
		return voucherBatchName;
	}
	public void setVoucherBatchName(String voucherBatchName) {
		this.voucherBatchName = voucherBatchName;
	}
	public String getVoucherName() {
		return voucherName;
	}
	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getGlDate() {
		return glDate;
	}
	public void setGlDate(String glDate) {
		this.glDate = glDate;
	}
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getVoucherMemo() {
		return voucherMemo;
	}
	public void setVoucherMemo(String voucherMemo) {
		this.voucherMemo = voucherMemo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getOuCode() {
		return ouCode;
	}
	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getVoucherSeqNo() {
		return voucherSeqNo;
	}
	public void setVoucherSeqNo(String voucherSeqNo) {
		this.voucherSeqNo = voucherSeqNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getCglCode() {
		return cglCode;
	}
	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
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
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
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
	public String getRowMemo() {
		return rowMemo;
	}
	public void setRowMemo(String rowMemo) {
		this.rowMemo = rowMemo;
	}
	public String getCglTradeNo() {
		return cglTradeNo;
	}
	public void setCglTradeNo(String cglTradeNo) {
		this.cglTradeNo = cglTradeNo;
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
