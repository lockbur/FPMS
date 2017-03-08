package com.forms.prms.web.util;

import java.util.Map;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkBean;
import com.forms.platform.web.returnlink.ReturnLinkHistory;

/**
 * 
 * Title:ReturnLinkUtils.java
 * Description:返回连接处理工具类
 *
 * Coryright: formssi
 * @author zuzeep
 * @project ERP
 * @date 2015-3-26
 * @version 1.0
 */
public class LinkDealUtils {
	
	/**
	 * @methodName addReturnLink
	 * desc  增加处理连接
	 * 
	 * @param id   	链接Id
	 * @param name 	链接按钮名字
	 * @param uri	链接对应的URL
	 * @param params链接所需参数
	 */
	public static void addReturnLink(String id, String name, String uri, Map<String,String[]> params){
		
		ReturnLinkBean linkBean = new ReturnLinkBean();
		linkBean.setId(id);
		linkBean.setName(name);
		linkBean.setUri(uri);
		linkBean.setParameters(params);
		
		ReturnLinkHistory history = (ReturnLinkHistory) WebUtils.getSessionAttr(ReturnLinkHistory.SESSION_KEY);
		if (history == null) {
			history = new ReturnLinkHistory();
			WebUtils.setSessionAttr(ReturnLinkHistory.SESSION_KEY, history);
		}
		history.addLink(linkBean);
	}

}
