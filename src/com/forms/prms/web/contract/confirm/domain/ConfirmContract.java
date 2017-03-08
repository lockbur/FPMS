package com.forms.prms.web.contract.confirm.domain;

import java.math.BigDecimal;

import com.forms.prms.web.contract.initiate.domain.ContractInitate;

public class ConfirmContract extends ContractInitate {
	private static final long serialVersionUID = 1L;
	private	BigDecimal cntTaxAmt;

	
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

	private String dataFlag;// 物料状态
	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}
	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
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

}
