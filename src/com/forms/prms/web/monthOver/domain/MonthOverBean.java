package com.forms.prms.web.monthOver.domain;

import java.io.Serializable;

public class MonthOverBean implements Serializable {

	private static final long serialVersionUID = -3129319567840124673L;

	private String id;// 序号

	private String dataFlag;// 状态

	private String instOper;// 操作柜员

	private String instDate;// 操作日期

	private String instTime;// 操作时间

	private String org1Code;// 一级分行代码

	private String type;

	private String org1Name;// 一级分行名称

	private String dataFlagName;// 月结状态 0-月结中 1-月结结束或非月结中

	private String taskType;// 任务类型：0-预提冲销，1-待摊预提

	private String taskName;// 任务名称

	private String month;// 月份

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getOrg1Name() {
		return org1Name;
	}

	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
