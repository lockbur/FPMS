package com.forms.prms.web.sysmanagement.referencespecial.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.sysmanagement.referencespecial.dao.ReferenceSpecialDAO;
import com.forms.prms.web.sysmanagement.referencespecial.domain.Reference;
import com.forms.prms.web.sysmanagement.referencespecial.domain.Special;
import com.forms.prms.web.sysmanagement.referencespecial.domain.TaxCode;

@Service
public class ReferenceSpecialService {
	@Autowired
	private ReferenceSpecialDAO rsDAO;

	/**
	 * 参考 列表
	 * 
	 * @param r
	 * @return
	 */
	public List<Reference> referenceList(Reference r) {
		CommonLogger.info("查询参考列表,ReferenceSpecialService,referenceList");
		ReferenceSpecialDAO pageDAO = PageUtils.getPageDao(rsDAO);
		return pageDAO.referenceList(r);
	}

	/**
	 * 检查参考Id是否存在
	 * 
	 * @param referenceId
	 * @return
	 */
	public boolean checkRID(String referenceId) {
		CommonLogger.info("检查参考Id是否存在,ReferenceSpecialService,checkRID");
		return Tool.CHECK.isEmpty(rsDAO.checkRID(referenceId)) ? true : false;
	}

	/**
	 * 参考 新增
	 * 
	 * @param r
	 */
	public int referenceAdd(Reference r) {
		CommonLogger.info("参考新增,ReferenceSpecialService,referenceAdd");
		return rsDAO.referenceAdd(r);
	}

	/**
	 * 参考详情
	 * 
	 * @param referenceId
	 * @return
	 */
	public Reference referenceView(String referenceId) {
		CommonLogger.info("参考详情,ReferenceSpecialService,referenceView");
		return rsDAO.referenceView(referenceId);
	}

	/**
	 * 参考 更新
	 * 
	 * @return
	 */
	public int referenceUpd(Reference r) {
		CommonLogger.info("参考更新,ReferenceSpecialService,referenceUpd");
		return rsDAO.referenceUpd(r);
	}

	/**
	 * 专项 列表
	 * 
	 * @param s
	 * @return
	 */
	public List<Special> specialList(Special s) {
		CommonLogger.info("查询专项列表,ReferenceSpecialService,specialList");
		ReferenceSpecialDAO pageDAO = PageUtils.getPageDao(rsDAO);
		return pageDAO.specialList(s);
	}

	/**
	 * 检查专项Id是否存在
	 * 
	 * @param specialId
	 * @return
	 */
	public boolean checkSID(String specialId) {
		CommonLogger.info("检查专项Id是否存在,ReferenceSpecialService,checkSID");
		return Tool.CHECK.isEmpty(rsDAO.checkSID(specialId)) ? true : false;
	}

	/**
	 * 专项 新增
	 * 
	 * @param s
	 */
	public int specialAdd(Special s) {
		CommonLogger.info("专项新增,ReferenceSpecialService,specialAdd");
		return rsDAO.specialAdd(s);
	}

	/**
	 * 专项详情
	 * 
	 * @param specialId
	 * @return
	 */
	public Special specialView(String specialId) {
		CommonLogger.info("专项详情,ReferenceSpecialService,specialView");
		return rsDAO.specialView(specialId);
	}

	/**
	 * 专项 更新
	 * 
	 * @return
	 */
	public int specialUpd(Special s) {
		CommonLogger.info("专项更新,ReferenceSpecialService,specialUpd");
		return rsDAO.specialUpd(s);
	}

	public List<Special> specialPage(Special s) {
		s.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
		ReferenceSpecialDAO pagedao = PageUtils.getPageDao(rsDAO);
		return pagedao.specialPage(s);
	}

	public List<Reference> referencePage(Reference r) {
		r.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
		ReferenceSpecialDAO pagedao = PageUtils.getPageDao(rsDAO);
		return pagedao.referencePage(r);
	}

	public List<TaxCode> taxCodeList(TaxCode taxCode) {
		CommonLogger.info("查询税码列表,ReferenceSpecialService,taxCodeList");
		ReferenceSpecialDAO pageDAO = PageUtils.getPageDao(rsDAO);
		return pageDAO.taxCodeList(taxCode);
	}
	/**
	 * 检查税码编号是否存在
	 * 
	 * @param taxCode
	 * @return
	 */
	public boolean checkTaxCode(String taxCode) {
		CommonLogger.info("检查税码编号是否存在,ReferenceSpecialService,ccheckTaxCodeheckSID");
		return Tool.CHECK.isEmpty(rsDAO.checkTaxCode(taxCode)) ? true : false;
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean taxCodeAdd(TaxCode t) {
		boolean isSuccess=false;
		CommonLogger.info("税码新增,ReferenceSpecialService,taxCodeAdd");
		String instUser=WebHelp.getLoginUser().getUserId();
		String updUser=WebHelp.getLoginUser().getUserId();
		t.setInstUser(instUser);
		t.setUpdUser(updUser);
		//如果为可抵扣税码则录入核算码百分比信息
		List<TaxCode> list = new ArrayList<TaxCode>();
		if("Y".equals(t.getDeductFlag())){
			for(int i=0;i<t.getPerCnts().length;i++){
				//将百分比转化为小数
				TaxCode taxCode=new TaxCode();
				taxCode.setPerCnt((t.getPerCnts()[i]).divide(BigDecimal.valueOf(100)));
				taxCode.setCglCode(t.getCglCodes()[i]);
				taxCode.setTaxCode(t.getTaxCode());
				list.add(taxCode);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			int a1=rsDAO.cglPerCntAdd(list);
			int a2=rsDAO.taxCodeAdd(t);
			if((a1==list.size())&&(a2>0)){
				isSuccess=true;
			}
		}
		else{
			int a3= rsDAO.taxCodeAdd(t);
			if(a3>0){
				isSuccess=true;
			}
		}
		return isSuccess;
	}

	public TaxCode preTaxCodeUpdate(String taxCode) {
		return rsDAO.preTaxCodeUpdate(taxCode);
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean taxCodeUpdate(TaxCode t) {
		CommonLogger.info("税码更新,ReferenceSpecialService,taxCodeUpdate");
		String updUser=WebHelp.getLoginUser().getUserId();
		t.setUpdUser(updUser);
		boolean isSuccess=false;
		List<TaxCode> list = new ArrayList<TaxCode>();
		//如果为可抵扣的税码则更新核算码百分比数据
		if("Y".equals(t.getDeductFlag())){
			//删除原来的数据
			rsDAO.delPerCntCgl(t);
			for(int i=0;i<t.getPerCnts().length;i++){
				//将百分比转化为小数
				TaxCode taxCode=new TaxCode();
				taxCode.setPerCnt((t.getPerCnts()[i]).divide(BigDecimal.valueOf(100)));
				taxCode.setCglCode(t.getCglCodes()[i]);
				taxCode.setTaxCode(t.getTaxCode());
				list.add(taxCode);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			int a1=rsDAO.cglPerCntAdd(list);
			int a2=rsDAO.taxCodeUpdate(t);
			if((a1==list.size())&&(a2>0)){
				isSuccess=true;
			}
		}
		else{
			int a3= rsDAO.taxCodeUpdate(t);
			if(a3>0){
				isSuccess=true;
			}
		}
		return isSuccess;
	}

	public List<TaxCode> perCntCglList(String taxCode) {
		// TODO Auto-generated method stub
		return rsDAO.perCntCglList(taxCode);
	}
}
