package com.forms.prms.web.pay.invoiceBack.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.pay.paysure.domain.PaySureBean;

@Repository
public interface InvoiceBackDao {

	/**
	 * 查询发票退回的信息
	 */
	public List<PayQueryBean> list(PayQueryBean payQueryBean);

	/**
	 * 更新付款的状态
	 * @param bean
	 * @return
	 */
	public int updatePayStatus(PaySureBean bean);

	/**
	 * 添加log操作日志到TD_PAY_AUDIT_LOG表
	 * @param bean
	 */
	public void addPayLog(PaySureBean bean);

}
