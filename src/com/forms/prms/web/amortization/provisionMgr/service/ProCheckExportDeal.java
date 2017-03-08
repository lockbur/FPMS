package com.forms.prms.web.amortization.provisionMgr.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.constantValues.ExcelTaskStatusValues;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.amortization.provisionMgr.domain.ProvisionBean;

public class ProCheckExportDeal implements IExportDataDeal{
	//判断处理状态
	private boolean flag = true;
	//处理信息
	private String dealMsg = "";
	
	ProvisionService service = ProvisionService.getInstance();
	
	public void start(String taskId, Map params) throws Exception{
		//导入前处理，校验各项参数
		if(taskId == null || taskId.trim().length() == 0){
			flag = false;
			dealMsg = "该导出流水不存在";
		}
	}
	
	public ProvisionBean setCreateDeptListByCreateDepts(ProvisionBean provision){
		if(null != provision.getCreateDepts()){
			if(provision.getCreateDepts().length != 0){
				provision.setCreateDeptList(Arrays.asList(provision.getCreateDepts()));
			}
		}
		return provision;
	}

	/**
	 * 修改方法，添加导出加载方法
	 */
	public void getSimpleExcelData(String taskId, Map params, SimplifyBatchExcelExporter excelExporter)throws Exception{		
		//导出前处理为失败，后续将不再导入
		if(!flag){
			return;
		}

		ProvisionBean proBean = new ProvisionBean();
		BeanUtils.copyProperties(proBean, params);
		proBean = this.setCreateDeptListByCreateDepts(proBean);
		//传入参数
		params.put("provision", proBean);
		
		String advCglList = service.getAdvCglList();	
		params.put("advCglList", advCglList);
		
		Map<String,Object> beansMap = new HashMap<String, Object>();
		List<ProvisionBean> proList = null;

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
				proList = service.queryContactList(params);
				beansMap.put("0", proList);
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
