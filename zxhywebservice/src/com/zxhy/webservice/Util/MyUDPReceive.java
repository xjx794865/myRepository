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

public class MyUDPReceive implements Runnable{

	public static Map<String, Object> map = new HashMap<String, Object>();

	public void run() {

		int num = 1;
		while (true) {

			Logger logger = Logger.getLogger(MyUDPReceive.class);
			Properties props = new Properties();
			try {
				props.load(UDPreceive3.class.getClassLoader().getResourceAsStream("log4j.properties"));
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			PropertyConfigurator.configure(props);

			// ���ý��ܶ˿�
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket(8900);
			} catch (SocketException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}

			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			try {
				socket.receive(receivePacket);
			} catch (IOException e) {
				logger.error(e.getMessage()+"�����̳߳���...");
				e.printStackTrace();
			}

			// �õ�������Ϣ���ַ�����
			int length = receivePacket.getLength();
			// �õ�String���͵ķ�����Ϣ
			String receiveMessage = new String(receiveData, 0, length);
			// ������Ϣ�Ŀ�ͷ�������ߺ�
			String antenna = StringUtil.toAntenna_no(receiveMessage);
			// U,P,XXX
			String receiveText = StringUtil.toReceiveMessage(receiveMessage);
			logger.info("myUDPReceive :"+receiveMessage);
			// map.put(antenna, receiveText);
			if (map.get(antenna) instanceof MyRunnable)
			{
				MyRunnable handle = (MyRunnable) map.get(antenna);
				handle.sendMsg(receiveText);
				//get��map��͌�������k   remove
				map.remove(antenna);
			} else if (map.get(antenna) instanceof MyProgramRunnable) 
			{
				MyProgramRunnable programhandle = (MyProgramRunnable) map.get(antenna);
				programhandle.sendMsg(receiveText);
				//get��map��͌�������k    remove
				map.remove(antenna);
			} else if (map.get(antenna) instanceof MySatelliteRunnable)
			{
				MySatelliteRunnable satellitehandle = (MySatelliteRunnable) map.get(antenna);
				satellitehandle.sendMsg(receiveText);
				//get��map��͌�������k    remove
				map.remove(antenna);
			}

			logger.info(map);
			logger.info("�ն����Ӵ���" + num++);
			socket.close();
		}

	}

}
