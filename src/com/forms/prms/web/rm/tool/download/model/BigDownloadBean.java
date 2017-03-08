package com.forms.prms.web.rm.tool.download.model;

public class BigDownloadBean {
	
	private String bean;
	
	private String sql;
	
	private String cols;
	
	private int sheetNum = 0;
	
	private int maxNum;
	
	private String[] sqls = null;
	
	private String[] subTitle = null;
	
	private int sqlIndex = -1;

	public String[] getSqls() {
		return sqls;
	}

	public void setSqls(String[] sqls) {
		this.sqls = sqls;
	}

	public int getSqlIndex() {
		return sqlIndex;
	}

	public void setSqlIndex(int sqlIndex) {
		this.sqlIndex = sqlIndex;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public String[] getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String[] subTitle) {
		this.subTitle = subTitle;
	}
	
}
