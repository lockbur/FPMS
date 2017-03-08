package com.forms.dealexceldata.importdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;

import com.forms.dealexceldata.exceldealtool.ExcelConfig;
import com.forms.dealexceldata.exceldealtool.ImportExcelBean;
import com.forms.dealexceldata.exceldealtool.service.ExcelDealService;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringHelp;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;

/**
 * 处理导入的线程
 */
public class ImportExcelThread implements Runnable {
	ExcelDealService excelService = new ExcelDealService();
	CommonExcelDealService excelDealService = CommonExcelDealService
			.getInstance();
	private CommonExcelDealBean loadBean;
	private String loadPath = WebHelp.getSysPara("EXCEL_UPLOAD_FILE_DIR");

	public ImportExcelThread(CommonExcelDealBean loadBean) {
		this.loadBean = loadBean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run() {
		CommonLogger.info(">>>>>>>>>正在处理导入线程【" + loadBean.getTaskId()
				+ "】<<<<<<<<<<<<<<");
		// 1.1将文件保存到本地目录(设置本地存储路径)
		InputStream shareStream = null;
		OutputStream localStream = null;
		String loaclFile = loadPath
				+ "/"
				+ loadBean.getTaskId()
				+ loadBean.getSourceFname().substring(
						loadBean.getSourceFname().lastIndexOf("."));

		// 1.2判断是否需要创建本地目录
		File localDir = new File(loadPath);
		if (!localDir.exists()) {
			localDir.mkdirs();
		}
		// 1.3将Excel内容复制到本地
		try {
			shareStream = new FileInputStream(loadBean.getSourceFpath()
					+ loadBean.getSourceFname());
			localStream = new FileOutputStream(loaclFile);
			IOUtils.copy(shareStream, localStream);
		} catch (FileNotFoundException e) {
			CommonLogger
					.error("Excel复制文件时发生 FileNotFoundException ，In[ExcelImportInter - shareStream/localStream] File Not Found!!");
			e.printStackTrace();
		} catch (IOException e) {
			CommonLogger
					.error("Excel复制文件时发生 IOException ，In[ExcelImportInter - IOUtils.copy() ] method , has Some Error!! ");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(shareStream);
			IOUtils.closeQuietly(localStream);
		}

		// 2.拼接路径的最后字符(文件夹路径+"/")
		if (!loadBean.getSourceFpath().endsWith("/")) {
			loadBean.setSourceFpath(loadBean.getSourceFpath() + "/");
		}

		try {
			// 1.根据taskId获取导入的汇总数据（获取批次号）
			ImportExcelBean importExcelBean = ExcelConfig
					.getImportExcel(loadBean.getConfigId());
			IImportData iImportData = (IImportData)SpringHelp.getBean(importExcelBean.getDealClass());
			iImportData.execute(importExcelBean, loadBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
