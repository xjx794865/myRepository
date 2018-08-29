package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * MyRunnableʵ����Callable�ӿ� ��Ҫ��дcall���� call��������ÿ���߳���Ҫ���еķ���
 * 
 * @author Lenovo
 *
 */
public class MyRunnable implements Runnable {

	boolean receiveSem = false;
	public String Msg = null;


	private String antenna;

	private String content;

	/**
	 * ������ ���ڴ������ߺź�content ���ڴ�������ߺŲ�ͬ ���͵��ն˾Ͳ�ͬ
	 * 
	 * @param antenna
	 * @param content
	 */
	public MyRunnable(String antenna, String content) {

		this.antenna = antenna;
		this.content = content;

	}

	@Override
	public void run() {
		
		try {
			UDPClient.SendMessage(antenna, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
  
	

	

}
