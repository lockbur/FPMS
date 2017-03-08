package com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.DateUtil;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;
import com.forms.prms.web.sysmanagement.concurrent.service.ConcurrentService;
import com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.dao.DutyMergeDao;
import com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.domain.DutyMergeBean;

@Service
public class DutyMergeService {
	@Autowired
	private DutyMergeDao dao;
	@Autowired
	private ConcurrentService concurrentService;
	@Autowired
	private ConcurrentService concurrentService2;

	/**
	 * 经办查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<DutyMergeBean> mergeHandleList(DutyMergeBean bean) {
		CommonLogger.info("撤并列表查询，DutyMergeService，mergeHandleList");
		DutyMergeDao pageDao = PageUtils.getPageDao(dao);
		bean.setModiYyyymmS(DateUtil.formatDateStr(bean.getModiYyyymmS(),
				"YYYYMMDD"));
		bean.setModiYyyymmE(DateUtil.formatDateStr(bean.getModiYyyymmE(),
				"YYYYMMDD"));
		return pageDao.mergeHandleList(bean);
	}

	/**
	 * 经办新增的校验
	 * 
	 * @param bean
	 * @return
	 */
	public Map handleAddAjax(DutyMergeBean bean) {
		String type = bean.getType();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String typeName = "";
		if ("01".equals(bean.getType())) {
			typeName = "责任中心";
		} else {
			typeName = "机构";
		}
		bean.setCodeBefs(bean.getCodeBefArray().split(","));
		bean.setCodeCurs(bean.getCodeCurArray().split(","));
		String invalidDate = "";
		if (null != bean.getCodeBefs() && bean.getCodeBefs().length > 0
				&& null != bean.getCodeCurs() && bean.getCodeCurs().length > 0) {
			List<DutyMergeBean> list = dao.getInvalidDutys(bean);
			if (null != list && list.size() > 0) {
				List<String> list2 = new ArrayList<String>();
				String err = "";
				for (int i = 0; i < list.size(); i++) {
					if (!list2.contains(list.get(i).getInvalidDate())) {
						list2.add(list.get(i).getInvalidDate());
						err += "【" + typeName + "：" + list.get(i).getDutyCode()
								+ "失效时间:" + list.get(i).getInvalidDate() + "】";
					}
				}
				if (list2 != null && list2.size() > 1) {
					map.put("msg", "不能同时对多个批次进行撤并经办" + err);
					map.put("flag", false);
					return map;
				} else {
					// 到了这个地方就说明失效日期只有一个了
					invalidDate = list.get(0).getInvalidDate();
					// 说明只有一个失效日期 检查这个失效日期是最早的
					List<DutyMergeBean> list3 = dao.getNoMergeDuty(invalidDate,
							bean.getType());
					if (null != list3 && list3.size() > 0) {
						map.put("msg", "当前撤并的责任中心的失效日期是" + invalidDate
								+ ",在它之前还有未经办的撤并责任中心");
						map.put("flag", false);
						return map;
					}
				}

			} else {
				map.put("msg", "至少有一个撤并前的责任中心是失效状态!");
				map.put("flag", false);
				return map;
			}
		}
		map.put("invalidDate", invalidDate);
		map.put("flag", true);
		return map;
	}

