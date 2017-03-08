package com.forms.prms.tool.exceltool;

import java.io.OutputStream;

/**
 * author : liy_nby <br>
 * date : 2014-4-22<br>
 * 操作Excel的接口<br>
 */
public interface ExcelHandler {

	/**
	 * 生成Excel文件
	 * @param outputStream
	 */
	void outputExcelFile(OutputStream outputStream) throws Exception;
	
	/**
	 * 填充数据
	 * @param bean
	 * @throws Exception
	 */
	<U,L> void fillExcel(ExcelBean<U,L> bean) throws Exception;
	
	/**
	 * 获得数据
	 * @param bean
	 * @throws Exception
	 */
	<U,L> void getBeanFromExcel(ExcelBean<U,L> bean) throws Exception;
}
