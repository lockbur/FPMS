package com.forms.prms.web.sysmanagement.org.domain;

import java.util.List;

public class OrgMapBean {
	//对象ID
	private String id;
	//对象名称
	private String name;
	//父级ID
	private String pId;
	//对象级别
	private int lvl;
	//对象子节点List
	private List<OrgMapBean> childrenList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public List<OrgMapBean> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<OrgMapBean> childrenList) {
		this.childrenList = childrenList;
	}
	public static void main(String[] args) {
	}
}
