package com.forms.prms.web.reportmgr.importAndexport.query.domain;

import java.io.Serializable;

public class ExportReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 流水号 +流水号，页面传入进来
	private String taskId;
	
	//任务描述 页面传入的简短说明
	private String taskDesc;
	
	// 配置ID 导入文件对应的配置ID
	private String configId;
	
	// 任务状态 00-待处理 01-处理中 02-处理失败 03-处理完成
	private String dataFlag;
	
	private String dataFlagName;
	
	private String waitSeq;
	
	// 导出文件的全路径
	private String exportFile;
	
	// 提交柜员
	private String instOper;
	
	private String instOperName;
	
	// 提交日期 yyyy-mm-dd
	private String instDate;
	
	// 提交时间 hh24:mi:ss
	private String instTime;
	
	// 结果文件全路径
	private String destFile;
	
	// 处理日期
	private String procDate;
	
	// 处理时间
	private String procTime;
	
	//处理结果说明 可以用于保存处理失败的原因说明
	private String procMemo;

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

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getWaitSeq() {
		return waitSeq;
	}

	public void setWaitSeq(String waitSeq) {
		this.waitSeq = waitSeq;
	}

	public String getExportFile() {
		return exportFile;
	}

	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public String getInstOperName() {
		return instOperName;
	}

	public void setInstOperName(String instOperName) {
		this.instOperName = instOperName;
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

}
