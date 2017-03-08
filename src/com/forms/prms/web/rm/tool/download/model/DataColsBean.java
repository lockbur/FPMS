package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;

/**
 * 保存excel中展示数据字段信息的bean
 * @see 
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class DataColsBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] colName; //字段名

	private int[] listIndex; //List的索引位置
	
	private boolean[] colCanSpan; //字段是否可跨行或跨列
	
	private String[] colType; //字段类型

	private String[] format;
	
	private LayerBean layerBean; //分层展示的bean

	private boolean inList;
	
	private String[] colTextValue;
	
	public String[] getColName()
	{
		return colName;
	}

	public void setColName(String[] colName)
	{
		this.colName = colName;
	}

	public int[] getListIndex()
	{
		return listIndex;
	}

	public void setListIndex(int[] listIndex)
	{
		this.listIndex = listIndex;
	}

	public boolean[] getColCanSpan()
	{
		return colCanSpan;
	}

	public void setColCanSpan(boolean[] colCanSpan)
	{
		this.colCanSpan = colCanSpan;
	}

	public String[] getColType()
	{
		return colType;
	}

	public void setColType(String[] colType)
	{
		this.colType = colType;
	}

	public LayerBean getLayerBean()
	{
		return layerBean;
	}

	public void setLayerBean(LayerBean layerBean)
	{
		this.layerBean = layerBean;
	}

	public String[] getFormat()
	{
		return format;
	}

	public void setFormat(String[] format)
	{
		this.format = format;
	}

	public boolean isInList()
	{
		return inList;
	}

	public void setInList(boolean inList)
	{
		this.inList = inList;
	}

	/**
	 * @return the colTextValue
	 */
	public String[] getColTextValue()
	{
		return colTextValue;
	}

	/**
	 * @param colTextValue the colTextValue to set
	 */
	public void setColTextValue(String[] colTextValue)
	{
		this.colTextValue = colTextValue;
	}
}