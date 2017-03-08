package com.forms.prms.web.sysmanagement.homepage.domain;

import java.io.Serializable;

public class OperWarnBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dutyCode;

	private String functype;

	private String operFlag;

	private int operNum;

	public int getOperNum() {
		return operNum;
	}

	public void setOperNum(int operNum) {
		this.operNum = operNum;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getFunctype() {
		return functype;
	}

	public void setFunctype(String functype) {
		this.functype = functype;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

}
