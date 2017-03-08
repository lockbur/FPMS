package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;
/**
 * 保存一个download信息中excel中头格式配置信息的bean
 * @see HeaderColsBean
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class HeaderColsBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HeaderColsBean[] colBean; //HeaderColsBean对象数组

	private String capName; //表格头名称

	private String rowspan = "1"; //表格跨行数

	private String colspan = "1"; //表格跨列数
	
	private int[] paramsIndex = null; //动态参数索引

	public HeaderColsBean[] getColBean()
	{
		return colBean;
	}

	public void setColBean(HeaderColsBean[] colBean)
	{
		this.colBean = colBean;
	}

	public String getColspan()
	{
		return colspan;
	}

	public void setColspan(String colspan)
	{
		this.colspan = colspan;
	}

	public String getRowspan()
	{
		return rowspan;
	}

	public void setRowspan(String rowspan)
	{
		this.rowspan = rowspan;
	}

	public String getCapName()
	{
		return capName;
	}
	
	public void setCapName(String capName)
	{
		this.capName = capName;
	}

	/**
	 * @return the paramsIndex
	 */
	public int[] getParamsIndex()
	{
		return paramsIndex;
	}

	/**
	 * @param paramsIndex the paramsIndex to set
	 */
	public void setParamsIndex(int[] paramsIndex)
	{
		this.paramsIndex = paramsIndex;
	}
	
}