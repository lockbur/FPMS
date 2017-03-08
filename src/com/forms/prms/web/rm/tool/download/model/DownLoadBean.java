package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;

import com.forms.prms.web.rm.tool.download.bean.ServletBean;


/**
 * 保存一个download信息的bean
 * @see ExcelBean,ServletBean
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class DownLoadBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ExcelBean excelBean; 	//excel报表信息

	private ServletBean servletBean;//公共servlet信息 
	
	private String field;			//字段组合成的字符串
	
	private int maxNum = 2000;		//每次处理的记录条数
	
	private String nm = "未命名文件"; //生成的文件名称
	
	/**
	 *  form的配置：用@分隔字段与字段；用:分隔字段与字段值；用,分隔字段值与字段值 
	 */
	private String formString;		//配置form中字段取值组合成的字符串

	public String getFormString() {
		return formString;
	}

	public void setFormString(String formString) {
		this.formString = formString;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public ServletBean getServletBean()
	{
		return servletBean;
	}

	public void setServletBean(ServletBean servletBean)
	{
		this.servletBean = servletBean;
	}

	public ExcelBean getExcelBean()
	{
		return excelBean;
	}

	public void setExcelBean(ExcelBean excelBean)
	{
		this.excelBean = excelBean;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
}