	// public Map handleAddAjax(DutyMergeBean bean) {
	// String type = bean.getType();
	// Map<String, Object> map = new HashMap<String, Object>();
	// boolean flag = true;
	// String typeName="";
	// if ("01".equals(bean.getType())) {
	// typeName = "责任中心";
	// }else {
	// typeName ="机构";
	// }
	//
	// String msg=typeName;
	// if (!Tool.CHECK.isBlank(bean.getCodeBef2()) &&
	// !Tool.CHECK.isBlank(bean.getCodeCur2())) {
	// //1->2,2->3
	// if (bean.getCodeBef2().equals(bean.getCodeCur())) {
	// // 1,2 将会消失 全部撤并到3
	// //校验 1 或者2 之前没有 还没有进行撤并的责任中心
	// String count = dao.getNoMergeDuty(bean.getCodeBef());
	// if (!Tool.CHECK.isEmpty(count) && Integer.parseInt(count)>0) {
	// map.put("msg", "不能进行经办操作，在责任中心"+bean.getCodeBef()+"失效之前还有未经办的撤并责任中心。");
	// map.put("flag", false);
	// return map;
	//
	// }
	// String count2 = dao.getNoMergeDuty(bean.getCodeBef2());
	// if (!Tool.CHECK.isEmpty(count2) && Integer.parseInt(count2)>0) {
	// map.put("msg", "不能进行经办操作，在责任中心"+bean.getCodeBef2()+"失效之前还有未经办的撤并责任中心。");
	// map.put("flag", false);
	// return map;
	//
	// }
	// if (!existBatch(bean.getCodeBef(),type)) {
	// //1在批次表里不存在
	// msg += typeName+bean.getCodeBef()+"没有失效！";
	// }
	// if (!existBatch(bean.getCodeCur(), type)) {
	// msg+= typeName+bean.getCodeCur()+"没有失效!";
	// }
	// if (!existFndwrr(bean.getCodeCur2(), typeName)) {
	// msg+=typeName+bean.getCodeCur2()+"在交叉表里不存在";
	// }
	// }
	// //填了两个 2->3,1->2
	// if (bean.getCodeBef().equals(bean.getCodeCur2())) {
	// String count = dao.getNoMergeDuty(bean.getCodeBef2());
	// if (!Tool.CHECK.isEmpty(count) && Integer.parseInt(count)>0) {
	// map.put("msg", "不能进行经办操作，在责任中心"+bean.getCodeBef2()+"失效之前还有未经办的撤并责任中心。");
	// map.put("flag", false);
	// return map;
	// }
	// //1将会消失
	// if (!existBatch(bean.getCodeBef(),type)) {
	// msg += typeName+bean.getCodeBef()+"没有失效！";
	// }
	// if (!existFndwrr(bean.getCodeCur(), type)) {
	// msg+= typeName+bean.getCodeCur()+"在交叉表里不存在!";
	// }
	// if (!existFndwrr(bean.getCodeBef2(), typeName)) {
	// msg+=typeName+bean.getCodeBef2()+"没有失效！";
	// }
	// }
	// }
	// map.put("msg", msg);
	// map.put("flag", flag);
	// return map;
	// }
	// public Map handleAddAjax(DutyMergeBean bean) {
	// String type = bean.getType();
	// Map<String, Object> map = new HashMap<String, Object>();
	// boolean flag = true;
	// String typeName="";
	// if ("01".equals(bean.getType())) {
	// typeName = "责任中心";
	// }else {
	// typeName ="机构";
	// }
	//
	// String msg=typeName;
	// if (!Tool.CHECK.isBlank(bean.getCodeBef2()) &&
	// !Tool.CHECK.isBlank(bean.getCodeCur2())) {
	// if (bean.getCodeBef().equals(bean.getCodeCur2())) {
	// //校验codeBef和 codeCur 必须在fndwrr表存在， codeBef2在batch存在
	// if (!existFndwrr(bean.getCodeBef(),type)) {
	// msg+=bean.getCodeBef2()+"在交叉表中不存在";
	// flag = false;
	// }
	// if (!existFndwrr(bean.getCodeCur(),type)) {
	// msg+=bean.getCodeCur()+"在交叉表中不存在";
	// flag = false;
	// }
	// if (!existBatch(bean.getCodeBef2(),type)) {
	// msg+=bean.getCodeBef2()+"在"+typeName+"变更表中不存在";
	// flag = false;
	// }
	// //如果通过了上面的 说明 这四个责任中心是在同一个批次里 就校验codebef2之前是否有为完成撤并的
	// String count = dao.getNoMergeDuty(bean.getCodeBef2());
	// if (!Tool.CHECK.isEmpty(count) && Integer.parseInt(count)>0) {
	// map.put("msg", "不能进行经办操作，在责任中心"+bean.getCodeBef2()+"失效之前还有未经办的撤并责任中心。");
	// map.put("flag", false);
	// return map;
	//
	// }
	//
	// }else if (bean.getCodeCur().equals(bean.getCodeBef2())) {
	// //codeBef2 codeCur2在fundwrr存在 codeBef在batch存在
	// if (!existFndwrr(bean.getCodeBef2(),type)) {
	// msg+=bean.getCodeBef2()+"在交叉表中不存在";
	// flag = false;
	// }
	// if (!existFndwrr(bean.getCodeCur2(),type)) {
	// msg+=bean.getCodeCur2()+"在交叉表中不存在";
	// flag = false;
	// }
	// if (!existBatch(bean.getCodeBef(),type)) {
	// msg+=bean.getCodeBef()+"在"+typeName+"变更表中不存在";
	// flag = false;
	// }
	// String count = dao.getNoMergeDuty(bean.getCodeBef());
	// if (!Tool.CHECK.isEmpty(count) && Integer.parseInt(count)>0) {
	// map.put("msg", "不能进行经办操作，在责任中心"+bean.getCodeBef()+"失效之前还有未经办的撤并责任中心。");
	// map.put("flag", false);
	// return map;
	//
	// }
	// }
	// }else {
	// //校验 这个撤并关系 A->B A撤并之前没有未经办的撤并
	// String count = dao.getNoMergeDuty(bean.getCodeBef());
	// if (!Tool.CHECK.isEmpty(count) && Integer.parseInt(count)>0) {
	// map.put("msg", "不能进行经办操作，在责任中心"+bean.getCodeBef()+"失效之前还有未经办的撤并责任中心。");
	// map.put("flag", false);
	// return map;
	//
	// }
	// //codeBef 在batch存在codeCur 在fndwrr存在
	// if (!existBatch(bean.getCodeBef(),type)) {
	// msg+=bean.getCodeBef()+"在"+typeName+"变更表中不存在";
	// flag = false;
	// }
	// //去掉是因为防止出现第一个批次1-》2 第二个批次2-》3 那1-》2时的情况
	// // if (!existFndwrr(bean.getCodeCur(),type)) {
	// // msg+=bean.getCodeCur()+"在交叉表中不存在";
	// // flag = false;
	// // }
	// }
	// map.put("msg", msg);
	// map.put("flag", flag);
	// return map;
	// }
	/**
	 * 在交叉表中
	 * 
	 * @return
	 */
	public boolean existFndwrr(String dutyCode, String type) {
		return Integer.parseInt(dao.existFndwrr(dutyCode, type)) > 0;
	}

