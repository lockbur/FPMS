package com.forms.prms.tool.fms;

import java.io.Serializable;
import java.util.List;

public class FMSDownloadBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String batchNo;// 批次号
	private String tradeType;// 交易类型 21-AP发票信息 22-AP付款信息 23-GL待摊预提信息 25-采购订单信息
								// 26-科目余额
	private int allCnt;// 总笔数
	private String downloadPath;// 下载文件路径
	private String downloadDate;// 下载日期
	private String downloadTime;// 下载时间
	private String dataFlag;// 状态 00 待处理 01 处理中 02 处理成功 03 处理失败
	private String tradeDate;// 交易日期
	private String md5Str;// MD5值
	private String dealLog;
	private String dealType;
	private String fileName;
	private List<FMSDownloadBean> tradeTypeList;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<FMSDownloadBean> getTradeTypeList() {
		return tradeTypeList;
	}

	public void setTradeTypeList(List<FMSDownloadBean> tradeTypeList) {
		this.tradeTypeList = tradeTypeList;
	}
	
	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getDealLog() {
		return dealLog;
	}

	public void setDealLog(String dealLog) {
		this.dealLog = dealLog;
	}

	public String getMd5Str() {
		return md5Str;
	}

	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
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

	public int getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(int allCnt) {
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

}
