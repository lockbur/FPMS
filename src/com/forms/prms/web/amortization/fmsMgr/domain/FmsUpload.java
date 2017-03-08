package com.forms.prms.web.amortization.fmsMgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.forms.prms.web.user.domain.User;

public class FmsUpload implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String batchNo     ; // 批次号        
	private String ouCode      ; // OU代码 
	private String likeOuCode ;//用于OU代码模糊查询的
	private String ouName      ; // OU代码 
	private String likeOuName ;//用于OU代码模糊查询的
	private String tradeType   ; // 交易类型      
	private BigDecimal allCnt      ; // 总笔数        
	private BigDecimal chkSuccCnt      ; // 成功笔数        
	private String uploadPath  ; // 上传文件路径  
	private String uploadDate  ; // 上传日期      
	private String uploadTime  ; // 上传时间      
	private String downloadPath; // 下载文件路径  
	private String downloadDate; // 下载日期      
	private String downloadTime; // 下载时间      
	private String dataFlag    ; // 状态          
	private String tradeDate   ; // 交易日期      
	private String seqNo       ; // 序号 
	private String startDate   ; // 查询起始日期
	private String endDate     ; // 查询结束日期
	private String fileFlag	   ; // 文件类型标识
	
	private String dealLog;
	
	private User user;      //登录用户
	
	
	
	public String getLikeOuCode() {
		return likeOuCode;
	}
	public void setLikeOuCode(String likeOuCode) {
		this.likeOuCode = likeOuCode;
	}
	public String getLikeOuName() {
		return likeOuName;
	}
	public void setLikeOuName(String likeOuName) {
		this.likeOuName = likeOuName;
	}
	public String getOuName() {
		return ouName;
	}
	public void setOuName(String ouName) {
		this.ouName = ouName;
	}
	public String getDealLog() {
		return dealLog;
	}
	public void setDealLog(String dealLog) {
		this.dealLog = dealLog;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BigDecimal getChkSuccCnt() {
		return chkSuccCnt;
	}
	public void setChkSuccCnt(BigDecimal chkSuccCnt) {
		this.chkSuccCnt = chkSuccCnt;
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
