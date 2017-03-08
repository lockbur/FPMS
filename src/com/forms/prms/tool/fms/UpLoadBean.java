package com.forms.prms.tool.fms;

import java.io.Serializable;
import java.math.BigDecimal;

public class UpLoadBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7484878611981667158L;

	private String batchNo;// 批次号
	private String ouCode;// OU代码
	private String tradeType;// 交易类型 31-应付发票 32-预付款核销 33-预提待摊 34-采购订单
	private BigDecimal allCnt;// 总笔数
	private String uploadPath;// 上传文件路径
	private String uploadDate;// 上传日期
	private String uploadTime;// 上传时间
	private String downloadPath;// 下载文件路径
	private String downloadDate;// 下载日期
	private String downloadTime;// 下载时间
	private String dataFlag;// 状态 00 待处理01 处理中02 发送成功03 处理中04 校验成功05 检验失败"
	private String[] dataFlags;// 状态 00 待处理01 处理中02 发送成功03 处理中04 校验成功05 检验失败"
	private String tradeDate;// 交易日期
	private String seqNo;// 序号
	private String dealLog;
	private String sqlStr;
	
	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String[] getDataFlags() {
		return dataFlags;
	}

	public void setDataFlags(String[] dataFlags) {
		this.dataFlags = dataFlags;
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

	public String getOuCode() {
		return ouCode;
	}

	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
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

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
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

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

}
