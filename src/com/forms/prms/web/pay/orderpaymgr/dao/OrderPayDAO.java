package com.forms.prms.web.pay.orderpaymgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.orderpaymgr.domain.OrderPayBean;

@Repository
public interface OrderPayDAO {

	/**
	 * 订单类付款流水查询
	 * 
	 * @param orderPayBean
	 * @return
	 */
	public List<OrderPayBean> list(OrderPayBean orderPayBean);

	/**
	 * 查询正常付款信息
	 * 
	 * @param orderPayBean
	 * @return
	 */
	public List<OrderPayBean> queryPayInfo(OrderPayBean orderPayBean);

	/**
	 * 查询暂收结清信息
	 * 
	 * @param orderPayBean
	 * @return
	 */
	public List<OrderPayBean> querySusPInfo(OrderPayBean orderPayBean);

	/**
	 * 更新TD_PAY_CLEAN_LOG表
	 * 
	 * @param orderPayBean
	 */
	public void updateTpcl(OrderPayBean orderPayBean);

	/**
	 * 更新累计付款金额
	 * @param orderPayBean
	 */
	public void updatePayTotal(OrderPayBean orderPayBean);

	/**
	 * 更新付款状态
	 * @param orderPayBean
	 */
	public void updatePayDataFlag(OrderPayBean orderPayBean);

	/**
	 * 更新累计结清金额
	 * @param orderPayBean
	 */
	public void updateSusTotal(OrderPayBean orderPayBean);

	/**
	 * 更新暂收表的数据
	 * @param orderPayBean
	 */
	public void updateCleanAmtFms(OrderPayBean orderPayBean);

	/**
	 * 根据付款单、累计付款金额更新付款的状态
	 * @param orderPayBean
	 */
	public void updatePayDataFlagById(OrderPayBean orderPayBean);

	/**
	 * 付款取消需要更新整个付款的状态为F2
	 * @param orderPayBean
	 */
	public void updateDataFlag(OrderPayBean orderPayBean);

	public void updateDataFlag2(OrderPayBean orderPayBean);

}
