package com.forms.prms.web.sysmanagement.homepage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.homePageRollInfo.domain.RollInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.ExcepInfoBean;
import com.forms.prms.web.sysmanagement.homepage.dao.HomePageDao;
import com.forms.prms.web.sysmanagement.homepage.domain.FmsErrorBean;
import com.forms.prms.web.sysmanagement.homepage.domain.HomePageBean;
import com.forms.prms.web.sysmanagement.homepage.domain.OperWarnBean;
import com.forms.prms.web.sysmanagement.homepage.domain.SysRollInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.UserDesktopBean;

@Service
public class HomePageService {

	@Autowired
	private HomePageDao homePageDao;

	public int dealOperWarn(String dutyCode, String functype, boolean operFlag) {
		OperWarnBean bean = new OperWarnBean();
		bean.setDutyCode(dutyCode);
		bean.setFunctype(functype);
		if (operFlag) {
			return homePageDao.updateOperWarnAdd(bean);
		} else {
			return homePageDao.updateOperWarnLess(bean);
		}
	}

	public HomePageBean mGetOperNum(String org1Code,String dutyCode,String roleId) 
	{
		List<OperWarnBean> loc_list = new ArrayList<OperWarnBean>();
		String rid[] = roleId.split(",");
		
		StringBuffer condiStr = new StringBuffer(" in (");
		for (int i = 0; i < rid.length; i++) {			
			condiStr.append((i==0?"'":",'") + rid[i] + "'");
		}
		condiStr.append(")");
		HashMap map = new HashMap();
		map.put("org1Code", org1Code);
		map.put("dutyCode", dutyCode);
		map.put("roleIds", Arrays.asList(rid));
		
		loc_list = homePageDao.queryOperWarnList(map);
		HomePageBean loc_bean = new HomePageBean();
		if(loc_list!=null&&loc_list.size()!=0)
		{
			for (OperWarnBean operWarnBean : loc_list) 
			{
				if ("C1".equals(operWarnBean.getFunctype())) 
				{
					loc_bean.setOperNum01(operWarnBean.getOperNum());
				}
				else if ("C2".equals(operWarnBean.getFunctype())) 
				{
					loc_bean.setOperNum02(operWarnBean.getOperNum());
				}
				else if ("C3".equals(operWarnBean.getFunctype())) 
				{
					loc_bean.setOperNum03(operWarnBean.getOperNum());
				} 
				else if ("P0".equals(operWarnBean.getFunctype()))
				{
					loc_bean.setOperNum10(operWarnBean.getOperNum());
				} 
				else if ("P1".equals(operWarnBean.getFunctype()))
				{
					loc_bean.setOperNum11(operWarnBean.getOperNum());
				} 
				else if ("P2".equals(operWarnBean.getFunctype()))
				{
					loc_bean.setOperNum12(operWarnBean.getOperNum());
				} 
				else if ("P3".equals(operWarnBean.getFunctype())) 
				{
					loc_bean.setOperNum13(operWarnBean.getOperNum());
				}
				else if("T1".equals(operWarnBean.getFunctype())){
					loc_bean.setOperNum21(operWarnBean.getOperNum());
				}
				else if("T2".equals(operWarnBean.getFunctype())){
					loc_bean.setOperNum22(operWarnBean.getOperNum());
				}
				else if("T3".equals(operWarnBean.getFunctype())){
					loc_bean.setOperNum23(operWarnBean.getOperNum());
				}
				else if("T4".equals(operWarnBean.getFunctype())){
					loc_bean.setOperNum24(operWarnBean.getOperNum());
				}
			}
		}
		return loc_bean;

	}

	public List<UserDesktopBean> mGetUserDesktopList(String userId) 
	{
		List<UserDesktopBean> list=homePageDao.queryUserDesktopList(userId);
		if(list==null)
		{
			list =new ArrayList<UserDesktopBean>();
		}
		return list;
	}

	
	public void addUserDesktop(UserDesktopBean userDesktopBean){
		homePageDao.addUserDesktop(userDesktopBean);
	}
	
	public UserDesktopBean getDesktopInfo(UserDesktopBean userDesktopBean){
		return homePageDao.getDesktopInfo(userDesktopBean);
	}
	
