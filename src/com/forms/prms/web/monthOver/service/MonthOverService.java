package com.forms.prms.web.monthOver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.amortization.fmsMgr.dao.FmsMgrDAO;
import com.forms.prms.web.amortization.fmsMgr.domain.FmsMgr;
import com.forms.prms.web.monthOver.dao.MonthOverDao;
import com.forms.prms.web.monthOver.domain.MonthOverBean;

@Service
public class MonthOverService {
	
	@Autowired
	private MonthOverDao mdao;
	@Autowired
	private FmsMgrDAO fdao;
	
	public List<MonthOverBean> list(MonthOverBean mb) {//月结状态列表
		CommonLogger.info("月结状态列表,MonthOverService,list");
		String org1Code = WebHelp.getLoginUser().getOrg1Code();	
		mb.setOrg1Code(org1Code);
		MonthOverDao pageDAO=PageUtils.getPageDao(mdao);
		return pageDAO.list(mb);			
}
	
	@Transactional(rollbackFor = Exception.class)
	public void insert(MonthOverBean mb){////插入一条月结结束的状态
	    String org1Code = WebHelp.getLoginUser().getOrg1Code();	
	    String instOper = WebHelp.getLoginUser().getUserId();
		mb.setOrg1Code(org1Code);
		mb.setInstOper(instOper);
		mb.setDataFlag("1");
		CommonLogger.debug("插入一条月结结束的状态,MonthOverService,insert");
		mdao.insert(mb);
	}
	
	public boolean checkOpenOrg1(){
		 String org1Code = WebHelp.getLoginUser().getOrg1Code();	
		 String r = mdao.checkOpenOrg1(org1Code);
		 if("".equals(r)||r == null){
			 return false;
		 }
		 else{
			 return true;
		 }
	}
	
	public String getMaxDataFlag(){	//查询最大时的月结状态
		//获取登陆用户所属一级行代码
	    String org1Code = WebHelp.getLoginUser().getOrg1Code();	
	    CommonLogger.info("获取当前月结状态！,MonthOverService, getMaxDataFlag()【org1Code:"+org1Code+"】");
		MonthOverBean mb1=mdao.getMaxDataFlag(org1Code);
		if (mb1==null) {//如果表为空，那么就插入一条月结结束的状态
			MonthOverBean mb2=new MonthOverBean();
			this.insert(mb2);
			return mb2.getDataFlag();
		}
		return mb1.getDataFlag();		
	}
		
	public String change(MonthOverBean mb) {//月结
		//获取登陆用户所属一级行代码
		String org1Code = WebHelp.getLoginUser().getOrg1Code();
	    mb.setOrg1Code(org1Code);	    		
		
		String dataFlag=this.getMaxDataFlag();//从数据库中查询最大时的月结状态
			
		if (dataFlag.equals(mb.getDataFlag())) {
			if ("0".equals(mb.getDataFlag())) {
				mb.setDataFlag("1");
			}else {
				mb.setDataFlag("0");
			}		
		}else if ("".equals(dataFlag)) {//数据为空时dataFlag为空字符串
			mb.setDataFlag("1");
		}else {//页面传上来的月结状态和数据库中最大时的月结状态不相等，就不进行月结
			return "error";
		}

		mb.setInstOper(WebHelp.getLoginUser().getUserId());//获取操作用户名
		CommonLogger.info("月结，操作人为："+WebHelp.getLoginUser().getUserId()+",MonthOverService,change");
		mdao.change(mb);
		
		return "ok";
	}
	
	/**
	 * 获取应附发票及预付款核销未上传的记录
	 * @return
	 */
	public FmsMgr getUnUploadRecode(){
		String org1Code = WebHelp.getLoginUser().getOrg1Code();//登录人所在的一级行号
		FmsMgr fmsMgr = new FmsMgr();
		String monDataFlag = this.getMaxDataFlag();
		CommonLogger.info("获取应附发票及预付款核销未上传的记录,登录人所在的一级行号为："+org1Code+",MonthOverService,getUnUploadRecode");
		fmsMgr = fdao.getPayCntInfo(org1Code,monDataFlag);
		return fmsMgr;
	}
	
	/**
	 * 校验冲销任务状态
	 * @return
	 */
	public boolean ajaxCheckProvision(){
		if("1".equals(mdao.ajaxCheckProvision(WebHelp.getLoginUser().getOrg1Code()))
				||"1".equals(mdao.ajaxCheckPP(WebHelp.getLoginUser().getOrg1Code()))){
			//存在未完成预提冲销任务
			return false;
		}
		return true;
	}
	
	/**
	 * 查看全国省行月结状态
	 * @return
	 */
	public List<MonthOverBean> getAllMonthOverFlag(MonthOverBean bean){
		CommonLogger.info("查看全国一级行月结状态,MonthOverService,getAllMonthOverFlag");
		return mdao.getAllMonthOverFlag(bean);
	}

}
