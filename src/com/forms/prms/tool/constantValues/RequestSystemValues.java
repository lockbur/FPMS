package com.forms.prms.tool.constantValues;
/**
 * 需求阶段相关参数
 * @author wangzf
 * @date 2014-08-08
 */
public class RequestSystemValues {
	/**
	 * 需求信息表表名
	 */
	public final String REQUEST_MAIN_INFO = "REQUEST_MAIN_INFO";
	
	/**
	 * 需求团队处理表名
	 */
	public final String REQUEST_REQDEPT_DEAL = "REQUEST_REQDEPT_DEAL";
	
	/**
	 * 需求退回处理表名
	 */
	public final String REQUEST_REQDEPT_BACK = "REQUEST_REQDEPT_BACK";
	
	/**
	 * 总行需求
	 */
	public final String REQUEST_SORT_0 = "0";
	
	/**
	 * 分行需求
	 */
	public final String REQUEST_SORT_1 = "1";
	
	/****************工作流相关参数***************************************/
	
	/**
	 * 需求阶段-总行需求创建流程定义KEY
	 */
	public final String REQ_HQ_REQUEST_KEY = "req_hq_request";
	
	/**
	 * 需求阶段-需求团队处理流程定义KEY
	 */
	public final String REQ_REQDEPT_DEAL_KEY = "req_reqdept_deal";
	
	/**
	 * 需求阶段-需求提出人处理流程定义KEY
	 */
	public final String REQ_REQDEPT_BACK = "req_reqdept_back";
	
	/**
	 * 需求阶段-分行需求创建流程定义KEY
	 */
	public final String REQ_SUB_REQUEST_KEY = "req_sub_request";
	
	/**
	 * 上级审批节点ID
	 */
	public final String DEPT_APPROVE = "dept_approve";
	
	/**
	 * 需求受理节点ID
	 */
	public final String REQ_APPEL = "req_appel";
	
	/**
	 * 经办修改节点ID
	 */
	public final String CREATER_EDIT = "creater_edit";
	
	/**
	 * 方案阶段变更流程节点ID
	 */
	public final String BP_REQCHANGE = "bp_reqchange";
	
	/**
	 * 方案阶段立项流程节点ID
	 */
	public final String BP_NEWPROJ = "bp_newproj";
	
	/**
	 * 需求团队处理节点ID
	 */
	public final String REQDEPT_DEAL = "reqdept_deal";
	
	/**
	 * 需求提出人处理节点ID
	 */
	public final String REQDEPT_BACK = "reqdept_back";
	
	/**
	 * 处理需求节点ID
	 */
	public final String REQ_DEAL = "req_deal";
	
	/**
	 * 需求讨论节点ID
	 */
	public final String REQ_DISCUSS = "req_discuss";
	
	/**
	 * 需求转发节点ID
	 */
	public final String REQ_TRANSMIT = "req_transmit";
	
	/**
	 * 补充材料节点ID
	 */
	public final String SUPPLY_STUFF = "supply_stuff";
	
	/**
	 * 经办征求意见节点ID
	 */
	public final String CREATER_GETIDEA = "creater_getidea";
	
	/**
	 * 总行信科总经理审批节点ID
	 */
	public final String MANAGER_APPROVE = "manager_approve";
	
	/**
	 * 费用审核
	 */
	public final String FARE_APPROVE="fare_approve";
	
	/**
	 * 出具批复节点ID
	 */
	public final String MAKE_POSTIL = "make_postil";
	
	/**
	 * 团队主管审批节点ID
	 */
	public final String DIRECTOR_APPROVE = "director_approve";
	
	/**
	 * 需求不通过节点ID
	 */
	public final String REQ_UNPASS = "req_unpass";
	
	/**
	 * 征求意见节点ID
	 */
	public final String CREATER_GETIDEA1 = "creater_getidea1";
	
