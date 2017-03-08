package com.forms.prms.web.budget.budgetInspect.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class SumCntDetail implements Serializable{
	private static final long serialVersionUID = -6857759218327699342L;
	
	private String scdId;//
	private String scId;//
	private String bgtType;//
	private String payId;//
	private BigDecimal bgtVal;//
	private String memo;//
	private String instDate;//
	private String bgtMatrcode;
 
	public String getBgtMatrcode() {
		return bgtMatrcode;
	}
	public void setBgtMatrcode(String bgtMatrcode) {
		this.bgtMatrcode = bgtMatrcode;
	}
	public String getScdId() {
		return scdId;
	}
	public void setScdId(String scdId) {
		this.scdId = scdId;
	}
	public String getScId() {
		return scId;
	}
	public void setScId(String scId) {
		this.scId = scId;
	}
	public String getBgtType() {
		return bgtType;
	}
	public void setBgtType(String bgtType) {
		this.bgtType = bgtType;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public BigDecimal getBgtVal() {
		return bgtVal;
	}
	public void setBgtVal(BigDecimal bgtVal) {
		this.bgtVal = bgtVal;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
}
