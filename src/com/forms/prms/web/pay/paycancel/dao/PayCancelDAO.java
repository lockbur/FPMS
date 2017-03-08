package com.forms.prms.web.pay.paycancel.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.paycancel.domain.PayCancelBean;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;

@Repository
public interface PayCancelDAO {

	/**
	 * 付款历史信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	public List<PayCancelBean> list(PayCancelBean payCancelBean);

	/**
	 * 查询合同付款信息
	 * 
	 * @param payId
	 * @return
	 */
	public PayCancelBean getPayByPayId(PayCancelBean payCancelBean);

	/**
	 * 查询付款设备信息
	 * 
	 * @param payId
	 * @return
	 */
	public List<PayQueryBean> getPayDeviceListByPayId(
			PayCancelBean payCancelBean);

	/**
	 * 查询预付款核销列表
	 * 
	 * @param cntNum
	 * @return
	 */
	public List<PayQueryBean> getPrePayCancleListByCntNum(
			PayCancelBean payCancelBean);

	/**
	 * 正常付款更新合同信息（预付款、正常付款、暂收）
	 * 
	 * @param payCancelBean
	 */
	public int updateCntInfoToPay(PayCancelBean payCancelBean);

	/**
	 * 正常付款更新合同设备信息（物料的已付金额）
	 * 
	 * @param payCancelBean
	 */
	public int updateCntDevInfo(PayCancelBean payCancelBean);

	/**
	 * 预付款更新合同信息（预付款）
	 * 
	 * @param payCancelBean
	 */
	public int updateCntInfoToPrePay(PayCancelBean payCancelBean);

	/**
	 * 更新付款表或预付表的状态
	 * 
	 * @param payCancelBean
	 * 
	 */
	public int updateDataFlag(PayCancelBean payCancelBean);

	/**
	 * 预付款核销信息更新
	 * @param pcBean
	 */
	public void updateAdvCancelInfo(PayCancelBean pcBean);

	/**
	 * 更新设备付款信息
	 * @param pcBean
	 */
	public void updatePayDevInfo(PayCancelBean pcBean);

	/**
	 * 更新付款表的金额
	 * @param payCancelBean
	 * @return
	 */
	public int updatePayInfo(PayCancelBean payCancelBean);

	/**
	 * 更新预付款信息
	 * @param payCancelBean
	 * @return
	 */
	public int updateAdvInfo(PayCancelBean payCancelBean);

	/**
	 * 根据id查询付款信息
	 * @param payId
	 * @return
	 */
	public PayCancelBean queryPayInfoById(PayCancelBean payCancelBean);

}
