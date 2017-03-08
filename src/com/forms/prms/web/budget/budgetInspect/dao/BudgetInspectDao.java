package com.forms.prms.web.budget.budgetInspect.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
import com.forms.prms.web.budget.budgetInspect.domain.MatrBean;
import com.forms.prms.web.budget.budgetInspect.domain.SumCnt;
import com.forms.prms.web.budget.budgetInspect.domain.SumCntDetail;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;

@Repository
public interface BudgetInspectDao {
	
	/**
	 * 得到合同预算的列表
	 * @param sumCnt
	 * @return
	 */
	List<SumCnt> getSumCntInfo(Map<String, Object> paramMap);
	
	/**
	 * 获取合同详细信息
	 * @param cntNum
	 * @return
	 */
	List<SumCntDetail> getCntDetail(String scId);
	
	/**
	 * 根据相应条件查询预算,获取相应预算信息
	 * @param bmBean
	 * @return
	 */
	public List<BudgetManageBean> queryBudgetmanageBeans(BudgetManageBean bmBean); 
	
	/**
	 * 根据bgtId查询预算下达明细
	 * @param bgtId
	 * @return
	 */
	public List<BudgetManageBean> view(String bgtId);
	
	/**
	 * 根据唯一sdId查询预算下达明细
	 * @param bgtId
	 * @return
	 */
	public BudgetManageBean sumDetail(String sdId); 
	
	/**
	 * 根据用户所在行或者指标类型获取监控指标名称
	 * @param bmBaBean
	 * @return
	 */
	public  List<BudgetManageBean> getMontName(BudgetManageBean bmBaBean);
	
	/**
	 *根据物料编码或者物料名称获取物料信息
	 *@param mBean
	 *@return 
	 */
	public List<MatrBean> getMatrName(MatrBean mBean);

	BudgetManageBean cntInspectDetail(String scdId);

	List<MontIndexBean> selectRole(MontIndexBean montIndexBean);

	List<BudgetManageBean> exportByBgtId(BudgetManageBean bmBean);

	List<BudgetManageBean> bugetAdjustLog(BudgetManageBean bean);

	BudgetManageBean getBugetInfo(BudgetManageBean bmBean);


	List<BudgetManageBean> getMontNameList(BudgetManageBean bean);

	BudgetManageBean getBean(String bgtId);

	int adjustValidBgt(BudgetManageBean bean);

	int delBgt(String bgtId);
}
