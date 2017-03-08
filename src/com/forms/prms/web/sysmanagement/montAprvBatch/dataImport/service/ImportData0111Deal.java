package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.imports.bean.CheckedSection;
import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.platform.excel.imports.inter.IBusinessDeal;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.AprvChainBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontBean;

//【监控指标】--付款数据Excel数据导入逻辑前后处理类
public class ImportData0111Deal implements IBusinessDeal {

	// 添加ServiceBean
	ImportService service = ImportService.getInstance();
	CommonExcelDealService excelDealService = CommonExcelDealService
			.getInstance();

	@Override
	public void start(String batchNo, String sheetConfigId, Map beans)
			throws Exception {
		// 2.添加用于保存Excel具体数据的bean
		ExcelBean<Object> bean = new ExcelBean<Object>();
		beans.put("bean", bean);
	}

	/**
	 * 
	 * @param batchNo
	 *            这个是task的批次
	 * @param sheetConfigId
	 * @param beans
	 * @param importInfo
	 * @throws Exception
	 */
	@Override
	public void end(String batchNo, String sheetConfigId, Map beans,
			ExcelImportInfo importInfo) throws Exception {
		String importBatchNo = (String) beans.get("impBatch");
		String proType = (String) beans.get("proType");
		// 开始进行错误信息处理
		int errRowCount = dealErrorMsg(importInfo, importBatchNo, proType);

		int insertSucDataCount = 0;
		// 5-1.更新导入任务信息
		CommonExcelDealBean taskInfo = new CommonExcelDealBean();
		taskInfo.setTaskId(batchNo);
		try {
			if (importInfo.isHasError()) {
				taskInfo.setProcMemo("导入校验有误,校验错误行信息数(不插入)：" + errRowCount);
				// 有错误数据 就修改监控指标和审批链 汇总表的状态
//				service.updateExcelStatus((String) beans.get("impBatch"), MontAprvType.EXCEL_E3);
//				ExcelBean<MontBean> excelBean = (ExcelBean<MontBean>) beans
//						.get("bean");
//				List<MontBean> excelInfoList = excelBean.getTemplateInfo();
			} else {
				// 导入数据到临时表
				this.insertDetail(beans, insertSucDataCount);
				String org1Code = (String) beans.get("org1Code");
				if ("01".equals(beans.get("proType"))) {
					// 调用存储过程 进行详细校验
					service.checkMont(importBatchNo);
				} else if ("02".equals(beans.get("proType"))) {
					// 调用存储过程 进行详细校验
					service.checkAprv(importBatchNo);
				}
				taskInfo.setProcMemo("导入任务成功,共插入成功数据" + insertSucDataCount + "条");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			String memo="在导入监控指标或者审批链数据的时候程序运行出现异常";
			service.updateError(importBatchNo, MontAprvType.EXCEL_E1,memo);
			CommonLogger.error(memo+"批次号是:"+importBatchNo);
		}
		
		taskInfo.setDataFlag("03");
		excelDealService.updateLoadResult(taskInfo); // 更新导入任务状态
		System.out.println("导入完毕");
	}

	/**
	 * 导入明细数据
	 * 
	 * @param excelInfoList
	 * @param beans
	 * @param insertSucDataCount
	 */
	@Transactional(rollbackFor = Exception.class)
	private void insertDetail(Map beans, int insertSucDataCount) {
		// 1.获取导入Excel的具体信息(bean)
		if ("01".equals(beans.get("proType"))) {
			// 监控指标
			ExcelBean<MontBean> excelBean = (ExcelBean<MontBean>) beans
					.get("bean");
			List<MontBean> excelInfoList = excelBean.getTemplateInfo();

			List<MontBean> montBeans = new ArrayList<MontBean>();
			if (null != excelInfoList && excelInfoList.size() > 0) {
				MontBean montBean = null;
				String instType = (String) beans.get("instType");
				for (int i = 0; i < excelInfoList.size(); i++) {
					montBean = excelInfoList.get(i);
					montBean.setSeq(i + "");
					montBean.setBatchNo((String) beans.get("impBatch"));
					montBean.setMontType(beans.get("subType").toString());
					montBean.setExcelNo(i + 3 + "");
					montBean.setOrg21Code(beans.get("org21Code").toString());
					montBeans.add(montBean);
					if (i > 0 && i % 500 == 0) {

						service.insertMontDetail(montBeans);
						montBeans = new ArrayList<MontBean>();
					}
					insertSucDataCount++;
				}
				if (null != montBeans && montBeans.size() > 0) {
					service.insertMontDetail(montBeans);
				}
			}
		} else if ("02".equals(beans.get("proType"))) {
			// 审批链
			ExcelBean<AprvChainBean> excelBean = (ExcelBean<AprvChainBean>) beans
					.get("bean");
			List<AprvChainBean> excelInfoList = excelBean.getTemplateInfo();

			List<AprvChainBean> aprvBeans = new ArrayList<AprvChainBean>();
			if (null != excelInfoList && excelInfoList.size() > 0) {
				AprvChainBean aprvBean = null;
				for (int i = 0; i < excelInfoList.size(); i++) {
					aprvBean = excelInfoList.get(i);
					aprvBean.setSeq(i + "");
					aprvBean.setBatchNo((String) beans.get("impBatch"));
					aprvBean.setExcelNo(i + 2 + "");
					aprvBeans.add(aprvBean);
					if (i > 0 && i % 500 == 0) {
						service.insertAprvDetail(aprvBeans);
						aprvBeans = new ArrayList<AprvChainBean>();
					}
					insertSucDataCount++;
				}
				if (null != aprvBeans && aprvBeans.size() > 0) {
					service.insertAprvDetail(aprvBeans);
				}
			}
		}

	}

	public int dealErrorMsg(ExcelImportInfo importInfo, String impBatchNo,
			String proType) {
		List<Map<String, Object>> errorMsgLogList = new ArrayList<Map<String, Object>>(); // 保存错误记录(List格式，String[0]保存错误行号，String[1]保存错误信息描述)
		int row; // 错误信息行号
		int col; // 错误信息列号
		String msg; // 校验错误cell单元格校验错误信息
		String value; // 校验错误cell单元格值(该值为从Excel读取到的值)
		List<String> errRowList = new ArrayList<String>(); // 用于保存错误的行号(用于插入数据库时比较该行是否为校验错误，在该List的行号行数据不插入数据库)
		List<CheckedSection> checkSectionList = importInfo.getErrorList(); // 错误行List行对象(包含该行中校验的多个错误cell信息)
		String checkedSectionType = "";
		if (importInfo.isHasError()) {
			UploadDataErrorInfoBean importErrMsgBean = new UploadDataErrorInfoBean();
			// 5-2.插入导入时校验错误的信息
			importErrMsgBean.setBatchNo(impBatchNo);
			for (int i = 0; i < checkSectionList.size(); i++) {
				// 【处理校验错误信息开始】
				row = checkSectionList.get(i).getCells().get(0).getRow() + 1; // 错误信息行号(从0开始，展示给用户时该值+1)
				col = checkSectionList.get(i).getCells().get(0).getCol() + 1;
				msg = checkSectionList.get(i).getCells().get(0).getErrorMsg();
				value = checkSectionList.get(i).getCells().get(0).getValue();

				errRowList.add(String.valueOf(row - 1)); // 将错误行号add进errRowList(重置为从0开始)
				// int rowErrCount = checkSectionList.get(i).getCells().size();
				// //该错误行中校验为错误列的数量

				if (row<=2) {
					// b表头校验错误
					checkedSectionType = "01";// 代表该校验区域为表头校验区域(XML中第一个Section区)
					importErrMsgBean.setSectionType(checkedSectionType);
					importErrMsgBean.setErrDesc("【Sheet表头校验错误】：第" + row + "行-第"
							+ col + "列表头信息不符，【" + msg + "--Cell的实际值为：" + value
							+ "】;");
				} else {
					// 处理主体信息校验的错误描述
					checkedSectionType = "02";// 代表该校验区域为主体信息校验区域(XML中第二个Section区)
					importErrMsgBean.setSectionType(checkedSectionType);
					importErrMsgBean.setErrDesc("第" + row + "行-第" + col
							+ "列,校验不通过，具体信息为【" + msg + "--Cell的实际值为：" + value
							+ "】;");
				}
				importErrMsgBean.setRowNo(String.valueOf(row));
				importErrMsgBean.setDataType("1");
				String dataType = "";
				String typeName="";
				if ("01".equals(proType)) {
					// 监控指标
					dataType = "03";
					typeName = "监控指标";
				} else if ("02".equals(proType)) {
					// 审批链
					dataType = "04";
					typeName = "审批链";
				}
				importErrMsgBean.setUploadType(dataType);
				try {
					excelDealService.insertUploadDataErrorInfo(importErrMsgBean);
					service.updateExcelStatus(impBatchNo, MontAprvType.EXCEL_E0,MontAprvType.EXCEL_E3);
				} catch (Exception e) {
					service.updateError(impBatchNo, MontAprvType.EXCEL_E1,"校验出错，但是在保存错误信息时程序出错");
					e.printStackTrace();
					CommonLogger.error(typeName+"导入的excel校验出现错误!");
				}
				

				// 【处理校验错误信息结束】
			}
		}
		if (null != errRowList && errRowList.size() > 0) {
			return errRowList.size();
		} else {
			return 0;
		}

	}

}
