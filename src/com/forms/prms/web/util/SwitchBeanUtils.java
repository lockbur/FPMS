package com.forms.prms.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;


public class SwitchBeanUtils 
{
	public static void deaLingCollection(Object obj){
		if(obj == null){
			return ;
		}
		if(obj instanceof Collection){
			Collection coll=(Collection)obj;
			Iterator itr=coll.iterator();
			while(itr.hasNext()){
				Object v=itr.next();
				if(v instanceof Collection){
					deaLingCollection(v);
				}else{
					dealBean(v);
				} 
			}
		}
	}
	//只做简单的bean处理
	public static void dealBean(Object obj){
		if(obj == null){
			return ;
		}
		Class beanClass=obj.getClass();
		Method[] methods=beanClass.getMethods();
		for(Method method : methods){
			String methodName=method.getName();
			Class retType=method.getReturnType();
			Class[] paramClass=method.getParameterTypes();
			//只处理get方法
			if(paramClass.length == 0){
				if("java.lang.String".equals(retType.getName())){
					if("get".equals(methodName.substring(0,3))){
						try
						{	
							Object value=method.invoke(obj);
							if(value instanceof String){
								String str=(String)value;
								if(StringUtils.isNotBlank(str)){
									StringBuffer strBuffer=new StringBuffer();
									for(int i=0;i<str.length();i++){
										String s=str.charAt(i)+"";
										if("'".equals(s)){
											s="\\"+s;
										}else if("\"".equals(s)){
											s="\\"+s;
										}else if("\\".equals(s)){
											s="\\"+s;
										}else if("\r".equals(s)){
											s="\\"+s;
										}else if("\n".equals(s)){
											s="\\"+s;
										}
										strBuffer.append(s);
									}
									String setMetodName="set"+methodName.substring(3);
									Method setMethod=beanClass.getMethod(setMetodName, String.class);
									if(setMethod != null){
										setMethod.invoke(obj, strBuffer.toString());
									}
								}
							}
						}
						catch (SecurityException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (NoSuchMethodException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (IllegalArgumentException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (IllegalAccessException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (InvocationTargetException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
					}
				}
			}
		}
	}
}
