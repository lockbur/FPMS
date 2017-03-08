package com.forms.prms.web.export.cnt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.domain.QueryContract;
import com.forms.prms.web.contract.query.service.ContractQueryService;

public class CntMatrExport implements IExportDataDeal{

	
	ContractQueryService service = ContractQueryService.getInstance();
	

	/**
	 * 修改方法，添加导出加载方法
	 */
	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";

		Map<String, Object> beansMap = new HashMap<String, Object>();
		// 获取方法调用的接收参数
 		String dataFlags = (String) params.get("dataFlagString");
		
		String[] arrayStrings = dataFlags.split(",");
		List<String> list = new ArrayList<String>();
		if(arrayStrings!=null){
			
			params.put("dataFlags", Arrays.asList(arrayStrings));
		}else{
			params.put("dataFlags", list);
			
		}
		// //插入excel数据内容
		try {
			List<QueryContract> projects = new ArrayList<QueryContract>();
			projects = service.szQueryContactList(params);
			beansMap.put("0", projects);
			exportSucFlag = true;
			exportMemo = "处理成功，可下载";
			//增加一条汇总数据
		} catch (Exception e) {
			exportMemo = "【处理失败】， ：" ;
			exportSucFlag = false;
			CommonLogger.error("数据导出时往excel里插入数据失败" + e.getMessage()
					+ "CntMatrExport,getSimpleExcelData");
			e.printStackTrace();
		}
		try {
			// 可按查找数据量进行分段加载
			ExcelExportUtility.loadExcelData(beansMap, excelExporter);
		} catch (Exception e) {
			exportSucFlag = false;
			exportMemo = "【处理失败】，";
			e.printStackTrace();
		} finally {
			params.put("exportSucFlag", exportSucFlag);
			params.put("exportMemo", exportMemo);
		}

	}

	@Override
	public void start(String taskId, Map params) throws Exception {
		boolean exportSucFlag = false; // Excel导出操作是否成功标识
		params.put("exportSucFlag", exportSucFlag);
	}

	@Override
	public void end(String taskId, Map params) throws Exception {
		CommonExcelDealBean bean = new CommonExcelDealBean();
		bean.setTaskId(taskId);
		if ((Boolean) params.get("exportSucFlag")) {
			// 成功
			bean.setDataFlag("03");
		} else {
			// 失败
			bean.setDataFlag("02");
		}
		bean.setProcMemo((String) params.get("exportMemo"));
		service.updateExcelResult(bean);
	}

	//一般不使用,提jxl方式处理方法
	public void getJxlExcelData(String taskId, Map params, JxlsExcelExporter jxlExporter) {
		// TODO Auto-generated method stub
		
	}	

}
