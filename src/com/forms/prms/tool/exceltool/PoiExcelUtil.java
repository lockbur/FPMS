package com.forms.prms.tool.exceltool;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author liy_nby<br>
 * date : 2014-4-22<br>
 * Poi操作Excel的基本方法<br>
 */
public class PoiExcelUtil {
	
	private static Float autoLength = 1.5f;//列宽自适应倍数
	private static String[] alowCollection = {"java.util.ArrayList","java.util.LinkedList"};
	
	/**
	 *  Excel表头汉字列宽度自适应问题（当有双字节字符时，列宽度扩大为原来的autoLength倍，autoLength可以看情况改变）
	 * @param modelSheet
	 * @throws Exception
	 */
	public static void autoSizeGBKColumn(Sheet modelSheet) throws Exception{
		Row columnRow=modelSheet.getRow(0);
		int iTotalCellNum=0;
		for (int i = 0; i <= modelSheet.getLastRowNum(); i++){
			for(int j=0;j<modelSheet.getRow(i).getLastCellNum();j++){
				iTotalCellNum++;
			}
		}
		//列宽自适应，只对英文和数字有效
		for (int i = 0; i < iTotalCellNum; i++){
			modelSheet.autoSizeColumn(i);
		}
		//获取当前列的宽度，然后对比本列的长度，取最大值
		for(int i=0;i<columnRow.getLastCellNum();i++){
			//当string的长度和转换成byte[]后的长度不相等时，说明有双字节的字符（有可能是汉字）
			if(PoiExcelUtil.getValue(columnRow.getCell(i)).getBytes().length!=PoiExcelUtil.getValue(columnRow.getCell(i)).length()){
				//当存在汉字等双字节字符时长度扩充为原来的autoLength倍
				modelSheet.setColumnWidth(i,Math.round(modelSheet.getColumnWidth(i)*autoLength));
			}
		}
	}
	
