package com.forms.prms.web.sysmanagement.projectcrinfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forms.prms.web.sysmanagement.projectcrinfo.domain.ProjectcrInfo;

/**
 * @author : YiXiaoYan <br>
 * @since : 2015-01-25 <br>
 * 电子审批信息DAO
 */
@Repository
public interface IProjectcrInfoDAO
{
	/**
	 * 保存或更新电子审批信息
	 * @param projectcr
	 */
	public void saveProjectcrInfo(ProjectcrInfo projectcr);
	
	/**
	 * 查询电子审批信息列表
	 * @param projectcr
	 * @return
	 */
	public List<ProjectcrInfo> selectProjectcrInfoList(ProjectcrInfo projectcr);
}
