package com.forms.prms.web.sysmanagement.function.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.authority.domain.Function;
import com.forms.platform.core.tree.ITree;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;
import com.forms.platform.web.json.SimpleJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.function.service.FunctionService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/function")
public class FunctionController {

	private static final String FUNCTION_BASE = "function/";

	@Autowired
	private FunctionService functionService;

	@RequestMapping("search.do")
	// public String search(Function func) {
	// if (func == null)
	// func = new Function();
	// List<Function> funcList = functionService.queryFunction(func);
	// WebUtils.setRequestAttr("funcList", funcList);
	// WebUtils.setRequestAttr("func", func);
	// return FUNCTION_BASE + "search";
	// }
	public String search() {
		ITree<Function> funcTree = functionService.getFuncTree();
		String jsonStr = getFuncJsonStr(funcTree);
		WebUtils.setRequestAttr("funcTree", jsonStr);
		return FUNCTION_BASE + "tree";
	}

	private String getFuncJsonStr(ITree<Function> funcTree) {
		Function root = funcTree.getRoot();
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "ROOT");
		map.put("text", "菜单树");
		if (!root.isLeaf()) {
			map.put("item", getSubFuncMap(root));
		}
		list.add(map);
		AbstractJsonObject jsonObject = new SimpleJsonObject();
		jsonObject.put("id", 0);
		jsonObject.put("item", list);
		return jsonObject.writeValueAsString();
	}

	private Object getSubFuncMap(Function f) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Function> children = f.getChildren();
		for (Function c : children) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", c.getFuncId());
			map.put("text", c.getFuncName());
			if (!c.isLeaf()) {
				map.put("item", getSubFuncMap(c));
			}
			list.add(map);
		}
		return list;
	}

//	@RequestMapping("preAdd.do")
//	public String preAdd() {
//		WebUtils.setRequestAttr("menuTree", functionService
//				.getUserMenuTree(WebHelp.getLoginUser().getUserId()));
//		return  FUNCTION_BASE + "tree";
		
		
//	}

	@RequestMapping("add.do")
	@PreventDuplicateSubmit
	public String add(Function function) {
		functionService.addFunc(function);
//		functionService.addFunction(func);
		WebUtils.getMessageManager().addInfoMessage("添加成功");
		return ForwardPageUtils.getSuccessPage();
	}

	@RequestMapping("preUpdate.do")
	public String preUpdate(String func) {
		ITree<Function> funcTree = functionService.getFuncTree();
		String jsonStr = getFuncJsonStr(funcTree);
		WebUtils.setRequestAttr("funcTree", jsonStr);
		
		WebUtils.setRequestAttr("func", functionService.getFunc(func));
		WebUtils.setRequestAttr("menuTree", functionService
				.getUserMenuTree(WebHelp.getLoginUser().getUserId()));
		return FUNCTION_BASE + "update";
	}

	@RequestMapping("update.do")
	@PreventDuplicateSubmit
	public String update(Function function) {
		functionService.updateFunc(function);
		WebUtils.getMessageManager().addInfoMessage("修改成功");
		return ForwardPageUtils.getSuccessPage();
	}

	@RequestMapping("delete.do")
	@PreventDuplicateSubmit
	public String delete(String[] delIds) {
		List<String> delIdList = Arrays.asList(delIds);
		functionService.deleteFuncs(delIdList);
		return search();
	}

	@RequestMapping("getSubMenu.do")
	@ResponseBody
	public String getSubMenu(String menuId) {
		AbstractJsonObject jsonObj = null;
		if(Tool.CHECK.isEmpty(WebHelp.getLoginUser())){
			jsonObj = new SuccessJsonObject();
			jsonObj.put("userNotFound", "userNotFound");
		}else{
			List<Function> subMenuList = functionService.getSubMenu(
					WebHelp.getLoginUser(), menuId);
			if (subMenuList != null && !subMenuList.isEmpty()) {
				jsonObj = new SuccessJsonObject();
				jsonObj.put("subMenuList", subMenuList);
			} else {
				jsonObj = new FailureJsonObject("message.submenu.not.found");
			}
		}
		return jsonObj.writeValueAsString();
	}

	@RequestMapping("move.do")
//	@PreventDuplicateSubmit
	public String move(String[] funcIds, int[] funcSeqs, String parentId,
			String funcId) {
		if (funcIds.length != funcSeqs.length) {
		    WebUtils.getMessageManager().addInfoMessage("输入参数有误!");
		}
		List<Function> funcList = new ArrayList<Function>();
		for (int i = 0; i < funcIds.length; i++) {
			Function func = new Function();
			func.setFuncId(funcIds[i]);
			func.setSeq(funcSeqs[i]);
			funcList.add(func);
		}
		functionService.moveFunc(funcId, parentId, funcList);
		return search();
	}
	
	
	
	@RequestMapping("getMenuInfoAjax.do")
	@ResponseBody
	public String getMenuInfo(String menuId) {
		Function functionInfo = functionService.getFunc(menuId);
		AbstractJsonObject jsonObj = null;
		if (functionInfo != null && menuId != "ROOT")
		{
			jsonObj = new SuccessJsonObject();
			jsonObj.put("functionInfo", functionInfo);
		} else if(menuId.equals("ROOT") && functionInfo == null){
			jsonObj = new SuccessJsonObject();
			jsonObj.put("functionInfo", "ROOT");
		}else{
			jsonObj = new FailureJsonObject("message.menu.not.found");
		}
		return jsonObj.writeValueAsString();
	}
	
	@RequestMapping("check.do")
	@ResponseBody
	public String check(String funcId){
		AbstractJsonObject jsonObject=new SuccessJsonObject();
		List<Function> func=functionService.check(funcId);
		if (func==null||func.isEmpty()) {
			jsonObject.put("pass", true);
		} else {
			jsonObject.put("pass", false);
		}		
		return jsonObject.writeValueAsString();
	}
}
