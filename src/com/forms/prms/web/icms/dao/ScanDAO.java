package com.forms.prms.web.icms.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.icms.domain.ScanBean;

@Repository
public interface ScanDAO {
	public void mergeUUID(ScanBean bean);
	public void updateUUID(ScanBean bean);
	public ScanBean selectUUID(ScanBean bean);
	public ScanBean findICMSConfig(@Param("dutyCode")String dutyCode);
}
