package com.zxhy.webservice.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class isSuccessUpgrade {
    /**
     * 参数升级
     * 遍历tbl_upgraderecord的successUpgrade，将successUpgrade = 0 的所有antenna_no存进list中.
     * @return
     * @throws SQLException
     * @throws IOException 
     */
	public static Map<String,String> selectAntennapBySuccessUpgrade()
	{
		Connection conn = JDBCUtils.getConnection();
		Map<String,String> p = new HashMap<String,String>();
		try
		{
		String sql = "SELECT antenna_no,content FROM tbl_upgradecontent,tbl_upgraderecord where tbl_upgradecontent.GUID = tbl_upgraderecord.GUID AND successUpgrade =0";
        PreparedStatement psata = conn.prepareStatement(sql);
		ResultSet rs = psata.executeQuery();

		while (rs.next()) 
		{
			p.put(rs.getString("antenna_no"), rs.getString("content"));
		}
		//将list<String>转成string[].
		//String[] arr = list.toArray(new String[list.size()]);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return p;
		 
	}
	
	/**
	 * 程序升级
	 * 遍历tbl_programerupgraderecord的successUpgrade，将successUpgrade = 0 的所有antenna_no存进list中.
	 * @return
	 */
	public static Map<String,String> selectAntennaxBySuccessUpgrade(){
		Connection conn = JDBCUtils.getConnection();
	    Map<String,String> x = new HashMap<String,String>();
		try
		{
		String sql = "SELECT antenna_no,content FROM tbl_programerupgradecontent,tbl_programerupgraderecord where tbl_programerupgradecontent.GUID = tbl_programerupgraderecord.GUID AND successUpgrade =0";
        PreparedStatement psata = conn.prepareStatement(sql);
		ResultSet rs = psata.executeQuery();

		while (rs.next()) 
		{
			x.put(rs.getString("antenna_no"), rs.getString("content"));
		}
		//将list<String>转成string[].
		//String[] arr = list.toArray(new String[list.size()]);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return x;
		 
	}
	
	/**
	 * 卫星参数升级
	 * 遍历tbl_satupgraderecord的successUpgrade，将successUpgrade = 0 的所有antenna_no存进list中.
	 * @return
	 */
	public static Map<String,String> selectAntennasBySuccessUpgrade(){
		Connection conn = JDBCUtils.getConnection();
		Map<String,String> s = new HashMap<String,String>();
		try
		{
		String sql = "SELECT antenna_no,content FROM tbl_satupgradecontent,tbl_satupgraderecord where tbl_satupgradecontent.GUID = tbl_satupgraderecord.GUID AND successUpgrade =0";
        PreparedStatement psata = conn.prepareStatement(sql);
		ResultSet rs = psata.executeQuery();

		while (rs.next()) 
		{
			s.put(rs.getString("antenna_no"), rs.getString("content"));
		}
		//将list<String>转成string[].
		//String[] arr = list.toArray(new String[list.size()]);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
		 
	}
	
	/**
	 * 根据传入的天线号来获得Pid的值
	 * @param antenna_no
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static int selectPidbyAntenna(String antenna_no) throws IOException, SQLException {
		
		int Pid = 0;
		
		Connection conn = JDBCUtils.getConnection();
		
		String sql = "SELECT Pid from tbl_upgraderecord where antenna_no = ?";
		
		PreparedStatement psata = conn.prepareStatement(sql);
		
		psata.setString(1, antenna_no);
		
		ResultSet rs = psata.executeQuery();
		
		while(rs.next()) {
			Pid = rs.getInt("Pid");
		}
		
		
		return Pid;
		
	}
	

}
