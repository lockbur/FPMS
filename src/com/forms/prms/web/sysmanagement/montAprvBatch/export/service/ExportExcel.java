package com.forms.prms.web.sysmanagement.montAprvBatch.export.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.export.exportPage.ExportPageToolService;
import com.forms.prms.web.sysmanagement.montAprvBatch.export.domain.ExportBean;

public class ExportExcel implements IExportDataDeal {

	ExportService es = ExportService.getInstance();
	ExportPageToolService exportPageToolService = ExportPageToolService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance(); // 用于更新导出任务结果状态和导出备注

	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";

		Map<String, Object> beansMap = new HashMap<String, Object>();
		// 获取方法调用的接收参数
		String dataYear = (String) params.get("dataYear");
		String proType = (String) params.get("proType");
		String subType = (String) params.get("subType");
		String org21Code = (String) params.get("org21Code");
//		String yearTag = (String) params.get("yearTag");

		ExportBean bean = new ExportBean();
		bean.setDataYear(dataYear);
		bean.setProType(proType);
		bean.setSubType(subType);
		bean.setOrg21Code(org21Code);
		String sql = "";
		// //插入excel数据内容
		try {
			if ("01".equals(proType)) {
				// 监控指标
				List<ExportBean> montContent = new ArrayList<ExportBean>();
				//首先判断 是不是这个这个二级行/一级行 的这个类型的 第一次导入
				int isFirst = es.isFirst(bean);
				if (isFirst==0) {
					sql = "SELECT  MATR_CODE, MATR_NAME, CGL_CODE FROM TB_MATR_TYPE	";
					if (subType.equals("12") || subType.equals("21")) {
						sql+="where MATR_TYPE ='1'";
					}else if (subType.equals("22")) {
						sql +="where MATR_TYPE ='3'";
					}
//					montContent = es.selectInitData(bean);
				}else {
					//导出该类型在数据库中最新 的数据
//					montContent = es.getLastMontsFromTable(bean);
				sql+= " SELECT                                                    ";
				sql+= " A.MATR_CODE,                                              ";
				sql+= " C.MATR_NAME,                                              ";
				sql+= " C.CGL_CODE,                                               ";
				sql+= " DECODE(A.IS_VALID,'1','可用','废弃') IS_VALID_NAME,           ";
				sql+= " B.MONT_CODE AS LAST_MONT_CODE,                            ";
				sql+= " B.MONT_NAME AS LAST_MONT_NAME,                            ";
				sql+= " A.MONT_PROJ_TYPE AS LAST_PROJ_TYPE                        ";
				sql+= " FROM TB_MONT_MATR_CONTRA  A                               ";
				sql+= " LEFT JOIN TB_MONT_NAME B  ON B.MONT_CODE = A.MONT_CODE    ";
				sql+= " LEFT JOIN TB_MATR_TYPE C ON C.MATR_CODE = A.MATR_CODE     ";
				sql+= " where  B.org21_code =  '"+org21Code+"'"  ;
				sql+= " and B.mont_type=  '"+subType+"'"  ;
				sql+= " and B.data_year =  '"+dataYear+"'"  ;
				sql+= " ORDER BY A.MONT_CODE,A.MATR_CODE,C.CGL_CODE ASC           ";
				}
//				beansMap.put("0", montContent);
			} else {
				List<ExportBean> aprvContent = new ArrayList<ExportBean>();
				// 审批链
				int isFirst = es.isFirst(bean);
				if (isFirst==0) {
					//审批链从来没有导入过，则初始化数据
//					aprvContent = es.selectInitAprvData(bean);
					if ("11".equals(subType)) {
						sql+="SELECT distinct c.mont_code, b.mont_name, c.matr_code, D.MATR_NAME ";
						sql+="  from TB_MONT_MATR_CONTRA C                                       ";
						sql+="  left join tb_mont_name b                                         ";
						sql+="    on b.mont_code = c.mont_code                                   ";
						sql+="  LEFT JOIN TB_MATR_TYPE D                                         ";
						sql+="    ON D.MATR_CODE = C.MATR_CODE                                   ";
						sql+=" where b.mont_type = '11'                                          ";
						sql+="   and b.org21_code = '"+org21Code+"'            ";
						sql+="   and c.is_valid = '1'                                            ";
						sql+=" ORDER BY C.MONT_CODE, C.MATR_CODE ASC                             ";
					}else if ("12".equals(subType)) {                            
						sql+=" SELECT distinct b.mont_code,                         ";
				        sql+="        b.mont_name,                                  ";
				        sql+="        c.matr_code,                                  ";
				        sql+="        D.MATR_NAME,                                  ";
				        sql+="        A.ORG_CODE,                                   ";
				        sql+="        A.ORG_NAME                                    ";
						sql+="  from tb_fndwrr a, TB_MONT_NAME B                    ";
				        sql+="                                                      ";
						sql+="  LEFT JOIN TB_MONT_MATR_CONTRA C                     ";
						sql+="    ON C.MONT_CODE = B.MONT_CODE                      ";
						sql+="  LEFT JOIN TB_MATR_TYPE D                            ";
						sql+="    ON D.MATR_CODE = C.MATR_CODE                      ";
						sql+=" where a.org1_code = '"+org21Code+"'            ";
						sql+="   and c.is_valid = '1'                               ";
						sql+="   AND B.ORG21_CODE = A.ORG1_CODE                     ";
						sql+="   AND B.MONT_TYPE =  '"+subType+"'            ";   
						sql+="   and b.data_year =  '"+dataYear+"'            ";   
						sql+=" ORDER BY A.ORG_CODE, B.MONT_CODE, C.MATR_CODE ASC    ";
					}else {
						sql+=" SELECT distinct b.mont_code,                            ";
						sql+="                 b.mont_name,                            ";
						sql+="                 c.matr_code,                            ";
						sql+="                 D.MATR_NAME,                            ";
						sql+="                 A.ORG_CODE,                             ";
						sql+="                 A.ORG_NAME                              ";
						sql+="   from tb_fndwrr a, TB_MONT_NAME B                      ";
				        sql+="                                                         ";
						sql+="   LEFT JOIN TB_MONT_MATR_CONTRA C                       ";
						sql+="     ON C.MONT_CODE = B.MONT_CODE                        ";
						sql+="   LEFT JOIN TB_MATR_TYPE D                              ";
						sql+="     ON D.MATR_CODE = C.MATR_CODE                        ";
						sql+="  where a.org2_code = '"+org21Code+"'   ";
						sql+="    and c.is_valid = '1'                                 ";
						sql+="    AND B.ORG21_CODE = A.ORG2_CODE                       ";
						sql+="    AND B.MONT_TYPE = '"+subType+"'   ";
						sql+="    and b.data_year = '"+dataYear+"'  ";
						sql+="  ORDER BY A.ORG_CODE, B.MONT_CODE, C.MATR_CODE ASC      ";
					}

				}else {
					if ("11".equals(subType)) {
						// 专项包
						if (Integer.parseInt(Tool.DATE.getDateStrNO().substring(0,4))< Integer.parseInt(bean.getDataYear())) {
							//如果传过来的年份大于当前年 肯定是在FUT表
							bean.setAprvChainTable("TB_APRV_CHAIN_SPEC_FUT");
						}else {
							//2014年和2015年都肯定在正式表.但是如果是2014年就使用MONT_CODE_LAST 如果是2015年就直接导出
							bean.setAprvChainTable("TB_APRV_CHAIN_SPEC");
						}
						if (Tool.DATE.getDateStrNO().substring(0,4).equals(bean.getDataYear())) {
							//直接导出
//							aprvContent = es.getAprvSpecTable(bean);
							sql += "SELECT *                                                          "  ;
							sql += "  FROM (select tf.mont_code,                                      "  ;
							sql += "               tf.mont_name,                                      "  ;
							sql += "               a.matr_code,                                       "  ;
							sql += "               c.matr_name,                                       "  ;
							sql += "               a.matr_buy_dept,                                   "  ;
							sql += "               d1.duty_name      as matr_buy_dept_name,           "  ;
							sql += "               a.matr_audit_dept,                                 "  ;
							sql += "               d2.duty_name      as matr_audit_dept_name,         "  ;
							sql += "               a.finc_dept_s,                                     "  ;
							sql += "               d3.duty_name      as finc_dept_s_name,             "  ;
							sql += "               a.finc_dept_2,                                     "  ;
							sql += "               d4.duty_name      as finc_dept_2_name,             "  ;
							sql += "               a.finc_dept_1,                                     "  ;
							sql += "               d5.duty_name      as finc_dept_1_name              "  ;
							sql += "          from "+bean.getAprvChainTable()+" a                                "  ;
							sql += "                                                                  "  ;
							sql += "          left join tb_mont_name tf                               "  ;
							sql += "            on tf.mont_code = a.mont_code                         "  ;
							sql += "          left join tb_matr_type c                                "  ;
							sql += "            on a.matr_code = c.matr_code                          "  ;
							sql += "          left join tb_fndwrr d1                                  "  ;
							sql += "            on d1.duty_code = a.matr_buy_dept                     "  ;
							sql += "          left join tb_fndwrr d2                                  "  ;
							sql += "            on d2.duty_code = a.matr_audit_dept                   "  ;
							sql += "          left join tb_fndwrr d3                                  "  ;
							sql += "            on d3.duty_code = a.finc_dept_s                       "  ;
							sql += "          left join tb_fndwrr d4                                  "  ;
							sql += "            on d4.duty_code = a.finc_dept_2                       "  ;
							sql += "          left join tb_fndwrr d5                                  "  ;
							sql += "            on d5.duty_code = a.finc_dept_1                       "  ;
							sql += "         where a.data_year = '"+dataYear+"'"  ;
							sql += "           and a.org1_code = '"+org21Code+"'"  ;
							sql += "                                                                  "  ;
							sql += "        union                                                     "  ;
							sql += "        select TMC.MONT_CODE,                                     "  ;
							sql += "               TMN.MONT_NAME,                                     "  ;
							sql += "               TMT.MATR_CODE,                                     "  ;
							sql += "               TMT.MATR_NAME,                                     "  ;
							sql += "               '' AS matr_buy_dept,                               "  ;
							sql += "               '' as matr_buy_dept_name,                          "  ;
							sql += "               '' AS matr_audit_dept,                             "  ;
							sql += "               '' as matr_audit_dept_name,                        "  ;
							sql += "               '' AS finc_dept_s,                                 "  ;
							sql += "               '' as finc_dept_s_name,                            "  ;
							sql += "               '' AS finc_dept_2,                                 "  ;
							sql += "               '' as finc_dept_2_name,                            "  ;
							sql += "               '' AS finc_dept_1,                                 "  ;
							sql += "               '' as finc_dept_1_name                             "  ;
							sql += "          from Tb_Mont_Matr_Contra TMC                            "  ;
							sql += "          LEFT JOIN TB_MONT_NAME TMN                              "  ;
							sql += "            ON TMN.MONT_CODE = TMC.MONT_CODE                      "  ;
							sql += "          LEFT JOIN TB_MATR_TYPE TMT                              "  ;
							sql += "            ON TMT.MATR_CODE = TMC.MATR_CODE                      "  ;
							sql += "         WHERE TMN.DATA_YEAR = '"+dataYear+"'"  ;                                
							sql += "           AND TMN.ORG21_CODE = '"+org21Code+"'"  ;
							sql += "           AND TMN.MONT_TYPE = '11'                               "  ;
							sql += "           AND TMC.IS_VALID = '1'                                 "  ;
							sql += "           AND NOT EXISTS                                         "  ;
							sql += "         (SELECT 1                                                "  ;
							sql += "                  FROM TB_APRV_CHAIN_SPEC TACS                    "  ;
							sql += "                 WHERE TACS.MONT_CODE = TMC.MONT_CODE             "  ;
							sql += "                   AND TACS.MATR_CODE = TMC.MATR_CODE)) UT        "  ;
							sql += " ORDER BY UT.MONT_CODE, UT.MATR_CODE ASC                          "  ;
						}else {
							//通过监控指标CHANGE表转换
//							aprvContent = es.getAprvSpecTableChange(bean);
							sql += " select distinct tf.mont_code,                              ";
							sql += "                 tf.mont_name,                              ";
							sql += "                 a.matr_code,                               ";
							sql += "                 c.matr_name,                               ";
							sql += "                 a.matr_buy_dept,                           ";
							sql += "                 d1.duty_name      as matr_buy_dept_name,   ";
							sql += "                 a.matr_audit_dept,                         ";
							sql += "                 d2.duty_name      as matr_audit_dept_name, ";
							sql += "                 a.finc_dept_s,                             ";
							sql += "                 d3.duty_name      as finc_dept_s_name,     ";
							sql += "                 a.finc_dept_2,                             ";
							sql += "                 d4.duty_name      as finc_dept_2_name,     ";
							sql += "                 a.finc_dept_1,                             ";
							sql += "                 d5.duty_name      as finc_dept_1_name      ";
							sql += "   from " +bean.getAprvChainTable() +" a                                 ";
							sql += "   left join tb_mont_matr_contra_change tc                  ";
							sql += "     on tc.mont_code_old = a.mont_code                      ";
							sql += "    and tc.matr_code = a.matr_code                          ";
							sql += "                                                            ";
							sql += "   left join tb_mont_name tf                                ";
							sql += "     on tf.mont_code = tc.mont_code_last                    ";
							sql += "   left join tb_matr_type c                                 ";
							sql += "     on a.matr_code = c.matr_code                           ";
							sql += "   left join tb_fndwrr d1                                   ";
							sql += "     on d1.duty_code = a.matr_buy_dept                      ";
							sql += "   left join tb_fndwrr d2                                   ";
							sql += "     on d2.duty_code = a.matr_audit_dept                    ";
							sql += "   left join tb_fndwrr d3                                   ";
							sql += "     on d3.duty_code = a.finc_dept_s                        ";
							sql += "   left join tb_fndwrr d4                                   ";
							sql += "     on d4.duty_code = a.finc_dept_2                        ";
							sql += "   left join tb_fndwrr d5                                   ";
							sql += "     on d5.duty_code = a.finc_dept_1                        ";
							sql += "  where a.data_year ='"+dataYear+"'"  ;   
							sql += "    and a.org1_code = '"+org21Code+"'"  ;   
						}
					} else {
						// 非专项包
						if (Integer.parseInt(Tool.DATE.getDateStrNO().substring(0,4))< Integer.parseInt(bean.getDataYear())) {
							//如果传过来的年份大于当前年 肯定是在FUT表
							bean.setAprvChainNoSpecTable("TB_APRV_CHAIN_NOSPEC_FUT");
						}else {
							//2014年和2015年都肯定在正式表.但是如果是2014年就使用MONT_CODE_LAST 如果是2015年就直接导出
							bean.setAprvChainNoSpecTable("TB_APRV_CHAIN_NOSPEC");
						}
						if (Tool.DATE.getDateStrNO().substring(0,4).equals(bean.getDataYear())) {
							//直接导出
//							aprvContent = es.getAprvNoSpecTable(bean);
							if (subType.equals("12")) {
								sql += "select *                                                                      ";
								sql += "  from (select a.FEE_CODE,                                                    ";
								sql += "               d0.duty_name      as FEE_NAME,                                 ";
								sql += "               tf.mont_code,                                                  ";
								sql += "               tf.mont_name,                                                  ";
								sql += "               a.matr_code,                                                   ";
								sql += "               c.matr_name,                                                   ";
								sql += "               a.matr_buy_dept,                                               ";
								sql += "               d1.duty_name      as matr_buy_dept_name,                       ";
								sql += "               a.matr_audit_dept,                                             ";
								sql += "               d2.duty_name      as matr_buy_audit_name,                      ";
								sql += "               a.finc_dept_s,                                                 ";
								sql += "               d3.duty_name      as finc_dept_s_name,                         ";
								sql += "               a.finc_dept_2,                                                 ";
								sql += "               d4.duty_name      as finc_dept_2_name,                         ";
								sql += "               a.finc_dept_1,                                                 ";
								sql += "               d5.duty_name      as finc_dept_1_name,                         ";
								sql += "               A.DECOMPOSE_ORG,                                               ";
								sql += "               a.org_code,                                                    ";
								sql += "               D7.org_name       AS ORG_NAME,                                 ";
								sql += "               d6.duty_name      as DECOMPOSE_ORG_name                        ";
								sql += "          from "+bean.getAprvChainNoSpecTable()+" a                                      ";
								sql += "          left join tb_mont_name tf                                           ";
								sql += "            on tf.mont_code = a.mont_code                                     ";
								sql += "          left join tb_matr_type c                                            ";
								sql += "            on a.matr_code = c.matr_code                                      ";
								sql += "          left join tb_fndwrr d0                                              ";
								sql += "            on d0.duty_code = a.FEE_CODE                                      ";
								sql += "          left join tb_fndwrr d1                                              ";
								sql += "            on d1.duty_code = a.matr_buy_dept                                 ";
								sql += "          left join tb_fndwrr d2                                              ";
								sql += "            on d2.duty_code = a.matr_audit_dept                               ";
								sql += "          left join tb_fndwrr d3                                              ";
								sql += "            on d3.duty_code = a.finc_dept_s                                   ";
								sql += "          left join tb_fndwrr d4                                              ";
								sql += "            on d4.duty_code = a.finc_dept_2                                   ";
								sql += "          left join tb_fndwrr d5                                              ";
								sql += "            on d5.duty_code = a.finc_dept_1                                   ";
								sql += "          left join tb_fndwrr d6                                              ";
								sql += "            on d6.duty_code = a.DECOMPOSE_ORG                                 ";
								sql += "          LEFT JOIN TB_FNDWRR D7                                              ";
								sql += "            ON D7.ORG_CODE = A.ORG_CODE                                       ";
								sql += "         where a.data_year ='"+dataYear+"'"  ;   
								sql += "           and a.org21_code = '"+org21Code+"'"  ;   
								sql += "           and aprv_type = '"+subType+"'"  ;   
								sql += "                                                                              ";
								sql += "        UNION                                                                 ";
								sql += "        SELECT '' as FEE_CODE,                                                ";
								sql += "               '' as FEE_NAME,                                                ";
								sql += "               FOJ.mont_code,                                                 ";
								sql += "               FOJ.mont_name,                                                 ";
								sql += "               FOJ. matr_code as matr_code,                                   ";
								sql += "               FOJ.matr_name as matr_name,                                    ";
								sql += "               '' as matr_buy_dept,                                           ";
								sql += "               '' as matr_buy_dept_name,                                      ";
								sql += "               '' as matr_audit_dept,                                         ";
								sql += "               '' as matr_buy_audit_name,                                     ";
								sql += "               '' as finc_dept_s,                                             ";
								sql += "               '' as finc_dept_s_name,                                        ";
								sql += "               '' as finc_dept_2,                                             ";
								sql += "               '' as finc_dept_2_name,                                        ";
								sql += "               '' as finc_dept_1,                                             ";
								sql += "               '' as finc_dept_1_name,                                        ";
								sql += "               '' as DECOMPOSE_ORG,                                           ";
								sql += "               TFN.org_code,                                                  ";
								sql += "               TFN.org_name AS ORG_NAME,                                      ";
								sql += "               '' as DECOMPOSE_ORG_name                                       ";
								sql += "                                                                              ";
								sql += "          FROM (select distinct org1_code, org_Code, org_name                 ";
								sql += "                  from TB_FNDWRR                                              ";
								sql += "                 where ORG1_CODE ='"+org21Code+"') TFN,     ";
								sql += "               (SELECT tmc.matr_code,                                         ";
								sql += "                       tmc.mont_code,                                         ";
								sql += "                       tmn.mont_name,                                         ";
								sql += "                       tmt.matr_name,                                         ";
								sql += "                       TMN.ORG21_CODE,                                        ";
								sql += "                       TMN.MONT_TYPE                                          ";
								sql += "                  FROM TB_MONT_MATR_CONTRA TMC                                ";
								sql += "                  LEFT JOIN TB_MONT_NAME TMN                                  ";
								sql += "                    ON TMC.MONT_CODE = TMN.MONT_CODE                          ";
								sql += "                  LEFT JOIN TB_MATR_TYPE TMT                                  ";
								sql += "                    ON TMT.MATR_CODE = TMC.MATR_CODE                          ";
								sql += "                 WHERE TMN.DATA_YEAR ='"+dataYear+"'"  ;    
								sql += "                   AND TMN.ORG21_CODE = '"+org21Code+"'"  ;   
								sql += "                   AND TMN.MONT_TYPE = '"+subType+"'"  ;   
								sql += "                   AND TMC.IS_VALID = '1'                                     ";
								sql += "                   AND NOT EXISTS                                             ";
								sql += "                 (SELECT 1                                                    ";
								sql += "                          FROM TB_APRV_CHAIN_NOSPEC TAC                       ";
								sql += "                         WHERE TAC.MONT_CODE = TMC.MONT_CODE                  ";
								sql += "                           AND TAC.MATR_CODE = TMC.MATR_CODE)) FOJ            ";
								sql += "                                                                              ";
								sql += "        ) UT                                                                  ";
								sql += " ORDER BY NVL(UT.FEE_CODE, UT.ORG_CODE), UT.MONT_CODE, UT.MATR_CODE ASC       ";

							}else {
								sql += "select *                                                                     ";
								sql += "  from (select a.FEE_CODE,                                                   ";
								sql += "               d0.duty_name      as FEE_NAME,                                ";
								sql += "               tf.mont_code,                                                 ";
								sql += "               tf.mont_name,                                                 ";
								sql += "               a.matr_code,                                                  ";
								sql += "               c.matr_name,                                                  ";
								sql += "               a.matr_buy_dept,                                              ";
								sql += "               d1.duty_name      as matr_buy_dept_name,                      ";
								sql += "               a.matr_audit_dept,                                            ";
								sql += "               d2.duty_name      as matr_buy_audit_name,                     ";
								sql += "               a.finc_dept_s,                                                ";
								sql += "               d3.duty_name      as finc_dept_s_name,                        ";
								sql += "               a.finc_dept_2,                                                ";
								sql += "               d4.duty_name      as finc_dept_2_name,                        ";
								sql += "               a.finc_dept_1,                                                ";
								sql += "               d5.duty_name      as finc_dept_1_name,                        ";
								sql += "               A.DECOMPOSE_ORG,                                              ";
								sql += "               a.org_code,                                                   ";
								sql += "               D7.org_name       AS ORG_NAME,                                ";
								sql += "               d6.duty_name      as DECOMPOSE_ORG_name                       ";
								sql += "          from "+bean.getAprvChainNoSpecTable()+" a                                     ";
								sql += "          left join tb_mont_name tf                                          ";
								sql += "            on tf.mont_code = a.mont_code                                    ";
								sql += "          left join tb_matr_type c                                           ";
								sql += "            on a.matr_code = c.matr_code                                     ";
								sql += "          left join tb_fndwrr d0                                             ";
								sql += "            on d0.duty_code = a.FEE_CODE                                     ";
								sql += "          left join tb_fndwrr d1                                             ";
								sql += "            on d1.duty_code = a.matr_buy_dept                                ";
								sql += "          left join tb_fndwrr d2                                             ";
								sql += "            on d2.duty_code = a.matr_audit_dept                              ";
								sql += "          left join tb_fndwrr d3                                             ";
								sql += "            on d3.duty_code = a.finc_dept_s                                  ";
								sql += "          left join tb_fndwrr d4                                             ";
								sql += "            on d4.duty_code = a.finc_dept_2                                  ";
								sql += "          left join tb_fndwrr d5                                             ";
								sql += "            on d5.duty_code = a.finc_dept_1                                  ";
								sql += "          left join tb_fndwrr d6                                             ";
								sql += "            on d6.duty_code = a.DECOMPOSE_ORG                                ";
								sql += "          LEFT JOIN TB_FNDWRR D7                                             ";
								sql += "            ON D7.ORG_CODE = A.ORG_CODE                                      ";
								sql += "         where a.data_year = '"+dataYear+"'"  ;   
								sql += "           and a.org21_code ='"+org21Code+"'"  ;   
								sql += "           and aprv_type ='"+subType+"'"  ;   
								sql += "                                                                             ";
								sql += "        UNION                                                                ";
								sql += "        SELECT '' as FEE_CODE,                                               ";
								sql += "               '' as FEE_NAME,                                               ";
								sql += "               FOJ.mont_code,                                                ";
								sql += "               FOJ.mont_name,                                                ";
								sql += "               FOJ. matr_code as matr_code,                                  ";
								sql += "               FOJ.matr_name as matr_name,                                   ";
								sql += "               '' as matr_buy_dept,                                          ";
								sql += "               '' as matr_buy_dept_name,                                     ";
								sql += "               '' as matr_audit_dept,                                        ";
								sql += "               '' as matr_buy_audit_name,                                    ";
								sql += "               '' as finc_dept_s,                                            ";
								sql += "               '' as finc_dept_s_name,                                       ";
								sql += "               '' as finc_dept_2,                                            ";
								sql += "               '' as finc_dept_2_name,                                       ";
								sql += "               '' as finc_dept_1,                                            ";
								sql += "               '' as finc_dept_1_name,                                       ";
								sql += "               '' as DECOMPOSE_ORG,                                          ";
								sql += "               TFN.org_code,                                                 ";
								sql += "               TFN.org_name AS ORG_NAME,                                     ";
								sql += "               '' as DECOMPOSE_ORG_name                                      ";
								sql += "                                                                             ";
								sql += "          FROM (select distinct org2_code, org_Code, org_name                ";
								sql += "                  from TB_FNDWRR                                             ";
								sql += "                 where ORG2_CODE = '"+org21Code+"') TFN,    ";
								sql += "               (SELECT tmc.matr_code,                                        ";
								sql += "                       tmc.mont_code,                                        ";
								sql += "                       tmn.mont_name,                                        ";
								sql += "                       tmt.matr_name,                                        ";
								sql += "                       TMN.ORG21_CODE,                                       ";
								sql += "                       TMN.MONT_TYPE                                         ";
								sql += "                  FROM TB_MONT_MATR_CONTRA TMC                               ";
								sql += "                  LEFT JOIN TB_MONT_NAME TMN                                 ";
								sql += "                    ON TMC.MONT_CODE = TMN.MONT_CODE                         ";
								sql += "                  LEFT JOIN TB_MATR_TYPE TMT                                 ";
								sql += "                    ON TMT.MATR_CODE = TMC.MATR_CODE                         ";
								sql += "                 WHERE TMN.DATA_YEAR = '"+dataYear+"'"  ;                                          
								sql += "                   AND TMN.ORG21_CODE = '"+org21Code+"'"  ;                                          
								sql += "                   AND TMN.MONT_TYPE = '"+subType+"'"  ;   
								sql += "                   AND TMC.IS_VALID = '1'                                    ";
								sql += "                   AND NOT EXISTS                                            ";
								sql += "                 (SELECT 1                                                   ";
								sql += "                          FROM TB_APRV_CHAIN_NOSPEC TAC                      ";
								sql += "                         WHERE TAC.MONT_CODE = TMC.MONT_CODE                 ";
								sql += "                           AND TAC.MATR_CODE = TMC.MATR_CODE)) FOJ           ";
								sql += "                                                                             ";
								sql += "        ) UT                                                                 ";
								sql += " ORDER BY NVL(UT.FEE_CODE, UT.ORG_CODE), UT.MONT_CODE, UT.MATR_CODE ASC      ";

							}
						}else {
							//通过监控指标CHANGE表转换
//							aprvContent = es.getAprvNoSpecTableChange(bean);
							sql += "select distinct a.FEE_CODE,                                     ";
							sql += "                d0.duty_name      as FEE_NAME,                  ";
							sql += "                tf.mont_code,                                   ";
							sql += "                tf.mont_name,                                   ";
							sql += "                a.matr_code,                                    ";
							sql += "                c.matr_name,                                    ";
							sql += "                a.matr_buy_dept,                                ";
							sql += "                d1.duty_name      as matr_buy_dept_name,        ";
							sql += "                a.matr_audit_dept,                              ";
							sql += "                d2.duty_name      as matr_audit_dept_name,      ";
							sql += "                a.finc_dept_s,                                  ";
							sql += "                d3.duty_name      as finc_dept_s_name,          ";
							sql += "                a.finc_dept_2,                                  ";
							sql += "                d4.duty_name      as finc_dept_2_name,          ";
							sql += "                a.finc_dept_1,                                  ";
							sql += "                d5.duty_name      as finc_dept_1_name,          ";
							sql += "                A.DECOMPOSE_ORG,                                ";
							sql += "                a.org_code,                                     ";
							sql += "                D7.ORG_NAME       AS org_name,                  ";
							sql += "                d6.duty_name      as DECOMPOSE_ORG_name         ";
							sql += "  from "+bean.getAprvChainNoSpecTable()+" a                                ";
							sql += "  left join tb_mont_matr_contra_change tc                       ";
							sql += "    on tc.mont_code_old = a.mont_code                           ";
							sql += "   and tc.matr_code = a.matr_code                               ";
							sql += "  left join tb_mont_name tf                                     ";
							sql += "    on tf.mont_code = tc.mont_code_last                         ";
							sql += "  left join tb_matr_type c                                      ";
							sql += "    on a.matr_code = c.matr_code                                ";
							sql += "  left join tb_fndwrr d0                                        ";
							sql += "    on d0.duty_code = a.FEE_CODE                                ";
							sql += "  left join tb_fndwrr d1                                        ";
							sql += "    on d1.duty_code = a.matr_buy_dept                           ";
							sql += "  left join tb_fndwrr d2                                        ";
							sql += "    on d2.duty_code = a.matr_audit_dept                         ";
							sql += "  left join tb_fndwrr d3                                        ";
							sql += "    on d3.duty_code = a.finc_dept_s                             ";
							sql += "  left join tb_fndwrr d4                                        ";
							sql += "    on d4.duty_code = a.finc_dept_2                             ";
							sql += "  left join tb_fndwrr d5                                        ";
							sql += "    on d5.duty_code = a.finc_dept_1                             ";
							sql += "  left join tb_fndwrr d6                                        ";
							sql += "    on d6.duty_code = a.DECOMPOSE_ORG                           ";
							sql += "  LEFT JOIN TB_FNDWRR D7                                        ";
							sql += "    ON D7.ORG_CODE = A.ORG_CODE                                 ";
							sql += " where a.data_year = '"+dataYear+"'"  ;  
							sql += "   and a.org1_code = '"+org21Code+"'"  ;  
							sql += "   and aprv_type = '"+subType+"'"  ;  

						}
					}
				}
				
				
				
//				beansMap.put("0", aprvContent);
			}
			exportPageToolService.execute(sql,excelExporter);
			exportSucFlag = true;
			exportMemo = "处理成功，可下载";
			//增加一条汇总数据
		} catch (Exception e) {
			exportMemo = "【处理失败】， ：" ;
			exportSucFlag = false;
			CommonLogger.error("数据导出时往excel里插入数据失败" + e.getMessage()
					+ "ExportExcel,getSimpleExcelData");
			e.printStackTrace();
		}
//		try {
//			// 可按查找数据量进行分段加载
//			ExcelExportUtility.loadExcelData(beansMap, excelExporter);
//		} catch (Exception e) {
//			exportSucFlag = false;
//			exportMemo = "【处理失败】，";
//			e.printStackTrace();
//		} 
		finally {
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
		excelDealService.updateExcelResult(bean);
	}

	/**
	 * 公共方法：批量更改Excel的头部标题 目的：根据配置文件的键值对更改Excel的标题
	 * 
	 * @param headStr
	 * @param updateInfoList
	 */
	public static void addUpdateExcelHeader(Map<String, String> headStr,
			List<String[]> updateInfoList) {
		for (int i = 0; i < updateInfoList.size(); i++) {
			headStr.put(updateInfoList.get(i)[0], updateInfoList.get(i)[1]);
		}
	}

	@Override
	public void getJxlExcelData(String taskId, Map params,
			JxlsExcelExporter jxlExporter) throws Exception {
		// TODO Auto-generated method stub

	}

}
