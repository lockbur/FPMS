package com.forms.prms.web.budget.budgetResolve.resolve.domain;

import java.io.Serializable;

public class Resolve implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String resolveType;
	
	private String validYear;
	
	private String resolveTempType;

	public String getResolveType() {
		return resolveType;
	}

	public void setResolveType(String resolveType) {
		this.resolveType = resolveType;
	}

	public String getValidYear() {
		return validYear;
	}

	public void setValidYear(String validYear) {
		this.validYear = validYear;
	}

	public String getResolveTempType() {
		return resolveTempType;
	}

	public void setResolveTempType(String resolveTempType) {
		this.resolveTempType = resolveTempType;
	}
}
