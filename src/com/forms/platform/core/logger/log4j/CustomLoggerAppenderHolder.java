package com.forms.platform.core.logger.log4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.io.FilenameUtils;

public class CustomLoggerAppenderHolder {

	private static String host;
	static{
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static String resolverCustomFileName(String oldFileName){
		String path = FilenameUtils.getFullPathNoEndSeparator(oldFileName);
		if(!path.endsWith("/"+host)){
			path += "/"+host;
		}
		return path+"/"+ FilenameUtils.getBaseName(oldFileName)+"."+FilenameUtils.getExtension(oldFileName);
	}
}
