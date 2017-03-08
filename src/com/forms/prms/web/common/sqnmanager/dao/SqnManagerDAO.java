package com.forms.prms.web.common.sqnmanager.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



@Repository()
public interface SqnManagerDAO
{
	public String getNextSeq(@Param("prefix")String prefix,@Param("sqnId")String sqnId,@Param("suffix")String suffix);
}
