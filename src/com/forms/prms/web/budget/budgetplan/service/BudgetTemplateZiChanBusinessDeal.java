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
import com.forms.prms.web.budget.budgetplan.domain.ZiChanBudgetDetailBean;

/**
 * 预算模板(资产类)Excel导入逻辑处理类
 * @author 	HQQ
 * @version 2015-02-03	11:36	
 */
public class BudgetTemplateZiChanBusinessDeal implements IBusinessDeal{
	
	//获取预算处理Service
	BudgetPlanService budgetService = BudgetPlanService.getInstance();

	/**
	 * 导入Excel前业务逻辑操作
	 * 		参数：流水号、Excel中Sheet的configId 、调用导入Excel组件时的携带参数
	 */
	@Override
	public void start(String batchNo,String sheetConfigId, Map beans) throws Exception {
		CommonLogger.debug("HQQQ_____IN BudgetBusinessDeal START"+"【流水号："+batchNo+"】");
		//1.将预算模板的状态更新为【01:处理中】
		String tmpltId = (String) beans.get("tmpltId");
		budgetService.updateBudgetStatus(tmpltId, "01");
		//2.将bean放至线程(组件规定)，用于保存Excel导入数据
		ExcelBean<ZiChanBudgetDetailBean> bean = new ExcelBean<ZiChanBudgetDetailBean>();
		beans.put("bean", bean);
	}

	/**
	 * 导入Excel后业务逻辑操作
	 */
	@Override
	public void end(String batchNo,String sheetConfigId, Map beans, ExcelImportInfo importInfo) throws Exception {
		CommonLogger.debug("HQQQ_____IN BudgetBusinessDeal END"+"【流水号："+batchNo+"】");
		
		//1.获取导入Excel的具体信息(bean)
		ExcelBean<ZiChanBudgetDetailBean> bean = (ExcelBean<ZiChanBudgetDetailBean>)beans.get("bean");
		String tmpltId = (String) beans.get("tmpltId");

		//2.遍历具体的Excel内部Bean信息,并写到数据库
		List<ZiChanBudgetDetailBean> excelInfoList = bean.getTemplateInfo();
		
		//Excel导入的头部校验，顺序不对不让导入
		Map<String , String> headValiMap = this.excelHeaderValidate(excelInfoList.get(0));
		String headValiResult = headValiMap.get("valiResult"); 
		if("F".equals(headValiResult)){
			//由于头部校验出错，故不再进行Excel导入操作
			
		}else{
			//Excel的title信息
			String importExcelTitle = bean.getTitle();
			
			ZiChanBudgetDetailBean beanInfo = null ;
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
				
				//处理Int类型-参考单价的写入数据库
				String referPriceInfo1 = "";
				String referPriceInfo2 = "";
				if(i==0){
					referPriceInfo1 = ((null == beanInfo.getReferPrice())? " " : beanInfo.getReferPrice())+"|";
				}else{
					boolean isNum = judgeStrToNum(beanInfo.getReferPrice());
					if(isNum){
						referPriceInfo2 = ((0 == Integer.parseInt(beanInfo.getReferPrice()))? 0 : beanInfo.getReferPrice())+"|";
					}else{
						referPriceInfo2 = ((null == beanInfo.getReferPrice())? " " : beanInfo.getReferPrice())+"|";
					}
				}
				rowInfo = 	beanInfo.getMontCode()+"|"+
							((null == beanInfo.getPropertyType())? " " : beanInfo.getPropertyType())+"|"+
							((null == beanInfo.getAcCode())? " " : beanInfo.getAcCode())+"|"+
							((null == beanInfo.getColumnOne())? " " : beanInfo.getColumnOne())+"|"+
							((null == beanInfo.getColumnTwo())? " " : beanInfo.getColumnTwo())+"|"+
							((null == beanInfo.getMatrCode())? " " : beanInfo.getMatrCode())+"|"+
							((null == beanInfo.getMatrName())? " " : beanInfo.getMatrName())+"|"+
							(i==0 ? referPriceInfo1 : referPriceInfo2)+
							((null == beanInfo.getReferType())? " " : beanInfo.getReferType());
				if(i==0){
					rowInfo += 	"|"+
								((null == beanInfo.getScrapNum())? " " : beanInfo.getScrapNum())+"|"+			//如果接收到Excel导入的值为空则拼接""
								((null == beanInfo.getDemandNum())? " " : beanInfo.getDemandNum())+"|"+
								((null == beanInfo.getBudgetAmount())? " " : beanInfo.getBudgetAmount())+"|"+
								((null == beanInfo.getMemoDescr())? " " : beanInfo.getMemoDescr());
				}
							
				budgetTempDetail.setRowInfo(rowInfo);
				budgetTempDetail.setMatrCode(beanInfo.getMatrCode());
				
				System.out.println("【HQQQ___测试插入Excel数据信息】："+rowInfo);
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
	 * 		校验顺序："预算监控指标","资产大类","核算码","物料编码","物料名称","参考单价","参考型号"
	 */
	public Map<String,String> excelHeaderValidate(ZiChanBudgetDetailBean ziChanBean){
		//validInfoMap用于保存验证结果以及验证错误信息
		Map<String , String> validInfoMap = new HashMap<String , String>();
		String valiFlag = "T";
		int valiFailIndex = 0;
		String[] headerValiInfo = new String[]{"预算监控指标","资产大类","核算码","物料编码","物料名称","参考单价","参考型号","报废数量","需求数量","预算金额","备注说明"};
		String[] excelHeaderInfo = new String[]{ziChanBean.getMontCode(),ziChanBean.getPropertyType(),ziChanBean.getAcCode(),
												ziChanBean.getMatrCode(),ziChanBean.getMatrName(),ziChanBean.getReferPrice(),
												ziChanBean.getReferType(),ziChanBean.getScrapNum(),ziChanBean.getDemandNum(),
												ziChanBean.getBudgetAmount(),ziChanBean.getMemoDescr()};
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
	
	/**
	 * 判断字符串是否能转换为数字类型
	 * @param str
	 * @return
	 */
	public static boolean judgeStrToNum(String str){
		try {
			Integer.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
