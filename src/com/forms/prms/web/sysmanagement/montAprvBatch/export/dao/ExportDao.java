package com.forms.prms.web.sysmanagement.montAprvBatch.export.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;

@Repository
public interface ExportDao {

	public List<ExportBean> getMontContent(@Param("batchNo")String batchNo);

	public List<ExportBean> getAprvContent(@Param("batchNo")String batchNo);
	/**
	 * 得到最大年份
	 * @param bean
	 * @return
	 */
	public ExportBean getLastYearData(ExportBean bean);
	/**
	 * 正式表中得到数据监控指标
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getMontList(ExportBean bean);
	/**
	 * 监控指标历史表
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getMontListForThisYear(ExportBean bean);
	/**
	 * 历史表专项包
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getAprvZxHisList(ExportBean bean);

	public ExportBean getLastYearDataAprvZX(ExportBean eb);

	public void deleteErrorData(ExportBean eb);
	/**
	 * 拿正式表所有数据
	 * @param bean
	 * @return
	 */
	public List<ExportBean> lastYearMontList(ExportBean bean);


	/**
	 * 拿出
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getAllMontList(ExportBean bean);
	/**
	 * 查询导出审批链对应的监控指标 是在哪个 表里 正式 或者FUT
	 * @return
	 */
	public String getCountFromMont(ExportBean bean);
	/**
	 * 监控指标是否在正式表
	 * @param bean
	 * @return
	 */
	public String isExistsFromMont(ExportBean bean);
	/**
	 * 转移触发数据
	 * @param string 
	 * @param bean
	 */
	public void transferMontData(@Param("montType")String montType, @Param("org21Code")String org21Code,
			@Param("dataYear")String dataYear, @Param("type")String type);
	/**
	 * 有没有这类型的数据
	 * @param bean
	 * @return
	 */
	public int isFirst(ExportBean bean);

	public List<ExportBean> selectInitData(ExportBean bean);

	/**
	 * 更新导出任务状态
	 * @param taskId
	 */
	public int updateTaskDataFlag(@Param("taskId")String taskId);

	public String showYear(ExportBean eb);

	public int isWorking(ExportBean eb);
	/**
	 * 导出监控指标
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getLastMontsFromTable(ExportBean bean);
	/**
	 * 校验今年的监控指标是否已经制定好了
	 * @param eb
	 * @return
	 */
	public int yearMontIsOk(ExportBean eb);
	/**
	 * 当年的审批链直接从表里取
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getAprvSpecTable(ExportBean bean);
	/**
	 * 不是当年的审批链得转换一下
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getAprvSpecTableChange(ExportBean bean);
	/**
	 * 非专项包当年的审批链直接从表里取
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getAprvNoSpecTable(ExportBean bean);
	/**
	 * 非专项包不是当年的审批链得转换一下
	 * @param bean
	 * @return
	 */
	public List<ExportBean> getAprvNoSpecTableChange(ExportBean bean);
	/**
	 * 审批链初次导出的初始化数据
	 * @param bean
	 * @return
	 */
	public List<ExportBean> selectInitAprvData(ExportBean bean);
	/**
	 * 专项包初始化数据
	 * @param bean
	 * @return
	 */
	public List<ExportBean> selectInitAprvDataSpec(ExportBean bean);
	/**
	 * 是否有待勾选的审批链
	 * @param eb
	 * @return
	 */
	public int isNoUpAprv(ExportBean eb);
	/**
	 * 加一条汇总信息
	 * @param eb
	 */
	public void insertSum(ExportBean eb);

	public int getCountAprvSpecTable(ExportBean bean);




		
}
