package com.forms.prms.web.sysmanagement.provider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.provider.domain.ProviderActBean;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderBean;

@Repository
public interface ProviderDao {
	List<ProviderBean> getProvider(ProviderBean bean);// 根据条件查询出供应商集合

	public int add(ProviderBean bean);// 添加供应商信息

	public String getProviderCode();// 得到添加供应商时当前要用到的供应商编号

	public boolean addAct(Map<String, Object> map);// 批量的添加供应商所对应的银行号信息

	ProviderBean getInfoByCode(@Param("providerCode")String providerCode);// 查找某供应商的详细信息
	
	public List<ProviderBean> queryInfoByCode(@Param("providerCode")String providerCode,@Param("providerAddrCode")String providerAddrCode);// 

	List<ProviderActBean> getActList(String providerCode);// 得到某供应商编号下的所有银行号信息

	public boolean editsubmit(ProviderBean bean);// 修改提交供应商信息

	public boolean batchDelete(ProviderBean bean);// 删除某供应商编号下的所有银行号

	public void deleteProvider(ProviderBean bean);// 批量删除供应商信息

	public void deleteProviderAct(ProviderBean bean);// 批量删除一个或者多个供应商编号下的所有银行号

	public String findOuCode(@Param("userId") String userId);// 通过登录人ID查找所属财务中心代码

	List<String> getOuCodeList(@Param("userId") String userId);// 通过登录人ID找到ouCode集合

	List<String> getOuNameList(@Param("userId") String userId);// 通过登录人ID找到ouName集合

	ProviderBean getDetailByCode(String providerCode);// 通过供应商编号查找详细信息

	List<ProviderBean> searchProviderAct(ProviderBean bean);// 弹出框查找银行号信息

	public ProviderBean checkProviderName(String providerName);// 查找供应商名称是否存在

	public int addProvider(ProviderBean bean);// 弹出框添加供应商信息

	public boolean addActPop(Map<String, Object> map);// 批量的添加供应商所对应的银行号信息

	public ProviderBean findProvider(ProviderBean bean);// 通过供应商编号以及币别和账号查找信息

}
