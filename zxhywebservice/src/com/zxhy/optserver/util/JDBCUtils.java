package com.zxhy.optserver.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	private static Connection conn = null;
	// 静态代码块
	static {
		try {
			// 1 使用Properties处理流
			// 使用load()方法加载指定的流
			Properties props = new Properties();
//			Reader is = new FileReader("jdbc.properties");
			InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			System.out.println(is);
			props.load(is);
			// 2 使用getProperty(key)，通过key获得需要的值，	
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			user = props.getProperty("username");
			password = props.getProperty("password");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得连接
	 */
	public static Connection getConnection() {
		try {
			// 1 注册驱动
			Class.forName(driver);
			// 2 获得连接
			 conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 关闭数据库连接
	 */
	public static void close() {
		if(conn != null) {
			try {
				conn.close();  //关闭数据库链接
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}