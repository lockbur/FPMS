package com.forms.prms.web.sysmanagement.parameter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.parameter.domain.Parameter;
import com.forms.prms.web.sysmanagement.parameter.domain.ParameterClass;

/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
@Repository
public interface ParameterDao {
	
	public List<ParameterClass> queryParamClassList(Parameter param);
	
	public List<Parameter> queryParameter(Parameter Para);
	
	public Parameter findPara(Parameter Para);
    
    public void editPara(Parameter Para);
    
    public void insertParaApplyLog(Parameter Para);
    
    public List<Parameter> selectPara();

	public List<Parameter> checkList(Parameter parameter);

	public void updatePara(Parameter para);

	public void refuse(Parameter para);
    
	public List<Parameter> queryApplyHisList(Parameter Para);

	public String getPara(@Param("paramVarName")String paramVarName);

	public Parameter getparaBean(String paramVarName);
	
	//检查应付账务系统按钮是否关闭
	public String checkDeadlineButton();
	
	public String getDeadlineDay();
	
	public String getDeadlineTime();
	
	public String checkDeadline(@Param("taskyyyymm")String taskyyyymm);
}
