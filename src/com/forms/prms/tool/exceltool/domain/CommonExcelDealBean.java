package com.forms.prms.tool.exceltool.domain;

import java.io.Serializable;

/**
 * Excel公共处理Bean对象
 * Copyright: 	formssi
 * @author  :	Zhuzeep
 * @project :	ERP
 * @date 	:	日期
 * @version :	1.0
 */
public class CommonExcelDealBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String taskId 		= "";		// 流水号
	private String taskDesc 	= "";		// 任务描述
	private String configId 	= "";		// 配置ID
	private String sourceFname 	= "";		// 导入文件名
	private String sourceFpath 	= "";		// 导入文件路径
	private String dataFlag 	= "";		// 任务状态 00-待处理 01-处理中 02-处理失败 03-处理完成
	private String instOper 	= "";		// 提交柜员
	private String instDate 	= "";		// 提交日期 yyyy-mm-dd
	private String instTime 	= "";		// 提交时间 hh24:mi:ss
	private String destFile 	= "";		// 结果文件全路径
	private String procDate 	= "";		// 处理日期
	private String procTime 	= "";		// 处理时间
	private String procMemo 	= "";		// 处理结果说明
	private String taskParams	= "";		// 参数信息
	private String exportType="";//导出类型
	private String loadType		= "";		// 导入类型(导入时特有参数，limao添加)
	private String taskBatchNo	= "";		// 导入任务所属批次号(一个导入批次有可能存在多个导入任务)
	
	private int sheetIndex;					//sheet相对位置(index下标：位于Excel文件中的第几个Sheet)

	public CommonExcelDealBean() {
		super();
	}

	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	public CommonExcelDealBean(String taskId) {
		super();
		this.taskId = taskId;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getTaskBatchNo() {
		return taskBatchNo;
	}

	public void setTaskBatchNo(String taskBatchNo) {
		this.taskBatchNo = taskBatchNo;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getSourceFname() {
		return sourceFname;
	}

	public void setSourceFname(String sourceFname) {
		this.sourceFname = sourceFname;
	}

	public String getSourceFpath() {
		return sourceFpath;
	}

	public void setSourceFpath(String sourceFpath) {
		this.sourceFpath = sourceFpath;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getInstTime() {
		return instTime;
	}

	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}

	public String getDestFile() {
		return destFile;
	}

	public void setDestFile(String destFile) {
		this.destFile = destFile;
	}

	public String getProcDate() {
		return procDate;
	}

	public void setProcDate(String procDate) {
		this.procDate = procDate;
	}

	public String getProcTime() {
		return procTime;
	}

	public void setProcTime(String procTime) {
		this.procTime = procTime;
	}

	public String getProcMemo() {
		return procMemo;
	}

	public void setProcMemo(String procMemo) {
		this.procMemo = procMemo;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(String taskParams) {
		this.taskParams = taskParams;
	}
}
