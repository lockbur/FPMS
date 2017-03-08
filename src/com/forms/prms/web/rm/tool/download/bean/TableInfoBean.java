package com.forms.prms.web.rm.tool.download.bean;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : CRM <br>
 * JDK version used : jdk1.6.0_20 <br>
 * Description :   <br>
 * Comments Name : PrdTableInfoBean.java <br>
 * author : yqz <br>
 * date : 2014-07-09<br>
 * Version : 1.00 <br>
 * editor : <br>
 * editorDate : <br>
 */
public class TableInfoBean
{
	private String colName;//字段名,java
	private String colNameDb;//字段名
	private String colNameCk;//字段名, 用于显示页面完整checkbox
	private String colComm;//列注释，作为显示时候的表头
	private String colType;//oracle 数据类型
	private String colTypeJ;//java中的数据类型
	private String tableName;//表名
	private String modelNm;
	private String modelId;
	private String userId;
	private String prodId;
	
	/**
	 * @return the colNameCk
	 */
	public String getColNameCk()
	{
		return colNameCk;
	}
	/**
	 * @param colNameCk the colNameCk to set
	 */
	public void setColNameCk(String colNameCk)
	{
		this.colNameCk = colNameCk;
	}
	/**
	 * @return the userId
	 */
	public String getUserId()
	{
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	/**
	 * @return the prodId
	 */
	public String getProdId()
	{
		return prodId;
	}
	/**
	 * @param prodId the prodId to set
	 */
	public void setProdId(String prodId)
	{
		this.prodId = prodId;
	}
	/**
	 * @return the modelId
	 */
	public String getModelId()
	{
		return modelId;
	}
	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(String modelId)
	{
		this.modelId = modelId;
	}
	/**
	 * @return the colNameDb
	 */
	public String getColNameDb()
	{
		return colNameDb;
	}
	/**
	 * @param colNameDb the colNameDb to set
	 */
	public void setColNameDb(String colNameDb)
	{
		this.colNameDb = colNameDb;
	}
	public String getModelNm()
	{
		return modelNm;
	}
	public void setModelNm(String modelNm)
	{
		this.modelNm = modelNm;
	}
	private int count;
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	public String getTableName()
	{
		return tableName;
	}
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
	public String getColType()
	{
		return colType;
	}
	public void setColType(String colType)
	{
		this.colType = colType;
	}
	public String getColTypeJ()
	{
		return colTypeJ;
	}
	public void setColTypeJ(String colTypeJ)
	{
		this.colTypeJ = colTypeJ;
	}
	public String getColName()
	{
		return colName;
	}
	public void setColName(String colName)
	{
		this.colName = colName;
	}
	public String getColComm()
	{
		return colComm;
	}
	public void setColComm(String colComm)
	{
		this.colComm = colComm;
	}
}
