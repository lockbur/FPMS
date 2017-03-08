package com.forms.prms.web.contract.confirm.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 预算冻结明细
 * 
 * @author lsj
 * 
 */
public class BudgetBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3608540034938531484L;
	private String cntNum;// ++合同号
	private String feeDept;// ++费用承担部门
	private String montCode;// ++监控指标
	private String matrCode;// ++物料编码
	private String reference;// ++参考
	private String special;// ++专项
	private String bgtYear;// ++预算年份
	private String scId;// 》唯一值
	private BigDecimal bgtFrozen;// 冻结预算
	private BigDecimal bgtOverdraw;// 透支预算
	private BigDecimal bgtUsedSum;// 总占用预算
	private BigDecimal bgtUsedThisyear;// 当年实际占用预算
	private BigDecimal bgtFree;// 总预算释放
	private BigDecimal bgtWaitFree;// 待释放预算
	private String bgtId;// 对应预算汇总表的bgt_id，通过此关联可以得到预算的使用情况
	private String bgtIdNew;// 撤并后的bgt_id，默认和bgt_id相同
	private	BigDecimal cntTaxAmt;

	
	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
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

	public BigDecimal getBgtOverdraw() {
		return bgtOverdraw;
	}

	public void setBgtOverdraw(BigDecimal bgtOverdraw) {
		this.bgtOverdraw = bgtOverdraw;
	}

	public BigDecimal getBgtUsedSum() {
		return bgtUsedSum;
	}

	public void setBgtUsedSum(BigDecimal bgtUsedSum) {
		this.bgtUsedSum = bgtUsedSum;
	}

	public BigDecimal getBgtUsedThisyear() {
		return bgtUsedThisyear;
	}

	public void setBgtUsedThisyear(BigDecimal bgtUsedThisyear) {
		this.bgtUsedThisyear = bgtUsedThisyear;
	}

	public BigDecimal getBgtFree() {
		return bgtFree;
	}

	public void setBgtFree(BigDecimal bgtFree) {
		this.bgtFree = bgtFree;
	}

	public BigDecimal getBgtWaitFree() {
		return bgtWaitFree;
	}

	public void setBgtWaitFree(BigDecimal bgtWaitFree) {
		this.bgtWaitFree = bgtWaitFree;
	}

	public String getBgtId() {
		return bgtId;
	}

	public void setBgtId(String bgtId) {
		this.bgtId = bgtId;
	}

	public String getBgtIdNew() {
		return bgtIdNew;
	}

	public void setBgtIdNew(String bgtIdNew) {
		this.bgtIdNew = bgtIdNew;
	}

}