	/**
	 * 同一个excel文件之间sheet页的复制
	 * @param sourceSheet
	 * @param targetSheet
	 */
	public static void copySheet(Sheet sourceSheet,Sheet targetSheet) throws Exception{
		int iCellIndex=0;
		Row sourceRow = null;
		Row targetRow = null;
		Cell sourceCell = null;
		Cell targetCell = null;

		//从第一行开始复制index=0(getLastRowNum方法返回的是行号，而不是行数)
		for (int i = 0; i <= sourceSheet.getLastRowNum(); i++) {
			sourceRow = sourceSheet.getRow(i);
			targetRow = targetSheet.createRow(i);
			targetRow.setHeight(sourceRow.getHeight());
			//从第一列开始复制（getLastCellNum方法返回的是列数，而不是列号）
			for (int m = sourceRow.getFirstCellNum(); m < sourceRow
					.getLastCellNum(); m++) {
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);
				
				targetSheet.setColumnWidth(iCellIndex, sourceSheet.getColumnWidth(iCellIndex));
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());
				PoiExcelUtil.copyCellValue(sourceCell,targetCell);
				iCellIndex++;
			}
		}
	}
	
	/**
     * 功能：复制原有sheet的合并单元格到新创建的sheet
     * @param sourceSheet
     * @param targetSheet
     */
	public  static void mergerRegion( Sheet sourceSheet,Sheet targetSheet) throws Exception{
		CellRangeAddress oldRange = null;
		CellRangeAddress newRange = null;
        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            oldRange = sourceSheet.getMergedRegion(i);
            newRange = new CellRangeAddress(
                    oldRange.getFirstRow(), oldRange.getLastRow(),
                    oldRange.getFirstColumn(), oldRange.getLastColumn());
            targetSheet.addMergedRegion(newRange);
        }
    }
	
	/**
	 * 获得集合元素的个数（目前只考虑有size()方法的集合）
	 * @param obj
	 * @return
	 */
	public static int getCollectionSize(Object obj){
		try{
			Class type = obj.getClass();
			Method method=type.getMethod("size");
			Object value=method.invoke(obj);
			return Integer.parseInt(method.invoke(obj).toString());
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * 获得集合的某个元素（目前只考虑有get(i)方法的集合）
	 * @param index
	 * @param obj
	 * @return
	 */
	public static Object getCollectionValue(int index,Object obj){
		try{
			Class type = obj.getClass();
			Method method=type.getMethod("get",int.class);
			return method.invoke(obj,index);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 设置集合的某个元素（目前只考虑有set(i,o)和add(o)和get(i)方法的集合）
	 * @param index
	 * @param obj
	 * @param childIndex
	 * @throws Exception
	 */
	public static void setCollectionValue(int index,Object obj,Object childIndex) throws Exception{
		Class type = obj.getClass();
		Method setMethod=type.getMethod("set",int.class,Object.class);
		Method addMethod=type.getMethod("add",Object.class);
		Method getMethod=type.getMethod("get", int.class);
		for(int i=getCollectionSize(obj);i<=index;i++){
			addMethod.invoke(obj, new Object());//防止java.lang.IndexOutOfBoundsException
		}
		setMethod.invoke(obj,index,childIndex);
	}
	
	/**
	 * 判断某对象的类型否算集合类型（目前只支持属性中的两种集合，若要考虑其他集合类型
	 * 请实现上边对应的getCollectionSize方法，getCollectionValue方法，setCollectionValue方法）
	 * @param obj
	 * @return
	 */
	public static boolean isCollection(Object obj){
		Class clz = obj.getClass();
		boolean isCol = false;
		for(int i=0;i<alowCollection.length;i++){
			if(clz.getName().contains(alowCollection[i])){
				isCol = true;
				break;
			}
		}
		return isCol;
	}
	
	/**
	 * 获得新的属性名
	 * @param oldPropertys
	 * @param params
	 * @param index
	 * @throws Exception
	 */
	public static void getNewProperty(List<String> oldPropertys,String[] params,int index) throws Exception{
		if(params==null||params.length!=3){
			throw new Exception("PoiExcelUtil getNewProperty 参数异常");
		}
		StringBuffer sb = new StringBuffer();
		String[] strTemp = null;
		//获得新的属性路径
		for(int j=0;j<oldPropertys.size();j++){
			if(oldPropertys.get(j).contains(params[2])){//只更改相同的属性变量名
				sb.setLength(0);
				strTemp = oldPropertys.get(j).split("\\.");
				if(strTemp[Integer.parseInt(params[0])].contains("[")&&strTemp[Integer.parseInt(params[0])].contains("]")){
					strTemp[Integer.parseInt(params[0])] = strTemp[Integer.parseInt(params[0])].substring(0, strTemp[Integer.parseInt(params[0])].length()-3);
				}
				strTemp[Integer.parseInt(params[0])]=strTemp[Integer.parseInt(params[0])]+"["+index+"]";
				for(int z=0;z<strTemp.length;z++){
					sb.append(strTemp[z]+".");
				}
				sb.setLength(sb.length()-1);
				oldPropertys.set(j, new String(sb));
			}
		}
	}
	
	/**
	 * 判断Excel中某一行是否有循环行，并返回循环的位置和次数，以及循环的属性名称
	 * @param lstPropertys
	 * @param data
	 * @return
	 */
	public static String delColProperty(List<String> lstPropertys,Object data){
		String[] proArray = null;
		String result = null;
		if(lstPropertys==null||lstPropertys.size()<=0){
			result = "-1_0";
		}
		//有集合属性且该集合属性中没有[]该
		for(int i=0;i<lstPropertys.size();i++){
			proArray = lstPropertys.get(i).trim().split("\\.");
			//去掉包名
			String[] tempStr = data.getClass().getName().split("\\.");
			String rootName = tempStr[tempStr.length-1];
			if(!proArray[0].equals(rootName)){
				result = "-1_0";//-1表示不是循环行，0标识循环次数
			}else{
				try{
					Object obj = data;
					for(int j=1;j<proArray.length;j++){
						String temp = proArray[j];
						int sIndex = temp.indexOf("[");
						int eIndex = -1;
						if(sIndex!=-1){
							eIndex = temp.substring(sIndex, temp.length()).indexOf("]")+sIndex;
						}
						if(eIndex>sIndex){//属性为已经循环的集合
							//获得集合
							String name = proArray[j].substring(0,sIndex);
							String methodName = StringUtil.toMethodName("get", name);
							Method method = obj.getClass().getMethod(methodName);
							obj = method.invoke(obj);
							//获得index
							int index = Integer.parseInt(proArray[j].substring(sIndex+1, eIndex));
							//获得集合中的某个元素
							obj = getCollectionValue(index,obj);
						}else{
							String name = proArray[j];
							String methodName = StringUtil.toMethodName("get", name);
							Method method = obj.getClass().getMethod(methodName);
							obj = method.invoke(obj);
							if(isCollection(obj)){
								result = j+"_"+getCollectionSize(obj)+"_";
								for(int z=0;z<=j;z++){//这里循环次数比较少，里边字符串运算内存应该影响不大
									result+=proArray[z];
									if(z!=j){
										result+=".";
									}
								}
								break;
							}
						}
					}
					if(!result.equals("-1_0")){
						break;
					}
				}catch(Exception e){
					result = "-1_0";
				}
			}
		}
		return result;
	}
	
	/**
	 * 把字符串转换成class所对应的常用类型，若class不对应常用类型则返回null
	 * @param c
	 * @param value
	 * @return
	 */
	public static Object changeStringToOther(Class c,String value) throws Exception{
		String typeName = c.getName();
		if("int".equals(typeName)){
			return Integer.parseInt(value);
		}else if("float".equals(typeName)){
			return Float.parseFloat(value);
		}else if("double".equals(typeName)){
			return Double.parseDouble(value);
		}else if("short".equals(typeName)){
			return Short.parseShort(value);
		}else if("long".equals(typeName)){
			return Long.parseLong(value);
		}else if("boolean".equals(typeName)){
			return Boolean.parseBoolean(value);
		}else if("char".equals(typeName)){
			return value.charAt(0);
		}else if("byte".equals(typeName)){
			return Byte.parseByte(value);
		}else if("java.util.Date".equals(typeName)){
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return sim.parse(value);
		}else if("java.lang.String".equals(typeName)){
			return value;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据属性路径设置值
	 * @param propertyName
	 * @param value
	 * @param data
	 */
	public static void setValueByProperty(String propertyName,String value,Object data) throws Exception{
		String[] proArray = propertyName.trim().split("\\.");
		for(int i=0;i<proArray.length;i++){
			proArray[i] = proArray[i].trim();
		}
		if(data==null){
			throw new Exception("PoiExcelUtil setValueByProperty 参数data不能为空");
		}
		//去掉包名
		String[] tempStr = data.getClass().getName().split("\\.");
		String rootName = tempStr[tempStr.length-1];
		if(!proArray[0].equals(rootName)){
			
		}else{
			String pName = null;
			String eName = null;
			String getMethodName = null;
			Method getMethod = null;
			String setMethodName = null;
			Method setMethod = null;
			Object child = null;
			Object childIndex = null;
			
			Object obj = data;
			for(int j=1;j<proArray.length;j++){
				String temp = proArray[j];
				int sIndex = temp.indexOf("[");
				int eIndex = -1;
				if(sIndex!=-1){
					eIndex = temp.substring(sIndex, temp.length()).indexOf("]")+sIndex;
				}
				if(eIndex>sIndex){//属性为已经循环的集合
					//属性格式肯定为^{TestUniqueObject.childs<test.TestUniqueObject>[1].name}
					int right = sIndex-1;
					int left = temp.indexOf("<");
					pName = proArray[j].substring(0,left);//集合属性名
					eName = proArray[j].substring(left+1,right).replaceAll("_", ".");//包含完整包名的类名
					getMethodName = StringUtil.toMethodName("get", pName);
					setMethodName = StringUtil.toMethodName("set", pName);
					getMethod = obj.getClass().getMethod(getMethodName);
					child = getMethod.invoke(obj);
					if(child == null){
						Field fd = obj.getClass().getDeclaredField(pName);
						child = fd.getType().newInstance();
						setMethod = obj.getClass().getMethod(setMethodName, fd.getType());
						setMethod.invoke(obj, child);
					}
					//获得index
					int index = Integer.parseInt(proArray[j].substring(sIndex+1, eIndex));
					if(j!=proArray.length-1){
						//获得集合中的某个元素
						childIndex = getCollectionValue(index,child);
						if(childIndex == null){
							childIndex = Class.forName(eName).newInstance();
							PoiExcelUtil.setCollectionValue(index, child, childIndex);
						}
						obj = childIndex;
					}else{
						PoiExcelUtil.setCollectionValue(index, child, PoiExcelUtil.changeStringToOther(Class.forName(eName), value));
					}
				}else{
					pName = proArray[j];
					getMethodName = StringUtil.toMethodName("get", pName);
					setMethodName = StringUtil.toMethodName("set", pName);
					getMethod = obj.getClass().getMethod(getMethodName);
					if(j!=proArray.length-1){
						child = getMethod.invoke(obj);
						if(child == null){
							Field fd = obj.getClass().getDeclaredField(pName);
							child = fd.getType().newInstance();
							setMethod = obj.getClass().getMethod(setMethodName, fd.getType());
							setMethod.invoke(obj, child);
						}
						obj = child;
					}else{
						Field fd = obj.getClass().getDeclaredField(pName);
						setMethod = obj.getClass().getMethod(setMethodName, fd.getType());
						setMethod.invoke(obj, PoiExcelUtil.changeStringToOther(fd.getType(), value));
					}
				}
			}
		}
	}
	
	/**
	 * 根据属性路径获得值
	 * @param propertyName
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String getValueByProperty(String propertyName,Object data) {
		String[] proArray = propertyName.trim().split("\\.");
		for(int i=0;i<proArray.length;i++){
			proArray[i] = proArray[i].trim();
		}
		//去掉包名
		String[] tempStr = data.getClass().getName().split("\\.");
		String rootName = tempStr[tempStr.length-1];
		if(!proArray[0].equals(rootName)){
			return " ";
		}else{
			try{
				Object obj = data;
				for(int i=1;i<proArray.length;i++){
					String temp = proArray[i];
					int sIndex = temp.indexOf("[");
					int eIndex = -1;
					if(sIndex!=-1){
						eIndex = temp.substring(sIndex, temp.length()).indexOf("]")+sIndex;
					}
					if(eIndex>sIndex){//属性为集合
						//获得集合
						String name = proArray[i].substring(0,sIndex);
						String methodName = StringUtil.toMethodName("get", name);
						Method method = obj.getClass().getMethod(methodName);
						obj = method.invoke(obj);
						//获得index
						int index = Integer.parseInt(proArray[i].substring(sIndex+1, eIndex));
						//获得集合中的某个元素
						obj = getCollectionValue(index,obj);
					}else{
						String name = proArray[i];
						String methodName = StringUtil.toMethodName("get", name);
						Method method = obj.getClass().getMethod(methodName);
						obj = method.invoke(obj);
					}
				}
				return obj.toString();
			}catch(Exception e){
				return " ";
			}
		}
	}
	
	/**
	 * 获得某一列的值,并转换为string类型
	 * @param excelCell
	 * @return
	 */
	public static String getValue(Cell excelCell) {
		//处理单元格内容
		if (excelCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(excelCell.getBooleanCellValue());
		} else if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(excelCell.getNumericCellValue());
		} else {
			return String.valueOf(excelCell.getStringCellValue());
		}
	}
	
	/**
	 * 把某一列的值赋值给另外一列，并保持类型不变
	 * @param sourceCell
	 * @param targetCell
	 * @throws Exception
	 */
	public static void copyCellValue(Cell sourceCell,Cell targetCell) throws Exception{
		//处理单元格内容
        switch (sourceCell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	            targetCell.setCellValue(sourceCell.getRichStringCellValue());
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	            targetCell.setCellValue(sourceCell.getNumericCellValue());
	            break;
	        case Cell.CELL_TYPE_BLANK:
	            targetCell.setCellType(Cell.CELL_TYPE_BLANK);
	            break;
	        case Cell.CELL_TYPE_BOOLEAN:
	            targetCell.setCellValue(sourceCell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_ERROR:
	            targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
	            break;
	        case Cell.CELL_TYPE_FORMULA:
	            targetCell.setCellFormula(sourceCell.getCellFormula());
	            break;
	        default:
	        	throw new Exception("PoiExcelUtil copyCellValue 未知类型异常");
        }
	}
	
	/**
	 * 把excel中的第startRow行完全复制（包括样式，值等），然后在第startRow之后，
	 * 插入rows行和第startRow行完全相同的行(并且还要处理循环行的父子关系)
	 * @param sheet
	 * @param starRow
	 * @param rows
	 * @param propertyName
	 * @throws Exception
	 */
	public static void insertRow(Sheet sheet, int starRow,int rows,String propertyName) throws Exception{
		int templateRow =  starRow;
		//向下移动的过程中隐藏行会显示出来，所以在移动之前先记录复制行之后的隐藏块
		List<Integer> lstZeroHeightRow = new ArrayList<Integer>();
		for(int i=0;i<sheet.getLastRowNum();i++){
			if(sheet.getRow(i).getZeroHeight()&&i>starRow){
				lstZeroHeightRow.add(i);
			}
		}
		//向下移动的过程中模板行的合并块的结构可能会被破坏，所以在移动之前先记录模板行的合并块的结构
		List<CellRangeAddress> lstOldCellRangeAddress = new ArrayList<CellRangeAddress>();
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress tempRange = sheet.getMergedRegion(i);
			int lastRow = tempRange.getLastRow();
	        int firstRow = tempRange.getFirstRow();
	        if(lastRow>=templateRow&&firstRow<=templateRow){
	        	lstOldCellRangeAddress.add(tempRange);
	        	sheet.removeMergedRegion(i);//记录并删除（反正也可能会被破坏）
	 	        i--;
	        }
		}
		//存在尾部时增加新的列必须把部分列向下移动才能增加
		try{
			sheet.shiftRows(starRow+1, sheet.getLastRowNum(), rows, true, false);
		}catch(IllegalArgumentException e){
			//不存在尾部时上述方法会报错，冷处理即可
		}
		//把移动过程中显示出来的隐藏行再次隐藏
		for(int i=0;i<lstZeroHeightRow.size();i++){
			sheet.getRow(lstZeroHeightRow.get(i)+rows).setHeight((short)0);
		}
		//复制
		starRow = starRow - 1;
		for (int i = 0; i < rows; i++) {
			Row sourceRow = null;
			Row targetRow = null;
			Cell sourceCell = null;
			Cell targetCell = null;
			starRow = starRow + 1;
			sourceRow = sheet.getRow(starRow);
			targetRow = sheet.createRow(starRow + 1);
			targetRow.setHeight(sourceRow.getHeight());
			for (int m = sourceRow.getFirstCellNum(); m < sourceRow
					.getLastCellNum(); m++) {
				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());
				PoiExcelUtil.copyCellValue(sourceCell,targetCell);
			}
		}
		//还原删除的合并块
		for(int i=0;i<lstOldCellRangeAddress.size();i++){
			sheet.addMergedRegion(lstOldCellRangeAddress.get(i));
		}
		//合并块处理
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress oldRange = sheet.getMergedRegion(i);
			int lastRow = oldRange.getLastRow();
	        int firstRow = oldRange.getFirstRow();
	        int lastColumn = oldRange.getLastColumn();
	        int firstColumn = oldRange.getFirstColumn();
	        //跨行合并块
	        if(lastRow>=templateRow&&firstRow<=templateRow&&lastRow!=firstRow){
	        	oldRange.setLastRow(lastRow+rows);
	        }
	        //跨列不跨行
	        if(lastRow==firstRow&&lastRow==templateRow&&lastColumn>firstColumn){
	        	if(propertyName!=null){
		        	if(!PoiExcelUtil.getValue(sheet.getRow(firstRow).getCell(firstColumn)).contains(propertyName)){//非兄弟节点或者非子节点
		        		oldRange.setLastRow(lastRow+rows);
		        	}else{
		        		for(int j=1;j<=rows;j++){
			        		CellRangeAddress newRange = new CellRangeAddress(firstRow+j, lastRow+j, firstColumn, lastColumn);
			        		sheet.addMergedRegion(newRange);
			        	}
		        	}
		        }else{
		        	for(int j=1;j<=rows;j++){
		        		CellRangeAddress newRange = new CellRangeAddress(firstRow+j, lastRow+j, firstColumn, lastColumn);
		        		sheet.addMergedRegion(newRange);
		        	}
		        }
	        }
		}
	}
}
