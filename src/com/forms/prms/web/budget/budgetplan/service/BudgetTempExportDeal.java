package com.forms.prms.web.budget.budgetplan.service;

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

public class BudgetTempExportDeal implements IExportDataDeal{
	
	BudgetPlanService service = BudgetPlanService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();		//用于更新导出任务的状态和相关备注
	
	/**
	 * Excel导入前逻辑处理(添加导出结果标识)
	 */
	public void start(String taskId, Map params) {
		boolean exportSucFlag = false;				//Excel导出操作是否成功标识
		params.put("exportSucFlag", exportSucFlag);
	}

	public void getSimpleExcelData(String taskId, Map params,SimplifyBatchExcelExporter excelExporter) {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
		
		Map<String,Object> beansMap = new HashMap<String, Object>();
		
		//获取方法调用的接收参数
		String tmpltId = (String) params.get("tmpltId");
		String dutyCode = (String) params.get("dutyCode");
		String dataAttr = (String) params.get("dataAttr");
		//处理导出Excel头部信息更改
		String col1 = "";
		String col2 = "";
		Map<String,String> headStr = new HashMap<String,String>();
		
		List<BudgetTempDetailBean> excelExportInfoList = service.getBudgetTmpExcelExpInfo(dutyCode , tmpltId);
		
		if(excelExportInfoList.size()<1){
			//TB_BUDGET_TMPLT_DETAIL表中查询不到相关数据(有可能表信息已被删除)
			exportSucFlag = false;
			exportMemo = "模板Detail表中查询不到数据，请检查数据";
		}else{
			//处理Excel表头信息(取得Col1和Col2的值并替换)
			BudgetTempDetailBean excelHeader = excelExportInfoList.get(0);
			if("0".equals(dataAttr)){
				//资产处理
				col1 = excelHeader.getRowInfo().split("\\|")[3];	//Excel表格位置D2
				col2 = excelHeader.getRowInfo().split("\\|")[4];	//Excel表格位置E2
				headStr.put("D2",col1);
				headStr.put("E2",col2);
				//处理Excel中核心List数据
				for(int i=1;i<excelExportInfoList.size();i++){  //从1开始取值，忽略表头(0)数据
					service.rowInfoConvertToZC(excelExportInfoList.get(i));
				}
			}else if("1".equals(dataAttr)){
				//费用处理
				col1 = excelHeader.getRowInfo().split("\\|")[3];	//Excel表格位置D2
				col2 = excelHeader.getRowInfo().split("\\|")[4];	//Excel表格位置E2
				headStr.put("D2",col1);
				headStr.put("E2",col2);
				//处理Excel中核心List数据
				for(int i=1;i<excelExportInfoList.size();i++){  //从1开始取值，忽略表头(0)数据
					service.rowInfoConvertToFee(excelExportInfoList.get(i));
				}
			}
			beansMap.put("0_head", headStr);
			//去掉表头数据，把核心数据进行Excel导出信息传输
			excelExportInfoList.remove(0);
			beansMap.put("0", excelExportInfoList);
		}
		
		try {
			//可按查找数据量进行分段加载
			ExcelExportUtility.loadExcelData(beansMap,excelExporter);
			exportSucFlag = true;
			exportMemo = "处理成功，可下载";
		} catch (Exception e) {
			exportMemo = "【处理失败】，详情："+e.getMessage().substring(300)+"...";
			e.printStackTrace();
		} finally{
			params.put("exportSucFlag", exportSucFlag);
			params.put("exportMemo", exportMemo);
		}
	}

	/**
	 * Excel导出后续处理(根据导出操作是否成功，更新状态、导出备注、处理时间(SQL))
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
		//更新操作
		excelDealService.updateExcelResult(bean);
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

	@Override
	public void getJxlExcelData(String taskId, Map params, JxlsExcelExporter jxlExporter) {
		// TODO Auto-generated method stub
		
	}
	
}
