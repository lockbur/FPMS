package com.forms.prms.web.pay.orderstart.domain;

import java.io.Serializable;

public class OrderStartBean implements Serializable {
	private static final long serialVersionUID = 1047080944938388171L;

	private String orderId;// 订单ID

	private String stockNum;// 采集编号

	private String cntNum;// 合同号

	private String cntName;// 合同事项

	private String orderDutyCode;// 采购部门

	private String orderDutyCodeName;// 采购部门名称

	private String orderMemo;// 订单说明

	private String stockAmt;// 采评会批复金额

	private String stockProv;// 采评会批复供应商

	private String deptId;// 部门编码

	private String signDate;// 合同或协议签署日期

	private String startDate;// 合同约定期限（开始日期）

	private String endDate;// 合同约定期限（截止日期）

	private String payTermMemo;// 付款条件说明

	private String procurementRoute;// 采购方式

	private String instDutyCode;// 创建责任中心

	private String instDate;// 创建日期

	private String instTime;// 创建时间

	private String instUser;// 创建用户
	
	private String chkUser;//订单确认用户

	private String matrCode;// 物料编码

	private String feeDept;// 费用承担部门

	private String operMemo;// 操作说明

	private String isAgree;// 确认通过与否

	private String innerNo;// 审批序号

	private String projName;// 项目名称

	private String dutyName;// 费用承担部门

	private String montCode;// 监控指标

	private String deviceModelName;// 设备型号名称

	private String referenceName;// 参考

	private String specialName;// 专项

	private String execNum;// 数量

	private String execPrice;// 单价

	private String execAmt;// 金额

	private String org1Code;// 一级行

	private String isBuyer;// 是否采购员
	
	private String montName;
	private String matrName;
	private String taxCode;
	private String cntTrAmt;
	
	
	public String getMontName() {
		return montName;
	}

	public void setMontName(String montName) {
		this.montName = montName;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
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

	public String getChkUser() {
		return chkUser;
	}

	public void setChkUser(String chkUser) {
		this.chkUser = chkUser;
	}

	public String getIsBuyer() {
		return isBuyer;
	}

	public void setIsBuyer(String isBuyer) {
		this.isBuyer = isBuyer;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
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

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getMontCode() {
		return montCode;
	}

	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}

	public String getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
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

	public String getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(String execAmt) {
		this.execAmt = execAmt;
	}

	public String getInnerNo() {
		return innerNo;
	}

	public void setInnerNo(String innerNo) {
		this.innerNo = innerNo;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public String getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(String stockAmt) {
		this.stockAmt = stockAmt;
	}

	public String getStockProv() {
		return stockProv;
	}

	public void setStockProv(String stockProv) {
		this.stockProv = stockProv;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPayTermMemo() {
		return payTermMemo;
	}

	public void setPayTermMemo(String payTermMemo) {
		this.payTermMemo = payTermMemo;
	}

	public String getProcurementRoute() {
		return procurementRoute;
	}

	public void setProcurementRoute(String procurementRoute) {
		this.procurementRoute = procurementRoute;
	}

	public String getInstDutyCode() {
		return instDutyCode;
	}

	public void setInstDutyCode(String instDutyCode) {
		this.instDutyCode = instDutyCode;
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

	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getOrderDutyCodeName() {
		return orderDutyCodeName;
	}

	public void setOrderDutyCodeName(String orderDutyCodeName) {
		this.orderDutyCodeName = orderDutyCodeName;
	}

	private String dataFlag;// 状态

	private String dataFlagName;// 状态值

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getOrderDutyCode() {
		return orderDutyCode;
	}

	public void setOrderDutyCode(String orderDutyCode) {
		this.orderDutyCode = orderDutyCode;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

}
