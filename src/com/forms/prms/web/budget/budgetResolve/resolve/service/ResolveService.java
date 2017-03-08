package com.forms.prms.web.budget.budgetResolve.resolve.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.budget.budgetResolve.resolve.dao.ResolveDao;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetReplyedBean;
import com.forms.prms.web.budget.budgetResolve.resolve.domain.BudgetResolveBean;
import com.forms.prms.web.budget.budgetplan.dao.BudgetPlanDAO;
import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;

@Service
public class ResolveService {

	@Autowired
	private ResolveDao dao;
	@Autowired
	public BudgetPlanDAO budgetDAO;
	
	/**
	 * 预算分解-可分解列表页面：查询已经预算批复的可分解预算列表(getResolveList.jsp)，
	 * 		通过分解部门做查询条件限制(以表TB_Mont_Name中DECOMPOSE_ORG列=dutyCode)
	 * @param budgetReplyed
	 * @return
	 */
	public List<BudgetReplyedBean> getBudgetReplyedList(BudgetReplyedBean budgetReplyed){
		if(null == WebHelp.getLoginUser().getDutyCode() || "".equals(WebHelp.getLoginUser().getDutyCode())){
			return null;
		}
		budgetReplyed.setDutyCode(WebHelp.getLoginUser().getDutyCode());
		ResolveDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getBudgetReplyedList(budgetReplyed);
	}
	
	/**
	 * 根据指定的预算模板ID和监控指标ID查找预算批复记录
	 * 		1.查找预算批复记录
	 * 		2.【待验证是否有用】根据TMPLT的模板类型，设置其Org21Code属性，并返回
	 * @param budgetReplyed
	 * @return
	 */
	public BudgetReplyedBean getBudReplyByTmpAndMont(BudgetReplyedBean budgetReplyed){
		//1.
		budgetReplyed = dao.getBudReplyByTmpAndMont(budgetReplyed.getTmpltId() , budgetReplyed.getMontCode());
		//2.
		String org21Code = "";
		if("0".equals(budgetDAO.getBudgetById(budgetReplyed.getTmpltId()).getDataAttr())){
			org21Code = WebHelp.getLoginUser().getOrg1Code();
		}else if("1".equals(budgetDAO.getBudgetById(budgetReplyed.getTmpltId()).getDataAttr())){
			org21Code = WebHelp.getLoginUser().getOrg2Code();
		}
		budgetReplyed.setOrg21Code(org21Code);
		return budgetReplyed;
	}
	
	/**
	 * 根据监控指标查找所属的物料列表，供预算分解时选择
	 * @param montCode
	 * @return
	 */
	public List<MatrType> getMatrListByMont(String montCode){
		ResolveDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getMatrListByMont(montCode);
	}
	
	/**
	 * 新增预算分解操作
	 * 		1.从页面上获取分解操作需要的属性(本来有的：tmpltId、montCode；用户填的：dutyCode、matrCode、sumAmt)，
	 * 				暂时将不允许空的usedAmt、freezeAmt、surplusAmt值设为0，执行数据库操作新增预算分解记录
	 *		2.更新Reply批复表中已分解总金额SplitedAmt
	 *				获取对应的预算批复记录getBudReplyByTmpAndMont
	 *				给该批复记录重新赋值setSplitedAmt(最新已分解金额=已分解金额+新增分解的金额)
	 *				更新该预算批复Bean记录updateSplitedAmtInReplyTb
	 * @param resolve
	 * @return
	 */
	public int addBudgetResolve( BudgetResolveBean resolve ){
		CommonLogger.info("预算分解新增操作,(主键信息为:[模板ID]"+resolve.getTmpltId()+",[监控指标]"+resolve.getMontCode()+",[分解部门]"+resolve.getDutyCode()+",[物料信息]"+resolve.getMatrCode()+"),[分解金额]"+resolve.getUsedAmt()+"),ResolveService,addBudgetResolve");
		//1.
		resolve.setUsedAmt("0");
		resolve.setFreezeAmt("0");
		resolve.setSurplusAmt("0");
		int addResult = dao.addBudgetResolve(resolve);
		
		//2.
		BudgetReplyedBean replyedBean = dao.getBudReplyByTmpAndMont(resolve.getTmpltId(), resolve.getMontCode());
		replyedBean.setSplitedAmt(new BigDecimal(String.valueOf(Double.parseDouble(replyedBean.getSplitedAmt().toString())+Double.parseDouble(resolve.getSumAmt()))));
		dao.updateSplitedAmtInReplyTb(replyedBean);
		return addResult;
	}
	
	/**
	 * 获取指定监控指标下的所属预算分解详情
	 * 		所需查询条件参数：tmpltId、montCode
	 * @param budgetReplyed
	 * @return
	 */
	public List<BudgetResolveBean> getResolveList( BudgetReplyedBean budgetReplyed ){
		//1.预算批复Bean和预算分解Bean的模板Id和监控指标编码一致，交换赋值
		BudgetResolveBean resolve = new BudgetResolveBean();
		resolve.setTmpltId(budgetReplyed.getTmpltId());
		resolve.setMontCode(budgetReplyed.getMontCode());
		//2.分页查询分解详情列表
		ResolveDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getResolveDetailList(resolve);
	}
	
	/**
	 * 删除预算分解操作
	 * 		1.删除预算分解表TD_BUDGET_SPLIT_DETAIL中对应的数据
	 * 		2.更新Reply批复表中已分解总金额SplitedAmt
	 * 			获取上述删除数据的删除金额sumAmt
	 * 			查找getBudReplyByTmpAndMont预算分解Bean对应的预算批复记录
	 * 			其已分解金额属性splitedAmt  减去  删除金额cancelSplitAmt，重新赋值并更新updateSplitedAmtInReplyTb该预算批复Bean
	 * @param resolve	预算分解Bean
	 * @return
	 */
	public int deleteBudgetResolve( BudgetResolveBean resolve ){
		CommonLogger.info("预算分解删除操作,(主键信息为:[模板ID]"+resolve.getTmpltId()+",[监控指标]"+resolve.getMontCode()+",[分解部门]"+resolve.getDutyCode()+",[物料信息]"+resolve.getMatrCode()+"),ResolveService,deleteBudgetResolve");
		//1.
		int delResult = dao.deleteBudgetResolve(resolve);
		//2.
		double cancelSplitAmt = Double.parseDouble(resolve.getSumAmt());
		BudgetReplyedBean replyedBean = dao.getBudReplyByTmpAndMont(resolve.getTmpltId(), resolve.getMontCode());
		replyedBean.setSplitedAmt(new BigDecimal(String.valueOf(Double.parseDouble(replyedBean.getSplitedAmt().toString())- cancelSplitAmt)));
		dao.updateSplitedAmtInReplyTb(replyedBean);
		return delResult;
	}
	
//	public Map<String , Object> testService(){
//		Map<String,Object> mapObj = new HashMap<String,Object>();
//		BudgetPlanBean budget = new BudgetPlanBean();
//		budget.setTmpltId("20150302000295");
//		budget.setTmpltName("MobanMingCheng");
//		mapObj.put("budget", budget);
//		mapObj.put("updateValue", "updateFileName-03260908");
//		mapObj.put("condition1", "true");
//		int result = dao.testUpdateSettingParams(mapObj);
//		System.out.println("【End:TestService......】");
//		
//		Map<String,Object> returnMapObj = new HashMap<String,Object>();
//		returnMapObj.put("result", result);
//		returnMapObj.put("budget", budget);
//		return returnMapObj;
//	}
}
