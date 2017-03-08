package com.forms.prms.tool.fms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.FMSTradeType;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.parse.service.FMS2ERP;
import com.forms.prms.tool.fms.parse.service.FMSDownloadDeal;
import com.forms.prms.tool.fms.parse.service.FMSUploadDeal;
import com.forms.prms.tool.fms.receive.service.ReceiveService;
import com.forms.prms.tool.fms.send.service.SendService;
import com.forms.prms.web.amortization.fmsMgr.service.FmsMgrService;
import com.forms.prms.web.amortization.provisionMgr.service.ProvisionService;
import com.forms.prms.web.cluster.Lock;
import com.forms.prms.web.monthOver.service.MonthOverService;
import com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.service.DutyMergeService;
import com.forms.prms.web.sysmanagement.parameter.service.ParameterService;

@Component
public class FmsGeneral {

	@Autowired
	private SendService sendService;
	@Autowired
	private ReceiveService receiveService;
	@Autowired
	private FMSUploadDeal fmsUploadDeal;
	@Autowired
	private FMSDownloadDeal fmsDownloadDeal;
	@Autowired
	private FMS2ERP fms2erp;
	@Autowired
	private MonthOverService monthOverService;
	@Autowired
	private ProvisionService proService;
	@Autowired
	private DutyMergeService dmService;
	@Autowired
	private FmsMgrService fmService;
	@Autowired
	private ParameterService parameterService;
	

	/**
	 * 生成31应付发票及预付款核销和34订单数据
	 * 
	 * @throws Exception
	 */
	@Lock(taskType = "'fmsupload'", taskSubType = "#org1Code", instOper = "#user", memo = "'应付发票、预付款核销和订单'")
	public boolean dealPayOrder(String user,String org1Code,String payNum) throws Exception {
		/****************** 1、生成数据 ***********************/
		//生成31应付发票及预付款核销数据
		if("31".equals(payNum))
		{
			CommonLogger.info("生成31应付发票及预付款核销数据！,FmsGeneral,dealPayOrder()");
			sendService.addFms31ToUpload();
		}
		// 生成34订单明细
		else if("34".equals(payNum))
		{
			CommonLogger.info("生成34订单数据！,FmsGeneral,dealPayOrder()");
			sendService.addFms34ToUpload();
		}
		//月结都上送
		else if("mo".equals(payNum))
		{
			CommonLogger.info("生成31应付发票及预付款核销数据！,FmsGeneral,dealPayOrder()");
			sendService.addFms31ToUpload();
			CommonLogger.info("生成34订单数据！,FmsGeneral,dealPayOrder()");
			sendService.addFms34ToUpload();
		}
		/****************** 2、扫描上传至FMS ***********************/
		CommonLogger.info("扫描上送数据至FMS！,FmsGeneral,dealPayOrder()");
		fmsUploadDeal.execute(null);
		return true;
	}
	
	/**
	 * 生成33 预提待摊数据
	 * 
	 * @throws Exception
	 */
	@Lock(taskType = "'fmsupload'", taskSubType = "#org1Code", instOper = "#user", memo = "'待摊、预提和预提冲销'")
	public int dealProvisionPrepaid(String org1Code,String yyyymm, String taskType, String user) throws Exception {
		
		//校验是否在月结状态下执行
		if(!"0".equals(monthOverService.getMaxDataFlag())){
			return 0;
		}
		
		//责任中心变动表中是否存在撤并责任中心不成功的任务
		String dmbatchStatus = dmService.checkBatchStatus();
		if(dmbatchStatus != null){
			return 5;//"存在撤并责任中心不成功的任务！"
		}
		
		//20160120 确认不校验机构及层级版本是否最新 linjia
		//校验download表中是否存在交易日期是系统日期前一天的机构及层级信息任务回盘未成功的
		/*		
		String download12 = dmService.checkDownload12();
		if(download12 != null){
			return 4;//"存在机构及机构层级信息回盘未成功的批次！"
		}
		*/
				
		/**冲销预提任务执行前的校验**/
		if("0".equals(taskType) && !fmService.checkProvisionMonth(yyyymm)){
		 //是否在冲销任务年月内执行
			   return 3;
	    /**预提待摊任务执行前的校验**/
		}else if("1".equals(taskType)&& "00".equals(proService.getPPStatus(org1Code,yyyymm)) 
				&& proService.getNotPass(org1Code) != null ){
		//待处理的预提待摊任务，检查预提复核状态，若存在未通过返回2
			return 2;
		}else if("1".equals(taskType) && ("00".equals(proService.getPPStatus(org1Code,yyyymm)) || "03".equals(proService.getPPStatus(org1Code,yyyymm)))
				&& !fmService.checkPPMonth(yyyymm) && !parameterService.checkDeadline(yyyymm)){
		//待产生接口数据状态下的预提待摊任务，在当任务年月内是否不超过截止时间
			return 6;
		}else if("1".equals(taskType) && ("04".equals(proService.getPPStatus(org1Code,yyyymm))|| "05".equals(proService.getPPStatus(org1Code,yyyymm)))){
			/***************0、若为未发生预提待摊数据状态，则生成预提待摊冲销数据************/
			sendService.callERP33(org1Code,yyyymm, taskType);
			return 1;
		}else{
		/****************** 1、根据任务类型，生成预提待摊冲销数据交易明细，即接口数据 ********/
		sendService.callERP33(org1Code,yyyymm, taskType);
		/****************** 2、扫描上传至FMS ***********************/
		fmsUploadDeal.execute(null);
		return 1;
		}
	}

