package com.forms.prms.web.budget.budgetplan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.budget.budgetplan.domain.BudgetTempDetailBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;

public class BudgetBasicTempExportDeal implements IExportDataDeal{
	
	BudgetPlanService service = BudgetPlanService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();		//用于更新导出任务结果状态和导出备注
	
	/**
	 * 前置处理逻辑
	 */
	public void start(String taskId, Map params) {
		//导出任务是否成功标识
		boolean exportSucFlag = false;
		params.put("exportSucFlag", exportSucFlag);
	}

	/**
	 * *主要方法：获取Excel导出相关数据List，并输出到中去
	 */
	public void getSimpleExcelData(String taskId, Map params, SimplifyBatchExcelExporter excelExporter) {
		String exportMemo = "";		//导出备注
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");		//导出操作标识
		
		Map<String,Object> beansMap = new HashMap<String, Object>();
		
		//导出Excel的表头处理
		Map<String,String> headStr = new HashMap<String,String>();
		beansMap.put("0_head", headStr);
		
		String dataAttr = (String) params.get("dataAttr");
		String org21Code = (String) params.get("org21Code");
		String montType = (String) params.get("montType");

		//获取导出基本数据List(监控指标+物料编号+物料名称)
		List<BudgetTempDetailBean> exportDataList = service.exportBasicBudgetInfo(org21Code , montType);
		beansMap.put("0", exportDataList);//zcBasicTempList
		
		try {
			//可按查找数据量进行分段加载
			ExcelExportUtility.loadExcelData(beansMap,excelExporter);
			exportSucFlag = true;
			exportMemo = "处理成功，可下载";
		} catch (Exception e) {
			exportMemo = "【处理失败】，详情："+e.getMessage().substring(300)+"...";
			e.printStackTrace();
		} finally{
			//放置导出操作过程记录
			params.put("exportSucFlag", exportSucFlag);
			params.put("exportMemo", exportMemo);
		}
	}

	
	/**
	 * 后续处理逻辑
	 * 		描述：设置并更新导出任务的状态和备注
	 */
	public void end(String taskId, Map params) {
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
		excelDealService.updateExcelResult(bean);
		
		
		
		//当Excel文件输出完毕后，将该文件保存到DB中，以备集群环境中文件下载找不到
//		try {
		//((DBFileOperUtil)SpringHelp.getBean(DBFileOperUtil.class)).saveFileToDB(destFileName, true);
//			String destFilePath = commonExcelDealService.getExportFileDestPathByTaskId(taskId);
//			DbFileUtil.saveFileToDB(destFilePath, true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 公共方法：批量更改Excel的头部标题
	 * 目的：根据配置文件的键值对更改Excel的标题
	 * @param headStr
	 * @param updateInfoList
	 */
	public static void addUpdateExcelHeader(Map<String,String> headStr , List<String[]> updateInfoList){
		for(int i=0;i<updateInfoList.size();i++){
			headStr.put(updateInfoList.get(i)[0], updateInfoList.get(i)[1]);
		}
	}
	
	public static void main(String[] args) {
		Map<String,String> headStr = new HashMap<String,String>();
		List<String[]> updateInfoList = new ArrayList<String[]>();
		
		String[] strArray = new String[2];
		strArray[0] = "D2";
		strArray[1] = "我的部门";
		updateInfoList.add(strArray);
		
		addUpdateExcelHeader(headStr,updateInfoList);
		System.out.println("【测试数据】："+headStr.get("D2"));
	}

	//接口规定实现方法
	@Override
	public void getJxlExcelData(String taskId, Map params, JxlsExcelExporter jxlExporter) {
		// TODO Auto-generated method stub
	}

}
