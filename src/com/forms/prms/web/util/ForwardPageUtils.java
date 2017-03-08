package com.forms.prms.web.util;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.Values;

public class ForwardPageUtils
{
    public static String getSuccessPage() {
        WebUtils.setRequestAttr(Values.IS_SUCCESS_PAGE, "Y");
        return Values.SUCCESS;
    }
    
    public static String getErrorPage() {
        WebUtils.setRequestAttr(Values.IS_ERROR_PAGE, "Y");
        return Values.ERROR;
    }
    /**
	 * 获得跳转页面的路径
	 * @param msg  提示信息前缀
	 * @param isSuccess  是否操作成功
	 * @param showLinkId 继续操作链接的ID
	 * @return String
	 */
	public static String getReturnUrlString(String msg,boolean isSuccess,String[] showLinkId){
		msg = isSuccess ? msg+"成功" : msg+"失败";
		if(!isSuccess){
			WebUtils.getMessageManager().addErrorMessage(msg);
			CommonLogger.info(msg);
			ReturnLinkUtils.setShowLink(showLinkId);
			return ForwardPageUtils.getErrorPage();
		}
		WebUtils.getMessageManager().addInfoMessage(msg);
		CommonLogger.info(msg);
		ReturnLinkUtils.setShowLink(showLinkId);
		return ForwardPageUtils.getSuccessPage();
	}
}
