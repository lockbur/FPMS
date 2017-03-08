package com.forms.dealexceldata.importdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.dealexceldata.exceldealtool.ExcelDealValues;
import com.forms.dealexceldata.exceldealtool.ImportExcelBean;
import com.forms.dealexceldata.exceldealtool.domain.ImportBean;
import com.forms.dealexceldata.exceldealtool.service.ExcelDealService;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;

/**
 * 实现导入数据接口
 * 
 * @author user
 * 
 */
@Service
public class ImportData implements IImportData {

	@Autowired
	public ExcelDealService excelDealService;
	@Autowired
	public ImportService service;

	@Override
	public void beforeImport(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception {
		// 更新td_task_load为导入中
		ImportBean importBean = new ImportBean();
		importBean.setTaskId(loadBean.getTaskId());
		importBean.setDataFlag(ExcelDealValues.EXCEL_IMPORT_DEALING);// 处理中
		importBean.setBeforeDataFlag(ExcelDealValues.EXCEL_IMPORT_FORDEAL);// 待处理
		excelDealService.updateTaskLoadStatus(importBean);

	}

	@Override
	public void dealFile(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception {
		/** convert xlsx2txt 、校验文件头与模版中的是否一致 */
		excelDealService.importDataDeal(importExcelBean, loadBean);
	}

	@Override
	public void callProc(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception {
		// 调用过程（临时表转正表，其中包含数据的校验）
		// 主要对监控指标、审批链的调用
		if (ExcelDealValues.TYPE_MON.equals(importExcelBean.getType())) {// 监控指标
			// 调用存储过程 进行详细校验
			service.checkMont(loadBean.getTaskBatchNo());
		} else if (ExcelDealValues.TYPE_APRV.equals(importExcelBean.getType())) {// 审批链
			// 调用存储过程 进行详细校验
			service.checkAprv(loadBean.getTaskBatchNo());
		} else if (ExcelDealValues.USER_ROLE_RLN.equals(importExcelBean.getType())){//用户职责信息
			service.checkUserRoleRln(loadBean.getTaskBatchNo(),"CHECK",loadBean.getInstOper());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void afterImport(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean, ImportBean iBean)
			throws Exception {
		if (iBean.isFlag()) {// 成功
			iBean.setTaskId(loadBean.getTaskId());
			iBean.setDataFlag(ExcelDealValues.EXCEL_IMPORT_SUCC);// 处理成功
			iBean.setProcMemo("处理成功");
			iBean.setBeforeDataFlag(ExcelDealValues.EXCEL_IMPORT_DEALING);// 处理中
			// 更新任务表状态
			excelDealService.updateTaskLoadStatus(iBean);
			if ("03".equals(importExcelBean.getType())) {
				// 更新预算汇总表的状态、 更新ERP_BUDGET_SUM_TOTAL表
				iBean.setTaskBatchNo(loadBean.getTaskBatchNo());
				iBean.setDataFlag(ExcelDealValues.BGT_IMPORT_FORSUBDEAL);// 待提交
				iBean.setBeforeDataFlag(ExcelDealValues.BGT_IMPORT_DEALING);// 处理中
				excelDealService.updateBgtSumTotal(iBean);
			}
		} else {// 失败
			iBean.setTaskId(loadBean.getTaskId());
			iBean.setDataFlag(ExcelDealValues.EXCEL_IMPORT_FAIL);// 处理失败
			iBean.setBeforeDataFlag(ExcelDealValues.EXCEL_IMPORT_DEALING);// 处理中
			iBean.setProcMemo("处理失败");
			iBean.setTaskBatchNo(loadBean.getTaskBatchNo());
			// 更新任务表状态
			excelDealService.updateTaskLoadStatus(iBean);
			if ("03".equals(importExcelBean.getType())) {// 预算
				// 更新ERP_BUDGET_SUM_TOTAL表
				iBean.setDataFlag(ExcelDealValues.BGT_IMPORT_FAIL);// 失败
				iBean.setBeforeDataFlag(ExcelDealValues.BGT_IMPORT_DEALING);// 处理中
				excelDealService.updateBgtSumTotal(iBean);
			} else if ("01".equals(importExcelBean.getType()) || "02".equals(importExcelBean.getType())) {// 审批链或监控指标
				// 更新审批链或监控指标的汇总状态
				iBean.setDataFlag(ExcelDealValues.EXCEL_STATUS_FAIL);// 失败
				iBean.setBeforeDataFlag(ExcelDealValues.EXCEL_STATUS_DEALING);// 处理中
				excelDealService.updateMontAprv(iBean);
			} else if("04".equals(importExcelBean.getType()) ){
				// 更新职责信息的汇总状态
				iBean.setDataFlag(ExcelDealValues.EXCEL_STATUS_FAIL);// 失败
				iBean.setBeforeDataFlag(ExcelDealValues.EXCEL_STATUS_DEALING);// 处理中
				excelDealService.updateUserRoleRln(iBean);
			}
			// 删除掉明细表中的数据
			excelDealService.deleteDataById(loadBean.getTaskBatchNo(), importExcelBean.getTableName());
		}
	}

	@Override
	public String execute(ImportExcelBean importExcelBean, CommonExcelDealBean loadBean) throws Exception {
		this.beforeImport(importExcelBean, loadBean);
		ImportBean iBean = new ImportBean();
		iBean.setFlag(true);
		iBean.setMemo("导入excel处理成功");
		try {
			this.dealFile(importExcelBean, loadBean);
		} catch (Exception e) {
			iBean.setMemo(!Tool.CHECK.isEmpty(e.getCause())?e.getCause().getMessage() : e.getMessage());
			iBean.setFlag(false);
			throw new Exception("导入excel处理失败");
		} finally {
			this.afterImport(importExcelBean, loadBean, iBean);
		}
		if (iBean.isFlag()) {
			try {
				this.callProc(importExcelBean, loadBean);
			} catch (Exception e) {
				e.printStackTrace();
				iBean.setMemo("调用存储过程出错");
				iBean.setFlag(false);
				throw new java.lang.Exception("导入excel处理失败");
			}finally{
				this.afterImport(importExcelBean, loadBean, iBean);
			}
			
		}
		return null;
	}

	@Override
	public void updataStatus(String taskId, String batchNo, String tableName, boolean flag, String memo)
			throws Exception {
		// 可以让子类重写
	}

}
