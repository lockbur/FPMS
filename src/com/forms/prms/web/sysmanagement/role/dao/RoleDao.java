package com.forms.prms.web.sysmanagement.role.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.platform.authority.domain.RoleFuncRln;
import com.forms.prms.web.sysmanagement.role.domain.Role;

@Repository
public interface RoleDao {

	public List<Role> queryRole(Role role);
	
	public Role findRole(@Param("roleId")String roleId);

	public void updateRole(Role role);
	
	public void insertRole(Role role);
	
	public void deleteRole(Role role);
	public void deleteRoleFunction(Role role);
	public void deleteUserRole(Role role);
	
	public String selectMaxId();
	
	public List<HashMap> selRoleByFuncId(@Param("funcId")String funcId);
	
	public void addRoleFuncRln(@Param("roleList") List<RoleFuncRln> list);
	
	public void delRoleFuncRlnByRoleId(@Param("roleId") String roleId);

	public List<Role> checkRole(String roleId);
}
