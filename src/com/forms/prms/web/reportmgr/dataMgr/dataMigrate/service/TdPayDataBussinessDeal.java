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
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayAdvanceBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayAdvanceCancelBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.TdPayDeviceBean;
import com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain.UploadDataControlInfoBean;

//【数据迁移】--付款数据Excel数据导入逻辑前后处理类
public class TdPayDataBussinessDeal implements IBusinessDeal{
	
	//添加ServiceBean
	DataMigrateService dmService = DataMigrateService.getInstance();
	BudgetPlanService budgetService = BudgetPlanService.getInstance();
	CommonExcelDealService excelDealService = CommonExcelDealService.getInstance();

	@Override
	public void start(String batchNo, String sheetConfigId, Map beans) throws Exception {
		CommonLogger.debug("HQQQ_____IN TdPayDataBussinessDeal START"+"【任务Task流水号："+batchNo+"】");
		//2.添加用于保存Excel具体数据的bean
		ExcelBean<Object> bean = new ExcelBean<Object>();
		beans.put("bean", bean);
	}

	@Override
	/**
	 *	1.设置常量值等
	 *	2.处理配置校验错误信息(取得错误行列值ErrMsg等，插入到表UPLOAD_DATA_ERROR_INFO中)，最后返回错误的行信息(用于后续是否插入Sheet中具体行到正式表)
	 *	3.校验错误行是否超标，根据该行是否为错误行标识，从而决定是否插入正式表
	 *	4.判断当  当前Deal处理最后一个导入Sheet时，根据校验信息决定是否继续调用存储过程进行校验/更新Control表的状态，更新导入任务Task的状态信息
	 */
	public void end(String batchNo, String sheetConfigId, Map beans, ExcelImportInfo importInfo) throws Exception {
		int errorPartFlag = 0;
		
		CommonLogger.debug("HQQQ_____IN TdPayDataBussinessDeal END"+"【任务流水号："+batchNo+"】");
		System.out.println("【HQQ-当次普通付款数据的ConfigId为：】"+sheetConfigId);
		
		//1.定义用于后续处理的初始缓存值
		//常量值
		String uploadType = "02";											//合同Excel模板
		//参数值
		String impBatchNo = (String)beans.get("impBatch");					//从参数中取出当次导入任务的导入批次号
		String dataType = ("DM_TDPAYADVANCE".equals(sheetConfigId) ? "0201" : ("DM_TDPAY".equals(sheetConfigId) ? "0202" :("DM_TDPAYADCANCEL".equals(sheetConfigId) ? "0203" : ("DM_TDPAYDEVICEAD".equals(sheetConfigId) ? "0204" : ("DM_TDPAYDEVICENOR".equals(sheetConfigId)? "0205" : "")))));
		//存储数据值
		List<String> errRowList;					//用于保存错误的行号(用于插入数据库时比较该行是否为校验错误，在该List的行号行数据不插入数据库)
		
		//2.处理错误信息并获取返回的错误信息(errRowList:错误行列表 、 allErrRowCount:校验错误总行数   ————用于后续处理)
		errRowList = this.dealTheValiErrorRow( importInfo, impBatchNo , uploadType , dataType );
		int allErrRowCount = errRowList.size();								//配置校验的错误行总行数(会自增)
		
		//3.(7-21)若基础校验行数超过XX时，则不再执行存储校验，直接更新状态
		//配置校验错误行数量未超指定值，允许插入到合同+付款的缓存表中
		//3-2.处理付款数据Excel中各个Sheet数据导入
		try {
			if("DM_TDPAYADVANCE".equals(sheetConfigId)){
				//Sheet1:预付款信息Sheet导入
				ExcelBean<TdPayAdvanceBean> bean = (ExcelBean<TdPayAdvanceBean>)beans.get("bean");
				List<TdPayAdvanceBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
			}else if("DM_TDPAY".equals(sheetConfigId)){
				ExcelBean<TdPayBean> bean = (ExcelBean<TdPayBean>)beans.get("bean"); 
				List<TdPayBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
			}else if("DM_TDPAYADCANCEL".equals(sheetConfigId)){
				ExcelBean<TdPayAdvanceCancelBean> bean = (ExcelBean<TdPayAdvanceCancelBean>)beans.get("bean");
				List<TdPayAdvanceCancelBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
			}else if("DM_TDPAYDEVICEAD".equals(sheetConfigId) || "DM_TDPAYDEVICENOR".equals(sheetConfigId)){
				//Sheet4/Sheet5		:	预付款核销物料Sheet/正常付款物料Sheet
				//根据ConfigId处理导入Sheet4/Sheet5对应的表
				ExcelBean<TdPayDeviceBean> bean = (ExcelBean<TdPayDeviceBean>)beans.get("bean");
				List<TdPayDeviceBean> excelInfoList = bean.getTemplateInfo();
				this.validataAndInsertDataToTb(excelInfoList, errRowList, impBatchNo, uploadType, dataType);
			}
			System.out.println("【HQQ-付款数据-Excel表校验正确数据插入["+sheetConfigId+"]完成！】");
		} catch (Exception e) {
			CommonLogger.error("TdPayDataBussinessDeal-[method:end]发生异常，于步骤3[处理付款数据Excel中各个Sheets的信息导入]时，请检查插入数据是否为空！");
			errorPartFlag = 3;
			catchExceptionHandle(beans, batchNo, errorPartFlag);		//参数：Map beans , String taskId , int errorPartFlag
		}
		
		
		//5.最后一个Sheet时更新Task状态和调存储过程校验或更新Control状态
		//当该Deal处理的为导入任务Excel模板中最后一个Sheet时，则可以更改Task的状态，并决定是否调用校验存储过程
		//【待处理】DM_TDPAY的值需随开发进度更改，最后应该更改为DM_TDPAYDEVICEAD
		if(sheetConfigId.equals(beans.get("lastConfigId"))){
			//【Part1】.处理当次导入任务的状态
			CommonExcelDealBean taskInfo = new CommonExcelDealBean();
			taskInfo.setTaskId(batchNo);
			taskInfo.setDataFlag("03");
			excelDealService.updateLoadResult(taskInfo);
			
			//【Part2】.调用存储过程校验或者更新Cotrol状态
			//4.调用存储过程开始数据确认校验（在存储过程中将Control表的DataFlag状态更改为01）
			UploadDataControlInfoBean udcBean = dmService.getRelTaskIdByBatchTaskId(batchNo);
			String batchTaskCntId = udcBean.getTaskCntId();
			//if另一个Task已完成，则执行存储过程，否则将当前的Task的状态置为3(前面代码已设置)				————根据taskId找到batchNo
			if("03".equals(excelDealService.getLoadTaskByTaskId(batchTaskCntId).getDataFlag())){
				if( dmService.getUpDataErrByBatchNo(udcBean.getBatchNo()).size() > 0 ){
					//如果配置校验错误行超过N行，或者错误信息表有数据，不调存储过程校验，将Control表信息状态改为01
					dmService.updateUpDataConInfoDataFlag("01", (String)beans.get("impBatch"));	
				}else{
					//配置校验无错误数据，调用存储过程校验，最后更新Control表状态为"01"
					dmService.callInitMainCheck(beans.get("impBatch").toString());	
				}
			}
			
			//【Part3】.【未处理】处理获取成功插入数据条数+校验错误数据条数
			CommonExcelDealBean upTaskMemoBean = new CommonExcelDealBean();
//			int currTaskInsertSuccCount = dmService.getPaySuccImpCountByBatchNo(beans.get("impBatch").toString());
			int currTaskSheetHeadErrCount = dmService.getUpDataErrCountByBatchAndUpType(beans.get("impBatch").toString(), "02" , "01");
			int currTaskValidateErrCount  = dmService.getUpDataErrCountByBatchAndUpType(beans.get("impBatch").toString(), "02" , "02");
			String procMemo = "";
			//根据是否有校验错误数据，填写导入任务的备注信息
			if( (currTaskSheetHeadErrCount + currTaskValidateErrCount ) > 0 ){
				//有校验错误信息
				procMemo = "导入校验有误;";
				if(currTaskSheetHeadErrCount > 0){
					procMemo += "Excel表头校验有误;";			//+currTaskSheetHeadErrCount;
				}
//				if(currTaskValidateErrCount > 0){
//					procMemo += ";校验有错误(不插入)：["+currTaskValidateErrCount+"]行";
//				}
//				procMemo += ";共插入[合同数据]有效性数据["+currTaskInsertSuccCount+"]行";
			}else{
				//无校验错误信息
				procMemo = "导入任务成功,校验无错误数据;";		//,共插入[付款数据]有效性数据["+currTaskInsertSuccCount+"]行";
			}
			upTaskMemoBean.setTaskId(batchNo);
			upTaskMemoBean.setProcMemo(procMemo);
			//更新导入任务的最终状态和导入备注描述
			excelDealService.updateLoadTaskProcMemo(upTaskMemoBean);
			System.out.println("PAY[付款数据]最后一个Sheet【"+sheetConfigId+"】导入完毕");
			CommonLogger.debug("【数据迁移】-付款数据导入前后逻辑处理--TdPayDataBussinessDeal导入完毕，当前执行为导入模板最后一个Sheet：["+sheetConfigId+"]");
		}
		System.out.println("导入完毕");
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
		List<TdPayAdvanceBean> tdPayAdvanceList = new ArrayList<TdPayAdvanceBean>();
		List<TdPayBean> tdPayList = new ArrayList<TdPayBean>();
		List<TdPayAdvanceCancelBean> tdPayAdvanceCancelList = new ArrayList<TdPayAdvanceCancelBean>();
		List<TdPayDeviceBean> tdPayDeviceList = new ArrayList<TdPayDeviceBean>();
		
		System.out.println("【validataAndInsertDataToTb----我是："+dataType+"】");
		int sheetHeadIndex = 2;		//Sheet的表头行默认占位行数=(表头信息默认占2行)
		boolean insertFlag ;
		//判断当前行是否信息校验错误行，若是则不插入该数据到数据库
		for(int i=0;i<impSheetInfoList.size();i++){
			insertFlag = true;
			for(int n=0;n<errRowList.size();n++){
				if(errRowList.get(n).equals(String.valueOf(i+sheetHeadIndex))){										//判断时使用i从0开始(因为errRowList下标也是从0开始的)
					insertFlag = false;	//该行为错误行,不插入
					break;
				}
			}
			//该行为非校验错误行，设置bean的公共属性，保存bean并保存到表中
			if(insertFlag){
				if("0201".equals(dataType)){
					((TdPayAdvanceBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((TdPayAdvanceBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));		//设置真实行号(i从0开始，实际上是第一行，所以+1后再加表头占用行sheetHeadIndex)
					((TdPayAdvanceBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((TdPayAdvanceBean)impSheetInfoList.get(i)).setDataType(dataType);
					tdPayAdvanceList.add((TdPayAdvanceBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
						dmService.insertToTDPAYADVANCE((List<TdPayAdvanceBean>)impSheetInfoList);
						tdPayAdvanceList = new ArrayList<TdPayAdvanceBean>();
					}
				}else if("0202".equals(dataType)){
					((TdPayBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((TdPayBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));
					((TdPayBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((TdPayBean)impSheetInfoList.get(i)).setDataType(dataType);
					tdPayList.add((TdPayBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
						dmService.insertToTDPAY((List<TdPayBean>)impSheetInfoList.get(i));
						tdPayList = new ArrayList<TdPayBean>();
					}
				}else if("0203".equals(dataType)){
					((TdPayAdvanceCancelBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((TdPayAdvanceCancelBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));
					((TdPayAdvanceCancelBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((TdPayAdvanceCancelBean)impSheetInfoList.get(i)).setDataType(dataType);
					tdPayAdvanceCancelList.add((TdPayAdvanceCancelBean)impSheetInfoList.get(i));
					if(i>0 && i%500 == 0){
						dmService.insertToTdPayAdvanceCancel((List<TdPayAdvanceCancelBean>)impSheetInfoList.get(i));
						tdPayAdvanceCancelList = new ArrayList<TdPayAdvanceCancelBean>();
					}
				}else if("0204".equals(dataType) || "0205".equals(dataType)){
					((TdPayDeviceBean)impSheetInfoList.get(i)).setBatchNo(impBatchNo);
					((TdPayDeviceBean)impSheetInfoList.get(i)).setRowNo(String.valueOf((i+1)+sheetHeadIndex));
					((TdPayDeviceBean)impSheetInfoList.get(i)).setUploadType(uploadType);
					((TdPayDeviceBean)impSheetInfoList.get(i)).setDataType(dataType);
					tdPayDeviceList.add((TdPayDeviceBean)impSheetInfoList.get(i));
					if(i>0 && i%500 ==0){
						if("0204".equals(dataType)){
							dmService.insertToTdPayDevice((List<TdPayDeviceBean>)impSheetInfoList.get(i),"0");		//Sheet4,payType=0为预付款核销物料Sheet
						}else{
							dmService.insertToTdPayDevice((List<TdPayDeviceBean>)impSheetInfoList.get(i),"1");		//Sheet5,payType=1为正常付款物料Sheet
						}
						tdPayDeviceList = new ArrayList<TdPayDeviceBean>();
					}
					
				}else{
					System.out.println("【找不到需要插入的对应表和Bean对象】");
				}
			}else{
				//【未处理】--可以考虑插入脏数据表(需手动处理插入值信息修改的)
			}
		}
		//处理不够500行数据的最后插入--到数据库
		if("0201".equals(dataType)){
			if( tdPayAdvanceList.size()>0 && null != tdPayAdvanceList ){
				dmService.insertToTDPAYADVANCE(tdPayAdvanceList); 
			}
		}else if("0202".equals(dataType)){
			if( tdPayList.size()>0 && null != tdPayList ){
				dmService.insertToTDPAY(tdPayList); 
			}
		}else if("0203".equals(dataType)){
			if( tdPayAdvanceCancelList.size()>0 && null != tdPayAdvanceCancelList ){
				dmService.insertToTdPayAdvanceCancel(tdPayAdvanceCancelList); 
			}
		}else if("0204".equals(dataType)){
			if( tdPayDeviceList.size()>0 && null != tdPayDeviceList ){
				dmService.insertToTdPayDevice(tdPayDeviceList , "0"); 
			}
		}else if("0205".equals(dataType)){
			if( tdPayDeviceList.size()>0 && null != tdPayDeviceList ){
				dmService.insertToTdPayDevice(tdPayDeviceList , "1"); 
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
				if( row <= 2){															//2是当前导入Excel中的表头行数
					//CheckedSection1(当校验错误的行数小于表头行时，认为是表头校验错误)配置为表头信息校验(根据配置section的位置改变应相应发生改变)
					int sheetHeadErrorColCount = checkSectionList.get(i).getCells().size();			//Sheet表头错误列数统计(【统计第一行表头，都有多少个校验错误列】)
					CommonLogger.info("【Sheet表头校验】该表头行共有校验错误列为："+sheetHeadErrorColCount+"列！");
					//处理表头校验的错误描述
					checkedSectionType = "01";//代表该校验区域为表头校验区域(XML中第一个Section区)
					importErrMsgBean.setSectionType(checkedSectionType);
					importErrMsgBean.setErrDesc("【Sheet表头校验错误】：第"+row+"行-第"+col+"列表头信息不符，【"+msg+"--Cell的实际值为："+value+"】;");
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
