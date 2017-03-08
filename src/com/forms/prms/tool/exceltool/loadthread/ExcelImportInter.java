package com.forms.prms.tool.exceltool.loadthread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.imports.ExcelImportUtility;
import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.StringUtil;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;

import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.service.DataMigrateService;


public class ExcelImportInter implements Runnable{
	
	DataMigrateService dmService = DataMigrateService.getInstance();
	CommonExcelDealService excelDealService = CommonExcelDealService.getInstance();

	private CommonExcelDealBean loadBean;
	private ExcelImportInfo excelImportInfo = new ExcelImportInfo();
	private String loadPath = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR");
	private Map beans;
	
	public ExcelImportInter(CommonExcelDealBean loadBean){
		this.loadBean = loadBean;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void run() {		
		CommonLogger.info(">>>>>>>>>正在处理导入线程【"+loadBean.getTaskId()+"】<<<<<<<<<<<<<<");
		//1.1将文件保存到本地目录(设置本地存储路径)
		InputStream shareStream = null;
		OutputStream localStream = null;
		String loaclFile = loadPath+ "/" + loadBean.getTaskId() + loadBean.getSourceFname().substring(loadBean.getSourceFname().lastIndexOf("."));
			
		//1.2判断是否需要创建本地目录
		File localDir = new File(loadPath);
		if(!localDir.exists()){
			localDir.mkdirs();
		}
		//1.3将Excel内容复制到本地
		try {
			shareStream = new FileInputStream(loadBean.getSourceFpath()+loadBean.getSourceFname());
			localStream = new FileOutputStream(loaclFile);
			IOUtils.copy(shareStream, localStream);
		}catch (FileNotFoundException e) {
			CommonLogger.error("Excel复制文件时发生 FileNotFoundException ，In[ExcelImportInter - shareStream/localStream] File Not Found!!");
			e.printStackTrace();
		} catch (IOException e) {
			CommonLogger.error("Excel复制文件时发生 IOException ，In[ExcelImportInter - IOUtils.copy() ] method , has Some Error!! ");
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(shareStream);
			IOUtils.closeQuietly(localStream);
		}
		
		//2.拼接路径的最后字符(文件夹路径+"/")
		if(!loadBean.getSourceFpath().endsWith("/")){
			loadBean.setSourceFpath(loadBean.getSourceFpath()+ "/") ;
		}
		//3.组织任务的参数(转换为map格式)
		if(loadBean.getTaskParams() != null && !"".equals(loadBean.getTaskParams().trim())){
			beans = StringUtil.parserToMap(loadBean.getTaskParams());
		}else{
			beans = new HashMap();
		}
		
		//4.分别处理该Excel文件中多个ConfigId的数据导入(从Excel——>DB)
		String[] configIds = loadBean.getConfigId().split(",");
		try {
			for(int i=0; i<configIds.length; i++){
				//4.1执行Excel中一个Sheet的数据导入DB
				excelImportInfo = ExcelImportUtility.proccess(loadBean.getTaskId(), loaclFile, i, configIds[i],beans,loadPath);
				//excelImportInfo = ExcelImportUtility.proccess(loadBean.getTaskId(), loaclFile, loadBean.getSheetIndex(), loadBean.getConfigId(),beans,loadPath);
			}
		} catch (Exception e) {
			CommonLogger.error("Dealing Parse Excel File In ExcelImportUtility.process() Method --SheetXX has Some Error , Then Will Be Update Control And TaskId's Flag ");
			//CommonLogger.error("解析Excel时发生异常:[Method: ExcelImportUtility.process() ; parseExcelConfigId :]"+configIds[i]);
			e.printStackTrace();
			excelImportInfo.setHasError(true);					//记录Excel解析有误有异常
			
			//4.2.1更新导入任务Task的最终错误状态：
			CommonExcelDealBean upTaskMemoBean = new CommonExcelDealBean();
			upTaskMemoBean.setTaskId(loadBean.getTaskId());
			upTaskMemoBean.setDataFlag("02");					//"02"=Task处理失败
			excelDealService.updateLoadResult(upTaskMemoBean);
			upTaskMemoBean.setProcMemo("Dealing the Excel import operator (parsing Excel file) has some error ,please recheck !");
			//4.2.2更新导入任务Task的最终状态和导入备注描述
			excelDealService.updateLoadTaskProcMemo(upTaskMemoBean);
			
			//4.2.3如果有批次号参数，直接将该批次的最终状态更新为"失败"
			if( null != beans.get("impBatch") && "" != beans.get("impBatch")){
				dmService.updateUpDataConInfoDataFlag("01", (String)beans.get("impBatch"));			//catch异常，更新Control表的状态
			}
		}
	}

}
