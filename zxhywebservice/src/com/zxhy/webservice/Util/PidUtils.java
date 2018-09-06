package com.zxhy.webservice.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PidUtils {
    /**
     * 遍历tb_upgraderecord的Pid，将Pid = 0 的所有antenna_no存进数组中.
     * @return
     * @throws SQLException
     * @throws IOException 
     */
	public static String[] selectAntennaByPid() throws SQLException, IOException {
		Connection conn = JDBCUtils.getConnection();
		List<String> list = new ArrayList<String>();

		String sql = "SELECT antenna_no from tbl_upgraderecord where Pid = 0";

		PreparedStatement psata = conn.prepareStatement(sql);

		ResultSet rs = psata.executeQuery();

		while (rs.next()) {
			list.add(rs.getString("antenna_no"))  ;

		}
		//将list<String>转成string[].
		String[] arr = list.toArray(new String[list.size()]);
		return arr;
		 
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
