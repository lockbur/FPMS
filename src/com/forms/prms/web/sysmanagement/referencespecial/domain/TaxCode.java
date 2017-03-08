package com.forms.prms.web.sysmanagement.referencespecial.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaxCode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String taxCode;//税码
	
	private BigDecimal taxRate;//税率
	
	private String deductFlag;//是否可抵扣
	
	private String deductFlagName;
	
	private String cglCode;//核算码
	
	private String hasTaxrow;//是否产生税行
	
	private String hasTaxrowName;
	
	
	
	private String validFlag;//是否启用
	
	private String instUser;//录入柜员
	
	private String instDate;//录入日期
	
	private String instTime;//录入时间
	
	private String updUser;//最后修改柜员
	
	private String updDate;//最后修改日期
	
	private String updTime;//最后修改时间
	
	private BigDecimal[] perCnts;//百分比数组
	
	private String[] cglCodes;//核算码数组
	
	
	private BigDecimal perCnt;
	
	private String isZero;
	
	
	
	
	

	public String getIsZero() {
		return isZero;
	}

	public void setIsZero(String isZero) {
		this.isZero = isZero;
	}

	public BigDecimal getPerCnt() {
		return perCnt;
	}

	public BigDecimal[] getPerCnts() {
		return perCnts;
	}

	public void setPerCnts(BigDecimal[] perCnts) {
		this.perCnts = perCnts;
	}

	public void setPerCnt(BigDecimal perCnt) {
		this.perCnt = perCnt;
	}


	public String[] getCglCodes() {
		return cglCodes;
	}

	public void setCglCodes(String[] cglCodes) {
		this.cglCodes = cglCodes;
	}

	public String getDeductFlagName() {
		return deductFlagName;
	}

	public void setDeductFlagName(String deductFlagName) {
		this.deductFlagName = deductFlagName;
	}

	public String getHasTaxrowName() {
		return hasTaxrowName;
	}

	public void setHasTaxrowName(String hasTaxrowName) {
		this.hasTaxrowName = hasTaxrowName;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getDeductFlag() {
		return deductFlag;
	}

	public void setDeductFlag(String deductFlag) {
		this.deductFlag = deductFlag;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String getHasTaxrow() {
		return hasTaxrow;
	}

	public void setHasTaxrow(String hasTaxrow) {
		this.hasTaxrow = hasTaxrow;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getInstTime() {
		return instTime;
	}

	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	
	
}