	public boolean existBatch(String dutyCode, String type) {
		return Integer.parseInt(dao.existBatch(dutyCode, type)) > 0;
	}

	/**
	 * 撤并经办新增
	 * 
	 * @param bean
	 *            codeBef2 和codeCur2 都是空 的那么数据库中就不存在1-2-3的关系就直接新增一条记录
	 *            如果不为空那么就要一条新增一条merge
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void handleAdd(DutyMergeBean bean) {
		CommonLogger.info("撤并经办新增,主键信息(经办日期:" + bean.getHandleDate() + ",类型："
				+ bean.getType() + ",撤并责任中心:" + bean.getCodeBef()
				+ "),DutyMergeService,handleAdd");
		String id = dao.getId(bean);
		bean.setId(id);
		bean.setHandleUser(WebHelp.getLoginUser().getUserId());
		if (null != bean.getCodeBefs() && bean.getCodeBefs().length > 0
				&& null != bean.getCodeCurs() && bean.getCodeCurs().length > 0) {
			dao.handleAdd(bean);
			for (int i = 0; i < bean.getCodeBefs().length; i++) {
				bean.setCodeBef(bean.getCodeBefs()[i]);
				bean.setCodeCur(bean.getCodeCurs()[i]);
				bean.setSeq(i + 1 + "");
				dao.handleDetailAdd(bean);
				// 反向更新batch表的撤并责任中心的状态；
				bean.setBatchStatus("00");
				bean.setStatus("01");
				dao.updateStatusForBatch(bean);
			}
		}
	}

	// public boolean handleAdd(DutyMergeBean bean) {
	// CommonLogger.info("撤并经办新增,主键信息(经办日期:"+bean.getHandleDate()+",类型："+bean.getType()+",撤并责任中心:"+bean.getCodeBef()+"),DutyMergeService,handleAdd");
	// String seq1="",seq2="",code="",code2="";//code是在batch表存在的那个 code2就是他撤并后的
	// if (!Tool.CHECK.isBlank(bean.getCodeBef2()) &&
	// !Tool.CHECK.isBlank(bean.getCodeCur2())) {
	// //不为空 要排序
	// if (bean.getCodeBef2() .equals(bean.getCodeCur())) {
	// // 1->2 ，2->3类型 那么后者应该先审批
	// seq1="2";
	// seq2="1";
	// code = bean.getCodeBef();
	// code2 = bean.getCodeCur();
	// }else if (bean.getCodeCur2().equals(bean.getCodeBef())) {
	// //1->2,3->1类型，那么前者先审批
	// seq1="1";
	// seq2="2";
	// code = bean.getCodeBef2();
	// code2=bean.getCodeCur2();
	// }
	// }else{
	// //为空只有一条记录
	// seq1="1";
	// code = bean.getCodeBef();
	// code2= bean.getCodeCur();
	// }
	// String id = dao.getId(bean);
	// bean.setId(id);
	// bean.setHandleUser(WebHelp.getLoginUser().getUserId());
	// bean.setCodeBef(bean.getCodeBef());
	// bean.setCodeCur(bean.getCodeCur());
	// bean.setSeq(seq1);
	// boolean flag1 =dao.handleAdd(bean)>0;
	// //将batch表反向更新状态
	// flag1 = dao.updateStatusForBatch(code,"01",code2);
	// boolean flag2=true;
	// if (!Tool.CHECK.isBlank(bean.getCodeBef2()) &&
	// !Tool.CHECK.isBlank(bean.getCodeCur2())) {
	// bean.setSeq(seq2);
	// bean.setCodeBef(bean.getCodeBef2());
	// bean.setCodeCur(bean.getCodeCur2());
	// flag2 = dao.handleAdd(bean)>0;
	// }
	// return flag1&&flag2;
	// }
	/**
	 * 经办删除
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void handleDel(DutyMergeBean bean) {
		CommonLogger.info("撤并经办删除,主键信息(经办日期:" + bean.getHandleDate() + ",类型："
				+ bean.getType() + ",撤并责任中心:" + bean.getCodeBef()
				+ "),DutyMergeService,handleDel");
		dao.updateStatusForDel(bean);
		dao.handleDel(bean);
	}

	/**
	 * 经办编辑页
	 * 
	 * @param bean
	 * @return
	 */
	public DutyMergeBean handlePreEdit(DutyMergeBean bean) {
		CommonLogger.info("撤并经办编辑页面,主键信息(经办日期:" + bean.getHandleDate() + ",类型："
				+ bean.getType() + ",撤并责任中心:" + bean.getCodeBef()
				+ "),DutyMergeService,handlePreEdit");
		return dao.getBean(bean);
	}

