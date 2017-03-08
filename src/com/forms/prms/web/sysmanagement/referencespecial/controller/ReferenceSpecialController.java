package com.forms.prms.web.sysmanagement.referencespecial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.web.sysmanagement.referencespecial.domain.Reference;
import com.forms.prms.web.sysmanagement.referencespecial.domain.Special;
import com.forms.prms.web.sysmanagement.referencespecial.domain.TaxCode;
import com.forms.prms.web.sysmanagement.referencespecial.service.ReferenceSpecialService;
import com.forms.prms.web.tag.domain.TagCommon;
import com.forms.prms.web.tag.service.TagCommonService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/referencespecial")
public class ReferenceSpecialController {
	@Autowired
	private ReferenceSpecialService rsService;

	@Autowired
	private TagCommonService tagCommonService;

	private static final String TO = "sysmanagement/referencespecial/";

	/**
	 * 参考 列表 011201
	 * 
	 * @param r
	 * @return
	 */
	@RequestMapping("referenceList.do")
	@AddReturnLink(id = "referenceList", displayValue = "返回参考维护列表")
	public String referenceList(Reference r) {
		WebUtils.setRequestAttr("rList", rsService.referenceList(r));
		WebUtils.setRequestAttr("r", r);
		return TO + "referenceList";
	}

