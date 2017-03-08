package com.forms.prms.web.sysmanagement.businessType.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.Values;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.businessType.dao.BusinessTypeDao;
import com.forms.prms.web.sysmanagement.businessType.domain.BusinessType;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Service
public class BusinessTypeService {
	@Autowired
	private BusinessTypeDao businessTypeDao;
	
	public List<BusinessType> queryParameter(BusinessType para) throws Exception
	{
		PageUtils.setPageSize(Values.DEFAULT_PAGE_SIZE);
//		businessTypeDao pageDao = PageUtils.getPageDao(businessTypeDao);
		List<BusinessType>   paramsClassList=new ArrayList<BusinessType>();
		//查业务类别列表
		CommonLogger.info("查询业务类别列表,BusinessTypeService,queryParameter");
		paramsClassList = businessTypeDao.queryParamClassList(para);
		
		List<BusinessType>  paramList=new ArrayList<BusinessType>();
		
		//查参数列表
		CommonLogger.info("查询参数列表,BusinessTypeService,queryParameter");
		paramList = businessTypeDao.queryParameter(para);
		
		List<BusinessType> pcl=new ArrayList<BusinessType>();//新建一个List，用于删除元素
		for (BusinessType parameterClass : paramsClassList)
		{
			 for (BusinessType parameter : paramList)
			 {
				 if(parameterClass.getCategoryId().equals(parameter.getCategoryId()))
				 {
					 parameterClass.getParams().add(parameter);
				 }
			 }
			 if(parameterClass.getParams().size()==0)
			 {
				 pcl.add(parameterClass);
			 }	
		}
		paramsClassList.removeAll(pcl);
		
		return paramsClassList;
	}
	
	public BusinessType findPara(BusinessType para) throws Exception 
	{
		CommonLogger.debug("根据ParamName查找系统参数对象,参数名：,"+para.getParamName()+",BusinessTypeService,findPara");
		BusinessType paraUpdate=businessTypeDao.findPara(para);
        return paraUpdate;
    }
	    
	@Transactional(rollbackFor = Exception.class)
    public void edit(BusinessType para) 
	{
		para.setApplyUserId(WebHelp.getLoginUser().getUserId());
		
        //更新SYS_PARAMETER 表信息
		CommonLogger.info("更新select表信息,BusinessTypeService,edit");
		businessTypeDao.editPara(para);
		
    }
	
	/**
	 * @methodName addSubmit
	 * desc  
	 * 新增提交
	 * @param rollInfoBean
	 */
	public void addSubmit(BusinessType para){
		CommonLogger.info("业务类型新增分类ID为"+para.getCategoryId()+"参数名称为"+para.getParamName()+"参数值为"+para.getParamValue()+"的信息，BusinessTypeService，addSubmit");
		Integer sortFlag = businessTypeDao.getMaxSort(para);
		para.setSortFlag(sortFlag);
		businessTypeDao.addSubmit(para);
	}
	
}
