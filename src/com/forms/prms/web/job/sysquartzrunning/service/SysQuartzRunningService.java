package com.forms.prms.web.job.sysquartzrunning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.job.sysquartzrunning.dao.ISysQuartzRunningDao;

/**
 * author : wuqm <br>
 * date : 2014-12-1<br>
 * 
 */
@Service
public class SysQuartzRunningService
{
	@Autowired
	private ISysQuartzRunningDao iSysQuartzRunningDao;
	
	/**
	 * 定时器启动锁定
	 * @param quartzFlag
	 * @return
	 */
	public boolean lockQuartz(String quartzFlag){
		boolean flag = true ;
		try
		{
			int count = 0;
			count = iSysQuartzRunningDao.insertQuartz(quartzFlag);
			flag = count>0 ? true:false ;
		}
		catch (Exception e)
		{
			CommonLogger.info("监听器【"+quartzFlag+"】执行中");
			flag = false;
		}
		return flag;
	}
	/**
	 * 定时器解锁
	 * @param quartzFlag
	 */
	public void unlockQuartz(String quartzFlag){
		iSysQuartzRunningDao.delQuartz(quartzFlag);
	}
}
