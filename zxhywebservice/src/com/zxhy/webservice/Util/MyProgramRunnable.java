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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyProgramRunnable implements Runnable{
	
	boolean receiveSem = false;
	public String Msg = null;

	private String antenna;

	private String content;
	
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
		String message = StringUtil.toMessage(content);

		// ͨ�����ӳػ�����ݿ�����
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		InetAddress inet = null;

		DatagramSocket socket = null;
		try {
			inet = InetAddress.getByName(InetAddressUtils.returnAddress(antenna));
		} catch (UnknownHostException e1) {

			logger.error(e1.getMessage());
			throw new RuntimeException(e1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			socket = new DatagramSocket();
		} catch (SocketException e1) {

			logger.error(e1.getMessage());
			throw new RuntimeException(e1);
		}

		// ���Ҫ���͵���Ϣ�����
		byte[] data = message.getBytes();

		// ��������ʱ��
		// socket.setSoTimeout(TIMEOUT);

		DatagramPacket sendPacket = null;

		try {
			sendPacket = new DatagramPacket(data, data.length, inet, InetAddressUtils.returnPort(antenna));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ѭ������
		int tries = 0;
		int failTries = 0;
		while (true) {

			tries++;
			System.out.println(tries);
			try
			{
				socket.send(sendPacket);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(3000);
			} catch (InterruptedException e1)
			{
				
				e1.printStackTrace();
			}

			if (tries > 10) 
			{
				logger.error(antenna + ":�ն˳������������쳣������");
			}

			receivedMessage = Msg;
		
			if (receiveSem) {
				
				if (receivedMessage.equals("U,X,SUCCESS ")) 
				{
				    logger.debug(antenna + ":���������ɹ�");
					break;
				
				} else if (receivedMessage.equals("U,X,ERROR,MODEL"))
				{
					
					logger.error(antenna + ":�����ͺŲ�ƥ�䣬���飡����"+"errorMessage:"+receivedMessage);
					break;
					
				}else if (receivedMessage.equals("U,X,WARN,IN-PROGRESS"))
				{
					logger.error(antenna + ":����������������ֹͣ���ͣ�����"+"errorMessage:"+receivedMessage);
					break;
				}else if (receivedMessage.equals("U,X,WARN,SAME-VERSION"))
				{
					logger.error(antenna + ":�����汾������һ�£����飡����"+"errorMessage:"+receivedMessage);
					break;
				}else if (receivedMessage.equals("U,X,WARN,TRACER-VERSION"))
				{
					logger.debug(antenna + "TRACER-VERSION,���������ͣ�����"+"errorMessage:"+receivedMessage);
				}
				else
				{
					logger.error(antenna+":Unknow message"+receivedMessage);
					break;
				}
			}
			
		}
		
	}
	
	public void sendMsg(String Msg) 
	{
		this.Msg = Msg;
		this.receiveSem = true;
	}
	

}
