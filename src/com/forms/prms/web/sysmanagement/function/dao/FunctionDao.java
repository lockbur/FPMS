package com.forms.prms.web.sysmanagement.function.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.platform.authority.domain.Function;

@Repository
public interface FunctionDao {

	public List<Function> queryFunction(Function func);
	
	/**
	 * 根据角色id集合查询菜单
	 * @param listRoleId
	 * @return
	 */
	public List<Function> queryFuncByRoleIds(@Param("list")List<String> listRoleId);

	public List<Function> check(@Param("funcId")String funcId);
}
