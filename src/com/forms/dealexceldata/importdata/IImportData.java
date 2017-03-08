package com.forms.dealexceldata.importdata;

import com.forms.dealexceldata.exceldealtool.ImportExcelBean;
import com.forms.dealexceldata.exceldealtool.domain.ImportBean;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;

/**
 * 导入数据接口
 * 
 * @author user
 * 
 */
public interface IImportData {

	public void beforeImport(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception;

	public void dealFile(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception;

	public void callProc(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception;

	public void afterImport(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean,ImportBean iBean) throws Exception;

	public String execute(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception;

	public void updataStatus(String taskId,String batchNo,String tableName, boolean flag,String memo) throws Exception;

}
