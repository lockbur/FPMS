package com.forms.prms.web.pay.orderpaymgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPayBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3190173536580929465L;
	/**clean_log**/
	private String batchNo;// 批次号
	private BigDecimal seqNo;// 序号
	private String cntNum;// 合同编号
	private String payId;// FMS付款单号
	private String erpPayId;// ERP付款单号
	private String payDate;// 付款日期
	private String befDate; // 起始日期
	private String aftDate; // 结束日期
	private BigDecimal payAmt;// 付款金额
	private String imageId;// 影像地址
	private String payCancelState;// 发票取消状态 Y/N
	private String payCancelDate;// 发票取消日期
	private String payType;// 付款类型 0：正常付款 1：暂收结清
	private String operUser;// 操作用户 ERP用户ID或者FMS
	private String operDate;// 操作日期
	private String operTime;// 操作时间
	private String modiUser;// 修改用户 ERP用户ID或者FMS
	private String modiDate;// 修改日期
	private String modiTime;// 修改时间
	private String flag;//
	/**TD_PAY**/
	private String invoiceId;// 发票号
	private BigDecimal attachmentNum;// 附件张数
	private String providerCode;// 供应商
	private String provActNo;// 银行帐号
	private String provActCurr;// 币别
	private String invoiceMemo;// 发票说明
	private BigDecimal invoiceAmt;// 发票金额 发票金额=暂收金额+付款金额+预付款核销金额
	private BigDecimal advanceCancelAmt;// 预付款核销金额
	private BigDecimal suspenseAmt;// 暂收金额
	private String suspenseDate;// 暂收付款日期
	private String suspenseName;// 暂收名称
	private String suspenseReason;// 挂帐原因
	private BigDecimal suspensePeriod;// 预计处理时间 月份
	private BigDecimal susTotalAmt;// 累计结清金额
	private String dataFlag;// A0-初始录入
	private String dataFlagInvoice;// 发票状态 0-录入 【1-校验失败】 2-创建成功 3-回冲
	private String dataFlagPay;// 付款状态 0-录入 【1-校验失败】 2-付款中 3-支付成功 4-付款回冲中 5-回冲
	private String instDutyCode;// 创建责任中心
	private String cntType;// 类别 0-资产类 1-费用类
	private String instUser;// 创建人
	private String instDate;// 创建日期
	private String instTime;// 创建时间
	private String payMode;// 支付方式 默认为供应商表的
	private String attachmentType;// 附件类型
	private String paySeqNo;// 付款流水号
	private String isCreditNote;// 是否为贷项通知单(0:是 1:否)
	private BigDecimal payTotalAmt;// 累计付款金额
	
	
	private String normalPayId;//正常付款单号
	private String sortId;//子序号
	private String cleanProject;//结清项目
	private BigDecimal uncleanAmt;//未结清金额（业务人员录入，本次暂收结清后的剩余结清金额）
	private BigDecimal cleanAmt;//结清金额 业务人员录入时提供
	private BigDecimal cleanAmtFms;//结清金额 FMS付款文件中提供
	private String cleanMemo;//结清说明
	private String cleanReason;//结清原因
	private String isFlag;
	
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

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}

	public String getNormalPayId() {
		return normalPayId;
	}

	public void setNormalPayId(String normalPayId) {
		this.normalPayId = normalPayId;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getCleanProject() {
		return cleanProject;
	}

	public void setCleanProject(String cleanProject) {
		this.cleanProject = cleanProject;
	}

	public BigDecimal getUncleanAmt() {
		return uncleanAmt;
	}

	public void setUncleanAmt(BigDecimal uncleanAmt) {
		this.uncleanAmt = uncleanAmt;
	}

	public BigDecimal getCleanAmt() {
		return cleanAmt;
	}

	public void setCleanAmt(BigDecimal cleanAmt) {
		this.cleanAmt = cleanAmt;
	}

	public BigDecimal getCleanAmtFms() {
		return cleanAmtFms;
	}

	public void setCleanAmtFms(BigDecimal cleanAmtFms) {
		this.cleanAmtFms = cleanAmtFms;
	}

	public String getCleanMemo() {
		return cleanMemo;
	}

	public void setCleanMemo(String cleanMemo) {
		this.cleanMemo = cleanMemo;
	}

	public String getCleanReason() {
		return cleanReason;
	}

	public void setCleanReason(String cleanReason) {
		this.cleanReason = cleanReason;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public BigDecimal getAttachmentNum() {
		return attachmentNum;
	}

	public void setAttachmentNum(BigDecimal attachmentNum) {
		this.attachmentNum = attachmentNum;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProvActNo() {
		return provActNo;
	}

	public void setProvActNo(String provActNo) {
		this.provActNo = provActNo;
	}

	public String getProvActCurr() {
		return provActCurr;
	}

	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}

	public String getInvoiceMemo() {
		return invoiceMemo;
	}

	public void setInvoiceMemo(String invoiceMemo) {
		this.invoiceMemo = invoiceMemo;
	}

	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
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

	public BigDecimal getSuspensePeriod() {
		return suspensePeriod;
	}

	public void setSuspensePeriod(BigDecimal suspensePeriod) {
		this.suspensePeriod = suspensePeriod;
	}

	public BigDecimal getSusTotalAmt() {
		return susTotalAmt;
	}

	public void setSusTotalAmt(BigDecimal susTotalAmt) {
		this.susTotalAmt = susTotalAmt;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDataFlagInvoice() {
		return dataFlagInvoice;
	}

	public void setDataFlagInvoice(String dataFlagInvoice) {
		this.dataFlagInvoice = dataFlagInvoice;
	}

	public String getDataFlagPay() {
		return dataFlagPay;
	}

	public void setDataFlagPay(String dataFlagPay) {
		this.dataFlagPay = dataFlagPay;
	}

	public String getInstDutyCode() {
		return instDutyCode;
	}

	public void setInstDutyCode(String instDutyCode) {
		this.instDutyCode = instDutyCode;
	}

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
	}

	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getInstTime() {
		return instTime;
	}

	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getPaySeqNo() {
		return paySeqNo;
	}

	public void setPaySeqNo(String paySeqNo) {
		this.paySeqNo = paySeqNo;
	}

	public String getIsCreditNote() {
		return isCreditNote;
	}

	public void setIsCreditNote(String isCreditNote) {
		this.isCreditNote = isCreditNote;
	}

	public BigDecimal getPayTotalAmt() {
		return payTotalAmt;
	}

	public void setPayTotalAmt(BigDecimal payTotalAmt) {
		this.payTotalAmt = payTotalAmt;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
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

	public String getErpPayId() {
		return erpPayId;
	}

	public void setErpPayId(String erpPayId) {
		this.erpPayId = erpPayId;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getPayCancelState() {
		return payCancelState;
	}

	public void setPayCancelState(String payCancelState) {
		this.payCancelState = payCancelState;
	}

	public String getPayCancelDate() {
		return payCancelDate;
	}

	public void setPayCancelDate(String payCancelDate) {
		this.payCancelDate = payCancelDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getModiUser() {
		return modiUser;
	}

	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

}
