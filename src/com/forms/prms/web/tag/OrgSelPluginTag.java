package com.forms.prms.web.tag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.forms.platform.core.util.Tool;

/**
 * 部门选择控件专用标签
 * @author LMM+HQQ
 */
public class OrgSelPluginTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	
	//后缀
	private String suffix;
	
	//后台取值js命名
	private String jsVarGetValue;
	
	//加载根节点
	private String rootNodeId;
	
	//加载根节点等级
	private String rootLevel;
	
	//初始化值
	private String initValue;
	
	//展示元素
	private boolean showInputFlag = true;
	
	//Class名称
	private String className;
	
	//change事件
	private String changeFun;
	
	//change事件参数值
	private String changeFunParams;
	
	//显示方式(文本框/按钮弹出Dialog)
	private boolean dialogFlag = false;	
	
	//选择类型(单选/复选)
	private boolean radioFlag = true;
	
	// 0：机构树  1：设备/其他
	private int treeType = 0;
	
	//button显示文本
	private String buttonName;
	
	//SQL查询语句
	private String querySql;
	
	//SQL查询参数传入
	private String queryParams;
	
	//查询到叶子的等级
	private String leafLevel;
	
	//是否返回半选值
	private boolean selHalfFlag = true;
	
	//是否可输入
	private boolean ableInputFlag = false;
	
	//是否允许用户模糊查询
	private boolean ableQuery = true;
	
	//是否只返回叶子节点
	private boolean leafOnlyFlag = false;
	
	//自定义JS定义名称
	private String jsVarName;
	
	//延迟加载
	private boolean relayLoadFlag = false;
	
	//父类是否可选择
	private boolean parentCheckFlag = true;
	
	//动态修改
	private boolean dynamicUpdateFlag = false;
	
	//同步异步加载
	private boolean asyncFlag = true;
	
	// 不加载js
	private boolean removeJs = false;
	
	// 触发元素 id或者name
	private String triggerEle;
	
	public String getTriggerEle() 
	throws Exception
	{
		return changeNull(this.triggerEle, null, true);
	}

	public void setTriggerEle(String triggerEle) {
		this.triggerEle = triggerEle;
	}

	public boolean isRemoveJs() {
		return removeJs;
	}

	public void setRemoveJs(boolean removeJs) {
		this.removeJs = removeJs;
	}

	public boolean isAsyncFlag() {
		return asyncFlag;
	}

	public void setAsyncFlag(boolean asyncFlag) {
		this.asyncFlag = asyncFlag;
	}

	public boolean isRelayLoadFlag() {
		return relayLoadFlag;
	}

	public void setRelayLoadFlag(boolean relayLoadFlag) {
		this.relayLoadFlag = relayLoadFlag;
	}
	
	public boolean isParentCheckFlag() {
		return parentCheckFlag;
	}

	public void setParentCheckFlag(boolean parentCheckFlag) {
		this.parentCheckFlag = parentCheckFlag;
	}

	public boolean isDynamicUpdateFlag() {
		return dynamicUpdateFlag;
	}

	public void setDynamicUpdateFlag(boolean dynamicUpdateFlag) {
		this.dynamicUpdateFlag = dynamicUpdateFlag;
	}

	public String getJsVarGetValue() throws Exception {
		return changeNull(jsVarGetValue, null, false);
	}

	public void setJsVarGetValue(String jsVarGetValue) {
		this.jsVarGetValue = jsVarGetValue;
	}

	public String getJsVarName() throws Exception {
		return jsVarName = changeNull( jsVarName , null, false);
	}

	public void setJsVarName(String jsVarName) {
		this.jsVarName = jsVarName;
	}

	public boolean isLeafOnlyFlag() {
		return leafOnlyFlag;
	}

	public void setLeafOnlyFlag(boolean leafOnlyFlag) {
		this.leafOnlyFlag = leafOnlyFlag;
	}

	public boolean isSelHalfFlag() {
		return selHalfFlag;
	}

	public void setSelHalfFlag(boolean selHalfFlag) {
		this.selHalfFlag = selHalfFlag;
	}

	public boolean isAbleInputFlag() {
		return ableInputFlag;
	}

	public void setAbleInputFlag(boolean ableInputFlag) {
		this.ableInputFlag = ableInputFlag;
	}

	public boolean isAbleQuery() {
		return ableQuery;
	}

	public void setAbleQuery(boolean ableQuery) {
		this.ableQuery = ableQuery;
	}

	public String getQueryParams() throws Exception {
		return changeNull(queryParams, null, true);
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getQuerySql() throws Exception {
		return changeNull(querySql, null, false);
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getLeafLevel() throws Exception {
		return changeNull(leafLevel, null, false);
	}

	public void setLeafLevel(String leafLevel) {
		this.leafLevel = leafLevel;
	}

	public int getTreeType() {
		return treeType;
	}

	public void setTreeType(int treeType) {
		this.treeType = treeType;
	}

	public String getButtonName() throws Exception {
		return changeNull(buttonName, null, false);
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getSuffix() throws Exception {
		return changeNull(suffix, "_", false);
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getRootNodeId() throws Exception {
		return changeNull(rootNodeId, null, true);
	}

	public void setRootNodeId(String rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

	public String getRootLevel() throws Exception {
		return changeNull(rootLevel, null, false);
	}

	public void setRootLevel(String rootLevel) {
		this.rootLevel = rootLevel;
	}


	public String getInitValue() throws Exception {
		return changeNull(initValue, null, true);
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public String getClassName() throws Exception {
		return changeNull(className, null, false);
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getChangeFun() throws Exception {
		return changeNull(changeFun, null, false);
	}

	public void setChangeFun(String changeFun) {
		this.changeFun = changeFun;
	}

	public String getChangeFunParams() throws Exception {
		return changeNull(changeFunParams, null, true);
	}

	public void setChangeFunParams(String changeFunParams) {
		this.changeFunParams = changeFunParams;
	}

	public boolean isShowInputFlag() {
		return showInputFlag;
	}

	public void setShowInputFlag(boolean showInputFlag) {
		this.showInputFlag = showInputFlag;
	}

	public boolean isDialogFlag() {
		return dialogFlag;
	}

	public void setDialogFlag(boolean dialogFlag) {
		this.dialogFlag = dialogFlag;
	}

	public boolean isRadioFlag() {
		return radioFlag;
	}

	public void setRadioFlag(boolean radioFlag) {
		this.radioFlag = radioFlag;
	}

	private String changeNull(String valStr, String beforeStr, boolean isReplace) throws Exception
	{
		if(null == valStr || "".equals(valStr.trim()))
		{
			return "";
		}
		else
		{
			if( !isReplace )
			{
				if( valStr.indexOf(",") != -1 ) throw new Exception("控件参数中不能包含逗号！！"); 
			}
			return changeNull(beforeStr, null, false) + (isReplace? valStr.trim().replaceAll(",", ";") : valStr.trim() );
		}
	}


	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException 
	{
		if (Tool.CHECK.isEmpty(this.rootLevel)) {
			this.rootLevel = "1";
		}
		JspWriter out = null;
		try {

			out = pageContext.getOut();
			StringBuilder outStr = new StringBuilder("");
			
			outStr.append("		<div id='orgSelectDiv_Plugin" + this.getSuffix() + 	"'>											");	
			outStr.append("			<span  id='orgSelectedPluginNodeSpan' name='orgSelectedPluginNodeSpan'></span>    			");
			outStr.append("			<input id='orgSelectedPluginNodeNm'   name='orgSelectedPluginNodeNm'  />    				");
			outStr.append("			<input id='orgSelectedPluginNodeButton'      type='button'/>								");
			outStr.append("			<input id='orgSelectedPluginNodeId'   name='orgSelPluginTagId' /> 							");
			outStr.append("				<div id='orgPluginZtreeDivDialog'>														");
			outStr.append("				<div id='orgPluginZtreeDiv' style='border: 1px solid #d9d6c3;' >               			");
			outStr.append("					<div align='center'>                                        						");
			outStr.append("						<label for='orgPluginSearchKey'>搜索：</label>									");
			outStr.append("						<input  id='orgPluginSearchKey' name='orgPluginSearchKey'/>  					");
			outStr.append("						<img  alt='点击切换搜索方式' title='点击切换搜索方式'/>							");
			outStr.append("					</div>                                                                      		");
			outStr.append("					<div style='background-color: white;height:300px;overflow:scroll;'>         		");
			outStr.append("						<ul id='zTree" + this.getSuffix() + "'  class='ztree' >                 		");
			outStr.append("									                                                            		");
			outStr.append("						</ul>                                                                   		");
			outStr.append("					</div>                                                                      		");
			outStr.append("				</div>                                                                          		");
			outStr.append("				</div>																					");
			
			if( !this.isRemoveJs() ) 
			{
			outStr.append("			<script type='text/javascript'>																");
			outStr.append("		  		var orgSelectDivObj" + this.getSuffix() + " = new OrgSelectDivObj(						");
			outStr.append("					'" + ((HttpServletRequest)pageContext.getRequest()).getContextPath() + "',			");
			outStr.append("					'" + this.getSuffix() + "',                     	");
			outStr.append("					'" + this.getRootNodeId() + "',                 	");
			outStr.append("					'" + this.getRootLevel() + "',                  	");
			outStr.append("					'" + this.getInitValue() + "',                  	");
			outStr.append("					'" + this.getJsVarGetValue() + "',              	");
			outStr.append("				 	" + this.isShowInputFlag() + ",                		");
			outStr.append("					'" + this.getClassName() + "',                  	");
			outStr.append("					'" + this.getChangeFun() + "',                  	");
			outStr.append("					'" + this.getChangeFunParams() + "',            	");
			outStr.append("				 	" + this.isRadioFlag() + " ,                   		");
			outStr.append("				 	" + this.isDialogFlag() + ",               			");
			outStr.append("					'" + this.getTreeType() + "',                  		");
			outStr.append("					'" + this.getButtonName() + "',                 	");
			outStr.append("					'" + this.getQuerySql() + "',                  		");
			outStr.append("					'" + this.getQueryParams() + "',                	");
			outStr.append("					'" + this.getLeafLevel() + "',                  	");
			outStr.append("				 	" + this.isSelHalfFlag() + " ,                 		");
			outStr.append("				 	" + this.isAbleInputFlag() + " ,               		");
			outStr.append("				 	" + this.isAbleQuery() + "  ,                  		");
			outStr.append("				 	" + this.isLeafOnlyFlag() + ",                 		");
			outStr.append("				 	" + this.isRelayLoadFlag() + ",                 	");
			outStr.append("				 	" + this.isParentCheckFlag() + ",              		");
			outStr.append("				 	" + this.isDynamicUpdateFlag() + ",             	");
			outStr.append("				 	" + this.isAsyncFlag() + ",             			");
			outStr.append("					'" + this.getTriggerEle() + "'                  	");
			outStr.append("				 );														");
			if( null != this.getJsVarName() && !"".equals(this.getJsVarName().trim()) )
			{
				outStr.append("		var " + this.getJsVarName() + " =  orgSelectDivObj" + this.getSuffix()+ ";" );
			}
			outStr.append("			$(document).ready(function(){  								");
			outStr.append("		 		orgSelectDivObj" + this.getSuffix() + ".tagInit(); 		");
			outStr.append("			});  														");		
			outStr.append("			</script>                                               	");	
			}
			
			outStr.append("		</div>                  										");
			
			out.print(outStr.toString());
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return super.doStartTag();
	}
}
