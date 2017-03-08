package com.forms.prms.web.budget.bgtImport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.budget.bgtImport.domain.BudgetImportBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.export.exportPage.ExportPageToolService;

public class BudgetExport implements IExportDataDeal {
	BudgetImportService es=BudgetImportService.getInstance();
	ExportPageToolService exportPageToolService = ExportPageToolService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance();		//用于更新导出任务结果状态和导出备注
	

	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
		
		Map<String,Object> beansMap = new HashMap<String, Object>();
		//获取方法调用的接收参数
		String batchNo = (String) params.get("batchNo");
		String type = (String) params.get("type");//01-是导入数据的模板下载，02-是导入的明细数据
		String bgtYear =(String) params.get("bgtYear");
		String org21Code=(String) params.get("org21Code");
		String bgtType = (String) params.get("bgtType");
		String subType = (String) params.get("subType");
		String orgType = (String) params.get("orgType");
		BudgetImportBean  bean = new BudgetImportBean();
		bean.setBatchNo(batchNo);
		bean.setBgtYear(bgtYear);
		bean.setOrg21Code(org21Code);
		bean.setBgtType(bgtType);
		bean.setSubType(subType);
		bean.setOrgType(orgType);
		String sql = "";
			//插入excel数据内容
		List<BudgetImportBean> budgetContent = new ArrayList<BudgetImportBean>();
		try {
			if ("01".equals(type)) {
				//这个是导入模板数据
				//要导入正式预算模板 则初始化出 所有临时预算和透支预算，也就是SUM表中的所有数据
				if ("01".equals(bgtType)) {
//					budgetContent = es.getDownloadTemp(bean);
					if ("1".equals(orgType)) {
						 sql+= " SELECT NVL2(F.DUTY_NAME,                                                     ";
						 sql+= "           F.DUTY_NAME,                                                       ";
						 sql+= "           NVL2(F2.ORG2_NAME, F2.ORG2_NAME, F3.ORG1_NAME)) AS bgt_ORG_name,   ";
						 sql+= "      e.bgt_ORGCODE,                                                          ";
						 sql+= "      E.BGT_MONTCODE,                                                         ";
						 sql+= "      DECODE(E.BGT_MATRCODE, '00000000000', '', E.BGT_MATRCODE) BGT_MATR_CODE,";
						 sql+= "      E.BGT_OVERDRAW,                                                         ";
						 sql+= "      (E.BGT_FROZEN + E.BGT_USED) AS BGT_FROZEN_USED,                         ";
						 sql+= "      T.MATR_NAME as bgt_matr_name,                                           ";
						 sql+= "      M.MONT_NAME as bgt_mont_name                                            ";
						 sql+= " FROM ERP_BUDGET_SUM E                                                        ";
						 sql+= " LEFT JOIN TB_MATR_TYPE T                                                     ";
						 sql+= "   ON E.BGT_MATRCODE = T.MATR_CODE                                            ";
						 sql+= " LEFT JOIN TB_MONT_NAME M                                                     ";
						 sql+= "   ON E.BGT_MONTCODE = M.MONT_CODE                                            ";
						 sql+= " LEFT JOIN tB_FNDWRR F                                                        ";
						 sql+= "   ON F.DUTY_CODE = E.BGT_ORGCODE                                             ";
						 sql+= " left join (select distinct org2_code, org2_name                              ";
						 sql+= "              from TB_FNDWRR                                                  ";
						 sql+= "             where org1_code = '"+org21Code+"') F2          ";
						 sql+= "   ON F2.org2_code = E.BGT_ORGCODE                                            ";
						 sql+= " left join (select distinct org1_code, org1_name                              ";
						 sql+= "              from TB_FNDWRR                                                  ";
						 sql+= "             where org1_code ='"+org21Code+"') F3          ";
						 sql+= "   ON F3.org1_code = E.BGT_ORGCODE                                            ";
						 sql+= "where E.BGT_YEAR = '"+bgtYear+"'                            ";
						 sql+= "  and M.mont_type ='"+subType+"'                           ";
						 sql+= "                                                                              ";
						 sql+= "  and exists                                                                  ";
						 sql+= "(select 1                                                                     ";
						 sql+= "         from tb_fndwrr a                                                     ";
						 sql+= "        where a.org1_code = '"+org21Code+"'                   ";                                                            
						 sql+= "          and (a.org1_code = e.bgt_orgcode or a.org2_code = e.bgt_orgcode or  ";
						 sql+= "              a.duty_code = e.bgt_orgcode))                                   ";

					}else {
						sql+=" 	SELECT NVL2(F.DUTY_NAME, F.DUTY_NAME, F2.ORG2_NAME) AS bgt_ORG_name,              ";
						sql+="        e.bgt_ORGCODE,                                                              ";
						sql+="        E.BGT_MONTCODE,                                                             ";
						sql+="        DECODE(E.BGT_MATRCODE, '00000000000', '', E.BGT_MATRCODE) BGT_MATR_CODE,    ";
						sql+="        E.BGT_OVERDRAW,                                                             ";
						sql+="        (E.BGT_FROZEN + E.BGT_USED) AS BGT_FROZEN_USED,                             ";
						sql+="        T.MATR_NAME as bgt_matr_name,                                               ";
						sql+="        M.MONT_NAME as bgt_mont_name                                                ";
						sql+="   FROM ERP_BUDGET_SUM E                                                            ";
						sql+="   LEFT JOIN TB_MATR_TYPE T                                                         ";
						sql+="     ON E.BGT_MATRCODE = T.MATR_CODE                                                ";
						sql+="   LEFT JOIN TB_MONT_NAME M                                                         ";
						sql+="     ON E.BGT_MONTCODE = M.MONT_CODE                                                ";
						sql+="   LEFT JOIN tB_FNDWRR F                                                            ";
						sql+="     ON F.DUTY_CODE = E.BGT_ORGCODE                                                 ";
						sql+="   left join (select distinct org2_code, org2_name                                  ";
						sql+="                from TB_FNDWRR                                                      ";
						sql+="               where org2_code = '"+org21Code+"') F2              ";
						sql+="     ON F2.org2_code = E.BGT_ORGCODE                                                ";
						sql+="  where E.BGT_YEAR ='"+bgtYear+"'                           ";
						sql+="    and M.mont_type = '"+subType+"'                            ";
						sql+="                                                                                    ";
						sql+="    and exists                                                                      ";
						sql+="  (select 1                                                                         ";
						sql+="           from tb_fndwrr a                                                         ";
						sql+="          where a.org2_code = '"+org21Code+"'                      ";
						sql+="            and (a.org2_code = e.bgt_orgcode or a.duty_code = e.bgt_orgcode))       ";

					}
				}else {
					//要导入的不是正式预算则 只导出透支预算
//					budgetContent = es.getDownloadTempOnlyOver(bean);
					if ("1".equals(orgType)) {
						sql+= " 	SELECT NVL2(F.DUTY_NAME,                                                      ";
						sql+= "             F.DUTY_NAME,                                                          ";
						sql+= "             NVL2(F2.ORG2_NAME, F2.ORG2_NAME, F3.ORG1_NAME)) AS bgt_ORG_name,      ";
						sql+= "        e.bgt_ORGCODE,                                                             ";
						sql+= "        E.BGT_MONTCODE,                                                            ";
						sql+= "        DECODE(E.BGT_MATRCODE, '00000000000', '', E.BGT_MATRCODE) BGT_MATR_CODE,   ";
						sql+= "        E.BGT_OVERDRAW,                                                            ";
						sql+= "        (E.BGT_FROZEN + E.BGT_USED) AS BGT_FROZEN_USED,                            ";
						sql+= "        T.MATR_NAME as bgt_matr_name,                                              ";
						sql+= "        M.MONT_NAME as bgt_mont_name                                               ";
						sql+= "   FROM ERP_BUDGET_SUM E                                                           ";
						sql+= "   LEFT JOIN TB_MATR_TYPE T                                                        ";
						sql+= "     ON E.BGT_MATRCODE = T.MATR_CODE                                               ";
						sql+= "   LEFT JOIN TB_MONT_NAME M                                                        ";
						sql+= "     ON E.BGT_MONTCODE = M.MONT_CODE                                               ";
						sql+= "   LEFT JOIN tB_FNDWRR F                                                           ";
						sql+= "     ON F.DUTY_CODE = E.BGT_ORGCODE                                                ";
						sql+= "   left join (select distinct org2_code, org2_name                                 ";
						sql+= "                from TB_FNDWRR                                                     ";
						sql+= "               where org1_code = '"+org21Code+"') F2             ";
						sql+= "     ON F2.org2_code = E.BGT_ORGCODE                                               ";
						sql+= "   left join (select distinct org1_code, org1_name                                 ";
						sql+= "                from TB_FNDWRR                                                     ";
						sql+= "               where org1_code = '"+org21Code+"') F3             ";
						sql+= "     ON F3.org1_code = E.BGT_ORGCODE                                               ";
						sql+= "  where E.BGT_YEAR = '"+bgtYear+"'                               ";
						sql+= "    and E.BGT_OVERDRAW != 0                                                        ";
						sql+= "    and M.mont_type = '"+subType+"'                             ";
						sql+= "    and exists                                                                     ";
						sql+= "  (select 1                                                                        ";
						sql+= "           from tb_fndwrr a                                                        ";
						sql+= "          where a.org1_code = '"+org21Code+"'                    ";                      
						sql+= "            and (a.org1_code = e.bgt_orgcode or a.org2_code = e.bgt_orgcode or     ";
						sql+= "                a.duty_code = e.bgt_orgcode))                                      ";

					}else {                                                                                    
						sql+=" 	SELECT NVL2(F.DUTY_NAME, F.DUTY_NAME, F2.ORG2_NAME) AS bgt_ORG_name,                 ";
						sql+="        e.bgt_ORGCODE,                                                                 ";
						sql+="        E.BGT_MONTCODE,                                                                ";
						sql+="        DECODE(E.BGT_MATRCODE, '00000000000', '', E.BGT_MATRCODE) BGT_MATR_CODE,       ";
						sql+="        E.BGT_OVERDRAW,                                                                ";
						sql+="        (E.BGT_FROZEN + E.BGT_USED) AS BGT_FROZEN_USED,                                ";
						sql+="        T.MATR_NAME as bgt_matr_name,                                                  ";
						sql+="        M.MONT_NAME as bgt_mont_name                                                   ";
						sql+="   FROM ERP_BUDGET_SUM E                                                               ";
						sql+="   LEFT JOIN TB_MATR_TYPE T                                                            ";
						sql+="     ON E.BGT_MATRCODE = T.MATR_CODE                                                   ";
						sql+="   LEFT JOIN TB_MONT_NAME M                                                            ";
						sql+="     ON E.BGT_MONTCODE = M.MONT_CODE                                                   ";
						sql+="   LEFT JOIN tB_FNDWRR F                                                               ";
						sql+="     ON F.DUTY_CODE = E.BGT_ORGCODE                                                    ";
						sql+="   left join (select distinct org2_code, org2_name                                     ";
						sql+="                from TB_FNDWRR                                                         ";
						sql+="               where org2_code = '"+org21Code+"'  ) F2                 ";
						sql+="     ON F2.org2_code = E.BGT_ORGCODE                                                   ";
						sql+="  where E.BGT_YEAR = '"+bgtYear+"'                                     ";
						sql+="    and E.BGT_OVERDRAW != 0                                                            ";
						sql+="    and M.mont_type = '"+subType+"'                                   ";
						sql+="    and exists                                                                         ";
						sql+="  (select 1                                                                            ";
						sql+="           from tb_fndwrr a                                                            ";
						sql+="          where a.org2_code ='"+org21Code+"'                      ";
						sql+="            and (a.org2_code = e.bgt_orgcode or a.duty_code = e.bgt_orgcode))          ";
					}
				}
				
				
			}else {
				//这个是导入的明细数据
//				budgetContent=es.getImportDetailForExcel(batchNo);
					sql+=" 	SELECT A.BGT_ORGCODE,                              ";
					sql+="        A.BGT_ORGNAME,                               ";
					sql+="        A.BGT_MONTCODE,                              ";
					sql+="        A.BGT_MONTNAME,                              ";
					sql+="        A.BGT_MATRCODE,                              ";
					sql+="        A.BGT_MATRNAME,                              ";
					sql+="        A.Bgt_Overdraw,                              ";
					sql+="        a.bgt_sum,                                   ";
					sql+="        B.ERR_DESC                                   ";
			        sql+="                                                     ";
					sql+="   FROM ERP_BUDGET_SUM_TEMP A                        ";
					sql+="   LEFT JOIN upload_data_error_info B                ";
					sql+="     ON B.BATCH_NO = A.BATCH_NO                      ";
					sql+="    AND B.ROW_NO = A.EXCEL_NO                        ";
					sql+="   LEFT JOIN TB_MONT_NAME C                          ";
					sql+="     ON C.MONT_CODE = A.BGT_MONTCODE                 ";
					sql+="   LEFT JOIN TB_MATR_TYPE D                          ";
					sql+="     ON D.MATR_CODE = A.BGT_MATRCODE                 ";
					sql+="  WHERE A.BATCH_NO = '"+batchNo+"'  ";
			}
			exportPageToolService.execute(sql,excelExporter);
			exportSucFlag=true;
			exportMemo="处理成功，可下载";
//			beansMap.put("0", budgetContent);	
		} catch (Exception e) {
			// TODO: handle exception
			exportMemo = "【处理失败】， ：" ;
			exportSucFlag = false;
			CommonLogger.error("数据导出时往excel里插入数据失败" + e.getMessage()
					+ "ExportExcel,getSimpleExcelData");
			e.printStackTrace();
		}
		
		
//		try {
//			//可按查找数据量进行分段加载
//			ExcelExportUtility.loadExcelData(beansMap,excelExporter);
//		} catch (Exception e) {
//			exportMemo = "【处理失败】，详情："+e.getMessage().substring(300)+"...";
//			e.printStackTrace();
//		} 
		finally{
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
