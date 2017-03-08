package com.forms.dealdata.download;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.constantValues.FmsValues;
import com.forms.prms.tool.fms.FMSDownloadBean;
import com.forms.prms.tool.fms.UpLoadBean;
import com.forms.prms.tool.fms.parse.FMSConfig;
import com.forms.prms.tool.fms.parse.FTPUtils;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.domain.FTPBean;
import com.forms.prms.tool.fms.parse.domain.SqlBean;
import com.forms.prms.tool.fms.parse.service.FMSService;

@Service
public class DownloadData implements IDownloadData {
	
	public String fileName;
	
	public String srcZipFileName;
	
	public UpLoadBean updateBean = new UpLoadBean();
	
	public FMSDownloadBean downBean = new FMSDownloadBean();
	
	@Autowired
	public FMSService service;
	
	@Override
	public void beforeDownload(String batchNo,String tradeType) throws Exception {
		if(!tradeType.startsWith("3"))
		{
			service.downloadInitStatus(batchNo,FmsValues.FMS_DOWNLOAD_FORDEAL);
		}
		else
		{
			service.uploadInitStatus(batchNo);
		}
	}

	@Override
	public void download(String batchNo,String tradeDate,String tradeType,String srcFileName) throws Exception {
		if(null == srcFileName || "".equals(srcFileName))
		{
			FMSBean fms = FMSConfig.getFMS(tradeType);
			srcZipFileName = fms.getFile().getFileName().replace("{date8}", tradeDate);
		}
		else
		{
			srcZipFileName = srcFileName;
		}
		DownLoadBean dlBean = new DownLoadBean(batchNo,tradeDate,tradeType,srcZipFileName);
		fileName = service.downloadFile(dlBean,updateBean,downBean);
	}

	@Override
	public void dealFile(String batchNo,String tradeDate,String tradeType) throws Exception {
		DownLoadBean dlBean = new DownLoadBean(batchNo,tradeDate,tradeType,fileName);
		service.fileDeal(dlBean,updateBean,downBean);
	}
	
