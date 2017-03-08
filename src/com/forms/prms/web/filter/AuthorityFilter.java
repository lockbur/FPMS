package com.forms.prms.web.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forms.platform.authority.AuthoriseComponent;
import com.forms.platform.authority.domain.Function;
import com.forms.platform.authority.domain.LoginObject;
import com.forms.platform.core.Common;
import com.forms.platform.core.tree.ITree;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.base.model.user.IUser;
import com.forms.platform.web.consts.WebConsts;
import com.forms.platform.web.filter.URLFilter;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;

public class AuthorityFilter extends URLFilter {

	private static final String UNLOGIN_URL_PARAM = "UNLOGIN_URL";

	private static final String UNAUTHORISED_URL_PARAM = "UNAUTHORIZED_URL";

	private String unloginUrl;

	private String unauthorisedUrl;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);

		unloginUrl = filterConfig.getInitParameter(UNLOGIN_URL_PARAM);
		if (Tool.CHECK.isEmpty(unloginUrl)) {
			throw new ServletException("Miss initial parameter 'UNLOGIN_URL'");
		}

		unauthorisedUrl = filterConfig.getInitParameter(UNAUTHORISED_URL_PARAM);
		if (Tool.CHECK.isEmpty(unauthorisedUrl)) {
			throw new ServletException(
					"Miss initial parameter 'UNAUTHORISED_URL'");
		}

	}

	@Override
	public void doFilter0(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String uri = WebUtils.getUriWithoutRoot();
		String requestType = request.getParameter(WebConsts.REQUEST_TYPE);
		String funcId = request.getParameter(WebConsts.FUNC_ID_KEY);
		String userId = null;
		IUser user = WebUtils.getUserModel();
		if (user != null) {
			userId = user.getUserId();
		}
		AuthoriseComponent ac = Common.getAuthoriseComponent();

		if (!ac.getLoginController().isLogin(
				(LoginObject) WebUtils
						.getSessionAttr(WebConsts.LOGIN_OBJECT_KEY))) {
			if (Tool.CHECK.isEmpty(funcId)
					|| ac.getAuthoriseController().needLogin(funcId, uri)) {
				if ("ajax".equals(requestType)) {
					processAjaxResponse(response, "access.unlogin", true);
				} else {
					response.sendRedirect(request.getContextPath() + unloginUrl);
				}
				return;
			}
		} else {
			setFunctionData(request, funcId);
			if (Tool.CHECK.isEmpty(funcId)
					|| !ac.getAuthoriseController().permit(userId, funcId, uri)) {
				if ("ajax".equals(requestType)) {
					processAjaxResponse(response, "access.failed", false);
				} else {
					response.sendRedirect(request.getContextPath()
							+ unauthorisedUrl);
				}
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void processAjaxResponse(HttpServletResponse response, String key, boolean gotoLogin)
			throws IOException {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			AbstractJsonObject jsonObject = new FailureJsonObject(Tool.i18n
					.getMessage(WebConsts.DEFAULT_RESOURCE_PACKAGE, key));
			if (gotoLogin) {
				jsonObject.put("gotoLogin", true);
			}
			jsonObject.writeValue(out);
		} finally {
			Tool.IO.closeQuietly(out);
		}
	}

	private void setFunctionData(HttpServletRequest request, String funcId) {
		ITree<Function> funcTree = Common.getAuthoriseComponent()
				.getFunctionManager().getFuncTree();
		List<Function> menuList = new ArrayList<Function>();

		if (funcTree != null) {
			List<Function> funcList = funcTree.getNodeList();
			Function visitFunc = null;
			for (Function f : funcList) {
				if (funcId.equals(f.getFuncId())) {
					visitFunc = f;
					break;
				}
			}
			if (visitFunc != null && "Y".equals(visitFunc.getIsMenu())) {
				while (visitFunc != null) {
					if ("Y".equals(visitFunc.getIsMenu())) {
						menuList.add(visitFunc);
					}
					visitFunc = visitFunc.getParent();
				}
				WebUtils.setSessionAttr(WebConsts.VISIT_MENU_LIST, menuList);
			} else {
				menuList = WebUtils.getSessionAttr(WebConsts.VISIT_MENU_LIST);
			}
		}
//		WebUtils.setRequestAttr(WebConsts.VISIT_MENU_LIST, menuList);
	}
}
