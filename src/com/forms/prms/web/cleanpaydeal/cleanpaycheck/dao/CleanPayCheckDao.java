package com.forms.prms.web.cleanpaydeal.cleanpaycheck.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.cleanpaydeal.cleanpaycheck.domain.CleanPayCheckBean;

@Repository
public interface CleanPayCheckDao {
	public List<CleanPayCheckBean> getList(CleanPayCheckBean bean);

	public void Agree(CleanPayCheckBean bean);

	public void Back(CleanPayCheckBean bean);
}
