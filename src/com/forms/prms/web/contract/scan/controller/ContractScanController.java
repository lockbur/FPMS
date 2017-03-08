package com.forms.prms.web.contract.scan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.scan.service.ContractScanService;
import com.forms.prms.web.icms.domain.ScanBean;
import com.forms.prms.web.icms.service.ScanService;
import com.forms.prms.web.sysmanagement.uploadfilemanage.service.UpFileManagerService;
import com.forms.prms.web.util.ForwardPageUtils;


@Controller
@RequestMapping("/common/contract/scan/")
public class ContractScanController {
	private static final String FUNCTION = "contract/scan/";
	@Autowired
	private ScanService scanSevice;
	@Autowired
	private ContractScanService contractScanService;
	@Autowired 
	public UpFileManagerService upFileService;
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("preadd.do")
	public String preadd(HttpServletRequest request, ScanBean bean) {
		String cntSuccScan = (String) request.getAttribute("cntSuccScan");
		request.setAttribute("contractNo", bean.getId());
		String dutyCode = WebHelp.getLoginUser().getDutyCode();
		ScanBean sBean = null;
		try 
		{
			sBean = scanSevice.findICMSConfig(dutyCode);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage("提示：未找到所属一级行对应的ICMS省行号");
			return ForwardPageUtils.getErrorPage();
		}
		request.setAttribute("scanConfig", sBean);
		request.setAttribute("cntSuccScan", cntSuccScan);
		return FUNCTION + "add";
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("add.do")
	@ResponseBody
	public String add(ScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		try{
		scanSevice.insertUUID(bean);
		}catch(Exception e){
			jsonObject = new FailureJsonObject(e.getMessage());
		}
		
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("preupdate.do")
	public String preupdate(HttpServletRequest request, ScanBean bean) {
		ScanBean scanBean = scanSevice.selectUUID(bean);
		String pkuuuid = "";
		if(scanBean!=null){
			pkuuuid = scanBean.getIcmsPkuuid();
		}
		String dutyCode = contractScanService.findDutyCode(bean.getId());
		ScanBean sBean = null;
		try 
		{
			sBean = scanSevice.findICMSConfig(dutyCode);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage("提示：未找到所属一级行对应的ICMS省行号");
			return ForwardPageUtils.getErrorPage();
		}
		request.setAttribute("scanConfig", sBean);
		request.setAttribute("pkuuid",pkuuuid);
		request.setAttribute("contractNo", bean.getId());
		return FUNCTION + "update";
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("update.do")
	@ResponseBody
	public String update(ScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		try{
			scanSevice.updateUUID(bean);
		}catch(Exception e){
			jsonObject = new FailureJsonObject(e.getMessage());
		}
		
		return jsonObject.writeValueAsString();
	}
	
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("view.do")
	public String view(HttpServletRequest request, ScanBean bean) {
		request.setAttribute("contractNo", bean.getId());
		request.setAttribute("pkuuid", bean.getIcmsPkuuid());
		String dutyCode = contractScanService.findDutyCode(bean.getId());
		ScanBean sBean = null;
		try 
		{
			sBean = scanSevice.findICMSConfig(dutyCode);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage("提示：未找到所属一级行对应的ICMS省行号");
			return ForwardPageUtils.getErrorPage();
		}
		request.setAttribute("scanConfig", sBean);
		return FUNCTION + "view";
	}
	
	/**
	 * 下载插件
	 * 
	 * @param upFile
	 * @return
	 */
	@RequestMapping("scanUpload.do")
	public void upFileDownload(HttpServletRequest request,HttpServletResponse response,String fileId) throws Exception{
		upFileService.upFileDownload(request ,response, fileId);
	}
	
	
}
