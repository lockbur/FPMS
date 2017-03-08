package com.forms.prms.web.cleanpaydeal.cleanpaysure.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;
@Repository
public interface CleanPaySureDao {
	public List<CleanPayBean> getList(CleanPayBean bean);

	public void Agree(CleanPayBean bean);

	public void Back(CleanPayBean bean);
}
