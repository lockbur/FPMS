package com.forms.prms.web.pay.payAdd.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 付款新增预算冻结的bean
 * 
 * @author user date : 2015-08-24<br>
 */
public class PayAddBgtBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7317286435522428461L;

	private String payId;// ++付款单
	private String cntNum;// ++合同号
	private String feeDept;// ++费用承担部门
	private String feeDeptName;// ++费用承担部门
	private String montCode;// ++监控指标
	private String montName;// ++监控指标名称
	private String matrCode;// ++物料编码
	private String matrName;// ++物料名称
	private String reference;// ++参考
	private String referenceName;// ++参考
	private String special;// ++专项
	private String specialName;// ++专项
	private String bgtYear;// ++预算年份
	private String scId;// 唯一值
	private BigDecimal bgtFrozen;// 需冻结预算
	private String montCodeNew;// 监控指标（最新）
	private String bgtId;// 对应预算汇总表的bgt_id
	private String memo;// 尝试冻结预算结果说明

	public String getMontName() {
		return montName;
	}

	public void setMontName(String montName) {
		this.montName = montName;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getFeeDeptName() {
		return feeDeptName;
	}

	public void setFeeDeptName(String feeDeptName) {
		this.feeDeptName = feeDeptName;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getMontCode() {
		return montCode;
	}

	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getBgtYear() {
		return bgtYear;
	}

	public void setBgtYear(String bgtYear) {
		this.bgtYear = bgtYear;
	}

	public String getScId() {
		return scId;
	}

	public void setScId(String scId) {
		this.scId = scId;
	}

	public BigDecimal getBgtFrozen() {
		return bgtFrozen;
	}

	public void setBgtFrozen(BigDecimal bgtFrozen) {
		this.bgtFrozen = bgtFrozen;
	}

	public String getMontCodeNew() {
		return montCodeNew;
	}

	public void setMontCodeNew(String montCodeNew) {
		this.montCodeNew = montCodeNew;
	}

	public String getBgtId() {
		return bgtId;
	}

	public void setBgtId(String bgtId) {
		this.bgtId = bgtId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
