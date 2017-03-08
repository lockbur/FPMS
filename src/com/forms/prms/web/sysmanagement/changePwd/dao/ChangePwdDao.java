package com.forms.prms.web.sysmanagement.changePwd.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.changePwd.domain.PwdSafeBean;

@Repository
public interface ChangePwdDao 
{
	
	public PwdSafeBean queryPwdSafe(PwdSafeBean param);
	
	public String existUserId(PwdSafeBean pwdSafeBean);
	
	public List<PwdSafeBean> existPwdQuestion(PwdSafeBean pwdSafeBean);
	
	public PwdSafeBean existEmail(PwdSafeBean pwdSafeBean);
	
	public PwdSafeBean queryPwdAnswer(PwdSafeBean pwdSafeBean);
	
	public void insertEmailResetPwd(PwdSafeBean pwdSafeBean);
	
	public PwdSafeBean selectEmailResetPwd(PwdSafeBean pwdSafeBean);
	
	public void deleteEmailResetPwd(PwdSafeBean pwdSafeBean);
}
