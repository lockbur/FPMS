package com.forms.prms.tool.seqno.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.seqno.dao.ISeqNoDAO;
import com.forms.prms.tool.seqno.domain.SeqNoBean;

@Service
public class SeqNoService {
	
	@Autowired
	ISeqNoDAO dao;
	
	/***************************************************************************
	 * 检查目标对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	private boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	
	/***************************************************************************
	 * 检查目标对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isNull(Object obj, String objName) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	
	/***************************************************************************
	 * 比较两个对象
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	private boolean equals(Object obj1, Object obj2) {
		// 判断是否为空
		if (this.isNull(obj1) || this.isNull(obj2)) {
			return false;
		}

		// 比较，比较规则由对象自己实现
		if (obj1.equals(obj2)) {
			return true;
		}

		return false;
	}
	
	private String buildFillString(String fillType, String src,
			String fillString, int length) {
		if (this.isNull(fillType, "fillType")
				|| this.isNull(fillString, "fillString")
				|| this.isNull(src, "src")) {
			return null;
		}

		if (!this.equals(fillType, "Left") && !this.equals(fillType, "Right")) {
			return null;
		}

		int fillLength = length - src.getBytes().length;
		if (fillLength == 0) {
			// not need to fill
			return src;
		} else if (fillLength < 0) {
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer();
		while (stringBuffer.length() < fillLength) {
			stringBuffer.append(fillString);
		}
		stringBuffer.delete(fillLength, stringBuffer.length());

		if (fillType.equalsIgnoreCase("Left")) {
			return stringBuffer + src;
		} else if (fillType.equalsIgnoreCase("Right")) {
			return src + stringBuffer;
		}
		return null;
	}
	
	private synchronized String getSqlNo(String seqType,String seqDate,int noLength) throws Exception
    {
		StringBuilder strbder = new StringBuilder() ;
		SeqNoBean params = new SeqNoBean();
		params.setSeqType(seqType);
		params.setSeqDate(seqDate);
			
		SeqNoBean bean = dao.getSeqNO(params);
        
        if(bean == null)
        {
        	params.setSeqNo("1");
        	
        	if(dao.insertSeqNo(params) == 0)
        	{
        		throw new Exception("插入序号失败!") ;
        	}
        	params.setSeqNo(this.buildFillString("Left", "1", "0", noLength));
        	strbder.append(params.getSeqNo()) ;
        }
        else
        {
        	int seqNo = 1 + Integer.parseInt(bean.getSeqNo());
        	bean.setSeqNo(seqNo+"");
			if(dao.updateSeqNo(bean)==0)
			{
				throw new Exception("更新序号失败!");
			}
			bean.setSeqNo(this.buildFillString("Left", String.valueOf(seqNo), "0", noLength)) ;
			strbder.append(bean.getSeqNo()) ;
        }
		return strbder.toString() ;
    }
	
	/**
	 * 组ID: ID类型(?)+日期(8)+ID序号(seqNoLength)
	 * @param seqType
	 * @param seqNoLength ID序号的长度
	 * @return
	 * @throws Exception
	 */
    public String getSeqNo(String seqType,int seqNoLength)throws Exception
    {
    	String seqDate = Tool.DATE.getDate().replace("-", "");
        return (seqType + seqDate + getSqlNo(seqType,seqDate,seqNoLength));
    }
    
    /**
     * 组ID: ID类型(?)+日期(8)+ID序号(seqNoLength)
     * @param seqType
     * @param seqDate
     * @param seqNoLength ID序号的长度
     * @return
     * @throws Exception
     */
    public String getSeqNo(String seqType,String seqDate,int seqNoLength)throws Exception
    {
    	seqDate = seqDate.replace("-", "");
        return (seqType + seqDate + getSqlNo(seqType,seqDate,seqNoLength));
    }

}
