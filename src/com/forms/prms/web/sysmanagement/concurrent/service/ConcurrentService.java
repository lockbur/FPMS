package com.forms.prms.web.sysmanagement.concurrent.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.web.sysmanagement.concurrent.dao.ConcurrentDao;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentBean;
import com.forms.prms.web.sysmanagement.concurrent.domain.ConcurrentType;


@Service
public class ConcurrentService {
	@Autowired
	private ConcurrentDao dao;
	public String org21Code="";
	public String instOper="";
	/**
	 * 校验并发
	 * @param bean
	 * @return
	 */
	public Map<String, Object> concurrent(String type,String subType) {
		ConcurrentBean bean = new ConcurrentBean();
		bean.setType(type);
		bean.setSubType(subType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", "true");
		bean.setOrg21Code(org21Code);
		if (ConcurrentType.Concurrent_A.equals(bean.getType())) {
			//A锁 就看有没有B C锁
			String lock_BC = dao.lock_BC(bean);
			if (!Tool.CHECK.isEmpty(lock_BC)) {
				//有B锁  不能进行
				String msg = lockAMsg(lock_BC);
				map.put("flag", "false");
				map.put("msg", msg);
				map.put("errorMsg", alertMsg(msg));
			}
		}else if (ConcurrentType.Concurrent_B.equals(bean.getType())) {
			//B锁  看有没有A C 锁, B锁内部互斥是通过导入校验的代码实现的
			String lock_AC = dao.lock_AC(bean);
			if (!Tool.CHECK.isEmpty(lock_AC)) {
				//有A C锁  不能进行
				String msg = lockAMsg(lock_AC);
				map.put("flag", "false");
				map.put("msg", msg);
				map.put("errorMsg", alertMsg(msg));
			}
		}else if (ConcurrentType.Concurrent_C.equals(bean.getType())) {
			//不能有A B C锁
			String lock_ABC  = dao.lock_ABC(bean);
			if (!Tool.CHECK.isEmpty(lock_ABC)) {
				//有A  B C锁  不能进行
				String msg = lockAMsg(lock_ABC);
				map.put("flag", "false");
				map.put("msg", msg);
				map.put("errorMsg", alertMsg(msg));
			}
		}
		return map;
	}
	/**
	 * 
	 * @param type
	 * @param subType
	 * @param memo 
	 * @return 1:存在互斥锁 2：加锁成功  3：存在相同的锁不需要再加，同样也不需要删除锁
	 * 
	 * @throws UnknownHostException 
	 */
	public int checkAndAddLock(String type,String subType,String org21Code,String instOper, String memo) throws Exception{
		int returnFlag = 0;
		try {
			this.org21Code = org21Code;
			this.instOper = instOper;
			Map<String, Object> map = concurrent(type, subType);
			String flag = map.get("flag").toString();
			if (flag.equals("false")) {
				returnFlag = 1;
				throw new Exception(map.get("msg").toString());
			}else {
				//没有锁 加锁
				try {
					if(addConcurrentLock(type, subType,memo)>0){
						returnFlag = 2;
					}else {
						returnFlag = 3;
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return returnFlag;
		
	}
	/**
	 * 增加同步锁
	 * @param lockNo 
	 * @param lockNo 
	 * @param memo 
	 * @param bean
	 * @throws UnknownHostException 
	 */
	public int   addConcurrentLock(String type ,String subType,   String memo) throws Exception, UnknownHostException{
		
		ConcurrentBean bean = new ConcurrentBean();
		bean.setType(type);
		bean.setSubType(subType);
		CommonLogger.info("加入互斥锁");
		bean.setInstOper(instOper);
		bean.setOrg21Code(org21Code);
		String  ipAddress = InetAddress.getLocalHost().getHostName().toString();
		bean.setIpAddress(ipAddress);
		bean.setMemo(memo);
		int i = dao.addConcurrentLock(bean);
		return i;
		
	}
	/**
	 * 删除同步锁
	 * @param batchNo 
	 * @param bean
	 * @throws UnknownHostException 
	 */
	public void  delConcurrentLock(String type ,String subType, String batchNo) throws UnknownHostException{
		ConcurrentBean bean = new ConcurrentBean();
		bean.setType(type);
		bean.setSubType(subType);
		CommonLogger.info("删除锁");
		bean.setOrg21Code(org21Code);
		bean.setBatchNo(batchNo);
		bean.setIpAddress(InetAddress.getLocalHost().getHostName().toString());
		dao.delConcurrentLock(bean);
		
	}
	private String alertMsg(String errorMsg){
		String string = "$( '<div>"+errorMsg+"</div>' ).dialog({";
		string+="resizable: false,";
		string+="height:200,";
		string+="width:400,";
		string+="modal: true,";
		string+="dialogClass: 'dClass',";
		string+="buttons: {";
		string+="'关闭': function() {";
		string+="$( this ).dialog( 'close' );";
		string+="},";
		string+="}";
		string+= "});";
		return string;
	}
	private String lockAMsg(String subType){
		String msg="一级行"+org21Code+"下有";
		if (ConcurrentType.A1.equals(subType)) {
			msg = "一级行"+org21Code+"下有费用类合同正在确立，同时进行本操作可能会导致预算数据错误，所以不能进行本操作，请稍后再试!";
		}
		if (ConcurrentType.A2.equals(subType)) {
			msg = "一级行"+org21Code+"下有合同的费用承担部门正在变更，同时进行本操作可能会导致预算数据错误，所以不能进行本操作，请稍后再试!";
		}
		if (ConcurrentType.A3.equals(subType)) {
			msg = "一级行"+org21Code+"下正在新增资产类付款单，同时进行本操作可能会导致预算数据错误，所以不能进行本操作，请稍后再试!";
		}
		if (ConcurrentType.C1.equals(subType)) {
			msg = "系统中有责任中心撤并正在审批，同时进行本操作可能会导致预算数据错误，所以不能进行本操作，请稍后再试!";
		}
		if (ConcurrentType.B1.equals(subType)) {
			msg = "一级行"+org21Code+"下有导入的监控指标正在审批，同时进行本操作可能会导致预算数据错误，所以不能进行本操作，请稍后再试!";
		}
		if (ConcurrentType.B2.equals(subType)) {
			msg = "一级行"+org21Code+"下有导入的预算正在审批，同时进行本操作可能会导致预算数据错误，所以不能进行本操作，请稍后再试!";
		}
		return msg;
	}
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(InetAddress.getLocalHost().getHostName().toString());
	}
}
