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
 * MyRunnable实现了Callable接口 需要重写call方法 call方法就是每个线程所要运行的方法
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
	 * 构造器 用于传入天线号和content 由于传入的天线号不同 发送的终端就不同
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

		// 通过连接池获得数据库连接
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

		// 获得要发送的信息并打包
		byte[] data = message.getBytes();

		// 设置阻塞时间
		// socket.setSoTimeout(TIMEOUT);

		DatagramPacket sendPacket = null;

		try {
			sendPacket = new DatagramPacket(data, data.length, inet, InetAddressUtils.returnPort(antenna));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 循环次数
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
				logger.error(antenna + ":天线升级出现异常，请检查");
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
						logger.debug(antenna + ":天线升级成功");
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (receivedMessage.equals("U,P,FAIL"))
				{
					failTries++;
					
					if(failTries > 10) 
					{
					logger.error(antenna + ":天线返回失败信息，将进行第" + failTries + "次推送");
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
