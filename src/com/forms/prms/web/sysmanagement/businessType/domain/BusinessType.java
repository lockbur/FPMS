package com.forms.prms.web.sysmanagement.businessType.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
public class BusinessType {

	private static final long serialVersionUID = 1L;

	private String categoryId; // 分类ID
	private String categoryName; // 分类ID名称
	private String paramName; // 参数显示名称
	private String paramValue; // 参数值
	private Integer sortFlag;
	private String paramDesc; // 参数描述
	private String applyUserId; // 修改人
	private String applyTime; // 修改时间
	private List<BusinessType> params = new ArrayList<BusinessType>();
	private String isInvalid;
	private String paramUpdateName;// 参数名称更新后值
	private String paramUpdateValue;// 参数值更新后值
	
	
	public String getParamUpdateName() {
		return paramUpdateName;
	}

	public void setParamUpdateName(String paramUpdateName) {
		this.paramUpdateName = paramUpdateName;
	}

	public String getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(String isInvalid) {
		this.isInvalid = isInvalid;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public List<BusinessType> getParams() {
		return params;
	}

	public void setParams(List<BusinessType> params) {
		this.params = params;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	public String getParamUpdateValue() {
		return paramUpdateValue;
	}

	public void setParamUpdateValue(String paramUpdateValue) {
		this.paramUpdateValue = paramUpdateValue;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

}
