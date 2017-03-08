package com.forms.prms.web.sysmanagement.role.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.authority.domain.Function;
import com.forms.platform.core.tree.ITree;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.base.model.user.IUser;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SimpleJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.function.service.FunctionService;
import com.forms.prms.web.sysmanagement.role.domain.Role;
import com.forms.prms.web.sysmanagement.role.service.RoleService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/role")
public class RoleController {

	private static final String ROLE_BASE = "role/";

	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;

	@RequestMapping("list.do")
	public String list(Role role) {
		IUser user = WebUtils.getUserModel();
		if (user != null) {
       		ITree<Function> menuTree = user.getMenuTree();
       		Function root = menuTree.getRoot();
       		List<Function> subMenuList = null;
       		if (root != null) {
       			subMenuList = root.getChildren();
       			if (subMenuList != null) {
					for (int i = subMenuList.size()-1; i >= 0; i--) {
						System.out.println(subMenuList.get(i).getFuncId()+":"+subMenuList.get(i).getFuncName());
					}
       			}
       		}
		}
		ReturnLinkUtils.addReturnLink("roleList", "返回列表");
		List<Role> roleList = roleService.getRoleList(role);
		WebUtils.setRequestAttr("roleList", roleList);
		return ROLE_BASE + "list";
	}

	@RequestMapping("preAuthorise.do")
	public String preAuthorise(Role role) {
		ReturnLinkUtils.addReturnLink("preAuth", "继续授权");
		role=roleService.findRole(role);
		ITree<Function> funcTree = functionService.getDisplayFuncTree();
		Set<String> roleFuncSet = roleService.getAuthFuncSet(role.getRoleId());
		String jsonStr = getFuncJsonStr(funcTree, roleFuncSet);
		WebUtils.setRequestAttr("roleInfo", role);
		WebUtils.setRequestAttr("funcTree", jsonStr);
		WebHelp.setLastPageLink("uri", "roleList");
		return ROLE_BASE + "authorise";
	}

	@RequestMapping("authorise.do")
	@PreventDuplicateSubmit
	public String authorise(Role role) {
		List<String> tList = Arrays.asList(role.getFuncIdList());
		List<String> list = new ArrayList<String>();
		list.addAll(tList);
		for (String s : list) {
			if ("ROOT".equals(s)) {
				list.remove(s);
				break;
			}
		}
		roleService.authorise(role.getRoleId(), list);
		roleService.edit(role);
		WebUtils.getMessageManager().addInfoMessage("授权成功!");
		ReturnLinkUtils.setShowLink(new String[] { "roleList", "preAuth" });
		return ForwardPageUtils.getSuccessPage();
	}

	private String getFuncJsonStr(ITree<Function> funcTree,
			Set<String> roleFuncSet) {
		Function root = funcTree.getRoot();
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "ROOT");
		map.put("text", "菜单树");
		if (!root.isLeaf()) {
			map.put("item", getSubFuncMap(root, roleFuncSet));
		}
		list.add(map);
		AbstractJsonObject jsonObject = new SimpleJsonObject();
		jsonObject.put("id", 0);
		jsonObject.put("item", list);
		return jsonObject.writeValueAsString();
	}

	private Object getSubFuncMap(Function f, Set<String> roleFuncSet) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Function> children = f.getChildren();
		for (Function c : children) {
			if ("Y".equals(c.getIsDisplay())) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", c.getFuncId());
				map.put("text", c.getFuncName());
				if (c.isLeaf() && roleFuncSet != null
						&& roleFuncSet.contains(c.getFuncId())) {
					map.put("checked", "true");
				}
				if (!c.isLeaf()) {
					map.put("item", getSubFuncMap(c, roleFuncSet));
				}
				list.add(map);
			}
		}
		return list;
	}
	
	@RequestMapping("preAdd.do")
	public String preAdd(Role role) {
		ReturnLinkUtils.addReturnLink("preAdd", "继续新增");
		ITree<Function> funcTree = functionService.getDisplayFuncTree();
		String jsonStr = getFuncJsonStr(funcTree, null);
		WebUtils.setRequestAttr("funcTree", jsonStr);
		if(ReturnLinkUtils.getLinkBean("roleList") == null){
			WebUtils.setRequestAttr("uri", "/PRMS/role/list.do?VISIT_FUNC_ID=010201");
		}else{
			WebHelp.setLastPageLink("uri", "roleList");
		}
		return ROLE_BASE + "add";
	}	
	@RequestMapping("add.do")
	@PreventDuplicateSubmit
	public String add(Role role) {
		List<String> tList = Arrays.asList(role.getFuncIdList());
		List<String> list = new ArrayList<String>();
		list.addAll(tList);
		for (String s : list) {
			if ("ROOT".equals(s)) {
				list.remove(s);
				break;
			}
		}
		/*role=roleService.add(role);
		roleService.authorise(role.getRoleId(), list);*/
		/**
		 * 事务一致性
		 */
		roleService.authorise(role,list);
		WebUtils.getMessageManager().addInfoMessage("授权成功!");
		ReturnLinkUtils.setShowLink(new String[] { "roleList", "preAdd" });
		return ForwardPageUtils.getSuccessPage();
	}
	
	@RequestMapping("delRole.do")
	public String delRole(Role role) {
		role.setRoleId(WebUtils.getParameter("roleId"));
		roleService.delete(role);
		WebUtils.getMessageManager().addInfoMessage("删除角色成功!");
		ReturnLinkUtils.setShowLink("roleList");
		return ForwardPageUtils.getSuccessPage();
	}
	
	@RequestMapping("checkIfRoleUsed.do")
	@ResponseBody
	public String checkIfRoleUsed(String roleId){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		List<Role> role=roleService.checkRole(roleId);
			if (role.isEmpty()) {
				jsonObject.put("isExist", false);
			} else {
				jsonObject.put("isExist", true);
			}
	return jsonObject.writeValueAsString();
	}
}
