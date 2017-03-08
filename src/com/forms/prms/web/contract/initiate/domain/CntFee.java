package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 费用类型
 * 
 * @author user
 *
 */
public class CntFee implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntNum;// 合同编号
	private String subId;// 子序号
	private String cglCode;// 核算码
	private String reference;// 参考
	private String special;// 专项
	private String feeDept;// 费用承担部门
	private String feeYyyymm;// 受益年月
	private BigDecimal cglCalAmt;// 测算金额 系统计算的
	private BigDecimal cglFeeAmt;// 受益金额 实际录入的

	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType; // 操作类型

	public BigDecimal getCglCalAmt() {
		return cglCalAmt;
	}

	public void setCglCalAmt(BigDecimal cglCalAmt) {
		this.cglCalAmt = cglCalAmt;
	}

	public BigDecimal getCglFeeAmt() {
		return cglFeeAmt;
	}

	public void setCglFeeAmt(BigDecimal cglFeeAmt) {
		this.cglFeeAmt = cglFeeAmt;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
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

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getFeeYyyymm() {
		return feeYyyymm;
	}

	public void setFeeYyyymm(String feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

}
