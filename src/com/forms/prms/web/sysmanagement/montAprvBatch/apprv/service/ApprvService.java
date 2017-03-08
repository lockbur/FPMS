package com.forms.prms.web.sysmanagement.montAprvBatch.apprv.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.Common;
import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.StringDeal;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.dao.ApprvDao;
import com.forms.prms.web.sysmanagement.montAprvBatch.apprv.domain.ApprvBean;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain.MontAprvType;
import com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.service.ImportService;

@Service
public class ApprvService {

	@Autowired
	private ApprvDao apprvDao;
	@Autowired
	private ImportService service;
    @Autowired
	private ConcurrentService concurrentService;
	public static ApprvService getInstance() {
		return SpringUtil.getBean(ApprvService.class);
	}

	/**
	 * @methodName shList desc 省行列表
	 * @param apprvBean
	 * @return
	 */
	public List<ApprvBean> getList(ApprvBean apprvBean) {
		if ("01".equals(apprvBean.getOrgType())) {
			apprvBean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		} else {
			apprvBean.setOrg21Code(WebHelp.getLoginUser().getOrg2Code());
		}
		ApprvDao pageDao = PageUtils.getPageDao(apprvDao);
		return pageDao.getList(apprvBean);
	}
	/**
	 * 审核通过
	 * 
	 * @param apprvBean
	 * @throws Exception
	 * 状态为E4的走这个
	 */
	public void audit(ApprvBean apprvBean) throws Exception {
		if ("01".equals(apprvBean.getProType())) {
			//监控指标
			if (MontAprvType.EXCEL_E4.equals(apprvBean.getStatus())) {
				auditE4(apprvBean);
			}
		}
	}
	//EXCEL复核
	@Transactional(rollbackFor = Exception.class)
	public void auditE4(ApprvBean apprvBean) throws Exception {
		if ("01".equals(apprvBean.getAuditFlag())) {
			//复核通过
			apprvDao.updateExcelStatus(apprvBean.getBatchNo(),
					MontAprvType.EXCEL_E4, MontAprvType.EXCEL_E5);
			try {
				//给新的监控指标生成监控指标代码
				buildMontCode(apprvBean);
				//插入带勾选合同信息
				apprvDao.intoSplit(apprvBean);
				int cntSplit = apprvDao.getCountCntSplit(apprvBean);
				if (cntSplit > 0) {
					// 说明有待勾选的合同 则更新CNT的状态
					apprvDao.updateCntStatus(apprvBean.getBatchNo(),
							MontAprvType.CNT_C0, MontAprvType.CNT_C1);
				}
			} catch (Exception e) {
				e.printStackTrace();
				CommonLogger.error("导入数据复核失败,批次号:" + apprvBean.getBatchNo());
				throw e;
			}
		}else {
			//复核不通过
			apprvDao.updateExcelStatusMemo(apprvBean.getBatchNo(), MontAprvType.EXCEL_E4, MontAprvType.EXCEL_E6,apprvBean.getMemo());
		}
	}
	public void updateBack(ApprvBean apprvBean){
		apprvDao.updateBack(apprvBean);
	}
	/**
	 * 审核C2的 也就是勾选合同的
	 * @param apprvBean
	 */
	@Transactional(rollbackFor = Exception.class)
	public void auditC2(ApprvBean apprvBean){
		if ("01".equals(apprvBean.getAuditFlag())) {
			//同意
			apprvBean.setStatus(MontAprvType.STATUS_3);
		}else {
			//退回
			apprvBean.setStatus(MontAprvType.STATUS_2);
		}
		//修改合同
		int n = 0;
		if (!Tool.CHECK.isBlank(apprvBean.getCntNum())) {// 如果cntNum合同号存在则是单个提交
			n += apprvDao.checkDeal(apprvBean);
		} else {// 批量提交
			if ("1".equals(apprvBean.getFlag())) {// 所有数据提交
				n += apprvDao.checkDealAll(apprvBean);
			} else {// 当页或部分数据提交
				List<ApprvBean> list = this.getApprvList(apprvBean);
				for (ApprvBean apBean : list) {
					apBean.setAuditMemo(apprvBean.getAuditMemo());
					n += apprvDao.checkDeal(apBean);
				}
			}
		}
		//更新为 合同勾选全部审核通过
		apprvDao.updateCntStatusC7(apprvBean);
		// 如果存在01,没有01状态了且存在有02 则更新汇总表为C3
		apprvDao.updateCntStatusC3(apprvBean);
		
	}

