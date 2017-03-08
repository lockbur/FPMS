package com.forms.prms.web.contract.scan.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractScanDAO {

	public String findDutyCode(@Param("contractNo")String contractNo);
}
