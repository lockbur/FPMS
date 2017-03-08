package com.forms.prms.web.amortization.abnormalDataMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidAccountBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApInvoiceBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApPayBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidCglAmtBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidOrderBean;

/**
 * Title:		AbnorDataMgrDAO
 * Description:	异常数据查询的DAO层
 * Copyright: 	formssi
 * @author: 	HQQ
 * @project: 	ERP
 * @date: 		2015-04-08
 * @version:	1.0
 */
@Repository
public interface AbnorDataMgrDAO {
	
	//查询总账凭证记录
	public List<TidAccountBean> getTidAccountQueryList(Map<String , Object> mapObj);
	
	//查询AP发票接口记录
	public List<TidApInvoiceBean> getTidApInvoiceQueryList(Map<String , Object> mapObj);
	
	//查询AP付款接口记录
	public List<TidApPayBean> getTidApPayQueryList(Map<String , Object> mapObj);
	
	//查询科目余额记录
	public List<TidCglAmtBean> getTidCglAmtQueryList(Map<String , Object> mapObj);
	
	//查询订单接口记录
	public List<TidOrderBean> getTidOrderQueryList(Map<String , Object> mapObj);

}
