package com.zxhy.webservice.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyUDPReceive1 implements Runnable{

	public static Map<String, MyRunnable2> map = new HashMap<String, MyRunnable2>();
    
	@Override
	public  void run() {
		
		int num = 1;
		while (true) {

			Logger logger = Logger.getLogger(MyUDPReceive1.class);
			Properties props = new Properties();
			try {
				props.load(UDPreceive3.class.getClassLoader().getResourceAsStream("log4j.properties"));
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			PropertyConfigurator.configure(props);

			// 设置接受端口
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket(6000);
			} catch (SocketException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			try {
				socket.receive(receivePacket);
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			// 得到返回信息的字符长度
			int length = receivePacket.getLength();
			// 得到String类型的返回信息
			String receiveMessage = new String(receiveData, 0, length);
			// 返回信息的开头就是天线号
			String antenna = StringUtil.toAntenna_no(receiveMessage);
			// U,P,XXX
			String receiveText = StringUtil.toReceiveMessage(receiveMessage);
			logger.info(receiveMessage);
			//map.put(antenna, receiveText);
			MyRunnable2 handle =  map.get(antenna);
			handle.sendMsg(receiveText);
			
			
			logger.info(map);
			logger.info("终端连接个数" + num++);
			socket.close();
	}

	}


}
