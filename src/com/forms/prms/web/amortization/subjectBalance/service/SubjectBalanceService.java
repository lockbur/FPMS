package com.forms.prms.web.amortization.subjectBalance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.subjectBalance.dao.SubjectBalanceDAO;
import com.forms.prms.web.amortization.subjectBalance.domian.SubjectBalanceBean;

@Service
public class SubjectBalanceService {
	@Autowired
	private SubjectBalanceDAO dao;
	
	//存放查找到的临时数据,便于展示详细
	private Map<String, SubjectBalanceBean> tempData;
	
	/**
	 * 根据条件查找科目余额列表
	 * @param bean
	 * @return
	 */
	public List<SubjectBalanceBean> getSubjectBalanceList(SubjectBalanceBean bean){
		if(null == bean.getDutyCode() || "".equals(bean.getDutyCode())){
			bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		}
		if(null != bean.getBefDate() && !"".equals(bean.getBefDate())){
			bean.setBefDate(bean.getBefDate().replaceAll("-", ""));
		}
		if(null != bean.getAftDate() && !"".equals(bean.getAftDate())){
			bean.setAftDate(bean.getAftDate().replaceAll("-", ""));
		}
		SubjectBalanceDAO pageDao = PageUtils.getPageDao(dao);
		CommonLogger.info("查询科目余额批次号与下载文件批次号相同的科目余额信息,SubjectBalanceService,getSubjectBalanceList");
		List<SubjectBalanceBean> list = pageDao.getSubjetcBalanceList(bean);
		modifyTempData(list);
		return list;
	}
	
	/**
	 * 根据批次号和序列号获取科目余额详情
	 * @param batchNo
	 * @param seqNo
	 * @return
	 */
	public SubjectBalanceBean getDetail(String batchNo, String seqNo){
		if(null != tempData){
			CommonLogger.info("根据批次号："+batchNo+"和序列号："+seqNo+"获取科目余额详情),SubjectBalanceService,getDetail");
			return tempData.get(batchNo+seqNo);
		} else {
			return null;
		}
	}
	
	/**
	 * 更新临时数据中的数据
	 * @param list
	 */
	private void modifyTempData(List<SubjectBalanceBean> list){
		CommonLogger.info("更新临时表中的数据！,SubjectBalanceService,modifyTempData()");
		if(null == tempData){			
			tempData = new HashMap<String, SubjectBalanceBean>();
		}
		if(null != list && list.size() > 0){
			tempData.clear();
			for (SubjectBalanceBean bean : list) {
				String key = bean.getBatchNo() + bean.getSeqNo();
				tempData.put(key, bean);
			}
		}
		
	}
}
