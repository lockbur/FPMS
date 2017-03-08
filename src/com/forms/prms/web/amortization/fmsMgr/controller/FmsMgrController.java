package com.forms.prms.web.amortization.fmsMgr.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.dealdata.download.DownloadService;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fms.FmsGeneral;
import com.forms.prms.tool.fms.FmsScanLock;
import com.forms.prms.tool.fms.FmsUploadLock;
import com.forms.prms.web.amortization.fmsMgr.domain.DealCountBean;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsCglBatch;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsDownload;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsUpload;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.pay.paycommon.service.PayCommonService;
import com.forms.prms.web.sysmanagement.parameter.service.ParameterService;
import com.forms.prms.web.util.ForwardPageUtils;


@Controller
@RequestMapping("/amortization/fmsMgr")
public class FmsMgrController {
	public static String FILE_NAME = "fms";
	private static final String BASE_URL = "amortization/fmsMgr/";
	@Autowired
	private FmsMgrService service;
	@Autowired
	private FmsGeneral fmsGeneral;
	@Autowired
	private PayCommonService pcService;
	@Autowired
	private MonthOverService monthOverService;
	@Autowired
	private DownloadService dwnservice;
	@Autowired
	private ParameterService pService;
	
	/**
	 * 订单付款信息查询
	 * @return
	 */
	@RequestMapping("payOrder.do")
	public String getCntInfo(){
		CommonLogger.info("应付发票、预付款核算及订单待上送信息查询！,FmsMgrController,getCntInfo()");
		WebUtils.setRequestAttr("fmsMgr", service.getCntInfo());
		return BASE_URL + "payOrder";
	}
	
