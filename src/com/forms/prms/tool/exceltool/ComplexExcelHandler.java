package com.forms.prms.tool.exceltool;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * author : liy_nby <br>
 * date : 2014-4-22<br>
 * 复杂样式的Excel处理类<br>
 */
public class ComplexExcelHandler implements ExcelHandler{
	
	/** 模板路径 */
	private String templateFileName;
	private FileInputStream fileinputstream;
	private Workbook wbook;
	private Integer initSheetSize;//初始化页数
	private List<Sheet> sheets;
	private Integer rowNums=5000;//每页Loop数据行的数目
	private String excelType;//处理的Excel文件版本
	
	public ComplexExcelHandler(String templateFileName,String excelType){
		this.excelType = excelType;
		if("xls".equalsIgnoreCase(this.excelType)){
			this.wbook = new HSSFWorkbook();
		}else if("xlsx".equalsIgnoreCase(this.excelType)){
			this.wbook = new XSSFWorkbook();
		}
		this.sheets=new ArrayList<Sheet>();
		this.templateFileName = templateFileName;
	}
	
	/**
	 * 打开excel文件
	 */
	private <U,L> void openfile(ExcelBean<U,L> bean) {
		try {
			this.fileinputstream = new FileInputStream(this.templateFileName);
			this.wbook = WorkbookFactory.create(fileinputstream);
			//this.sheets.add(this.wbook.getSheetAt(0));//第一页肯定存在，模板页嘛
			//有可能Excel文件打开时就存在多个分页了，这些分页既有可能是模版页，也有可能不是模版页
			this.initSheetSize = this.wbook.getNumberOfSheets();
			for(int i=0;i<this.wbook.getNumberOfSheets();i++){
				this.sheets.add(this.wbook.getSheetAt(i));
			}
			//如果没指定哪些是模版页，则默认都为模版页
			if(bean.getTemplateIndex()==null||bean.getTemplateIndex().size()<=0){
				bean.setTemplateIndex(new ArrayList<Integer>());
				for(int i=0;i<this.sheets.size();i++){
					bean.getTemplateIndex().add(i);
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}finally{
			try {
				this.fileinputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 补完模板中的某类属性路径
	 * @param sheetNum
	 * @param type
	 * @param obj
	 * @throws Exception
	 */
	private void repairTemplate(int sheetNum,String type,Object obj) throws Exception{
		Row excelRow = null;
		Cell excelCell = null;
		List<String> lstPropertys = new ArrayList<String>(); 
		String temp = null;
		String propertyName = null;
		for (int iRowNum = 0; iRowNum <= this.sheets.get(sheetNum).getLastRowNum(); iRowNum++) {// 循环行Row
			lstPropertys.clear();
			excelRow = this.sheets.get(sheetNum).getRow(iRowNum);
			if (excelRow == null) {
				continue;
			}
			for (int cellNum = 0; cellNum < excelRow.getLastCellNum(); cellNum++) {// 循环列Cell
				excelCell = excelRow.getCell(cellNum);
				if (excelCell == null) {
					continue;
				}
				//解析占位符
				temp = PoiExcelUtil.getValue(excelCell);
				//解析小模块
				int sIndex = temp.indexOf(type+"{");
				int eIndex = -1;
				if(sIndex!=-1){
					eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
				}
				
				//存在"type{%}"的这种结构时
				while(sIndex!=-1&&eIndex>sIndex) {
					propertyName = temp.substring(sIndex+2, eIndex);
					temp=temp.substring(0, sIndex)+temp.substring(eIndex+1,temp.length());
					//解析小模块
					sIndex = temp.indexOf(type+"{");
					eIndex = -1;
					if(sIndex!=-1){
						eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
					}
					
					lstPropertys.add(propertyName);
				}
			}
			if(!PoiExcelUtil.delColProperty(lstPropertys, obj).equals("-1_0")){
				//开始扩展模版
				String[] params = PoiExcelUtil.delColProperty(lstPropertys, obj).split("_");
				PoiExcelUtil.insertRow(this.sheets.get(sheetNum), iRowNum, Integer.parseInt(params[1])-1,params[2]);
				for(int i = 0;i<Integer.parseInt(params[1]);i++){
					PoiExcelUtil.getNewProperty(lstPropertys, params, i);
					Iterator<String> ite = lstPropertys.iterator();
					//补全iRowNum到iRowNum+Integer.parseInt(params[1])-1行之间的属性路径
					excelRow = this.sheets.get(sheetNum).getRow(iRowNum+i);
					if (excelRow == null) {
						continue;
					}
					for (int cellNum = 0; cellNum < excelRow.getLastCellNum(); cellNum++) {// 循环列Cell
						excelCell = excelRow.getCell(cellNum);
						if (excelCell == null) {
							continue;
						}
						//解析占位符
						temp = PoiExcelUtil.getValue(excelCell);
						//解析小模块
						int sIndex = temp.indexOf(type+"{");
						int eIndex = -1;
						if(sIndex!=-1){
							eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
						}
						
						//存在"type{%}"的这种结构时
						while(sIndex!=-1&&eIndex>sIndex&&ite.hasNext()) {
							propertyName = temp.substring(sIndex, eIndex+1);
							excelCell.setCellValue(PoiExcelUtil.getValue(excelCell).replace(propertyName, type+"{"+ite.next()+"}"));//替换
							temp=temp.substring(0, sIndex)+temp.substring(eIndex+1,temp.length());
							//解析小模块
							sIndex = temp.indexOf(type+"{");
							eIndex = -1;
							if(sIndex!=-1){
								eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
							}
						}
					}
				}
				iRowNum--;//防止某一行是多次循环列
			}
		}
	}
	
	/**
	 * 填充loop数据
	 * @param loopData
	 */
	private <U,L> void  fillLoopData(ExcelBean<U,L> bean,int loopRowIndex) throws Exception{
		//异常检查
		if(loopRowIndex<0){
			throw new Exception("ComplexExcelHandler fillLoopData 模版中不存在loop数据行");
		}
		if(bean == null || bean.getLoopObject() == null || bean.getLoopObject().size()<=0){
			throw new Exception("ComplexExcelHandler fillLoopData 数据中不存在loop数据行");
		}
		
		List<L> loopData = bean.getLoopObject();
		Iterator<L> iteL = loopData.iterator();
		//局部变量申明
		L loop = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		String temp = null;
		String propertyName = null;
		String noTypeProperty = null;
		for(int k=0;k<this.sheets.size();k++){
			sheet = this.sheets.get(k);
			if(sheet == null){
				continue;
			}
			//初始化时那些非模版页也不处理
			if(k<this.initSheetSize&&k!=bean.getTemplateIndex().get(0)){
				continue;
			}
			for(int i=loopRowIndex;i<=sheet.getLastRowNum();i++){
				row = sheet.getRow(i);
				if(row == null){
					continue;
				}
				if(iteL.hasNext()){
					loop = iteL.next();
					for(int j=0;j<row.getLastCellNum();j++){
						cell = row.getCell(j);
						if(cell == null){
							continue;
						}
						//解析占位符
						temp = PoiExcelUtil.getValue(cell);
						//解析小模块
						int sIndex = temp.indexOf("^{");
						int eIndex = -1;
						if(sIndex!=-1){
							eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
						}
						
						//存在"^{%}"的这种结构时
						while(sIndex!=-1&&eIndex>sIndex) {
							propertyName = temp.substring(sIndex, eIndex+1);
							noTypeProperty = temp.substring(sIndex+2,eIndex);
							cell.setCellValue(PoiExcelUtil.getValue(cell).replace(propertyName, PoiExcelUtil.getValueByProperty(noTypeProperty, loop)));
							/*
							 * temp=temp.substring(0, sIndex)+temp.substring(eIndex+1,temp.length());
							 * 这种截取再判断的方式，有个缺陷就是当值是另外一个占位符的时不能得到有效的填充
							 */
							temp=PoiExcelUtil.getValue(cell);
							//解析小模块
							sIndex = temp.indexOf("^{");
							eIndex = -1;
							if(sIndex!=-1){
								eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 填充数据
	 * @param sheetNum
	 * @param type
	 * @param obj
	 */
	private void fillData(int sheetNum,String type,Object obj){
		Row excelRow = null;
		Cell excelCell = null;
		String temp = null;
		String propertyName = null;
		String noTypeName = null;
		for (int iRowNum = 0; iRowNum <= this.sheets.get(sheetNum).getLastRowNum(); iRowNum++) {// 循环行Row
			excelRow = this.sheets.get(sheetNum).getRow(iRowNum);
			if (excelRow == null) {
				continue;
			}
			for (int cellNum = 0; cellNum < excelRow.getLastCellNum(); cellNum++) {// 循环列Cell
				excelCell = excelRow.getCell(cellNum);
				if (excelCell == null) {
					continue;
				}
				//解析占位符
				temp = PoiExcelUtil.getValue(excelCell);
				//解析小模块
				int sIndex = temp.indexOf(type+"{");
				int eIndex = -1;
				if(sIndex!=-1){
					eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
				}
				
				//存在"type{%}"的这种结构时
				while(sIndex!=-1&&eIndex>sIndex) {
					propertyName = temp.substring(sIndex, eIndex+1);
					noTypeName = temp.substring(sIndex+2, eIndex);
					excelCell.setCellValue(PoiExcelUtil.getValue(excelCell).replace(propertyName, PoiExcelUtil.getValueByProperty(noTypeName, obj)));//替换
					/*
					 * temp=temp.substring(0, sIndex)+temp.substring(eIndex+1,temp.length());
					 * 这种截取再判断的方式，有个缺陷就是当值是另外一个占位符的时不能得到有效的填充
					 */
					temp=PoiExcelUtil.getValue(excelCell);
					//解析小模块
					sIndex = temp.indexOf(type+"{");
					eIndex = -1;
					if(sIndex!=-1){
						eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
					}
				}
			}
		}
	}
	
	/**
	 * 数据和模版整合
	 * @param bean
	 * @throws Exception
	 */
	private <H,U,L> void repairAll(ExcelBean<U,L> bean) throws Exception{
		if((bean.getLoopObject()!=null&&bean.getLoopObject().size()>0)&&(bean.getUniqueObject()!=null&&bean.getUniqueObject().size()>0)){
			throw new Exception("ComplexExcelHandler repairAll Unique数据和Loop数据不能同时存在，否则不好确定分页情况");
		}
		if(bean.getHandObject()!=null){
			for(int i=0;i<bean.getTemplateIndex().size();i++){
				this.repairTemplate(i, "!", bean.getHandObject());
				//填充Head数据
				this.fillData(i, "!", bean.getHandObject());
			}
		}
		int totalSheetNum = 1;
		//确定分页情况
		if(bean.getUniqueObject()!=null&&bean.getUniqueObject().size()>0){
			totalSheetNum = bean.getUniqueObject().size();
			//复制模版页
			for(int i=0;i<totalSheetNum-1;i++){
				for(int j=0;j<bean.getTemplateIndex().size();j++){
					int newIndex = this.initSheetSize+i*bean.getTemplateIndex().size()+j+1;
					this.sheets.add(this.wbook.createSheet("第"+newIndex+"页"));
					PoiExcelUtil.copySheet(this.sheets.get(bean.getTemplateIndex().get(j)), this.sheets.get(newIndex-1));
					PoiExcelUtil.mergerRegion(this.sheets.get(bean.getTemplateIndex().get(j)), this.sheets.get(newIndex-1));
				}
			}
			//补全Unique数据属性路径同时填充Unique数据
			for(int i=0;i<this.initSheetSize;i++){
				this.repairTemplate(i, "?", bean.getUniqueObject().get(0));
				this.fillData(i, "?", bean.getUniqueObject().get(0));
			}
			int sheetIndex = this.sheets.size()-1;
			int fillIndex =  bean.getUniqueObject().size()-1;
			int pulsIndex = 0;
			while(sheetIndex>=this.initSheetSize&&fillIndex>=1){
				this.repairTemplate(sheetIndex, "?", bean.getUniqueObject().get(fillIndex));
				this.fillData(sheetIndex, "?", bean.getUniqueObject().get(fillIndex));
				sheetIndex--;
				pulsIndex++;
				if(pulsIndex>=bean.getTemplateIndex().size()){
					fillIndex--;
					pulsIndex=0;
				}
			}
		}
		if(bean.getLoopObject()!=null&&bean.getLoopObject().size()>0){
			if(bean.getTemplateIndex()!=null&&bean.getTemplateIndex().size()!=1){
				throw new Exception("ComplexExcelHandler repairAll 处理Loop类型问题时模版页只能有一页");
			}
			if(this.rowNums<=0){
				throw new Exception("ComplexExcelHandler repairAll 每页Loop数据行数不能设置为小于0的数");
			}else{
				if(bean.getLoopObject().size()%this.rowNums!=0){
					totalSheetNum = bean.getLoopObject().size()/this.rowNums+1;
				}else{
					totalSheetNum = bean.getLoopObject().size()/this.rowNums;
				}
			}
			//复制模版页
			for(int i=1;i<totalSheetNum;i++){
				this.sheets.add(this.wbook.createSheet("第"+(this.initSheetSize+i)+"页"));
				PoiExcelUtil.copySheet(this.sheets.get(bean.getTemplateIndex().get(0)), this.sheets.get(this.initSheetSize+i-1));
			}
			int loopRowIndex = checkLoopRowNum();
			//新增每一页的Loop数据行
			if(loopRowIndex>-1){
				if(bean.getLoopObject().size()<=this.rowNums){
					PoiExcelUtil.insertRow(this.sheets.get(bean.getTemplateIndex().get(0)), loopRowIndex, bean.getLoopObject().size()-1,null);
				}else{
					PoiExcelUtil.insertRow(this.sheets.get(bean.getTemplateIndex().get(0)), loopRowIndex, this.rowNums-1,null);
				}
				if(totalSheetNum>1){
					//模版页和initSheetSize，totalSheetNum+initSheetSize-3之间的页的新增Loop数据行数都是row
					for(int i=this.initSheetSize;i<=totalSheetNum+this.initSheetSize-3;i++){
						PoiExcelUtil.insertRow(this.sheets.get(i), loopRowIndex, this.rowNums-1,null);//不考虑父子关系
					}
					PoiExcelUtil.insertRow(this.sheets.get(totalSheetNum+this.initSheetSize-2), loopRowIndex, bean.getLoopObject().size()-(totalSheetNum-1)*this.rowNums-1,null);
				}
				//填充loop数据
				this.fillLoopData(bean,loopRowIndex);
			}
			//补全Loop数据属性路径(这一步到底需不需要，暂定为不需要，因为规则设定时就假设Loop数据针对的是那种简单属性的对象数据)
		}
	}
	
	/**
	 * 判断第几行是Loop数据行
	 * @return
	 */
	private int checkLoopRowNum(){
		int loopRowNum = -1;
		Row excelRow = null;
		Cell excelCell = null;
		for (int iRowNum = 0; iRowNum <= this.sheets.get(0).getLastRowNum(); iRowNum++) {// 循环行Row
			excelRow = this.sheets.get(0).getRow(iRowNum);
			if (excelRow == null) {
				continue;
			}
			for (int cellNum = 0; cellNum < excelRow.getLastCellNum(); cellNum++) {// 循环列Cell
				excelCell = excelRow.getCell(cellNum);
				if (excelCell == null) {
					continue;
				}
				//解析占位符
				//解析小模块
				int sIndex = PoiExcelUtil.getValue(excelCell).indexOf("^{");
				int eIndex = -1;
				if(sIndex!=-1){
					eIndex = PoiExcelUtil.getValue(excelCell).substring(sIndex, PoiExcelUtil.getValue(excelCell).length()).indexOf("}")+sIndex;
				}
				//存在"^{"的这种结构时
				if(sIndex!=-1&&eIndex>sIndex) {
					loopRowNum = iRowNum;
					break;
				}
			}
			if(loopRowNum>-1){
				break;
			}
		}
		return loopRowNum;
	}
	
	@Override
	public void outputExcelFile(OutputStream outputStream) throws Exception {
		try {
			this.wbook.write(outputStream);
			outputStream.flush();
		} catch (Exception exception) {
			exception.printStackTrace();
		}finally{
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public <U,L> void fillExcel(ExcelBean<U,L> bean) throws Exception {
		this.openfile(bean);
		this.repairAll(bean);
		//自定义设计器
		if(bean.getDesigners()!=null){
			List<ExcelDesigner> designers = bean.getDesigners();
			for(int i =0;i< designers.size();i++){
				designers.get(i).setExcelData(this.wbook);
				designers.get(i).setExcelStyle(this.wbook);
			}
		}
		//根据参数更改默认页面
		List<String> sheetName = bean.getSheetName();
		if(sheetName!=null&&sheetName.size()>0){
			for(int i=0;i<sheetName.size();i++){
				if(this.sheets.get(i)!=null){
					this.wbook.setSheetName(i, sheetName.get(i));
				}
			}
		}
	}
	
	@Override
	public <U, L> void getBeanFromExcel(ExcelBean<U, L> bean) throws Exception {
		
	}

	public String getTemplateFileName() {
		return templateFileName;
	}
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}
	public int getSheetsNum() {
		return sheets.size();
	}
	public Integer getRowNums() {
		return rowNums;
	}
	public void setRowNums(Integer rowNums) {
		this.rowNums = rowNums;
	}
}
