package com.forms.prms.web.reportmgr.importAndexport.query.domain;

import java.io.Serializable;

public class ImporExporCommonBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//任务流水号
	private String taskId;
	//任务描述
	private String taskDesc;
	//导入Excel配置ID
	private String configId;
	//任务状态
	private String dataFlag;
	//任务状态名称
	private String dataFlagName;
	//待排名处理序号
	private String waitSeq;
	//提交柜员
	private String instOper;
	//提交柜员姓名
	private String instOperName;
	//提交日期 yyyy-mm-dd
	private String instDate;
	//提交时间 hh24:mi:ss
	private String instTime;
	//处理日期
	private String procDate;
	//处理时间
	private String procTime;
	//处理结果说明(可以用于保存处理失败的原因说明)
	private String procMemo;
	//任务参数
	private String taskParams;
	
	
	//Excel模板类型
	private String tempType;
	//错误行数统计
	private int errRowCount;
	
	private String loadType;
	/**
	 * 【导入特有属性】
	 */
	//导入文件名
	private String sourceFname;
	//导入文件路径
	private String sourceFpath;
	
	/**
	 * 【导出特有属性】
	 */
	//导出文件的全路径
	private String exportFile;
	//结果文件全路径
	private String destFile;
	
	
	//[公共属性]：用于判断该保存bean是导入bean还是导出bean
	private String imOutportType;
	
	private String taskBatchNo;//导入对应的批次号
	
	
	private String upStepUrl;		//上一次操作的URL地址
	private String funcId;			//URL对应的FuncId
	private String upStepParams;	//上一次操作的参数s
	
	
	public String getFuncId() {
		return funcId;
	}
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	public String getUpStepParams() {
		return upStepParams;
	}
	public void setUpStepParams(String upStepParams) {
		this.upStepParams = upStepParams;
	}
	public String getUpStepUrl() {
		return upStepUrl;
	}
	public void setUpStepUrl(String upStepUrl) {
		this.upStepUrl = upStepUrl;
	}
	public String getTaskBatchNo() {
		return taskBatchNo;
	}
	public void setTaskBatchNo(String taskBatchNo) {
		this.taskBatchNo = taskBatchNo;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
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
	public String getInstOperName() {
		return instOperName;
	}
	public void setInstOperName(String instOperName) {
		this.instOperName = instOperName;
	}
	public String getExportFile() {
		return exportFile;
	}
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}
	public String getDestFile() {
		return destFile;
	}
	public void setDestFile(String destFile) {
		this.destFile = destFile;
	}
	public String getImOutportType() {
		return imOutportType;
	}
	public void setImOutportType(String imOutportType) {
		this.imOutportType = imOutportType;
	}
	public String getTempType() {
		return tempType;
	}
	public void setTempType(String tempType) {
		this.tempType = tempType;
	}
	public int getErrRowCount() {
		return errRowCount;
	}
	public void setErrRowCount(int errRowCount) {
		this.errRowCount = errRowCount;
	}
	public String getTaskParams() {
		return taskParams;
	}
	public void setTaskParams(String taskParams) {
		this.taskParams = taskParams;
	}
	
}