	public void delUserDesktop(UserDesktopBean userDesktopBean){
		homePageDao.delUserDesktop(userDesktopBean);
	}
	public void updateUserDesktop(UserDesktopBean userDesktopBean){
		homePageDao.updateUserDesktop(userDesktopBean);
	}
	/**
	 * @methodName getCount
	 * desc  
	 * 计算此用户收藏功能的个数，最多只能收藏15个
	 * @param userDesktopBean
	 * @return
	 */
	public boolean getCount(UserDesktopBean userDesktopBean){
		Integer numb = new Integer(homePageDao.getCount(userDesktopBean));
		if(numb.intValue()>14){
			return false;
		}else{
			return true;
		}
	}

	public List<SysRollInfoBean> mGetSysRollInfo()
	{
		List<SysRollInfoBean> list=new ArrayList<SysRollInfoBean>();
		SysRollInfoBean sysRollInfoBean=new SysRollInfoBean();
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		sysRollInfoBean.setOrg1Code(org1Code);
		String org2Code=WebHelp.getLoginUser().getOrg2Code();
		sysRollInfoBean.setOrg2Code(org2Code);
		String dutyCode=WebHelp.getLoginUser().getDutyCode();
		sysRollInfoBean.setDutyCode(dutyCode);
		list=homePageDao.querySysRollInfo(sysRollInfoBean);
		return list;
		
	}

	public FmsErrorBean mGetFmsErrorNum(String ouCode,String userId) {
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		String isSuperAdmin = WebHelp.getLoginUser().getIsSuperAdmin();
		//如果用户有查看FMS返回文件的权限则统计返回文件的异常条数
		FmsErrorBean errorBean = homePageDao.getFmsErrorNum(ouCode,org1Code,isSuperAdmin,userId);
		return errorBean;
	}
	
	public RollInfoBean rollInfoDetail(RollInfoBean rollInfoBean) {
		return homePageDao.rollInfoDetail(rollInfoBean);
	}
	

	
	

	public static HomePageService getInstance(){
		return SpringUtil.getBean(HomePageService.class);
	}
	/**
	 * 
	 * @param dutyCode 责任中心
	 * @param busType  业务类型  C：合同 P：付款
	 * 
	 * 
	 * */
	public  synchronized void  DealSysWarnCount(String dutyCode,String  busType)
	{
	   homePageDao.callSysWarnCount(dutyCode,busType);
	}
	

	public String getExcepInfoCount(String userId) {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		return homePageDao.getExcepInfoCount(userId,org1Code);
	}
	public List<ExcepInfoBean> getExcepInfos(String userId) {
		CommonLogger.info("用户"+WebHelp.getLoginUser().getUserId()+"获取异常信息，HomePageService，getExcepInfos");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		HomePageDao pageDao=PageUtils.getPageDao(homePageDao);
		ExcepInfoBean exi = new ExcepInfoBean();
		exi.setUserId(userId);
		exi.setOrg1Code(org1Code);
		return pageDao.getExcepInfos(exi);
	}
	public ExcepInfoBean excepInfoDetail(ExcepInfoBean excepInfoBean) {
		return homePageDao.excepInfoDetail(excepInfoBean);
	}
	
	
	public void haveRead(ExcepInfoBean excepInfoBean) {	
		CommonLogger.info("用户"+WebHelp.getLoginUser().getUserId()+"已阅异常信息，HomePageService，haveRead");
		String[] excepInfoChecked = excepInfoBean.getExcepInfoChecked();
		if(excepInfoChecked!=null){
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
				for(int i=0;i<excepInfoChecked.length;i++){
					Map<String, String> map = new HashMap<String, String>();
					map.put("excepInfoChecked",excepInfoChecked[i]);
					list.add(map);
				}
				excepInfoBean.setExcepInfoList(list);
		}
		homePageDao.haveRead(excepInfoBean);
	}
	
	
	
	public List<UserDesktopBean> getList(UserDesktopBean userDesktopBean) {
		return homePageDao.queryUserDesktopList(userDesktopBean.getUserId());
	}
	
	public String getPPRight(){
		if("1".equals(homePageDao.getPPRight(WebHelp.getLoginUser().getUserId()))){
			return "ppRight";
		}
		return "none";
	}
	
	public String getInterfaceRight(){
		if("1".equals(homePageDao.getInterfaceRight(WebHelp.getLoginUser().getUserId()))){
			return "interfaceRight";
		}
		return "none";
	}
}
