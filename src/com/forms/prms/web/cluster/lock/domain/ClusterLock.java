package com.forms.prms.web.cluster.lock.domain;

import java.io.Serializable;

public class ClusterLock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String taskType;// 任务类型 1）batch 批量任务
	private String taskSubType;// 任务子类（批量为批量任务类型，联机为各自批次号）
	private String ipAddress;// IP地址
	private String instOper;// 操作柜员
	private String instDate;// 开始日期
	private String instTime;// 开始时间
	private String memo;// 备注
	private String delFlag;// 是否删除

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskSubType() {
		return taskSubType;
	}

	public void setTaskSubType(String taskSubType) {
		this.taskSubType = taskSubType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
