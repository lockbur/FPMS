package com.forms.prms.tool.fms.send.domain;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SendDao {

	/**
	 * 生成31应付发票及预付款核销
	 */
	public void addFms31ToUpload(@Param("org1Code")String org1Code);


	/**
	 * 生成34订单详情
	 */
	public void addFms34ToUpload(@Param("org1Code")String org1Code);

	/**
	 * 冲销、摊销、预提处理
	 * @param yyyymm
	 * @param taskType
	 */
	public void callERP33(@Param("org1Code") String org1Code,@Param("yyyymm") String yyyymm,@Param("taskType") String taskType);

}
