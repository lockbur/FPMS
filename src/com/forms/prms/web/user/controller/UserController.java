package com.forms.prms.web.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.authority.domain.LoginObject;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;

import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.user.domain.CtrlFileBean;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	public static User user = null;
	
	@RequestMapping("/gotoLogin.do")
	public String gotoLogin() {
		return "user/login";
	}

	@RequestMapping("/login.do")
	public String login(User user)
	{
		User nUser = userService.getUser(user.getUserId());
		if (nUser != null) 
		{
			HttpSession session = WebUtils.getSession();
			try
			{
				session.invalidate();
			}
			catch (IllegalStateException e)
			{
				
			}
			session = WebUtils.getRequest().getSession(true);
			LoginObject lo = new LoginObject(session.getId(), user.getUserId());
			userService.login(lo);
			String date=userService.getDate();//获取数据库时间
			session.setAttribute("nowDate", date);//放在Session里面 初始化datepicker控件的值
			session.setAttribute(WebConsts.LOGIN_OBJECT_KEY, lo);
			String loginIp = WebUtils.getRequest().getRemoteAddr();
			nUser.setLoginIp(loginIp);
			nUser.setSessionId(session.getId());
			userService.initUser(nUser);
			WebUtils.saveUserModel(nUser);
			return "redirect:/homepage/main.do?" + WebConsts.FUNC_ID_KEY + "=0001";
		} 
		else
		{
			return "user/login";
		}
	}

	
	/*@RequestMapping("/main.do")
	public String gotoMain(HttpServletRequest request) {
		List<CtrlFileBean> list = userService.getDownloadList();
		WebUtils.setRequestAttr("fileList", list);
		return "welcome";
	}*/

	@RequestMapping("/logout.do")
	public String logout() {
		User sessionUser = WebHelp.getLoginUser();
		if(sessionUser==null){
			//该用户第一次登录
		}else{
			//同时删除在线用户表信息
			try{
				userService.deleteOnlineUser(sessionUser.getUserId());
			}catch(Exception e){
				//donothing
			}
		}
		userService.logout((LoginObject) WebUtils.getSession().getAttribute(
				WebConsts.LOGIN_OBJECT_KEY));
		WebUtils.getSession().invalidate();
		return gotoLogin();
	}
	@RequestMapping("downloadFile.do")
	public String downloadFile(HttpServletRequest request,HttpServletResponse response,CtrlFileBean ctrlFileBean) throws Exception {
		userService.downloadFile(request,response,ctrlFileBean);
		return null;
	}
	
}
