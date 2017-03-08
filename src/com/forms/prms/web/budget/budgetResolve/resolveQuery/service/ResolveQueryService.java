package com.forms.prms.web.budget.budgetResolve.resolveQuery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.prms.web.budget.budgetResolve.resolveQuery.dao.ResolveQueryDao;
import com.forms.prms.web.budget.budgetResolve.resolveQuery.domain.ResolveQuery;

@Service
public class ResolveQueryService {

	@Autowired
	private ResolveQueryDao dao;
	
	/**
	 * @param resolveQuery
	 * @return
	 */
	public List<ResolveQuery> getResolveQueryList( ResolveQuery resolveQuery )
	{
		ResolveQueryDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getResolveQueryList(resolveQuery);
	}
	
	/**
	 * @param resolveQuery
	 * @return
	 */
	public ResolveQuery getResolveQuery( ResolveQuery resolveQuery )
	{
		return dao.getResolveQuery(resolveQuery);
	}
}
