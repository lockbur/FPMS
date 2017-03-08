package com.forms.prms.web.budget.budgetReply.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.budget.budgetReply.dao.BudgetReplyDao;
import com.forms.prms.web.budget.budgetReply.domain.BudgetReplyBean;

@Service
public class BudgetReplyService {
	
	@Autowired
	private BudgetReplyDao dao;
	
	/**
	 * 获取需要批复列表
	 * @param budgetReplyBean
	 * @return
	 */
	public List<BudgetReplyBean> list(BudgetReplyBean budgetReplyBean) {
		BudgetReplyDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.list(budgetReplyBean);
	}
	
	/**
	 * 获取单个批复详情
	 * @param tmpltId
	 * @return
	 */
	public BudgetReplyBean getOneTemp(String tmpltId) {
		return dao.getOneTemp(tmpltId);
	}

	/**
	 * 根据单个批复模板获取其资产列表
	 * @param budgetReplyBean
	 * @return
	 */
	public List<BudgetReplyBean> getMont(BudgetReplyBean budgetReplyBean) {
		return dao.getMont(budgetReplyBean);
	}

	/**
	 * 预算批复
	 * @param budgetReplyBean
	 * @return
	 */
	public String reply(BudgetReplyBean budgetReplyBean) {
		CommonLogger.info("预算批复提交操作,(模板:"+budgetReplyBean.getTmpltId()+"批复金额:"+budgetReplyBean.getReplyAmt()+"),BudgetReplyService,reply");
		double count = 0;
		if(budgetReplyBean.getMontCodes() != null && budgetReplyBean.getMontCodes().length > 0) {
			for(int i=0; i<budgetReplyBean.getMontCodes().length; i++) {
				BudgetReplyBean tempBean = new BudgetReplyBean();
				tempBean.setTmpltId(budgetReplyBean.getTmpltId());
				tempBean.setMontCode(budgetReplyBean.getMontCodes()[i]);
				tempBean.setReplyAmt(new BigDecimal(budgetReplyBean.getReplyFees()[i]));
				tempBean.setReplyOper(budgetReplyBean.getReplyOper());
				count += Double.valueOf(budgetReplyBean.getReplyFees()[i]);
				dao.mergeToDetail(tempBean);
			}
		}
		budgetReplyBean.setAllotedAmt(new BigDecimal(count));
		dao.mergeToHeader(budgetReplyBean);
		return null;
	}
	/**
	 * 查询出模板已经分解金额
	 * @param budgetReplyBean
	 * @return
	 */
	public String getTmpltHaveSplit(BudgetReplyBean budgetReplyBean) {
		return dao.getTmpltHaveSplit(budgetReplyBean);
	}
	/**
	 * 得到指标已经分解金额
	 * @param budgetReplyBean
	 * @return
	 */
	public String getMontHaveSplit(BudgetReplyBean budgetReplyBean) {
		//原代码：return dao.getTmpltHaveSplit(budgetReplyBean);4-28修改为下方代码
		return dao.getMontHaveSplit(budgetReplyBean);
	}

}