	/**
	 * 经办编辑或者批复
	 * 
	 * @param bean
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean edit(DutyMergeBean bean) {
		// if ("01".equals(bean.getMenuTag())) {
		// bean.setModiUser(WebHelp.getLoginUser().getUserId());
		// CommonLogger.info("撤并经办编辑执行,主键信息(经办日期:"+bean.getHandleDate()+",类型："+bean.getType()+",撤并责任中心:"+bean.getCodeBef()+"),DutyMergeService,edit");
		// }else if ("02".equals(bean.getMenuTag())) {
		// bean.setAprvUser(WebHelp.getLoginUser().getUserId());
		// CommonLogger.info("撤并复核执行,主键信息(经办日期:"+bean.getHandleDate()+",类型："+bean.getType()+",撤并责任中心:"+bean.getCodeBef()+"),复核状态为:"+bean.getStatus()+",DutyMergeService,edit");
		// }
		bean.setModiUser(WebHelp.getLoginUser().getUserId());

		// 更新批次表里的状态
		dao.updateStatusForDel(bean);
		// 删掉数据库中的明细数据
		dao.handleDel(bean);
		// 添加新的数据
		if (null != bean.getCodeBefs() && bean.getCodeBefs().length > 0
				&& null != bean.getCodeCurs() && bean.getCodeCurs().length > 0) {
			for (int i = 0; i < bean.getCodeBefs().length; i++) {
				bean.setCodeBef(bean.getCodeBefs()[i]);
				bean.setCodeCur(bean.getCodeCurs()[i]);
				bean.setSeq(i + 1 + "");
				dao.handleDetailAdd(bean);
				// 更新批次表的状态
				bean.setBatchStatus("00");
				bean.setStatus("01");
				dao.updateStatusForBatch(bean);
			}
		}
		return dao.edit(bean);
	}

	// ----------------------------------------------------复核--------------------------------------
	/**
	 * 撤并复核查询
	 * 
	 * @param bean
	 * @return
	 */
	public List<DutyMergeBean> mergeAprvList(DutyMergeBean bean) {
		CommonLogger.info("撤并复核列表查询，DutyMergeService，mergeAprvList");
		DutyMergeDao pageDao = PageUtils.getPageDao(dao);
		bean.setModiYyyymmS(DateUtil.formatDateStr(bean.getModiYyyymmS(),
				"YYYYMMDD"));
		bean.setModiYyyymmE(DateUtil.formatDateStr(bean.getModiYyyymmE(),
				"YYYYMMDD"));
		bean.setMenuTag("02");
		return pageDao.mergeHandleList(bean);
	}

