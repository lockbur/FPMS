package com.forms.prms.web.sysmanagement.parameter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.EncryptUtil;
import com.forms.prms.tool.Values;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.init.SystemParamManage;
import com.forms.prms.web.sysmanagement.parameter.dao.ParameterDao;
import com.forms.prms.web.sysmanagement.parameter.domain.Parameter;
import com.forms.prms.web.sysmanagement.parameter.domain.ParameterClass;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Service
public class ParameterService {
	@Autowired
	private ParameterDao parameterDao;
	
	public List<ParameterClass> queryParameter(Parameter para) throws Exception
	{
		PageUtils.setPageSize(Values.DEFAULT_PAGE_SIZE);
//		ParameterDao pageDao = PageUtils.getPageDao(parameterDao);
		List<ParameterClass>   paramsClassList=new ArrayList<ParameterClass>();
		//查参数类别列表
		CommonLogger.info("查询参数类别列表,ParameterService,queryParameter");
		paramsClassList = parameterDao.queryParamClassList(para);
		
		List<Parameter>  paramList=new ArrayList<Parameter>();
		
		//查参数列表
		CommonLogger.info("查询参数列表,ParameterService,queryParameter");
		paramList = parameterDao.queryParameter(para);
		
		List<ParameterClass> pcl=new ArrayList<ParameterClass>();//新建一个List，用于删除元素
		for (ParameterClass parameterClass : paramsClassList)
		{
			 int numb = 0;
			 for (Parameter parameter : paramList)
			 {
				 if(parameterClass.getCategoryId().equals(parameter.getCategoryId()))
				 {
					 if("1".equals(parameter.getIsPwdType()))
					 {
						 String  paramValue=new EncryptUtil("").decipherNumStr(parameter.getParamValue());
						 parameter.setParamValue(paramValue);
						 String paramUpdateValue = parameter.getParamUpdateValue();
						 if(paramUpdateValue !=null&& ("".equals(paramUpdateValue)==false))
						 {
							 paramUpdateValue=new EncryptUtil("").decipherNumStr(parameter.getParamUpdateValue());
							 parameter.setParamUpdateValue(paramUpdateValue);
						 }
					 }
					 String paramUpdateValue = parameter.getParamUpdateValue();
					 if(paramUpdateValue != null&&("".equals(paramUpdateValue)==false))
					 {
						 numb++;
					 }
					 parameterClass.getParams().add(parameter);
					 parameterClass.setNumb(numb);
				 }
			 }
			 if(parameterClass.getParams().size()==0)
			 {
				 pcl.add(parameterClass);
			 }				 
		}
		paramsClassList.removeAll(pcl);
		
		return paramsClassList;
	}
	
	public List<Parameter> queryApplyHisList(Parameter para) throws Exception 
	{
		CommonLogger.info("查询参数历史列表,ParameterService,queryApplyHisList");
		ParameterDao pageDao = PageUtils.getPageDao(parameterDao);
		List<Parameter>  applyHisList=new ArrayList<Parameter>();
		applyHisList = pageDao.queryApplyHisList(para);
		for (Parameter param : applyHisList) 
		{
			if("1".equals(param.getIsPwdType()))
			 {
				 String  paramValue=new EncryptUtil("").decipherNumStr(param.getParamOrigValue());
				 param.setParamOrigValue(paramValue);
				 String  paramUpdateValue=new EncryptUtil("").decipherNumStr(param.getParamUpdateValue());
				 param.setParamUpdateValue(paramUpdateValue);
			 }
		}
		return applyHisList;
	}
	
	
	
	public Parameter findPara(Parameter para) throws Exception 
	{
		CommonLogger.debug("根据ParamVarName查找系统参数对象,参数名：,"+para.getParamVarName()+",ParameterService,findPara");
		Parameter paraUpdate=parameterDao.findPara(para);
		if ("1".equals(paraUpdate.getIsPwdType())) 
        {
        	String  paramValue=new EncryptUtil(null).decipherNumStr(paraUpdate.getParamValue());
        	paraUpdate.setParamValue(paramValue);
        	String paramUpdateValue = paraUpdate.getParamUpdateValue();
        	 if(paramUpdateValue !=null&& ("".equals(paramUpdateValue)==false))
			 {
				 paramUpdateValue=new EncryptUtil("").decipherNumStr(paraUpdate.getParamUpdateValue());
				 paraUpdate.setParamUpdateValue(paramUpdateValue);
			 }
        }
        return paraUpdate;
    }
	    