	/**
	 * 检查参考Id是否存在 01120101
	 * 
	 * @param referenceId
	 * @return
	 */
	@RequestMapping("checkRID.do")
	@ResponseBody
	public String checkRID(String referenceId) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if (rsService.checkRID(referenceId)) {
			jsonObject.put("pass", true);
		} else {
			jsonObject.put("pass", false);
		}
		return jsonObject.writeValueAsString();
	}

	/**
	 * 参考新增页面 01120102
	 * 
	 * @return
	 */
	@RequestMapping("preReferenceAdd.do")
	@AddReturnLink(id = "preReferenceAdd", displayValue = "返回参考维护新增页面")
	public String preReferenceAdd() {
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/referencespecial/referenceList.do?VISIT_FUNC_ID=011201");
		return TO + "referenceAdd";
	}

	/**
	 * 参考 新增 01120103
	 * 
	 * @param r
	 */
	@RequestMapping("referenceAdd.do")
	public String referenceAdd(Reference r) {
		ReturnLinkUtils.setShowLink(new String[] { "referenceList", "preReferenceAdd" });
		if (rsService.referenceAdd(r) > 0) {
			WebUtils.getMessageManager().addInfoMessage("新增参考成功！");
			// 清空缓存
			TagCommon tag = new TagCommon();
			tag.setConditionStr("");
			tag.setOrderColumn("REFERENCE_ID");
			tag.setOrderType("ASC");
			tag.setSelectColumn("REFERENCE_ID,REFERENCE_NAME");
			tag.setTableName("TB_REFERENCE");
			tag.setTextColumn("REFERENCE_NAME");
			tag.setValueColumn("REFERENCE_ID");
			tagCommonService.removeSelectList(tag);
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("新增参考失败！");
			return ForwardPageUtils.getErrorPage();
		}

	}

	/**
	 * 参考 详情页面（更新页面）01120104
	 * 
	 * @param referenceId
	 * @param jspPage
	 * @return
	 */
	@RequestMapping("referenceView.do")
	public String referenceView(String referenceId) {
		WebUtils.setRequestAttr("bean", rsService.referenceView(referenceId));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/referencespecial/referenceList.do?VISIT_FUNC_ID=011201");
		return TO + "referenceUpdate";
	}

	/**
	 * 参考 更新 01120105
	 * 
	 * @return
	 */
	@RequestMapping("referenceUpd.do")
	public String referenceUpd(Reference r) {
		ReturnLinkUtils.setShowLink("referenceList");
		if (rsService.referenceUpd(r) > 0) {
			WebUtils.getMessageManager().addInfoMessage("更新参考成功！");
			// 清空缓存
			TagCommon tag = new TagCommon();
			tag.setConditionStr("");
			tag.setOrderColumn("REFERENCE_ID");
			tag.setOrderType("ASC");
			tag.setSelectColumn("REFERENCE_ID,REFERENCE_NAME");
			tag.setTableName("TB_REFERENCE");
			tag.setTextColumn("REFERENCE_NAME");
			tag.setValueColumn("REFERENCE_ID");
			tagCommonService.removeSelectList(tag);
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("更新参考失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 专项 列表 011202
	 * 
	 * @param s
	 * @return
	 */
	@RequestMapping("specialList.do")
	@AddReturnLink(id = "specialList", displayValue = "返回专项维护列表")
	public String specialList(Special s) {
		WebUtils.setRequestAttr("sList", rsService.specialList(s));
		WebUtils.setRequestAttr("s", s);
		return TO + "specialList";
	}

	/**
	 * 检查专项Id是否存在 01120201
	 * 
	 * @param specialId
	 * @return
	 */
	@RequestMapping("checkSID.do")
	@ResponseBody
	public String checkSID(String specialId) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if (rsService.checkSID(specialId)) {
			jsonObject.put("pass", true);
		} else {
			jsonObject.put("pass", false);
		}
		return jsonObject.writeValueAsString();
	}

	/**
	 * 专项新增页面 01120202
	 * 
	 * @return
	 */
	@RequestMapping("preSpecialAdd.do")
	@AddReturnLink(id = "preSpecialAdd", displayValue = "返回专项维护新增页面")
	public String preSpecialAdd() {
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/referencespecial/specialList.do?VISIT_FUNC_ID=011202");
		return TO + "specialAdd";
	}

	/**
	 * 专项 新增 01120203
	 * 
	 * @param s
	 */
	@RequestMapping("specialAdd.do")
	public String specialAdd(Special s) {
		ReturnLinkUtils.setShowLink(new String[] { "specialList", "preSpecialAdd" });
		if (rsService.specialAdd(s) > 0) {
			WebUtils.getMessageManager().addInfoMessage("新增专项成功！");
			// 清空缓存
			TagCommon tag = new TagCommon();
			tag.setConditionStr("");
			tag.setOrderColumn("SPECIAL_ID");
			tag.setOrderType("ASC");
			tag.setSelectColumn("SPECIAL_ID,SPECIAL_NAME");
			tag.setTableName("TB_SPECIAL");
			tag.setTextColumn("SPECIAL_NAME");
			tag.setValueColumn("SPECIAL_ID");
			tagCommonService.removeSelectList(tag);
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("新增专项失败！");
			return ForwardPageUtils.getErrorPage();
		}

	}

	/**
	 * 专项 详情页面（更新页面）01120204
	 * 
	 * @param specialId
	 * @return
	 */
	@RequestMapping("specialView.do")
	public String specialView(String specialId) {
		WebUtils.setRequestAttr("bean", rsService.specialView(specialId));
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/referencespecial/specialList.do?VISIT_FUNC_ID=011202");
		return TO + "specialUpdate";
	}

	/**
	 * 专项 更新 01120205
	 * 
	 * @return
	 */
	@RequestMapping("specialUpd.do")
	public String specialUpd(Special s) {
		ReturnLinkUtils.setShowLink("specialList");
		if (rsService.specialUpd(s) > 0) {
			WebUtils.getMessageManager().addInfoMessage("更新专项成功！");
			// 清空缓存
			TagCommon tag = new TagCommon();
			tag.setConditionStr("");
			tag.setOrderColumn("SPECIAL_ID");
			tag.setOrderType("ASC");
			tag.setSelectColumn("SPECIAL_ID,SPECIAL_NAME");
			tag.setTableName("TB_SPECIAL");
			tag.setTextColumn("SPECIAL_NAME");
			tag.setValueColumn("SPECIAL_ID");
			tagCommonService.removeSelectList(tag);
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("更新专项失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 弹出框查找专项
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("specialPage.do")
	public String specialPage(Special s) {
		List<Special> specialList = rsService.specialPage(s);
		WebUtils.setRequestAttr("specialList", specialList);
		WebUtils.setRequestAttr("spe", s);
		return TO + "specialPage";
	}

	/**
	 * 弹出框查找参考
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("referencePage.do")
	public String referencePage(Reference r) {
		List<Reference> referenceList = rsService.referencePage(r);
		WebUtils.setRequestAttr("referenceList", referenceList);
		WebUtils.setRequestAttr("re", r);
		return TO + "referencePage";
	}
	/**
	 * 税码 列表 
	 * 
	 * @param s
	 * @return
	 */
	@RequestMapping("taxCodeList.do")
	@AddReturnLink(id = "taxCodeList", displayValue = "返回税码维护列表")
	public String taxCodeList(TaxCode taxCode) {
		WebUtils.setRequestAttr("tList", rsService.taxCodeList(taxCode));
		WebUtils.setRequestAttr("t", taxCode);
		return TO + "taxCodeList";
	}
	/**
	 * 税码新增页面 
	 * 
	 * @return
	 */
	@RequestMapping("preTaxCodeAdd.do")
	@AddReturnLink(id = "preTaxCodeAdd", displayValue = "返回税码维护新增页面")
	public String preTaxCodeAdd() {
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/referencespecial/taxCodeList.do?VISIT_FUNC_ID=011203");
		return TO + "taxCodeAdd";
	}
	/**
	 * 检查税码编号是否存在
	 * 
	 * @param specialId
	 * @return
	 */
	@RequestMapping("checkTaxCode.do")
	@ResponseBody
	public String checkTaxCode(String taxCode) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if (rsService.checkTaxCode(taxCode)) {
			jsonObject.put("pass", true);
		} else {
			jsonObject.put("pass", false);
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 税码 新增 
	 * 
	 * @param r
	 */
	@RequestMapping("taxCodeAdd.do")
	public String taxCodeAdd(TaxCode t) {
		ReturnLinkUtils.setShowLink(new String[] { "taxCodeList", "preTaxCodeAdd" });
		if (rsService.taxCodeAdd(t)) {
			WebUtils.getMessageManager().addInfoMessage("新增税码成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("新增参考失败！");
			return ForwardPageUtils.getErrorPage();
		}

	}
	/**
	 * 税码 详情页面（更新页面）
	 * 
	 * @param taxCode
	 * @return
	 */
	@RequestMapping("preTaxCodeUpdate.do")
	public String preTaxCodeUpdate(String taxCode) {
		WebUtils.setRequestAttr("bean", rsService.preTaxCodeUpdate(taxCode));
		//通过税码得到核算码百分比集合
		List<TaxCode> perCntCglList=rsService.perCntCglList(taxCode);
		WebUtils.setRequestAttr("perCntCglList", perCntCglList);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
				+ "/sysmanagement/referencespecial/taxCodeList.do?VISIT_FUNC_ID=011203");
		return TO + "taxCodeUpdate";
	}
	/**
	 * 税码更新提交
	 * 
	 * @return
	 */
	@RequestMapping("taxCodeUpdate.do")
	public String taxCodeUpdate(TaxCode t) {
		ReturnLinkUtils.setShowLink("taxCodeList");
		if (rsService.taxCodeUpdate(t)) {
			WebUtils.getMessageManager().addInfoMessage("更新税码成功！");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("更新税码失败！");
			return ForwardPageUtils.getErrorPage();
		}
	}
}
