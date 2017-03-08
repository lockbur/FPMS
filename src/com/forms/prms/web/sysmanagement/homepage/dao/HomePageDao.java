package com.forms.prms.web.sysmanagement.homepage.dao;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.homePageRollInfo.domain.RollInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.ExcepInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.FmsErrorBean;
import com.forms.prms.web.sysmanagement.homepage.domain.OperWarnBean;
import com.forms.prms.web.sysmanagement.homepage.domain.SysRollInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.UserDesktopBean;


@Repository
public interface HomePageDao 
{
	public int updateOperWarnAdd(OperWarnBean operWarnBean);
	
	public int updateOperWarnLess(OperWarnBean operWarnBean);

	//public List<OperWarnBean> queryOperWarnList(String org1Code,String dutyCode,String roleId);
	public List<OperWarnBean> queryOperWarnList(HashMap map);

	public List<UserDesktopBean> queryUserDesktopList(String userId);

	
	public void addUserDesktop(UserDesktopBean userDesktopBean);
	
	public UserDesktopBean getDesktopInfo(UserDesktopBean userDesktopBean);
	
	public void delUserDesktop(UserDesktopBean userDesktopBean);
	
	public void updateUserDesktop(UserDesktopBean userDesktopBean);
	
	public String getCount(UserDesktopBean userDesktopBean);
	
	public List<SysRollInfoBean> querySysRollInfo(SysRollInfoBean sysRollInfoBean);
	
	public FmsErrorBean getFmsErrorNum(@Param("ouCode")String ouCode,@Param("org1Code")String org1Code,@Param("isSuperAdmin")String isSuperAdmin,@Param("userId")String userId);
	
	public RollInfoBean rollInfoDetail(RollInfoBean rollInfoBean);

	public void callSysWarnCount(@Param("dutyCode")String dutyCode,@Param("busType")String busType);
	
	public String getRollInfoCount();
	
	public String getExcepInfoCount(@Param("userId")String userId,@Param("org1Code")String org1Code);
	
	public List<ExcepInfoBean> getExcepInfos(ExcepInfoBean excepInfoBean);
	
	public ExcepInfoBean excepInfoDetail(ExcepInfoBean excepInfoBean);
	
	public void haveRead(ExcepInfoBean excepInfoBean);
	
	public List<UserDesktopBean> getList(UserDesktopBean userDesktopBean);
	
	public String getPPRight(@Param("userId")String userId);
	
	public String getInterfaceRight(@Param("userId")String userId);
}
