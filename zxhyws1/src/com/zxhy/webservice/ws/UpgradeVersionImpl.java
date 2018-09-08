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

import com.zxhy.webservice.Util.GUIDUtil;
import com.zxhy.webservice.Util.JDBCUtils;
import com.zxhy.webservice.Util.MyUDPReceive;
import com.zxhy.webservice.Util.PortUtil;
import com.zxhy.webservice.Util.StringUtil;
import com.zxhy.webservice.Util.ThreadPool;

import com.zxhy.webservice.entity.UpdateInfo;

@WebService
public class UpgradeVersionImpl implements UpgradeVersion {

	Logger logger = Logger.getLogger(UpgradeVersionImpl.class);

	public UpgradeVersionImpl() 
	{
		Properties props = new Properties();
		try {
			props.load(UpgradeVersionImpl.class.getClassLoader().getResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(props);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (PortUtil.isPortUsing(6000)) 
		{ 
		System.out.println("Start UDP revieve thread...");
		new Thread(new MyUDPReceive()).start();
		}
		

	}

	/**
	 * ��ȡһ�����ߺ� ��Ҫ���͵�content���Ƚ���������tb_upgradeRecord��tb_upgradeContent���У�
	 * �ٿ��ٶ��߳̽���ͬʱ�����ն˷������ݡ�
	 * 
	 * @throws IOException
	 */
	@Override
	public void SendUpdateInfo(UpdateInfo info) throws IOException {
		switch (info.cmd) {
		case "P_UPDATE":
			System.out.println(info.cmd + ":���������");
			UpdateInfoInitialize(info);
			try
			{
			// ���������̳߳�
			ThreadPool.ExecutorCompletionService(info);

			} catch (Exception e)
			{
				logger.error(e.getMessage());
			}
			break;
		case "X_UPDATE":
			System.out.println(info.cmd + ":��Ӕ�����");
			UpdateInfoProgram(info);
			try
			{
		    //���������̳߳�
			ThreadPool.ExectorCompletionServiceForUpgrate(info);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			break;
		case "S_UPDATE":
			System.out.println(info.cmd + ":���������");
			UpdateInfoSatellite(info);
			try
			{
			//���ǲ��������̳߳�
			ThreadPool.ExectorCompletionServiceForSatellite(info);
			}catch(Exception e )
			{
				logger.error(e.getMessage());
			}
			break;
		default:
			logger.error("unknown update command: " + info.cmd);
		}
	}
    
	//��������
	private void UpdateInfoInitialize(UpdateInfo info) {
		String GUID = StringUtil.toGuid(info.content);
		Boolean isGUID = true;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			// �ر��Զ��ύ ������
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);

			// sql������
			for (String antenna_no : info.terms) 
			{
				pstat.setString(1, antenna_no);
				pstat.setString(2, GUID);
				pstat.addBatch();
			}
			pstat.executeBatch();
            //����GUID �����ڵ�GUID�Ƚ� ����ͬ�ľͲ���ӵ����ݿ�
			String[] a = GUIDUtil.selectGUID();
			logger.debug("GUID��ͬ");
			for(int i = 0;i<a.length;i++) 
			{
				if(a[i].equals(GUID))
				{
					isGUID = false;
				}
			}
			if(isGUID)
			{
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
			}
		} catch (Exception e) 
		{
			try 
			{
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
	
	
	 //��������
	private void UpdateInfoProgram(UpdateInfo info) {
			Boolean isGUID = true;
			String GUID = StringUtil.toProgram(info.content);
			Connection conn = null;
			PreparedStatement pstat = null;
			
			try {
				conn = JDBCUtils.getConnection();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
			    //����GUID �����ڵ�GUID�Ƚ� ����ͬ�ľͲ���ӵ����ݿ�
				String[] a = GUIDUtil.selectGUID();
			    for(int i = 0;i<a.length;i++) 
		     	{
				    if(a[i].equals(GUID))
				    {
				       isGUID = false;
				    }
			    }
				if(isGUID)
				{
				// �ر��Զ��ύ ������
				conn.setAutoCommit(false);
				String sql2 = "INSERT INTO 	tbl_programerupgradecontent(GUID,content,dateTime) VALUES(?,?,?)";
	            pstat = conn.prepareStatement(sql2);
				pstat.setString(1, GUID);
				pstat.setString(2, info.content);
				// ��util�е�date��sql��date��ƥ��
				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());
				pstat.setTimestamp(3, timestamp);
				pstat.executeUpdate();
				}
				
				String sql1 = "INSERT INTO tbl_programerupgraderecord(antenna_no,GUID) VALUES(?,?)";
				PreparedStatement pstat2 = conn.prepareStatement(sql1);

				// sql������
				for (String antenna_no : info.terms) 
				{
					pstat2.setString(1, antenna_no);
					pstat2.setString(2, GUID);
					pstat2.addBatch();
				}
				pstat2.executeBatch();

				conn.commit();
				
				pstat2.close();
				pstat.close();
			    conn.close();
				// JDBCUtils.closeConnection();
				
			} catch (Exception e) {
				try 
				{
				  conn.rollback();
				} catch (SQLException e1)
				{
					logger.error(e1.getMessage());
				}
				
				logger.error(e.getMessage());
				
			}
			finally {
				
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	//���ǲ�������
	private void UpdateInfoSatellite(UpdateInfo info) {
		String GUID = StringUtil.toGuid(info.content);
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try 
		{
			// �ر��Զ��ύ ������
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO  tbl_satupgraderecoed(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);

			// sql������
			for (String antenna_no : info.terms) 
			{
				pstat.setString(1, antenna_no);
				pstat.setString(2, GUID);
				pstat.addBatch();
			}
			pstat.executeBatch();

			String sql2 = "INSERT INTO 	tbl_satupgradecontent(GUID,content,dateTime) VALUES(?,?,?)";
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

		} catch (Exception e) 
		{
			try 
			{
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
				
			}
			logger.error(e.getMessage());
			try
            {
				conn.close();
			} catch (SQLException e1) {
				logger.error(e1.getMessage());
			}
		}
	}
}
