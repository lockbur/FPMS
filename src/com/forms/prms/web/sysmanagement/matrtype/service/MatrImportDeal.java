package com.forms.prms.web.sysmanagement.matrtype.service;

import java.util.List;
import java.util.Map;

import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.platform.excel.imports.inter.IBusinessDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;
import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;


public class MatrImportDeal implements IBusinessDeal {

	// 添加ServiceBean
	MatrTypeService service = MatrTypeService.getInstance();
	CommonExcelDealService excelDealService = CommonExcelDealService
			.getInstance();

	@Override
	public void start(String batchNo, String sheetConfigId, Map beans)
			throws Exception {

		//.添加用于保存Excel具体数据的bean
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

		ExcelBean<MatrType> excelBean = (ExcelBean<MatrType>) beans
				.get("bean");
		List<MatrType> matrTypes = excelBean.getTemplateInfo();
		CommonExcelDealBean taskInfo = new CommonExcelDealBean();
		taskInfo.setTaskId(batchNo);
		try {
				if (matrTypes!=null&&matrTypes.size()>0) {
					service.importMt(matrTypes);

					taskInfo.setDataFlag("03");											//该Task任务状态保存为"03"处理成功
					taskInfo.setProcMemo("导入成功");
					excelDealService.updateLoadResult(taskInfo);						//更新Task的最终导入任务
					excelDealService.updateLoadTaskProcMemo(taskInfo);					//更新Task的导入操作备注
				} 
					
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			taskInfo.setDataFlag("02");											//捕获异常，该Task任务状态保存为"02"处理失败
			taskInfo.setProcMemo("导入失败 请检查数据后重试！");
			excelDealService.updateLoadResult(taskInfo);						//更新Task的最终导入任务
			excelDealService.updateLoadTaskProcMemo(taskInfo);					//更新Task的导入操作备注
		}

		System.out.println("导入完毕");
	}

	

}
