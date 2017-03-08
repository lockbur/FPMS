package com.forms.prms.web.contract.scan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.contract.scan.dao.ContractScanDAO;

@Service
public class ContractScanService {
	@Autowired 
	ContractScanDAO contractScanDAO;
	
	
	public String findDutyCode(String contractNo){
		CommonLogger.info("找到合同号为"+contractNo+"对应的创建部门，ContractScanService，findDutyCode");
		return contractScanDAO.findDutyCode(contractNo);
	}
}