	@Override
	public void callProc(String batchNo,String tradeType) throws Exception {
		try
		{
			FMSBean fms = FMSConfig.getFMS(tradeType);
			String[] prcNames = fms.getFile().getProcedureName().split(",");
			for(String prcName : prcNames)
			{
				service.dealFmsFile(prcName, batchNo);
			}
			
			//删除FTP上的文件
			FTPBean ftp = new FTPBean();
			Boolean isdelFile = WebHelp.getSysParaBoolean("FTP_DEL_FILE_FLAG");
			if("1".equals(ftp.getDownloadFileLocal()) && isdelFile)
			{
				boolean delRes = FTPUtils.deleteFile(ftp.getDownloadHostAddr(), ftp.getDownloadPort(), ftp.getDownloadUserName(), ftp.getDownloadPassword(),
						ftp.getDownloadFolder(tradeType), srcZipFileName);
				CommonLogger.info("删除FTP上的文件【"+srcZipFileName+"】" + (delRes ? "成功！" : "失败！"));
			}
			
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_SUCC);
			downBean.setDealLog("结果文件处理成功");
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_SUCC);
			updateBean.setDealLog("校验文件处理成功");
			
			
		}
		catch(Exception e)
		{
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
			downBean.setDealLog("结果文件下载处理callProc异常:"+e.getMessage().substring(0,300));
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			updateBean.setDealLog("校验文件下载处理callProc异常:"+e.getMessage().substring(0,300));
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void afterDownload(String batchNo,String tradeType) throws Exception{
		if(tradeType.startsWith("3"))
		{
			updateBean.setBatchNo(batchNo);
			service.updateStatus(updateBean);
		}
		else
		{
			downBean.setBatchNo(batchNo);
			service.updateDlStatus(downBean);
		}
		//删除临时表数据
		FMSBean fms = FMSConfig.getFMS(tradeType);
		String truncateTmpSql = fms.getFile().getTruncateTmpSql();
		service.truncateTmpData(truncateTmpSql);
	}
	
	@Override
	public String execute(String batchNo,String tradeDate,String tradeType,String srcFileName) throws Exception {
		try
		{
			this.beforeDownload(batchNo,tradeType);
			this.download(batchNo,tradeDate,tradeType,srcFileName);
			if(null==fileName || "".equals(fileName))
			{
				return "0";//未从FTP获取到文件
			}
			this.dealFile(batchNo,tradeDate,tradeType);
			this.callProc(batchNo,tradeType);
			return "1";
		}
		catch(Exception e)
		{
			CommonLogger.error("execute fail! batchNo:"+batchNo+ " tradeType:" +tradeType);
			e.printStackTrace();
			throw e;
		}
		finally
		{
			this.afterDownload(batchNo, tradeType);
		}
		
	}

	//校验回复的数据与上传的数据数量是否一致
	public void checkData(String batchNo, String tradeDate, String tradeType) throws Exception
	{
		FMSBean fms = FMSConfig.getFMS(tradeType);
		fms.setBatchNo(batchNo);
		try
		{
			Map<String, String> param = new HashMap<String, String>();
			param.put("typeName", "0");
			param.put("fId", tradeType);
			param.put("batchNo", batchNo);
			param.put("tableName", fms.getTableName());
			param.put("checkTableName", fms.getCheckTableName());
			param.put("infoMsg", "");
			service.checkFileData(param);
			String infoMsg = param.get("infoMsg");
			if(("31".equals(tradeType)||"32".equals(tradeType)||"34".equals(tradeType))&&!Tool.CHECK.isBlank(infoMsg)){
				updateBean.setDealLog(infoMsg);
				updateBean.setDataFlag(FmsValues.FMS_UPDATE_CHK_FAIL);
				CommonLogger.info(infoMsg);
				// 删除文件对应check数据库的数据
				service.deleteCheckData(fms);
				throw new Exception(infoMsg);
			}else{
				//从check表进行批量更新
				List<SqlBean> sqlList = fms.getFile().getCheckUpdateSql();
				if(null!=sqlList && sqlList.size()>0)
				{
					for(SqlBean bean : sqlList)
					{
						service.updateCheckStatus(bean.getSql(), batchNo);
					}
					updateBean.setDataFlag(FmsValues.FMS_UPDATE_SUCC);
					updateBean.setDealLog("校验文件处理成功");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(("31".equals(tradeType)||"32".equals(tradeType)||"34".equals(tradeType))){
				// 删除文件对应数据库的数据
				service.deleteCheckData(fms);
			}
			updateBean.setDataFlag(FmsValues.FMS_UPDATE_FAIL);
			updateBean.setDealLog("fms校验文件下载校验错误,"+e.getMessage());
			throw e;
		}
	}
	
	//校验文件的内容是否有效（同一批次同一付款单(或同一订单)下的物料是否相同，状态是否相同）
	public void checkResultData(String batchNo, String tradeDate, String tradeType) throws Exception
	{
		FMSBean fms = FMSConfig.getFMS(tradeType);
		fms.setBatchNo(batchNo);
		try
		{
			Map<String, String> param = new HashMap<String, String>();
			param.put("typeName", "1");
			param.put("fId", tradeType);
			param.put("batchNo", fms.getBatchNo());
			param.put("tableName", fms.getTableName());
//			param.put("checkTableName", fms.getCheckTableName());
			param.put("infoMsg", "");
			String infoMsg = param.get("infoMsg");
			if(!Tool.CHECK.isBlank(infoMsg)){
				downBean.setDealLog(infoMsg);
				downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_CHK_FAIL);
				//删除tid的数据
				service.delDetailByBatchNO(batchNo,fms.getTableName());
				throw new Exception("file check fail");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			downBean.setDataFlag(FmsValues.FMS_DOWNLOAD_FAIL);
			downBean.setDealLog("结果文件下载校验错误");
			CommonLogger.error("FMS2ERP fileDeal() Exception:", e);
			throw e;
		}
	}
}
