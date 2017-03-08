package com.forms.prms.tool.fms.parse.domain;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Column")
public class ColumnBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	@XStreamAsAttribute
	@XStreamAlias("id")
	private int id;//字段顺序编号
	
	@XStreamAsAttribute
	@XStreamAlias("comment")
	private String comment;//字段中文名称
	
	@XStreamAsAttribute
	@XStreamAlias("col")
	private String col;//数据库字段名称
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
}
