package com.forms.prms.web.rm.tool.download.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HeaderRowBean  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1581383194631547457L;
	private List<HeaderColsBean> headColsList = new ArrayList<HeaderColsBean>();
	
	public List<HeaderColsBean> getHeadColsList() {
		return headColsList;
	}
	public void setHeadColsList(List<HeaderColsBean> headColsList) {
		this.headColsList = headColsList;
	}

	
}

