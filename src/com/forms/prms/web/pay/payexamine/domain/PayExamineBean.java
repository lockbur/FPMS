package com.forms.prms.web.pay.payexamine.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PayExamineBean implements Serializable {
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
	private String payDataFlag;// 状态 00 录入01 退回02 待复核 03 财务中心退回 04 付款待确认06
								// 付款确认完成10 付款成功11
								// 付款回冲"
	private String dataFlagInvoice;// 发票状态 0-录入 2-支付成功 3-回冲
	private String dataFlagPay;// 付款状态 0-录入 2-支付成功 3-回冲
	private String instDutyCode;// 创建责任中心

	private List<String> instDutyCodes;// 责任中心集合

	private String payCondition;// 付款条件

	private String payMode;// 支付方式

	private String payModeName;// 支付方式名称

	private String createDept;// 付款机构

	private String batchNo;// 批次号

	private String mainCnt;// 主件数

	private String attachCnt;// 总页数
	
	private String mainCntOk;// 实际主件数

	private String attachCntOk;// 实际总页数
	
    private String uploadTime; //影像上传时间
    
    private String auditTime; //付款扫描复核时间
	
	private String dataFlag;// 扫描状态

	private String instOper;// 扫描用户
	
	private String instOperOuCode;//扫描用户的oucode
	
	private String isEnable;//是否可以查看扫描付款单的明细

	private String instDate;// 扫描日期

	private String instTime;// 扫描时间

	private String dataFlagName;// 扫描状态值

	private String instOrg21Code;// 扫描柜员所属的二级行（目前扫描柜员是设置在二级行的）

	private String orgCode;

	private String memo;

	private String innerId;

	private String auditMemo; // 审批意见

	private String org2CodeOri;

	private String befDate; // 起始日期
	private String aftDate; // 结束日期

	private String isCreditNote;// 是否为贷项通知单(0:是 1:否)
	private String isOrder;// 是否生成订单 0-是 1-否
	private String cancelAmtTotal;// 已核销金额

	private BigDecimal addTaxAmt;// 增值税金额
	private String cntName;// 
	private String cntTypeName;// 
	
	private String isFrozenBgt;//付款是否冻结物料预算：0-是 1-否 (默认未冻结，在付款冻结预算存储过程更新)
	private String feeDept;
	private String feeDeptName;
	
	private String payExamineUser; //PAY_EXAMINE_USER 付款复核人
	private String paySureUser;    //PAY_SURE_USER 财务中心审核人
	
	//营改增
	private BigDecimal cntTaxAmt;//税额
	
	private BigDecimal cntAllAmt;//合同总金额
	private String invoiceIdBlue;//原蓝字发票编号
	private BigDecimal invoiceAmtNotax; //不含税金额
	private BigDecimal invoiceAmtTax;//税额
	
	private String taxCode;
	
	private BigDecimal taxAmt;
	
	private BigDecimal payedAmtTax;
	
	private BigDecimal freezeAmtTax;
	
	private BigDecimal advancePayInvAmts;
	
	private BigDecimal advPayAddTaxAmts;
	
	private BigDecimal advancePayIvrowMemos;
	
	private BigDecimal payInvAmts;
	
	private BigDecimal payAddTaxAmts;

	private BigDecimal payIvrowMemos;
	
	private String tableName;
	
	
	
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getInvoiceIdBlue() {
		return invoiceIdBlue;
	}

	public void setInvoiceIdBlue(String invoiceIdBlue) {
		this.invoiceIdBlue = invoiceIdBlue;
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

	public void setAdvancePayInvAmts(BigDecimal advancePayInvAmts) {
		this.advancePayInvAmts = advancePayInvAmts;
	}

	public BigDecimal getAdvPayAddTaxAmts() {
		return advPayAddTaxAmts;
	}

	public void setAdvPayAddTaxAmts(BigDecimal advPayAddTaxAmts) {
		this.advPayAddTaxAmts = advPayAddTaxAmts;
	}

	public BigDecimal getAdvancePayIvrowMemos() {
		return advancePayIvrowMemos;
	}

	public void setAdvancePayIvrowMemos(BigDecimal advancePayIvrowMemos) {
		this.advancePayIvrowMemos = advancePayIvrowMemos;
	}

	public BigDecimal getPayInvAmts() {
		return payInvAmts;
	}

	public void setPayInvAmts(BigDecimal payInvAmts) {
		this.payInvAmts = payInvAmts;
	}

	public BigDecimal getPayAddTaxAmts() {
		return payAddTaxAmts;
	}

	public void setPayAddTaxAmts(BigDecimal payAddTaxAmts) {
		this.payAddTaxAmts = payAddTaxAmts;
	}

	public BigDecimal getPayIvrowMemos() {
		return payIvrowMemos;
	}

	public void setPayIvrowMemos(BigDecimal payIvrowMemos) {
		this.payIvrowMemos = payIvrowMemos;
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

	public BigDecimal getCntAllAmt() {
		return cntAllAmt;
	}

	public void setCntAllAmt(BigDecimal cntAllAmt) {
		this.cntAllAmt = cntAllAmt;
	}

	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public String getPayExamineUser() {
		return payExamineUser;
	}

	public void setPayExamineUser(String payExamineUser) {
		this.payExamineUser = payExamineUser;
	}

	public String getPaySureUser() {
		return paySureUser;
	}

	public void setPaySureUser(String paySureUser) {
		this.paySureUser = paySureUser;
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

	public String getIsFrozenBgt() {
		return isFrozenBgt;
	}

	public void setIsFrozenBgt(String isFrozenBgt) {
		this.isFrozenBgt = isFrozenBgt;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
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

	public BigDecimal getAddTaxAmt() {
		return addTaxAmt;
	}

	public void setAddTaxAmt(BigDecimal addTaxAmt) {
		this.addTaxAmt = addTaxAmt;
	}

	public String getCancelAmtTotal() {
		return cancelAmtTotal;
	}

	public void setCancelAmtTotal(String cancelAmtTotal) {
		this.cancelAmtTotal = cancelAmtTotal;
	}

	public String getIsCreditNote() {
		return isCreditNote;
	}

	public void setIsCreditNote(String isCreditNote) {
		this.isCreditNote = isCreditNote;
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

	public String getOrg2CodeOri() {
		return org2CodeOri;
	}

	public void setOrg2CodeOri(String org2CodeOri) {
		this.org2CodeOri = org2CodeOri;
	}

	/*** 扫描 **/
	private String id;

	private String icmsPkuuid;

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

	public String getPayModeName() {
		return payModeName;
	}

	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}

	public String getInstOrg21Code() {
		return instOrg21Code;
	}

	public void setInstOrg21Code(String instOrg21Code) {
		this.instOrg21Code = instOrg21Code;
	}

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
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

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getMainCnt() {
		return mainCnt;
	}

	public void setMainCnt(String mainCnt) {
		this.mainCnt = mainCnt;
	}

	public String getAttachCnt() {
		return attachCnt;
	}

	public void setAttachCnt(String attachCnt) {
		this.attachCnt = attachCnt;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
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

	public List<String> getInstDutyCodes() {
		return instDutyCodes;
	}

	public void setInstDutyCodes(List<String> instDutyCodes) {
		this.instDutyCodes = instDutyCodes;
	}

	private String cntType;// 合同类型

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

	private String advancePayId;// 预付款批次

	private String cancelAmt;// 已核销金额

	private String execAmt;// 执行金额

	private String provActCurr;// 币别

	private String freezeTotalAmt;// 总冻结金额

	private String suspenseTotalAmt;// 暂收总金额

	private String ivrowMemo;// 发票行说明

	private String freezeAmt;// 冻结金额

	public String getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public String getIvrowMemo() {
		return ivrowMemo;
	}

	public void setIvrowMemo(String ivrowMemo) {
		this.ivrowMemo = ivrowMemo;
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

	public String getProvActCurr() {
		return provActCurr;
	}

	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}

	public String getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(String execAmt) {
		this.execAmt = execAmt;
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

	public String getIsPrePay() {
		return isPrePay;
	}

	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInnerId() {
		return innerId;
	}

	public void setInnerId(String innerId) {
		this.innerId = innerId;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMainCntOk() {
		return mainCntOk;
	}

	public void setMainCntOk(String mainCntOk) {
		this.mainCntOk = mainCntOk;
	}

	public String getAttachCntOk() {
		return attachCntOk;
	}

	public void setAttachCntOk(String attachCntOk) {
		this.attachCntOk = attachCntOk;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getInstOperOuCode() {
		return instOperOuCode;
	}

	public void setInstOperOuCode(String instOperOuCode) {
		this.instOperOuCode = instOperOuCode;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
}
