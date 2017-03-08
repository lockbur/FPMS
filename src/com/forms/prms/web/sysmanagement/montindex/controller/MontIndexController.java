package com.forms.prms.web.sysmanagement.montindex.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.consts.WebConsts;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.StringDeal;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.montindex.domain.MontIndexBean;
import com.forms.prms.web.sysmanagement.montindex.service.MontIndexService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/montindex")
public class MontIndexController {
	private final String MONTINDEXPATH = "sysmanagement/montindex/";

	@Autowired
	private MontIndexService mis;

	/**
	 * 查询省行监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/shList.do")
	public String shList(MontIndexBean bean) {
		String orgType = "01";
		bean.setOrgType(orgType);
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(Tool.DATE.getDate().substring(0,4));
		}
		ReturnLinkUtils.addReturnLink("MontIndexController.shList", "返回列表");
		List<MontIndexBean> shList = mis.shList(bean);
		WebUtils.setRequestAttr("list", shList);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("thisYear", Tool.DATE.getDateStrNO().subSequence(0, 4));
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		WebUtils.setRequestAttr("orgType", orgType);
		return MONTINDEXPATH + "list";

	}

	/**
	 * 查询分行监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/fhList.do")
	public String fhList(MontIndexBean bean) {
		String orgType = "02";
		bean.setOrgType(orgType);
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(Tool.DATE.getDate().substring(0,4));
		}
		ReturnLinkUtils.addReturnLink("MontIndexController.fhList", "返回列表");
		List<MontIndexBean> fhList = mis.fhList(bean);
		WebUtils.setRequestAttr("list", fhList);
		WebUtils.setRequestAttr("selectInfo", bean);
		WebUtils.setRequestAttr("thisYear", Tool.DATE.getDateStrNO().subSequence(0, 4));
		WebUtils.setRequestAttr("funcId", WebUtils.getParameter(WebConsts.FUNC_ID_KEY));
		WebUtils.setRequestAttr("orgType", orgType);
		return MONTINDEXPATH + "list";

	}

	@RequestMapping("/preAdd")
	public String zcPreAdd(MontIndexBean bean) {
		ReturnLinkUtils.addReturnLink("preAdd", "返回新增");
		String org21Code = WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg21Code(org21Code);
		WebUtils.setRequestAttr("montBean", bean);
		WebUtils.setRequestAttr("thisYear", Tool.DATE.getDate().substring(0,4));
		if ("01".equals(bean.getOrgType())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/montindex/shList.do?VISIT_FUNC_ID=08120201");
		}else {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/montindex/fhList.do?VISIT_FUNC_ID=08120202");
		}
		
		return MONTINDEXPATH + "preAdd";
	}

//	@RequestMapping("/fhPreAdd")
//	public String fhPreAdd(MontIndexBean bean) {
//		ReturnLinkUtils.addReturnLink("preAdd", "返回新增");
//		String org21Code = WebHelp.getLoginUser().getOrg2Code();
//		bean.setOrg21Code(org21Code);
//		WebUtils.setRequestAttr("montBean", bean);
//		WebUtils.setRequestAttr("thisYear", Tool.DATE.getDate().substring(0,4));
//		
//		return MONTINDEXPATH + "fhPreAdd";
//	}

	/**
	 * 新增页面的物料列表查询
	 * 
	 * @param montBean
	 * @return
	 */
	@RequestMapping("/addMatrs")
	public String addMatrs(MontIndexBean bean) {
		//查询所有核算吗
		List<MontIndexBean> cglCodeList = mis.getCglCodeList();
		List<MontIndexBean> matrsList = null;
		if (!Tool.CHECK.isEmpty(bean.getCglCodeS())) {
			bean.setCglCodeList(Arrays.asList(bean.getCglCodeS()));
		}
		if ("11".equals(bean.getMontType())) {
			matrsList = mis.fyzcList(bean);
		} else if ("12".equals(bean.getMontType()) || "21".equals(bean.getMontType())) {
			matrsList = mis.zcList(bean);
		} else {
			matrsList = mis.fyList(bean);
		}
		WebUtils.setRequestAttr("matrsList", matrsList);
		WebUtils.setRequestAttr("cglCodeList", cglCodeList);
		WebUtils.setRequestAttr("montBean", bean);
		return MONTINDEXPATH + "/addMatrList";
	}

