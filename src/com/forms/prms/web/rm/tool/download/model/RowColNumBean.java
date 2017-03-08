package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;
/**
 * Excel的行号与列号，生成Excel时使用
 * @see 
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class RowColNumBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int rowNum = 0;

	int colNum = 0;

	int maxRowNum = 0;	
	
	public int getMaxRowNum()
	{
		return maxRowNum;
	}

	public void setMaxRowNum(int maxRowNum)
	{
		this.maxRowNum = maxRowNum;
	}

	public int getColNum()
	{
		return colNum;
	}

	public void setColNum(int colNum)
	{
		this.colNum = colNum;
	}

	public int getRowNum()
	{
		return rowNum;
	}

	public void setRowNum(int rowNum)
	{
		this.rowNum = rowNum;
	}

}
