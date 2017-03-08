package com.forms.prms.web.sysmanagement.concurrent.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentBean;

@Repository
public interface ConcurrentDao {
	/**
	 * 查看是否有B锁
	 * @return
	 */
	String lock_B(ConcurrentBean bean);

	String lock_AB(ConcurrentBean bean);

	int addConcurrentLock(ConcurrentBean bean);

	void delConcurrentLock(ConcurrentBean bean);

	String lock_AC(ConcurrentBean bean);

	String lock_ABC(ConcurrentBean bean);

	String lock_BC(ConcurrentBean bean);


}
