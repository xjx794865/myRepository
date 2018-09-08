package com.zxhy.optserver.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DebugUtil
{
	private static ArrayList<String> debugs = new ArrayList<String>();
	private static String format = "yyyy-MM-dd HH:mm:ss";
	
	//系统级别
	public final static int LEVEL_1 = 1;
	//运行级别
	public final static int LEVEL_2 = 2;
	//调试级别
	public final static int LEVEL_3 = 3;
	
	public static int CURRENT_LEVEL = LEVEL_3;
	
	public static synchronized void addDebug(String info, int level)
	{
		if (level <= CURRENT_LEVEL)
		{
			if (debugs.size() >= 400)
			{
				debugs.remove(0);
			}
			
			String debug = systemTime();
			debug = debug + "  " + info;
			debugs.add(debug);
			
			System.out.println(debug);
		}
	}
	
	public static synchronized String getDebugs()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("");
		
		if (debugs.size() > 0)
		{
			if (debugs.size() > 1)
			{
				for (int i = 0; i < (debugs.size() - 1); i ++)
				{
					sb.append(debugs.get(i));
					sb.append("\n\n");
				}
			}
			
			sb.append(debugs.get(debugs.size() - 1)).append("\n");
		}
		
		return sb.toString();
	}
	
	public static void clear()
	{
		debugs.clear();
	}
	
	public static String getExpMessage(Exception exp)
	{
		StackTraceElement[] trace = exp.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append(exp.toString()).append("\r\n");
		for (StackTraceElement s : trace) 
		{
			sb.append("\tat ").append(s).append("\r\n");
	    }
		
		return sb.toString();
	}
	
	/**
	 * 获取固定格式的系统当前时间  yyyyMMddHHmmss
	 * @return
	 */
	public static String systemTime()
	{
		TimeZone tz = TimeZone.getTimeZone("Etc/GMT-8");
		TimeZone.setDefault(tz);
		
		Date current = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateTime = sdf.format(current);
		
		return dateTime;
	}
}
