package com.forms.prms.web.job.sysquartzrunning.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * author : wuqm <br>
 * date : 2014-12-1<br>
 * 
 */
@Repository
public interface ISysQuartzRunningDao
{
	public int insertQuartz(@Param("quartzFlag") String quartzFlag);
	
	public int delQuartz(@Param("quartzFlag") String quartzFlag);
}
