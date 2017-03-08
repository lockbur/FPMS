package com.forms.prms.tool.exceltool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.constantValues.ExcelTaskStatusValues;
import com.forms.prms.tool.exceltool.dao.ICommonExcelDealDao;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.tool.exceltool.domain.UploadDataErrorInfoBean;

@Service
public class CommonExcelDealService {
	
	@Autowired
	private ICommonExcelDealDao dao;
	
	public static CommonExcelDealService getInstance(){
		return SpringUtil.getBean(CommonExcelDealService.class);
	}

	public CommonExcelDealBean getLoadTaskByTaskId(String taskId){
		CommonLogger.info("Excel导入导出任务公共模块，查询导入任务["+taskId+"],CommonExcelDealService,getLoadTaskByTaskId");
		return dao.getLoadTaskById(taskId);
	}
	
	public List<CommonExcelDealBean> getLoadList(){
		return dao.getLoadList();
	}
	
	public List<CommonExcelDealBean> getExportList(){
		return dao.getExportList();
	}
	
	public boolean checkedStatusAndUpdateLoadStatus(String taskId){
		return dao.updateLoadStatus(taskId,ExcelTaskStatusValues.ONDEAL.toString())==0?true:false;
	}
	
	public boolean checkedStatusAndupdateExportStatus(String taskId){
		//更新状态，返回更新条数为0，表示没有数据被更新
		return dao.updateExportStatus(taskId,ExcelTaskStatusValues.ONDEAL.toString())==0?true:false;
	}
	
	//更新导入任务的结果
	public void updateLoadResult(CommonExcelDealBean commonExcelBean){
		dao.updateLoadResult(commonExcelBean);
	}
	
	public void updateLoadTaskProcMemo(CommonExcelDealBean commonExcelBean){
		dao.updateLoadTaskProcMemo(commonExcelBean);
	}
	
	//更新导出任务的结果
	public void updateExportResult(CommonExcelDealBean commonExcelBean){
		dao.updateExportResult(commonExcelBean);
	}
	
	//根据BatchNo批次号获取导入的错误校验信息
	public List<UploadDataErrorInfoBean> getImportErrMsgByBatchNoAndUploadType(String batchNo , String uploadType){
		CommonLogger.info("Excel导入导出任务公共模块,查询存量业务中导入批次号["+batchNo+"],导入Excel模板类型为["+uploadType+"]的校验错误信息,CommonExcelDealService,getImportErrMsgByBatchNoAndUploadType()");
		ICommonExcelDealDao pageDao = PageUtils.getPageDao(dao);
		System.out.println(batchNo+"---"+uploadType);
		List<UploadDataErrorInfoBean> batchErrMsgList = pageDao.getImportErrMsgByBatchNoAndUploadType(batchNo , uploadType);//, uploadType
		return batchErrMsgList;
	}
	
	public void insertUploadDataErrorInfo(UploadDataErrorInfoBean uploadDataErrBean){
		dao.addUploadDataErrorInfo(uploadDataErrBean);
	}
	
	public String getExportFileDestPathByTaskId(String taskId){
		return dao.getExportFileDestPathByTaskId(taskId);
	}
}
