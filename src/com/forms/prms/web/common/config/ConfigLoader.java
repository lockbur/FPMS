package com.forms.prms.web.common.config;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class ConfigLoader implements ServletContextAware{

	private static ConfigLoader configLoader = null;
	private ServletContext servletContext;
	
	
	private ConfigLoader(){
		
	}
	
	public static ConfigLoader getInstance(){
		if(configLoader==null){
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public ServletContext getServletContext(){
		return servletContext;
	}

}
