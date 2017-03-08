package com.forms.prms.web.sysmanagement.homepage.domain;

import java.io.Serializable;

public class UserDesktopBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String funcId;

	private String funcName;
	
	private String iconName;

	private String funcUrl;
	
	private String funcMemo;
	
	private String addTime;

	
	
	
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getFuncMemo() {
		return funcMemo;
	}

	public void setFuncMemo(String funcMemo) {
		this.funcMemo = funcMemo;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getFuncUrl() {
		return funcUrl;
	}

	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}

}
