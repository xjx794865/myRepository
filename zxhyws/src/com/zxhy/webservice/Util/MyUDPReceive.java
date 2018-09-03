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

/**
 * �汾1
 * @author Lenovo
 *
 */

public class MyUDPReceive implements Runnable {

	public static Map<String, String> map = new HashMap<String, String>();

	@Override
	public void run() {

		int num = 1;

		while (true) {
			Logger logger = Logger.getLogger(UDPreceive3.class);
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

			// �õ�������Ϣ���ַ�����
			int length = receivePacket.getLength();
			// �õ�String���͵ķ�����Ϣ
			String receiveMessage = new String(receiveData, 0, length);
			// ������Ϣ�Ŀ�ͷ�������ߺ�
			String antenna = StringUtil.toAntenna_no(receiveMessage);
			// U,P,XXX
			String receiveText = StringUtil.toReceiveMessage(receiveMessage);
			logger.debug(receiveMessage);
			map.put(antenna, receiveText);
			logger.debug(map);
			logger.debug("�ն����Ӹ���" + num++);
			socket.close();

		}

	}
}
