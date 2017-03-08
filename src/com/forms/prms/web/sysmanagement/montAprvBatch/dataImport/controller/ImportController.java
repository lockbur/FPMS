package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.dealexceldata.exceldealtool.service.ExcelDealService;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.FuncOrgType;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.ImportBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/montAprvBatch/import")
public class ImportController {
	private static final String basePath="sysmanagement/montAprvBatch/import/";
	
	@Autowired
	private ImportService service;
	@Autowired
	private ExcelDealService excelDealService;
	
	
	
	/**
	 * 省行 08110101
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/shList.do")
	public String shList(ImportBean eb){
		eb.setOrgType("01");
//		List<Map<String, Object>> authList = service.getAuthList(WebHelp.getLoginUser().getRoleId(), ImportDataType.shAuthList("import"));
//		List<Map<String, Object>> list = ImportDataType.getSelectList(authList,ImportDataType.shTag,"import");
//		WebUtils.setRequestAttr("selectList", list);
		List<ImportBean> eblist=service.shList(eb);
		WebUtils.setRequestAttr("list",eblist);
		WebUtils.setRequestAttr("searchInfo", eb);
		WebUtils.setRequestAttr("orgType", "01");
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		ReturnLinkUtils.addReturnLink("shList", FuncOrgType.ORG1_TYPE+"数据导入列表查询");
		return basePath+"list";
	}
	
	/**
	 * 分行 08110102
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/fhList.do")
	public String fhList(ImportBean eb){
		eb.setOrgType("02");
//		List<Map<String, Object>> authList = service.getAuthList(WebHelp.getLoginUser().getRoleId(), ImportDataType.shAuthList("import"));
//		List<Map<String, Object>> list = ImportDataType.getSelectList(authList,ImportDataType.shTag,"import");
//		WebUtils.setRequestAttr("selectList", list);
		List<ImportBean> eblist=service.fhList(eb);
		WebUtils.setRequestAttr("list",eblist);
		WebUtils.setRequestAttr("searchInfo", eb);
		WebUtils.setRequestAttr("orgType", "02");
		WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
		ReturnLinkUtils.addReturnLink("fhList", FuncOrgType.ORG2_TYPE+"数据导入列表查询");
		return basePath+"list";
	}
	/**
	 * 新增页面
	 */
	@RequestMapping("/preAdd.do")
	public String shAdd(ImportBean bean){
		if ("01".equals(bean.getOrgType())) {
			 WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/import/shList.do?VISIT_FUNC_ID=08110201");
		}else {
			 WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/import/fhList.do?VISIT_FUNC_ID=08110202");
		}
		
		
		WebUtils.setRequestAttr("bean", bean);
		String dataYear = Tool.DATE.getDateStrNO().substring(0,4);
		WebUtils.setRequestAttr("dataYear", dataYear);
		return basePath+"add";
	}
	
