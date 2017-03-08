package com.forms.dealdata.download;

import org.springframework.stereotype.Component;

@Component
public class DownloadData22 extends DownloadData {
	
	@Override
	public void dealFile(String batchNo, String tradeDate, String tradeType)
			throws Exception {
		super.dealFile(batchNo, tradeDate, tradeType);
		//转移数据到订单类正常付款表
		service.tranDataCleanLog(batchNo,tradeType);
	}

}
