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
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	
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

				if (receivedMessage.equals("U,X,SUCCESS ")) 
				{
					//TO DO
				    logger.debug(antenna + ":���������ɹ�");
					break;
				
				} else if (receivedMessage.equals("U,X,ERROR,MODEL"))
				{
					
					logger.error(antenna + ":�����ͺŲ�ƥ��"+"("+receivedMessage+")");
					break;
					
				}else if (receivedMessage.equals("U,X,WARN,IN-PROGRESS"))
				{
					logger.error(antenna + ":������������"+"("+receivedMessage+")");
					break;
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
			
		}
		
	}
	
	public void sendMsg(String Msg) 
	{
		this.Msg = Msg;
		
		recvSemophore.release();
		
	}
	

}
