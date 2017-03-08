package com.forms.prms.web.pay.orderquery.domain;

import java.io.Serializable;

public class OrderQueryBean implements Serializable {
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

	private String dataFlagName;// 订单状态

	private String poNumber;// PO单号

	private String dataFlag;// 状态

	private String org1Code;// 一级行
	
	private String org2Code;// 二级行
	
	private String isSuperAdmin; //1-是 0-否
	
	private String chkDutyCode;//确认人所在责任中心
	
	private String orgFlag;
	
	private String orderAmt;
	
	private String menuType;
	
	private String chkUser;
	
	
	private String poLineNo;
	
	private String providerName;
	private String projName;
	private String projId;
	private String matrName;
	private String matrCode;
	private String cglCode;
	private String execNum;
	private String execPrice;
	private String execAmt;
	private String flag;//1导出订单信息 2导出订单明细信息
	private String montCode;
	private String montName;
	
	private String operMemo;
	
	private String cntTrAmt;
	private String taxCode;
	
	
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
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

	public String getPoLineNo() {
		return poLineNo;
	}

	public void setPoLineNo(String poLineNo) {
		this.poLineNo = poLineNo;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String getChkUser() {
		return chkUser;
	}

	public void setChkUser(String chkUser) {
		this.chkUser = chkUser;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public String getChkDutyCode() {
		return chkDutyCode;
	}

	public void setChkDutyCode(String chkDutyCode) {
		this.chkDutyCode = chkDutyCode;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

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

	public String getOrderDutyCodeName() {
		return orderDutyCodeName;
	}

	public void setOrderDutyCodeName(String orderDutyCodeName) {
		this.orderDutyCodeName = orderDutyCodeName;
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

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
	}

}
