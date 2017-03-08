package com.forms.prms.web.cleanpaydeal.cleanpayquery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;

@Repository
public interface CleanPayQueryDao {
	public List<CleanPayBean> getList(CleanPayBean bean);
	
	
	/**
	 * @methodName getCleanPayReportData
	 * desc  暂收结清封面打印
	 * 
	 * @param sortId
	 * @return
	 */
	public CleanPayBean getCleanPayReportData(@Param(value="sortId") String sortId,@Param(value="normalPayId")String normalPayId);
	
	public CleanPayBean getBeforeCleanAmt(@Param(value="sortId") String sortId,@Param(value="normalPayId")String normalPayId);

}
