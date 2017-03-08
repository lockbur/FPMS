package com.forms.prms.web.amortization.accEntry.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.accEntry.domain.CglTrade;

@Repository
public interface AccEntryDAO {
	/**
	 * 获取会计分录明细
	 * @param cntNum
	 * @return
	 */
	public List<CglTrade> getAccEntry(@Param("cntNum")String cntNum,@Param("startDate")String startDate,@Param("endDate")String endDate);
}
