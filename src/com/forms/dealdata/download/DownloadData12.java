package com.forms.dealdata.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.service.OrgService;

@Component
public class DownloadData12 extends DownloadData {
	
	@Autowired
	private OrgService orgService;
	
	@Override
	public void callProc(String batchNo,String tradeType) throws Exception {
		super.callProc(batchNo,tradeType);
		//清除机构树缓存
		orgService.removeCachedZtreeTag(new BaseBean());
	}
	
}
