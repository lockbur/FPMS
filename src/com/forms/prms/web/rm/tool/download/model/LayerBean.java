package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;

/**
 * 保存分层展示的字段配置信息
 * @see 
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class LayerBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String layerName; //表示层次的字段名称

	private String layerType; //表示层次的字段类型

	private int layerNumber;//报表层次

	private String[] layerColor; //各个层次分别使用的颜色，各个层次颜色设置顺序必须0,1,2,3...

	public int getLayerNumber()
	{
		return layerNumber;
	}

	public void setLayerNumber(int layerNumber)
	{
		this.layerNumber = layerNumber;
	}

	public String[] getLayerColor()
	{
		return layerColor;
	}

	public void setLayerColor(String[] layerColor)
	{
		this.layerColor = layerColor;
	}

	public String getLayerName()
	{
		return layerName;
	}

	public void setLayerName(String layerName)
	{
		this.layerName = layerName;
	}

	public String getLayerType()
	{
		return layerType;
	}

	public void setLayerType(String layerType)
	{
		this.layerType = layerType;
	}

}
