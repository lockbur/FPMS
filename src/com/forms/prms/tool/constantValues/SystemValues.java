package com.forms.prms.tool.constantValues;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: SystemValues 
 * @Description: 系统常用的常量类
 * @author RYAN
 * @date 2014-4-9
 *
 */
public class SystemValues {

	/**
	 * 成功别名
	 */
	public final String SUCCESS = "SUCCESS";
	
	/**
	 * 失败别名
	 */
	public final String FAILURE = "FAILURE";
	
	/**
	 * 引用需求登记详情时,公共页面bean的别名
	 */
	public final String BEAN_NAME = "reqRegistInfo";
	
	/**
	 * 实施阶段流程定义KEY
	 */
	public final String IMPLEMENT_STAGE_KEY = "impl_new_proj";
	
	/**
	 * 实施阶段(需求登记)流程定义KEY
	 */
	public final String IMPLEMENT_REQ_REGIST_KEY = "impl_req_regist";
	
	/**
	 * 实施阶段(实施跟踪)流程定义KEY
	 */
	public final String IMPL_IMPLEMENT_TRACK_KEY = "impl_track_process";
	
	/**
	 * 实施阶段(重排期审批)流程定义KEY
	 */
	public final String IMPL_APPLY_BACKSCHED = "impl_apply_backsched";
	
	/**
	 * 实施跟踪表
	 */
	public final String IMPL_IMPLEMENT_TRACK = "IMPL_IMPLEMENT_TRACK";
	
	/**
	 * 重排期申请表
	 */
	public final String IMPL_IMPLEMENT_APPLYBACK = "IMPL_IMPLEMENT_APPLYBACK";
	
	/**
	 * 实施阶段待办事项菜单id
	 */
	public final String IMPL_WAIT_ID = "0502";
	
	/**
	 * 立项登记节点ID
	 */
	public final String ACT_PROJ_REGIST = "proj_regist";
	
	/**
	 * 需求登记节点ID
	 */
	public final String ACT_REQ_REGIST = "req_regist";
	
	/**
	 * 需求确认节点ID
	 */
	public final String ACT_REQ_COMFIRM = "req_comfirm";
	
	/**
	 * 需求分配节点ID
	 */
	public final String ACT_REQ_ASSIGN = "req_assign";
	
	/**
	 * 需求分配节点ID
	 */
	public final String ACT_SCHEDULE_APPLY = "schedule_apply";
	
	/**
	 *  需求排期节点ID
	 */
	public final String ACT_REQ_SCHEDULE = "req_schedule";
	
	/**
	 *  实施跟踪节点ID
	 */
	public final String ACT_IMPL_GRACE = "impl_grace";
	
	/**
	 *  任务结束节点ID
	 */
	public final String ACT_TASK_FINISH = "task_finish";
	
	/**
	 *  进入需求登记详细公共页面时,默认先显示哪个tab
	 */
	public final String SELECTED_TABS_0 = "0"; //需求登记基本信息
	public final String SELECTED_TABS_1 = "1"; //任务信息//审核意见
	public final String SELECTED_TABS_2 = "2"; //排期信息
	public final String SELECTED_TABS_3 = "3"; 
	
	/**
	 *  提交测试版本
	 */
	public final String ACT_SUBMIT_TEST_VERSION = "submit_test_version";
	
	/**
	 *  提交正式版本
	 */
	public final String ACT_SUBMIT_OFFICIAL_VERSION = "submit_official_version";
	
	/**
	 *  接收测试版本
	 */
	public final String ACT_ACCEPT_TEST_VERSION = "accept_test_version";
	
	/**
	 * 提交测试报告
	 */
	public final String ACT_SUBMIT_TEST_REPORT = "submit_test_report";
	
	/**
	 * 接收正式版本
	 */
	public final String ACT_ACCEPT_OFFICIAL_VERSION = "accept_official_version";
	
	/**
	 * 发布正式版本
	 */
	public final String ACT_PUBLISH_OFFICIAL_VERSION = "publish_official_version";
	
	/**
	 * 重排期审核节点ID
	 */
	public final String ACT_SECOND_SCHEDULE_AUDIT = "second_schedule_audit";
	
	/**
	 * 用户角色CODE与所属团队对应关系
	 */
	public final static Map<String,String> roleCodeMap = new HashMap<String,String>();
	
