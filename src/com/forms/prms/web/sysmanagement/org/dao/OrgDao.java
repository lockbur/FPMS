package com.forms.prms.web.sysmanagement.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.domain.Org;

@Repository
public interface OrgDao {
	
	public List<BaseBean> getOrgList(BaseBean baseBean);
	
	//机构控件查询
	public List<BaseBean> getOrgListTag(BaseBean baseBean);
	
	//设备类型控件查询
	public List<BaseBean> getDevTypeListTag(BaseBean baseBean);
	
	//责案中心控件查询
	public List<BaseBean> getCachedZtreeTag(BaseBean baseBean);
	
	public List<Org> getOrgAndChildren(Org org);
	
	public Org getOrg(Org org);
	
	public int updateOrg(Org org);
	
	public int deleteOrg(Org org);
	
	public int addOrg(Org org);
	
	public int updateDragOrg(Map<String,Object> map);
	
	public int isValidOrg(Org org);

	public void updateInteAddress(@Param("myAddress")String myAddress);

	public void insertInteAddress();

	public String getOrg1Code(@Param("rootId")String rootId);
}
