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

public class MyProgramRunnable implements Runnable{
	
	private String flag = "X";
	
	public String Msg = null;

	private String antenna;

	private String content;
	
	private Semaphore recvSemophore = new Semaphore(0);
	
	public MyProgramRunnable(String antenna, String content)
	{
		this.antenna = antenna;
		this.content = content;
	}

	@Override
	public void run() {

		Logger logger = Logger.getLogger(MyProgramRunnable.class);
		Properties props = new Properties();
		try {
			props.load(MyProgramRunnable.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);

		String receivedMessage = null;

		// U,X,content
		//String message = StringUtil.toProgramContent(content);

		// ͨ�����ӳػ�����ݿ�����
		Connection 	conn = JDBCUtils.getConnection();
            while (true)
            {
            logger.debug(content);
			SendMessageUtil.sendContent(antenna, content,flag);
			boolean isSemAvilable = false;
			
			try 
			{
				isSemAvilable = recvSemophore.tryAcquire(30000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			if(isSemAvilable)
			{
			    receivedMessage = Msg;

				if (receivedMessage.equals("U,X,SUCCESS")) 
				{
					try {
						String sql = "UPDATE tbl_programerupgraderecord SET successUpgrade = 1 WHERE antenna_no = ?";
						PreparedStatement pstat = conn.prepareStatement(sql);
						pstat.setString(1, antenna);
						pstat.executeUpdate();

						conn.commit();
						pstat.close();
						conn.close();
					    logger.debug(antenna + ":���������ɹ�");
					    break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				   
				
				} else if (receivedMessage.equals("U,X,ERROR,MODEL"))
				{
					
					logger.error(antenna + ":�����ͺŲ�ƥ��"+"("+receivedMessage+")");
					break;
					
				}else if (receivedMessage.equals("U,X,WARN,IN-PROCESSING"))
				{
					logger.error(antenna + ":������������"+"("+receivedMessage+")");
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}else if (receivedMessage.equals("U,X,WARN,SAME-VERSION"))
				{
					logger.error(antenna + ":�����汾������һ��"+"("+receivedMessage+")");
					break;
				}else if (receivedMessage.equals("U,X,WARN,TRACER-VERSION"))
				{
					logger.debug(antenna + ":TRACER-VERSION,���������ͣ�����"+"("+receivedMessage+")");
				}else if(receivedMessage.equals("U,X,ERROR,DOWNLOAD"))
				{
					logger.debug(antenna + ":DOWNLOAD��������"+"("+receivedMessage+")");
					break;
				}
				else
				{
					logger.error(antenna+":Unknow message"+"("+receivedMessage+")");
					break;
				}
			}
			
			int online = SendMessageUtil.selectOnlineByMid(antenna);
			if(online == 0)
			{
				logger.error(antenna+"���ն˲����ߣ���ֹͣ����");
				//thread terminated when error
				break;
			}
			
		}
		
	}
	
	public void sendMsg(String Msg) 
	{
		//�������߳̽��յ������ݴ���Msg
		this.Msg = Msg;
		//�ͷ�һ���ź���
		recvSemophore.release();
		
	}
	

}
