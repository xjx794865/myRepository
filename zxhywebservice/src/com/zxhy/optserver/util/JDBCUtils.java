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
	// ��̬�����
	static {
		try {
			// 1 ʹ��Properties������
			// ʹ��load()��������ָ������
			Properties props = new Properties();
//			Reader is = new FileReader("jdbc.properties");
			InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			System.out.println(is);
			props.load(is);
			// 2 ʹ��getProperty(key)��ͨ��key�����Ҫ��ֵ��	
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			user = props.getProperty("username");
			password = props.getProperty("password");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �������
	 */
	public static Connection getConnection() {
		try {
			// 1 ע������
			Class.forName(driver);
			// 2 �������
			 conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * �ر����ݿ�����
	 */
	public static void close() {
		if(conn != null) {
			try {
				conn.close();  //�ر����ݿ�����
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}