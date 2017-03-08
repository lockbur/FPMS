package com.forms.prms.web.monthOver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.monthOver.domain.MonthOverBean;

@Repository
public interface MonthOverDao {
	
	
	public List<MonthOverBean> list(MonthOverBean mb);//月结状态列表

	public void change(MonthOverBean mb);//月结

	public MonthOverBean getMaxDataFlag(@Param("org1Code")String org1Code);//查询最大时的月结状态

	public void insert(MonthOverBean mb);//插入一条月结结束的状态
	
	public String ajaxCheckProvision(@Param("org1Code")String org1Code);//校验冲销任务状态
	
	public String ajaxCheckPP(@Param("org1Code")String org1Code);//校验预提待摊任务状态
	
	public List<MonthOverBean> getAllMonthOverFlag(MonthOverBean bean);//查看全国省行月结状态
	
	public String checkOpenOrg1(@Param("org1Code")String org1Code);//检查本一级行是否在开放的一级行

}
