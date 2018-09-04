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

public class MySatelliteRunnable implements Runnable {

	boolean receiveSem = false;
	public String Msg = null;

	private String antenna;

	private String content;

	public MySatelliteRunnable(String antenna, String content) {
		this.antenna = antenna;
		this.content = content;
	}

	@Override
	public void run() {

		Logger logger = Logger.getLogger(MyRunnable.class);
		Properties props = new Properties();
		try {
			props.load(MyRunnable.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);

		String receivedMessage = null;

		// U,S,content
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
		//int failTries = 0;
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
				logger.error(antenna + ":卫星参数升级出现异常，请检查");
			}

			receivedMessage = Msg;

			if (receiveSem) {

				if (receivedMessage.equals("U,S,SUCCESS"))
				{
					try
					{
						String sql = "UPDATE  tbl_satupgraderecoed SET successUpgrade = 1 WHERE antenna_no = ?";
						PreparedStatement pstat = conn.prepareStatement(sql);
						pstat.setString(1, antenna);
						pstat.executeUpdate();

						conn.commit();
						pstat.close();
						conn.close();
						logger.debug(antenna + ":卫星参数传输成功");
						break;
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				} else if (receivedMessage.equals("U,S,ERROR,FORMAT")) 
				{

					logger.error(antenna + ":参数格式错误，请检查！！！" + "errorMessage:" + receivedMessage);
					break;

				} else if (receivedMessage.equals("U,S,WARN,SAME-GUID"))
				{
					logger.error(antenna + ":参数版本重复，请检查！！！" + "errorMessage:" + receivedMessage);
					break;
				} else if (receivedMessage.equals("U,S,ERROR,FAILED")) 
				{
					logger.error(antenna + ":返回错误信息，请检查！！！" + "errorMessage:" + receivedMessage);
					break;
				} else
				{
					logger.error(antenna + ":Unkown message:" + receivedMessage);
				}
			}
		}

	}

	public void sendMsg(String Msg) {
		this.Msg = Msg;
		this.receiveSem = true;
	}

}