	/**
	 * 调用存储过程
	 * 
	 * @param apprvBean
	 * @throws Exception 
	 */
	public void starThread(ApprvBean apprvBean,String preStatus) throws Exception {
		// 先将状态改为 审核中
		try {
			CommonLogger.info("审核导入数据,批次号为"+apprvBean.getBatchNo()+",ApprvService,starThread");
			//如果preStatus为E5说明是 没有勾选合同 
			if (MontAprvType.EXCEL_E5.equals(preStatus)) {
				preStatus = MontAprvType.CNT_C0;
			}
			
			apprvDao.updateC4AndIpAddress(apprvBean.getBatchNo(), preStatus, MontAprvType.CNT_C4,InetAddress.getLocalHost().getHostName().toString());
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage().substring(0,150);
			apprvDao.updateCntStatusMemo(apprvBean.getBatchNo(), preStatus, MontAprvType.CNT_C6,msg);
			CommonLogger.error("审核导入数据的时候批次号为"+apprvBean.getBatchNo()+"审核时调用存储过程出错,ApprvService,starThread");
			throw new Exception("审核时调用存储过程出错");
		}
		
		ApprvThread thread = new ApprvThread();
		apprvBean.setStatus(MontAprvType.CNT_C4);
		apprvBean.setOrg21Code(WebHelp.getLoginUser().getOrg1Code());
		apprvBean.setInstOper(WebHelp.getLoginUser().getUserId());
		thread.setBean(apprvBean);
		thread.bStop = false;
		new Thread(thread).start();

	}

	/**
	 * @methodName getPath desc 数据导出
	 * 
	 *             根据传来的批次号获取下载的路径获取路径
	 * @param apprvBean
	 * @return
	 */

	public ApprvBean getPath(String batchNo) {
		return apprvDao.getPath(batchNo);
	}
	/**
	 * 生成监控指标
	 * @param apprvBean
	 */
	public void buildMontCode(ApprvBean apprvBean){
		if ("01".equals(apprvBean.getProType())) {
			// 监控指标
			// 重新生成监控指标代码 查找 所有 的新的监控指标代码
			List<ApprvBean> list = apprvDao.getAllMontName(apprvBean);
			// 查找这一类型的数据是否存在
			String code = apprvDao.getCode(apprvBean);
			String batchSeq = "";
			if (Tool.CHECK.isEmpty(code)) {
				batchSeq = "001";
			} else {
				String seq = Integer.parseInt(code) + 1 + "";
				batchSeq = StringDeal.buildFillString("Left", seq, "0",
						3);
			}
			if (null != list && list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {
					String seq = StringDeal.buildFillString("Left", i
							+ 1 + "", "0", 5);
					String montCode = list.get(i).getDataYear()
							+ list.get(i).getOrg21Code()
							+ list.get(i).getSubType() + batchSeq + seq;
					apprvDao.updateMontCode(apprvBean.getBatchNo(),
							list.get(i).getMontName(), montCode);
				}
			}
		}
	}

