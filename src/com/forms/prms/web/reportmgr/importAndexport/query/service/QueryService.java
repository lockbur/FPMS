package com.forms.prms.web.reportmgr.importAndexport.query.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.base.model.page.PageUtils;
import com.forms.platform.core.util.Tool;
import com.forms.platform.web.WebUtils;
import com.forms.prms.web.reportmgr.importAndexport.query.dao.QueryDao;
import com.forms.prms.web.reportmgr.importAndexport.query.domain.ImporExporCommonBean;

@Service
public class QueryService {

	@Autowired
	private QueryDao queryDao;
	
	/**
	 * 查询导出列表(有条件过滤参数)
	 * @param exportTask
	 * @return
	 */
	public List<ImporExporCommonBean> getExportReportList( ImporExporCommonBean exportTask )
	{
		QueryDao pageDao = PageUtils.getPageDao( queryDao );
		return pageDao.getExportTaskList(exportTask);
	}
	
	/**
	 * 查询单个导出记录
	 * @param exportTask
	 * @return
	 */
	public ImporExporCommonBean getExportReport( ImporExporCommonBean exportTask )
	{
		return queryDao.getExportTask(exportTask);
	}
	
	/**
	 * 查询导入列表(7-7：添加根据指定BatchNo批次查询相关Task)
	 * 		描述：	根据过滤条件查询符合的导入任务结果集
	 * 						导入模板字段值使用taskParams中的"impTempType"取值(须在调用导入组件时beans中赋值)，
	 * @param importTask	用于保存查询的过滤条件(SQL中体现)
	 * @param batchId		导入批次号，如果该传入参数为空，则查询所有导入任务；否则则指定查询某一导入批次下所属的导入任务(过滤条件：byBatchNo)
	 * @param queryByOrg1	该参数决定根据登录者还是登录者所属一级行进行过滤查询
	 * @return
	 */
	public List<ImporExporCommonBean> getImportReportList(ImporExporCommonBean importTask , String batchId , String queryByOrg1){
		QueryDao pageDao = PageUtils.getPageDao( queryDao );
		String uploadType;
		int errRowCount;
		//1.组织查询过滤参数(importTask、batchId、queryByOrg1),并进行符合的导入任务查询结果集
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("importTask", importTask);
		//1-2.如果传入参数batchId不为空，则认为需要精确查找某一条导入数据
		if(!"".equals(batchId) && batchId!=null){
			params.put("batchId", batchId);
		}
		//1-3.如果方法的输入参数queryByOrg1不为空时，根据登录者的一级行进行查询；否则根据Task任务的提交者进行查询；
		params.put("queryByOrg1", queryByOrg1);
		//1-4.执行查询，过滤参数包含在params中
		List<ImporExporCommonBean> impTaskListTemp = pageDao.getImportTaskList(params);
		
		//2.组合属性值，并为每个导入任务添加上传模板类型和错误行统计属性赋值
		for(int i=0;i<impTaskListTemp.size();i++){
			//2-1.处理导入模板类型的取值，该模板类型的值如果在调用Excel的execute时有指定(impTempType)，则取该值，否则根据taskId去查询
			ImporExporCommonBean impTaskBean = impTaskListTemp.get(i);
			JSONObject jsonObj = JSONObject.fromObject(impTaskBean.getTaskParams());
			if(!Tool.CHECK.isEmpty(jsonObj.get("impTempType"))){
				uploadType = (String)jsonObj.get("impTempType");
			}else{
				uploadType = queryDao.getUploadTypeByTaskId(impTaskBean.getTaskId());				//上传模板类型(用于后续查询的条件)
			}
			if (!Tool.CHECK.isEmpty(impTaskBean.getLoadType()) && "01".equals(impTaskBean.getLoadType())) {
				//2-2.这个上传类型是基础模块的  监控指标 审批链  预算
				errRowCount = queryDao.getErrRowCountByBatchNo(impTaskBean.getTaskBatchNo());
			}else {
				errRowCount = (null == queryDao.getErrRowCountByTaskId(impTaskBean.getTaskId()) ? 0 :queryDao.getErrRowCountByTaskId(impTaskBean.getTaskId()) );				//上传任务错误行数(按row进行分组统计)
			}
			impTaskBean.setTempType(uploadType);
			impTaskBean.setErrRowCount(errRowCount);
			//System.out.println("Time"+i+":Result:"+uploadType+"---"+errRowCount);
		}
		//3.返回最终的查询结果集
		return impTaskListTemp;
	}
	
	/**
	 * 查询单个导入记录
	 * @param importTask
	 * @return
	 */
	public ImporExporCommonBean getImportReport( ImporExporCommonBean importTask ){
		return queryDao.getImportTask(importTask);
	}
	
	/**
	 * @methodName getLastPageUri
	 * 		描述：	根据传递过来的uri和参数parameters组合成"返回"按钮的链接
	 * 				如果方法参数uriFuncId为空时，则根据参数uri查询FuncId，否则直接使用参数uriFuncId拼接返回路径
	 * @param uri			uri格式：reportmgr/dataMgr/datamigrate/todataimportpage.do
	 * @param uriFuncId		uri对应的FuncId值
	 * @param parameters	其他参数(参数格式应完整，如&paramA='aaa'$paramB='bbb'....)
	 * @return
	 */
	public String getLastPageUri(String uri , String uriFuncId , String parameters){
		//1.判断取值FuncId
		if(null == uriFuncId || "".equals(uriFuncId) ){
			uriFuncId = queryDao.getFuncIdByFuncUri(uri);
		}
		//2.拼接返回路径(root+uri+funcId，不带参数)
		String returnUrl = WebUtils.getRoot()+uri+"?VISIT_FUNC_ID="+uriFuncId;
		//3.拼接传入参数
		if(Tool.CHECK.isEmpty(parameters)){
			returnUrl += parameters;
		}
		//4.返回最终的上一页完整路径
		return returnUrl;
	}
	
}
