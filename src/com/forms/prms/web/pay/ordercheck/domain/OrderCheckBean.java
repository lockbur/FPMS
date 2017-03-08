package com.forms.prms.web.pay.ordercheck.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderCheckBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3615238148355750870L;

	private String orderId;// 订单号 5位采购部门+8位日期+序号
	private String orderMemo;// 订单说明
	private String stockNum;// 集采编号
	private BigDecimal stockAmt;// 采评会批复金额
	private String stockProv;// 采评会批复供应商
	private String stockProvName;// 采评会批复供应商
	private String cntNum;// 合同或协议编号
	private String deptId;// 部门编码
	private String signDate;// 合同或协议签署日期
	private String startDate;// 合同约定期限（开始日期）
	private String endDate;// 合同约定期限（截止日期）
	private String payTermMemo;// 付款条件说明
	private String procurementRoute;// 采购方式
	private String instDutyCode;// 创建责任中心
	private String instDutyCodeName;// 创建责任中心
	private String instDate;// 创建日期
	private String instTime;// 创建时间
	private String instUser;// 创建用户
	private String orderDutyCode;// 采购部门
	private String orderDutyCodeName;// 采购部门
	private String dataFlag;// 状态 00-待补录 02-补录完成 03-复核退回 04-复核通过 08-创建中 10-发送成功
							// 11-订单创建失败 12-订单创建成功
	private String dataFlagName;// 状态 00-待补录 02-补录完成 03-复核退回 04-复核通过 08-创建中
								// 10-发送成功 11-订单创建失败 12-订单创建成功
	private BigDecimal poNumber;// PO单号 fms回盘文件时更新

	private String isAgree;//同意/退回
	
	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getStockProvName() {
		return stockProvName;
	}

	public void setStockProvName(String stockProvName) {
		this.stockProvName = stockProvName;
	}

	public String getInstDutyCodeName() {
		return instDutyCodeName;
	}

	public void setInstDutyCodeName(String instDutyCodeName) {
		this.instDutyCodeName = instDutyCodeName;
	}

	public String getOrderDutyCodeName() {
		return orderDutyCodeName;
	}

	public void setOrderDutyCodeName(String orderDutyCodeName) {
		this.orderDutyCodeName = orderDutyCodeName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public BigDecimal getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(BigDecimal stockAmt) {
		this.stockAmt = stockAmt;
	}

	public String getStockProv() {
		return stockProv;
	}

	public void setStockProv(String stockProv) {
		this.stockProv = stockProv;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
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

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public BigDecimal getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(BigDecimal poNumber) {
		this.poNumber = poNumber;
	}

}
