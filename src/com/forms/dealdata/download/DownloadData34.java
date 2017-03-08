package com.forms.dealdata.download;

import org.springframework.stereotype.Component;

@Component
public class DownloadData34 extends DownloadData {
	
	@Override
	public void dealFile(String batchNo, String tradeDate, String tradeType)
			throws Exception {
		super.dealFile(batchNo, tradeDate, tradeType);
		//校验文件，更新数据
		checkData(batchNo, tradeDate, tradeType);
	}

}
