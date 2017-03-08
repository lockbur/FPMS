package com.forms.prms.web.rm.tool.download.init;

import java.util.HashMap;

import com.forms.prms.web.rm.tool.download.common.ParseDownLoadConfig;
/**
 * 初始化下载配置信息（读取配置文件信息到内存）
 * @see 
 * @version 1.0
 * @author ahnan 
 * 创建时间: 2012-10-31
 * 最后修改时间: 2012-10-31
 */
public class DownLoadInit
{

	private static DownLoadInit downLoadInit = null;
	
	private HashMap downLoadMap = null;

	private String realPath = null;

	public String getRealPath()
	{
		return realPath;
	}

	public void setRealPath(String realPath)
	{
		this.realPath = realPath;
	}

	public static DownLoadInit getInstance(String realPath)
	{
		if (downLoadInit == null)
		{
			downLoadInit = new DownLoadInit(realPath);
		}
		return downLoadInit;
	}

	public static DownLoadInit getInstance()
	{
		return downLoadInit;
	}

	public DownLoadInit(String realPath)
	{
		this.realPath = realPath;
		initialize();
	}

	public void refresh()
	{
		downLoadMap.clear();
		initialize();
	}

	public void initialize()
	{
		ParseDownLoadConfig parseDownLoadConfig = new ParseDownLoadConfig();

		this.downLoadMap = parseDownLoadConfig.readXML(this.realPath);
	}

	public static DownLoadInit getDownLoadInit()
	{
		return downLoadInit;
	}

	public static void setDownLoadInit(DownLoadInit downLoadInit2)
	{
		DownLoadInit.downLoadInit = downLoadInit;
	}

	public HashMap getDownLoadMap()
	{
		return downLoadMap;
	}

	public void setDownLoadMap(HashMap downLoadMap)
	{
		this.downLoadMap = downLoadMap;
	}
	
}
