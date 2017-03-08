package com.forms.prms.web.sysmanagement.quartzflag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.prms.web.sysmanagement.quartzflag.dao.IQuartzFlagDao;
import com.forms.prms.web.sysmanagement.quartzflag.domain.QuartzFlag;

@Service
public class QuartzFlagService
{
	@Autowired
	private IQuartzFlagDao iQuartzFlagDao;
	
	public int addJob(String quartzJob){
		QuartzFlag quartzFlag = new QuartzFlag();
		quartzFlag.setQuartzJob(quartzJob);
		int flag = 0;
		try
		{
			flag = iQuartzFlagDao.addJob(quartzFlag);
		}
		catch (Exception e)
		{
			flag = 0;
		}
		return flag;
	} 
	
	public int delJob(String quartzJob){
		QuartzFlag quartzFlag = new QuartzFlag();
		quartzFlag.setQuartzJob(quartzJob);
		return iQuartzFlagDao.delJob(quartzFlag);
	} 
}
