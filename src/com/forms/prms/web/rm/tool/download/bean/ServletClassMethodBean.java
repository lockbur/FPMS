package com.forms.prms.web.rm.tool.download.bean;

/**
 * 公用servlet内部Class/methods/Method的Bean类
 * 存储servlet-config.xml文件中的servlet/Class/methods节点的一个method子节点信息
 * @author ahnan
 *
 */
public class ServletClassMethodBean
{
	private String methodName;//方法名称

	private String returnType;//返回类型

	private String returnInnerEmType;//返回类型的内部元素类型，当返回类型为数组或list时起作用

	private ServletClassMethodParaBean[] paras;//参数数组

	public String getMethodName()
	{
		return methodName;
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public ServletClassMethodParaBean[] getParas() {
		return paras;
	}

	public void setParas(ServletClassMethodParaBean[] paras) {
		this.paras = paras;
	}

	public String getReturnInnerEmType()
	{
		return returnInnerEmType;
	}

	public void setReturnInnerEmType(String returnInnerEmType)
	{
		this.returnInnerEmType = returnInnerEmType;
	}

	public String getReturnType()
	{
		return returnType;
	}

	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}
}
