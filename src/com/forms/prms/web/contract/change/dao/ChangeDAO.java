package com.forms.prms.web.contract.change.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.change.domain.ChangeForm;

@Repository
public interface ChangeDAO {

	/**
	 * 列表
	 * 
	 * @param form
	 * @return
	 */
	public List<ChangeForm> list(ChangeForm form);

	/**
	 * 发起变更
	 * 
	 * @param cntNum
	 * @return
	 */
	public int toChange(ChangeForm form);

}
