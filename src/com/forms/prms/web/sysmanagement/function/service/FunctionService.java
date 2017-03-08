package com.forms.prms.web.sysmanagement.function.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.authority.IAuthoriseController;
import com.forms.platform.authority.IFunctionManager;
import com.forms.platform.authority.domain.Function;
import com.forms.platform.core.Common;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.tree.ITree;
import com.forms.platform.web.base.model.user.IUser;
import com.forms.prms.web.sysmanagement.function.dao.FunctionDao;

@Service
public class FunctionService {
	
	@Autowired
	private FunctionDao funcDao;

	public ITree<Function> getFuncTree() {
		return Common.getAuthoriseComponent().getFunctionManager()
				.getFuncTree();
	}

	public ITree<Function> getUserMenuTree(String userId) {
		IAuthoriseController ac = Common.getAuthoriseComponent()
				.getAuthoriseController();
		return ac.getUserMenuTree(userId);
	}

	public void addFunc(Function func) {
		IFunctionManager fm = Common.getAuthoriseComponent()
				.getFunctionManager();
		fm.addFunc(func);
	}

	public Function getFunc(String funcId) {
		IFunctionManager fm = Common.getAuthoriseComponent()
				.getFunctionManager();
		return fm.getFunc(funcId);
	}

	public void updateFunc(Function func) {
		IFunctionManager fm = Common.getAuthoriseComponent()
				.getFunctionManager();
		fm.updateFunc(func);
	}

	public void deleteFuncs(List<String> delIdList) {
		IFunctionManager fm = Common.getAuthoriseComponent()
				.getFunctionManager();
		fm.deleteFuncs(delIdList);
	}

	public List<Function> getSubMenu(IUser user, String menuId) {
		if (null!=user) {
			ITree<Function> menuTree = user.getMenuTree();
			if (menuTree != null) {
				List<Function> menuList = menuTree.getNodeList();
				if (menuList != null) {
					for (Function f : menuList) {
						if (f.getFuncId().equals(menuId)) {
							return f.getChildren();
						}
					}
				}
			}
		}
		
		return null;
	}

	public ITree<Function> getDisplayFuncTree() {
		return Common.getAuthoriseComponent().getFunctionManager()
				.getDisplayFuncTree();
	}

	public void moveFunc(String funcId, String parentId, List<Function> funcList) {
		IFunctionManager fm = Common.getAuthoriseComponent()
				.getFunctionManager();
		fm.moveFunc(funcId, parentId, funcList);
	}
	
	/**
	 * 根据角色id集合查询菜单
	 * @param roleIds
	 * @param isPage 是否分页
	 * @return
	 */
	public List<Function> queryFuncByRoleIds(String roleIds,boolean isPage){
		String[] arrStr = roleIds.split(",");
		List<String> listRoleId = new ArrayList<String>();
		for(int i=0;i<arrStr.length;i++){
			if(arrStr[i]!=null&&!"".equals(arrStr[i])){
				listRoleId.add(arrStr[i]);
			}
		}
		if(isPage){
			FunctionDao pageDao = PageUtils.getPageDao(funcDao);
			return pageDao.queryFuncByRoleIds(listRoleId);
		}else{
			return funcDao.queryFuncByRoleIds(listRoleId);
		}
	}

	public List<Function> check(String funcId) {
		return funcDao.check(funcId);
	}
}
