package com.forms.dealexceldata.exceldealtool.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.dealexceldata.exceldealtool.domain.ImportBean;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;

@Repository
public interface ExcelDealDao {
	/**
	 * 更新任务状态
	 * 
	 * @param importBean
	 * @return
	 */
	public int updateTaskLoadStatus(ImportBean importBean);

	/**
	 * 根据taskId找导入的汇总数据
	 * 
	 * @param importBean
	 * @return
	 */
	public ImportBean getTaskLoadDataById(@Param("taskId") String taskId);

	public int queryById(@Param("tableName") String tableName, @Param("taskBatchNo") String taskBatchNo);

	public int deleteDataById(@Param("batchNo") String batchNo, @Param("tableName") String tableName);

	public int updateBgtSumTotal(ImportBean iBean);

	public int updateMontAprv(ImportBean iBean);
	
	public int updateUserRoleRln(ImportBean iBean);

	public CommonExcelDealBean queryTaskByBatchNo(@Param("batchNo")String batchNo);

}
