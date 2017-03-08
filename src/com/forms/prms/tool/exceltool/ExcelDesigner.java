package com.forms.prms.tool.exceltool;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * author : liy_nby <br>
 * date : 2014-4-22<br>
 * Excel自定义接口<br>
 */
public interface ExcelDesigner {
	/**
	 * 自定义报表样式
	 * @param wbook
	 */
	void setExcelStyle(Workbook wbook) throws Exception;
	
	/**
	 * 自定义报表数据填充
	 * @param wbook
	 * @throws Exception
	 */
	void setExcelData(Workbook wbook) throws Exception;
	
	/**
	 * 自定义报表数据获取
	 * @param wbook
	 * @return
	 * @throws Exception
	 */
	List<Object> getExcelData(Workbook wbook) throws Exception;
}
