package com.forms.prms.web.contract.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.constantValues.ExcelTaskStatusValues;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.domain.QueryContract;

public class ContractExportDeal implements IExportDataDeal{
	//判断处理状态
	private boolean flag = true;
	//处理信息
	private String dealMsg = "";
	
	ContractQueryService service = ContractQueryService.getInstance();
	
	public void start(String taskId, Map params) throws Exception{
		//导入前处理，校验各项参数
		if(taskId == null || taskId.trim().length() == 0){
			flag = false;
			dealMsg = "该导出流水不存在";
		}
	}

	/**
	 * 修改方法，添加导出加载方法
	 */
	public void getSimpleExcelData(String taskId, Map params, SimplifyBatchExcelExporter excelExporter)throws Exception{		
		//导出前处理为失败，后续将不再导入
		if(!flag){
			return;
		}

		Map<String,Object> beansMap = new HashMap<String, Object>();
		List<QueryContract> cntList = null;

		String retCount = service.queryDownloadCount(params);
		long count =  Long.parseLong(retCount!=null?retCount:"0");
		if(count == 0){
			flag = false;
			dealMsg = "导出失败，未查找到对应的导出数据";
			return;
		}

		try {
			//可按查找数据量进行分段加载,设定每段加载数为1000条记录
			for(int i=0; i<=count/1000;i++){
				params.put("start", String.valueOf(i*1000));
				params.put("end", String.valueOf(i==count/1000?count:((i+1)*1000)));
				cntList = service.queryContactList(params);
				beansMap.put("0", cntList);
				ExcelExportUtility.loadExcelData(beansMap,excelExporter);
			}
		} catch (Exception e) {
			flag = false;
			dealMsg = "导出失败，请查看相关日志";
			e.printStackTrace();
		}
	}

	public void end(String taskId, Map params) throws Exception{
		CommonExcelDealBean bean = new CommonExcelDealBean();
		bean.setTaskId(taskId);
		if(flag){			
			bean.setDataFlag(ExcelTaskStatusValues.DEALCOMP.toString());
			bean.setProcMemo("处理完成，可下载");
		}else{
			bean.setDataFlag(ExcelTaskStatusValues.DEALFAIL.toString());
			bean.setProcMemo(dealMsg);			
		}
		
		service.updateExcelResult(bean);
	}

	//一般不使用,提jxl方式处理方法
	public void getJxlExcelData(String taskId, Map params, JxlsExcelExporter jxlExporter) {
		// TODO Auto-generated method stub
		
	}	

}
