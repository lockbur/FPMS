package com.forms.prms.web.amortization.unnormalDataMgr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.unnormalDataMgr.domain.UnnorDataMgrBean;

/**
 * Title:		unnorDataMgrDAO
 * Description:	异常数据查询的DAO层
 * Copyright: 	formssi
 * @author: 	
 * @project: 	ERP
 * @date: 		2015-07-02
 * @version:	1.0
 */
@Repository
public interface UnnorDataMgrDAO {
	
	//查询总账凭证记录
	public List<UnnorDataMgrBean> getOrderCancelList(Map<String , Object> mapObj);

	public int unnormalDataUpdate(UnnorDataMgrBean unnorDataMgrBean);

}
