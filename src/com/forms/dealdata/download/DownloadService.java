package com.forms.dealdata.download;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.FMSDownloadBean;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.service.FMSService;
import com.forms.prms.web.cluster.Lock;
import com.forms.prms.web.contract.confirm.dao.IContractConfirmDAO;

@Component
public class DownloadService {
	
	@Autowired
	private FMSService service;
	
	// @Autowired
	// private MontAprvService mpservice;
	
	@Autowired
	private IContractConfirmDAO dao;
	
	@Lock(taskType = "'fmsdownload'", taskSubType = "'fms'", instOper = "#user", memo = "'校验和回复文件以及用户、机构、供应商文件处理'")
	public void dealDownload(String user,String batchNo) throws Exception 
	{
		String currDate = Tool.DATE.getDateStrNO();
		if(null == batchNo)
		{
			dealResult(currDate);
		}
		dealCheck(currDate,batchNo);
		dealYearBugetBatchProcess(currDate.substring(0, 4));		
	}
	
	private void dealResult(String currDate)
	{
		
		String batchDate = "";
		String tradeTypes = "";
		List<FMSDownloadBean> taskList = null;
		try {
			// 查询处理日期
					
			tradeTypes = FMSConfig.getFMS("FMS2ERP").getTradeTypes();
			batchDate = service.getDealDate(tradeTypes);
			if(Tool.CHECK.isEmpty(batchDate)){
				//处理日期如果为空 1.未初始化处理任务  2.第一天的任务处理失败
				String firstTradeDate = service.getFirstTradeDate(tradeTypes);
				if(Tool.CHECK.isEmpty(firstTradeDate))
				{
					batchDate = currDate;
				}
				else
				{
					batchDate = firstTradeDate;
				}
			}		
			// 结果文件处理（1.1、1.2、1.3、2.1、2.2、2.3、2.5）
			while (batchDate.compareTo(currDate) <= 0) {
				//merge batchDate日的处理任务
				service.mergeDownload(batchDate, tradeTypes.split(","));
				// 查询非成功的记录，依次处理
				taskList = service.unsuccTask(batchDate);
				if (!executeResult(batchDate, taskList)) // 如果该天未全部处理成功，跳出循环
					break;
				CommonLogger.info("【"+batchDate+"】fms下载基础数据及结果文件任务全部执行成功！");
				batchDate = DateUtil.getNextDay(batchDate, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("FMS下载线程在处理【"+batchDate+"】基础数据和结果文件时出错：" + e.getMessage());
		}
	}
	
	
	private void dealCheck(String currDate,String batchNo)
	{
		// 校验文件处理（3.1、3.2,、3.3、3.4）
		List<UpLoadBean> downList = null;
		UpLoadBean bean = new UpLoadBean();
		if(null!=batchNo)
		{
			//页面触发“处理”时，校验该笔数据是否已经处理成功
			String dataFlag = service.getDataFlagByBatchNo(batchNo);
			if(FmsValues.FMS_UPDATE_SUCC.equals(dataFlag))
			{
				return ;
			}
			bean.setBatchNo(batchNo);
		}
		else
		{
			String[] dataFlags = { FmsValues.FMS_DOWN_FORDEAL,
					FmsValues.FMS_UPDATE_CHK_FAIL,FmsValues.FMS_UPDATE_FAIL,FmsValues.FMS_DOWNLOAD_WAITFILE };
			bean.setDataFlags(dataFlags);
		}
		try {
			downList = service.getSummaryList(bean);
			executeCheck(currDate, downList);
		} catch (Exception e) {
			e.printStackTrace();
			CommonLogger.error("FMS下载线程在批处理校验文件时出错：" + e.getMessage());
		}
	}
	
	public boolean executeResult(String tradeDate,
			List<FMSDownloadBean> taskList) {
		boolean execResult = true;
		String resultStr = "";
		if (null != taskList && taskList.size() > 0) {
			for (FMSDownloadBean downloadBean : taskList) {
				String tradeType = downloadBean.getTradeType();
				String batchNo = downloadBean.getBatchNo();
				long beginMills = 0;
				long endMills = 0;
				try {
					beginMills = new Date().getTime();
					FMSBean fms = FMSConfig.getFMS(tradeType);
					// Class downloadClass =
					// Class.forName(fms.getFile().getDealClass());
					// IDownloadData downloadObj =
					// (IDownloadData)downloadClass.newInstance();
					IDownloadData downloadObj = (IDownloadData) SpringHelp
							.getBean(fms.getFile().getDealBean());
					resultStr = downloadObj.execute(batchNo, tradeDate, tradeType, null);
					if("0".equals(resultStr))
					{
						// 任一个文件未下载到，停止当日处理
						execResult = false;
					}
					// Method method = downloadClass.getMethod("execute",new
					// Class[]{String.class,String.class,String.class});
					// method.invoke(downloadObj,new
					// String[]{batchNo,tradeDate,tradeType});
				} catch (Exception e) {
					// 一个类型处理失败，继续处理下一个类型
					execResult = false;
					//e.printStackTrace();
					CommonLogger.error("tradeType:" + tradeType
							+ " DownloadThread Exception:", e);
				}
				finally
				{
					endMills = new Date().getTime();
					//更新处理时间
					long costMills = (endMills - beginMills)/1000;
					service.updateCostTime(batchNo,"  (处理时间："+costMills+"s)");
				}
			}
		}
		return execResult;
	}

	public void executeCheck(String tradeDate, List<UpLoadBean> taskList) {
		if (taskList != null && taskList.size() > 0) {
			for (UpLoadBean uploadbean : taskList) {
				String tradeType = uploadbean.getTradeType();
				String batchNo = uploadbean.getBatchNo();
				String fileName = uploadbean.getUploadPath();
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				try {
					FMSBean fms = FMSConfig.getFMS(tradeType);
					IDownloadData downloadObj = (IDownloadData) SpringHelp
							.getBean(fms.getFile().getDealBean());
					downloadObj.execute(batchNo, tradeDate, tradeType, fileName);
				} catch (Exception e) {
					// 一个类型处理失败，继续处理下一个类型
					//e.printStackTrace();
					CommonLogger.error("FMS下载线程在处理【"+fileName+"】校验文件时出错：", e);
				}
			}
		}
	}
	
	public void dealYearBugetBatchProcess(String year){
		//处理预算（调过程）
		CommonLogger.info("存量合同年初预算冻结存储过程自动调用开始.....");
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("cntNum", null);//合同号
			param.put("isOrder", null);//是否订单
			param.put("instUser", "system");//确认人
			param.put("instUserDutyCode", "00000");//确认人所在责任中心
			dao.bgtFrozen(param);
			String flag = param.get("flag");
			String returnStr = param.get("msgInfo");
			CommonLogger.info("存量合同年初预算冻结存储过程运行结果：" +("1".equals(flag)?"成功，":"失败， ") + returnStr);
		} catch (Exception e) {
			//e.printStackTrace();
			CommonLogger.error("存量合同年初预算冻结存储过程自动调用出错:", e);
		}		
	}
}
