package com.forms.prms.web.sysmanagement.homepage.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FmsErrorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ouCode;
	
	private BigDecimal dwnErrorNum;//接口文件下载处理失败数量（1.*、2.*）
	
	private BigDecimal updErrorNum;//erp上传文件失败数量（3.*）
	
	private BigDecimal chkErrorNum;//校验文件处理失败数量（3.*）
	
	
	public String getOuCode() {
		return ouCode;
	}
	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}
	public BigDecimal getDwnErrorNum() {
		return dwnErrorNum;
	}
	public void setDwnErrorNum(BigDecimal dwnErrorNum) {
		this.dwnErrorNum = dwnErrorNum;
	}
	public BigDecimal getUpdErrorNum() {
		return updErrorNum;
	}
	public void setUpdErrorNum(BigDecimal updErrorNum) {
		this.updErrorNum = updErrorNum;
	}
	public BigDecimal getChkErrorNum() {
		return chkErrorNum;
	}
	public void setChkErrorNum(BigDecimal chkErrorNum) {
		this.chkErrorNum = chkErrorNum;
	}
}
