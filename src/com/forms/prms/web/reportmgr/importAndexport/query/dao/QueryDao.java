package com.forms.prms.web.reportmgr.importAndexport.query.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.reportmgr.importAndexport.query.domain.ImporExporCommonBean;

@Repository
public interface QueryDao {

	/**
	 * 查询导出列表
	 * @param exportReport
	 * @return
	 */
	public List<ImporExporCommonBean> getExportTaskList(ImporExporCommonBean exportBean);
	
	/**
	 * 查询单个导出记录
	 * @param exportReport
	 * @return
	 */
	public ImporExporCommonBean getExportTask(ImporExporCommonBean exportBean);
	
	
	/**
	 * 查询导入任务列表
	 * @param importReport
	 * @return
	 */
	public List<ImporExporCommonBean> getImportTaskList(Map params);
	
	/**
	 * 查询单个导入任务记录 
	 * @param importReport
	 * @return
	 */
	public ImporExporCommonBean getImportTask(ImporExporCommonBean importBean);
	
	
	//根据TaskID去表UPLOAD_DATA_CONTROL_INFO查找，如果该值在TASK_CNT_ID字段中存在则返回01("合同数据模板"),如果在TASK_PAY_ID字段中存在则返回02("付款数据模板")
	public String getUploadTypeByTaskId(@Param("taskId") String taskId);
	
	public Integer getErrRowCountByTaskId(@Param("taskId") String taskId);
	/**
	 * 根据批次号查询错误信息条数
	 * @param taskBatchNo
	 * @return
	 */
	public int getErrRowCountByBatchNo(@Param("taskBatchNo")String taskBatchNo);
	
	//根据Func的uri在表PF_FUNCTION中查找FuncId
	public String getFuncIdByFuncUri(String uri);
}