	/**
	 * 付款触发
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("payMgr.do")
	public String payMgr(String paynum) throws Exception{
		CommonLogger.info("触发上传应付发票、预付款核算及订单！,FmsMgrController,payMgr()");
		
		//检查总账接口是否已关闭
		if(!pService.checkDealineButton()){
			WebUtils.getMessageManager().addInfoMessage("上送接口已关闭，无法上送应付发票、预付款核销及订单接口数据！");
			return ForwardPageUtils.getErrorPage();
		}

		// AP发票生成前，校验资产类合同付款单是否全部冻结预算，若无则退回付款单。
			
		if(!pcService.updateChangePay()){
			WebUtils.getMessageManager().addInfoMessage("上传付款文件前，退回资产类合同无冻结预算付款单的处理失败！");
			return ForwardPageUtils.getErrorPage();
		}		
		
		fmsGeneral.dealPayOrder(WebHelp.getLoginUser().getUserId(),WebHelp.getLoginUser().getOrg1Code(),paynum);
		
		if("31".equals(paynum))
		{
			WebUtils.getMessageManager().addInfoMessage("已触发上传付款、预付款核销文件！");
		}
		else if("34".equals(paynum))
		{
			WebUtils.getMessageManager().addInfoMessage("已触发上传订单文件！");
		}
		
		return ForwardPageUtils.getSuccessPage();
		
	}
	
	/**
	 * 异步校验当月冲销预提是否已经完成--04030102
	 * 
	 */
	@RequestMapping("ajaxCheckProvision.do")
	@ResponseBody
	public String checkProvision() {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Boolean r = service.checkProvision();
		if (!r) {
			jsonObject.put("pass", true);
			CommonLogger.info("校验当月冲销预提通过！,FmsMgrController,check31Upload()");
		} else {
			jsonObject.put("pass", false);
			CommonLogger.info("校验当月冲销预提不通过！,FmsMgrController,check31Upload()");
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * fms下载查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("fmsDownloadList.do")
	public String fmsDownloadList(FmsDownload bean){
		String startDateString=bean.getStartDate();
		String endDateString=bean.getEndDate();
		WebUtils.setRequestAttr("startDateString", startDateString);
		WebUtils.setRequestAttr("endDateString", endDateString);
		WebUtils.setRequestAttr("fmsDownload", bean);
		WebUtils.setRequestAttr("fmsDownloadList", service.getFmsDownLoadList(bean));
		return BASE_URL + "fmsDownload";
	}
	
	/**
	 * fms上传查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("fmsUploadList.do")
	public String fmsUploadList(FmsUpload bean){
		String startDateString=bean.getStartDate();
		String endDateString=bean.getEndDate();
		WebUtils.setRequestAttr("startDateString", startDateString);
		WebUtils.setRequestAttr("endDateString", endDateString);
		WebUtils.setRequestAttr("fmsUpload", bean);
		WebUtils.setRequestAttr("fmsUploadList", service.getFmsUpLoadList(bean));
		WebUtils.setRequestAttr("ouList", service.getOuList(bean));
		//判断用户是否是超级管理员
		if(WebHelp.getLoginUser().getIsSuperAdmin().equals("1")){
			WebUtils.setRequestAttr("role","ADMIN");
		}
		return BASE_URL + "fmsUpload";
	}
	
	/**
	 * 预提待摊
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("getCglBatchList.do")
	@AddReturnLink(id="getCglBatchList",displayValue="返回预提待摊列表")
	public String getCglBatchList(FmsCglBatch bean) throws Exception{
		CommonLogger.info("查询预提待摊任务列表！,FmsMgrController,getCglBatchList()");
		if(!monthOverService.checkOpenOrg1()){
			WebUtils.getMessageManager().addInfoMessage("系统基础参数未初始化一级行，不能执行操作！请联系管理员！");
			return ForwardPageUtils.getErrorPage();
		}
		String startDateString=bean.getStartDate();
		String endDateString=bean.getEndDate();
		FmsCglBatch fcb = new FmsCglBatch();
		List<FmsCglBatch> fcbList = new ArrayList<FmsCglBatch>();
		fcbList = service.getCglBatchList(fcb);
		if(fcbList.size()==0){
			service.initCglBatch();
		}
		WebUtils.setRequestAttr("startDateString", startDateString);
		WebUtils.setRequestAttr("endDateString", endDateString);
		WebUtils.setRequestAttr("fmsCglBatch", bean);
		WebUtils.setRequestAttr("fmsCglBatchList", service.getCglBatchList(bean));
		WebUtils.setRequestAttr("deadlineDay", pService.getDeadlineDay());
		WebUtils.setRequestAttr("deadlineTime", pService.getDeadlineTime());
		return BASE_URL + "fmsCglBatch";
	}
	
	/**
	 * fms上传下载部分的文件下载
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("fmsFileDownload.do")
	public String downloadFile(HttpServletRequest request,HttpServletResponse response,String batchNo,String fileFlag) throws Exception{
		response.setContentType("application/x-msdownload;charset=UTF-8");
		String filePath = "";
		if("0".equals(fileFlag)){
			FmsDownload bean = new FmsDownload();
			bean.setBatchNo(batchNo);
			FmsDownload fmsDownload = service.getFmsDownLoad(bean);
			filePath = fmsDownload.getDownloadPath();
		}else if("1".equals(fileFlag)){
			FmsUpload bean = new FmsUpload();
			bean.setBatchNo(batchNo);
			FmsUpload fmsUpload = service.getFmsUpLoad(bean);
			filePath = fmsUpload.getDownloadPath();
		}else if("2".equals(fileFlag)){
			FmsUpload bean = new FmsUpload();
			bean.setBatchNo(batchNo);
			FmsUpload fmsUpload = service.getFmsUpLoad(bean);
			filePath = fmsUpload.getUploadPath();
		}
		String fileName = "";
		if(filePath.indexOf("/")>-1)
		{
			fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		}
		else
		{
			fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
		service.downloadFile(request,response,filePath);
		return null;
	}
	
	/**
	 * 处理预提待摊未处理的和处理失败的文件数据
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dealCglBatch.do")
	public String dealCglBatch(FmsCglBatch bean) throws Exception{
		String userId = WebHelp.getLoginUser().getUserId();
		int flag = fmsGeneral.dealProvisionPrepaid(bean.getOrg1Code(),bean.getFeeYyyymm(),bean.getTaskType(),userId);
		if(flag == 0){
			CommonLogger.error("操作失败，当前月结状态为“月结结束”，请触发月结！,FmsMgrController,dealCglBatch()");
			return ForwardPageUtils.getReturnUrlString("操作失败，当前月结状态为“月结结束”，请触发月结！", false, new String[]{"getCglBatchList"});
		}else if(flag == 2){
			CommonLogger.error("操作失败，存在未经办或未复核的预提记录！,FmsMgrController,dealCglBatch()");
			return ForwardPageUtils.getReturnUrlString("操作失败，存在未经办或未复核的预提记录！", false, new String[]{"getCglBatchList"});
		}
		else if(flag == 3){
			CommonLogger.error("操作失败，请在任务年月时间段执行任务！,FmsMgrController,dealCglBatch()");
			return ForwardPageUtils.getReturnUrlString("操作失败，请在任务年月时间段执行任务！", false, new String[]{"getCglBatchList"});
		}
		/*else if(flag == 4){
			CommonLogger.error("操作失败，存在机构及机构层级信息回盘未成功的批次！,FmsMgrController,dealCglBatch()");
			return ForwardPageUtils.getReturnUrlString("操作失败，存在机构及机构层级信息回盘未成功的批次！", false, new String[]{"getCglBatchList"});
		}*/
		else if(flag == 5){
			CommonLogger.error("操作失败，存在发生变化的责任中心未处理情况！,FmsMgrController,dealCglBatch()");
			return ForwardPageUtils.getReturnUrlString("操作失败，存在发生变化的责任中心未处理情况！", false, new String[]{"getCglBatchList"});
		}
		else if(flag == 6){
			CommonLogger.error("操作失败，预提待摊任务超出当月截止时间！请在次月初触发补做。,FmsMgrController,dealCglBatch()");
			return ForwardPageUtils.getReturnUrlString("操作失败，预提待摊任务超出当月截止时间！请在次月初触发补做。", false, new String[]{"getCglBatchList"});
		}
		CommonLogger.info("已触发预提待摊的处理过程！,FmsMgrController,dealCglBatch()");
		return ForwardPageUtils.getReturnUrlString("已触发预提待摊的处理过程！", true, new String[]{"getCglBatchList"});
		
	}
	
	/**
	 * 异步校验31上传是否存在异常--04030402
	 * 
	 */
	@RequestMapping("ajaxCheck31.do")
	@ResponseBody
	public String check31Upload() {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Boolean r = service.check31Upload();
		if (!r) {
			jsonObject.put("pass", true);
			CommonLogger.info("校验31上传状态通过！,FmsMgrController,check31Upload()");
		} else {
			jsonObject.put("pass", false);
			CommonLogger.info("校验31上传状态不通过！,FmsMgrController,check31Upload()");
		}
		return jsonObject.writeValueAsString();
	}
	
	
	/**
	 * fms回盘文件下载页面跳转
	 * @return
	 */
	@RequestMapping("fmsBackFile.do")
	public String fileDowload(){
		CommonLogger.info("跳转到下载回盘文件页面！,FmsMgrController,fileDowload()");
		return BASE_URL+"fmsBackFile";
	}
	
	/**
	 * fms回盘文件下载触发
	 * @param tradeDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("fmsBackDownload.do")
	public String dealDownLoad() throws Exception{
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		CommonLogger.info("触发fms回盘文件下载！,FmsMgrController,dealDownLoad()");
//		fmsGeneral.dealDownload(user);
		//查询各机构各类型文件待处理数量
		DealCountBean countBean = service.getDealCount(org1Code);
		StringBuffer infoMessage = new StringBuffer("");
		infoMessage.append("已触发FMS回盘文件下载！");
		infoMessage.append("<br/>待处理数量：<br/>");
		infoMessage.append("FMS导出员工信息："+countBean.getCount11());
		infoMessage.append("<br/>");
		infoMessage.append("FMS导出机构及机构层级信息："+countBean.getCount12());
		infoMessage.append("<br/>");
		infoMessage.append("FMS导出供应商及银行账户信息："+countBean.getCount13());
		infoMessage.append("<br/>");
		infoMessage.append("FMS导出AP发票信息："+countBean.getCount21());
		infoMessage.append("<br/>");
		infoMessage.append("FMS导出AP付款信息："+countBean.getCount22());
		infoMessage.append("<br/>");
		infoMessage.append("FMS导出GL待摊预提信息："+countBean.getCount23());
		infoMessage.append("<br/>");
		infoMessage.append("FMS导出采购订单信息："+countBean.getCount25());
		infoMessage.append("<br/>");
		infoMessage.append("应付发票校验文件："+countBean.getCount31());
		infoMessage.append("<br/>");
//		infoMessage.append("预付款核销校验文件："+countBean.getCount32());
//		infoMessage.append("<br/>");
		infoMessage.append("总账凭证校验文件："+countBean.getCount33());
		infoMessage.append("<br/>");
		infoMessage.append("采购订单校验文件："+countBean.getCount34());
		
		WebUtils.getMessageManager().addInfoMessage(infoMessage.toString());
		//唤醒线程扫描
	    FmsScanLock.newInstance().executeWake();
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 校验和下载文件补做
	 * @param batchNo
	 * @param tradeType
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("mendFmsDownload.do")
	public String mendFmsDownload(String batchNo,String tradeType) throws Exception{
		String user = WebHelp.getLoginUser().getUserId();
		dwnservice.dealDownload(user, batchNo);
		CommonLogger.info("已触发FMS文件下载和校验的补做！,FmsMgrController,mendFmsDownload()");
		WebUtils.getMessageManager().addInfoMessage("已触发FMS校验文件下载的补做！");
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 补做FMS上传文件
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mendFmsUpload.do")
	public String mendFmsUpload(String batchNo) throws Exception {
		fmsGeneral.mendFmsUpload(batchNo,WebHelp.getLoginUser().getOrg1Code(), WebHelp.getLoginUser().getUserId());
		// 唤醒上传线程，上传文件
		FmsUploadLock.newInstance().executeWake();
		WebUtils.getMessageManager().addInfoMessage("已触发FMS文件上传补做！");
		return ForwardPageUtils.getSuccessPage();
	}
	
	/**
	 * 查询log日志信息
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("querLog.do")
	public String querLog(String batchNo,String tradeType) throws Exception {
		String dealLog = service.querLog(batchNo,tradeType);
		WebUtils.setRequestAttr("dealLog", dealLog);
		return BASE_URL + "queryLog";
	}
		
	@RequestMapping("querUpdateDetail.do")
	public String querUpdateDetail(FmsUpload bean){
		FmsUpload fmsUpload = service.getFmsUpLoad(bean);
		String filePath = fmsUpload.getDownloadPath();//FMS下载文件路径
		String fileName = "";
		String basePath = "";
		String badFilePath = "";
		String logFilePath = "";
		if(null!=filePath && !"".equals(filePath))
		{
			filePath = filePath.replace("\\", "/");
			basePath = filePath.substring(0,filePath.lastIndexOf("/"));
			fileName = filePath.substring(filePath.lastIndexOf("/")+1).replace(".TXT.gz", "");
			badFilePath = basePath + File.separator + fileName+".bad";
			logFilePath = basePath + File.separator + fileName+".log";
		}
		//判断文件是否存在，存在则把路径传到前台页面
		this.jugleFile("baseFile","filePath",filePath);
		this.jugleFile("badFile","badFilePath",badFilePath);
		this.jugleFile("logFile","logFilePath",logFilePath);
		WebUtils.setRequestAttr("fmsDownload", bean);
		WebUtils.setRequestAttr("uploaddeallog", null==fmsUpload?"":fmsUpload.getDealLog());
		return BASE_URL + "downloadDetail";
	}	
	
	/**
	 * 查询明细
	 * @param batchNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("querDetail.do")
	public String querDetail(FmsDownload bean){
		FmsDownload fmsDownload = service.getFmsDownLoad(bean);
		String filePath = fmsDownload.getDownloadPath();//FMS下载文件路径
		String fileName = "";
		String basePath = "";
		String badFilePath = "";
		String logFilePath = "";
		if(null!=filePath && !"".equals(filePath))
		{
			filePath = filePath.replace("\\", "/");
			basePath = filePath.substring(0,filePath.lastIndexOf("/"));
			fileName = filePath.substring(filePath.lastIndexOf("/")+1).replace(".TXT.gz", "");
			badFilePath = basePath + File.separator + fileName+".bad";
			logFilePath = basePath + File.separator + fileName+".log";
		}
		//判断文件是否存在，存在则把路径传到前台页面
		this.jugleFile("baseFile","filePath",filePath);
		this.jugleFile("badFile","badFilePath",badFilePath);
		this.jugleFile("logFile","logFilePath",logFilePath);
		WebUtils.setRequestAttr("fmsDownload", bean);
		return BASE_URL + "downloadDetail";
	}

	private void jugleFile(String fileName,String pathName,String filePath) {
		String fileRealPath = filePath;
		if(null!=fileRealPath && !"".equals(fileRealPath))
		{
			fileRealPath = filePath.replace("\\", "/");
			File file = new File(fileRealPath);
			if(file.exists()){
				WebUtils.setRequestAttr(pathName, fileRealPath);
				WebUtils.setRequestAttr(fileName, fileRealPath.substring(fileRealPath.lastIndexOf("/")+1));
			}
		}
	}
	
	/**
	 * fms下载的文件下载
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@RequestMapping("fmsDownloadFile.do")
	public String fmsDownloadFile(HttpServletRequest request,HttpServletResponse response,String filePath) throws Exception{
		response.setContentType("application/x-msdownload;charset=UTF-8");
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
		service.downloadFile(request,response,filePath);
		return null;
	}
	
}