	/**
	 * Ajax检验监控指标是否存在
	 * 
	 * @param providerName
	 * @return
	 */
	@RequestMapping("checkMont.do")
	@ResponseBody
	public String checkMont(MontIndexBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		String checkMontName = mis.checkMont(bean);
		if (checkMontName == null) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}
	/**
	 * 物料新增
	 */
	@RequestMapping("add.do")
	public String add(MontIndexBean bean) {
		String montCode = "";
		
		if ("11".equals(bean.getMontType()) || "12".equals(bean.getMontType())) {
			// 资产类全省的
			String org1Code = WebHelp.getLoginUser().getOrg1Code();
			bean.setOrg21Code(org1Code);
		} else {
			String org2Code = WebHelp.getLoginUser().getOrg2Code();
			bean.setOrg21Code(org2Code);
		}
		String maxCode = mis.selectCount(bean);
		
		String seq = "";
		if (Tool.CHECK.isEmpty(maxCode)) {
			seq = "00100001";
		}else {
			String batSeq = maxCode.substring(0,3);
			String maxSeq =maxCode.substring(3,8);
			int nowSeq = Integer.parseInt(maxSeq) + 1;
			seq =batSeq+StringDeal.buildFillString("Left", nowSeq + "", "0", 5);
		}
		montCode = bean.getDataYear()+bean.getOrg21Code() + bean.getMontType() + seq;
		bean.setMontCode(montCode);
		try {
			mis.checkExecute(bean);
			mis.addMont(bean);
			if("11".equals(bean.getMontType())||"12".equals(bean.getMontType())){
				ReturnLinkUtils.setShowLink("MontIndexController.shList");
			}
			else{
				ReturnLinkUtils.setShowLink("MontIndexController.fhList");
			}
			WebUtils.getMessageManager().addInfoMessage("监控指标添加成功,请维护监控指标审批链!");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if("11".equals(bean.getMontType())||"12".equals(bean.getMontType())){
				ReturnLinkUtils.setShowLink("MontIndexController.shList");
			}
			else{
				ReturnLinkUtils.setShowLink("MontIndexController.fhList");
			}
			ReturnLinkUtils.setShowLink("montController.list");
			WebUtils.getMessageManager().addInfoMessage("监控指标添加失败！"+e.getCause().getMessage());
			return ForwardPageUtils.getErrorPage();
		}
	}
	/**
	 * @methodName selectProjType
	 * desc  
	 * 选择项目类型
	 * @param bean
	 * @return
	 */
	@RequestMapping("/selectProjType")
	public String selectProjType(MontIndexBean bean) {
		List<MontIndexBean> list = mis.selectProjType(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("projType",bean.getProjType());
		return MONTINDEXPATH + "selectProjTypelist";
	}
	/**
	 * 进入省行监控指标编辑页面
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/preEdit")
	public String preEdit(MontIndexBean bean) {
		String orgType = bean.getOrgType();
		if ("01".equals(orgType)) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		} else {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		List<MontIndexBean> list = mis.getMontMatrByMontCode(bean);
		//无效的物料
//		List<MontIndexBean> invalid = mis.getMontMatrByMontCodeInvalid(bean);
		bean = mis.getMontInfoByMontCode(bean);
		bean.setOrgType(orgType);
//		WebUtils.setRequestAttr("invalid", invalid);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("bean", bean);
//		WebUtils.setRequestAttr("validCount", list.size());
//		WebUtils.setRequestAttr("inValidCount", invalid.size());
		if ("01".equals(orgType)) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/montindex/shList.do?VISIT_FUNC_ID=08120201");
		}else {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()
					+ "/sysmanagement/montindex/fhList.do?VISIT_FUNC_ID=08120202");
		}
		return MONTINDEXPATH + "preEdit";
		
	}


	/**
	 * 物料修改查询
	 * 
	 * @param montBean
	 * @return
	 */
	@RequestMapping("/editMatrs")
	public String editMatrs(MontIndexBean bean) {
		List<MontIndexBean> noUpMatrs = null;
		List<MontIndexBean> haveUpMatrs = null;
		if ("11".equals(bean.getMontType())) {
			// 未维护
			noUpMatrs = mis.fyzcList(bean);
		} else if ("12".equals(bean.getMontType())|| "21".equals(bean.getMontType())) {
			noUpMatrs = mis.zcList(bean);
		} else {
			noUpMatrs = mis.fyList(bean);
		}
		// 已维护
		haveUpMatrs = mis.selectHaveMontMatrs(bean);
		List<MontIndexBean> cglCodeList = mis.getCglCodeList();
		WebUtils.setRequestAttr("cglCodeList", cglCodeList);
		WebUtils.setRequestAttr("noUpMatrs", noUpMatrs);
		WebUtils.setRequestAttr("haveUpMatrs", haveUpMatrs);
		WebUtils.setRequestAttr("bean", bean);
		return MONTINDEXPATH + "/editMatrList";
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("delMontMatr.do")
	@ResponseBody
	public String delMontMatr(MontIndexBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			mis.checkExecute(bean);
			mis.delMontMatr(bean);
			map.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
			map.put("msg", e.getCause().getMessage());
			
		}
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("del.do")
	public String del(MontIndexBean bean) {
		try {
			mis.checkExecute(bean);
			mis.del(bean);
			if("11".equals(bean.getMontType())||"12".equals(bean.getMontType())){
				ReturnLinkUtils.setShowLink("MontIndexController.shList");
			}
			else{
				ReturnLinkUtils.setShowLink("MontIndexController.fhList");
			}
			WebUtils.getMessageManager().addInfoMessage("删除监控指标成功!");
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			
			if("11".equals(bean.getMontType())||"12".equals(bean.getMontType())){
				ReturnLinkUtils.setShowLink("MontIndexController.shList");
			}
			else{
				ReturnLinkUtils.setShowLink("MontIndexController.fhList");
			}
			WebUtils.getMessageManager().addInfoMessage("删除监控指标失败!"+e.getCause().getMessage());
			return ForwardPageUtils.getErrorPage();
		}
	}

	/**
	 * 修改物料监控指标
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("up.do")
	public String up(MontIndexBean bean) {
		try {
			mis.checkExecute(bean);
			mis.editMartMont(bean);
			WebUtils.getMessageManager().addInfoMessage("修改物料监控指标成功,请维护监控指标审批链相关数据!");
			if("01".equals(bean.getOrgType())){
				ReturnLinkUtils.setShowLink("MontIndexController.shList");
			}
			else{
				ReturnLinkUtils.setShowLink("MontIndexController.fhList");
			}
			return ForwardPageUtils.getSuccessPage();
		} catch (Exception e) {
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage("修改物料监控指标失败!"+e.getCause().getMessage());
			ReturnLinkUtils.setShowLink("MontIndexController.shList");
			return ForwardPageUtils.getErrorPage();
			
		}
	}
	
	/**
	 * 监控指标查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("/montList.do")
	public String montList(MontIndexBean bean){
		if (Tool.CHECK.isEmpty(bean.getDataYear())) {
			bean.setDataYear(Tool.DATE.getDateStrNO().substring(0,4));
		}
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		//得到二级行或一级行名称
		MontIndexBean bean2 = mis.getOrg21Name(bean.getOrg2Code());
		List<MontIndexBean> list = mis.selectMont(bean);
		WebUtils.setRequestAttr("ml",list);
		WebUtils.setRequestAttr("bean",bean);
		WebUtils.setRequestAttr("bean2", bean2);
		return MONTINDEXPATH+"montList";
	}
	
	/**
	 * 监控指标导出
	 * @param bean
	 * @return
	 */
	@RequestMapping("/download.do")
	@ResponseBody
	public String download(MontIndexBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();	
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg1Code(org1Code);
		if (bean.getOrg2Code()==null||bean.getOrg2Code().equals("")) {//如果二级行为空，则赋值用户所在二级行
			bean.setOrg2Code(WebHelp.getLoginUser().getOrg2Code());
		}
		//Excel导出操作
		try {if (mis.download(bean)==null) {
			jsonObject.put("pass", false);
		} else {
			jsonObject.put("pass", true);
		}		

		} catch (Exception e) {
			jsonObject.put("pass", false);
			e.printStackTrace();
		}		
		return jsonObject.writeValueAsString();
	}
		
	
	/**
	 * Ajax检验监控指标是否存在合同设备表
	 * 
	 * @param montCode
	 * @return
	 */
	@RequestMapping("checkMontCode.do")
	@ResponseBody
	public String checkMontCode(String montCode,String matrCode) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Map<String,Object> map = mis.checkMontCode(montCode,matrCode);
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 增加的时候校验 当年数据是否存在 如果存在就弹出提示框
	 * @param bean
	 * @return
	 */
	@RequestMapping("preAddIsTrue.do")
	@ResponseBody
	public String preAddIsTrue(MontIndexBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if ("11".equals(bean.getMontType())||"12".equals(bean.getMontType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else if ("21".equals(bean.getMontType())||"22".equals(bean.getMontType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		String montCode = mis.preAddIsTrue(bean);
		if (Tool.CHECK.isEmpty(montCode)) {
			jsonObject.put("isExist", false);
			jsonObject.put("msg", bean.getDataYear()+"年该类型的监控指标是首次增加，必须先通过EXCEL导入");
		} else {
			jsonObject.put("isExist", true);
		}

		return jsonObject.writeValueAsString();
	}
//	/**
//	 * 启用
//	 * @param bean
//	 * @return
//	 */
//	@RequestMapping("/changeValid.do")
//	@ResponseBody
//	public String changeValid(MontIndexBean bean){
//		AbstractJsonObject jsonObject = new SuccessJsonObject();
//		if ("11".equals(bean.getMontType())||"12".equals(bean.getMontType())) {
//			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
//		}else if ("21".equals(bean.getMontType())||"22".equals(bean.getMontType())) {
//			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
//		}
//		try {
//			mis.changeValid(bean);
//			jsonObject.put("flag", true);
//		} catch (Exception e) {
//			jsonObject.put("flag", false);
//			e.printStackTrace();
//		}
//
//		return jsonObject.writeValueAsString();
//	}
}
