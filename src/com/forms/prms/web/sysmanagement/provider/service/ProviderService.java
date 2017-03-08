package com.forms.prms.web.sysmanagement.provider.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.provider.dao.ProviderDao;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderActBean;
import com.forms.prms.web.sysmanagement.provider.domain.ProviderBean;

@Service
public class ProviderService {
	@Autowired
	private ProviderDao dao;

	public List<ProviderBean> getProvider(ProviderBean bean) {
		bean.setOuCode(WebHelp.getLoginUser().getOuCode());
		bean.setIsAdmin(WebHelp.getLoginUser().getIsSuperAdmin());
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		ProviderDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.getProvider(bean);
	}

	/**
	 * 添加供应商信息
	 * 
	 * @param bean
	 * @return
	 */
	/*public boolean add(ProviderBean bean) {
		boolean j = false;
		if (Tool.CHECK.isBlank(bean.getProviderCode())) {
			String codeId = dao.getProviderCode();
			bean.setProviderCode(codeId);
		}
		int i = dao.add(bean);
		CommonLogger.info("添加IAP供应商基本信息,ProviderService,add");
		if (bean.getActNo().length > 0) {
			List<ProviderActBean> providerActList = new ArrayList<ProviderActBean>();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int k = 0; k < bean.getActNo().length; k++) {
				ProviderActBean providerAct = new ProviderActBean();
				providerAct.setProviderCode(bean.getProviderCode());
				providerAct.setActNo(bean.getActNo()[k]);
				providerAct.setActCurr(bean.getActCurr()[k]);
				if (bean.getActName()[k] != null && bean.getActName()[k] != "") {
					providerAct.setActName(bean.getActName()[k]);
				}
				if (bean.getActType()[k] != null && bean.getActType()[k] != "") {
					providerAct.setActName(bean.getActName()[k]);
				}
				if (bean.getBankCode()[k] != null && bean.getBankCode()[k] != "") {
					providerAct.setBankCode(bean.getBankCode()[k]);
				}
				if (bean.getBankInfo()[k] != null && bean.getBankInfo()[k] != "") {
					providerAct.setBankInfo(bean.getBankInfo()[k]);
				}
				if (bean.getBankName()[k] != null && bean.getBankName()[k] != "") {
					providerAct.setActName(bean.getActName()[k]);
				}
				if (bean.getActName()[k] != null && bean.getActName()[k] != "") {
					providerAct.setBankName(bean.getBankName()[k]);
				}
				if (bean.getBranchName()[k] != null && bean.getBranchName()[k] != "") {
					providerAct.setBranchName(bean.getBranchName()[k]);
				}
				if (bean.getBankArea()[k] != null && bean.getBankArea()[k] != "") {
					providerAct.setBankArea(bean.getBankArea()[k]);
				}
				providerAct.setIsMasterAct(bean.getIsMasterAct()[k]);
				providerActList.add(providerAct);
			}
			map.put("providerActList", providerActList);
			j = dao.addAct(map);
			CommonLogger.info("添加IAP供应商账号信息,ProviderService,add");
		}

		if (i != 1 || j == false) {
			return false;
		}
		return true;
	}*/

	public String getProviderCode() {
		CommonLogger.info("系统自动得到一个供应商编号,ProviderService,getProviderCode");
		return dao.getProviderCode();
	}

	/**
	 * 通过供应商编号查找所有信息
	 * 
	 * @param providerCode
	 * @return
	 */
	public ProviderBean getInfoByCode(String providerCode) {
		CommonLogger.info("查找供应商编号为【"+providerCode+"】的基本信息,ProviderService,getInfoByCode");
		return dao.getInfoByCode(providerCode);
	}
	
	/**
	 * 通过供应商编码
	 * @param providerCode
	 * @return
	 */
	public List<ProviderBean> queryInfoByCode(String providerCode,String providerAddrCode) {
		CommonLogger.info("查找【供应商编号:"+providerCode+"】【供应商地点编号:"+providerAddrCode+"】的基本信息,ProviderService,queryInfoByCode");
		return dao.queryInfoByCode(providerCode,providerAddrCode);
	}

	/**
	 * 查找供应商编号对应的银行信息
	 * 
	 * @param providerCode
	 * @return
	 */
	public List<ProviderActBean> getActList(String providerCode) {
		CommonLogger.info("查找供应商编号为【"+providerCode+"】的开户信息,ProviderService,getInfoByCode");
		ProviderDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.getActList(providerCode);
	}

	/**
	 * 提交更新供应商信息
	 * 
	 * @param ProviderBean
	 * @return
	 */
	//@Transactional(rollbackFor = Exception.class)
	/*public boolean editSubmit(ProviderBean bean) {
		// 先删除银行信息在更新供应商信息最后添加银行信息
		boolean flag1;
		boolean flag2;
		flag1 = dao.batchDelete(bean);
		flag2 = dao.editsubmit(bean);
		boolean flag3 = this.addAct(bean);
		return flag1 && flag2 && flag3;
	}*/

