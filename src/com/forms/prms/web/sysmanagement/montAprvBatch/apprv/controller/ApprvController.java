package com.forms.prms.web.sysmanagement.montAprvBatch.apprv.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.FuncOrgType;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.domain.ApprvBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.service.ApprvService;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/montAprvBatch/apprv")
public class ApprvController {
	@Autowired
	private ApprvService apprvService;
	@Autowired
	private static final String PREFIX = "sysmanagement/montAprvBatch/apprv/";
	@Autowired
	private FmsMgrService service;
	@Autowired
	private ImportService importService;
	@Autowired
	private DBFileOperUtil dBFileOperUtil;

	/**
	 * @methodName shList desc 省行列表查询
	 * @param apprvBean
	 * @return
	 */
	@RequestMapping("/shList.do")
	public String shList(ApprvBean apprvBean) {
//		List<Map<String, Object>> authList = importService.getAuthList(WebHelp
//				.getLoginUser().getRoleId(), ImportDataType.shAuthList("aprv"));
//		List<Map<String, Object>> proTypeList = ImportDataType.getSelectList(
//				authList, ImportDataType.shTag, "aprv");
//		WebUtils.setRequestAttr("selectList", proTypeList);
		ReturnLinkUtils.addReturnLink("shList", FuncOrgType.ORG1_TYPE+"数据复核列表查询");
		apprvBean.setOrgType("01");
		List<ApprvBean> list = apprvService.getList(apprvBean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchInfo", apprvBean);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		WebUtils.setRequestAttr("thisYear", Tool.DATE.getDateStrNO().substring(0,4));
		return PREFIX + "list";
	}

	/**
	 * @methodName fhList desc 分行列表查询
	 * @param apprvBean
	 * @return
	 */
	@RequestMapping("/fhList.do")
	public String fhList(ApprvBean apprvBean) {
//		List<Map<String, Object>> authList = importService.getAuthList(WebHelp
//				.getLoginUser().getRoleId(), ImportDataType.shAuthList("aprv"));
//		List<Map<String, Object>> proTypeList = ImportDataType.getSelectList(
//				authList, ImportDataType.shTag, "aprv");
//		WebUtils.setRequestAttr("selectList", proTypeList);
		ReturnLinkUtils.addReturnLink("fhList", FuncOrgType.ORG2_TYPE+"数据复核列表查询");
		apprvBean.setOrgType("02");
		List<ApprvBean> list = apprvService.getList(apprvBean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchInfo", apprvBean);
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		WebUtils.setRequestAttr("thisYear", Tool.DATE.getDateStrNO().substring(0,4));
		return PREFIX + "list";
	}

	/**
	 * @methodName upFileDownload desc 数据复核数据导出
	 * @param request
	 * @param response
	 * @param batchNo
	 * @return
	 */

	@RequestMapping("/Export.do")
	public String upFileDownload(HttpServletRequest request,
			HttpServletResponse response, String batchNo) {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		String path = apprvService.getPath(batchNo).getPath();
		String fileName = apprvService.getPath(batchNo).getSourceFileName();
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			service.downloadFile(request, response, path);
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			// 忽略浏览器客户端刷新关闭的异常的信息打印
			if (!"ClientAbortException".equals(simplename)) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @methodName checkDownFileExist desc 文件导出前进行校验，文件是否存在
	 * @param path
	 * @return
	 */
	@RequestMapping("checkDownFileExist.do")
	@ResponseBody
	public String checkDownFileExist(String batchNo) {
		AbstractJsonObject json = new SuccessJsonObject();
		boolean checkFlag = false; // 校验结果标识
		String checkMsg = ""; // 校验结果MSG(当校验结果为false时，该对象才会有值)
		try {
			String patn = apprvService.getPath(batchNo).getPath();
			File downFile = new File(patn);
			if ((!downFile.exists()) || !(downFile.isFile())) {
				// 不存在文件 将数据库中的文件下载到服务器中
				dBFileOperUtil.findFileFromDB(patn);
				checkMsg = "当前服务器不存在这个文件，现正在从数据库中下载到本地,请稍后再试";
				checkFlag = false;
			} else {
				checkFlag = true;
				CommonLogger.debug("【文件存在性检测】：存在   "
						+ apprvService.getPath(batchNo).getPath());
			}
		} catch (Exception e) {
			checkMsg = "需要下载的文件在当前服务器和数据库不存在，请检查！";
		}
		json.put("batchNo", batchNo); // 用于Ajax检测后的下载操作中参数传输
		json.put("checkFlag", checkFlag);
		json.put("checkMsg", checkMsg);
		return json.writeValueAsString();

	}

	/**
	 * 复核合同勾选列表查询
	 * 
	 * @param apprvBean
	 * @return
	 */
	@RequestMapping("cntChooseList.do")
	@AddReturnLink(id = "cntChooseList", displayValue = "复核合同勾选列表查询")
	public String cntChooseList(ApprvBean apprvBean) {
		WebUtils.setRequestAttr("status", apprvBean.getStatus());
		apprvBean.setStatus(MontAprvType.STATUS_1);
		List<ApprvBean> list1 = apprvService.cntChooseList(apprvBean,
				"pageKey1");// 查询待审核
		apprvBean.setStatus(MontAprvType.STATUS_2);
		List<ApprvBean> list2 = apprvService.cntChooseList(apprvBean,
				"pageKey2");// 查询退回
		apprvBean.setStatus(MontAprvType.STATUS_3);
		List<ApprvBean> list3 = apprvService.cntChooseList(apprvBean,
				"pageKey3");// 查询审核通过
		WebUtils.setRequestAttr("list1", list1);
		WebUtils.setRequestAttr("list2", list2);
		WebUtils.setRequestAttr("list3", list3);
		WebUtils.setRequestAttr("batchNo", apprvBean.getBatchNo());
		WebUtils.setRequestAttr("orgType", apprvBean.getOrgType());
		WebUtils.setRequestAttr("isDetail", apprvBean.getIsDetail());
		WebUtils.setRequestAttr("proType", apprvBean.getProType());
		if ("01".equals(apprvBean.getOrgType())) {
			WebUtils.setRequestAttr(
					"uri",
					WebUtils.getRequest().getContextPath()
							+ "/sysmanagement/montAprvBatch/apprv/shList.do?VISIT_FUNC_ID=08110301");
		} else {
			WebUtils.setRequestAttr(
					"uri",
					WebUtils.getRequest().getContextPath()
							+ "/sysmanagement/montAprvBatch/apprv/fhList.do?VISIT_FUNC_ID=08110302");
		}
		return PREFIX + "cntChooseList";
	}

	/**
	 * 复核
	 * @param apprvBean
	 * @return
	 */
	@RequestMapping("/audit.do")
	public String audit(ApprvBean apprvBean) {
		//这里在controller层给 复核操作和 调用存储过程分开调用 来保证他们不在同一个事务
		try {
			if (MontAprvType.EXCEL_E4.equals(apprvBean.getStatus())) {
				// 复核EXCEL
				apprvService.auditE4(apprvBean);
			} else if ("01".equals(apprvBean.getProType())
					&& MontAprvType.CNT_C2.equals(apprvBean.getStatus())) {
				// 复核合同勾选
				apprvService.auditC2(apprvBean);
			}
			
			// 这个是 EXCEL 和 合同都复核完了 或者是存储过程跑错了 调用存储过程
			String lastStatus = apprvService.getLastStatus(apprvBean);
			if (MontAprvType.EXCEL_E5.equals(lastStatus) || MontAprvType.CNT_C7.equals(lastStatus) || MontAprvType.CNT_C6.equals(lastStatus)) {
				// EXCEL复核通过，且 没有勾选的(监控指标) 或者 是审批链
				if (MontAprvType.CNT_C6.equals(lastStatus) && !"01".equals(apprvBean.getAuditFlag())) {
					//审核不通过
					apprvService.updateBack(apprvBean);
				}else {
					apprvService.starThread(apprvBean,lastStatus);
				}
				
			}
			
			if ("01".equals(apprvBean.getOrgType())) {
				ReturnLinkUtils.setShowLink("shList");
				WebUtils.getMessageManager().addInfoMessage(FuncOrgType.ORG1_TYPE+"复核过程已开启，请稍后查询审核结果！");
				return ForwardPageUtils.getSuccessPage();
			} else {
				ReturnLinkUtils.setShowLink("fhList");
				WebUtils.getMessageManager().addInfoMessage(FuncOrgType.ORG2_TYPE+"复核过程已开启，请稍后查询审核结果！");
				return ForwardPageUtils.getSuccessPage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if ("01".equals(apprvBean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString(FuncOrgType.ORG1_TYPE+"复核", false,
						new String[] { "shList" });
			} else {
				return ForwardPageUtils.getReturnUrlString(FuncOrgType.ORG1_TYPE+"复核", false,
						new String[] { "fhList" });
			}
		}

	}
}
