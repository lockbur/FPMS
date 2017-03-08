package com.forms.prms.web.budget.budgetResolve.resolveQuery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.budget.budgetResolve.resolveQuery.domain.ResolveQuery;

@Repository
public interface ResolveQueryDao {

	/**
	 * @param resolveQuery
	 * @return
	 */
	public List<ResolveQuery> getResolveQueryList( ResolveQuery resolveQuery );
	
	/**
	 * @param resolveQuery
	 * @return
	 */
	public ResolveQuery getResolveQuery( ResolveQuery resolveQuery );
}
