package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 分期支付信息
 * 
 * @author user
 *
 */
public class StageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntNum;
	private String subId;
	// 按进度支付
	private BigDecimal jdtj;// 付款进度
	private BigDecimal jdzf;// 进度支付
	private String jdDate;// 付款日期
	// 按日期支付
	private BigDecimal rqtj;// 付款进度
	private BigDecimal rqzf;// 进度支付
	// 按条件支付
	private BigDecimal dhzf;// 到货支付
	private BigDecimal yszf;// 验收支付
	private BigDecimal jszf;// 结算进度

	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType; // 操作类型

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public BigDecimal getJdtj() {
		return jdtj;
	}
//
//	public void setJdtj(BigDecimal jdtj) {
//		this.jdtj = jdtj;
//	}

	public BigDecimal getJdzf() {
		return jdzf;
	}

	public void setJdzf(BigDecimal jdzf) {
		this.jdzf = jdzf;
	}

	public String getJdDate() {
		return jdDate;
	}

	public void setJdDate(String jdDate) {
		this.jdDate = jdDate;
	}

	public BigDecimal getRqtj() {
		return rqtj;
	}

	public void setRqtj(BigDecimal rqtj) {
		this.rqtj = rqtj;
	}

	public BigDecimal getRqzf() {
		return rqzf;
	}

	public void setRqzf(BigDecimal rqzf) {
		this.rqzf = rqzf;
	}

	public BigDecimal getDhzf() {
		return dhzf;
	}

	public void setDhzf(BigDecimal dhzf) {
		this.dhzf = dhzf;
	}

	public BigDecimal getYszf() {
		return yszf;
	}

	public void setYszf(BigDecimal yszf) {
		this.yszf = yszf;
	}

	public BigDecimal getJszf() {
		return jszf;
	}

	public void setJszf(BigDecimal jszf) {
		this.jszf = jszf;
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
}
