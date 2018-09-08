package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MySatelliteRunnable implements Runnable {
	
    private String flag = "S";
	public String Msg = null;

	private String antenna;

	private String content;
	
	private Semaphore recvSemophore = new Semaphore(0);

	public MySatelliteRunnable(String antenna, String content) {
		this.antenna = antenna;
		this.content = content;
	}

	@Override
	public void run() {

		Logger logger = Logger.getLogger(MySatelliteRunnable.class);
		Properties props = new Properties();
		try {
			props.load(MySatelliteRunnable.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);

		String receivedMessage = null;

		// U,S,content
		String message = StringUtil.toText(content);

		// 通过连接池获得数据库连接
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		while (true) 
		{			
			SendMessageUtil.sendContent(antenna, message,flag);
			boolean isSemAvilable = false;
			try {
				isSemAvilable = recvSemophore.tryAcquire(30000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(isSemAvilable)
			{
				receivedMessage = Msg;
	
				String errHead = antenna + ":";
				if (receivedMessage.equals("U,S,SUCCESS")) 
				{
					try {
						String sql = "UPDATE tb_upgraderecord SET Pid = 1 WHERE antenna_no = ?";
						PreparedStatement pstat = conn.prepareStatement(sql);
						pstat.setString(1, antenna);
						pstat.executeUpdate();
						conn.commit();
						pstat.close();
						conn.close();							
						logger.debug(antenna + ":天线升级成功");
						} catch (Exception e) {
							e.printStackTrace();
						}
						errHead = "";
					} 
					else if(receivedMessage.equals("U,S,ERROR,FORMAT"))
					{
							errHead +="参数格式错误(";
					}
					else if(receivedMessage.equals("U,S,WARN,SAME-GUID")) 
					{
						errHead += "参数版本重复(";
					}
					else if(receivedMessage.equals("U,S,ERROR,FAILED")) 
					{
						errHead += "升级错误(";
					}  
					else
					{
						errHead += "未知回应(";
					}
					if(!errHead.equals(""))
					{
						logger.error(errHead + receivedMessage + ")");
					}
					//thread terminated when error or success
					break;
				}	
			int online = SendMessageUtil.selectOnlineByMid(antenna);
			if(online == 0)
			{
				logger.error(antenna+"：终端不在线，将停止发送");
				//thread terminated when online = 0
				break;
			}
		}
		
	}
	
	
	public void sendMsg(String Msg) {
		this.Msg = Msg;
		recvSemophore.release();
	}

}
