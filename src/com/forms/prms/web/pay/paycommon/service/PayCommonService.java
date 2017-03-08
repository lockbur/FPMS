package com.forms.prms.web.pay.paycommon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.paycommon.dao.PayCommonDAO;
import com.forms.prms.web.pay.paycommon.domain.PayInfo;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;

@Service
public class PayCommonService {

	@Autowired
	private PayCommonDAO payCommonDAO;
	@Autowired
	private SysWarnCountService sysWarnCountService;

	private final String busType = "P";
	/**
	 * 插入SYS_WARN_PAY_INFO表
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addSysWarnPayInfo(String payId, String dutyCode, String funcType) {
		CommonLogger.info("添加SYS_WARN_PAY_INFO付款信息（付款单号："+payId+"）,PayCommonService,addSysWarnPayInfo");
		 payCommonDAO.addSysWarnPayInfo(this.getPayInfoBean(payId,
				dutyCode, funcType));
		 sysWarnCountService.DealSysWarnCount(dutyCode,busType);
	}

	/**
	 * 更新SYS_WARN_PAY_INFO表
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateSysWarnPayInfo(String payId, String dutyCode,
			String funcType) {
		CommonLogger.info("更新SYS_WARN_PAY_INFO付款信息（付款单号："+payId+"）,PayCommonService,updateSysWarnPayInfo");
		 payCommonDAO.updateSysWarnPayInfo(this.getPayInfoBean(payId,
				dutyCode, funcType));
		 sysWarnCountService.DealSysWarnCount(dutyCode,busType);
	}

	
	/**
	 * 删除SYS_WARN_PAY_INFO表
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delSysWarnPayInfo(String payId, String dutyCode,
			String funcType) {
		CommonLogger.info("删除SYS_WARN_PAY_INFO付款信息（付款单号："+payId+"）,PayCommonService,delSysWarnPayInfo");
		payCommonDAO.delSysWarnPayInfo(this.getPayInfoBean(payId,
				dutyCode, funcType));
		sysWarnCountService.DealSysWarnCount(dutyCode,busType);
	}
	
	
	
	/**
	 * 封装bean
	 * 
	 * @param payId
	 * @param dutyCode
	 * @param funcType
	 * @return
	 */
	private PayInfo getPayInfoBean(String payId, String dutyCode,
			String funcType) {
		PayInfo payInfo = new PayInfo();
		payInfo.setPayId(payId);
		payInfo.setDutyCode(dutyCode);
		payInfo.setFuncType(funcType);
		return payInfo;
	}
	
	/**
	 * 查询付款单的责任中心及一级行
	 * 
	 */
	public PayInfo getPayInfo(String payId){
		return payCommonDAO.getPayInfo(payId);
	}
	
	
	/**
	 * AP发票生成前，校验资产类合同付款单是否全部冻结预算，若无则退回付款单。
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateChangePay(){
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		
		//1）添加到log表中
		String operMemo = "付款单缺乏预算冻结。";
		CommonLogger.info("添加由于付款单无冻结预算退回的付款单的日志记录。，PayCommonService，updateChangePay()");
		payCommonDAO.addChangePayLog(operMemo,org1Code);
		
		//2）改状态；
		CommonLogger.info("退回付款单，资产类付款单缺乏预算冻结。，PayCommonService，updateChangePay()");
		payCommonDAO.updateChangePay(org1Code);
		
		//3）更新首页付款待修改统计信息。
		CommonLogger.info("更新首页付款待修改统计信息。，PayCommonService，updateChangePay()");
		payCommonDAO.addBatchSysWarnPayInfo(org1Code);
		
		return true;
		
	}
}
