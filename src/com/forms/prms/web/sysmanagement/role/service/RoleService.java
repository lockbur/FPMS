package com.forms.prms.web.sysmanagement.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.authority.IPrivilegeAuthorizer;
import com.forms.platform.authority.domain.Function;
import com.forms.platform.authority.domain.RoleFuncRln;
import com.forms.platform.authority.impl.PrivilegeAuthorizerImpl;
import com.forms.platform.core.Common;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.tree.ITree;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.role.dao.RoleDao;
import com.forms.prms.web.sysmanagement.role.domain.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	public List<Role> getRoleList(Role role) {
		CommonLogger.info("角色列表,RoleService,getRoleList");
		RoleDao pageDao = PageUtils.getPageDao(roleDao);
		return pageDao.queryRole(role);
	}

	public Set<String> getAuthFuncSet(String roleId) {
		ITree<Function> funcTree = Common.getAuthoriseComponent()
				.getAuthoriseController().getRoleFuncTree(roleId);
		if (funcTree == null) {
			return null;
		}

		List<Function> funcList = funcTree.getNodeList();
		if (funcList == null || funcList.isEmpty()) {
			return null;
		}

		Set<String> funcSet = new HashSet<String>();
		for (Function f : funcList) {
			funcSet.add(f.getFuncId());
		}
		return funcSet;
	}

	@Transactional(rollbackFor = Exception.class)
	public void authorise(String roleId, List<String> funcIdList) {
		IPrivilegeAuthorizer pa = Common.getAuthoriseComponent()
				.getPrivilegeAuthorizer();
		pa.setPrivilege(roleId, funcIdList);
	}

	@Transactional(rollbackFor = Exception.class)
	public void authorise(Role role,List<String> funcIdList){
		CommonLogger.debug("角色新增,角色名称("+role.getRoleName()+"),RoleService,authorise");
		role.setInstUser(WebHelp.getLoginUser().getUserId());
		Role retRole=this.add(role);
		InnerIPrivilegeAuthorizer pa=new InnerIPrivilegeAuthorizer();
		pa.setPrivilege(retRole.getRoleId(), funcIdList);
	}
	
	public void getDisplayFuncTree() {

	}
	
	@Transactional(rollbackFor = Exception.class)
    public Role  add(Role role) {
    	String roleId="C"+roleDao.selectMaxId();
    	role.setRoleId(roleId);
    	role.setEnableDel("1");
    	CommonLogger.debug("插入角色ID:("+roleId+"),RoleService,add");
    	roleDao.insertRole(role);
        return role;
    }
    
    public Role findRole(Role role) {
    	CommonLogger.debug("根据用户ID:("+role.getRoleId()+")查询角色信息,RoleService,findRole");
        return roleDao.findRole(role.getRoleId());
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Role edit(Role role) {
    	Role existRole=roleDao.findRole(role.getRoleId());
        if(null==existRole)
        {
            CommonLogger.error("更新角色信息时,未查找到角色【"+role.getRoleId()+"】信息!RoleService,edit");
            return null;
        }
        role.setUpdateUser(WebHelp.getLoginUser().getUserId());
        roleDao.updateRole(role);
        
        return role;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void delete(Role role)
	{
//    	role.setDelIdLst(Arrays.asList(role.getDelIds()));
//		if(role.getDelIdLst()!=null&&role.getDelIdLst().size()>0){
//			roleDao.deleteRole(role);
//			roleDao.deleteRoleFunction(role);
//		}
    	CommonLogger.debug("删除角色,角色ID("+role.getRoleId()+"),RoleService,delete");
    	roleDao.deleteRole(role);
    	roleDao.deleteRoleFunction(role);
    	roleDao.deleteUserRole(role);
    	
	}
    
    public List<HashMap> selRoleByFuncId(String funcId) {
		return roleDao.selRoleByFuncId(funcId);
	}
    private class InnerIPrivilegeAuthorizer extends PrivilegeAuthorizerImpl{

		@Override
		public void setPrivilege(String roleId, List<String> funcIdList)
		{
			CommonLogger.debug("设置角色关联的权限,角色ID("+roleId+"),RoleService,setPrivilege");

			// 删除原来角色关联的功能ID
			roleDao.delRoleFuncRlnByRoleId(roleId);

			List<RoleFuncRln> roleList=new ArrayList<RoleFuncRln>();
			// 添加角色功能关联关系
			for (String id : funcIdList) {
				RoleFuncRln roleFuncRln = new RoleFuncRln();
				roleFuncRln.setRoleId(roleId);
				roleFuncRln.setFuncId(id);
				roleList.add(roleFuncRln);
		//		dao.addRoleFuncRln(roleFuncRln);
			}
			roleDao.addRoleFuncRln(roleList);
		}
    	
    }
	public List<Role> checkRole(String roleId) {
		CommonLogger.debug("检查角色是否有用户使用：角色ID("+roleId+"),RoleService,checkRole");
		return roleDao.checkRole(roleId);
	}
}
