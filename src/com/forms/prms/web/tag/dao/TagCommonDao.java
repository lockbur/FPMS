package com.forms.prms.web.tag.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.tag.domain.TagCommon;

/**
 * author : wuqm <br>
 * date : 2013-11-4<br>
 * 
 */
@Repository
public interface TagCommonDao {
	public List<Map> getOptionList(TagCommon tagCommon);
	
	public List<Map> getList(Map<String,String> mapTo);
	/**
	 * 得到所有一级行
	 * @return
	 */
	public List<String> getOrg1List();

	public List<BaseBean> getDetailList(String org1Code);

	public String getParamterNameCount(@Param("paramterName")String paramterName);
}
