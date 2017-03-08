package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HeaderBean  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1581383194631547457L;
	private List<HeaderRowBean> headList = new ArrayList<HeaderRowBean>();

	public List<HeaderRowBean> getHeadList() {
		return headList;
	}

	public void setHeadList(List<HeaderRowBean> headList) {
		this.headList = headList;
	}

}
