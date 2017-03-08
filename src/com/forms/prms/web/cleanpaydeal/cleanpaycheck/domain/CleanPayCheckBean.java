package com.forms.prms.web.cleanpaydeal.cleanpaycheck.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CleanPayCheckBean implements Serializable {
	private static final long serialVersionUID = -1671480868820790335L;

	/** 合同信息 **/
	private BigDecimal cntAmt;// 合同金额
	private BigDecimal normarlTotalAmt;// 正常付款金额 ap文件回盘后增加
	private BigDecimal advanceTotalAmt;// 预付款总金额 ap文件回盘后增加，核销文件回盘后减少
	private BigDecimal freezeTotalAmt;// 冻结金额
										// 付款新增时增加，预付款增加付款金额，正常付款增加付款金额+暂收金额，文件回盘后减少
	private BigDecimal suspenseTotalAmt;// 暂收总金额 ap文件回盘后增加
	private BigDecimal zbAmt;// 质保金

	/** 付款信息 **/
	private String payId;// 付款单号 付款单号 5位ou+8位日期+1位标志(0:预付款，1：正常付款)+序号
	private String cntNum;// 合同编号 BOC+一级分行机构号+yyyymmdd+六位序号+_责任中心
	private String invoiceId;// 发票号
	private BigDecimal attachmentNum;// 附件张数
	private String providerCode;// 供应商
	private String providerName;// 供应商
	private String provActNo;// 银行帐号
	private String bankName;// 银行名称
	private String provActCurr;// 币别
	private String invoiceMemo;// 发票说明
	private BigDecimal invoiceAmt;// 发票金额 发票金额=暂收金额+付款金额+预付款核销金额
	private BigDecimal advanceCancelAmt;// 预付款核销金额
	private BigDecimal payAmt;// 付款金额
	private String payDate;// 付款日期
	private BigDecimal suspenseAmt;// 暂收金额
	private String suspenseDate;// 暂收付款日期
	private String suspenseName;// 暂收名称
	private String suspenseReason;// 挂帐原因
	private BigDecimal suspensePeriod;// 预计处理时间 月份
	private BigDecimal susTotalAmt;// 累计结清金额
	private String dataFlag;// 状态 00 录入01 退回02 待复核03 财务中心退回04 付款待确认06 付款确认完成08
							// 付款中10 发送成功11 付款失败12 付款成功13 付款回冲"
	private String dataFlagName;// 状态名
	private String dataFlagInvoice;// 发票状态 0-录入 2-支付成功 3-回冲
	private String dataFlagPay;// 付款状态 0-录入 2-支付成功 3-回冲
	private String instDutyCode;// 创建责任中心
	private String cntType;// 类别 0-资产类 1-费用类
	private String cntTypeName;// 类别 0-资产类 1-费用类
	private String instUser;// 创建人
	private String instDate;// 创建日期
	private String instTime;// 创建时间
	private String payMode;// 支付方式 默认为供应商表的
	/** 结清信息 **/
	private String cleanPayId;// 暂收结清编号
	private String normalPayId;// 正常付款单号
	private String cleanProject;// 结清项目
	private BigDecimal cleanAmt;// 结清金额 业务人员录入时提供
	private BigDecimal cleanAmtIngTotal;// 正常结清的总金额
	private BigDecimal cleanAmtFms;// 结清金额 FMS付款文件中提供
	private String cleanMemo;// 结清说明
	private String cleanReason;// 结清原因

	private String befDate;// 付款日期
	private String aftDate;// 付款日期

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public BigDecimal getZbAmt() {
		return zbAmt;
	}

	public void setZbAmt(BigDecimal zbAmt) {
		this.zbAmt = zbAmt;
	}

	private String payFlag;// 付款标志

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public BigDecimal getCleanAmtIngTotal() {
		return cleanAmtIngTotal;
	}

	public void setCleanAmtIngTotal(BigDecimal cleanAmtIngTotal) {
		this.cleanAmtIngTotal = cleanAmtIngTotal;
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public BigDecimal getCntAmt() {
		return cntAmt;
	}

	public void setCntAmt(BigDecimal cntAmt) {
		this.cntAmt = cntAmt;
	}

	public BigDecimal getNormarlTotalAmt() {
		return normarlTotalAmt;
	}

	public void setNormarlTotalAmt(BigDecimal normarlTotalAmt) {
		this.normarlTotalAmt = normarlTotalAmt;
	}

	public BigDecimal getAdvanceTotalAmt() {
		return advanceTotalAmt;
	}

	public void setAdvanceTotalAmt(BigDecimal advanceTotalAmt) {
		this.advanceTotalAmt = advanceTotalAmt;
	}

	public BigDecimal getFreezeTotalAmt() {
		return freezeTotalAmt;
	}

	public void setFreezeTotalAmt(BigDecimal freezeTotalAmt) {
		this.freezeTotalAmt = freezeTotalAmt;
	}

	public BigDecimal getSuspenseTotalAmt() {
		return suspenseTotalAmt;
	}

	public void setSuspenseTotalAmt(BigDecimal suspenseTotalAmt) {
		this.suspenseTotalAmt = suspenseTotalAmt;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
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

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
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

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
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

	public String getCleanPayId() {
		return cleanPayId;
	}

	public void setCleanPayId(String cleanPayId) {
		this.cleanPayId = cleanPayId;
	}

	public String getNormalPayId() {
		return normalPayId;
	}

	public void setNormalPayId(String normalPayId) {
		this.normalPayId = normalPayId;
	}

	public String getCleanProject() {
		return cleanProject;
	}

	public void setCleanProject(String cleanProject) {
		this.cleanProject = cleanProject;
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

}
