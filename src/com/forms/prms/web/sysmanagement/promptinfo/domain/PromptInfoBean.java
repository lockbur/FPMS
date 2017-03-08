/**
 * 
 */
package com.forms.prms.web.sysmanagement.promptinfo.domain;

import java.io.Serializable;

/**
 * @author ZhengCuixian
 *
 */
public class PromptInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5367139219374158269L;
	
	private String dispName;//字段名称
	private String promptInfo;//对应的提示信息
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getPromptInfo() {
		return promptInfo;
	}
	public void setPromptInfo(String promptInfo) {
		this.promptInfo = promptInfo;
	}

}
