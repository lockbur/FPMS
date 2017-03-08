package com.forms.prms.web.pay.payexamine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.payexamine.domain.PayExamineBean;

@Repository
public interface PayExamineDao {
	/**
	 * 合同预付款和付款信息列表查询
	 * 
	 * @param PayExamineBean
	 * @return
	 */
	public List<PayExamineBean> getList(PayExamineBean bean);

	PayExamineBean getPayByPayId(String payId);// 查找 付款单号的所有信息

	PayExamineBean getPrePayByPayId(String payId);// 查找 预付款单号的所有信息

	public List<PayExamineBean> getPrePayDeviceListByPayId(String payId);

	public List<PayExamineBean> getPayDeviceListByPayId(String payId);

	public List<PayExamineBean> getPrePayCancleListByCntNum(PayExamineBean bean);

	public void agreePrePay(PayExamineBean bean);

	public void agreePay(PayExamineBean bean);

	public void backPrePay(PayExamineBean bean);

	public void backPay(PayExamineBean bean);

	public List<String> getinstDutyCodes(PayExamineBean bean);

	public void addIcms(PayExamineBean bean);

	public List<PayExamineBean> scanList(PayExamineBean bean);// 查询扫描汇总

	public List<PayExamineBean> scanDetailList(PayExamineBean bean);// 查询扫描明细列表

	public int scanAgreePrePay(PayExamineBean bean);

	public int scanAgreePay(PayExamineBean bean);

	public int scanBackPrePay(PayExamineBean bean);

	public int scanBackPay(PayExamineBean bean);

	public void addArgeePayLog(PayExamineBean bean);

	public void addBackPayLog(PayExamineBean bean);
	
	public int updateScanBatchDataFlag(@Param("batchNo") String batchNo);

	public String isOrder(@Param("payId")String payId);

	public String isOrder1(@Param("payId")String payId);

}
