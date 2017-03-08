package com.forms.prms.web.rm.tool.download.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServiceTool {
    
    public static String getElVal(HttpServletRequest request,String src)
	{
		String ft = src;
		if(src!=null && src.endsWith("@"))
		{
			String[] tmp1 = src.split("@");
			String value = request.getParameter(tmp1[0]);
			for(int i=1; i<tmp1.length; i++)
			{
				String[] tmp2 = tmp1[i].split(":");
				if(value.equalsIgnoreCase(tmp2[0])||"default".equalsIgnoreCase(tmp2[0]))
				{
					ft = tmp2[1];
					break;
				}
			}
			
			if(ft == src)
				ft = "#,##0.00";
		}
		
		return ft;
	}
	
	public static String getDefaultFormat(String xmlformat, String type)
	{
		String format = "";
		if (xmlformat != null && !xmlformat.equals(""))
		{
			format = xmlformat;
		} else
		{
			if ("NUM".equalsIgnoreCase(type))
			{
				format = "#,##0.00";
			} else
			{
				format = "text";
			}
		}

		return format;
	}
	
	/**
	 * 功能：将数据库字段转变为Java变量，且第一个字母小写，如ORG_ID转化后为orgId。
	 * @param ret
	 * @return
	 */
	public static String datebaseToJava(String ret) {
		
		ret = ret.toLowerCase();
		String[] tmpArr = ret.split("_");
		String tmp = tmpArr[0];
		for (int i = 1; i < tmpArr.length; i++) {
			String innerTmp = tmpArr[i];
			innerTmp = innerTmp.substring(0, 1).toUpperCase()
					+ innerTmp.substring(1, innerTmp.length());
			tmp += innerTmp;
		}
		return tmp;
	}
	/**
	 * 通过Session获取当前的语言版本
	 * @param ip_session
	 * @return
	 */
	public static String getLangType(HttpSession ip_session)
	{
		String loc_langType = "";
		if(ip_session != null)
		{
			loc_langType = (String)ip_session.getAttribute("localeKey");
			if(loc_langType == null)
			{
				loc_langType = "CN";
			}
		}
		return loc_langType;
	}

}
