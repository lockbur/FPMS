package com.forms.prms.web.pay.payquery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.pay.payquery.domain.CleanLogBean;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;

@Repository
public interface PayQueryDao {

	/**
	 * 合同列表查询
	 * 
	 * @param PayQueryBean
	 * @return
	 */
	public List<PayQueryBean> list(PayQueryBean bean);

	public PayQueryBean getPreCntByCntNum(String cntNum);

	public PayQueryBean getCntByCntNum(String cntNum);

	public List<PayQueryBean> getPrePayListByCntNum(String cntNum);

	public List<PayQueryBean> getPayListByCntNum(String cntNum);

	PayQueryBean getPrePayByPayId(String payId);// 查找 预付款单号的所有信息

	public List<PayQueryBean> getPayDeviceListByPayId(String payId);

	public List<PayQueryBean> getPrePayDeviceListByPayId(String payId);

	public List<PayQueryBean> getPrePayCancleListByCntNum(PayQueryBean bean);

	PayQueryBean getPayByPayId(String payId);// 查找 付款单号的所有信息

	public List<PayQueryBean> getPayCleanList(String payId);

	public List<PayQueryBean> getPayAdvanceCancelList(String payId);

	/**
	 * 查询付款Log信息
	 * 
	 * @param cleanLogBean
	 * @return
	 */
	public List<PayQueryBean> queryPayLog(@Param("payId") String payId);

	/**
	 * 查询正常付款的数据有几条(如果有多条则只更新结清明细表的状态)
	 * 
	 * @param payId
	 * @return
	 */
	public int queryNormalPayNumById(@Param("payId") String payId, @Param("payCancelState") String payCancelState);

	/**
	 * 更新结清明细表的状态
	 * 
	 * @param cleanLogBean
	 */
	public void updateCleanLog(CleanLogBean cleanLogBean);

	/**
	 * 更新累计结清金额
	 * 
	 * @param cleanLogBean
	 */
	public void updateSusTotalAmt(CleanLogBean cleanLogBean);

	/**
	 * 更新付款的状态
	 * 
	 * @param cleanLogBean
	 */
	public void updateDataFlagPay(CleanLogBean cleanLogBean);

	/**
	 * 付款流水明细
	 * 
	 * @param payQueryBean
	 * @return
	 */
	public PayQueryBean queryPayLogDetail(PayQueryBean payQueryBean);
	
	public String getOrder(PayQueryBean payQueryBean);

	/**
	 * 根据合同号得到合同的影像
	 * @param cntNum
	 * @return
	 */
	public QueryContract getCntICMSByCntNum(@Param("cntNum")String cntNum);

	/**
	 * 22付款明细查询
	 * @param invoiceId
	 * @return
	 */
	public List<PayQueryBean> queryPay22Detail(@Param("invoiceId")String invoiceId);
}
