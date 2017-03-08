package com.forms.prms.web.sysmanagement.org.domain;

import java.util.List;

public class ResponsibilityTree {
	
	//一级行(36个)
	private List<OrgMapBean> orgMapFirst;
	private List<OrgMapBean> orgMapSecond;
	private List<OrgMapBean> orgMapThird;
	private List<OrgMapBean> orgMapFour;
	
	public List<OrgMapBean> getOrgMapFirst() {
		return orgMapFirst;
	}
	public void setOrgMapFirst(List<OrgMapBean> orgMapFirst) {
		this.orgMapFirst = orgMapFirst;
	}
	public List<OrgMapBean> getOrgMapSecond() {
		return orgMapSecond;
	}
	public void setOrgMapSecond(List<OrgMapBean> orgMapSecond) {
		this.orgMapSecond = orgMapSecond;
	}
	public List<OrgMapBean> getOrgMapThird() {
		return orgMapThird;
	}
	public void setOrgMapThird(List<OrgMapBean> orgMapThird) {
		this.orgMapThird = orgMapThird;
	}
	public List<OrgMapBean> getOrgMapFour() {
		return orgMapFour;
	}
	public void setOrgMapFour(List<OrgMapBean> orgMapFour) {
		this.orgMapFour = orgMapFour;
	}
	
	
	

}
