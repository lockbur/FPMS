package com.forms.prms.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ImportDataType {
	
	//省行
	public static String shTag="01";
	
	private static String shMontExport="0811010104";
	private static String shAprvExport="0811010105";
	private static String shBudgetExport="0811010106";
	
	private static String shMontImport="0811020109";
	private static String shAprvImport="0811020110";
	private static String shBudgetImport="0811020111";
	
	private static String shMontAprv="0811030104";
	private static String shAprvAprv="0811030105";
	private static String shBudgetAprv="0811030106";
	//分行
	public static String fhTag="02";
	private static String fhMontExport="0811010204";
	private static String fhAprvExport="0811010205";
	private static String fhBudgetExport="0811010206";
	
	private static String fhMontImport="0811020209";
	private static String fhAprvImport="0811020210";
	private static String fhBudgetImport="0811020211";
	
	private static String fhMontAprv="0811030204";
	private static String fhAprvAprv="0811030205";
	private static String fhBudgetAprv="0811030206";
	
	public static List<String> shAuthList(String type){
		List<String> list = new ArrayList<String>();
		if ("export".equals(type)) {
			list.add(shMontExport);
			list.add(shAprvExport);
			list.add(shBudgetExport);
		}else if ("import".equals(type)) {
			list.add(shMontImport);
			list.add(shAprvImport);
			list.add(shBudgetImport);
		}else if ("aprv".equals(type)) {
			list.add(shMontAprv);
			list.add(shAprvAprv);
			list.add(shBudgetAprv);
		}
		
		return list;
	}
	public static List<String> fhAuthList(String type){
		List<String> list = new ArrayList<String>();
		if ("export".equals(type)) {
			list.add(fhMontExport);
			list.add(fhAprvExport);
			list.add(fhBudgetExport);
		}else if ("import".equals(type)) {
			list.add(fhMontImport);
			list.add(fhAprvImport);
			list.add(fhBudgetImport);
		}else if ("aprv".equals(type)) {
			list.add(fhMontAprv);
			list.add(fhAprvAprv);
			list.add(fhBudgetAprv);
		}
		
		return list;
	}
	/**
	 * 根据权限组装下拉框选项
	 * @param authList
	 * @param tag 
	 * @return
	 */
//	public static List<Map<String, Object>> getSelectList(List<Map<String, Object>> authList, String tag,String type) {
//		CommonLogger.info("根据导出的权限来组装下拉框的选项,ImportDataType,getSelectList");
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		if (null != authList && authList.size()>0) {
//			if (tag.equals(ImportDataType.shTag)) {
//				for (int i = 0; i < authList.size(); i++) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					String montFuncId = "",aprvFuncId="",budgetFuncId="";
//					if ("import".equals(type)) {
//						montFuncId = shMontImport;
//						aprvFuncId = shAprvImport;
//						budgetFuncId = shBudgetImport;
//					}else if ("export".equals(type)) {
//						montFuncId = shMontExport;
//						aprvFuncId = shAprvExport;
//						budgetFuncId = shBudgetExport;
//						
//					}else if ("aprv".equals(type)) {
//						montFuncId = shMontAprv;
//						aprvFuncId = shAprvAprv;
//						budgetFuncId = shBudgetAprv;
//					}
//					if (authList.get(i).get("funcId").equals(montFuncId)) {
//						map.put("desc", "监控指标");
//						map.put("value", "01");
//					}
//					if (authList.get(i).get("funcId").equals(aprvFuncId)) {
//						map.put("desc", "审批链");
//						map.put("value", "02");
//					}
//					if (authList.get(i).get("funcId").equals(budgetFuncId)) {
//						map.put("desc", "预算");
//						map.put("value", "03");
//					}
//					list.add(map);
//				}
//			}else {
//				for (int i = 0; i < authList.size(); i++) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					String montFuncId = "",aprvFuncId="",budgetFuncId="";
//					if ("import".equals(type)) {
//						montFuncId = fhMontImport;
//						aprvFuncId = fhAprvImport;
//						budgetFuncId = fhBudgetImport;
//					}else if ("export".equals(type)) {
//						montFuncId = fhMontExport;
//						aprvFuncId = fhAprvExport;
//						budgetFuncId = fhBudgetExport;
//						
//					}else if ("aprv".equals(type)) {
//						montFuncId = fhMontAprv;
//						aprvFuncId = fhAprvAprv;
//						budgetFuncId = fhBudgetAprv;
//					}
//					if (authList.get(i).get("funcId").equals(montFuncId)) {
//						map.put("desc", "监控指标");
//						map.put("value", "01");
//					}
//					if (authList.get(i).get("funcId").equals(aprvFuncId)) {
//						map.put("desc", "审批链");
//						map.put("value", "02");
//					}
//					if (authList.get(i).get("funcId").equals(budgetFuncId)) {
//						map.put("desc", "预算");
//						map.put("value", "03");
//					}
//					list.add(map);
//				}
//			}
//			
//		}
//		return list;
//	}
	public static List<Map<String, Object>> getSelectList( ) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("desc", "监控指标");
		map1.put("value", "01");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("desc", "审批链");
		map2.put("value", "02");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list.add(map2);
		list.add(map1);
		return list;
	}
	

}
