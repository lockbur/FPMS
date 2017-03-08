package com.forms.prms.web.sysmanagement.projtype.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.projtype.domain.ProjTypeBean;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Repository
public interface ProjTypeDao {
	public List<ProjTypeBean> list(ProjTypeBean projTypeBean);
	
	public void addSubmit(ProjTypeBean projTypeBean);
	
	public void updateSubmit(ProjTypeBean projTypeBean);
	
	public void del(ProjTypeBean projTypeBean);

	public ProjTypeBean queryInfo(ProjTypeBean projTypeBean);
	
	public String createRollId(ProjTypeBean projTypeBean);
	
}
