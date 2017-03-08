package com.forms.prms.web.util;

import java.lang.reflect.Method;
import java.net.URLDecoder;


public class UrlEncodingUtils
{
	//转码
	public static void decode(Object obj)throws Exception{
		Class objClass=obj.getClass();
		Method[] methods=objClass.getMethods();
		for(Method method : methods){
			String methodName=method.getName();
			if("get".equals(methodName.substring(0, 3))){
				Object  retValue=method.invoke(obj);
				if(retValue != null && retValue != ""){
					Class retType=method.getReturnType();
					if("java.lang.String".equals(retType.getName())){
						String decodeValue=URLDecoder.decode((String)retValue, "UTF-8");
						Method m=objClass.getMethod("set"+methodName.substring(3),String.class);
						if(m != null) {
							m.invoke(obj, decodeValue);
						}
					}
				}
			}
		}
		return ;
	}
}
