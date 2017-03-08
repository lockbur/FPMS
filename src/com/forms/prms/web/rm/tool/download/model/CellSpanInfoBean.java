package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;


public class CellSpanInfoBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String cellValue; //字段名

	private int startRow;
	
	private short startCol;
	
	private int endRow;
	
	private short endCol;

	/**
	 * @return the cellValue
	 */
	public String getCellValue()
	{
		return cellValue;
	}

	/**
	 * @param cellValue the cellValue to set
	 */
	public void setCellValue(String cellValue)
	{
		this.cellValue = cellValue;
	}

	/**
	 * @return the startRow
	 */
	public int getStartRow()
	{
		return startRow;
	}

	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow)
	{
		this.startRow = startRow;
	}

	/**
	 * @return the startCol
	 */
	public short getStartCol()
	{
		return startCol;
	}

	/**
	 * @param startCol the startCol to set
	 */
	public void setStartCol(short startCol)
	{
		this.startCol = startCol;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow()
	{
		return endRow;
	}

	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow)
	{
		this.endRow = endRow;
	}

	/**
	 * @return the endCol
	 */
	public short getEndCol()
	{
		return endCol;
	}

	/**
	 * @param endCol the endCol to set
	 */
	public void setEndCol(short endCol)
	{
		this.endCol = endCol;
	}

}
