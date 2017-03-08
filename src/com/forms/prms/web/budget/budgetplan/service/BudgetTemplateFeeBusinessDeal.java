package com.forms.prms.web.budget.budgetplan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.platform.excel.imports.inter.IBusinessDeal;
import com.forms.prms.web.budget.budgetplan.domain.BudgetPlanBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTempDetailBean;
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;
import com.forms.prms.web.budget.budgetplan.domain.FeeBudgetDetailBean;

/**
 * 预算模板(费用类)Excel导入逻辑处理类
 * @author 	HQQ
 * @version 2015-02-05	19:36	
 */
public class BudgetTemplateFeeBusinessDeal implements IBusinessDeal{

	//获取预算处理Service
	BudgetPlanService budgetService = BudgetPlanService.getInstance();
	
	@Override
	public void start(String batchNo, String arg1, Map beans) throws Exception {
		//beans.put("tmpltId", batchNo);
		CommonLogger.debug("HQQQ__:Into Class【BudgetTemplateFeeBusinessDeal】——[START]"+"【流水号："+batchNo+"】");
		
		//1.将预算模板的状态更新为【01:处理中】
		String tmpltId = (String) beans.get("tmpltId");
		budgetService.updateBudgetStatus(tmpltId, "01");
		//2.将bean放至线程，用于保存Excel导入数据
		ExcelBean<FeeBudgetDetailBean> bean = new ExcelBean<FeeBudgetDetailBean>();
		beans.put("bean", bean);
	}
	
	@Override
	public void end(String batchNo, String arg1, Map beans, ExcelImportInfo importInfo) throws Exception {
		CommonLogger.debug("HQQQ__:Into Class【BudgetTemplateFeeBusinessDeal】——[END]"+"【流水号："+batchNo+"】");
		
		//1.获取导入Excel的具体信息(bean)
		ExcelBean<FeeBudgetDetailBean> bean = (ExcelBean<FeeBudgetDetailBean>)beans.get("bean");
		String tmpltId = (String) beans.get("tmpltId");

		//2.遍历具体的Excel内部Bean信息,并写到数据库
		List<FeeBudgetDetailBean> excelInfoList = bean.getTemplateInfo();
		
		Map<String , String> headValiMap = this.excelHeaderValidate(excelInfoList.get(0));
		String headValiResult = headValiMap.get("valiResult"); 
		if("F".equals(headValiResult)){
			//由于头部校验出错，故不再进行Excel导入操作
			
		}else{
			
			//Excel的title信息
			String importExcelTitle = bean.getTitle();
			
			FeeBudgetDetailBean beanInfo = null ;
			BudgetTempDetailBean budgetTempDetail ;
			//行号
			String rowNo ;
			
			//详情信息(从row=1开始)
			for(int i=0;i<excelInfoList.size();i++){
				budgetTempDetail = new BudgetTempDetailBean();
				rowNo = String.valueOf(i);
				beanInfo = excelInfoList.get(i);
				budgetTempDetail.setTmpltId(tmpltId);
				budgetTempDetail.setRowSeq(rowNo);
				String rowInfo ;
				rowInfo = 	beanInfo.getMontCode()+"|"+
							((null == beanInfo.getJyZm())? " " : beanInfo.getJyZm())+"|"+
							((null == beanInfo.getAcCode())? " " : beanInfo.getAcCode())+"|"+
							((null == beanInfo.getColumnOne())? " " : beanInfo.getColumnOne())+"|"+
							((null == beanInfo.getColumnTwo())? " " : beanInfo.getColumnTwo())+"|"+
							beanInfo.getMatrCode()+"|"+
							beanInfo.getMatrName();
				if(i==0){
					rowInfo += "|"+
							((null == beanInfo.getBudgetAmount())? " " : beanInfo.getBudgetAmount())+"|"+
							((null == beanInfo.getMemoDesr())? " " : beanInfo.getMemoDesr());
				}
				budgetTempDetail.setRowInfo(rowInfo);
				budgetTempDetail.setMatrCode(beanInfo.getMatrCode());
				
				System.out.println("HQQQ___测试插入Excel数据信息："+rowInfo);
				if(importInfo.isHasError()){
					//每一条数据都有可能有Error
					beanInfo.setErrorMemo("导入信息有异常，详情如下：....");
				}
				budgetService.insertBudgetTempDetail(budgetTempDetail);
				CommonLogger.debug("[测试数据]WDDD_Bean信息："+beanInfo.getColumnOne()+"【流水号："+batchNo+"】");
			}
		}
		
		
		//3.根据导入数据是否有异常进行处理
//		List<CheckedSection> checkErrorList = importInfo.getErrorList();
//		long countNum = importInfo.getImportCount();
		BudgetPlanBean budget = budgetService.view(tmpltId);
		if(importInfo.isHasError() || "F".equals(headValiResult)){
			//【未完成】---Error时的相关处理
			if("F".equals(headValiResult)){
				budget.setMemo(headValiMap.get("valiFailIndex"));
			}else{
				budget.setMemo("Excel导入时发生其他错误");
			}
			budgetService.updateTaskLoadStatus(batchNo, "02");
			budgetService.updateBudgetStatus(tmpltId, "02");
		}else{
			//如果导入Excel操作成功，则更改Task表的状态为03完成
			budget.setMemo("导入成功无误！");
			budgetService.updateTaskLoadStatus(batchNo, "03");
			budgetService.updateBudgetStatus(tmpltId, "03");
		}
		budgetService.updateBudgetPlan(budget);
		CommonLogger.debug("HQQQ____BusinessDeal的END执行完毕，"+"【流水号："+batchNo+"】");
	}
	
	/**
	 * 导入Excel头部的验证(头部验证不通过则不进行导入操作)
	 * 【注意】：只对Excel的头部做校验
	 * 		校验顺序："预算监控指标","捐益子目","核算码","物料编码","物料名称","预算金额","备注说明"
	 */
	public Map<String,String> excelHeaderValidate(FeeBudgetDetailBean feeBean){
		//validInfoMap用于保存验证结果以及验证错误信息
		Map<String , String> validInfoMap = new HashMap<String , String>();
		String valiFlag = "T";
		int valiFailIndex = 0;
		String[] headerValiInfo = new String[]{"预算监控指标","捐益子目","核算码","物料编码","物料名称","预算金额","备注说明"};
		String[] excelHeaderInfo = new String[]{feeBean.getMontCode(),feeBean.getJyZm(),feeBean.getAcCode(),
												feeBean.getMatrCode(),feeBean.getMatrName(),feeBean.getBudgetAmount(),feeBean.getMemoDesr()};
		for(int i=0;i<headerValiInfo.length;i++){
			if("T".equals(valiFlag)){
				if(!headerValiInfo[i].equals(excelHeaderInfo[i])){
					//验证为：不匹配！
					valiFlag = "F";
					valiFailIndex = i;
				}
			}
		}
		validInfoMap.put("valiResult" , valiFlag);
		if("F".equals(valiFlag)){
			validInfoMap.put("valiFailIndex", "导入Excel头部验证失败，第"+String.valueOf(valiFailIndex)+"列["+excelHeaderInfo[valiFailIndex]+"]不匹配！");
		}
		return validInfoMap;
	}
}
