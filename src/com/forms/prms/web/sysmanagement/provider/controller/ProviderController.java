package com.forms.prms.web.sysmanagement.provider.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderActBean;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderBean;
import com.forms.prms.web.sysmanagement.provider.service.ProviderService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/provider")
public class ProviderController {
	private final String APPLYMANAGEPATH = "sysmanagement/provider/";

	@Autowired
	private ProviderService ps;

	@RequestMapping("/list.do")
	public String getProvider(ProviderBean bean) {
		ReturnLinkUtils.addReturnLink("ProviderController.providerList", "返回");
		List<ProviderBean> providerList = ps.getProvider(bean);
		WebUtils.setRequestAttr("providerList", providerList);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return APPLYMANAGEPATH + "list";

	}

	@RequestMapping("/preAdd.do")
	public String preAdd() {
		String ouCode = WebHelp.getLoginUser().getOuCode();
		String ouName = WebHelp.getLoginUser().getOuName();
		WebUtils.setRequestAttr("ouCode", ouCode);
		WebUtils.setRequestAttr("ouName", ouName);
		ReturnLinkUtils.addReturnLink("ProviderController.preAdd", "继续新增");
		return APPLYMANAGEPATH + "add";
	}

	/**
	 * 添加数据
	 * 
	 * @param bean
	 * @return
	 */
	/*@RequestMapping("add.do")
	public String add(ProviderBean bean) {
		boolean flag = ps.add(bean);
		if (flag) {
			ReturnLinkUtils
					.setShowLink(new String[] { "ProviderController.preAdd", "ProviderController.providerList" });
			return ForwardPageUtils.getSuccessPage();
		} else {
			return ForwardPageUtils.getErrorPage();
		}

	}
*/
	@RequestMapping("subAddPage.do")
	public String subAddPage() {
		return APPLYMANAGEPATH + "subAdd";
	}

