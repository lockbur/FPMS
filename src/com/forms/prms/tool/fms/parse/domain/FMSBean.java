package com.forms.prms.tool.fms.parse.domain;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("FMS")
public class FMSBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String batchNo;
	
	@XStreamAsAttribute
	@XStreamAlias("ID")
	private String id;
	
	@XStreamAsAttribute
	@XStreamAlias("TableName")
	private String tableName;
	
	@XStreamAsAttribute
	@XStreamAlias("TableNameTmp")
	private String tableNameTmp;
	
	@XStreamAsAttribute
	@XStreamAlias("CheckTableName")
	private String checkTableName;
	
	@XStreamAsAttribute
	@XStreamAlias("Type")
	private String type;
	
	@XStreamAsAttribute
	@XStreamAlias("OrderBy")
	private String orderBy;
	
	@XStreamAsAttribute
	@XStreamAlias("TradeTypes")
	private String tradeTypes;
	
	@XStreamAlias("File")
	private FileBean file;
	
	
	@XStreamAlias("FileColumns")
	private List<ColumnBean> fileColumns;
	
	@XStreamAlias("UpdateColumns")
	private List<ColumnBean> updateColumns;
	
	@XStreamAlias("PrimaryColumns")
	private List<ColumnBean> primaryColumns;
	
	public String getTableNameTmp() {
		return tableNameTmp;
	}
	public void setTableNameTmp(String tableNameTmp) {
		this.tableNameTmp = tableNameTmp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCheckTableName() {
		return checkTableName;
	}
	public void setCheckTableName(String checkTableName) {
		this.checkTableName = checkTableName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(String tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	public FileBean getFile() {
		return file;
	}
	public void setFile(FileBean file) {
		this.file = file;
	}
	public List<ColumnBean> getFileColumns() {
		return fileColumns;
	}
	public void setFileColumns(List<ColumnBean> fileColumns) {
		this.fileColumns = fileColumns;
	}
	public List<ColumnBean> getUpdateColumns() {
		return updateColumns;
	}
	public void setUpdateColumns(List<ColumnBean> updateColumns) {
		this.updateColumns = updateColumns;
	}
	public List<ColumnBean> getPrimaryColumns() {
		return primaryColumns;
	}
	public void setPrimaryColumns(List<ColumnBean> primaryColumns) {
		this.primaryColumns = primaryColumns;
	}
}
