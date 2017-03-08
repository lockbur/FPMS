package com.forms.prms.web.amortization.accEntry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.amortization.accEntry.dao.AccEntryDAO;
import com.forms.prms.web.amortization.accEntry.domain.CglTrade;

@Service
public class AccEntryService {
	
	@Autowired
	private AccEntryDAO dao;
	
	/**
	 * 获取会计分录明细
	 * @param cntNum
	 * @return
	 */
	public List<CglTrade> getAccEntry(String cntNum,String startDate,String endDate){
		CommonLogger.info("查询会计分录明细信息(合同号:"+cntNum+";开始日期:"+startDate+";结束日期:"+endDate+"),ApproveChainService,preserveList");
		return dao.getAccEntry(cntNum,startDate,endDate);
	}
}
