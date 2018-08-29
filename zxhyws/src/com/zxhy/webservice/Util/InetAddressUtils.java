package com.zxhy.webservice.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zxhy.webservice.ws.UpgradeVersionImpl;

public class InetAddressUtils {
	
	public static String returnAddress(String antenna_no) throws IOException {
		
		Logger logger = Logger.getLogger(InetAddressUtils.class);
		Properties props = new Properties();
		props.load(InetAddressUtils.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		String address = null;
		
		Connection conn = JDBCUtils.getConnection();
		String sql = "SELECT address FROM tb_inetaddress WHERE antenna_no = ?";
		
		try {
			PreparedStatement stat= conn.prepareStatement(sql);
			stat.setString(1, antenna_no);
			ResultSet rs = stat.executeQuery();
			
			
			
		    while(rs.next()) {
		    	address = rs.getString("address");
		    }

		    rs.close();
		    stat.close();
		    conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		
		
		if(address == null) {
			logger.debug("ip地址为空");
		}
		return address;
		
	}
	
	public static int returnPort(String antenna_no) throws IOException {
		
		Logger logger = Logger.getLogger(InetAddressUtils.class);
		Properties props = new Properties();
		props.load(InetAddressUtils.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
		//初始化端口号
		int port = 0;
		
		Connection conn = JDBCUtils.getConnection();
		String sql = "SELECT port FROM tb_inetaddress WHERE antenna_no = ?";
		
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, antenna_no);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				port = rs.getInt("port");
			}

			rs.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		
		
		
		
		return port;
	}

}
