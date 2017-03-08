package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.service;

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
import com.forms.prms.web.budget.budgetplan.domain.ExcelBean;
import com.forms.prms.web.budget.budgetplan.service.BudgetPlanService;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntFqfkBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntMatrInfoBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.CntTenancyCondiBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.UploadDataControlInfoBean;

/**
 * Title:			CntDataBussinessDeal
 * Description:		【数据迁移】--合同数据Excel数据导入逻辑前后处理类
 * Copyright: 		formssi
 * @author: 		HQQ
 * @project: 		ERP
 * @date: 			日期
 * @version: 		1.0
 */
public class CntDataBussinessDeal implements IBusinessDeal{
	
	//添加ServiceBean
	DataMigrateService dmService = DataMigrateService.getInstance();
	BudgetPlanService budgetService = BudgetPlanService.getInstance();
	CommonExcelDealService excelDealService = CommonExcelDealService.getInstance();
	
	@Override
	public void start(String batchNo, String sheetConfigId, Map beans) throws Exception {
		CommonLogger.debug("HQQQ_____IN CntDataBussinessDeal START"+"【任务流水号："+batchNo+"】");
		//0.将Excel文件的路径传递进来，检测该文件的XXX，如果有误，则不执行后面的了！！！
		
		
		
		
		//1.添加用于保存Excel具体数据的bean
		ExcelBean<Object> bean = new ExcelBean<Object>();
		beans.put("bean", bean);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 *	Excel解析后处理逻辑：
	 *		1.设置常量值等
	 *		2.处理配置校验错误信息(取得错误行列值ErrMsg等，插入到表UPLOAD_DATA_ERROR_INFO中)，最后返回错误的行信息(用于后续是否插入Sheet中具体行到正式表)
	 *		3.校验错误行是否超标，根据该行是否为错误行标识，从而决定是否插入正式表
	 *		4.判断当  当前Deal处理最后一个导入Sheet时，根据校验信息决定是否继续调用存储过程进行校验/更新Control表的状态，更新导入任务Task的状态信息
	 */
	public void end(String batchNo, String sheetConfigId, Map beans, ExcelImportInfo importInfo) throws Exception {
		int errorPartFlag = 0;
		
		CommonLogger.debug("HQQQ_____IN CntDataBussinessDeal END"+"【任务流水号："+batchNo+"】");
		System.out.println("【HQQ-当次合同数据模板的ConfigId为：】"+sheetConfigId);
		
		//1.定义用于后续处理的初始缓存值
		String uploadType = "01";											//[常量值]合同Excel模板
		String impBatchNo = (String)beans.get("impBatch");					//[参数值]从参数中取出当次导入任务的导入批次号
		String dataType = ("DM_CNTINFO".equals(sheetConfigId) ? "0101" : ("DM_CNTMATR".equals(sheetConfigId) ? "0102" :("DM_CNTFK".equals(sheetConfigId) ? "0103" : ("DM_CNTTENANCY".equals(sheetConfigId) ? "0104" : "0105"))));
		List<String> errRowList;											//[存储数据值]用于保存错误的行号(用于插入数据库时比较该行是否为校验错误，在该List的行号行数据不插入数据库)
		
		//2.处理错误数据并获取返回的错误信息(errRowList:错误行列表 、 allErrRowCount:校验错误总行数   ————用于后续处理)
		errRowList = this.dealTheValiErrorRow( importInfo, impBatchNo , uploadType , dataType );
		int allErrRowCount = errRowList.size();								//配置校验的错误行总行数(会自增)
		
		//3.处理合同数据Excel中各个Sheets的信息导入
		try {
			if("DM_CNTINFO".equals(sheetConfigId)){
				//3-1.获取导入Excel[合同信息Sheet]的具体信息(bean)
				ExcelBean<CntInfoBean> bean = (ExcelBean<CntInfoBean>)beans.get("bean"); 
				List<CntInfoBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
				System.out.println("【HQQ-合同信息表插入数据完成！】");
			}else if("DM_CNTMATR".equals(sheetConfigId)){
				//合同物料信息Sheet导入
				ExcelBean<CntMatrInfoBean> bean = (ExcelBean<CntMatrInfoBean>)beans.get("bean");
				List<CntMatrInfoBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
				System.out.println("【HQQ-合同物料信息表插入数据完成！】");
			}else if("DM_CNTFK".equals(sheetConfigId)){
				//合同分期付款Sheet导入
				ExcelBean<CntFqfkBean> bean = (ExcelBean<CntFqfkBean>)beans.get("bean");
				List<CntFqfkBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
				System.out.println("【HQQ-合同分期付款信息表插入数据完成！】");
			}else if("DM_CNTTENANCY".equals(sheetConfigId)){
				//租金递增条件Sheet导入
				ExcelBean<CntTenancyCondiBean> bean = (ExcelBean<CntTenancyCondiBean>)beans.get("bean");
				List<CntTenancyCondiBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
				System.out.println("【HQQ-合同资金递增条件信息表插入数据完成！】");
			}
			System.out.println("【HQQ-合同数据-Excel表校验正确数据插入["+sheetConfigId+"]完成！】");
//			System.out.println("校验产生的错误条数："+validateErrorCount);
		} catch (Exception e) {
			CommonLogger.error("CntDataBussinessDeal-[method:end]发生异常，于步骤3[处理合同数据Excel中各个Sheets的信息导入]时，请检查插入数据是否为空！");
			errorPartFlag = 3;
			catchExceptionHandle(beans, batchNo, errorPartFlag);		//参数：Map beans , String taskId , int errorPartFlag
		}
		
		
		//4.当当前处理的sheetConfigId为Excel模板中最后一个Sheet时，根据导入pay数据决定更新Task状态和是否调存储过程校验或更新Control状态
		if(sheetConfigId.equals(beans.get("lastConfigId"))){
			//【Part1】.处理当次导入任务的状态
			CommonExcelDealBean taskInfo = new CommonExcelDealBean();
			taskInfo.setTaskId(batchNo);
			taskInfo.setDataFlag("03");
			excelDealService.updateLoadResult(taskInfo);	
			
			//【Part2】.调用存储过程开始数据确认校验或者直接更新Control状态（在存储过程或直接将Control表的DataFlag状态更改为01）
			UploadDataControlInfoBean udcBean = dmService.getRelTaskIdByBatchTaskId(batchNo);
			String batchTaskPayId = udcBean.getTaskPayId();
			//if另一个Task已完成，则执行存储过程，否则将当前的Task的状态置为3(上面代码已更新)   		————根据taskId找到batchNo
			if("03".equals(excelDealService.getLoadTaskByTaskId(batchTaskPayId).getDataFlag())){
				if( dmService.getUpDataErrByBatchNo(udcBean.getBatchNo()).size() > 0 ){
					//如果配置校验错误行校验有错误数据，则不调存储过程校验，直接将Control表信息状态改为01
					dmService.updateUpDataConInfoDataFlag("01", (String)beans.get("impBatch"));	
				}else{
					//配置校验无错误数据，调用存储过程校验，最后更新Control表状态为01
					dmService.callInitMainCheck(beans.get("impBatch").toString());	
				}
			}
			
			//【Part3】处理获取成功插入数据条数+校验错误数据条数  + 更新导入任务的备注描述等
			CommonExcelDealBean upTaskMemoBean = new CommonExcelDealBean();
//			int currTaskInsertSuccCount = dmService.getCntSuccImpCountByBatchNo(beans.get("impBatch").toString());
			int currTaskSheetHeadErrCount =  dmService.getUpDataErrCountByBatchAndUpType(beans.get("impBatch").toString(), "01" , "01");
			int currTaskValidateErrCount  =  dmService.getUpDataErrCountByBatchAndUpType(beans.get("impBatch").toString(), "01" , "02");
			String procMemo = "";
			//根据是否有校验错误数据，填写导入任务的备注信息
			if( (currTaskSheetHeadErrCount + currTaskValidateErrCount ) > 0 ){
				//有校验错误信息
				procMemo = "导入校验有误;";
				if(currTaskSheetHeadErrCount > 0){
					procMemo += "Excel表头校验有误;";
				}
//				if(currTaskValidateErrCount > 0){
//					procMemo += ";校验有错误(不插入)：["+currTaskValidateErrCount+"]行";
//				}
//				procMemo += ";共插入[合同数据]有效性数据["+currTaskInsertSuccCount+"]行";
			}else{
				//无校验错误信息
				procMemo = "导入任务成功,校验无错误数据;";		//共插入[合同数据]有效性数据["+currTaskInsertSuccCount+"]行";
			}
			upTaskMemoBean.setTaskId(batchNo);
			upTaskMemoBean.setProcMemo(procMemo);
			//更新导入任务的最终状态和导入备注描述
			excelDealService.updateLoadTaskProcMemo(upTaskMemoBean);
			System.out.println("CNT[合同数据]最后一个Sheet【"+sheetConfigId+"】导入完毕");
			CommonLogger.debug("【数据迁移】-合同数据导入前后逻辑处理--CntDataBussinessDeal导入完毕，当前执行为导入模板最后一个Sheet：["+sheetConfigId+"]");
		}
	}
	
	/**
	 * @methodName catchExceptionHandle
	 * 			描述：捕获到异常Exception时的公共处理(1.记录错误信息	2.更新Control导入批次状态	3.更新导入任务Task状态)
	 * @param beans
	 * @param taskId
	 * @param errorPartFlag(保存错误的部分PartX , part3:代表为将Excel中数据行导入DB时发生错误)
	 */
	public void catchExceptionHandle( Map beans , String taskId , int errorPartFlag){
		//1.Take Error Log Info
		CommonLogger.error("CntDataBussinessDeal-[method:end]发生异常，于步骤Part{"+errorPartFlag+"}时发生异常，请根据错误Part查找错误原因！");
		//2.更新Control表的状态
		dmService.updateUpDataConInfoDataFlag("01", (String)beans.get("impBatch"));			//catch异常，更新Control表的状态
		//3.更新导入任务Task的状态
		CommonExcelDealBean taskInfo = new CommonExcelDealBean();
		taskInfo.setTaskId(taskId);
		taskInfo.setDataFlag("02");											//捕获异常，该Task任务状态保存为"02"处理失败
		taskInfo.setProcMemo("该导入任务在导入过程中捕获到异常，已终止！");
		excelDealService.updateLoadResult(taskInfo);						//更新Task的最终导入任务
		excelDealService.updateLoadTaskProcMemo(taskInfo);					//更新Task的导入操作备注
	}
	
	
	
	/**
	 * @methodName validataAndInsertDataToTb
	 * 		方法描述：【公共方法】根据传入参数的ListBean(类型不确定List<?>)，判断该行是否为错误行，如果非错误行，则将数据插入到bean对应的表中
	 * @param impSheetInfoList
	 * @param errRowList
	 * @param dataType
	 * @param impBatchNo
	 */
	@Transactional(rollbackFor = Exception.class)
	public void validataAndInsertDataToTb(List<?> impSheetInfoList , List<String> errRowList ,  String impBatchNo , String uploadType , String dataType){
		List<CntInfoBean> cntInfoList = new ArrayList<CntInfoBean>();
		List<CntMatrInfoBean> cntMatrInfoList = new ArrayList<CntMatrInfoBean>();
		List<CntFqfkBean> cntFqfkList = new ArrayList<CntFqfkBean>();
		List<CntTenancyCondiBean> cntTenancyList = new ArrayList<CntTenancyCondiBean>();

		System.out.println("【validataAndInsertDataToTb----我是："+dataType+"】");
		int sheetHeadIndex = 2;						//Sheet的表头行默认占位行数=(表头信息默认占2行)
		boolean insertFlag ;						//校验行数据是否有校验失败信息，当存在校验失败时，该行数据不插入数据库
		//判断当前行是否信息校验错误行，若是则不插入该数据到数据库
		for(int i=0;i<impSheetInfoList.size();i++){
			insertFlag = true;
			for(int n=0;n<errRowList.size();n++){
				if(errRowList.get(n).equals(String.valueOf(i+sheetHeadIndex))){									//判断时使用i从0开始(因为errRowList下标也是从0开始的)
					insertFlag = false;	//该行为错误行,不插入
					break;
				}
			}
			
			//该行为非校验错误行，设置bean的公共属性，保存bean并保存到表中
			if(insertFlag){
				if("0101".equals(dataType)){
					((CntInfoBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((CntInfoBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));		//设置真实行号(i从0开始，实际上是第一行，所以+1后再加表头占用行sheetHeadIndex)
					((CntInfoBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((CntInfoBean)impSheetInfoList.get(i)).setDataType(dataType);
					
					cntInfoList.add((CntInfoBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
//						dmService.insertToTbCNTDATA((CntInfoBean)impSheetInfoList.get(i)); 
						dmService.insertToTbCNTDATA(cntInfoList); 
						cntInfoList = new ArrayList<CntInfoBean>();
					}
				}else if("0102".equals(dataType)){
					((CntMatrInfoBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((CntMatrInfoBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));
					((CntMatrInfoBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((CntMatrInfoBean)impSheetInfoList.get(i)).setDataType(dataType);
					
					cntMatrInfoList.add((CntMatrInfoBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
						dmService.insertToTDCNTDEVICE(cntMatrInfoList);
						cntMatrInfoList = new ArrayList<CntMatrInfoBean>();
					}
				}else if("0103".equals(dataType)){
					((CntFqfkBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((CntFqfkBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));
					((CntFqfkBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((CntFqfkBean)impSheetInfoList.get(i)).setDataType(dataType);
					
					cntFqfkList.add((CntFqfkBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
						dmService.insertToTDCNTFK(cntFqfkList);
						cntFqfkList = new ArrayList<CntFqfkBean>();
					}
				}else if("0104".equals(dataType)){
					((CntTenancyCondiBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((CntTenancyCondiBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));
					((CntTenancyCondiBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((CntTenancyCondiBean)impSheetInfoList.get(i)).setDataType(dataType);
					
					cntTenancyList.add((CntTenancyCondiBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
						dmService.insertToTDCNTTENANCY(cntTenancyList);
						cntTenancyList = new ArrayList<CntTenancyCondiBean>();
					}
				}else{
					System.out.println("【找不到需要插入的对应表和Bean对象】");
				}
			}else{
				//【未处理】--可以考虑插入脏数据表(需手动处理插入值信息修改的)
			}
		}
		
		//处理不够500行数据的最后插入--到数据库
		if("0101".equals(dataType)){
			if( cntInfoList.size()>0 && null != cntInfoList ){
				dmService.insertToTbCNTDATA(cntInfoList); 
			}
		}else if("0102".equals(dataType)){
			if( cntMatrInfoList.size()>0 && null != cntMatrInfoList ){
				dmService.insertToTDCNTDEVICE(cntMatrInfoList); 
			}
		}else if("0103".equals(dataType)){
			if( cntFqfkList.size()>0 && null != cntFqfkList ){
				dmService.insertToTDCNTFK(cntFqfkList); 
			}
		}else if("0104".equals(dataType)){
			if( cntTenancyList.size()>0 && null != cntTenancyList ){
				dmService.insertToTDCNTTENANCY(cntTenancyList); 
			}
		}
		
		
	}
	
	
	/**
	 * @methodName dealTheValiErrorRow
	 *		方法描述：处理错误数据的插入并返回错误行信息 
	 * @param importInfo
	 * @param impBatchNo
	 * @param uploadType
	 * @param dataType
	 * @return
	 */
	public List<String> dealTheValiErrorRow( ExcelImportInfo importInfo , String impBatchNo , String uploadType ,String dataType){
		int row;															//错误信息行号(保存值为Excel实际行)
		int col;															//错误信息列号(保存值为Excel实际列)
		String msg;															//校验错误cell单元格校验错误信息
		String value;														//校验错误cell单元格值(该值为从Excel读取到的值)
		List<CheckedSection> checkSectionList = importInfo.getErrorList();	//错误行List行对象(包含该行中校验的多个错误cell信息)
		List<String> errRowList = new ArrayList<String>();					//用于保存错误的行号(用于插入数据库时比较该行是否为校验错误，在该List的行号行数据不插入数据库)
		String checkedSectionType = "";										//XML配置校验的类型(01:表头校验Section、02:主体信息校验Section)
		
		//公共处理错误信息
		if(importInfo.isHasError()){
			//当配置校验有错误数据时，将校验错误信息插入表UPLOAD_DATA_ERROR_INFO中
			UploadDataErrorInfoBean importErrMsgBean = new UploadDataErrorInfoBean();
			importErrMsgBean.setBatchNo(impBatchNo);									//每个均为同一导入批次
			importErrMsgBean.setUploadType(uploadType);									//每个Sheet均为同一Excel模板(此处合同为"01")
			importErrMsgBean.setDataType(dataType);										//dataType根据Sheet的类别不同而变化
			//【处理校验错误信息开始】（拼接记录每条错误信息的行号和Memo、总错误条数）
			for(int i=0;i<checkSectionList.size();i++){
				row = checkSectionList.get(i).getCells().get(0).getRow()+1;				//错误信息行号(从0开始，展示给用户时该值+1)		[应为同一行中多个cell的row都是同样的，所以这里取get(0)就可以了]
				col = checkSectionList.get(i).getCells().get(0).getCol()+1;				//错误行中第一个错误列的错误信息(//同行中若存在多个校验错误信息，只记录行中第一个Cell的错误信息（代码中为get(0)表示）)
				msg = checkSectionList.get(i).getCells().get(0).getErrorMsg();			//该校验错误Cell的错误信息描述ErrMsg
				value = checkSectionList.get(i).getCells().get(0).getValue();			//该校验错误Cell中的实际文本值value
				
				errRowList.add(String.valueOf(row-1));									//将错误行号add进errRowList(重置为从0开始)
				
				importErrMsgBean.setRowNo(String.valueOf(row));							//Sheet中真实的错误行号
//				if( 0 == i ){															
				if( row <= 2 ){															//2是当前导入Excel中的表头行数	
					//CheckedSection1(当校验错误的行数小于表头行时，认为是表头校验错误)配置为表头信息校验(根据配置section的位置改变应相应发生改变)
					int sheetHeadErrorColCount = checkSectionList.get(i).getCells().size();			//Sheet表头错误列数统计(【统计第一行表头，都有多少个校验错误列】)
					CommonLogger.info("【Sheet表头校验】该表头行共有校验错误列为："+sheetHeadErrorColCount+"列！");
					//处理表头校验的错误描述
					checkedSectionType = "01";//代表该校验区域为表头校验区域(XML中第一个Section区)
					importErrMsgBean.setSectionType(checkedSectionType);
					importErrMsgBean.setErrDesc("【Sheet表头校验错误】：第"+row+"行-第"+col+"列表头信息不符，【"+msg+"--Cell的实际值为："+value+"】;该表头行共有校验错误列："+sheetHeadErrorColCount+"列");			//只输出表头行第一个错误校验列的校验错误信息
					CommonLogger.info("【Sheet表头校验错误】：第"+row+"行-第"+col+"列表头信息不符，【"+msg+"--Cell的实际值为："+value+"】;该表头行共有校验错误列："+sheetHeadErrorColCount+"列");
				}else{
					//处理主体信息校验的错误描述
					checkedSectionType = "02";//代表该校验区域为主体信息校验区域(XML中第二个Section区)
					importErrMsgBean.setSectionType(checkedSectionType);
					importErrMsgBean.setErrDesc("第"+row+"行-第"+col+"列,校验不通过，具体信息为【"+msg+"--Cell的实际值为："+value+"】;");
				}
				excelDealService.insertUploadDataErrorInfo(importErrMsgBean);			//保存错误信息行
				System.out.println("【CntDataBussinessDeal--总的校验错误行数：】"+errRowList.size()+"行");
			}
		}
		return errRowList;
	}
}
