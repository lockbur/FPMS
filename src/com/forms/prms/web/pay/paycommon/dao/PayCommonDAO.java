package com.forms.prms.web.pay.paycommon.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.pay.paycommon.domain.PayInfo;

@Repository
public interface PayCommonDAO {

	/**
	 * 插入SYS_WARN_PAY_INFO表
	 */
	public int addSysWarnPayInfo(PayInfo payInfo);

	/**
	 * 更新SYS_WARN_PAY_INFO表
	 */
	public int updateSysWarnPayInfo(PayInfo payInfo);

	public int delSysWarnPayInfo(PayInfo payInfoBean);

	/**
	 * 查询付款单的责任中心及一级行
	 * 
	 */
	public PayInfo getPayInfo(String payId);
	
	
	/**
	 * 将有费用转资产的合同未上送付款单状态改AF
	 * @return
	 */
	public int updateChangePay(@Param("org1Code")String org1Code);
	
	/**
	 * 变更添加到log表中
	 * @return
	 */
	public int addChangePayLog(@Param("operMemo")String operMemo,@Param("org1Code")String org1Code);
	
	/**
	 * 批量插入SYS_WARN_PAY_INFO表
	 * @return
	 */
	public int addBatchSysWarnPayInfo(@Param("org1Code")String org1Code);

}
