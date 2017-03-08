package com.forms.prms.web.sysmanagement.homepage.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;



@Service
public class SysWarnCountService {
	
	@Autowired
	private HomePageService homePageService;
	
	/**
	 * 
	 * @param dutyCode 责任中心
	 * @param busType  业务类型  C：合同 P：付款   T:预提待摊
	 * 
	 * 
	 * */
	public void DealSysWarnCount(String dutyCode,String  busType)
	{
		CommonLogger.info("调用过程PRC_SYS_WARN_COUNT处理业务提醒数据汇总,SysWarnCountService,DealSysWarnCount");
		homePageService.DealSysWarnCount(dutyCode, busType);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void dealSysWarn(String dutyCode,String  busType){
		DealSysWarnCount( dutyCode,  busType);
	}
	
}
