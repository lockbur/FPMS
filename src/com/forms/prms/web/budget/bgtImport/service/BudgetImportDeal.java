package com.forms.prms.web.budget.bgtImport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.imports.bean.CheckedSection;
import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.platform.excel.imports.inter.IBusinessDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.web.budget.bgtImport.domain.BudgetImportBean;
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;

public class BudgetImportDeal implements IBusinessDeal{

	@Override
	public void start(String batchNo, String sheetConfigId, Map beans)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(String batchNo, String sheetConfigId, Map beans,
			ExcelImportInfo importInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}
//
//	// 添加ServiceBean
//	BudgetImportService service = BudgetImportService.getInstance();
//	CommonExcelDealService excelDealService = CommonExcelDealService
//			.getInstance();
//
//	@Override
//	public void start(String batchNo, String sheetConfigId, Map beans)
//			throws Exception {
//
//		// 2.添加用于保存Excel具体数据的bean
//		ExcelBean<Object> bean = new ExcelBean<Object>();
//		beans.put("bean", bean);
//	}
//
//	/**
//	 * 
//	 * @param batchNo
//	 *            这个是task的批次
//	 * @param sheetConfigId
//	 * @param beans
//	 * @param importInfo
//	 * @throws Exception
//	 */
//	@Override
//	public void end(String batchNo, String sheetConfigId, Map beans,
//			ExcelImportInfo importInfo) throws Exception {
//		String importBatchNo = (String) beans.get("impBatch");
//		// 开始进行错误信息处理
//		int errRowCount = dealErrorMsg(importInfo, importBatchNo);
//
//		int insertSucDataCount = 0;
//		// 5-1.更新导入任务信息
//		CommonExcelDealBean taskInfo = new CommonExcelDealBean();
//		taskInfo.setTaskId(batchNo);
//		try {
//			if (importInfo.isHasError()) {
//				taskInfo.setProcMemo("导入校验有误,校验错误行信息数(不插入)：" + errRowCount);
//				// 有错误数据 就修改汇总状态
//				service.updateStatus(importBatchNo, "01","02",taskInfo.getProcMemo());
//				ExcelBean<BudgetImportBean> excelBean = (ExcelBean<BudgetImportBean>) beans
//						.get("bean");
//				List<BudgetImportBean> excelInfoList = excelBean.getTemplateInfo();
//			} else {
//				// 导入数据到临时表
//				try {
//					this.insertDetail(beans, insertSucDataCount);
//					service.updateStatus(importBatchNo, "01", "03","");
//				} catch (Exception e) {
//					//导入失败
//					service.updateStatus(importBatchNo, "01","02","");
//					e.printStackTrace();
//				}
//				taskInfo.setProcMemo("导入任务成功,共插入成功数据" + insertSucDataCount + "条");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			CommonLogger.error("预算导入失败");
//			service.updateStatus(importBatchNo, "01","02","");
//		}
//		
//		taskInfo.setDataFlag("03");
//		excelDealService.updateLoadResult(taskInfo); // 更新导入任务状态
//		System.out.println("导入完毕");
//	}
//
//	/**
//	 * 导入明细数据
//	 * 
//	 * @param excelInfoList
//	 * @param beans
//	 * @param insertSucDataCount
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	private void insertDetail(Map beans, int insertSucDataCount) {
//		// 1.获取导入Excel的具体信息(bean)
//		ExcelBean<BudgetImportBean> excelBean = (ExcelBean<BudgetImportBean>) beans
//				.get("bean");
//		List<BudgetImportBean> excelInfoList = excelBean.getTemplateInfo();
//
//		List<BudgetImportBean> budgetBeans = new ArrayList<BudgetImportBean>();
//		if (null != excelInfoList && excelInfoList.size() > 0) {
//			BudgetImportBean budgetBean = null;
//			for (int i = 0; i < excelInfoList.size(); i++) {
//				budgetBean = excelInfoList.get(i);
//				budgetBean.setSeq(i + "");
//				budgetBean.setBatchNo((String) beans.get("impBatch"));
//				budgetBean.setExcelNo(i + 2 + "");
//				budgetBeans.add(budgetBean);
//				if (i > 0 && i % 500 == 0) {
//
//					service.insertDetail(budgetBeans);
//					budgetBeans = new ArrayList<BudgetImportBean>();
//				}
//				insertSucDataCount++;
//			}
//			if (null != budgetBeans && budgetBeans.size() > 0) {
//				service.insertDetail(budgetBeans);
//			}
//		}
//	}
//
//	public int dealErrorMsg(ExcelImportInfo importInfo, String impBatchNo) {
//		List<Map<String, Object>> errorMsgLogList = new ArrayList<Map<String, Object>>(); // 保存错误记录(List格式，String[0]保存错误行号，String[1]保存错误信息描述)
//		int row; // 错误信息行号
//		int col; // 错误信息列号
//		String msg; // 校验错误cell单元格校验错误信息
//		String value; // 校验错误cell单元格值(该值为从Excel读取到的值)
//		List<String> errRowList = new ArrayList<String>(); // 用于保存错误的行号(用于插入数据库时比较该行是否为校验错误，在该List的行号行数据不插入数据库)
//		List<CheckedSection> checkSectionList = importInfo.getErrorList(); // 错误行List行对象(包含该行中校验的多个错误cell信息)
//		String checkedSectionType = "";
//		if (importInfo.isHasError()) {
//			UploadDataErrorInfoBean importErrMsgBean = new UploadDataErrorInfoBean();
//			// 5-2.插入导入时校验错误的信息
//			importErrMsgBean.setBatchNo(impBatchNo);
//			for (int i = 0; i < checkSectionList.size(); i++) {
//				// 【处理校验错误信息开始】
//				row = checkSectionList.get(i).getCells().get(0).getRow() + 1; // 错误信息行号(从0开始，展示给用户时该值+1)
//				col = checkSectionList.get(i).getCells().get(0).getCol() + 1;
//				msg = checkSectionList.get(i).getCells().get(0).getErrorMsg();
//				value = checkSectionList.get(i).getCells().get(0).getValue();
//
//				errRowList.add(String.valueOf(row - 1)); // 将错误行号add进errRowList(重置为从0开始)
//				// int rowErrCount = checkSectionList.get(i).getCells().size();
//				// //该错误行中校验为错误列的数量
//
//				if (row<=2) {
//					// b表头校验错误
//					checkedSectionType = "01";// 代表该校验区域为表头校验区域(XML中第一个Section区)
//					importErrMsgBean.setSectionType(checkedSectionType);
//					importErrMsgBean.setErrDesc("【Sheet表头校验错误】：第" + row + "行-第"
//							+ col + "列表头信息不符，【" + msg + "--Cell的实际值为：" + value
//							+ "】;");
//				} else {
//					// 处理主体信息校验的错误描述
//					checkedSectionType = "02";// 代表该校验区域为主体信息校验区域(XML中第二个Section区)
//					importErrMsgBean.setSectionType(checkedSectionType);
//					importErrMsgBean.setErrDesc("第" + row + "行-第" + col
//							+ "列,校验不通过，具体信息为【" + msg + "--Cell的实际值为：" + value
//							+ "】;");
//				}
//				importErrMsgBean.setRowNo(String.valueOf(row));
//				importErrMsgBean.setDataType("1");
//				importErrMsgBean.setUploadType("05");//05代表预算
//				try {
//					excelDealService.insertUploadDataErrorInfo(importErrMsgBean);
//				} catch (Exception e) {
//					e.printStackTrace();
//					CommonLogger.error("导入的预算校验出现错误!");
//				}finally{
//					service.updateStatus(impBatchNo, "01","02","");
//				}
//				
//
//			}
//		}
//		if (null != errRowList && errRowList.size() > 0) {
//			return errRowList.size();
//		} else {
//			return 0;
//		}
//
//	}
//
}
