package com.forms.prms.web.sysmanagement.referencespecial.domain;

import java.io.Serializable;

public class Reference implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String referenceId;// 参考编号
	private String referenceName;// 参考说明
	private String scope;// 安全性 1-总行使用 2-分行使用 3-全局 4-停用

	private String scopeName;

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

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
