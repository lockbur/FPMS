package com.forms.prms.web.amortization.abnormalDataMgr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidAccountBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApInvoiceBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidApPayBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidCglAmtBean;
import com.forms.prms.web.amortization.abnormalDataMgr.domain.TidOrderBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;

/**
 * Title:		AbnormalDataExportDeal
 * Description:	异常数据查询模块的查询数据Excel导出逻辑处理类(处理Excel导出前后逻辑、更新相关导出任务表状态、查询导出数据等)
 * Copyright: 	formssi
 * @author： 	HQQ
 * @project： 	ERP
 * @date: 		2015-04-09
 * @version: 	1.0
 */
public class AbnormalDataExportDeal implements IExportDataDeal {
	
	//用于更新导出任务的状态和相关备注
	ContractQueryService excelDealService = ContractQueryService.getInstance();
	//用于处理异常数据的相关查询操作
	AbnorDataMgrService queryDataService = AbnorDataMgrService.getInstance();

	//Excel导出任务导出前逻辑处理
	@Override
	public void start(String taskId, Map params) throws Exception {
		//Excel导出操作是否成功标识(初始值设置为false，并put到传递参数Map中)
		boolean exportSucFlag = false;
		params.put("exportSucFlag", exportSucFlag);
	}
	
	/*
	 * 【主要】Excel导出任务导出主要逻辑处理
	 * 		参	    数：导出任务ID、导出传递参数map、Excel导出工具类
	 * 		处理步骤：	1.导出备注、导出结果标识
	 * 					2.根据查询条件进行SQL的查询，取得beanList
	 * 					3.调用Excel导出工具类进行查询数据的Excel导出
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void getSimpleExcelData(String taskId, Map params, SimplifyBatchExcelExporter excelExporter) throws Exception {
		//导出任务备注
		String exportMemo = "";
		//导出任务结果标识
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		
		//必须，该Map用于存放SQL查找的DataList，作用于ExcelUtil的导出工具内部代码
		Map<String,Object> beansMap = new HashMap<String, Object>();	
		//导出Excel表头更改表头名称处理
		Map<String,String> headStr = new HashMap<String,String>();
		String tableHeadDesc = "";	
		String exportQueryState = "";
		if("1".equals((String)params.get("queryState"))){
			exportQueryState = "正常";
		}else if("0".equals((String)params.get("queryState"))){
			exportQueryState = "异常";
		}else{
			exportQueryState = "全部";
		}
		
		//此段用于存放SQL查询的条件(将queryMapObj对象放至Map中)
		String queryType = (String)params.get("queryType");
//		Map<String,Object> mapObj = new HashMap<String,Object>();		
		TidAccountBean queryMapObj = new TidAccountBean();
		queryMapObj.setUseFlag((String)params.get("queryState"));
//		mapObj.put("queryMapObj", queryMapObj);
//		params.put("queryMapObj", queryMapObj);
		params.put("queryMapObj", params.get("commonQueryBean"));
		
//		System.out.println("【HQQ-0504】:"+((CommonTidQueryBean)(params.get("commonQueryBean"))).getCntNum());
//		((CommonTidQueryBean)(params.get("commonQueryBean"))).getCntNum();
		
		
		//根据查询条件进行导出数据的查询(决定查询的表、查询的异常状态)，并put到beansmap中，"0"代表Excel文件的第一个sheet表
		if("1".equals(queryType)){
			List<TidAccountBean> beanDataList = queryDataService.queryTidAccountAllData(params);
			tableHeadDesc = "总账凭证接口["+exportQueryState+"]状态导出信息:";
			beansMap.put("0", beanDataList);
		}else if("2".equals(queryType)){
			List<TidApInvoiceBean> beanDataList = queryDataService.queryTidApInvoiceAllData(params);
			tableHeadDesc = "AP发票接口["+exportQueryState+"]状态导出信息:";
			beansMap.put("0", beanDataList);
		}else if("3".equals(queryType)){
			List<TidApPayBean> beanDataList = queryDataService.queryTidApPayAllData(params);
			tableHeadDesc = "AP付款接口["+exportQueryState+"]状态导出信息:";
			beansMap.put("0", beanDataList);
		}else if("4".equals(queryType)){
			List<TidCglAmtBean> beanDataList = queryDataService.queryTidCglAmtAllData(params);
			tableHeadDesc = "科目余额["+exportQueryState+"]状态导出信息:";
			beansMap.put("0", beanDataList);
		}else if("5".equals(queryType)){
			List<TidOrderBean> beanDataList = queryDataService.queryTidOrderAllData(params);
			tableHeadDesc = "订单接口["+exportQueryState+"]状态导出信息:";
			beansMap.put("0", beanDataList);
		}
		headStr.put("A1", tableHeadDesc);
		beansMap.put("0_head", headStr);
		
		//调用Excel导出工具类进行查询数据——>Excel表格的数据装载
		try {
			//当SQL进行数据查询的查询结果为0条时，则无需进行Excel的导出，直接返回false
			if(((List<Object>)beansMap.get("0")).size() == 0){
				CommonLogger.debug("Testing:【Excel导出是没有查询到数据.....】");
				exportMemo = "导出任务中查询不到指定条件的数据信息，请检查查询条件后进行Excel导出。";
			}else{
				ExcelExportUtility.loadExcelData(beansMap,excelExporter);
				exportSucFlag = true;
				exportMemo = "处理成功，可下载";
			}
		} catch (Exception e) {
			//导出过程出现异常，在导出任务备注中记录异常信息
			exportMemo = "处理失败，详情："+e.getMessage().substring(300)+"...";
			e.printStackTrace();
		} finally{
			params.put("exportSucFlag", exportSucFlag);
			params.put("exportMemo", exportMemo);
		}
	}

	//Excel导出任务导出后逻辑处理(更新导出任务的结果标识和备注)
	@Override
	public void end(String taskId, Map params) throws Exception {
		CommonExcelDealBean bean = new CommonExcelDealBean();
		bean.setTaskId(taskId);
		if((Boolean) params.get("exportSucFlag")){
			//成功
			bean.setDataFlag("03");
		}else{
			//失败
			bean.setDataFlag("02");
		}
		bean.setProcMemo((String)params.get("exportMemo"));
		//更新操作
		excelDealService.updateExcelResult(bean);
	}
	
	//不使用
	@Override
	public void getJxlExcelData(String taskId, Map params, JxlsExcelExporter jxlExporter) throws Exception {
		// TODO Auto-generated method stub
	}

}
