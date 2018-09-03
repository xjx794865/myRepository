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
	 * �������ӳ�BasicDataSource
	 */
	public static BasicDataSource dataSource = new BasicDataSource();

	private static int count = 0;

	// ��̬�����
	static  {
		try {
			// 1 ʹ��Properties������
			// ʹ��load()��������ָ������
			Properties props = new Properties();
			// Reader is = new FileReader("jdbc.properties");
			InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			// System.out.println(is);
			props.load(is);
			// 2 ʹ��getProperty(key)��ͨ��key�����Ҫ��ֵ��
			

			dataSource.setDriverClassName(props.getProperty("driver")); // ����Ҫ���ӵ����ݿ������
			dataSource.setUrl(props.getProperty("url")); // ָ��Ҫ���ӵ����ݿ��ַ
			dataSource.setUsername(props.getProperty("username")); // ָ��Ҫ�������ݵ��û���
			dataSource.setPassword(props.getProperty("password")); // ָ��Ҫ�������ݵ�����
	       

		} catch (Exception e) {
			//throw new RuntimeException(e);
			e.printStackTrace();
		}
	}
	
	 //�����ӳ��л�ȡһ������
    public static Connection getConnection() throws IOException{
    	Logger logger = Logger.getLogger(JDBCUtils.class);
		Properties props = new Properties();
		props.load(JDBCUtils.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
    	
        Connection connection = null;
        try{
        	
            connection = dataSource.getConnection();
            count++;
            logger.debug("�������ݿ����"+count);
           
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
	 * �ر����ݿ�����
	 */
	public static void release(Connection conn ) {
	

		if (conn != null) {
			try {
				// ��Connection���Ӷ��󻹸����ݿ����ӳ�
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
