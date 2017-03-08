package com.forms.prms.web.rm.tool.download.bean;

/**
 * 公用Servlet的Bean
 * 存储servlet-config.xml文件中的一个servlet节点信息
 * @author ahnan
 *
 */
public class ServletBean
{

	private ServletClassBean[] classes;//类数组 

	public ServletClassBean[] getClasses()
	{
		return classes;
	}

	public void setClasses(ServletClassBean[] classes)
	{
		this.classes = classes;
	}

}
