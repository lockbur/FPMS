package com.forms.prms.web.amortization.subjectBalance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.amortization.subjectBalance.domian.SubjectBalanceBean;

@Repository
public interface SubjectBalanceDAO {

	/**
	 * 根据条件查询
	 * @return
	 */
	public List<SubjectBalanceBean> getSubjetcBalanceList(SubjectBalanceBean bean);
}
