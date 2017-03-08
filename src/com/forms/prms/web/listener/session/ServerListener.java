package com.forms.prms.web.listener.session;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.user.service.UserService;

public class ServerListener implements ServletContextListener{
	private UserService userService;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("###################### 服务器关闭监听 ######################");
		try {
//			this.userService = SpringUtil.getBean(com.forms.prms.web.user.service.UserService.class);
//			System.out.println("dfdsfdsfdfd");
		} catch (Exception e) {
			CommonLogger.error(e.getMessage());
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("########################## 服务器开启监听 #############################");
		
		
	}

}
