package com.forms.prms.web.reportmgr.importAndexport.query.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.tool.fileUtils.service.DBFileOperUtil;
import com.forms.prms.web.reportmgr.importAndexport.query.domain.ImporExporCommonBean;
import com.forms.prms.web.reportmgr.importAndexport.query.service.QueryService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/reportmgr/importAndexport/query/")
public class QueryController {

	private static final String BASE_PATH = "reportmgr/importAndexport/query/";
	@Autowired
	private QueryService queryService;
	@Autowired
	private CommonExcelDealService excelDealService;
	@Autowired
	private DBFileOperUtil DbFileUtil;
//	CommonExcelDealService excelDealService = CommonExcelDealService.getInstance();
	
	/**
	 * @methodName getExportReportList
	 * 		描述：根据条件参数查询导入/导出任务的列表  
	 * @param imExCommonBean
	 * @param batchId			[导入任务使用]如果该输入参数不为空，则根据批次号查找该批次下的所有Task导出任务(该参数会保存在任务Bean.taskParam中，例如存量业务数据中，根据批次号查找批次下的合同导入数据+付款导入数据)
	 * @param queryByOrg1		[导入任务使用]如果该输入参数不为空，则根据登录者的一级行查询导入任务(较高权限的一级管理员)，否则只查询当前登录者的导入任务
	 * @param lvl1ReturnLink	[公共处理使用]用于保存跳转至该页面的前一个页面的URL路径
	 * @return
	 */
	@AddReturnLink(id="toImExTaskList" , displayValue="跳转至导入导出任务列表")
	@RequestMapping("getExportReportList.do")
	public String getExportReportList(ImporExporCommonBean imExCommonBean , String batchId , String queryByOrg1 , String lvl1ReturnLink)
	{
		//1.[公共处理]进入此页面的上一个页面返回链接(如果传入参数包含upStepUrl属性值，则根据该url查询出前一页面的完整urlLink路径)
		String lastPageLink = "";
		if(null != imExCommonBean.getUpStepUrl() && "" != imExCommonBean.getUpStepUrl()){
			lastPageLink = queryService.getLastPageUri(imExCommonBean.getUpStepUrl() , imExCommonBean.getFuncId() , imExCommonBean.getUpStepParams());
		}
		//2.设置查询的默认条件参数
		imExCommonBean.setInstOper( WebHelp.getLoginUser().getUserId() );			//默认根据登录者查询其的导入导出任务(受方法参数queryByOrg1影响)
		if("".equals(imExCommonBean.getImOutportType()) || null == imExCommonBean.getImOutportType()){
			//如果不指定查询任务的类型(导入/导出)，则设置查询默认值为"1":查询导出任务数据(即用户直接进入[报表管理]模块，点击[导入导出]中的[查询]按钮)
			imExCommonBean.setImOutportType("1");
		}
		//3.根据imOutPortType的值调用Service层方法查询导入/导出的任务数据
		if("0".equals(imExCommonBean.getImOutportType())){
			//查询导入任务
			WebUtils.setRequestAttr("imExCommonBean", imExCommonBean);
			WebUtils.setRequestAttr("getImporExporTaskList", queryService.getImportReportList(imExCommonBean , batchId , queryByOrg1));
		}else if("1".equals(imExCommonBean.getImOutportType())){
			//查询导出任务
			WebUtils.setRequestAttr("imExCommonBean", imExCommonBean);
			WebUtils.setRequestAttr("getImporExporTaskList", queryService.getExportReportList(imExCommonBean));
		}
		//4.设置返回至查询结果页面前端需要使用的参数
		if(!"".equals(batchId)&& null != batchId){
			//如果传入参数batchId不为空，则将该参数传递到前端JSP页面(前端需要使用该参数值)
			WebUtils.setRequestAttr("batchId", batchId);
		}
		WebUtils.setRequestAttr("lastPageLink", lastPageLink);
		WebUtils.setRequestAttr("lvl1ReturnLink", lvl1ReturnLink);
		//5.返回查询结果集页面
		return BASE_PATH + "exportReportList";
	}
	
