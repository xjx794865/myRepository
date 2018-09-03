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
	 * 获取一组天线号 和要发送的content，先将数据填入tb_upgradeRecord和tb_upgradeContent表中，
	 * 再开辟多线程进行同时向多个终端发送数据。
	 * @throws IOException 
	 */
	@Override
	public void SendUpdateInfo(UpdateInfo info) throws IOException{
		switch(info.getCmd())
		{
		case "P_UPDATE":
			System.out.println(info.cmd+":添加数据中");
			UpdateInfoInitialize(info);
			try {
				// 根据天线号的多少开发相应的线程并发送数据
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
			// 关闭自动提交 打开事务
			conn.setAutoCommit(false);
			String sql1 = "INSERT INTO tb_upgradeRecord(antenna_no,GUID) VALUES(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql1);
	
			// sql批处理
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
	}
}
	
