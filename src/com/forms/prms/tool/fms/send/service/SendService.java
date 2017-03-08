package com.forms.prms.tool.fms.send.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.send.domain.SendDao;

@Service
public class SendService {

	@Autowired
	private SendDao dao;

	/**
	 * 生成31应付发票及预付款核销
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addFms31ToUpload() {
		CommonLogger.info("生成31应付发票及预付款核销接口数据！,SendService,addFms31ToUpload()");
		dao.addFms31ToUpload(WebHelp.getLoginUser().getOrg1Code());
	}


	/**
	 * 生成34订单明细
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addFms34ToUpload() {
		CommonLogger.info("生成34订单明细接口数据！,SendService,addFms34ToUpload()");
		dao.addFms34ToUpload(WebHelp.getLoginUser().getOrg1Code());
	}

	/**
	 * 调用ERP33处理过程
	 * @param yyyymm
	 * @param taskType
	 */
	@Transactional(rollbackFor = Exception.class)
	public void callERP33(String org1Code,String yyyymm, String taskType) {
		CommonLogger.info("调用ERP33处理预提待摊过程！,SendService,callERP33()【org1Code:"+org1Code+";yyyymm:"+yyyymm+";taskType:"+taskType+";】");
		dao.callERP33(org1Code,yyyymm, taskType);
	}

}
