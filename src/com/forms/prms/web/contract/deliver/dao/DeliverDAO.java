package com.forms.prms.web.contract.deliver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.deliver.domain.DeliverForm;

@Repository
public interface DeliverDAO {

	/**
	 * 列表
	 * 
	 * @param form
	 * @return
	 */
	public List<DeliverForm> list(DeliverForm form);

	/**
	 * 发起移交
	 * 
	 * @param form
	 * @return
	 */
	public int deliver(DeliverForm form);

	/**
	 * 接受移交列表
	 * 
	 * @param form
	 * @return
	 */
	public List<DeliverForm> deliverList(DeliverForm form);

	/**
	 * 接受移交
	 * 
	 * @param form
	 * @return
	 */
	public int accept(DeliverForm form);

	/**
	 * 拒绝移交
	 * 
	 * @param form
	 * @return
	 */
	public int reject(DeliverForm form);

	/**
	 * 取消列表
	 * 
	 * @param form
	 * @return
	 */
	public List<DeliverForm> cancelList(DeliverForm form);

	/**
	 * 取消移交
	 * 
	 * @param form
	 * @return
	 */
	public int cancel(DeliverForm form);

	public List<DeliverForm> getDutyS(DeliverForm form);

	public void callWarnCount(@Param("deliverDutyCode")String deliverDutyCode);

}