	@Transactional(rollbackFor = Exception.class)
    public void edit(Parameter para)
	{
		if ("1".equals(para.getIsPwdType()) && !Tool.CHECK.isEmpty( para.getParamUpdateValue().trim())) 
        {
        	String paramValue=null;
        	try 
        	{
				 paramValue=new EncryptUtil(null).encryptNumStr(para.getParamUpdateValue());
			} 
        	catch (Exception e) 
        	{
				e.printStackTrace();
			}
        	para.setParamUpdateValue(paramValue);
		}
		para.setApplyUserId(WebHelp.getLoginUser().getUserId());
		
        //更新SYS_PARAMETER 表信息
		CommonLogger.info("更新参数信息,ParameterService,edit");
		parameterDao.editPara(para);
		
		
		
        
        /*//刷新缓存
        Cache cache = EhcacheUtil.getBaseCache();
        Element ele = new Element(EhcacheUtil.prefix_key_sys+para.getParamVarName(), para.getParamValue());
        cache.put(ele);*/
    }
	
	/*public List<Parameter> queryParameter(Parameter para , boolean isPage){
		if(isPage){
			return this.queryParameter(para);
		}else{
			return this.parameterDao.queryParameter(para);  
		}
	}*/

	public List<Parameter> checkList(Parameter parameter) throws Exception 
	{
		CommonLogger.info("参数审批列表,ParameterService,checkList");
		List<Parameter>  paramsList =new ArrayList<Parameter>();
		ParameterDao pagedao=PageUtils.getPageDao(parameterDao);
		paramsList= pagedao.checkList(parameter);
		for (Parameter param : paramsList) 
		{
			if("1".equals(param.getIsPwdType()))
			 {
				 String  paramValue=new EncryptUtil("").decipherNumStr(param.getParamValue());
				 param.setParamValue(paramValue);
				 String  paramUpdateValue=new EncryptUtil("").decipherNumStr(param.getParamUpdateValue());
				 param.setParamUpdateValue(paramUpdateValue);
			 }
		}
		return paramsList;
	}

	/**
	 * 通过
	 * 
	 * */
	public void pass(Parameter para)
	{
		para.setApproveUserId(WebHelp.getLoginUser().getUserId());
		para.setApplyStatus("2");
		//插入SYS_PARAMETER_APPLY_LOG 表信息
		CommonLogger.info("更新参数log表,ParameterService,pass");
		parameterDao.insertParaApplyLog(para);
		//更新SYS_PARAMETER表信息
		CommonLogger.info("更新参数表,ParameterService,pass");
		parameterDao.updatePara(para);
		SystemParamManage.getInstance().init();
	}

	/**
	 * 不通过
	 * 
	 * */
	public void refuse(Parameter para) 
	{
		para.setApproveUserId(WebHelp.getLoginUser().getUserId());
		para.setApplyStatus("1");
		//插入SYS_PARAMETER_APPLY_LOG 表信息
		CommonLogger.info("更新参数log表,ParameterService,pass");
		parameterDao.insertParaApplyLog(para);
		CommonLogger.info("更新参数表,ParameterService,pass");
		parameterDao.refuse(para);
	}
	
	/**
	 * 检查应付账务系统按钮是否关闭
	 * @return
	 */
	public boolean checkDealineButton(){
		if("1".equals(parameterDao.checkDeadlineButton())){//1:关账 0：开账
		return false;
		}else{
			return true;
		}
	}
	
    public String getDeadlineDay(){
    	return parameterDao.getDeadlineDay();
    }
	
	public String getDeadlineTime(){
		return parameterDao.getDeadlineTime();
	}
	
	public boolean checkDeadline(String yyyymm) throws Exception{
		
		int day = Integer.parseInt("-"+getDeadlineDay());
		String task = yyyymm+"01 "+getDeadlineTime();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date date = df.parse(task);
		
		Calendar task_yyyymm = Calendar.getInstance();
		task_yyyymm.setTime(date);
		task_yyyymm.add(Calendar.MONTH, 1);
		task_yyyymm.add(Calendar.DAY_OF_MONTH,-1);
		task_yyyymm.add(Calendar.DAY_OF_MONTH,(day+1));
		String taskyyyymm = df.format(task_yyyymm.getTime());
		
		if("1".equals(parameterDao.checkDeadline(taskyyyymm))){
			return true; //最终时限大于系统时间，则通过
		}
		return false;
	}
	
}
