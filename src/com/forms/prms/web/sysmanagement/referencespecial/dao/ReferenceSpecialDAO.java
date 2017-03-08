package com.forms.prms.web.sysmanagement.referencespecial.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.referencespecial.domain.Reference;
import com.forms.prms.web.sysmanagement.referencespecial.domain.Special;
import com.forms.prms.web.sysmanagement.referencespecial.domain.TaxCode;

@Repository
public interface ReferenceSpecialDAO {
	
	/**
	 * 参考 列表
	 * @param r
	 * @return
	 */
	public List<Reference> referenceList(Reference r);
	
	/**
	 * 检查参考Id是否存在
	 * @param referenceId
	 * @return
	 */
	public Reference checkRID(String referenceId);
	
	/**
	 * 参考 新增
	 * @param r
	 */
	public int referenceAdd(Reference r);
	
	/**
	 * 参考详情
	 * @param referenceId
	 * @return
	 */
	public Reference referenceView(String referenceId);
	
	/**
	 * 参考 更新
	 * @return
	 */
	public int referenceUpd(Reference r);
	
	/**
	 * 专项 列表
	 * @param s
	 * @return
	 */
	public List<Special> specialList(Special s);
	
	/**
	 * 检查专项Id是否存在
	 * @param specialId
	 * @return
	 */
	public Special checkSID(String specialId);
	
	/**
	 * 专项 新增
	 * @param s
	 */
	public int specialAdd(Special s);
	
	/**
	 * 专项详情
	 * @param specialId
	 * @return
	 */
	public Special specialView(String specialId);
	
	/**
	 * 专项 更新
	 * @return
	 */
	public int specialUpd(Special s);

	public List<Special> specialPage(Special s);

	public List<Reference> referencePage(Reference r);

	public List<TaxCode> taxCodeList(TaxCode taxCode);

	public TaxCode checkTaxCode(String taxCode);

	public int taxCodeAdd(TaxCode t);

	public TaxCode preTaxCodeUpdate(String taxCode);

	public int taxCodeUpdate(TaxCode t);

	public int cglPerCntAdd(List<TaxCode> list);

	public List<TaxCode> perCntCglList(String taxCode);

	public void delPerCntCgl(TaxCode t);
	
		

}
