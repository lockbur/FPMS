package com.forms.prms.web.budget.firstaudit.domain;

import java.math.BigDecimal;

public class BudgetDetailBean {
	
	private String tmpltId;//模板编号
	private String dutyCode;//责任中心
	private String dutyName;//责任中心
	private String rowSeq;//行号
	private String matrCode;//物料编码
	private BigDecimal inAmt;//预算金额 资产及费用类公共字段
	private String inMemo;//备注说明 资产及费用类公共字段
	private BigDecimal inNouseCnt;//报废数量 资产类专用
	private BigDecimal inNeedCnt;//需求数量 资产类专用
	private BigDecimal auditAmt;//初审金额 该字段为null说明未初审，有值就说明已初审。
	private String auditOper;//
	private String auditDate;//
	private String auditTime;//
	
	private String rowInfo;
	private String stockAmt;		//存量金额
	
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getRowSeq() {
		return rowSeq;
	}
	public void setRowSeq(String rowSeq) {
		this.rowSeq = rowSeq;
	}
	public BigDecimal getInAmt() {
		return inAmt;
	}
	public void setInAmt(BigDecimal inAmt) {
		this.inAmt = inAmt;
	}
	public String getInMemo() {
		return inMemo;
	}
	public void setInMemo(String inMemo) {
		this.inMemo = inMemo;
	}
	public BigDecimal getInNouseCnt() {
		return inNouseCnt;
	}
	public void setInNouseCnt(BigDecimal inNouseCnt) {
		this.inNouseCnt = inNouseCnt;
	}
	public BigDecimal getInNeedCnt() {
		return inNeedCnt;
	}
	public void setInNeedCnt(BigDecimal inNeedCnt) {
		this.inNeedCnt = inNeedCnt;
	}
	public BigDecimal getAuditAmt() {
		return auditAmt;
	}
	public void setAuditAmt(BigDecimal auditAmt) {
		this.auditAmt = auditAmt;
	}
	public String getAuditOper() {
		return auditOper;
	}
	public void setAuditOper(String auditOper) {
		this.auditOper = auditOper;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getRowInfo() {
		return rowInfo;
	}
	public void setRowInfo(String rowInfo) {
		this.rowInfo = rowInfo;
	}
	public String getStockAmt() {
		return stockAmt;
	}
	public void setStockAmt(String stockAmt) {
		this.stockAmt = stockAmt;
	}
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	

}
