package com.zxhy.webservice.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class JDBCUtils {



	/*
	 * 创建连接池BasicDataSource
	 */
	public static BasicDataSource dataSource = new BasicDataSource();

	private static int count = 0;

	// 静态代码块
	static  {
		try {
			// 1 使用Properties处理流
			// 使用load()方法加载指定的流
			Properties props = new Properties();
			// Reader is = new FileReader("jdbc.properties");
			InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			// System.out.println(is);
			props.load(is);
			// 2 使用getProperty(key)，通过key获得需要的值，
			

			dataSource.setDriverClassName(props.getProperty("driver")); // 这是要连接的数据库的驱动
			dataSource.setUrl(props.getProperty("url")); // 指定要连接的数据库地址
			dataSource.setUsername(props.getProperty("username")); // 指定要连接数据的用户名
			dataSource.setPassword(props.getProperty("password")); // 指定要连接数据的密码
	       

		} catch (Exception e) {
			//throw new RuntimeException(e);
			e.printStackTrace();
		}
	}
	
	 //从连接池中获取一个连接
    public static Connection getConnection() throws IOException{
    	Logger logger = Logger.getLogger(JDBCUtils.class);
		Properties props = new Properties();
		props.load(JDBCUtils.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
    	
        Connection connection = null;
        try{
        	
            connection = dataSource.getConnection();
            count++;
            logger.debug("连接数据库次数"+count);
           
    }catch(SQLException e){
           logger.error(e.getMessage());
           throw new RuntimeException(e);
        }
        try {
           connection.setAutoCommit(false);
        } catch (SQLException e) {
           logger.error(e.getMessage());
           throw new RuntimeException(e);
        }

        return connection;
   }

	/**
	 * 关闭数据库连接
	 */
	public static void release(Connection conn ) {
	

		if (conn != null) {
			try {
				// 将Connection连接对象还给数据库连接池
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
