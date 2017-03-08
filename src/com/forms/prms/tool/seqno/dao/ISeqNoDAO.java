package com.forms.prms.tool.seqno.dao;

import org.springframework.stereotype.Repository;

import com.forms.prms.tool.seqno.domain.SeqNoBean;

@Repository()
public interface ISeqNoDAO {
	
	public SeqNoBean getSeqNO(SeqNoBean bean);
	
	public Integer insertSeqNo(SeqNoBean bean);
	
	public Integer updateSeqNo(SeqNoBean bean);
	
}
