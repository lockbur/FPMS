package com.forms.prms.tool.fms.parse.domain;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SqlBean")
public class SqlBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	@XStreamAlias("sql")
	private String sql;

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