	/**
	 * 处理下载文件及下载文件
	 * 
	 * @throws Exception
	 */
	@Lock(taskType = "'fmsdownload'", taskSubType = "'fms'", instOper = "#user", memo = "'校验和回复文件以及用户、机构、供应商文件处理'")
	public void dealDownload(String user) throws Exception {
		CommonLogger.info("下载fms回盘文件及处理下载fms回盘文件!,FmsGeneral,dealDownload()");
		// 校验文件
		fmsDownloadDeal.execute(null);
		// 结果文件
		fms2erp.execute(null,"FMS2ERP");
		//基础信息
		String curTime = Tool.DATE.getTime().substring(0, 5);
		String taskStarTime = WebHelp.getSysPara("FILE_DOWLOAD_TIME_START");//00:00
		String taskEndTime = WebHelp.getSysPara("FILE_DOWLOAD_TIME_END");//22:00
		if (curTime.compareTo(taskStarTime) >= 0 && curTime.compareTo(taskEndTime)<0) {
			fms2erp.execute(null,"FMSSYS");
		}
	}

	/**
	 * 补做处理失败的fms文件下载
	 * 
	 * @param batchNo
	 * @param tradeType
	 * @throws Exception
	 */
	@Lock(taskType = "'fmsdownload'", taskSubType = "'fms'", instOper = "#user", memo = "'校验和回复文件补做'")
	public void mendFmsDownload(String batchNo, String tradeType, String user) throws Exception {
		CommonLogger.info("补做处理失败的fms文件下载！,FmsGeneral,mendFmsDownload()【batchNo:"+batchNo+";tradeType:"+tradeType+";user:"+user+";】");
		if (FMSTradeType.TRADE_TYPE_21.equals(tradeType) || FMSTradeType.TRADE_TYPE_22.equals(tradeType)
				|| FMSTradeType.TRADE_TYPE_23.equals(tradeType) || FMSTradeType.TRADE_TYPE_25.equals(tradeType)
				|| FMSTradeType.TRADE_TYPE_26.equals(tradeType)) {
			receiveService.dealFmsFile(tradeType, batchNo);
		} else if (FMSTradeType.TRADE_TYPE_31.equals(tradeType) || FMSTradeType.TRADE_TYPE_32.equals(tradeType)
				|| FMSTradeType.TRADE_TYPE_33.equals(tradeType) || FMSTradeType.TRADE_TYPE_34.equals(tradeType)) {
			UpLoadBean bean = new UpLoadBean();
			bean.setBatchNo(batchNo);
			bean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			fmsDownloadDeal.execute(bean);
		}else if (FMSTradeType.TRADE_TYPE_11.equals(tradeType) || FMSTradeType.TRADE_TYPE_12.equals(tradeType)
				|| FMSTradeType.TRADE_TYPE_13.equals(tradeType)) {
			//补做，就重新调用存储过程
			receiveService.dealFmsFile(tradeType,batchNo);
			
		}
	}
	
	/**
	 * 上传文件补做
	 * @param batchNo
	 * @param user
	 * @throws Exception 
	 */
	@Lock(taskType = "'fmsupload'", taskSubType = "#org1Code", instOper = "#user", memo = "'上传文件补做'")
	public void mendFmsUpload(String batchNo,String org1Code, String user) throws Exception{
		CommonLogger.info("上传文件补做！,FmsGeneral,mendFmsUpload()【batchNo:"+batchNo+";user:"+user+";】");
		UpLoadBean bean = new UpLoadBean();
		bean.setBatchNo(batchNo);
		fmsUploadDeal.execute(bean);
	}

}
