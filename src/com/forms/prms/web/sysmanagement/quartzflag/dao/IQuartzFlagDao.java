package com.forms.prms.web.sysmanagement.quartzflag.dao;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.quartzflag.domain.QuartzFlag;
@Repository
public interface IQuartzFlagDao {
	public int addJob(QuartzFlag quartzFlag);
	
	public int delJob(QuartzFlag quartzFlag);
}
