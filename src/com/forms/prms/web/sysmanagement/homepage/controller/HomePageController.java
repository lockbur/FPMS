package com.forms.prms.web.sysmanagement.homepage.controller;



import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.forms.prms.web.sysmanagement.homePageRollInfo.domain.RollInfoBean;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.authority.domain.Function;
import com.forms.platform.core.tree.ITree;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;
import com.forms.platform.web.json.SimpleJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.function.service.FunctionService;
import com.forms.prms.web.sysmanagement.homepage.domain.FmsErrorBean;
import com.forms.prms.web.sysmanagement.homepage.domain.ExcepInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.HomePageBean;
import com.forms.prms.web.sysmanagement.homepage.domain.SysRollInfoBean;
import com.forms.prms.web.sysmanagement.homepage.domain.UserDesktopBean;
import com.forms.prms.web.sysmanagement.homepage.service.HomePageService;
import com.forms.prms.web.sysmanagement.homepage.service.SysWarnCountService;
import com.forms.prms.web.sysmanagement.role.service.RoleService;



@Controller
@RequestMapping("/homepage")
public class HomePageController {

	@Autowired
     private  HomePageService service;
	
	@Autowired
	private FunctionService functionService;
	
	@Autowired
	private RoleService roleService;
	
	
	@Autowired
    private  SysWarnCountService sysWarnCountService;
	
