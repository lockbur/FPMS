package com.forms.prms.web.contract.confirmchg.domain;

import java.math.BigDecimal;

import com.forms.prms.web.contract.initiate.domain.ContractInitate;

public class ConfirmChgContract extends ContractInitate {
	private static final long serialVersionUID = 1L;

	// 供应商名称
	private String providerName;

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

	private String operateLog;

	private BigDecimal cntTaxAmt;

	private BigDecimal payAmt;

	private String orgFlag;

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public String getOperateLog() {
		return operateLog;
	}

	public void setOperateLog(String operateLog) {
		this.operateLog = operateLog;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

}
