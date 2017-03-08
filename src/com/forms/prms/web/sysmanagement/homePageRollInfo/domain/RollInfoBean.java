package com.forms.prms.web.sysmanagement.homePageRollInfo.domain;
/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
public class RollInfoBean {
	private static final long serialVersionUID = 1L;
	private String rollId;  	//ID
	private String rollTitle;  	//名称
	private String rollInfo;  	//内容
	private int rollDays;  	//天数
	private String lastDate;  	//最后日期
	private String addUid;  	//创建人
	private String addTime;  	//创建时间
	private String lupdUid;  	//修改人
	private String lupdTime;  	//修改时间
	private String userId;  	//当前登录用户
	private String befDate;  	
	private String aftDate;  	
	private String visualGrade;  //可见级别
	private String visualOrg;  //可见机构
	private String dutyCode;  //责任中心
	private String org1Code;  //一级行
	private String org2Code;  //二级行
	private String isA0001SuperAdmin;//是否是总行超级管理员 1-是 0-否
	
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getOrg2Code() {
		return org2Code;
	}
	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getVisualGrade() {
		return visualGrade;
	}
	public void setVisualGrade(String visualGrade) {
		this.visualGrade = visualGrade;
	}
	public String getVisualOrg() {
		return visualOrg;
	}
	public void setVisualOrg(String visualOrg) {
		this.visualOrg = visualOrg;
	}	
	public String getBefDate() {
		return befDate;
	}
	public void setBefDate(String befDate) {
		this.befDate = befDate;
	}
	public String getAftDate() {
		return aftDate;
	}
	public void setAftDate(String aftDate) {
		this.aftDate = aftDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRollId() {
		return rollId;
	}
	public void setRollId(String rollId) {
		this.rollId = rollId;
	}
	public String getRollTitle() {
		return rollTitle;
	}
	public void setRollTitle(String rollTitle) {
		this.rollTitle = rollTitle;
	}
	public String getRollInfo() {
		return rollInfo;
	}
	public void setRollInfo(String rollInfo) {
		this.rollInfo = rollInfo;
	}
	public int getRollDays() {
		return rollDays;
	}
	public void setRollDays(int rollDays) {
		this.rollDays = rollDays;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public String getAddUid() {
		return addUid;
	}
	public void setAddUid(String addUid) {
		this.addUid = addUid;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getLupdUid() {
		return lupdUid;
	}
	public void setLupdUid(String lupdUid) {
		this.lupdUid = lupdUid;
	}
	public String getLupdTime() {
		return lupdTime;
	}
	public void setLupdTime(String lupdTime) {
		this.lupdTime = lupdTime;
	}
	public String getIsA0001SuperAdmin() {
		return isA0001SuperAdmin;
	}
	public void setIsA0001SuperAdmin(String isA0001SuperAdmin) {
		this.isA0001SuperAdmin = isA0001SuperAdmin;
	}
}