	/*
	 * 8 查询明细页面
	 */
	public DutyMergeBean detail(DutyMergeBean bean) {
		CommonLogger.info("撤并明细页面,主键信息(经办日期:" + bean.getHandleDate() + ",类型："
				+ bean.getType() + ",撤并责任中心:" + bean.getCodeBef()
				+ "),DutyMergeService,detail");
		return dao.getBean(bean);
	}

	/**
	 * 关联信息
	 * 
	 * @param bean
	 * @return
	 */
	public List<DutyMergeBean> glxx(DutyMergeBean bean) {
		return dao.glxx(bean);
	}

	/**
	 * 复核提交
	 * 
	 * @param bean
	 * @return
	 * @throws com.sun.star.uno.Exception 
	 * @throws UnknownHostException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void aprv(DutyMergeBean bean) throws Exception {
		if("1".equals(bean.getDealFlag())){//同意
			bean.setCurStatus("05");
			bean.setIpAddress(InetAddress.getLocalHost().getHostName().toString());
			int affect = dao.update5IpAddress(bean);
			if (affect > 0) {
				try {
					bean.setAprvUser(WebHelp.getLoginUser().getUserId());
					//撤并操作
					bean.setStatus("02");
					//删除锁定表的信息
					dao.deleteLock(bean);
					dao.aprv(bean);
					dao.updateBatch(bean);
					List<DutyMergeBean> list = dao.getMergeList(bean);
					//调用存储过程
					if (null != list && list.size()>0) {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getCodeBef().equals(list.get(i).getCodeCur())) {
								continue;
							}else {
								if ("01".equals(list.get(i).getChangeType())) {
									dao.callDutyMerge(list.get(i).getCodeBef(),list.get(i).getCodeCur());
								}else if ("02".equals(list.get(i).getChangeType())) {
									dao.callOrgMerge(list.get(i).getCodeBef(),list.get(i).getCodeCur());
								}
							}
							
						}
						
					}
				} catch (Exception e) {
					//复核失败
					e.printStackTrace();
					bean.setStatus("04");
					bean.setMemo(e.getMessage());
					dao.aprv(bean);
					throw new Exception(e.getMessage());
				}
			}
		}else {
			//复核退回
			bean.setStatus("03");
			dao.aprv(bean);
		}
		
	
	}
	@Transactional(rollbackFor = Exception.class)
	public void mergeExecute(DutyMergeBean bean){
		
	}

	/**
	 * 撤并前责任中心列表
	 * 
	 * @param bean
	 * @return
	 */
	public List<DutyMergeBean> getMergeDutys(DutyMergeBean bean) {
		CommonLogger.info("撤并前责任中心列表查询，DutyMergeService，getMergeDutys");
		DutyMergeDao pageDao = PageUtils.getPageDao(dao);
		return pageDao.getMergeDutys(bean);
	}

	public boolean isHaveMerge(String code) {
		return Integer.parseInt(dao.isHaveMerge(code)) > 0;

	}

	public List<DutyMergeBean> selectGlgxList(DutyMergeBean bean2) {
		return dao.selectGlgxList(bean2);
	}

	/**
	 * 查看ti_download 责任中心同步是否成功
	 * 
	 * @return
	 */
	public String checkDownload12() {
		CommonLogger
				.info("查看ti_download 责任中心同步是否成功！,DutyMergeService,checkDownload12()");
		return dao.checkDownload12();
	}

	/**
	 * 查看 tb_fndwrr_batch 处理状态
	 * 
	 * @param org1Code
	 * @return
	 */
	public String checkBatchStatus() {
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
		return dao.checkBatchStatus(org1Code);
	}

