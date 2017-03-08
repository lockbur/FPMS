package com.forms.prms.tool.exceltool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * @author liy_nby<br>
 * date : 2014-4-22<br>
 * String工具类<br>
 */
public class StringUtil {
	
	/**
	 * 把某个对象中类型是字符串且具有get和set方法的属性转换成一定编码
	 * 只转换String类型，而且只支持转换自身的属性，集成的不能转换
	 * @param obj
	 * @param oldType
	 * @param newType
	 */
	public static void decode(Object obj,String oldType,String newType){
		Field[] field = obj.getClass().getDeclaredFields();
		for(int i=0;i<field.length;i++){
			try{
				Method setMethod = obj.getClass().getMethod(toMethodName("set",field[i].getName()), String.class);
				Method getMethod = obj.getClass().getMethod(toMethodName("get",field[i].getName()));
				String typeName = field[i].getType().getName();
				if("java.lang.String".equals(typeName)&&setMethod!=null&&getMethod!=null){
					String oldValue = getMethod.invoke(obj).toString();
					String newValue = new String(oldValue.getBytes(oldType),newType);
					setMethod.invoke(obj, newValue);
				}
			}catch(Exception e){
				continue;//异常不处理该属性
			}
		}
	}
	
	/**
	 * 首字母转小写   
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirstOne(String str)     { 
		if(Character.isLowerCase(str.charAt(0)))      
			return str;       
		else           
			return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();   
	}    
	
	/**
	 * 首字母转大写  
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirstOne(String str)     {     
		if(Character.isUpperCase(str.charAt(0)))        
			return str;   
		else    
			return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();    
	} 
	
	/**
	 * 属性名转换成其对应的默认get方法名或者set方法名
	 * @param methodName
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public static String toMethodName(String methodName,String propertyName) throws Exception{
		//方法名的一些规则
		if(!("get".equals(methodName)||"set".equals(methodName))){
			throw new Exception("StringUtil toMethodName 该方法最好用于把属性名转换成其对应的默认get方法名或者set方法名!");
		}
		//属性名的一些规则
		if(propertyName==null||propertyName.length()<=0){
			throw new Exception("StringUtil toMethodName 参数异常!");
		}
		String temp = propertyName.charAt(0)+"";
		if(!temp.toLowerCase().equals(temp)){
			throw new Exception("StringUtil toMethodName 属性名的第一个字母应该是小写!");
		}
		//判断属性名头小写字母的个数
		int iNum = 0;
		for(int i=0;i<propertyName.length();i++){
			temp = propertyName.charAt(i)+"";
			if(!temp.toLowerCase().equals(temp)){
				break;
			}else{
				iNum++;
			}
		}
		if(iNum<=0){
			throw new Exception("StringUtil toMethodName 参数异常!");
		}else if(iNum ==1&&propertyName.length()>1){
			return methodName+propertyName;
		}else{
			return methodName+toUpperCaseFirstOne(propertyName);
		}
	}
	
	/**
	 * 将字符串转化为map对象
	 * @param jsonStr
	 * @return
	 */
	public static Map parserToMap(String jsonStr){
		Map map=new HashMap();  
	    JSONObject json=JSONObject.fromObject(jsonStr);  
	    Iterator keys=json.keys();  
	    while(keys.hasNext()){  
	        String key=(String) keys.next();  
	        String value=json.get(key).toString();  
	        if(value.startsWith("{")&&value.endsWith("}")){  
	            map.put(key, parserToMap(value));  
	        }else{  
	            map.put(key, value);  
	        }  
	  
	    }
	    return map;
	}
}
