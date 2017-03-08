package com.forms.dealexceldata.exceldealtool.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.dealexceldata.exceldealtool.ImportExcelBean;
import com.forms.dealexceldata.exceldealtool.LoadData;
import com.forms.dealexceldata.exceldealtool.ReaderExcel;
import com.forms.dealexceldata.exceldealtool.XLSX2CSV;
import com.forms.dealexceldata.exceldealtool.dao.ExcelDealDao;
import com.forms.dealexceldata.exceldealtool.domain.ImportBean;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.platform.excel.exports.configparse.ExportConfig;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;

@Service
public class ExcelDealService {
	@Autowired
	private ExcelDealDao excelDealDao;

	/**
	 * 更新任务状态
	 * 
	 * @param importBean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateTaskLoadStatus(ImportBean importBean) {
		CommonLogger.info("更新任务ID+" + importBean.getTaskId() + "的状态");
		return excelDealDao.updateTaskLoadStatus(importBean) > 0 ? true : false;
	}

	/**
	 * 根据taskId找导入的汇总数据
	 * 
	 * @param importBean
	 * @return
	 */
	public ImportBean getTaskLoadDataById(String taskId) {
		CommonLogger.info("查询任务ID+" + taskId + "的汇总数据");
		return excelDealDao.getTaskLoadDataById(taskId);
	}

	/**
	 * 导入文件处理
	 * 
	 * @param configId
	 * @param txtFilePath
	 * @param substring
	 * @throws java.lang.Exception 
	 */
	public void importDataDeal(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws  Exception {
		String txtFilePath = "";// 转后txt文件的路径
		String inputFilePath = "";
		try {
			/** convert xlsx2txt 、校验文件头与模版中的是否一致 */
			inputFilePath = loadBean.getSourceFpath() + loadBean.getSourceFname();// 导入的文件路径
			String sheetName = "Sheet1";// sheet名固定Sheet1，仅支持一个Sheet的导入
			// 从配置中获取模版文件的路径
			String templetFilePath = ExportConfig.baseConfigPath + File.separator+ importExcelBean.getTempletExcelFile();// 获取模版的文件路径
			List<String> templetColumns = ReaderExcel.readerExcel(templetFilePath);// 获取模版中的表头
			int minColumns = templetColumns.size();// 从excel文件中取得最小列数
			Map<String, Object> map = XLSX2CSV.covertXLSX2CSV(inputFilePath, sheetName, minColumns, null, ".txt",
					loadBean.getTaskBatchNo(),templetColumns.toString());
			// 加载数据q
			txtFilePath = map.get("outFilePath").toString();
			int textRow = Integer.parseInt(map.get("row").toString());
			new LoadData().load(loadBean.getTaskBatchNo(),loadBean.getConfigId(), txtFilePath.substring(0, txtFilePath.lastIndexOf("/")),
					txtFilePath.substring(txtFilePath.lastIndexOf("/") + 1));
			int loadDbRow = this.queryById(importExcelBean.getTableName(),
					loadBean.getTaskBatchNo());
			if (textRow == 0) {
				throw new Exception("导入的文件是空的");
			}
			if (textRow != loadDbRow) {
				throw new Exception("填写的数据条数和导入数据库的条数不匹配,填写了"+textRow+"条记录，导入数据库"+loadDbRow+"条");
				
			}
		} catch (Exception e) {
			String str = "";
			if(!Tool.CHECK.isBlank(e.getMessage())){
				str = e.getMessage();
			}
			CommonLogger.error("导入excel处理出错！"+str);
			throw new Exception("导入excel处理出错！"+str);
		}finally{
			String mkdirsPath = inputFilePath.substring(0,
					inputFilePath.lastIndexOf("."));
			String xlsl_txt = mkdirsPath+"_temp.txt";
			File file = new File(xlsl_txt);
			if (file.exists()) {
				try {
					file.delete();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteDataById(String batchNo, String tableName) {
		CommonLogger.info("删除批次：" + batchNo + "对应的明细数据");
		return excelDealDao.deleteDataById(batchNo, tableName);
	}

	@Transactional(rollbackFor = Exception.class)
	public int queryById(String tableName, String taskBatchNo) {
		CommonLogger.info("查询" + tableName + "中批次号：" + taskBatchNo + "的数据");
		return excelDealDao.queryById(tableName, taskBatchNo);
	}

	public int updateBgtSumTotal(ImportBean iBean) {
		CommonLogger.info("更新批次：" + iBean.getTaskBatchNo() + "的预算汇总数据状态");
		return excelDealDao.updateBgtSumTotal(iBean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateMontAprv(ImportBean iBean) {
		CommonLogger.info("更新批次：" + iBean.getTaskBatchNo() + "的审批链或监控指标的汇总数据状态");
		return excelDealDao.updateMontAprv(iBean);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateUserRoleRln(ImportBean iBean) {
		CommonLogger.info("更新批次：" + iBean.getTaskBatchNo() + "的用户职责信息的汇总数据状态");
		return excelDealDao.updateUserRoleRln(iBean);
	}
	
	public CommonExcelDealBean queryTaskByBatchNo(String batchNo) {
		CommonLogger.info("查询批次：" + batchNo + "对应的task任务信息");
		return excelDealDao.queryTaskByBatchNo(batchNo);
	}
	 public static String covertXLSX2CSV2(String inputFilePath,   String outFilePath, String outFileType) throws Exception {
	    	String xlsl_txt = "";
	    	String mkdirsPath = inputFilePath.substring(0,
					inputFilePath.lastIndexOf("."));
			xlsl_txt = mkdirsPath+"_temp"+outFileType;
			outFilePath = mkdirsPath + outFileType;
			XLSX2CSV xlsx2csv = new XLSX2CSV(inputFilePath, xlsl_txt);
			 xlsx2csv.process();
			 return "";
	    }
}
