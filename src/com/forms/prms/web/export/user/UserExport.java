package com.forms.prms.web.export.user;

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
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.sysmanagement.user.domain.UserInfo;
import com.forms.prms.web.sysmanagement.user.service.UserInfoService;

public class UserExport implements IExportDataDeal{

	UserInfoService es = UserInfoService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance(); // 用于更新导出任务结果状态和导出备注

	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";

		Map<String, Object> beansMap = new HashMap<String, Object>();
		// 获取方法调用的接收参数
		String userId = (String) params.get("userId");
		String userName = (String) params.get("userName");
		String ouCode = (String) params.get("ouCode");
		String dutyCode = (String) params.get("dutyCode");
 		String dutyName = (String) params.get("dutyName");
 		String orgCode = (String) params.get("orgCode");
 		String orgName = (String) params.get("orgName");
 		String isSuperAdmin = (String) params.get("isSuperAdmin");
 		String isBuyer = (String) params.get("isBuyer");
 		String roleId = (String) params.get("roleId");
 		String haveRole = (String) params.get("haveRole");
 		String org21Code = (String) params.get("org21Code");
 		String isSuper = (String) params.get("isSuper");
 		String org1Code = (String) params.get("org1Code");
 		
		UserInfo bean = new UserInfo();
		bean.setUserId(userId);
		bean.setUserName(userName);
		bean.setOuCode(ouCode);
		bean.setDutyCode(dutyCode);
		bean.setDutyName(dutyName);
		bean.setOrgCode(orgCode);
		bean.setOrgName(orgName);
		bean.setIsSuperAdmin(isSuperAdmin);
		bean.setIsBuyer(isBuyer);
		bean.setRoleId(roleId);
		bean.setHaveRole(haveRole);
		bean.setOrg21Code(org21Code);
		bean.setIsSuper(isSuper);
		bean.setOrg1Code(org1Code);
		// //插入excel数据内容
		try {
			List<UserInfo> userContent = new ArrayList<UserInfo>();
			userContent = es.userExportExcute(bean);
			beansMap.put("0", userContent);
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
		try {
			// 可按查找数据量进行分段加载
			ExcelExportUtility.loadExcelData(beansMap, excelExporter);
		} catch (Exception e) {
			exportSucFlag = false;
			exportMemo = "【处理失败】，";
			e.printStackTrace();
		} finally {
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
