package com.forms.platform.core.logger.log4j;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.mvc.ServletHelp;

public class MybatisLog extends Slf4jImpl {

	private Log mylog;
	
	public MybatisLog(String arg0) {
		super(arg0);
		this.mylog = (Log)Tool.BEAN.getProperty(this, "log");
	}

	@Override
	  public boolean isDebugEnabled() {
	    return true;
	  }

	  @Override
	  public boolean isTraceEnabled() {
	    return mylog.isTraceEnabled();
	  }

	  @Override
	  public void error(String s, Throwable e) {
	    mylog.error(facade(s), e);
	  }

	  @Override
	  public void error(String s) {
	    mylog.error(facade(s));
	  }

	  @Override
	  public void debug(String s) {
		  if(null != s){
			  if(s.startsWith("ooo Using Connection")
			  || s.startsWith("==>  Preparing:")
			  || s.startsWith("==> Parameters:")
			  || s.indexOf("SELECT DISTINCT F1.* FROM PF_FUNCTION F1 LEFT JOIN PF_ROLE_FUNC_RLN RF ON F1.FUNC_ID = RF.FUNC_ID OR")!=-1){
				  return;
			  } 
		  }
		  
	    mylog.debug(facade(s));
	  }
	  
	  private String facade(String s){
		  try{
			  if(null != s && !"".equals(s.trim())){
				  s = "requestId:"+ServletHelp.getRequest().getRequestId()+" [" + ServletHelp.getRequest().getRequestIp() + "] \t"+s;
			  }
		  }catch(Throwable t){
			  
		  }
		  return s;
	  }

	  @Override
	  public void trace(String s) {
	    mylog.trace(facade(s));
	  }

	  @Override
	  public void warn(String s) {
	    mylog.warn(facade(s));
	  }

	}