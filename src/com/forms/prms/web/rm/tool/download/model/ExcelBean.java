package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;
import java.util.List;
/**
 * 保存一个download信息中excel配置信息的bean
 * @see DataColsBean
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class ExcelBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6793108733356090655L;

	private List<?> headerColsBeanList; //表格头格式信息 装载HeaderColsBean对象

	private DataColsBean dataColsBean; //数据字段信息
	
	private HeaderBean headerBean;

	public HeaderBean getHeaderBean() {
		return headerBean;
	}

	public void setHeaderBean(HeaderBean headerBean) {
		this.headerBean = headerBean;
	}

	public DataColsBean getDataColsBean()
	{
		return dataColsBean;
	}

	public void setDataColsBean(DataColsBean dataColsBean)
	{
		this.dataColsBean = dataColsBean;
	}

	public List<?> getHeaderColsBeanList()
	{
		return headerColsBeanList;
	}

	public void setHeaderColsBeanList(List<?> headerColsBeanList)
	{
		this.headerColsBeanList = headerColsBeanList;
	}

}
