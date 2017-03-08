package com.forms.prms.web.contract.freeze.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.contract.freeze.domain.Contract;

@Repository
public interface FreezeDAO {

	/**
	 * 可冻结列表
	 * 
	 * @param c
	 * @return
	 */
	public List<Contract> freezeList(Contract c);

	/**
	 * 可解冻列表
	 * 
	 * @param c
	 * @return
	 */
	public List<Contract> unfreezeList(Contract c);

	/**
	 * 冻结
	 * 
	 * @param cntNum
	 * @return
	 */
	public int freeze(String cntNum);

	/**
	 * 解冻
	 * 
	 * @param cntNum
	 * @return
	 */
	public int unfreeze(String cntNum);

}