	/**
	 * 复核的时候校验
	 * 
	 * @param bean
	 * @return
	 */
	public Map aprvAddAjax(DutyMergeBean bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DutyMergeBean> list1 = dao.getNoMergeDuty(bean.getInvalidDate(),
				bean.getType());
		if (null != list1 && list1.size() > 0) {
			map.put("msg", "不能进行复核操作，在 本批次的失效日期" + bean.getInvalidDate()
					+ "之前还有未经办的撤并责任中心。请先对其进行经办，复核。");
			map.put("flag", false);
			return map;
		} else {
			List<DutyMergeBean> list = dao.getNoAprvDuty(bean.getInvalidDate());
			if (null != list && list.size() > 0) {
				map.put("msg", "不能进行复核操作，在 本批次的失效日期" + bean.getInvalidDate()
						+ "之前还有未复核完成的撤并责任中心。请先对其进行经办。");
				map.put("flag", false);
				return map;
			} else {
				map.put("flag", true);
			}
		}

		return map;
	}

	/**
	 * 检查编码是否存在
	 * 
	 * @param codeCur
	 * @param type
	 * @return
	 */
	public boolean existsCode(String code, String type) {
		return dao.existsCode(code, type) > 0;
	}

	/**
	 * 待处理的撤并日期
	 * 
	 * @return
	 */
	public String getDealDate() {
		CommonLogger.info("机构撤并页面的撤并日期,DutyMergeService,getDealDate");
		return dao.getDealDate();
	}

	/**
	 * 以下列表的数据日期
	 * 
	 * @return
	 */
	public DutyMergeBean getListDate() {
		CommonLogger.info("机构撤并页面的可撤并日期,DutyMergeService,getListDate");
		return dao.getListDate();
	}

	/**
	 * 查询经办列表
	 * 
	 * @param batchNo
	 * @return
	 */
	public List<DutyMergeBean> list(String batchNo) {
		return dao.list(batchNo);
	}

	/**
	 * 通过撤并后的查询
	 * 
	 * @param dutyMergeBean
	 * @return
	 */
	public DutyMergeBean queryFndwrr(DutyMergeBean dutyMergeBean) {
		return dao.queryFndwrr(dutyMergeBean);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateFndChangeDetail(DutyMergeBean dutyMergeBean) {
		CommonLogger.info("机构撤并保存撤并后的代码,DutyMergeService,updateFndChangeDetail");
		return dao.updateFndChangeDetail(dutyMergeBean);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateFndChange(DutyMergeBean dutyMergeBean) {
		CommonLogger.info("机构撤并提交时更新时间戳,DutyMergeService,updateFndChange");
		return dao.updateFndChange(dutyMergeBean);
	}

	public List<DutyMergeBean> queryFndwrrChnageDetail(String batchNo) {
		CommonLogger.info("查询机构撤并明细,DutyMergeService,queryFndwrrChnageDetail");
		return dao.queryFndwrrChnageDetail(batchNo);
	}

	public void updateStatus(DutyMergeBean bean) {
		CommonLogger.info("机构撤并更新状态,DutyMergeService,updateStatus");
		bean.setPreStatus(bean.getStatus());
		bean.setCurStatus("01");
		 dao.updateStatus(bean);
	}

	public DutyMergeBean getFndwrrChangeInfo(String batchNo) {
		CommonLogger.info("得到机构撤并信息,DutyMergeService,getFndwrrChangeInfo");
		return dao.getFndwrrChangeInfo(batchNo);
	}

	/**
	 * 查询复核列表
	 * @param batchNo
	 * @return
	 */
	public List<DutyMergeBean> checkList(String batchNo) {
		CommonLogger.info("查询复核列表,DutyMergeService,checkList");
		return dao.checkList(batchNo);
	}

	public DutyMergeBean queryDetail(DutyMergeBean bean) {
		return dao.queryDetail(bean);
	}
	@Transactional(rollbackFor = Exception.class)
	public void ajaxSave(DutyMergeBean dutyMergeBean) {
		CommonLogger.info("ajax保存撤并后的代码,DutyMergeService,ajaxSave");
		dao.updateFndChangeDetail(dutyMergeBean);
		dao.updateFndChange(dutyMergeBean);
		
		dao.updateFndBatch(dutyMergeBean);
	}

	public DutyMergeBean getDutyBean(DutyMergeBean dutyMergeBean) {
		// TODO Auto-generated method stub
		return dao.getDutyBean(dutyMergeBean);
	}

}
