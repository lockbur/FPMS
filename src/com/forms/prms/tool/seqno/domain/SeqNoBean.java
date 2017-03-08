package com.forms.prms.tool.seqno.domain;

import java.io.Serializable;

public class SeqNoBean implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1267764426155233752L;
	
	private String seqType; //序号类型

	private String seqDate; //序号日期
	
	private String seqNo;   //序号

	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	public String getSeqDate() {
		return seqDate;
	}

	public void setSeqDate(String seqDate) {
		this.seqDate = seqDate;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
}