	/**
	 * 征求意见节点ID
	 */
	public final String CREATER_GETIDEA2 = "creater_getidea2";
	
	
	/**
	 * 需求提出人处理流程结束节点
	 */
	public final String END_EVENT1 = "endevent1";
	/*----------------------需求状态---------------------------*/
	/**
	 * 待提交
	 * 判断登录用户是否需求创建人--修改、删除 
	 */
	public final String STATE_01 = "01"; 
	/**
	 * 待提出部门总经理审批
	 * 判断登录用户角色是否指令对应角色--审批
	 */
	public final String STATE_02 = "02"; 
	/**
	 * 待需求管理团队处理
	 * 
	 */
	public final String STATE_03 = "03"; 
	/**
	 * 需求转发中
	 */
	public final String STATE_04 = "04"; 
	/**
	 * 需求转发已回复
	 */
	public final String STATE_05 = "05"; 
	/**
	 * 已发应用团队
	 */
	public final String STATE_06 = "06"; 
	/**
	 * 应用团队已回复
	 */
	public final String STATE_07 = "07"; 
	/**
	 * 讨论中
	 */
	public final String STATE_08 = "08"; 
	/**
	 * 讨论结束
	 */
	public final String STATE_09 = "09"; 
	/**
	 * 待总行信科总经理审批
	 */
	public final String STATE_10 = "10"; 
	/**
	 * 总行信科总经理已审批
	 */
	public final String STATE_11 = "11"; 
	/**
	 * 需求不通过退回
	 */
	public final String STATE_12 = "12"; 
	/**
	 * 退回补充材料
	 */
	public final String STATE_13 = "13"; 
	/**
	 * 已删除
	 */
	public final String STATE_14 = "14"; 
	/**
	 * 待修改
	 */
	public final String STATE_15 = "15"; 
	/**
	 * 材料已补充
	 */
	public final String STATE_16 = "16";
	/**
	 * 需求通过待立项
	 */
	public final String STATE_17 = "17";
	/**
	 * 需求团队主管不通过
	 */
	public final String STATE_18 = "18";
	/**
	 * 总行信科总经理不通过
	 */
	public final String STATE_19 = "19";
	
	/**
	 * 需求已通过
	 */
	public final String STATE_20 = "20";
	
	/*-------------------操作类型------------------------------*/
	/**
	 * 创建需求
	 */
	public final String OPER_TYPE_01 = "01";
	/**
	 * 更新需求
	 */
	public final String OPER_TYPE_02 = "02";
	/**
	 * 申请经办部门审批
	 */
	public final String OPER_TYPE_03 = "03";
	/**
	 * 发起讨论
	 */
	public final String OPER_TYPE_04 = "04";
	/**
	 * 征求意见
	 */
	public final String OPER_TYPE_05 = "05";
	/**
	 * 审批通过,提交需求管理团队处理
	 */
	public final String OPER_TYPE_06 = "06";
	/**
	 * 审批不通过，退回修改
	 */
	public final String OPER_TYPE_07 = "07";
	/**
	 * 信科总经理审批
	 */
	public final String OPER_TYPE_08 = "08";
	/**
	 * 删除需求
	 */
	public final String OPER_TYPE_09 = "09";
	/**
	 * 审批不通过，退回
	 */
	public final String OPER_TYPE_10 = "10";
	/**
	 * 退回补充材料
	 */
	public final String OPER_TYPE_11 = "11";
	/**
	 * 受理需求
	 */
	public final String OPER_TYPE_12 = "12";
	/**
	 * 材料已补充
	 */
	public final String OPER_TYPE_13 = "13";
	
