package com.forms.prms.web.sysmanagement.matrtype.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.core.util.Tool;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.platform.web.returnlink.annotation.AddReturnLink;
import com.forms.prms.tool.Check;
import com.forms.prms.tool.constantValues.FileValues;
import com.forms.prms.web.sysmanagement.matrtype.domain.MatrType;
import com.forms.prms.web.sysmanagement.matrtype.service.MatrTypeService;
import com.forms.prms.web.util.ForwardPageUtils;

/**
 * 
 * @author LinJia
 *
 */

@Controller
@RequestMapping("/sysmanagement/matrtype")
public class MatrTypeController
{
	private static final String FUNCTION = "sysmanagement/matrtype/";

	@Autowired
	private MatrTypeService mService;
	
	/**
	 * 列表 --010806
	 * @param mt
	 * @return
	 */
	@RequestMapping("list.do")
	@AddReturnLink(id="MatrTypeList",displayValue="返回物料类别列表")
	public String list(MatrType mt)
	{	
		if (mt == null)
			mt = new MatrType();
		
		if (Tool.CHECK.isEmpty(mt.getMatrType())) {
			mt.setMatrType("0");
		}
		List<MatrType> mtList = mService.list(mt);
		WebUtils.setRequestAttr("mt", mt);
		WebUtils.setRequestAttr("mtList", mtList);
		return FUNCTION + "list";
	}
	
	/**
	 * 查看明细-- 010803
	 * @param matrCode
	 * @param jspName
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(String matrCode,String jspName)
	{
		MatrType mt = mService.view(matrCode);
		WebUtils.setRequestAttr("mt", mt);
		WebUtils.setRequestAttr("uri", WebUtils.getRequest().getContextPath()+"/sysmanagement/matrtype/list.do?VISIT_FUNC_ID=010806");
		return FUNCTION + jspName;
	}
	
	/**
	 * 进入导入Excel文件页面 --010801
	 * @return
	 */
	@RequestMapping("preImpExcel.do")
	public String preImpExcel(){
		return FUNCTION + "impExcel";
	}
	
	/**
	 * 物料选择Option列表 --010804
	 * @param m
	 * @return
	 */
	@RequestMapping("matrTypeOption.do")
	public String matrTypeOption(MatrType m){
		String allMontType = "0";
		if (Tool.CHECK.isEmpty(m.getIsSpec())) {
			allMontType = "1";
			
		}
		m.setAllMontType(allMontType);
		List cglList = mService.getcglList();
		WebUtils.setRequestAttr("cglList", cglList);
		WebUtils.setRequestAttr("mOptions", mService.matrTypeOption(m));
		WebUtils.setRequestAttr("m", m);
		WebUtils.setRequestAttr("allMontType", allMontType);
		WebUtils.setRequestAttr("matrCode", m.getMatrCode());
		return FUNCTION + "matrTypeOptionPage";
	}
	
	
	/**
	 * 导入Excel--010802
	 * @param mt
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("impExcel.do")
	public String impExcel(MatrType mt) throws Exception
	{
			mService.imp(mt);
			WebUtils.getMessageManager().addInfoMessage("操作成功，后台正在处理！请点击下方按钮查看处理结果");
			WebUtils.setRequestAttr("buttonValue","导入列表");
		    return ForwardPageUtils.getSuccessPage();

	}
	
	/**
	 * 物料导入模板下载
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tempDownload.do")
	public String tempDownload(HttpServletResponse response) throws Exception{
		{
			String fileName = "matrTypeImportTemp.xls";
			response.setContentType("application/x-msdownload;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("物料信息导入模板.xls".getBytes("GB2312"), "ISO-8859-1"));
			BufferedOutputStream bos = null;
			FileInputStream fis = null;
			try
			{	
				File file = FileValues.getTemplatesFle(fileName);
				if(!file.exists())
				{
					throw new Exception("找不到指定文件："+fileName);
				}
				// 开始下载
				fis = new FileInputStream(file);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] b = new byte[8192];
				int data = 0;
				while ((data = fis.read(b)) != -1)
				{
					bos.write(b, 0, data);
				}
				// 刷新流
				bos.flush();
			}
			catch (Exception e)
			{
				throw e;
			}
			finally
			{
				if (bos != null)
					bos.close();
				if (fis != null)
					fis.close();
			}
			return null;
		}		
	}
	
	
	/**
	 * 物料新增页面--010805 
	 * @param mt
	 * @return
	 */
	@RequestMapping("preAdd.do")
	public String preAdd(MatrType mt){
		
		ReturnLinkUtils.addReturnLink("preAdd", "返回新增");
		return FUNCTION+"add";
	
	}
	
	/**
	 * 物料新增--01080501
	 * @param mt
	 * @return
	 */
	
	
	@RequestMapping("add.do")
	public String add(MatrType mt){
		String msg=mService.insert(mt);	
		if (msg != null) {
			CommonLogger.error(msg);
			WebUtils.getMessageManager().addErrorMessage(msg);
			ReturnLinkUtils.setShowLink("preAdd");
			return ForwardPageUtils.getErrorPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("操作成功！");
			ReturnLinkUtils.setShowLink("preAdd");
			return ForwardPageUtils.getSuccessPage();
		}
	}
	/**
	 * ajax检查物料编码--01080502
	 * @param matrCode
	 * @return
	 */
	
