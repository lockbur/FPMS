package com.forms.prms.tool.exceltool.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;

@Repository
public interface ICommonExcelDealDao {
	/**
	 * 获取待导入数据
	 * @return
	 */
	public List<CommonExcelDealBean> getLoadList();
	
	/**
	 * 获取待导出数据
	 * @return
	 */
	public List<CommonExcelDealBean> getExportList();
	
	/**
	 * 插入导入汇总数据
	 * @param bean 
	 */
	public void insertLoad(CommonExcelDealBean bean);
	
	/**
	 * 插入导出汇总数据
	 * @param bean 
	 */
	public void insertExport(CommonExcelDealBean bean);
	
	/**
	 * 更新导入状态
	 * @param taskId 导入任务Id
	 * @param dataFlag 更新状态
	 */
	public int updateLoadStatus(@Param("taskId") String taskId,@Param("dataFlag") String dataFlag);
	
	/**
	 * 更新导出状态
	 * @param taskId 导出任务Id
	 * @param dataFlag 更新状态
	 */
	public int updateExportStatus(@Param("taskId") String taskId,@Param("dataFlag") String dataFlag);
	
	/**
	 * 查询导入指定任务Id的状态
	 * @param taskId 导入任务Id
	 * @return
	 */
	public String getLoadStatus(@Param("taskId") String taskId);
	
	/**
	 * 查询导出指定任务Id的状态
	 * @param taskId 导出任务Id
	 * @return
	 */
	public String getExportStatus(@Param("taskId") String taskId);
	
	/**
	 * Generate生成获取导入任务Id
	 * @return
	 */
	public String getLoadTaskId();

	/**
	 * Generate生成获取导出任务Id
	 * @return
	 */
	public String getExportTaskId();
	
	/**
	 * 根据TaskId获取导入任务Task对象
	 * @methodName getLoadTaskById
	 * @param taskId
	 * @return
	 */
	public CommonExcelDealBean getLoadTaskById(String taskId);
	
	/**
	 * 更新导入结果
	 * @param bean 导入任务bean
	 */
	public void updateLoadResult(CommonExcelDealBean bean);
	
	/**
	 * 更新导入任务的操作备注ProcMemo
	 * @methodName updateLoadTaskProcMemo
	 * @param bean
	 */
	public void updateLoadTaskProcMemo(CommonExcelDealBean bean);
	
	/**
	 * 更新导出结果
	 * @param bean 导出任务bean
	 */
	public void updateExportResult(CommonExcelDealBean bean);
	
	
	/**
	 * 根据批次号获取导入错误信息
	 * @param taskId
	 * @return
	 */
	public List<UploadDataErrorInfoBean> getImportErrMsgByBatchNoAndUploadType(@Param("batchNo") String batchNo,@Param("uploadType") String uploadType);
	
	/**
	 * 新增导入时错误信息记录@methodName addUploadDataErrorInfo
	 * @param uploadDataErrBean
	 */
	public void addUploadDataErrorInfo(UploadDataErrorInfoBean uploadDataErrBean);
	
	
	public String getExportFileDestPathByTaskId(@Param("taskId") String taskId);
}
