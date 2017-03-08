package com.forms.prms.web.pay.payquery.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PayQueryBean implements Serializable {
	private static final long serialVersionUID = 1047080944938388171L;

	/*** TD_PAY表 **/
	private String payId;// 付款单号 5位ou+8位日期+1位标志+序号
	private String cntNum;// 合同编号 BOC+一级分行机构号+yyyymmdd+六位序号+_责任中心
	private String invoiceId;// 发票号
	private BigDecimal attachmentNum;// 附件张数
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
	private BigDecimal uncleanAmt;// 未结清金额
	private BigDecimal payTotalAmt;// 付款累计金额
	private String payDataFlag;// 状态 00 录入01 退回02 待复核04 付款待确认06 付款确认完成10 付款成功11
								// 付款回冲"
	private String dataFlagInvoice;// 发票状态 0-录入 2-支付成功 3-回冲
	private String dataFlagPay;// 付款状态 0-录入 2-支付成功 3-回冲
	private String instDutyCode;// 创建责任中心
	private String signDate;// 签订日期
	private String dataFlag;// 合同状态
	private String createDate;// 创建日期
	private List<String> instDutyCodes;// 责任中心集合

	private String cntType;// 合同类型
	private String cntTypeName;// 
	private String isOrder;// 是否订单
	private String cntName;// 
	private String providerName;// 供应商名称

	private String isPrePay;// 表标识

	private String cntAmt;// 合同金额

	private String zbAmt;// 质保金

	private String normarlTotalAmt;// 正常付款金额 ap文件回盘后增加

	private String advanceTotalAmt;// 预付款总金额 ap文件回盘后增加，核销文件回盘后减少

	private String providerType;// 供应商类型

	private String provActNo;// 收款账号

	private String bankName;// 开户行

	private String cglCode;// 核算码

	private String projName;// 项目名称

	private String matrName;// 物料类型

	private String deviceModel;// 设备型号

	private String deviceModelName;// 设备型号名称

	private String payedAmt;// 已付金额

	private String subInvoiceAmt;// 发票分配金额

	private String execNum;// 数量

	private String execPrice;// 单价

	private String lxlx;// 审批类型

	private String stockNum;// 采集编号

	private String payDataFlagText;// 付款状态文本值

	private String dataFlagText;// 结清表中状态

	private String cntDateFlagText;// 合同状态文本值

	private String createDept;// 创建机构

	private String tabsIndex; // 页签标志

	private String cleanProject;// 暂收项目

	private String cleanAmt;// 结清金额

	private String cleanMemo;// 结清说明

	private String cleanReason;// 结清原因

	private String advancePayId;// 预付款核销单号

	private String cancelAmt;// 核销金额

	private String cleanPayId;// 暂收结清编号

	private String dataFlagName;// 付款状态文本

	private String befDate;// 前时间

	private String aftDate;// 后时间

	private String execAmt;// 执行金额

	private String provActCurr;// 币别

	private String freezeTotalAmt;// 冻结金额

	private String suspenseTotalAmt;// 暂收总金额

	private String payCondition;// 付款条件

	private String ivrowMemo;// 发票行说明

	private String freezeAmt;// 冻结金额

	private String payMode;// 付款方式

	private String payModeName;// 付款方式名称

	private String org1Code;// 一级分行名称
	
	private String org2Code;// 二级分行
	
	private String dutyCode;// 责任中心

	private String payCancelState;// 付款是否取消

	private String flag;

	private String id;// 已扫描合同号

	private String isCreditNote;// 是否为贷项通知单(0:是 1:否)

	private String ouCode;// 所属财务中心代码

	private String ouName;// 所属财务中心名称

	private String batchNo;// 批次号
	private BigDecimal seqNo;// 序号
	private String imageId;// 影像ID
	private String payCancelDate;// 付款取消日期
	private String useFlag;// 状态 0-无效 1-有效
	private String erpPayId;// erp的付款单

	private String cancelAmtTotal;// 已核销金额
	private String buttonFlag;// 按钮标志，根据其值显示相应按钮

	private String fromFlag;// 调用方法来源
	
	private BigDecimal addTaxAmt;// 增值税金额
	private String feeDept;
	private String feeDeptName;
	
	private String orgFlag;
	
	private String invoiceIdBlue;//原蓝字发票编号
	private BigDecimal invoiceAmtNotax;//发票不含税金额
	private BigDecimal invoiceAmtTax;//发票税额
	
	//营改增
	private BigDecimal cntTaxAmt;//税额
	
	private BigDecimal cntAllAmt;//合同总金额
	

	
	private String taxCode;
	
	private BigDecimal taxAmt;
	
	private BigDecimal payedAmtTax;
	
	private BigDecimal freezeAmtTax;
	
	private BigDecimal advancePayInvAmts;
	
	private BigDecimal advPayAddTaxAmts;
	
	private String  advancePayIvrowMemos;
	
	private BigDecimal payInvAmts;
	
	private BigDecimal payAddTaxAmts;

	private String  payIvrowMemos;
	
	private String conCglCode;
	
	private String inCglCode;//入账核算码
	private String progRess;//合同进度
	
	
	
	
	
	
	
	public String getProgRess() {
		return progRess;
	}

	public void setProgRess(String progRess) {
		this.progRess = progRess;
	}

	public String getInCglCode() {
		return inCglCode;
	}

	public void setInCglCode(String inCglCode) {
		this.inCglCode = inCglCode;
	}

	public String getConCglCode() {
		return conCglCode;
	}

	public void setConCglCode(String conCglCode) {
		this.conCglCode = conCglCode;
	}

	public BigDecimal getAdvPayAddTaxAmts() {
		return advPayAddTaxAmts;
	}

	public void setAdvPayAddTaxAmts(BigDecimal advPayAddTaxAmts) {
		this.advPayAddTaxAmts = advPayAddTaxAmts;
	}

	public String getAdvancePayIvrowMemos() {
		return advancePayIvrowMemos;
	}

	public void setAdvancePayIvrowMemos(String advancePayIvrowMemos) {
		this.advancePayIvrowMemos = advancePayIvrowMemos;
	}

	public BigDecimal getPayAddTaxAmts() {
		return payAddTaxAmts;
	}

	public void setPayAddTaxAmts(BigDecimal payAddTaxAmts) {
		this.payAddTaxAmts = payAddTaxAmts;
	}

	public String getPayIvrowMemos() {
		return payIvrowMemos;
	}

	public void setPayIvrowMemos(String payIvrowMemos) {
		this.payIvrowMemos = payIvrowMemos;
	}

	public void setAdvancePayInvAmts(BigDecimal advancePayInvAmts) {
		this.advancePayInvAmts = advancePayInvAmts;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(BigDecimal taxAmt) {
		this.taxAmt = taxAmt;
	}

	public BigDecimal getPayedAmtTax() {
		return payedAmtTax;
	}

	public void setPayedAmtTax(BigDecimal payedAmtTax) {
		this.payedAmtTax = payedAmtTax;
	}

	public BigDecimal getFreezeAmtTax() {
		return freezeAmtTax;
	}

	public void setFreezeAmtTax(BigDecimal freezeAmtTax) {
		this.freezeAmtTax = freezeAmtTax;
	}

	public BigDecimal getAdvancePayInvAmts() {
		return advancePayInvAmts;
	}

	

	public BigDecimal getPayInvAmts() {
		return payInvAmts;
	}

	public void setPayInvAmts(BigDecimal payInvAmts) {
		this.payInvAmts = payInvAmts;
	}

	
	
	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public BigDecimal getCntAllAmt() {
		return cntAllAmt;
	}

	public void setCntAllAmt(BigDecimal cntAllAmt) {
		this.cntAllAmt = cntAllAmt;
	}

	
	
	
	

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
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

	public BigDecimal getPayTotalAmt() {
		return payTotalAmt;
	}

	public void setPayTotalAmt(BigDecimal payTotalAmt) {
		this.payTotalAmt = payTotalAmt;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getFeeDeptName() {
		return feeDeptName;
	}

	public void setFeeDeptName(String feeDeptName) {
		this.feeDeptName = feeDeptName;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public BigDecimal getAddTaxAmt() {
		return addTaxAmt;
	}

	public void setAddTaxAmt(BigDecimal addTaxAmt) {
		this.addTaxAmt = addTaxAmt;
	}

	public String getFromFlag() {
		return fromFlag;
	}

	public void setFromFlag(String fromFlag) {
		this.fromFlag = fromFlag;
	}

	public String getButtonFlag() {
		return buttonFlag;
	}

	public void setButtonFlag(String buttonFlag) {
		this.buttonFlag = buttonFlag;
	}

	public String getCancelAmtTotal() {
		return cancelAmtTotal;
	}

	public void setCancelAmtTotal(String cancelAmtTotal) {
		this.cancelAmtTotal = cancelAmtTotal;
	}

	public String getErpPayId() {
		return erpPayId;
	}

	public void setErpPayId(String erpPayId) {
		this.erpPayId = erpPayId;
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getPayCancelDate() {
		return payCancelDate;
	}

	public void setPayCancelDate(String payCancelDate) {
		this.payCancelDate = payCancelDate;
	}

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	public String getOuCode() {
		return ouCode;
	}

	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getIsCreditNote() {
		return isCreditNote;
	}

	public void setIsCreditNote(String isCreditNote) {
		this.isCreditNote = isCreditNote;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
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

	private String icmsPkuuid;//

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPayCancelState() {
		return payCancelState;
	}

	public void setPayCancelState(String payCancelState) {
		this.payCancelState = payCancelState;
	}

	public BigDecimal getUncleanAmt() {
		return uncleanAmt;
	}

	public void setUncleanAmt(BigDecimal uncleanAmt) {
		this.uncleanAmt = uncleanAmt;
	}

	public String getPayModeName() {
		return payModeName;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayCondition() {
		return payCondition;
	}

	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}

	public String getIvrowMemo() {
		return ivrowMemo;
	}

	public void setIvrowMemo(String ivrowMemo) {
		this.ivrowMemo = ivrowMemo;
	}

	public String getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String getFreezeTotalAmt() {
		return freezeTotalAmt;
	}

	public void setFreezeTotalAmt(String freezeTotalAmt) {
		this.freezeTotalAmt = freezeTotalAmt;
	}

	public String getSuspenseTotalAmt() {
		return suspenseTotalAmt;
	}

	public void setSuspenseTotalAmt(String suspenseTotalAmt) {
		this.suspenseTotalAmt = suspenseTotalAmt;
	}

	public String getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(String execAmt) {
		this.execAmt = execAmt;
	}

	public String getProvActCurr() {
		return provActCurr;
	}

	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
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

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
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

	public List<String> getInstDutyCodes() {
		return instDutyCodes;
	}

	public void setInstDutyCodes(List<String> instDutyCodes) {
		this.instDutyCodes = instDutyCodes;
	}

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getDataFlagText() {
		return dataFlagText;
	}

	public void setDataFlagText(String dataFlagText) {
		this.dataFlagText = dataFlagText;
	}

	public String getCleanPayId() {
		return cleanPayId;
	}

	public void setCleanPayId(String cleanPayId) {
		this.cleanPayId = cleanPayId;
	}

	public String getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(String cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public String getAdvancePayId() {
		return advancePayId;
	}

	public void setAdvancePayId(String advancePayId) {
		this.advancePayId = advancePayId;
	}

	public String getCleanProject() {
		return cleanProject;
	}

	public void setCleanProject(String cleanProject) {
		this.cleanProject = cleanProject;
	}

	public String getCleanAmt() {
		return cleanAmt;
	}

	public void setCleanAmt(String cleanAmt) {
		this.cleanAmt = cleanAmt;
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

	public String getCntDateFlagText() {
		return cntDateFlagText;
	}

	public void setCntDateFlagText(String cntDateFlagText) {
		this.cntDateFlagText = cntDateFlagText;
	}

	public String getPayDataFlagText() {
		return payDataFlagText;
	}

	public void setPayDataFlagText(String payDataFlagText) {
		this.payDataFlagText = payDataFlagText;
	}

	public String getTabsIndex() {
		return tabsIndex;
	}

	public void setTabsIndex(String tabsIndex) {
		this.tabsIndex = tabsIndex;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public String getLxlx() {
		return lxlx;
	}

	public void setLxlx(String lxlx) {
		this.lxlx = lxlx;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
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

	public String getPayDataFlag() {
		return payDataFlag;
	}

	public void setPayDataFlag(String payDataFlag) {
		this.payDataFlag = payDataFlag;
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getIsPrePay() {
		return isPrePay;
	}

	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}

	public String getCntAmt() {
		return cntAmt;
	}

	public void setCntAmt(String cntAmt) {
		this.cntAmt = cntAmt;
	}

	public String getZbAmt() {
		return zbAmt;
	}

	public void setZbAmt(String zbAmt) {
		this.zbAmt = zbAmt;
	}

	public String getNormarlTotalAmt() {
		return normarlTotalAmt;
	}

	public void setNormarlTotalAmt(String normarlTotalAmt) {
		this.normarlTotalAmt = normarlTotalAmt;
	}

	public String getAdvanceTotalAmt() {
		return advanceTotalAmt;
	}

	public void setAdvanceTotalAmt(String advanceTotalAmt) {
		this.advanceTotalAmt = advanceTotalAmt;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getProvActNo() {
		return provActNo;
	}

	public void setProvActNo(String provActNo) {
		this.provActNo = provActNo;
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

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(String payedAmt) {
		this.payedAmt = payedAmt;
	}

	public String getSubInvoiceAmt() {
		return subInvoiceAmt;
	}

	public void setSubInvoiceAmt(String subInvoiceAmt) {
		this.subInvoiceAmt = subInvoiceAmt;
	}

	public String getExecNum() {
		return execNum;
	}

	public void setExecNum(String execNum) {
		this.execNum = execNum;
	}

	public String getExecPrice() {
		return execPrice;
	}

	public void setExecPrice(String execPrice) {
		this.execPrice = execPrice;
	}

	public String getInvoiceIdBlue() {
		return invoiceIdBlue;
	}

	public void setInvoiceIdBlue(String invoiceIdBlue) {
		this.invoiceIdBlue = invoiceIdBlue;
	}

	public BigDecimal getInvoiceAmtNotax() {
		return invoiceAmtNotax;
	}

	public void setInvoiceAmtNotax(BigDecimal invoiceAmtNotax) {
		this.invoiceAmtNotax = invoiceAmtNotax;
	}

	public BigDecimal getInvoiceAmtTax() {
		return invoiceAmtTax;
	}

	public void setInvoiceAmtTax(BigDecimal invoiceAmtTax) {
		this.invoiceAmtTax = invoiceAmtTax;
	}
	
	

}