	@RequestMapping("/main.do")
	public String gotoMain(HttpServletRequest request) 
	{
		String ouCode = WebHelp.getLoginUser().getOuCode();
		String dutyCode=WebHelp.getLoginUser().getDutyCode();
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		String userId=WebHelp.getLoginUser().getUserId();
		String roleId=WebHelp.getLoginUser().getRoleId();
		
		HomePageBean  homePageBean=service.mGetOperNum(org1Code,dutyCode,roleId);
		List<UserDesktopBean> userDesktopList=service.mGetUserDesktopList(userId);
		List<SysRollInfoBean> rollInfos=service.mGetSysRollInfo();
		int rollInfoNumb = rollInfos.size();
		String excepInfoNumb = service.getExcepInfoCount(userId);
		List<ExcepInfoBean> excepInfos=service.getExcepInfos(userId);
		FmsErrorBean fmsErrorBean = service.mGetFmsErrorNum(ouCode,userId);
		WebUtils.setRequestAttr("homePageBean", homePageBean);
		WebUtils.setRequestAttr("userDesktopList", userDesktopList);
		WebUtils.setRequestAttr("rollInfos", rollInfos);
		WebUtils.setRequestAttr("fmsErrorBean", fmsErrorBean);
		WebUtils.setRequestAttr("rollInfoNumb", rollInfoNumb);
		WebUtils.setRequestAttr("excepInfoNumb", excepInfoNumb);
		WebUtils.setRequestAttr("excepInfos", excepInfos);
		WebUtils.setRequestAttr("ppRight", service.getPPRight());
		WebUtils.setRequestAttr("interfaceRight", service.getInterfaceRight());
		return "welcome";
	}
	
	
	@RequestMapping("/showTreePage.do")
	public String showTree(HttpServletRequest request) 
	{
	//	ITree<Function> funcTree = functionService.getDisplayFuncTree();
		ITree<Function> funcTree = WebHelp.getLoginUser().getMenuTree();  
		Set<String> roleFuncSet = roleService.getAuthFuncSet(WebHelp.getLoginUser().getRoleId());
		String jsonStr = getFuncJsonStr(funcTree, roleFuncSet);
		WebUtils.setRequestAttr("jsonStr", jsonStr);
		return "shortcut";
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

	
	
	@RequestMapping("initUserDesktopAjax.do")
	@ResponseBody
	public String initUserDesktop() 
	{
		String userId=WebHelp.getLoginUser().getUserId();
		List<UserDesktopBean> userDesktopList=service.mGetUserDesktopList(userId);
		AbstractJsonObject jsonObj = null;
		jsonObj = new SuccessJsonObject();
		jsonObj.put("userDesktopList", userDesktopList);
		return jsonObj.writeValueAsString();
	}
	/**
	 * @methodName addUserDesktop
	 * desc  
	 * 功能收藏新增，新增前先计算此用户收藏功能的个数，达到上限将不能新增
	 * @param userDesktopBean
	 * @return
	 */
	@RequestMapping("addUserDesktopAjax.do")
	@ResponseBody
	public String addUserDesktop(UserDesktopBean userDesktopBean)
	{
		AbstractJsonObject jsonObj = null;
		String userId=WebHelp.getLoginUser().getUserId();
		userDesktopBean.setUserId(userId);
		if(!service.getCount(userDesktopBean)){
			jsonObj = new FailureJsonObject("新增失败,收藏的功能已达上限");
			return jsonObj.writeValueAsString();
		}else{
			try {
				service.addUserDesktop(userDesktopBean);
				jsonObj = new SuccessJsonObject();
				jsonObj.put("success", "新增成功");
				return jsonObj.writeValueAsString();
			} catch (Exception e) {
				jsonObj = new FailureJsonObject("新增失败");
				return jsonObj.writeValueAsString();
			}
		}
	}
	/**
	 * @methodName getDesktopInfo
	 * desc  
	 * 通过传过来的id查得菜单树的信息，同时也查得SYS_USER_DESKTOP_INFO表中的信息看是否已经新增过
	 * @param menuId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("getDesktopInfoAjax.do")
	@ResponseBody
	public String getDesktopInfo(UserDesktopBean userDesktopBean) 
	{
		String menuId = userDesktopBean.getFuncId();
		Function functionInfo = functionService.getFunc(menuId);
		userDesktopBean.setUserId(WebHelp.getLoginUser().getUserId());
		UserDesktopBean bean = service.getDesktopInfo(userDesktopBean);
		AbstractJsonObject jsonObj = null;
		if (functionInfo != null)
		{
			jsonObj = new SuccessJsonObject();
			jsonObj.put("functionInfo", functionInfo);
			jsonObj.put("userDesktopBean", bean);
//			WebUtils.setRequestAttr("userDesktopBean", bean);
		}else{
			jsonObj = new FailureJsonObject("节点信息获取失败");
		}
		return jsonObj.writeValueAsString();
	}
	/**
	 * @methodName delUserDesktop
	 * desc  
	 * 收藏功能删除
	 * @param userDesktopBean
	 * @return
	 */
	@RequestMapping("delUserDesktopAjax.do")
	@ResponseBody
	public String delUserDesktop(UserDesktopBean userDesktopBean)
	{
		String userId=WebHelp.getLoginUser().getUserId();
		userDesktopBean.setUserId(userId);
		AbstractJsonObject jsonObj = null;
		try {
			service.delUserDesktop(userDesktopBean);
			jsonObj = new SuccessJsonObject();
			jsonObj.put("success", "删除成功");
			return jsonObj.writeValueAsString();
			
		} catch (Exception e) {
			// TODO: handle exception
			jsonObj = new FailureJsonObject("删除失败");
			return jsonObj.writeValueAsString();
		}
	}
	/**
	 * @methodName updateUserDesktop
	 * desc  
	 * 收藏功能更新
	 * @param userDesktopBean
	 * @return
	 */
	@RequestMapping("updateUserDesktopAjax.do")
	@ResponseBody
	public String updateUserDesktop(UserDesktopBean userDesktopBean)
	{
		String userId=WebHelp.getLoginUser().getUserId();
		userDesktopBean.setUserId(userId);
		AbstractJsonObject jsonObj = null;
		try {
			service.updateUserDesktop(userDesktopBean);
			jsonObj = new SuccessJsonObject();
			jsonObj.put("success", "更新成功");
			return jsonObj.writeValueAsString();
			
		} catch (Exception e) {
			jsonObj = new FailureJsonObject("更新失败");
			return jsonObj.writeValueAsString();
		}
	}
	
	@RequestMapping("rollInfoDetail.do")
	public String rollInfoDetail(RollInfoBean rollInfoBean){
		RollInfoBean rollInfo = service.rollInfoDetail(rollInfoBean);
		WebUtils.setRequestAttr("roll", rollInfo);
		return "rollInfoDetail";
	}
	@RequestMapping("rollInfoList.do")
	public String rollInfoList(HttpServletRequest request){
		List<SysRollInfoBean> rollInfos=service.mGetSysRollInfo();
		WebUtils.setRequestAttr("list", rollInfos);
		return "rollInfoList";
	}
	@RequestMapping("excepInfoDetail.do")
	public String excepInfoDetail(ExcepInfoBean excepInfoBean){
		ExcepInfoBean excepInfos=service.excepInfoDetail(excepInfoBean);
		WebUtils.setRequestAttr("excepInfo", excepInfos);
		return "excepInfoDetail";
	}
	@RequestMapping("excepInfoList.do")
	public String excepInfoList(ExcepInfoBean excepInfoBean){
		excepInfoBean.setUserId(WebHelp.getLoginUser().getUserId());
		List<ExcepInfoBean> excepInfos=service.getExcepInfos(excepInfoBean.getUserId());
		WebUtils.setRequestAttr("list", excepInfos);
		return "excepInfoList";
	}
	/**
	 * @methodName haveRead
	 * desc  
	 * 已阅功能
	 * @param excepInfoBean
	 * @return
	 */
	@RequestMapping("excepInfoHaveRead.do")
	@ResponseBody
	public String haveRead(ExcepInfoBean excepInfoBean)
	{
		String userId=WebHelp.getLoginUser().getUserId();
		excepInfoBean.setUserId(userId);
		AbstractJsonObject jsonObj = null;
		try {
			service.haveRead(excepInfoBean);
			String excepInfoNumb = service.getExcepInfoCount(userId);
			List<ExcepInfoBean> excepInfos=service.getExcepInfos(userId);
			String str[]=new String[excepInfos.size()];
			String excepId[]=new String[excepInfos.size()];
			for(int i=0;i<excepInfos.size();i++){
				str[i]=excepInfos.get(i).getAddTime()+"："+excepInfos.get(i).getExcepInfo();
				excepId[i]=excepInfos.get(i).getExcepId();
			}
			jsonObj = new SuccessJsonObject();
			jsonObj.put("excepInfoNumb", excepInfoNumb);
			jsonObj.put("excepInfos", str);
			jsonObj.put("excepIds", excepId);
			return jsonObj.writeValueAsString();
			
		} catch (Exception e) {
			jsonObj = new FailureJsonObject("操作失败");
			return jsonObj.writeValueAsString();
		}
	}
	
	@RequestMapping("ajaxGetList.do")
	@ResponseBody
	public String getList(UserDesktopBean userDesktopBean)
	{
		
		AbstractJsonObject jsonObj = null;
		try {
			String userId=WebHelp.getLoginUser().getUserId();
			userDesktopBean.setUserId(userId);
			List<UserDesktopBean> userDesktopList=service.getList(userDesktopBean);
			String funcUrl[]=new String[userDesktopList.size()];
			String iconName[]=new String[userDesktopList.size()];
			String funcName[]=new String[userDesktopList.size()];
			String funcMemo[]=new String[userDesktopList.size()];
			for(int i=0;i<userDesktopList.size();i++){
				funcUrl[i]=userDesktopList.get(i).getFuncUrl();
				iconName[i]=userDesktopList.get(i).getIconName();
				funcName[i]=userDesktopList.get(i).getFuncName();
				funcMemo[i]=userDesktopList.get(i).getFuncMemo();
			}
			jsonObj = new SuccessJsonObject();
			jsonObj.put("funcUrl", funcUrl);
			jsonObj.put("iconName", iconName);
			jsonObj.put("funcName", funcName);
			jsonObj.put("funcMemo", funcMemo);
			return jsonObj.writeValueAsString();
		} catch (Exception e) {
			jsonObj = new FailureJsonObject("刷新失败");
			return jsonObj.writeValueAsString();
		}
	}
}