	/**
	 * 导入校验是否已经存在
	 * @param bean
	 * @return
	 */
	@RequestMapping("/ajaxDataExist.do")
	@ResponseBody
	public String ajaxDataExist(ImportBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Map<String, Object> map = service.ajaxDataExist(bean);
		jsonObject.put("resultValue", map);
		return jsonObject.writeValueAsString();
	}
	@RequestMapping("/add.do")
	public String add(ImportBean bean){
		try {
			service.addExcel(bean);
			if ("03".equals(bean.getInstType())) {
				//copy数据
				WebUtils.getMessageManager().addInfoMessage("确认无误后请点击提交，等待审核通过。");
			}else {
				WebUtils.getMessageManager().addInfoMessage("导入excel已经新增，模板Excel导入线程进入排队！待校验通过后请点击提交，等待审核通过");
			}
			if ("01".equals(bean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString(FuncOrgType.ORG1_TYPE+"导入列表",true,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString(FuncOrgType.ORG2_TYPE+"导入列表",true,
						new String[] {"fhList"});
			}
		} catch (Exception e) {
			e.printStackTrace();
			if ("01".equals(bean.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString(FuncOrgType.ORG1_TYPE+"导入列表",false,
						new String[] { "shList"});
			}else {
				return ForwardPageUtils.getReturnUrlString(FuncOrgType.ORG2_TYPE+"导入列表",false,
						new String[] {"fhList"});
			}
			
		}
		
	}
	
	/**
	 * 删除复核未通过数据 08110103
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/delete.do")
	public String delete(ImportBean eb){
		eb.setBatchNo(WebUtils.getParameter("batchNo"));
		eb.setProType(WebUtils.getParameter("proType"));
		service.delete(eb);
		if ("01".equals(eb.getOrgType())) {
			return ForwardPageUtils.getReturnUrlString("数据删除",true,
					new String[] { "shList"});
		}else {
			return ForwardPageUtils.getReturnUrlString("数据删除",true,
					new String[] {"fhList"});
		}
	}
	
	/**
	 * 提交校验成功数据 08110104
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	@RequestMapping("/submit.do")
	public String submit(ImportBean eb){
		eb.setBatchNo(WebUtils.getParameter("batchNo"));
		try {
			service.submit(eb);
			if ("01".equals(eb.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString("数据提交",true,
						new String[] { "shList"});
			}else{
				return ForwardPageUtils.getReturnUrlString("数据提交",true,
						new String[] { "fhList"});
			}
		} catch (Exception e) {
			CommonLogger.error("数据导入导出提交失败,批次号是:"+eb.getBatchNo());
			e.printStackTrace();
			if ("01".equals(eb.getOrgType())) {
				return ForwardPageUtils.getReturnUrlString("数据提交",false,
						new String[] { "shList"});
			}else{
				return ForwardPageUtils.getReturnUrlString("数据提交",false,
						new String[] { "fhList"});
			}
		}
		
		
	}
	
	/**
	 * 导出校验失败数据 08110105
	 * 
	 * 
	 * @param eb
	 * @return
	 */
//	@RequestMapping("/download.do")
//	@ResponseBody
//	public String download(ExportBean eb) {
//		AbstractJsonObject jsonObject = new SuccessJsonObject();		
////		Excel导出操作
//		try {if (service.downLoad(eb)==null) {
//			jsonObject.put("pass", false);
//		} else {
//			jsonObject.put("pass", true);
//		}		
//
//		} catch (Exception e) {
//			jsonObject.put("pass", false);
//			e.printStackTrace();
//		}
//		return jsonObject.writeValueAsString();
//	}
	/**
	 * 省行详情 08110203
	 * 
	 * 
	 * @param eb
	 * @return
	 */
	 @RequestMapping("/getDetail.do")
	 public String getDetail(ImportBean bean){
		 ImportBean eb=service.getDetail(bean);
		 List<ImportBean> err=service.getErrData(eb);
		 if (null != eb && !"03".equals(eb.getInstType())) {
			
				//查询log信息
				CommonExcelDealBean iBean= excelDealService.queryTaskByBatchNo(bean.getBatchNo());
				String logFilePath = "";
				String badFilePath = "";
				if (!Tool.CHECK.isEmpty(iBean)) {
					//从任务表中获取日志文件的路径,路径+configId
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
				 if ("01".equals(bean.getOrgType())) {
					 WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/import/shList.do?VISIT_FUNC_ID=08110201");
				}else {
					 WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/montAprvBatch/import/fhList.do?VISIT_FUNC_ID=08110202");
				}
				
				
		}
		 WebUtils.setRequestAttr("bean", eb);
		 WebUtils.setRequestAttr("err",err);
		 return basePath+"detail";
	 }
	  /**
	   * copy数据的校验                                                                                                                                                                      
	   * @param bean
	   * @return
	   */
	@RequestMapping("/ajaxCopyExists.do")
	@ResponseBody
	public String ajaxCopyExists(ImportBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();	
		Map<String, Object> map = service.ajaxCopyExists(bean);
		jsonObject.put("map", map);
		return jsonObject.writeValueAsString();
	}
	

}
