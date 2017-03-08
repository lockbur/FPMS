package com.forms.prms.web.amortization.fmsMgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FmsDownload implements Serializable {

	private static final long serialVersionUID = 1L;

	private String batchNo; // 批次号
	private String tradeType; // 交易类型
	private BigDecimal allCnt; // 总笔数
	private String downloadPath; // 下载文件路径
	private String downloadDate; // 下载日期
	private String downloadTime; // 下载时间
	private String dataFlag; // 状态
	private String tradeDate; // 交易日期
	private String startDate; // 查询起始日期
	private String endDate; // 查询结束日期
	private String fileFlag; // 文件类型标识
	private String dealLog; //
	private String filePath; //
	private String dataFlagName; // 状态名称
	private String tradeTypeName; // 交易类型名称
	
	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getTradeTypeName() {
		return tradeTypeName;
	}

	public void setTradeTypeName(String tradeTypeName) {
		this.tradeTypeName = tradeTypeName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDealLog() {
		return dealLog;
	}

	public void setDealLog(String dealLog) {
		this.dealLog = dealLog;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public BigDecimal getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(BigDecimal allCnt) {
		this.allCnt = allCnt;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(String downloadDate) {
		this.downloadDate = downloadDate;
	}

	public String getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
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

	public String getFileFlag() {
		return fileFlag;
	}

	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}

}
