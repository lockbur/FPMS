package com.forms.dealexceldata.exceldealtool;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import com.forms.platform.core.logger.CommonLogger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ExcelConfig {
	private static Map<String, ImportExcelBean> importExcelConfig = new HashMap<String, ImportExcelBean>();
	
	public static void init(String fileclasspath) throws Exception{
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(new Class[]{ImportExcelBean.class});
		xstream.alias("ImportExcelConfig", List.class);
		List<ImportExcelBean> list = (List<ImportExcelBean>)xstream.fromXML(new FileInputStream(ResourceUtils.getFile(fileclasspath)));
		importExcelConfig.clear();
		for(ImportExcelBean importExcelBean : list){
			importExcelConfig.put(importExcelBean.getId(),importExcelBean);
		}
	}
	
	public static Map<String, ImportExcelBean> getImportExcelMap() throws Exception{
		if(importExcelConfig.isEmpty()){
			throw new Exception("未初始化importExcelConfig.xml配置信息");
		}
		return importExcelConfig;
	}
	
	public static ImportExcelBean getImportExcel(String id) throws Exception{
		if(importExcelConfig.get(id) == null){
			CommonLogger.error("未找到对应的ExcelConfig配置信息！,ExcelConfig,getImportExcel(),【id:"+id+"】");
			throw new Exception("未找到id（"+id+"）对应的ExcelConfig配置信息");
		}
		return importExcelConfig.get(id);
	}
	
}
