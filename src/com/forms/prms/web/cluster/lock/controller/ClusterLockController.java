package com.forms.prms.web.cluster.lock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.web.WebUtils;
import com.forms.platform.web.returnlink.ReturnLinkUtils;
import com.forms.prms.web.cluster.Lock;
import com.forms.prms.web.cluster.lock.domain.ClusterLock;
import com.forms.prms.web.cluster.lock.service.ClusterLockService;
import com.forms.prms.web.user.domain.User;
import com.forms.prms.web.util.ForwardPageUtils;

@Controller
@RequestMapping("/cluster/lock")
public class ClusterLockController {
	@Autowired
	private ClusterLockService cService;

	private static final String FUNCTION = "cluster/lock/";

	/**
	 * 集群锁--列表 0109
	 * 
	 * @return
	 */
	@RequestMapping("list.do")
	public String getClusterLockList() {
		ReturnLinkUtils.addReturnLink("ClusterList", "返回集群管理列表");
		List<ClusterLock> list = cService.getClusterLockList();
		WebUtils.setRequestAttr("cList", list);
		return FUNCTION + "list";
	}

	/**
	 * 删除集群锁 -- 010901
	 * 
	 * @param cl
	 * @return
	 */
	@RequestMapping("deleteClusterLock.do")
	public String deleteClusterLock(ClusterLock cl) {
		if (cService.deleteClusterLock(cl)) {
			WebUtils.getMessageManager().addInfoMessage("删除集群锁成功！");
			ReturnLinkUtils.setShowLink("ClusterList");
			return ForwardPageUtils.getSuccessPage();
		} else {
			WebUtils.getMessageManager().addInfoMessage("删除集群锁失败！");
			ReturnLinkUtils.setShowLink("ClusterList");
			return ForwardPageUtils.getErrorPage();
		}
	}
}
