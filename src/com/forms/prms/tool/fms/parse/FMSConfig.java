package com.forms.prms.tool.fms.parse;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.fms.parse.domain.ColumnBean;
import com.forms.prms.tool.fms.parse.domain.FMSBean;
import com.forms.prms.tool.fms.parse.domain.FileBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FMSConfig {

	private static Map<String, FMSBean> fmsConfig = new HashMap<String, FMSBean>();
	
	public static void init(String fileclasspath) throws Exception{
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(new Class[]{FMSBean.class, FileBean.class,  ColumnBean.class});
		xstream.alias("FMSConfig", List.class);
		List<FMSBean> fmsList = (List<FMSBean>)xstream.fromXML(new FileInputStream(ResourceUtils.getFile(fileclasspath)));
		fmsConfig.clear();
		for(FMSBean fms : fmsList){
			fmsConfig.put(fms.getId(), fms);
		}
	}
	
	public static Map<String, FMSBean> getFMSMap() throws Exception{
		if(fmsConfig.isEmpty()){
			throw new Exception("未初始化FMSConfig.xml配置信息");
		}
		return fmsConfig;
	}
	
	public static FMSBean getFMS(String id) throws Exception{
		if(fmsConfig.get(id) == null){
			CommonLogger.error("未找到对应的FMS配置信息！,FMSConfig,getFMS(),【id:"+id+"】");
			throw new Exception("未找到id（"+id+"）对应的FMS配置信息");
		}
		return fmsConfig.get(id);
	}
	
}
