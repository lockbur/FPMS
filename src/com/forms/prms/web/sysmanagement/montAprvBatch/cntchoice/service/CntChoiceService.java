package com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.ImportUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.exceltool.exportthread.ExcelExportGenernalDeal;
import com.forms.prms.web.contract.initiate.domain.CntDevice;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.dao.ApprvDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.dao.CntChoiceDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.domain.CntChoiceBean;
import com.forms.prms.web.sysmanagement.user.service.UserInfoService;

@Service
public class CntChoiceService {
	@Autowired
	private CntChoiceDao dao;
	@Autowired
	private ExcelExportGenernalDeal exportDeal;	
	
	//获得类实例
	public static CntChoiceService getInstance(){
		return SpringUtil.getBean(CntChoiceService.class);
	}
	/**
	 * 查询省行列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<CntChoiceBean> getList(CntChoiceBean bean) {
		if ("01".equals(bean.getOrgType())) {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		}else {
			bean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		CntChoiceDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getList(bean);
	}

	/**
	 * 得到待处理和退回的合同集合
	 * 
	 * @param bean
	 * @return
	 */
	public List<CntChoiceBean> waitManageBackList(CntChoiceBean bean) {
		CntChoiceDao pageDao = PageUtils.getPageDao(dao, "waitManageBack");
		return pageDao.waitManageBackList(bean);
	}

	/**
	 * 得到复核通过的合同集合
	 * 
	 * @param bean
	 * @return
	 */
	public List<CntChoiceBean> checkPassList(CntChoiceBean bean) {
		CntChoiceDao pageDao = PageUtils.getPageDao(dao, "checkPass");
		return pageDao.checkPassList(bean);
	}

	/**
	 * 得到待复核的数据
	 * 
	 * @param bean
	 * @return
	 */
	public List<CntChoiceBean> waitCheckList(CntChoiceBean bean) {
		CntChoiceDao pageDao = PageUtils.getPageDao(dao, "waitCheck");
		return pageDao.waitCheckList(bean);
	}

	/**
	 * 合同勾选后的操作
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void choiceCnt(CntChoiceBean bean) {
		String batchNo = bean.getBatchNo();
		// 先更新勾选的合同状态为待审核
		// 得到一个合同号，子序号，监控指标的数组
		String[] cntNumSubIdMont = bean.getCntNumSubIdMont();
		// 循环去更新每一个合同号对应子序号的新监控指标代码名称
		for (int i = 0; i < cntNumSubIdMont.length; i++) {
			if (!Tool.CHECK.isEmpty(cntNumSubIdMont[i].trim())) {
				String cntNum = cntNumSubIdMont[i].split("<&%>")[0];// 得到合同号
				String montCodeOld = cntNumSubIdMont[i].split("<&%>")[1];// 得到旧监控指标
				String matrCode = cntNumSubIdMont[i].split("<&%>")[2];//得到物料
				String montNameNew = cntNumSubIdMont[i].split("<&%>")[3];// 得到监控指标代码名称
				String montCodeNew = cntNumSubIdMont[i].split("<&%>")[4];// 得到新监控指标代码
				CntChoiceBean newBean = new CntChoiceBean();
				newBean.setBatchNo(batchNo);
				newBean.setMontCodeNew(montCodeNew);
				newBean.setMontNameNew(montNameNew);
				newBean.setMontCodeOld(montCodeOld);
				newBean.setMatrCode(matrCode);
				newBean.setCntNum(cntNum);
				
				// 更新合同的状态以及新的监控指标代码名称
				dao.updateMontCodeName(newBean);
			}
			
		}
		// 执行改变合同主状态的方法
		dao.updateCntMain(batchNo);
	}

	public String userExport(CntChoiceBean bean) throws Exception {
		String sourceFileName = "合同勾选数据导出"+bean.getBatchNo();
		ImportUtil.createFilePath(new File(WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")));
		String destFile = WebHelp.getSysPara("EXCEL_DOWNLOAD_FILE_DIR")+"/"+sourceFileName+".xlsx";;
		//导出Excel操作
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("batchNo", bean.getBatchNo());
		return exportDeal.execute(sourceFileName, "CNT_CHOICE_EXPORT", destFile , map);
	}
	public List<CntDevice> exportExcute(String batchNo) {
		// TODO Auto-generated method stub
		return dao.exportExcute(batchNo);
	}
}
