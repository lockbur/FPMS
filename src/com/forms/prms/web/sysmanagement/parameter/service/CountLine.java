package com.forms.prms.web.sysmanagement.parameter.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CountLine 
{
	public static int sum = 0;
	public void countFileLine() throws Exception
	{
		File dir = new File("E:\\szworkspace\\ERP");
		showFileLine(dir);
		System.out.println(this.sum);
	}
	private void showFileLine(File dir) throws Exception
	{
		File[] fs = dir.listFiles();
		for(int i=0;i<fs.length;i++)
		{
			if(fs[i].isFile())
			{
				FileReader fr = new FileReader(fs[i]);
				BufferedReader br = new BufferedReader(fr);
				int fileLine = 0;
				while(br.readLine()!=null)
				{
					fileLine++;
				}
				this.sum += fileLine;	
			}
			else if(fs[i].isDirectory())
			{
				try
				{
					showFileLine(fs[i]);
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception 
	{
		new CountLine().countFileLine();
	}
}
