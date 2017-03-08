package com.forms.dealexceldata.exceldealtool;

/**
 * 处理excel的常量
 * 
 * @author user
 * 
 */
public class ExcelDealValues {
	// 审批链与监控指标---导入任务状态 00-待处理 01-处理中 02-处理失败 03-处理完成
	public final static String EXCEL_IMPORT_FORDEAL = "00";
	public final static String EXCEL_IMPORT_DEALING = "01";
	public final static String EXCEL_IMPORT_FAIL = "02";
	public final static String EXCEL_IMPORT_SUCC = "03";
	// 预算----01导入中 02导入失败 03 待提交 04 提交中 05 提交成功 06 提交失败
	public final static String BGT_IMPORT_DEALING = "01";
	public final static String BGT_IMPORT_FAIL = "02";
	public final static String BGT_IMPORT_FORSUBDEAL = "03";
	public final static String BGT_IMPORT_FORSUBDEALING = "04";
	public final static String BGT_IMPORT_SUBSUCC = "05";
	public final static String BGT_IMPORT_SUBFAIL = "06";
	// 配置中的类型 01监控指标 02审批链 03 代表预算
	public final static String TYPE_MON = "01";
	public final static String TYPE_APRV = "02";
	public final static String TYPE_BGT = "03";
	public final static String USER_ROLE_RLN = "04";

	// 审批链或监控 指标汇总的状态
	public final static String EXCEL_STATUS_DEALING = "E0";
	public final static String EXCEL_STATUS_FAIL = "E1";
	public final static String EXCEL_STATUS_SUCC = "E2";
	
	public final static String LINE_SEPARATOR = "\n"; 

}
