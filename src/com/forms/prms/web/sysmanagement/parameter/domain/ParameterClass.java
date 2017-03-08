package com.forms.prms.web.sysmanagement.parameter.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
public class ParameterClass
{
	private static final long serialVersionUID = 1L;
	private String categoryId; // 分类ID
	private String categoryName; // 参数类型名称
	private int numb; //未审批数量
	private List<Parameter> params = new ArrayList<Parameter>();
	
	public int getNumb() {
		return numb;
	}

	public void setNumb(int numb) {
		this.numb = numb;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Parameter> getParams() {
		return params;
	}

	public void setParams(List<Parameter> params) {
		this.params = params;
	}

}
