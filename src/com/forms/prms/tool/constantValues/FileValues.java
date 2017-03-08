package com.forms.prms.tool.constantValues;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

public class FileValues {

	/**
	 * 获取src/templates目录下面的文件
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static File getTemplatesFle(String fileName) throws FileNotFoundException {
		return ResourceUtils.getFile("classpath:templates/" + fileName);
	}
}