	/**
	 * 导出任务exportTask对象文件
	 * 		逻辑描述：在导出任务结果列表中，
	 * 					1.用户点击指定task对应的"下载"进入此方法；
	 * 					2.根据TaskId查找需下载的任务对象，获取任务的导出目标地址destFile；
	 * 					3.执行文件下载方法进行下载(由服务器复制到客户端本地保存)；
	 * @param request	请求参数(包含taskId等)
	 * @param response	响应参数(执行文件下载时需使用)
	 * @return
	 */
	@RequestMapping("exportReportFileLoad.do")
	public String exportReportFileLoad(HttpServletRequest request, HttpServletResponse response){	
		String taskId = request.getParameter("taskId");
		CommonLogger.debug("[导入导出模块]：导出下载文件<"+taskId+"> In QueryController.class	===> exportReportFileLoad()");
		//1.获取需要导出的TaskBean任务对象
		ImporExporCommonBean exportTask = new ImporExporCommonBean();
		exportTask.setTaskId(taskId);
		ImporExporCommonBean bean = queryService.getExportReport(exportTask);
		//2.取得该导出任务的文件类型(文件后缀名)
		String suffix = bean.getDestFile().substring( bean.getDestFile().lastIndexOf(".") );
		//3.采用集成处理方式(先本地下载 + 后集群下载)进行导出文件的下载
		CommonLogger.debug("[报表导出模块-文件导出操作]：集成处理文件下载 - DbFileUtil.getFileDownFromPathAndDb()  START.....");
		try {
			DbFileUtil.getFileDownFromPathAndDb(bean.getDestFile() , bean.getTaskDesc() + suffix , request , response);
		} catch (Exception e1) {
			CommonLogger.error("调用DbFileUtil.getFileDownFromPathAndDb()时发生异常，出现在Class[QueryController]中Method[exportReportFileLoad]...");
			e1.printStackTrace();
			WebUtils.getMessageManager().addErrorMessage("集成下载文件[任务流水号："+taskId+"]发生异常，请检查！");
			ReturnLinkUtils.setShowLink(new String[]{"toImExTaskList"});					//快速链接：返回导出文件列表
			return ForwardPageUtils.getErrorPage();
		}
		CommonLogger.debug("[报表导出模块-文件导出操作]：集成处理文件下载 - DbFileUtil.getFileDownFromPathAndDb()  NORMAL END.....");
		return null;
	}
	
	/**
	 * @methodName getImportFileDetailInfo
	 * 			查看导入任务的详情
	 * @param importTask		:	导入任务bean(作为参数时只包含taskId属性值)
	 * @param batchId			:	批次号，用于统计导入时的校验错误信息
	 * @param lvl1ReturnLink	:	用于"返回"按钮，将进入导入列表页面的前一个页面URL作为参数传进来保存("返回"时再将该参数传回去)
	 * @return
	 */
	@RequestMapping("getImportFileDetailInfo.do")
	public String getImportFileDetailInfo(ImporExporCommonBean importTask , String batchId , String lvl1ReturnLink){
		//1.根据导入taskId查找导入任务的bean对象
		ImporExporCommonBean importTaskDetail =  queryService.getImportReport(importTask);
		CommonExcelDealBean impTaskBean = excelDealService.getLoadTaskByTaskId(importTask.getTaskId()); 
		//从任务表中获取日志文件的路径,路径+configId
		String logFilePath = impTaskBean.getSourceFpath()+impTaskBean.getConfigId()+"_"+impTaskBean.getTaskBatchNo()+".log";
	    String badFilePath = impTaskBean.getSourceFpath()+impTaskBean.getConfigId()+"_"+impTaskBean.getTaskBatchNo()+".bad";
	    if(new File(logFilePath).exists()){
	    	WebUtils.setRequestAttr("logFilePath", logFilePath);
	    	WebUtils.setRequestAttr("logFile", impTaskBean.getConfigId()+"_"+impTaskBean.getTaskBatchNo()+".log");
	    }
	    if(new File(badFilePath).exists()){
	    	WebUtils.setRequestAttr("badFilePath", badFilePath);
	    	WebUtils.setRequestAttr("badFile", impTaskBean.getConfigId()+"_"+impTaskBean.getTaskBatchNo()+".bad");
	    }
		//2.根据batchId和uploadType查询该导入任务的错误信息List(用于展示到页面上)
//		String taskParams = impTaskBean.getTaskParams();
//		JSONObject jsonObj = JSONObject.fromObject(taskParams);
//		String uploadType = (String)jsonObj.get("uploadType");
//		//批次号取值逻辑：1.传入参数如果有值，则直接取传入参数值；	2.否则取导入任务impTask的参数中的impBatch值；	3.当上述两个取值均为空时，直接取导入任务的taskId值作为批次号
//		if(Tool.CHECK.isEmpty(batchId)){
//			if(!Tool.CHECK.isEmpty(jsonObj.get("impBatch"))){
//				batchId = (String)jsonObj.get("impBatch");
//			}else{
//				batchId = importTask.getTaskId();
//			}
//		}
//		List<UploadDataErrorInfoBean> importErrMsgList = excelDealService.getImportErrMsgByBatchNoAndUploadType(batchId , uploadType);
		//3.设置页面返回信息，并跳转至导入任务详情页，展示详情明细信息
		WebUtils.setRequestAttr("batchId", batchId);
//		WebUtils.setRequestAttr("importErrMsgList", importErrMsgList);
		WebUtils.setRequestAttr("importTaskDetail", importTaskDetail);
		WebUtils.setRequestAttr("lvl1ReturnLink", lvl1ReturnLink);
		return BASE_PATH + "importFileDetail";
	}
	
	public static void main(String[] args) {
		//测试将JSon格式的字符串转换为JSON对象，并取值
		String str = "{'uploadType':'01','org1Code':'A0002','instOper':'hqq','impBatch':'DMBATCH000007'}";
		JSONObject jObj = JSONObject.fromObject(str);
		System.out.println(jObj.get("impBatch"));
	}
}
