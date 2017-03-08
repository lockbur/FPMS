package com.forms.dealdata.download;

public interface IDownloadData
{
	public void beforeDownload(String batchNo,String tradeType) throws Exception;
	
	public void download(String batchNo,String tradeDate,String tradeType,String srcFileName) throws Exception;
	
	public void dealFile(String batchNo,String tradeDate,String tradeType) throws Exception;
	
	public void callProc(String batchNo,String tradeType) throws Exception;
	
	public void afterDownload(String batchNo,String tradeType) throws Exception;
	
	public String execute(String batchNo,String tradeDate,String tradeType,String srcFileName) throws Exception;
	
}
