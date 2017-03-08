package com.forms.prms.web.icms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.icms.dao.ScanDAO;
import com.forms.prms.web.icms.domain.ScanBean;

@Service
public class ScanService {
	@Autowired
	private ScanDAO dao;
	
	public void insertUUID(ScanBean bean){
		CommonLogger.info("增加一条扫描记录，ScanService，insertUUID");
		dao.mergeUUID(bean);
	}
	
	public void updateUUID(ScanBean bean){
		CommonLogger.info("更新ID为"+bean.getId()+"对应的关联编号，ScanService，updateUUID");
		dao.updateUUID(bean);
	}
	
	public ScanBean selectUUID(ScanBean bean){
		CommonLogger.info("通过ID为"+bean.getId()+"得到详细信息，ScanService，selectUUID");
		return dao.selectUUID(bean);
	}
	
	public ScanBean findICMSConfig(String dutyCode) throws Exception {
		CommonLogger.info("通过责任中心为"+dutyCode+"得到一个扫描对象，ScanService，findICMSConfig");
		ScanBean bean = dao.findICMSConfig(dutyCode);
		if(null == bean || null == bean.getIcmsCode() || "".equals(bean.getIcmsCode()))
		{
			throw new Exception("未找到所属一级行对应的ICMS省行号");
		}
//		String icms = bean.getIcms();
//		String[] array = icms.split("\\|");
 		bean.setServerIp(WebHelp.getSysPara("ICMS_SERVER_IP"));
 		bean.setServerPort(WebHelp.getSysPara("ICMS_SERVER_PORT"));
 		bean.setBankCode(WebHelp.getSysPara("ICMS_BANK_CODE"));
 		bean.setSystemId(WebHelp.getSysPara("ICMS_SYSTEM_ID"));
		bean.setDataType(WebHelp.getSysPara("ICMS_DATA_TYPE"));
		bean.setIcmsClassId(WebHelp.getSysPara("ICMS_CLASSID"));
		bean.setMsProvince(bean.getIcmsCode());
		bean.setDataProvince(bean.getIcmsCode());
		return bean;
	}
	
}
