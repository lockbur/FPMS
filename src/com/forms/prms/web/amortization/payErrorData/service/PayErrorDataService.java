package com.forms.prms.web.amortization.payErrorData.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.payErrorData.dao.PayErrorDataDAO;
import com.forms.prms.web.amortization.payErrorData.domain.PayErrorDataBean;

/**
 * Title: PayErrorDataService Description: 付款异常数据补录模块的Service层 Copyright:
 * formssi
 * 
 * @author： wangtao
 * @project： ERP
 * @date： 2015-05-25
 * @version： 1.0
 */
@Service
public class PayErrorDataService {

	@Autowired
	private PayErrorDataDAO payErrorDataDAO;

	// 获取信息列表
	public List<PayErrorDataBean> getList(PayErrorDataBean payErrorDataBean) {
		CommonLogger.info("查询订单类异常数据列表！,PayErrorDataService,getList()");
		// 得到登录人所在的一级行
		payErrorDataBean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());//一级行
		payErrorDataBean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());//二级行
		payErrorDataBean.setDutyCode(WebHelp.getLoginUser().getDutyCode());//责任中心
		
		PayErrorDataDAO pageDao = PageUtils.getPageDao(payErrorDataDAO);
		return pageDao.getList(payErrorDataBean);
	}
	
	public int deal(PayErrorDataBean payErrorDataBean) {
		CommonLogger.info("更新订单类异常的处理信息（" + payErrorDataBean.getBatchNo() + "，"+payErrorDataBean.getSeqNo()+"）PayErrorDataService，deal");
		payErrorDataBean.setDealUser(WebUtils.getUserModel().getUserId());
//		payErrorDataBean.setIsDealed("Y");
		return payErrorDataDAO.deal(payErrorDataBean);
	}
	
	// 根据合同信息查合同信息
	public PayErrorDataBean constractInfo(PayErrorDataBean payErrorDataBean) {
		CommonLogger.info("查询合同号" + payErrorDataBean.getCntNum() + "的合同信息，PayErrorDataService，constractInfo");
		return payErrorDataDAO.constractInfo(payErrorDataBean);
	}

	// 根据付款单号查付款信息
	public PayErrorDataBean queryPayInfo(PayErrorDataBean payErrorDataBean) {
		CommonLogger.info("查询付款单号" + payErrorDataBean.getPayId() + "的付款信息，PayErrorDataService，queryPayInfo");
		return payErrorDataDAO.queryPayInfo(payErrorDataBean);
	}

	/**
	 * 根据合同号查预付款核销信息
	 * 
	 * @param payErrorDataBean
	 * @return
	 */
	public List<PayErrorDataBean> queryPayAdvanceCancel(PayErrorDataBean payErrorDataBean) {
		CommonLogger
				.info("查询合同号" + payErrorDataBean.getCntNum() + "的预付款核销信息，PayErrorDataService，queryPayAdvanceCancel");
		return payErrorDataDAO.queryPayAdvanceCancel(payErrorDataBean);
	}

	/**
	 * 查询合同采购设备信息
	 * 
	 * @param payErrorDataBean
	 * @return
	 */
	public List<PayErrorDataBean> queryDevicesById(PayErrorDataBean payErrorDataBean) {
		CommonLogger.info("查询付款单号" + payErrorDataBean.getPayId() + "的合同采购设备付款信息，PayErrorDataService，queryDevicesById");
		return payErrorDataDAO.queryDevicesById(payErrorDataBean);
	}

	public List<PayErrorDataBean> getOrgList() {
		return payErrorDataDAO.getOrgList();
	}
}
