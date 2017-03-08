package com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.domain.ApproveChainMgrBean;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;

public class ExportExcel implements IExportDataDeal {
	ApproveChainMgrService service=ApproveChainMgrService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();	
	
	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
		Map<String,Object> beansMap = new HashMap<String, Object>();
		//获取方法调用的接收参数
		String feeCode=(String)params.get("feeCode");
		String montCode=(String)params.get("montCode");
		String matrBuyDept=(String)params.get("matrBuyDept");
		String matrAuditDept=(String)params.get("matrAuditDept");
		String decomposeOrg=(String)params.get("decomposeOrg");
		String fincDeptS=(String)params.get("fincDeptS");
		String fincDept2=(String)params.get("fincDept2");		
		String fincDept1=(String)params.get("fincDept1");		
		String dataYear=(String)params.get("dataYear");
		String matrCode=(String)params.get("matrCode");
		String matrName=(String)params.get("matrName");
		String org1Code=(String)params.get("org1Code");
		String isProvinceBuy=(String)params.get("isProvinceBuy");	
		String aprvType = (String)params.get("aprvType");
		String org21Code = (String)params.get("org21Code");
		ApproveChainMgrBean bean=new ApproveChainMgrBean();
		
		bean.setFeeCode(feeCode);
		bean.setMontCode(montCode);		
		bean.setMatrBuyDept(matrBuyDept);
		bean.setMatrAuditDept(matrAuditDept);
		bean.setDataYear(dataYear);
		bean.setMatrCode(matrCode);
		bean.setMatrName(matrName);
		bean.setOrg1Code(org1Code);
		bean.setDecomposeOrg(decomposeOrg);
		bean.setFincDept1(fincDept1);
		bean.setFincDept2(fincDept2);
		bean.setFincDeptS(fincDeptS);
		bean.setIsProvinceBuy(isProvinceBuy);
		bean.setAprvType(aprvType);
		bean.setOrg21Code(org21Code);
		List<ApproveChainMgrBean> appList=service.aprvList(bean);
				
		exportSucFlag=true;
		exportMemo="处理成功，可下载";
		beansMap.put("0", appList);
		
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
