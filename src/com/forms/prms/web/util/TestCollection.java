package com.forms.prms.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCollection
{
	private String name;
	private String pass;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPass()
	{
		return pass;
	}
	public void setPass(String pass)
	{
		this.pass = pass;
	}
	
	
	public static void main(String args[]){
		List<TestCollection> list=new ArrayList<TestCollection>();
		TestCollection t1=new TestCollection();
		t1.setName("d''涛");
		t1.setPass("hello");
		TestCollection t2=new TestCollection();
		t2.setName("d'\"\"\"'涛");
		t2.setPass("world");
		list.add(t1);
		list.add(t2);
		SwitchBeanUtils.deaLingCollection(list);
		for(TestCollection t : list){
			System.out.println(t.getName()+" -- >"+t.getPass());
		}
		Map<String,TestCollection> map=new HashMap<String,TestCollection>();
		map.put("1",t1 );
		map.put("2",t2 );
		SwitchBeanUtils.deaLingCollection(map);
		for(String key : map.keySet() ){
			TestCollection t=map.get(key);
			System.out.println(t.getName()+" -- >"+t.getPass());
		}
	}
}
