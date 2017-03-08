package com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.controller;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.net.aso.f;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.platform.web.token.PreventDuplicateSubmit;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.domain.DutyMergeBean;
import com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.service.DutyMergeService;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/sysmanagement/orgremanagement/dutyMerge")
public class DutyMergeController {
	@Autowired
	private DutyMergeService service;
	@Autowired
	private ConcurrentService concurrentService;
	
	private String BASEPATH = "/sysmanagement/orgremanagement/dutyMerge/";
	private String aprvTag = "01";
	/**
	 * 经办列表
	 */
	@RequestMapping("/mergeHandleList.do")
	@AddReturnLink(id = "mergeHandleList", displayValue = "撤并经办列表查询")
	public String mergeHandleList(DutyMergeBean bean){
		List<DutyMergeBean> handleList = service.mergeHandleList(bean);
		WebUtils.setRequestAttr("list", handleList);
		bean.setMenuTag("01");
		WebUtils.setRequestAttr("searchInfo", bean);
		return BASEPATH +"list";
	}
	
	/**
	 * 新增的时候的校验
	 */
	/**
	 * 异步校验
	 * @param orgRemBean
	 * @return
	 */
	@RequestMapping("handleAddAjax.do")
	@ResponseBody
	public String handleAddAjax(DutyMergeBean bean) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Map map = service.handleAddAjax(bean);
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 关联信息
	 * @param bean
	 * @return
	 */
	@RequestMapping("glxxAjax.do")
	@ResponseBody
	public String glxxAjax(DutyMergeBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		Map<String, Object>  map = new HashMap<String, Object>();
		String flag = "Y";
		String msg="";
		String typeName="";
		if ("01".equals(bean.getType())) {
			typeName = "责任中心";
		}else {
			typeName ="机构";
		}
		//校验 责任中心是否存在
		boolean flag1 = service.existsCode(bean.getCodeBef(),bean.getType()); 
		if (!flag1) {
			flag = "N";
			msg += typeName+bean.getCodeBef()+"不存在";
		}
		boolean flag2 = service.existsCode(bean.getCodeCur(),bean.getType()); 
		if (!flag2) {
			flag = "N";
			if (!Tool.CHECK.isEmpty(msg)) {
				msg +=",";
			}
			msg += typeName+bean.getCodeCur()+"不存在";
		}
		if (flag1 && flag2) {
			//都存在 
			if (!service.existBatch(bean.getCodeBef(),bean.getType())) {
				flag = "W";
				msg += typeName +bean.getCodeBef()+"没有失效，且在交叉表中存在!";
			}
			if (!service.existFndwrr(bean.getCodeCur(),bean.getType())) {
				flag = "W";
				if (!Tool.CHECK.isEmpty(msg)) {
					msg +=",";
				}
				msg += typeName +bean.getCodeCur()+"在交叉表不中存在,可能已经失效！";
			}
		}
		map.put("flag", flag);
		map.put("msg", msg);
		jsonObject.put("data", map);
		return jsonObject.writeValueAsString();
	}
	/**
	 * 新增
	 * @param bean
	 * @return
	 */
	@RequestMapping("handleAdd.do")
	public String handleAdd(DutyMergeBean bean){
		boolean flag = false;
		try {
			service.handleAdd(bean);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		ReturnLinkUtils.setShowLink(new String[]{"handlePreAdd"});
		WebUtils.getMessageManager().addInfoMessage("撤并经办添加成功，等待复核人员复核！");
		return ForwardPageUtils.getSuccessPage();
	}
	
	//--------------------------------------------复核-------------------------------
	
	//---------------------------------------------查询----------------------------------------
	
	/**
	 * 撤并删除
	 * @param bean
	 * @return
	 */
	@RequestMapping("del.do")
	public String handleDel(DutyMergeBean bean){
		boolean flag = false;
		try {
			service.handleDel(bean);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return ForwardPageUtils.getReturnUrlString("撤并删除", flag,
			new String[] {  "mergeQueryList"});
	}
	/**
	 * 撤并经办编辑页
	 * @param bean
	 * @return
	 */
	@RequestMapping("preEdit.do")
	public String handlePreEdit(DutyMergeBean bean){
		DutyMergeBean bean1 = new DutyMergeBean();
		List<DutyMergeBean> list = service.selectGlgxList(bean);
		if (null != list && list.size()>0) {
			  bean1 = list.get(0);
		}
		WebUtils.setRequestAttr("bean", bean1);
		if ("03".equals(bean.getMenuTag())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/orgremanagement/dutyMerge/mergeQueryList.do?VISIT_FUNC_ID=01041103");
		}else if ("02".equals(bean.getMenuTag())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/orgremanagement/dutyMerge/mergeAprvList.do?VISIT_FUNC_ID=01041102");
		}
		WebUtils.setRequestAttr("list", list);
		return BASEPATH +"edit";
	}
	/**
	 * 经办编辑
	 * @param bean
	 * @return
	 */
	@RequestMapping("edit.do")
	public String handleEdit(DutyMergeBean bean){
		boolean flag = service.edit(bean);
			return ForwardPageUtils.getReturnUrlString("撤并编辑", flag,
					new String[] {  "mergeQueryList"});
		
	}
	
	/**
	 * 复核页面
	 * @param bean
	 * @return
	 */
	@RequestMapping("preAprv")
	public String preAprv(DutyMergeBean bean){
		
		DutyMergeBean bean1 = new DutyMergeBean();
		List<DutyMergeBean> list = service.selectGlgxList(bean);
		if (null != list && list.size()>0) {
			  bean1 = list.get(0);
		}
		bean1.setAprvTag(aprvTag);
		if ("02".equals(bean.getMenuTag())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/orgremanagement/dutyMerge/mergeAprvList.do?VISIT_FUNC_ID=01041102");
		}else if ("03".equals(bean.getMenuTag())) {
			WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/orgremanagement/dutyMerge/mergeQueryList.do?VISIT_FUNC_ID=01041103");
		}
		WebUtils.setRequestAttr("bean", bean1);
		WebUtils.setRequestAttr("list", list);
		return BASEPATH+"aprv";
	}
	
	
	/**
	 * 撤并前列表
	 * @param bean
	 * @return
	 */
	@RequestMapping("mergeDutys.do")
	public String mergeDutys(DutyMergeBean bean){
		WebUtils.setRequestAttr("bean", bean);
		String desc="";
		if ("01".equals(bean.getType())) {
			desc = "责任中心";
		}else {
			desc="机构";
		}
		WebUtils.setRequestAttr("desc", desc);
		List<DutyMergeBean> list = service.getMergeDutys(bean);
		WebUtils.setRequestAttr("list", list);
		return BASEPATH+"mergeDutys";
	}
	
	
	
	//----------------------------20160105 lsj----------------------------
	
	/**
	 * 撤并经办
	 * @param bean
	 * @return
	 */
	@RequestMapping("/handlePreAdd.do")
	@AddReturnLink(id = "handlePreAdd", displayValue = "撤并经办")
	public String preAdd(DutyMergeBean bean){
		//待处理的撤并日期
		String dealDate = service.getDealDate();
		//以下列表的数据日期
		DutyMergeBean dutyMergeBean = service.getListDate();
		if(!Tool.CHECK.isEmpty(dutyMergeBean)){
			//查询经办列表
			List<DutyMergeBean> list = service.list(dutyMergeBean.getBatchNo());
			
			DutyMergeBean dMBean = service.getFndwrrChangeInfo(dutyMergeBean.getBatchNo());
			
			WebUtils.setRequestAttr("list", list);
			WebUtils.setRequestAttr("listDate", dutyMergeBean.getDataDate());
			WebUtils.setRequestAttr("batchNo", dutyMergeBean.getBatchNo());
			WebUtils.setRequestAttr("status", dutyMergeBean.getStatus());
			WebUtils.setRequestAttr("memo", dMBean.getMemo());
		}
		WebUtils.setRequestAttr("dealDate", dealDate);
		return BASEPATH+"handleAdd";
	}
	
	/**
	 * ajax保存校验
	 * @param bean
	 * @return
	 */
	@RequestMapping("ajaxSaveValidate.do")
	@ResponseBody
	public String ajaxSaveValidate(DutyMergeBean dutyMergeBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		DutyMergeBean dmBean = service.queryFndwrr(dutyMergeBean);
		boolean flag = true;
		if(Tool.CHECK.isEmpty(dmBean)){
			flag = false;
		}
		jsonObject.put("flag", flag);
		jsonObject.put("dmBean", dmBean);
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * ajax保存
	 * @param bean
	 * @return
	 */
	@RequestMapping("ajaxSave.do")
	@ResponseBody
	public String ajaxSave(DutyMergeBean dutyMergeBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//确定更新后再更新tb_fndwrr_change_detail中的code_curr、name_cur 以及 tb_fndwrr_change对应记录的last_updtime。
		String codeCur = dutyMergeBean.getCodeCur();
		service.ajaxSave(dutyMergeBean);
		dutyMergeBean = service.getDutyBean(dutyMergeBean);
		if (Tool.CHECK.isEmpty(dutyMergeBean)) {
			dutyMergeBean = new DutyMergeBean();
			dutyMergeBean.setCodeCur(codeCur);
		}
		jsonObject.put("dmBean", dutyMergeBean);
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * ajax提交校验
	 * @param bean
	 * @return
	 */
	@RequestMapping("ajaxSubmitValidate.do")
	@ResponseBody
	public String ajaxSubmitValidate(DutyMergeBean dutyMergeBean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//检查，是否  {batch_no} 的tb_change_detail中的数据中的code_cur字段都已经有值
		List<DutyMergeBean> list = service.queryFndwrrChnageDetail(dutyMergeBean.getBatchNo());
		if(Tool.CHECK.isEmpty(list)){//如果全部撤并了
			jsonObject.put("flag", true);
		}else{
			jsonObject.put("flag", false);
		}
		return jsonObject.writeValueAsString();
	}
	/**
	 * 提交
	 * @param dutyMergeBean
	 * @return
	 */
	@RequestMapping("submit.do")
	@PreventDuplicateSubmit
	public String submit(DutyMergeBean dutyMergeBean){
		try {
			service.updateStatus(dutyMergeBean);
			return ForwardPageUtils.getReturnUrlString("撤并提交", true,
					new String[] {});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ForwardPageUtils.getReturnUrlString("撤并提交", false,
					new String[] {});
		}
		
	}
	/**
	 * 撤并复核查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("mergeAprvList.do")
	@AddReturnLink(id = "mergeAprvList", displayValue = "撤并复核列表查询")
	public String mergeAprvList(DutyMergeBean bean){
		//待处理的撤并日期
		String dealDate = service.getDealDate();
		//以下列表的数据日期
		DutyMergeBean  dutyMergeBean= service.getListDate();
		//查询复核列表
		if(!Tool.CHECK.isEmpty(dutyMergeBean)){
			List<DutyMergeBean> list = service.checkList(dutyMergeBean.getBatchNo());
			WebUtils.setRequestAttr("list", list);
			WebUtils.setRequestAttr("listDate", dutyMergeBean.getDataDate());
			WebUtils.setRequestAttr("batchNo", dutyMergeBean.getBatchNo());
			//拿last_updtime的值
			DutyMergeBean dMergeBean = service.getFndwrrChangeInfo(dutyMergeBean.getBatchNo());
			WebUtils.setRequestAttr("lastUpdtime", dMergeBean.getLastUpdtime());
			
			DutyMergeBean dMBean = service.getFndwrrChangeInfo(dutyMergeBean.getBatchNo());
			WebUtils.setRequestAttr("memo", dMBean.getMemo());
		}
		WebUtils.setRequestAttr("dealDate", dealDate);
		
		return BASEPATH+"aprv";
	}
	
	
	/**
	 * 撤并复核提交前校验
	 * @param bean
	 * @return
	 */
	@RequestMapping("aprvAjax.do")
	@ResponseBody
	public String aprvAjax(DutyMergeBean bean){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		//拿last_updtime的值
		DutyMergeBean dMergeBean = service.getFndwrrChangeInfo(bean.getBatchNo());
		boolean flag = false;
		if(bean.getLastUpdtime().equals(dMergeBean.getLastUpdtime())){//最后更新时间一致
			flag = true;
		}
		jsonObject.put("flag", flag);
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 撤并复核提交
	 * @param bean
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping("aprv.do")
	@PreventDuplicateSubmit
	public String aprv(DutyMergeBean bean) throws UnknownHostException{
		try {
			//校验并发性
			String org21Code = WebHelp.getLoginUser().getOrg1Code();
			String instOper = WebHelp.getLoginUser().getUserId();
			try {
				String memo = "复核提交的撤并责任中心时增加的锁";
				 concurrentService.checkAndAddLock(ConcurrentType.Concurrent_C,
							ConcurrentType.C1,org21Code,instOper,memo);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getCause().getMessage());
			}
			service.aprv(bean);
			return ForwardPageUtils.getReturnUrlString("撤并复核", true,
					new String[] {  "mergeAprvList"});
		} catch (Exception e) {
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage(e.getCause().getMessage());
			return ForwardPageUtils.getReturnUrlString("撤并复核", false,
					new String[] {  "mergeAprvList"});
		}finally{
			//删除锁
			concurrentService.delConcurrentLock(ConcurrentType.Concurrent_C, ConcurrentType.C1,"");
		}
	}
	/**
	 * 撤并查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("mergeQueryList.do")
	@AddReturnLink(id = "mergeQueryList", displayValue = "撤并查询列表")
	public String mergeQueryList(DutyMergeBean bean){
		List<DutyMergeBean> list = service.mergeHandleList(bean);
		WebUtils.setRequestAttr("list", list);
		WebUtils.setRequestAttr("searchInfo", bean);
		return BASEPATH+"list";
	}
	
	/**
	 * 明细查询
	 * @param bean
	 * @return
	 */
	@RequestMapping("detail.do")
	public String detail(DutyMergeBean bean){
		DutyMergeBean dMBean = service.queryDetail(bean);
		WebUtils.setRequestAttr("dMBean", dMBean);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/orgremanagement/dutyMerge/mergeQueryList.do?VISIT_FUNC_ID=01041103");
		return BASEPATH+"detail";
	}
}
