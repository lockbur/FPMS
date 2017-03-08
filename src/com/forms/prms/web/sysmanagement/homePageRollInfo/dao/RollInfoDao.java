package com.forms.prms.web.sysmanagement.homePageRollInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.homePageRollInfo.domain.RollInfoBean;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Repository
public interface RollInfoDao {
	public List<RollInfoBean> list(RollInfoBean rollInfoBean);
	
	public void addSubmit(RollInfoBean rollInfoBean);
	
	public void updateSubmit(RollInfoBean rollInfoBean);
	
	public void del(RollInfoBean rollInfoBean);

	public RollInfoBean queryInfo(RollInfoBean rollInfoBean);
	
	public String createRollId(RollInfoBean rollInfoBean);
	
}
