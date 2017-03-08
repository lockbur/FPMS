package com.forms.prms.web.pay.payAdd.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : lisj <br>
 * date : 2015-01-26<br>
 * 合同付款新增Bean
 */
public class PayAddBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1047080944938388171L;

	/** TD_CNT表 **/
	private String cntNum;// 合同编号 BOC+一级分行机构号+yyyymmdd+六位序号+_责任中心
	private String org1Code;// 所属一级行机构号
	private String psbh;// 评审编号
	private String stockNum;// 集采编号
	private String qbh;// 签文报号
	private String lxlx;// 审批类别 1：电子审批;2：签报立项;3：部内审批；
	private String lxlxName;// 审批类别名称
	private BigDecimal lxsl;// 审批数量 （电子审批数量小计）
	private BigDecimal lxje;// 审批金额 （电子审批金额小计）
	private BigDecimal cntAmt;// 合同金额
	private BigDecimal totalNum;// 采购数量
	private String cntType;// 类别 0-资产类 1-费用类
	private String cntTypeName;// 类别 0-资产类 1-费用类
	private String feeType;// 费用类型 0-金额固定、受益期固定 1-受益期固定、合同金额不固定 2-受益期不固定，付款时确认费用
	private String userId;
	private String dateTime;
	
	
	// 3-宣传费
	private String feeTypeName;// 费用类型名称
	private String feeSubType;// 费用子类型 0-普通费用类 1-房屋租赁类
	private String feeSubTypeName;// 费用子类型名称
	private String feeStartDate;// 合同受益起始日期
	private String feeEndDate;// 合同受益终止日期
	private BigDecimal feeAmt;// 合同金额确认部分
	private BigDecimal feePenalty;// 合同金额约定罚金
	private String signDate;// 签订日期/采购日期
	private String isProvinceBuy;// 省行统购项目 0-是 1-否
	private BigDecimal zbAmt;// 质保金
	private String payDutyCode;// 付款责任中心 录入时为发起人责任中心，接收移交后更新
	private String deliverDutyCode;// 移交后责任中心 录入时为空，提出移交后更新
	private String providerCode;// 供应商编号
	private String providerName; // 供应商名称
	private String providerAddr;// 供应商地点名称
	private String actName;// 收款账户名称
	private String bankInfo;// 开户行详细信息
	private String bankCode;// 开户行行号
	private String bankArea;// 银行地区码
	private String actType;// 账户类型
	private String provActNo;// 银行账户编号
	private String provActCurr;// 银行账户币种 RMB
	private String srcPoviderName;// 原始供应商名称 供应商是内部供应商记录所对应的外部供应商名字，可以录入
	private String payTerm;// 付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款 3-分期付款
	private String payTermName;// 付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款
								// 2-合同完毕后一次性付款3-分期付款
	private String stageType;// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
	private String memo;// 备注
	private String currency;// 币别 默认为CNY
	private String isOrder;// 是否生成订单 0-是 1-否
	private String dataFlag;// 合同状态
	private String dataFlagName; // 合同状态名字
	private String createDate;// 创建日期
	private String createDept;// 创建部门
	private String befDate; // 签订日期区间：起始日期
	private String aftDate; // 签订日期区间：结束日期
	private String dutyCode;// 责任中心
	private String dutyName;// 责任中心名称
	private String cntName;//
	private String oncePrepaid;// 是否一次待摊 0-是 1：否 （合同终止时更新）
	private String stopDate;// 合同终止日期
	private String bgtType;//
	private BigDecimal cancelAmtTotal;//
	private BigDecimal payTotalAmt;// 付款累计金额
	/*** TD_PAY表 **/
	private String payId;// 付款单号 5位ou+8位日期+1位标志+序号
	private String invoiceId;// 发票号
	private BigDecimal attachmentNum;// 附件张数
	private String invoiceMemo;// 发票说明
	private BigDecimal invoiceAmt;// 发票金额 发票金额=暂收金额+付款金额+预付款核销金额
	private BigDecimal advanceCancelAmt;// 预付款核销金额
	private BigDecimal payAmt;// 付款金额
	// private BigDecimal payAmt_;// 付款金额
	private String payDate;// 付款日期
	private BigDecimal suspenseAmt;// 暂收金额
	private String suspenseDate;// 暂收付款日期
	private String suspenseName;// 暂收名称
	private String suspenseReason;// 挂帐原因
	private BigDecimal suspensePeriod;// 预计处理时间 月份
	private BigDecimal susTotalAmt;// 累计结清金额
	private String payDataFlag;// 状态 00 录入01 退回02 待复核04 付款待确认06 付款确认完成10 付款成功11
								// 付款回冲"
	private String payDataFlagName;
	private String dataFlagInvoice;// 发票状态 0-录入 2-支付成功 3-回冲
	private String dataFlagInvoiceName;
	private String dataFlagPay;// 付款状态 0-录入 2-支付成功 3-回冲
	private String dataFlagPayName;
	private String instDutyCode;// 创建责任中心
	private String instDutyName;
	private String instUser; // 创建人
	private String instDate;// 创建日期
	private String instTime;// 创建时间
	private String payMode;// 付款方式 默认为供应商表的

	private String bank_name_act_no;// 供应商开户行及账号

	private String isCreditNote;// 是否为贷项通知单(0:是 1:否)
	private String isPrepaidProvision;// 是否预提待摊(0:是 1:否)

	/** TD_CNT_FKJD表 **/
	private BigDecimal subId;// 子序号
	private BigDecimal[] subIds;// 子序号数组
	private BigDecimal jdzf;// 付款金额
	private BigDecimal jdtj;// 进度
	private String jdDate;// 付款日期

	private String tabsIndex; // 页签标志
	private String bankName;// 银行名称

	private String ouCode; // OU
	private String payFlag;// 0:预付款，1：正常付款
	private BigDecimal cancelAmt;// 核销金额
	private BigDecimal[] cancelAmts;// 核销金额数组
	private String table;// 表名
	private String cglCode;// 核算码
	private String projName;// 项目名称
	private String matrName;// 物料名称（设备类型）
	private String deviceModelName;// 设备型号名称
	private BigDecimal payedAmt;// 已支付金额
	private BigDecimal execAmt;// 执行金额（设备总金额）
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
	private BigDecimal suspenseTotalAmt;// 暂收金额
	private BigDecimal freezeAmt;// 设备冻结金额
	/** TD_PAY_CLEAN **/
	private String cleanPayId;// 暂收结清编号
	private String cleanProject;// 结清项目
	private BigDecimal cleanAmt;// 结清金额 业务人员录入时提供
	private BigDecimal cleanAmtFms;// 结清金额 FMS付款文件中提供
	private String cleanMemo;// 结清说明
	private String cleanReason;// 结清原因
	private String payType;
	private BigDecimal cleanAmtIngTotal;// 正在暂收结清的总金额
	private String providerType;// 供应商类型
	private BigDecimal cglCodeAmt;//核算码总金额
	private String[] payIvrowMemos;// 正常付款设备发票行说明
	private String[] advancePayIvrowMemos;// 预付款设备发票行说明
	private String ivrowMemo;// 发票行说明

	private String attachmentType;// 附件类型
	private String attachmentTypeName;// 附件类型名称

	private String flag;// 返回标识
	private String feeDept;
	private String feeDeptName;

	private String sDate;// 日期
	private BigDecimal execPrice;
	private BigDecimal poNumber;
	private BigDecimal poLineno;
	private BigDecimal execNum;
	/*** 付款log **/
	private BigDecimal innerNo;// +审批序号
	private String operMemo;// 操作说明
	private String instOper;// 操作柜员
	private String advCancelPayIdStrs;

	/*** 扫描 **/
	private String id;

	private String icmsPkuuid;

	private String buttonFlag;// 按钮标志，根据其值显示相应按钮

	private BigDecimal bgtOverdraw;// 超支预算

	private String providerAddrCode;
	private String isModify;
	
	private BigDecimal invoiceAmtNotax; //不含税金额
	private BigDecimal invoiceAmtTax;//税额
	private BigDecimal invoiceAmtLeft;//INVOICE_AMT_LEFT
	
	private BigDecimal addTaxAmtLeft;//剩余可冲销税额
	private BigDecimal subInvoiceAmtLeft;//剩余可冲销不含税金额
	private String invoiceIdBlue;//原蓝字发票编码
	private String payIdBlue;//原蓝字发票付款单号
	private String taxCode;//税码
	private String isGdzc;//是否固定资产
	
	private  String matrCode;
	
	
	
	
	private BigDecimal payAmtTotal;//预付款总额
	
	//营改增
	private BigDecimal cntAllAmt;//合同总金额
	private BigDecimal cntTaxAmt;//税额
	private BigDecimal taxRate;//税率
	private String deductFlag;//Y-可抵扣 N-不可抵扣
	private BigDecimal taxAmt;//总税额
	private BigDecimal payedAmtTax;//已支付税额
	private BigDecimal freezeAmtTax;//冻结税额
	
	private BigDecimal totalDeviceAmt;//设备金额合计（不含税+税额）
	private BigDecimal totalPayedAmt;//设备已付金额合计（不含税+税额）
	private BigDecimal totalFreezeAmt;//设备冻结金额合计（不含税+税额）
	
		
	private BigDecimal days;//
	private BigDecimal addTaxAmt;// 增值税金额
	private BigDecimal[] payAddTaxAmts;// 正常付款增值税金额
	private BigDecimal[] advPayAddTaxAmts;// 预付款增值税金额
	
	private String hasTaxrow;//是否产生税行

	private BigDecimal acUpdInvoiceAmtLeft;
	private BigDecimal acUpdTaxAmtLeft;
	
	

	

	public BigDecimal getCglCodeAmt() {
		return cglCodeAmt;
	}

	public void setCglCodeAmt(BigDecimal cglCodeAmt) {
		this.cglCodeAmt = cglCodeAmt;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public BigDecimal getAcUpdInvoiceAmtLeft() {
		return acUpdInvoiceAmtLeft;
	}

	public void setAcUpdInvoiceAmtLeft(BigDecimal acUpdInvoiceAmtLeft) {
		this.acUpdInvoiceAmtLeft = acUpdInvoiceAmtLeft;
	}

	public BigDecimal getAcUpdTaxAmtLeft() {
		return acUpdTaxAmtLeft;
	}

	public void setAcUpdTaxAmtLeft(BigDecimal acUpdTaxAmtLeft) {
		this.acUpdTaxAmtLeft = acUpdTaxAmtLeft;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalDeviceAmt() {
		return totalDeviceAmt;
	}

	public void setTotalDeviceAmt(BigDecimal totalDeviceAmt) {
		this.totalDeviceAmt = totalDeviceAmt;
	}

	public BigDecimal getTotalPayedAmt() {
		return totalPayedAmt;
	}

	public void setTotalPayedAmt(BigDecimal totalPayedAmt) {
		this.totalPayedAmt = totalPayedAmt;
	}

	public BigDecimal getTotalFreezeAmt() {
		return totalFreezeAmt;
	}

	public void setTotalFreezeAmt(BigDecimal totalFreezeAmt) {
		this.totalFreezeAmt = totalFreezeAmt;
	}

	public String getPayIdBlue() {
		return payIdBlue;
	}

	public void setPayIdBlue(String payIdBlue) {
		this.payIdBlue = payIdBlue;
	}

	public String getIsGdzc() {
		return isGdzc;
	}

	public void setIsGdzc(String isGdzc) {
		this.isGdzc = isGdzc;
	}

	
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getDeductFlag() {
		return deductFlag;
	}

	public void setDeductFlag(String deductFlag) {
		this.deductFlag = deductFlag;
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

	public BigDecimal getInvoiceAmtLeft() {
		return invoiceAmtLeft;
	}

	public void setInvoiceAmtLeft(BigDecimal invoiceAmtLeft) {
		this.invoiceAmtLeft = invoiceAmtLeft;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getSubInvoiceAmtLeft() {
		return subInvoiceAmtLeft;
	}

	public void setSubInvoiceAmtLeft(BigDecimal subInvoiceAmtLeft) {
		this.subInvoiceAmtLeft = subInvoiceAmtLeft;
	}

	public String getInvoiceIdBlue() {
		return invoiceIdBlue;
	}

	public void setInvoiceIdBlue(String invoiceIdBlue) {
		this.invoiceIdBlue = invoiceIdBlue;
	}

	public BigDecimal getPayAmtTotal() {
		return payAmtTotal;
	}

	public void setPayAmtTotal(BigDecimal payAmtTotal) {
		this.payAmtTotal = payAmtTotal;
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

	public BigDecimal getAddTaxAmtLeft() {
		return addTaxAmtLeft;
	}

	public void setAddTaxAmtLeft(BigDecimal addTaxAmtLeft) {
		this.addTaxAmtLeft = addTaxAmtLeft;
	}

	public BigDecimal getPayTotalAmt() {
		return payTotalAmt;
	}

	public void setPayTotalAmt(BigDecimal payTotalAmt) {
		this.payTotalAmt = payTotalAmt;
	}

	public String getFeeDeptName() {
		return feeDeptName;
	}

	public void setFeeDeptName(String feeDeptName) {
		this.feeDeptName = feeDeptName;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getAdvCancelPayIdStrs() {
		return advCancelPayIdStrs;
	}

	public void setAdvCancelPayIdStrs(String advCancelPayIdStrs) {
		this.advCancelPayIdStrs = advCancelPayIdStrs;
	}

	public BigDecimal getExecNum() {
		return execNum;
	}

	public void setExecNum(BigDecimal execNum) {
		this.execNum = execNum;
	}

	public BigDecimal getExecPrice() {
		return execPrice;
	}

	public void setExecPrice(BigDecimal execPrice) {
		this.execPrice = execPrice;
	}

	public BigDecimal getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(BigDecimal poNumber) {
		this.poNumber = poNumber;
	}

	public BigDecimal getPoLineno() {
		return poLineno;
	}

	public void setPoLineno(BigDecimal poLineno) {
		this.poLineno = poLineno;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getProviderAddrCode() {
		return providerAddrCode;
	}

	public void setProviderAddrCode(String providerAddrCode) {
		this.providerAddrCode = providerAddrCode;
	}

	public BigDecimal getCancelAmtTotal() {
		return cancelAmtTotal;
	}

	public void setCancelAmtTotal(BigDecimal cancelAmtTotal) {
		this.cancelAmtTotal = cancelAmtTotal;
	}

	public String getBgtType() {
		return bgtType;
	}

	public void setBgtType(String bgtType) {
		this.bgtType = bgtType;
	}

	public BigDecimal getBgtOverdraw() {
		return bgtOverdraw;
	}

	public void setBgtOverdraw(BigDecimal bgtOverdraw) {
		this.bgtOverdraw = bgtOverdraw;
	}

	public String getOncePrepaid() {
		return oncePrepaid;
	}

	public void setOncePrepaid(String oncePrepaid) {
		this.oncePrepaid = oncePrepaid;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getIsPrepaidProvision() {
		return isPrepaidProvision;
	}

	public void setIsPrepaidProvision(String isPrepaidProvision) {
		this.isPrepaidProvision = isPrepaidProvision;
	}


	public BigDecimal getDays() {
		return days;
	}

	public void setDays(BigDecimal days) {
		this.days = days;
	}

	public BigDecimal getAddTaxAmt() {
		return addTaxAmt;
	}

	public void setAddTaxAmt(BigDecimal addTaxAmt) {
		this.addTaxAmt = addTaxAmt;
	}

	public BigDecimal[] getPayAddTaxAmts() {
		return payAddTaxAmts;
	}

	public void setPayAddTaxAmts(BigDecimal[] payAddTaxAmts) {
		this.payAddTaxAmts = payAddTaxAmts;
	}

	public BigDecimal[] getAdvPayAddTaxAmts() {
		return advPayAddTaxAmts;
	}

	public void setAdvPayAddTaxAmts(BigDecimal[] advPayAddTaxAmts) {
		this.advPayAddTaxAmts = advPayAddTaxAmts;
	}

	public String getButtonFlag() {
		return buttonFlag;
	}

	public void setButtonFlag(String buttonFlag) {
		this.buttonFlag = buttonFlag;
	}

	public String getIsCreditNote() {
		return isCreditNote;
	}

	public void setIsCreditNote(String isCreditNote) {
		this.isCreditNote = isCreditNote;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAttachmentTypeName() {
		return attachmentTypeName;
	}

	public void setAttachmentTypeName(String attachmentTypeName) {
		this.attachmentTypeName = attachmentTypeName;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getIvrowMemo() {
		return ivrowMemo;
	}

	public void setIvrowMemo(String ivrowMemo) {
		this.ivrowMemo = ivrowMemo;
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

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public BigDecimal getCleanAmtIngTotal() {
		return cleanAmtIngTotal;
	}

	public void setCleanAmtIngTotal(BigDecimal cleanAmtIngTotal) {
		this.cleanAmtIngTotal = cleanAmtIngTotal;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(BigDecimal freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public BigDecimal getSuspenseTotalAmt() {
		return suspenseTotalAmt;
	}

	public void setSuspenseTotalAmt(BigDecimal suspenseTotalAmt) {
		this.suspenseTotalAmt = suspenseTotalAmt;
	}

	public String getCleanPayId() {
		return cleanPayId;
	}

	public void setCleanPayId(String cleanPayId) {
		this.cleanPayId = cleanPayId;
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

	public String getPayDataFlagName() {
		return payDataFlagName;
	}

	public void setPayDataFlagName(String payDataFlagName) {
		this.payDataFlagName = payDataFlagName;
	}

	public String getDataFlagInvoiceName() {
		return dataFlagInvoiceName;
	}

	public void setDataFlagInvoiceName(String dataFlagInvoiceName) {
		this.dataFlagInvoiceName = dataFlagInvoiceName;
	}

	public String getDataFlagPayName() {
		return dataFlagPayName;
	}

	public void setDataFlagPayName(String dataFlagPayName) {
		this.dataFlagPayName = dataFlagPayName;
	}

	public String getInstDutyName() {
		return instDutyName;
	}

	public void setInstDutyName(String instDutyName) {
		this.instDutyName = instDutyName;
	}

	public BigDecimal getFreezeTotalAmt() {
		return freezeTotalAmt;
	}

	public void setFreezeTotalAmt(BigDecimal freezeTotalAmt) {
		this.freezeTotalAmt = freezeTotalAmt;
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

	public String getNormalPayId() {
		return normalPayId;
	}

	public void setNormalPayId(String normalPayId) {
		this.normalPayId = normalPayId;
	}

	public BigDecimal getSubInvoiceAmt() {
		return subInvoiceAmt;
	}

	public void setSubInvoiceAmt(BigDecimal subInvoiceAmt) {
		this.subInvoiceAmt = subInvoiceAmt;
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

	public BigDecimal[] getSubIds() {
		return subIds;
	}

	public void setSubIds(BigDecimal[] subIds) {
		this.subIds = subIds;
	}

	public BigDecimal[] getCancelAmts() {
		return cancelAmts;
	}

	public void setCancelAmts(BigDecimal[] cancelAmts) {
		this.cancelAmts = cancelAmts;
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

	public BigDecimal getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(BigDecimal execAmt) {
		this.execAmt = execAmt;
	}

	public BigDecimal getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(BigDecimal payedAmt) {
		this.payedAmt = payedAmt;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
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

	public BigDecimal getCancelAmt() {
		return cancelAmt;
	}

	public void setCancelAmt(BigDecimal cancelAmt) {
		this.cancelAmt = cancelAmt;
	}

	public String getOuCode() {
		return ouCode;
	}

	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTabsIndex() {
		return tabsIndex;
	}

	public void setTabsIndex(String tabsIndex) {
		this.tabsIndex = tabsIndex;
	}

	public BigDecimal getSubId() {
		return subId;
	}

	public void setSubId(BigDecimal subId) {
		this.subId = subId;
	}

	public BigDecimal getJdzf() {
		return jdzf;
	}

	public void setJdzf(BigDecimal jdzf) {
		this.jdzf = jdzf;
	}

	public BigDecimal getJdtj() {
		return jdtj;
	}

	public void setJdtj(BigDecimal jdtj) {
		this.jdtj = jdtj;
	}

	public String getJdDate() {
		return jdDate;
	}

	public void setJdDate(String jdDate) {
		this.jdDate = jdDate;
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

	public String getLxlxName() {
		return lxlxName;
	}

	public void setLxlxName(String lxlxName) {
		this.lxlxName = lxlxName;
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

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public String getFeeSubType() {
		return feeSubType;
	}

	public void setFeeSubType(String feeSubType) {
		this.feeSubType = feeSubType;
	}

	public String getFeeSubTypeName() {
		return feeSubTypeName;
	}

	public void setFeeSubTypeName(String feeSubTypeName) {
		this.feeSubTypeName = feeSubTypeName;
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

	public String getPayTermName() {
		return payTermName;
	}

	public void setPayTermName(String payTermName) {
		this.payTermName = payTermName;
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

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
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

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getBank_name_act_no() {
		return bank_name_act_no;
	}

	public void setBank_name_act_no(String bank_name_act_no) {
		this.bank_name_act_no = bank_name_act_no;
	}

	public String getProviderAddr() {
		return providerAddr;
	}

	public void setProviderAddr(String providerAddr) {
		this.providerAddr = providerAddr;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getHasTaxrow() {
		return hasTaxrow;
	}

	public void setHasTaxrow(String hasTaxrow) {
		this.hasTaxrow = hasTaxrow;
	}
	
	

}
