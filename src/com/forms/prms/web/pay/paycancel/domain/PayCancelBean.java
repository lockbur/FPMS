package com.forms.prms.web.pay.paycancel.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayCancelBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1507870652707935113L;
	/** 合同信息 **/
	private String cntNum;// 合同号
	private String org1Code;// 所属一级行机构号
	private String psbh;// 评审编号
	private String stockNum;// 集采编号
	private String qbh;// 签文报号
	private String lxlx;// 审批类别 1：电子审批;2：签报立项;3：部内审批；
	private BigDecimal lxsl;// 审批数量 （电子审批数量小计）
	private BigDecimal lxje;// 审批金额 （电子审批金额小计）
	private BigDecimal cntAmt;// 合同金额
	private BigDecimal totalNum;// 采购数量
	private String cntType;// 类别 0-资产类 1-费用类
	private String feeType;// 费用类型 0-金额固定、受益期固定 1-受益期固定、合同金额不固定 2-受益期不固定，付款时确认费用
							// 3-宣传费
	private String feeSubType;// 费用子类型 0-普通费用类 1-房屋租赁类
	private String feeStartDate;// 合同受益起始日期
	private String feeEndDate;// 合同受益终止日期
	private BigDecimal feeAmt;// 合同金额确认部分
	private BigDecimal feePenalty;// 合同金额约定罚金
	private String signDate;// 签订日期/采购日期
	private String isProvinceBuy;// 省行统购项目 0-是 1-否
	private BigDecimal zbAmt;// 质保金
	private String payDutyCode;// 付款责任中心 录入时为发起人责任中心，接收移交后更新
	private String deliverDutyCode;// 移交后责任中心 录入时为空，提出移交后更新
	private String providerCode;// 供应商
	private String provActNo;// 银行账户编号
	private String provActCurr;// 银行账户币种 RMB
	private String srcPoviderName;// 原始供应商名称 供应商是内部供应商记录所对应的外部供应商名字，可以录入
	private String payTerm;// 付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款 3-分期付款
	private String stageType;// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
	private String memo;// 备注
	private String currency;// 币别 默认为CNY
	private String dataFlag;// 合同状态
	private String createDate;// 创建日期
	private String createDept;// 创建责任中心
	private BigDecimal normarlTotalAmt;// 正常付款金额 ap文件回盘后增加
	private BigDecimal advanceTotalAmt;// 预付款总金额 ap文件回盘后增加，核销文件回盘后减少
	private BigDecimal freezeTotalAmt;// 冻结金额
										// 付款新增时增加，预付款增加付款金额，正常付款增加付款金额+暂收金额，文件回盘后减少
	private BigDecimal suspenseTotalAmt;// 暂收总金额 ap文件回盘后增加
	private String isPrepaidProvision;// 是否预提待摊 0-是 1-否
	private BigDecimal providerTaxRate;// 供应商税率（从供应商表中带出，页面直接展示）
	private BigDecimal providerTax;// 供应商税（为合同金额*供应商税率，页面可修改）
	private String isSpec;// 是否专项包：0-是 1-否
	private String isOrder;// 是否是订单：0-是 1-否
	private String cntName;// 合同事项
	private String cntNumRelated;// 关联的合同号
	private String oncePrepaid;// 是否一次待摊 0-是 1：否 （合同终止时更新）
	private String isOlddata;// 是否遗留数据：0-是 1-否
	private String cntTypeName;//
	/** 付款信息 **/
	private String payId;// 付款单号 付款单号 5位ou+8位日期+1位标志(0:预付款，1：正常付款)+序号
	private String invoiceId;// 发票号
	private BigDecimal attachmentNum;// 附件张数
	private String invoiceMemo;// 发票说明
	private BigDecimal invoiceAmt;// 发票金额 发票金额=暂收金额+付款金额+预付款核销金额
	private BigDecimal advanceCancelAmt;// 预付款核销金额
	private BigDecimal payAmt;// 付款金额
	private String payDate;// 付款日期
	private String befDate;// 付款日期
	private String aftDate;// 付款日期
	private BigDecimal suspenseAmt;// 暂收金额
	private String suspenseDate;// 暂收付款日期
	private String suspenseName;// 暂收名称
	private String suspenseReason;// 挂帐原因
	private BigDecimal suspensePeriod;// 预计处理时间 月份
	private BigDecimal susTotalAmt;// 累计结清金额
	private String dataFlagInvoice;// 发票状态 0-录入 【1-校验失败】 2-创建成功 3-回冲
	private String dataFlagPay;// 付款状态 0-录入 【1-校验失败】 2-付款中 3-支付成功 4-付款回冲中 5-回冲
	private String instDutyCode;// 创建责任中心
	private String instUser;// 创建人
	private String instDate;// 创建日期
	private String instTime;// 创建时间
	private String payMode;// 支付方式 默认为供应商表的
	private String payModeName;// 支付方式 默认为供应商表的
	private String attachmentType;// 附件类型
	private String paySeqNo;// 付款流水号
	private String isCreditNote;// 是否为贷项通知单(0:是 1:否)
	private BigDecimal payTotalAmt;// 累计付款金额

	private String payDataFlag;
	private String providerName;
	private String providerType;
	private String ouCode;
	private String ouCodeName;

	private String cglCode;
	private String projName;
	private String matrName;
	private String deviceModelName;
	private String execAmt;
	private String execPrice;
	private String payedAmt;
	private String freezeAmt;
	private String subInvoiceAmt;
	private String ivrowMemo;
	private String payType;
	private String bankName;
	private BigDecimal cancelAmt;
	private BigDecimal cancelAmtTotal;
	private String table;
	private String isPrePay;

	private String advancePayId;
	private String[] advancePayIds;// 预付款单号数组
	private BigDecimal cancelInvoAmt;// 发票取消金额
	private BigDecimal cancelAdvClAmt;// 预付款核销取消金额
	private BigDecimal cancelPayAmt;// 付款取消金额
	private BigDecimal preCancelPayAmt;// 预付款取消金额
	private BigDecimal cancelSusAmt;// 暂收付款取消金额
	private BigDecimal[] preCancelAmts;// 本次核销取消金额
	private BigDecimal[] advancePayCancelAmts;// 预付款设备取消金额
	private BigDecimal advancePayCancelAmt;//
	private BigDecimal[] payCancelAmts;// 正常付款设备取消金额
	private BigDecimal payCancelAmt;
	private BigDecimal devCancelAmt;
	private BigDecimal subId;// 物料对应的子序号
	private BigDecimal[] subIds;// 正常付款物料对应的子序号
	private BigDecimal[] advSubIds;// 预付款核销物料对应的子序号
	private BigDecimal addTaxAmt;
    private String cntAllAmt;
    
    private String bankInfo;
    
    
    
    
	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getCntAllAmt() {
		return cntAllAmt;
	}

	public void setCntAllAmt(String cntAllAmt) {
		this.cntAllAmt = cntAllAmt;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public BigDecimal getAddTaxAmt() {
		return addTaxAmt;
	}

	public void setAddTaxAmt(BigDecimal addTaxAmt) {
		this.addTaxAmt = addTaxAmt;
	}

	public BigDecimal getPreCancelPayAmt() {
		return preCancelPayAmt;
	}

	public void setPreCancelPayAmt(BigDecimal preCancelPayAmt) {
		this.preCancelPayAmt = preCancelPayAmt;
	}

	public BigDecimal getDevCancelAmt() {
		return devCancelAmt;
	}

	public void setDevCancelAmt(BigDecimal devCancelAmt) {
		this.devCancelAmt = devCancelAmt;
	}

	public BigDecimal getAdvancePayCancelAmt() {
		return advancePayCancelAmt;
	}

	public void setAdvancePayCancelAmt(BigDecimal advancePayCancelAmt) {
		this.advancePayCancelAmt = advancePayCancelAmt;
	}

	public BigDecimal getPayCancelAmt() {
		return payCancelAmt;
	}

	public void setPayCancelAmt(BigDecimal payCancelAmt) {
		this.payCancelAmt = payCancelAmt;
	}

	public BigDecimal getCancelSusAmt() {
		return cancelSusAmt;
	}

	public void setCancelSusAmt(BigDecimal cancelSusAmt) {
		this.cancelSusAmt = cancelSusAmt;
	}

	public BigDecimal getSubId() {
		return subId;
	}

	public void setSubId(BigDecimal subId) {
		this.subId = subId;
	}

	public String[] getAdvancePayIds() {
		return advancePayIds;
	}

	public void setAdvancePayIds(String[] advancePayIds) {
		this.advancePayIds = advancePayIds;
	}

	public BigDecimal getCancelInvoAmt() {
		return cancelInvoAmt;
	}

	public void setCancelInvoAmt(BigDecimal cancelInvoAmt) {
		this.cancelInvoAmt = cancelInvoAmt;
	}

	public BigDecimal getCancelAdvClAmt() {
		return cancelAdvClAmt;
	}

	public void setCancelAdvClAmt(BigDecimal cancelAdvClAmt) {
		this.cancelAdvClAmt = cancelAdvClAmt;
	}

	public BigDecimal getCancelPayAmt() {
		return cancelPayAmt;
	}

	public void setCancelPayAmt(BigDecimal cancelPayAmt) {
		this.cancelPayAmt = cancelPayAmt;
	}

	public BigDecimal[] getPreCancelAmts() {
		return preCancelAmts;
	}

	public void setPreCancelAmts(BigDecimal[] preCancelAmts) {
		this.preCancelAmts = preCancelAmts;
	}

	public BigDecimal[] getAdvancePayCancelAmts() {
		return advancePayCancelAmts;
	}

	public void setAdvancePayCancelAmts(BigDecimal[] advancePayCancelAmts) {
		this.advancePayCancelAmts = advancePayCancelAmts;
	}

	public BigDecimal[] getPayCancelAmts() {
		return payCancelAmts;
	}

	public void setPayCancelAmts(BigDecimal[] payCancelAmts) {
		this.payCancelAmts = payCancelAmts;
	}

	public BigDecimal[] getSubIds() {
		return subIds;
	}

	public void setSubIds(BigDecimal[] subIds) {
		this.subIds = subIds;
	}

	public BigDecimal[] getAdvSubIds() {
		return advSubIds;
	}

	public void setAdvSubIds(BigDecimal[] advSubIds) {
		this.advSubIds = advSubIds;
	}

	public String getIsPrePay() {
		return isPrePay;
	}

	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public BigDecimal getCancelAmtTotal() {
		return cancelAmtTotal;
	}

	public void setCancelAmtTotal(BigDecimal cancelAmtTotal) {
		this.cancelAmtTotal = cancelAmtTotal;
	}

	public String getPayModeName() {
		return payModeName;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAdvancePayId() {
		return advancePayId;
	}

	public void setAdvancePayId(String advancePayId) {
		this.advancePayId = advancePayId;
	}

	public BigDecimal getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(BigDecimal cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(String execAmt) {
		this.execAmt = execAmt;
	}

	public String getExecPrice() {
		return execPrice;
	}

	public void setExecPrice(String execPrice) {
		this.execPrice = execPrice;
	}

	public String getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(String payedAmt) {
		this.payedAmt = payedAmt;
	}

	public String getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public String getSubInvoiceAmt() {
		return subInvoiceAmt;
	}

	public void setSubInvoiceAmt(String subInvoiceAmt) {
		this.subInvoiceAmt = subInvoiceAmt;
	}

	public String getIvrowMemo() {
		return ivrowMemo;
	}

	public void setIvrowMemo(String ivrowMemo) {
		this.ivrowMemo = ivrowMemo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public String getOuCode() {
		return ouCode;
	}

	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}

	public String getOuCodeName() {
		return ouCodeName;
	}

	public void setOuCodeName(String ouCodeName) {
		this.ouCodeName = ouCodeName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPayDataFlag() {
		return payDataFlag;
	}

	public void setPayDataFlag(String payDataFlag) {
		this.payDataFlag = payDataFlag;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getPsbh() {
		return psbh;
	}

	public void setPsbh(String psbh) {
		this.psbh = psbh;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getQbh() {
		return qbh;
	}

	public void setQbh(String qbh) {
		this.qbh = qbh;
	}

	public String getLxlx() {
		return lxlx;
	}

	public void setLxlx(String lxlx) {
		this.lxlx = lxlx;
	}

	public BigDecimal getLxsl() {
		return lxsl;
	}

	public void setLxsl(BigDecimal lxsl) {
		this.lxsl = lxsl;
	}

	public BigDecimal getLxje() {
		return lxje;
	}

	public void setLxje(BigDecimal lxje) {
		this.lxje = lxje;
	}

	public BigDecimal getCntAmt() {
		return cntAmt;
	}

	public void setCntAmt(BigDecimal cntAmt) {
		this.cntAmt = cntAmt;
	}

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeSubType() {
		return feeSubType;
	}

	public void setFeeSubType(String feeSubType) {
		this.feeSubType = feeSubType;
	}

	public String getFeeStartDate() {
		return feeStartDate;
	}

	public void setFeeStartDate(String feeStartDate) {
		this.feeStartDate = feeStartDate;
	}

	public String getFeeEndDate() {
		return feeEndDate;
	}

	public void setFeeEndDate(String feeEndDate) {
		this.feeEndDate = feeEndDate;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public BigDecimal getFeePenalty() {
		return feePenalty;
	}

	public void setFeePenalty(BigDecimal feePenalty) {
		this.feePenalty = feePenalty;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getIsProvinceBuy() {
		return isProvinceBuy;
	}

	public void setIsProvinceBuy(String isProvinceBuy) {
		this.isProvinceBuy = isProvinceBuy;
	}

	public BigDecimal getZbAmt() {
		return zbAmt;
	}

	public void setZbAmt(BigDecimal zbAmt) {
		this.zbAmt = zbAmt;
	}

	public String getPayDutyCode() {
		return payDutyCode;
	}

	public void setPayDutyCode(String payDutyCode) {
		this.payDutyCode = payDutyCode;
	}

	public String getDeliverDutyCode() {
		return deliverDutyCode;
	}

	public void setDeliverDutyCode(String deliverDutyCode) {
		this.deliverDutyCode = deliverDutyCode;
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

	public String getSrcPoviderName() {
		return srcPoviderName;
	}

	public void setSrcPoviderName(String srcPoviderName) {
		this.srcPoviderName = srcPoviderName;
	}

	public String getPayTerm() {
		return payTerm;
	}

	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}

	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
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

	public String getIsPrepaidProvision() {
		return isPrepaidProvision;
	}

	public void setIsPrepaidProvision(String isPrepaidProvision) {
		this.isPrepaidProvision = isPrepaidProvision;
	}

	public BigDecimal getProviderTaxRate() {
		return providerTaxRate;
	}

	public void setProviderTaxRate(BigDecimal providerTaxRate) {
		this.providerTaxRate = providerTaxRate;
	}

	public BigDecimal getProviderTax() {
		return providerTax;
	}

	public void setProviderTax(BigDecimal providerTax) {
		this.providerTax = providerTax;
	}

	public String getIsSpec() {
		return isSpec;
	}

	public void setIsSpec(String isSpec) {
		this.isSpec = isSpec;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public String getCntNumRelated() {
		return cntNumRelated;
	}

	public void setCntNumRelated(String cntNumRelated) {
		this.cntNumRelated = cntNumRelated;
	}

	public String getOncePrepaid() {
		return oncePrepaid;
	}

	public void setOncePrepaid(String oncePrepaid) {
		this.oncePrepaid = oncePrepaid;
	}

	public String getIsOlddata() {
		return isOlddata;
	}

	public void setIsOlddata(String isOlddata) {
		this.isOlddata = isOlddata;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
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

}
