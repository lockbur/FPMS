package com.forms.dealexceldata.exceldealtool.domain;

public class ImportBean {
	private String taskId;// 任务ID
	private String taskDesc;// 任务描述
	private String configId;// 配置ID
	private String sourceFname;// 文件名
	private String sourceFpath;// 文件路径
	private String dataFlag;// 任务状态
	private String beforeDataFlag;// 任务状态更新的条件
	private String procDate;
	private String procTime;
	private String procMemo;
	private String taskParams;
	private String loadType;
	private String taskBatchNo;
	private String memo;
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getBeforeDataFlag() {
		return beforeDataFlag;
	}

	public void setBeforeDataFlag(String beforeDataFlag) {
		this.beforeDataFlag = beforeDataFlag;
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

	public String getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(String taskParams) {
		this.taskParams = taskParams;
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

}
