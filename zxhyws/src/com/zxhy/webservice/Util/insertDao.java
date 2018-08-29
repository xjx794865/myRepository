package com.zxhy.webservice.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class insertDao {
	
	public static void insertByAntennas(String[] antennas, String content) throws IOException {
		String GUID = StringUtil.toGuid(content);
		Connection conn = JDBCUtils.getConnection();
		try {
			//关闭自动提交 打开事务
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);
			
			for(String antenna_no : antennas) {
			pstat.setString(1, antenna_no);	
			pstat.setString(2, GUID);
			pstat.addBatch();
			}
			pstat.executeBatch();
			
			String sql2 = "INSERT INTO 	tb_upgradeContent(GUID,content,dateTime) VALUES(?,?,?)";
			PreparedStatement pstat2 = conn.prepareStatement(sql2);
			pstat2.setString(1, GUID);
			pstat2.setString(2, content);
			//将util中的date与sql的date相匹配
			Date date  = new Date();
			Timestamp timestamp=new Timestamp(date.getTime());
			pstat2.setTimestamp(3, timestamp);
			pstat2.executeUpdate();
			
			
			conn.commit();
			conn.setAutoCommit(true);
			pstat2.close();
			pstat.close();
		    conn.close();
			//JDBCUtils.closeConnection();
		
			
		}catch(Exception e ) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e1);
			}
		}
	}
}