	/**
	 * 需求通过
	 */
	public final String OPER_TYPE_14 = "14";
	/**
	 * 费用审核
	 */
	public final String OPER_TYPE_15 = "15";
	/*-------------------指令类型------------------------------*/
	/**
	 * 经办部门审批
	 */
	public final String ASSIGN_TYPE_1 = "1";
	/**
	 * 需求转发
	 */
	public final String ASSIGN_TYPE_2 = "2";
	/**
	 * 征求意见
	 */
	public final String ASSIGN_TYPE_3 = "3";
	/**
	 * 发起讨论
	 */
	public final String ASSIGN_TYPE_4 = "4";
	/**
	 * 转信科总经理审批
	 */
	public final String ASSIGN_TYPE_5 = "5";
	/**
	 * 退回经办修改
	 */
	public final String ASSIGN_TYPE_6 = "6";
	/**
	 * 发起讨论（特殊）
	 */
	public final String ASSIGN_TYPE_A = "A";
	/**
	 * 征求意见（特殊）
	 */
	public final String ASSIGN_TYPE_B = "B";
	/**
	 * 征求意见完待业务部门修改（特殊）
	 */
	public final String ASSIGN_TYPE_C = "C";
	/**
	 * 需求管理部门受理（特殊，审批成功后邮件通知需求管理部门时用到）
	 */
	public final String ASSIGN_TYPE_D = "D";
	/**
	 * 需求管理部门待处理（特殊，讨论结束后邮件通知需求管理部门时用到）
	 */
	public final String ASSIGN_TYPE_E = "E";
	/**
	 * 需求管理部门待处理（特殊，需求转发已全部回复后通知需求管理部门时用到）
	 */
	public final String ASSIGN_TYPE_F = "F";
	/**
	 * 需求管理部门待处理（特殊，总工室已回复后通知需求管理部门时用到）
	 */
	public final String ASSIGN_TYPE_G = "G";
	/**
	 * 需求提出部门立项（特殊，需求通过后转需求提出部门立项时用到）
	 */
	public final String ASSIGN_TYPE_H = "H";
	/**
	 * 需求提出部门待处理（特殊，需求修改并补充材料后转需求提出部门时用到）
	 */
	public final String ASSIGN_TYPE_I = "I";
	/**
	 * 审核结束（特殊，总行信科总经理审核通过后转需求提出部门时用到）
	 */
	public final String ASSIGN_TYPE_J = "J";
	/**
	 * 费用审核（转总行计划团队进行费用审核）
	 */
	public final String ASSIGN_TYPE_K = "K";
	/*-------------------附件类型------------------------------*/
	/**
	 * 需求说明书
	 */
	public final String ATTACH_TYPE_R01="R01";
	/**
	 * 需求管理审核意见
	 */
	public final String ATTACH_TYPE_R02="R02";
	/**
	 * 技术方案建议书
	 */
	public final String ATTACH_TYPE_R03="R03";
	/**
	 * 总工室审核意见
	 */
	public final String ATTACH_TYPE_R04="R04";
	/**
	 * 可行性分析报告
	 */
	public final String ATTACH_TYPE_R05="R05";
	/**
	 * 立项申请表
	 */
	public final String ATTACH_TYPE_R06="R06";
	/**
	 * 资源配置方案及审核意见
	 */
	public final String ATTACH_TYPE_R07="R07";
	/**
	 * 讨论附件
	 */
	public final String ATTACH_TYPE_O01="O01";
	/**
	 * 优化建议附件
	 */
	public final String ATTACH_TYPE_Y01="Y01";
	
	/*-------------------指令处理------------------------------*/
	/**
	 * 不通过
	 */
	public final String DEAL_FLAG_0="0";
	/**
	 * 通过
	 */
	public final String DEAL_FLAG_1="1";
	/**
	 * 不存在
	 */
	public final String DEAL_FLAG_00="-1";
	/**
	 * 未处理
	 */
	public final String IS_DEALED_0="0";
	/**
	 * 已处理
	 */
	public final String IS_DEALED_1="1";
	
	/*-------------------审批展示---------------------------------*/
	/**
	 * 需求阶段
	 */
	public final String STAGE_FLAG_1="1";
	/**
	 * 方案阶段
	 */
	public final String STAGE_FLAG_2="2";
	/**
	 * 意见表
	 * 经办意见
	 */
	public final String OP_TYPE_1="1";
	/**
	 * 意见表
	 * 主管意见
	 */
	public final String OP_TYPE_2="2";
	/**
	 * 意见表
	 * 总经理意见
	 */
	public final String OP_TYPE_3="3";
	/**
	 * 意见表
	 * 总行计划团队审核意见
	 */
	public final String OP_TYPE_4="4";
	
}