	/**
	 * 添加银行信息
	 * 
	 * @param bean
	 * @return
	 */
	/*public boolean addAct(ProviderBean bean) {
		boolean j = false;
		if (bean.getActNo().length > 0) {
			List<ProviderActBean> providerActList = new ArrayList<ProviderActBean>();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int k = 0; k < bean.getActNo().length; k++) {
				ProviderActBean providerAct = new ProviderActBean();
				providerAct.setProviderCode(bean.getProviderCode());
				providerAct.setActNo(bean.getActNo()[k]);
				providerAct.setActCurr(bean.getActCurr()[k]);
				providerAct.setActName(bean.getActName()[k]);
				providerAct.setActType(bean.getActType()[k]);
				providerAct.setBankCode(bean.getBankCode()[k]);
				providerAct.setBankInfo(bean.getBankInfo()[k]);
				providerAct.setBankName(bean.getBankName()[k]);
				providerAct.setBranchName(bean.getBranchName()[k]);
				providerAct.setIsMasterAct(bean.getIsMasterAct()[k]);
				providerAct.setBankArea(bean.getBankArea()[k]);
				providerActList.add(providerAct);
			}
			map.put("providerActList", providerActList);
			j = dao.addAct(map);
		}
		if (j == false) {
			return false;
		}
		return true;
	}*/

	/**
	 * 删除供应商信息
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteProvider(ProviderBean bean) {
		bean.setDelIdList(Arrays.asList(bean.getDelIds()));
		if (bean.getDelIdList() != null && bean.getDelIdList().size() > 0) {
			dao.deleteProvider(bean);
		}
	}

	/**
	 * 删除供应商对应的银行信息
	 * 
	 * @param bean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteProviderAct(ProviderBean bean) {
		bean.setDelIdList(Arrays.asList(bean.getDelIds()));
		if (bean.getDelIdList() != null && bean.getDelIdList().size() > 0) {
			dao.deleteProviderAct(bean);
		}
	}

	/**
	 * 通过登录人ID找到对应的ouCode集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getOuCodeList(String userId) {
		return dao.getOuCodeList(userId);
	}

	/**
	 * 通过登录人ID找到对应的ouName集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getOuNameList(String userId) {
		return dao.getOuNameList(userId);
	}

	/**
	 * 根据登录人ID找到ouCode
	 * 
	 * @param userId
	 * @return
	 */
	public String findOuCode(String userId) {
		return dao.findOuCode(userId);
	}

	/**
	 * 通过供应商编号查找所有信息
	 * 
	 * @param providerCode
	 * @return
	 */
	public ProviderBean getDetailByCode(String providerCode) {
		return dao.getDetailByCode(providerCode);
	}

	/**
	 * 弹出框查询供应商账号列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<ProviderBean> searchProviderAct(ProviderBean bean) {
		CommonLogger.info("弹出层供应商以及账户查询,ProviderService,searchProviderAct");
		String ouCode = WebHelp.getLoginUser().getOuCode();
		if (ouCode != null && ouCode != "") {
			bean.setOuCode(ouCode);
		}
		PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
		ProviderDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.searchProviderAct(bean);
	}

	/**
	 * 弹出框查询供应商列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<ProviderBean> searchProvider(ProviderBean bean) {
		CommonLogger.info("弹出层供应商基本信息查询,ProviderService,searchProvider");
		//得到登录人所在的ouCode		
		bean.setOuCode(WebHelp.getLoginUser().getOuCode());
		bean.setIsAdmin(WebHelp.getLoginUser().getIsSuperAdmin());
		bean.setOrg1Code(WebHelp.getLoginUser().getOrg1Code());
		PageUtils.setPageSize(10);// 设置弹出框的每页显示数据条数
		ProviderDao pagedao = PageUtils.getPageDao(dao);
		return pagedao.getProvider(bean);
	}

	/**
	 * 根据供应商名称查询对象
	 * 
	 * @param providerName
	 * @return
	 */
	public ProviderBean checkProviderName(String providerName) {
		return dao.checkProviderName(providerName);
	}

	/**
	 * 弹出框添加供应商信息
	 * 
	 * @param bean
	 * @return
	 */
	/*public boolean addPop(ProviderBean bean) {
		boolean j = false;
		String ouCode = WebHelp.getLoginUser().getOuCode();// 得到当前登录人的ouCode
		String ouName = WebHelp.getLoginUser().getOuName();// 得到当前登录人的ouName
		String instOper=WebHelp.getLoginUser().getUserId();//当前登录人的ID
		bean.setOuCode(ouCode);
		bean.setOuName(ouName);
		bean.setInstOper(instOper);
		int i = dao.addProvider(bean);
		CommonLogger.info("添加IAP供应商基本信息,ProviderService,addPop");
		if (bean.getActNo().length > 0) {
			List<ProviderActBean> providerActList = new ArrayList<ProviderActBean>();
			Map<String, Object> map = new HashMap<String, Object>();
			for (int k = 0; k < bean.getActNo().length; k++) {
				ProviderActBean providerAct = new ProviderActBean();
				providerAct.setProviderCode(bean.getProviderCode());
				providerAct.setActNo(bean.getActNo()[k]);
				providerAct.setActName(bean.getActName()[k]);
				providerAct.setActType(bean.getActType()[k]);
				providerAct.setBankName(bean.getBankName()[k]);
				providerAct.setBankCode(bean.getBankCode()[k]);
				providerActList.add(providerAct);
			}
			map.put("providerActList", providerActList);
			j = dao.addActPop(map);
			CommonLogger.info("添加IAP供应商的开户信息,ProviderService,addPop");
		}

		if (i != 1 || j == false) {
			return false;
		}
		return true;
	}*/

	/**
	 * 根据供应商编号查询对象
	 * 
	 * @param providerCode
	 * @return
	 */
	public ProviderBean findProvider(ProviderBean bean) {
		return dao.findProvider(bean);
	}

}
