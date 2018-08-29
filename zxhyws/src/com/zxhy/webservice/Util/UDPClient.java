package com.zxhy.webservice.Util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.zxhy.webservice.entity.Terminal;

/**
 * 通过天线号获得IP和port并将content打包发送出去
 * 
 * @author Lenovo
 *
 */
public class UDPClient {
	

	// 设置超时为3秒
	//private static final int TIMEOUT = 3000;
	// 最大重发次数5次
	// private static final int MAXTRIES = 5;

	public static void SendMessage(String antenna_no, String content) throws IOException, SQLException {
   
		Logger logger = Logger.getLogger(UDPClient.class);
		Properties props = new Properties();
		props.load(UDPClient.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
	    String	receivedMessage  = null;
		
		// U,P,content
		String message = StringUtil.toMessage(content);

		// 通过连接池获得数据库连接
		Connection conn = JDBCUtils.getConnection();

		InetAddress inet = null;

		DatagramSocket socket = null;
		try {
			inet = InetAddress.getByName(InetAddressUtils.returnAddress(antenna_no));
		} catch (UnknownHostException e1) {

			logger.error(e1.getMessage());
			throw new RuntimeException(e1);
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
		//socket.setSoTimeout(TIMEOUT);

		DatagramPacket sendPacket = new DatagramPacket(data, data.length, inet,
				InetAddressUtils.returnPort(antenna_no));
		//循环次数
		int tries = 0;
		
		
        do {
	 
			socket.send(sendPacket);
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
			 receivedMessage = MyUDPReceive.map.get(antenna_no);
			logger.debug(MyUDPReceive.map.get(antenna_no));
			 
			 tries++;
			 
			 if(tries > 10) {
				 logger.debug(antenna_no+":发送超过10次，请注意  发送次数为"+tries);
			 }
			
        }while(receivedMessage == null);
		

		if (receivedMessage.equals("U,P,OK")) {
			String sql = "UPDATE tb_upgraderecord SET Pid = 1 WHERE antenna_no = ?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, antenna_no);
			pstat.executeUpdate();

			conn.commit();
			pstat.close();
			conn.close();
			logger.debug(antenna_no+":天线升级成功");

		} else if (receivedMessage.equals("U,P,FAIL")) {
			logger.debug(antenna_no+":天线返回失败信息，将进行第二次推送");
			do {
				// 发送消息
				socket.send(sendPacket);
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
				tries++;

				receivedMessage = MyUDPReceive.map.get(antenna_no);
				
				
				if(tries > 10 ) {
					logger.debug(antenna_no+":发送超过10次，请注意，当前发送次数"+tries);
				}

			} while (receivedMessage == null);

			

			if (receivedMessage.equals("U,P,OK")) {
				String sql = "UPDATE tb_upgraderecord SET Pid = 1 WHERE antenna_no = ?";
				PreparedStatement pstat = conn.prepareStatement(sql);
				pstat.setString(1, antenna_no);
				pstat.executeUpdate();

				conn.commit();
				pstat.close();
				conn.close();
				logger.debug(antenna_no+":天线升级成功");

			} else if (receivedMessage.equals("U,P,FAIL")) {
				logger.error(antenna_no+":请检查天线是否正常");

			}

		}

		socket.close();
	}

	
	/**
	 * 程序升级命令  向终端发送"U,X,AMC,SN,SIZE,size" 如果终端升级成功则返回"U,X,OK" 如果终端升级失败 返回失败信息 
	 * @param antenna_no
	 * @param AMC
	 * @param SN
	 * @param SIZE
	 * @param size
	 * @throws IOException
	 */
	public static void sendMessageForUpgrateProgram(String antenna_no, String AMC, String VERSION,String SIZE,String size) throws IOException {
       
		Logger logger = Logger.getLogger(UDPClient.class);
		Properties props = new Properties();
		props.load(UDPClient.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
		String receiveMessage = null;
		
		String message = StringUtil.toUpgrateProgram(AMC, VERSION, SIZE, size);

		InetAddress inet = null;
		DatagramSocket socket = null;
		try {
			inet = InetAddress.getByName(InetAddressUtils.returnAddress(antenna_no));
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
        //打包要发送的信息	
		byte[] data = message.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(data, data.length, inet,
				InetAddressUtils.returnPort(antenna_no));

		int tries = 0;
		do {
			// 发送消息
			socket.send(sendPacket);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tries++;
			
			receiveMessage = MyUDPReceive.map.get(antenna_no);
			logger.debug(MyUDPReceive.map);
			
			if(tries > 10 ) {
				logger.debug(antenna_no+":发送超过10次，请注意，当前发送次数"+tries);
			}
	
		} while (receiveMessage == null);
		
		
		if(receiveMessage.equals("U,X,OK")) {
			
			logger.debug(antenna_no+":程序升级成功");
			
		}else {
			
			logger.error(antenna_no+":"+receiveMessage);
			
		}
			

		
		socket.close();

	}

}
