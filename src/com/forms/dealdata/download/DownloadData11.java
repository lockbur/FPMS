package com.forms.dealdata.download;

import org.springframework.stereotype.Component;

@Component
public class DownloadData11 extends DownloadData 
{

	/*@Autowired
	private ReceiveDao dao;

	@Override
	public void callProc(String batchNo,String tradeType) throws Exception {
		super.callProc(batchNo,tradeType);
		//查询出密码为空的用户名 然后循环update
		List<User> list = dao.selectInitUser();
		if (null !=list && list.size()>0) {
			for (User user : list) {
				String userId = user.getUserId();
				String password = new EncryptUtil(userId).encryptNumStr(InitPwd.pwd);
				dao.updateUserPwd(userId,password);
			}
		}
	}*/
	
}
