package com.forms.prms.web.budget.bgtImport.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.dealexceldata.exceldealtool.service.ExcelDealService;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.budget.bgtImport.domain.BudgetImportBean;
import com.forms.prms.web.budget.bgtImport.service.BudgetImportService;
import com.forms.prms.web.budget.budgetInput.domain.BudgetInputBean;
import com.forms.prms.web.budget.budgetInspect.domain.BudgetManageBean;
import com.forms.prms.web.budget.budgetInspect.service.BudgetInspectService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/budget/bgtImport")
public class BudgetImportController {
	private static final String BASE_URL = "budget/bgtImport/";
	@Autowired 
	private BudgetImportService service;
	@Autowired
	private DBFileOperUtil dBFileOperUtil;
	@Autowired
	private FmsMgrService fmsService;
	@Autowired
	private ExcelDealService excelDealService;
	@Autowired
	BudgetInspectService bugetService;
	
	/**
	 * 预算导入的列表页面
	 * @param bean
	 * @return
	 */
	@RequestMapping("/shList.do")
	@AddReturnLink(id = "shList", displayValue = "导入列表查询")
	public String shList(BudgetImportBean bean){
		String orgType="1";//1-代表省行 2-代表分行
		bean.setOrgType(orgType);
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		List<BudgetImportBean> list = service.getImportList(bean);
		WebUtils.setRequestAttr("selectList", list);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("dataYear", Tool.DATE.getDateStrNO().substring(0,4));
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL+"list";
	}
	@RequestMapping("/fhList.do")
	@AddReturnLink(id = "fhList", displayValue = "导入列表查询")
	public String fhList(BudgetImportBean bean){
		String orgType="2";//1-代表省行 2-代表分行
		bean.setOrgType(orgType);
		bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		List<BudgetImportBean> list = service.getImportList(bean);
		WebUtils.setRequestAttr("selectList", list);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("dataYear", Tool.DATE.getDateStrNO().substring(0,4));
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		return BASE_URL+"list";
	}
	/**
	 * 进入导入页面
	 * @param bean
	 * @return
	 */
	@RequestMapping("/importPage.do")
	public String importPage(BudgetImportBean bean){
		WebUtils.setRequestAttr("bean", bean);
		if ("1".equals(bean.getOrgType())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/bgtImport/shList.do?VISIT_FUNC_ID=020901");
		}else {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/bgtImport/fhList.do?VISIT_FUNC_ID=020902");
		}
		return BASE_URL+"import";
	}
	/**
	 * 导入前校验是否可以导入
	 * 1：是否有同类型的监控指标在导入，
	 * 2：初次导入只能导一次，
	 * 3: 正式预算必须先导入初始预算后才能追加
	 * 2：下达临时预算时校验正式预算下达了如果是（导入中，校验中，提交中，提交成功 ）就不能到临时预算，如果是（导入失败，校验失败）则可以直接重新导入，如果是（校验成功还未提交，提交失败）则必须删除数据再导入
	 * 3：下达预算的时候校验有没有还未完成的预算，必须完成后才能再次导入
	 * @param bgtStbean
	 * @return
	 */
	@RequestMapping("importPageAjax.do")		//020808
	@ResponseBody
	public String importPageAjax(BudgetImportBean bean){
		if ("1".equals(bean.getOrgType())) {
			//省行
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			//分行
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		AbstractJsonObject jsonObj = new SuccessJsonObject();
//		bean.setBgtType(bean.getOperType()+bean.getOperSubType());
		Map<String ,Object> map = service.importPageAjax(bean) ;
		jsonObj.put("data", map);
		return jsonObj.writeValueAsString();
	}
	/*8
	 * 导入提交
	 */
	@RequestMapping("bgtImport.do")
	public String bgtImport(BudgetImportBean bean){
		try {
			service.bgtImport(bean);
			WebUtils.getMessageManager().addInfoMessage("导入excel已经新增，待校验通过后请点击提交。");
			if ("1".equals(bean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString("导入",true,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString("导入",true,
						new String[] {"fhList"});
			}
		} catch (Exception e) {
			if ("1".equals(bean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString("导入",false,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString("导入",false,
						new String[] {"fhList"});
			}
		}
	}
	/**
	 * 删除
	 * @param bean
	 * @return
	 */
	@RequestMapping("del.do")
	public String del(BudgetImportBean bean){
		try {
			service.del(bean);
			if ("1".equals(bean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString("删除",true,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString("删除",true,
						new String[] {"fhList"});
			}
		} catch (Exception e) {
			if ("1".equals(bean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString("删除",false,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString("删除",false,
						new String[] {"fhList"});
			}
		}
	}
	/**
	 * 详情
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(BudgetImportBean bean){
		BudgetImportBean bean2 = service.getTotalInfo(bean);
		//查询log信息
		CommonExcelDealBean iBean = excelDealService.queryTaskByBatchNo(bean.getBatchNo());
		//从任务表中获取日志文件的路径,路径+configId
		
		String logFilePath = "";
	    String badFilePath = "";
	    if (!Tool.CHECK.isEmpty(iBean)) {
	    	  logFilePath = iBean.getSourceFpath()+iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".log";
		      badFilePath = iBean.getSourceFpath()+iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".bad";
		}
	    if(new File(logFilePath).exists()){
	    	WebUtils.setRequestAttr("logFilePath", logFilePath);
	    	WebUtils.setRequestAttr("logFile", iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".log");
	    }
	    if(new File(badFilePath).exists()){
	    	WebUtils.setRequestAttr("badFilePath", badFilePath);
	    	WebUtils.setRequestAttr("badFile", iBean.getConfigId()+"_"+iBean.getTaskBatchNo()+".bad");
	    }
		//查看错误信息
		List<BudgetImportBean> errorList = service.getErrorList(bean);
		WebUtils.setRequestAttr("bean", bean2);
		WebUtils.setRequestAttr("errorList", errorList);
		if ("1".equals(bean.getOrgType())) {
			//省行
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/bgtImport/shList.do?VISIT_FUNC_ID=020901");
		}else {
			//省行
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/budget/bgtImport/fhList.do?VISIT_FUNC_ID=020902");
		}
		return BASE_URL+"detail";
	}
	/**
	 * 导出数据
	 * @param eb
	 * @return
	 */
	@RequestMapping("/export.do")
	@ResponseBody
	public String export(BudgetImportBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
//		Excel导出操作
		try {
			//Service层调用Excel组件进行Excel导出功能
			service.dataExport(bean);
			jsonObject.put("pass", true);
		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 导入数据模板下载
	 * @param eb
	 * @return
	 */
	@RequestMapping("/downloadTemp.do")
	@ResponseBody
	public String downloadTemp(BudgetImportBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();		
//		Excel导出操作
		try {
			//Service层调用Excel组件进行Excel导出功能
			service.downloadTemp(bean);
			jsonObject.put("pass", true);
		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 预算提交
	 * @param bean
	 * @return
	 */
	@RequestMapping("/bgtSubmit.do")
	public String bgtSubmit(BudgetImportBean bean){
		try {
			WebUtils.getMessageManager().addInfoMessage("提交请求已发送，回到列表等待提交结果");
			service.bgtSubmit(bean);
			if ("11".equals(bean.getSubType()) || "12".equals(bean.getSubType())) {
				return ForwardPageUtils.getReturnUrlString("提交请求发送",true,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString("提交请求发送",true,
						new String[] {"fhList"});
			}
		} catch (Exception e) {
			if ("11".equals(bean.getSubType()) || "12".equals(bean.getSubType())) {
				return ForwardPageUtils.getReturnUrlString("提交请求发送",false,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString("提交请求发送",false,
						new String[] {"fhList"});
			}
		}
	}
	@RequestMapping("checkDownFileExist.do")
	@ResponseBody
	public String checkDownFileExist(String batchNo) {
		AbstractJsonObject json = new SuccessJsonObject();
		boolean checkFlag = true; // 校验结果标识
		String checkMsg = ""; // 校验结果MSG(当校验结果为false时，该对象才会有值)
		BudgetImportBean bean = service.getPath(batchNo);
		File downFile = new File(bean.getFilePath());
		if ((!downFile.exists()) || !(downFile.isFile())) {
			// 不存在文件 将数据库中的文件下载到服务器中
			checkMsg = "当前服务器不存在这个文件";
			checkFlag = false;
		} 
		json.put("batchNo", batchNo); // 用于Ajax检测后的下载操作中参数传输
		json.put("checkFlag", checkFlag);
		json.put("checkMsg", checkMsg);
		return json.writeValueAsString();

	}
	@RequestMapping("/sorceFileDownload.do")
	public String upFileDownload(HttpServletRequest request,
			HttpServletResponse response, String batchNo) {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		BudgetImportBean bean = service.getPath(batchNo);
		String fileName = bean.getSourceFilename();
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
			fmsService.downloadFile(request, response, bean.getFilePath());
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			// 忽略浏览器客户端刷新关闭的异常的信息打印
			if (!"ClientAbortException".equals(simplename)) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
