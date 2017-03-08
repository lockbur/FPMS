package com.forms.prms.web.rm.tool.download.bean;


/**
 * 公用servlet内部Class/vars/var的Bean类
 * 存储servlet-config.xml文件中的servlet/Class/vars节点的一个var子节点信息
 * @author ahnan
 *
 */
public class ServletClassVarBean {
	
	private boolean isSmpObj;//是否为简单类型
	
	private String varName;//变量名称
	
	private String varType;//变量类型

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	public boolean getIsSmpObj() {
		return isSmpObj;
	}

	public void setIsSmpObj(boolean isSmpObj) {
		this.isSmpObj = isSmpObj;
	}
}
