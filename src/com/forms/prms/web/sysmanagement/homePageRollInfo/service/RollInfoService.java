package com.forms.prms.web.sysmanagement.homePageRollInfo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.homePageRollInfo.dao.RollInfoDao;
import com.forms.prms.web.sysmanagement.homePageRollInfo.domain.RollInfoBean;
import java.util.List;
/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Service
public class RollInfoService {
	@Autowired
	private RollInfoDao rollInfoDao;
	/**
	 * @methodName list
	 * desc  
	 * 列表查询
	 * @param rollInfoBean
	 * @return
	 */
	public List<RollInfoBean> list(RollInfoBean rollInfoBean){
		CommonLogger.info("首页滚动信息列表查询,RollInfoService,list");
		RollInfoDao pageDao=PageUtils.getPageDao(rollInfoDao);
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		String gradeOrOrgCode = rollInfoBean.getVisualGrade();
		//初始化进入
		if(Tool.CHECK.isEmpty(gradeOrOrgCode)){
			if("1".equals(rollInfoBean.getIsA0001SuperAdmin())){
			rollInfoBean.setVisualGrade("");
			rollInfoBean.setVisualOrg("");
			}
			else{
				rollInfoBean.setVisualGrade("2");
				rollInfoBean.setVisualOrg(org1Code);
			}
		}
		else
		//1-全国
		if("1".equals(gradeOrOrgCode)){
			rollInfoBean.setVisualGrade(gradeOrOrgCode);
			rollInfoBean.setVisualOrg("");	
		}
		else
		//2-省行
		if("2".equals(gradeOrOrgCode)){
			rollInfoBean.setVisualGrade(gradeOrOrgCode);
			rollInfoBean.setVisualOrg(org1Code);	
		}
		//3-总行超级管理员查看某省
		else{
			rollInfoBean.setVisualGrade("2");
			rollInfoBean.setVisualOrg(gradeOrOrgCode);
		}		
		return pageDao.list(rollInfoBean);
	}
	/**
	 * @methodName addSubmit
	 * desc  
	 * 新增提交
	 * @param rollInfoBean
	 */
	public void addSubmit(RollInfoBean rollInfoBean){
		CommonLogger.info("首页滚动信息新增ID为"+rollInfoBean.getRollId()+"的信息，RollInfoService，addSubmit");
		rollInfoBean.setAddUid(WebHelp.getLoginUser().getUserId());
		if(rollInfoBean.getVisualGrade().equals("1")){
			rollInfoBean.setVisualOrg("");
		}
		else
		if(rollInfoBean.getVisualGrade().equals("2")){
		  rollInfoBean.setVisualOrg(WebHelp.getLoginUser().getOrg1Code());
		}
		else {
			rollInfoBean.setVisualOrg(rollInfoBean.getVisualGrade());
			rollInfoBean.setVisualGrade("2");
		}
		rollInfoDao.addSubmit(rollInfoBean);
	}
	
	/**
	 * @methodName addSubmit
	 * desc  
	 * 更新提交
	 * @param rollInfoBean
	 */
	public void updateSubmit(RollInfoBean rollInfoBean){
		rollInfoBean.setLupdUid(WebHelp.getLoginUser().getUserId());
		if(rollInfoBean.getVisualGrade().equals("1")){
			rollInfoBean.setVisualOrg(null);
		}else {
			rollInfoBean.setVisualOrg(rollInfoBean.getVisualGrade());
			rollInfoBean.setVisualGrade("2");
		}
		CommonLogger.info("更新提交,RollInfoService,updateSubmit");
		rollInfoDao.updateSubmit(rollInfoBean);
	}
	
	/**
	 * @methodName addSubmit
	 * desc  
	 * 删除
	 * @param rollInfoBean
	 */
	public void del(RollInfoBean rollInfoBean){
		CommonLogger.info("首页滚动信息删除ID为"+rollInfoBean.getRollId()+"的信息，RollInfoService，del");
		rollInfoDao.del(rollInfoBean);
	}
	public RollInfoBean queryInfo(RollInfoBean rollInfoBean){
		String org1Code=WebHelp.getLoginUser().getOrg1Code();
		rollInfoBean.setOrg1Code(org1Code);
		rollInfoBean.setIsA0001SuperAdmin("1".equals(WebHelp.getLoginUser().getIsSuperAdmin())&&"A0001".equalsIgnoreCase(rollInfoBean.getOrg1Code())?"1":"0");
		return rollInfoDao.queryInfo(rollInfoBean);
	}
	/**
	 * @methodName addSubmit
	 * desc  
	 * 生成ID
	 * @param rollInfoBean
	 */
	public String createRollId(RollInfoBean rollInfoBean){
		CommonLogger.info("生成ID,RollInfoService,createRollId");
		return rollInfoDao.createRollId(rollInfoBean);
	}
	
}
