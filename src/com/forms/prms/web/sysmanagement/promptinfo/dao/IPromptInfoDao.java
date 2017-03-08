/**
 * 
 */
package com.forms.prms.web.sysmanagement.promptinfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.promptinfo.domain.PromptInfoBean;


/**
 * @author ZhengCuixian
 *
 */
@Repository
public interface IPromptInfoDao {

	public List<PromptInfoBean> query(PromptInfoBean promptInfoBean);

	public void edit(PromptInfoBean promptInfoBean);

	public void delete(PromptInfoBean promptInfoBean);

	public void add(PromptInfoBean promptInfoBean);

	public int checkPrimaryKey(PromptInfoBean promptInfoBean);

	public List<PromptInfoBean> promptInfoList();

}