	static{
		roleCodeMap.put("SOFTWARE_CENTER", "R96");
		roleCodeMap.put("TEST_CENTER", "R97");
		roleCodeMap.put("MESSAGE_CENTER", "R98");
		roleCodeMap.put("PROJECT_CENTER", "R99");
	}
	
	/**************方案阶段******************/
	/**
	 * 业务类型
	 */
	public final String BP_BUS_TYPE_1= "1"; 
	public final String BP_BUS_TYPE_2= "2"; 
	public final String BP_BUS_TYPE_3= "3"; 
	public final String BP_BUS_TYPE_4= "4"; 
	
	/**
	 * 意见表处理状态 , 0 退回/不同意
	 */
	public final String BP_DEAL_RESULT_0= "0"; //0 退回/不同意 
	/**
	 * 意见表处理状态 , 1同意/通过
	 */
	public final String BP_DEAL_RESULT_1= "1"; //1同意/通过
	/**
	 * 意见表处理状态 ,  -1 不存在审核状态
	 */
	public final String BP_DEAL_RESULT_00= "-1"; //-1 不存在审核状态
	
	/**
	 * 承担单位类型 , 0自主(软件中心)
	 */
	public final String BP_UNIT_TYPE_0= "0"; 
	
	/**
	 * 承担单位类型 , 1外包(外部厂商)
	 */
	public final String BP_UNIT_TYPE_1= "1"; 
	
	/**
	 * 方案阶段-立项受理流程定义key
	 */
	public final String BP_NEW_PROJ = "bp_new_proj";
	
	/**
	 * 方案阶段-需求变更流程定义key
	 */
	public final String BP_REQ_CHANGE = "bp_req_change";
	
	/**
	 * 方案阶段-技术方案变更流程定义key
	 */
	public final String BP_TECH_CHANGE = "bp_tech_change";
	
	/**
	 * 方案阶段-压力测试指标流程定义key
	 */
	public final String BP_PRES_TEST = "bp_pres_test";
	
	/**
	 * 方案阶段-资源配置定义key
	 */
	public final String BP_RES_CONFIG = "bq_res_config";
	
	/**
	 * 方案阶段待办事项菜单id
	 */
	public final String BP_WAIT_ID = "0601";
	
	/**
	 * 配置审核表
	 */
	public final String BLPR_RES_CONFIG_AUTH = "BLPR_RES_CONFIG_AUTH";
	
	/**
	 * 方案阶段-明确主办人节点ID
	 */
	public final String ACT_APPOINT_HANDLE = "appoint_handle";
	
	/**
	 * 方案阶段-提出意见节点ID
	 */
	public final String ACT_RAISE_ADVISE = "raise_advise";
	
	/**
	 * 方案阶段-处理需求节点ID
	 */
	public final String ACT_HANDLE_REQUIRE = "handle_require";
	
	/**
	 * 方案阶段-复议节点ID
	 */
	public final String ACT_SECOND_DISCUSS = "second_discuss";
	
	/**
	 * 方案阶段-提出技术方案建议书节点ID
	 */
	public final String ACT_SUBMIT_SCHEME = "submit_scheme";
	
	/**
	 * 方案阶段-项目团队审核节点ID
	 */
	public final String ACT_PROJECT_TEAM = "project_team";
	
	/**
	 * 方案阶段-总工事审核节点ID
	 */
	public final String ACT_CHIEF_TEAM= "chief_team";
	
	/**
	 * 方案阶段-安全团队审核节点ID
	 */
	public final String ACT_SAFE_TEAM = "safe_team";
	
	/**
	 * 方案阶段-资源团队审核节点ID
	 */
	public final String ACT_RESOURCE_TEAM = "resource_team";
	
	/**
	 * 方案阶段-团队审核汇总节点ID
	 */
	public final String ACT_COLLECT_AUDIT = "collect_audit";
	
	/**
	 * 方案阶段-条线审核节点ID
	 */
	public final String ACT_BLAME_AUDIT = "blame_audit";
	
	/**
	 *  方案阶段-提交材料节点ID
	 */
	public final String ACT_SUBMIT_MATERIAL = "submit_material";
	
	/**
	 * 方案阶段-提交需求节点ID
	 */
	public final String ACT_SUBMIT_REQUIRE = "submit_require";
	
