package com.forms.prms.tool.exceltool.exportthread;

import java.util.HashMap;
import java.util.Map;

import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.prms.tool.exceltool.StringUtil;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;

public class ExcelExportInter implements Runnable{

	private CommonExcelDealBean loadBean;
	private Map params;
	
	public ExcelExportInter(CommonExcelDealBean loadBean){
		this.loadBean = loadBean;
	}
	
	@Override
	public void run() {		
		//TODO
		try{
			if(loadBean.getTaskParams() != null && !"".equals(loadBean.getTaskParams().trim())){
				params = StringUtil.parserToMap(loadBean.getTaskParams());
			}else{
				params = new HashMap();
			}
			//判断Excel文件的导出方式
			if(loadBean.getExportType().equals("bean")){
				ExcelExportUtility.processJxlExport(loadBean.getTaskId(), loadBean.getConfigId(), loadBean.getDestFile(),params);
			}else{
			ExcelExportUtility.processExport(loadBean.getTaskId(), loadBean.getConfigId(), loadBean.getDestFile(),params);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
