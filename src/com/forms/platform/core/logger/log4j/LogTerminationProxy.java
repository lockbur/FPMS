package com.forms.platform.core.logger.log4j;

import org.slf4j.Logger;

import com.forms.platform.core.logger.ILogTermination;
import com.forms.platform.core.logger.LogLevel;
import com.forms.platform.core.stack.IStack;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 日志终端代理类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0 <br>
 * Date : 2013-10-3<br>
 */
public class LogTerminationProxy implements ILogTermination{
	
	/**
	 * 根据日志级别写日志
	 * @param level
	 * @param logBean
	 */
	public void write(LogLevel level, IStack logBean){
		Logger logger = logBean.getLogger();
		String message = logBean.getMessage();
		Throwable e = logBean.getException();
		switch(level){
		case DEBUG:
			//logger.debug(message, e);
			break;
		case INFO:
			if(message.indexOf("SELECT DISTINCT F1.* FROM PF_FUNCTION F1 LEFT JOIN PF_ROLE_FUNC_RLN RF ON F1.FUNC_ID = RF.FUNC_ID OR")!=-1){
				break;
			}else{
			logger.info(message, e);			
			break;}
		case WARN:
			logger.warn(message, e);
			break;
		case ERROR:
			logger.error(message, e);
			break;
		}
	}
}
