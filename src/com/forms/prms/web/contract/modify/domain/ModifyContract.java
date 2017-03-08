package com.forms.prms.web.contract.modify.domain;

import com.forms.prms.web.contract.initiate.domain.ContractInitate;

public class ModifyContract extends ContractInitate {
	private static final long serialVersionUID = 1L;
	// 签订日期区间：起始日期
	private String befDate;

	// 签订日期区间：结束日期
	private String aftDate;

	// 流水备注
	private String waterMemo;

	// 流水位置
	private String location;

	// 物料修改标志
	private String deviceChg;

	// 记录操作是否成功
	private boolean isSuc;

	// 单笔物料信息是否被修改
	private boolean[] projDevChg;

	private String createDeptName; // 创建部门名称

	private String id;// 扫描ID

	private String icmsPkuuid;//

	private String isOrderBack; // 是否全部订单退回

	private String[] isOrderSucDev; // 是否订单成功 的物料

	private String[] auditMemo;// 审批意见

	private String operateLog;
	private String cntNum;
	private String cntTypeName;
	private String houseKindName;

	// 供应商
	private String providerType;
	private String provActCurr;
	private String provActNo;
	private String providerCode;
	private String providerName;
	private String providerAddr;
	private String actName;
	private String bankInfo;
	private String bankCode;
	private String bankArea;
	private String bankName;
	private String actType;
	private String payMode;

	private String bgtType;
	private String providerAddrCode;

	private String actualFeeEndDate;
	
	private String feeStartDateShow;
	
	private String feeEndDateShow;
	
	private String[] projchange;//项目是否更改
	
	private String[] projIdOld;//未更改前的项目ID
	
	public String[] getProjIdOld() {
		return projIdOld;
	}

	public void setProjIdOld(String[] projIdOld) {
		this.projIdOld = projIdOld;
	}

	public String[] getProjchange() {
		return projchange;
	}

	public void setProjchange(String[] projchange) {
		this.projchange = projchange;
	}

	public String getFeeStartDateShow() {
		return feeStartDateShow;
	}

	public void setFeeStartDateShow(String feeStartDateShow) {
		this.feeStartDateShow = feeStartDateShow;
	}

	public String getFeeEndDateShow() {
		return feeEndDateShow;
	}

	public void setFeeEndDateShow(String feeEndDateShow) {
		this.feeEndDateShow = feeEndDateShow;
	}

	public String getActualFeeEndDate() {
		return actualFeeEndDate;
	}

	public void setActualFeeEndDate(String actualFeeEndDate) {
		this.actualFeeEndDate = actualFeeEndDate;
	}

	public String getBgtType() {
		return bgtType;
	}

	public void setBgtType(String bgtType) {
		this.bgtType = bgtType;
	}

	public String getProviderAddrCode() {
		return providerAddrCode;
	}

	public void setProviderAddrCode(String providerAddrCode) {
		this.providerAddrCode = providerAddrCode;
	}

	public String getProvActCurr() {
		return provActCurr;
	}

	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}

	public String getProvActNo() {
		return provActNo;
	}

	public void setProvActNo(String provActNo) {
		this.provActNo = provActNo;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getHouseKindName() {
		return houseKindName;
	}

	public void setHouseKindName(String houseKindName) {
		this.houseKindName = houseKindName;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getOperateLog() {
		return operateLog;
	}

	public void setOperateLog(String operateLog) {
		this.operateLog = operateLog;
	}

	public String[] getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String[] auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getIcmsPkuuid() {
		return icmsPkuuid;
	}

	public void setIcmsPkuuid(String icmsPkuuid) {
		this.icmsPkuuid = icmsPkuuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
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

	public String getWaterMemo() {
		return waterMemo;
	}

	public void setWaterMemo(String waterMemo) {
		this.waterMemo = waterMemo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDeviceChg() {
		return deviceChg;
	}

	public void setDeviceChg(String deviceChg) {
		this.deviceChg = deviceChg;
	}

	public boolean isSuc() {
		return isSuc;
	}

	public void setSuc(boolean isSuc) {
		this.isSuc = isSuc;
	}

	public boolean[] getProjDevChg() {
		return projDevChg;
	}

	public void setProjDevChg(boolean[] projDevChg) {
		this.projDevChg = projDevChg;
	}

	public String getCreateDeptName() {
		return createDeptName;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

	public String getIsOrderBack() {
		return isOrderBack;
	}

	public void setIsOrderBack(String isOrderBack) {
		this.isOrderBack = isOrderBack;
	}

	public String[] getIsOrderSucDev() {
		return isOrderSucDev;
	}

	public void setIsOrderSucDev(String[] isOrderSucDev) {
		this.isOrderSucDev = isOrderSucDev;
	}

}
