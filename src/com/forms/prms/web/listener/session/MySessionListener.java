package com.forms.prms.web.listener.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.web.user.service.UserService;
public class MySessionListener implements HttpSessionListener{
	
	private UserService userService;

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		//session创建
		System.out.println("########################## SESSIONCREATED ID:"+arg0.getSession().getId()+"#############################");
		//arg0.getSession().setMaxInactiveInterval(10);//session生命周期10s测试用
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//session销毁
		System.out.println("########################## SESSIONDESTROYED ID:"+arg0.getSession().getId()+"#############################");
		String sessionId = arg0.getSession().getId();
		try{
			this.userService = SpringUtil.getBean(com.forms.prms.web.user.service.UserService.class);
			this.userService.deleteOnlineUserBySessionId(sessionId);
		}catch(Exception e){
			//donothing
		}
	}
}
