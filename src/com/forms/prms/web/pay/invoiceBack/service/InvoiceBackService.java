package com.forms.prms.web.pay.invoiceBack.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.pay.invoiceBack.dao.InvoiceBackDao;
import com.forms.prms.web.pay.paycommon.domain.PayInfo;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.pay.paymodify.dao.PayModifyDAO;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;

@Service
public class InvoiceBackService {

	@Autowired
	private InvoiceBackDao invoiceBackDao;
	@Autowired
	private PayCommonService payCommonService;
	@Autowired
	private PayModifyDAO payModifyDAO;
	/**
	 * 查询发票退回的信息
	 */
	public List<PayQueryBean> list(PayQueryBean payQueryBean) {
		CommonLogger.info("查询发票退回的信息，InvoiceBackService，list");
		InvoiceBackDao pageDao = PageUtils.getPageDao(invoiceBackDao);
		payQueryBean.setOuCode(WebHelp.getLoginUser().getOuCode());// 得到登录人所在的OU
		return pageDao.list(payQueryBean);
	}

	/**
	 * 退回经办
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean backToModify(PaySureBean bean) throws Exception	{
		PayInfo payInfo = payCommonService.getPayInfo(bean.getPayId());
		if(!"CD".equals(payInfo.getDataFlag()) && !"C0".equals(payInfo.getDataFlag())&&!"C1".equals(payInfo.getDataFlag()))
		{
			CommonLogger.error("付款单状态状态为:"+payInfo.getDataFlag()+",不能退回。");
			return false;
		}
		
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {//预付款
			bean.setTable("TD_PAY_ADVANCE");
		} else {//正常付款
			bean.setTable("TD_PAY");
			//正常付款，如果冻结了预算则做预算的释放
			if("0".equals(bean.getIsFrozenBgt())){
				Map<String, String> param = new HashMap<String, String>();
				param.put("payid", bean.getPayId());
				param.put("memo", "02");//复核退回
				param.put("retMsg", "");
				payModifyDAO.deletePayFreeBgt(param);
				//预算处理失败，抛出异常
				if(null == param.get("retMsg") || "".contentEquals(param.get("retMsg")) || "0".contentEquals(param.get("retMsg")))
				{
					CommonLogger.error("扫描岗将财务中心退回的发票退回经办："+bean.getPayId()+" ，释放预算失败");
					throw new Exception("扫描岗将财务中心退回的发票退回经办："+bean.getPayId()+" ，释放预算失败");
				}
			}
		}
		if("CD".equals(payInfo.getDataFlag()))
		{
			bean.setPayDataFlag("AD");
		}
		else if("C0".equals(payInfo.getDataFlag())||"C1".equals(payInfo.getDataFlag()))
		{
			bean.setPayDataFlag("AC");
		}
		//更新付款的状态
		CommonLogger.info("发票退回经办（付款单号："+bean.getPayId()+"），更新状态AD，InvoiceBackService，backToModify");
		invoiceBackDao.updatePayStatus(bean);
		//添加log操作日志到TD_PAY_AUDIT_LOG表
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setAuditMemo("退回经办："+bean.getAuditMemo());
		
		CommonLogger.info("发票退回经办（付款单号："+bean.getPayId()+"），添加log信息，InvoiceBackService，backToModify");
		invoiceBackDao.addPayLog(bean);
		
		/**统计首页信息**/
		
		//添加到待修改
		CommonLogger.info("发票退回经办添加付款单号"+bean.getPayId()+"的数据到统计表中,InvoiceBackService,backToModify");
		payCommonService.addSysWarnPayInfo(bean.getPayId(),payInfo.getDutyCode(),"P0");
		return true;
	}

	/**
	 * 重新扫描
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean backToScan(PaySureBean bean) {
		if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {//预付款
			bean.setTable("TD_PAY_ADVANCE");
		} else {//正常付款
			bean.setTable("TD_PAY");
		}
		bean.setPayDataFlag("C0");
		//更新付款的状态
		CommonLogger.info("发票退回重新扫描（付款单号："+bean.getPayId()+"），更新状态C0，InvoiceBackService，backToScan");
		invoiceBackDao.updatePayStatus(bean);
		//添加log操作日志到TD_PAY_AUDIT_LOG表
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		bean.setAuditMemo("退回扫描："+bean.getAuditMemo());
		CommonLogger.info("发票退回重新扫描（付款单号："+bean.getPayId()+"），添加log信息，InvoiceBackService，backToScan");
		invoiceBackDao.addPayLog(bean);
		return true;
	}

}
