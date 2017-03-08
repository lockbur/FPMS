package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;

public class ExportExcel implements IExportDataDeal {
	
	ImportService es=ImportService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();		//用于更新导出任务结果状态和导出备注


	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
		
		Map<String,Object> beansMap = new HashMap<String, Object>();
		//获取方法调用的接收参数
		String batchNo = (String) params.get("batchNo");
		String proType = (String) params.get("proType");

		ExportBean bean=new ExportBean();
		bean.setBatchNo(batchNo);
		bean.setProType(proType);
	
			//插入excel数据内容
			if ("01".equals(proType)){//监控指标
				List<ExportBean> montContent=es.getMontContent(batchNo);				
				if (montContent.size()<1||montContent.isEmpty()) {
					exportSucFlag = false;
					exportMemo = "tbl_mont_name表数据为空，请检查数据";
					
				} else {	
					exportSucFlag=true;
					exportMemo="处理成功，可下载";
					beansMap.put("0", montContent);
				}				
			}
			if ("02".equals(proType)){//审批链
				List<ExportBean> aprvContent=es.getAprvContent(batchNo);
				if (aprvContent.size()<1||aprvContent.isEmpty()) {
					exportSucFlag = false;
					exportMemo = "tbl_aprv_chain数据为空，请检查数据";
				} else {
					exportSucFlag=true;
					exportMemo="处理成功，可下载";
					beansMap.put("0", aprvContent);
				}			
		}
		
		try {
			//可按查找数据量进行分段加载
			ExcelExportUtility.loadExcelData(beansMap,excelExporter);
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
	public void getJxlExcelData(String taskId, Map params,
			JxlsExcelExporter jxlExporter) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
