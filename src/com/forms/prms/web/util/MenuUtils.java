package com.forms.prms.web.util;

import java.util.List;

import com.forms.platform.authority.domain.Function;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;

public class MenuUtils {

	public static String getSubMenuStr(Function f) {
		StringBuffer sb = new StringBuffer();
		List<Function> funcList = f.getChildren();
		if (funcList != null) {
			for (Function subFunc : funcList) {
				sb.append(getMenuStr(subFunc));
			}
		}
		return sb.toString();
	}

	public static String getMenuStr(Function f) {
		StringBuffer sb = new StringBuffer();
		if (f.isLeaf()) {
			sb.append("<li><a href='").append(WebUtils.getRoot() + f.getUrl())
					.append("?").append(WebConsts.FUNC_ID_KEY).append("=")
					.append(f.getFuncId()).append("'>")
					.append(WebUtils.getMessage(f.getFuncName()))
					.append("</a></li>");
		} else {
			sb.append("<li>");
			sb.append("<a href='#'>")
					.append(WebUtils.getMessage(f.getFuncName()))
					.append("</a>");
			sb.append("<ul>");
			List<Function> subMenus = f.getChildren();
			if (subMenus != null) {
				for (Function subMenu : subMenus) {
					sb.append(getMenuStr(subMenu));
				}
			}
			sb.append("</ul>");
			sb.append("</li>");
		}
		return sb.toString();
	}

}
