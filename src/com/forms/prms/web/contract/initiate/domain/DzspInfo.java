package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 电子审批信息
 * 
 * @author user
 *
 */
public class DzspInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntNum;// 合同号
	private String abcde;// 缩位码
	private BigDecimal abcdeAmt;// 金额
	private BigDecimal abcdeNum;// 数量

	private String projCrId; // 审批编号
	private BigDecimal projCrAmt; // 立项总金额
	private String createDate; // 立项日期
	private BigDecimal projCrNum; // 立项总数量
	private String status; // 状态 0 未立项 1 已立项
	private BigDecimal exeAmt; // 已立项金额
	private BigDecimal exeNum; // 已立项数量
	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType; // 操作类型

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getAbcde() {
		return abcde;
	}

	public void setAbcde(String abcde) {
		this.abcde = abcde;
	}

	public BigDecimal getAbcdeAmt() {
		return abcdeAmt;
	}

	public void setAbcdeAmt(BigDecimal abcdeAmt) {
		this.abcdeAmt = abcdeAmt;
	}

	public BigDecimal getAbcdeNum() {
		return abcdeNum;
	}

	public void setAbcdeNum(BigDecimal abcdeNum) {
		this.abcdeNum = abcdeNum;
	}

	public String getProjCrId() {
		return projCrId;
	}

	public void setProjCrId(String projCrId) {
		this.projCrId = projCrId;
	}

	public BigDecimal getProjCrAmt() {
		return projCrAmt;
	}

	public void setProjCrAmt(BigDecimal projCrAmt) {
		this.projCrAmt = projCrAmt;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getProjCrNum() {
		return projCrNum;
	}

	public void setProjCrNum(BigDecimal projCrNum) {
		this.projCrNum = projCrNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getExeAmt() {
		return exeAmt;
	}

	public void setExeAmt(BigDecimal exeAmt) {
		this.exeAmt = exeAmt;
	}

	public BigDecimal getExeNum() {
		return exeNum;
	}

	public void setExeNum(BigDecimal exeNum) {
		this.exeNum = exeNum;
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
