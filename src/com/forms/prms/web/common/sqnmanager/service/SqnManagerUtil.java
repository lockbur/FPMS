package com.forms.prms.web.common.sqnmanager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.prms.web.common.sqnmanager.dao.SqnManagerDAO;



@Service
public class SqnManagerUtil
{
	@Autowired
	private SqnManagerDAO dao;
	
	/**
	 *@param prefix 前缀
	 *@param sqnId  数据库函数RM_GET_COMMON_ID参数
	 *@param suffix 后缀
	 *@return  
	 * 
	 */
	public  String mGetNextSeq(String prefix,String sqnId,String suffix) 
	{
		return dao.getNextSeq(prefix,sqnId,suffix);
	}
	
	
	
}
