package com.forms.prms.web.sysmanagement.businessType.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.businessType.domain.BusinessType;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Repository
public interface BusinessTypeDao {

	public List<BusinessType> queryParamClassList(BusinessType param);

	public List<BusinessType> queryParameter(BusinessType Para);

	public BusinessType findPara(BusinessType Para);

	public void editPara(BusinessType Para);
	
	public void addSubmit(BusinessType Para);
	
	public Integer getMaxSort(BusinessType Para);

}
