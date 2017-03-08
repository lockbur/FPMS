package com.forms.prms.web.amortization.tradeBackwash.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.tradeBackwash.dao.BackwashDAO;
import com.forms.prms.web.amortization.tradeBackwash.domain.BackwashBean;
import com.forms.prms.web.cluster.Lock;

@Service
public class BackwashService {

	@Autowired
	private BackwashDAO bDao;

	/**
	 * 获取可回冲的合同列表
	 * 
	 * @param org1Code
	 * @return
	 */
	public List<BackwashBean> getBackWashList(BackwashBean bean) {
		BackwashDAO pageDao = PageUtils.getPageDao(bDao);
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg1Code(org1code);
		CommonLogger.info("查询可回冲的合同列表("+org1code+")！,BackwashService,getBackWashList()");
		return pageDao.getBackWashList(bean);
	}

	/**
	 * 查询是否存在停留在预提复核状态的数据
	 * 
	 * @param bean
	 * @return
	 */
	public String getTradeStatus(BackwashBean bean) {
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg1Code(org1code);
		CommonLogger.info("查询是否存在停留在预提复核状态的数据("+org1code+")！,BackwashService,getTradeStatus()");
		return bDao.getTradeStatus(bean);
	}

	/**
	 * 获取当月预提待摊状态
	 * 
	 * @param org1Code
	 * @return
	 */
	public boolean getProPreStatus() {
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		String flag = bDao.getProPreStatus(org1code);
		CommonLogger.info("获取当月预提待摊状态("+org1code+")！,BackwashService,getProPreStatus()");
		if(flag == null || "04".equals(flag) || "03".equals(flag)){ 
			return false;//不通过
		}
		return true;
	}
	
	/**
	 * 查看当前任务是否为预提待摊任务
	 * @return
	 */
	public boolean getTaskTypeStatus(){
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		String flag = bDao.getTaskTypeStatus(org1code);
		CommonLogger.info("查看当前任务是否为预提待摊任务("+org1code+")！,BackwashService,getTaskTypeStatus()");
		if(flag == null || "".equals(flag)){ 
			return false;//不是
		}
		return true; //是
	}
	
	/**
	 *  预提待摊任务时间是否为系统当前年月
	 * @return
	 */
	public boolean isSysYYYYMM(){
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		String flag = bDao.isSysYYYYMM(org1code);
		CommonLogger.info(" 预提待摊任务时间是否为系统当前年月("+org1code+")！,BackwashService,isSysYYYYMM()");
		if(flag == null || "".equals(flag)){ 
			return false;//不是
		}
		return true; //是
	}
	
	
	/**
	 * 检查预提待摊上传文件是否已经回了校验文件 
	 * @return
	 */
	public boolean check33Upload(){
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		String flag = bDao.check33Upload(org1code);
		CommonLogger.info("检查预提待摊上传文件是否已经回了校验文件 ("+org1code+")！,BackwashService,check33Upload()");
		if(!"0".equals(flag)){ //存在未回盘的预提待摊文件
			return false;//不通过
		}
		return true;
	}
	
	
	
	/**
	 * 回冲处理
	 * @param org1Code
	 * @param cntNumList
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	@Lock(taskType = "'fmsupload'", taskSubType = "#org1Code", instOper = "#user", memo = "'预提待摊回冲'")
	public void backWash(String cntNumList,String org1Code, String user) throws Exception{
		CommonLogger.info("进行数据回冲处理(合同号："+cntNumList+"；用户："+user+")！,BackwashService,backWash()");
		String userId = WebHelp.getLoginUser().getUserId();
		String[] cntNums = cntNumList.split(",");
		BackwashBean b = new BackwashBean();
		b.setCntNums(cntNums);
		b.setOperUser(userId);
		//1.添加回冲合同信息
		bDao.addTradeBackWash(b);
		CommonLogger.info("将回冲合同添加进回冲表中(合同号："+cntNumList+"；用户："+user+")！,BackwashService,subBackWash()");
		//2.合同回冲预提待摊处理
	    bDao.backWash(org1Code, cntNumList);
	    CommonLogger.info("合同回冲预提待摊处理(合同号："+cntNumList+"；用户："+user+")！,BackwashService,subBackWash()");
	    
	    //3.回冲物料预算 add by sunxing 20151223
	    if(!"".equals(cntNums) && null != cntNums)
	    {
	    	for(String cntNum : cntNums)
		    {
	    		CommonLogger.info("预提待摊回冲，合同号：："+cntNum+" ，准备回冲预算...");
	    		Map<String, String> param = new HashMap<String, String>();
	    		param.put("cntNum", cntNum);
	    		param.put("retMsg", "");
	    		bDao.backWashBgt(param);
	    		//回冲预算失败，抛出异常
	    		if(null == param.get("retMsg") || "".contentEquals(param.get("retMsg")) || "0".contentEquals(param.get("retMsg")))
	    		{
	    			CommonLogger.error("预提待摊回冲，合同号："+cntNum+" ，回冲预算失败");
	    			throw new Exception("预提待摊回冲，合同号："+cntNum+" ，回冲预算失败");
	    		}
	    		CommonLogger.info("预提待摊回冲，合同号：："+cntNum+" ，回冲预算成功 ");
		    }
	    }
	}
	
	/**
	 * 获取回冲历史列表
	 * @param bean
	 * @return
	 */
	public List<BackwashBean> getBackWashHistory(BackwashBean bean){
		String org1code = WebHelp.getLoginUser().getOrg1Code();
		bean.setOrg1Code(org1code);
		//转换日期格式
		/*bean.setBefDate(DateUtil.formatDateStr(bean.getBefDate(), "YYYYMMDD"));
		bean.setAftDate(DateUtil.formatDateStr(bean.getAftDate(), "YYYYMMDD"));	*/
		BackwashDAO pageDao = PageUtils.getPageDao(bDao);
		CommonLogger.info("获取回冲历史列表(一级行："+org1code+")！,BackwashService,getBackWashHistory()");
		return pageDao.getBackWashHistory(bean);
	}


}
