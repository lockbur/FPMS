package com.forms.prms.tool.constantValues;
/**
 * 协同管理工作流相关参数
 * @author wangzf
 * @date 2014-10-23
 */
public class CoopActivityValues {
	
	/**************************待更新的相关表名start*****************************/
	
	/**
	 * 需求信息表表名
	 */
	public final String REQUEST_MAIN_INFO = "REQUEST_MAIN_INFO";
	
	/**************************待更新的相关表名end********************************/
	
	/***************************流程定义key start******************************/
	
	/**
	 * 总行总体流程key
	 */
	public final String REQ_ZH_REQUEST_KEY = "req_zh_request";
	
	/**
	 * 应用分析处理总行需求流程key
	 */
	public final String REQ_ZHTASK_ANALY_KEY = "req_zhtask_analy";
	
	/**
	 * 提前启动补充流程key
	 */
	public final String REQ_ADVANCE_START_KEY = "req_advance_start";
	
	/**
	 * 任务排期及批次管理流程key
	 */
	public final String IMPL_SCH_APPLY_KEY = "impl_sch_apply";
	
	/**
	 * 应用团队处理流程key
	 */
	public final String REQ_APPTEAM_DEAL_KEY = "req_appteam_deal";
	
	/**
	 * 需求提出人处理流程key
	 */
	public final String REQ_REQDEPT_BACK_KEY = "req_reqdept_back";
	
	/**
	 * 需求团队处理流程key
	 */
	public final String REQ_REQDEPT_DEAL_KEY = "req_reqdept_deal";
	
	/**
	 * 资源团队审核流程key
	 */
	public final String REQ_RESTEAM_DEAL_KEY = "req_resteam_deal";
	
	/**
	 * 安全团队审核流程Key
	 */
	public final String REQ_SAVETEAM_DEAL_KEY = "req_saveteam_deal";
	
	/**
	 * 总工室审核流程key
	 */
	public final String REQ_ZGSTEAM_DEAL_KEY = "req_zgsteam_deal"; 
	
	/**
	 * 分行总体流程Key
	 */
	public final String REQ_FH_REQUEST_KEY = "req_fh_request";
	
	/*************************流程定义key end***********************************/
	
	/***************************工作流节点ID定义 start*****************************/
	
	/**
	 * 上级审批节点ID
	 */
	public final String DEPT_APPROVE = "dept_approve"; 
	
	/**
	 * 需求受理节点ID
	 */
	public final String REQ_APPEL = "req_appel";
	
	/**
	 * 提出人修改节点ID
	 */
	public final String CREATER_EDIT = "creater_edit";
	
	/**
	 * 需求提出人处理节点ID(子流程节点)
	 */
	public final String REQDEPT_BACK = "reqdept_back";
	
	/**
	 * 需求团队处理节点ID(子流程节点)
	 */
	public final String REQDEPT_DEAL = "reqdept_deal";
	
	/**
	 * 应用团队处理分析节点ID(子流程节点)
	 */
	public final String REQZHTASK_ANALY = "reqzhtask_analy";
	
	/**
	 * 任务排期及实施节点ID(子流程节点)
	 */
	public final String IMPLSCH_APPLY = "implsch_apply";
	
	/**
	 * 明确经办人节点ID
	 */
	public final String APPOINT_HANDLE = "appoint_handle";
	
	/**
	 * 转其他应用团队节点ID
	 */
	public final String REQDEPT_REGAIN = "reqdept_regain";
	
	/**
	 * 应用经办受理节点ID
	 */
	public final String HANDLE_APPEL = "handle_appel";
	
	/**
	 * 相关团队审核节点ID
	 */
	public final String TEAM_AUDIT = "team_audit";
	
	/**
	 * 实施建议节点ID
	 */
	public final String IMPL_ADVICE = "impl_advice";
	
	/**
	 * 立项审批结果节点ID
	 */
	public final String AUDIT_RESULT = "audit_result";
	
	/**
	 * 立项材料审核节点ID
	 */
	public final String STUFF_AUDIT = "stuff_audit";
	
	/**
	 * 补充立项材料节点ID
	 */
	public final String SUPPLY_STUFF = "supply_stuff";
	
	/**
	 * 应用经办处理节点ID
	 */
	public final String APPTEAM_DEAL = "appteam_deal";
	
	/**
	 * 提交技术方案节点ID
	 */
	public final String SUBMIT_SCHEME = "submit_scheme";
	
	/**
	 * 征求意见节点ID
	 */
	public final String CREATER_GETIDEA2 = "creater_getidea2";
	
	/**
	 * 需求不通过节点ID
	 */
	public final String REQ_UNPASS = "req_unpass";
	
	/**
	 * 需求提出人处理流程结束节点
	 */
	public final String END_EVENT1 = "endevent1";
	
	/**
	 * 征求意见节点ID
	 */
	public final String CREATER_GETIDEA1 = "creater_getidea1";
	
	/**
	 * 处理需求节点ID
	 */
	public final String REQ_DEAL = "req_deal";
	
	/**
	 * 需求讨论节点ID
	 */
	public final String REQ_DISCUSS = "req_discuss";
	
	/**
	 * 征求意见节点ID
	 */
	public final String REQ_TRANSMIT = "req_transmit";
	
	/**
	 * 分派经办人节点ID
	 */
	public final String APPOINT_TRANSACTER = "appoint_transacter";
	
	/**
	 * 资源配置节点ID
	 */
	public final String CONFIG_RESOURCE = "config_resource";
	
	/**
	 * 资源复核节点ID
	 */
	public final String CONFIG_AUDIT = "config_audit";
	
	/**
	 * 资源终审节点ID
	 */
	public final String SECOND_AUDIT = "second_audit";
	
	/**
	 * 总工资源配置意见节点ID
	 */
	public final String ZGRES_AUDIT = "zgres_audit";
	
	/**
	 * 总工资源审核确认节点ID
	 */
	public final String ZGRES_CONFIRM = "zgres_confirm";
	
	/**
	 * 安全审核意见节点ID
	 */
	public final String SAVEAUDIT_OPINION = "saveaudit_opinion";
	
	/**
	 * 主管审核节点ID
	 */
	public final String CHARGE_AUDIT = "charge_audit";
	
	/**
	 * 总工室安全审核意见节点ID
	 */
	public final String ZGSAVE_AUDIT = "zgsave_audit";
	
	/**
	 * 总工安全审核确认节点ID
	 */
	public final String ZGSAVE_CONFIRM = "zgsave_confirm";
	
	/**
	 * 审核意见节点ID
	 */
	public final String ZGSJG_AUDIT = "zgsjg_audit";
	
	/**
	 * 总工室审批确认节点ID
	 */
	public final String ZGSJG_CONFIRM = "zgsjg_confirm";
	
	/**************************工作流节点ID定义 end*****************************/
	
}
