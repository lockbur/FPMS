package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.imports.bean.CheckedSection;
import com.forms.platform.excel.imports.bean.ExcelImportInfo;
import com.forms.platform.excel.imports.inter.IBusinessDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;
import com.forms.prms.tool.exceltool.service.CommonExcelDealService;
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;
import com.forms.prms.web.budget.budgetplan.service.BudgetPlanService;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.ZsysRoleBean;

//数据迁移测试--ZsysRoleBean数据导入逻辑处理类
public class ZsysRoleBussinessDeal implements IBusinessDeal{
	
	//添加ServiceBean
	DataMigrateService dmService = DataMigrateService.getInstance();
	BudgetPlanService budgetService = BudgetPlanService.getInstance();
	CommonExcelDealService excelDealService = CommonExcelDealService.getInstance();

	@Override
	public void start(String batchNo, String sheetConfigId, Map beans) throws Exception {
		CommonLogger.debug("HQQQ_____IN ZsysRoleBussinessDeal START"+"【流水号："+batchNo+"】");
		
		//1.插入导入数据记录至表UPLOAD_DATA_CONTROL_INFO
//		dmService.addUpDataControlInfo(batchNo,(String)beans.get("instOper"),(String)beans.get("org1Code"),"","");
		
		//2.添加用于保存Excel具体数据的bean
		ExcelBean<Object> bean = new ExcelBean<Object>();
		beans.put("bean", bean);
	}

	@Override
	public void end(String batchNo, String sheetConfigId, Map beans, ExcelImportInfo importInfo) throws Exception {
		CommonLogger.debug("HQQQ_____IN BudgetBusinessDeal END"+"【流水号："+batchNo+"】");
		
		List<String[]> errorMsgLogList = new ArrayList<String[]>();			//保存错误记录(List格式，String[0]保存错误行号，String[1]保存错误信息描述)
		int row;															//错误信息行号
		int col;															//错误信息列号
		String msg;															//校验错误cell单元格校验错误信息
		String value;														//校验错误cell单元格值(该值为从Excel读取到的值)
		int validateErrorCount = 0;											//总的校验错误数量
		int insertSucDataCount = 0;											//成功插入数据库信息数量
		List<String> errRowList = new ArrayList<String>();					//用于保存错误的行号(用于插入数据库时比较该行是否为校验错误，在该List的行号行数据不插入数据库)
		List<CheckedSection> checkSectionList = importInfo.getErrorList();	//错误行List行对象(包含该行中校验的多个错误cell信息)
		boolean insertFlag ;												//校验行数据是否有校验失败信息，当存在校验失败时，该行数据不插入数据库
		
//		System.out.println("【导入END-测试】");
//		System.out.println("导入数据行："+importInfo.getImportCount());
//		System.out.println("是否有错误："+importInfo.isHasError());
		
		for(int i=0;i<checkSectionList.size();i++){
//【处理校验错误信息开始】			
			row = checkSectionList.get(i).getCells().get(0).getRow()+1;				//错误信息行号(从0开始，展示给用户时该值+1)
			errRowList.add(String.valueOf(row-1));									//将错误行号add进errRowList(重置为从0开始)
			int rowErrCount = checkSectionList.get(i).getCells().size();			//该错误行中校验为错误列的数量
			for(int n=0; n<rowErrCount; n++){
				validateErrorCount ++;
				col = checkSectionList.get(i).getCells().get(n).getCol()+1;
				msg = checkSectionList.get(i).getCells().get(n).getErrorMsg();
				value = checkSectionList.get(i).getCells().get(n).getValue();
				errorMsgLogList.add(new String[]{String.valueOf(row),"第"+row+"行-第"+col+"列,校验不通过，具体信息为【"+msg+"--"+value+"】;"});
			}
//【处理校验错误信息结束】					
		}
		
		if("DATAMIGRATE_TEST_TB1".equals(sheetConfigId)){
			//第一张Sheet
			System.out.println("第一张Sheet");
			
			//1.获取导入Excel的具体信息(bean)
			ExcelBean<ZsysRoleBean> bean = (ExcelBean<ZsysRoleBean>)beans.get("bean");
			List<ZsysRoleBean> excelInfoList = bean.getTemplateInfo();
			
			
			for(int i=0;i<excelInfoList.size();i++){
//【处理空值】
//				if(null == excelInfoList.get(i).getRoleName() || "".equals(excelInfoList.get(i).getRoleName())){
//					excelInfoList.get(i).setRoleName("AKB");
//				}
				insertFlag = true;						//初始化值为校验正确
				//判断该行是否为校验错误行
				for(int n=0;n<errRowList.size();n++){
					if(errRowList.get(n).equals(String.valueOf((i+2)))){
						//该行  为校验错误行
						insertFlag = false;
						break;
					}
				}
				if(insertFlag){
					//该行校验无误，可以插入
					dmService.insertZZZSYSROLE(excelInfoList.get(i));
					insertSucDataCount ++;
				}else{
					//【未处理】--可以考虑插入脏数据表(需用户修改的)
				}
			}
			
		}else if("DATAMIGRATE_TEST_TB2".equals(sheetConfigId)){
			//第二张Sheet
			System.out.println("第二张Sheet");
			
			//1.获取导入Excel的具体信息(bean)
			ExcelBean<ZsysRoleBean> bean = (ExcelBean<ZsysRoleBean>)beans.get("bean");
			List<ZsysRoleBean> excelInfoList = bean.getTemplateInfo();
			
			for(ZsysRoleBean zsysRole : excelInfoList){
				dmService.insertZZZSYSROLE(zsysRole);
			}
		}
		System.out.println("错误条数："+validateErrorCount);
		
//【处理导入结果信息-开始】
		CommonExcelDealBean taskInfo = new CommonExcelDealBean();
		UploadDataErrorInfoBean importErrMsgBean = new UploadDataErrorInfoBean();
		
		importErrMsgBean.setBatchNo(batchNo);
		importErrMsgBean.setUploadType(beans.get("uploadType").toString());
		importErrMsgBean.setDataType(beans.get("dataType").toString());
		
		taskInfo.setTaskId(batchNo);
		String procMemo = "";
		if(importInfo.isHasError()){
			if("系统异常终止导入线程"=="1"){
				taskInfo.setDataFlag("02");				//【未处理】处理失败
			}else{
				taskInfo.setDataFlag("03");
				procMemo = "导入校验有误，共插入成功数据"+insertSucDataCount+"条，校验错误条数(不插入)："+validateErrorCount;
				taskInfo.setProcMemo(procMemo);
				for(int i=0;i<errorMsgLogList.size();i++){
					importErrMsgBean.setRowNo(errorMsgLogList.get(i)[0]);
					importErrMsgBean.setErrDesc(errorMsgLogList.get(i)[1]);
					excelDealService.insertUploadDataErrorInfo(importErrMsgBean);
				}
			}
		}else{
			taskInfo.setProcMemo("导入任务成功");
			taskInfo.setDataFlag("03");
		}
		excelDealService.updateLoadResult(taskInfo);			//更新导入任务状态
		System.out.println("导入完毕");
		
		if(importInfo.isHasError()&&(insertSucDataCount == 0)){
			dmService.updateUpDataConInfoDataFlag("02", batchNo);
		}else{
			dmService.updateUpDataConInfoDataFlag("01", batchNo);
		}
//【处理导入结果信息-结束】		

//		budgetService.updateTaskLoadStatus(batchNo, "03");
	}

}
