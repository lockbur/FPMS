package com.forms.prms.web.rm.tool.download.model;


public class RowColNumBean2 {
	
	private short[] colRowNum = new short[200];//每一列的当前行数（表头），目前设置允许表头列数最多为200
	
	private int rowNum;
	
	private int colNum;

	public short[] getColRowNum() {
		return colRowNum;
	}

	public void setColRowNum(short[] colRowNum) {
		this.colRowNum = colRowNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	
	public void setColNum()
	{
		this.colNum = 0;
		short minRow = this.colRowNum[0];
		for(short i=0; i<this.colRowNum.length; i++)
		{
			if(this.colRowNum[i]!= 3&&this.colRowNum[i]<minRow)
			{
				this.colNum = i;
				this.rowNum = this.colRowNum[i];
				minRow = this.colRowNum[i];
			}
		}
			
	}
}