	/**
	 * 得到供应商编号的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("getInfo.do")
	public String getInfo(ProviderBean bean) {
		WebUtils.setRequestAttr("providerInfo", bean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/provider/list.do?VISIT_FUNC_ID=0107");
		return APPLYMANAGEPATH + "providerInfo";
	}

	/**
	 * 修改数据之前显示数据
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("preEdit.do")
	public String preEdit(ProviderBean bean) {
		bean = ps.getInfoByCode(bean.getProviderCode());
		List<ProviderActBean> providerActList = ps.getActList(bean.getProviderCode());
		String ouCode = WebHelp.getLoginUser().getOuCode();
		String ouName = WebHelp.getLoginUser().getOuName();
		WebUtils.setRequestAttr("ouCode", ouCode);
		WebUtils.setRequestAttr("ouName", ouName);
		WebUtils.setRequestAttr("bean", bean);
		WebUtils.setRequestAttr("providerActList", providerActList);
		return APPLYMANAGEPATH + "edit";
	}

	/**
	 * 显示某供应商编号下的银行编号下某币种信息
	 * 
	 * @param ProviderActBean
	 * @return
	 */
	@RequestMapping("subEditActPage.do")
	public String editAct(ProviderActBean bean) {
		try {
			if (bean.getActCurr() != null && !bean.getActCurr().equals("")) {
				String actCurr = new String(bean.getActCurr().getBytes("ISO-8859-1"), "UTF-8");
				bean.setActCurr(actCurr);
			}
			if (bean.getActName() != null && !bean.getActName().equals("")) {
				String actName = new String(bean.getActName().getBytes("ISO-8859-1"), "UTF-8");
				bean.setActName(actName);
			}
			if (bean.getActNo() != null && !bean.getActNo().equals("")) {
				String actNo = new String(bean.getActNo().getBytes("ISO-8859-1"), "UTF-8");
				bean.setActNo(actNo);
			}
			if (bean.getActType() != null && !bean.getActType().equals("")) {
				String actType = new String(bean.getActType().getBytes("ISO-8859-1"), "UTF-8");
				bean.setActType(actType);
			}
			if (bean.getBankArea() != null && !bean.getBankArea().equals("")) {
				String bankArea = new String(bean.getBankArea().getBytes("ISO-8859-1"), "UTF-8");
				bean.setBankArea(bankArea);
			}
			if (bean.getBankCode() != null && !bean.getBankCode().equals("")) {
				String bankCode = new String(bean.getBankCode().getBytes("ISO-8859-1"), "UTF-8");
				bean.setBankCode(bankCode);
			}
			if (bean.getBankInfo() != null && !bean.getBankInfo().equals("")) {
				String bankInfo = new String(bean.getBankInfo().getBytes("ISO-8859-1"), "UTF-8");
				bean.setBankInfo(bankInfo);
			}
			if (bean.getBankName() != null && !bean.getBankName().equals("")) {
				String bankName = new String(bean.getBankName().getBytes("ISO-8859-1"), "UTF-8");
				bean.setBankName(bankName);
			}
			if (bean.getBranchName() != null && !bean.getBranchName().equals("")) {
				String branchName = new String(bean.getBranchName().getBytes("ISO-8859-1"), "UTF-8");
				bean.setBranchName(branchName);
			}
			if (bean.getIsMasterAct() != null && !bean.getIsMasterAct().equals("")) {
				String isMasterAct = new String(bean.getIsMasterAct().getBytes("ISO-8859-1"), "UTF-8");
				bean.setIsMasterAct(isMasterAct);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		WebUtils.setRequestAttr("actInfo", bean);
		return APPLYMANAGEPATH + "editAct";
	}

	/**
	 * 修改提交
	 * 
	 * @param ProviderBean
	 * @return
	 */
	/*@RequestMapping("/editSubmit.do")
	public String editSubmit(ProviderBean bean) {
		boolean flag = ps.editSubmit(bean);
		if (flag) {
			WebUtils.getMessageManager().addInfoMessage("修改信息成功!");
			ReturnLinkUtils.setShowLink("ProviderController.providerList");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("修改信息失败!");
			ReturnLinkUtils.setShowLink("ProviderController.providerList");
			return ForwardPageUtils.getSuccessPage();
		}
	}*/

	/**
	 * 删除供应商信息
	 * 
	 * @param ProviderBean
	 * @return
	 * */
	@RequestMapping("delete.do")
	public String delete(ProviderBean bean) {
		ps.deleteProvider(bean);
		ps.deleteProviderAct(bean);
		WebUtils.getMessageManager().addInfoMessage("删除供应商信息成功!");
		ReturnLinkUtils.setShowLink("ProviderController.providerList");
		return ForwardPageUtils.getSuccessPage();
	}

	/**
	 * 弹出框查找供应商信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("searchProvider.do")
	public String searchProvider(ProviderBean bean) {
		List<ProviderBean> providerList = ps.searchProvider(bean);
		WebUtils.setRequestAttr("providerList", providerList);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return APPLYMANAGEPATH + "providerPop";
	}

	/**
	 * 弹出框查找银行账号
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("searchProviderAct.do")
	public String searchProviderAct(ProviderBean bean) {
		// 如果为外部供应商则默认选择外部供应商
		if (bean.getFlag() != null) {
			if (bean.getFlag().equals("1")) {
				bean.setProviderType("VENDOR");
			}
			if (bean.getFlag().equals("2")) {
				bean.setProviderType("EMPLOYEE");
			}
		}
		ReturnLinkUtils.addReturnLink("ProviderController.providerListPop", "返回");
		List<ProviderBean> providerActList = ps.searchProviderAct(bean);
		/*// 如果供应商类型为IAP则将该条信息加入到集合中去
		if (bean.getProviderType() != null) {
			if (bean.getProviderType().equals("IAP")) {
				ProviderBean providerBean = ps.findProvider(bean);
				providerActList.add(providerBean);
				bean.setProviderCode(providerBean.getProviderCode());
			}
		}*/
		WebUtils.setRequestAttr("providerActList", providerActList);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		return APPLYMANAGEPATH + "providerActPop";
	}

	/**
	 * 弹出框供应商新增
	 * 
	 * @return
	 */
	@RequestMapping("/preAddPop.do")
	public String preAddPop() {
		// 生成一个供应商编号
		String providerCode = ps.getProviderCode();
		WebUtils.setRequestAttr("providerCode", providerCode);
		ReturnLinkUtils.addReturnLink("ProviderController.preAddPop", "继续新增");
		return APPLYMANAGEPATH + "addPop";
	}

	/**
	 * Ajax检验供应商是否存在
	 * 
	 * @param providerName
	 * @return
	 */
	@RequestMapping("checkProviderName.do")
	@ResponseBody
	public String checkProviderName(String providerName) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		ProviderBean bean = ps.checkProviderName(providerName);
		if (bean == null) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}

	/**
	 * 弹出层添加数据
	 * 
	 * @param bean
	 * @return
	 */
	/*@RequestMapping("addPop.do")
	@ResponseBody
	public String addPop(ProviderBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		boolean flag = ps.addPop(bean);
		if (flag) {
			jsonObject.put("isSuccess", true);
		} else {
			jsonObject.put("isSuccess", false);
		}
		return jsonObject.writeValueAsString();
	}*/
}
