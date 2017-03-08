package com.forms.prms.web.contract.check.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author : lisj <br>
 * date : 2015-01-23<br>
 * 合同复核Bean
 */
public class ContractCheckBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/** TD_CNT表 **/
	private String cntNum;// 合同编号 BOC+一级分行机构号+yyyymmdd+六位序号+_责任中心
	private String cntName;// 合同事项
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
	private String providerCode;// 供应商
	private String providerName; // 供应商名称
	private String provActNo;// 银行账户编号
	private String provActCurr;// 银行账户币种 RMB
	private String srcPoviderName;// 原始供应商名称 供应商是内部供应商记录所对应的外部供应商名字，可以录入
	private String payTerm;// 付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款 3-分期付款
	private String payTermName;// 付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款
								// 3-分期付款
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
	private String isSpec; // 专项包

	/** TD_CNT_DEVICE **/
	private BigDecimal subId;// 子序号
	private BigDecimal[] subIds;// 子序号数组
	private String projId;// 合同项目ID 根据项目可见性选择
	private String projName;// 合同项目Name 根据项目可见性选择
	private String matrCode;// 物料编码 根据合同类型、费用类型、审批链(费用承担部门、物料编码和是否省行统购)选择
	private String matrName;// 物料编码名称
	private String montCode;// 监控指标编码
	private String montName;// 监控指标名称
	private String deviceModel;// 设备型号
	private String deviceModelName;// 设备型号名称 因为录入时可以手工增加，保留名称
	private BigDecimal execAmt;// 执行金额
	private BigDecimal execNum;// 数量
	private BigDecimal execPrice;// 单价
	private BigDecimal warranty;// 保修期
	private String productor;// 制造商
	private String feeDept;// 费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
	private String dataFlagDevice;// 状态 00-待审 01-退回 99-成功
	private String dataFlagDeviceName;// 状态 00-待审 01-退回 99-成功
	private String reference;// 参考
	private String special;// 专项
	private String referenceName;// 参考
	private String specialName;// 专项
	private BigDecimal payedAmt;// 已支付金额
	private String auditMemo; // 复核说明
	private String auditDept; // 审核部门
	private String auditOper; // 审核人员
	private String auditDate; // 审核日期
	private String auditTime; // 审核时间
	private BigDecimal cntTaxAmt;
	private String cntTrAmt;
	private String taxCode;
	private String isEnable;
	private String instUser;
	
	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getCntTrAmt() {
		return cntTrAmt;
	}

	public void setCntTrAmt(String cntTrAmt) {
		this.cntTrAmt = cntTrAmt;
	}

	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public BigDecimal[] getSubIds() {
		return subIds;
	}

	public void setSubIds(BigDecimal[] subIds) {
		this.subIds = subIds;
	}

	public String getDataFlagDeviceName() {
		return dataFlagDeviceName;
	}

	public void setDataFlagDeviceName(String dataFlagDeviceName) {
		this.dataFlagDeviceName = dataFlagDeviceName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public String getLxlxName() {
		return lxlxName;
	}

	public void setLxlxName(String lxlxName) {
		this.lxlxName = lxlxName;
	}

	public String getFeeSubTypeName() {
		return feeSubTypeName;
	}

	public void setFeeSubTypeName(String feeSubTypeName) {
		this.feeSubTypeName = feeSubTypeName;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getPayTermName() {
		return payTermName;
	}

	public void setPayTermName(String payTermName) {
		this.payTermName = payTermName;
	}

	public BigDecimal getSubId() {
		return subId;
	}

	public void setSubId(BigDecimal subId) {
		this.subId = subId;
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

	public BigDecimal getWarranty() {
		return warranty;
	}

	public void setWarranty(BigDecimal warranty) {
		this.warranty = warranty;
	}

	public String getProductor() {
		return productor;
	}

	public void setProductor(String productor) {
		this.productor = productor;
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

	public BigDecimal getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(BigDecimal payedAmt) {
		this.payedAmt = payedAmt;
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

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getMontCode() {
		return montCode;
	}

	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}

	public String getMontName() {
		return montName;
	}

	public void setMontName(String montName) {
		this.montName = montName;
	}

	public String getIsSpec() {
		return isSpec;
	}

	public void setIsSpec(String isSpec) {
		this.isSpec = isSpec;
	}

	public String getAuditDept() {
		return auditDept;
	}

	public void setAuditDept(String auditDept) {
		this.auditDept = auditDept;
	}

	public String getAuditOper() {
		return auditOper;
	}

	public void setAuditOper(String auditOper) {
		this.auditOper = auditOper;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

}
