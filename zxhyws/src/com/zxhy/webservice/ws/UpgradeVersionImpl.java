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
import com.zxhy.webservice.Util.MyUDPReceive1;
import com.zxhy.webservice.Util.PortUtil;
import com.zxhy.webservice.Util.StringUtil;
import com.zxhy.webservice.Util.ThreadPool;
import com.zxhy.webservice.Util.UDPClient;
import com.zxhy.webservice.entity.UpdateInfo;


@WebService
public class UpgradeVersionImpl implements UpgradeVersion {
	
	Logger logger = Logger.getLogger(UpgradeVersionImpl.class);
	
	public UpgradeVersionImpl()
	{
		Properties props = new Properties();
		try
		{
			props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(props);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}
	/**
	 * ��ȡһ�����ߺ� ��Ҫ���͵�content���Ƚ���������tb_upgradeRecord��tb_upgradeContent���У�
	 * �ٿ��ٶ��߳̽���ͬʱ�����ն˷������ݡ�
	 * @throws IOException 
	 */
	@Override
	public void SendUpdateInfo(UpdateInfo info) throws IOException{
		switch(info.getCmd())
		{
		case "P_UPDATE":
			System.out.println(info.cmd+":���������");
			UpdateInfoInitialize(info);
			try {
				// �������ߺŵĶ��ٿ�����Ӧ���̲߳���������
				ThreadPool.ExecutorCompletionService(info);
		
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new RuntimeException(e);
			}			
			break;
		case "X_UPDATE":
			try {
				ThreadPool.ExectorCompletionServiceForUpgrate(info);
			}catch(Exception ex) {
				logger.error(ex.getMessage());
				throw new RuntimeException(ex);
			}
			break;
		case "S_UPDATE":
			
		default:
			System.out.println("unknown update command: " + info.cmd);
		}
	}

	private void UpdateInfoInitialize(UpdateInfo info)
	{
		String GUID = StringUtil.toGuid(info.content);
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			// �ر��Զ��ύ ������
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);
	
			// sql������
			for (String antenna_no : info.terms) {
				pstat.setString(1, antenna_no);
				pstat.setString(2, GUID);
				pstat.addBatch();
			}
			pstat.executeBatch();
	
			String sql2 = "INSERT INTO 	tb_upgradeContent(GUID,content,dateTime) VALUES(?,?,?)";
			PreparedStatement pstat2 = conn.prepareStatement(sql2);
			pstat2.setString(1, GUID);
			pstat2.setString(2, info.content);
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
	}
}
	
