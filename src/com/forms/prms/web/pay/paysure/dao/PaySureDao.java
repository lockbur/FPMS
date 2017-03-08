package com.forms.prms.web.pay.paysure.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.paysure.domain.PaySureBean;

@Repository
public interface PaySureDao {
	/**
	 * 合同预付款和付款待确认信息列表查询
	 * 
	 * @param PaySureBean
	 * @return
	 */
	public List<PaySureBean> getList(PaySureBean bean);

	PaySureBean getPayByPayId(String payId);// 查找 付款单号的所有信息

	PaySureBean getPrePayByPayId(String payId);// 查找 预付款单号的所有信息

	public List<PaySureBean> getPrePayDeviceListByPayId(String payId);

	public List<PaySureBean> getPayDeviceListByPayId(String payId);

	public List<PaySureBean> getPrePayCancleListByCntNum(PaySureBean bean);

	public void agreePrePay(PaySureBean bean);

	public void agreePay(PaySureBean bean);

	public void backPrePay(PaySureBean bean);

	public void backPay(PaySureBean bean);

	public void addLog(PaySureBean bean);// 增加操作日志

	public List<PaySureBean> ouCodeList(String org1Code);
}
