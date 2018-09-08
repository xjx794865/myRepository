package com.zxhy.webservice.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SendMessageUtil {
	/**
	 * ���ݴ�����ն˺ź�message����tbl_upgradesendcontent���������
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
		System.out.println(antenna+":�����Ѿ���ӵ�tbl_upgradesendcontent"+"..."+"����Ϊ��"+flag);
		pstat.close();
		conn.close();
		
		}catch(Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * ��ѯ��Ӧantenna��onlineֵ
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
	  * ���ݴ�������ߺ�ɾ��tbl_upgradetermstate�ж�Ӧ������
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
