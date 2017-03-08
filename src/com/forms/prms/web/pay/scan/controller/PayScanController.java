package com.forms.prms.web.pay.scan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.json.AbstractJsonObject;
import com.forms.platform.web.json.FailureJsonObject;
import com.forms.platform.web.json.SuccessJsonObject;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.icms.domain.ScanBean;
import com.forms.prms.web.icms.service.ScanService;
import com.forms.prms.web.pay.scan.domain.PayScanBean;
import com.forms.prms.web.pay.scan.service.PayScanService;
import com.forms.prms.web.util.ForwardPageUtils;


@Controller
@RequestMapping("/common/pay/scan/")
public class PayScanController {
	private static final String FUNCTION = "pay/scan/";
	@Autowired
	private ScanService scanSevice;
	@Autowired
	private PayScanService payScanSevice;
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("preadd.do")
	public String preadd(HttpServletRequest request, PayScanBean bean) {
		request.setAttribute("batchNo", bean.getBatchNo());
		request.setAttribute("mainCnt", bean.getMainCnt());
		request.setAttribute("attachCnt", bean.getAttachCnt());
		ScanBean scanBean = null;
		try 
		{
			scanBean = scanSevice.findICMSConfig(WebHelp.getLoginUser().getDutyCode());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			WebUtils.getMessageManager().addInfoMessage("提示：未找到所属一级行对应的ICMS省行号");
			return ForwardPageUtils.getErrorPage();
		}
		request.setAttribute("scanConfig", scanBean);
		return FUNCTION + "add";
	}
	
	/**
	 * 扫描后上传前的处理
	 * @param bean
	 * @return
	 */
	@RequestMapping("insertAjax.do")
	@ResponseBody
	public String insertAjax(PayScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		try{
			payScanSevice.insertBatchDetail(bean);
			//查询检验出错的记录
			bean.setDataFlag("00");
			bean.setPayId("");
			List<PayScanBean> list = payScanSevice.selectBatchDetail(bean);
			jsonObject.put("errorList", list);
		}catch(Exception e){
			jsonObject = new FailureJsonObject(e.getMessage());
		}
		
		return jsonObject.writeValueAsString();
	}
	
	/**
	 * 上传后的处理
	 * @param bean
	 * @return
	 */
	@RequestMapping("uploadAjax.do")
	@ResponseBody
	public String uploadAjax(PayScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		try{
			payScanSevice.updateBatchDetail(bean);
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
	@RequestMapping("add.do")
	@ResponseBody
	public String add(PayScanBean bean) {
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
	public String preupdate(HttpServletRequest request, PayScanBean bean) {
		ScanBean scanBean = scanSevice.selectUUID(bean);
		String pkuuuid = "";
		if(scanBean!=null){
			pkuuuid = scanBean.getIcmsPkuuid();
		}
		String dutyCode = payScanSevice.findDutyCode(bean.getId());
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
		request.setAttribute("dataFlag", bean.getDataFlag());
		request.setAttribute("isPrePay", bean.getIsPrePay());
		return FUNCTION + "update";
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("update.do")
	@ResponseBody
	public String update(PayScanBean bean) {
		bean.setInstOper(WebHelp.getLoginUser().getUserId());
		AbstractJsonObject jsonObject = new SuccessJsonObject();
		try{
//			scanSevice.updateUUID(bean);
			int cnt;
			if ("Y".equalsIgnoreCase(bean.getIsPrePay())) {
				cnt = payScanSevice.agreePreEdit(bean);
			} else {
				cnt = payScanSevice.agreeEdit(bean);
			}
			if (cnt != 1) {
				throw new Exception ("付款单号"+bean.getPayId()+"影像编辑已经被其他人员重新上传成功!");
			} 
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
	public String view(HttpServletRequest request, PayScanBean bean) {
		request.setAttribute("payId", bean.getId());
		request.setAttribute("pkuuid", bean.getIcmsPkuuid());
		String dutyCode = payScanSevice.findDutyCode(bean.getId());
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
	
	
}
