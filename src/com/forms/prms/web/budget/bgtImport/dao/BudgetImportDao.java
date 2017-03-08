package com.forms.prms.web.budget.bgtImport.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.bgtImport.domain.BudgetImportBean;
import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
@Repository
public interface BudgetImportDao {
	/*8
	 * 预算导入的汇总信息列表
	 */
	public List<BudgetImportBean> getImportList(BudgetImportBean bean);
	/**
	 * 查看是否有正在导入的监控指标
	 * @param bean
	 * @return
	 */
	public int getMontCount(BudgetImportBean bean);
	/**
	 * 校验正式预算是否下达了
	 * @param bean
	 * @return
	 */
	public BudgetImportBean checkZsBgtIsDone(BudgetImportBean bean);
	/**
	 * 校验初始预算只能导入一次
	 * @param bean
	 * @return
	 */
	public int checkInitCount(BudgetImportBean bean);
	/**
	 * 校验正式预算追加时必须要先下正式预算初次下达
	 * @param bean
	 * @return
	 */
	public int checkBgt(BudgetImportBean bean);
	/**
	 * 得到批次信息
	 * @return
	 */
	public String getBatchNo();
	/**
	 * 插入汇总信息
	 * @param bean
	 */
	public void insertSummary(BudgetImportBean bean);
	
	public int updateStatus(@Param("batchNo")String batchNo, @Param("preStatus")String preStatus, @Param("nextStatus")String nexStatus,@Param("memo")String memo);
	/**
	 * 预算明细数据的导入
	 * @param budgetBeans
	 */
	public void insertDetail(@Param("budgetBeans")List<BudgetImportBean> budgetBeans);
	/*8
	 * 导入后校验存储过程
	 */
	public void checkBudgetProduce(String batchNo);
	/**
	 * 删除
	 * @param bean
	 */
	public void delSummary(BudgetImportBean bean);
	public void delDetail(BudgetImportBean bean);
	/**
	 * 得到汇总信息
	 * @param bean
	 * @return
	 */
	public BudgetImportBean getTotalInfo(BudgetImportBean bean);
	/**
	 * 删除SYS_file_info信息
	 * @param bean
	 */
	public void delSysFileInfo(BudgetImportBean bean);
	/**
	 * 删除错误信息
	 * @param bean
	 */
	public void delErrorInfo(BudgetImportBean bean);
	/**
	 * 得到错误信息明细
	 * @param bean
	 * @return
	 */
	public List<BudgetImportBean> getErrorList(BudgetImportBean bean);
	/**
	 * 导出数据
	 * @param batchNo
	 * @return
	 */
	public List<BudgetImportBean> getImportDetailForExcel(String batchNo);
	/**
	 * 导入模板下载
	 * @param bean 
	 * @return
	 */
	public List<BudgetImportBean> getDownloadTemp(BudgetImportBean bean);
	/**
	 * 导出只有透支的预算
	 * @param bean
	 * @return
	 */
	public List<BudgetImportBean> getDownloadTempOnlyOver(BudgetImportBean bean);
	/**
	 * 提交的存储过程
	 * @param batchNo
	 */
	public void callSubmitProduce(@Param("batchNo")String batchNo);
	
	public BudgetImportBean getPath(@Param("batchNo")String batchNo);
	public int isHaveLsOrZs(BudgetImportBean bean);
	public int isMontDone(BudgetImportBean bean);
	public String isMore(BudgetManageBean bmBean);
	public String isMore1(BudgetManageBean bmBean);
	public int adjustAdd(BudgetManageBean bean);
	public int adjustReduce(BudgetManageBean bean);
	public int insertLog(BudgetManageBean bean);
	public String getBgtUsed(BudgetManageBean bean);
	public int adjust(BudgetManageBean bean);
	public int isUsed(BudgetManageBean bmBean);
	public int updateStatusAndIpAddress(@Param("batchNo")String batchNo, @Param("preStatus")String preStatus, @Param("nextStatus")String nexStatus,@Param("ipAddress")String ipAddress);

}
