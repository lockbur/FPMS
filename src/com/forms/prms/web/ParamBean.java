package com.forms.prms.web;

import java.io.Serializable;

public class ParamBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4300729220438296766L;

	private String paramCode;
	
	private String viewValue;
	
	private String value;

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getViewValue() {
		return viewValue;
	}

	public void setViewValue(String viewValue) {
		this.viewValue = viewValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