	public void execute(ApprvBean apprvBean){
		try {
			// 审核通过
			if ("03".equals(apprvBean.getInstType())) {
				// 这个是copy数据的审核
				apprvDao.auditCopy(apprvBean);
			} else {
				//校验互斥锁
				if ("01".equals(apprvBean.getProType())) {
					String memo = "";
					if ("11".equals(apprvBean.getSubType())) {
						memo = "一级行"+apprvBean.getOrg21Code()+"审批导入的专项包类型的监控指标（批次号："+apprvBean.getBatchNo()+"）时增加锁";
					}
					if ("12".equals(apprvBean.getSubType())) {
						memo = "一级行"+apprvBean.getOrg21Code()+"审批导入的省行统购资产类型的监控指标（批次号："+apprvBean.getBatchNo()+"）时增加锁";
					}
					if ("21".equals(apprvBean.getSubType())) {
						memo = "二级行"+apprvBean.getOrg21Code()+"审批导入的非省行统购资产类型的监控指标（批次号："+apprvBean.getBatchNo()+"）时增加锁";
					}
					if ("22".equals(apprvBean.getSubType())) {
						memo = "二级行"+apprvBean.getOrg21Code()+"审批导入的非专项包费用类型的监控指标（批次号："+apprvBean.getBatchNo()+"）时增加锁";
					}
					try {
						   concurrentService.checkAndAddLock(ConcurrentType.Concurrent_B,
								ConcurrentType.B1,apprvBean.getOrg21Code(),apprvBean.getInstOper(),memo);
					} catch (Exception e) {
						e.printStackTrace();
						apprvDao.updateCntStatusMemo(apprvBean.getBatchNo(),
								apprvBean.getStatus(), MontAprvType.CNT_C6,e.getCause().getMessage());
						throw e;
					}
				}
				// 调用存储过程
				apprvDao.audit(apprvBean);
				//删除锁
				try {
					if ("01".equals(apprvBean.getProType())) {
						concurrentService.delConcurrentLock(ConcurrentType.Concurrent_B, ConcurrentType.B1,apprvBean.getBatchNo());
					}
				} catch (Exception e) {
					CommonLogger.error("监控指标审批删除锁的时候出错，批次号为"+apprvBean.getBatchNo());
					e.printStackTrace();
					
				}
				
				
			}
		} catch (Exception e) {
			
			apprvDao.updateCntStatusMemo(apprvBean.getBatchNo(),
					apprvBean.getStatus(), MontAprvType.CNT_C6,"调用审核存储过程出错");
			e.printStackTrace();
		}finally{
			
		}

	}

	/**
	 * 复核合同勾选列表查询(待审核，退回，审核通过)
	 * 
	 * @param apprvBean
	 * @return
	 */
	public List<ApprvBean> cntChooseList(ApprvBean apprvBean, String pageKey) {
		ApprvDao pageDao = PageUtils.getPageDao(apprvDao, pageKey);
		return pageDao.cntChooseList(apprvBean);
	}

	/**
	 * 复核通过
	 * 
	 * @param apprvBean
	 * @return
	 */

	/**
	 * 批量提交时的list组装
	 * 
	 * @param apprvBean
	 * @return
	 */
	private List<ApprvBean> getApprvList(ApprvBean apprvBean) {
		List<ApprvBean> list = new ArrayList<ApprvBean>();
		if (!Tool.CHECK.isEmpty(apprvBean.getCntNumSubId())) {
			String[] cntNumSubId = apprvBean.getCntNumSubId();
			for (String str : cntNumSubId) {
				String[] cns = str.split(":");
				String cntNum = cns[0];
				String matrCode = cns[1];
				String montCodeOld = cns[2];
				ApprvBean apBean = new ApprvBean();
				apBean.setCntNum(cntNum);
				apBean.setMatrCode(matrCode);
				apBean.setMontCodeOld(montCodeOld);
				apBean.setBatchNo(apprvBean.getBatchNo());
				apBean.setStatus(apprvBean.getStatus());
				list.add(apBean);
			}
		}
		return list;
	}
	/**
	 * 得到最终的状态
	 * @param apprvBean
	 * @return
	 */
	public String getLastStatus(ApprvBean apprvBean) {
		return apprvDao.getLastStatus(apprvBean);
	}
}
