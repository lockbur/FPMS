package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class CntDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntNum;// 合同号
	private String subId;// 子序号
	private String projId;// 合同项目ID 根据项目可见性选择
	private String matrCode;// 物料编码 根据合同类型、费用类型、审批链(费用承担部门、物料编码和是否省行统购)选择
	private String matrName;// 物料名称
	private String deviceModel;// 设备型号
	private String deviceModelName;// 设备型号名称 因为录入时可以手工增加，保留名称
	private BigDecimal execAmt;// 执行金额
	private BigDecimal execNum;// 数量
	private BigDecimal execPrice;// 单价
	private BigDecimal warranty;// 保修期
	private String productor;// 制造商
	private String feeDept;// 费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
	private String dataFlag;// 状态 00-待审 01-退回 99-成功
	private String reference;// 参考
	private String special;// 专项
	private BigDecimal payedAmt;// 已支付金额
	private String montCode;// 监控指标
	private String montName;// 监控指标名称
	
	private String projName; // 项目名称
	private String feeDeptName; // 费用部门名称
	private String cglCode; // 核算码
	private String specialName; // 专项说明
	private String referenceName; // 参考说明
	private String feeYyyymm;// 受益年月
	private BigDecimal cglCalAmt;// 测算金额 系统计算的
	private BigDecimal cglFeeAmt;// 受益金额 实际录入的
	private String auditMemo; // 审批意见 
	private String orderId; // 订单编号
	private String auditDept; // 审批部门
	private String auditDeptName; // 审批部门名称
	private String auditOper; // 审批人员
	private String auditDate; // 审批日期
	private String auditTime; // 审批时间
	private String matrBuyDeptName;//物料采购部门
	private String  dataFlagName;
	private String isNotinfee; //是否为不入库费用 Y-是 N-否
	
	private String taxCode;// 税码
	private BigDecimal taxRate;// 税率
	private String deductFlag;//是否可抵扣
	private BigDecimal taxYamt;// 可抵扣sh
	private BigDecimal taxNamt;// 不可抵扣税额
	private BigDecimal cntTrAmt;
	private String currMontCode;
	private String currMontName;
	
	
	public String getCurrMontCode() {
		return currMontCode;
	}

	public void setCurrMontCode(String currMontCode) {
		this.currMontCode = currMontCode;
	}

	public String getCurrMontName() {
		return currMontName;
	}

	public void setCurrMontName(String currMontName) {
		this.currMontName = currMontName;
	}

	public BigDecimal getCntTrAmt() {
		return cntTrAmt;
	}

	public void setCntTrAmt(BigDecimal cntTrAmt) {
		this.cntTrAmt = cntTrAmt;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
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

	public BigDecimal getTaxYamt() {
		return taxYamt;
	}

	public void setTaxYamt(BigDecimal taxYamt) {
		this.taxYamt = taxYamt;
	}

	public BigDecimal getTaxNamt() {
		return taxNamt;
	}

	public void setTaxNamt(BigDecimal taxNamt) {
		this.taxNamt = taxNamt;
	}

	public String getIsNotinfee() {
		return isNotinfee;
	}

	public void setIsNotinfee(String isNotinfee) {
		this.isNotinfee = isNotinfee;
	}

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getMatrBuyDeptName() {
		return matrBuyDeptName;
	}

	public void setMatrBuyDeptName(String matrBuyDeptName) {
		this.matrBuyDeptName = matrBuyDeptName;
	}

	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType; // 操作类型

	
	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
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

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
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

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
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

	public BigDecimal getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(BigDecimal payedAmt) {
		this.payedAmt = payedAmt;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getFeeDeptName() {
		return feeDeptName;
	}

	public void setFeeDeptName(String feeDeptName) {
		this.feeDeptName = feeDeptName;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getFeeYyyymm() {
		return feeYyyymm;
	}

	public void setFeeYyyymm(String feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}

	public BigDecimal getCglCalAmt() {
		return cglCalAmt;
	}

	public void setCglCalAmt(BigDecimal cglCalAmt) {
		this.cglCalAmt = cglCalAmt;
	}

	public BigDecimal getCglFeeAmt() {
		return cglFeeAmt;
	}

	public void setCglFeeAmt(BigDecimal cglFeeAmt) {
		this.cglFeeAmt = cglFeeAmt;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAuditDept() {
		return auditDept;
	}

	public void setAuditDept(String auditDept) {
		this.auditDept = auditDept;
	}

	public String getAuditDeptName() {
		return auditDeptName;
	}

	public void setAuditDeptName(String auditDeptName) {
		this.auditDeptName = auditDeptName;
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
}
