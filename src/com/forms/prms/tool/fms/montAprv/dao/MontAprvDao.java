package com.forms.prms.tool.fms.montAprv.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MontAprvDao {
	
	String getCountInTable(@Param("type")String type, @Param("org21Code")String org21Code);

	String getCountInFut(@Param("type")String type, @Param("org21Code")String org21Code);

	String getStatusTransfer();

	void updateStatus(@Param("type")String type, @Param("org21Code")String org21Code, @Param("statusOld")String string, @Param("statusNew")String string2);
	/**
	 * 转移数据
	 */
	void transferAprv();

	String getCountPoFut(@Param("dutyCode")String dutyCode);
	/**
	 * 得到需要转移的数据
	 * @return
	 */
	List<String> getNoTransfer();
	/**
	 * 年初转移数据
	 * @param object
	 */
	void transfer(@Param("batchNo")String batchNo);
	/**
	 * 校验是否导入了审批链
	 * @param type
	 * @param org21Code
	 * @return
	 */
	String isHaveImportData(@Param("type")String type, @Param("org21Code")String org21Code);

}
