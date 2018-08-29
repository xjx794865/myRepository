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
 * MyRunnable实现了Callable接口 需要重写call方法 call方法就是每个线程所要运行的方法
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
	 * 构造器 用于传入天线号和content 由于传入的天线号不同 发送的终端就不同
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
