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

import sun.awt.Mutex;

/**
 * MyRunnableʵ����Callable�ӿ� ��Ҫ��дcall���� call��������ÿ���߳���Ҫ���еķ���
 * 
 * @author Lenovo
 *
 */
public class MyRunnable2 implements Runnable {

	boolean receiveSem = false;
	public String Msg = null;

	private String antenna;

	private String content;
	
	private Mutex msgMutex = new Mutex();

	/**
	 * ������ ���ڴ������ߺź�content ���ڴ�������ߺŲ�ͬ ���͵��ն˾Ͳ�ͬ
	 * 
	 * @param antenna
	 * @param content
	 */
	public MyRunnable2(String antenna, String content) {

		this.antenna = antenna;
		this.content = content;

	}

	@Override
	public void run() 
	{
		Logger logger = Logger.getLogger(UDPClient.class);
		Properties props = new Properties();
		try {
			props.load(UDPClient.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);

		String receivedMessage = null;

		// U,P,content
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
			try {
				socket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (tries > 10) {
				logger.error(antenna + ":�������������쳣������");
			}

			receivedMessage = Msg;
		
			if (receiveSem) {
				
				if (receivedMessage.equals("U,P,OK")) 
				{
					try {
						String sql = "UPDATE tb_upgraderecord SET Pid = 1 WHERE antenna_no = ?";
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
				} else if (receivedMessage.equals("U,P,FAIL"))
				{
					failTries++;
					
					if(failTries > 10) 
					{
					logger.error(antenna + ":���߷���ʧ����Ϣ�������е�" + failTries + "������");
					break;
					}
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
