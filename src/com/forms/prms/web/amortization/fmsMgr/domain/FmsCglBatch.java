package com.forms.prms.web.amortization.fmsMgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FmsCglBatch implements Serializable {

	private static final long serialVersionUID = 1L;

	private String feeYyyymm; // 月份
	private String taskType; // 任务类型
	private BigDecimal allCnt; // 总笔数
	private BigDecimal sucCnt; // 处理成功笔数
	private String dataFlag; // 状态
	private String tradeDate; // 交易日期
	private String tradeTime; // 交易时间
	private String startDate; // 查询起始日期
	private String endDate; // 查询结束日期
	private String org1Code;//一级分行代码 ORG1_CODE

	
	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getFeeYyyymm() {
		return feeYyyymm;
	}

	public void setFeeYyyymm(String feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(BigDecimal allCnt) {
		this.allCnt = allCnt;
	}

	public BigDecimal getSucCnt() {
		return sucCnt;
	}

	public void setSucCnt(BigDecimal sucCnt) {
		this.sucCnt = sucCnt;
	}

}