	@RequestMapping("checkMatrType.do")
	@ResponseBody
	public String checkMatrType(String matrCode, String cglCode) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		if (!Check.isEmpty(matrCode)) {
			MatrType mt = mService.view(matrCode);
			if (mt == null) {
				jsonObject.put("isExist", false);
			} else {
				jsonObject.put("isExist", true);
			}
		}
		else{
			MatrType mt = mService.propertyList(cglCode);
			if (mt == null) {
				jsonObject.put("isExist", false);
			} else {
				jsonObject.put("isExist", true);
				jsonObject.put("longPrepaidCode", mt.getLongPrepaidCode());
				jsonObject.put("shortPrepaidCode", mt.getShortPrepaidCode());
			}
		}

		return jsonObject.writeValueAsString();
	}
	
	/**
	 * ajax检查费用核算码--01080705
	 * @param matrCode
	 * @return
	 */
	
	@RequestMapping("checkCglType.do")
	@ResponseBody
	public String checkCglType(String cglCode) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		MatrType mt = mService.propertyList(cglCode);
		if (mt == null) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
			jsonObject.put("longPrepaidCode", mt.getLongPrepaidCode());
			jsonObject.put("shortPrepaidCode", mt.getShortPrepaidCode());
		}

		return jsonObject.writeValueAsString();
	}
	
	/**
	 * ajax检查该费用核算码的长短期待摊预提核算码是否被使用--01080706
	 * @param matrCode
	 * @return
	 */
	
	@RequestMapping("checkIsUseCglCode.do")
	@ResponseBody
	public String checkCglCode(String cglCode) {
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		int count = mService.checkCglCode(cglCode);
		if (count == 0) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
		}
		return jsonObject.writeValueAsString();
	}
	
	
	@RequestMapping("checkCglCode.do")
	@ResponseBody
	public String  queryCglCode(String cglCode){
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		MatrType mt=mService.propertyList(cglCode);
		if (mt == null) {
			jsonObject.put("isExist", false);
		} else {
			jsonObject.put("isExist", true);
			jsonObject.put("matrType", mt);
		}
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 弹出费用核算码选择页面 --01080504
	 * @param mt
	 * @return
	 */
	@RequestMapping("searchCglCode.do")
	public String listCglCode(MatrType mt)
	{	
		
		List<MatrType> mtList = mService.listCglCode(mt);
		WebUtils.setRequestAttr("cglCode", mt.getCglCode());
		WebUtils.setRequestAttr("mtList", mtList);
		return FUNCTION + "searchClgCode";
	}
	/**
	 * 弹出费用核算码选择页面 --01080504
	 * @param mt
	 * @return
	 */
	@RequestMapping("listPrepaidCode.do")
	public String listPrepaidCode(MatrType mt)
	{	
		ReturnLinkUtils.addReturnLink("MatrTypeController.listPrepaidCode", "返回列表");
		List<MatrType> mtList = mService.listCglCode(mt);
		//获取所有的物料核算码
		WebUtils.setRequestAttr("cglCode", mt.getCglCode());
		WebUtils.setRequestAttr("mtList", mtList);
		return FUNCTION + "listCgl";
	}
	/**
	 * 弹出费用核算码选择页面 --01080504
	 * @param mt
	 * @return
	 */
	@RequestMapping("preAddPrepaidCode.do")
	public String preAddPrepaidCode(MatrType mt)
	{	
		WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/matrtype/listPrepaidCode.do?VISIT_FUNC_ID=010807");
		return FUNCTION + "addCgl";
	}
	/**
	 * 弹出费用核算码选择页面 --01080504
	 * @param mt
	 * @return
	 */
	@RequestMapping("addPrepaidCode.do")
	public String addPrepaidCode(MatrType mt)
	{	
		mService.addPrepaidCode(mt);
		ReturnLinkUtils.setShowLink("MatrTypeController.listPrepaidCode");
		WebUtils.getMessageManager().addInfoMessage("新增成功");
		return ForwardPageUtils.getSuccessPage();
	}
	/**
	 * 弹出费用核算码选择页面 --01080504
	 * @param mt
	 * @return
	 */
	@RequestMapping("preEditPrepaidCode.do")
	public String preEditPrepaidCode(MatrType mt)
	{	
		MatrType matrType = mService.propertyList(mt.getCglCode());
		WebUtils.setRequestAttr("mt",matrType);
		WebUtils.setRequestAttr("url",WebUtils.getRequest().getContextPath()+"/sysmanagement/matrtype/listPrepaidCode.do?VISIT_FUNC_ID=010807");
		return FUNCTION + "updateCgl";
	}
	/**
	 * 弹出费用核算码选择页面 --01080504
	 * @param mt
	 * @return
	 */
	@RequestMapping("updatePrepaidCode.do")
	public String updatePrepaidCode(MatrType mt)
	{	
		mService.updatePrepaidCode(mt);
		ReturnLinkUtils.setShowLink("MatrTypeController.listPrepaidCode");
		WebUtils.getMessageManager().addInfoMessage("更新成功");
		return ForwardPageUtils.getSuccessPage();
	}
}
