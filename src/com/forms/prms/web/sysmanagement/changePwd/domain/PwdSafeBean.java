package com.forms.prms.web.sysmanagement.changePwd.domain;

import java.io.Serializable;
import java.util.Date;

public class PwdSafeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String questionId;
	private String questionSeq;
	private String questionInfo;
	private String questionAnswer;
	private String answer;
	private String password;
	
	private String email;
	private String tokenId;
	private Date tokenCreateTime;
	private Date tokenValidTime;
	private Date sysDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getTokenCreateTime() {
		return tokenCreateTime;
	}

	public void setTokenCreateTime(Date tokenCreateTime) {
		this.tokenCreateTime = tokenCreateTime;
	}

	public Date getTokenValidTime() {
		return tokenValidTime;
	}

	public void setTokenValidTime(Date tokenValidTime) {
		this.tokenValidTime = tokenValidTime;
	}

	public Date getSysDate() {
		return sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(String questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(String questionInfo) {
		this.questionInfo = questionInfo;
	}

}
