package com.forms.prms.web.sysmanagement.montindex.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.util.Tool;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;

public class ExportExcel implements IExportDataDeal {
	MontIndexService mis=MontIndexService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();	
	
	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
		Map<String,Object> beansMap = new HashMap<String, Object>();
		//获取方法调用的接收参数
		String montCode=(String)params.get("montCode");
		String montCodeHis=(String)params.get("montCodeHis");
		String montName=(String)params.get("montName");
		String montType=(String)params.get("montType");
		String dataYear=(String)params.get("dataYear");
		String matrCode=(String)params.get("matrCode");
		String matrName=(String)params.get("matrName");
		String org1Code=(String)params.get("org1Code");
		String org2Code=(String)params.get("org2Code");
		
		MontIndexBean bean=new MontIndexBean();
		bean.setMontCode(montCode);
		if (montCodeHis!=""&&montCodeHis!=null) {
			bean.setMontCodeHis(montCodeHis);
		}
		bean.setMontName(montName);
		bean.setMontType(montType);
		bean.setDataYear(dataYear);
		bean.setMatrCode(matrCode);
		bean.setMatrName(matrName);
		bean.setOrg1Code(org1Code);
		bean.setOrg2Code(org2Code);
		List<MontIndexBean> montList=new ArrayList<MontIndexBean>();
		if (montCodeHis!=""&&montCodeHis!=null&&montCodeHis!="null") {
			montList=mis.alMontList(bean);
		} else {
			try {
				int nowYear=Integer.parseInt(Tool.DATE.getDateStrNO().substring(0,4));
				int year=Integer.parseInt(dataYear);
				if (year<nowYear) {//如果年份小于当前年份，就查历史表的数据
					montList=mis.hisMontList(bean);
				}else {
					montList=mis.mmontList(bean);
				}
			} catch (Exception e) {
				System.out.println("年份为空，默认查询本年度数据!");
				montList=mis.mmontList(bean);
			}
		}
		exportSucFlag=true;
		exportMemo="处理成功，可下载";
		beansMap.put("0", montList);
		
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
	public void getJxlExcelData(String taskId, Map params,
			JxlsExcelExporter jxlExporter) throws Exception {
		// TODO Auto-generated method stub
		
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
	
}
