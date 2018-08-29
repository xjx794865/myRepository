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
 * ͨ�����ߺŻ��IP��port����content������ͳ�ȥ
 * 
 * @author Lenovo
 *
 */
public class UDPClient {
	

	// ���ó�ʱΪ3��
	//private static final int TIMEOUT = 3000;
	// ����ط�����5��
	// private static final int MAXTRIES = 5;

	public static void SendMessage(String antenna_no, String content) throws IOException, SQLException {
   
		Logger logger = Logger.getLogger(UDPClient.class);
		Properties props = new Properties();
		props.load(UDPClient.class.getClassLoader().getResourceAsStream("log4j.properties"));
		PropertyConfigurator.configure(props);
		
	    String	receivedMessage  = null;
		
		// U,P,content
		String message = StringUtil.toMessage(content);

		// ͨ�����ӳػ�����ݿ�����
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

		// ���Ҫ���͵���Ϣ�����
		byte[] data = message.getBytes();

		// ��������ʱ��
		//socket.setSoTimeout(TIMEOUT);

		DatagramPacket sendPacket = new DatagramPacket(data, data.length, inet,
				InetAddressUtils.returnPort(antenna_no));
		//ѭ������
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
				 logger.debug(antenna_no+":���ͳ���10�Σ���ע��  ���ʹ���Ϊ"+tries);
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
			logger.debug(antenna_no+":���������ɹ�");

		} else if (receivedMessage.equals("U,P,FAIL")) {
			logger.debug(antenna_no+":���߷���ʧ����Ϣ�������еڶ�������");
			do {
				// ������Ϣ
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
					logger.debug(antenna_no+":���ͳ���10�Σ���ע�⣬��ǰ���ʹ���"+tries);
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
				logger.debug(antenna_no+":���������ɹ�");

			} else if (receivedMessage.equals("U,P,FAIL")) {
				logger.error(antenna_no+":���������Ƿ�����");

			}

		}

		socket.close();
	}

	
	/**
	 * ������������  ���ն˷���"U,X,AMC,SN,SIZE,size" ����ն������ɹ��򷵻�"U,X,OK" ����ն�����ʧ�� ����ʧ����Ϣ 
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
        //���Ҫ���͵���Ϣ	
		byte[] data = message.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(data, data.length, inet,
				InetAddressUtils.returnPort(antenna_no));

		int tries = 0;
		do {
			// ������Ϣ
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
				logger.debug(antenna_no+":���ͳ���10�Σ���ע�⣬��ǰ���ʹ���"+tries);
			}
	
		} while (receiveMessage == null);
		
		
		if(receiveMessage.equals("U,X,OK")) {
			
			logger.debug(antenna_no+":���������ɹ�");
			
		}else {
			
			logger.error(antenna_no+":"+receiveMessage);
			
		}
			

		
		socket.close();

	}

}
