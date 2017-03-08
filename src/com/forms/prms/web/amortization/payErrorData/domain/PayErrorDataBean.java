package com.forms.prms.web.amortization.payErrorData.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Title: TdPayRepairBean Description: javaBean , 对应表TD_PAY_REPAIR字段 Copyright:
 * formssi
 * 
 * @author： wangtao
 * @project： ERP
 * @date： 2015-05-25
 * @version： 1.0
 */
public class PayErrorDataBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String isPrepaidProvision; // 是否预提待摊类 是：1，否：0
	private String isRepair; // 是否已补录 是：1，否：0
	private String payId;// 付款单号 5位ou+8位日期+1位标志(0:预付款，1：正常付款)+序号
	private String cntNum;// 合同号
	private String cntType;// 合同类型
	private String cntTypeName;// 合同类型
	private String invoiceId;// 发票号
	private BigDecimal attachmentNum;// 附件张数
	private String providerCode;// 供应商
	private String providerName;// 供应商
	private String provActNo;// 银行帐号
	private String provActCurr;// 币别
	private String invoiceMemo;// 发票说明
//	private BigDecimal invoiceAmt;// 发票金额 发票金额=暂收金额+付款金额+预付款核销金额
	private String invoiceAmt;// 发票金额 发票金额=暂收金额+付款金额+预付款核销金额
	private BigDecimal advanceCancelAmt;// 预付款核销金额
	private BigDecimal payAmt;// 付款金额
	private String payDate;// 付款日期
	private BigDecimal suspenseAmt;// 暂收金额
	private String suspenseDate;// 暂收付款日期
	private String suspenseName;// 暂收名称
	private String suspenseReason;// 挂帐原因
	private BigDecimal suspensePeriod;// 预计处理时间 月份
	private BigDecimal susTotalAmt;// 累计结清金额
	private String dataFlag;// 状态 00 录入01 退回02 待复核04 付款待确认06 付款确认完成10 付款成功11
							// 付款回冲"
	private String dataFlagName;
	private String dataFlagInvoice;// 发票状态 0-录入 2-支付成功 3-回冲
	private String dataFlagPay;// 付款状态 0-录入 2-支付成功 3-回冲
	private String instDutyCode;// 创建责任中心
	private String payMode;// 付款条件 默认为供应商表的
	private String isOrder;// 是否生成订单 0-是 1-否
	/** TD_CNT_DEVICE **/
	private BigDecimal subId;// 子序号
	private BigDecimal[] subIds;// 子序号
	private String projId;// 合同项目ID 根据项目可见性选择
	private String matrCode;// 物料编码 根据合同类型、费用类型、审批链(费用承担部门、物料编码和是否省行统购)选择
	private String deviceModel;// 设备型号
	private String deviceModelName;// 设备型号名称 因为录入时可以手工增加，保留名称
	private BigDecimal execAmt;// 执行金额
	private String feeDept;// 费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
	private String dataFlagDevice;// 状态 00-待审 01-退回 99-成功
	private BigDecimal payedAmt;// 已支付金额
	private String payType;// 付款类型

	private String befDate; // 起始日期
	private String aftDate; // 结束日期

	private BigDecimal zbAmt;// 质保金
	private BigDecimal cntAmt;// 合同金额
	private BigDecimal cancelAmt;// 本次核销金额
	private BigDecimal[] cancelAmts;// 本次核销金额
	private String bankName;// 银行名称

	private String cglCode;// 核算码
	private String projName;// 项目名称
	private String matrName;// 物料名称
	private String reference;// 参考
	private String special;// 专项
	private String payFlag;// 0:预付款，1：正常付款
	private String normalPayId;// 正常付款单号
	private String advancePayId;// 预付款单号
	private String[] advancePayIds;// 预付款单号数组
	private BigDecimal advancePayInvAmt;// 预付款发票分配金额
	private BigDecimal[] advancePayInvAmts;// 预付款发票分配金额数组
	private BigDecimal payInvAmt;// 正常付款发票分配金额
	private BigDecimal[] payInvAmts;// 正常付款发票分配金额数组
	private BigDecimal advSubId;// 预付款合同子序列
	private BigDecimal[] advSubIds;// 预付款合同子序列数组
	private BigDecimal subInvoiceAmt;// 分配发票金额
	private BigDecimal normarlTotalAmt;// 正常付款金额 ap文件回盘后增加
	private BigDecimal advanceTotalAmt;// 预付款总金额 ap文件回盘后增加，核销文件回盘后减少
	private BigDecimal freezeTotalAmt;// 冻结金额 付款新增时增加，文件回盘后减少
	private BigDecimal cancelAmtTotal;
	private BigDecimal freezeAmt;
	private BigDecimal payAmtBefore;
	private BigDecimal suspenseAmtBefore;
	private BigDecimal suspenseTotalAmt;

	private BigDecimal[] freezeAmtBefores;// 正常付款设备上一次操作添加的冻结金额
	private BigDecimal freezeAmtBefore;//
	private BigDecimal[] freezeAmtAdvBefores;// 预付款设备上一次操作添加的冻结金额
	private BigDecimal freezeAmtAdvBefore;

	private String createDept;// 创建机构
	private String createDeptName;// 创建机构
	private String providerType;// 供应商类型

	private String[] payIvrowMemos;// 正常付款设备发票行说明
	private String[] advancePayIvrowMemos;// 预付款设备发票行说明
	private String ivrowMemo;// 发票行说明

	private String attachmentType;// 附件类型
	private String attachmentTypeName;// 附件类型名称

	/*** 付款log **/
	private BigDecimal innerNo;// +审批序号
	private String operMemo;// 操作说明
	private String instOper;// 操作柜员

	/*** 扫描 **/
	private String id;

	private String icmsPkuuid;

	private String table; // 表
	
	private String org1Code;
	
	private String org1CodeSearch;
	private String org1Name;
	
	/**TID_AP_INVOICE_ORDER_ERROR**/
	private String batchNo;//批次号
	private String seqNo;//序号
	private String invoiceType;
	private String invoiceCancelState;
	private String errMemo;
	private String isDealed;
	private String instDate;
	private String instTime;
	private String dealUser;
	private String dealDate;
	private String dealTime;
	private String orgFlag;
	private String org2Code;
	private String dutyCode;
	private String cntAllAmt;
	
	private String bankInfo;
	
	private String isCenterAdmin;
	
	public String getOrg1CodeSearch() {
		return org1CodeSearch;
	}

	public void setOrg1CodeSearch(String org1CodeSearch) {
		this.org1CodeSearch = org1CodeSearch;
	}

	public String getOrg1Name() {
		return org1Name;
	}

	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}

	public String getIsCenterAdmin() {
		return isCenterAdmin;
	}

	public void setIsCenterAdmin(String isCenterAdmin) {
		this.isCenterAdmin = isCenterAdmin;
	}

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

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

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

	public String getErrMemo() {
		return errMemo;
	}

	public void setErrMemo(String errMemo) {
		this.errMemo = errMemo;
	}

	public String getIsDealed() {
		return isDealed;
	}

	public void setIsDealed(String isDealed) {
		this.isDealed = isDealed;
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

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getIsPrepaidProvision() {
		return isPrepaidProvision;
	}

	public void setIsPrepaidProvision(String isPrepaidProvision) {
		this.isPrepaidProvision = isPrepaidProvision;
	}

	public String getIsRepair() {
		return isRepair;
	}

	public void setIsRepair(String isRepair) {
		this.isRepair = isRepair;
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

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
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

	public String getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(String invoiceAmt) {
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

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public BigDecimal getSubId() {
		return subId;
	}

	public void setSubId(BigDecimal subId) {
		this.subId = subId;
	}

	public BigDecimal[] getSubIds() {
		return subIds;
	}

	public void setSubIds(BigDecimal[] subIds) {
		this.subIds = subIds;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public BigDecimal getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(BigDecimal execAmt) {
		this.execAmt = execAmt;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getDataFlagDevice() {
		return dataFlagDevice;
	}

	public void setDataFlagDevice(String dataFlagDevice) {
		this.dataFlagDevice = dataFlagDevice;
	}

	public BigDecimal getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(BigDecimal payedAmt) {
		this.payedAmt = payedAmt;
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

	public BigDecimal getZbAmt() {
		return zbAmt;
	}

	public void setZbAmt(BigDecimal zbAmt) {
		this.zbAmt = zbAmt;
	}

	public BigDecimal getCntAmt() {
		return cntAmt;
	}

	public void setCntAmt(BigDecimal cntAmt) {
		this.cntAmt = cntAmt;
	}

	public BigDecimal getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(BigDecimal cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public BigDecimal[] getCancelAmts() {
		return cancelAmts;
	}

	public void setCancelAmts(BigDecimal[] cancelAmts) {
		this.cancelAmts = cancelAmts;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getNormalPayId() {
		return normalPayId;
	}

	public void setNormalPayId(String normalPayId) {
		this.normalPayId = normalPayId;
	}

	public String getAdvancePayId() {
		return advancePayId;
	}

	public void setAdvancePayId(String advancePayId) {
		this.advancePayId = advancePayId;
	}

	public String[] getAdvancePayIds() {
		return advancePayIds;
	}

	public void setAdvancePayIds(String[] advancePayIds) {
		this.advancePayIds = advancePayIds;
	}

	public BigDecimal getAdvancePayInvAmt() {
		return advancePayInvAmt;
	}

	public void setAdvancePayInvAmt(BigDecimal advancePayInvAmt) {
		this.advancePayInvAmt = advancePayInvAmt;
	}

	public BigDecimal[] getAdvancePayInvAmts() {
		return advancePayInvAmts;
	}

	public void setAdvancePayInvAmts(BigDecimal[] advancePayInvAmts) {
		this.advancePayInvAmts = advancePayInvAmts;
	}

	public BigDecimal getPayInvAmt() {
		return payInvAmt;
	}

	public void setPayInvAmt(BigDecimal payInvAmt) {
		this.payInvAmt = payInvAmt;
	}

	public BigDecimal[] getPayInvAmts() {
		return payInvAmts;
	}

	public void setPayInvAmts(BigDecimal[] payInvAmts) {
		this.payInvAmts = payInvAmts;
	}

	public BigDecimal getAdvSubId() {
		return advSubId;
	}

	public void setAdvSubId(BigDecimal advSubId) {
		this.advSubId = advSubId;
	}

	public BigDecimal[] getAdvSubIds() {
		return advSubIds;
	}

	public void setAdvSubIds(BigDecimal[] advSubIds) {
		this.advSubIds = advSubIds;
	}

	public BigDecimal getSubInvoiceAmt() {
		return subInvoiceAmt;
	}

	public void setSubInvoiceAmt(BigDecimal subInvoiceAmt) {
		this.subInvoiceAmt = subInvoiceAmt;
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

	public BigDecimal getCancelAmtTotal() {
		return cancelAmtTotal;
	}

	public void setCancelAmtTotal(BigDecimal cancelAmtTotal) {
		this.cancelAmtTotal = cancelAmtTotal;
	}

	public BigDecimal getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(BigDecimal freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public BigDecimal getPayAmtBefore() {
		return payAmtBefore;
	}

	public void setPayAmtBefore(BigDecimal payAmtBefore) {
		this.payAmtBefore = payAmtBefore;
	}

	public BigDecimal getSuspenseAmtBefore() {
		return suspenseAmtBefore;
	}

	public void setSuspenseAmtBefore(BigDecimal suspenseAmtBefore) {
		this.suspenseAmtBefore = suspenseAmtBefore;
	}

	public BigDecimal getSuspenseTotalAmt() {
		return suspenseTotalAmt;
	}

	public void setSuspenseTotalAmt(BigDecimal suspenseTotalAmt) {
		this.suspenseTotalAmt = suspenseTotalAmt;
	}

	public BigDecimal[] getFreezeAmtBefores() {
		return freezeAmtBefores;
	}

	public void setFreezeAmtBefores(BigDecimal[] freezeAmtBefores) {
		this.freezeAmtBefores = freezeAmtBefores;
	}

	public BigDecimal getFreezeAmtBefore() {
		return freezeAmtBefore;
	}

	public void setFreezeAmtBefore(BigDecimal freezeAmtBefore) {
		this.freezeAmtBefore = freezeAmtBefore;
	}

	public BigDecimal[] getFreezeAmtAdvBefores() {
		return freezeAmtAdvBefores;
	}

	public void setFreezeAmtAdvBefores(BigDecimal[] freezeAmtAdvBefores) {
		this.freezeAmtAdvBefores = freezeAmtAdvBefores;
	}

	public BigDecimal getFreezeAmtAdvBefore() {
		return freezeAmtAdvBefore;
	}

	public void setFreezeAmtAdvBefore(BigDecimal freezeAmtAdvBefore) {
		this.freezeAmtAdvBefore = freezeAmtAdvBefore;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public String getCreateDeptName() {
		return createDeptName;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String[] getPayIvrowMemos() {
		return payIvrowMemos;
	}

	public void setPayIvrowMemos(String[] payIvrowMemos) {
		this.payIvrowMemos = payIvrowMemos;
	}

	public String[] getAdvancePayIvrowMemos() {
		return advancePayIvrowMemos;
	}

	public void setAdvancePayIvrowMemos(String[] advancePayIvrowMemos) {
		this.advancePayIvrowMemos = advancePayIvrowMemos;
	}

	public String getIvrowMemo() {
		return ivrowMemo;
	}

	public void setIvrowMemo(String ivrowMemo) {
		this.ivrowMemo = ivrowMemo;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentTypeName() {
		return attachmentTypeName;
	}

	public void setAttachmentTypeName(String attachmentTypeName) {
		this.attachmentTypeName = attachmentTypeName;
	}

	public BigDecimal getInnerNo() {
		return innerNo;
	}

	public void setInnerNo(BigDecimal innerNo) {
		this.innerNo = innerNo;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
	}

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcmsPkuuid() {
		return icmsPkuuid;
	}

	public void setIcmsPkuuid(String icmsPkuuid) {
		this.icmsPkuuid = icmsPkuuid;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}
