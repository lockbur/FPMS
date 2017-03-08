package com.forms.prms.web.amortization.payErrorData.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.payErrorData.domain.PayErrorDataBean;

/**
 * Title:		PayErrorDataDAO
 * Description:	付款异常数据补录模块的DAO层
 * Copyright: 	formssi
 * @author: 	wangtao
 * @project: 	ERP
 * @date: 		2015-05-25
 * @version:	1.0
 */
@Repository
public interface PayErrorDataDAO {
	//获取列表
	public List<PayErrorDataBean> getList(PayErrorDataBean payErrorDataBean);
	
	public int deal(PayErrorDataBean payErrorDataBean);
	
	
	//根据合同号查询合同信息
	public PayErrorDataBean constractInfo(PayErrorDataBean payErrorDataBean);
	
	
	//查询付款信息（正常或预付款）
	public PayErrorDataBean queryPayInfo(PayErrorDataBean payErrorDataBean);
	
	public List<PayErrorDataBean> queryPayAdvanceCancel(PayErrorDataBean payErrorDataBean);
	
	/**
	 * 查询合同采购设备信息
	 * 
	 * @param payModifyBean
	 * @return
	 */
	public List<PayErrorDataBean> queryDevicesById(PayErrorDataBean payErrorDataBean);

	public List<PayErrorDataBean> getOrgList();

}
