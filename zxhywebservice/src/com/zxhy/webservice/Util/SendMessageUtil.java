package com.zxhy.webservice.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SendMessageUtil {
	/**
	 * 根据传入的终端号和message，往tbl_upgradesendcontent中添加数据
	 * @param antenna
	 * @param message
	 */
	public static void sendContent(String antenna,String message,String flag)
	{
		try 
		{
		Connection conn = JDBCUtils.getConnection();
		String sql1 = "INSERT INTO tbl_upgradesendcontent(mid,content,flag) VALUES(?,?,?) ";
		
		PreparedStatement pstat = conn.prepareStatement(sql1);
		
		pstat.setString(1, antenna);
		pstat.setString(2, message);
		pstat.setString(3, flag);
		pstat.executeUpdate();
		
		
		conn.commit();
		System.out.println(antenna+":数据已经添加到tbl_upgradesendcontent"+"..."+"类型为："+flag);
		pstat.close();
		conn.close();
		
		}catch(Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * 查询对应antenna的online值
	 * @param antenna
	 * @return
	 */
	public static int selectOnlineByMid(String antenna)
	{
		int online = 2;
		try
		{
		
		Connection conn = JDBCUtils.getConnection();
		String sql1 = "SELECT * from tbl_terminals where TermID = ?";
		PreparedStatement pstat = conn.prepareStatement(sql1);
		pstat.setString(1, antenna);
		ResultSet rs = pstat.executeQuery();
		
		while(rs.next()) 
		{
			online = rs.getInt("ThisStatus");
		}
		
		rs.close();
		pstat.close();
		conn.close();
		
		}catch(Exception e ) 
		{
			e.printStackTrace();
		}
		return  online;
	}
	 
	
	 /**
	  * 根据传入的天线号删除tbl_upgradetermstate中对应的数据
	  * @param antenna
	  */
	public static void deleteByMid(String antenna)
	{
		try {
		Connection conn = JDBCUtils.getConnection();
		String sql1 = "DELETE FROM tbl_upgradetermstate where mid = ?";
		PreparedStatement pstat = conn.prepareStatement(sql1);
		pstat.setString(1, antenna);
		pstat.executeUpdate();
		
		conn.commit();
		pstat.close();
		conn.close();
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
	}
	
}
