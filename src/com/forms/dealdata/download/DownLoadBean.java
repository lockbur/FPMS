package com.forms.dealdata.download;

public class DownLoadBean 
{
	private String batchNo;
	
	private String tradeDate;
	
	private String tradeType;
	
	private String fileName;
	
	public DownLoadBean(String batchNo,String tradeDate,String tradeType,String fileName)
	{
		this.batchNo = batchNo;
		this.tradeDate = tradeDate;
		this.tradeType = tradeType;
		this.fileName = fileName;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
