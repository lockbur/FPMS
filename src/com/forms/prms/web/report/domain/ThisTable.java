package com.forms.prms.web.report.domain;

import java.util.ArrayList;

public class ThisTable {
	private String tableName = null;
	private String tableTitle = null;

	//字段列表
	private ArrayList fieldList = new ArrayList();
	
	//行列表 
	private ArrayList rowList = new ArrayList();
	
	public ThisTable(String tableName) {
		super();
		this.tableName = tableName;
	}


	public String getTableName() {
		return tableName;
	}

	void addField(String fieldName) {
		fieldList.add(fieldName);
	}

	/**
	 * 获取当前表的字段列表
	 * @return
	 */
	ArrayList getFieldList() {
		return fieldList;
	}
	
	/**
	 * 获取表的所有行
	 * @return
	 */
	ArrayList getRowList() {
		return rowList;
	}

	public String getTableTitle() {
		return tableTitle;
	}


	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
	
	/**
	 * 增加一行数据
	 * @param thisRow
	 */
	public void addRow(ThisRow thisRow) {
		rowList.add(thisRow);
	}
}
