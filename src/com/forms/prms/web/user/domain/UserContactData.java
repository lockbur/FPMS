package com.forms.prms.web.user.domain;

/**
 * 用户联系信息Bean
 * @function：<br>
 * @author: cz 2009-3-16 <br>
 * @company: 深圳四方精创<br>
 * @since 1.5
 */
public class UserContactData
{
	private String				userName;
	private String				userId;
	private String				unitId;
	private String				unitName;
	private String				postId;
	private String				telphone; //固定电话
	private String				mobile;//移动电话
	private String				fax;//传真
	private String				email;//EMAIL
	private String				key;//发送邮件时传送唯一的Key
	private String				deptId;
	private String				deptName;
	private String				phoneNumber;
	private String				notesId;

	
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUnitId()
	{
		return unitId;
	}
	public void setUnitId(String unitId)
	{
		this.unitId = unitId;
	}
	public String getUnitName()
	{
		return unitName;
	}
	public void setUnitName(String unitName)
	{
		this.unitName = unitName;
	}
	public String getPostId()
	{
		return postId;
	}
	public void setPostId(String postId)
	{
		this.postId = postId;
	}
	public String getTelphone()
	{
		return telphone;
	}
	public void setTelphone(String telphone)
	{
		this.telphone = telphone;
	}
	public String getMobile()
	{
		return mobile;
	}
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	public String getFax()
	{
		return fax;
	}
	public void setFax(String fax)
	{
		this.fax = fax;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getDeptId()
	{
		return deptId;
	}
	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public String getNotesId()
	{
		return notesId;
	}
	public void setNotesId(String notesId)
	{
		this.notesId = notesId;
	}
}
