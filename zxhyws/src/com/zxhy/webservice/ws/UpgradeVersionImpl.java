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
	 * �����Ƚ�������Ϣ����tb_upgradeRecord��tb_upgradeContent���У�������������� ֮��Ҫ���͵���Ϣ������ͳ�ȥ
	 */
	@Override
	public void insertAndSendByNo(String antenna_no, String content) throws IOException {
		Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
		Properties props = new Properties();
		props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
		String GUID = StringUtil.toGuid(content);

		// �����ӳ����ȡ��������
		Connection conn = JDBCUtils.getConnection(); 
		try {
			// �ر��Զ��ύ
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
			// ��util�е�date��sql��date��ƥ��
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			pstat2.setTimestamp(3, timestamp);
			pstat2.executeUpdate();

			// �ύ����
			conn.commit();
			// �ر�����
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
	 * ��ȡһ�����ߺ� ��Ҫ���͵�content���Ƚ���������tb_upgradeRecord��tb_upgradeContent���У�
	 * �ٿ��ٶ��߳̽���ͬʱ�����ն˷������ݡ�
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
			// �ر��Զ��ύ ������
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);

			// sql������
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
			// ��util�е�date��sql��date��ƥ��
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
			// �������ߺŵĶ��ٿ�����Ӧ���̲߳���������
			ThreadPool.ExecutorCompletionService(antennas, content);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * ͨ�����ߺ����ն˷���"U,X,AMC,VERSION,SIZE,size",���ڳ�������
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
	 * ͬʱ�����ն˷������������������Ӧ����
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
