package com.forms.prms.web.budget.budgetInspect.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.util.Tool;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
import com.forms.prms.web.budget.budgetInspect.domain.SumCnt;
import com.forms.prms.web.contract.query.service.ContractQueryService;

public class BgtInspectTotalExportExcel implements IExportDataDeal {

	BudgetInspectService biService = BudgetInspectService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();		//用于更新导出任务结果状态和导出备注
	
	@Override
	public void getSimpleExcelData(String taskId, Map params, SimplifyBatchExcelExporter excelExporter)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> beansMap = new HashMap<String, Object>();
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
		String exportType = (String)params.get("exportType");
		
		int queryCount = 0;
		List<BudgetManageBean> queryTotalDataList = null;
		List<BudgetManageBean> queryReleaseDtlDataList = null;
		List<SumCnt> queryUseDtlDataList = null;
		
		if("1".equals(exportType)){
			BudgetManageBean bmBean = new BudgetManageBean();
			bmBean.setBgtId((String)params.get("bgtId"));
			bmBean.setBgtYear((String)params.get("bgtYear"));
			bmBean.setOverDrawType((String)params.get("overDrawType"));
			bmBean.setBgtOrgcode((String)params.get("bgtOrgcode"));
			bmBean.setBgtMatrcode((String)params.get("matrCode"));
			bmBean.setMontCode((String)params.get("montCode"));
			
			bmBean.setMontType((String)params.get("montType"));
			bmBean.setMontName((String)params.get("montName"));
			bmBean.setOrg1Code((String)params.get("org1Code"));
			bmBean.setOrg2Code((String)params.get("org2Code"));
			
			bmBean.setOrgType((String)params.get("orgType"));
			
			bmBean.setExportType(exportType);
			if (Tool.CHECK.isEmpty(bmBean.getOverDrawType())) {
				bmBean.setOverDrawType("0");//根据bean中的OverDrawType属性是否存在来设置器初始值为0
			}
			queryTotalDataList = biService.exportDataExcute(bmBean);
			queryCount = queryTotalDataList.size();
		}else if("2".equals(exportType)){
			//查询下达明细
			BudgetManageBean bmBean = new BudgetManageBean();
			bmBean.setBgtId((String)params.get("bgtId"));
			bmBean.setExportType(exportType);
			queryTotalDataList = biService.exportDataExcute(bmBean);
			queryCount = queryTotalDataList.size();
		}
		if(queryCount < 1){
			exportSucFlag = false;
			exportMemo = "模板Detail表中查询不到数据，请检查数据";
		}else{
			beansMap.put("0", queryTotalDataList);
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

	

	@Override
	public void start(String taskId, Map params) throws Exception {
		boolean exportSucFlag = false;				//Excel导出操作是否成功标识
		params.put("exportSucFlag", exportSucFlag);
	}

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
		excelDealService.updateExcelResult(bean);
	}
	
	@Override
	public void getJxlExcelData(String taskId, Map params, JxlsExcelExporter jxlExporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
