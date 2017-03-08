package com.forms.prms.tool.IDCreater;

import org.springframework.stereotype.Repository;

/**
 * author : ryan <br>
 * date : 2014-04-02<br>
 * 获取主键,类似如下规则：
 * 立项类【FP-YYYYMMDD-001】GET_IMPL_TASK_NO('FP');,
 *   其他【F-YYYYMMDD-001】   GET_IMPL_TASK_NO('F');
 */
@Repository()
public interface IDCreaterDao {
	
	/**
	 * parameter对应GET_IMPL_TASK_NO('FP')方法中的参数'FP'
	 */
	public String getId(String parameter);
	
}
