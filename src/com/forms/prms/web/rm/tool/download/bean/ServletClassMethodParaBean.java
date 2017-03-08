package com.forms.prms.web.rm.tool.download.bean;

/**
 * 公用servlet内部Class/methods/method/paras/para的Bean类
 * 存储servlet-config.xml文件中的servlet/Class/methods/method/paras节点的一个para子节点信息
 * @author ahnan
 *
 */
public class ServletClassMethodParaBean {
	
	private boolean isSmpObj;//是否为简单类型
	
	private String paraName;//参数名称
	
	private String paraType;//参数类型

	public boolean getIsSmpObj() {
		return isSmpObj;
	}

	public void setIsSmpObj(boolean isSmpObj) {
		this.isSmpObj = isSmpObj;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}

}
