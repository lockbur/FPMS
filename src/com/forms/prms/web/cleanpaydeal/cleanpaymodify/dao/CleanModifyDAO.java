package com.forms.prms.web.cleanpaydeal.cleanpaymodify.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.cleanpaydeal.cleanpay.domain.CleanPayBean;

@Repository
public interface CleanModifyDAO {

	/**
	 * 查询暂收结清信息列表
	 * 
	 * @return
	 */
	public List<CleanPayBean> cleanList(CleanPayBean cleanPayBean);

	/**
	 * 暂收结清信息修改保存或提交
	 * @param cleanPayBean
	 * @return
	 */
	public int cleanpayEditSaveOrSubmit(CleanPayBean cleanPayBean);

	/**
	 * 查询暂收结清明细信息
	 * @param cleanPayBean
	 * @return
	 */
	public CleanPayBean queryCleanedDetail(CleanPayBean cleanPayBean);
}
