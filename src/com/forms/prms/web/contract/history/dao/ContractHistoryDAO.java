package com.forms.prms.web.contract.history.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.history.domain.ContractHistory;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.contract.initiate.domain.DzspInfo;
import com.forms.prms.web.contract.initiate.domain.StageInfo;
import com.forms.prms.web.contract.initiate.domain.TenancyDz;


@Repository
public interface ContractHistoryDAO {
	List<ContractHistory> queryList(Map<String, Object> map);

	ContractHistory getDetail(ContractHistory bean);

	List<CntDevice> getCntProj(ContractHistory bean);
	
	List<DzspInfo> getDZSPProj(ContractHistory bean);
	
	List<String> getMatrCode(String cntNum);
	
	List<TenancyDz> getTcyDz(ContractHistory bean);
	
	List<TenancyDz> getTcyDzByMatrCode(ContractHistory bean);
	
	List<StageInfo> getOnSchedule(ContractHistory bean);
	
	List<StageInfo> getOnDate(ContractHistory bean);
	
	List<StageInfo> getOnTerm(ContractHistory bean);

	List<CntDevice> queryCntDevice(@Param("cntNum") String cntNum,@Param("versionNo") String versionNo);
}
