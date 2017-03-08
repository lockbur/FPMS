package com.forms.prms.web.contract.history.domain;

import com.forms.prms.web.contract.initiate.domain.ContractInitate;

public class ContractHistory extends ContractInitate {

	private static final long serialVersionUID = -9136576946108764593L;

	// 供应商名称
	private String providerName;

	// 签订日期区间：起始日期
	private String befDate;

	// 签订日期区间：结束日期
	private String aftDate;

	private String dutyName;// 创建责任中心名称

	private String id;

	private String icmsPkuuid;
	
	private String matrCodeString;
	

	public String getMatrCodeString() {
		return matrCodeString;
	}

	public void setMatrCodeString(String matrCodeString) {
		this.matrCodeString = matrCodeString;
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

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

}
