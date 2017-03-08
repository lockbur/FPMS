package com.forms.prms.web.contract.contractcommon.domain;

import java.io.Serializable;

public class ContractCommonBean implements Serializable {
	private static final long serialVersionUID = 1047080944938388171L;

	private String funcType;// 业务类型

	private String dutyCode;// 责任中心

	private String cntNum;// 合同号

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
	}

}
