package com.forms.prms.web.sysmanagement.referencespecial.domain;

import java.io.Serializable;

public class Special implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String specialId;// 专项编号
	private String specialName;// 专项说明
	private String scope;// 安全性 1-总行使用 2-分行使用 3-全局 4-停用

	private String scopeName;// 安全性名

	private String org1Code;

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
