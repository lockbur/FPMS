package com.forms.prms.tool.exceltool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 直接导出表格式的Excel文件
 * 节约内存提高效率beta
 */
public class TableExcelHandler implements ExcelDesigner,ExcelHandler{
	
	private Workbook wbook;
	private List<Sheet> sheets;
	private Integer rowNums=5000;//每页数据行的数目
	private String dataExcelPath;//导入时的数据文件路径
	private FileInputStream fileinputstream;
	private String excelType;//处理的Excel文件版本
	
	public TableExcelHandler(String excelType){
		this.excelType = excelType;
		if("xls".equalsIgnoreCase(this.excelType)){
			this.wbook = new HSSFWorkbook();
		}else if("xlsx".equalsIgnoreCase(this.excelType)){
			this.wbook = new XSSFWorkbook();
		}
		this.sheets=new ArrayList<Sheet>();
	}
	
	/**
	 * 导入时读取excel文件
	 */
	private void openfile() {
		try {
			this.fileinputstream = new FileInputStream(this.dataExcelPath);
			this.wbook = WorkbookFactory.create(fileinputstream);
			this.sheets.clear();//读取之前先清空数据防止旧数据带来干扰
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
	 * 生成excel文件
	 * @param outputStream
	 */
	public void outputExcelFile(OutputStream outputStream) {
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
	
	/**
	 * 根据列名生成第一页表头行
	 * @param lstColumnName
	 */
	private void creatTableHead(List<String> lstColumnName) throws Exception{
		Sheet templateSheet = this.wbook.createSheet("第1页");
		Row columnRow=templateSheet.createRow(0);
		Cell columnCell = null;
		int iColumnLength=lstColumnName.size();
		
		for(int i=0;i<iColumnLength;i++){
			columnCell=columnRow.createCell(i);
			columnCell.setCellValue(lstColumnName.get(i));
		}
		PoiExcelUtil.autoSizeGBKColumn(templateSheet);
		this.sheets.add(templateSheet);
	}
	
	@Override
	public <U, L> void getBeanFromExcel(ExcelBean<U, L> bean) throws Exception {
		
	}
	
	@Override
	public <U, L> void fillExcel(ExcelBean<U, L> bean) throws Exception {
		//一些规则
		if(bean.getCellName()==null||bean.getCellName().size()<=0){
			throw new Exception("TableExcelHandler fillExcel 表头名不能为空");
		}
		if(bean.getCellProperty()==null&&bean.getCellProperty().size()<=0){
			throw new Exception("TableExcelHandler fillExcel 属性名不能为空");
		}
		if(bean.getCellProperty().size()!=bean.getCellName().size()){
			throw new Exception("TableExcelHandler fillExcel 表头名和属性名必须一一对应");
		}
		//暂定为允许空数据
		this.creatTableHead(bean.getCellName());
		Iterator<L> iteL = bean.getLoopObject().iterator();
		//根据数据和每页数据行数算总页数
		int totalSheetNum = 0;
		if((bean.getLoopObject().size()%this.rowNums)==0){
			totalSheetNum = bean.getLoopObject().size()/this.rowNums;
		}else{
			totalSheetNum = bean.getLoopObject().size()/this.rowNums+1;
		}
		
		for(int i=1;i<totalSheetNum;i++){
			Sheet sheet = this.wbook.createSheet("第"+(i+1)+"页");
			PoiExcelUtil.copySheet(this.sheets.get(0), sheet);
			this.sheets.add(sheet);
		}
		//开始填充数据
		List<List<String>> cells = this.getCellProperty(bean.getCellProperty());
		for(int i=0;i<totalSheetNum;i++){
			Row targetRow = null;
			Cell targetCell = null;
			for(int j=1;j<=this.rowNums;j++){
				if(iteL.hasNext()){
					L loop = iteL.next();
					targetRow = this.sheets.get(i).createRow(j);
					for(int z=0;z<bean.getCellProperty().size();z++){
						String cellValue = bean.getCellProperty().get(z);
						targetCell = targetRow.createCell(z);
						for(int p=0;p<cells.get(z).size();p++){
							//简单数据表格样式Excel不考虑填入的值也是占位符的情况
							cellValue=cellValue.replace("^{"+cells.get(z).get(p)+"}", PoiExcelUtil.getValueByProperty(cells.get(z).get(p), loop));
						}
						targetCell.setCellValue(cellValue);
					}
				}else{
					break;//当为了去掉分页效果而设置每页行数为正无穷的时候，防止出现无限循环
				}
			}
		}
		this.setExcelStyle(wbook);//默认样式
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
	
	/**
	 * 获得列属性名集合包含的具体属性名
	 * @param cellProperty
	 * @return
	 */
	private List<List<String>> getCellProperty(List<String> cellProperty){
		List<List<String>> result = new ArrayList<List<String>>();
		for(int z=0;z<cellProperty.size();z++){
			List<String> tempRes = new ArrayList<String>();
			//解析该property
			String temp = cellProperty.get(z);
			//解析小模块
			int sIndex = temp.indexOf("^{");
			int eIndex = -1;
			if(sIndex!=-1){
				eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
			}
			//存在"^{"的这种结构时
			while(sIndex!=-1&&eIndex>sIndex) {
				tempRes.add(temp.substring(sIndex+2, eIndex));
				temp=temp.substring(0, sIndex)+temp.substring(eIndex+1,temp.length());
				//解析小模块
				sIndex = temp.indexOf("^{");
				eIndex = -1;
				if(sIndex!=-1){
					eIndex = temp.substring(sIndex, temp.length()).indexOf("}")+sIndex;
				}
			}
			result.add(tempRes);
		}
		return result;
	}

	public void setExcelStyle(Workbook wb) {
		//设置表头样式
		CellStyle headCellStyle = wb.createCellStyle();
		//设置边框
		headCellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		headCellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		headCellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
		headCellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
		//设置居中
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		//设置字体
		Font headFont = wb.createFont();
		headFont.setFontName("黑体");
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体显示
		headCellStyle.setFont(headFont);//选择需要用到的字体格式
		headCellStyle.setWrapText(true);//设置自动换行
		
		for(int j=0;j<this.sheets.size();j++){
			Row headRow = this.sheets.get(j).getRow(0);
			for(int i=headRow.getFirstCellNum();i<headRow.getLastCellNum();i++){
				Cell headCell = headRow.getCell(i);
				headCell.setCellStyle(headCellStyle);
			}
		}
		
		//设置数据样式
		CellStyle dataCellStyle = wb.createCellStyle();
		//设置边框
		dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
		dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
		//设置居中
		dataCellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 居左
		Font dataFont = wb.createFont();
		dataFont.setFontName("黑体");
		dataCellStyle.setFont(dataFont);//选择需要用到的字体格式
		
		for(int z=0;z<this.sheets.size();z++){
			for(int i=1;i<=this.sheets.get(z).getLastRowNum();i++){
				if(this.sheets.get(z).getRow(i)!=null){
					Row dataRow = this.sheets.get(z).getRow(i);
					for(int j=dataRow.getFirstCellNum();j<dataRow.getLastCellNum();j++){
						Cell dataCell = dataRow.getCell(j);
						dataCell.setCellStyle(dataCellStyle);
					}
				}
			}
		}
	}
	
	@Override
	public void setExcelData(Workbook wbook) throws Exception {
		//默认数据填充即为设计填充
		
	}
	
	@Override
	public List<Object> getExcelData(Workbook wbook) throws Exception {
		return null;
	}
	
	/**
	 * 由于导入未能解决规则问题，所以暂时只实现简单表格样式的Excel的导入
	 * @param propertyMapping
	 * @param dataExcelPath
	 * @param rootClassPath
	 * @param startSheet
	 * @param endSheet
	 * @return
	 * @throws Exception
	 */
	public List<Object> getBeanFromExcel(List<String> propertyMapping,
			String dataExcelPath, String rootClassPath,int startSheet,int endSheet) throws Exception {
		
		List<Object> data = new ArrayList<Object>();
		this.dataExcelPath = dataExcelPath;
		this.openfile();
		if(propertyMapping==null||propertyMapping.size()<=0){
			throw new Exception("TableExcelHandler getBeanFromExcel 导入时属性路径不能为空");
		}
		if(startSheet<0){
			startSheet = 0;
		}
		if(endSheet>=this.wbook.getNumberOfSheets()){
			endSheet = this.wbook.getNumberOfSheets()-1;
		}
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		for(int i=startSheet;i<=endSheet;i++){
			sheet = this.wbook.getSheetAt(i);
			if(sheet==null){
				continue;
			}
			for(int j=1;j<=sheet.getLastRowNum();j++){//简单属性表格从第二行开始为数据行
				row = sheet.getRow(j);
				if(row == null){
					continue;
				}
				Object obj = Class.forName(rootClassPath).newInstance();
				for(int z=0;z<propertyMapping.size();z++){
					cell = row.getCell(z);
					if(cell==null){
						continue;
					}
					PoiExcelUtil.setValueByProperty(propertyMapping.get(z), PoiExcelUtil.getValue(cell), obj);
				}
				data.add(obj);
			}
		}
		return data;
	}

	public void setRowNums(Integer rowNums) {
		this.rowNums = rowNums;
	}
	public int getSheetsNum() {
		return sheets.size();
	}
	public int getRowNums() {
		return rowNums;
	}
}
