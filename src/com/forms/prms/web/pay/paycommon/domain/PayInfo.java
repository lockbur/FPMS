package com.forms.prms.web.pay.paycommon.domain;

import java.io.Serializable;

public class PayInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9118024192114566420L;
	private String payId;
	private String dutyCode;
	private String funcType;
	
	private String dataFlag;

	private String org1Code;
	
	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

}
