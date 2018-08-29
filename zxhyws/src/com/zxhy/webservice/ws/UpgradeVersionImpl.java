package com.zxhy.webservice.ws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zxhy.webservice.Util.JDBCUtils;
import com.zxhy.webservice.Util.StringUtil;
import com.zxhy.webservice.Util.ThreadPool;
import com.zxhy.webservice.Util.UDPClient;


@WebService
public class UpgradeVersionImpl implements UpgradeVersion {
	
	
	/**
	 * 首先先将发送信息填入tb_upgradeRecord和tb_upgradeContent表中，并进行事务管理 之后将要发送的信息打包发送出去
	 */
	@Override
	public void insertAndSendByNo(String antenna_no, String content) throws IOException {
		Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
		Properties props = new Properties();
		props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
		String GUID = StringUtil.toGuid(content);

		// 从连接池里获取数据连接
		Connection conn = JDBCUtils.getConnection(); 
		try {
			// 关闭自动提交
			conn.setAutoCommit(false);

			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);
			pstat.setString(1, antenna_no);
			pstat.setString(2, GUID);
			pstat.executeUpdate();

			String sql2 = "INSERT INTO 	tb_upgradeContent(GUID,content,dateTime) VALUES(?,?,?)";
			PreparedStatement pstat2 = conn.prepareStatement(sql2);
			pstat2.setString(1, GUID);
			pstat2.setString(2, content);
			// 将util中的date与sql的date相匹配
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			pstat2.setTimestamp(3, timestamp);
			pstat2.executeUpdate();

			// 提交事务
			conn.commit();
			// 关闭事务
			conn.setAutoCommit(true);
			pstat2.close();
			pstat.close();
			conn.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
                logger.error(e1.getMessage());
				throw new RuntimeException(e1);
			}
			logger.error(e.getMessage());
			try {
				conn.close();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
				throw new RuntimeException(e1);
			}
		}

		try {
			UDPClient.SendMessage(antenna_no, content);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取一组天线号 和要发送的content，先将数据填入tb_upgradeRecord和tb_upgradeContent表中，
	 * 再开辟多线程进行同时向多个终端发送数据。
	 * @throws IOException 
	 */
	@Override
	public void insertAllAndSend(String[] antennas, String content) throws IOException {
		
		Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
		Properties props = new Properties();
		props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);

		String GUID = StringUtil.toGuid(content);
		Connection conn = JDBCUtils.getConnection();
		try {
			// 关闭自动提交 打开事务
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);

			// sql批处理
			for (String antenna_no : antennas) {
				pstat.setString(1, antenna_no);
				pstat.setString(2, GUID);
				pstat.addBatch();
			}
			pstat.executeBatch();

			String sql2 = "INSERT INTO 	tb_upgradeContent(GUID,content,dateTime) VALUES(?,?,?)";
			PreparedStatement pstat2 = conn.prepareStatement(sql2);
			pstat2.setString(1, GUID);
			pstat2.setString(2, content);
			// 将util中的date与sql的date相匹配
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			pstat2.setTimestamp(3, timestamp);
			pstat2.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);
			pstat2.close();
			pstat.close();
			conn.close();
			// JDBCUtils.closeConnection();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
				throw new RuntimeException(e1);
			}
			logger.error(e.getMessage());
			try {
				conn.close();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
				throw new RuntimeException(e1);
			}
		}

		try {
			// 根据天线号的多少开发相应的线程并发送数据
			ThreadPool.ExecutorCompletionService(antennas, content);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * 通过天线号向终端发送"U,X,AMC,VERSION,SIZE,size",用于程序升级
	 * @throws IOException 
	 */
	@Override
	public void sendForUpgrateProgram(String antenna_no, String AMC, String VERSION,String SIZE,String size) throws IOException {
		
		Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
		Properties props = new Properties();
		props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		try {
			UDPClient.sendMessageForUpgrateProgram(antenna_no, AMC, VERSION,SIZE,size);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
	
	

	/**
	 * 同时向多个终端发送升级命令，并接受相应数据
	 */
	@Override
	public void sendAllForUpgrateProgram(String[] antennas, String AMC, String VERSION, String SIZE, String size) throws IOException {

		Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
		Properties props = new Properties();
		props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
		try {
		ThreadPool.ExectorCompletionServiceForUpgrate(antennas, AMC, VERSION, SIZE, size);
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException(ex);
		}
	}



}
