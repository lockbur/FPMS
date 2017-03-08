package com.forms.prms.web.task.util;

public class FileUtil {
	/**
	 * 格式化路径
	 * 
	 * @param path
	 * @param ifEnd  是否加上结束符
	 * @return
	 */
	public static String formatPath(String path, boolean ifEnd) {
		path = path.replace("\\", "/");
		if (ifEnd&&path.charAt(path.length() - 1) != '/'
				&& path.charAt(path.length() - 1) != '\\')
			path = path + "/";
		else if (path.charAt(path.length() - 1) == '\\') {
			path = path.replace("\\", "/");
		}
		
		return path;
	}
}