	/**
	 * 方案阶段-指定经办人
	 */
	public final String ACT_APPOINT_TRANSACTER = "appoint_transacter";
	
	/**
	 * 方案阶段-配置资源审核单节点ID
	 */
	public final String ACT_CONFIG_RESOURCE = "config_resource";
	
	/**
	 * 方案阶段-审核配置节点ID
	 */
	public final String ACT_CONFIG_AUDIT = "config_audit";
	
	/**
	 * 方案阶段-复核配置节点ID
	 */
	public final String ACT_SECOND_AUDIT = "second_audit";
	
	/**
	 * 方案阶段-审核方案节点ID
	 */
	public final String ACT_AUDIT_SCHEME = "audit_scheme";
	
	/**
	 * 方案阶段-发送需求变更表节点ID
	 */
	public final String ACT_SEND_FILE = "send_file";
	
	/**
	 * 方案阶段-处理指标节点ID
	 */
	public final String ACT_HANDLE_STANDARD = "handle_standard";
	
	/**
	 * 方案阶段-审核指标节点ID
	 */
	public final String ACT_AUDIT_STANDARD = "audit_standard";
	
	/**
	 * 方案阶段-提交最终指标节点ID
	 */
	public final String ACT_COMMIT_FINAL_STANDARD = "commit_final_standard";
	
	/**
	 * 方案阶段-发送最终指标节点ID
	 */
	public final String ACT_SEND_FINAL_STANDARD = "send_final_standard";
	
	/**
	 * 方案阶段-审核方案节点ID
	 */
	public final String ACT_HANDLE_SCHEME = "handle_scheme";
	
	/**
	 * 方案阶段-提交最终方案节点ID
	 */
	public final String ACT_COMMIT_FINAL_SCHEME = "commit_final_scheme";
	
	/**
	 * 方案阶段-发送最终方案节点ID
	 */
	public final String ACT_SEND_FINAL_SCHEME = "send_final_scheme";
	
	/**
	 * 方案阶段-发起
	 */
	public final String BP_SEND_FINAL_STATE_ID = "send_state";	
	
	public final String BP_SEND_FINAL_STATE_NAME = "发起";
	
	/**
	 * 首页展示权限
	 */
	public final String WAIT_DEAL_DEMAND1_FUNCID = "0711";//待处理需求 ${reqFlag=='1'}
	public final String WAIT_DEAL_DEMAND2_FUNCID = "0703";//待处理需求 ${reqFlag=='2'}
	public final String START_DISCUSS_DEMAND_FUNCID = "0706";//已发起讨论需求
	public final String TRANSMIT_REPLY_DEMAND_FUNCID = "0707";//转发已回复需求
	public final String DATA_SUPPLY_DEMAND_FUNCID = "0710";//材料已补充需求
	public final String BOSS_REPLYED_DEMAND_FUNCID = "0708";//应用团队已回复需求
	public final String BOSS_REFUSE_DEMAND_FUNCID = "0709";//领导审批不通过
	public final String DISCUSSING_DEMAND_FUNCID = "0703";//讨论中需求
	public final String WAIT_EDIT_DEMAND_FUNCID = "0703";//待修改需求
	public final String PASSED_DEMAND_FUNCID = "0712";//已通过需求
	public final String WAIT_CHECK_DEMAND_FUNCID = "0703";//待审批需求
	public final String WAIT_REPLY_DEMAND_FUNCID = "0703";//待回复需求
	public final String WAIT_DEAL_SUGGEST_FUNCID = "0403";//待处理建议
	public final String DISCUSSING_SUGGEST_FUNCID = "0403";//讨论中建议
	public final String REPLYED_SUGGEST_FUNCID = "0403";//已回复建议
	public final String WAIT_EDIT_SUGGEST_FUNCID = "0403";//待修改建议
	public final String FEEDBACK_SUGGEST_FUNCID = "0405";//已反馈建议
	public final String WAIT_CHECK_SUGGEST_FUNCID = "0403";//待审批建议
	public final String WAIT_REPLY_SUGGEST_FUNCID = "0403";//待回复建议
	
	
	public final static String SYS_ICMS_PARAMETER_PRIEX = "ICMS";//ICMS银行号前缀
	
}
