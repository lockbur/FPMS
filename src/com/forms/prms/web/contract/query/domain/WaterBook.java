package com.forms.prms.web.contract.query.domain;

public class WaterBook {

	private String wbNum;//流水号
	
	private String busNum;//业务号
	
	private String busType;//业务类型
	
	private String busMenu;//业务菜单
	
	private String operateLog;//操作日志
	
	private String operateType;//操作类型
	
	private String operateDate;//操作日期
	
	private String operateTime;//操作时间
	
	private String operator;//操作人
	
	private String oldDataFlag;//旧数据状态
	
	private String newDataFlag;//新数据状态

	public String getWbNum() {
		return wbNum;
	}

	public void setWbNum(String wbNum) {
		this.wbNum = wbNum;
	}

	public String getBusNum() {
		return busNum;
	}

	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getBusMenu() {
		return busMenu;
	}

	public void setBusMenu(String busMenu) {
		this.busMenu = busMenu;
	}

	public String getOperateLog() {
		return operateLog;
	}

	public void setOperateLog(String operateLog) {
		this.operateLog = operateLog;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOldDataFlag() {
		return oldDataFlag;
	}

	public void setOldDataFlag(String oldDataFlag) {
		this.oldDataFlag = oldDataFlag;
	}

	public String getNewDataFlag() {
		return newDataFlag;
	}

	public void setNewDataFlag(String newDataFlag) {
		this.newDataFlag = newDataFlag;
	}
	
	
	
}
